package com.playzone.kidszone.adaptors;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.muddzdev.styleabletoast.StyleableToast;
import com.playzone.kidszone.Parent;
import com.playzone.kidszone.R;
import com.playzone.kidszone.Swipe_home;
import com.playzone.kidszone.fragmentpackage.User_account_screen;
import com.playzone.kidszone.models.ChildModel;
import com.playzone.kidszone.models.access_type_model;

import java.io.File;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class ChildAccountAdapter extends RecyclerView.Adapter<ChildAccountAdapter.ViewHolder>{

    public LayoutInflater layoutInflater;
    public List<ChildModel> listStorage;

    public Activity con;
public FragmentActivity act;

    List<Uri>  fruits = new ArrayList<>();
    List<Uri>  bgs = new ArrayList<>();
  int fruit_counter=0;
  int bgs_counter=0;
    int bgs_checker=0;

public View dialog2=null;

    public ChildAccountAdapter(Activity context, List<ChildModel> customizedListView, FragmentActivity ac,
                               List<Uri>  fruit, List<Uri>  bg) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
        this.con = context;
        this.act = ac;
        this.fruits=fruit;
        this.bgs=bg;

        //System.out.println("size=====" + bg.size());
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
View view=null;

if(listStorage.size()==1){
    view = LayoutInflater.from(con).inflate(R.layout.child_account_view_single, parent, false);

}
else{

    int  screenSize = act.getResources().getConfiguration().screenLayout &
            Configuration.SCREENLAYOUT_SIZE_MASK;

    switch (screenSize) {
        case Configuration.SCREENLAYOUT_SIZE_LARGE:
            //toastMsg = true;
            view = LayoutInflater.from(con).inflate(R.layout.child_account_view_large, parent, false);

            break;
        case Configuration.SCREENLAYOUT_SIZE_NORMAL:
            // toastMsg = false;
            view = LayoutInflater.from(con).inflate(R.layout.child_account_view, parent, false);

            break;
        case Configuration.SCREENLAYOUT_SIZE_SMALL:
            // toastMsg = false;
            view = LayoutInflater.from(con).inflate(R.layout.child_account_view, parent, false);


            break;
        default:
            view = LayoutInflater.from(con).inflate(R.layout.child_account_view_large, parent, false);

            // toastMsg = true;
    }
}

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

    Drawable draw;
    Drawable draw2;
        try{
            if(fruit_counter==4){
                fruit_counter=0;

            }


                 if(bgs_checker==6){
                    bgs_checker=0;
                 }


//System.out.println(listStorage.size());
             if(bgs_counter==listStorage.size()){

                 if(bgs_checker==6){
                     bgs_checker=0;
                 }
                 bgs_counter=0;

              bgs_checker+=1;
             }

            InputStream in = con.getContentResolver().openInputStream(fruits.get(fruit_counter));
            draw= Drawable.createFromStream(in, fruits.get(fruit_counter).toString());
            holder.lin.setBackground(draw);

            InputStream in2 = con.getContentResolver().openInputStream(bgs.get(bgs_checker));
            draw2= Drawable.createFromStream(in2, bgs.get(bgs_checker).toString());
            User_account_screen.rel.setBackground(draw2);

            fruit_counter+=1;
            bgs_counter+=1;



        holder.username.setText(listStorage.get(position).getName());
       // holder.access_type.setText(listStorage.get(position).getAccess_type());


      String AccessValue=  holder.ComputeAccessTime(listStorage.get(position).getKid_id(),
              listStorage.get(position).getAccess_type()
        , listStorage.get(position).getWhole_week(), listStorage.get(position).getSingle_day(), position);


        if(AccessValue.equalsIgnoreCase("active")){
holder.lock.setImageDrawable(con.getResources().getDrawable(R.drawable.time));
            holder.lin.setEnabled(true);
            holder.lin.setClickable(true);

        }
        else if(AccessValue.equalsIgnoreCase("expired")){
            holder.lock.setImageDrawable(con.getResources().getDrawable(R.drawable.time3));
            holder.lin.setEnabled(false);
            holder.lin.setClickable(false);
        }


      //  if (listStorage.get(position).getAccess_type().equalsIgnoreCase("Unlimited")) {
            //holder.Switch.setChecked(true);
      //  }

       // Bitmap bit = ThumbnailUtils.extractThumbnail(BitmapFactory.
       //         decodeFile(listStorage.get(position).getIcon()), 250, 250);

       // holder.profile.setImageURI(listStorage.get(position).getIcon());
           if( listStorage.get(position).getIcon().equals("")){
               holder.profile.setImageURI(listStorage.get(position).geticon2());
           }
           else {
               holder.profile.setImageURI(Uri.parse(new File(listStorage.get(position).getIcon()).toString()));
           }
       // holder.UpdateAppList(holder.Switch,holder.time_set,holder.delete,holder.edit
        //        ,listStorage, position, holder);

        holder.lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Parent.kid_id=listStorage.get(position).getKid_id();

Parent.Remaining_hour = holder.remaining_time;
Parent.access_type = holder.access_type;

Parent.access_type_per_acc.clear();

Parent.access_type_per_acc.add(new access_type_model( Parent.kid_id, Parent.access_type));


                //LinkedHashSet<access_type_model> s = new LinkedHashSet(Parent.access_type_per_acc);


                Parent.dbhelper.addAccessType(Parent.access_type_per_acc);

                Parent.data_clear_by_android="false";

                Parent.OldTotalApp.clear();
              //  Parent.selectedApp_2.clear();
                Parent.OldTotalApp = Parent.installedApps;

                if(listStorage.get(position).getPass().equals("") || listStorage.get(position).getPass().equals("none")) {
                   // showCustomDialog();
                    Parent.selectedApp_2.clear();
                    Parent.selectedApp_3.clear();
                    Parent.selectedApp_2 = Parent.dbhelper.getApplist(Parent.kid_id);
                    con.startActivity(new Intent(con, Swipe_home.class));
                }
                else{
                    try {
                       // showCustomDialog();
                        Parent.selectedApp_2.clear();
                        Parent.selectedApp_3.clear();
                        Parent.selectedApp_2 = Parent.dbhelper.getApplist(Parent.kid_id);
                        con.startActivity(new Intent(con, Swipe_home.class));

                      //  holder.PasswordScreen(position);
                    }
                    catch (Exception f){

                    }
                }
              //  Parent.dialog.dismiss();
                //act.finish();
            }
        });

        }
        catch(Exception d){
System.out.println(d.getMessage());
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return listStorage.size();
    }



    class ViewHolder  extends RecyclerView.ViewHolder{

        ImageView profile;
        ImageView time, lock;

        TextView username;
       RelativeLayout lin;
        long remaining_time;
        String access_type;

        //  boolean ischecked;
        public ViewHolder(View v) {

            super(v);
            username = (TextView) v.findViewById(R.id.username);
            profile = (ImageView) v.findViewById(R.id.profile_image);
            lock = (ImageView) v.findViewById(R.id.lock);
            lin=(RelativeLayout)v.findViewById(R.id.bac);

        }




        public String ComputeAccessTime(String id, String accesstype, boolean whole, boolean single, int pos){
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

                        stime=  listStorage.get(pos).getStart_time();
                 etime=   listStorage.get(pos).getEnd_time();

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
                    //Toast.makeText(con, f.toString(), Toast.LENGTH_SHORT).show();
                }
                }

                }



                else if(single){

String Accesst="";
Calendar cal= Calendar.getInstance();
  int today= cal.get(Calendar.DAY_OF_WEEK);

  switch (today){

      case 1:
           for(int d = 0; d<= Parent.sun.size()-1; d++){
               if(Parent.sun.get(d).getKid_id().equalsIgnoreCase(id)){
                   stime=  Parent.sun.get(d).getStart_time();
                   etime=   Parent.sun.get(d).getEnd_time();
                   Accesst=   Parent.sun.get(d).getAccess_type();
               }
           }
          break;

          case 2:
              for(int d = 0; d<= Parent.mon.size()-1; d++){
                  if(Parent.mon.get(d).getKid_id().equalsIgnoreCase(id)){
                      stime=  Parent.mon.get(d).getStart_time();
                      etime=   Parent.mon.get(d).getEnd_time();
                      Accesst=   Parent.mon.get(d).getAccess_type();
                  }
              }
          break;

              case 3:
              for(int d = 0; d<= Parent.tues.size()-1; d++){
                  if(Parent.tues.get(d).getKid_id().equalsIgnoreCase(id)){
                      stime=  Parent.tues.get(d).getStart_time();
                      etime=   Parent.tues.get(d).getEnd_time();
                      Accesst=   Parent.tues.get(d).getAccess_type();
                  }
              }
          break;

              case 4:
              for(int d = 0; d<= Parent.wed.size()-1; d++){
                  if(Parent.wed.get(d).getKid_id().equalsIgnoreCase(id)){
                      stime=  Parent.wed.get(d).getStart_time();
                      etime=   Parent.wed.get(d).getEnd_time();
                      Accesst=   Parent.wed.get(d).getAccess_type();
                  }
              }
          break;

              case 5:
              for(int d = 0; d<= Parent.thurs.size()-1; d++){
                  if(Parent.thurs.get(d).getKid_id().equalsIgnoreCase(id)){
                      stime=  Parent.thurs.get(d).getStart_time();
                      etime=   Parent.thurs.get(d).getEnd_time();
                      Accesst=   Parent.thurs.get(d).getAccess_type();
                  }
              }
          break;

              case 6:
              for(int d = 0; d<= Parent.fri.size()-1; d++){
                  if(Parent.fri.get(d).getKid_id().equalsIgnoreCase(id)){
                      stime=  Parent.fri.get(d).getStart_time();
                      etime=   Parent.fri.get(d).getEnd_time();
                      Accesst=   Parent.fri.get(d).getAccess_type();
                  }
              }
          break;

              case 7:
              for(int d = 0; d<= Parent.sat.size()-1; d++){
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
                       // Toast.makeText(con, "second" + f.toString(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

access_type = Parent.access_type;

                if(Parent.access_type.equalsIgnoreCase("timed")) {

                    String pattern = "HH:mm";
                    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                    String currentTimetosend = new SimpleDateFormat("HH:mm").format(new Date());

                    try {
                        Date dateEnd = sdf.parse(etime);
                        Date curent = sdf.parse(currentTimetosend);


                        Parent.Remaining_hour = dateEnd.getTime() - curent.getTime();
                        remaining_time= Parent.Remaining_hour;

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }


            return  value;
        }




      /*  List<ChildModel> UpdateAppList(final SwitchCompat c, final ImageView settime,final ImageView delete,
                                       final ImageView edit, final List<ChildModel> initial, final int posi, final ViewHolder hold) {

            c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (c.isChecked()) {

                        String time_slot = (initial.get(posi).getAccess_type());

                       // MainActivity.childModelList.add(new ChildModel("Unlimited"));

                        listStorage.get(posi).setAccess_type("Unlimited");
                        
                        for (int r = 0; r <= MainActivity.childModelList.size()-1; r++) {
               if (MainActivity.childModelList.get(r).getName().equalsIgnoreCase(hold.Child_name.getText().toString())) {
                                MainActivity.childModelList.get(r).setAccess_type("Unlimited");
                                notifyDataSetChanged();

                                break;
                            }
                        }
                    }

                    else if(!c.isChecked()) {

                        listStorage.get(posi).setAccess_type("Restricted");

                        //  Toast.makeText(con, hold.packageInListView.getText(), Toast.LENGTH_SHORT).show();
                        //int a=MainActivity.selectedApp.indexOf(hold.packageInListView.getText());

                        for (int r = 0; r <= MainActivity.childModelList.size()-1; r++) {
                            if (MainActivity.childModelList.get(r).getName().equalsIgnoreCase(hold.Child_name.getText().toString())) {
                                MainActivity.childModelList.get(r).setAccess_type("Restricted");
                                notifyDataSetChanged();

                                break;
                            }
                        }


                        //MainActivity.selectedApp.(hold.packageInListView.getText());

                        // MainActivity.selectedApp.add( MainActivity.selectedApp.remove(a));

                    }
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                        //  Toast.makeText(con, hold.packageInListView.getText(), Toast.LENGTH_SHORT).show();
                        //int a=MainActivity.selectedApp.indexOf(hold.packageInListView.getText());

                        for (int r = 0; r <= MainActivity.childModelList.size()-1; r++) {
                            if (MainActivity.childModelList.get(r).getName().equalsIgnoreCase(hold.Child_name.getText().toString())) {
                                MainActivity.childModelList.remove(r);
                                notifyDataSetChanged();
                        Toast.makeText(con, hold.Child_name.getText()+ " " +
                                "was Deleted Successfully", Toast.LENGTH_SHORT).show();

                                break;
                            }
                        }


                        //MainActivity.selectedApp.(hold.packageInListView.getText());

                        // MainActivity.selectedApp.add( MainActivity.selectedApp.remove(a));


                }
            });


            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

MainActivity.edit_id=posi;
                    FragmentTransaction transaction = act.getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragmain,new Edit_child());
                    // transaction.addToBackStack(null);
                    transaction.commit();
                    Toast.makeText(con, "EDIT WAS CLICKED", Toast.LENGTH_SHORT).show();


                }
            });

            settime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    Toast.makeText(con, "Set Time WAS CLICKED", Toast.LENGTH_SHORT).show();


                }
            });
            return MainActivity.childModelList;
        }

       */

        public void PasswordScreen(int pos) {
            try {
               /* final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);
                //alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setIcon(R.drawable.pass);
                LayoutInflater inflater = act.getLayoutInflater();
               // final View dialog = inflater.inflate(R.layout.access_control_child, null);
*/

                ImageView Ninos_Mascot;
                Animation moving_mascot;
                Animation moving_mascot_shake;
                TextView error;
                Dialog dialog = new Dialog(con);
                LayoutInflater inflater = act.getLayoutInflater();


                if(User_account_screen.isBiScreen==true) {
                    dialog2 = inflater.inflate(R.layout.access_control_child_large, null);
                }
                else if(User_account_screen.isBiScreen==false){
                    dialog2 = inflater.inflate(R.layout.access_control_child, null);

                }
             Ninos_Mascot = dialog2.findViewById(R.id.profile);
             error = dialog2.findViewById(R.id.forget);

            moving_mascot = AnimationUtils.loadAnimation(act, R.anim.bounce);
            moving_mascot_shake = AnimationUtils.loadAnimation(act, R.anim.shake);

            Ninos_Mascot.startAnimation(moving_mascot_shake);

            Ninos_Mascot.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Ninos_Mascot.startAnimation(moving_mascot);
                }
            });
                    dialog.setContentView(dialog2);

                    //dialog.setTitle("Mr MINP");
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    // set the custom dialog components - text, image and button

                    final EditText pass;
               // final de.hdodenhof.circleimageview.CircleImageView pix;
                final EditText pass_confirm;

              //  if (Build.VERSION.SDK_INT >= 21) {
               //     alertDialogBuilder.setView(dialog);
                 //   final AlertDialog alert= alertDialogBuilder.create();

                    pass= ((EditText)dialog.findViewById(R.id.pass));
                 //  pix= ((de.hdodenhof.circleimageview.CircleImageView)dialog.findViewById(R.id.profile));


                  /*  Picasso.get()
                            .load(R.drawable.nino_log2)
                            .placeholder(R.drawable.upload)
                            .error(R.drawable.upload).
                            into(pix);

                   */
//pix.setImageURI(Uri.parse(new File(listStorage.get(pos).getIcon()).toString()));

                    ((ImageView)dialog.findViewById(R.id.close)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            User_account_screen.imm.hideSoftInputFromWindow(view.getRootView().getWindowToken(), 0);

                            dialog.dismiss();

                        }
                    });

                    ((Button)dialog.findViewById(R.id.save)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            String p= pass.getText().toString();

                            if (p.equals(listStorage.get(pos).getPass())) {
                                showCustomDialog();
                                StyleableToast.makeText(con,  "Login Successful",
                                        Toast.LENGTH_LONG, R.style.mytoast3).show();

                                // notifyDataSetChanged();
                                Parent.selectedApp_2.clear();
                                Parent.selectedApp_3.clear();
                                Parent.selectedApp_2 = Parent.dbhelper.getApplist(Parent.kid_id);


                                User_account_screen.imm.hideSoftInputFromWindow(view.getRootView().getWindowToken(), 0);

                                dialog.dismiss();

                                con.startActivity(new Intent(con, Swipe_home.class));


                            }
                            else{
                                pass.setError("Password MisMatch");
                                error.setVisibility(View.VISIBLE);
                                error.setText("Please contact your parent for your password or try again");
                                User_account_screen.imm.hideSoftInputFromWindow(view.getRootView().getWindowToken(), 0);

                            }
                        }
                    });



                dialog.show();
               /* }

                else {
                    alertDialogBuilder.setMessage("Coming soon...");




                }

                */

            } catch (Exception e) {
               // System.out.println(e.getMessage());
            }
        }

    }





    /* access modifiers changed from: protected */
    public void showCustomDialog() {
        Parent.dialog = new Dialog(act, android.R.style.Theme_Translucent);
        Parent.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Parent.dialog.setCancelable(true);
        Parent.dialog.setContentView(R.layout.layout_dialog);
        ((ImageView)  Parent.dialog.findViewById(R.id.loader)).startAnimation(AnimationUtils.loadAnimation(act, R.anim.rotate));
        Parent.dialog.getWindow().setBackgroundDrawable( new ColorDrawable(0x7f000000));
        Parent.dialog.show();
    }


}



