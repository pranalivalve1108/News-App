package com.example.newsupdates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView recycler_view;
    List<Article> articleList = new ArrayList<>();
    NewsRecyclerAdapter adapter;
    LinearProgressIndicator progress_bar;
    Button btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7;
    SearchView search_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler_view=findViewById(R.id.recycler_view);
        progress_bar = findViewById(R.id.progress_bar);
        search_view = findViewById(R.id.search_view);

        btn_1 =findViewById(R.id.btn_1);
        btn_2 =findViewById(R.id.btn_2);
        btn_3 =findViewById(R.id.btn_3);
        btn_4 =findViewById(R.id.btn_4);
        btn_5 =findViewById(R.id.btn_5);
        btn_6 =findViewById(R.id.btn_6);
        btn_7 =findViewById(R.id.btn_7);

        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);

        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getNews("General",query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        setUpRecyclerView();
        getNews("General",null);
    }

    void setUpRecyclerView(){
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter=new NewsRecyclerAdapter(articleList);
        recycler_view.setAdapter(adapter);
    }

    void changeInProgress(boolean show){
        if (show)
            progress_bar.setVisibility(View.VISIBLE);
        else
            progress_bar.setVisibility(View.INVISIBLE);
    }


    void getNews(String category,String query){
        changeInProgress(true);
        NewsApiClient newsApiClient = new NewsApiClient("0f30f13de2df4ff6963e2193d8332a28");
        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                        .language("en")
                        .q(query)
                        .category(category)
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {

                        runOnUiThread(()->{
                            changeInProgress(false);
                            articleList = response.getArticles();
                            adapter.updateData(articleList);
                            adapter.notifyDataSetChanged();
                        });
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.i("Got Failure",throwable.getMessage());
                    }
                }
        );
    }

    @Override
    public void onClick(View view) {
        Button button =(Button) view;
        String category = button.getText().toString();
        getNews(category,null);
    }
}