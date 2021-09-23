package com.example.androidebook.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.androidebook.InterFace.InterstitialAdView;
import com.example.androidebook.Item.CategoryList;
import com.example.androidebook.Item.SchoolList;
import com.example.androidebook.R;
import com.example.androidebook.Util.Method;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class SchoolHomeAdapter extends RecyclerView.Adapter<SchoolHomeAdapter.ViewHolder> {

    private Method method;
    private Activity activity;
    private String type;
    private List<CategoryList> categoryLists;
    private List<SchoolList> schoolLists;
    private int lastPosition = -1;

    public SchoolHomeAdapter(Activity activity, List<SchoolList> schoolLists, String type, InterstitialAdView interstitialAdView) {
        this.activity = activity;
        this.type = type;
        this.schoolLists = schoolLists;
        method = new Method(activity, interstitialAdView);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.category_home_adapter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.textView_Name.setText(schoolLists.get(position).getSchool_name());
        holder.textView_Name.setTypeface(Method.scriptable);
        String totalBook = "(" + schoolLists.get(position).getTotal_course() + ") " + "Courses";
        holder.textView_categoryCount.setText(totalBook);
        Picasso.get().load(schoolLists.get(position).getsch_image())
                .placeholder(R.drawable.placeholder_portable)
                .into(holder.imageView_category_adapter);

        if (position > lastPosition) {
            if (position % 2 == 0) {
                Animation animation = AnimationUtils.loadAnimation(activity,
                        R.anim.up_from_bottom_right);
                holder.itemView.startAnimation(animation);
                lastPosition = position;
            } else {
                Animation animation = AnimationUtils.loadAnimation(activity,
                        R.anim.up_from_bottom_left);
                holder.itemView.startAnimation(animation);
                lastPosition = position;
            }
        }

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                method.interstitialAdShow(position, type, schoolLists.get(position).getCid(), schoolLists.get(position).getSchool_name(), "", "");
            }
        });

    }

    @Override
    public int getItemCount() {
        return schoolLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout relativeLayout;
        private TextView textView_Name, textView_categoryCount;
        RoundedImageView imageView_category_adapter;

        public ViewHolder(View itemView) {
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.relativelayout_category_adapter);
            textView_Name = itemView.findViewById(R.id.textViewName_category_adapter);
            textView_categoryCount = itemView.findViewById(R.id.textView_categoryCount_category_adapter);
            imageView_category_adapter = itemView.findViewById(R.id.imageView_category_adapter);
        }
    }
}
