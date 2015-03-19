package com.fabriciooliveira.ubookteste.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.fabriciooliveira.ubookteste.AppController;
import com.fabriciooliveira.ubookteste.DribbleShotDetailsActivity;
import com.fabriciooliveira.ubookteste.R;
import com.fabriciooliveira.ubookteste.model.Shot;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fabriciooliveira on 3/18/15.
 */
public class CustomListAdapter extends BaseAdapter {

    private Activity mActivity;

    private List<Shot> mShots = new ArrayList<Shot>();

    private LayoutInflater inflater;

    private ImageLoader imageLoader;


    public CustomListAdapter(Activity activity, List<Shot> shots) {
        super();
        this.mActivity = activity;
        this.mShots = shots;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = AppController.getInstance().getImageLoader();
    }

    @Override
    public int getCount() {
        return mShots.size();
    }

    @Override
    public Object getItem(int position) {
        return mShots.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(inflater== null){
            inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_lines, null);

        }

        if(imageLoader == null){
            imageLoader = AppController.getInstance().getImageLoader();
        }

        NetworkImageView imageShot = (NetworkImageView) convertView.findViewById(R.id.imageShot);

        TextView txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
        TextView txtViewCount = (TextView) convertView.findViewById(R.id.txt_view_count);
        TextView txtCreatedAt = (TextView) convertView.findViewById(R.id.txt_created_at);

        //Retrieving shot elements
        final Shot shot = mShots.get(position);

        imageShot.setImageUrl(shot.getUrlImage(), imageLoader);

        txtTitle.setText("Title: " + shot.getTitle());
        txtViewCount.setText("View Counts: " + String.valueOf(shot.getViewsCount()));

        txtCreatedAt.setText("Created at: " + shot.getCreatedAt());

        convertView.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                Intent intent = new Intent(mActivity, DribbleShotDetailsActivity.class);
                intent.putExtra("id_user", shot.getId());
                v.getContext().startActivity(intent);
            }
        });

        return convertView;
    }

}
