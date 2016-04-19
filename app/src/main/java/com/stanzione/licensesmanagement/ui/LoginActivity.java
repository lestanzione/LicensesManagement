package com.stanzione.licensesmanagement.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.stanzione.licensesmanagement.Operations;
import com.stanzione.licensesmanagement.R;
import com.stanzione.licensesmanagement.model.UserAccess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.support.v7.widget.*;

public class LoginActivity extends AppCompatActivity implements Operations.OperationsCallback{

    private static final int CODE_LOGIN = 1;

    private static final String TAG = LoginActivity.class.getSimpleName();

	private Toolbar toolbar;
    private Button btnLogin;
    private TextInputLayout loginTextInput;
    private TextInputLayout passwordTextInput;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private ProgressBar progressBar;

    public AsyncTask<String, Void, Integer> doLoginTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

		toolbar = (Toolbar) findViewById(R.id.toolbar);
        loginTextInput = (TextInputLayout) findViewById(R.id.usernameTextInput);
        passwordTextInput = (TextInputLayout) findViewById(R.id.passwordTextInput);
        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);

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

        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        boolean hasError = false;

        if(username.trim().isEmpty()){
            loginTextInput.setError("Fill in the username");
            hasError = true;
        }
        else{
            loginTextInput.setErrorEnabled(false);
        }

        if(password.trim().isEmpty()){
            passwordTextInput.setError("Fill in the password");
            hasError = true;
        }
        else{
            passwordTextInput.setErrorEnabled(false);
        }

        if(hasError)
            return;

        hideSoftKeyboard();

        progressBar.setVisibility(View.VISIBLE);

        Operations ops = new Operations(this, CODE_LOGIN);
        ops.doLogin(username, password);

    }

    private void hideSoftKeyboard(){
        if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void onOperationSuccess(Object returnObject, int operationCode) {

        UserAccess loggedUser = (UserAccess) returnObject;

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("loggedUser", loggedUser);

        startActivity(intent);

        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onOperationFail(Object returnObject, int operationCode) {

        Toast.makeText(this, "Could not log into the system. Wrong username or password!", Toast.LENGTH_LONG).show();

        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onOperationError(Object returnObject, int operationCode) {

        progressBar.setVisibility(View.INVISIBLE);

    }
}
