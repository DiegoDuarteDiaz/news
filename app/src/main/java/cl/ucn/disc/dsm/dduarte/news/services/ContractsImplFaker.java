package cl.ucn.disc.dsm.dduarte.news.services;

import com.github.javafaker.Faker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.List;




import cl.ucn.disc.dsm.dduarte.news.model.News;

/**
 * The Faker implementation of {@link Contracts}
 * @author Diego Duarte Diaz
 */
public final class ContractsImplFaker implements Contracts {

    /**
     * The Logger
     */
    private static final Logger log = LoggerFactory.getLogger(ContractsImpl.class);

    /**
     * The list of news
     */
    private final List<News> news = new ArrayList<>();

    public ContractsImplFaker() {
        final Faker faker = Faker.instance();

        for(int i = 0; i<5; i++){
            this.news.add(new News(
                //Integer.toUnsignedLong(1),
                faker.book().title(),
                faker.name().username(),
                faker.name().fullName(),
                faker.internet().url(),
                faker.internet().avatar(),
                faker.harryPotter().quote(),
                faker.lorem().paragraph(3),
                ZonedDateTime.now(ZoneId.of("-3"))
            ));

        }

    }

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