/*
 * Copyright (c) 2020.
 *
 * Copyright 2020 Diego Duarte Diaz diego.duarte@alumnos.ucn.cl
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package cl.ucn.disc.dsm.dduarte.news.services;

import com.kwabenaberko.newsapilib.models.Article;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import cl.ucn.disc.dsm.dduarte.news.model.News;
import cl.ucn.disc.dsm.dduarte.news.utils.Validation;


public class ContractImplNewsApi implements Contracts {
    /**
     * The Logger
     */
    private static final Logger log = LoggerFactory.getLogger(ContractImplNewsApi.class);

    /**
     * The connection to NewsApi
     */
    private final NewApiService newsApiServices;



    public ContractImplNewsApi(String apiKey) {
        Validation.notNull(apiKey, "ApiKey !!");
        this.newsApiServices = new NewApiService(apiKey);
    }

    /**
     * The Assembler/Transformer pattern!
     *
     * @param article used to source
     * @return the News.
     */
    private static News toNews(final Article article) {
        Validation.notNull(article, "Article null !?!");
        // Warning message?
        boolean needFix = false;
        // Fix the author null : (
        if (article.getAuthor() == null || article.getAuthor().length() == 0) {
            article.setAuthor("No author*");
            needFix = true;
        }
        // Fix more restrictions :(
        if (article.getDescription() == null || article.getDescription().length() == 0) {
            article.setDescription("No description*");
            needFix = true;
        }

        // .. yes, warning message.
        if (needFix) {
            // Debug of Article
            log.warn("Article with invalid restrictions: {}.", ToStringBuilder.reflectionToString(article, ToStringStyle.MULTI_LINE_STYLE
            ));
        }

        // The date
        ZonedDateTime publishedAt = ZonedDateTime
                .parse(article.getPublishedAt())
                .withZoneSameInstant(ZoneId.of("-3"));

        // The News
        return new News(
                article.getTitle(), article.getSource().getName(), article.getAuthor(), article.getUrl(), article.getUrlToImage(), article.getDescription(),
                article.getDescription(), // FIXME: Where is the content?
                publishedAt
        );
    }
    @Override
    public List<News> retrieveNews(Integer size) {
        try{
            //Request to NewsApi
            List<Article> articles = this.newsApiServices.getTopHeadlines("general", size);

            //The final list of news
            List <News> news = new ArrayList<>();
            for (Article article : articles){
                //Article -> News
                news.add(article2news(article));
            }
            //return the list of news.
            return news.stream()
                    //Remote the duplicates (by id)
                    .filter(distintById(News::getId))
                    //Sort the stream by publishedAt
                    .sorted((k1,k2) -> k2.getPublishedAt().compareTo(k1.getPublishedAt()))
                    //return the stream to list
                    .collect(Collectors.toList());
        }catch(IOException e){
            log.error("error", e);
            //Inner exception
            throw new RuntimeException(e);
        }
    }

    /**
     * Filter the stream
     * @param idExtractor
     * @param <T> news to filter
     * @return true if the news already exists.
     */
    private static <T>Predicate<T>distintById(Function<? super T, ?> idExtractor){
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(idExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * Article to News. (Transformer Pattern).
     * @param article to convert.
     * @return the news.
     */
    private static News article2news(Article article) {
        //Debug for Article
        log.debug("Articles: {}.", ToStringBuilder.reflectionToString(article, ToStringStyle.MULTI_LINE_STYLE));

        //the date
        ZonedDateTime publishedAt = ZonedDateTime.parse(article.getPublishedAt()).withZoneSameInstant(ZoneId.of("-3"));

        if(article.getAuthor() == null){
            log.warn("Article without author!!");
            return null;
        }
        if(article.getDescription() == null){
            log.warn("Article without description!!");
            return null;
        }

        //fixing the restrictions :
        if (article.getAuthor() == null){
            article.setAuthor("No Author");
        }
        //fixing the description
        if (article.getDescription() == null || article.getDescription().length() == 0){
            article.setAuthor("No Description");
        }

        return new News(
                article.getTitle(),
                article.getSource().getName(),
                article.getAuthor(),
                article.getUrl(),
                article.getUrlToImage(),
                article.getDescription(),
                article.getDescription(), //FIXME: Where is the content?
                publishedAt
        );
    }

    @Override
    public void saveNews(News news) {
        throw new NotImplementedException("Cant save news in NewsAPI!");
    }
}
