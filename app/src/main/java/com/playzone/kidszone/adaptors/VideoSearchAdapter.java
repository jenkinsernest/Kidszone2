package com.playzone.kidszone.adaptors;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.playzone.kidszone.Method;
import com.playzone.kidszone.Parent;
import com.playzone.kidszone.Parent_games;
import com.playzone.kidszone.R;
import com.playzone.kidszone.Swipe_home;
import com.playzone.kidszone.fragmentpackage.LatestFragment2;
import com.playzone.kidszone.fragmentpackage.Video_views;
import com.playzone.kidszone.fragmentpackage.new_home_screen;
import com.playzone.kidszone.models.SubCategoryList;
import com.playzone.kidszone.models.categories_model;
import com.playzone.kidszone.models.game_model;
import com.playzone.kidszone.models.playlist_model;
import com.playzone.kidszone.models.video_cat_model;
import com.playzone.kidszone.models.video_series_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class VideoSearchAdapter extends RecyclerView.Adapter<VideoSearchAdapter.ViewHolder> {


    private Activity mContext;
    Swipe_home main;
    private String type;
    String paid_data = null;
    String paid_data_name = null;
    private int lastPosition = -1;
    private int index = 0;
Dialog dialog;
    List<categories_model> mItems;

    private ViewGroup parent;
    private int viewType;
    View view;
Fragment frag;
    public Method method;
    Game2Adapter mAdapterGD;
    String Search_key;
    JSONArray jsonArrayPopular = null;

    public VideoSearchAdapter(Activity con, List<categories_model> list, String ser, Fragment frag) {
        this.mContext = con;
        this.type = type;
        main = new Swipe_home();
        mItems = list;
this.frag = frag;
this.Search_key=ser;
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


                view = LayoutInflater.from(mContext).inflate(R.layout.ultimate_view_big, parent, false);

                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:

                // toastMsg = false;
                view = LayoutInflater.from(mContext).inflate(R.layout.ultimate_view, parent, false);

                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:

                // toastMsg = false;
                view = LayoutInflater.from(mContext).inflate(R.layout.ultimate_view, parent, false);

                break;
            default:

                view = LayoutInflater.from(mContext).inflate(R.layout.ultimate_view_big, parent, false);
                // toastMsg = true;
        }




        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position ) {


      //  et_data(ImageView m, RecyclerView rec, String catname, List<video_cat_model> data)
        String name="";
 name =mItems.get(position).getName().replace("8", "");
 name =name.replace("11", "");
 name =name.replace("6", "");
 name =name.replace("15", "");
 name =name.replace("17", "");
        name =name.replace("0", "");

        holder.cat_name.setText( name);


       // JSONArray jsonArrayPopular = null;
        try {

          //if(  mItems.get(position).getName().equals("BOOKS")){

         // }
         // else {
             // jsonArrayPopular = Parent.jsonObjectUltimate.getJSONArray(mItems.get(position).getName());
            jsonArrayPopular = Parent.jsonObjectUltimate.getJSONArray(mItems.get(position).getName());
       //   }

          //  System.out.println("name =" + name );

            if(name.equalsIgnoreCase("Games")){


                Parent.games2.clear();
                for (int a = 0; a < jsonArrayPopular.length(); a++) {
                    JSONObject object = jsonArrayPopular.getJSONObject(a);

                    game_model game = new game_model();



    game.setName(object.getString("name"));
    game.setUrl(object.getString("url"));
    game.setBanner(object.getString("banner"));
    game.setLogo(object.getString("logo"));
    game.setDescription(object.getString("description"));
    game.setCategory(object.getString("category"));


    Parent.games2.add(game);

                }

                if(!Parent.games2.isEmpty()) {

                    Handler handle = new Handler(Looper.getMainLooper());
                    handle.post(new Runnable() {
                        @Override
                        public void run() {
                            Parent.games.clear();
                            Parent.games.addAll(Parent.games2);


                            LinearLayoutManager mLayoutManager8 = new LinearLayoutManager(mContext);
                            mLayoutManager8.setOrientation(RecyclerView.HORIZONTAL);

                            holder.cat_data.setLayoutManager(mLayoutManager8);
                            holder.cat_data.setFocusable(true);

                            mAdapterGD = new Game2Adapter(mContext, Parent.games, frag);
                            holder.cat_data.setAdapter(mAdapterGD);


                            Parent.dbhelper.addGAME(Parent.games);

                        }
                    });


                    holder.more.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(new_home_screen.total_task_time==0  || Parent.educational_goals.get(0).getLearn_first().equalsIgnoreCase("false")) {


                                Intent in = new Intent(mContext, Parent_games.class);
                            in.putExtra("game_url", "none");
                            mContext.startActivity(in);
                            }
                            else{
                                method.alertBox("Please Finish your educational Goals",3);
                            }
                        }
                    });


                }




            }

            else if(name.equalsIgnoreCase("Books")){
             //   HomeFragment home=new HomeFragment();
//home.Home_call(holder.cat_data, mContext, holder.more);

                for (int a = 0; a < jsonArrayPopular.length(); a++) {
                    JSONObject object = jsonArrayPopular.getJSONObject(a);




                        String id = object.getString("id");
                        String cat_id = object.getString("cat_id");
                        String book_title = object.getString("book_title");
                        String book_description = object.getString("book_description");
                        String book_cover_img = object.getString("book_cover_img");
                        String book_bg_img = object.getString("book_bg_img");
                        String book_file_type = object.getString("book_file_type");
                        String total_rate = object.getString("total_rate");
                        String rate_avg = object.getString("rate_avg");
                        String book_views = object.getString("book_views");
                        String author_id = object.getString("author_id");
                        String author_name = object.getString("author_name");


                        holder.data_book.add(new SubCategoryList(id, cat_id, book_title, book_description, book_cover_img, book_bg_img, book_file_type, total_rate, rate_avg, book_views, author_id, author_name, ""));
                    }



                if(!holder.data_book.isEmpty()) {
                    holder.set_data3(holder.more, holder.cat_data, mItems.get(0).getName(), holder.data_book, holder);

                }

            }

            else if(name.equalsIgnoreCase("DISCOVER")){
                for (int a = 0; a < jsonArrayPopular.length(); a++) {
                    JSONObject object = jsonArrayPopular.getJSONObject(a);
//System.out.println(Search_key + "object==" + object.getString("title"));



                        String title = object.getString("title");
                        String descr = object.getString("descr");
                        String pix_url = object.getString("pix");
                        String channame = object.getString("channame");


                        holder.data_discover.add(new video_cat_model(title, descr, pix_url, channame));
                    }

                String  Category="";
               Category =mItems.get(position).getName().replace("11", "");
                Category =Category.replace("6", "");
                Category =Category.replace("15", "");
                Category =Category.replace("17", "");
                Category =Category.replace("0", "");

                if(!holder.data_discover.isEmpty()) {
                    holder.set_data2(holder.more, holder.cat_data, Category, holder.data_discover, holder);
                }

            }

            else if(mItems.get(position).getName().contains("8")){

                for (int a = 0; a < jsonArrayPopular.length(); a++) {
                    JSONObject object = jsonArrayPopular.getJSONObject(a);



                    String title = object.getString("title");
                    String descr = object.getString("descr");
                    String pix_url = object.getString("pix");
                    String channame = object.getString("channame");
                    String play_id = object.getString("playlist_id");
                    String item_count = object.getString("item_count");

                    if(title.equals("Short Films")){

                    }
                    else {


                        holder.learn.add(new playlist_model(title, descr, pix_url, channame, play_id, item_count));


                    }
                }


                if(!holder.learn.isEmpty()) {
                    holder.set_data7(holder.more, holder.cat_data, mItems.get(position).getName(), holder.learn, holder);
                }

            }
            else {
               // Parent.vid_cat.clear();

                for (int a = 0; a < jsonArrayPopular.length(); a++) {
                    JSONObject object = jsonArrayPopular.getJSONObject(a);


                        String title = object.getString("title");
                        String descr = object.getString("descr");
                        String pix_url = object.getString("pix");
                        String channame = object.getString("channame");
                        String video_url = object.getString("video_url");


                        holder.data_each.add(new video_series_model(title, descr, pix_url, channame, position + "", video_url));


                }

if(!holder.data_each.isEmpty()) {


    holder.set_data(holder.more, holder.cat_data, mItems.get(position).getName(), holder.data_each, holder);
}

          }
        } catch (JSONException e) {
            e.printStackTrace();
        }





}









    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView cat_name, pack;
        ImageView image, more, lockstatus;
        LinearLayout ll;
        LinearLayout card;
        TextView duration,package_name,expire, status, expiretext;
        RecyclerView cat_data;
        List<video_series_model> data_each = new ArrayList<video_series_model>();
        List<SubCategoryList> data_book = new ArrayList<SubCategoryList>();
        List<video_cat_model> data_discover = new ArrayList<video_cat_model>();
        List<playlist_model> playlist = new ArrayList<playlist_model>();
        List<playlist_model> playlist2 = new ArrayList<playlist_model>();
        List<playlist_model> learn = new ArrayList<playlist_model>();
        List<playlist_model> playlistFeatured = new ArrayList<playlist_model>();

        Home_data_test_Adapter   mAdapterVC;
        VideoCatAdapter   mAdapterVC2;
        PlaylistAdapter   playAdapter;
        MoviesAdapter   movieAdapter;
View view;
        public ViewHolder(View v) {

            super(v);
           // Toast.makeText(mContext,"view holder", Toast.LENGTH_LONG).show();

view=v;
               // card = (LinearLayout) v.findViewById(R.id.cardView);

                more=(ImageView) v.findViewById(R.id.more);
                cat_name=(TextView) v.findViewById(R.id.cat_name);
            cat_data = (RecyclerView)v.findViewById(R.id.cat_data);





        }

        void set_data(ImageView m, RecyclerView rec, String catname, List<video_series_model> data,ViewHolder hold){

            hold.data_each=data;
            LinearLayoutManager mLayoutManager8 = new LinearLayoutManager(mContext);
            mLayoutManager8.setOrientation(RecyclerView.HORIZONTAL);

            rec.setLayoutManager(mLayoutManager8);
            rec.setFocusable(true);



            m.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Video_views.poster=data.get(0).getPix_url();

                    if(new_home_screen.total_task_time==0 || Parent.educational_goals.get(0).getLearn_first().equalsIgnoreCase("false")) {

                        Video_views.Category= catname;
                        Video_views.data_each.clear();
                        Video_views.data_discover.clear();
                        Video_views.data_learn.clear();
                        Video_views.data_playlistfeatured.clear();

                        Video_views.data_playlist.clear();
                        Collections.reverse(hold.data_each);

                        Video_views.data_each=hold.data_each;



                        Swipe_home.fab.setVisibility(View.GONE);
                        //  Swipe_home.ninos.setVisibility(View.GONE);

                        Swipe_home.apps.setVisibility(View.GONE);
                        Swipe_home.book_lay.setVisibility(View.GONE);

                        FragmentTransaction transaction = ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
                        transaction .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                        transaction.replace(R.id.dfrag, new Video_views());
                        transaction.commit();


                    }
                    else{
                        method.alertBox("Please Finish Your Educational Goals",3);
                    }





                }
            });



            hold.mAdapterVC = new Home_data_test_Adapter(mContext, hold.data_each, catname, frag);
            // RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(getActivity(), 4);
            //  recyclerViewPopular.setLayoutManager(layoutManager2);
            rec.setAdapter(hold.mAdapterVC);



        }

        void set_data3(ImageView m, RecyclerView rec, String catname, List<SubCategoryList> data, ViewHolder hold){

            hold.data_book=data;
            LinearLayoutManager mLayoutManager8 = new LinearLayoutManager(mContext);
            mLayoutManager8.setOrientation(RecyclerView.HORIZONTAL);

            rec.setLayoutManager(mLayoutManager8);
            rec.setFocusable(true);


            BookAdapterGV3 latestAdapter3 = new BookAdapterGV3(mContext, hold.data_book, "home_latest", frag);
            //  RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
            //  recyclerViewLatest.setLayoutManager(layoutManager);
            rec.setAdapter(latestAdapter3);




            //progressBar.setVisibility(View.GONE);
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  Intent in = new Intent(mContext, Start_Screen.class);
                    //in.putExtra("game_url", "none");
                    //  mContext.startActivity(in);

                    if(new_home_screen.task_exist.equalsIgnoreCase("book") ||
                            new_home_screen.task_exist.equalsIgnoreCase("both") ||
                            new_home_screen.task_exist.equalsIgnoreCase("none")) {

                        String catname2 = catname;

                        catname2 = catname2.replace("11", "");
                        catname2 = catname2.replace("6", "");
                        catname2 = catname2.replace("15", "");
                        catname2 = catname2.replace("17", "");
                        catname2 = catname2.replace("8", "");
                        catname2 = catname2.replace("0", "");


                        Video_views.Category = catname2;
                        Video_views.data_each.clear();
                        Video_views.data_discover.clear();
                        Video_views.data_learn.clear();
                        Video_views.data_playlistfeatured.clear();

                        Video_views.data_playlist.clear();
                        Video_views.data_book.clear();

                        Video_views.data_book = hold.data_book;


                        Swipe_home.fab.setVisibility(View.GONE);
                        //  Swipe_home.ninos.setVisibility(View.GONE);

                        Swipe_home.apps.setVisibility(View.GONE);

                        Swipe_home.book_lay.setVisibility(View.GONE);
                        FragmentTransaction transaction = ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
                        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                        transaction.replace(R.id.dfrag, new LatestFragment2());
                        transaction.commit();


                    }
                    else if(new_home_screen.task_exist.equalsIgnoreCase("video") &&
                            Parent.educational_goals.get(0).getLearn_first().equalsIgnoreCase("true")) {

                        method.alertBox("Please Complete Educational Goal", 3);

                    }


                    else{
                        String catname2 = catname;

                        catname2 = catname2.replace("11", "");
                        catname2 = catname2.replace("6", "");
                        catname2 = catname2.replace("15", "");
                        catname2 = catname2.replace("17", "");
                        catname2 = catname2.replace("8", "");
                        catname2 = catname2.replace("0", "");


                        Video_views.Category = catname2;
                        Video_views.data_each.clear();
                        Video_views.data_discover.clear();
                        Video_views.data_learn.clear();
                        Video_views.data_playlistfeatured.clear();

                        Video_views.data_playlist.clear();
                        Video_views.data_book.clear();

                        Video_views.data_book = hold.data_book;


                        Swipe_home.fab.setVisibility(View.GONE);
                        //  Swipe_home.ninos.setVisibility(View.GONE);

                        Swipe_home.apps.setVisibility(View.GONE);

                        Swipe_home.book_lay.setVisibility(View.GONE);
                        FragmentTransaction transaction = ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
                        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                        transaction.replace(R.id.dfrag, new LatestFragment2());
                        transaction.commit();

                    }

                }
            });



        }




        void set_data2(ImageView m, RecyclerView rec, String catname, List<video_cat_model> data, ViewHolder hold){

            hold.data_discover=data;
            LinearLayoutManager mLayoutManager8 = new LinearLayoutManager(mContext);
            mLayoutManager8.setOrientation(RecyclerView.HORIZONTAL);

            rec.setLayoutManager(mLayoutManager8);
            rec.setFocusable(true);



            m.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Video_views.poster=data.get(0).getPix_url();

                    if(new_home_screen.total_task_time==0 || Parent.educational_goals.get(0).getLearn_first().equalsIgnoreCase("false")) {





                        Video_views.Category= catname;

                        Video_views.data_each.clear();
                        Video_views.data_discover.clear();
                        Video_views.data_playlist.clear();
                        Video_views.data_playlistfeatured.clear();
                        Video_views.data_learn.clear();
                        Video_views.data_discover= hold.data_discover;

                        Swipe_home.fab.setVisibility(View.GONE);
                        //  Swipe_home.ninos.setVisibility(View.GONE);

                        Swipe_home.apps.setVisibility(View.GONE);

                        Swipe_home.book_lay.setVisibility(View.GONE);

                        FragmentTransaction transaction = ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
                        transaction .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                        transaction.replace(R.id.dfrag, new Video_views());
                        transaction.commit();




                    }
                    else{
                        method.alertBox("Please Finish Your Educational Goals",3);
                    }



                }
            });



            hold.mAdapterVC2 = new VideoCatAdapter(mContext, hold.data_discover, catname, frag);
            // RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(getActivity(), 4);
            //  recyclerViewPopular.setLayoutManager(layoutManager2);
            rec.setAdapter(hold.mAdapterVC2);



        }



        void set_data4(ImageView m, RecyclerView rec, String catname, List<playlist_model> data,ViewHolder hold){

            hold.playlist=data;
            LinearLayoutManager mLayoutManager8 = new LinearLayoutManager(mContext);
            mLayoutManager8.setOrientation(RecyclerView.HORIZONTAL);

            rec.setLayoutManager(mLayoutManager8);
            rec.setFocusable(true);



            m.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(new_home_screen.total_task_time==0 || Parent.educational_goals.get(0).getLearn_first().equalsIgnoreCase("false")) {




                        Video_views.Category= catname;

                        Video_views.data_each.clear();
                        Video_views.data_discover.clear();
                        Video_views.data_playlist.clear();
                        Video_views.data_learn.clear();
                        Video_views.data_playlistfeatured.clear();
                        Video_views.data_playlist= hold.playlist;



                        Swipe_home.fab.setVisibility(View.GONE);
                        //  Swipe_home.ninos.setVisibility(View.GONE);

                        Swipe_home.apps.setVisibility(View.GONE);
                        Swipe_home.book_lay.setVisibility(View.GONE);


                        FragmentTransaction transaction = ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
                        transaction .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                        transaction.replace(R.id.dfrag, new Video_views());
                        transaction.commit();




                    }
                    else{
                        method.alertBox("Please Finish Your Educational Goals",3);
                    }


                }
            });



            hold.movieAdapter = new MoviesAdapter(mContext, hold.playlist, catname, frag);
            // RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(getActivity(), 4);
            //  recyclerViewPopular.setLayoutManager(layoutManager2);
            rec.setAdapter(hold.movieAdapter);



        }



        void set_data5(ImageView m, RecyclerView rec, String catname, List<playlist_model> data, ViewHolder hold){

            hold.playlist2=data;
            LinearLayoutManager mLayoutManager8 = new LinearLayoutManager(mContext);
            mLayoutManager8.setOrientation(RecyclerView.HORIZONTAL);

            rec.setLayoutManager(mLayoutManager8);
            rec.setFocusable(true);



            m.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(new_home_screen.total_task_time==0 || Parent.educational_goals.get(0).getLearn_first().equalsIgnoreCase("false")) {



                        Video_views.Category= catname;

                        Video_views.data_each.clear();
                        Video_views.data_discover.clear();
                        Video_views.data_playlist.clear();
                        Video_views.data_playlist2.clear();
                        Video_views.data_learn.clear();
                        Video_views.data_playlistfeatured.clear();

                        Video_views.data_playlist2= hold.playlist2;



                        Swipe_home.fab.setVisibility(View.GONE);
                        //  Swipe_home.ninos.setVisibility(View.GONE);

                        Swipe_home.apps.setVisibility(View.GONE);
                        Swipe_home.book_lay.setVisibility(View.GONE);


                        FragmentTransaction transaction = ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
                        transaction .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                        transaction.replace(R.id.dfrag, new Video_views());
                        transaction.commit();



                    }
                    else{
                        method.alertBox("Please Finish Your Educational Goals",3);
                    }




                }
            });



            hold.movieAdapter = new MoviesAdapter(mContext, hold.playlist2, catname, frag);
            // RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(getActivity(), 4);
            //  recyclerViewPopular.setLayoutManager(layoutManager2);
            rec.setAdapter(hold.movieAdapter);



        }









        void set_data6(ImageView m, RecyclerView rec, String catname, List<playlist_model> data, ViewHolder hold){

            hold.playlistFeatured=data;
            LinearLayoutManager mLayoutManager8 = new LinearLayoutManager(mContext);
            mLayoutManager8.setOrientation(RecyclerView.HORIZONTAL);

            rec.setLayoutManager(mLayoutManager8);
            rec.setFocusable(true);




            m.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(new_home_screen.total_task_time==0 || Parent.educational_goals.get(0).getLearn_first().equalsIgnoreCase("false")) {


                        Video_views.Category= catname;

                        Video_views.data_each.clear();
                        Video_views.data_discover.clear();
                        Video_views.data_playlist.clear();
                        Video_views.data_playlist2.clear();
                        Video_views.data_learn.clear();
                        Video_views.data_playlistfeatured.clear();
                        Video_views.data_playlistfeatured= hold.playlistFeatured;



                        Swipe_home.fab.setVisibility(View.GONE);
                        //  Swipe_home.ninos.setVisibility(View.GONE);

                        Swipe_home.apps.setVisibility(View.GONE);
                        Swipe_home.book_lay.setVisibility(View.GONE);


                        FragmentTransaction transaction = ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
                        transaction .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                        transaction.replace(R.id.dfrag, new Video_views());
                        transaction.commit();





                    }
                    else{
                        method.alertBox("Please Finish Your Educational Goals",3);
                    }

                }
            });



            hold.movieAdapter = new MoviesAdapter(mContext, hold.playlistFeatured, catname, frag);
            // RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(getActivity(), 4);
            //  recyclerViewPopular.setLayoutManager(layoutManager2);
            rec.setAdapter(hold.movieAdapter);



        }




        void set_data7(ImageView m, RecyclerView rec, String catname, List<playlist_model> data,ViewHolder hold){

            hold.learn=data;
            LinearLayoutManager mLayoutManager8 = new LinearLayoutManager(mContext);
            mLayoutManager8.setOrientation(RecyclerView.HORIZONTAL);

            rec.setLayoutManager(mLayoutManager8);
            rec.setFocusable(true);




            m.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Video_views.poster=data.get(0).getPix_url();

                    if( catname.contains("0") ){
                        Parent.is_task_video="no";

                        if(new_home_screen.total_task_time==0  || Parent.educational_goals.get(0).getLearn_first().equalsIgnoreCase("false")) {


                            Video_views.Category = catname;

                            Video_views.data_each.clear();
                            Video_views.data_discover.clear();
                            Video_views.data_playlist.clear();
                            Video_views.data_playlist2.clear();
                            Video_views.data_learn.clear();
                            Video_views.data_playlistfeatured.clear();
                            Video_views.data_learn = hold.learn;


                            Swipe_home.fab.setVisibility(View.GONE);
                            //  Swipe_home.ninos.setVisibility(View.GONE);

                            Swipe_home.apps.setVisibility(View.GONE);
                            Swipe_home.book_lay.setVisibility(View.GONE);

                            FragmentTransaction transaction = ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                            transaction.replace(R.id.dfrag, new Video_views());
                            transaction.commit();

                        }
                        else{
                            method.alertBox("Please Finish Your Educational Goals",3);
                        }
                    }

                    else{

                        if(new_home_screen.task_exist.equalsIgnoreCase("none")) {
                            Parent.is_task_video = "no";
                        }
                        else{
                            Parent.is_task_video = "yes";
                        }


                        if(new_home_screen.task_exist.equalsIgnoreCase("video") ||
                                new_home_screen.task_exist.equalsIgnoreCase("both") ||
                                new_home_screen.task_exist.equalsIgnoreCase("none")) {


                            Video_views.Category = catname;

                            Video_views.data_each.clear();
                            Video_views.data_discover.clear();
                            Video_views.data_playlist.clear();
                            Video_views.data_playlist2.clear();
                            Video_views.data_learn.clear();
                            Video_views.data_playlistfeatured.clear();
                            Video_views.data_learn = hold.learn;


                            Swipe_home.fab.setVisibility(View.GONE);
                            //  Swipe_home.ninos.setVisibility(View.GONE);

                            Swipe_home.apps.setVisibility(View.GONE);
                            Swipe_home.book_lay.setVisibility(View.GONE);

                            FragmentTransaction transaction = ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                            transaction.replace(R.id.dfrag, new Video_views());
                            transaction.commit();
                        }
                        else if(new_home_screen.task_exist.equalsIgnoreCase("book") &&
                                Parent.educational_goals.get(0).getLearn_first().equalsIgnoreCase("true")){
                            method.alertBox("Please Finish Your Educational Goals",3);
                        }
                        else{


                            Video_views.Category = catname;

                            Video_views.data_each.clear();
                            Video_views.data_discover.clear();
                            Video_views.data_playlist.clear();
                            Video_views.data_playlist2.clear();
                            Video_views.data_learn.clear();
                            Video_views.data_playlistfeatured.clear();
                            Video_views.data_learn = hold.learn;


                            Swipe_home.fab.setVisibility(View.GONE);
                            //  Swipe_home.ninos.setVisibility(View.GONE);

                            Swipe_home.apps.setVisibility(View.GONE);
                            Swipe_home.book_lay.setVisibility(View.GONE);

                            FragmentTransaction transaction = ((FragmentActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                            transaction.replace(R.id.dfrag, new Video_views());
                            transaction.commit();
                        }

                    }




                }
            });



            hold.movieAdapter = new MoviesAdapter(mContext, hold.learn, catname, frag);
            // RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(getActivity(), 4);
            //  recyclerViewPopular.setLayoutManager(layoutManager2);
            rec.setAdapter(hold.movieAdapter);



        }

        //  Toast.makeText(mContext,"view holder", Toast.LENGTH_LONG).show();

    }


}