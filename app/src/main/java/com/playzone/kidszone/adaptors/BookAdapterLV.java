package com.playzone.kidszone.adaptors;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.playzone.kidszone.R;
import com.playzone.kidszone.fragmentpackage.new_home_screen;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import database.DatabaseHandler;
import database.InterstitialAdView;
import database.Method;
import database.OnClick;

public class BookAdapterLV extends RecyclerView.Adapter<BookAdapterLV.ViewHolder> {

    private Method method;
    private Method method2;
    private Activity activity;
    private List<com.playzone.kidszone.models.SubCategoryList> subCategoryLists;
    private int lastPosition = -1;
    private String type;
    private DatabaseHandler db;

    public BookAdapterLV(Activity activity, OnClick onClick, List<com.playzone.kidszone.models.SubCategoryList> subCategoryLists, String type, InterstitialAdView interstitialAdView) {
        this.activity = activity;
        this.subCategoryLists = subCategoryLists;
        this.type = type;
        db=new DatabaseHandler(activity);
        method = new Method(activity, interstitialAdView);
        method2 = new database.Method(activity, onClick);
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.latest_list_adapter, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, final int position) {

        holder.textView_Name.setText(subCategoryLists.get(position).getBook_title());
        holder.textView_Name.setTypeface(Method.scriptable);
        holder.textView_author.setText(activity.getString(R.string.author_by) + "\u0020" + subCategoryLists.get(position).getAuthor_name());
        holder.textView_ratingCount.setText(subCategoryLists.get(position).getTotal_rate());
        holder.textView_viewCount.setText(Method.Format(Integer.parseInt(subCategoryLists.get(position).getBook_views())));
        holder.textView_description.setText(Html.fromHtml(subCategoryLists.get(position).getBook_description()));
       // holder.ratingBar.setRating(Float.parseFloat(subCategoryLists.get(position).getRate_avg()));

        Picasso.get().load(subCategoryLists.get(position).getBook_cover_img())
                .placeholder(R.drawable.book_big)
                .into(holder.imageView);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int age = Integer.parseInt(new_home_screen.content_restriction);
                if(age>=11){
                    method2.onClickAd(position, type, subCategoryLists.get(position).getId(), "", "", "", "", "");

                }
                else {
                    method.interstitialAdShow(position, type, subCategoryLists.get(position).getId(), "", "", "");
                }

                //method.addContinue(db,position,subCategoryLists);
            }
        });

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

    }

    @Override
    public int getItemCount() {
        return subCategoryLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView_Name, textView_author, textView_ratingCount, textView_viewCount, textView_description;

        private RelativeLayout relativeLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.relativeLayout_latestListView_adapter);
            imageView = itemView.findViewById(R.id.imageView_latestListView_adapter);
            textView_Name = itemView.findViewById(R.id.textView_name_latestListView_adapter);
            textView_author = itemView.findViewById(R.id.textView_author_latestListView_adapter);
          // ratingBar = itemView.findViewById(R.id.ratingBar_latestListView_adapter);
            textView_ratingCount = itemView.findViewById(R.id.textView_ratingCount_latestListView_adapter);
            textView_viewCount = itemView.findViewById(R.id.textView_view_latestListView_adapter);
            textView_description = itemView.findViewById(R.id.textView_description_latestListView_adapter);

        }
    }

}
