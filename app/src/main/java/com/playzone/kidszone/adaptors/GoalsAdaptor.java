package com.playzone.kidszone.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dualcores.swagpoints.SwagPoints;
import com.playzone.kidszone.Method;
import com.playzone.kidszone.Parent;
import com.playzone.kidszone.R;
import com.playzone.kidszone.models.Goals_Model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;


public class GoalsAdaptor extends RecyclerView.Adapter<GoalsAdaptor.ViewHolder>{

    public LayoutInflater layoutInflater;
    public List<Goals_Model> listStorage;
public String data;
    public Context con;
public FragmentActivity act;
    public int Volume_value=0;
public Fragment frag;
    public Method method;

public int stop=0;
    public GoalsAdaptor(Context context, List<Goals_Model> customizedListView, FragmentActivity ac, Fragment frag) {
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
        view = LayoutInflater.from(con).inflate(R.layout.goals, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

holder.setIsRecyclable(false);

                holder.task_name.setText(listStorage.get(position).getTaskname());
                holder.status.setText("Status : " + listStorage.get(position).getStatus());

                if(listStorage.get(position).getAlotted_time().equals("") || listStorage.get(position).getAlotted_time().equals(null)) {
                    holder.alotted_time.setText(listStorage.get(position).getAlotted_time());
                }
                else{
                    int i=Integer.parseInt(listStorage.get(position).getAlotted_time());
                    if (i < 60) {
                        holder.alotted_time.setText(i + " minutes");
                    } else if (i == 60) {
                        holder.alotted_time.setText(1 + " hour");
                    } else if (i > 60 && i < 120) {
                        int min = i - 60;
                        holder.alotted_time.setText(1 + " hour, " + min + " minutes");
                    } else if (i == 120) {
                        holder.alotted_time.setText(2 + " hours");

                    }
                }


        int t=Integer.parseInt(listStorage.get(position).getSpent());

        if (t < 60) {
            holder.spent.setText("Spent: " + t + " minutes");
        } else if (t == 60) {
            holder.spent.setText("Spent: 1   hour");
        } else if (t > 60 && t < 120) {
            int min = t - 60;
            holder.spent.setText("Spent: 1   hour, " + min + " minutes");
        } else if (t == 120) {
            holder.spent.setText("Spent: 2  hours");

        }


                holder.seekBar.setMax(120);

                holder.seekBar.setPoints(Integer.parseInt(listStorage.get(position).getAlotted_time()));

holder.seekBar.setOnSwagPointsChangeListener(new SwagPoints.OnSwagPointsChangeListener() {
    @Override
    public void onPointsChanged(SwagPoints swagPoints, int i, boolean fromUser) {
        if(i>120){
            holder.seekBar.setEnabled(false);
        }
        else{
            holder.seekBar.setEnabled(true);

        }


                if (i < 60) {
                    holder.alotted_time.setText(i + " minutes");
                } else if (i == 60) {
                    holder.alotted_time.setText(1 + " hour");
                } else if (i > 60 && i < 120) {
                    int min = i - 60;
                    holder.alotted_time.setText(1 + " hour, " + min + " minutes");
                } else if (i == 120) {
                    holder.alotted_time.setText(2 + " hours");

                }


                if (i > 0) {
                    holder.status.setText("Status : " + "Active");
                } else if (i == 0) {
                    holder.status.setText("Status : " + "Not Active");
                }






        SimpleDateFormat formateDate2 = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        Calendar date2 = Calendar.getInstance();
        String dates = null;

        dates = formateDate2.format(date2.getTime());


        if (i == 0) {

            Parent.educational_goals.get(position).setP_email(Parent.settingsList.get(0).getpemail());
            Parent.educational_goals.get(position).setKid(Parent.kid_id);
            Parent.educational_goals.get(position).setActive_day(dates);
            Parent.educational_goals.get(position).setAlotted_time(i + "");
            Parent.educational_goals.get(position).setSpent("0");
            Parent.educational_goals.get(position).setStatus("Not Active");
            Parent.educational_goals.get(position).setReward_earned("0");
            Parent.educational_goals.get(position).setTaskname(listStorage.get(position).getTaskname());


            //  Parent.dbhelper.addSettings(Parent.settingsList.get(0));



        } else {
            Parent.educational_goals.get(position).setP_email(Parent.settingsList.get(0).getpemail());
            Parent.educational_goals.get(position).setKid(Parent.kid_id);
            Parent.educational_goals.get(position).setActive_day(dates);
            Parent.educational_goals.get(position).setAlotted_time(i + "");
            Parent.educational_goals.get(position).setSpent("0");
            Parent.educational_goals.get(position).setStatus("Active");
            Parent.educational_goals.get(position).setReward_earned("0");
            Parent.educational_goals.get(position).setTaskname(listStorage.get(position).getTaskname());


        }
    }

    @Override
    public void onStartTrackingTouch(SwagPoints swagPoints) {

    }

    @Override
    public void onStopTrackingTouch(SwagPoints swagPoints) {

    }
});




    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return listStorage.size();
    }



    class ViewHolder  extends RecyclerView.ViewHolder {
        TextView task_name;
        TextView status;
        TextView alotted_time;
        TextView spent;
    int total;
    int value;
        //androidx.appcompat.widget.AppCompatSeekBar seekBar;
        com.dualcores.swagpoints.SwagPoints seekBar;
        int clicked=0;

        //  boolean ischecked;
        public ViewHolder(View v) {

            super(v);
            task_name = (TextView) v.findViewById(R.id.taskname);
            status = (TextView) v.findViewById(R.id.status);

            alotted_time = (TextView) v.findViewById(R.id.alotted_time);
            spent = (TextView) v.findViewById(R.id.spent);
            seekBar = (com.dualcores.swagpoints.SwagPoints ) v.findViewById(R.id.seek);


        }




    }



/*


 holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                        long minute = (Parent.Remaining_hour / 1000) / 60;

                        if(i<holder.total) {
                            holder.value= holder.total - i;

                            holder.total-=(holder.total-i);


                            Educational_Goals.total_goals_time-=holder.total;
                            stop=0;
                        }
                        else if(i>holder.total){
                            holder.value=i - holder.total ;

                            holder.total+=(holder.value);

                            Educational_Goals.total_goals_time+=holder.total;
                            stop=0;
                        }
                        else if(i==holder.total){
stop=1;
                            method.alertBox("Goals Time (" + Educational_Goals.total_goals_time + " ) "+
                                    " should not exceed Screen Time " + minute, 3);
                        }



                        if(stop==0) {
                            if (Parent.access_type.equalsIgnoreCase("timed")) {


                                if (Educational_Goals.total_goals_time <= minute) {

                                    if (i < 60) {
                                        holder.alotted_time.setText(i + " minutes");
                                    } else if (i == 60) {
                                        holder.alotted_time.setText(1 + " hour");
                                    } else if (i > 60 && i < 120) {
                                        int min = i - 60;
                                        holder.alotted_time.setText(1 + " hour, " + min + " minutes");
                                    } else if (i == 120) {
                                        holder.alotted_time.setText(2 + " hours");

                                    }


                                    if (i > 0) {
                                        holder.status.setText("Status : " + "Active");
                                    } else if (i == 0) {
                                        holder.status.setText("Status : " + "Not Active");
                                    }


                                    if (i == 0) {

                                        Parent.educational_goals.get(position).setP_email(Parent.settingsList.get(0).getpemail());
                                        Parent.educational_goals.get(position).setKid(Parent.kid_id);
                                        Parent.educational_goals.get(position).setActive_day(new Date().toString());
                                        Parent.educational_goals.get(position).setAlotted_time(i + "");
                                        Parent.educational_goals.get(position).setSpent("0");
                                        Parent.educational_goals.get(position).setStatus("not active");
                                        Parent.educational_goals.get(position).setTaskname(listStorage.get(0).getTaskname());


                                        //  Parent.dbhelper.addSettings(Parent.settingsList.get(0));

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
                                            Parent.educational_goals.get(position).setP_email(Parent.settingsList.get(0).getpemail());
                                            Parent.educational_goals.get(position).setKid(Parent.kid_id);
                                            Parent.educational_goals.get(position).setActive_day(new Date().toString());
                                            Parent.educational_goals.get(position).setAlotted_time(i + "");
                                            Parent.educational_goals.get(position).setSpent("0");
                                            Parent.educational_goals.get(position).setStatus("active");
                                            Parent.educational_goals.get(position).setTaskname(listStorage.get(0).getTaskname());


                                            }
                                            } else if (Educational_Goals.total_goals_time > minute) {
                                            holder.total -= holder.value;
                                            Educational_Goals.total_goals_time -= holder.value;

                                            seekBar.setProgress(holder.total);

                                            }
                                            } else {
                                            method.alertBox("No Screen Time Set", 1);

                                            }
                                            }
                                            //  method.alertBox("diff= "+ holder.value + "  total= " + holder.total, 1);
                                            }

@Override
public void onStartTrackingTouch(SeekBar seekBar) {

        }

@Override
public void onStopTrackingTouch(SeekBar seekBar) {

        }
        });
 */



}



