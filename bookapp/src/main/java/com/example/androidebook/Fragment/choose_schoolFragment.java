package com.example.androidebook.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.androidebook.Activity.AuthorByList;
import com.example.androidebook.Activity.BookDetail;
import com.example.androidebook.Activity.CategoryByList;
import com.example.androidebook.Activity.Find;
import com.example.androidebook.Activity.MainActivity;
import com.example.androidebook.Adapter.BookAdapterGV;
import com.example.androidebook.Adapter.BookHomeAdapterGV;
import com.example.androidebook.Adapter.CategoryAdapter;
import com.example.androidebook.Adapter.CategoryHomeAdapter;
import com.example.androidebook.Adapter.HomeAuthorAdapter;
import com.example.androidebook.Adapter.SchoolHomeAdapter;
import com.example.androidebook.DataBase.DatabaseHandler;
import com.example.androidebook.InterFace.InterstitialAdView;
import com.example.androidebook.Item.AuthorList;
import com.example.androidebook.Item.CategoryList;
import com.example.androidebook.Item.SchoolList;
import com.example.androidebook.Item.SubCategoryList;
import com.example.androidebook.R;
import com.example.androidebook.Util.API;
import com.example.androidebook.Util.Constant_Api;
import com.example.androidebook.Util.Method;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class choose_schoolFragment extends Fragment {


    private Method method;
    private ProgressBar progressBar;

    private List<SchoolList> schoolLists;



    private List<CategoryList> categoryLists;
    private CategoryAdapter catAdapter;
    private InterstitialAdView interstitialAdView;
    private InputMethodManager imm;
    private DatabaseHandler db;
    private EditText editText_search;
    private LinearLayout linearLayout_continue;
    private RecyclerView recyclerViewContinue,recyclerViewSchool, recyclerViewLatest, recyclerViewPopular, recyclerViewCategory, recyclerViewAuthor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.category_fragment, container, false);

        MainActivity.toolbar.setTitle("Select School");

        db = new DatabaseHandler(getActivity());

        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        interstitialAdView = new InterstitialAdView() {
            @Override
            public void position(int position, String type, String id, String title, String fileType, String fileUrl) {
                if (type.equals("home_cat")) {
                    startActivity(new Intent(getActivity(), CategoryByList.class)
                            .putExtra("title", title)
                            .putExtra("id", id)
                            .putExtra("type", type));
                } else if (type.equals("home_author")) {
                    startActivity(new Intent(getActivity(), AuthorByList.class)
                            .putExtra("title", title)
                            .putExtra("id", id)
                            .putExtra("type", type));
                } else {
                    startActivity(new Intent(getActivity(), BookDetail.class)
                            .putExtra("bookId", id)
                            .putExtra("position", position)
                            .putExtra("type", type));
                }
            }
        };
        method = new Method(requireActivity(), interstitialAdView);


        schoolLists = new ArrayList<>();
        categoryLists = new ArrayList<>();

        DatabaseHandler db = new DatabaseHandler(getActivity());

        progressBar = view.findViewById(R.id.progreesbar_home_fragment);

        recyclerViewSchool = view.findViewById(R.id.recyclerViewSch_home_fragment);
       // mDemoSlider = view.findViewById(R.id.custom_indicator_home_fragment);







        recyclerViewSchool.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerSch = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSchool.setLayoutManager(layoutManagerSch);
        recyclerViewSchool.setFocusable(false);


        editText_search = view.findViewById(R.id.editText_home_fragment);
        ImageView imageView_search = view.findViewById(R.id.imageView_search_home_fragment);

      //  Button button_sch = view.findViewById(R.id.button_latest_home_fragment_sch);



      //  button_sch.setOnClickListener(new View.OnClickListener() {
      //      @Override
      //      public void onClick(View v) {
       //         requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new SchoolFragment(), "school").commit();
       //     }
       // });


        editText_search.setTypeface(null);

        editText_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search();
                }
                return false;
            }
        });


        imageView_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        if (method.isNetworkAvailable()) {
            Home();
        } else {
            method.alertBox(getResources().getString(R.string.internet_connection));
            progressBar.setVisibility(View.GONE);
        }

        setHasOptionsMenu(true);

        return view;
    }

    public void search() {

        String search = editText_search.getText().toString();
        //do something
        if (!search.isEmpty() || !search.equals("")) {

            editText_search.clearFocus();
            imm.hideSoftInputFromWindow(editText_search.getWindowToken(), 0);

            startActivity(new Intent(getActivity(), Find.class)
                    .putExtra("id", "0")
                    .putExtra("search", search)
                    .putExtra("type", "normal"));
        } else {
            if (getActivity().getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            }
            method.alertBox(getResources().getString(R.string.wrong));
        }

    }

    private void Home() {

        progressBar.setVisibility(View.VISIBLE);

        if (getActivity() != null) {

            JsonObject jsObj = (JsonObject) new Gson().toJsonTree(new API(getActivity()));
            jsObj.addProperty("method_name", "get_category");
            jsObj.addProperty("package_name", API.package_name);
            jsObj.addProperty("sign", API.sign);
            jsObj.addProperty("salt", API.salt);

            RequestQueue mRequestQueue;
            // Instantiate the cache
            Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap

            // Set up the network to use HttpURLConnection as the HTTP client.
            Network network = new BasicNetwork(new HurlStack());

// Instantiate the RequestQueue with the cache and network.
            mRequestQueue = new RequestQueue(cache, network);

// Start the queue
            mRequestQueue.start();

            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    Constant_Api.url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String responseBody) {

                            Log.d("Response", new String(responseBody));
                            String res = new String(responseBody);

                            try {
                                JSONObject jsonObject = new JSONObject(res);



                                JSONArray jsonArraySch = jsonObject.getJSONArray(Constant_Api.tag);


                                for (int i = 0; i < jsonArraySch.length(); i++) {

                                    JSONObject object = jsonArraySch.getJSONObject(i);
                                    String cid = object.getString("cid");
                                    String category_name = object.getString("category_name");
                                    String total_books = object.getString("total_books");
                                    String cat_image = object.getString("category_image");

                                    categoryLists.add(new CategoryList(cid, category_name, total_books, cat_image));

                                }




                                catAdapter = new CategoryAdapter(getActivity(), categoryLists, "category1", interstitialAdView);
                                recyclerViewSchool.setAdapter(catAdapter);

                                progressBar.setVisibility(View.GONE);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressBar.setVisibility(View.GONE);
                                method.alertBox(getResources().getString(R.string.contact_msg));
                            }

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    try {

                        progressBar.setVisibility(View.GONE);
                    } catch (Exception f) {

                    }

                }
            }) {

                @Override
                protected Map<String, String> getParams() {


                    Map<String, String> params = new HashMap<String, String>();

                    params.put("data", API.toBase64(jsObj.toString()));


                    return params;
                }


            };

//Adding request to request queue
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(80 * 1000, 3,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            mRequestQueue.add(stringRequest);


        }


    }




}
