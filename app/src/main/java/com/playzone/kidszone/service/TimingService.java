package com.playzone.kidszone.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.Toast;

import com.muddzdev.styleabletoast.StyleableToast;
import com.playzone.kidszone.Parent;
import com.playzone.kidszone.R;
import com.playzone.kidszone.Swipe_home;
import com.playzone.kidszone.logout_alertdialog;

import java.util.concurrent.TimeUnit;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.playzone.kidszone.fragmentpackage.new_home_screen.child_index;

public class TimingService extends Service {
    private static final int FIRST_RUN_TIMEOUT_MILISEC = 2 * 1000;
    private static final int SERVICE_STARTER_INTERVAL_MILISEC = 1 * 1000;
    private static final int SERVICE_TASK_TIMEOUT_SEC = 10;
    private final int REQUEST_CODE = 1;
    private AlarmManager serviceStarterAlarmManager = null;
    private static final String OUR_SECURE_ADMIN_PASSWORD = "12345";
    Handler collapseNotificationHandler;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }





    @Override
    public void onCreate() {
        super.onCreate();
        // Start of timeout-autostarter for our service (watchdog)

       // Toast.makeText(this, "Service Started!", Toast.LENGTH_LONG).show();


    }


    @Override
    public int onStartCommand(Intent intent, int flag, int startid) {

        try {
            Toast.makeText(this, "Timer Service Started!", Toast.LENGTH_LONG).show();
            schedule_AlarmManager();
            timer();

        }
        catch (Exception d){

        }


        return START_STICKY;

    }



    @Override
    public void onDestroy() {
        // performs when user or system kills our service
    //  startServiceStarter();

    }

@Override
public void onTaskRemoved(Intent in){
    super.onTaskRemoved(in);
     }




    void schedule_AlarmManager() {
        long minute = (Parent.Remaining_hour / 1000) / 60;


        if (minute > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Intent intent = new Intent(getApplicationContext(), Swipe_home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(
                        getApplicationContext(), 234567, intent, 0);

                AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);


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
            StyleableToast.makeText(getApplicationContext(), "You Have " + (Parent.time / 1000) / 60 + " Minutes ",
                    Toast.LENGTH_LONG, R.style.mytoast).show();
        } else {
            StyleableToast.makeText(getApplicationContext(), "You Have " + 0 + " Minutes ",
                    Toast.LENGTH_LONG, R.style.mytoast).show();
        }

        Parent.hasCountdown_started = true;

        System.out.println("------TIMER STARTED SUCCESSFULLY------- " + (Parent.time / 1000) / 60 + " Minutes");


        Parent.countertimer = new CountDownTimer(Parent.time, 1000) {

            public void onTick(long millisUntilFinished) {

               // timer.setText("" + (millisUntilFinished / 1000));
               // timer.setVisibility(View.GONE);

                if ((millisUntilFinished / 1000) <= ((Parent.time / 1000) / 2)) {

                    //changing the text color if half time or less the 5 sec
                    if ((millisUntilFinished / 1000) <= ((Parent.time / 1000) / 9)) {
                       // timer.setTextColor(Color.RED);
                       // timer.setVisibility(View.VISIBLE);


                        System.out.println("------TIMER IS ALMOST UP-------");



                      /*  new StyleableToast
                                .Builder(getActivity())
                                .text("Your Time Is Almost Up")
                                .textColor(Color.WHITE)
                                .backgroundColor(Color.RED)
                                .show();
*/

                    } else {

                       // timer.setTextColor(Color.parseColor("#FFBF00"));
                       // timer.setVisibility(View.VISIBLE);


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


                    //timer.setText("Time Up");
                    Parent.time = 0;
                    Parent.Remaining_hour = 0;
                    Parent.hasCountdown_started = false;

                    System.out.println("------TIMES UP-------");


                    try {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            getApplicationContext().startForegroundService(new Intent(getApplicationContext().getApplicationContext(),
                                    foregroundService.class));
                        } else {
                            getApplicationContext().startService(new Intent(getApplicationContext().getApplicationContext(),
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
               // timer.setText("Time Up");
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
                        getApplicationContext().startForegroundService(new Intent(getApplicationContext().getApplicationContext(),
                                foregroundService.class));
                    } else {
                        getApplicationContext().startService(new Intent(getApplicationContext().getApplicationContext(),
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

                Parent.currentActivity.finish();

            }
            else{
                if( Parent.player_activity != null) {
                    Parent.player_activity.finish();
                }
            }





            Intent dial = new Intent(getApplicationContext(), logout_alertdialog.class);
            dial.setFlags(FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(dial);

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
           /* String s=Parent.currentActivity.getClass().getCanonicalName().toString();
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
*/

          /*  if (method.isNetworkAvailable()) {

                SET_USER_DETAILS set = new SET_USER_DETAILS();


                //so we can add reward
                set.updateContentRestriction(Parent.pemail, Parent.kid_id,
                        Parent.childModelList.get(new_home_screen.child_index).getContent_Restriction()
                        , String.valueOf(0),
                        new Fragment(), getActivity());

            }
*/

            if(part3.equals("Parent_games"))
            {
                //  method.alertBox(part3, 2);

                Parent.currentActivity.finish();
                // getActivity().fi
            }
            else if(part3.equals("Swipe_home")) {

                Parent.currentActivity.finish();

            }
            else{
                if( Parent.player_activity != null) {
                    Parent.player_activity.finish();
                }
            }


            Intent dial = new Intent(getApplicationContext(), logout_alertdialog.class);
            getApplicationContext().startActivity(dial);

        } catch (Exception fg) {

            // Toast.makeText(getContext(),"exception= " + fg.toString(), Toast.LENGTH_SHORT).show();
            System.out.println("------exception = " + fg.toString());
        }

    }




























}