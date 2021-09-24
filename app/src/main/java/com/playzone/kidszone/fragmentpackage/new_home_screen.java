package com.playzone.kidszone.fragmentpackage;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.muddzdev.styleabletoast.StyleableToast;
import com.playzone.kidszone.API;
import com.playzone.kidszone.Method;
import com.playzone.kidszone.Parent;
import com.playzone.kidszone.R;
import com.playzone.kidszone.Swipe_home;
import com.playzone.kidszone.adaptors.AppsAdapter;
import com.playzone.kidszone.adaptors.BooksAdapter;
import com.playzone.kidszone.adaptors.Game2Adapter;
import com.playzone.kidszone.adaptors.RecentAdapter;
import com.playzone.kidszone.adaptors.SearchAdapter;
import com.playzone.kidszone.adaptors.VideoCatAdapter;
import com.playzone.kidszone.adaptors.VideoCatTestAdapter;
import com.playzone.kidszone.adaptors.VideoSeriesAdapter;
import com.playzone.kidszone.models.AppListModel;
import com.playzone.kidszone.models.SubCategoryList;
import com.playzone.kidszone.models.active_user_model;
import com.playzone.kidszone.models.categories_model;
import com.playzone.kidszone.models.game_model;
import com.playzone.kidszone.models.playlist_model;
import com.playzone.kidszone.models.search_model;
import com.playzone.kidszone.models.video_cat_model;
import com.playzone.kidszone.models.video_series_model;
import com.playzone.kidszone.service.TSLsocketfactory;
import com.playzone.kidszone.service.TimingService;
import com.playzone.kidszone.service.foregroundService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import rjsv.floatingmenu.floatingmenubutton.FloatingMenuButton;
import rjsv.floatingmenu.floatingmenubutton.listeners.FloatingMenuStateChangeListener;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;


public class new_home_screen extends Fragment implements SearchView.OnQueryTextListener, FloatingMenuStateChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    CircleImageView img;
 public  static View parentview;
    String data = null;
    int Tag;
    FloatingMenuButton floatingButton;
    RecyclerView recyclerViewApp;
    RecyclerView recyclerViewRecent;
    RecyclerView recyclerViewBooks;
    RecyclerView recyclerViewTv;
    public static RecyclerView recyclerViewVideo;
    RecyclerView recyclerViewVideoStories;
    RecyclerView recyclerViewGames;
    RecyclerView recyclerViewDC;
    AppsAdapter mAdapter2;
    RecentAdapter mAdapterR;
    BooksAdapter mAdapterB;
    VideoCatAdapter mAdapterVC;
    VideoSeriesAdapter mAdapterDC;
    VideoSeriesAdapter mAdapterVStory;
    VideoSeriesAdapter mAdapterTV;
    Game2Adapter mAdapterGD;
    ImageView exit, pc, battery, wifi, profile;
    ImageView apps, games, tv, video_stories, video_series, books, dc;
    TextView username, timer, date;
    public static List<SubCategoryList> latestList2;
    // public Dialog dialog;

    static public Method method;
    public static SearchView sea;
    BroadcastReceiver batterystatus;
public ImageView time_left;
public RelativeLayout timed_zone;
    WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;

    IntentFilter filter = new IntentFilter("android.intent.action.ACTION_POWER_CONNECTED");
    IntentFilter filter2 = new IntentFilter("android.intent.action.ACTION_POWER_DISCONNECTED");
    IntentFilter filter3 = new IntentFilter("android.intent.action.BATTERY_OKAY");
    IntentFilter filter4 = new IntentFilter("android.intent.action.BATTERY_LOW");
    public int bgnight_counter = 0;

    public static InputMethodManager imm;

    public static int total_task_time=0;
    public static int book_task_time=0;
    public static int video_task_time=0;
    public static int video_task_index=0;
    public static int book_task_index=0;
    public static int child_index=0;
    public static String task_exist="none";
    public static String content_restriction="null";


    public void showSystemUI() {
        if (!Parent.full_screen) {
            View v = getActivity().getWindow().getDecorView();
            v.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }


    private void hideSystemUI() {
        if (Parent.full_screen) {

            //  getWindow().addFlags(WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY);
            // Enables regular immersive mode.
            // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
            // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            View v = getActivity().getWindow().getDecorView();
            v.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE
                            // Set the content to appear under the system bars so that the
                            // content doesn't resize when the system bars hide and show.
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            // Hide the nav bar and status bar
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN);

        }
    }


    void calc() {
        try {
            String pattern = "HH:mm";
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            String currentTime = new SimpleDateFormat("HH:mm").format(new Date());

            Date dateStart = sdf.parse("17:00");

            Date curent = sdf.parse(currentTime);

            if (dateStart.compareTo(curent) <= 0) {

//cord.setBackground(getResources().getDrawable(R.drawable.night_background));
                if (bgnight_counter == 3) {
                    bgnight_counter = 0;
                }
                try {
                    InputStream in = getActivity().getContentResolver().openInputStream(Parent.bgnight.get(bgnight_counter));
                    Drawable draw = Drawable.createFromStream(in, Parent.bgnight.get(bgnight_counter).toString());
                    Swipe_home.cord.setBackground(draw);
                    bgnight_counter++;
                } catch (Exception f) {

                }
            } else {
                Swipe_home.cord.setBackground(getActivity().getResources().getDrawable(R.drawable.fresh2));
            }

        } catch (Exception f) {

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onResume() {
        super.onResume();



        if (Parent.dialog != null) {
            Parent.dialog.dismiss();


        }


      //  Parent.games = Parent.dbhelper.getGames();

        Parent.access_type_per_acc.clear();
        Parent.access_type_per_acc = Parent.dbhelper.getAccessType();

        Parent.active_user_details = Parent.dbhelper.getActiveUser();

        Parent.access_type = Parent.active_user_details.get(0).getTimed();


        Parent.kid_id = Parent.access_type_per_acc.get(0).getKid_id();

        Parent.access_type = Parent.access_type_per_acc.get(0).getAccess_type();



        if (method.isNetworkAvailable()) {


            new Thread(new Runnable() {
                @Override
                public void run() {
                    // showCustomDialog();

                    if (getActivity() != null) {


                           load_offline();

                           load_new_data();



                        // load_game();
                        // load_vid_cat();
                        // video_stories();
                        // tv_series();
                        // dc_comics();

                    }

                }
            }).start();


        }
        else{
            load_offline();
        }

      //  if (Parent.selectedApp_2.isEmpty()) {
            //  Parent.selectedApp_2.clear();

            Parent.selectedApp_2 = Parent.dbhelper.getApplist(Parent.kid_id);
       // }

        //reload user profile
        setUserProfile(profile, username);
        //   }


        //reload user profile
        if (Parent.access_type.equalsIgnoreCase("timed")) {
           // System.out.println("------HAS COUNTDOWNSTARTED-------" + Parent.hasCountdown_started);


            if (!Parent.hasCountdown_started) {

                if(Parent.data_clear_by_android.equalsIgnoreCase("false")) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    getActivity().startForegroundService(new Intent(getActivity(), TimingService.class)
                            .putExtra("type", "second")
                    );

                } else {
                    getActivity().startService(new Intent(getActivity(), TimingService.class)
                            .putExtra("type", "second"));
                }


                   // schedule_AlarmManager();
                   // timer();
                }
                else{
                    getActivity().finish();
                }
            }
        } else {

            time_left.setVisibility(View.INVISIBLE);

            StyleableToast.makeText(getActivity(), "You Have Unlimited Access for today",
                    Toast.LENGTH_LONG, R.style.mytoast).show();
        }


        Swipe_home swipe = new Swipe_home();
        if (swipe.timer != null) {
            swipe.timer.cancel();

        }
        swipe.count_down();


        new Thread(new Runnable() {
            @Override
            public void run() {

                checkAppAvailability();
            }
        }).start();
        //calc();



        educationa_goals();
    }

    @Override
    public void onStart() {
        super.onStart();
        requireActivity().registerReceiver(batterystatus, filter);
        requireActivity().registerReceiver(batterystatus, filter2);
        requireActivity().registerReceiver(batterystatus, filter3);
        requireActivity().registerReceiver(batterystatus, filter4);
    }

    @Override
    public void onStop() {
        super.onStop();
        requireActivity().unregisterReceiver(batterystatus);
        //countertimer.cancel();
    }

    @Override
    public void onPause() {
        super.onPause();

    }


    public new_home_screen() {
        // Required empty public constructor
    }

    public void setTag(int id) {
        this.Tag = id;
    }

    public int gettag() {

        return this.Tag;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // Parent.educational_goals.clear();
        Parent.educational_goals=Parent.dbhelper.getGoals_byId(Parent.kid_id);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (Swipe_home.isBig) {
            parentview = inflater.inflate(R.layout.activity_new_home_screen_big, container, false);
        } else {
            parentview = inflater.inflate(R.layout.activity_new_home_screen, container, false);

        }
        Parent.task_id = getActivity().getTaskId();
        // setfullscreen();

        Swipe_home.fab.setVisibility(View.VISIBLE);
        Swipe_home.apps.setVisibility(View.VISIBLE);
        Swipe_home.book_lay.setVisibility(View.VISIBLE);


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(getActivity());
        mLayoutManager2.setOrientation(RecyclerView.HORIZONTAL);

        LinearLayoutManager mLayoutManager3 = new LinearLayoutManager(getActivity());
        mLayoutManager3.setOrientation(RecyclerView.HORIZONTAL);

        LinearLayoutManager mLayoutManager4 = new LinearLayoutManager(getActivity());
        mLayoutManager4.setOrientation(RecyclerView.VERTICAL);

        LinearLayoutManager mLayoutManager5 = new LinearLayoutManager(getActivity());
        mLayoutManager5.setOrientation(RecyclerView.HORIZONTAL);

        LinearLayoutManager mLayoutManager6 = new LinearLayoutManager(getActivity());
        mLayoutManager6.setOrientation(RecyclerView.HORIZONTAL);

        LinearLayoutManager mLayoutManager7 = new LinearLayoutManager(getActivity());
        mLayoutManager7.setOrientation(RecyclerView.HORIZONTAL);

        LinearLayoutManager mLayoutManager8 = new LinearLayoutManager(getActivity());
        mLayoutManager8.setOrientation(RecyclerView.HORIZONTAL);




        recyclerViewVideo = (RecyclerView) parentview.findViewById(R.id.vid_series);

        img = (CircleImageView) parentview.findViewById(R.id.search);
        time_left = (ImageView) parentview.findViewById(R.id.time_left);
        timed_zone = (RelativeLayout) parentview.findViewById(R.id.prof);

        apps = (ImageView) parentview.findViewById(R.id.app);

        sea = (SearchView) parentview.findViewById(R.id.searchbar);
        sea.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

        sea.setOnQueryTextListener(this);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sea.getVisibility() == View.VISIBLE) {
                    sea.setVisibility(View.GONE);


                    recyclerViewVideo.setLayoutManager(mLayoutManager4);

                   load_offline();
                } else {
                    sea.setVisibility(View.VISIBLE);

                }
            }
        });



        timed_zone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeLeft();
            }
        });


        recyclerViewVideo.setLayoutManager(mLayoutManager4);
        recyclerViewVideo.setFocusable(true);







        pc = (ImageView) parentview.findViewById(R.id.pc);
        exit = (ImageView) parentview.findViewById(R.id.exit);
        wifi = (ImageView) parentview.findViewById(R.id.wifi);
        profile = (ImageView) parentview.findViewById(R.id.profile_image);
        battery = (ImageView) parentview.findViewById(R.id.battery);
        username = (TextView) parentview.findViewById(R.id.username);
        timer = (TextView) parentview.findViewById(R.id.timer);
        // date=(TextView)parentview.findViewById(R.id.date);

        method = new Method(getActivity());



        setBatteryandWifi(battery, wifi);

        setUserProfile(profile, username);

        batterystatus = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                //  Toast.makeText(getActivity(), "Am Called = " + intent.getAction(), Toast.LENGTH_LONG).show();

                if (intent.getAction().equals("android.intent.action.ACTION_POWER_CONNECTED")) {

                    getBatteryStatus();
                    if (Swipe_home.isFull && Swipe_home.isCharging) {
                        Swipe_home.isCharging = false;
                    }

                    if (Swipe_home.isFull) {
                        battery.setImageDrawable(getResources().getDrawable(R.drawable.bat_full));

                    } else if (Swipe_home.isCharging) {
                        battery.setImageDrawable(getResources().getDrawable(R.drawable.bat_charging));
                    }

                } else if (intent.getAction().equals("android.intent.action.ACTION_POWER_DISCONNECTED")) {
                    setBatteryandWifi(battery, wifi);
                } else if (intent.getAction().equals("android.intent.action.BATTERY_OKAY")) {
                    setBatteryandWifi(battery, wifi);
                } else if (intent.getAction().equals("android.intent.action.BATTERY_LOW")) {
                    setBatteryandWifi(battery, wifi);
                }
            }
        };







        return parentview;
    }


    private void setUserProfile(ImageView image, TextView user_name) {

        for (int m = 0; m <= Parent.childModelList.size() - 1; m++) {
            if (Parent.childModelList.get(m).getKid_id().equalsIgnoreCase(Parent.kid_id)) {
                // image.setImageURI(Parent.childModelList.get(m).getIcon());
                child_index=m;

                image.setImageURI(Uri.parse(new File(Parent.childModelList.get(m).getIcon()).toString()));

                if (Parent.childModelList.get(m).getIcon().equals("")) {
                    image.setImageURI(Parent.childModelList.get(m).geticon2());
                } else {
                    image.setImageURI(Uri.parse(new File(Parent.childModelList.get(m).getIcon()).toString()));
                }

                user_name.setText(Parent.childModelList.get(m).getName());
                Parent.kid_name = Parent.childModelList.get(m).getName();

                content_restriction = Parent.childModelList.get(m).getContent_Restriction();
            }
        }

        Parent.active_user_details.add(new active_user_model(Parent.kid_id, Parent.access_type, "true"));

        Parent.dbhelper.addActiveUser(new active_user_model(Parent.kid_id, Parent.access_type, "true"));
    }


    void schedule_AlarmManager() {
        long minute = (Parent.Remaining_hour / 1000) / 60;


        if (minute > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Intent intent = new Intent(getActivity(), Swipe_home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(
                        getActivity(), 234567, intent, 0);

                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);


                //EVERY ONE MINUTE

                alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        SystemClock.elapsedRealtime() + minute * 60000
                        ,
                        pendingIntent);


                // StyleableToast.makeText(getActivity(), "You Have " + minute + " Minutes ",
                //        Toast.LENGTH_LONG, R.style.mytoast2).show();
            }
        }

    }

    public void timer() {

        Parent.time = Parent.Remaining_hour;// minus half minute counttimervalue * 60000;

        if (Parent.time > 0) {
            StyleableToast.makeText(getActivity(), "You Have " + (Parent.time / 1000) / 60 + " Minutes ",
                    Toast.LENGTH_LONG, R.style.mytoast).show();
        } else {
            StyleableToast.makeText(getActivity(), "You Have " + 0 + " Minutes ",
                    Toast.LENGTH_LONG, R.style.mytoast).show();
        }

        Parent.hasCountdown_started = true;

        System.out.println("------TIMER STARTED SUCCESSFULLY------- " + (Parent.time / 1000) / 60 + " Minutes");


        Parent.countertimer = new CountDownTimer(Parent.time, 1000) {

            public void onTick(long millisUntilFinished) {

                timer.setText("" + (millisUntilFinished / 1000));
                timer.setVisibility(View.GONE);

                if ((millisUntilFinished / 1000) <= ((Parent.time / 1000) / 2)) {

                    //changing the text color if half time or less the 5 sec
                    if ((millisUntilFinished / 1000) <= ((Parent.time / 1000) / 9)) {
                        timer.setTextColor(Color.RED);
                        timer.setVisibility(View.VISIBLE);


                        System.out.println("------TIMER IS ALMOST UP-------");



                      /*  new StyleableToast
                                .Builder(getActivity())
                                .text("Your Time Is Almost Up")
                                .textColor(Color.WHITE)
                                .backgroundColor(Color.RED)
                                .show();
*/

                    } else {

                        timer.setTextColor(Color.parseColor("#FFBF00"));
                        timer.setVisibility(View.VISIBLE);


                    }
                }
            }

            public void onFinish() {


                int reward = Integer.parseInt( Parent.dbhelper.getReward_byId(Parent.kid_id) );
                //Integer.parseInt(Parent.childModelList.get(child_index).getReward_earned());
              //  System.out.println("------REWARD =------- " + reward);

                if (reward > 0) {

                  //  timer.setText("reward = " + reward);
                    Parent.time = 0;
                    Parent.Remaining_hour = 0;
                    Parent.hasCountdown_started = false;

                    System.out.println("------TIMES UP2-------");

                    Earned_timer(reward);
                }
                else {


                    timer.setText("Time Up");
                    Parent.time = 0;
                    Parent.Remaining_hour = 0;
                    Parent.hasCountdown_started = false;

                    System.out.println("------TIMES UP-------");


                    try {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            getActivity().startForegroundService(new Intent(getActivity().getApplicationContext(),
                                    foregroundService.class));
                        } else {
                            getActivity().startService(new Intent(getActivity().getApplicationContext(),
                                    foregroundService.class));
                        }
                    } catch (Exception d) {
                        // Toast.makeText(getContext(),"exception= " + d.toString(), Toast.LENGTH_SHORT).show();
                        System.out.println("------exception = " + d.toString());
                    }


                    bringback();

                }

            }
        }.start();
    }








    public void Earned_timer(int value) {

        if( Parent.countertimer!=null) {
            Parent.countertimer.cancel();
        }
           // StyleableToast.makeText(getContext(), "You Have " + value + " Minutes ",
            //        Toast.LENGTH_LONG, R.style.mytoast).show();


        Parent.hasCountdown_started = true;

        System.out.println("------Reward TIMER STARTED SUCCESSFULLY------- " + value + " Minutes");


        Parent.countertimer = new CountDownTimer(TimeUnit.MINUTES.toMillis(value), 1000) {

            public void onTick(long millisUntilFinished) {

               // timer.setText("Reward Time : " + (millisUntilFinished / 1000));
               // timer.setVisibility(View.GONE);

   Parent.Reward_time = String.valueOf((millisUntilFinished / 1000)/60)  ;

                Parent.childModelList.get(child_index).setReward_earned(Parent.Reward_time);


            }

            public void onFinish() {
                timer.setText("Time Up");
                Parent.time = 0;
                Parent.Remaining_hour = 0;
                Parent.hasCountdown_started = false;
                Parent.Reward_time ="0";
                System.out.println("------TIMES UP-------");



                Parent.childModelList.get(child_index).setReward_earned("0");

                Parent.dbhelper.UpdateChildReward_eaarned( "0", Parent.kid_id);

                 Parent.dbhelper.editReward("0",Parent.kid_id );


                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        getActivity().startForegroundService(new Intent(getActivity().getApplicationContext(),
                                foregroundService.class));
                    } else {
                        getActivity().startService(new Intent(getActivity().getApplicationContext(),
                                foregroundService.class));
                    }
                } catch (Exception d) {
                    // Toast.makeText(getContext(),"exception= " + d.toString(), Toast.LENGTH_SHORT).show();
                    System.out.println("------exception = " + d.toString());
                }


                bringback2();




            }


        }.start();
    }





    void bringback() {
        //  if(MainActivity.isParent==false) {
        try {

            new Parent().bringback();

            String s=Parent.currentActivity.getClass().getCanonicalName().toString();
            String[] parts = s.split("\\."); // escape .
            String part1 = parts[0];
            String part2 = parts[1];
            String part3 = parts[2];

           // method.alertBox(Parent.currentActivity.getClass().getCanonicalName().toString(), 2);

            if(part3.equals("Parent_games"))
            {
              //  method.alertBox(part3, 2);

                Parent.currentActivity.finish();
               // getActivity().fi
            }
            else if(part3.equals("Swipe_home")) {

                Fragment f = Parent.currentActivity.getSupportFragmentManager().findFragmentById(R.id.dfrag);


                if (f instanceof new_home_screen) {

                } else {
                    FragmentTransaction transaction = Parent.currentActivity.getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                    transaction.replace(R.id.dfrag, new new_home_screen());
                    // transaction.addToBackStack(null);
                    transaction.commit();
                }

            }
            else{
               if( Parent.player_activity != null) {
                   Parent.player_activity.finish();

                   FragmentTransaction transaction = Parent.currentActivity.getSupportFragmentManager().beginTransaction();
                   transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                   transaction.replace(R.id.dfrag, new new_home_screen());
                   // transaction.addToBackStack(null);
                   transaction.commit();
               }
            }




            Logout();

        } catch (Exception fg) {

            // Toast.makeText(getContext(),"exception= " + fg.toString(), Toast.LENGTH_SHORT).show();
            System.out.println("------exception = " + fg.toString());
        }
        // }

          /*
            ActivityManager acti = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                acti = (ActivityManager) Objects.requireNonNull(new Parent().con.getSystemService(Context.ACTIVITY_SERVICE));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      acti.moveTaskToFront(Parent.task_id, ActivityManager.MOVE_TASK_NO_USER_ACTION);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                ((Activity)new Parent().getApplicationContext()).getWindow().closeAllPanels();
            }
            // Toast.makeText(MainActivity.this, getTaskId() + " ", Toast.LENGTH_SHORT).show();

*/
        // }
    }


 void bringback2() {
        //  if(MainActivity.isParent==false) {
        try {

            new Parent().bringback();

            String s=Parent.currentActivity.getClass().getCanonicalName().toString();
            String[] parts = s.split("\\."); // escape .
            String part1 = parts[0];
            String part2 = parts[1];
            String part3 = parts[2];

           // method.alertBox(Parent.currentActivity.getClass().getCanonicalName().toString(), 2);

            if(part3.equals("Parent_games"))
            {
              //  method.alertBox(part3, 2);

                Parent.currentActivity.finish();
               // getActivity().fi
            }
            else if(part3.equals("Swipe_home")) {

                Fragment f = Parent.currentActivity.getSupportFragmentManager().findFragmentById(R.id.dfrag);


                if (f instanceof new_home_screen) {

                } else {
                    FragmentTransaction transaction = Parent.currentActivity.getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                    transaction.replace(R.id.dfrag, new new_home_screen());
                    // transaction.addToBackStack(null);
                    transaction.commit();
                }

            }
            else{
               if( Parent.player_activity != null) {
                   Parent.player_activity.finish();

                   FragmentTransaction transaction = Parent.currentActivity.getSupportFragmentManager().beginTransaction();
                   transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                   transaction.replace(R.id.dfrag, new new_home_screen());
                   // transaction.addToBackStack(null);
                   transaction.commit();
               }
            }


          /*  if (method.isNetworkAvailable()) {

                SET_USER_DETAILS set = new SET_USER_DETAILS();


                //so we can add reward
                set.updateContentRestriction(Parent.pemail, Parent.kid_id,
                        Parent.childModelList.get(new_home_screen.child_index).getContent_Restriction()
                        , String.valueOf(0),
                        new Fragment(), getActivity());

            }
*/


            Logout();

        } catch (Exception fg) {

            // Toast.makeText(getContext(),"exception= " + fg.toString(), Toast.LENGTH_SHORT).show();
            System.out.println("------exception = " + fg.toString());
        }
        // }

          /*
            ActivityManager acti = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                acti = (ActivityManager) Objects.requireNonNull(new Parent().con.getSystemService(Context.ACTIVITY_SERVICE));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      acti.moveTaskToFront(Parent.task_id, ActivityManager.MOVE_TASK_NO_USER_ACTION);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                ((Activity)new Parent().getApplicationContext()).getWindow().closeAllPanels();
            }
            // Toast.makeText(MainActivity.this, getTaskId() + " ", Toast.LENGTH_SHORT).show();

*/
        // }
    }


    private boolean checkwifiOnandConnected() {
        WifiManager wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        boolean data = false;

        if (wifiManager.isWifiEnabled()) {

            WifiInfo wifiInfo = wifiManager.getConnectionInfo();

            if (wifiInfo != null) {

                if (wifiInfo.getNetworkId() == -1) {
                    data = false; // its on but not connected to an access point
                }
                data = true;// its on and connected
            }
        } else {
            data = false;
        }


        return data;
    }


    public int getBatteryStatus() {
        BatteryManager bm = (BatteryManager) getActivity().getApplicationContext().getSystemService(Context.BATTERY_SERVICE);


        // state
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        Intent battery = getActivity().registerReceiver(null, filter);

        int status = battery.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

        Swipe_home.isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING;

        Swipe_home.isFull = status == BatteryManager.BATTERY_STATUS_FULL;


        // percentage
        int percentage = 0;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            percentage = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

        }

        return percentage;
    }


    public void setBatteryandWifi(ImageView bat, ImageView wifi) {
        int percent = getBatteryStatus();


        if (Swipe_home.isFull && Swipe_home.isCharging) {
            Swipe_home.isCharging = false;
        }


        if (Swipe_home.isFull) {
            bat.setImageDrawable(getResources().getDrawable(R.drawable.bat_full));
        } else if (Swipe_home.isCharging) {
            bat.setImageDrawable(getResources().getDrawable(R.drawable.bat_charging));
        } else {

            if (percent < 100 && percent > 50) {
                bat.setImageDrawable(getResources().getDrawable(R.drawable.bat_medium));

            } else if (percent < 50 && percent > 20) {
                bat.setImageDrawable(getResources().getDrawable(R.drawable.bat_low));

            } else if (percent == 0) {
                bat.setImageDrawable(getResources().getDrawable(R.drawable.bat_empty));

            } else if (percent == 1) {
                bat.setImageDrawable(getResources().getDrawable(R.drawable.bat_one_percent));

            }

        }

        if (checkwifiOnandConnected()) {
            wifi.setImageDrawable(getResources().getDrawable(R.drawable.wifi_available));
        } else {
            wifi.setImageDrawable(getResources().getDrawable(R.drawable.wifi_disconnect));

        }

        Swipe_home.isCharging = false;
        Swipe_home.isFull = false;
    }


    public void Logout() {
        try {
           AlertDialog.Builder alertDialogBuilder;
            if(Parent.currentActivity!=null){
                 alertDialogBuilder = new AlertDialog.Builder(Parent.currentActivity);
            }
            else if(Parent.player_activity!=null){
                 alertDialogBuilder = new AlertDialog.Builder(Parent.player_activity);
            }
            else{
                alertDialogBuilder = new AlertDialog.Builder(getActivity());
            }

            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setIcon(R.drawable.opps);
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            final View dialog = inflater.inflate(R.layout.time_up, null);

            if (Build.VERSION.SDK_INT >= 21) {
                alertDialogBuilder.setView(dialog);
                final AlertDialog alert = alertDialogBuilder.create();

                // ((TextView)dialog.findViewById(R.id.itemname)).setText("No Child Account Yet");

                ((Button) dialog.findViewById(R.id.ve)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        alert.dismiss();

                        Parent.user_never_logout = 0;

                        Parent.hasCountdown_started = false;
                        Parent.countertimer.cancel();

                        getActivity().stopService(new Intent(getActivity(), foregroundService.class));

                        NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
                        mNotificationManager.cancel(78);

                        Parent.active_user_details.clear();
                        Parent.dbhelper.addActiveUser(new active_user_model(Parent.kid_id, Parent.access_type, "false"));

                        Parent.active_user_details.add(new active_user_model(Parent.kid_id, Parent.access_type, "false"));






                        Parent.childModelList.get(child_index).setReward_earned(Parent.Reward_time);

                        Parent.dbhelper.UpdateChildReward_eaarned(Parent.Reward_time, Parent.kid_id);

                        Parent.dbhelper.editReward(Parent.Reward_time, Parent.kid_id);


                        // startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();

                    }
                });


                alert.show();
            } else {
                alertDialogBuilder.setMessage("Time is up");
                //  alertDialogBuilder.setTitle("Empty Account");


                alertDialogBuilder.setNegativeButton("Yea Ok Mum", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                        Parent.hasCountdown_started = false;
                        Parent.countertimer.cancel();
                        Parent.user_never_logout = 0;

                        Parent.dialog.dismiss();

                        getActivity().stopService(new Intent(getActivity(), foregroundService.class));

                        NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
                        mNotificationManager.cancel(78);

                        Parent.active_user_details.clear();
                        Parent.dbhelper.addActiveUser(new active_user_model(Parent.kid_id, Parent.access_type, "false"));

                        Parent.active_user_details.add(new active_user_model(Parent.kid_id, Parent.access_type, "false"));



                        // startActivity(new Intent(getActivity(), MainActivity.class));
                        Parent.settingsList.get(0).setPname(Parent.parentModelList.get(0).getPname());
                        Parent.settingsList.get(0).setPemail(Parent.parentModelList.get(0).getP_email());


                        Parent.childModelList.get(child_index).setReward_earned(Parent.Reward_time);

                        Parent.dbhelper.UpdateChildReward_eaarned(Parent.Reward_time, Parent.kid_id);
                        getActivity().finish();

                    }
                });
                alertDialogBuilder.create().show();
            }

        } catch (Exception e) {
        }
    }


    public List<AppListModel> checkAppAvailability() {
        try {

            if (Parent.pm == null) {
                Parent.pm = requireActivity().getPackageManager();
            }

            List<AppListModel> apps = new ArrayList<AppListModel>();
            Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
            intent.addCategory("android.intent.category.LAUNCHER");
            int count = 0;


            for (ResolveInfo ri : Parent.pm.queryIntentActivities(intent, 0)) {

                ActivityManager.RunningTaskInfo run;

                String appName = ri.loadLabel(Parent.pm).toString();
                Drawable icon = ri.activityInfo.loadIcon(Parent.pm);
                String packages = ri.activityInfo.packageName;
                boolean checked = false;


                for (int d = 0; d <= Parent.selectedApp_2.size() - 1; d++) {

                    if (Parent.selectedApp_2.get(d).getPackages().equals(packages)) {

                        apps.add(new AppListModel(appName, icon, packages, true, Parent.kid_id));

                        break;
                    }

                }


                count++;

            }


            Parent.selectedApp_3.clear();

            Parent.selectedApp_3.addAll(0, Parent.inbuildApp);
            Parent.selectedApp_3.addAll(apps);
            // mAdapter2.notifyDataSetChanged();

            Handler handle = new Handler(Looper.getMainLooper());
            handle.post(new Runnable() {
                @Override
                public void run() {
                    //  recyclerView.getRecycledViewPool().clear();

                  //  Fragment f = Parent.currentActivity.getSupportFragmentManager().findFragmentById(R.id.dfrag);

                  //  if (f instanceof new_home_screen) {

                        // recyclerView.removeAllViews();
                        Parent.selectedApp_2.clear();

                        Parent.selectedApp_2.addAll(Parent.selectedApp_3);

                        //mAdapter2 = new AppsAdapter(getActivity(), Parent.selectedApp_2, new_home_screen.this);
                        //recyclerViewApp.setAdapter(mAdapter2);
                        // recyclerView.swapAdapter(mAdapter2, true);
                        // mAdapter2.notifyDataSetChanged();
                        //Parent.dialog.dismiss();


                        //  mAdapterGD = new Game_displayAdapter(getActivity(), Parent.selectedApp_2, new_home_screen.this);
                        //  recyclerViewGames.setAdapter(mAdapterGD);


                        //  mAdapterR = new RecentAdapter(getActivity(), Parent.selectedApp_2, new_home_screen.this);
                        //  recyclerViewRecent.setAdapter(mAdapterR);

                        //  mAdapterVS = new VideoSeriesAdapter(getActivity(), Parent.selectedApp_2, new_home_screen.this);
                        // recyclerViewVideo.setAdapter(mAdapterVS);

                        //  mAdapterB = new BooksAdapter(getActivity(), Parent.selectedApp_2, new_home_screen.this);
                        // recyclerViewBooks.setAdapter(mAdapterB);


                //    }

                }
            });
        } catch (Exception d) {
            System.out.println(d.getMessage());
        }


        return Parent.selectedApp_3;
    }


    /* access modifiers changed from: protected */
    public void showCustomDialog() {
        Parent.dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent);
        Parent.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Parent.dialog.setCancelable(true);
        Parent.dialog.setContentView(R.layout.layout_dialog2);
        ((ImageView) Parent.dialog.findViewById(R.id.loader)).startAnimation(AnimationUtils.loadAnimation(getActivity(),
                R.anim.rotate));
        Parent.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x7f000000));
        Parent.dialog.show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
      //  video_search
      //  booklist_search
        //recyclerViewVideo
       imm.hideSoftInputFromWindow(parentview.getRootView().getWindowToken(), 0);

        if(!query.isEmpty()){

            run_down(query);


        }

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText.isEmpty()){

            LinearLayoutManager mLayoutManager4 = new LinearLayoutManager(getActivity());
            mLayoutManager4.setOrientation(RecyclerView.VERTICAL);
            recyclerViewVideo.setLayoutManager(mLayoutManager4);


            VideoCatTestAdapter mAdapterVC2 = new VideoCatTestAdapter(getActivity(), Parent.category, "cat", new_home_screen.this);
            recyclerViewVideo.setAdapter(mAdapterVC2);
        }


        return true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imm = (InputMethodManager)  getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        // (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    }
    @Override
    public void onMenuOpened(FloatingMenuButton floatingMenuButton) {

    }

    @Override
    public void onMenuClosed(FloatingMenuButton floatingMenuButton) {

    }



    public void load_offline(){


    if (getActivity() != null) {
        Fragment f = Parent.currentActivity.getSupportFragmentManager().findFragmentById(R.id.dfrag);

        if (f instanceof new_home_screen) {


            //  Log.d("Response", new String(responseBody));
            String res = new String(readfile(getActivity(), "offline"));


            //System.out.println(readfile(getActivity(), "offline"));

            try {
                //  method.alertBox(res);
                JSONObject jsonObject = new JSONObject(res);

                Parent.jsonObjectUltimate = null;
                Parent.jsonArrayUltimate = null;
                // JSONObject jsonObjectBook = jsonObject.getJSONObject("EBOOK_APP");
                JSONObject jsonObjectBook = jsonObject.getJSONObject("EBOOK_APP");
                Parent.jsonObjectUltimate = jsonObject.getJSONObject("EBOOK_APP");
                Parent.category.clear();

                //  for(int d= 0; d<jsonObjectBook.length(); d++){
                //  JSONArray jsonArrayPopular = jsonObject.getJSONArray( "EBOOK_APP");
                // String key = it.;

                if (Parent.settingsList.get(0).getContent_Restriction() == null) {
                    JSONArray jsonArrayPopular2 = jsonObjectBook.names();
                    Parent.jsonArrayUltimate = jsonObjectBook.names();


                    for (int g = 0; g < jsonArrayPopular2.length(); g++) {
                        // System.out.println(jsonArrayPopular.toString());
                        String name = jsonArrayPopular2.get(g).toString();

                        if (name.contains("17")) {
                            Parent.category.add(new categories_model(name));
                        }

                    }
                } else {
                    int age = Integer.parseInt(content_restriction);

                    JSONArray jsonArrayPopular2 = jsonObjectBook.names();
                    Parent.jsonArrayUltimate = jsonObjectBook.names();

                    if (age >= 16) {
                        for (int g = 0; g < jsonArrayPopular2.length(); g++) {
                            // System.out.println(jsonArrayPopular.toString());
                            String name = jsonArrayPopular2.get(g).toString();
                            if (name.contains("17")) {
                                Parent.category.add(new categories_model(name));
                            }
                        }
                    } else if (age >= 10 && age < 16) {
                        for (int g = 0; g < jsonArrayPopular2.length(); g++) {
                            // System.out.println(jsonArrayPopular.toString());
                            String name = jsonArrayPopular2.get(g).toString();

                            if (name.contains("15")) {
                                Parent.category.add(new categories_model(name));
                            }
                        }
                    } else if (age > 5 && age < 11) {


                        for (int g = 0; g < jsonArrayPopular2.length(); g++) {
                            // System.out.println(jsonArrayPopular.toString());
                            String name = jsonArrayPopular2.get(g).toString();

                            if (name.contains("11")) {
                                Parent.category.add(new categories_model(name));
                            }
                        }
                    } else if (age >= 0 && age < 6) {


                        for (int g = 0; g < jsonArrayPopular2.length(); g++) {
                            // System.out.println(jsonArrayPopular.toString());
                            String name = jsonArrayPopular2.get(g).toString();

                            if (name.contains("6")) {
                                Parent.category.add(new categories_model(name));
                            }
                        }
                    }
                    //   }

                }
                Handler handle = new Handler(Looper.getMainLooper());
                handle.post(new Runnable() {
                    @Override
                    public void run() {
                        VideoCatTestAdapter mAdapterVC2 = new VideoCatTestAdapter(getActivity(), Parent.category, "cat", new_home_screen.this);
                        // RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(getActivity(), 4);
                        //  recyclerViewPopular.setLayoutManager(layoutManager2);

                        recyclerViewVideo.swapAdapter(mAdapterVC2, true);
                                //setAdapter(mAdapterVC2);

                    }
                });
                //  mWaveSwipeRefreshLayout.setRefreshing(false);


            } catch (JSONException e) {
                e.printStackTrace();
                //  progressBar.setVisibility(View.GONE);
                //  method.alertBox("hi");

                // mWaveSwipeRefreshLayout.setRefreshing(false);
            }

        }

}
        }







    public void load_game() {


        // progressDialog.setMessage("Loading Yummy Games");
        // progressDialog.setCancelable(true);
        // progressDialog.show();

        final JsonObject jsObj = (JsonObject) new Gson().toJsonTree(new API(new_home_screen.this));


        jsObj.addProperty("method_name", "load_games");
        jsObj.addProperty("package_name", API.package_name);
        jsObj.addProperty("sign", API.sign);
        jsObj.addProperty("salt", API.salt);

        RequestQueue mRequestQueue;


        // Instantiate the cache
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        TSLsocketfactory tsl = null;


        try {
            tsl = new TSLsocketfactory();
        } catch (Exception r) {

        }
        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack(null, tsl));

// Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

// Start the queue
        mRequestQueue.start();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Parent.url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String responseBody) {


                        Log.d("Response", new String(responseBody));
                        String res = new String(responseBody);

                        try {
                            JSONObject jsonObject = new JSONObject(res);

                            JSONArray jsonArray = jsonObject.getJSONArray("GAMES");
                            Parent.games2.clear();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                game_model game = new game_model();

                                JSONObject object = jsonArray.getJSONObject(i);

                                game.setName(object.getString("name"));
                                game.setUrl(object.getString("url"));
                                game.setBanner(object.getString("banner"));
                                game.setLogo(object.getString("logo"));
                                game.setDescription(object.getString("description"));
                                game.setCategory(object.getString("category"));


                                Parent.games2.add(game);

                            }

                            if (!Parent.games2.isEmpty()) {

                                Handler handle = new Handler(Looper.getMainLooper());
                                handle.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Parent.games.clear();
                                        Parent.games.addAll(Parent.games2);

                                        mAdapterGD = new Game2Adapter(getActivity(), Parent.games, new_home_screen.this);
                                        recyclerViewGames.setAdapter(mAdapterGD);


                                        Parent.dbhelper.addGAME(Parent.games);

                                    }
                                });

                            } else {
                                Handler handle = new Handler(Looper.getMainLooper());
                                handle.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        mAdapterGD = new Game2Adapter(getActivity(), Parent.games, new_home_screen.this);
                                        recyclerViewGames.setAdapter(mAdapterGD);

                                    }
                                });

                            }

                            //  progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();

                            //  progressDialog.dismiss();
                            //   method.alertBox(responseBody, 1);
                            //getResources().getString(R.string.contact_msg));
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                try {

                    // progressDialog.dismiss();

                    // method.alertBox("Failed, please retry", 1);
                } catch (Exception f) {

                }

            }
        }) {

            @Override
            protected Map<String, String> getParams() {


                Map<String, String> params = new HashMap<String, String>();

                params.put("data", API.toBase64(jsObj.toString()));


                return params;
            }


        };

//Adding request to request queue
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(stringRequest);


    }


    public void load_vid_cat() {

        // progressBar.setVisibility(View.VISIBLE);


        JsonObject jsObj = (JsonObject) new Gson().toJsonTree(new API(new_home_screen.this));
        jsObj.addProperty("method_name", "get_cat");
        //  jsObj.addProperty("cat_id", method.pref.getString("School_id", null));
        jsObj.addProperty("package_name", API.package_name);
        jsObj.addProperty("sign", API.sign);
        jsObj.addProperty("salt", API.salt);

        RequestQueue mRequestQueue;
        // Instantiate the cache
        Cache cache = new DiskBasedCache(requireActivity().getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

// Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

// Start the queue
        mRequestQueue.start();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://testzone.ng/kidszone/access/api_video.php",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String responseBody) {

                        Log.d("Response", new String(responseBody));
                        String res = new String(responseBody);

                        try {
                            //  method.alertBox(res);
                            JSONObject jsonObject = new JSONObject(res);


                            // JSONObject jsonObjectBook = jsonObject.getJSONObject("EBOOK_APP");
                            JSONObject jsonObjectBook = jsonObject.getJSONObject("EBOOK_APP");
                            //  Parent.jsonObjectUltimate = jsonObject.getJSONObject("EBOOK_APP");
                            Parent.vid_cat.clear();

                            //  for(int d= 0; d<jsonObjectBook.length(); d++){
                            //  JSONArray jsonArrayPopular = jsonObject.getJSONArray( "EBOOK_APP");
                            // String key = it.;


                            JSONArray jsonArrayPopular2 = jsonObjectBook.names();
                            //   Parent.jsonArrayUltimate= jsonObjectBook.names();

                            for (int g = 0; g < jsonArrayPopular2.length(); g++) {
                                // System.out.println(jsonArrayPopular.toString());
                                JSONArray jsonArrayPopular = jsonObjectBook.getJSONArray(jsonArrayPopular2.get(g).toString());

                                for (int a = 0; a < jsonArrayPopular.length(); a++) {
                                    JSONObject object = jsonArrayPopular.getJSONObject(a);

                                    String title = object.getString("title");
                                    String descr = object.getString("descr");
                                    String pix_url = object.getString("pix");
                                    String channame = object.getString("channame");


                                    Parent.vid_cat.add(new video_cat_model(title, descr, pix_url, channame));
                                }
                            }
                            //   }


                            mAdapterVC = new VideoCatAdapter(getActivity(), Parent.vid_cat, "cat", new_home_screen.this);
                            // RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(getActivity(), 4);
                            //  recyclerViewPopular.setLayoutManager(layoutManager2);
                            recyclerViewVideo.setAdapter(mAdapterVC);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            //  progressBar.setVisibility(View.GONE);
                            //  method.alertBox("hi");


                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {

            @Override
            protected Map<String, String> getParams() {


                Map<String, String> params = new HashMap<String, String>();

                params.put("data", API.toBase64(jsObj.toString()));


                return params;
            }


        };

//Adding request to request queue
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(80 * 1000, 3,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(stringRequest);


    }


    public void load_new_data() {

        // progressBar.setVisibility(View.VISIBLE);


        JsonObject jsObj = (JsonObject) new Gson().toJsonTree(new API(new_home_screen.this));
        jsObj.addProperty("method_name", "load_data");
        //  jsObj.addProperty("cat_id", method.pref.getString("School_id", null));
        jsObj.addProperty("package_name", API.package_name);
        jsObj.addProperty("sign", API.sign);
        jsObj.addProperty("salt", API.salt);

        RequestQueue mRequestQueue;
        // Instantiate the cache
        Cache cache = new DiskBasedCache(requireActivity().getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

// Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

// Start the queue
        mRequestQueue.start();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://testzone.ng/kidszone/access/api_video.php",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String responseBody) {

                       // Log.d("Response", new String(responseBody));
                        String res = new String(responseBody);
                        if(getActivity()!=null) {
                            Fragment f = Parent.currentActivity.getSupportFragmentManager().findFragmentById(R.id.dfrag);

                            if (f instanceof new_home_screen) {

                                generateNoteOnSD(getActivity(), "offline.txt", res);

                                // System.out.println(readfile(getActivity(), "offline"));
                                //System.out.println(res);

                                try {
                                    //  method.alertBox(res);
                                    JSONObject jsonObject = new JSONObject(res);

                                    Parent.jsonObjectUltimate = null;
                                    Parent.jsonArrayUltimate = null;
                                    // JSONObject jsonObjectBook = jsonObject.getJSONObject("EBOOK_APP");
                                    JSONObject jsonObjectBook = jsonObject.getJSONObject("EBOOK_APP");
                                    Parent.jsonObjectUltimate = jsonObject.getJSONObject("EBOOK_APP");
                                    Parent.category.clear();

                                    //  for(int d= 0; d<jsonObjectBook.length(); d++){
                                    //  JSONArray jsonArrayPopular = jsonObject.getJSONArray( "EBOOK_APP");
                                    // String key = it.;
if(content_restriction==null){

    JSONArray jsonArrayPopular2 = jsonObjectBook.names();
    Parent.jsonArrayUltimate = jsonObjectBook.names();
    for (int g = 0; g < jsonArrayPopular2.length(); g++) {
    String name = jsonArrayPopular2.get(g).toString();


    if (name.contains("17") ) {
        Parent.category.add(new categories_model(name));
    }
}
}
else {

    int age = Integer.parseInt(content_restriction);

    JSONArray jsonArrayPopular2 = jsonObjectBook.names();
    Parent.jsonArrayUltimate = jsonObjectBook.names();

    if (age >= 16) {
        for (int g = 0; g < jsonArrayPopular2.length(); g++) {
            // System.out.println(jsonArrayPopular.toString());
            String name = jsonArrayPopular2.get(g).toString();

            if (name.contains("17") ) {
                Parent.category.add(new categories_model(name));
            }
        }
    } else if (age >= 10 && age < 16) {
        for (int g = 0; g < jsonArrayPopular2.length(); g++) {
            // System.out.println(jsonArrayPopular.toString());
            String name = jsonArrayPopular2.get(g).toString();

            if (name.contains("15")) {
                Parent.category.add(new categories_model(name));
            }
        }
    } else if (age >= 5 && age < 11) {


        for (int g = 0; g < jsonArrayPopular2.length(); g++) {
            // System.out.println(jsonArrayPopular.toString());
            String name = jsonArrayPopular2.get(g).toString();

            if (name.contains("11")) {
                Parent.category.add(new categories_model(name));
            }
        }
    } else if (age >= 0 && age < 5) {


        for (int g = 0; g < jsonArrayPopular2.length(); g++) {
            // System.out.println(jsonArrayPopular.toString());
            String name = jsonArrayPopular2.get(g).toString();

            if (name.contains("6")) {
                Parent.category.add(new categories_model(name));
            }
        }
    }
}
                                    VideoCatTestAdapter mAdapterVC2 = new VideoCatTestAdapter(getActivity(), Parent.category, "cat", new_home_screen.this);
                                    // RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(getActivity(), 4);
                                    //  recyclerViewPopular.setLayoutManager(layoutManager2);

                                    if(Parent.category==null || Parent.category.isEmpty()) {
                                           recyclerViewVideo.setAdapter(mAdapterVC2);
                                    }
                                    else {
                                        recyclerViewVideo.swapAdapter(mAdapterVC2, false);
                                        //setAdapter(mAdapterVC2);

                                       // mAdapterVC2.notifyDataSetChanged();
                                    }
                                //  mWaveSwipeRefreshLayout.setRefreshing(false);


                            } catch(JSONException e){
                                e.printStackTrace();
                                //  progressBar.setVisibility(View.GONE);
                                // method.alertBox("hi");
                                    System.out.println(e.getMessage());
                                //  mWaveSwipeRefreshLayout.setRefreshing(false);
                               // readfile(getActivity(), "offline");
                                    load_offline();
                            }
                        }
                    }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
             //   mWaveSwipeRefreshLayout.setRefreshing(false);
             //   method.alertBox(error.getMessage(), 1);
                load_offline();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {


                Map<String, String> params = new HashMap<String, String>();

                params.put("data", API.toBase64(jsObj.toString()));


                return params;
            }


        };

//Adding request to request queue
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(40 * 1000, 1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(stringRequest);


    }


    public void video_stories() {

        // progressBar.setVisibility(View.VISIBLE);


        JsonObject jsObj = (JsonObject) new Gson().toJsonTree(new API(new_home_screen.this));
        // jsObj.addProperty("method_name", "get_home");
        //  jsObj.addProperty("cat_id", method.pref.getString("School_id", null));
        jsObj.addProperty("package_name", API.package_name);
        jsObj.addProperty("sign", API.sign);
        jsObj.addProperty("salt", API.salt);

        RequestQueue mRequestQueue;
        // Instantiate the cache
        Cache cache = new DiskBasedCache(requireActivity().getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

// Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

// Start the queue
        mRequestQueue.start();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://testzone.ng/kidszone/access/videos.php",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String responseBody) {

                        Log.d("Response", new String(responseBody));
                        String res = new String(responseBody);

                        try {
                            //  method.alertBox(res);
                            JSONObject jsonObject = new JSONObject(res);

                            JSONObject jsonObjectBook = jsonObject.getJSONObject("EBOOK_APP");

                            JSONArray jsonArrayPopular = jsonObjectBook.getJSONArray("video_stories");
                            Parent.vid_stories.clear();
                            for (int i = 0; i < jsonArrayPopular.length(); i++) {

                                JSONObject object = jsonArrayPopular.getJSONObject(i);
                                String title = object.getString("title");
                                String descr = object.getString("descr");
                                String pix_url = object.getString("pix");
                                String channame = object.getString("channame");
                                String position = object.getString("position");
                                String video_url = object.getString("video_url");

                                Parent.vid_stories.add(new video_series_model(title, descr, pix_url, channame, position, video_url));

                            }


                            mAdapterVStory = new VideoSeriesAdapter(getActivity(), Parent.vid_stories, "stories", new_home_screen.this);
                            // RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(getActivity(), 4);
                            //  recyclerViewPopular.setLayoutManager(layoutManager2);
                            recyclerViewVideoStories.setAdapter(mAdapterVStory);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            //  progressBar.setVisibility(View.GONE);
                            //  method.alertBox("hi");


                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {

            @Override
            protected Map<String, String> getParams() {


                Map<String, String> params = new HashMap<String, String>();

                params.put("data", API.toBase64(jsObj.toString()));


                return params;
            }


        };

//Adding request to request queue
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(80 * 1000, 3,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(stringRequest);


    }


    public void dc_comics() {

        // progressBar.setVisibility(View.VISIBLE);


        JsonObject jsObj = (JsonObject) new Gson().toJsonTree(new API(new_home_screen.this));
        jsObj.addProperty("method_name", "DC COMICS");
        //  jsObj.addProperty("cat_id", method.pref.getString("School_id", null));
        jsObj.addProperty("package_name", API.package_name);
        jsObj.addProperty("sign", API.sign);
        jsObj.addProperty("salt", API.salt);

        RequestQueue mRequestQueue;
        // Instantiate the cache
        Cache cache = new DiskBasedCache(requireActivity().getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

// Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

// Start the queue
        mRequestQueue.start();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://testzone.ng/kidszone/access/api_video.php",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String responseBody) {

                        Log.d("Response", new String(responseBody));
                        String res = new String(responseBody);

                        try {
                            //  method.alertBox(res);
                            JSONObject jsonObject = new JSONObject(res);

                            JSONObject jsonObjectBook = jsonObject.getJSONObject("EBOOK_APP");

                            JSONArray jsonArrayPopular = jsonObjectBook.getJSONArray("video");
                            Parent.dc_comics.clear();
                            for (int i = 0; i < jsonArrayPopular.length(); i++) {

                                JSONObject object = jsonArrayPopular.getJSONObject(i);
                                String title = object.getString("title");
                                String descr = object.getString("descr");
                                String pix_url = object.getString("pix");
                                String channame = object.getString("channame");
                                String position = object.getString("position");
                                String video_url = object.getString("video_url");

                                Parent.dc_comics.add(new video_series_model(title, descr, pix_url, channame, position, video_url));

                            }


                            mAdapterDC = new VideoSeriesAdapter(getActivity(), Parent.dc_comics, "dc", new_home_screen.this);
                            // RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(getActivity(), 4);
                            //  recyclerViewPopular.setLayoutManager(layoutManager2);
                            recyclerViewDC.setAdapter(mAdapterDC);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            //  progressBar.setVisibility(View.GONE);
                            //  method.alertBox("hi");


                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {

            @Override
            protected Map<String, String> getParams() {


                Map<String, String> params = new HashMap<String, String>();

                params.put("data", API.toBase64(jsObj.toString()));


                return params;
            }


        };

//Adding request to request queue
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(80 * 1000, 3,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(stringRequest);


    }


    public void tv_series() {

        // progressBar.setVisibility(View.VISIBLE);


        JsonObject jsObj = (JsonObject) new Gson().toJsonTree(new API(new_home_screen.this));
        // jsObj.addProperty("method_name", "get_home");
        //  jsObj.addProperty("cat_id", method.pref.getString("School_id", null));
        jsObj.addProperty("package_name", API.package_name);
        jsObj.addProperty("sign", API.sign);
        jsObj.addProperty("salt", API.salt);

        RequestQueue mRequestQueue;
        // Instantiate the cache
        Cache cache = new DiskBasedCache(requireActivity().getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

// Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

// Start the queue
        mRequestQueue.start();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://testzone.ng/kidszone/access/tv_series.php",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String responseBody) {

                        Log.d("Response", new String(responseBody));
                        String res = new String(responseBody);

                        try {
                            //  method.alertBox(res);
                            JSONObject jsonObject = new JSONObject(res);

                            JSONObject jsonObjectBook = jsonObject.getJSONObject("EBOOK_APP");

                            JSONArray jsonArrayPopular = jsonObjectBook.getJSONArray("tv_series");
                            Parent.tv_series.clear();
                            for (int i = 0; i < jsonArrayPopular.length(); i++) {

                                JSONObject object = jsonArrayPopular.getJSONObject(i);
                                String title = object.getString("title");
                                String descr = object.getString("descr");
                                String pix_url = object.getString("pix");
                                String channame = object.getString("channame");
                                String position = object.getString("position");
                                String video_url = object.getString("video_url");

                                Parent.tv_series.add(new video_series_model(title, descr, pix_url, channame, position, video_url));

                            }


                            mAdapterTV = new VideoSeriesAdapter(getActivity(), Parent.tv_series, "tv_series", new_home_screen.this);
                            // RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(getActivity(), 4);
                            //  recyclerViewPopular.setLayoutManager(layoutManager2);
                            recyclerViewTv.setAdapter(mAdapterTV);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            //  progressBar.setVisibility(View.GONE);
                            //  method.alertBox("hi");


                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {

            @Override
            protected Map<String, String> getParams() {


                Map<String, String> params = new HashMap<String, String>();

                params.put("data", API.toBase64(jsObj.toString()));


                return params;
            }


        };

//Adding request to request queue
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(80 * 1000, 3,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(stringRequest);


    }


    public void generateNoteOnSD(Context context, String sFileName, String sBody) {
        try {
            // File root = new File(Environment.getExternalStorageDirectory(), sFileName);
           File file = new File(requireActivity().getFilesDir(), "offline");
           // File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/offline/");
            if(!file.exists())
            {
                file.mkdirs();
            }
           // File file1=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/offline/"+sFileName);

            File file1 = new File(requireActivity().getFilesDir(), "offline/"+sFileName);



            FileOutputStream output2 = new FileOutputStream(file1);
           // FileWriter writer = new FileWriter(root);
          //  writer.write(sBody);//append(sBody);
            output2.write(sBody.getBytes());
            output2.flush();
            output2.close();
           // Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readfile(Context con, String filename) {
        String contents="null";
        try {

          //  File file2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/offline/"+
           //         filename + ".txt");
            File file2 = new File(requireActivity().getFilesDir(), "offline/"+filename + ".txt");

            if(file2.exists()) {
                FileInputStream fis = new FileInputStream(file2);
                InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
                StringBuilder stringBuilder = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                    String line = reader.readLine();
                    while (line != null) {
                        stringBuilder.append(line).append('\n');
                        line = reader.readLine();
                    }
                } catch (IOException e) {
                    // Error occurred when opening raw file for reading.
                } finally {
                    contents = stringBuilder.toString();
                    //  System.out.println(contents);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

return contents;
    }





    public void run_down(String Search_key){

        Parent.category_search.clear();
        Parent.search_list.clear();

Parent.category_search.addAll(Parent.category);

        for(int position = 0; position <= (Parent.category.size()-1); position++){

            try{

                String name="";
                name =Parent.category.get(position).getName().replace("8", "");
                name =name.replace("11", "");
                name =name.replace("6", "");
                name =name.replace("15", "");
                name =name.replace("17", "");
                name =name.replace("0", "");

                String cat="";
                cat =Parent.category.get(position).getName().replace("8", "");
                cat =cat.replace("11", "");
                cat =cat.replace("6", "");
                cat =cat.replace("15", "");
                cat =cat.replace("17", "");





                JSONArray jsonArrayPopular = null;
        jsonArrayPopular = Parent.jsonObjectUltimate.getJSONArray(Parent.category.get(position).getName());
        //   }

        //  System.out.println("name =" + name );

        if(name.equalsIgnoreCase("Games")){


            Parent.games2.clear();
            for (int a = 0; a < jsonArrayPopular.length(); a++) {
                JSONObject object = jsonArrayPopular.getJSONObject(a);

                game_model game = new game_model();

                if(object.getString("name").toLowerCase().contains(Search_key.toLowerCase())) {

                    game.setName(object.getString("name"));
                    game.setUrl(object.getString("url"));
                    game.setBanner(object.getString("banner"));
                    game.setLogo(object.getString("logo"));
                    game.setDescription(object.getString("description"));
                    game.setCategory(object.getString("category"));


                    Parent.games2.add(game);
                    Parent.search_list.add(new search_model(object.getString("name"),object.getString("banner")
                    , cat, object.getString("url")));
                }
            }

            if(!Parent.games2.isEmpty()) {

                Handler handle = new Handler(Looper.getMainLooper());
                handle.post(new Runnable() {
                    @Override
                    public void run() {
                        Parent.games.clear();
                        Parent.games.addAll(Parent.games2);

                    }
                });




            }

            else{

                for (int s=0; s<=Parent.category_search.size()-1; s++) {

                    if(Parent.category_search.get(s).getName().equalsIgnoreCase(Parent.category.get(position).getName())){
                        Parent.category_search.remove(Parent.category_search.get(s));
                    }
                }

            }



        }

        else if(name.equalsIgnoreCase("Books")){
            //   HomeFragment home=new HomeFragment();
//home.Home_call(holder.cat_data, mContext, holder.more);
            List<SubCategoryList> data_book = new ArrayList<SubCategoryList>();
            for (int a = 0; a < jsonArrayPopular.length(); a++) {
                JSONObject object = jsonArrayPopular.getJSONObject(a);


                if(object.getString("book_title").toLowerCase().contains(Search_key.toLowerCase())) {


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


                   data_book.add(new SubCategoryList(id, cat_id, book_title, book_description, book_cover_img, book_bg_img, book_file_type, total_rate, rate_avg, book_views, author_id, author_name, ""));

  Parent.search_list.add(new search_model(object.getString("book_title"),object.getString("book_cover_img")
                            , cat, object.getString("id")));
                }
            }


            if(data_book.isEmpty()) {

                for (int s=0; s<=Parent.category_search.size()-1; s++) {

                    if(Parent.category_search.get(s).getName().equalsIgnoreCase(Parent.category.get(position).getName())){
                        Parent.category_search.remove(Parent.category_search.get(s));
                    }
                }
            }

        }

        else if(name.equalsIgnoreCase("DISCOVER")){
            List<video_cat_model> data_discover = new ArrayList<video_cat_model>();
            for (int a = 0; a < jsonArrayPopular.length(); a++) {
                JSONObject object = jsonArrayPopular.getJSONObject(a);
//System.out.println(Search_key + " object==" + object.getString("title"));

                if(object.getString("title").toLowerCase().contains(Search_key.toLowerCase())) {

                    String title = object.getString("title");
                    String descr = object.getString("descr");
                    String pix_url = object.getString("pix");
                    String channame = object.getString("channame");


                   data_discover.add(new video_cat_model(title, descr, pix_url, channame));

    Parent.search_list.add(new search_model(object.getString("title"),object.getString("pix")
                            , cat, object.getString("channame")));
                }
            }

            if(data_discover.isEmpty()) {


                for (int s=0; s<=Parent.category_search.size()-1; s++) {

                    if(Parent.category_search.get(s).getName().equalsIgnoreCase(Parent.category.get(position).getName())){
                        Parent.category_search.remove(Parent.category_search.get(s));
                    }
                }
            }
        }

        else if(Parent.category.get(position).getName().contains("8")){
            List<playlist_model> learn = new ArrayList<playlist_model>();
            for (int a = 0; a < jsonArrayPopular.length(); a++) {
                JSONObject object = jsonArrayPopular.getJSONObject(a);

                if(object.getString("title").toLowerCase().contains(Search_key.toLowerCase())) {

                    String title = object.getString("title");
                    String descr = object.getString("descr");
                    String pix_url = object.getString("pix");
                    String channame = object.getString("channame");
                    String play_id = object.getString("playlist_id");
                    String item_count = object.getString("item_count");

                    if(title.equals("Short Films")){

                    }
                    else {


                       learn.add(new playlist_model(title, descr, pix_url, channame, play_id, item_count));

  Parent.search_list.add(new search_model(object.getString("title"),object.getString("pix")
                                , cat, object.getString("playlist_id")));
                    }
                }
            }


            if(learn.isEmpty()) {

                for (int s=0; s<=Parent.category_search.size()-1; s++) {

                    if(Parent.category_search.get(s).getName().equalsIgnoreCase(Parent.category.get(position).getName())){
                        Parent.category_search.remove(Parent.category_search.get(s));
                    }
                }
            }
        }
        else {
            // Parent.vid_cat.clear();
            List<video_series_model> data_each = new ArrayList<video_series_model>();
            for (int a = 0; a < jsonArrayPopular.length(); a++) {
                JSONObject object = jsonArrayPopular.getJSONObject(a);

                if(object.getString("title").toLowerCase().contains(Search_key.toLowerCase())) {
                    String title = object.getString("title");
                    String descr = object.getString("descr");
                    String pix_url = object.getString("pix");
                    String channame = object.getString("channame");
                    String video_url = object.getString("video_url");


                    data_each.add(new video_series_model(title, descr, pix_url, channame, position + "", video_url));

                    if(cat.contains("0")) {

                        Parent.search_list.add(new search_model(object.getString("title"), object.getString("pix")
                                , "video0", object.getString("video_url")));
                    }
                    else{
                        Parent.search_list.add(new search_model(object.getString("title"), object.getString("pix")
                                , "video", object.getString("video_url")));
                    }
                }
            }

            if(data_each.isEmpty()) {



                for (int s=0; s<=Parent.category_search.size()-1; s++) {

                    if(Parent.category_search.get(s).getName().equalsIgnoreCase(Parent.category.get(position).getName())){
                        Parent.category_search.remove(Parent.category_search.get(s));
                    }
                }          }
        }
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println(e.toString());
    }


}




        recyclerViewVideo.setLayoutManager(new GridLayoutManager(getActivity(), 2));

       // for(int sd=0; sd<=Parent.category_search.size()-1; sd++) {
       //     System.out.println(Parent.category_search.get(sd).getName());
       // }
       // VideoSearchAdapter mAdapter2 = new VideoSearchAdapter(getActivity(), Parent.category_search, Search_key, new_home_screen.this);
SearchAdapter mAdapter2 = new SearchAdapter(getActivity(), Parent.search_list, Search_key, new_home_screen.this);
        recyclerViewVideo.setAdapter(mAdapter2);
}



public void showTimeLeft(){
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
    alertDialogBuilder.setIcon(R.drawable.time);
    String reward = Parent.dbhelper.getReward_byId(Parent.kid_id) ;

    if(Parent.Reward_time.equals("") || Parent.Reward_time.equals("0")){
        reward=reward;
    }
    else{
        reward = Parent.Reward_time;
    }
    if (Parent.access_type.equalsIgnoreCase("timed")) {
        alertDialogBuilder.setMessage("Available Time : " + (Parent.time / 1000) / 60 + " Minutes\n\n" +
                " Available Reward Time :  " +  reward + " Minutes"
);
    }
    else{
        alertDialogBuilder.setMessage("You Have Unlimited Access For Today... Enjoy!");
    }
    alertDialogBuilder.setPositiveButton("Ok",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {

                    arg0.dismiss();

                }
            });


    AlertDialog alertDialog = alertDialogBuilder.create();
    alertDialog.show();
}




public void educationa_goals(){

    total_task_time = 0;
    Parent.video_task_time = 0;
    Parent. book_task_time = 0;
    Parent.book_task_index = 0;
    Parent.video_task_index =0;

          //  Parent.educational_goals.clear();

            method.alertBox("size= " +  Parent.educational_goals.size(), 3);


            if(Parent.educational_goals.size()>0) {

                for (int d = 0; d <= Parent.educational_goals.size() - 1; d++) {

                    if (Parent.educational_goals.get(d).getStatus().equalsIgnoreCase("active")) {

                        SimpleDateFormat formateDate2 = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
                        Calendar date3 = Calendar.getInstance();
                        String Tdates = null;

                        Tdates = formateDate2.format(date3.getTime());


                        try {
                            Date dates = formateDate2.parse(Parent.educational_goals.get(d).getActive_day());
                            Date dates4 = formateDate2.parse(Tdates);


                            //UPdating the current date and refreshing spent time
                            if (dates.compareTo(dates4) > 0 || dates.compareTo(dates4) < 0) {

                                Calendar date2 = Calendar.getInstance();
                                String Ndates = null;

                                Ndates = formateDate2.format(date2.getTime());
                                Parent.educational_goals.get(d).setActive_day(Ndates);

                                Parent.educational_goals.get(d).setSpent("0");


                                Parent.dbhelper.editGoal(Parent.educational_goals, Parent.kid_id);


                            }


                            if (Parent.educational_goals.get(d).getTaskname().equalsIgnoreCase("Books")) {

                                Parent. book_task_time = Integer.parseInt(Parent.educational_goals.get(d).getAlotted_time());
                                Parent. book_task_index = d;
                                //set

                                int spent_book = Integer.parseInt(Parent.educational_goals.get(d).getSpent());

                                if (Parent.book_task_time == spent_book) {
                                    Parent. book_task_time = 0;
                                }

                            } else if (Parent.educational_goals.get(d).getTaskname().equalsIgnoreCase("Learning Videos")) {
                                Parent.  video_task_time = Integer.parseInt(Parent.educational_goals.get(d).getAlotted_time());
                                int spent_vid = Integer.parseInt(Parent.educational_goals.get(d).getSpent());

                                if (Parent.video_task_time == spent_vid) {
                                    Parent.video_task_time = 0;
                                }

                                Parent.video_task_index = d;
                            }


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }


                }
                total_task_time = Parent.video_task_time + Parent.book_task_time;

                if (Parent.video_task_time > 0 && Parent.book_task_time > 0) {
                    task_exist = "both";
                } else if (Parent.video_task_time > 0 && Parent.book_task_time == 0) {
                    task_exist = "video";
                } else if (Parent.video_task_time == 0 && Parent.book_task_time > 0) {
                    task_exist = "book";

                } else if (total_task_time == 0) {
                    task_exist = "none";
                }
                if (task_exist.equalsIgnoreCase("none")) {

                } else {
                    goals_announcement(Parent.educational_goals.get(0).getLearn_first(), task_exist);

                }
            }

            else{

            }

}







    public void goals_announcement(String status,String task_avail) {
        try {

            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            // alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setIcon(R.drawable.done);
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View dialog = inflater.inflate(R.layout.goal_announcement, null);
            Dialog dialog2 = new Dialog(getActivity());

            int spent_video =Integer.parseInt(Parent.educational_goals.get(Parent.video_task_index).getSpent());
            int spent_book =Integer.parseInt(Parent.educational_goals.get(Parent.book_task_index).getSpent());

           int remain_time_book= Parent.book_task_time - spent_book;
           int remain_time_video= Parent.video_task_time - spent_video;

            if (Build.VERSION.SDK_INT >= 21) {
               // alertDialogBuilder.setView(dialog);
               // final AlertDialog alert = alertDialogBuilder.create();
//method.alertBox(task_avail, 2);
                dialog2.setContentView(dialog);

                //dialog.setTitle("Mr MINP");
                dialog2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                if(status.equalsIgnoreCase("true")) {
                    ((TextView) dialog2.findViewById(R.id.status)).setText("Mandatory");
                }
                else{
                    ((TextView) dialog2.findViewById(R.id.status)).setText("Not Mandatory");
                }


                ((TextView) dialog2.findViewById(R.id.duration_b)).setText("Duration : " +Parent.book_task_time + " mins");
                ((TextView) dialog2.findViewById(R.id.duration_v)).setText("Duration : " +Parent.video_task_time + " mins");


                ((TextView) dialog2.findViewById(R.id.reward_b)).setText("Reward : Extra " +Parent.book_task_time/2 + " mins");
                ((TextView) dialog2.findViewById(R.id.reward_v)).setText("Reward : Extra " +Parent.video_task_time/2 + " mins");

    ((TextView) dialog2.findViewById(R.id.remaining_b)).setText("Remaining : " + remain_time_book + " mins");
 ((TextView) dialog2.findViewById(R.id.remaining_v)).setText("Remaining : " + remain_time_video + " mins");




                if(task_avail.equalsIgnoreCase("video")) {
                    ((RelativeLayout) dialog2.findViewById(R.id.layout_v)).setVisibility(View.VISIBLE);
                }
                else if(task_avail.equalsIgnoreCase("book")){
                    ((RelativeLayout) dialog2.findViewById(R.id.layout_b)).setVisibility(View.VISIBLE);
                }
                else if(task_avail.equalsIgnoreCase("both")){
                    ((RelativeLayout) dialog2.findViewById(R.id.layout_v)).setVisibility(View.VISIBLE);
                    ((RelativeLayout) dialog2.findViewById(R.id.layout_b)).setVisibility(View.VISIBLE);
                }




                ((Button) dialog2.findViewById(R.id.ve)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        dialog2.dismiss();

                    }
                });



                dialog2.show();
            }


            else {
                alertDialogBuilder.setMessage("Educational Goals");
                alertDialogBuilder.setTitle("List of educational goals");


                alertDialogBuilder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        arg0.dismiss();

                    }
                });
                alertDialogBuilder.setNegativeButton("SAVE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {


                        arg0.dismiss();


                    }
                });
                alertDialogBuilder.create().show();
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}

