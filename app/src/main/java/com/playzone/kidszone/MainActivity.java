package com.playzone.kidszone;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
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
import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RaveUiManager;
import com.flutterwave.raveandroid.rave_java_commons.RaveConstants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.muddzdev.styleabletoast.StyleableToast;
import com.playzone.kidszone.fragmentpackage.Add_child;
import com.playzone.kidszone.fragmentpackage.Insert_child;
import com.playzone.kidszone.fragmentpackage.List_Installed_frag;
import com.playzone.kidszone.fragmentpackage.Login;
import com.playzone.kidszone.fragmentpackage.Parent_control;
import com.playzone.kidszone.fragmentpackage.Recover;
import com.playzone.kidszone.fragmentpackage.User_account_screen;
import com.playzone.kidszone.fragmentpackage.security;
import com.playzone.kidszone.fragmentpackage.selected_app_frag;
import com.playzone.kidszone.models.AppListModel;
import com.playzone.kidszone.models.Internet_Reg_stats_model;
import com.playzone.kidszone.models.access_type_model;
import com.playzone.kidszone.service.BackgroundService;
import com.playzone.kidszone.service.BackgroundService2;
import com.playzone.kidszone.service.TSLsocketfactory;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import io.reactivex.functions.Consumer;




public class MainActivity  extends RuntimePermissionsActivity {
    boolean current_focus;
    boolean isPaused;
    Handler collapseNotificationHandler;
    static View parentview;
     com.playzone.kidszone.Method method;
    public static int taskid;
    public static InputMethodManager imm;
    public  static int screenSize;
    static int page=6;
    static String empty="false";
    String data=null;
    private static final int REQUEST_PERMISSIONS = 24;
    public static MyTask asyncTask = null;
    public  static boolean view_selected_apps_is_parent =false;
   // private BroadcastReceiver screen_status ;
   long remaining_time;
    String access_type;

   static  String txRef;

    Configuration config ;

    AudioManager audioManager;

    @Override
    public  void onNewIntent(Intent in){
        super.onNewIntent(in);
       // Toast.makeText(getApplicationContext(), "new intent", Toast.LENGTH_LONG).show();

/*

        if(Parent.user_never_logout==1) {
            Parent.data_clear_by_android = "false";



            showCustomDialog();

            if(Parent.childModelList.isEmpty()){
                Parent.childModelList =  Parent.dbhelper.getchildren(Parent.pname);
            }
            else {
                String accesstype=null;
                boolean whole=false;
                boolean single=false;
                int position=0;

                for(int search=0; search<=Parent.childModelList.size()-1; search++){

                    if(Parent.childModelList.get(search).getKid_id().equals(Parent.active_user_details.get(0).getKid_id())){

                        accesstype= Parent.childModelList.get(search).getAccess_type();
                        whole=  Parent.childModelList.get(search).getWhole_week();
                        single= Parent.childModelList.get(search).getSingle_day();
                        position=search;

                        break;
                    }
                }

//StyleableToast.makeText(this, accesstype + " = id =" +  Parent.active_user_details.get(0).getKid_id()
                //        + " whole or single = " + whole +" or " + single, Toast.LENGTH_LONG, R.style.mytoast).show();

                ComputeAccessTime(Parent.active_user_details.get(0).getKid_id(), accesstype, whole, single, position);


            }

        }
*/

        if(  !Parent.settingsList.isEmpty()) {
            if (Parent.settingsList.get(0).getlock_volume_botton() != 0) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        Parent.settingsList.get(0).getlock_volume_botton(), AudioManager.FLAG_SHOW_UI);
            }
        }



        if(Parent.parentModelList.isEmpty()){

        }
        else {
            check_subscription();
        }

        setDrawOverOtherAppsCheck();
    }

    public void setDrawOverOtherAppsCheck(){
        Handler handle = new Handler(Looper.getMainLooper());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (!Settings.canDrawOverlays(MainActivity.this)) {
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



    @Override
    public void onStart(){
        super.onStart();

    }
    /* access modifiers changed from: protected */
    public void showCustomDialog() {
        Parent.dialog = new Dialog(this, android.R.style.Theme_Translucent);
        Parent.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Parent.dialog.setCancelable(true);
        Parent.dialog.setContentView(R.layout.layout_dialog);
        ((ImageView)  Parent.dialog.findViewById(R.id.loader)).startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate));
        Parent.dialog.getWindow().setBackgroundDrawable( new ColorDrawable(0x7f000000));
        Parent.dialog.show();
    }


    @Override
    public void onResume(){
        super.onResume();
        isPaused=false;

        //Toast.makeText(getApplicationContext(), "Resumed", Toast.LENGTH_LONG).show();



     //if(Parent.user_never_logout==1) {
     //    Parent.data_clear_by_android = "false";



//showCustomDialog();


       //    }


      if(  !Parent.settingsList.isEmpty()) {
          if (Parent.settingsList.get(0).getlock_volume_botton() != 0) {
              audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                      Parent.settingsList.get(0).getlock_volume_botton(), AudioManager.FLAG_SHOW_UI);
          }
      }



        if(Parent.parentModelList.isEmpty()){

        }
        else {
            check_subscription();
        }



        setDrawOverOtherAppsCheck();
    }



    void bringback(){
      if(Parent.isParent==false) {

         // taskid=getTaskId();
          ActivityManager acti = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
          acti.moveTaskToFront(taskid, ActivityManager.MOVE_TASK_NO_USER_ACTION);
        // getWindow().closeAllPanels();


      }
    }








    @Override
    protected  void onPause(){
        super.onPause();

       // Toast.makeText(MainActivity.this, "PAUSED", Toast.LENGTH_SHORT).show();
        isPaused=true;


    }


            @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//change();
               // Toast.makeText(MainActivity.this, "PAUSED", Toast.LENGTH_SHORT).show();



        setContentView(R.layout.activity_main3);
        this.parentview = findViewById(R.id.house);


               Parent.task_id=getTaskId();
                method = new com.playzone.kidszone.Method(MainActivity.this);
                audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);






               // init_unity();
                init();
getAppStatus();



    }

    void init(){


        new Thread(new Runnable() {
            @Override
            public void run() {

                parentview = getWindow().getDecorView();

                Parent. pm = getPackageManager();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


                        parentview.setOnSystemUiVisibilityChangeListener
                                (new View.OnSystemUiVisibilityChangeListener() {
                                    @Override
                                    public void onSystemUiVisibilityChange(int visibility) {
                                        // Note that system bars will only be "visible" if none of the
                                        // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
                                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                                            // TODO: The system bars are visible. Make any desired
                                            // adjustments to your UI, such as showing the action bar or
                                            // other navigational controls.
                                            //  Toast.makeText(getApplicationContext(), "Visible", Toast.LENGTH_LONG).show();
                                            if (!Parent.isTyping) {
                                                if (Parent.full_screen) {
                                                    hideSystemUI();
                                                }
                                            }
                                        } else {
                                            // TODO: The system bars are NOT visible. Make any desired
                                            // adjustments to your UI, such as hiding the action bar or
                                            // other navigational controls.
                                            //   Toast.makeText(getApplicationContext(), "Not Visible", Toast.LENGTH_LONG).show();

                                        }
                                    }
                                });


                        if (Parent.full_screen) {
                            hideSystemUI();
                        }

                     /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            MainActivity.super.requestAppPermissions(new
                                            String[]{

                                            Manifest.permission.CHANGE_COMPONENT_ENABLED_STATE,
                                            Manifest.permission.WRITE_SETTINGS,
                                            Manifest.permission.BIND_APPWIDGET,
                                            Manifest.permission.SYSTEM_ALERT_WINDOW,
                                            Manifest.permission.FOREGROUND_SERVICE,
                                            Manifest.permission.KILL_BACKGROUND_PROCESSES,
                                            Manifest.permission.READ_EXTERNAL_STORAGE,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE,


                                    }
                                    , R.string.message, REQUEST_PERMISSIONS);




                        }

*/
                    }
                });
            }
        }).start();



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            RxPermissions rxPermission = new RxPermissions(MainActivity.this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                rxPermission
                        .requestEach(Manifest.permission.CHANGE_COMPONENT_ENABLED_STATE,
                                Manifest.permission.WRITE_SETTINGS,
                                Manifest.permission.BIND_APPWIDGET,
                                Manifest.permission.FOREGROUND_SERVICE,
                                Manifest.permission.KILL_BACKGROUND_PROCESSES,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.RECEIVE_BOOT_COMPLETED
                        )
                        .subscribe(new Consumer<Permission>() {
                            @Override
                            public void accept(Permission permission) throws Exception {
                                if (permission.granted) {
                                    // user has agreed to the authority
                                    System.out.println(permission.name + " is granted.");
                                   // Log.d("TAG", permission.name + " is granted.");
                                } else if (permission.shouldShowRequestPermissionRationale) {
                                    // When the user rejects the authority, do not select "Do not ask" (Never ask again), then the next time you start again, the dialog box will prompt requesting permission
                                   // Log.d("TAG", permission.name + " is denied. More info should be provided.");
                                    System.out.println(permission.name + " is denied. More info should be provided.");

                                } else {
                                    // User denied this privilege, and select "Do not ask me."
                                  //  Log.d("TAG", permission.name + " is denied.");
                                    System.out.println(permission.name + " is denied.");
                                }
                            }
                        });
            }
        }
              /*  PowerManager  powerManager =(PowerManager)getSystemService(POWER_SERVICE);
                Parent.wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                        "MyApp::Kidszone"); */






     /*   if(method.isNetworkAvailable()){
            AppLovinSdk.getInstance( this ).setMediationProvider( "max" );
            AppLovinSdk.initializeSdk( this, new AppLovinSdk.SdkInitializationListener() {
                @Override
                public void onSdkInitialized(final AppLovinSdkConfiguration configuration)
                {
                    // AppLovin SDK is initialized, start loading ads
                }
            } );
        }

*/





        try {

            screenSize = getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK;


            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                       startForegroundService(new Intent(getApplicationContext(), BackgroundService2.class)
                               .putExtra("type", "first")
                        );


                        startForegroundService(new Intent(getApplicationContext(), BackgroundService.class)
                                .putExtra("type", "first")
                        );


                    } else {
                         startService(new Intent(getApplicationContext(), BackgroundService2.class)
                                .putExtra("type", "first"));

                        startService(new Intent(getApplicationContext(), BackgroundService.class)
                                .putExtra("type", "first"));

                    }

                }
            }).start();



        } catch (Exception m) {
            //  Toast.makeText(getApplicationContext(), m.toString(), Toast.LENGTH_LONG).show();
            //toast(m.toString());
            System.out.println(m.toString());
        }






            if (Parent.isParentAvailable) {

                if (method.pref.getString(com.playzone.kidszone.Method.isLoggedin, "false").equals("true")) {


                    if (Parent.restart_main == 1) {
                        Parent.restart_main = 0;
                        changeFragment(new security());

                    }
                    else if (Parent.restart_main == 2) {
                        Parent.restart_main = 0;
                        changeFragment(new Parent_control());

                    }
                    else if (Parent.restart_main == 3) {
                        Parent.restart_main = 0;
                        changeFragment(new Add_child());

                    }else if (Parent.restart_main == 10) {
                        Parent.restart_main = 0;
                        changeFragment(new Insert_child());

                    }else if (Parent.restart_main == 11) {
                        Parent.restart_main = 0;
                        changeFragment(new User_account_screen());

                    } else {
                        changeFragment(new User_account_screen());
                    }
                }
                else{
                    changeFragment(new Login());
                }
        }

            else {
            changeFragment(new Login());

        }


    }












    void schedule_AlarmManager(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(this, BackgroundService2.class);

            PendingIntent pendingIntent = PendingIntent.getService(
                    this.getApplicationContext(), 234, intent, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);




            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + 60 * 1000,
                    60 * 1000, pendingIntent);


            //EVERY ONE MINUTE
/*
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() +
                            60 * 1000,
                    pendingIntent);

*/
            StyleableToast.makeText(getApplicationContext(),  "Watch Master Active",
                    Toast.LENGTH_LONG, R.style.mytoast2).show();        }


    }



    public void init_payment(boolean value, Activity act, String c){

        final double amount_1 = 1.8;
        final double amount_2 = 23.03;
        String email = Parent.parentModelList.get(0).getP_email();
        String fName = Parent.parentModelList.get(0).getPname();
        String lName = "";
        String narration = "payment for Pro App Version";
        txRef=  UUID.randomUUID().toString();
        String country = "NG";
        String currency = "NGN";
        String currency2 = "USD";
        final String publicKey = "FLWPUBK-fe85f9a6ecee1630d0d7c18f47cf96fd-X"; //Get your public key from your account
        final String encryptionKey = "95e56a38d07f5bec9a8ee528"; //Get your encryption key from your account


        if(value) {
            new RaveUiManager(act)
                    .setAmount(amount_1)
                    .setCountry(country)
                    .setCurrency(c)
                    .setEmail(email)
                    .setfName(fName)
                    .setlName(lName)
                    .setPaymentPlan("49514")  //monthly
                    .setNarration(narration)
                    .setPublicKey(publicKey)
                    .setEncryptionKey(encryptionKey)
                    .setTxRef(txRef)
                    .acceptCardPayments(true)
                    .acceptUssdPayments(true)
                    .acceptAccountPayments(true)

                    /*  .acceptMpesaPayments(true)
                      .acceptAchPayments(true)
                      .acceptGHMobileMoneyPayments(true)
                      .acceptUgMobileMoneyPayments(true)
                      .acceptMpesaPayments(true)
                      .acceptAchPayments(true)
                      .acceptGHMobileMoneyPayments(true)
                      .acceptUgMobileMoneyPayments(true)
                      .acceptZmMobileMoneyPayments(true)
                      .acceptRwfMobileMoneyPayments(true)
                      .acceptSaBankPayments(true)
                      .acceptUkPayments(true)
                      .acceptBankTransferPayments(true)
                      .acceptUssdPayments(true)
                      .acceptBarterPayments(true) */
                    // .acceptFrancMobileMoneyPayments(true)
                    .onStagingEnv(false)
                    .showStagingLabel(false)

                    .allowSaveCardFeature(true)
                    // .setMeta(List<Meta>)
                    .withTheme(R.style.DefaultTheme)
                    // .isPreAuth(true)

                    //  .setSubAccounts(List<SubAccount>)
                    .shouldDisplayFee(true)
                    // .onStagingEnv(false)

                    .initialize();
        }
        else if(!value){
            new RaveUiManager(act)
                    .setAmount(amount_2)
                    .setCountry(country)
                    .setCurrency(c)
                    .setEmail(email)
                    .setfName(fName)
                    .setlName(lName)
                    .setPaymentPlan("49513")  //yearly
                    .setNarration(narration)
                    .setPublicKey(publicKey)
                    .setEncryptionKey(encryptionKey)
                    .setTxRef(txRef)
                    .acceptCardPayments(true)

                    .acceptUssdPayments(true)

                    .acceptAccountPayments(true)


                    .allowSaveCardFeature(true)

                    .withTheme(R.style.DefaultTheme)
                    .onStagingEnv(false)
                    .showStagingLabel(false)
                    .shouldDisplayFee(true)
                    .initialize();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


      //  Toast.makeText(this, "code =" + requestCode, Toast.LENGTH_LONG).show();

        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            String message = data.getStringExtra("response");




            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
showCustomDialog();
               // data.toString();
                JSONObject jsonObject = null;
               // Toast.makeText(this, message
               //         , Toast.LENGTH_SHORT).show();


                try {
                    jsonObject = new JSONObject(message);


                JSONArray jsonArray = new JSONArray();

                jsonArray.put(jsonObject);


                   JSONObject jsonObject2 = jsonArray.getJSONObject(0);

                    JSONArray jsonArray2 = new JSONArray();

                    jsonArray2.put(jsonObject2.get("data"));

                  //  Toast.makeText(this, jsonArray2.getJSONObject(0) + ""
                  //   , Toast.LENGTH_SHORT).show();


                   // System.out.println("the info = " + jsonArray2.getJSONObject(0));

                for (int i = 0; i < jsonArray2.length(); i++) {

                    JSONObject object = jsonArray2.getJSONObject(i);
                    String success = object.getString("status");
                    String email = object.getString("customer.email");
                    String plan_name = object.getString("paymentPlan");
                    String pay_type = object.getString("paymentType");
                    String pay_id = object.getString("paymentId");
                    String currency = object.getString("currency");
                    String amount = object.getString("amount");
                    String ref = object.getString("txRef");
                    String ref2 = object.getString("flwRef");
                    String account_id = object.getString("customer.AccountId");
                    String createdAt = object.getString("customer.createdAt");
                    String updatedAt = object.getString("customer.updatedAt");

                   /* Toast.makeText(this, success  + " amount :" + amount + " Ref :" + ref+
                                    " pay type :" + pay_type
                            , Toast.LENGTH_SHORT).show();
*/
                    // System.out.println(success  + " amount :" + amount + " Ref :" + ref+
                    //         " pay type :" + pay_type);
 //System.out.println("data ==" + jsonArray2.toString());

                     CONFIRM(email, txRef,new Fragment(), MainActivity.this, plan_name);


                }


                   // Toast.makeText(this, jsonArray.toString()
                          //  , Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();


                }
            }
            else if (resultCode == RavePayActivity.RESULT_ERROR) {
              //  Toast.makeText(this, "ERROR " + message, Toast.LENGTH_SHORT).show();
                //method.alertBox(message);
                JSONObject jsonObject = null;
                // Toast.makeText(this, message
                //         , Toast.LENGTH_SHORT).show();


                try {
                    jsonObject = new JSONObject(message);


                    JSONArray jsonArray = new JSONArray();

                    jsonArray.put(jsonObject);


                    JSONObject jsonObject2 = jsonArray.getJSONObject(0);

                    JSONArray jsonArray2 = new JSONArray();

                    jsonArray2.put(jsonObject2.get("data"));
                    JSONObject object = jsonArray2.getJSONObject(0);
                    String success = object.getString("vbvrespmessage");
                    method.alertBox(success, 1);

                }catch (Exception d){

                }
                }
            else if (resultCode == RavePayActivity.RESULT_CANCELLED) {
               // Toast.makeText(this, "CANCELLED " + message, Toast.LENGTH_SHORT).show();
               // method.alertBox(message);

                JSONObject jsonObject = null;
                // Toast.makeText(this, message
                //         , Toast.LENGTH_SHORT).show();


                try {
                    jsonObject = new JSONObject(message);


                    JSONArray jsonArray = new JSONArray();

                    jsonArray.put(jsonObject);


                    JSONObject jsonObject2 = jsonArray.getJSONObject(0);

                    JSONArray jsonArray2 = new JSONArray();

                    jsonArray2.put(jsonObject2.get("data"));
                    JSONObject object = jsonArray2.getJSONObject(0);
                    String success = object.getString("vbvrespmessage");
                    method.alertBox(success, 1);

                }catch (Exception d){

                }
            }
        }
        else if((requestCode == 578)){


           set_second_selection();


        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }




    public void set_second_selection() {
        try {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            // alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setIcon(R.drawable.done);
            LayoutInflater inflater = getLayoutInflater();
            final View dialog = inflater.inflate(R.layout.set_permissions6, null);

            if (Build.VERSION.SDK_INT >= 21) {
                alertDialogBuilder.setView(dialog);
                final AlertDialog alert = alertDialogBuilder.create();



                ((Button) dialog.findViewById(R.id.launcher)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alert.dismiss();

                        PackageManager packageManager = getPackageManager();
                        ComponentName componentName = new ComponentName(MainActivity.this, FakeLauncher.class);
//disable first

                        packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);


                        Intent selector = new Intent(Intent.ACTION_MAIN);
                        selector.addCategory(Intent.CATEGORY_HOME);
                        selector.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(selector);



                       // alert.cancel();
                    }
                });


alert.show();
            }

        } catch (Exception f) {

        }
    }






    public void check_subscription(){
        if(method.isNetworkAvailable()) {
            CHECK_SUB(Parent.pemail, new Fragment(), MainActivity.this);
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
                        Parent.dbhelper.UpdateSubscribeParent("expired",Parent.parentModelList.get(0).getP_email());


                    } else {
                        //Toast.makeText(this, list.get(k).getItem_name() + "= not expired", Toast.LENGTH_LONG).show();

                       // method.alertBox("Not Expired", 2);

                        Parent.parentModelList.get(0).setSubscribed("true");
                        Parent.dbhelper.UpdateSubscribeParent("true",Parent.parentModelList.get(0).getP_email());

                    }



                    int old_count=Integer.parseInt( Parent.in_reg_stats.get(0).getOffline_count());
                   int sum_count = old_count + 1;
                   String sum = String.valueOf(sum_count);

                    Parent.dbhelper.addIsRegInternet(new Internet_Reg_stats_model(Parent.in_reg_stats.get(0).getHas_reg(),
                            Parent.in_reg_stats.get(0).getTimes(), Parent.in_reg_stats.get(0).getOffline_changes(),
                            sum));



                } catch (Exception f) {
                   // Toast.makeText(MainActivity.this, f.toString(), Toast.LENGTH_LONG).show();

                }

            }

        }
    }


    public void CONFIRM(final String sendEmail, String ref,final Fragment frag, final Activity act, final String planid) {

        final JsonObject jsObj = (JsonObject) new Gson().toJsonTree(new API(frag));
        jsObj.addProperty("email", sendEmail);
        jsObj.addProperty("ref", ref);


        jsObj.addProperty("method_name", "confirm_payment");
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

                                    String startdate = object.getString("start_date");
                                    String enddate = object.getString("expire_date");

                                    Parent.dialog.dismiss();
                                       // StyleableToast.makeText(act, msg,
                                   Long elapsed_time= SystemClock.elapsedRealtime();

                                    Parent.parentModelList.get(0).setEnd_date(enddate);
                                    Parent.parentModelList.get(0).setElapsed_time(elapsed_time);
                                    Parent.parentModelList.get(0).setStart_date(startdate);
String duration=null;
String packagename = null;
                                    if(planid.equals("11128")){//yearly
                                       duration= "12 months";
                                       packagename = "Yearly";
                                    }
                                    else if(planid.equals("11129")){ //monthly
                                        duration= "1 month";
                                        packagename = "Monthly";

                                    }

//method.alertBox( duration + " " +  packagename,2);

   Parent.dbhelper.UpdateSubscribeParent("true",Parent.parentModelList.get(0).getP_email(),
           elapsed_time, enddate, startdate, duration, packagename);
                                    //       Toast.LENGTH_LONG, R.style.mytoast3).show();

method.alertBox(msg, 2);


                                }
                                else {
                                    Parent.dialog.dismiss();
                                    Long elapsed_time= SystemClock.elapsedRealtime();
            Parent.dbhelper.UpdateSubscribeParent("false",Parent.parentModelList.get(0).getP_email(), elapsed_time,
                    "01/01/1992", new Date().toString(), "0", "none");

                                    // method.alertBox(msg);
                                  //  StyleableToast.makeText(act, msg,
                                   //         Toast.LENGTH_LONG, R.style.mytoast3).show();

                                    method.alertBox(msg, 1);

                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Parent.dialog.dismiss();
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
                    StyleableToast.makeText(act, "Bad network, Please retry",
                            Toast.LENGTH_LONG, R.style.mytoast3).show();


                    ///still give value and store in local DB


                    //method.alertBox("Failed, please retry");
                } catch (Exception f) {
                    Parent.dialog.dismiss();
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
    Parent.dbhelper.UpdateSubscribeParent("false",Parent.parentModelList.get(0).getP_email(),
            elapsed_time, enddate, startdate, duration, packagename);
}
else if(msg.equals("expired")){
    Parent.dbhelper.UpdateSubscribeParent("expired",Parent.parentModelList.get(0).getP_email(),
            elapsed_time, enddate, startdate, duration, packagename);
}
else if(msg.equals("active")){
    Parent.dbhelper.UpdateSubscribeParent("true",Parent.parentModelList.get(0).getP_email(),
            elapsed_time, enddate, startdate, duration, packagename);
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


    public void getserverdate() {

        final JsonObject jsObj = (JsonObject) new Gson().toJsonTree(new API(new Fragment()));


        jsObj.addProperty("method_name", "getdate");
        jsObj.addProperty("package_name", API.package_name);
        jsObj.addProperty("sign", API.sign);
        jsObj.addProperty("salt", API.salt);

        RequestQueue mRequestQueue;


        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
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


    public  void getAppStatus() {

        final JsonObject jsObj = (JsonObject) new Gson().toJsonTree(new API(new Fragment()));


        jsObj.addProperty("method_name", "getappstatus");
        jsObj.addProperty("package_name", API.package_name);
        jsObj.addProperty("sign", API.sign);
        jsObj.addProperty("salt", API.salt);

        RequestQueue mRequestQueue;


        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
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
                               // String msg = object.getString("msg");

                                if (success.equals("1")) {

                                    String status = object.getString("status");
                                    String code = object.getString("code");
                                    String verson_name = object.getString("name");
                                    //method.alertBox("was successful");
                                    String version=null;
                                    int verCode=0;
                                    try {
                                        PackageInfo pInfo =getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
                                        version = pInfo.versionName;
                                        verCode = pInfo.versionCode;
                                    } catch (PackageManager.NameNotFoundException e) {
                                        e.printStackTrace();
                                    }




                                    if(code.equals("0") && !(version.equals(verson_name))) {
                                        method.alertBox2(status, MainActivity.this, false, true);

                                    }
                                    else if(code.equals("ok") || version.equals(verson_name)){

                                    }
                                    else if(!(version.equals(verson_name))){
                                        method.alertBox2(status, MainActivity.this, true, false);

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

    public void load_inbuildApps(){
        try{
   Uri url = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
            "://" + getResources().getResourcePackageName(R.drawable.fruit1_small)
            + '/' + getResources().getResourceTypeName(R.drawable.fruit1_small) + '/' +
            getResources().getResourceEntryName(R.drawable.fruit1_small));

    Drawable draw;

        InputStream in = getContentResolver().openInputStream(url);
        draw= Drawable.createFromStream(in, url.toString());

          //  Parent.inbuildApp.add(new AppListModel("Nino's Browser", draw, "browser", true));

        if(!Parent.selectedApp.contains(Parent.inbuildApp)) {

           // Parent.selectedApp.add(new AppListModel("Nino's Browser", draw, "browser", true));

        }


        //done to ensure unique values
            Set<AppListModel> s = new LinkedHashSet<AppListModel>(Parent.selectedApp);




            Parent.selectedApp.clear();
            Parent.inbuildApp.clear();

            Parent.selectedApp.addAll(s);

        }
    catch(Exception d){

    }


}

    public void load_bg(){
        try{
            Uri url = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                    "://" + getResources().getResourcePackageName(R.drawable.fruit1_small)
                    + '/' + getResources().getResourceTypeName(R.drawable.fruit1_small) + '/' +
                    getResources().getResourceEntryName(R.drawable.fruit1_small));

            Drawable draw;

            InputStream in = getContentResolver().openInputStream(url);
            draw= Drawable.createFromStream(in, url.toString());

            //  Parent.inbuildApp.add(new AppListModel("Nino's Browser", draw, "browser", true));

            if(!Parent.selectedApp.contains(Parent.inbuildApp)) {

                // Parent.selectedApp.add(new AppListModel("Nino's Browser", draw, "browser", true));

            }


            //done to ensure unique values
            Set<AppListModel> s = new LinkedHashSet<AppListModel>(Parent.selectedApp);




            Parent.selectedApp.clear();
            Parent.inbuildApp.clear();

            Parent.selectedApp.addAll(s);

        }
        catch(Exception d){

        }


    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {

            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                   if( Parent.settingsList.get(0).getlock_volume_botton()==0) {
                       audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
                   }

                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {

                    try {
                        if (Parent.settingsList.get(0).getlock_volume_botton() == 0) {
                            audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
                        }
                    }catch (Exception fd){

                    }
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }


/*
    void startWorkManager(){
        Parent.periodic=new PeriodicWorkRequest.Builder(Myworker2.class, 15, TimeUnit.MINUTES)
              //  .setInitialDelay(1, TimeUnit.MINUTES)
                // .setConstraints(constraint)
                .addTag("Kids_zone_work")
                .build();

        WorkManager.getInstance().enqueue(Parent.periodic);






        WorkManager.getInstance().getWorkInfoByIdLiveData(Parent.periodic.getId())
                .observe(this, workStatus -> {
                    if (workStatus.getState() == WorkInfo.State.SUCCEEDED) {

                       // StyleableToast.makeText(getApplicationContext(),  workStatus.getState().toString(),
                        //        Toast.LENGTH_LONG, R.style.mytoast2).show();

                        StyleableToast.makeText(getApplicationContext(),  "Watch Master Active",
                                Toast.LENGTH_LONG, R.style.mytoast2).show();
                        // restart_worker();
                    }
                    else if(workStatus.getState() == WorkInfo.State.FAILED){

                        StyleableToast.makeText(getApplicationContext(),  workStatus.getState().toString(),
                                Toast.LENGTH_LONG, R.style.mytoast2).show();



                    }
                    else if(workStatus.getState() == WorkInfo.State.CANCELLED){

                        StyleableToast.makeText(getApplicationContext(),  workStatus.getState().toString(),
                                Toast.LENGTH_LONG, R.style.mytoast2).show();

                     //  restart_worker();

                    }
                    else if(workStatus.getState() == WorkInfo.State.BLOCKED){

                        StyleableToast.makeText(getApplicationContext(),  workStatus.getState().toString(),
                               Toast.LENGTH_LONG, R.style.mytoast2).show();

                       // restart_worker();

                    }
                    else if(workStatus.getState() == WorkInfo.State.ENQUEUED){

                        StyleableToast.makeText(getApplicationContext(), "Watch Master Queued",
                               Toast.LENGTH_LONG, R.style.mytoast2).show();

                       // restart_worker();

                    }

                });



    }

*/




    public void changeFragment(Fragment targetFragment){

        try {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmain, targetFragment, "fragment")
                  //  .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                 .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)

                    .commit();
        }catch(Exception d){
            Toast.makeText(MainActivity.this
                    , d.toString(), Toast.LENGTH_SHORT).show();
        }
    }







    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        current_focus= hasFocus;
        if (hasFocus) {
if(!Parent.isTyping) {

    if(Parent.full_screen) {
        hideSystemUI();
    }
}
        }
        else{


//serviceTask();

          //  collapseNow();
          //
        }
    }




    private void hideSystemUI() {

      //  getWindow().addFlags(WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY);
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        parentview = getWindow().getDecorView();
        parentview.setSystemUiVisibility(
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

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    public void showSystemUI() {
        parentview = getWindow().getDecorView();
        parentview.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }







    @Override
    public boolean onKeyDown(int keycode, KeyEvent event){
        boolean value= false;
       if(getSupportFragmentManager().findFragmentById(R.id.fragmain) instanceof Login){
           value=true;
           onBackPressed();
       }
        return value;
    }






    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        boolean isAppAllow = false;

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                componentInfo = taskInfo.get(0).topActivity;
            }

            if (componentInfo.getPackageName().equals("com.example.kidszone")) {
                isInBackground = false;
                 isAppAllow=true;
            }
            else {
                for(int d=0; d<=Parent.selectedApp.size()-1; d++){

                    if (Parent.selectedApp.get(d).getPackages().equals(componentInfo.getPackageName())) {
                        isInBackground = true;
                        isAppAllow=true;
                        break;
                    }
                    else{
                        isAppAllow=false;

                    }
                }
        }

        return isAppAllow;
    }
















    /* Advance SECTION Please dont edit

            */





    public String getRunningApps(){
        String toppackage="";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager usage=(UsageStatsManager)getSystemService(USAGE_STATS_SERVICE);

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, -1);
            List<UsageStats> stats =usage.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, cal.getTimeInMillis(),
                    System.currentTimeMillis());
            if(stats != null){
                SortedMap<Long, UsageStats> mystore = new TreeMap<Long, UsageStats>();
                for(UsageStats usageStats : stats){
                    mystore.put(usageStats.getLastTimeUsed(), usageStats);
                }

                if(mystore != null && !mystore.isEmpty()){
                    toppackage = mystore.get(mystore.lastKey()).getPackageName();
                }
            }

        }


        if (toppackage.equals("com.example.kidszone")) {
            Parent.isParent=true;
            Parent.package_name="Allowed";
          //  Toast.makeText(getApplicationContext(), "App =  "status= " + package_name, Toast.LENGTH_LONG).show();

        }
        else if(toppackage.equals("com.google.android.permissioncontroller")){
            Parent.isParent=true;
            Parent.package_name="Allowed";
        }
        else {
            Parent.isParent=false;

            for (int d = 0; d <= Parent.selectedApp.size() - 1; d++) {

                if (Parent.selectedApp.get(d).getPackages().equals(toppackage)) {
                    Parent.package_name = "Allowed";
                    break;
                }
                else {
                    Parent. package_name = "Not Allowed";


                }
            }

            if(Parent.package_name.equals("Not Allowed")){

                bringback();
             //   Toast.makeText(getApplicationContext(), toppackage + " has been blocked by Kidzone", Toast.LENGTH_LONG).show();
              // toast(toppackage);
                new StyleableToast
                        .Builder(getApplicationContext())
                        .text(toppackage + " has been blocked by Kidzone")
                        .textColor(Color.WHITE)
                        .backgroundColor(Color.BLUE)

                        .show();


            }
        }



        return toppackage;

    }





    void toast(final String info) {

        try {
            Handler handle = new Handler(Looper.getMainLooper());
            handle.post(new Runnable() {
                @Override
                public void run() {

                    //  Toast.makeText(getApplicationContext(), info, Toast.LENGTH_LONG).show();
                    new StyleableToast
                            .Builder(getApplicationContext())
                            .text(info + " has been blocked by Kidzone")
                            .textColor(Color.WHITE)
                            .backgroundColor(Color.BLUE)
                            .show();

                    bringback();
                }
            });
        }
        catch (Exception d){

        }
    }






public static void cancelTask(){
    if(!asyncTask.isCancelled() ){
        asyncTask.cancel(true);
    }
}


    private void serviceTask() {
       asyncTask = new MyTask();

        asyncTask.execute();
    }


    class MyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                for (; ; ) {
                    TimeUnit.SECONDS.sleep(Parent.SERVICE_TASK_TIMEOUT_SEC);

                    // check if performing of the task is needed
                    if (isCancelled()) {
                        break;
                    }

                    // Initiating of onProgressUpdate callback that has access to UI
                    publishProgress();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... progress) {
            super.onProgressUpdate(progress);
getRunningApps();

        }


    }



    private void schedule(){
        ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor();

        schedule.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                getRunningApps();

            }
        }, 0, 500, TimeUnit.MILLISECONDS);
    }



    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

            Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragmain);

            if (f instanceof selected_app_frag) {


            }else if (f instanceof List_Installed_frag) {


                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.fragmain,new Parent_control());
                // transaction.addToBackStack(null);
                transaction.commit();
            }

            else if (f instanceof Recover) {

                if (getCurrentFocus() != null) {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                    transaction.replace(R.id.fragmain,new Login());
                    // transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
            else if (f instanceof Login) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setIcon(R.drawable.ic_action_done);
                alertDialogBuilder.setMessage("Do You Wish To Exit ?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                stopService(new Intent(MainActivity.this, BackgroundService2.class));
                               stopService(new Intent(MainActivity.this, BackgroundService.class));


                                NotificationManager mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                                mNotificationManager.cancel(1992);
                                mNotificationManager.cancel(1993);

                                // util.job.cancelAll();

                                Intent intent = new Intent(MainActivity.this, BackgroundService2.class);

                                PendingIntent pendingIntent = PendingIntent.getService(
                                        MainActivity.this, 234, intent, 0);


                                Intent intent2 = new Intent(MainActivity.this, BackgroundService.class);

                                PendingIntent pendingIntent2 = PendingIntent.getService(
                                        MainActivity.this, 2345, intent, 0);

                                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                                alarmManager.cancel(pendingIntent);
                                alarmManager.cancel(pendingIntent2);

                                // MainActivity.cancelTask();

                              getPackageManager().clearPackagePreferredActivities(getPackageName());
finish();
                                System.exit(0);


                            }
                        });
                alertDialogBuilder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                                return;

                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }

            else {
                super.onBackPressed();
                // handle by activity

            }
        }






void setAlarmManager(){
    Intent intent = new Intent(this, BackgroundService2.class);

    PendingIntent pendingIntent = PendingIntent.getService(
            this.getApplicationContext(), 234, intent, 0);

    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        //EVERY ONE MINUTE


        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() +  60 * 1000,
                60 * 1000, pendingIntent);

/*

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() +
                        60 * 1000,
                pendingIntent);

 */
    }
    else{
       // startWorkManager();
    }

}







/*
    void restart_worker(){

        try {
            //  WorkManager.getInstance().cancelAllWork();

            Parent.workRequest = new OneTimeWorkRequest.Builder(Myworker.class)
                    .setInitialDelay(1, TimeUnit.MINUTES)
                    //  .setConstraints(constraint)
                    .addTag("Kids_zone")
                    .build();

            WorkManager.getInstance().enqueue(Parent.workRequest);
            // WorkManager.getInstance().enqueue(Parent.workRequest_idle);
            StyleableToast.makeText(getApplicationContext(),  "Watch Master Active",
                    Toast.LENGTH_LONG, R.style.mytoast2).show();
        }
        catch (Exception df){
            Toast.makeText(getApplicationContext(), df.toString(), Toast.LENGTH_LONG).show();
        }

    }
*/


    public void collapseNow() {

        // Initialize 'collapseNotificationHandler'
        if (collapseNotificationHandler == null) {
            collapseNotificationHandler = new Handler();
        }
        else {
            // If window focus has been lost && activity is not in a paused state
            // Its a valid check because showing of notification panel
            // steals the focus from current activity's window, but does not
            // 'pause' the activity
            //   if (!current_focus && !isPaused) {

            // Post a Runnable with some delay - currently set to 300 ms

            // Post a Runnable with some delay - currently set to 300 ms
            collapseNotificationHandler.postDelayed(new Runnable() {

                @Override
                public void run() {

                    // Use reflection to trigger a method from 'StatusBarManager'

                    @SuppressLint("WrongConstant") Object statusBarService = getSystemService("statusbar");
                    Class<?> statusBarManager = null;

                    try {
                        statusBarManager = Class.forName("android.app.StatusBarManager");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    Method collapseStatusBar = null;


                    // Prior to API 17, the method to call is 'collapse()'
                    // API 17 onwards, the method to call is `collapsePanels()`
                    try {

                        // Prior to API 17, the method to call is 'collapse()'
                        // API 17 onwards, the method to call is `collapsePanels()`

                        if (Build.VERSION.SDK_INT > 16) {
                            collapseStatusBar = statusBarManager.getMethod("collapsePanels");
                        } else {
                            collapseStatusBar = statusBarManager.getMethod("collapse");
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }

                    collapseStatusBar.setAccessible(true);

                    try {
                        collapseStatusBar.invoke(statusBarService);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    // Check if the window focus has been returned
                    // If it hasn't been returned, post this Runnable again
                    // Currently, the delay is 100 ms. You can change this
                    // value to suit your needs.
                    // if (!current_focus && !isPaused) {
                    collapseNotificationHandler.postDelayed(this, 100L);
                    // }

                }
            }, 200L);
            //  }
        }
    }



    public void ComputeAccessTime(String id, String accesstype, boolean whole, boolean single, int pos){
        String value="active";

        String  stime="";
        String  etime = "";
        Parent.access_type="unlimited";
        // Toast.makeText(con, "whole=  " + whole + "  Single= " + single, Toast.LENGTH_SHORT).show();


        if(whole){


            if(accesstype.equalsIgnoreCase("unlimited")){
                value="active";
                Parent.access_type="unlimited";

            }
            else if(accesstype.equalsIgnoreCase("prohibited")){
                value="expired";
            }
            else if(accesstype.equalsIgnoreCase("Restricted")){
                Parent.access_type="timed";

                stime=  Parent.childModelList.get(pos).getStart_time();
                etime=   Parent.childModelList.get(pos).getEnd_time();

                String pattern ="HH:mm";
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                String currentTime = new SimpleDateFormat("HH:mm").format(new Date());

                try{
                    Date dateStart = sdf.parse(stime);
                    Date dateEnd = sdf.parse(etime);
                    Date curent = sdf.parse(currentTime);

                    if(dateStart.compareTo(curent)<0 && dateEnd.compareTo(curent)<0){

                        value="expired";
                    }
                    else if(curent.compareTo(dateStart)<0){
                        //current date  is behind start date
                        //login time has not reached-- Expired
                        value="expired";
                    }
                    else if(curent.compareTo(dateStart)>=0 && curent.compareTo(dateEnd)<=0){
                        //login time is active
                        value="active";
                    }
                    else if(curent.compareTo(dateEnd)>0){
                        //start date is behind current date
                        //login time has not reached-- Expired
                        value="expired";
                    }
                    else if(curent.compareTo(dateEnd)==0){
                        //start date is behind current date
                        //login time has not reached-- Expired
                        value="expired";
                    }

                    else if(curent.compareTo(dateEnd)<=0 && curent.compareTo(dateStart)>0){
                        //start date is behind current date
                        //login time has not reached-- Expired
                        value="active";
                    }
                }
                catch (Exception f){
                   // Toast.makeText(getApplicationContext(), f.toString(), Toast.LENGTH_SHORT).show();
                }
            }

        }



        else if(single){

            String Accesst="";
            Calendar cal= Calendar.getInstance();
            int today= cal.get(Calendar.DAY_OF_WEEK);

            switch (today){

                case 1:
                    for(int d=0; d<=Parent.sun.size()-1; d++){
                        if(Parent.sun.get(d).getKid_id().equalsIgnoreCase(id)){
                            stime=  Parent.sun.get(d).getStart_time();
                            etime=   Parent.sun.get(d).getEnd_time();
                            Accesst=   Parent.sun.get(d).getAccess_type();
                        }
                    }
                    break;

                case 2:
                    for(int d=0; d<=Parent.mon.size()-1; d++){
                        if(Parent.mon.get(d).getKid_id().equalsIgnoreCase(id)){
                            stime=  Parent.mon.get(d).getStart_time();
                            etime=   Parent.mon.get(d).getEnd_time();
                            Accesst=   Parent.mon.get(d).getAccess_type();
                        }
                    }
                    break;

                case 3:
                    for(int d=0; d<=Parent.tues.size()-1; d++){
                        if(Parent.tues.get(d).getKid_id().equalsIgnoreCase(id)){
                            stime=  Parent.tues.get(d).getStart_time();
                            etime=   Parent.tues.get(d).getEnd_time();
                            Accesst=   Parent.tues.get(d).getAccess_type();
                        }
                    }
                    break;

                case 4:
                    for(int d=0; d<=Parent.wed.size()-1; d++){
                        if(Parent.wed.get(d).getKid_id().equalsIgnoreCase(id)){
                            stime=  Parent.wed.get(d).getStart_time();
                            etime=   Parent.wed.get(d).getEnd_time();
                            Accesst=   Parent.wed.get(d).getAccess_type();
                        }
                    }
                    break;

                case 5:
                    for(int d=0; d<=Parent.thurs.size()-1; d++){
                        if(Parent.thurs.get(d).getKid_id().equalsIgnoreCase(id)){
                            stime=  Parent.thurs.get(d).getStart_time();
                            etime=   Parent.thurs.get(d).getEnd_time();
                            Accesst=   Parent.thurs.get(d).getAccess_type();
                        }
                    }
                    break;

                case 6:
                    for(int d=0; d<=Parent.fri.size()-1; d++){
                        if(Parent.fri.get(d).getKid_id().equalsIgnoreCase(id)){
                            stime=  Parent.fri.get(d).getStart_time();
                            etime=   Parent.fri.get(d).getEnd_time();
                            Accesst=   Parent.fri.get(d).getAccess_type();
                        }
                    }
                    break;

                case 7:
                    for(int d=0; d<=Parent.sat.size()-1; d++){
                        if(Parent.sat.get(d).getKid_id().equalsIgnoreCase(id)){
                            stime=  Parent.sat.get(d).getStart_time();
                            etime=   Parent.sat.get(d).getEnd_time();
                            Accesst=   Parent.sat.get(d).getAccess_type();
                        }
                    }
                    break;
            }

            if(Accesst.equalsIgnoreCase("unlimited")){
                value="active";
                Parent.access_type="unlimited";
            }
            else if(Accesst.equalsIgnoreCase("prohibited")){
                value="expired";
            }
            else if(Accesst.equalsIgnoreCase("Restricted")) {
                Parent.access_type="timed";

                String pattern = "HH:mm";
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                String currentTime = new SimpleDateFormat("HH:mm").format(new Date());

                try {
                    Date dateStart = sdf.parse(stime);
                    Date dateEnd = sdf.parse(etime);
                    Date curent = sdf.parse(currentTime);

                    if (dateStart.compareTo(curent) < 0 && dateEnd.compareTo(curent) < 0) {

                        value = "expired";
                    } else if (curent.compareTo(dateStart) < 0) {
                        //current date  is behind start date
                        //login time has not reached-- Expired
                        value = "expired";
                    } else if (curent.compareTo(dateStart) >= 0 && curent.compareTo(dateEnd) <= 0) {
                        //login time is active
                        value = "active";
                    } else if (curent.compareTo(dateEnd) > 0) {
                        //start date is behind current date
                        //login time has not reached-- Expired
                        value = "expired";
                    }
                    else if(curent.compareTo(dateEnd)==0){
                        //start date is behind current date
                        //login time has not reached-- Expired
                        value="expired";
                    }
                    else if (curent.compareTo(dateEnd) <= 0 && curent.compareTo(dateStart) > 0) {
                        //start date is behind current date
                        //login time has not reached-- Expired
                        value = "active";
                    }
                } catch (Exception f) {
                   // Toast.makeText(getApplicationContext(), "second" + f.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }

       // access_type = Parent.access_type;

        if(Parent.access_type.equalsIgnoreCase("timed")) {

            String pattern = "HH:mm";
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            String currentTimetosend = new SimpleDateFormat("HH:mm").format(new Date());

            try {
                Date dateEnd = sdf.parse(etime);
                Date curent = sdf.parse(currentTimetosend);


                Parent.Remaining_hour = dateEnd.getTime() - curent.getTime();
                remaining_time=Parent.Remaining_hour;

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


       // return  value;

          //  StyleableToast.makeText(this,  Parent.access_type , Toast.LENGTH_LONG, R.style.mytoast).show();

      //  Parent.kid_id = Parent.active_user_details.get(0).getKid_id();

        Parent.Remaining_hour = remaining_time;
       // Parent.access_type = access_type;

        Parent.access_type_per_acc.add(new access_type_model(Parent.kid_id, Parent.access_type));


        //LinkedHashSet<access_type_model> s = new LinkedHashSet(Parent.access_type_per_acc);


        Parent.dbhelper.addAccessType(Parent.access_type_per_acc);


        Parent.OldTotalApp.clear();
      //  Parent.selectedApp_2.clear();
        Parent.OldTotalApp = Parent.installedApps;
        //  PrintStream printStream = System.out;
        // printStream.println("size== " + Parent.selectedApp.size());
       // init();


            Parent.selectedApp_2.clear();
            Parent.selectedApp_3.clear();
            Parent.selectedApp_2 = Parent.dbhelper.getApplist(Parent.kid_id);

     //  startActivity(new Intent(getApplicationContext(), Swipe_home.class));

      //  Parent.dialog.dismiss();

      // finish();
    }



    @Override
    protected void onDestroy() {

        super.onDestroy();
    }


public  void user_alloted_time(String kid){

    if(Parent.childModelList.isEmpty()){
        Parent.childModelList =  Parent.dbhelper.getchildren(Parent.pname);
    }
    else {
        String accesstype=null;
        boolean whole=false;
        boolean single=false;
        int position=0;

        for(int search=0; search<=Parent.childModelList.size()-1; search++){

            if(Parent.childModelList.get(search).getKid_id().equals(kid)){

                accesstype= Parent.childModelList.get(search).getAccess_type();
                whole=  Parent.childModelList.get(search).getWhole_week();
                single= Parent.childModelList.get(search).getSingle_day();
                position=search;

                break;
            }
        }

//StyleableToast.makeText(this, accesstype + " = id =" +  Parent.active_user_details.get(0).getKid_id()
        //        + " whole or single = " + whole +" or " + single, Toast.LENGTH_LONG, R.style.mytoast).show();

        ComputeAccessTime(kid, accesstype, whole, single, position);


    }
}


}

