package com.playzone.kidszone;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.playzone.kidszone.fragmentpackage.new_home_screen;
import com.playzone.kidszone.models.active_user_model;
import com.playzone.kidszone.service.foregroundService;

import androidx.annotation.Nullable;

public class logout_alertdialog  extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        displayAlert();

    }


    private void displayAlert(){

        try {
            AlertDialog.Builder alertDialogBuilder;

                alertDialogBuilder = new AlertDialog.Builder(this);
alertDialogBuilder.setInverseBackgroundForced(true);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setIcon(R.drawable.opps);
            LayoutInflater inflater = this.getLayoutInflater();
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

                        getApplicationContext().stopService(new Intent(getApplicationContext(), foregroundService.class));

                        NotificationManager mNotificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                        mNotificationManager.cancel(78);

                        Parent.active_user_details.clear();
                        Parent.dbhelper.addActiveUser(new active_user_model(Parent.kid_id, Parent.access_type, "false"));

                        Parent.active_user_details.add(new active_user_model(Parent.kid_id, Parent.access_type, "false"));






                        Parent.childModelList.get(new_home_screen.child_index).setReward_earned(Parent.Reward_time);

                        Parent.dbhelper.UpdateChildReward_eaarned(Parent.Reward_time, Parent.kid_id);

                        Parent.dbhelper.editReward(Parent.Reward_time, Parent.kid_id);



                        String s=Parent.currentActivity.getClass().getCanonicalName().toString();
                        String[] parts = s.split("\\."); // escape .
                        String part1 = parts[0];
                        String part2 = parts[1];
                        String part3 = parts[2];

                        // method.alertBox(Parent.currentActivity.getClass().getCanonicalName().toString(), 2);

                        if(part3.equals("Swipe_home")) {

                            Parent.currentActivity.finish();

                        }
                        else{
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }


                        logout_alertdialog.this.finish();
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

                        getApplicationContext().stopService(new Intent(getApplicationContext(), foregroundService.class));

                        NotificationManager mNotificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                        mNotificationManager.cancel(78);

                        Parent.active_user_details.clear();
                        Parent.dbhelper.addActiveUser(new active_user_model(Parent.kid_id, Parent.access_type, "false"));

                        Parent.active_user_details.add(new active_user_model(Parent.kid_id, Parent.access_type, "false"));



                        // startActivity(new Intent(getActivity(), MainActivity.class));
                        Parent.settingsList.get(0).setPname(Parent.parentModelList.get(0).getPname());
                        Parent.settingsList.get(0).setPemail(Parent.parentModelList.get(0).getP_email());


                        Parent.childModelList.get(new_home_screen.child_index).setReward_earned(Parent.Reward_time);

                        Parent.dbhelper.UpdateChildReward_eaarned(Parent.Reward_time, Parent.kid_id);
                      //  getActivity().finish();




                        String s=Parent.currentActivity.getClass().getCanonicalName().toString();
                        String[] parts = s.split("\\."); // escape .
                        String part1 = parts[0];
                        String part2 = parts[1];
                        String part3 = parts[2];

                        // method.alertBox(Parent.currentActivity.getClass().getCanonicalName().toString(), 2);

                         if(part3.equals("Swipe_home")) {
//if swipe home survivied then kill it
                            Parent.currentActivity.finish();

                        }
                        else{
                            //if none survived restart main activity
                             startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }


                        logout_alertdialog.this.finish();



                    }
                });
                alertDialogBuilder.create().show();
            }

        } catch (Exception e) {
        }
    }
}
