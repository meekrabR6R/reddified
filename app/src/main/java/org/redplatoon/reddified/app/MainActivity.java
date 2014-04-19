package org.redplatoon.reddified.app;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.redplatoon.reddified.app.models.Post;

import java.util.ArrayList;
import java.util.Map;


public class MainActivity extends ListActivity {

    public static final String USER_CREDS = "ReddifiedUser";
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = getSharedPreferences(USER_CREDS, Context.MODE_PRIVATE);
        makePosts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    private void makePosts() {
        final ArrayList<Post> posts = new ArrayList<Post>();

        Ion.with(getApplicationContext(), "http://www.reddit.com/.json")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        JsonArray children = result
                                .get("data")
                                .getAsJsonObject()
                                .get("children")
                                .getAsJsonArray();

                        for(JsonElement child: children) {
                            Post post = new Post();
                            post.title = child.getAsJsonObject().get("data").getAsJsonObject().get("title").toString();
                            post.author = child.getAsJsonObject().get("data").getAsJsonObject().get("author").toString();
                            post.url = child.getAsJsonObject().get("data").getAsJsonObject().get("url").toString().trim();

                            post.thumbnail = child.getAsJsonObject()
                                                  .get("data")
                                                  .getAsJsonObject()
                                                  .get("thumbnail")
                                                  .toString()
                                                  .replaceAll("^\\p{Graph}", "")
                                                  .replaceAll("\"", "");

                            post.id = child.getAsJsonObject().get("data").getAsJsonObject().get("id").toString();
                            post.subreddit = child.getAsJsonObject().get("data").getAsJsonObject().get("subreddit").toString();
                            post.permaLink = child.getAsJsonObject().get("data").getAsJsonObject().get("permalink").toString();

                            post.ups = child.getAsJsonObject().get("data").getAsJsonObject().get("ups").getAsInt();
                            post.downs = child.getAsJsonObject().get("data").getAsJsonObject().get("downs").getAsInt();
                            post.score = child.getAsJsonObject().get("data").getAsJsonObject().get("score").getAsInt();
                            post.created = child.getAsJsonObject().get("data").getAsJsonObject().get("created").getAsInt();
                            post.createdUtc = child.getAsJsonObject().get("data").getAsJsonObject().get("created_utc").getAsInt();
                            post.numComments = child.getAsJsonObject().get("data").getAsJsonObject().get("num_comments").getAsInt();

                            post.visited = child.getAsJsonObject().get("data").getAsJsonObject().get("visited").getAsBoolean();
                            post.nsfw = child.getAsJsonObject().get("data").getAsJsonObject().get("over_18").getAsBoolean();

                            posts.add(post);
                        }
                        setListAdapter(new PostsAdapter(getApplicationContext(), posts));
                    }
                });
    }

    private void signIn() {
        /*
        final Button mSignout = (Button) findViewById(R.id.sign_out);
        final Map<String, ?> prefs = settings.getAll();

        if(prefs.containsKey("cookie") && prefs.containsKey("modHash")) {
            mSignout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    SharedPreferences.Editor editor = settings.edit();
                    editor.remove("cookie");
                    editor.remove("modHash");
                    editor.commit();
                    Toast toast = Toast.makeText(getApplicationContext(), "See ya", Toast.LENGTH_LONG);
                    toast.show();
                    Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            Intent intent = new Intent(this, SigninActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
        */
    }
}
