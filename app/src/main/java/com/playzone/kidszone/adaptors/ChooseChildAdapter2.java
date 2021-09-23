package com.playzone.kidszone.adaptors;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Configuration;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.playzone.kidszone.MainActivity;
import com.playzone.kidszone.Parent;
import com.playzone.kidszone.R;
import com.playzone.kidszone.fragmentpackage.User_account_screen;
import com.playzone.kidszone.models.ChildModel;
import com.playzone.kidszone.models.active_child_model;

import java.io.File;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class ChooseChildAdapter2 extends RecyclerView.Adapter<ChooseChildAdapter2.ViewHolder> {


    private Activity mContext;
    MainActivity main;
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

FragmentActivity fa;

    public ChooseChildAdapter2(Activity con, List<ChildModel> list, FragmentActivity fa) {
        this.mContext = con;
        this.type = type;
this.fa = fa;
        mItems = list;
this.main= new MainActivity();

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


                holder.card.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {

                                                       Parent.kid_id=holder.id;
                                                       Parent.kid_name=holder.name.getText().toString();


                                Parent.dbhelper.addActiveChild(new active_child_model(Parent.kid_id, Parent.pemail));


                                                       for(int d = 0; d<= Parent.childModelList.size()-1; d++){

                    if(Parent.pemail.equals(Parent.pemail) && Parent.kid_id.equals(Parent.childModelList.get(d).Kid_id)){
                                                               Parent.CurrentchildModelList.clear();
                                                               Parent.CurrentchildModelList.add(Parent.childModelList.get(d));
                                                           }
                                                       }

                                                       if(Parent.CurrentchildModelList.isEmpty()){

                                                       }
                                                       else{
                                                           User_account_screen.   recycle2.setVisibility(View.VISIBLE);

                   ChildAccountAdapter mAdapter2 =
                             new ChildAccountAdapter(mContext, Parent.CurrentchildModelList, fa,   User_account_screen.fruits,
                                     User_account_screen.bgs);

                                                           User_account_screen.   recyclerView.setVisibility(View.GONE);
                              User_account_screen.   recycle2.swapAdapter(mAdapter2, true);
                                                          // setAdapter(User_account_screen.mAdapter2);
notifyDataSetChanged();
                                                           User_account_screen.alert2.dismiss();
 /*

                   FragmentTransaction transaction = main.getSupportFragmentManager().beginTransaction();
                                                           transaction .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                                                           transaction.replace(R.id.fragmain,new User_account_screen());
                                                           // transaction.addToBackStack(null);
                                                           transaction.commit();
 */

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

}