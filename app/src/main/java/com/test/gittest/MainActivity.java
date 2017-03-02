package com.test.gittest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private String[] queryType;

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
        queryType = getResources().getStringArray(R.array.repo_type_array_values);
        // parameter dbIdExtra is 0 so next activity should make a call to retrofit
        btnGoToList.setOnClickListener(e -> startSecondActivity(userNameV.getText().toString(), 0));
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<User> users = User.getUserNames();
        ArrayList<String> listOfUsers = new ArrayList<>();
        for (User user : users) {
            listOfUsers.add(user.getUsername() + " | " + user.getRepo_type());
        }
        ArrayAdapter<String> lastRequestsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listOfUsers);
        lastRequestListV.setAdapter(lastRequestsAdapter);
        lastRequestListV.setOnItemClickListener((a,v,p,i) -> startSecondActivity(users.get(p).getUsername(), users.get(p).getId()));
    }

    private void startSecondActivity(String userName, long dbIdExtra) {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("user_name", userName);
        intent.putExtra("query_type", queryType[repoTypeV.getSelectedItemPosition()]);
        intent.putExtra("user_id", dbIdExtra);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
