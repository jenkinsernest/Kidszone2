package com.playzone.kidszone.fragmentpackage;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.playzone.kidszone.Parent;
import com.playzone.kidszone.R;
import com.playzone.kidszone.service.BackgroundService;
import com.playzone.kidszone.service.BackgroundService2;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;


public class dialog extends DialogFragment {


        private EditText mEditText;
          EditText data;
    private List<String> numbers= new ArrayList<>();
    public static int Direction_code=0;

        public dialog() {
            // Empty constructor is required for DialogFragment
            // Make sure not to add arguments to the constructor
            // Use `newInstance` instead as shown below
        }

    @Override
    public void onResume() {
      /*  int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height);
        getDialog().getWindow().setLayout(width, height);
*/
        Window window = getDialog().getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 75% of the screen width
        window.setLayout((int) (size.x * 0.70), (int) (size.y * 0.65));
        window.setGravity(Gravity.CENTER);


        super.onResume();
    }

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.access_control_screen, container);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            // Get field from view
            data= ((EditText)view.findViewById(R.id.pass_data));



            ((Button)view.findViewById(R.id.forgot)).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    data.setText("");
                    numbers.clear();

                    dismiss();

                    Parent.from_dialog=1;

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                    transaction.replace(R.id.fragmain,new Recover());
                    // transaction.addToBackStack(null);
                    transaction.commit();
                }
            });



            ((ImageView)view.findViewById(R.id.clear)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(numbers.size()!=0) {
                        numbers.remove(numbers.size() - 1);
                        String num="";

                        for(String s : numbers) {
                            num= num + s;

                        }
                        data.setText(num);

                    }
                    return;

                }
            });

            ((ImageView)view.findViewById(R.id.done)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(data.getText().toString().equalsIgnoreCase(Parent.MasterPass)) {
                        data.setText("");
numbers.clear();

                        if (Direction_code == 1) {
                            dismiss();
                            Parent.selectedApp_2.clear();

                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                            transaction.replace(R.id.fragmain, new Parent_control());
                            // transaction.addToBackStack(null);
                            transaction.commit();


                        }  else if (Direction_code == 2) {
                            dismiss();

                            User_account_screen screen = new User_account_screen();


                            if (Parent.childModelList.isEmpty()) {
                               CustomErrorLayout();
                            } else {

                                // ChooseChildLayout();
                                screen.ChooseChildLayout();
                            }


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

                            ActivityManager acti = (ActivityManager) getActivity().getApplicationContext()
                                    .getSystemService(Context.ACTIVITY_SERVICE);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                acti.killBackgroundProcesses("com.playzone.kidszone");
                            } else {
                                acti.restartPackage("com.playzone.kidszone");

                            }



                        }
                    }
                    else{
                        // data.setTextColor(Color.RED);
                        data.setError("Wrong Password");
                    }

                }
            });






            ((TextView)view.findViewById(R.id.one)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView tv=(TextView)view;
                    updateView(tv.getText().toString(), data);

                }
            }); ((TextView)view.findViewById(R.id.two)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView tv=(TextView)view;
                    updateView(tv.getText().toString(), data);

                }
            }); ((TextView)view.findViewById(R.id.three)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView tv=(TextView)view;
                    updateView(tv.getText().toString(), data);

                }
            }); ((TextView)view.findViewById(R.id.four)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView tv=(TextView)view;
                    updateView(tv.getText().toString(), data);

                }
            }); ((TextView)view.findViewById(R.id.five)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView tv=(TextView)view;
                    updateView(tv.getText().toString(), data);

                }
            }); ((TextView)view.findViewById(R.id.six)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView tv=(TextView)view;
                    updateView(tv.getText().toString(), data);

                }
            }); ((TextView)view.findViewById(R.id.seven)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView tv=(TextView)view;
                    updateView(tv.getText().toString(), data);

                }
            }); ((TextView)view.findViewById(R.id.eight)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView tv=(TextView)view;
                    updateView(tv.getText().toString(), data);

                }
            }); ((TextView)view.findViewById(R.id.nine)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView tv=(TextView)view;
                    updateView(tv.getText().toString(), data);


                }
            }); ((TextView)view.findViewById(R.id.zero)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView tv=(TextView)view;
                    updateView(tv.getText().toString(), data);

                }
            });







            //getDialog().setTitle("title");

        }




    void updateView(String value, EditText text){
        numbers.add(value);
        String num="";
        for(String s : numbers) {
            num= num + s;
        }


        text.setText(num);

    }




    public void CustomErrorLayout() {
        try {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setCancelable(true);
            alertDialogBuilder.setIcon(R.drawable.done);
            LayoutInflater inflater = getDialog().getLayoutInflater();
            final View dialog = inflater.inflate(R.layout.customerr, null);


                alertDialogBuilder.setMessage("No Child Account Yet");
                alertDialogBuilder.setTitle("Empty Account");


                alertDialogBuilder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        arg0.dismiss();

                    }
                });

                alertDialogBuilder.create().show();


        } catch (Exception e) {
        }
    }



}