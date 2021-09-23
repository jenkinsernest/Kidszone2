package com.playzone.kidszone.adaptors;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.playzone.kidszone.MainActivity;
import com.playzone.kidszone.Method;
import com.playzone.kidszone.Parent;
import com.playzone.kidszone.R;
import com.playzone.kidszone.SET_USER_DETAILS;
import com.playzone.kidszone.Swipe_home;
import com.playzone.kidszone.fragmentpackage.Educational_Goals;
import com.playzone.kidszone.fragmentpackage.List_Installed_frag;
import com.playzone.kidszone.fragmentpackage.Parent_control;
import com.playzone.kidszone.fragmentpackage.Web_settings;
import com.playzone.kidszone.fragmentpackage.statistics;
import com.playzone.kidszone.fragmentpackage.time_slot_home;
import com.playzone.kidszone.models.ChildModel;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class ChooseChildAdapter extends RecyclerView.Adapter<ChooseChildAdapter.ViewHolder> {


    private Activity mContext;
    Swipe_home main;
    private String type;
    String paid_data = null;
    String paid_data_name = null;
    private int lastPosition = -1;
    private int index = 0;
Dialog dialog;
    List<ChildModel> mItems;

    private ViewGroup parent;
    private int viewType;
    View view;
    public int Volume_value=0;

Method method;
    public ChooseChildAdapter(Activity con, List<ChildModel> list) {
        this.mContext = con;
        this.type = type;

        mItems = list;
        method = new Method(con);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;
        this.viewType = viewType;
       view = null;



            switch (MainActivity.screenSize) {

                case Configuration.SCREENLAYOUT_SIZE_LARGE:
                    //toastMsg = true;


                    view = LayoutInflater.from(mContext).inflate(R.layout.myview6, parent, false);

                    break;
                case Configuration.SCREENLAYOUT_SIZE_NORMAL:

                    // toastMsg = false;
                    view = LayoutInflater.from(mContext).inflate(R.layout.myview6, parent, false);

                    break;
                case Configuration.SCREENLAYOUT_SIZE_SMALL:

                    // toastMsg = false;
                    view = LayoutInflater.from(mContext).inflate(R.layout.myview6, parent, false);

                    break;
                default:

                    view = LayoutInflater.from(mContext).inflate(R.layout.myview6, parent, false);
                    // toastMsg = true;
            }





        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position ) {




                holder.name.setText(
                        mItems.get(position).getName());


               // holder.image.setImageURI(mItems.get(position).getIcon());
        holder.image.setImageURI(Uri.parse(new File(mItems.get(position).getIcon()).toString()));
       // Picasso.get().load(mItems.get(position).getIcon()).into(holder.image);
        if( mItems.get(position).getIcon().equals("")){
            holder.image.setImageURI(mItems.get(position).geticon2());
        }
        else {
            holder.image.setImageURI(Uri.parse(new File(mItems.get(position).getIcon()).toString()));
        }

        holder.id=mItems.get(position).getKid_id();

holder.position=position;

                holder.card.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {

                                                       Parent.kid_id=holder.id;
                                                       Parent.kid_name=holder.name.getText().toString();
                                                       Parent_control.alert.dismiss();

                                                       if(Parent_control.choice.equalsIgnoreCase("apps")) {

                                                           FragmentTransaction transaction = ((FragmentActivity) v.getContext()).getSupportFragmentManager()
                                                                   .beginTransaction();
                                                           transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                                                           transaction.replace(R.id.fragmain, new List_Installed_frag());
                                                           // transaction.addToBackStack(null);
                                                           transaction.commit();
                                                       }

                                                       else if(Parent_control.choice.equalsIgnoreCase("age_restriction")) {

                                                           setAgeRestriction(holder.position);
                                                       }

                                                       else if(Parent_control.choice.equalsIgnoreCase("web_settings")){

                                                           FragmentTransaction transaction = ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction();
                                                           transaction .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                                                           transaction.replace(R.id.fragmain,new Web_settings());
                                                           // transaction.addToBackStack(null);
                                                           transaction.commit();
                                                       }
                                                       else if(Parent_control.choice.equalsIgnoreCase("time")){

                                                           FragmentTransaction transaction = ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction();
                                                           transaction .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                                                           transaction.replace(R.id.fragmain,new time_slot_home());
                                                           // transaction.addToBackStack(null);
                                                           transaction.commit();
                                                       }
 else if(Parent_control.choice.equalsIgnoreCase("statistics")){

                                                           FragmentTransaction transaction = ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction();
                                                           transaction .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                                                           transaction.replace(R.id.fragmain, new statistics());
                                                           // transaction.addToBackStack(null);
                                                           transaction.commit();
                                                       }
 else if(Parent_control.choice.equalsIgnoreCase("goal")){

     MainActivity main= new MainActivity();
     main.user_alloted_time(Parent.kid_id);
                                                           FragmentTransaction transaction = ((FragmentActivity) v.getContext()).getSupportFragmentManager().beginTransaction();
                                                           transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                                                           transaction.replace(R.id.fragmain, new Educational_Goals());
                                                        //   transaction.addToBackStack(null);
                                                           transaction.commit();
                                                       }

                                                   }
                                               }
                );
            }













    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView name, pack;
        ImageView image, image_done, lockstatus;
        LinearLayout ll;
        LinearLayout card;
        TextView duration,package_name,expire, status, expiretext;
   String id;
   int position;
        public ViewHolder(View v) {

            super(v);
           // Toast.makeText(mContext,"view holder", Toast.LENGTH_LONG).show();


            name = (TextView) v.findViewById(R.id.childname);
            //ll=(LinearLayout) v.findViewById(R.id.ll);


                card = (LinearLayout) v.findViewById(R.id.cardView);


                image=(ImageView) v.findViewById(R.id.image);

          //  Toast.makeText(mContext,"view holder", Toast.LENGTH_LONG).show();



        }
    }




    public void setAgeRestriction(int pos) {
        try {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
            // alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setIcon(R.drawable.done);
            LayoutInflater inflater = mContext.getLayoutInflater();
            final View dialog = inflater.inflate(R.layout.set_age_restriction, null);

            if (Build.VERSION.SDK_INT >= 21) {
                alertDialogBuilder.setView(dialog);
                final AlertDialog alert = alertDialogBuilder.create();

                ((TextView) dialog.findViewById(R.id.itemname)).setText("Set Age Restriction");


                AppCompatSeekBar seekbar = null;
                seekbar = dialog.findViewById(R.id.seek);
                seekbar.setMax(17);

                if(Parent.childModelList.get(pos).getContent_Restriction().equals(null)  ||
                        Parent.childModelList.get(pos).getContent_Restriction().equals("")) {
                   // seekbar.setProgress(Integer.parseInt(Parent.childModelList.get(pos).getContent_Restriction()));
                }
                else{
                    seekbar.setProgress(Integer.parseInt(Parent.childModelList.get(pos).getContent_Restriction()));

                    ((TextView) dialog.findViewById(R.id.itemname)).append("\nAge 0 - " +
                            Parent.childModelList.get(pos).getContent_Restriction());

                }


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

                            Parent.childModelList.get(pos).setContent_Restriction(Volume_value + "");

                            Parent.dbhelper.UpdateChildContent_Restriction(Volume_value + "", Parent.kid_id);

                            if (method.isNetworkAvailable()) {

                                    SET_USER_DETAILS set = new SET_USER_DETAILS();

                                    set.updateContentRestriction(Parent.pemail, Parent.kid_id,
                                            String.valueOf(Volume_value),"0",
                                            new Fragment(), mContext);


                            }

                        } else {


                            Parent.childModelList.get(pos).setContent_Restriction(Volume_value + "");

                            Parent.dbhelper.UpdateChildContent_Restriction(Volume_value + "", Parent.kid_id);


                            // g.settings_status.setText("Age 0 - " + Volume_value);
                            ((TextView) dialog.findViewById(R.id.itemname)).setText("Age 0 - " + Volume_value);

                            if (method.isNetworkAvailable()) {

                                SET_USER_DETAILS set = new SET_USER_DETAILS();

                                set.updateContentRestriction(Parent.pemail, Parent.kid_id,
                                        String.valueOf(Volume_value),"0",
                                        new Fragment(), mContext);


                            }


                        }


                        // listStorage.get(0).setlock_volume_botton(true);
                        //Parent.settingsList.get(0).setlock_volume_botton(true);


                        // Parent.volume_lock= Parent.settingsList.get(0).getlock_volume_botton();

                        method.alertBox("Age Restriction saved", 2);
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


}