package com.playzone.kidszone.fragmentpackage;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
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
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.muddzdev.styleabletoast.StyleableToast;
import com.playzone.kidszone.API;
import com.playzone.kidszone.GET_USER_DETAILS;
import com.playzone.kidszone.MainActivity;
import com.playzone.kidszone.Method;
import com.playzone.kidszone.Parent;
import com.playzone.kidszone.R;
import com.playzone.kidszone.SET_USER_DETAILS;
import com.playzone.kidszone.adaptors.ChooseChildAdapter;
import com.playzone.kidszone.models.Internet_Reg_stats_model;
import com.playzone.kidszone.models.parent_model;
import com.playzone.kidszone.models.statistics_model;
import com.playzone.kidszone.service.TSLsocketfactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class Parent_control extends Fragment implements View.OnClickListener {
    ImageView back;
    LinearLayout apps;
    RelativeLayout upgrade;
    LinearLayout exit, account, time, settings, web_settings, security,
            statistics, rate, share, ads, backup, policy, terms, check_update, age_rest, goals;
    RecyclerView recyclerView;
    ChooseChildAdapter mAdapter2;
public static AlertDialog alert;
     public static String choice;
     View dialog ;
    AlertDialog.Builder alertDialogBuilder;
public static ShowcaseView show;
      Button logout;
      TextView Pname, plan_display;
    public static DevicePolicyManager policyMgr;
    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;
    private Method method;

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    CountDownTimer timer;

    static int countdown_has_started =0;
    public int Volume_value=0;
    WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;

    public ScrollView scroll;

    public Parent_control() {
        // Required empty public constructor
    }

    public void showCustomDialog() {
        Parent.dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent);
        Parent.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Parent.dialog.setCancelable(true);
        Parent.dialog.setContentView(R.layout.layout_dialog);
        Parent.dialog.findViewById(R.id.loader).startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.rotate));
        Parent.dialog.getWindow().setBackgroundDrawable( new ColorDrawable(0x7f000000));
        Parent.dialog.show();
    }


    public void showSystemUI() {
      //  if(!Parent.full_screen) {
           /* View v = getActivity().getWindow().getDecorView();
            v.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
*/

        Parent.isTyping=false;
            Parent.restart_main=2;
            Parent.full_screen=false;
            Parent. first_time_open=false;


        Parent.dbhelper.UpdateParent("false",Parent.parentModelList.get(0).getP_email() );

            Intent in = new Intent(getActivity(), MainActivity.class);

            getActivity().finish();
            getActivity().startActivity(in);
       // }
    }



    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void hideSystemUI() {


            //  getWindow().addFlags(WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY);
            // Enables regular immersive mode.
            // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
            // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            View v =getActivity(). getWindow().getDecorView();
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

    @Override
    public void onResume() {

       // showSystemUI();

       // preset();


try {
    if (!Parent.selectedApp_2.isEmpty()) {
        // Parent.dbhelper.addApplist(Parent.selectedApp);
        // for(int f=0; f<=Parent.selectedApp_2.size()-1; f++) {
        //    Toast.makeText(getActivity(), " " + Parent.kid_id , Toast.LENGTH_LONG).show();
        // }
        Parent.dbhelper.addApplist(Parent.selectedApp_2, Parent.kid_id);

        SET_USER_DETAILS set = new SET_USER_DETAILS();

        set.add_selected_apps( Parent.selectedApp_2, Parent_control.this,
                getActivity());

        Parent.selectedApp = Parent.dbhelper.getApplist();


    }
    Parent.selectedApp_2.clear();



getAppStatus();
}
catch (Exception d){

}
        super.onResume();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    void ShowGuide2(View v) {

        ViewTarget target = new ViewTarget(v);


        show = new ShowcaseView.Builder(getActivity())
                .setTarget(target)
                .useDecorViewAsParent()
                .setStyle(R.style.CustomShowcaseTheme)
                .setContentTitle(Parent.pc==1 ? "Click to add Apps for your child account" : Parent.pc==2 ?
                        "Click to set Up App Security" : Parent.pc==3 ?  "Click to set Browser Restrictions" :
                                Parent.pc==4 ? "Click to set time slots for your child's account" :
                        "Congratulations !\n \nYour App has been set up. \n\n Click to view your children Account"
                         )

             .setOnClickListener(this)
                .build();




        show.show();
    }
    void ShowGuideApp(View v) {

        ViewTarget target = new ViewTarget(v);
Button but= new Button(getActivity());
but.setVisibility(View.GONE);

        show = new ShowcaseView.Builder(getActivity())
                .setTarget(target)
                .useDecorViewAsParent()
                .setStyle(R.style.CustomShowcaseTheme)
                .setContentTitle("Click to set Up App Security")
             .setOnClickListener(this)
                .replaceEndButton(but)
                .build();




        show.show();
    }

 void ShowGuide1(View v) {
        if(Parent.first_time_open) {
            hideSystemUI();
        }
     Parent.full_screen=true;


     ViewTarget target = new ViewTarget(v);

     show = new ShowcaseView.Builder(getActivity())
             .setTarget(target)

             .setStyle(R.style.CustomShowcaseTheme3)
             .setContentTitle("Hi ! .. Let us guide you through a quick setup process.\n\n")
             .setContentText("...Click to create a child account")
             .setOnClickListener(this)

             .build();

     show.show();
    }



    void ShowGuide_final(View v) {

     ViewTarget target = new ViewTarget(v);

     show = new ShowcaseView.Builder(getActivity())
            // .setTarget(target)

             .setStyle(R.style.CustomShowcaseTheme5)
   .setContentTitle("Congratulations !\n \nYour App has been set up. \n\nClick on the Go Back Arrow to view your children Account")
            .setOnClickListener(this)
             .build();


     show.show();
    }


    @Override
    public void onClick(View view) {

        if (view instanceof Button) {
           // Toast.makeText(getActivity(), "show entered2 ", Toast.LENGTH_LONG).show();

            show.hide();
            showSystemUI();
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_parent_control_configuraton, container, false);
       // System.out.println("--------------------size= " + Parent.mon.size());
        preset();



        new Thread(new Runnable() {
            @Override
            public void run() {


                usage_stat_Check();
                setDrawOverOtherAppsCheck();
              //  setApp_deviceAmin_check();


            }
        }).start();

        method = new Method( getActivity());

if(method.isNetworkAvailable()) {
    check_subscription();
}
        // MainActivity.is_loggedin=false;
        // MainActivity.has_paid=false;

        pref =  getActivity().getSharedPreferences("login", 0); // 0 - for private mode
        editor = pref.edit();



        back= v.findViewById(R.id.back);
        scroll= v.findViewById(R.id.scroll);
        apps= v.findViewById(R.id.app);
        exit= v.findViewById(R.id.exit);
        account= v.findViewById(R.id.acc);

        share= v.findViewById(R.id.invite);
        ads= v.findViewById(R.id.no_ads);
        rate= v.findViewById(R.id.rate);

        upgrade= v.findViewById(R.id.upgrade);

        time= v.findViewById(R.id.time);
        statistics= v.findViewById(R.id.statistics);
        settings= v.findViewById(R.id.setting);
        security= v.findViewById(R.id.security);
        age_rest= v.findViewById(R.id.age_rest);
        goals= v.findViewById(R.id.goals);
        Pname= v.findViewById(R.id.pname);
        logout= v.findViewById(R.id.logout);
        backup= v.findViewById(R.id.backup);
        terms= v.findViewById(R.id.terms);
        policy= v.findViewById(R.id.policy);
        check_update= v.findViewById(R.id.update);
        web_settings= v.findViewById(R.id.websettings);
        plan_display= v.findViewById(R.id.plan_display);

        mAdView = v.findViewById(R.id.adView);


         mWaveSwipeRefreshLayout = v.findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setColorSchemeColors(Color.WHITE, Color.WHITE);
        mWaveSwipeRefreshLayout.setWaveColor(getActivity().getResources().getColor(R.color.bluen));

        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Do work to refresh the list here.
RetrieveParentAccountInfo();
            }
        });


if(Parent.parentModelList.get(0).getSubscribed().equals("true")){
    ads.setVisibility(View.GONE);
    plan_display.setText("Subscription : " + "Active" +
            " (" + Parent.parentModelList.get(0).duration + ")");
}
else if(Parent.parentModelList.get(0).getSubscribed().equals("Pending")){
    plan_display.setText("Subscription : " + "Pending" +
            " (" + Parent.parentModelList.get(0).duration + ")");
}
else if(Parent.parentModelList.get(0).getSubscribed().equals("expired")){
    plan_display.setText("Subscription : " + "Expired" +
            " (" + Parent.parentModelList.get(0).duration + ")");
}




        Pname.setText(Parent.pname);
        ShowGuide1(account);
         show.hide();

        if(Parent.first_time_open) {
            if (Parent.pc == 0) {
             /*   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    scroll.scrollToDescendant(account);
                }
                else{

              */
                   // scroll.setSmoothScrollingEnabled(true);
                scroll.post(new Runnable() {
                    @Override
                    public void run() {
                        scroll.fullScroll(View.FOCUS_DOWN);

                        ShowGuide1(account);
                    }
                });
                   // scroll.fullScroll(View.FOCUS_UP);
              //  }

            } else if (Parent.pc == 1) {
                ShowGuide2(apps);

            } else if (Parent.pc == 2) {


                scroll.post(new Runnable() {
                    @Override
                    public void run() {
                        scroll.fullScroll(View.FOCUS_DOWN);
                        ShowGuideApp(security);
                    }
                });


            } else if (Parent.pc == 3) {
                ShowGuide2(web_settings);
            } else if (Parent.pc == 4) {

                scroll.post(new Runnable() {
                    @Override
                    public void run() {
                        scroll.fullScroll(View.FOCUS_DOWN);
                        ShowGuide2(time);
                    }
                });


            } else if (Parent.pc == 5) {
                ShowGuide_final(back);


                Parent.isTyping=false;
                Parent.restart_main=2;
                Parent.full_screen=false;
                Parent. first_time_open=false;


                Parent.dbhelper.addParentEdit(new parent_model(Parent.parentModelList.get(0).getPname(),
                        Parent.parentModelList.get(0).getP_email(), Parent.parentModelList.get(0).getPass(),
                        "false"));

                Parent.restart_main = 11;

            }
        }
        else {

          if(Parent.parentModelList.get(0).getSubscribed().equals("false") ||
                    Parent.parentModelList.get(0).getSubscribed().equals("Pending")||
                    Parent.parentModelList.get(0).getSubscribed().equals("expired") ){


                //show ads




            if (method.isNetworkAvailable()) {
                    try {
                        //List<String> testDeviceIds = Arrays.asList("617913307373536687348A3328118180");
                        //RequestConfiguration configuration =
                         //      new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
                     //  MobileAds.setRequestConfiguration(configuration);




                        MobileAds.initialize(getActivity(), initializationStatus -> {


                            if (countdown_has_started == 0) {
                                countdown_has_started = 1;

                                //every one minute
                                timer = new CountDownTimer(5000, 1000) {

                                    public void onTick(long millisUntilFinished) {
                                    }

                                    public void onFinish() {
                                        //method.alertBox("done");
                                        countdown_has_started = 0;
                                        loadAd();

                                    }


                                }.start();

                            }

                        });



                       // AdRequest adRequest = new AdRequest.Builder().build();
                       // mAdView.loadAd(adRequest);



                    } catch (Exception d) {
                        System.out.println(d);
                    }

                }


            }

        }


        if(method.isNetworkAvailable()) {

            Parent.in_reg_stats=Parent.dbhelper.getInternet_reg_stats();

            if (Parent.in_reg_stats.get(0).getHas_reg().equalsIgnoreCase("false")) {

                if (Parent.in_reg_stats.get(0).getTimes().equalsIgnoreCase("0")) {

                    SimpleDateFormat formateDate2 = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
                    Calendar date2 = Calendar.getInstance();
                    String dates = null;

                    dates = formateDate2.format(date2.getTime());

                    Parent.in_reg_stats.get(0).setTimes(dates);

                    Parent.dbhelper.addIsRegInternet(new Internet_Reg_stats_model("false", dates));
                    ask_backup("Your account was created without internet connection. Please Backup your data...");
                } else {

                    SimpleDateFormat formateDate2 = new SimpleDateFormat("MM-dd-yyyy", Locale.US);

                    String store_date = Parent.in_reg_stats.get(0).getTimes();


                    Calendar date2 = Calendar.getInstance();
                    String today = formateDate2.format(date2.getTime());

                    if (store_date.equals(today)) {

                    } else {
                        Calendar daten = Calendar.getInstance();
                        String datesn = null;

                        datesn = formateDate2.format(daten.getTime());
                        Parent.in_reg_stats.get(0).setTimes(datesn);
                        Parent.dbhelper.addIsRegInternet(new Internet_Reg_stats_model("false", datesn));

                        ask_backup("Your account was created without internet connection. Please Backup your data...");
                    }

                }
            }
        }



        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countdown_has_started=0;
                timer.cancel();
                getActivity().finish();
            }
        });
        policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Parent.is_parent_or_signup=0;
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.fragmain,new web_payment());
                 transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        age_rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choice = "age_restriction";
                if (Parent.childModelList.isEmpty()) {

                    CustomErrorLayout();
                } else {

                    alert.show();

                }

            }
        });

        goals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                choice = "goal";
                if (Parent.childModelList.isEmpty()) {

                    CustomErrorLayout();
                } else {

alert.show();

                }
            }
        });
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Parent.is_parent_or_signup=0;
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.fragmain,new web_payment());
                 transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        check_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAppStatus2();
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.playzone.kidszone"));
                getActivity().startActivity(i);
            }
        });


        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  getActivity().finish();
                if(Parent.parentModelList.get(0).getSubscribed().equals("false") ||
                        Parent.parentModelList.get(0).getSubscribed().equals("Pending")||
                        Parent.parentModelList.get(0).getSubscribed().equals("expired") ) {

                    ask_subscribe();
                }
                else {
                    ask_backup("Click to backup your data");
                }
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = "I recommend this app for your kids, it's a good parental control app \n " +
                        "the free features are awesome \n visit https://play.google.com/store/apps/details?id=com.playzone.kidszone  to download now!";

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/html");
                shareIntent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(shareIntent, "Share link using"));
            }
        });

        ads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // getActivity().finish();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.fragmain,new Premium_page());
                // transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // getActivity().finish();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.fragmain,new Premium_page());
                // transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        apps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // showCustomDialog();
                choice="apps";
                show.hide();

                if(Parent.usage_stats==0) {
                    //usage_stat();==2

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                     transaction.replace(R.id.fragmain,new setPermissions(2));
                    // transaction.addToBackStack(null);
                     transaction.commit();
                }
                else if(Parent.usage_stats==1) {


                    if (Parent.childModelList.isEmpty()) {

                        CustomErrorLayout();
                    } else {

                        // ChooseChildLayout();
                        alert.show();
                    }
                }
              /*  FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.fragmain,new List_Installed_frag());
                // transaction.addToBackStack(null);
                transaction.commit();

               */
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
               transaction.replace(R.id.fragmain,new settings());
                // transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.hide();

                /*if(Parent.device_admin==0){
                  //  setApp_deviceAmin(); //1
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                    transaction.replace(R.id.fragmain,new setPermissions(1));
                    // transaction.addToBackStack(null);
                    transaction.commit();
                }
                else if(Parent.device_admin==1) {
*/
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                    transaction.replace(R.id.fragmain, new security());
                    // transaction.addToBackStack(null);
                    transaction.commit();
              //  }
            }
        });

        web_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.hide();
                choice="web_settings";
                if(Parent.childModelList.isEmpty()){

                    CustomErrorLayout();
                }
                else {

                    // ChooseChildLayout();
                    alert.show();
                }


            }
        });

        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // show.hide();
                choice="statistics";
                if(Parent.childModelList.isEmpty()){

                    CustomErrorLayout();
                }
                else {

                    // ChooseChildLayout();
                    alert.show();
                }


            }
        });


        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.hide();
                choice = "time";

                if (Parent.draw_over == 0) {
                    //setDrawOverOtherApps();//0

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                    transaction.replace(R.id.fragmain,new setPermissions(0));
                    // transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    if (Parent.childModelList.isEmpty()) {

                        CustomErrorLayout();
                    } else {

                        // ChooseChildLayout();
                        alert.show();
                    }


                }
            }

        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show.hide();

                if(timer!=null) {
                    timer.cancel();
                }
                countdown_has_started=0;

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.fragmain,new First_time_page());
                // transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              show.hide();

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.fragmain,new Add_child());
                // transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure you want to logout?")
                        .setConfirmText("Yes")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
sDialog.dismiss();

                                editor.putString(Method.isLoggedin, "false");
                                editor.commit();
                                Parent.settingsList.clear();

                                FragmentTransaction transaction =  getActivity().getSupportFragmentManager().beginTransaction();
                                transaction .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                                transaction.replace(R.id.fragmain,new Login());
                                // transaction.addToBackStack(null);
                                transaction.commit();







                            }
                        })

                        .setCancelButton("No", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .show();




            }
        });

        return  v;
    }



    public void loadAd() {

        AdRequest adRequest = new AdRequest.Builder().build();
      //  mAdView.loadAd(adRequest);
try {


    //live key  ca-app-pub-2678126954197689/1393682300
    //test key  ca-app-pub-3940256099942544/1033173712
    InterstitialAd.load(getActivity(), "ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback() {
        @Override
        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
            // The mInterstitialAd reference will be null until
            // an ad is loaded.
            mInterstitialAd = interstitialAd;


            if (mInterstitialAd != null) {
                mInterstitialAd.show(requireActivity());
            } else {
                method.alertBox("The interstitial ad wasn't ready yet.", 1);
            }

            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    // Called when fullscreen content is dismissed.
                    alert.dismiss();

                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    // Called when fullscreen content failed to show.
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    // Called when fullscreen content is shown.
                    // Make sure to set your reference to null so you don't
                    // show it a second time.
                    mInterstitialAd = null;
                }
            });
        }

        @Override
        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
            // Handle the error
            mInterstitialAd = null;
        }
    });
}catch (Exception f){

}
    }

    public void CustomErrorLayout() {
        try {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setCancelable(true);
            alertDialogBuilder.setIcon(R.drawable.done);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialog = inflater.inflate(R.layout.customerr, null);

            if (Build.VERSION.SDK_INT >= 21) {
                alertDialogBuilder.setView(dialog);
                final AlertDialog alert= alertDialogBuilder.create();

                ((TextView)dialog.findViewById(R.id.itemname)).setText("No Child Account Yet");

                dialog.findViewById(R.id.ve).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                        transaction.replace(R.id.fragmain,new Add_child());
                        // transaction.addToBackStack(null);
                        transaction.commit();

                        alert.dismiss();

                    }
                });

                dialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
alert.dismiss();
                       return;

                    }
                });

                alert.show();
            }

            else {
                alertDialogBuilder.setMessage("No Child Account Yet");
                alertDialogBuilder.setTitle("Empty Account");


                alertDialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        arg0.dismiss();

                    }
                });
                alertDialogBuilder.setNegativeButton("Add Child", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                        transaction.replace(R.id.fragmain,new Add_child());
                        // transaction.addToBackStack(null);
                        transaction.commit();

                    }
                });
                alertDialogBuilder.create().show();
            }

        } catch (Exception e) {
        }
    }




    public void preset(){
         alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setCancelable(true);
       // alertDialogBuilder.setIcon(R.drawable.done);
        LayoutInflater inflater = this.getLayoutInflater();

         dialog = inflater.inflate(R.layout.child_selection, null);
        recyclerView = dialog.findViewById(R.id.sc);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerView.setFocusable(true);
        mAdapter2 = new ChooseChildAdapter(getActivity(), Parent.childModelList);

        recyclerView.setAdapter(mAdapter2);

        if (Build.VERSION.SDK_INT >= 21) {
            alertDialogBuilder.setView(dialog);
            alert = alertDialogBuilder.create();


            dialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alert.dismiss();

                   // preset();
                    return;

                }
            });
        }
    }

    public void ChooseChildLayout() {
        try {



               // Parent.dialog.dismiss();
                alert.show();



        } catch (Exception e) {
        }
    }







    public void usage_stat(){


        boolean granted= false;
        AppOpsManager appops = null;
        int mode=0;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            appops = (AppOpsManager) getActivity().getSystemService(Context.APP_OPS_SERVICE);

            mode = appops.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(),
                    getActivity().getPackageName());
        }

        if(mode == AppOpsManager.MODE_DEFAULT){
            granted = (getActivity().checkCallingOrSelfPermission(Manifest.permission.PACKAGE_USAGE_STATS)
                    == PackageManager.PERMISSION_GRANTED);
        }
        else{
            granted = (mode == AppOpsManager.MODE_ALLOWED);
        }

        if(!granted) {
            Intent in = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivityForResult(in, 2024);
        }
        else{
           Parent. usage_stats=1;
           // PowerManager power = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                   /*   if(!power.isIgnoringBatteryOptimizations("com.example.kidszone")){

                          Intent in = new Intent();
                          in.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                         // in.setData(Uri.parse("package:" + getPackageName()));
                          startActivity(in);
                      }
*/

            }



        }
    }

    public void usage_stat_Check(){


        boolean granted= false;
        AppOpsManager appops = null;
        int mode=0;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            appops = (AppOpsManager) getActivity().getSystemService(Context.APP_OPS_SERVICE);

            mode = appops.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(),
                    getActivity().getPackageName());
        }

        if(mode == AppOpsManager.MODE_DEFAULT){
            granted = (getActivity().checkCallingOrSelfPermission(Manifest.permission.PACKAGE_USAGE_STATS)
                    == PackageManager.PERMISSION_GRANTED);
        }
        else{
            granted = (mode == AppOpsManager.MODE_ALLOWED);
        }

        Handler handle = new Handler(Looper.getMainLooper());
        if(!granted) {
            handle.post(new Runnable() {
                @Override
                public void run() {

                    Parent. usage_stats=0;
                }
            });

        }
        else{

            handle.post(new Runnable() {
                @Override
                public void run() {

                    Parent. usage_stats=1;
                }
            });




        }
    }



    public void setDrawOverOtherApps(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(getActivity())) {

                //Click to allow kidzone to display over other apps

                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getActivity().getPackageName()));
                // intent.setData(Uri.parse("package:" + "com.example.kidszone"));
                getActivity(). startActivityForResult(intent, 1993);
            }
            else{
                Parent.draw_over=1;
            }
        }


    }

    public void setDrawOverOtherAppsCheck(){
        Handler handle = new Handler(Looper.getMainLooper());


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (!Settings.canDrawOverlays(getActivity())) {
                handle.post(new Runnable() {
                    @Override
                    public void run() {

                        Parent.draw_over=0;
                    }
                });
            }
            else{
                handle.post(new Runnable() {
                    @Override
                    public void run() {

                        Parent.draw_over=1;
                    }
                });

            }
        }
        else{
                handle.post(new Runnable() {
                    @Override
                    public void run() {

                         Parent.draw_over=1;
                    }
                });

        }


    }





/*
    public void setApp_deviceAmin(){
        try {
            // Initiate DevicePolicyManager.
            policyMgr = (DevicePolicyManager) getActivity(). getSystemService(Context.DEVICE_POLICY_SERVICE);

            // Set DeviceAdminDemo Receiver for active the component with different option
         //   ComponentName componentName = new ComponentName(getActivity(),
          //          DeviceAdminReceiver_code.class);

            //  policyMgr.setLockTaskPackages(componentName,Parent.DATA);




            if (!policyMgr.isAdminActive(componentName)) {
                // try to become active

                Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
                intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                        "Click on Activate button to protect your application from Being Uninstalling!");
                startActivityForResult(intent, 2020);

                // Toast.makeText(getApplicationContext(), "DONE", Toast.LENGTH_LONG).show();

            }
            else{
               Parent. device_admin=1;
            }

        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }

        // startLuncherSelection();


    }


    public void setApp_deviceAmin_check(){
        try {
            Handler handle = new Handler(Looper.getMainLooper());

            // Initiate DevicePolicyManager.
            policyMgr = (DevicePolicyManager) getActivity(). getSystemService(Context.DEVICE_POLICY_SERVICE);

            // Set DeviceAdminDemo Receiver for active the component with different option
            ComponentName componentName = new ComponentName(getActivity(),
                    DeviceAdminReceiver_code.class);

            if (!policyMgr.isAdminActive(componentName)) {
                // try to become active
                handle.post(new Runnable() {
                    @Override
                    public void run() {

                        Parent. device_admin=0;

                    }
                });
            }
            else{
                handle.post(new Runnable() {
                    @Override
                    public void run() {

                        Parent. device_admin=1;
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }

        // startLuncherSelection();


    }




*/


    public void ask_subscribe() {
        try {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            //alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setIcon(R.drawable.done2);
            LayoutInflater inflater = getLayoutInflater();
            final View dialog = inflater.inflate(R.layout.subscribe, null);
            final Button no;
            final Button yes;
            TextView text = dialog.findViewById(R.id.dtext);
           // text.setText(Data);

            if (Build.VERSION.SDK_INT >= 21) {
                alertDialogBuilder.setView(dialog);
                final AlertDialog alert = alertDialogBuilder.create();


                dialog.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //security.imm.hideSoftInputFromWindow(view.getRootView().getWindowToken(), 0);

                        alert.dismiss();

                    }
                });

                dialog.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                        transaction.replace(R.id.fragmain,new Premium_page());
                        // transaction.addToBackStack(null);
                        transaction.commit();
                        alert.dismiss();
                    }
                });
                alert.show();
            }
        } catch (Exception d) {

        }
    }




                        public void ask_backup(String Data){
        try {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            //alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setIcon(R.drawable.done2);
            LayoutInflater inflater = getLayoutInflater();
            final View dialog = inflater.inflate(R.layout.back_up, null);
            final Button no;
            final Button yes;
          TextView text= dialog.findViewById(R.id.dtext);
           text.setText(Data);

            if (Build.VERSION.SDK_INT >= 21) {
                alertDialogBuilder.setView(dialog);
                final AlertDialog alert= alertDialogBuilder.create();


                dialog.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //security.imm.hideSoftInputFromWindow(view.getRootView().getWindowToken(), 0);

                        alert.dismiss();

                    }
                });

                dialog.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
showCustomDialog();
                        Parent.in_reg_stats.get(0).setTimes("0");
                        Parent.in_reg_stats.get(0).setHas_reg("true");
                       // Parent.dbhelper.addIsRegInternet(new Internet_Reg_stats_model("true", "0"));

                        alert.dismiss();






try {
    SET_USER_DETAILS set = new SET_USER_DETAILS();

    //save apps
    set.add_selected_apps( Parent.selectedApp, Parent_control.this,
            getActivity());


    //save parent
    set.AddParent(Parent.pemail, Parent.parentModelList.get(0).getPass(), Parent.pname, "no", Parent_control.this,
            getActivity());


    //adding child
    if (!Parent.childModelList.isEmpty()) {

        for (int f = 0; f <= Parent.childModelList.size() - 1; f++) {

            set.updateChildren(Parent.childModelList.get(f).getPemail(),Parent.childModelList.get(f).getKid_id(),
                    Parent.childModelList.get(f).getIcon(), Parent.childModelList.get(f).getName(),
                    Parent.childModelList.get(f).getGender(), Parent.childModelList.get(f).getPass(),
                    Parent.childModelList.get(f).getDob(),

                     Parent.childModelList.get(f).getStart_time(),
                    Parent.childModelList.get(f).getEnd_time(),
                   Parent.childModelList.get(f).getWhole_week(),
                  Parent.childModelList.get(f).getSingle_day(),
                    Parent_control.this, getActivity());




            set.addmonday(Parent.mon.get(f).getKid_id(), Parent.mon.get(f).getAccess_type(), Parent
                            .mon.get(f).getEnd_time(),
                    Parent.mon.get(f).getStart_time(), Parent_control.this, getActivity());


            set.addtuesday(Parent.tues.get(f).getKid_id(), Parent.tues.get(f).getAccess_type(),
                    Parent.tues.get(f).getEnd_time(),
                    Parent.tues.get(f).getStart_time(), Parent_control.this, getActivity());



            set.addwednesday(Parent.wed.get(f).getKid_id(), Parent.wed.get(f).getAccess_type(),
                    Parent.wed.get(f).getEnd_time(),
                    Parent.wed.get(f).getStart_time(), Parent_control.this, getActivity());



            set.addthursday(Parent.thurs.get(f).getKid_id(), Parent.thurs.get(f).getAccess_type(),
                    Parent.thurs.get(f).getEnd_time(),
                    Parent.thurs.get(f).getStart_time(), Parent_control.this, getActivity());

            set.addfriday(Parent.fri.get(f).getKid_id(), Parent.fri.get(f).getAccess_type(),
                    Parent.fri.get(f).getEnd_time(),
                    Parent.fri.get(f).getStart_time(), Parent_control.this, getActivity());


            set.addsaturday(Parent.sat.get(f).getKid_id(), Parent.sat.get(f).getAccess_type(),
                    Parent.sat.get(f).getEnd_time(),
                    Parent.sat.get(f).getStart_time(), Parent_control.this, getActivity());


            set.addsunday(Parent.sun.get(f).getKid_id(), Parent.sun.get(f).getAccess_type(),
                    Parent.sun.get(f).getEnd_time(),
                    Parent.sun.get(f).getStart_time(), Parent_control.this, getActivity());



        }


    }


    //ADDING WEB SETTINGS
    if (!Parent.WebsetModelList.isEmpty()) {

        for (int k = 0; k <= Parent.WebsetModelList.size() - 1; k++) {
            set.addwebsettings(Parent.pemail, Parent.WebsetModelList.get(k).getname(), Parent.WebsetModelList.get(k).geturl(),
                    Parent.WebsetModelList.get(k).getKeyword(), Parent.WebsetModelList.get(k).getId(),
                    Parent.WebsetModelList.get(k).getCID()
                    , Parent_control.this, getActivity());
        }
    }


    //ADDING SETTINGS
    set.addsettings(Parent.pemail, String.valueOf(Parent.settingsList.get(0).getlock_home_button()),
            String.valueOf(Parent.settingsList.get(0).getfullscreen_mode()),
            String.valueOf(Parent.settingsList.get(0).getlock_notification_bar()),
            String.valueOf(Parent.settingsList.get(0).getlock_volume_botton()),
            Parent.settingsList.get(0).getMasterPassword(),
            Parent.settingsList.get(0).getfeedback(), Parent_control.this, getActivity());

/*
    //adding time settings
    if (!Parent.childModelList.isEmpty()) {

        for (int f = 0; f <= Parent.childModelList.size() - 1; f++) {

            set.EditChild(Parent.childModelList.get(f).getIcon(), Parent.childModelList.get(f).getName(),
                    Parent.childModelList.get(f).getGender(), Parent.childModelList.get(f).getPass(),
                    Parent.childModelList.get(f).getDob(),
                    Parent.kid_id, Parent.childModelList.get(f).getPemail(),
                    Parent.childModelList.get(f).getWhole_week().toString(), Parent.childModelList.get(f).getStart_time(),
                    Parent.childModelList.get(f).getEnd_time(),
                    Parent.childModelList.get(f).getSingle_day().toString(),
                    Parent_control.this, getActivity());


        }

    }

 */
}catch (Exception d){

}
                                Parent.dialog.dismiss();



                    }
                });








                alert.show();
            }

            else {
                alertDialogBuilder.setMessage("Coming soon...");




            }

        } catch (Exception e) {
        }
    }





    public void RetrieveParentAccountInfo(){

        GET_USER_DETAILS get = new GET_USER_DETAILS();

        // get.getChildren(Parent.pemail, Login.this, getActivity());

       // new Thread(new Runnable() {
        //    @Override
         //   public void run() {


        get.getChildren(Parent.pemail, Parent_control.this, getActivity());



        get.getwebsettings(Parent.pemail, Parent_control.this, getActivity()); //3    1

        get.getsettings(Parent.pemail, Parent_control.this, getActivity()); //2    3

        get_statistics(Parent.pemail, Parent_control.this, getActivity()); //1   2




       //     }
//}).start();







       /* FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.fragmain, new Parent_control());
        // transaction.addToBackStack(null);
        transaction.commit();

*/


    }





    public void get_statistics(final String pemail, Fragment frag, Activity act) {

        final JsonObject jsObj = (JsonObject) new Gson().toJsonTree(new API(frag));
        jsObj.addProperty("p_email", pemail);


        jsObj.addProperty("method_name", "get_user_statistics");
        jsObj.addProperty("package_name", API.package_name);
        jsObj.addProperty("sign", API.sign);
        jsObj.addProperty("salt", API.salt);

        RequestQueue mRequestQueue;


        // Instantiate the cache
        Cache cache = new DiskBasedCache(act.getCacheDir(), 1024 * 1024); // 1MB cap
        TSLsocketfactory tsl=null;


        try {
            tsl = new TSLsocketfactory();
        }catch (Exception r){

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


                        Log.d("Response", responseBody);
                        String res = responseBody;


                        //StyleableToast.makeText(act, "statistics",
                        //     Toast.LENGTH_LONG, R.style.mytoast3).show();

                        try {
                            JSONObject jsonObject = new JSONObject(res);

                            JSONArray jsonArray = jsonObject.getJSONArray("PARENT_CONTROL");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                String success = object.getString("success");
                                String msg = object.getString("msg");

                                if (success.equals("1")) {

                                    String cid = object.getString("cid");
                                    String app_name = object.getString("app_name");
                                    String times_opened = object.getString("times_opened");
                                    String date_open = object.getString("date_open");
                                    String app_icon = object.getString("app_icon");
                                    String p_email = object.getString("p_email");

                                    Parent.statisticsList.add(new statistics_model(cid, app_name,
                                            null, times_opened, date_open, p_email));

                                    Parent.dbhelper.addstatistics_from_internet(new statistics_model(cid, app_name,
                                                    null, times_opened, date_open, p_email),
                                            app_name, cid);



                                } else {
                                    //  method.alertBox(msg, 1);
                                    //  StyleableToast.makeText(act, msg,
                                    //      Toast.LENGTH_LONG, R.style.mytoast3).show();

                                }

                            }

                            mWaveSwipeRefreshLayout.setRefreshing(false);

                            method.alertBox("App Refreshed", 2);
                            // progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            // progressDialog.dismiss();
                            // method.alertBox(responseBody);
                           // StyleableToast.makeText(act, responseBody,
                             //       Toast.LENGTH_LONG, R.style.mytoast3).show();

                            mWaveSwipeRefreshLayout.setRefreshing(false);
                            method.alertBox("App Refresh Failed, try again with a stable network", 1);


                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                try {

                    // progressDialog.dismiss();
                    StyleableToast.makeText(act, "bad newtwork, please retry",
                            Toast.LENGTH_LONG, R.style.mytoast3).show();
                    mWaveSwipeRefreshLayout.setRefreshing(false);
                    //method.alertBox("Failed, please retry");
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 3,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(stringRequest);


    }








    public void CHECK_SUB(final String sendEmail,final Fragment frag, final Activity act) {

        final JsonObject jsObj = (JsonObject) new Gson().toJsonTree(new API(frag));
        jsObj.addProperty("email", sendEmail);


        jsObj.addProperty("method_name", "check_subscription");
        jsObj.addProperty("package_name", API.package_name);
        jsObj.addProperty("sign", API.sign);
        jsObj.addProperty("salt", API.salt);

        RequestQueue mRequestQueue;


        // Instantiate the cache
        Cache cache = new DiskBasedCache(act.getCacheDir(), 1024 * 1024); // 1MB cap
        TSLsocketfactory tsl=null;


        try {
            tsl = new TSLsocketfactory();
        }catch (Exception r){

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


                        Log.d("Response", responseBody);
                        String res = responseBody;

                        try {
                            JSONObject jsonObject = new JSONObject(res);

                            JSONArray jsonArray = jsonObject.getJSONArray("PARENT_CONTROL");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                String success = object.getString("success");
                                String msg = object.getString("msg");

                                if (success.equals("1")) {
                                    String packagename = "none";
                                    String startdate = "0";
                                    String enddate = "0";
                                    String duration = "0";
                                    long elapsed_time =0;

                                    if(msg.equals("active") || msg.equals("expired")) {
                                        startdate = object.getString("date");
                                        enddate = object.getString("expire_date");
                                        duration = object.getString("duration");


                                        // Parent.dialog.dismiss();
                                        // StyleableToast.makeText(act, msg,
                                        elapsed_time = SystemClock.elapsedRealtime();

                                        Parent.parentModelList.get(0).setEnd_date(enddate);
                                        Parent.parentModelList.get(0).setElapsed_time(elapsed_time);
                                        Parent.parentModelList.get(0).setStart_date(startdate);


                                        if (duration.equals("monthly")) {
                                            duration = "1 month";
                                            packagename = "Monthly";
                                        } else if (duration.equals("yearly")) {
                                            duration = "1 Year";
                                            packagename = "Yearly";

                                        }
                                    }
                                    //method.alertBox( duration + " " +  packagename,2);


                                    //       Toast.LENGTH_LONG, R.style.mytoast3).show();

                                    //  Parent.dialog.dismiss();
                                    // StyleableToast.makeText(act, msg,
                                    if(msg.equals("not subscribed")){
            Parent.dbhelper.UpdateSubscribeParent("false",Parent.parentModelList.get(0).getP_email(),
                                                elapsed_time, enddate, startdate, duration, packagename);
                                    }
                                    else if(msg.equals("expired")){
                 Parent.dbhelper.UpdateSubscribeParent("expired",Parent.parentModelList.get(0).getP_email(),
                                                elapsed_time, enddate, startdate, duration, packagename);

                                        plan_display.setText("Subscription : " + "Expired" +
                                                " (" + duration + ")");
                                    }
                                    else if(msg.equals("active")){
                                        Parent.dbhelper.UpdateSubscribeParent("true",Parent.parentModelList.get(0).getP_email(),
                                                elapsed_time, enddate, startdate, duration, packagename);


                                        ads.setVisibility(View.GONE);
                                        plan_display.setText("Subscription : " + "Active" +
                                                " (" + duration + ")");
                                    }
                                    // method.alertBox(msg, 2);

                                    Parent.parentModelList=Parent.dbhelper.getParent();
                                }
                                else {
                                    //Parent.dialog.dismiss();


                                    // method.alertBox(msg, 1);

                                }

                            }





                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Parent.dialog.dismiss();
                            // progressDialog.dismiss();
                            method.alertBox(responseBody, 1);
                            //  StyleableToast.makeText(act, responseBody,
                            //         Toast.LENGTH_LONG, R.style.mytoast3).show();

                            //getResources().getString(R.string.contact_msg));
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Parent.dialog.dismiss();
                    // progressDialog.dismiss();
                    //  StyleableToast.makeText(act, "Failed, please retry",
                    //        Toast.LENGTH_LONG, R.style.mytoast3).show();


                    ///still give value and store in local DB


                    //method.alertBox("Failed, please retry");
                } catch (Exception f) {
                    // Parent.dialog.dismiss();
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 3,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(stringRequest);


    }



    public void check_subscription(){
        if(method.isNetworkAvailable()) {
            CHECK_SUB(Parent.pemail, new Fragment(), getActivity());
        }
        else {  //offline activities
//actually  a counter
            Parent.in_reg_stats= Parent.dbhelper.getInternet_reg_stats();

            int value = Integer.parseInt(Parent.in_reg_stats.get(0).getOffline_count());

            if(value >= 3) {
                //Toast.makeText(MainActivity.this, "forbidden", Toast.LENGTH_LONG).show();

                if (method.isNetworkAvailable()) {
                    showCustomDialog();
                    getserverdate();
                }
                else{
                    Parent.parentModelList.get(0).setSubscribed("Pending");

                    plan_display.setText("Subscription : " + "Pending" +
                            " (" + Parent.parentModelList.get(0).duration + ")");
                }
            }
            else {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date strDate = null;



                //Long elapsed_time= SystemClock.elapsedRealtime();

                Parent.parentModelList= Parent.dbhelper.getParent();
                try {

                    strDate = sdf.parse(Parent.parentModelList.get(0).getEnd_date());
                    long time_spent = Parent.parentModelList.get(0).getElapsed_time();


                    Date d = sdf.parse(Parent.parentModelList.get(0).getStart_date());//put ur date value here
                    //Toast.makeText(MainActivity.this,"date= " + d, Toast.LENGTH_LONG).show();

                    // long time_spent = offline.getElapsed_time();
                    long diff = SystemClock.elapsedRealtime() - time_spent;

                    long mytime = d.getTime() + diff;

                    //Toast.makeText(MainActivity.this,d+ " ----- " , Toast.LENGTH_LONG).show();

                    if (mytime > strDate.getTime()) {
                        //Toast.makeText(this, list.get(k).getItem_name()+ "= expired", Toast.LENGTH_LONG).show();
                        // method.alertBox("Expired", 1);

                        Parent.parentModelList.get(0).setSubscribed("expired");
                        Parent.dbhelper.UpdateSubscribeParent("expired",Parent.parentModelList.get(0).getP_email());


                        plan_display.setText("Subscription : " + "Expired" +
                                " (" + Parent.parentModelList.get(0).duration + ")");

                    } else {
                        //Toast.makeText(this, list.get(k).getItem_name() + "= not expired", Toast.LENGTH_LONG).show();

                        // method.alertBox("Not Expired", 2);

                        Parent.parentModelList.get(0).setSubscribed("true");
                        Parent.dbhelper.UpdateSubscribeParent("true",Parent.parentModelList.get(0).getP_email());


                        ads.setVisibility(View.GONE);
                        plan_display.setText("Subscription : " + "Active" +
                                " (" + Parent.parentModelList.get(0).duration + ")");
                    }



                    int old_count=Integer.parseInt( Parent.in_reg_stats.get(0).getOffline_count());
                    int sum_count = old_count + 1;
                    String sum = String.valueOf(sum_count);

                    Parent.dbhelper.addIsRegInternet(new Internet_Reg_stats_model(Parent.in_reg_stats.get(0).getHas_reg(),
                            Parent.in_reg_stats.get(0).getTimes(), Parent.in_reg_stats.get(0).getOffline_changes(),
                            sum));




                } catch (Exception f) {
                    Toast.makeText(getActivity(), f.toString(), Toast.LENGTH_LONG).show();

                }

            }

        }
    }




    public void getserverdate() {

        final JsonObject jsObj = (JsonObject) new Gson().toJsonTree(new API(new Fragment()));


        jsObj.addProperty("method_name", "getdate");
        jsObj.addProperty("package_name", API.package_name);
        jsObj.addProperty("sign", API.sign);
        jsObj.addProperty("salt", API.salt);

        RequestQueue mRequestQueue;


        // Instantiate the cache
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        TSLsocketfactory tsl=null;


        try {
            tsl = new TSLsocketfactory();
        }catch (Exception r){

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


                        Log.d("Response", responseBody);
                        String res = responseBody;

                        try {
                            JSONObject jsonObject = new JSONObject(res);

                            JSONArray jsonArray = jsonObject.getJSONArray("PARENT_CONTROL");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                String success = object.getString("success");
                                String msg = object.getString("msg");

                                if (success.equals("1")) {

                                    String date = object.getString("date");
                                    //method.alertBox("was successful");


                                    // offline.setdate(date);
                                    //offline.setElapsed_time(SystemClock.elapsedRealtime());

                                    Parent.dbhelper.UpdateStartDateParent(date,SystemClock.elapsedRealtime(), Parent.pemail);

                                    //  Parent.dialog.dismiss();
                                    // StyleableToast.makeText(act, msg,

                                    //method.alertBox(msg, 2);
                                    Parent.parentModelList.get(0).setStart_date(date);
                                    Parent.parentModelList.get(0).setElapsed_time(SystemClock.elapsedRealtime());

                                    Parent.in_reg_stats= Parent.dbhelper.getInternet_reg_stats();


                                    Parent.dbhelper.addIsRegInternet(new Internet_Reg_stats_model(Parent.in_reg_stats.get(0).getHas_reg(),
                                            Parent.in_reg_stats.get(0).getTimes(), Parent.in_reg_stats.get(0).getOffline_changes(),
                                            "0"));
                                    check_subscription();

                                }
                                else {
                                    //Parent.dialog.dismiss();

                                    method.alertBox(msg, 1);

                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Parent.dialog.dismiss();
                            // progressDialog.dismiss();
                            // method.alertBox(responseBody, 1);
                            //  StyleableToast.makeText(act, responseBody,
                            //         Toast.LENGTH_LONG, R.style.mytoast3).show();

                            //getResources().getString(R.string.contact_msg));
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Parent.dialog.dismiss();
                    // progressDialog.dismiss();
                    // StyleableToast.makeText(MainActivity.this, "Failed, please retry",
                    //        Toast.LENGTH_LONG, R.style.mytoast3).show();


                    ///still give value and store in local DB


                    //method.alertBox("Failed, please retry");
                } catch (Exception f) {
                    // Parent.dialog.dismiss();
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 3,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(stringRequest);


    }





    public  void getAppStatus() {

        final JsonObject jsObj = (JsonObject) new Gson().toJsonTree(new API(new Fragment()));


        jsObj.addProperty("method_name", "getappstatus");
        jsObj.addProperty("package_name", API.package_name);
        jsObj.addProperty("sign", API.sign);
        jsObj.addProperty("salt", API.salt);

        RequestQueue mRequestQueue;


        // Instantiate the cache
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        TSLsocketfactory tsl=null;


        try {
            tsl = new TSLsocketfactory();
        }catch (Exception r){

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


                        Log.d("Response", responseBody);
                        String res = responseBody;

                        try {
                            JSONObject jsonObject = new JSONObject(res);

                            JSONArray jsonArray = jsonObject.getJSONArray("PARENT_CONTROL");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                String success = object.getString("success");
                                // String msg = object.getString("msg");

                                if (success.equals("1")) {

                                    String status = object.getString("status");
                                    String code = object.getString("code");
                                    String verson_name = object.getString("name");
                                    //method.alertBox("was successful");
                                    String version=null;
                                    int verCode=0;
                                    try {
                                        Fragment f = Parent.currentActivity.getSupportFragmentManager().findFragmentById(R.id.fragmain);

                                        if (f instanceof Parent_control) {

                                            if(getActivity()!=null){
                                            PackageInfo pInfo = requireActivity().getApplicationContext().getPackageManager().getPackageInfo(getActivity().getApplicationContext().getPackageName(), 0);
                                            version = pInfo.versionName;
                                            verCode = pInfo.versionCode;
                                            }
                                        }
                                    } catch (PackageManager.NameNotFoundException e) {
                                        e.printStackTrace();
                                    }




                                    if(code.equals("0") && !(version.equals(verson_name))) {
                                        method.alertBox2(status, getActivity(), false, true);

                                    }
                                    else if(code.equals("ok") || version.equals(verson_name)){
//method.alertBox("App is up to date", 2);
                                    }
                                    else if(!(version.equals(verson_name))){
                                        method.alertBox2(status, getActivity(), true, false);

                                    }
                                }
                                else {
                                    //Parent.dialog.dismiss();

                                    // method.alertBox(msg, 1);

                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    //method.alertBox("Failed, please retry");
                } catch (Exception f) {
                    // Parent.dialog.dismiss();
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 3,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(stringRequest);


    }

    public  void getAppStatus2() {
showCustomDialog();

        final JsonObject jsObj = (JsonObject) new Gson().toJsonTree(new API(new Fragment()));


        jsObj.addProperty("method_name", "getappstatus");
        jsObj.addProperty("package_name", API.package_name);
        jsObj.addProperty("sign", API.sign);
        jsObj.addProperty("salt", API.salt);

        RequestQueue mRequestQueue;


        // Instantiate the cache
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        TSLsocketfactory tsl=null;


        try {
            tsl = new TSLsocketfactory();
        }catch (Exception r){

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


                        Log.d("Response", responseBody);
                        String res = responseBody;

                        try {
                            JSONObject jsonObject = new JSONObject(res);

                            JSONArray jsonArray = jsonObject.getJSONArray("PARENT_CONTROL");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                String success = object.getString("success");
                                // String msg = object.getString("msg");

                                if (success.equals("1")) {

                                    String status = object.getString("status");
                                    String code = object.getString("code");
                                    String verson_name = object.getString("name");
                                    //method.alertBox("was successful");
                                    String version=null;
                                    int verCode=0;
                                    try {
                       Fragment f = Parent.currentActivity.getSupportFragmentManager().findFragmentById(R.id.fragmain);

                                        if (f instanceof Parent_control) {
if(getActivity()!=null) {
    PackageInfo pInfo = requireActivity().getApplicationContext().getPackageManager().getPackageInfo(getActivity().getApplicationContext().getPackageName(), 0);
    version = pInfo.versionName;
    verCode = pInfo.versionCode;
}
                                        }
                                    } catch (PackageManager.NameNotFoundException e) {
                                        Parent.dialog.dismiss();
                                        e.printStackTrace();
                                    }
                                   // method.alertBox("version= "+ version + "vercode =" + verCode, 2);


                                    Parent.dialog.dismiss();

                                    if(code.equals("0") && !(version.equals(verson_name))) {
                                        method.alertBox2(status, getActivity(), false, true);

                                    }
                                    else if(code.equals("ok") || version.equals(verson_name)){
                                        method.alertBox("App is up to date", 2);
                                    }
                                    else if(!(version.equals(verson_name))){
                                        method.alertBox2(status, getActivity(), true, false);

                                    }


                                }
                                else {
                                    //Parent.dialog.dismiss();
                                    Parent.dialog.dismiss();
                                    // method.alertBox(msg, 1);

                                }

                            }


                        } catch (JSONException e) {
                            Parent.dialog.dismiss();
                            e.printStackTrace();

                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    //method.alertBox("Failed, please retry");
                    Parent.dialog.dismiss();
                } catch (Exception f) {
                    // Parent.dialog.dismiss();
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 3,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(stringRequest);


    }





    public void setAgeRestriction() {
        try {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            // alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setIcon(R.drawable.done);
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View dialog = inflater.inflate(R.layout.set_age_restriction, null);

            if (Build.VERSION.SDK_INT >= 21) {
                alertDialogBuilder.setView(dialog);
                final AlertDialog alert = alertDialogBuilder.create();

                ((TextView) dialog.findViewById(R.id.itemname)).setText("Set Age Restriction");


                AppCompatSeekBar seekbar = null;
                seekbar = dialog.findViewById(R.id.seek);
                seekbar.setMax(17);
                seekbar.setProgress(Integer.parseInt(Parent.settingsList.get(0).getContent_Restriction()));


                ((TextView) dialog.findViewById(R.id.itemname)).append("\nAge 0 - " +
                        Parent.settingsList.get(0).getContent_Restriction());

                seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int newVolume, boolean b) {
                        ((TextView) dialog.findViewById(R.id.itemname)).setText("Age 0 - " + newVolume);
                        Volume_value = newVolume;
                        // audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVolume, 0);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });


                dialog.findViewById(R.id.ve).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if (Volume_value == 0) {

                            Parent.settingsList.get(0).setContent_Restriction(Volume_value + "");
                            Parent.settingsList.get(0).setContent_Restriction(Volume_value + "");
                            Parent.settingsList.get(0).setPname(Parent.parentModelList.get(0).getPname());
                            Parent.settingsList.get(0).setPemail(Parent.parentModelList.get(0).getP_email());

                            Parent.dbhelper.addSettings(Parent.settingsList.get(0));

                            if (method.isNetworkAvailable()) {

                                  /*  SET_USER_DETAILS set = new SET_USER_DETAILS();
                                    set.addsettings(Parent.settingsList.get(0).getpemail(), String.valueOf(Parent.settingsList.get(0).getlock_home_button()),
                                            String.valueOf(Parent.settingsList.get(0).getfullscreen_mode()),
                                            String.valueOf(Parent.settingsList.get(0).getlock_notification_bar()),
                                            String.valueOf(Parent.settingsList.get(0).getlock_volume_botton()),
                                            Parent.settingsList.get(0).getMasterPassword(),
                                            Parent.settingsList.get(0).getfeedback(), frag, act);

                                   */
                            }

                        } else {
                            Parent.settingsList.get(0).setContent_Restriction(Volume_value + "");
                            Parent.settingsList.get(0).setPname(Parent.parentModelList.get(0).getPname());

                            Parent.settingsList.get(0).setPemail(Parent.parentModelList.get(0).getP_email());

                            Parent.dbhelper.addSettings(Parent.settingsList.get(0));

                           // g.settings_status.setText("Age 0 - " + Volume_value);
                            ((TextView) dialog.findViewById(R.id.itemname)).setText("Age 0 - " + Volume_value);

                            if (method.isNetworkAvailable()) {
                                    /*
                                    SET_USER_DETAILS set = new SET_USER_DETAILS();
                                    set.addsettings(Parent.settingsList.get(0).getpemail(), String.valueOf(Parent.settingsList.get(0).getlock_home_button()),
                                            String.valueOf(Parent.settingsList.get(0).getfullscreen_mode()),
                                            String.valueOf(Parent.settingsList.get(0).getlock_notification_bar()),
                                            String.valueOf(Parent.settingsList.get(0).getlock_volume_botton()),
                                            Parent.settingsList.get(0).getMasterPassword(),
                                            Parent.settingsList.get(0).getfeedback(), frag, act);

                                     */
                            }

                        }


                        // listStorage.get(0).setlock_volume_botton(true);
                        //Parent.settingsList.get(0).setlock_volume_botton(true);


                        // Parent.volume_lock= Parent.settingsList.get(0).getlock_volume_botton();

method.alertBox("Age Restriction saved", 2);
                        alert.dismiss();

                    }
                });

                dialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alert.dismiss();
                        return;

                    }
                });

                alert.show();
            } else {
                alertDialogBuilder.setMessage("Set Age Restriction");
                alertDialogBuilder.setTitle("Content Restriction Settings");


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
        }
    }

}
