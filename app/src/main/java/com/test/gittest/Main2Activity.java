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
import android.widget.Toast;

import com.test.gittest.retrofit.App;
import com.test.gittest.retrofit.RepoAdapter;
import com.test.gittest.retrofit.RepoPostModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main2Activity extends AppCompatActivity {

    private final String TAG = "Main2Activity";

    private Spinner sortSpinner;
    private RecyclerView recyclerView;
    private List<RepoPostModel> repos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_left);
        toolbar.setNavigationOnClickListener(e -> {
            finish();
        });
        sortSpinner = (Spinner) toolbar.findViewById(R.id.tb_spinner);
        recyclerView = (RecyclerView) findViewById(R.id.rec_view);
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO
                if (position == 1) {
                    ((RepoAdapter) recyclerView.getAdapter()).sortByDate();
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
                Toast.makeText(getBaseContext(), parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Toast.makeText(getBaseContext(), "Nothing selected", Toast.LENGTH_SHORT).show();
            }
        });
        //
        getRemoteData();
    }

    private void getRemoteData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        repos = new ArrayList<>();
        RepoAdapter postsAdapter = new RepoAdapter(repos);
        recyclerView.setAdapter(postsAdapter);
        //
        String userNameStr = getIntent().getStringExtra("user_name");
        String queryTypeStr = getIntent().getStringExtra("query_type");
        App.getApi().getData(userNameStr, queryTypeStr).enqueue(new Callback<List<RepoPostModel>>() {
            @Override
            public void onResponse(Call<List<RepoPostModel>> call, Response<List<RepoPostModel>> response) {
                if (response.body() != null) {
                    repos.addAll(response.body());
                    if (sortSpinner.getSelectedItemPosition() == 1)
                        postsAdapter.sortByDate();
                    postsAdapter.notifyDataSetChanged();
                } else
                    Log.d(TAG, "null_response for " + userNameStr);
            }

            @Override
            public void onFailure(Call<List<RepoPostModel>> call, Throwable t) {
                Log.d(TAG, "Failed response for " + userNameStr);
            }
        });
    }

}
