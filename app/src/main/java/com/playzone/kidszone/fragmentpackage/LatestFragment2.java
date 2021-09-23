package com.playzone.kidszone.fragmentpackage;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.playzone.kidszone.API;
import com.playzone.kidszone.BookDetail;
import com.playzone.kidszone.SingleBookDetail;
import com.playzone.kidszone.Parent;
import com.playzone.kidszone.R;
import com.playzone.kidszone.Swipe_home;
import com.playzone.kidszone.adaptors.BookAdapterGV;
import com.playzone.kidszone.adaptors.BookAdapterLV;
import com.playzone.kidszone.models.SubCategoryList;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import cz.msebera.android.httpclient.Header;
import database.InterstitialAdView;
import database.Method;
import database.OnClick;

public class LatestFragment2 extends Fragment {

    private Method method;
    private String type;
    private ProgressBar progressBar;
    private List<SubCategoryList> subCategoryLists;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ImageView imageView_gridView, imageView_listView;
    private com.playzone.kidszone.adaptors.BookAdapterLV latestAdapterLV;
    private com.playzone.kidszone.adaptors.BookAdapterGV latestAdapterGV;
    private boolean isView = true;
    private TextView textViewCount, textView_noData;
    private LayoutAnimationController animation;
    private InterstitialAdView interstitialAdView;
    public static String Book_cat_name;
    private OnClick onClick;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.latest_book_frag, container, false);

        subCategoryLists = new ArrayList<>();
      ImageView  back=(ImageView)view.findViewById(com.playzone.kidszone.R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction .setCustomAnimations(com.playzone.kidszone.R.anim.enter_from_left, com.playzone.kidszone.R.anim.exit_to_right);
                transaction.replace(com.playzone.kidszone.R.id.dfrag,new new_home_screen());
                // transaction.addToBackStack(null);
                transaction.commit();

                Swipe_home.fab.setVisibility(View.VISIBLE);
                Swipe_home.apps.setVisibility(View.VISIBLE);

            }
        });


/*        assert getArguments() != null;
        type = getArguments().getString("type");
        assert type != null;
        if (type.equals("latest")) {
           // MainActivity.toolbar.setTitle(getTag());
           // SchoolFragment.tx.setText("Latest");
        } else {
           // MainActivity.toolbar.setTitle(getTag());
           // SchoolFragment.tx.setText("Popular Book");
        }
*/
        interstitialAdView = new InterstitialAdView() {
            @Override
            public void position(int position, String type, String id, String title, String fileType, String fileUrl) {
                startActivity(new Intent(getActivity(), SingleBookDetail.class)
                        .putExtra("bookId", id)
                        .putExtra("position", position)
                        .putExtra("type", type));
            }
        };



        onClick = (position, type, id, subId, title, fileType, fileUrl, otherData) -> {
            startActivity(new Intent(getActivity(), BookDetail.class)
                    .putExtra("bookId", id)
                    .putExtra("position", position)
                    .putExtra("type", type));

        };


        method = new Method(getActivity(), interstitialAdView);

        int resId = R.anim.layout_animation_fall_down;
        animation = AnimationUtils.loadLayoutAnimation(getActivity(), resId);

        imageView_gridView = view.findViewById(R.id.imageView_gridView_latest_fragment);
        imageView_listView = view.findViewById(R.id.imageView_listView_latest_fragment);

        progressBar = view.findViewById(R.id.progresbar_latest_fragment);
        textViewCount = view.findViewById(R.id.textView_number_ofItem_latest_fragment);
        textView_noData = view.findViewById(R.id.textView_latest_fragment);
        TextView cat = view.findViewById(R.id.vid_series);
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefresh_latest_fragment);

        recyclerView = view.findViewById(R.id.recyclerView_latest_fragment);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusable(false);


        cat.setText(Book_cat_name);

        imageView_listView.setImageDrawable(getResources().getDrawable(R.drawable.ic_list_hov));

        mSwipeRefreshLayout.setColorSchemeResources(R.color.toolbar);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (method.isNetworkAvailable()) {
                    Latest(isView);
                } else {
                    method.alertBox(getResources().getString(R.string.internet_connection));
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        imageView_gridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isView = false;
                imageView_gridView.setImageDrawable(getResources().getDrawable(R.drawable.ic_grid_hov));
                imageView_listView.setImageDrawable(getResources().getDrawable(R.drawable.ic_list));
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setFocusable(false);
                if (method.isNetworkAvailable()) {
                    Latest(isView);
                } else {
                    method.alertBox(getResources().getString(R.string.internet_connection));
                }
            }
        });

        imageView_listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isView = true;
                imageView_gridView.setImageDrawable(getResources().getDrawable(R.drawable.ic_grid));
                imageView_listView.setImageDrawable(getResources().getDrawable(R.drawable.ic_list_hov));
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setFocusable(false);
                if (method.isNetworkAvailable()) {
                    Latest(isView);
                } else {
                    method.alertBox(getResources().getString(R.string.internet_connection));
                }
            }
        });

        if (method.isNetworkAvailable()) {
           // method.alertBox(Book_cat_name);

            Latest(isView);
            isView = true;
        } else {
            method.alertBox(getResources().getString(R.string.internet_connection));
            progressBar.setVisibility(View.GONE);
        }

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, @NotNull MenuInflater inflater) {


       /* inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.ic_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener((new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startActivity(new Intent(getActivity(), Find.class)
                        .putExtra("id", "0")
                        .putExtra("search", query)
                        .putExtra("type", "normal"));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        }));
*/
    }

    private void Latest(final boolean isTrue) {

        subCategoryLists.clear();

       // progressBar.setVisibility(View.VISIBLE);

        showCustomDialog();

        if (getActivity() != null) {

            AsyncHttpClient client = new AsyncHttpClient(true,80,443);
            RequestParams params = new RequestParams();
            JsonObject jsObj = (JsonObject) new Gson().toJsonTree(new API(LatestFragment2.this));

                jsObj.addProperty("method_name", "get_book");

            jsObj.addProperty("keyword", Book_cat_name);

            jsObj.addProperty("package_name", com.playzone.kidszone.API.package_name);
            jsObj.addProperty("sign", com.playzone.kidszone.API.sign);
            jsObj.addProperty("salt", com.playzone.kidszone.API.salt);

            params.put("data",  com.playzone.kidszone.API.toBase64(jsObj.toString()));
            client.post("https://testzone.ng/kidszone/access/api_video.php", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    Log.d("Response", new String(responseBody));
                    String res = new String(responseBody);
//System.out.println(res);
                    try {

                     //   Toast.makeText(getActivity(), res, Toast.LENGTH_SHORT).show();
                        JSONObject jsonObject = new JSONObject(res);

                        JSONObject jsonObjectBook = jsonObject.getJSONObject("EBOOK_APP");

                        JSONArray jsonArrayPopular = jsonObjectBook.getJSONArray("Books");
                        Parent.booklist.clear();

                        for (int a = 0; a < jsonArrayPopular.length(); a++) {
                            JSONObject object = jsonArrayPopular.getJSONObject(a);


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


Parent.booklist.add(new com.playzone.kidszone.models.SubCategoryList(id, cat_id, book_title, book_description, book_cover_img, book_bg_img, book_file_type, total_rate, rate_avg, book_views, author_id, author_name, ""));

                        }



                        if (Parent.booklist.size() == 0) {
                            textView_noData.setVisibility(View.VISIBLE);
                        } else {
                            textView_noData.setVisibility(View.GONE);
                            if (isTrue) {
                                latestAdapterLV = new BookAdapterLV(getActivity(),onClick, Parent.booklist, type, interstitialAdView);
                                recyclerView.setAdapter(latestAdapterLV);
                            } else {
                                latestAdapterGV = new BookAdapterGV(getActivity(),onClick, Parent.booklist, type, interstitialAdView);
                                recyclerView.setAdapter(latestAdapterGV);
                                recyclerView.setLayoutAnimation(animation);
                            }
                        }

                        String count = Parent.booklist.size() + " " + getResources().getString(R.string.iteam);
                        textViewCount.setText(count);
                        textViewCount.setTypeface(Method.scriptable);

                      //  progressBar.setVisibility(View.GONE);

                        Parent.dialog.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                      //  progressBar.setVisibility(View.GONE);
                        Parent.dialog.dismiss();
                        method.alertBox(getResources().getString(R.string.contact_msg));
                       // method.alertBox(e.getMessage());
                       // method.alertBox(e.getMessage());
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                   // progressBar.setVisibility(View.GONE);
Parent.dialog.dismiss();
                  //  method.alertBox(error.getMessage());
                }
            });

        }
    }





    public void showCustomDialog() {
        Parent.dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent);
        Parent.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Parent.dialog.setCancelable(true);
        Parent.dialog.setContentView(R.layout.layout_dialog2);
        ((ImageView)  Parent.dialog.findViewById(R.id.loader)).startAnimation(AnimationUtils.loadAnimation(getActivity(),
                R.anim.rotate));
        Parent.dialog.getWindow().setBackgroundDrawable( new ColorDrawable(0x7f000000));
        Parent.dialog.show();
    }

}
