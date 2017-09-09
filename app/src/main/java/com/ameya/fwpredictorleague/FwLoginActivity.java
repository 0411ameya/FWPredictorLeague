package com.ameya.fwpredictorleague;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * A login screen that offers login via email/password.
 */
public class FwLoginActivity extends AppCompatActivity {

    public Profile profile = Profile.getInstance();

    public final String P_id = "PROFILE ID";
    public final String P_username = "PROFILE USERNAME";
    public final String P_emailId = "PROFILE EMAIL_ID";
    public final String P_pwd = "PROFILE PWD";
    public final String P_team_id = "PROFILE TEAM_ID";
    public final String P_team_name = "PROFILE TEAM_NAME";

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    public CheckBox mCheckBox;

    public boolean saveLogin;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;

    public OkHttpClient okHttpClient;
    public Request request;
    public RequestBody requestBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fw_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);

        mCheckBox = (CheckBox) findViewById(R.id.login_remember_me);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            Log.v("Remember me","saveLogin is " + saveLogin);
            mEmailView.setText(loginPreferences.getString("username", ""));
            mPasswordView.setText(loginPreferences.getString("password", ""));
            mCheckBox.setChecked(true);
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            authenticateUser();
        }
    }

    private void authenticateUser() {
        String url = getResources().getString(R.string.userAuthUrl);
        okHttpClient = new OkHttpClient();

        requestBody = new FormBody.Builder()
                .add("username", mEmailView.getText().toString())
                .add("password", mPasswordView.getText().toString())
                .build();

        request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("OkHttp", e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mEmailView.requestFocus();
                        mEmailView.setError(getString(R.string.userAuthFailMsg));
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                Log.i("OkHttp", body);
                JSONParser jsonParser = new JSONParser();
                try {
                    JSONObject obj = (JSONObject) jsonParser.parse(body);
                    Long code = (Long) obj.get("code");
                    Log.i("OkHttp", code.toString());
                    if (code == 200) {
                        rememberUser();
                        JSONObject userdata = (JSONObject) obj.get("data");
                        String id = (String) userdata.get("id");
                        String username = (String) userdata.get("username");
                        String full_name = (String) userdata.get("name");
                        String firstName = full_name.split(" ")[0];
                        String lastName = full_name.split(" ")[1];
                        String emailId = (String) userdata.get("emailId");
                        String pwd = (String) userdata.get("pwd");
                        String team_id = (String) userdata.get("team_id");
                        String team_name = (String) userdata.get("team_name");
                        Long team_rank = (Long) userdata.get("team_rank");
                        String opp_team_id = (String) userdata.get("opp_team_id");
                        String opp_team_name = (String) userdata.get("opp_team_name");
                        Long opp_team_rank = (Long) userdata.get("opp_team_rank");
                        buildProfile(id, username, emailId, pwd, team_id, team_name, full_name, firstName, lastName, team_rank, opp_team_id, opp_team_name, opp_team_rank);
                        Log.i("OkHttp", profile.id + profile.username + profile.emailId + profile.pwd + profile.team_id + profile.team_name);
                        FwLoginActivity.this.finish();
                        loadHome(id, username, emailId, pwd, team_id, team_name);
                    } else if (code == 400) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showProgress(false);
                                mEmailView.requestFocus();
                                mEmailView.setError(getString(R.string.userAuthFailMsg));
                            }
                        });
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void rememberUser() {
        Log.v("Remember Me", "mChecked is " + mCheckBox.isChecked());
        if (mCheckBox.isChecked()){
            Log.v("Remember Me", "Came in checked" + mEmailView.getText().toString() + mPasswordView.getText().toString());
            loginPrefsEditor.putBoolean("saveLogin", true);
            loginPrefsEditor.putString("username", mEmailView.getText().toString());
            loginPrefsEditor.putString("password", mPasswordView.getText().toString());
            loginPrefsEditor.commit();
        } else {
            loginPrefsEditor.clear();
            loginPrefsEditor.commit();
        }
    }

    private void buildProfile(String id, String username, String emailId, String pwd, String team_id, String team_name, String full_name, String firstName, String lastName, Long team_rank, String opp_team_id, String opp_team_name, Long opp_team_rank) {
        profile.setId(id);
        profile.setUsername(username);
        profile.setEmailId(emailId);
        profile.setPwd(pwd);
        profile.setName(full_name);
        profile.setFirst_name(firstName);
        profile.setLast_name(lastName);
        profile.setTeam_id(team_id);
        profile.setTeam_name(team_name);
        profile.setTeam_rank(team_rank);
        profile.setOpp_team_id(opp_team_id);
        profile.setOpp_team_name(opp_team_name);
        profile.setOpp_team_rank(opp_team_rank);
    }


    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    private void loadHome(String id, String username, String emailId, String pwd, String team_id, String team_name) {
        Intent i = new Intent(FwLoginActivity.this, HomeActivity.class);
        i.putExtra(P_id, id);
        i.putExtra(P_username, username);
        i.putExtra(P_emailId, emailId);
        i.putExtra(P_pwd, pwd);
        i.putExtra(P_team_id, team_id);
        i.putExtra(P_team_name, team_name);
        startActivity(i);
    }
}

