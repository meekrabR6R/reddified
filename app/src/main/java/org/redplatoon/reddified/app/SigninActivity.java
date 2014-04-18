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
import com.koushikdutta.ion.Ion;


public class SigninActivity extends Activity {

    public static final String USER_CREDS = "ReddifiedUser";
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        settings = getSharedPreferences(USER_CREDS, Context.MODE_PRIVATE);
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
            Toast toast = Toast.makeText(getApplicationContext(), "Username required.", Toast.LENGTH_LONG);
            toast.show();
        } else if(passwd.length() <= 0) {
            Toast toast = Toast.makeText(getApplicationContext(), "Password cannot be blank.", Toast.LENGTH_LONG);
            toast.show();
        } else {
            Ion.with(getApplicationContext(), "http://www.reddit.com/api/login")
                    .setBodyParameter("user", user)
                    .setBodyParameter("passwd", passwd)
                    .setBodyParameter("api_type", "json")
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            JsonObject json = result.get("json").getAsJsonObject();
                            JsonArray errors = json.get("errors").getAsJsonArray();

                            if(errors.size() <= 0) {
                                SharedPreferences.Editor editor = settings.edit();
                                JsonObject data = json.get("data").getAsJsonObject();

                                String cookie = data.get("cookie").toString();
                                String modHash = data.getAsJsonObject().get("modhash").toString();

                                editor.putString("cookie", cookie);
                                editor.putString("modHash", modHash);
                                editor.commit();

                                Toast toast = Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG);
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
