package com.playzone.kidszone.adaptors;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.ornolfr.ratingview.RatingView;
import com.google.android.material.textview.MaterialTextView;
import com.playzone.kidszone.R;
import com.playzone.kidszone.models.BookList;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import database.Method;
import database.OnClick;

public class BookHomeAdapter extends RecyclerView.Adapter<BookHomeAdapter.ViewHolder> {

    private Method method;
    private Activity activity;
    private String type;
    private List<BookList> subCategoryLists;

    public BookHomeAdapter(Activity activity, List<BookList> subCategoryLists, String type, OnClick onClick) {
        this.activity = activity;
        this.subCategoryLists = subCategoryLists;
        this.type = type;
        method = new Method(activity, onClick);
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.latest_gridview_adapter, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, final int position) {

        holder.textViewName.setText(subCategoryLists.get(position).getBook_title());
        holder.textViewAuthor.setText("by" + "\u0020" + subCategoryLists.get(position).getAuthor_name());
       // holder.textViewRatingCount.setText(subCategoryLists.get(position).getTotal_rate());
       // holder.ratingBar.setRating(Float.parseFloat(subCategoryLists.get(position).getRate_avg()));

        if (!subCategoryLists.get(position).getBook_cover_img().equals("")) {

           /* Glide.with(activity).load(subCategoryLists.get(position).getBook_cover_img())
                    .placeholder(R.drawable.placeholder_portable)
                    .into(holder.imageView);

            */

            Picasso.get()
                    .load(subCategoryLists.get(position).getBook_cover_img())
                    .placeholder(R.drawable.placeholder_portable)
                    .error(R.drawable.drawer_image).
                    into(holder.imageView);
        }

        holder.cardView.setOnClickListener(v -> method.onClickAd(position, type, subCategoryLists.get(position).getId(),"", "", "", "",""));

    }

    @Override
    public int getItemCount() {
        return subCategoryLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private RatingView ratingBar;
        private LinearLayout cardView;
        private MaterialTextView textViewName, textViewAuthor, textViewRatingCount;


        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView_latest_gridview_adapter);
            cardView = itemView.findViewById(R.id.cardView_latest_gridview_adapter);
            textViewName = itemView.findViewById(R.id.book_name);
            textViewAuthor = itemView.findViewById(R.id.author_name);
           // ratingBar = itemView.findViewById(R.id.ratingBar_book_home_adapter);
           // textViewRatingCount = itemView.findViewById(R.id.textView_ratingCount_book_home_adapter);

        }
    }

}
