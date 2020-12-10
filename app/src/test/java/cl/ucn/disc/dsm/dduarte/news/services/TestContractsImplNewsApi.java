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


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import cl.ucn.disc.dsm.dduarte.news.model.News;


/**
 * Testing the contracts with NewsApi service
 * @author Diego Duarte Diaz
 */
public final class TestContractsImplNewsApi {
    /**
     * The logger
     */
    private static final Logger log = LoggerFactory.getLogger(TestContractsImplNewsApi.class);
    /**
     * The Test
     */
    @Test
    public void testRetrieveNews(){
        log.debug("testing..");

        // The apiKey from: https://newsapi.org/account
        String apiKey = "ffb8d49a73ad48b88608e103b0537e01";

        Contracts contracts = new ContractImplNewsApi(apiKey);
        List<News> news = contracts.retrieveNews(10);
        Assertions.assertNotNull(news, "List null");
        Assertions.assertEquals(10,news.size(),"Wrong size!");

        for(News n :news){
            log.debug("News: {}.", ToStringBuilder.reflectionToString(n, ToStringStyle.MULTI_LINE_STYLE));
        }

        log.debug(".. done");

    }
}
