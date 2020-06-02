package com.digger.app.ui.news;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.digger.app.R;
import com.digger.app.adapter.NewsAdapter;
import com.digger.app.model.Alert;
import com.digger.app.model.News;

import java.util.ArrayList;

public class NewsFragment extends Fragment {

    private NewsViewModel newsViewModel;

    private RecyclerView recyclerView;





    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newsViewModel =
                ViewModelProviders.of(this).get(NewsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_news, container, false);

        recyclerView = root.findViewById(R.id.list_news);


        final NewsAdapter adapter = new NewsAdapter();

        newsViewModel.getNews().observe(this, new Observer<ArrayList<News>>() {
            @Override
            public void onChanged(ArrayList<News> news) {
                if(news != null) {
                    adapter.setNews(news);
                    adapter.notifyDataSetChanged();
                }
            }
        });



        adapter.setListener(new ArticleClickListner() {
            @Override
            public void onClick(News article) {
                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                httpIntent.setData(Uri.parse(article.getUrl()));
                startActivity(httpIntent);
            }
        });

        recyclerView.setAdapter(adapter);
        //newsViewModel.loadNews();
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_news, menu);
        super.onCreateOptionsMenu(menu,inflater);
        MenuItem menuItem =menu.findItem(R.id.search_mag_icon);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                newsViewModel.loadNews(newText);

                return true;
            }
        });

    }
}
