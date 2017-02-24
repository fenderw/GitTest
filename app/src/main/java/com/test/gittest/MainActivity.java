package com.test.gittest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private EditText userNameV;
    private Spinner repoTypeV;
    private Button btnGoToList;
    private ListView lastRequestListV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        userNameV = (EditText) findViewById(R.id.user_name);
        repoTypeV = (Spinner) findViewById(R.id.repo_type);
        btnGoToList = (Button) findViewById(R.id.button_goto_list);
        lastRequestListV = (ListView) findViewById(R.id.last_requests);
        //
        String[] queryType = getResources().getStringArray(R.array.repo_type_array_values);
        btnGoToList.setOnClickListener(e -> {
            Intent intent = new Intent(this, Main2Activity.class);
            intent.putExtra("user_name", userNameV.getText().toString());
            intent.putExtra("query_type", queryType[repoTypeV.getSelectedItemPosition()]);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
        });
        //
        ArrayAdapter<String> listLastRequestsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listLastRequestsAdapter.add("Test request 1");
        listLastRequestsAdapter.add("Test request 2");
        listLastRequestsAdapter.add("Test request 3");
        lastRequestListV.setAdapter(listLastRequestsAdapter);

    }

    private boolean startRequest() {
        //
        return true;
    }
}
