package com.fabriciooliveira.ubookteste;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.fabriciooliveira.ubookteste.adapter.CustomListAdapter;
import com.fabriciooliveira.ubookteste.commom.Constants;
import com.fabriciooliveira.ubookteste.model.Shot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DribbleListActivity extends ActionBarActivity {

    private static final String TAG = DribbleListActivity.class.getSimpleName();

    private ListView listViewShots;
    private CustomListAdapter adapter;
    private List<Shot> shotsList = new ArrayList<Shot>();

    private ProgressDialog progressDialog;

    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dribble_list);

        listViewShots = (ListView)findViewById(R.id.list_shots);
        adapter = new CustomListAdapter(this, shotsList);

        listViewShots.setAdapter(adapter);

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Loading...");
        progressDialog.show();


        JsonArrayRequest requestShots = new JsonArrayRequest(Constants.URL_SHOTS, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                hideDialog();

                for(int i = 0; i<jsonArray.length(); i++){
                    try{
                        JSONObject currentShot = jsonArray.getJSONObject(i);

                        Shot shot = new Shot();

                        shot.setId(currentShot.getInt("id"));

                        if(currentShot.has("images") && !currentShot.isNull("images")){
                            JSONObject images = currentShot.getJSONObject("images");
                            if(images.has("teaser") && images != null){
                                shot.setUrlImage(images.getString("teaser"));
                            }
                        }

                        shot.setTitle(currentShot.getString("title"));
                        shot.setViewsCount(currentShot.getInt("views_count"));

                        Date date = null;
                        String createdDate = currentShot.getString("created_at");

                        try{
                            date = mDateFormat.parse(createdDate);
                        }catch(ParseException e){
                            e.printStackTrace();
                        }

                        shot.setCreatedAt(mDateFormat.format(date));

                        shotsList.add(shot);

                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                }

                adapter.notifyDataSetChanged();

            }
        },

                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Erro: " + error.getMessage());
                        hideDialog();
                    }

                });

        AppController.getInstance().addToRequestQueue(requestShots);

    }


    private void hideDialog(){
        if(progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        hideDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}


