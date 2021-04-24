package com.example.themovieapp2.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.themovieapp2.R;
import com.example.themovieapp2.movieTabs;
import com.example.themovieapp2.response.CreateRequestToken;
import com.example.themovieapp2.response.CreateSessionId;
import com.example.themovieapp2.response.ValidateRequestToken;
import com.example.themovieapp2.volley.GsonRequest;
import com.example.themovieapp2.volley.GsonRequestPost;

import org.json.JSONObject;

import java.util.HashMap;





public class LoginActivity extends AppCompatActivity {


    /* Variables */
    private RequestQueue mRequestQueue;
    private String mUrl;
    private String mRequestToken;
    EditText username;
    EditText password;
    TextView sign_up;
    Button btn_login;
    private String mUsername;
    private String mPassword;
    private int mStatusCode;
    private String mStatusMessage;
    private String mSessionId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setContentView(R.layout.activity_login);


        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);
        final Button btn_login = findViewById(R.id.login);


        username.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                username.setHint("");
            else
                username.setHint("Username");
        });

        password.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                password.setHint("");
            else
                password.setHint("Password");
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   btn_login.startAnimation();
                mUsername = username.getText().toString().trim();
                mPassword = password.getText().toString().trim();

                if (mUsername != null && mPassword != null) {
                    createRequestToken();
                    isLoginCorrect();
                    InputMethodManager inputManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }

            }
        });


    }


    private void isLoginCorrect(/*String username, String password*/) {
        // go to MainActivity and save user/pass or token.
        Intent login = new Intent(this, movieTabs.class);
        login.putExtra("session_id", mSessionId);
        startActivity(login);
    }


    private void createRequestToken() {

        mRequestQueue = Volley.newRequestQueue(this);

        try {
            mUrl = "https://api.themoviedb.org/3/authentication/token/new?api_key=b7e59f58c0bccf15f6d4dd07f31176ec";

            GsonRequest<CreateRequestToken> request = new GsonRequest<>(mUrl,
                    CreateRequestToken.class,
                    getTokenSuccessListener(),
                    getErrorListener());

            mRequestQueue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Response.Listener<CreateRequestToken> getTokenSuccessListener() {
        return new Response.Listener<CreateRequestToken>() {
            @Override
            public void onResponse(CreateRequestToken response) {
                try {
                    mRequestToken = response.getRequest_token();

                    validateRequestToken(mUsername, mPassword, mRequestToken);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }


    private void validateRequestToken(String username, String password, String token) {

        mRequestQueue = Volley.newRequestQueue(this);

        try {
            mUrl = "https://api.themoviedb.org/3/authentication/token/validate_with_login?api_key=b7e59f58c0bccf15f6d4dd07f31176ec";


            // Post params to be sent to the server
            HashMap<String, String> params = new HashMap<>();
            params.put("username", username);
            params.put("password", password);
            params.put("request_token", token);


            GsonRequestPost<ValidateRequestToken> request = new GsonRequestPost<ValidateRequestToken>(mUrl,
                    ValidateRequestToken.class,
                    params,
                    getValidateTokenSuccessListener(),
                    getErrorListener());

            mRequestQueue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Response.Listener<ValidateRequestToken> getValidateTokenSuccessListener() {
        return new Response.Listener<ValidateRequestToken>() {
            @Override
            public void onResponse(ValidateRequestToken response) {
                try {
                    if (response.isSuccess()) {
                        mRequestToken = response.getRequest_token(); // token validated

                        if (mUsername != null && mPassword != null && mRequestToken != null) {
                            createSessionId(mRequestToken);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }


    private void createSessionId(String token) {

        mRequestQueue = Volley.newRequestQueue(this);

        try {
            mUrl = "https://api.themoviedb.org/3/authentication/session/new?api_key=b7e59f58c0bccf15f6d4dd07f31176ec";

            // Post params to be sent to the server
            HashMap<String, String> params = new HashMap<>();
            params.put("request_token", token);


            GsonRequestPost<CreateSessionId> request = new GsonRequestPost<>(mUrl,
                    CreateSessionId.class,
                    params,
                    getSessionIdSuccessListener(),
                    getErrorListener());

            mRequestQueue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Response.Listener<CreateSessionId> getSessionIdSuccessListener() {
        return new Response.Listener<CreateSessionId>() {
            @Override
            public void onResponse(CreateSessionId response) {
                try {
                    if (response.isSuccess()) {
                        mSessionId = response.getSession_id(); // get session id

                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                isLoginCorrect();

                            }
                        }, 4500);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    //
//
//
    private Response.ErrorListener getErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Do whatever you want to do with error.getMessage();
                String message;
                String responseBody;
                JSONObject data;


//                btn_login.revertAnimation();
//                btn_login.setBackground(getResources().getDrawable(R.drawable.login_button)); //@drawable/login_button

                try {
                    NetworkResponse response = error.networkResponse;
                    if (response != null && response.data != null) {
                        switch (response.statusCode) {
                            case 400:
                                responseBody = new String(error.networkResponse.data, "utf-8");
                                data = new JSONObject(responseBody);

                                message = data.getString("status_message");

                                break;
                            case 401:
                                responseBody = new String(error.networkResponse.data, "utf-8");
                                data = new JSONObject(responseBody);

                                message = data.getString("status_message");

                                break;
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };
    }
}


//package com.example.themovieapp2.ui.login;
//
//import android.app.Activity;
//
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProvider;
//
//import android.content.Intent;
//import android.os.Bundle;
//
//import androidx.annotation.Nullable;
//import androidx.annotation.StringRes;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.inputmethod.EditorInfo;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.themovieapp2.R;
//import com.example.themovieapp2.movieTabs;
//
//public class LoginActivity extends AppCompatActivity {
//
//    private LoginViewModel loginViewModel;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
//                .get(LoginViewModel.class);
//
//        final EditText usernameEditText = findViewById(R.id.username);
//        final EditText passwordEditText = findViewById(R.id.password);
//        final Button loginButton = findViewById(R.id.login);
//        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
//
//        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
//            @Override
//            public void onChanged(@Nullable LoginFormState loginFormState) {
//                if (loginFormState == null) {
//                    return;
//                }
//                loginButton.setEnabled(loginFormState.isDataValid());
//                if (loginFormState.getUsernameError() != null) {
//                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
//                }
//                if (loginFormState.getPasswordError() != null) {
//                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
//                }
//            }
//        });
//
//        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
//            @Override
//            public void onChanged(@Nullable LoginResult loginResult) {
//                if (loginResult == null) {
//                    return;
//                }
//                loadingProgressBar.setVisibility(View.GONE);
//                if (loginResult.getError() != null) {
//                    showLoginFailed(loginResult.getError());
//                }
//                if (loginResult.getSuccess() != null) {
//                    updateUiWithUser(loginResult.getSuccess());
//                }
//                setResult(Activity.RESULT_OK);
//
//                //Complete and destroy login activity once successful
//                finish();
//            }
//        });
//
//        TextWatcher afterTextChangedListener = new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // ignore
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // ignore
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
//                        passwordEditText.getText().toString());
//            }
//        };
//        usernameEditText.addTextChangedListener(afterTextChangedListener);
//        passwordEditText.addTextChangedListener(afterTextChangedListener);
//        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    loginViewModel.login(usernameEditText.getText().toString(),
//                            passwordEditText.getText().toString());
//                }
//                return false;
//            }
//        });
//
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    String username = usernameEditText.getText().toString();
//                    String password = passwordEditText.getText().toString();
////                    String authToken = createAuthToken(username, password);
//
//                                loadingProgressBar.setVisibility(View.VISIBLE);
//                loginViewModel.login(usernameEditText.getText().toString(),
//                        passwordEditText.getText().toString());
//                if (usernameEditText.getText().toString().equals("athtech") && passwordEditText.getText().toString().equals("123456")) {
//                    Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(LoginActivity.this, movieTabs.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//
//    private void updateUiWithUser(LoggedInUserView model) {
//        String welcome = getString(R.string.welcome) + model.getDisplayName();
//        // TODO : initiate successful logged in experience
//        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
//    }
//
//    private void showLoginFailed(@StringRes Integer errorString) {
//        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
//    }
//}