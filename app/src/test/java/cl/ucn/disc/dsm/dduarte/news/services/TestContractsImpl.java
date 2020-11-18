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

import com.github.javafaker.Faker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import cl.ucn.disc.dsm.dduarte.news.model.News;

//import org.junit.platform.commons.logging.Logger;
//import org.junit.platform.commons.logging.LoggerFactory;


/**
 * Testing of ContractImpl
 * @author Diego Duarte Diaz
 */
public class TestContractsImpl {
    /**
     * The Logger
     */
    private static final Logger log = LoggerFactory.getLogger(TestContractsImpl.class);

    @Test
    public void testRetrieveNews() {
        /**
         * The Test of Retrieve news.
         */
        log.debug("Testing..");

        // The implementation
        Contracts contracts = new ContractsImpl();

        // Call the method
        List<News> news = contracts.retrieveNews(10);
        Assertions.assertNotNull(news, "List was null :(");
        Assertions.assertTrue(news.size() != 0, "Empty List? :(");
        Assertions.assertTrue(news.size()==10, "List size != 5 :(");
        log.debug("Done.");
    }

    /**
     * Show the faker
     */
    @Test
    public void testFaker(){

        //build the faker
        Faker faker = Faker.instance();
        for (int i =0; i<10;i++){
            log.debug("Name: {}", faker.name().fullName());
            // FIXME: Remover
            System.out.println("Name: " + faker.name().fullName());
            System.out.println("Cat: " + faker.cat().name());

        }
    }

}
