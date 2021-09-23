package com.playzone.kidszone.adaptors;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.playzone.kidszone.API;
import com.playzone.kidszone.MainActivity;
import com.playzone.kidszone.Method;
import com.playzone.kidszone.Parent;
import com.playzone.kidszone.R;
import com.playzone.kidszone.SET_USER_DETAILS;
import com.playzone.kidszone.fragmentpackage.Insert_child;
import com.playzone.kidszone.fragmentpackage.Parent_control;
import com.playzone.kidszone.fragmentpackage.dialog;
import com.playzone.kidszone.models.AppListModel;
import com.playzone.kidszone.models.ChildModel;
import com.playzone.kidszone.models.Internet_Reg_stats_model;
import com.playzone.kidszone.models.active_child_model;
import com.playzone.kidszone.models.fri_model;
import com.playzone.kidszone.models.mon_model;
import com.playzone.kidszone.models.sat_model;
import com.playzone.kidszone.models.sun_model;
import com.playzone.kidszone.models.thurs_model;
import com.playzone.kidszone.models.tues_model;
import com.playzone.kidszone.models.wed_model;
import com.playzone.kidszone.service.BackgroundService;
import com.playzone.kidszone.service.BackgroundService2;
import com.playzone.kidszone.service.TSLsocketfactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;

public class User_account_screen extends Fragment {

    View parentview;
    String data=null;
    int Tag;
  public static RecyclerView recyclerView , recycle2;
    RelativeLayout show;
 public ChildAccountAdapter mAdapter2;
    ImageView exit,pc, battery, wifi;
    Button change;
    public static List<Uri>  fruits = new ArrayList<>();
    public static List<Uri>  bgs = new ArrayList<>();
    public static AlertDialog alert2;
    AlertDialog.Builder alertDialogBuilder;
    public  AlertDialog alert;
    View dialog2 ;

private List<String> numbers= new ArrayList<>();
    ChooseChildAdapter2 mAdapter;

private int Direction_code=0;
    dialog editNameDialogFragment;
    FragmentManager fm ;
    TextView clock, date;

 public  int child_count_from_server=0;
 public  int child_count_from_server_compare=0;

    private BroadcastReceiver batterystatus ;
public static RelativeLayout rel;
    IntentFilter filter = new IntentFilter("android.intent.action.ACTION_POWER_CONNECTED");
    IntentFilter filter2 = new IntentFilter("android.intent.action.ACTION_POWER_DISCONNECTED");
    IntentFilter filter3 = new IntentFilter("android.intent.action.BATTERY_OKAY");
    IntentFilter filter4 = new IntentFilter("android.intent.action.BATTERY_LOW");
    public static InputMethodManager imm;
    public static boolean  isBiScreen=false;
    private Method method;
    @Override
    public void onStart(){
        super.onStart();
        requireActivity().registerReceiver(batterystatus, filter);
        getActivity().registerReceiver(batterystatus, filter2);
        getActivity().registerReceiver(batterystatus, filter3);
        getActivity().registerReceiver(batterystatus, filter4);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imm = (InputMethodManager)  requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        // (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    }


    @Override
    public void onStop(){
        super.onStop();
      requireActivity().unregisterReceiver(batterystatus);
    }

    @Override
    public void onPause(){
        super.onPause();
     // getActivity().unregisterReceiver(batterystatus);
    }



    public void showSystemUI() {
        if(!Parent.full_screen) {
            View v = getActivity().getWindow().getDecorView();
            v.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }



    private void hideSystemUI() {
        if(Parent.full_screen) {

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
    }


    @Override
    public void onResume(){
        super.onResume();



//showSystemUI();
try {
    editNameDialogFragment = new dialog();
    //  PasswordScreen();
    fm = getChildFragmentManager();
    editNameDialogFragment.show(fm, "password");
    editNameDialogFragment.dismiss();


    Parent.hasCountdown_started = false;


    // if(Parent.childModelList.isEmpty()) {
    Parent.childModelList = Parent.dbhelper.getchildren(Parent.pemail);
   // mAdapter2.notifyDataSetChanged();

   // Parent.parentModelList.clear();
   // Parent.parentModelList=Parent.dbhelper.getParent();
   // Parent.MasterPass= Parent.parentModelList.get(0).getPass();
    //   StyleableToast.makeText(getActivity(),  "entered" ,
    //          Toast.LENGTH_LONG, R.style.mytoast).show();







    if (Parent.restart_main == 11) {
        Intent in = new Intent(getActivity(), MainActivity.class);

        getActivity().finish();
        getActivity().startActivity(in);
    }


    SimpleDateFormat formateDate2 = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
    Calendar date2= Calendar.getInstance();
    String dates=null;

    dates =formateDate2.format(date2.getTime());
    date.setText(dates);




    check_subscription();
}catch (Exception f){

}



    }


    public User_account_screen() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



       parentview=  inflater.inflate(R.layout.user_acc_screen, container, false);

        method = new Method( getActivity());

        preset();


        if(method.isNetworkAvailable()){
            //Parent.childModelList.clear();
            Parent.in_reg_stats.clear();
            Parent.in_reg_stats= Parent.dbhelper.getInternet_reg_stats();


           // Toast.makeText(getActivity(), "data= " + Parent.in_reg_stats.get(0).getOffline_changes(), Toast.LENGTH_LONG).show();

            if(Parent.in_reg_stats.get(0).getOffline_changes().equals("true")){
                ask_backup("Changes were made when offline, Please save your changes online");
            }
            else {

                child_count_from_server_compare=0;
                child_count_from_server=0;
                UpdateParentAccountInfo();
            }
        }


        int  screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                isBiScreen=true;
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                // toastMsg = false;
                isBiScreen=false;
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                // toastMsg = false;
                isBiScreen=false;
                break;
            default:
                isBiScreen=true;
                // toastMsg = true;
        }





        recyclerView = (RecyclerView)parentview.findViewById(R.id.adaptmultiple);
        recycle2 = (RecyclerView)parentview.findViewById(R.id.adaptsingle);

      //  recyclerView.setHasFixedSize(true);


            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        recyclerView.setFocusable(false);


       // recycle2.setHasFixedSize(true);
        recycle2.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recycle2.setFocusable(false);






  fillBackgroundImages();
fillBackgroundImages2();
fillBackgroundImages3();
if(Parent.childModelList.isEmpty()){
    recyclerView.setVisibility(View.GONE);
}



         pc=(ImageView)parentview.findViewById(R.id.pc);
         date=(TextView)parentview.findViewById(R.id.date);
         change=(Button)parentview.findViewById(R.id.change);
         exit=(ImageView)parentview.findViewById(R.id.exit);
         wifi=(ImageView)parentview.findViewById(R.id.wifi);
         battery=(ImageView)parentview.findViewById(R.id.battery);
         show=(RelativeLayout) parentview.findViewById(R.id.show);
         rel=(RelativeLayout) parentview.findViewById(R.id.changebg);



      setBatteryandWifi(battery, wifi);



        batterystatus = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
          // Toast.makeText(getActivity(), "Am Called = " + intent.getAction(), Toast.LENGTH_LONG).show();

                if(intent.getAction().equals("android.intent.action.ACTION_POWER_CONNECTED")){
                    getBatteryStatus();
                    if(Parent.isFull && Parent.isCharging){
                        Parent.isCharging=false;
                    }

                    if(Parent.isFull){
                        battery.setImageDrawable(getResources().getDrawable(R.drawable.bat_full));

                    }
                    else if(Parent.isCharging) {
                        battery.setImageDrawable(getResources().getDrawable(R.drawable.bat_charging));
                    }
                }
                else if(intent.getAction().equals("android.intent.action.ACTION_POWER_DISCONNECTED")){
                    setBatteryandWifi(battery, wifi);
                }
                else if(intent.getAction().equals("android.intent.action.BATTERY_OKAY")){
                    setBatteryandWifi(battery, wifi);
                }
                else if(intent.getAction().equals("android.intent.action.BATTERY_LOW")){
                    setBatteryandWifi(battery, wifi);
                }




            }
        };





         if(!Parent.childModelList.isEmpty()){
             show.setVisibility(View.GONE);
         }

         pc.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 dialog.Direction_code=1;
                 //PasswordScreen();

                     showEditDialog();


//alert.show();
             }
         });

       exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialog. Direction_code=0;
              // PasswordScreen();
             //   alert.show();

                    showEditDialog();

            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.fragmain,new Insert_child());
                // transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dialog. Direction_code=2;
                // PasswordScreen();
                //   alert.show();

                showEditDialog();

            }
        });




        //   }
        // else {

        active_child_model active = new active_child_model();
        active= Parent.dbhelper.getActive_child();

        for(int d = 0; d<= Parent.childModelList.size()-1; d++){

            if(active.getPid().equals(Parent.pemail) && active.getKid_id().equals(Parent.childModelList.get(d).Kid_id)){
                Parent.CurrentchildModelList.clear();
                Parent.CurrentchildModelList.add(Parent.childModelList.get(d));
            }
        }

        if(Parent.CurrentchildModelList.isEmpty()){
//method.alertBox(Parent.CurrentchildModelList.size() + "", 2);
            recyclerView.setVisibility(View.GONE);
        }
        else{
           // method.alertBox(Parent.CurrentchildModelList.get(0).Kid_id + "", 2);

            mAdapter2 = new ChildAccountAdapter(getActivity(), Parent.CurrentchildModelList, getActivity(), fruits, bgs);
            recyclerView.setVisibility(View.GONE);
            recycle2.setAdapter(mAdapter2);

        }


               /* if(Parent.childModelList.size()==1) {
                    recyclerView.setVisibility(View.GONE);
                    recycle2.setAdapter(mAdapter2);

                }
                else{
                    recycle2.setVisibility(View.GONE);
                    recyclerView.setAdapter(mAdapter2);

                }
*/




        return parentview;
    }






    private void fillBackgroundImages() {
//File file = new File(Environment.getExternalStorageDirectory() + "\\drawable\\emtrim.png");
        Uri url = null;


        for (int a = 0; a <= 4; a++) {


            if (a == 0) {


                url = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + getResources().getResourcePackageName(R.drawable.fruit1)
                        + '/' + getResources().getResourceTypeName(R.drawable.fruit1) + '/' +
                        getResources().getResourceEntryName(R.drawable.fruit1));
                fruits.add(url);
            } else if (a == 1) {
                url = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + getResources().getResourcePackageName(R.drawable.fruit)
                        + '/' + getResources().getResourceTypeName(R.drawable.fruit) + '/' +
                        getResources().getResourceEntryName(R.drawable.fruit));
                fruits.add(url);
            }
            else if (a == 2) {
                url = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + getResources().getResourcePackageName(R.drawable.fruit2)
                        + '/' + getResources().getResourceTypeName(R.drawable.fruit2) + '/' +
                        getResources().getResourceEntryName(R.drawable.fruit2));
                fruits.add(url);
            }

            else if (a == 3) {
                url = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + getResources().getResourcePackageName(R.drawable.fruit3)
                        + '/' + getResources().getResourceTypeName(R.drawable.fruit3) + '/' +
                        getResources().getResourceEntryName(R.drawable.fruit3));
                fruits.add(url);
            }


        }

        //Toast.makeText(getActivity(), fruits.toString() + "size= " +fruits.size(), Toast.LENGTH_LONG).show();
    }


    private void fillBackgroundImages2() {
//File file = new File(Environment.getExternalStorageDirectory() + "\\drawable\\emtrim.png");
        Uri url = null;


        for (int a = 0; a <= 6; a++) {


            if (a == 0) {

                url = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + getResources().getResourcePackageName(R.drawable.centri)
                        + '/' + getResources().getResourceTypeName(R.drawable.centri) + '/' +
                        getResources().getResourceEntryName(R.drawable.centri));
                bgs.add(url);

            } else if (a == 1) {
                url = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + getResources().getResourcePackageName(R.drawable.freshkid)
                        + '/' + getResources().getResourceTypeName(R.drawable.freshkid) + '/' +
                        getResources().getResourceEntryName(R.drawable.freshkid));
                bgs.add(url);
            } else if (a == 2) {
                url = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + getResources().getResourcePackageName(R.drawable.fresh2)
                        + '/' + getResources().getResourceTypeName(R.drawable.fresh2) + '/' +
                        getResources().getResourceEntryName(R.drawable.fresh2));
                bgs.add(url);
            } else if (a == 3) {
                url = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + getResources().getResourcePackageName(R.drawable.baby)
                        + '/' + getResources().getResourceTypeName(R.drawable.baby) + '/' +
                        getResources().getResourceEntryName(R.drawable.baby));
                bgs.add(url);
            } else if (a == 4) {
                url = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + getResources().getResourcePackageName(R.drawable.angry_rab)
                        + '/' + getResources().getResourceTypeName(R.drawable.angry_rab) + '/' +
                        getResources().getResourceEntryName(R.drawable.angry_rab));
                bgs.add(url);
            }else if (a == 5) {
                url = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + getResources().getResourcePackageName(R.drawable.baby_loading)
                        + '/' + getResources().getResourceTypeName(R.drawable.baby_loading) + '/' +
                        getResources().getResourceEntryName(R.drawable.baby_loading));
                bgs.add(url);
            }


        }
    }
 private void fillBackgroundImages3() {
//File file = new File(Environment.getExternalStorageDirectory() + "\\drawable\\emtrim.png");
        Uri url = null;


        for (int a = 0; a <= 2; a++) {


            if (a == 0) {

                url = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + getResources().getResourcePackageName(R.drawable.night)
                        + '/' + getResources().getResourceTypeName(R.drawable.night) + '/' +
                        getResources().getResourceEntryName(R.drawable.night));
                Parent.bgnight.add(url);

            } else if (a == 1) {
                url = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + getResources().getResourcePackageName(R.drawable.night_background)
                        + '/' + getResources().getResourceTypeName(R.drawable.night_background) + '/' +
                        getResources().getResourceEntryName(R.drawable.night_background));
                Parent.bgnight.add(url);
            }







        }

        //Toast.makeText(getActivity(), fruits.toString() + "size= " +fruits.size(), Toast.LENGTH_LONG).show();
    }


    private boolean checkwifiOnandConnected(){
  WifiManager wifiManager=(WifiManager)getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);

  boolean data=false;

  if(wifiManager.isWifiEnabled()){

      WifiInfo wifiInfo =wifiManager.getConnectionInfo();

      if(wifiInfo!=null){

          if(wifiInfo.getNetworkId() == -1){
              data=  false; // its on but not connected to an access point
          }
          data=true;// its on and connected
      } }

  else{
      data= false;
  }


        return data;
    }





    public int getBatteryStatus(){
        BatteryManager bm = (BatteryManager)getActivity().getApplicationContext().getSystemService(Context.BATTERY_SERVICE);


        // state
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        Intent battery= getActivity().registerReceiver(null, filter);

        int status= battery.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

        Parent.isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ;

        Parent.isFull= status== BatteryManager.BATTERY_STATUS_FULL;


              // percentage
        int percentage=0;


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            percentage=bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

        }

        return percentage;
    }




    public void setBatteryandWifi(ImageView bat, ImageView wifi){
int percent = getBatteryStatus();

        if(Parent.isFull && Parent.isCharging){
            Parent.isCharging=false;
        }

if(Parent.isFull){
    bat.setImageDrawable(getResources().getDrawable(R.drawable.bat_full));
}

      else  if( Parent.isCharging  ){
            bat.setImageDrawable(getResources().getDrawable(R.drawable.bat_charging));
        }
        else {

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

       if(checkwifiOnandConnected()){
           wifi.setImageDrawable(getResources().getDrawable(R.drawable.wifi_available));
       }
       else{
           wifi.setImageDrawable(getResources().getDrawable(R.drawable.wifi_disconnect));

       }
    }


    private void showEditDialog() {
       // dialog editNameDialogFragment = new dialog();
        if(editNameDialogFragment.isAdded()){

        }
        else {
            editNameDialogFragment.show(fm, "password");
        }
    }


    public void PasswordScreen() {
        try {

            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            //alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setIcon(R.drawable.done);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialog = inflater.inflate(R.layout.access_control_screen, null);
            final EditText data;

            if (Build.VERSION.SDK_INT >= 21) {
                alertDialogBuilder.setView(dialog);
                alert= alertDialogBuilder.create();

                data= ((EditText)dialog.findViewById(R.id.pass_data));

                ((Button)dialog.findViewById(R.id.forgot)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alert.dismiss();

                    }
                });

                ((ImageView)dialog.findViewById(R.id.clear)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(numbers.size()!=0) {
                            numbers.remove(numbers.size() - 1);
String num="";
                            for(int a=0; a<=numbers.size()-1; a++) {
                                num= num + numbers.get(a);

                            }
                            data.setText(num);

                        }
                        return;

                    }
                });
                ((ImageView)dialog.findViewById(R.id.done)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(data.getText().toString().equalsIgnoreCase("1234")) {

                            if (Direction_code == 1) {
                                alert.dismiss();
                                Parent.selectedApp_2.clear();

                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                                transaction.replace(R.id.fragmain, new Parent_control());
                                // transaction.addToBackStack(null);
                                transaction.commit();


                            } else if (Direction_code == 0) {
                                Parent.isParent = true;
                               // removeAsHomeApp();

                                //Parent.wakeLock.release();

                                getActivity().stopService(new Intent(getActivity(), BackgroundService2.class));
                                getActivity().stopService(new Intent(getActivity(), BackgroundService.class));


                                NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
                                mNotificationManager.cancel(1992);
                                mNotificationManager.cancel(1993);

                                // util.job.cancelAll();

                                Intent intent = new Intent(getActivity(), BackgroundService2.class);

                                PendingIntent pendingIntent = PendingIntent.getService(
                                        getActivity(), 234, intent, 0);


                                Intent intent2 = new Intent(getActivity(), BackgroundService.class);

                                PendingIntent pendingIntent2 = PendingIntent.getService(
                                        getActivity(), 2345, intent, 0);

                                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
                                alarmManager.cancel(pendingIntent);
                                alarmManager.cancel(pendingIntent2);

                                // MainActivity.cancelTask();

        getActivity().getPackageManager().clearPackagePreferredActivities(getActivity().getPackageName());

                                getActivity().finish();
                                System.exit(0);




                            }
                        }
                        else{
                           // data.setTextColor(Color.RED);
                            data.setError("Wrong Password");
                        }

                    }
                });




                ((TextView)dialog.findViewById(R.id.one)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView tv=(TextView)view;
                        updateView(tv.getText().toString(), data);

                    }
                }); ((TextView)dialog.findViewById(R.id.two)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView tv=(TextView)view;
                        updateView(tv.getText().toString(), data);

                    }
                }); ((TextView)dialog.findViewById(R.id.three)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView tv=(TextView)view;
                        updateView(tv.getText().toString(), data);

                    }
                }); ((TextView)dialog.findViewById(R.id.four)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView tv=(TextView)view;
                        updateView(tv.getText().toString(), data);

                    }
                }); ((TextView)dialog.findViewById(R.id.five)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView tv=(TextView)view;
                        updateView(tv.getText().toString(), data);

                    }
                }); ((TextView)dialog.findViewById(R.id.six)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView tv=(TextView)view;
                        updateView(tv.getText().toString(), data);

                    }
                }); ((TextView)dialog.findViewById(R.id.seven)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView tv=(TextView)view;
                        updateView(tv.getText().toString(), data);

                    }
                }); ((TextView)dialog.findViewById(R.id.eight)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView tv=(TextView)view;
                        updateView(tv.getText().toString(), data);

                    }
                }); ((TextView)dialog.findViewById(R.id.nine)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView tv=(TextView)view;
                        updateView(tv.getText().toString(), data);


                    }
                }); ((TextView)dialog.findViewById(R.id.zero)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView tv=(TextView)view;
                        updateView(tv.getText().toString(), data);

                    }
                });





            }

            else {
                alertDialogBuilder.setMessage("For Parent Only !");




            }

        } catch (Exception e) {
        }
    }


    void updateView(String value, EditText text){
        numbers.add(value);
String num="";
        for(int a=0; a<=numbers.size()-1; a++) {
            num= num + numbers.get(a);
        }

        text.setText(num);

    }




    void removeAsHomeApp(){

        Intent intent = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent = new Intent(Settings.ACTION_HOME_SETTINGS);
            //select Kidzone as home app
        }
        else{
            //click on Home App and choose Kidzone as home app
            intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + "com.playzone.kidszone"));

        }

        // Toast.makeText(con, listStorage.get(i).packages, Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }











    public void UpdateParentAccountInfo(){

        getChildren(Parent.pemail, User_account_screen.this, getActivity());




    }



    public void getChildren(final String sendEmail, final Fragment frag, final Activity act) {

        final JsonObject jsObj = (JsonObject) new Gson().toJsonTree(new API(frag));
        jsObj.addProperty("p_email", sendEmail);


        jsObj.addProperty("method_name", "get_children");
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


                        Log.d("Response", new String(responseBody));
                        String res = new String(responseBody);

                        try {
                            JSONObject jsonObject = new JSONObject(res);

                            JSONArray jsonArray = jsonObject.getJSONArray("PARENT_CONTROL");
                            Parent.childModelList.clear();
                            Parent.sun.clear();
                            Parent.mon.clear();
                            Parent.tues.clear();
                            Parent.wed.clear();
                            Parent.thurs.clear();
                            Parent.fri.clear();
                            Parent.sat.clear();

                            child_count_from_server=jsonArray.length();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                String success = object.getString("success");
                                String msg = object.getString("msg");

                                if (success.equals("1")) {

                                    String icon = object.getString("icon");
                                    String Name = object.getString("name");
                                    String dob = object.getString("dob");
                                    String pass = object.getString("pass");
                                    String gender = object.getString("gender");
                                    String starttime = object.getString("starttime");
                                    String endtime = object.getString("endtime");
                                    String wholeweek = object.getString("wholeweek");
                                    String singleday = object.getString("singleday");
                                    String kidid = object.getString("kidid");
                                    String accesstype = object.getString("accesstype");

                                    Uri  icon2=null;
                                    Uri url=null;

                                    Parent.ac = 1;

                                    File file = new File(icon);

                                    if(file.exists()) {
                                        icon2 = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                                                "://" + act.getResources().getResourcePackageName(R.drawable.touxiang00)
                                                + '/' + act.getResources().getResourceTypeName(R.drawable.touxiang00) + '/' +
                                                act.getResources().getResourceEntryName(R.drawable.touxiang00));


                                    }

                                    else{

                                        if(i==0) {
                                            url = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                                                    "://" + act.getResources().getResourcePackageName(R.drawable.touxiang00)
                                                    + '/' + act.getResources().getResourceTypeName(R.drawable.touxiang00) + '/' +
                                                    act.getResources().getResourceEntryName(R.drawable.touxiang00));
                                        }  else if(i==1) {
                                            url = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                                                    "://" + act.getResources().getResourcePackageName(R.drawable.touxiang01)
                                                    + '/' + act.getResources().getResourceTypeName(R.drawable.touxiang01) + '/' +
                                                    act.getResources().getResourceEntryName(R.drawable.touxiang01));
                                        }
                                        else if(i==2) {
                                            url = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                                                    "://" + act.getResources().getResourcePackageName(R.drawable.touxiang02)
                                                    + '/' + act.getResources().getResourceTypeName(R.drawable.touxiang02) + '/' +
                                                    act.getResources().getResourceEntryName(R.drawable.touxiang02));
                                        } else if(i==3) {
                                            url = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                                                    "://" + act.getResources().getResourcePackageName(R.drawable.touxiang03)
                                                    + '/' + act.getResources().getResourceTypeName(R.drawable.touxiang03) + '/' +
                                                    act.getResources().getResourceEntryName(R.drawable.touxiang03));
                                        }
                                        else{
                                            url = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                                                    "://" + act.getResources().getResourcePackageName(R.drawable.touxiang04)
                                                    + '/' + act.getResources().getResourceTypeName(R.drawable.touxiang04) + '/' +
                                                    act.getResources().getResourceEntryName(R.drawable.touxiang04));
                                        }




                                        icon= "";
                                        icon2= url;
                                    }


                                    Parent.childModelList.add(new ChildModel(Name, icon,
                                            gender, dob, pass,
                                            sendEmail,
                                            accesstype, kidid,starttime, endtime, Boolean.parseBoolean(wholeweek),
                                            Boolean.parseBoolean(singleday), icon2 ));

                                    long succ = Parent.dbhelper.addChild(Parent.childModelList);

                                    // if (succ == 1) {
                                    //  StyleableToast.makeText(act, "Child Added",
                                    //         Toast.LENGTH_SHORT, R.style.mytoast3).show();


                                 /*   Parent.selectedApp_2.clear();


                                    String name = object.getString("app_name");
                                    String id_value = object.getString("id_value");
                                    String icon2 = object.getString("app_icon");
                                    String packages = object.getString("packages");
                                    String checked = object.getString("checked");

                                    Parent.selectedApp_2.add( new AppListModel(name,null, packages ,
                                            Boolean.parseBoolean(checked), id_value));

if(name.equals("") && id_value.equals("") && packages.equals("")){

}
else {
    Parent.dbhelper.addApplist(Parent.selectedApp_2, kidid);
}
*/
                                    try {


                                        getselected_apps(kidid, frag, act);


                                        getDaysoftheweek(kidid, frag, act);

                                     //   System.out.println("--------------------called= " + i );
                                    }catch (Exception gh){

                                        System.out.println("thread place ==" + gh.getMessage());
                                    }
                                    //   }
                                    // }).start();



                                    // }

                                } else {
                                    // method.alertBox(msg);
                                    //StyleableToast.makeText(act, msg,
                                    //    Toast.LENGTH_LONG, R.style.mytoast3).show();

                                }

                            }


                           // Parent.childModelList = Parent.dbhelper.getchildren(Parent.pemail);
                        //    Fragment f = Parent.currentActivity.getSupportFragmentManager().findFragmentById(R.id.fragmain);

                         //   if(f instanceof User_account_screen) {


                          //  }
                           // System.out.println("--------------------size= " + Parent.mon.size());

                            //  progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            // progressDialog.dismiss();
                            // method.alertBox(responseBody);
                            //StyleableToast.makeText(act, responseBody,
                             //       Toast.LENGTH_LONG, R.style.mytoast3).show();

                            //getResources().getString(R.string.contact_msg));
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                try {

                    // progressDialog.dismiss();
                   // StyleableToast.makeText(act, "Failed, please retry",
                    //        Toast.LENGTH_LONG, R.style.mytoast3).show();

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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(stringRequest);


    }




    public void getDaysoftheweek(final String kid, Fragment frag, Activity act) {

        final JsonObject jsObj = new JsonObject() ;//new Gson().toJsonTree(new API(frag));
        jsObj.addProperty("kid_id", kid);


        jsObj.addProperty("method_name", "get_sunday");
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


                        Log.d("Response", new String(responseBody));
                        String res = new String(responseBody);

                        try {
                            JSONObject jsonObject = new JSONObject(res);

                            JSONArray jsonArray = jsonObject.getJSONArray("PARENT_CONTROL");

                            // StyleableToast.makeText(act, "days of the week",
                            //        Toast.LENGTH_LONG, R.style.mytoast3).show();

                         //   System.out.println("--------------------called= "  );

                            for (int i = 0; i < jsonArray.length(); i++) {

                                // System.out.println("days of the week = " + count);
                                JSONObject object = jsonArray.getJSONObject(i);
                                String success = object.getString("success");
                                String msg = object.getString("msg");

                                if (success.equals("1")) {

                                    String start_time = object.getString("start_time");
                                    String end_time = object.getString("end_time");
                                    String access_type = object.getString("access_type");
                                    String kid_id = object.getString("kid_id");

                                    if(access_type.equals("")){
                                        access_type= "unlimited";
                                    }

                                    if(i==0) {
                                        //sun
                                        Parent.sun.add(new sun_model(start_time, end_time, kid_id
                                                , access_type));

                                        Parent.dbhelper.addSunday(Parent.sun);
                                    }
                                    else if(i==1){
                                        //mon
                                        Parent.mon.add(new mon_model(start_time, end_time, kid_id
                                                , access_type));

                                        Parent.dbhelper.addMonday(Parent.mon);
                                    }else if(i==2){
                                        //tues
                                        Parent.tues.add(new tues_model(start_time, end_time, kid_id
                                                , access_type));

                                        Parent.dbhelper.addTuesday(Parent.tues);
                                    }else if(i==3){
                                        //wed
                                        Parent.wed.add(new wed_model(start_time, end_time, kid_id
                                                , access_type));

                                        Parent.dbhelper.addWednesday(Parent.wed);
                                    }else if(i==4){
                                        //thurs
                                        Parent.thurs.add(new thurs_model(start_time, end_time, kid_id
                                                , access_type));

                                        Parent.dbhelper.addThursday(Parent.thurs);

                                    }else if(i==5){
                                        //fri
                                        Parent.fri.add(new fri_model(start_time, end_time, kid_id
                                                , access_type));

                                        Parent.dbhelper.addFriday(Parent.fri);
                                    }else if(i==6){
                                        //sat
                                        Parent.sat.add(new sat_model(start_time, end_time, kid_id
                                                , access_type));

                                        Parent.dbhelper.addSaturday(Parent.sat);
                                    }


                                } else {
                                    // method.alertBox(msg, 1);
                                    // StyleableToast.makeText(act, msg,
                                    //     Toast.LENGTH_LONG, R.style.mytoast3).show();

                                }

                            }






           child_count_from_server_compare+=1;

                            if(getActivity()!=null) {
                                //StyleableToast.makeText(act, "compared= "+child_count_from_server_compare + "actual = " + (child_count_from_server*2),
                                 //       Toast.LENGTH_LONG, R.style.mytoast3).show();

                                if(child_count_from_server_compare==(child_count_from_server*2)) {
                                 //   mAdapter2 = new ChildAccountAdapter(requireActivity(), Parent.childModelList, getActivity(), fruits, bgs);
                                  //  recyclerView.setAdapter(mAdapter2);
                                }
                            }
                            // progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            // progressDialog.dismiss();
                            // method.alertBox(responseBody);
                          //  StyleableToast.makeText(act, responseBody,
                           //         Toast.LENGTH_LONG, R.style.mytoast3).show();

                            //getResources().getString(R.string.contact_msg));
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                try {

                    // progressDialog.dismiss();
                   // StyleableToast.makeText(act, "Failed, please retry",
                       //     Toast.LENGTH_LONG, R.style.mytoast3).show();

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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(stringRequest);


    }








    public void getselected_apps(final String kid, Fragment frag, Activity act) {

        final JsonObject jsObj =new JsonObject() ;// (JsonObject) new Gson().toJsonTree(new API(frag));
        jsObj.addProperty("id_value", kid);


        jsObj.addProperty("method_name", "get_applist");
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


                        Log.d("Response", new String(responseBody));
                        String res = new String(responseBody);

                        try {
                            JSONObject jsonObject = new JSONObject(res);

                            JSONArray jsonArray = jsonObject.getJSONArray("PARENT_CONTROL");



                            //    StyleableToast.makeText(act, "selected apps",
                            //           Toast.LENGTH_LONG, R.style.mytoast3).show();
                            //   System.out.println("selected app = " );
                            Parent.selectedApp_2.clear();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                // System.out.println("selected app = " + count);

                                JSONObject object = jsonArray.getJSONObject(i);
                                String success = object.getString("success");
                                String msg = object.getString("msg");

                                if (success.equals("1")) {

                                    String name = object.getString("name");
                                    String id_value = object.getString("id_value");
                                    String icon = object.getString("icon");
                                    String packages = object.getString("packages");
                                    String checked = object.getString("checked");

                                    Parent.selectedApp_2.add(new AppListModel(name,null, packages ,
                                            Boolean.parseBoolean(checked), id_value));



                                } else {
                                    // method.alertBox(msg, 1);
                                    //  StyleableToast.makeText(act, msg,
                                    //     Toast.LENGTH_LONG, R.style.mytoast3).show();

                                }

                            }




                            if(Parent.selectedApp_2.isEmpty() || Parent.selectedApp_2==null){

                            }
                            else {
                                Parent.dbhelper.addApplist(Parent.selectedApp_2, kid);


                            }

                            child_count_from_server_compare+=1;
                            //progressDialog.dismiss();
                            if(getActivity()!=null) {
                               // StyleableToast.makeText(act, "compared= "+child_count_from_server_compare + "actual = " + (child_count_from_server*2),
                               //         Toast.LENGTH_LONG, R.style.mytoast3).show();

                                if(child_count_from_server_compare==(child_count_from_server*2)) {
                                  //  mAdapter2 = new ChildAccountAdapter(requireActivity(), Parent.childModelList, getActivity(), fruits, bgs);
                                  //  recyclerView.setAdapter(mAdapter2);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // progressDialog.dismiss();
                            // method.alertBox(responseBody);
                           // StyleableToast.makeText(act, responseBody,
                            //        Toast.LENGTH_LONG, R.style.mytoast3).show();

                            //getResources().getString(R.string.contact_msg));
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                try {

                    // progressDialog.dismiss();
                   // StyleableToast.makeText(act, "Failed, please retry",
                     //       Toast.LENGTH_LONG, R.style.mytoast3).show();

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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(stringRequest);


    }









    public void showCustomDialog() {
        Parent.dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent);
        Parent.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Parent.dialog.setCancelable(true);
        Parent.dialog.setContentView(R.layout.layout_dialog);
        ((ImageView)  Parent.dialog.findViewById(R.id.loader)).startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.rotate));
        Parent.dialog.getWindow().setBackgroundDrawable( new ColorDrawable(0x7f000000));
        Parent.dialog.show();
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
            TextView text= (TextView)dialog.findViewById(R.id.dtext);
            text.setText(Data);

            if (Build.VERSION.SDK_INT >= 21) {
                alertDialogBuilder.setView(dialog);
                final AlertDialog alert= alertDialogBuilder.create();


                ((Button)dialog.findViewById(R.id.no)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //security.imm.hideSoftInputFromWindow(view.getRootView().getWindowToken(), 0);

                        alert.dismiss();

                    }
                });

                ((Button)dialog.findViewById(R.id.yes)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showCustomDialog();
                        Parent.in_reg_stats.get(0).setTimes("0");
                        Parent.in_reg_stats.get(0).setHas_reg("true");
                        // Parent.dbhelper.addIsRegInternet(new Internet_Reg_stats_model("true", "0"));

                        alert.dismiss();





                        new Thread(new Runnable() {
                            @Override
                            public void run() {


                                try {
                                    SET_USER_DETAILS set = new SET_USER_DETAILS();

                                    //save apps
                                    set.add_selected_apps( Parent.selectedApp, User_account_screen.this,
                                            getActivity());


                                    //save parent
                                    set.AddParent(Parent.pemail, Parent.parentModelList.get(0).getPass(), Parent.pname, "no", User_account_screen.this,
                                            getActivity());


                                    //adding child
                                    if (!Parent.childModelList.isEmpty()) {

                                        for (int f = 0; f <= Parent.childModelList.size() - 1; f++) {

                                            set.updateChildren(Parent.childModelList.get(f).getPemail(), Parent.childModelList.get(f).getKid_id(),
                                                    Parent.childModelList.get(f).getIcon(), Parent.childModelList.get(f).getName(),
                                                    Parent.childModelList.get(f).getGender(), Parent.childModelList.get(f).getPass(),
                                                    Parent.childModelList.get(f).getDob(),

                                                    Parent.childModelList.get(f).getStart_time(),
                                                    Parent.childModelList.get(f).getEnd_time(),
                                                    Parent.childModelList.get(f).getWhole_week(),
                                                    Parent.childModelList.get(f).getSingle_day(),
                                                    User_account_screen.this, getActivity());




                                            set.addmonday(Parent.mon.get(f).getKid_id(), Parent.mon.get(f).getAccess_type(), Parent
                                                            .mon.get(f).getEnd_time(),
                                                    Parent.mon.get(f).getStart_time(), User_account_screen.this, getActivity());


                                            set.addtuesday(Parent.tues.get(f).getKid_id(), Parent.tues.get(f).getAccess_type(),
                                                    Parent.tues.get(f).getEnd_time(),
                                                    Parent.tues.get(f).getStart_time(), User_account_screen.this, getActivity());



                                            set.addwednesday(Parent.wed.get(f).getKid_id(), Parent.wed.get(f).getAccess_type(),
                                                    Parent.wed.get(f).getEnd_time(),
                                                    Parent.wed.get(f).getStart_time(), User_account_screen.this, getActivity());



                                            set.addthursday(Parent.thurs.get(f).getKid_id(), Parent.thurs.get(f).getAccess_type(),
                                                    Parent.thurs.get(f).getEnd_time(),
                                                    Parent.thurs.get(f).getStart_time(), User_account_screen.this, getActivity());

                                            set.addfriday(Parent.fri.get(f).getKid_id(), Parent.fri.get(f).getAccess_type(),
                                                    Parent.fri.get(f).getEnd_time(),
                                                    Parent.fri.get(f).getStart_time(), User_account_screen.this, getActivity());


                                            set.addsaturday(Parent.sat.get(f).getKid_id(), Parent.sat.get(f).getAccess_type(),
                                                    Parent.sat.get(f).getEnd_time(),
                                                    Parent.sat.get(f).getStart_time(), User_account_screen.this, getActivity());


                                            set.addsunday(Parent.sun.get(f).getKid_id(), Parent.sun.get(f).getAccess_type(),
                                                    Parent.sun.get(f).getEnd_time(),
                                                    Parent.sun.get(f).getStart_time(), User_account_screen.this, getActivity());



                                        }


                                    }


                                    //ADDING WEB SETTINGS
                                    if (!Parent.WebsetModelList.isEmpty()) {

                                        for (int f = 0; f <= Parent.WebsetModelList.size() - 1; f++) {
                                            set.addwebsettings(Parent.pemail, Parent.WebsetModelList.get(f).getname(), Parent.WebsetModelList.get(f).geturl(),
                                                    Parent.WebsetModelList.get(f).getKeyword(), Parent.WebsetModelList.get(f).getId(), Parent.WebsetModelList.get(f).getCID()
                                                    , User_account_screen.this, getActivity());
                                        }
                                    }


                                    //ADDING SETTINGS
                                    set.addsettings(Parent.pemail, String.valueOf(Parent.settingsList.get(0).getlock_home_button()),
                                            String.valueOf(Parent.settingsList.get(0).getfullscreen_mode()),
                                            String.valueOf(Parent.settingsList.get(0).getlock_notification_bar()),
                                            String.valueOf(Parent.settingsList.get(0).getlock_volume_botton()),
                                            Parent.settingsList.get(0).getMasterPassword(),
                                            Parent.settingsList.get(0).getfeedback(), User_account_screen.this, getActivity());


                                }catch (Exception d){

                                }
                                Parent.dialog.dismiss();
                            }
                        }).start();



  Parent.dbhelper.addIsRegInternet(new Internet_Reg_stats_model(Parent.in_reg_stats.get(0).getHas_reg(), Parent.in_reg_stats.get(0).getTimes(), "false"));

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











    public void CHECK_SUB(final String sendEmail, final Fragment frag, final Activity act) {

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


                        Log.d("Response", new String(responseBody));
                        String res = new String(responseBody);

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
                                        Parent.dbhelper.UpdateSubscribeParent("false", Parent.parentModelList.get(0).getP_email(),
                                                elapsed_time, enddate, startdate, duration, packagename);
                                    }
                                    else if(msg.equals("expired")){
                                        Parent.dbhelper.UpdateSubscribeParent("expired", Parent.parentModelList.get(0).getP_email(),
                                                elapsed_time, enddate, startdate, duration, packagename);
                                    }
                                    else if(msg.equals("active")){
                                        Parent.dbhelper.UpdateSubscribeParent("true", Parent.parentModelList.get(0).getP_email(),
                                                elapsed_time, enddate, startdate, duration, packagename);
                                    }
                                    // method.alertBox(msg, 2);

                                    Parent.parentModelList= Parent.dbhelper.getParent();
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
                          //  method.alertBox(responseBody, 1);
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
                        Parent.dbhelper.UpdateSubscribeParent("expired", Parent.parentModelList.get(0).getP_email());


                    } else {
                        //Toast.makeText(this, list.get(k).getItem_name() + "= not expired", Toast.LENGTH_LONG).show();

                        // method.alertBox("Not Expired", 2);

                        Parent.parentModelList.get(0).setSubscribed("true");
                        Parent.dbhelper.UpdateSubscribeParent("true", Parent.parentModelList.get(0).getP_email());

                    }



                    int old_count=Integer.parseInt( Parent.in_reg_stats.get(0).getOffline_count());
                    int sum_count = old_count + 1;
                    String sum = String.valueOf(sum_count);

                    Parent.dbhelper.addIsRegInternet(new Internet_Reg_stats_model(Parent.in_reg_stats.get(0).getHas_reg(),
                            Parent.in_reg_stats.get(0).getTimes(), Parent.in_reg_stats.get(0).getOffline_changes(),
                            sum));




                } catch (Exception f) {
                   // Toast.makeText(getActivity(), f.toString(), Toast.LENGTH_LONG).show();

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


                        Log.d("Response", new String(responseBody));
                        String res = new String(responseBody);

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












    public  void preset(){
        alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setCancelable(true);
        // alertDialogBuilder.setIcon(R.drawable.done);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        dialog2 = inflater.inflate(R.layout.child_selection, null);
        RecyclerView recyclerView = (RecyclerView)dialog2.findViewById(R.id.sc);
       // recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recyclerView.setFocusable(true);
        mAdapter = new ChooseChildAdapter2(getActivity(), Parent.childModelList, new FragmentActivity());

        recyclerView.setAdapter(mAdapter);

        if (Build.VERSION.SDK_INT >= 21) {
            alertDialogBuilder.setView(dialog2);
            alert2 = alertDialogBuilder.create();


            ((Button) dialog2.findViewById(R.id.close)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alert2.dismiss();

                    // preset();
                    return;

                }
            });
        }
    }

    public void ChooseChildLayout() {
        try {



            // Parent.dialog.dismiss();
            alert2.show();



        } catch (Exception e) {
        }
    }

}
