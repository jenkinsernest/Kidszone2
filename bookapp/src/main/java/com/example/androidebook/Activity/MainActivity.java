package com.example.androidebook.Activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

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
import com.example.androidebook.Fragment.AuthorFragment;
import com.example.androidebook.Fragment.CategoryFragment;
import com.example.androidebook.Fragment.DownloadFragment;
import com.example.androidebook.Fragment.FavouriteFragment;
import com.example.androidebook.Fragment.HomeFragment;
import com.example.androidebook.Fragment.LatestFragment;
import com.example.androidebook.Fragment.SettingFragment;
import com.example.androidebook.Item.AboutUsList;
import com.example.androidebook.R;
import com.example.androidebook.Util.API;
import com.example.androidebook.Util.Constant_Api;
import com.example.androidebook.Util.Events;
import com.example.androidebook.Util.GlobalBus;
import com.example.androidebook.Util.Method;
import com.google.ads.consent.ConsentForm;
import com.google.ads.consent.ConsentFormListener;
import com.google.ads.consent.ConsentInfoUpdateListener;
import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static Toolbar toolbar;

    final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 101;
    boolean doubleBackToExitPressedOnce = false;
    public static DrawerLayout drawer;
    public static NavigationView navigationView;
    private LinearLayout linearLayout;
    private Method method;
    private ConsentForm form;
    public static boolean download_menu = false;
    public Menu rota;
    public ImageView reload;
    static View vv;
   //public static String is_clicked="false";
   static BottomNavigationView navigation;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {// mTextMessage.setText(R.string.title_home);
                // Toast.makeText(getApplicationContext(),"home", Toast.LENGTH_LONG).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new HomeFragment(), "Home").commit();

                return true;
            } else if (itemId == R.id.download) {// Toast.makeText(getApplicationContext(),"Search", Toast.LENGTH_LONG).show();

                if (Method.allowPermissionExternalStorage) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new DownloadFragment(), "Download").addToBackStack(null)
                            .commit();
                } else {
                    MainActivity.download_menu = true;
                    MainActivity main = new MainActivity();
                    main.checkPer();
                }

                return true;

                // case R.id.profile:
                //    startActivity(new Intent(MainActivity.this, Profile.class));


                //   return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GlobalBus.getBus().register(this);

        method = new Method(MainActivity.this);
        method.forceRTLIfSupported(getWindow());

     //  MainActivity.is_clicked= method.pref.getString("is_clicked","false");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.white));
        }


//Toast.makeText(this, MainActivity.is_clicked,Toast.LENGTH_LONG).show();
      //  toolbar = findViewById(R.id.toolbar_main);
      // toolbar.setTitle(getResources().getString(R.string.app_name));

       // linearLayout = findViewById(R.id.linearLayout_main);
        navigation = findViewById(R.id.navigation);
        navigation.getMenu().getItem(1).setChecked(true);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
       // setSupportActionBar(toolbar);

      /* drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
               this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationIcon(R.drawable.ic_side_nav);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
*/
        if (method.pref.getBoolean(method.pref_login, false)) {
           // navigationView.getMenu().getItem(9).setIcon(R.drawable.ic_logout);
           // navigationView.getMenu().getItem(9).setTitle(getResources().getString(R.string.logout));
        }
        try {
        checkPer();

        if (method.isNetworkAvailable()) {
            String check=method.pref.getString("School_name","none");
  /*if(check.equals("none")){
    getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new SchoolFragment(), "School").commit();

  }else {
    if (method.pref.getBoolean(method.pref_login, false)) {
*/
 getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new HomeFragment()
         , method.pref.getString("School_name", null)).commit();
/*
    } else {
        getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new SchoolFragment(), "School").commit();

    }*/

            aboutUs();

        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new DownloadFragment()
                    , method.pref.getString("School_name", null)).commit();

            navigation.getMenu().getItem(0).setChecked(true);
           // method.alertBox(getResources().getString(R.string.internet_connection));
        }
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.wrong), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
       // DrawerLayout drawer = findViewById(R.id.drawer_layout);
       // if (drawer.isDrawerOpen(GravityCompat.START)) {
       //     drawer.closeDrawer(GravityCompat.START);
       // }
         if(getSupportFragmentManager().getBackStackEntryCount()>0){
           getSupportFragmentManager().popBackStack();

        }

            else  {
              //  super.onBackPressed();
                finish();
            }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

       // getMenuInflater().inflate(R.menu.homepage_menu, menu);
        rota = menu;

        //reload = (ImageView) menu.findItem(R.id.refresh).getActionView();
        // reload.setScaleX(100);
        //  reload.setScaleY(100);
        /*
        reload.setImageResource(R.drawable.refresh);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                vv = view;
                Animation rotation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotation);
                if(method.isNetworkAvailable()) {
                    view.startAnimation(rotation);
                    getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new HomeFragment(), "home").commit();
                    view.clearAnimation();
                }
                else{
                    method.alertBox("No Internet Connection");
                }
            }


        });

*/


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       if (id == R.id.refresh) {
            // swip.setRefreshing(true);


            // swip.setRefreshing(false);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        if (item.isChecked())
            item.setChecked(false);
        else
            item.setChecked(true);

        //Closing drawer on item click
        drawer.closeDrawers();

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new HomeFragment(), "Home").commit();

            return true;
        } else if (id == R.id.latest) {
            LatestFragment latestFragment = new LatestFragment();
            Bundle bundle = new Bundle();
            bundle.putString("type", "latest");
            latestFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main,
                    latestFragment, "latest").addToBackStack(null).commit();
            return true;
        } else if (id == R.id.school) {// getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new SchoolFragment(), "School").commit();
            return true;
        } else if (id == R.id.course) {
            getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new CategoryFragment(), "Course").commit();
            return true;
        } else if (id == R.id.author) {
            getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new AuthorFragment(), "Author").commit();
            return true;
        } else if (id == R.id.download) {
            if (Method.allowPermissionExternalStorage) {
                getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new DownloadFragment(), "Download").commit();
            } else {
                download_menu = true;
                checkPer();
            }
            return true;
        } else if (id == R.id.favorite) {
            getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new FavouriteFragment(), "Favourite").commit();
            return true;
        } else if (id == R.id.setting) {
            getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new SettingFragment(), "Settings").commit();
            return true;
        } else if (id == R.id.profile) {// startActivity(new Intent(MainActivity.this, Profile.class));

            return true;
        } else if (id == R.id.login) {
            if (method.pref.getBoolean(method.pref_login, false)) {
                method.editor.putBoolean(method.pref_login, false);
                method.editor.commit();
            }
            //  startActivity(new Intent(MainActivity.this, Login.class));
            //  finishAffinity();
            return true;
        }
        return true;
    }

    public  void checkPer() {
        if ((ContextCompat.checkSelfPermission(MainActivity.this, "android.permission.WRITE_EXTERNAL_STORAGE" + "android.permission.WRITE_INTERNAL_STORAGE" + "android.permission.READ_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED)) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.WRITE_INTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            } else {
                Method.allowPermissionExternalStorage = true;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        boolean canUseExternalStorage = false;

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    canUseExternalStorage = true;
                    Method.allowPermissionExternalStorage = true;
                    if (download_menu) {
                        download_menu = false;
                        getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new DownloadFragment(), "download").commit();
                    }
                }
                if (!canUseExternalStorage) {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.cannot_use_save_permission), Toast.LENGTH_SHORT).show();
                    Method.allowPermissionExternalStorage = false;
                }
            }
        }
    }

    public void aboutUs() {
        JsonObject jsObj = (JsonObject) new Gson().toJsonTree(new API(MainActivity.this));
        jsObj.addProperty("method_name", "get_app_details");
        jsObj.addProperty("package_name", API.package_name);
        jsObj.addProperty("sign", API.sign);
        jsObj.addProperty("salt", API.salt);


        RequestQueue mRequestQueue;
        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

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

                            JSONArray jsonArray = jsonObject.getJSONArray(Constant_Api.tag);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                String app_name = object.getString("app_name");
                                String app_logo = object.getString("app_logo");
                                String app_version = object.getString("app_version");
                                String app_author = object.getString("app_author");
                                String app_contact = object.getString("app_contact");
                                String app_email = object.getString("app_email");
                                String app_website = object.getString("app_website");
                                String app_description = object.getString("app_description");
                                String app_privacy_policy = object.getString("app_privacy_policy");
                                String publisher_id = object.getString("publisher_id");
                                boolean interstital_ad = Boolean.parseBoolean(object.getString("interstital_ad"));
                                String interstital_ad_id = object.getString("interstital_ad_id");
                                String interstital_ad_click = object.getString("interstital_ad_click");
                                boolean banner_ad = Boolean.parseBoolean(object.getString("banner_ad"));
                                String banner_ad_id = object.getString("banner_ad_id");

                                Constant_Api.aboutUsList = new AboutUsList(app_name, app_logo, app_version, app_author, app_contact, app_email, app_website, app_description, app_privacy_policy, publisher_id, interstital_ad_id, interstital_ad_click, banner_ad_id, interstital_ad, banner_ad);

                            }

                            try {
                                Constant_Api.AD_COUNT_SHOW = Integer.parseInt(Constant_Api.aboutUsList.getInterstital_ad_click());
                            } catch (Exception e) {
                                Log.d("error", e.toString());
                            }



                            checkForConsent();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            method.alertBox(getResources().getString(R.string.contact_msg));
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                try {


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










    public void checkForConsent() {

        ConsentInformation consentInformation = ConsentInformation.getInstance(MainActivity.this);
        String[] publisherIds = {Constant_Api.aboutUsList.getPublisher_id()};
        consentInformation.requestConsentInfoUpdate(publisherIds, new ConsentInfoUpdateListener() {
            @Override
            public void onConsentInfoUpdated(ConsentStatus consentStatus) {
                Log.d("consentStatus", consentStatus.toString());
                // User's consent status successfully updated.
                switch (consentStatus) {
                    case PERSONALIZED:
                        Method.personalization_ad = true;
                        method.showPersonalizedAds(linearLayout);
                        break;
                    case NON_PERSONALIZED:
                        Method.personalization_ad = false;
                        method.showNonPersonalizedAds(linearLayout);
                        break;
                    case UNKNOWN:
                        if (ConsentInformation.getInstance(getBaseContext())
                                .isRequestLocationInEeaOrUnknown()) {
                            requestConsent();
                        } else {
                            Method.personalization_ad = true;
                           // method.showPersonalizedAds(linearLayout);
                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailedToUpdateConsentInfo(String errorDescription) {
                // User's consent status failed to update.
            }
        });

    }

    public void requestConsent() {
        URL privacyUrl = null;
        try {
            // TODO: Replace with your app's privacy policy URL.
            privacyUrl = new URL(getResources().getString(R.string.admob_privacy_link));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            // Handle error.
        }
        form = new ConsentForm.Builder(MainActivity.this, privacyUrl)
                .withListener(new ConsentFormListener() {
                    @Override
                    public void onConsentFormLoaded() {
                        showForm();
                        // Consent form loaded successfully.
                    }

                    @Override
                    public void onConsentFormOpened() {
                        // Consent form was displayed.
                    }

                    @Override
                    public void onConsentFormClosed(ConsentStatus consentStatus, Boolean userPrefersAdFree) {
                        Log.d("consentStatus_form", consentStatus.toString());
                        switch (consentStatus) {
                            case PERSONALIZED:
                                Method.personalization_ad = true;
                                method.showPersonalizedAds(linearLayout);
                                break;
                            case NON_PERSONALIZED:
                                Method.personalization_ad = false;
                                method.showNonPersonalizedAds(linearLayout);
                                break;
                            case UNKNOWN:
                                Method.personalization_ad = false;
                                method.showNonPersonalizedAds(linearLayout);
                        }
                    }

                    @Override
                    public void onConsentFormError(String errorDescription) {
                        Log.d("errorDescription", errorDescription);
                    }
                })
                .withPersonalizedAdsOption()
                .withNonPersonalizedAdsOption()
                .build();
        form.load();
    }

    private void showForm() {
        if (form != null) {
            form.show();
        }
    }

    @Subscribe
    public void getLogin(Events.Login login) {
        if (method != null) {
            if (method.pref.getBoolean(method.pref_login, false)) {
                if (navigationView != null) {
                    navigationView.getMenu().getItem(8).setIcon(R.drawable.ic_logout);
                    navigationView.getMenu().getItem(8).setTitle(getResources().getString(R.string.logout));
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        GlobalBus.getBus().unregister(this);
        super.onDestroy();
    }

}

