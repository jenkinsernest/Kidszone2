package com.example.androidebook.Fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
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
import com.example.androidebook.Activity.AuthorByList;
import com.example.androidebook.Activity.BookDetail;
import com.example.androidebook.Activity.CategoryByList;
import com.example.androidebook.Activity.Find;
import com.example.androidebook.Adapter.BookAdapterGV;
import com.example.androidebook.Adapter.BookHomeAdapterGV;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener {
    static BottomNavigationView navigation;

private boolean is_detached=false;
    private Method method;
    private ProgressBar progressBar;
    private List<SubCategoryList> continueList;
    private List<SubCategoryList> sliderList;
    private List<SubCategoryList> latestList;
    private List<SubCategoryList> mostPopularList;
    private List<CategoryList> categoryLists;
    private List<SchoolList> schoolLists;
    private List<AuthorList> authorLists;
    private BookHomeAdapterGV latestAdapter;
    private BookAdapterGV latestAdapter2;
    private BookAdapterGV popularAdapter;
    private BookHomeAdapterGV continueAdapter;
    private HomeAuthorAdapter authorAdapter;
    private SliderLayout mDemoSlider;
    private CategoryHomeAdapter categoryAdapter;
    private SchoolHomeAdapter schoolAdapter;
    private InterstitialAdView interstitialAdView;
    private InputMethodManager imm;
    private DatabaseHandler db;
    private EditText editText_search;
    public Menu rota;
    public ImageView reload;
    static View vv;
    private boolean is_refreshed=false;
    private LinearLayout linearLayout_continue;
    static android.widget.SearchView sv;
     public ImageView load;
    private RecyclerView recyclerViewContinue,recyclerViewSchool, recyclerViewLatest, recyclerViewPopular, recyclerViewCategory, recyclerViewAuthor;

/*

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:
                    // mTextMessage.setText(R.string.title_home);
                    // Toast.makeText(getApplicationContext(),"home", Toast.LENGTH_LONG).show();
                   requireActivity(). getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new HomeFragment(), "Home").commit();

                    return true;

                case R.id.download:
                    // Toast.makeText(getApplicationContext(),"Search", Toast.LENGTH_LONG).show();

                    if (Method.allowPermissionExternalStorage) {
                        requireActivity(). getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new DownloadFragment(), "Download").commit();
                    } else {
                        MainActivity.download_menu = true;
                        MainActivity main=new MainActivity();
                        main.checkPer();
                    }

                    return true;

                case R.id.profile:
                    startActivity(new Intent(getActivity(), Profile.class));


                    return true;


            }
            return false;
        }
    };


*/



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_fragment, container, false);

        method = new Method(requireActivity(), interstitialAdView);
      //  MainActivity.toolbar.setTitle(method.pref.getString("School_name",null));
       // SchoolFragment.tx.setText(method.pref.getString("School_name",null));
       // MainActivity.navigationView.setVisibility(View.VISIBLE);
       // MainActivity.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        db = new DatabaseHandler(getActivity());

        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

       // navigation = view.findViewById(R.id.navigation);
     //   navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        method.editor.putInt("terms", 1);
          method.editor.commit();



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


        continueList = new ArrayList<>();
        sliderList = new ArrayList<>();
        latestList = new ArrayList<>();
        mostPopularList = new ArrayList<>();
        categoryLists = new ArrayList<>();
        schoolLists = new ArrayList<>();
        authorLists = new ArrayList<>();

        DatabaseHandler db = new DatabaseHandler(getActivity());
        continueList.clear();
        continueList = db.getContinueBook("4");

        progressBar = view.findViewById(R.id.progreesbar_home_fragment);
        linearLayout_continue = view.findViewById(R.id.linearLayout_continue_home_fragment);
        recyclerViewContinue = view.findViewById(R.id.recyclerViewContinue_home_fragment);
        recyclerViewLatest = view.findViewById(R.id.recyclerViewLatest_home_fragment);
        recyclerViewPopular = view.findViewById(R.id.recyclerViewPopular_home_fragment);
        recyclerViewCategory = view.findViewById(R.id.recyclerViewCat_home_fragment);
       // recyclerViewSchool = view.findViewById(R.id.recyclerViewSch_home_fragment);
      recyclerViewAuthor = view.findViewById(R.id.recyclerViewAuthor_home_fragment);
       // mDemoSlider = view.findViewById(R.id.custom_indicator_home_fragment);
         load=view.findViewById(R.id.reload);
        LinearLayout linearLayout = view.findViewById(R.id.linearLayout_adView_home_fragment);

        if (Method.personalization_ad) {
            method.showPersonalizedAds(linearLayout);
        } else {
            method.showNonPersonalizedAds(linearLayout);
        }

        View viewContinue = view.findViewById(R.id.viewContinue_home_fragment);
        View viewCat = view.findViewById(R.id.viewCat_home_fragment);
      //  View viewSch = view.findViewById(R.id.viewSch_home_fragment);
        View viewLatest = view.findViewById(R.id.viewLatest_home_fragment);
        View viewPopular = view.findViewById(R.id.viewPopular_home_fragment);
        View viewAuthor = view.findViewById(R.id.viewAuthor_home_fragment);

        if (getResources().getString(R.string.isRTL).equals("true")) {
            //viewContinue.setBackground(getResources().getDrawable(R.drawable.bg_gradient_white_rtl));
           // viewCat.setBackground(getResources().getDrawable(R.drawable.bg_gradient_white_rtl));
           // viewSch.setBackground(getResources().getDrawable(R.drawable.bg_gradient_white_rtl));
            //viewLatest.setBackground(getResources().getDrawable(R.drawable.bg_gradient_white_rtl));
           // viewPopular.setBackground(getResources().getDrawable(R.drawable.bg_gradient_white_rtl));
           // viewAuthor.setBackground(getResources().getDrawable(R.drawable.bg_gradient_white_rtl));
        } else {
          //  viewContinue.setBackground(getResources().getDrawable(R.drawable.bg_gradient_white));
           // viewCat.setBackground(getResources().getDrawable(R.drawable.bg_gradient_white));
           // viewSch.setBackground(getResources().getDrawable(R.drawable.bg_gradient_white));
           // viewLatest.setBackground(getResources().getDrawable(R.drawable.bg_gradient_white));
           // viewPopular.setBackground(getResources().getDrawable(R.drawable.bg_gradient_white));
           // viewAuthor.setBackground(getResources().getDrawable(R.drawable.bg_gradient_white));
        }

        TextView text_home_cat = view.findViewById(R.id.text_home_cat);
       // TextView text_home_sch = view.findViewById(R.id.text_home_sch);
        TextView text_home_latest = view.findViewById(R.id.text_home_latest);
        TextView text_home_popular = view.findViewById(R.id.text_home_popular);
      text_home_cat.setTypeface(Method.scriptable);
       // text_home_sch.setTypeface(Method.scriptable);
        text_home_latest.setTypeface(Method.scriptable);
        text_home_popular.setTypeface(Method.scriptable);

        linearLayout_continue.setVisibility(View.GONE);

        recyclerViewContinue.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager_Continue = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewContinue.setLayoutManager(layoutManager_Continue);
        recyclerViewContinue.setFocusable(false);

        recyclerViewLatest.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerLatest = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewLatest.setLayoutManager(layoutManagerLatest);
        recyclerViewLatest.setFocusable(false);

        recyclerViewPopular.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerPopular = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopular.setLayoutManager(layoutManagerPopular);
        recyclerViewPopular.setFocusable(false);

        recyclerViewCategory.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerCat = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategory.setLayoutManager(layoutManagerCat);
        recyclerViewCategory.setFocusable(false);


       // recyclerViewSchool.setHasFixedSize(true);
       // RecyclerView.LayoutManager layoutManagerSch = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
       // recyclerViewSchool.setLayoutManager(layoutManagerSch);
       // recyclerViewSchool.setFocusable(false);


        recyclerViewAuthor.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManagerAuthor = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewAuthor.setLayoutManager(layoutManagerAuthor);
        recyclerViewAuthor.setFocusable(false);

        editText_search = view.findViewById(R.id.editText_home_fragment);
        ImageView imageView_search = view.findViewById(R.id.imageView_search_home_fragment);
        Button button_continue = view.findViewById(R.id.button_continue_home_fragment);
        Button button_latest = view.findViewById(R.id.button_latest_home_fragment);
        Button button_popular = view.findViewById(R.id.button_popular_home_fragment);
        Button button_cat = view.findViewById(R.id.button_latest_home_fragment_cat);
        Button button_sch = view.findViewById(R.id.button_latest_home_fragment_sch);
       Button button_author = view.findViewById(R.id.button_author_home_fragment);

        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = requireActivity().getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(getResources().getColor(R.color.black));
                }

requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new ContinueFragment(), "Continue")
        .addToBackStack(null).commit();
            }
        });

        button_latest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = requireActivity().getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(getResources().getColor(R.color.black));
                }

                LatestFragment latestFragment = new LatestFragment();
                Bundle bundle = new Bundle();
                bundle.putString("type", "latest");
                latestFragment.setArguments(bundle);
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main,
                        latestFragment, "Latest").addToBackStack(null).commit();
            }
        });

        button_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = requireActivity().getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(getResources().getColor(R.color.black));
                }

                requireActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.framlayout_main, new CategoryFragment(), "Categories").addToBackStack(null).commit();
            }
        });

      //  button_sch.setOnClickListener(new View.OnClickListener() {
      //      @Override
      //      public void onClick(View v) {
          //      requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new SchoolFragment(), "school").commit();
         //   }
       // });

        button_popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
  //requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new HomeFragment(), "home").commit();
Refresh();
               /* LatestFragment latestFragment = new LatestFragment();
                Bundle bundle = new Bundle();
                bundle.putString("type", "popular");
                latestFragment.setArguments(bundle);
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main,
                        latestFragment, "Latest").commit();

                */
            }
        });
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Refresh();
            }
        });

        button_author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = requireActivity().getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(getResources().getColor(R.color.black));
                }

                requireActivity().getSupportFragmentManager().
                        beginTransaction().replace(R.id.framlayout_main, new AuthorFragment(), "Authors").addToBackStack(null).commit();
            }
        });

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
            Refresh();
        } else {
            method.alertBox(getResources().getString(R.string.internet_connection));
            progressBar.setVisibility(View.GONE);


        }

        setHasOptionsMenu(true);

        return view;
    }



    public void Refresh(){
        if(is_refreshed==false) {

            is_refreshed = true;

            Animation rotation = AnimationUtils.loadAnimation(requireActivity(), R.anim.rotation);
            if (method.isNetworkAvailable()) {
                load.startAnimation(rotation);

                sliderList.clear();
                latestList.clear();
                mostPopularList.clear();
                categoryLists.clear();
                schoolLists.clear();
                authorLists.clear();
Home();
            } else {
                method.alertBox("No Internet Connection");
            }
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.homepage_menu, menu);
        rota = menu;
        sv = (android.widget.SearchView) menu.findItem(R.id.searchme).getActionView();
        sv.setQueryHint("search me..");
        SearchManager sm = (SearchManager) requireActivity().getSystemService(Context.SEARCH_SERVICE);
        sv.setSearchableInfo(sm.getSearchableInfo(requireActivity().getComponentName()));
        // search.setQueryHint("Search KeyWord");

        sv.setIconified(false);


        sv.setOnQueryTextListener(HomeFragment.this);
        reload = (ImageView) menu.findItem(R.id.refresh).getActionView();
        // reload.setScaleX(100);
        //  reload.setScaleY(100);
        reload.setImageResource(R.drawable.refresh);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


if(is_refreshed==false) {
    is_refreshed=true;
    vv = view;
    Animation rotation = AnimationUtils.loadAnimation(requireActivity(), R.anim.rotation);
    if (method.isNetworkAvailable()) {
        view.startAnimation(rotation);
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new HomeFragment(), "home").commit();
        view.clearAnimation();
    } else {
        method.alertBox("No Internet Connection");
    }
}
            }


        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // action with ID action_refresh was selected
        if (item.getItemId() == R.id.refresh) {
        }

        return super.onOptionsItemSelected(item);
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
            jsObj.addProperty("method_name", "get_home");
        //  jsObj.addProperty("cat_id", method.pref.getString("School_id", null));
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
if(is_detached!=true) {
    Log.d("Response", new String(responseBody));
    String res = new String(responseBody);

    try {
      //  method.alertBox(res);

        System.out.println(res);
        JSONObject jsonObject = new JSONObject(res);

        JSONObject jsonObjectBook = jsonObject.getJSONObject(Constant_Api.tag);

                               /* JSONArray jsonArray = jsonObjectBook.getJSONArray("featured_books");
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("id");
                                    String cat_id = object.getString("cat_id");
                                    String book_title = object.getString("book_title");
                                    String book_description = object.getString("book_description");
                                    String book_cover_img = object.getString("book_cover_img");
                                    String book_bg_img = object.getString("book_bg_img");
                                    String book_file_type = object.getString("book_file_type");
                                    String total_rate = object.getString("total_rate");
                                    String rate_avg = object.getString("rate_avg");
                                    String book_views = object.getString("book_views");
                                    String author_id = object.getString("author_id");
                                    String author_name = object.getString("author_name");

                                    sliderList.add(new SubCategoryList(id, cat_id, book_title, book_description, book_cover_img, book_bg_img, book_file_type, total_rate, rate_avg, book_views, author_id, author_name, ""));

                                }
*/
        JSONArray jsonArrayLatest = jsonObjectBook.getJSONArray("latest_books");
        for (int i = 0; i < jsonArrayLatest.length(); i++) {

            JSONObject object = jsonArrayLatest.getJSONObject(i);
            String id = object.getString("id");
            String cat_id = object.getString("cat_id");
            String book_title = object.getString("book_title");
            String book_description = object.getString("book_description");
            String book_cover_img = object.getString("book_cover_img");
            String book_bg_img = object.getString("book_bg_img");
            String book_file_type = object.getString("book_file_type");
            String total_rate = object.getString("total_rate");
            String rate_avg = object.getString("rate_avg");
            String book_views = object.getString("book_views");
            String author_id = object.getString("author_id");
            String author_name = object.getString("author_name");

            latestList.add(new SubCategoryList(id, cat_id, book_title, book_description, book_cover_img, book_bg_img, book_file_type, total_rate, rate_avg, book_views, author_id, author_name, ""));

        }

        JSONArray jsonArrayPopular = jsonObjectBook.getJSONArray("popular_books");
        for (int i = 0; i < jsonArrayPopular.length(); i++) {

            JSONObject object = jsonArrayPopular.getJSONObject(i);
            String id = object.getString("id");
            String cat_id = object.getString("cat_id");
            String book_title = object.getString("book_title");
            String book_description = object.getString("book_description");
            String book_cover_img = object.getString("book_cover_img");
            String book_bg_img = object.getString("book_bg_img");
            String book_file_type = object.getString("book_file_type");
            String total_rate = object.getString("total_rate");
            String rate_avg = object.getString("rate_avg");
            String book_views = object.getString("book_views");
            String author_id = object.getString("author_id");
            String author_name = object.getString("author_name");

            mostPopularList.add(new SubCategoryList(id, cat_id, book_title, book_description, book_cover_img, book_bg_img, book_file_type, total_rate, rate_avg, book_views, author_id, author_name, ""));

        }

        JSONArray jsonArrayCat = jsonObjectBook.getJSONArray("category_list");
        for (int i = 0; i < jsonArrayCat.length(); i++) {

            JSONObject object = jsonArrayCat.getJSONObject(i);
            String cid = object.getString("cid");
            String category_name = object.getString("category_name");
            String total_books = object.getString("total_books");
            String cat_image = object.getString("category_image");

            categoryLists.add(new CategoryList(cid, category_name, total_books, cat_image));

        }

/*
                                JSONArray jsonArraySch = jsonObjectBook.getJSONArray("category_list");
                                for (int i = 0; i < jsonArraySch.length(); i++) {

                                    JSONObject object = jsonArraySch.getJSONObject(i);
                                    String cid = object.getString("cid");
                                    String category_name = object.getString("category_name");
                                    String total_books = object.getString("total_books");
                                    String cat_image = object.getString("category_image");

                                    schoolLists.add(new SchoolList(cid, category_name, total_books, cat_image));

                                }
*/
        JSONArray jsonArrayAuthor = jsonObjectBook.getJSONArray("author_list");
        for (int i = 0; i < jsonArrayAuthor.length(); i++) {

            JSONObject object = jsonArrayAuthor.getJSONObject(i);
            String author_id = object.getString("author_id");
            String author_name = object.getString("author_name");
            String author_image = object.getString("author_image");

            authorLists.add(new AuthorList(author_id, author_name, author_image));

        }

        if (continueList.size() != 0) {
            linearLayout_continue.setVisibility(View.VISIBLE);
            continueAdapter = new BookHomeAdapterGV(getActivity(), continueList, "home_continue", interstitialAdView);
            recyclerViewContinue.setAdapter(continueAdapter);
        } else {
            linearLayout_continue.setVisibility(View.GONE);
        }

        latestAdapter2 = new BookAdapterGV(getActivity(), latestList, "home_latest", interstitialAdView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerViewLatest.setLayoutManager(layoutManager);
        recyclerViewLatest.setAdapter(latestAdapter2);

        popularAdapter = new BookAdapterGV(getActivity(), mostPopularList, "home_most", interstitialAdView);
        RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(getActivity(), 3);
        recyclerViewPopular.setLayoutManager(layoutManager2);
        recyclerViewPopular.setAdapter(popularAdapter);

        categoryAdapter = new CategoryHomeAdapter(getActivity(), categoryLists, "home_cat", interstitialAdView);
        recyclerViewCategory.setAdapter(categoryAdapter);

        //schoolAdapter = new SchoolHomeAdapter(getActivity(), schoolLists, "home_cat", interstitialAdView);
        //recyclerViewSchool.setAdapter(schoolAdapter);

      // authorAdapter = new HomeAuthorAdapter(getActivity(), authorLists, "home_author", interstitialAdView);
      // recyclerViewAuthor.setAdapter(authorAdapter);
/*
                                for (int i = 0; i < sliderList.size(); i++) {
                                    TextSliderView textSliderView = new TextSliderView(getActivity());
                                    // initialize a SliderLayout
                                    textSliderView
                                            .name(sliderList.get(i).getBook_title())
                                            .sub_name(sliderList.get(i).getAuthor_name())
                                            .rating(sliderList.get(i).getRate_avg())
                                            .ratingView(sliderList.get(i).getTotal_rate())
                                            .image(sliderList.get(i).getBook_bg_img())
                                            .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                                @Override
                                                public void onSliderClick(BaseSliderView slider) {
                                                    method.addContinue(db, mDemoSlider.getCurrentPosition(), sliderList);
                                                    startActivity(new Intent(getActivity(), BookDetail.class)
                                                            .putExtra("bookId", sliderList.get(mDemoSlider.getCurrentPosition()).getId())
                                                            .putExtra("position", mDemoSlider.getCurrentPosition())
                                                            .putExtra("type", "slider"));
                                                }
                                            })
                                            .setScaleType(BaseSliderView.ScaleType.Fit);
                                  //  mDemoSlider.addSlider(textSliderView);
                                }
*/
      //  if (getResources().getString(R.string.isRTL).equals("true")) {
            //  mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Left_Bottom);
      //  } else {
            //  mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
      //  }
        //  mDemoSlider.getPagerIndicator().setDefaultIndicatorColor(getResources().getColor(R.color.selectedColor)
        //          , getResources().getColor(R.color.unselectedColor));
        // mDemoSlider.setCustomAnimation(new DescriptionAnimation());

        progressBar.setVisibility(View.GONE);
        if(is_refreshed==true){
            load.clearAnimation();
            is_refreshed = false;
        }

    } catch (JSONException e) {
        e.printStackTrace();
        progressBar.setVisibility(View.GONE);
        method.alertBox(getResources().getString(R.string.contact_msg));

        if(is_refreshed==true){
            load.clearAnimation();
            is_refreshed = false;
        }
    }
}
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    try {

                        progressBar.setVisibility(View.GONE);
                        if(is_refreshed==true){
                            load.clearAnimation();
                            is_refreshed = false;
                        }
                    } catch (Exception f) {
                        if(is_refreshed==true){
                            load.clearAnimation();
                            is_refreshed = false;
                        }
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

    public void onDetached() {
        super.onDetach();
        is_detached=true;
        // Unregister the registered event.

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        String search = s;
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
              //  method.alertBox(getResources().getString(R.string.wrong));
            }

        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
