package com.example.androidebook.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.androidebook.Activity.choose_school;
import com.example.androidebook.InterFace.InterstitialAdView;
import com.example.androidebook.Item.SchoolList;
import com.example.androidebook.R;
import com.example.androidebook.Util.Method;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class SchoolAdapter extends RecyclerView.Adapter<SchoolAdapter.ViewHolder> {

    private Method method;
    private Activity activity;
    private String type;
    private List<SchoolList> schoolLists;
    private int lastPosition = -1;
private  int index=0;
    public SchoolAdapter(Activity activity, List<SchoolList> schoolLists, String type, InterstitialAdView interstitialAdView) {
        this.activity = activity;
        this.type = type;
        this.schoolLists = schoolLists;
        method = new Method(activity, interstitialAdView);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.text_adapter, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.textView_Name.setText(schoolLists.get(position).getSchool_name());
        holder.textView_Name.setTypeface(Method.scriptable);

      /* if( method.pref.getInt(schoolLists.get(position).getCid(),0)==1){
           holder.textView_Name.setTextColor(Color.parseColor("white"));
           holder.textView_Name.setBackgroundResource(R.color.toolbar);

       }
*/
       // String totalBook = "(" + schoolLists.get(position).getTotal_course() + ") " + "Courses";
       // holder.textView_categoryCount.setText(totalBook);
       /* Picasso.get().load(schoolLists.get(position).getsch_image())
                .placeholder(R.drawable.placeholder_portable)
                .into(holder.imageView_category_adapter);
*/
        if (position > lastPosition) {
            if (position % 2 == 0) {
                Animation animation = AnimationUtils.loadAnimation(activity,
                        R.anim.up_from_bottom_right);
                holder.itemView.startAnimation(animation);
                lastPosition = position;
            } else {
                Animation animation = AnimationUtils.loadAnimation(activity,
                        R.anim.up_from_bottom_left);
                holder.itemView.startAnimation(animation);
                lastPosition = position;
            }
        }

        holder.textView_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // holder.clicked= method.pref.getInt(schoolLists.get(position).getCid(),0);
               // holder.name=schoolLists.get(position).getCid();

                if(holder.clicked==0) {

                    if(index>0){
                       method.alertBox("Single Selection Allowed Only..");
                    }
                   else {
                        holder.clicked = 1;
                   // method.editor.putInt(schoolLists.get(position).getCid(), holder.clicked);
                  //  method.editor.commit();

                    holder.textView_Name.setTextColor(Color.parseColor("white"));
                        holder.textView_Name.setBackgroundResource(R.color.toolbar);

                        if(index==0) {
                            holder.name=schoolLists.get(position).getCid();
                            choose_school.first_element=holder.name;
                            choose_school.itemclick.add( holder.name);
                        }
                        else{
                            holder.name="OR b.sid= "+ schoolLists.get(position).getCid();
                            choose_school.first_replacement=holder.name;
                          choose_school.itemclick.add(holder.name);

                        }

                        index += 1;
                        choose_school.save.setText("SAVE");
                        choose_school.save.setTextColor(Color.parseColor("white"));
                        choose_school.save.setBackgroundResource(R.drawable.button_select2);

                    }
                }
                else{
                    holder.clicked = 0;
                   // method.editor.putInt(schoolLists.get(position).getCid(), holder.clicked);
                   // method.editor.commit();

                    holder.textView_Name.setTextColor(Color.rgb(4,158,127));

                        holder.textView_Name.setBackgroundResource(R.drawable.text_bg);

                        if(holder.name.equals(choose_school.first_element)){
                            choose_school.first_element="";
                        }
                        else if(holder.name.equals(choose_school.first_replacement)){
                            choose_school.first_replacement="";
                        }
                 choose_school.itemclick.remove(holder.name);
                   index=choose_school.itemclick.size();

                    choose_school.save.setText("Select 1 Item");
                    choose_school.save.setTextColor(Color.parseColor("white"));
                    choose_school.save.setBackgroundResource(R.drawable.button_select);

                }
             // method.interstitialAdShow(position, type, schoolLists.get(position).getCid(), schoolLists.get(position).getSchool_name(),"","");

            }

        });

    }

    @Override
    public int getItemCount() {
        return schoolLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout relativeLayout;
        private TextView textView_Name, textView_categoryCount;
        RoundedImageView imageView_category_adapter;
        int clicked=0;
       private String name;

        public ViewHolder(View itemView) {
            super(itemView);

            //relativeLayout = itemView.findViewById(R.id.relativelayout_category_adapter);
            textView_Name = itemView.findViewById(R.id.school_name);
            //textView_categoryCount = itemView.findViewById(R.id.textView_categoryCount_category_adapter);
            //imageView_category_adapter = itemView.findViewById(R.id.imageView_category_adapter);
        }
    }
}
