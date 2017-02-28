package com.test.gittest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.test.gittest.models.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private EditText userNameV;
    private Spinner repoTypeV;
    private ListView lastRequestListV;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("user_name", userNameV.getText().toString());
        outState.putInt("repo_type", repoTypeV.getSelectedItemPosition());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        userNameV.setText(savedInstanceState.getString("user_name"));
        repoTypeV.setSelection(savedInstanceState.getInt("repo_type"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        userNameV = (EditText) findViewById(R.id.user_name);
        repoTypeV = (Spinner) findViewById(R.id.repo_type);
        Button btnGoToList = (Button) findViewById(R.id.button_goto_list);
        lastRequestListV = (ListView) findViewById(R.id.last_requests);
        //
        String[] queryType = getResources().getStringArray(R.array.repo_type_array_values);
        //
        btnGoToList.setOnClickListener(e -> {
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("user_name", userNameV.getText().toString());
            intent.putExtra("query_type", queryType[repoTypeV.getSelectedItemPosition()]);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<User> users = User.getUserNames();
        ArrayList<String> listOfUsers = new ArrayList<>();
        for (User user : users) {
            String str = user.getUsername() + " | " + user.getRepo_type();
            listOfUsers.add(str);
            Log.d(TAG, str);
        }
        ArrayAdapter<String> lastRequestsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listOfUsers);
        lastRequestListV.setAdapter(lastRequestsAdapter);
    }
}
