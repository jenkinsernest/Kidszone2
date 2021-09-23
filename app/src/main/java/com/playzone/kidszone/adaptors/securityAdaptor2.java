package com.playzone.kidszone.adaptors;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.muddzdev.styleabletoast.StyleableToast;
import com.playzone.kidszone.FakeLauncher;
import com.playzone.kidszone.MainActivity;
import com.playzone.kidszone.Method;
import com.playzone.kidszone.Parent;
import com.playzone.kidszone.R;
import com.playzone.kidszone.SET_USER_DETAILS;
import com.playzone.kidszone.fragmentpackage.security;
import com.playzone.kidszone.models.SettingsModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


public class securityAdaptor2 extends RecyclerView.Adapter<securityAdaptor2.ViewHolder>{

    public LayoutInflater layoutInflater;
    public List<SettingsModel> listStorage;
public String data;
    public Context con;
public FragmentActivity act;
    public int Volume_value=0;
public Fragment frag;
    public Method method;

    public int num_of_launchers=0;

    public securityAdaptor2(Context context, List<SettingsModel> customizedListView, FragmentActivity ac, Fragment frag) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
        this.con = context;
        this.act = ac;
        this.frag = frag;
        this.method= new Method(ac);
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
View view=null;
        view = LayoutInflater.from(con).inflate(R.layout.myview5, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        switch (position){

            case 0:
                holder.settings_name.setText("Set Full Screen Mode");
                holder.settings_status.setText( "status : " +listStorage.get(0).getfullscreen_mode());
                holder.settings_image.setImageResource(R.drawable.nav_bar);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder.clicked==0) {

                    holder.group.setVisibility(View.VISIBLE);
                    holder.clicked=1;
                    holder.checklistener(holder.enable, holder.disable, 0, holder);

                }
                else{
                    holder.group.setVisibility(View.GONE);
                    holder.clicked=0;
                }
            }
        });

  break;
 case 1:
                holder.settings_name.setText("Set Lock Home Button");
                holder.settings_status.setText( "status : " +listStorage.get(0).getlock_home_button());
                holder.settings_image.setImageResource(R.drawable.nav_bar);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder.clicked==0) {

                    holder.group.setVisibility(View.VISIBLE);
                    holder.clicked=1;
                    holder.checklistener(holder.enable, holder.disable, 1, holder);

                }
                else{
                    holder.group.setVisibility(View.GONE);
                    holder.clicked=0;
                }
            }
        });

  break;

    case 2:
        holder.settings_name.setText("Set Lock Notification Bar");
        holder.settings_status.setText( "status : " +listStorage.get(0).getlock_notification_bar());

        holder.settings_image.setImageResource(R.drawable.not_bar);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder.clicked==0) {
                    holder.group.setVisibility(View.VISIBLE);
                    holder.clicked=1;
                    holder.checklistener(holder.enable, holder.disable, 2, holder);

                }
                else{
                    holder.group.setVisibility(View.GONE);
                    holder.clicked=0;
                }
            }
        });

  break;

  case 3:
      holder.settings_name.setText("Set Lock Volume Button");
      holder.settings_status.setText( "status : " + listStorage.get(0).getlock_volume_botton());

      holder.settings_image.setImageResource(R.drawable.volume);
      holder.card.setOnClickListener(new View.OnClickListener() {

          @Override
          public void onClick(View view) {


              holder.setVolume(holder);



          }
      });

  break;


            case 4:
                holder.settings_name.setText("Set Parent Pass Code");
                holder.settings_status.setText(listStorage.get(0).getMasterPassword());

                holder.settings_image.setImageResource(R.drawable.passward);
                holder.card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

holder.MasterPasswordScreen(holder);
                    }
                });

  break;

 case 5:
                holder.settings_name.setText("Set Content Restriction");
                holder.settings_status.setText("Age Limit : " + listStorage.get(0).getContent_Restriction());

                holder.settings_image.setImageResource(R.drawable.age);
                holder.card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

holder.setAgeRestriction(holder);
                    }
                });

  break;




        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return 6;
    }



    class ViewHolder  extends RecyclerView.ViewHolder {
        TextView settings_name;
        TextView settings_status;
        ImageView settings_image, icon;
        LinearLayout card;
        RadioGroup group;
        RadioButton enable, disable;
        int clicked = 0;

        //  boolean ischecked;
        public ViewHolder(View v) {

            super(v);
            settings_name = (TextView) v.findViewById(R.id.name);
            settings_status = (TextView) v.findViewById(R.id.status);

            settings_image = (ImageView) v.findViewById(R.id.image);
            icon = (ImageView) v.findViewById(R.id.icon);
            card = (LinearLayout) v.findViewById(R.id.cardView);
            group = (RadioGroup) v.findViewById(R.id.radio);
            enable = (RadioButton) v.findViewById(R.id.enabled);
            disable = (RadioButton) v.findViewById(R.id.disabled);

        }


        public void checklistener(RadioButton enable, RadioButton disable, int position, ViewHolder holder) {



            enable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    switch (position) {

                        case 0:
                            //full screen
                            if (b) {

                                listStorage.get(0).setFullscreen_mode(true);
                                Parent.settingsList.get(0).setFullscreen_mode(true);
                                disable.setChecked(false);
                                Parent.full_screen = true;

                                hideSystemUI();

                            } else {
                                listStorage.get(0).setFullscreen_mode(false);
                                Parent.settingsList.get(0).setFullscreen_mode(false);
                                Parent.full_screen = false;
                                disable.setChecked(true);

                                showSystemUI();

                            }

                            // Parent.home_lock= Parent.settingsList.get(0).getlock_home_button();
                            //  notifyDataSetChanged();
                            holder.settings_status.setText("status : " + listStorage.get(0).getfullscreen_mode());
                            break;

                        //Parent.home_lock= Parent.settingsList.get(0).getlock_home_button();
                        case 1:
                            //home button
                            if (b) {

                                listStorage.get(0).setlock_home_button(true);
                                Parent.settingsList.get(0).setlock_home_button(true);
                                // disabled.setChecked(false);

                                restart_permit_add(disable, enable, false);
                            } else {
                                listStorage.get(0).setlock_home_button(false);
                                Parent.settingsList.get(0).setlock_home_button(false);
                                // disabled.setChecked(true);

                              //  restart_permit_remove(disable, enable, true);
                            }

                            // Parent.home_lock= Parent.settingsList.get(0).getlock_home_button();
                            //  notifyDataSetChanged();
                            holder.settings_status.setText("status : " + listStorage.get(0).getlock_home_button());
                            break;

                        //Parent.home_lock= Parent.settingsList.get(0).getlock_home_button();

                        case 2:
                            //notification bar
                            if (b) {
                                listStorage.get(0).setlock_notification_bar(true);
                                Parent.settingsList.get(0).setlock_notification_bar(true);

                                disable.setChecked(false);
                            } else {
                                listStorage.get(0).setlock_notification_bar(false);
                                Parent.settingsList.get(0).setlock_notification_bar(false);

                                disable.setChecked(true);
                            }

                            Parent.collapse_lock = Parent.settingsList.get(0).getlock_notification_bar();
                            //   notifyDataSetChanged();
                            holder.settings_status.setText("status : " + listStorage.get(0).getlock_notification_bar());
                            break;


                        case 3:
                            //volume
                            if (b) {
                                // listStorage.get(0).setlock_volume_botton(true);
                                // Parent.settingsList.get(0).setlock_volume_botton(true);

                                disable.setChecked(false);
                            } else {
                                //  listStorage.get(0).setlock_volume_botton(false);
                                // Parent.settingsList.get(0).setlock_volume_botton(false);

                                disable.setChecked(true);
                            }

                            // Parent.volume_lock= Parent.settingsList.get(0).getlock_volume_botton();

                            // notifyDataSetChanged();
                            holder.settings_status.setText("status : " + listStorage.get(0).getlock_volume_botton());
                            break;
                    }

                    Parent.settingsList.get(0).setPname(Parent.parentModelList.get(0).getPname());
                    Parent.settingsList.get(0).setPemail(Parent.parentModelList.get(0).getP_email());
                    Parent.dbhelper.addSettings(Parent.settingsList.get(0));

                    if (method.isNetworkAvailable()) {
                        SET_USER_DETAILS set = new SET_USER_DETAILS();
                        set.addsettings(Parent.settingsList.get(0).getpemail(),
                                String.valueOf(Parent.settingsList.get(0).getlock_home_button()),
                                String.valueOf(Parent.settingsList.get(0).getfullscreen_mode()),
                                String.valueOf(Parent.settingsList.get(0).getlock_notification_bar()),
                                String.valueOf(Parent.settingsList.get(0).getlock_volume_botton()),
                                Parent.settingsList.get(0).getMasterPassword(),
                                Parent.settingsList.get(0).getfeedback(), frag, act);

                    }
                }
            });


            disable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                    switch (position) {

                        case 0:
                            //full screen
                            if (b) {
                                listStorage.get(0).setFullscreen_mode(false);
                                Parent.settingsList.get(0).setFullscreen_mode(false);
                                Parent.full_screen = false;

                                showSystemUI();
                                enable.setChecked(false);
                            } else {
                                listStorage.get(0).setFullscreen_mode(true);
                                Parent.settingsList.get(0).setFullscreen_mode(true);
                                Parent.full_screen = true;

                                hideSystemUI();
                                enable.setChecked(true);
                            }
                            holder.settings_status.setText("status : " + listStorage.get(0).getlock_home_button());

                            // notifyDataSetChanged();

                            break;
                        case 1:
                            //home button
                            if (b) {
                                listStorage.get(0).setlock_home_button(false);
                                Parent.settingsList.get(0).setlock_home_button(false);

                                //enabled.setChecked(false);

                                restart_permit_remove(enable, disable, false);

                            } else {
                                listStorage.get(0).setlock_home_button(true);
                                Parent.settingsList.get(0).setlock_home_button(true);

                                // enabled.setChecked(true);
                              //  restart_permit_add(enable, disable, true);


                            }
                            holder.settings_status.setText("status : " + listStorage.get(0).getlock_home_button());

                            // notifyDataSetChanged();

                            break;


                        case 2:
                            //notification bar
                            if (b) {
                                listStorage.get(0).setlock_notification_bar(false);
                                Parent.settingsList.get(0).setlock_notification_bar(false);
                                enable.setChecked(false);
                            } else {
                                listStorage.get(0).setlock_notification_bar(true);
                                Parent.settingsList.get(0).setlock_notification_bar(true);
                                enable.setChecked(true);
                            }
                            Parent.collapse_lock = Parent.settingsList.get(0).getlock_notification_bar();

                            holder.settings_status.setText("status : " + listStorage.get(0).getlock_notification_bar());
                            break;


                        case 3:
                            //volume
                            if (b) {
                                //  listStorage.get(0).setlock_volume_botton(false);
                                // Parent.settingsList.get(0).setlock_volume_botton(false);

                                enable.setChecked(false);
                            } else {
                                // listStorage.get(0).setlock_volume_botton(true);
                                //Parent.settingsList.get(0).setlock_volume_botton(true);
                                enable.setChecked(true);
                            }


                            // Parent.volume_lock= Parent.settingsList.get(0).getlock_volume_botton();

                            //  holder.settings_status.setText( "status : " + listStorage.get(0).getlock_volume_botton());


                            break;
                    }


                    Parent.settingsList.get(0).setPname(Parent.parentModelList.get(0).getPname());
                    Parent.settingsList.get(0).setPemail(Parent.parentModelList.get(0).getP_email());
                    Parent.dbhelper.addSettings(Parent.settingsList.get(0));

                    if (method.isNetworkAvailable()) {
                        SET_USER_DETAILS set = new SET_USER_DETAILS();

                        set.addsettings(Parent.settingsList.get(0).getpemail(), String.valueOf(Parent.settingsList.get(0).getlock_home_button()),
                                String.valueOf(Parent.settingsList.get(0).getfullscreen_mode()),
                                String.valueOf(Parent.settingsList.get(0).getlock_notification_bar()),
                                String.valueOf(Parent.settingsList.get(0).getlock_volume_botton()),
                                Parent.settingsList.get(0).getMasterPassword(),
                                Parent.settingsList.get(0).getfeedback(), frag, act);

                    }

                }


            });
        }


        public void restart_permit_remove(RadioButton able, RadioButton active, boolean value) {

            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);
            //alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setIcon(R.drawable.restricted);

            alertDialogBuilder.setTitle("This Action will remove this app as Default Launcher");
            alertDialogBuilder.setMessage("Do you want to continue ?");

            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();

                  //  able.setChecked(value);

                    removeAsHomeApp();
                   // dialogInterface.dismiss();
                }
            });

            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();

                   // active.setChecked(false);

                }
            });


            alertDialogBuilder.show();
        }


        public void restart_permit_add(RadioButton able, RadioButton active, boolean value) {

            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);
            //alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setIcon(R.drawable.restricted);

            alertDialogBuilder.setTitle("This Action will make this app Default Launcher");
            alertDialogBuilder.setMessage("Do you want to continue ?");

            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                   // able.setChecked(value);

                    dialogInterface.dismiss();
                    startLuncherSelection();
                }
            });

            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                  //  active.setChecked(false);
                    dialogInterface.dismiss();

                }
            });


            alertDialogBuilder.show();
        }


        public void MasterPasswordScreen(ViewHolder holder) {
            try {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);
                //alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setIcon(R.drawable.pass);
                LayoutInflater inflater = act.getLayoutInflater();
                final View dialog = inflater.inflate(R.layout.set_master_password, null);
                final EditText pass;
                final EditText pass_confirm;

                if (Build.VERSION.SDK_INT >= 21) {
                    alertDialogBuilder.setView(dialog);
                    final AlertDialog alert = alertDialogBuilder.create();

                    pass = ((EditText) dialog.findViewById(R.id.pass));
                    pass_confirm = ((EditText) dialog.findViewById(R.id.confirm_pass));

                    ((Button) dialog.findViewById(R.id.close)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            security.imm.hideSoftInputFromWindow(view.getRootView().getWindowToken(), 0);

                            alert.dismiss();

                        }
                    });

                    ((Button) dialog.findViewById(R.id.save)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            String p = pass.getText().toString();
                            String pc = pass_confirm.getText().toString();

                            if (p.equals(pc)) {
                                listStorage.get(0).setMasterPassword(p);

                                Parent.settingsList.get(0).setMasterPassword(p);
                                Parent.MasterPass = p;

                                Parent.settingsList.get(0).setPname(Parent.parentModelList.get(0).getPname());
                                Parent.settingsList.get(0).setPemail(Parent.parentModelList.get(0).getP_email());

                                Parent.dbhelper.addSettings(Parent.settingsList.get(0));
                                Parent.parentModelList = Parent.dbhelper.getParent();

                                Parent.parentModelList.get(0).setP_pass(Parent.MasterPass);
                                Parent.dbhelper.UpdatePassParent(Parent.MasterPass, Parent.parentModelList.get(0).getP_email());

                                StyleableToast.makeText(con, "Password Saved Successfully",
                                        Toast.LENGTH_LONG, R.style.mytoast).show();

                                // notifyDataSetChanged();

                                holder.settings_status.setText(listStorage.get(0).getMasterPassword());

                                security.imm.hideSoftInputFromWindow(view.getRootView().getWindowToken(), 0);

                                alert.dismiss();

                                if (method.isNetworkAvailable()) {
                                    SET_USER_DETAILS set = new SET_USER_DETAILS();

                                    set.addsettings(Parent.settingsList.get(0).getpemail(), String.valueOf(Parent.settingsList.get(0).getlock_home_button()),
                                            String.valueOf(Parent.settingsList.get(0).getfullscreen_mode()),
                                            String.valueOf(Parent.settingsList.get(0).getlock_notification_bar()),
                                            String.valueOf(Parent.settingsList.get(0).getlock_volume_botton()),
                                            Parent.settingsList.get(0).getMasterPassword(),
                                            Parent.settingsList.get(0).getfeedback(), frag, act);
                                }

                            } else {
                                pass.setError("Password MisMatch");
                                pass_confirm.setError("Password MisMatch");
                                security.imm.hideSoftInputFromWindow(view.getRootView().getWindowToken(), 0);

                            }
                        }
                    });


                    alert.show();
                } else {
                    alertDialogBuilder.setMessage("Coming soon...");


                }

            } catch (Exception e) {
            }
        }


        public void setAgeRestriction(ViewHolder g) {
            try {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);
                // alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setIcon(R.drawable.done);
                LayoutInflater inflater = act.getLayoutInflater();
                final View dialog = inflater.inflate(R.layout.set_age_restriction, null);

                if (Build.VERSION.SDK_INT >= 21) {
                    alertDialogBuilder.setView(dialog);
                    final AlertDialog alert = alertDialogBuilder.create();

                    ((TextView) dialog.findViewById(R.id.itemname)).setText("Set Age Restriction");


                    AppCompatSeekBar seekbar = null;
                    seekbar = dialog.findViewById(R.id.seek);
                    seekbar.setMax(17);
                    seekbar.setProgress(Integer.parseInt(Parent.settingsList.get(0).getContent_Restriction()));

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


                    ((Button) dialog.findViewById(R.id.ve)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            if (Volume_value == 0) {

                                listStorage.get(0).setContent_Restriction(Volume_value + "");
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
                                listStorage.get(0).setContent_Restriction(Volume_value + "");
                                Parent.settingsList.get(0).setContent_Restriction(Volume_value + "");
                                Parent.settingsList.get(0).setPname(Parent.parentModelList.get(0).getPname());

                                Parent.settingsList.get(0).setPemail(Parent.parentModelList.get(0).getP_email());

                                Parent.dbhelper.addSettings(Parent.settingsList.get(0));

                                g.settings_status.setText("Age 0 - " + Volume_value);


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


                            alert.dismiss();

                        }
                    });

                    ((Button) dialog.findViewById(R.id.close)).setOnClickListener(new View.OnClickListener() {
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
 public void setVolume(ViewHolder g) {
            try {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);
                // alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setIcon(R.drawable.done);
                LayoutInflater inflater = act.getLayoutInflater();
                final View dialog = inflater.inflate(R.layout.setvolume, null);

                if (Build.VERSION.SDK_INT >= 21) {
                    alertDialogBuilder.setView(dialog);
                    final AlertDialog alert = alertDialogBuilder.create();

                    ((TextView) dialog.findViewById(R.id.itemname)).setText("Set Phone Volume");


                    AudioManager audioManager = (AudioManager) act.getSystemService(Context.AUDIO_SERVICE);
                    AppCompatSeekBar seekbar = null;
                    seekbar = dialog.findViewById(R.id.seek);
                    seekbar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));

                    seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int newVolume, boolean b) {
                            ((TextView) dialog.findViewById(R.id.itemname)).setText("Media Volume : " + newVolume);
                            Volume_value = newVolume;
                            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, newVolume, 0);
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                        }
                    });


                    ((Button) dialog.findViewById(R.id.ve)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            if (Volume_value == 0) {
                                Volume_value = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

                                listStorage.get(0).setlock_volume_botton(Volume_value);
                                Parent.settingsList.get(0).setlock_volume_botton(Volume_value);
                                Parent.settingsList.get(0).setPname(Parent.parentModelList.get(0).getPname());
                                Parent.settingsList.get(0).setPemail(Parent.parentModelList.get(0).getP_email());

                                Parent.dbhelper.addSettings(Parent.settingsList.get(0));

                                if (method.isNetworkAvailable()) {
                                    SET_USER_DETAILS set = new SET_USER_DETAILS();
                                    set.addsettings(Parent.settingsList.get(0).getpemail(), String.valueOf(Parent.settingsList.get(0).getlock_home_button()),
                                            String.valueOf(Parent.settingsList.get(0).getfullscreen_mode()),
                                            String.valueOf(Parent.settingsList.get(0).getlock_notification_bar()),
                                            String.valueOf(Parent.settingsList.get(0).getlock_volume_botton()),
                                            Parent.settingsList.get(0).getMasterPassword(),
                                            Parent.settingsList.get(0).getfeedback(), frag, act);
                                }

                            } else {
                                listStorage.get(0).setlock_volume_botton(Volume_value);
                                Parent.settingsList.get(0).setlock_volume_botton(Volume_value);
                                Parent.settingsList.get(0).setPname(Parent.parentModelList.get(0).getPname());

                                Parent.settingsList.get(0).setPemail(Parent.parentModelList.get(0).getP_email());

                                Parent.dbhelper.addSettings(Parent.settingsList.get(0));

                                g.settings_status.setText("Volume : " + Volume_value);


                                if (method.isNetworkAvailable()) {
                                    SET_USER_DETAILS set = new SET_USER_DETAILS();
                                    set.addsettings(Parent.settingsList.get(0).getpemail(), String.valueOf(Parent.settingsList.get(0).getlock_home_button()),
                                            String.valueOf(Parent.settingsList.get(0).getfullscreen_mode()),
                                            String.valueOf(Parent.settingsList.get(0).getlock_notification_bar()),
                                            String.valueOf(Parent.settingsList.get(0).getlock_volume_botton()),
                                            Parent.settingsList.get(0).getMasterPassword(),
                                            Parent.settingsList.get(0).getfeedback(), frag, act);
                                }

                            }


                            // listStorage.get(0).setlock_volume_botton(true);
                            //Parent.settingsList.get(0).setlock_volume_botton(true);


                            // Parent.volume_lock= Parent.settingsList.get(0).getlock_volume_botton();


                            alert.dismiss();

                        }
                    });

                    ((Button) dialog.findViewById(R.id.close)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alert.dismiss();
                            return;

                        }
                    });

                    alert.show();
                } else {
                    alertDialogBuilder.setMessage("Set Media Volume");
                    alertDialogBuilder.setTitle("Volume Settings");


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


        public void startLuncherSelection() {
            set_first_selection();
           // isMyAppLauncherDefault();

            /*
       if(isMyAppLauncherDefault() == false ) {
          // Toast.makeText(act, "" +num_of_launchers, Toast.LENGTH_LONG).show();

           set_first_selection();

       }
        else{
          // Toast.makeText(act, "" + num_of_launchers, Toast.LENGTH_LONG).show();
            Toast.makeText(act, "App Already Launcher", Toast.LENGTH_LONG).show();
        }

*/

         //   set_first_selection();

        }

        public boolean isMyAppLauncherDefault() {
            final IntentFilter filter = new IntentFilter(Intent.ACTION_MAIN);
            filter.addCategory(Intent.CATEGORY_HOME);

            List<IntentFilter> filters = new ArrayList<IntentFilter>();
            filters.add(filter);

            final String myPackageName = act.getPackageName();
            List<ComponentName> activities = new ArrayList<ComponentName>();
            final PackageManager packageManager = (PackageManager) act.getPackageManager();

            packageManager.getPreferredActivities(filters, activities, null);

           // num_of_launchers=activities.size();
            num_of_launchers=0;

            for (ComponentName activity : activities) {

                num_of_launchers+=1;

              // Toast.makeText(act, "size=" +num_of_launchers, Toast.LENGTH_LONG).show();

                if (myPackageName.equals(activity.getPackageName())) {
                    //  choice +=1;
                   // Toast.makeText(act, activity.getPackageName(), Toast.LENGTH_LONG).show();

                    return true;

                }

            }

            return false;

        }


        public void resetPreferredLauncherAndOpenChooser(Context context) {
            PackageManager packageManager = context.getPackageManager();
            ComponentName componentName = new ComponentName(context, FakeLauncher.class);
//disable first
            //  packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, PackageManager.DONT_KILL_APP);


            packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);


            Intent selector = new Intent(Intent.ACTION_MAIN);
            selector.addCategory(Intent.CATEGORY_HOME);
            selector.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            act.startActivityForResult(selector, 578);

/*

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
            act.startActivityForResult(intent, 2021);

 */
            // packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, PackageManager.DONT_KILL_APP);
        }


        public void setAsHomeApp() {
            resetPreferredLauncherAndOpenChooser(act);

        /*
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
       act.startActivityForResult(intent, 2021);

         */


        }


        void removeAsHomeApp() {


            PackageManager packageManager = act.getPackageManager();
            ComponentName componentName = new ComponentName(act, FakeLauncher.class);
//disable first
            packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, PackageManager.DONT_KILL_APP);



            isMyAppLauncherDefault();

            if(num_of_launchers>1) {
                Intent selector = new Intent(Intent.ACTION_MAIN);
                selector.addCategory(Intent.CATEGORY_HOME);
                selector.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                act.startActivity(selector);
            }
            else {
              // act.finish();
                Toast.makeText(con, "Removed as Launcher", Toast.LENGTH_SHORT).show();

            }

        /*
        Intent intent = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent = new Intent(Settings.ACTION_HOME_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            //select Kidzone as home app
        }
        else{
            //click on Home App and choose Kidzone as home app
            intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + "com.playzone.kidszone"));
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        }

        // Toast.makeText(con, listStorage.get(i).packages, Toast.LENGTH_SHORT).show();
       act. startActivityForResult(intent, 5050);
*/
//act.finishActivity(5050);
            // act.finish();
            // act.moveTaskToBack(true);
            // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //    act.finishAndRemoveTask();
            //}
            //  Intent in = new Intent(act, MainActivity.class);
            //  act.startActivity(in);
            //System.exit(0);


            // Process.killProcess(Process.myPid());
        }


        public void showSystemUI() {


            Parent.isTyping = true;
            // if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            View v = act.getWindow().getDecorView();


            v.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);


            Parent.restart_main = 1;
            Intent in = new Intent(act, MainActivity.class);

            act.finish();
            act.startActivity(in);

        }

        private void hideSystemUI() {
            if (Parent.full_screen) {

                //  getWindow().addFlags(WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY);
                // Enables regular immersive mode.
                // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
                // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                View v = act.getWindow().getDecorView();
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


        public void set_first_selection() {
            try {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);
                // alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setIcon(R.drawable.done);
                LayoutInflater inflater = act.getLayoutInflater();
                final View dialog = inflater.inflate(R.layout.set_permissions5, null);

                if (Build.VERSION.SDK_INT >= 21) {
                    alertDialogBuilder.setView(dialog);
                    final AlertDialog alert = alertDialogBuilder.create();


                    ((Button) dialog.findViewById(R.id.launcher)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alert.dismiss();

                            setAsHomeApp();

                        }
                    });


                    alert.show();
                }

            } catch (Exception f) {

            }
        }


    }}



