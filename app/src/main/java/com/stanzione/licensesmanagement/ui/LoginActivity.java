package com.stanzione.licensesmanagement.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.stanzione.licensesmanagement.Operations;
import com.stanzione.licensesmanagement.R;
import com.stanzione.licensesmanagement.model.UserAccess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import android.support.v7.widget.*;

public class LoginActivity extends AppCompatActivity implements Operations.OperationsCallback{

    private static final int CODE_LOGIN = 1;

    private static final String TAG = LoginActivity.class.getSimpleName();

	private Toolbar toolbar;
    private Button btnLogin;
    private EditText edLogin;
    private EditText edPass;

    public AsyncTask<String, Void, Integer> doLoginTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

		toolbar = (Toolbar) findViewById(R.id.toolbar);
        edLogin = (EditText) findViewById(R.id.edLogin);
        edPass = (EditText) findViewById(R.id.edPassword);

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle("Licenses Management");
		
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });

    }

    private void doLogin(){

        String login = edLogin.getText().toString();
        String pass = edPass.getText().toString();

        if(login.trim().isEmpty() || pass.trim().isEmpty()){
            Toast.makeText(this, "Please, fill in the fields above", Toast.LENGTH_LONG).show();
            return;
        }

        Operations ops = new Operations(this, CODE_LOGIN);
        ops.doLogin(login, pass);

    }

    // Reads an InputStream and converts it to a String.
    public String convertToString(InputStream stream) throws IOException {

        InputStreamReader is = new InputStreamReader(stream);
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(is);
        String read = br.readLine();

        while(read != null) {
            //System.out.println(read);
            sb.append(read);
            read = br.readLine();

        }

        return sb.toString();

    }

    @Override
    public void onOperationSuccess(Object returnObject, int operationCode) {

        UserAccess loggedUser = (UserAccess) returnObject;

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("loggedUser", loggedUser);

        startActivity(intent);

    }

    @Override
    public void onOperationFail(Object returnObject, int operationCode) {

        Toast.makeText(this, "Could not log into the system. Wrong username or password!", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onOperationError(Object returnObject, int operationCode) {

    }
}
