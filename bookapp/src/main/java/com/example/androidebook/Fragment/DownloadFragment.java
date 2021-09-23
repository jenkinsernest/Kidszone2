package com.example.androidebook.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.androidebook.Activity.PDFShow;
import com.example.androidebook.Adapter.DownloadAdapter;
import com.example.androidebook.DataBase.DatabaseHandler;
import com.example.androidebook.InterFace.InterstitialAdView;
import com.example.androidebook.Item.DownloadList;
import com.example.androidebook.R;
import com.example.androidebook.Util.Method;
import com.folioreader.FolioReader;
import com.folioreader.model.HighLight;
import com.folioreader.util.OnHighlightListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

//import com.folioreader.model.locators.ReadLocator;
//import com.folioreader.util.ReadLocatorListener;

public class DownloadFragment extends Fragment {

    private Method method;
    private String root;
    private DatabaseHandler db;
    private TextView textView_noData;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;
    private List<File> inFiles;
    private List<DownloadList> downloadLists;
    private List<DownloadList> downloadListsCompair;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private DownloadAdapter downloadAdapter;
    private InterstitialAdView interstitialAdView;
    public Toolbar toolbar;
    private Button back;
    ImageView pix;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.download_fragment, container, false);
       toolbar = view.findViewById(R.id.toolbar_download);
        toolbar.setTitle("My Downloads");


       // MainActivity.toolbar.setTitle(getTag());
      //  SchoolFragment.tx.setText(getTag());
        db = new DatabaseHandler(getActivity());
        downloadLists = new ArrayList<>();
        progressDialog = new ProgressDialog(getActivity());

        interstitialAdView = new InterstitialAdView() {
            @Override
            public void position(final int position, String type, final String id, String title, String fileType, String fileUrl) {
                if (fileUrl.contains(".epub")) {
                    FolioReader folioReader = FolioReader.get();
                    folioReader.setOnHighlightListener(new OnHighlightListener() {
                        @Override
                        public void onHighlight(HighLight highlight, HighLight.HighLightAction type) {

                        }
                    });
                    if (db.checkIdEpub(id)) {
                        folioReader.openBook(fileUrl);
                    } else {

                        try {
                            String string = db.getEpub(id);
                            JSONObject jsonObject = new JSONObject(string);
                            /*String bookId = jsonObject.getString("bookId");
                            String chapterId = jsonObject.getString("chapterId");
                            String chapterHref = jsonObject.getString("chapterHref");
                            int chapterIndex = Integer.parseInt(jsonObject.getString("chapterIndex"));
                            boolean usingId = Boolean.parseBoolean(jsonObject.getString("usingId"));
                            String value = jsonObject.getString("value");*/

                           // ReadLocator readPosition = ReadLocator.fromJson(string);
                           // folioReader.setReadLocator(readPosition);
                            folioReader.openBook(fileUrl);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                 /*   folioReader.setReadLocatorListener(new ReadLocatorListener() {
                        @Override
                        public void saveReadLocator(ReadLocator readLocator) {

                            Log.d("readPosition", readLocator.toJson());

                            if (db.checkIdEpub(id)) {
                                db.addEpub(id, readLocator.toJson());
                            } else {
                                db.updateEpub(id, readLocator.toJson());
                            }
                        }
                    });*/
                } else {

                    String[] strings = fileUrl.split("filename-");
                    String[] idPdf = strings[1].split(".pdf");

                    startActivity(new Intent(getActivity(), PDFShow.class)
                            .putExtra("id", idPdf[0])
                            .putExtra("link", fileUrl)
                            .putExtra("title", title)
                            .putExtra("type", "file"));
                }
            }
        };
        method = new Method(getActivity(), interstitialAdView);

        inFiles = new ArrayList<>();
        downloadListsCompair = new ArrayList<>();

        File file1 = new File(Objects.requireNonNull(getActivity()).getFilesDir(), "AndroidEBook/");

       // root = Environment.getExternalStorageDirectory() + "/AndroidEBook/";
        root = file1.getAbsolutePath();

        progressBar = view.findViewById(R.id.progressbar_download_fragment);
        textView_noData = view.findViewById(R.id.textView_download_fragment);
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefresh_download_fragment);
        recyclerView = view.findViewById(R.id.recyclerView_download_fragment);
        back = view.findViewById(R.id.clickback);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  requireActivity().getSupportFragmentManager().popBackStack();
 requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framlayout_main, new HomeFragment(), "Home").commit();

            }
        });
        pix=view.findViewById(R.id.pix);
        progressBar.setVisibility(View.GONE);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusable(false);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.toolbar);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                PdfAsynTask();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        //new Execute().execute();
        PdfAsynTask();
        return view;
    }







    public void  PdfAsynTask (){



        progressDialog.show();
        progressDialog.setMessage(getResources().getString(R.string.loading));
        progressDialog.setCancelable(true);

        downloadLists.clear();
        inFiles.clear();

        downloadListsCompair.clear();


        downloadLists = db.getDownload();

        new Thread(new Runnable() {
            @Override
            public void run() {


                try {




                    Handler handle = new Handler(Looper.getMainLooper());
                    handle.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        //  recyclerView.getRecycledViewPool().clear();

                                        File file = new File(root);
                                        getListFiles(file);
                                        getDownloadLists(inFiles);



                                        if (downloadListsCompair.size() == 0) {
                                            textView_noData.setVisibility(View.VISIBLE);
                                            pix.setVisibility(View.VISIBLE);
                                            back.setVisibility(View.VISIBLE);
                                        } else {
                                            textView_noData.setVisibility(View.GONE);
                                            pix.setVisibility(View.GONE);
                                            back.setVisibility(View.GONE);
                                            downloadAdapter = new DownloadAdapter(getActivity(), downloadListsCompair, "download", interstitialAdView);
                                            recyclerView.setAdapter(downloadAdapter);
                                        }

                                        progressDialog.dismiss();
                                    }

                                }
                    );




                } catch (Exception e) {
                    Log.d("toast", e.toString());
                }

            }
        }).start();

    }

/*
    class Execute extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            progressDialog.show();
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.setCancelable(true);

            downloadLists.clear();
            inFiles.clear();

            downloadListsCompair.clear();

            db = new DatabaseHandler(getContext());
            downloadLists = db.getDownload();

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            File file = new File(root);
            getListFiles(file);
            getDownloadLists(inFiles);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            if (downloadListsCompair.size() == 0) {
                textView_noData.setVisibility(View.VISIBLE);
                pix.setVisibility(View.VISIBLE);
               back.setVisibility(View.VISIBLE);
            } else {
                textView_noData.setVisibility(View.GONE);
                pix.setVisibility(View.GONE);
                back.setVisibility(View.GONE);
                downloadAdapter = new DownloadAdapter(getActivity(), downloadListsCompair, "download", interstitialAdView);
                recyclerView.setAdapter(downloadAdapter);
            }

            progressDialog.dismiss();
            super.onPostExecute(s);
        }
    }
*/

    private List<File> getListFiles(File parentDir) {
        try {
            Queue<File> files = new LinkedList<>();
            files.addAll(Arrays.asList(parentDir.listFiles()));
            while (!files.isEmpty()) {
                File file = files.remove();
                if (file.isDirectory()) {
                    files.addAll(Arrays.asList(file.listFiles()));
                } else if (file.getName().endsWith(".epub") || file.getName().endsWith(".pdf")) {
                    inFiles.add(file);
                }
            }
        } catch (Exception e) {
            Log.d("error", e.toString());
        }
        return inFiles;
    }

    private List<DownloadList> getDownloadLists(List<File> list) {

        for (int i = 0; i < downloadLists.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).toString().contains(downloadLists.get(i).getUrl())) {
                    downloadListsCompair.add(downloadLists.get(i));
                    break;
                } else {
                    if (j == list.size() - 1) {
                        db.deleteDownloadBook(downloadLists.get(i).getId());
                    }
                }
            }
        }
        return downloadListsCompair;
    }
}
