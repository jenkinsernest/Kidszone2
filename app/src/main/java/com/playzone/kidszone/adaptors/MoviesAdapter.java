package com.playzone.kidszone.adaptors;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.playzone.kidszone.Method;
import com.playzone.kidszone.Parent;
import com.playzone.kidszone.R;
import com.playzone.kidszone.SET_USER_DETAILS;
import com.playzone.kidszone.Swipe_home;
import com.playzone.kidszone.fragmentpackage.Video_series;
import com.playzone.kidszone.fragmentpackage.new_home_screen;
import com.playzone.kidszone.models.playlist_model;
import com.playzone.kidszone.models.statistics_model;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {


    private Activity mContext;
    Swipe_home main;
    private String type;
    String paid_data = null;
    String paid_data_name = null;
    private int lastPosition = -1;
    private int index = 0;
Dialog dialog;
    List<playlist_model> mItems;

    private ViewGroup parent;
    private int viewType;
    View view;
Fragment frag;
    public Method method;
 public String catname;

    public MoviesAdapter(Activity con, List<playlist_model> list, String catname, Fragment frag) {
        this.mContext = con;
        this.type = type;
        main = new Swipe_home();
        mItems = list;
this.frag = frag;
        this.method= new Method(con);
        this.catname= catname;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;
        this.viewType = viewType;
       view = null;



        switch (Parent.screenSize) {

            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                //toastMsg = true;


                view = LayoutInflater.from(mContext).inflate(R.layout.playlist_seasons_large_front, parent, false);

                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:

                // toastMsg = false;
                view = LayoutInflater.from(mContext).inflate(R.layout.playlist_seasons_front, parent, false);

                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:

                // toastMsg = false;
                view = LayoutInflater.from(mContext).inflate(R.layout.playlist_seasons_front, parent, false);

                break;
            default:

                view = LayoutInflater.from(mContext).inflate(R.layout.playlist_seasons_large_front, parent, false);
                // toastMsg = true;
        }




        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position ) {




   // holder.image.setImageDrawable(mItems.get(position).getIcon());

        Picasso.get()
                .load(mItems.get(position).getPix_url())
                .placeholder(R.drawable.video10)
                .error(R.drawable.video10).
                into(holder.image);

holder.name.setText(mItems.get(position).getTitle());
holder.name.setTextColor(Color.WHITE);
holder.count.setText(mItems.get(position).getItem_count());

    holder.card.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Video_series.poster=mItems.get(position).getPix_url();

                                           if (catname.contains("0")) {

                                               Parent.is_task_video="no";

 if (new_home_screen.total_task_time == 0 || Parent.educational_goals.get(0).getLearn_first().equalsIgnoreCase("false")) {

                                                   Video_series.Direct = true;
                                                   Video_series.Category = "Latest Playlist";
                                                   Video_series.playlist_id = mItems.get(position).getPlaylist_id();
                                                   Video_series.playlist_name = mItems.get(position).getTitle();


                                                   Swipe_home.fab.setVisibility(View.GONE);
                                                   // Swipe_home.ninos.setVisibility(View.GONE);
                                                   Swipe_home.apps.setVisibility(View.GONE);
     Swipe_home.book_lay.setVisibility(View.GONE);
                                                   FragmentTransaction transaction = ((FragmentActivity) v.getContext()).getSupportFragmentManager()
                                                           .beginTransaction();
                                                   transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                                                   transaction.replace(R.id.dfrag, new Video_series());
                                                   // transaction.addToBackStack(null);
                                                   transaction.commit();


                                                   SimpleDateFormat formateDate2 = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
                                                   Calendar date2 = Calendar.getInstance();
                                                   String dates = null;

                                                   dates = formateDate2.format(date2.getTime());
                                                   //  date2= formateDate2.parse(dates);
                                                   String title = mItems.get(position).getTitle().replace("'", "");

                                                   Parent.dbhelper.addstatistics(new statistics_model(Parent.kid_id, title,
                                                                   null, "1", dates, Parent.pname),
                                                           title, Parent.kid_id);

                                                   if (method.isNetworkAvailable()) {
                                                       SET_USER_DETAILS set = new SET_USER_DETAILS();

                                                       set.add_statistics(Parent.kid_id, title, Parent.current_app_click_count,
                                                               dates, "icon", frag, mContext);
                                                   }

                                               } else {
                                                   method.alertBox("Please Finish Your Educational Goals", 3);
                                               }
                                               // Games.method.alertBox("grid= " + Parent.statisticsList.get(0).times_opened);
                                           }
                                           else{
                                               Parent.is_task_video="yes";

                                               if(new_home_screen.task_exist.equalsIgnoreCase("video") ||
                                                       new_home_screen.task_exist.equalsIgnoreCase("both") ||
                                                       new_home_screen.task_exist.equalsIgnoreCase("none")) {


                                                   //allow

                                                   Video_series.Direct = true;
                                                   Video_series.Category = "Latest Playlist";
                                                   Video_series.playlist_id = mItems.get(position).getPlaylist_id();
                                                   Video_series.playlist_name = mItems.get(position).getTitle();


                                                   Swipe_home.fab.setVisibility(View.GONE);
                                                   // Swipe_home.ninos.setVisibility(View.GONE);
                                                   Swipe_home.apps.setVisibility(View.GONE);
                                                   Swipe_home.book_lay.setVisibility(View.GONE);
                                                   FragmentTransaction transaction = ((FragmentActivity) v.getContext()).getSupportFragmentManager()
                                                           .beginTransaction();
                                                   transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                                                   transaction.replace(R.id.dfrag, new Video_series());
                                                   // transaction.addToBackStack(null);
                                                   transaction.commit();


                                                   SimpleDateFormat formateDate2 = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
                                                   Calendar date2 = Calendar.getInstance();
                                                   String dates = null;

                                                   dates = formateDate2.format(date2.getTime());
                                                   //  date2= formateDate2.parse(dates);
                                                   String title = mItems.get(position).getTitle().replace("'", "");

                                                   Parent.dbhelper.addstatistics(new statistics_model(Parent.kid_id, title,
                                                                   null, "1", dates, Parent.pname),
                                                           title, Parent.kid_id);

                                                   if (method.isNetworkAvailable()) {
                                                       SET_USER_DETAILS set = new SET_USER_DETAILS();

                                                       set.add_statistics(Parent.kid_id, title, Parent.current_app_click_count,
                                                               dates, "icon", frag, mContext);
                                                   }
                                               }
                   else if(new_home_screen.task_exist.equalsIgnoreCase("book") &&
                   Parent.educational_goals.get(0).getLearn_first().equalsIgnoreCase("true")){

                                                   method.alertBox("Please Finish Your Educational Goals",3);
                                               }
                                               else{
                                                //allow


                                                   Video_series.Direct = true;
                                                   Video_series.Category = "Latest Playlist";
                                                   Video_series.playlist_id = mItems.get(position).getPlaylist_id();
                                                   Video_series.playlist_name = mItems.get(position).getTitle();


                                                   Swipe_home.fab.setVisibility(View.GONE);
                                                   // Swipe_home.ninos.setVisibility(View.GONE);
                                                   Swipe_home.apps.setVisibility(View.GONE);
                                                   Swipe_home.book_lay.setVisibility(View.GONE);

                                                   FragmentTransaction transaction = ((FragmentActivity) v.getContext()).getSupportFragmentManager()
                                                           .beginTransaction();
                                                   transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                                                   transaction.replace(R.id.dfrag, new Video_series());
                                                   // transaction.addToBackStack(null);
                                                   transaction.commit();


                                                   SimpleDateFormat formateDate2 = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
                                                   Calendar date2 = Calendar.getInstance();
                                                   String dates = null;

                                                   dates = formateDate2.format(date2.getTime());
                                                   //  date2= formateDate2.parse(dates);
                                                   String title = mItems.get(position).getTitle().replace("'", "");

                                                   Parent.dbhelper.addstatistics(new statistics_model(Parent.kid_id, title,
                                                                   null, "1", dates, Parent.pname),
                                                           title, Parent.kid_id);

                                                   if (method.isNetworkAvailable()) {
                                                       SET_USER_DETAILS set = new SET_USER_DETAILS();

                                                       set.add_statistics(Parent.kid_id, title, Parent.current_app_click_count,
                                                               dates, "icon", frag, mContext);
                                                   }


                                               }



                                               }
                                       }
                                       }



    );

}













    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView name, count;
        ImageView image, image_done, lockstatus;
        LinearLayout ll;
        LinearLayout card;
        TextView duration,package_name,expire, status, expiretext;

        public ViewHolder(View v) {

            super(v);
           // Toast.makeText(mContext,"view holder", Toast.LENGTH_LONG).show();



                card = (LinearLayout) v.findViewById(R.id.cardView);

                image=(ImageView) v.findViewById(R.id.image);
                name=(TextView) v.findViewById(R.id.vidname);
                count=(TextView) v.findViewById(R.id.item_count);

          //  Toast.makeText(mContext,"view holder", Toast.LENGTH_LONG).show();



        }
    }

}