package com.example.simplejavaapi;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.simplejavaapi.adapters.MyAdapter;
import com.example.simplejavaapi.models.UnsplashImage;
import com.example.simplejavaapi.utils.APIClient;
import com.example.simplejavaapi.utils.APIInterface;
import com.example.simplejavaapi.utils.OnBottomReachListener;
import com.example.simplejavaapi.viewModels.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    private MainViewModel viewModel;
    private RecyclerView rv;
    private MyAdapter adapter;
    private LinearLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.initViewModel();
        initView();

        observeLiveData();
    }



    private void observeLiveData(){
        viewModel.getLiveImages().observe(this, images -> {
            progressBar.setVisibility(View.GONE);

            if(adapter == null){
                initAdapter();
            } else {
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initAdapter(){
        adapter = new MyAdapter(getApplicationContext(), viewModel.getLiveImages().getValue());
        adapter.setOnBottomReachListener(position -> {
            Toast.makeText(getApplicationContext(), "End position", Toast.LENGTH_SHORT).show();
            viewModel.getBasicPagination();
        });

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(staggeredGridLayoutManager);
        rv.setAdapter(adapter);
    }


    private void initView(){
        progressBar = findViewById(R.id.unsplash_picker_progress_bar_layout);
        rv = findViewById(R.id.recycler_view);
        progressBar.setVisibility(View.VISIBLE);

        EditText et_search = findViewById(R.id.et_search);
        Button btn_search = findViewById(R.id.btn_search);

        btn_search.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            viewModel.getSearchImages(et_search.getText().toString());
        });

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

}
