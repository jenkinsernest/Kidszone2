package com.playzone.kidszone.adaptors;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.github.ornolfr.ratingview.RatingView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.playzone.kidszone.R;
import com.playzone.kidszone.models.BookList;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import database.Method;
import database.OnClick;

public class BookAdapterGV2 extends RecyclerView.Adapter {

    private Method method;
    private Activity activity;
    private String type;
    private int columnWidth;
    private List<BookList> bookLists;

    private final int VIEW_TYPE_LOADING = 0;
    private final int VIEW_TYPE_ITEM = 1;
    private final int VIEW_TYPE_Ad = 2;

    public BookAdapterGV2(Activity activity, List<BookList> bookLists, String type, OnClick onClick) {
        this.activity = activity;
        this.type = type;
        this.bookLists = bookLists;
        method = new Method(activity, onClick);
        Resources r = activity.getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, r.getDisplayMetrics());
        columnWidth = (int) ((method.getScreenWidth() - ((4 + 3) * padding)));
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(activity).inflate(R.layout.book_gridview_adapter, parent, false);
            return new ViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View v = LayoutInflater.from(activity).inflate(R.layout.layout_loading_item, parent, false);
            return new ProgressViewHolder(v);
        } else if (viewType == VIEW_TYPE_Ad) {
          //  View view = LayoutInflater.from(activity).inflate(R.layout.adview_adapter, parent, false);
           // return new AdOption(view);
        }
        return null;
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder.getItemViewType() == VIEW_TYPE_ITEM) {

            final ViewHolder viewHolder = (ViewHolder) holder;

            viewHolder.cardView.setLayoutParams(new RelativeLayout.LayoutParams(columnWidth / 3, columnWidth / 2));

            viewHolder.textViewName.setText(bookLists.get(position).getBook_title());
            viewHolder.textViewAuthor.setText("by" + "\u0020" + bookLists.get(position).getAuthor_name());
            viewHolder.textViewRatingCount.setText(bookLists.get(position).getTotal_rate());
            viewHolder.ratingBar.setRating(Float.parseFloat(bookLists.get(position).getRate_avg()));

          /*  Glide.with(activity).load(bookLists.get(position).getBook_cover_img())
                    .placeholder(R.drawable.placeholder_portable)

                  .into(viewHolder.imageView);

           */

            Picasso.get()
                    .load(bookLists.get(position).getBook_cover_img())
                    .placeholder(R.drawable.placeholder_portable)
                    .error(R.drawable.ic_grid).
                    into(viewHolder.imageView);



            viewHolder.cardView.setOnClickListener(v -> method.onClickAd(position, type, bookLists.get(position).getId(), "", "", "", "", ""));

        }

    }

    @Override
    public int getItemCount() {
        if (bookLists.size() != 0) {
            return bookLists.size() + 1;
        } else {
            return bookLists.size();
        }
    }

    public void hideHeader() {
        ProgressViewHolder.progressBar.setVisibility(View.GONE);
    }

    @Override
    public int getItemViewType(int position) {
        if (bookLists.size() == position) {
            return VIEW_TYPE_LOADING;
        } else if (bookLists.get(position).isIs_ads()) {
            return VIEW_TYPE_Ad;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private RatingView ratingBar;
        private MaterialCardView cardView;
        private MaterialTextView textViewName, textViewAuthor, textViewRatingCount;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView_bookGrid_adapter);
            cardView = itemView.findViewById(R.id.cardView_bookGrid_adapter);
            textViewName = itemView.findViewById(R.id.textView_title_bookGrid_adapter);
            textViewAuthor = itemView.findViewById(R.id.textView_author_bookGrid_adapter);
            ratingBar = itemView.findViewById(R.id.ratingBar_bookGrid_adapter);
            textViewRatingCount = itemView.findViewById(R.id.textView_ratingCount_bookGrid_adapter);

        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public static ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = v.findViewById(R.id.progressBar_loading);
        }
    }

    public class AdOption extends RecyclerView.ViewHolder {

        private ConstraintLayout conAdView;

        public AdOption(View itemView) {
            super(itemView);
           // conAdView = itemView.findViewById(R.id.con_adView);
        }
    }

}
