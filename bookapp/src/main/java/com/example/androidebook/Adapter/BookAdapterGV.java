package com.example.androidebook.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidebook.DataBase.DatabaseHandler;
import com.example.androidebook.InterFace.InterstitialAdView;
import com.example.androidebook.Item.SubCategoryList;
import com.example.androidebook.R;
import com.example.androidebook.Util.Method;
import com.github.ornolfr.ratingview.RatingView;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class BookAdapterGV extends RecyclerView.Adapter<BookAdapterGV.ViewHolder> {

    private Method method;
    private Activity activity;
    private List<SubCategoryList> subCategoryLists;
    private int columnWidth;
    private String type;
    private DatabaseHandler db;

    public BookAdapterGV(Activity activity, List<SubCategoryList> subCategoryLists, String type, InterstitialAdView interstitialAdView) {
        this.activity = activity;
        this.type = type;
        this.subCategoryLists = subCategoryLists;

        method = new Method(activity, interstitialAdView);
        try {
            Resources r = activity.getResources();
            float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics());
            columnWidth = (int) ((method.getScreenWidth() - ((4 + 1) * padding)));
            db = new DatabaseHandler(activity);
        }catch(Exception f){

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.latest_gridview_adapter, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

       // holder.cardView.setLayoutParams(new CardView.LayoutParams(columnWidth / 3, columnWidth / 2));
       // holder.imageView.setLayoutParams(new RelativeLayout.LayoutParams(columnWidth / 3, columnWidth / 2));
       // holder.view.setLayoutParams(new RelativeLayout.LayoutParams(columnWidth / 3, columnWidth / 2));
       // holder.viewImage.setLayoutParams(new RelativeLayout.LayoutParams(columnWidth / 3, columnWidth / 2));

        holder.bookname.setText(subCategoryLists.get(position).getBook_title());
        holder.authorname.setText(subCategoryLists.get(position).getAuthor_name());
          holder.authorname.setTypeface(Method.scriptable);
       //  holder.textView_Name.setTypeface(Method.scriptable);
        holder.bookname.setTypeface(Method.scriptable);



       // holder.textView_Name.setText(subCategoryLists.get(position).getBook_title());
       // holder.textView_Name.setTypeface(Method.scriptable);
       // holder.textView_author.setText(activity.getString(R.string.author_by) + "\u0020" + subCategoryLists.get(position).getAuthor_name());
      // holder.textView_ratingCount.setText(subCategoryLists.get(position).getTotal_rate());
      //  holder.ratingBar.setRating(Float.parseFloat(subCategoryLists.get(position).getRate_avg()));

        Picasso.get().load(subCategoryLists.get(position).getBook_cover_img())
                .placeholder(R.drawable.placeholder_portable)
                .into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                method.interstitialAdShow(position, type, subCategoryLists.get(position).getId(), "", "", "");
                method.addContinue(db, position, subCategoryLists);
            }
        });

    }

    @Override
    public int getItemCount() {
        return subCategoryLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private View view, viewImage;
        private TextView textView_Name, textView_author, textView_ratingCount;
        private RatingView ratingBar;
        private LinearLayout cardView;
        private TextView bookname;
        private TextView authorname;


        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView_latest_gridview_adapter);
            cardView = itemView.findViewById(R.id.cardView_latest_gridview_adapter);
          //  view = itemView.findViewById(R.id.view_latest_gridView_adapter);
            viewImage = itemView.findViewById(R.id.viewImage_latest_gridView_adapter);
           // textView_Name = itemView.findViewById(R.id.textView_title_latest_gridview_adapter);
           // textView_author = itemView.findViewById(R.id.textView_author_latest_gridview_adapter);
         //   ratingBar = itemView.findViewById(R.id.ratingBar_latest_gridview_adapter);
           // textView_ratingCount = itemView.findViewById(R.id.textView_ratingCount_latest_gridview_adapter);
            bookname=itemView.findViewById(R.id.book_name);
            authorname=itemView.findViewById(R.id.author_name);

        }
    }

}
