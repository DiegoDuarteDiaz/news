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

package cl.ucn.disc.dsm.dduarte.news.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ModelAdapter;

import java.util.List;

import cl.ucn.disc.dsm.dduarte.news.R;
import cl.ucn.disc.dsm.dduarte.news.model.News;
import cl.ucn.disc.dsm.dduarte.news.services.ContractImplNewsApi;
import cl.ucn.disc.dsm.dduarte.news.services.Contracts;

;

/**
 * The Main Class
 * @author Diego Duarte Diaz
 */
public class MainActivity extends AppCompatActivity {

    /**
     * The ListView.
     */
    protected ListView listView;

    /**
     * OnCreate.
     * @param savedInstanceState used to reload the app.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //The toolbar
        this.setSupportActionBar(findViewById(R.id.am_t_toolbar));

        // The FastAdapter
        ModelAdapter<News, NewsItem> newsAdapter = new ModelAdapter<>(NewsItem::new);
        FastAdapter<NewsItem> fastAdapter = FastAdapter.with(newsAdapter);
        fastAdapter.withSelectable(false);

        // The Recycler view
        RecyclerView recyclerView = findViewById(R.id.am_rv_news);
        recyclerView.setAdapter(fastAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        //Get the news in the background thread
        AsyncTask.execute(()->{
            //Using the contracts to get the news
            Contracts contracts = new ContractImplNewsApi("ffb8d49a73ad48b88608e103b0537e01");
            //Get the News from NewsApi (internet)
            List<News> listNews = contracts.retrieveNews(30);
            /*//Build the simple adapter to show the list of news (string)
            ArrayAdapter<String> adapter = new ArrayAdapter(
                    this, android.R.layout.simple_list_item_1, listNews
            );*/
            //Set the adapter!
            runOnUiThread(() ->{
                newsAdapter.add(listNews);
            });

        });
    }
}