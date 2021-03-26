package com.example.android.miwok;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    private int color;

    public WordAdapter (Context context, ArrayList<Word> words, int color) {
        super(context, 0, words);
        this.color = color;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position

        Word word = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view

        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.textviews_adapt, parent, false);

        }

        // Lookup view for data population

        TextView miwokTrans = convertView.findViewById(R.id.text_view1);

        TextView defaultTrans = convertView.findViewById(R.id.text_view2);

        ImageView imageView = convertView.findViewById(R.id.image_view);

        LinearLayout linearLayout = convertView.findViewById(R.id.text_views_group);

        // Populate the data into the template view using the data object

        assert word != null;
        miwokTrans.setText(word.getMiwokTrans());

        defaultTrans.setText(word.getDefaultTrans());

        linearLayout.setBackgroundColor(ContextCompat.getColor(this.getContext(), this.color));

        if (word.isImagePresent()) {
            imageView.setImageResource(word.getmImageRes());
            imageView.setVisibility(View.VISIBLE);
        }
        else {
            imageView.setVisibility(View.GONE);
        }

        // Return the completed view to render on screen

        return convertView;
    }

}
