package com.test.gittest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.test.gittest.retrofit.App;
import com.test.gittest.retrofit.RepoAdapter;
import com.test.gittest.retrofit.RepoPostModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondActivity extends AppCompatActivity {

    private final String TAG = "SecondActivity";

    private Call<List<RepoPostModel>> retrofitCall;
    private Spinner sortSpinner;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("spiner_position", sortSpinner.getSelectedItemPosition());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        sortSpinner.setSelection(savedInstanceState.getInt("spiner_position"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //
        List<RepoPostModel> repos = new ArrayList<>();
        RepoAdapter reposAdapter = new RepoAdapter(repos);
        //
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_left);
        toolbar.setNavigationOnClickListener(e -> {
            finish();
        });
        //
        sortSpinner = (Spinner) toolbar.findViewById(R.id.tb_spinner);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rec_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(reposAdapter);
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortRepos(reposAdapter, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });
        //
        getRemoteData(reposAdapter);
    }

    private void getRemoteData(RepoAdapter adapter) {
        String userNameStr = getIntent().getStringExtra("user_name");
        String queryTypeStr = getIntent().getStringExtra("query_type");
        retrofitCall = App.getApi().getData(userNameStr, queryTypeStr);
        retrofitCall.enqueue(new Callback<List<RepoPostModel>>() {
            @Override
            public void onResponse(Call<List<RepoPostModel>> call, Response<List<RepoPostModel>> response) {
                if (response.body() != null) {
                    adapter.getCollection().addAll(response.body());
                    sortRepos(adapter, sortSpinner.getSelectedItemPosition());
                    adapter.notifyDataSetChanged();
                } else
                    Log.d(TAG, "null_response for " + userNameStr);
            }

            @Override
            public void onFailure(Call<List<RepoPostModel>> call, Throwable t) {
                Log.d(TAG, "Failed response for " + userNameStr);
            }
        });
    }

    // adapter.sortBy.. methods are null safe
    private void sortRepos(RepoAdapter adapter, int position) {
        if (adapter != null) {
            // sort collection
            switch (position) {
                // by full name
                case 0:
                    adapter.sortByFullNameAsc();
                    break;
                // by date
                case 1:
                    adapter.sortByDateAsc();
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (retrofitCall != null)
            if (retrofitCall.isExecuted()) {
                retrofitCall.cancel();
            }
    }
}
