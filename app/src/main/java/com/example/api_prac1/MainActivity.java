package com.example.api_prac1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends YouTubeBaseActivity {
    String s="",url,x,URL;
    EditText sr;
    Button search;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }



        RequestQueue r;
        r= Volley.newRequestQueue(this);
        sr=findViewById(R.id.sr);
        search=findViewById(R.id.search);
        rv=findViewById(R.id.rv);

        url="https://www.googleapis.com/youtube/v3/search?part=snippet&q=";
        x="&key=AIzaSyAzufsH1zuez70YGf99MtHMMfRhuKRwVto&maxResults=20";
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               URL=url;
               URL+=sr.getText().toString();
               URL=URL+x;
               // Toast.makeText(MainActivity.this, URL, Toast.LENGTH_SHORT).show();
                Log.d("myapp", "onClick: "+URL);
        StringRequest job=new StringRequest(Request.Method.GET,URL,new Response.Listener<String>(){
            @Override
            public void onResponse(String response)
            {
                try {
                    JSONObject jo = new JSONObject(response);
                    JSONArray ja=jo.getJSONArray("items");
                    CustomAdapter c=new CustomAdapter(ja);
                    rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    rv.setAdapter(c);


                    Log.d("myapp", "onResponse:SUCCESS");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            },new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("myapp", "onErrorResponse:"+error);
            }
        }
        );
                int socketTimeout = 10000;
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                job.setRetryPolicy(policy);
        r.add(job);

            }
        });





    }

}