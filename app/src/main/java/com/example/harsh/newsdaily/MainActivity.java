package com.example.harsh.newsdaily;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://newsapi.org/v2/top-headlines";
    private static final String PARAM_COUNTRY = "country";
    private static final String PARAM_API = "apiKey";
    private static final String API_KEY = "356407a4f5e84fe0acca6cbe428900b4";
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();

        navigationView = findViewById(R.id.navigationView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_business:
                        //Do whatever you want
                        Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_sports:
                        //Do whatever you want
                        break;
                    case R.id.nav_entertainment:
                        //Do whatever you want
                        break;
                    case R.id.nav_health:
                        //Do whatever you want
                        break;
                    case R.id.nav_technology:
                        //Do whatever you want
                        break;
                    case R.id.nav_science:
                        //Do whatever you want
                        break;
                    case R.id.nav_settings:
                        //Do whatever you want
                        break;
                    case R.id.nav_about_us:
                        //Do whatever you want
                        break;
                }

                return false;
            }
        });
        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Building Uri
        Uri buildUri = Uri.parse(BASE_URL).
                buildUpon().
                appendQueryParameter(PARAM_COUNTRY, "in").
                appendQueryParameter(PARAM_API, API_KEY).build();

        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        StringRequest request = new StringRequest(Request.Method.GET, url.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Headlines headlines = gson.fromJson(response, Headlines.class);
                List<Article> articles = headlines.getArticles();
                recyclerView.setAdapter(new NewsAdapter(MainActivity.this, articles));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error receiving data", Toast.LENGTH_SHORT).show();
            }
        });

        //Creating request queue and adding our request
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void setUpToolbar() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        //Calling this method to make our toolbar backward compatible
        setSupportActionBar(toolbar);

        /*
        Creating object of actionBarDrawerToggle(i.e helper class) and attaching
        it to drawer layout which will automatically handles events like open
        and closing of drawer and then to make the toolbar and drawerLayout
        to work in synchronization with each other we call syncState method
        */
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }
}
