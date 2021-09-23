package com.example.androidebook.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.androidebook.Adapter.CategoryAdapter;
import com.example.androidebook.Adapter.NothingSelectedSpinnerAdapter;
import com.example.androidebook.Adapter.SchoolAdapter;
import com.example.androidebook.Fragment.HomeFragment;
import com.example.androidebook.InterFace.InterstitialAdView;
import com.example.androidebook.Item.CategoryList;
import com.example.androidebook.Item.SchoolList;
import com.example.androidebook.Util.API;
import com.example.androidebook.Util.Constant_Api;
import com.example.androidebook.Util.Method;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import cz.msebera.android.httpclient.Header;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidebook.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class choose_school extends AppCompatActivity {
    private Method method;
    private ProgressBar progressBar;
    private TextView textView_noData;
    private List<SchoolList> schoolLists;
    private List<CategoryList> categoryLists;
    public static ArrayList<String> itemclick;
  public static String first_element;
  public static String first_replacement;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SchoolAdapter schoolAdapter;
    private CategoryAdapter categoryAdapter;
    private ArrayList<String> mCategoryName;
    private InterstitialAdView interstitialAdView;
    private Menu rota;
    public static Button save;
    View vv;
    private boolean is_refreshed=false;
    ImageView reload;
    public static TextView tx;
    private int is_loaded=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_school);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(" ");

        setSupportActionBar(toolbar);
        method = new Method(choose_school.this, interstitialAdView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
//MainActivity.navigationView.setVisibility(View.GONE);
//MainActivity.navigationView.setEnabled(false);
//MainActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

/*
if(MainActivity.is_clicked.equals("false")) {
    MainActivity.toolbar.removeViewAt(1);
    tx = new TextView(getActivity());
    tx.setText("Please Choose Your School");
    tx.setTextColor(Color.parseColor("white"));
    tx.setTextSize(15);


    MainActivity.toolbar.addView(tx);
}
else{
    SchoolFragment.tx.setText("Please Choose Your School");

}
*/

        schoolLists = new ArrayList<>();
        categoryLists = new ArrayList<>();
        mCategoryName = new ArrayList<>();
        itemclick=new ArrayList<String>();

        interstitialAdView = new InterstitialAdView() {
            @Override
            public void position(int position, String type, String id, String title, String fileType, String fileUrl) {
                // startActivity(new Intent(getActivity(), SchoolByList.class)
                //        .putExtra("title", title)
                //        .putExtra("id", id)
                //       .putExtra("type", type));
                call_course(id,title);
            }
        };
        // method = new Method(getActivity(), interstitialAdView);

        textView_noData = findViewById(R.id.textView_category);
        progressBar = findViewById(R.id.progressbar_category_fragment);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefresh_category_fragment);
        recyclerView = findViewById(R.id.recyclerView_category_fragment);
        save=findViewById(R.id.save_button);
        TextView terms=findViewById(R.id.terms);

         if(method.pref.getInt("terms",0)==1){
             terms.setVisibility(View.GONE);
         }


        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(choose_school.this);
        recyclerView.setLayoutManager(new GridLayoutManager(choose_school.this, 3));
        recyclerView.setFocusable(false);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.toolbar);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (method.isNetworkAvailable()) {
                    Category();
                } else {
                    method.alertBox(getResources().getString(R.string.internet_connection));
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        if (method.isNetworkAvailable()) {
            Category();
        } else {
            method.alertBox(getResources().getString(R.string.internet_connection));
            progressBar.setVisibility(View.GONE);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!itemclick.isEmpty()) {
                   // call_course(itemclick.get(0), "name");
                    String temp="";
                    String query=new String(itemclick.toString());

                    query=query.replace("[","");
                    query=query.replace("]","");
                    query=query.replace(",","");

                    if(first_element.equals("")){
                        temp=first_replacement.replace("OR b.sid=", "");
                        query=temp + " " +query;


                        if(first_replacement.equals("")){
                            itemclick.trimToSize();
                            temp=itemclick.get(0);
                            temp=temp.replace("OR b.sid=", "");
                            query=temp + " "+query;
                        }
                    }



                   // method.alertBox(query);
                    call_course(query, "name");
                }
            }
        });





    }

    public void call_course(String id, String name){
        // MainActivity.is_clicked="true";
        method.editor.putString("School_name", name);
        method.editor.putString("School_id", id);
        // method.editor.putString("is_clicked", MainActivity.is_clicked);
        method.editor.commit();
        //getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new HomeFragment(), name).commit();
        Intent i = new Intent(choose_school.this, MainActivity.class);
        startActivity(i);
        finishAffinity();
    }

    private void showSearch() {
        final Dialog mDialog = new Dialog(choose_school.this, R.style.Theme_AppCompat_Translucent);
        mDialog.setContentView(R.layout.search_sch_dialog);
        final EditText edtSearch = mDialog.findViewById(R.id.edt_name);
        Button btnSubmit = mDialog.findViewById(R.id.btn_submit);
        ImageView close = mDialog.findViewById(R.id.txtClose);
        final Spinner spinnerAuthor = mDialog.findViewById(R.id.spName);
        TextView text_search_title = mDialog.findViewById(R.id.text_search_title);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(choose_school.this, R.layout.spinner_item, mCategoryName);
        typeAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerAuthor.setAdapter(
                new NothingSelectedSpinnerAdapter(typeAdapter,
                        R.layout.contact_spinner_row_nothing_selected_cat,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        choose_school.this));

        edtSearch.setTypeface(Method.scriptable);
        btnSubmit.setTypeface(Method.scriptable);
        text_search_title.setTypeface(Method.scriptable);

        spinnerAuthor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.search_text));
                    ((TextView) parent.getChildAt(0)).setTextSize(14);
                    ((TextView) parent.getChildAt(0)).setTypeface(Method.scriptable);
                } else {
                    ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.search_text));
                    ((TextView) parent.getChildAt(0)).setTextSize(14);
                    ((TextView) parent.getChildAt(0)).setTypeface(Method.scriptable);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = edtSearch.getText().toString();
                if (spinnerAuthor.getSelectedItemPosition() == 0) {
                    Toast.makeText(choose_school.this, "Please select a School", Toast.LENGTH_SHORT).show();
                } else {
                    if (!search.isEmpty()) {
                        Intent intent = new Intent(choose_school.this, Find.class);
                        intent.putExtra("id", schoolLists.get(spinnerAuthor.getSelectedItemPosition() - 1).getCid());
                        intent.putExtra("search", search);
                        intent.putExtra("type", "category_search");
                        startActivity(intent);
                        mDialog.dismiss();
                    } else {
                        edtSearch.setError(getResources().getString(R.string.please_enter_book));
                    }

                }

            }
        });
        mDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflater.inflate(, menu);
        getMenuInflater().inflate(R.menu.search_school, menu);
        rota = menu;

        /*
        reload = (ImageView) menu.findItem(R.id.refresh).getActionView();
        reload.setScaleX(100);
         reload.setScaleY(100);

        reload.setImageResource(R.drawable.refresh);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

is_refreshed=true;

                vv = view;
                Animation rotation = AnimationUtils.loadAnimation(requireActivity(), R.anim.rotation);
                if(method.isNetworkAvailable()) {
                    view.startAnimation(rotation);
Category();
                }
                else{
                    view.clearAnimation();
                    method.alertBox("No Internet Connection");
                }
            }


        });
*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();// action with ID action_refresh was selected
        if (itemId == R.id.ic_search) {
            if (schoolLists.size() != 0) {
                showSearch();
            } else {
                method.alertBox(getResources().getString(R.string.wrong));
            }
        } else if (itemId == R.id.refresh2) {
            if (method.isNetworkAvailable()) {
                if (is_loaded == 0) {
                    Category();
                    is_loaded = 1;
                }
            } else {

                method.alertBox("No Internet Connection");
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void Category() {

        schoolLists.clear();
        categoryLists.clear();
        mCategoryName.clear();

        progressBar.setVisibility(View.VISIBLE);



            AsyncHttpClient client = new AsyncHttpClient(true,80,443);
            RequestParams params = new RequestParams();
            JsonObject jsObj = (JsonObject) new Gson().toJsonTree(new API(choose_school.this));
            jsObj.addProperty("method_name", "get_categories");  //get_school
            jsObj.addProperty("package_name", API.package_name);
            jsObj.addProperty("sign", API.sign);
            jsObj.addProperty("salt", API.salt);

            params.put("data", API.toBase64(jsObj.toString()));
            client.post(Constant_Api.url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    Log.d("Response", new String(responseBody));
                    String res = new String(responseBody);

                    try {
                        JSONObject jsonObject = new JSONObject(res);

                        JSONArray jsonArray = jsonObject.getJSONArray(Constant_Api.tag);

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                           /* String cid = object.getString("sid");
                            String category_name = object.getString("school_name");//school_name
                            String total_books = object.getString("total_course"); //total_course
                            String cat_image = object.getString("school_image");//school_image
                            mCategoryName.add(object.getString("school_name"));//school_name

                            schoolLists.add(new SchoolList(cid, category_name, total_books, cat_image));
*/ String cid = object.getString("cid");
                            String category_name = object.getString("category_name");//school_name
                            String total_books = object.getString("total_books"); //total_course
                            String cat_image = object.getString("category_image");//school_image
                            mCategoryName.add(object.getString("category_name"));//school_name

                            categoryLists.add(new CategoryList(cid, category_name, total_books, cat_image));

                        }

                        if (categoryLists.size() == 0) {
                            if(is_refreshed==true) {
                                vv.clearAnimation();
                                is_refreshed=false;
                            }
                            textView_noData.setVisibility(View.VISIBLE);
                        } else {
                            textView_noData.setVisibility(View.GONE);
                            //schoolAdapter = new SchoolAdapter(choose_school.this, categoryLists, "school", interstitialAdView);
        categoryAdapter = new CategoryAdapter(choose_school.this, categoryLists, "category", interstitialAdView);
                            recyclerView.setAdapter(categoryAdapter);
                            if(is_refreshed==true) {
                                vv.clearAnimation();
                                is_refreshed=false;
                            }

                        }

                        progressBar.setVisibility(View.GONE);
                       is_loaded=0;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        is_loaded=0;
                        if(is_refreshed==true) {
                            vv.clearAnimation();
                            is_refreshed=false;
                        }
                        is_loaded=0;
                        progressBar.setVisibility(View.GONE);
                        method.alertBox(getResources().getString(R.string.contact_msg));
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    progressBar.setVisibility(View.GONE);
                    is_loaded=0;
                    if(is_refreshed==true) {
                        vv.clearAnimation();
                        is_refreshed=false;
                    }
                }
            });

        }
    }







