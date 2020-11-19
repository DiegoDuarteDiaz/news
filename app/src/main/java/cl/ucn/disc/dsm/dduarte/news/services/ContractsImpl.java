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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import cl.ucn.disc.dsm.dduarte.news.model.News;

/**
 * @author Diego Duarte Diaz
 */
public class ContractsImpl implements Contracts {

    /**
     * The Logger
     */
    private static final Logger log = LoggerFactory.getLogger(ContractsImpl.class);

    /**
     * The list of news
     */
    private final List<News> news = new ArrayList<>();

    /**
     * Get the list of news
     * @param size size of the list.
     * @return List of News
     */

    @Override
    public List<News> retrieveNews(final Integer size) {
        //The last "size" elements.
        return news.subList(news.size() - size, news.size());
    }

    /**
     * Save one News into the System
     * @param news to save
     */
    @Override
    public void saveNews (final News news){
        //Fixme: Don't allow duplicated!!
        this.news.add(news);
    }

}
