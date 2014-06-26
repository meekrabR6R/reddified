package org.redplatoon.reddified.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;

import org.redplatoon.reddified.app.services.RedditService;


public class SigninActivity extends Activity {

    public static final String USER_CREDS = "ReddifiedUser";
    private SharedPreferences mSettings;
    private String mUserAgent;
    private RedditService mReddit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mSettings = getSharedPreferences(USER_CREDS, Context.MODE_PRIVATE);
        mUserAgent = getString(R.string.user_agent);
        mReddit = new RedditService(mUserAgent, null, null);

        final Button mSignin = (Button) findViewById(R.id.sign_in);
        final EditText mUser = (EditText)findViewById(R.id.username);
        final EditText mPasswrd = (EditText)findViewById(R.id.password);

        mSignin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String user = mUser.getText().toString();
                String passwd = mPasswrd.getText().toString();
                attemptLogin(user, passwd);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.signin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void attemptLogin(String user, String passwd) {
        if (user.length() <= 0) {
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.username_req), Toast.LENGTH_LONG);
            toast.show();
        } else if(passwd.length() <= 0) {
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.password_req), Toast.LENGTH_LONG);
            toast.show();
        } else {
            mReddit.signIn(getApplicationContext(), user, passwd,   new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            JsonObject json = result.get("json").getAsJsonObject();
                            JsonArray errors = json.get("errors").getAsJsonArray();

                            if(errors.size() <= 0) {
                                SharedPreferences.Editor editor = mSettings.edit();
                                JsonObject data = json.get("data").getAsJsonObject();

                                String cookie = data.get("cookie").getAsString();
                                String modHash = data.getAsJsonObject().get("modhash").getAsString();

                                editor.putString("cookie", cookie);
                                editor.putString("modHash", modHash);
                                editor.commit();

                                Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.greeting), Toast.LENGTH_LONG);
                                toast.show();

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            } else {
                                String error = errors.get(0).getAsJsonArray().get(1).toString();
                                Toast toast = Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                    });
        }
    }
}
