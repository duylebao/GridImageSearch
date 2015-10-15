package com.training.android.dle.gridimagesearch.adapter;

import android.content.Context;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.training.android.dle.gridimagesearch.R;
import com.training.android.dle.gridimagesearch.model.ImageResult;

import java.util.List;

public class ImageResultAdapter extends ArrayAdapter<ImageResult>{

    public ImageResultAdapter(Context context, List<ImageResult> objects) {
        super(context, R.layout.image_result, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageResult result = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.image_result, parent, false);
            viewHolder.title = (TextView)convertView.findViewById(R.id.tvTitle);
            viewHolder.photo = (ImageView)convertView.findViewById(R.id.ivResult);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.title.setText(Html.fromHtml(result.title));
        // clear out photo view
        viewHolder.photo.setImageResource(0);
        Picasso.with(getContext()).load(result.url).resize(result.tbWidth, result.tbHeight).into(viewHolder.photo);
        return convertView;
    }

    private static class ViewHolder {
        TextView title;
        ImageView photo;
    }
}
