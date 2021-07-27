package com.example.api_prac1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private JSONArray data;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView p,c,t;
        private ImageView im;
        String id;


        public ViewHolder(View view) {
            super(view);
            p=view.findViewById(R.id.p);
            c=view.findViewById(R.id.c);
            t=view.findViewById(R.id.t);
            im=view.findViewById(R.id.im);

            // Define click listener for the ViewHolder's View
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent x=new Intent(v.getContext(),MainActivity2.class);
                    x.putExtra("vid",id);
                    x.putExtra("tit",t.getText().toString());
                    v.getContext().startActivity(x);


                }
            });
        }

        public TextView getTextViewp() {
            return p;
        }
        public TextView getTextViewc() {
            return c;
        }
        public TextView getTextViewt() {
            return t;
        }
        public ImageView getImageViewim() {
            return im;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param j String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public CustomAdapter(JSONArray j) {
        data = j;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.yt, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        try {
            viewHolder.id=data.getJSONObject(position).getJSONObject("id").getString("videoId").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            viewHolder.getTextViewp().setText("   Date: "+data.getJSONObject(position).getJSONObject("snippet").getString("publishTime").toString().substring(0,10));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            viewHolder.getTextViewc().setText("   Channel: "+data.getJSONObject(position).getJSONObject("snippet").getString("channelTitle").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            viewHolder.getTextViewt().setText(data.getJSONObject(position).getJSONObject("snippet").getString("title").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url= null ;
        try {
            url = data.getJSONObject(position).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("high").getString("url").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        viewHolder.getImageViewim().setImageBitmap(get(url));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return data.length();
    }
    public static Bitmap get(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }
}
