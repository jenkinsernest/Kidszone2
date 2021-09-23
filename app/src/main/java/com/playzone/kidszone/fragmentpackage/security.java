package com.playzone.kidszone.fragmentpackage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.playzone.kidszone.Parent;
import com.playzone.kidszone.R;
import com.playzone.kidszone.adaptors.securityAdaptor2;
import com.playzone.kidszone.models.SettingsModel;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class security extends Fragment{

    View parentview;

            ImageView back;
    RecyclerView recyclerView , recycle2;
    securityAdaptor2 mAdapter2;
public static InputMethodManager imm;

    public security() {
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
       parentview=  inflater.inflate(R.layout.security, container, false);


       back=(ImageView) parentview.findViewById(R.id.back);
        recyclerView = (RecyclerView)parentview.findViewById(R.id.adapt);


        recyclerView.setHasFixedSize(true);


        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        recyclerView.setFocusable(false);




        back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Parent.pc=3;

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



            Parent.settingsList.add(new SettingsModel());

            mAdapter2 = new securityAdaptor2(getActivity(), Parent.settingsList, getActivity(), security.this);


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
