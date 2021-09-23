package com.playzone.kidszone.fragmentpackage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.playzone.kidszone.Method;
import com.playzone.kidszone.Parent;
import com.playzone.kidszone.R;
import com.playzone.kidszone.SET_USER_DETAILS;
import com.playzone.kidszone.adaptors.GoalsAdaptor;
import com.playzone.kidszone.models.Goals_Model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class Educational_Goals extends Fragment{

    View parentview;

            ImageView back;
    RecyclerView recyclerView , recycle2;
    GoalsAdaptor mAdapter2;
public static InputMethodManager imm;
    public static int total=0;
public Switch learn_first;
public Button save;
    public Method method;
public static int total_goals_time=0;
public TextView kname;


    public Educational_Goals() {
        // Required empty public constructor
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
    public  void onResume(){
     //  showSystemUI();
        super.onResume();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       parentview=  inflater.inflate(R.layout.educational_goals, container, false);


       back=(ImageView) parentview.findViewById(R.id.back);
       kname=(TextView) parentview.findViewById(R.id.kidname);
       learn_first =(Switch) parentview.findViewById(R.id.learn_first);
       save=(Button) parentview.findViewById(R.id.save);
        recyclerView = (RecyclerView)parentview.findViewById(R.id.adapt);


        method= new Method(getActivity());
        recyclerView.setHasFixedSize(true);


        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        recyclerView.setFocusable(false);



        kname.setText(Parent.kid_name);


        Educational_Goals.total_goals_time=0;

save.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

      //  Parent.settingsList.add(new SettingsModel());
boolean Time_value=false;

        if(learn_first.isChecked()){

            for(int y=0; y<=Parent.educational_goals.size()-1; y++) {

                Parent.educational_goals.get(y).setLearn_first("true");
            }
        }
        else{
            for(int y=0; y<=Parent.educational_goals.size()-1; y++) {
Parent.educational_goals.get(y).setLearn_first("false");
            }
        }
        Parent.settingsList.get(0).setGoal("true");


        if (Parent.access_type.equalsIgnoreCase("timed")) {
            long minute = (Parent.Remaining_hour / 1000) / 60;

for(int y=0; y<=Parent.educational_goals.size()-1; y++) {

    Educational_Goals.total_goals_time+= Integer.parseInt(Parent.educational_goals.get(y).getAlotted_time());
}


            if (Educational_Goals.total_goals_time <= minute) {
                Time_value=true;
            }
            else{
                Time_value=false;
            }
        }
        else{
            Time_value=true;
        }


        if(Time_value){
            Parent.settingsList.get(0).setPname(Parent.parentModelList.get(0).getPname());
            Parent.settingsList.get(0).setPemail(Parent.parentModelList.get(0).getP_email());

            Parent.dbhelper.addSettings(Parent.settingsList.get(0));

           long res=  Parent.dbhelper.editGoal(Parent.educational_goals, Parent.kid_id);



            if (method.isNetworkAvailable()) {




                SET_USER_DETAILS set = new SET_USER_DETAILS();

          set.set_educational_goals(Parent.educational_goals,
                                            new Fragment(), getActivity());


            }


            if(Parent.full_screen) {
                Parent.isTyping = false;
                hideSystemUI();
            }
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
            transaction.replace(R.id.fragmain,new Parent_control());
            // transaction.addToBackStack(null);
            transaction.commit();


            method.alertBox("Educational Goals Saved", 2);

        }
        else{
            method.alertBox("Cant Exceed Screen Time", 3);
        }
    }
});

        back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {


               if(Parent.full_screen) {
                   Parent.isTyping = false;
                   hideSystemUI();
               }
               FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
               transaction .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
               transaction.replace(R.id.fragmain,new Parent_control());
               // transaction.addToBackStack(null);
               transaction.commit();
           }
       });


Parent.educational_goals.clear();
       // Goals_Model(String p_email, String kid, String taskname, String alotted_time, String active_day, String status)


           Parent.educational_goals=Parent.dbhelper.getGoals_byId(Parent.kid_id);

           if(Parent.educational_goals.size()==0){

               SimpleDateFormat formateDate2 = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
               Calendar date2 = Calendar.getInstance();
               String dates = null;

               dates = formateDate2.format(date2.getTime());


               Parent.educational_goals.add(new Goals_Model(Parent.pemail, Parent.kid_id,"Books", "0",dates,
                       "not active", "0", "false"));

 Parent.educational_goals.add(new Goals_Model(Parent.pemail, Parent.kid_id,"Learning Videos", "0",dates,
                       "not active", "0", "false"));

               Parent.dbhelper.addnGoal(Parent.educational_goals);


               Parent.educational_goals=Parent.dbhelper.getGoals_byId(Parent.kid_id);
           }

           if(Parent.educational_goals.get(0).getLearn_first().equals("true")){
               learn_first.setChecked(true);
           }
           else{
               learn_first.setChecked(false);

           }
            mAdapter2 = new GoalsAdaptor(getActivity(), Parent.educational_goals, getActivity(), Educational_Goals.this);


            recyclerView.setAdapter(mAdapter2);


        return parentview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imm = (InputMethodManager)  getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
       // (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    }




}
