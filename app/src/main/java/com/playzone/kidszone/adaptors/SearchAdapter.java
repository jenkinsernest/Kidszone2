package com.playzone.kidszone.adaptors;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.playzone.kidszone.SingleBookDetail;
import com.playzone.kidszone.Method;
import com.playzone.kidszone.Parent;
import com.playzone.kidszone.Parent_games;
import com.playzone.kidszone.R;
import com.playzone.kidszone.SET_USER_DETAILS;
import com.playzone.kidszone.Swipe_home;
import com.playzone.kidszone.fragmentpackage.Video_series;
import com.playzone.kidszone.fragmentpackage.new_home_screen;
import com.playzone.kidszone.models.search_model;
import com.playzone.kidszone.models.statistics_model;
import com.playzone.kidszone.models.video_series_model;
import com.playzone.kidszone.youtube_player;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {


    private Activity mContext;
    Swipe_home main;
    private String type;
    String paid_data = null;
    String paid_data_name = null;
    private int lastPosition = -1;
    private int index = 0;
Dialog dialog;
    List<search_model> mItems = new ArrayList<search_model>();

    private ViewGroup parent;
    private int viewType;
    View view;
Fragment frag;
    public Method method;
String cat;

    public SearchAdapter(Activity con, List<search_model> list, String ser, Fragment frag) {
        this.mContext = con;
        this.type = type;

        mItems = list;
this.frag = frag;
        this.method= new Method(con);

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


                view = LayoutInflater.from(mContext).inflate(R.layout.search_large, parent, false);

                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:

                // toastMsg = false;
                view = LayoutInflater.from(mContext).inflate(R.layout.search, parent, false);

                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:

                // toastMsg = false;
                view = LayoutInflater.from(mContext).inflate(R.layout.search, parent, false);

                break;
            default:

                view = LayoutInflater.from(mContext).inflate(R.layout.search_large, parent, false);
                // toastMsg = true;
        }




        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position ) {


        holder.setIsRecyclable(false);

   // holder.image.setImageDrawable(mItems.get(position).getIcon());
holder.name.setText(position+1 + ".  " + mItems.get(position).getTitle());

        Picasso.get()
                .load(mItems.get(position).getPix_url())
                .placeholder(R.drawable.video10)
                .error(R.drawable.video10).
                into(holder.image);
//holder.mItems2= mItems;



holder.v1.setVisibility(View.GONE);
holder.v2.setVisibility(View.GONE);






        holder.set_stuff(holder.card,holder, mItems.get(position));
}













    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView name, pack;
        ImageView image, image_play, lockstatus;
        LinearLayout ll;
        LinearLayout card;
        TextView duration,package_name,expire, status, expiretext;
      video_series_model items = new video_series_model();
        List<video_series_model> mItems2 = new ArrayList<video_series_model>();
        search_model mItems3 = new search_model();
        View v1, v2;
        int id;
        public ViewHolder(View v) {

            super(v);
           // Toast.makeText(mContext,"view holder", Toast.LENGTH_LONG).show();



                card = (LinearLayout) v.findViewById(R.id.cardView);

                image=(ImageView) v.findViewById(R.id.image);
                image_play=(ImageView) v.findViewById(R.id.vid);
                name=(TextView) v.findViewById(R.id.vidname);
                v1=(View) v.findViewById(R.id.view);
                v2=(View) v.findViewById(R.id.view2);

          //  Toast.makeText(mContext,"view holder", Toast.LENGTH_LONG).show();



        }




        private void set_stuff(LinearLayout card,ViewHolder holder,search_model items){

   holder.card=card;

       cat=items.getCategory();
            cat =cat.replace("0", "");

            holder.mItems3=items;

            if (cat.equalsIgnoreCase("discover")) {
                holder.image_play.setVisibility(View.GONE);


            }

            else if (cat.equalsIgnoreCase("books")  ) {
                holder.image_play.setVisibility(View.GONE);
                //holder.image_play.setImageDrawable(mContext.getResources().getDrawable(R.drawable.book_big));
                holder.v1.setVisibility(View.VISIBLE);
                holder.v2.setVisibility(View.VISIBLE);
            }

            else if (cat.equalsIgnoreCase("games")) {
//holder.image_play.setVisibility(View.GONE);
               holder.image_play.setImageDrawable(mContext.getResources().getDrawable(R.drawable.games_real));
            }


            holder.card.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            cat=holder.mItems3.getCategory();
                                            cat =cat.replace("0", "");

         //  method.alertBox("cat= "+ cat + "category = " + holder.mItems3.getCategory() , 3);


                                            if (cat.equalsIgnoreCase("video")) {

                                                if (holder.mItems3.getCategory().contains("0")) {

                                                    Parent.is_task_video = "no";
 if (new_home_screen.total_task_time == 0 || Parent.educational_goals.get(0).getLearn_first().equalsIgnoreCase("false")) {

                                                        // youtube_player.data.clear();
                                                        youtube_player.data = holder.mItems2;
                                                        //if (mItems.size()>=1) {
                                                        // youtube_player play = new youtube_player();
                                                        youtube_player.video = items.geturl();
                                                        youtube_player.category = "video_series";
                                                        Intent in = new Intent(mContext, youtube_player.class);
                                                        mContext.startActivity(in);


                    save_data_fix_layout(holder.mItems3.getTitle());

                                                    } else {
                                                        method.alertBox("Please Finish Your Educational Goals", 3);
                                                    }
                                                }
                                                else {
                                                    Parent.is_task_video = "yes";

                                                    if (new_home_screen.task_exist.equalsIgnoreCase("video") ||
                                                            new_home_screen.task_exist.equalsIgnoreCase("both") ||
                                                            new_home_screen.task_exist.equalsIgnoreCase("none")) {



                                                        youtube_player.data = holder.mItems2;
                                                        //if (mItems.size()>=1) {
                                                        // youtube_player play = new youtube_player();
                                                        youtube_player.video = items.geturl();
                                                        youtube_player.category = "video_series";
                                                        Intent in = new Intent(mContext, youtube_player.class);
                                                        mContext.startActivity(in);

                                                        save_data_fix_layout(holder.mItems3.getTitle());


                                                    }

                                                    else if(new_home_screen.task_exist.equalsIgnoreCase("book") &&
                                                            Parent.educational_goals.get(0).getLearn_first().equalsIgnoreCase("true")){

                                                        method.alertBox("Please Finish Your Reading Educational Goals",3);
                                                    }
                                                    else{

                                                        youtube_player.data = holder.mItems2;
                                                        //if (mItems.size()>=1) {
                                                        // youtube_player play = new youtube_player();
                                                        youtube_player.video = items.geturl();
                                                        youtube_player.category = "video_series";
                                                        Intent in = new Intent(mContext, youtube_player.class);
                                                        mContext.startActivity(in);


                                                        save_data_fix_layout(holder.mItems3.getTitle());

                                                    }


                                                }

                                            }


                                            else if (cat.equalsIgnoreCase("playlist")) {

                                                if (holder.mItems3.getCategory().contains("0")) {

                                                    Parent.is_task_video = "no";
                                                    if (new_home_screen.total_task_time == 0 || Parent.educational_goals.get(0).getLearn_first().equalsIgnoreCase("false")) {


                                                        Video_series.poster = holder.mItems3.getPix_url();

                                                        Video_series.Category = "Latest Playlist";
                                                        Video_series.playlist_id = items.geturl();
                                                        Video_series.playlist_name = items.getTitle();
                                                        Video_series.Direct = true;


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


                                                        save_data_fix_layout(holder.mItems3.getTitle());
                                                        // }


                                                    } else {
                                                        method.alertBox("Please Finish Your Educational Goals", 3);
                                                    }
                                                }

                                                else {

                                                    Parent.is_task_video = "yes";
        if (new_home_screen.task_exist.equalsIgnoreCase("video") ||
                                                            new_home_screen.task_exist.equalsIgnoreCase("both") ||
                                                            new_home_screen.task_exist.equalsIgnoreCase("none")) {

            Video_series.poster = holder.mItems3.getPix_url();

            Video_series.Category = "Latest Playlist";
            Video_series.playlist_id = items.geturl();
            Video_series.playlist_name = items.getTitle();
            Video_series.Direct = true;


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


            save_data_fix_layout(holder.mItems3.getTitle());
            // }

                                                    }
                                                    else if(new_home_screen.task_exist.equalsIgnoreCase("book") &&
                                                            Parent.educational_goals.get(0).getLearn_first().equalsIgnoreCase("true")){

                                                        method.alertBox("Please Finish Your Reading Educational Goals",3);
                                                    }
                                                    else {



            Video_series.poster = holder.mItems3.getPix_url();

            Video_series.Category = "Latest Playlist";
            Video_series.playlist_id = items.geturl();
            Video_series.playlist_name = items.getTitle();
            Video_series.Direct = true;


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


            save_data_fix_layout(holder.mItems3.getTitle());
            // }
                                                    }
                                                }
                                            }

                                            else if (cat.equalsIgnoreCase("discover")) {
  if (new_home_screen.total_task_time == 0 || Parent.educational_goals.get(0).getLearn_first().equalsIgnoreCase("false")) {


      Video_series.poster = items.getPix_url();
      Video_series.Category = items.getTitle();

      FragmentTransaction transaction = ((FragmentActivity) v.getContext()).getSupportFragmentManager()
              .beginTransaction();
      transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
      transaction.replace(R.id.dfrag, new Video_series());
      // transaction.addToBackStack(null);
      transaction.commit();

      Swipe_home.fab.setVisibility(View.GONE);
      // Swipe_home.ninos.setVisibility(View.GONE);
      Swipe_home.apps.setVisibility(View.GONE);
      Swipe_home.book_lay.setVisibility(View.GONE);

      // }

      save_data_fix_layout(holder.mItems3.getTitle());
  }
  else{
      method.alertBox("Please Finish Your Educational Goals", 3);
  }

                                            }


                                            else if (cat.equalsIgnoreCase("books")  ) {

                                                if (holder.mItems3.getCategory().contains("0")) {


                                                    if (new_home_screen.total_task_time == 0 || Parent.educational_goals.get(0).getLearn_first().equalsIgnoreCase("false")) {


                                                        mContext.startActivity(new Intent(mContext, SingleBookDetail.class)
                                                                .putExtra("bookId", items.geturl())
                                                                .putExtra("position", 1)
                                                                .putExtra("type", ""));
                                                        // }

                                                        save_data_fix_layout(holder.mItems3.getTitle());
                                                    } else {
                                                        method.alertBox("Please Finish Your Educational Goals", 3);
                                                    }
                                                } else {


                                                    if (new_home_screen.task_exist.equalsIgnoreCase("book") ||
                                                            new_home_screen.task_exist.equalsIgnoreCase("both") ||
                                                            new_home_screen.task_exist.equalsIgnoreCase("none")) {

                                                        mContext.startActivity(new Intent(mContext, SingleBookDetail.class)
                                                                .putExtra("bookId", items.geturl())
                                                                .putExtra("position", 1)
                                                                .putExtra("type", ""));
                                                        // }

                                                        save_data_fix_layout(holder.mItems3.getTitle());

                 } else if (new_home_screen.task_exist.equalsIgnoreCase("video") &&
                                                            Parent.educational_goals.get(0).getLearn_first().equalsIgnoreCase("true")) {

                                                        method.alertBox("Please Finish Your Video Educational Goals", 3);
                                                    } else {

                                                        mContext.startActivity(new Intent(mContext, SingleBookDetail.class)
                                                                .putExtra("bookId", items.geturl())
                                                                .putExtra("position", 1)
                                                                .putExtra("type", ""));
                                                        // }

                                                        save_data_fix_layout(holder.mItems3.getTitle());
                                                    }
                                                }
                                            }

                                            else if (cat.equalsIgnoreCase("games")) {


                                                if (new_home_screen.total_task_time == 0 || Parent.educational_goals.get(0).getLearn_first().equalsIgnoreCase("false")) {


                                                    Intent in = new Intent(mContext, Parent_games.class);
                                                    in.putExtra("game_url", "none");
                                                    mContext.startActivity(in);

                                                    // }
                                                    save_data_fix_layout(holder.mItems3.getTitle());
                                                }
                                                else {
                                                    method.alertBox("Please Finish Your Educational Goals", 3);
                                                }






                                            }
 else   {


                                                if (holder.mItems3.getCategory().contains("0")) {

                                                    Parent.is_task_video = "no";
                                                    if (new_home_screen.total_task_time == 0 || Parent.educational_goals.get(0).getLearn_first().equalsIgnoreCase("false")) {


                                                        Video_series.poster = holder.mItems3.getPix_url();

                                                        Video_series.Category = "Latest Playlist";
                                                        Video_series.playlist_id = items.geturl();
                                                        Video_series.playlist_name = items.getTitle();
                                                        Video_series.Direct = true;


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


                                                        save_data_fix_layout(holder.mItems3.getTitle());
                                                        // }


                                                    } else {
                                                        method.alertBox("Please Finish Your Educational Goals", 3);
                                                    }
                                                }

                                                else {
                                                    Parent.is_task_video = "yes";

                                                    if (new_home_screen.task_exist.equalsIgnoreCase("video") ||
                                                            new_home_screen.task_exist.equalsIgnoreCase("both") ||
                                                            new_home_screen.task_exist.equalsIgnoreCase("none")) {

                                                        Video_series.poster = holder.mItems3.getPix_url();

                                                        Video_series.Category = "Latest Playlist";
                                                        Video_series.playlist_id = items.geturl();
                                                        Video_series.playlist_name = items.getTitle();
                                                        Video_series.Direct = true;


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


                                                        save_data_fix_layout(holder.mItems3.getTitle());
                                                        // }

                                                    }
                                                    else if(new_home_screen.task_exist.equalsIgnoreCase("book") &&
                                                            Parent.educational_goals.get(0).getLearn_first().equalsIgnoreCase("true")){

                                                        method.alertBox("Please Finish Your Reading Educational Goals",3);
                                                    }
                                                    else {



                                                        Video_series.poster = holder.mItems3.getPix_url();

                                                        Video_series.Category = "Latest Playlist";
                                                        Video_series.playlist_id = items.geturl();
                                                        Video_series.playlist_name = items.getTitle();
                                                        Video_series.Direct = true;


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


                                                        save_data_fix_layout(holder.mItems3.getTitle());
                                                        // }
                                                    }
                                                }
                                                // Games.method.alertBox("grid= " + Parent.statisticsList.get(0).times_opened);


                                                // }
                                            }

                                          //  new_home_screen. recyclerViewVideo.setLayoutManager(new GridLayoutManager(mContext, 2));

                      // new_home_screen. imm.hideSoftInputFromWindow(new_home_screen.parentview.getRootView().getWindowToken(), 0);

// new_home_screen.imm.hideSoftInputFromWindow(null, 0);
                                          //
                                        }
                                        // }


                                    }



            );

        }



}


void save_data_fix_layout(String name){

    LinearLayoutManager mLayoutManager4 = new LinearLayoutManager(mContext);
    mLayoutManager4.setOrientation(RecyclerView.VERTICAL);

    new_home_screen.recyclerViewVideo.setLayoutManager(mLayoutManager4);





    SimpleDateFormat formateDate2 = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
    Calendar date2 = Calendar.getInstance();
    String dates = null;

    dates = formateDate2.format(date2.getTime());
    //  date2= formateDate2.parse(dates);

    // if (mItems.size()>=1) {
    // System.out.println("am in2");
    String title = name.replace("'", "");

    try {
        Parent.dbhelper.addstatistics(new statistics_model(Parent.kid_id, title,
                        null, "1", dates, Parent.pname),
                title, Parent.kid_id);

    }catch (Exception d){

    }
    if (method.isNetworkAvailable()) {
        SET_USER_DETAILS set = new SET_USER_DETAILS();

        set.add_statistics(Parent.kid_id, title, Parent.current_app_click_count,
                dates, "icon", frag, mContext);
    }
    // Games.method.alertBox("grid= " + Parent.statisticsList.get(0).times_opened);



}


}