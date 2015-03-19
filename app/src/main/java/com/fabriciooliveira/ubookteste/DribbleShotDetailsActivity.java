package com.fabriciooliveira.ubookteste;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.fabriciooliveira.ubookteste.commom.Constants;
import com.fabriciooliveira.ubookteste.model.Shot;

import org.json.JSONException;
import org.json.JSONObject;


public class DribbleShotDetailsActivity extends ActionBarActivity {

    private static final String TAG = DribbleShotDetailsActivity.class.getSimpleName();

    private int mIdUser;
    private ProgressDialog mProgressDialog;

    private TextView mTitle, mDescription, mViewCounts, mCommentCounts;

    NetworkImageView mImageShot;

    private ImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dribble_shot_details);

        Intent intent = getIntent();

        mIdUser = intent.getIntExtra("id_user", 0);

        setupViewElements();

        mImageLoader = AppController.getInstance().getImageLoader();

        mProgressDialog = new ProgressDialog(this);

        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();

        sendRequest();

    }

    private void sendRequest() {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, Constants.URL_FOR_SHOT + mIdUser, "", new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                hideDialog();

                try {
                    Shot shot = new Shot();

                    shot.setTitle(response.getString("title"));
                    shot.setDescription(response.getString("description"));

                    String plainText = Html.fromHtml(shot.getDescription()).toString();
                    shot.setDescription(plainText);

                    shot.setViewsCount(response.getInt("views_count"));
                    shot.setCommentsCount(response.getInt("comments_count"));

                    shot.setUrlImage(response.getString("image_url"));

                    populateValuesOnView(shot);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                hideDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

                hideDialog();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void hideDialog(){
        if(mProgressDialog != null){
            mProgressDialog.dismiss();
            mProgressDialog = null;
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

    private void setupViewElements(){
        mTitle = (TextView) findViewById(R.id.textTitle);
        mDescription = (TextView) findViewById(R.id.textDescription);
        mViewCounts = (TextView) findViewById(R.id.textViewCounts);
        mCommentCounts = (TextView) findViewById(R.id.textComments);

        mImageShot = (NetworkImageView) findViewById(R.id.imageShot);

    }

    private void populateValuesOnView(Shot shot){
        mTitle.setText(shot.getTitle());
        mDescription.setText("Description: " + shot.getDescription());
        mViewCounts.setText("View Count: " + String.valueOf(shot.getViewsCount()));
        mCommentCounts.setText("Comment Count: " + String.valueOf(shot.getCommentsCount()));

        mImageShot.setImageUrl(shot.getUrlImage(), mImageLoader);

    }

}

