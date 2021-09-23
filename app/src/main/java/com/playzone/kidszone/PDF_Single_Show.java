package com.playzone.kidszone;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.playzone.kidszone.fragmentpackage.new_home_screen;
import com.shockwave.pdfium.PdfDocument;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import database.DatabaseHandler;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class PDF_Single_Show extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener, OnPageErrorListener {

    private database.Method method;
    private PDFView pdfView;
    private DatabaseHandler db;
    private final static int REQUEST_CODE = 42;
    public static final int PERMISSION_CODE = 42042;
    private String id, uri, type;
    private InputStream is;
    public Toolbar toolbar;
    private ProgressDialog progressDialog;

    private static final String TAG = PDF_Single_Show.class.getSimpleName();
    CountDownTimer countDownTimer;
    public Method method2;
    int duration=0;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_pdfview);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }
        method = new database.Method(PDF_Single_Show.this);
        method.forceRTLIfSupported(getWindow());

        method2 = new Method(PDF_Single_Show.this);

        db = new DatabaseHandler(PDF_Single_Show.this);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        uri = intent.getStringExtra("link");
        String toolbarTitle = intent.getStringExtra("title");
        type = intent.getStringExtra("type");

        progressDialog = new ProgressDialog(PDF_Single_Show.this);

        toolbar = findViewById(R.id.toolbar_pdfView);
        toolbar.setTitle(toolbarTitle);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        LinearLayout linearLayout = findViewById(R.id.linearLayout_pdfView);
        if (Method.personalization_ad) {
            method.showPersonalizedAds(linearLayout);
        } else {
            method.showNonPersonalizedAds(linearLayout);
        }


        pdfView = findViewById(R.id.pdfView_activity);
       pdfView.setBackgroundColor(Color.LTGRAY);

        if (type.equals("link")) {
            if (!db.checkIdPdfBook(id)) {
                PdfAsynTask2(uri,db.getPdf(id),  String.valueOf(true));
                //.execute(uri, db.getPdf(id), String.valueOf(true));
            } else {
              PdfAsynTask2(uri, String.valueOf(0), String.valueOf(true));
                db.addPdf(id, String.valueOf(0));
            }



        } else {

            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.setCancelable(true);
            progressDialog.show();


            File file = new File(uri);

            if (!db.checkIdPdfBook(id)) {
                displayFromFile(file, Integer.parseInt(db.getPdf(id)), true);
            } else {
                displayFromFile(file, 0, false);
                db.addPdf(id, String.valueOf(0));
            }
        }

    }

    public void displayFromUri(InputStream inputStream, int pagePosition, boolean isRead) {


        pdfView.fromStream(inputStream)
                .defaultPage(pagePosition)
                .onPageChange(this)
                .swipeHorizontal(true)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(10) // in dp
                .onPageError(this)
                .load();

        if (Parent.book_task_time > 0) {
            timer();
        }

        progressDialog.dismiss();
    }

    public void displayFromFile(File file, int pagePosition, boolean isRead) {
        pdfView.fromFile(file)
                .defaultPage(pagePosition)
                .onPageChange(this)
                .swipeHorizontal(true)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(10) // in dp
                .onPageError(this)
                .load();



        if (Parent.book_task_time > 0) {
            timer();
        }

        progressDialog.cancel();
    }







    public void timer() {

        // System.out.println("------TIMER STARTED SUCCESSFULLY------- " + new_home_screen.video_task_time + " Minutes");
        duration=0;

        int spent=Integer.parseInt(Parent.educational_goals.get(Parent.book_task_index).getSpent());

        String status= Parent.educational_goals.get(Parent.book_task_index).getStatus();


        if(spent == Parent.book_task_time){
            countDownTimer.onFinish();

        }
        else {
            int left = Parent.book_task_time - spent;

            if (left > 0) {
                duration = left;
            } else {
                duration = new_home_screen.book_task_time;
            }

            method2.alertBox("DURATION: " + duration, 3);

        }
        countDownTimer = new CountDownTimer(TimeUnit.MINUTES.toMillis(duration), 1000) {

            public void onTick(long millisUntilFinished) {



                String spent = String.valueOf(Parent.book_task_time - ((millisUntilFinished / 1000)/60)  );

                Parent.educational_goals.get(Parent.book_task_index).setSpent(spent);

                      /*  new StyleableToast
                                .Builder(getActivity())
                                .text("Your Time Is Almost Up")
                                .textColor(Color.WHITE)
                                .backgroundColor(Color.RED)
                                .show();
*/



            }

            public void onFinish() {

                System.out.println("------TIMES UP-------");

                // if(Parent.educational_goals.get(new_home_screen.video_task_index))
                // Parent.educational_goals.get(new_home_screen.video_task_index).setStatus("completed");

                int total =Integer.parseInt( Parent.educational_goals.get(Parent.book_task_index).getAlotted_time());
                int reward_earned = total/2;
                String reward = String.valueOf(reward_earned);
                Parent.educational_goals.get(Parent.book_task_index).setReward_earned(reward);

                Parent.book_task_time=0;

                String rew =  Parent.dbhelper.getReward_byId(Parent.kid_id) ;
                //String rew =   Parent.childModelList.get(new_home_screen.child_index).getReward_earned();
                int total_reward_earned;

                if(rew==null || rew.equals("0")){
                    total_reward_earned=0;
                }
                else {
                    total_reward_earned = Integer.parseInt(rew);

                            //Integer.parseInt(Parent.childModelList.get(new_home_screen.child_index).
                          //  getReward_earned());
                }
                total_reward_earned= total_reward_earned + reward_earned ;

                Parent.childModelList.get(new_home_screen.child_index).setReward_earned(String.valueOf(total_reward_earned));


      Parent.dbhelper.UpdateChildReward_eaarned( Parent.childModelList.get(new_home_screen.child_index).getReward_earned(), Parent.kid_id);



                Parent.dbhelper.editGoal(Parent.educational_goals, Parent.kid_id);

                Parent.dbhelper.editReward(String.valueOf(total_reward_earned),Parent.kid_id );


                method2.alertBox("Congratulations! you have completed your "
                        + Parent.educational_goals.get(Parent.book_task_index).getSpent() + " minutes Reading task for today.", 3);

                if (method.isNetworkAvailable()) {

                    SET_USER_DETAILS set = new SET_USER_DETAILS();


                    //so we can add reward
                    set.updateContentRestriction(Parent.pemail, Parent.kid_id,
                            Parent.childModelList.get(new_home_screen.child_index).getContent_Restriction()
                            ,String.valueOf(total_reward_earned),
                            new Fragment(), PDF_Single_Show.this);


                    //so we can update the child's account education goal
                  //  set.set_educational_goals(Parent.educational_goals,
                     //       new Fragment(), PDF_Single_Show.this);



                        set.update_Educational_goals(Parent.educational_goals, Parent.kid_id,"Books",
                                new Fragment(), PDF_Single_Show.this);

                }


            }


        }.start();
    }













    @Override
    public void onPageChanged(int page, int pageCount) {
        db.updatePdf(id, String.valueOf(page));
    }

    @Override
    public void loadComplete(int nbPages) {
        progressDialog.dismiss();
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        Log.e(TAG, "title = " + meta.getTitle());
        Log.e(TAG, "author = " + meta.getAuthor());
        Log.e(TAG, "subject = " + meta.getSubject());
        Log.e(TAG, "keywords = " + meta.getKeywords());
        Log.e(TAG, "creator = " + meta.getCreator());
        Log.e(TAG, "producer = " + meta.getProducer());
        Log.e(TAG, "creationDate = " + meta.getCreationDate());
        Log.e(TAG, "modDate = " + meta.getModDate());
    }

    @Override
    public void onPageError(int page, Throwable t) {
        progressDialog.dismiss();
        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchPicker();
            }
        }
    }

    void launchPicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            //alert user that file manager not working
            Toast.makeText(this, "error_launchPicker", Toast.LENGTH_SHORT).show();
        }
    }


  public void  PdfAsynTask2 (String url, String page, String read){



          progressDialog.setMessage(getResources().getString(R.string.loading));
          progressDialog.setCancelable(true);
      progressDialog.show();

      new Thread(new Runnable() {
          @Override
          public void run() {


          try {
              boolean isRead;
              String pagePosition;

              URL myURL = new URL(url);
              pagePosition = page;
              isRead = Boolean.parseBoolean(read);
              HttpURLConnection conn = (HttpURLConnection) myURL.openConnection();
              conn.setDoInput(true);
              conn.setConnectTimeout(90000);

              conn.setUseCaches(true);

              conn.connect();
              is = conn.getInputStream();
              Log.d("uri", uri);


              Handler handle = new Handler(Looper.getMainLooper());
              handle.post(new Runnable() {
                  @Override
                  public void run() {
                      //  recyclerView.getRecycledViewPool().clear();

                      displayFromUri(is, Integer.parseInt(pagePosition), isRead);
                      }

                  }
              );




          } catch (Exception e) {
              Log.d("toast", e.toString());

              progressDialog.cancel();
          }

          }
      }).start();

    }

    /*
    @SuppressLint("StaticFieldLeak")
    class PdfAsyncTask extends AsyncTask<String, String, String> {

        boolean isRead;
        String pagePosition;

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.setCancelable(false);
            progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getResources().getString(R.string.cancel_dialog), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                URL myURL = new URL(strings[0]);
                pagePosition = strings[1];
                isRead = Boolean.parseBoolean(strings[2]);
                HttpURLConnection conn = (HttpURLConnection) myURL.openConnection();
                conn.setDoInput(true);
                conn.setConnectTimeout(90000);

                conn.setUseCaches(true);

                conn.connect();
                is = conn.getInputStream();
                Log.d("uri", uri);

            } catch (Exception e) {
                Log.d("toast", e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            displayFromUri(is, Integer.parseInt(pagePosition), isRead);
            super.onPostExecute(s);
        }
    }
*/
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    protected void onDestroy() {

        if(countDownTimer!=null) {
            countDownTimer.cancel();
            Parent.dbhelper.editGoal(Parent.educational_goals, Parent.kid_id);



            if (method.isNetworkAvailable()) {

                SET_USER_DETAILS set = new SET_USER_DETAILS();

                set.update_Educational_goals(Parent.educational_goals, Parent.kid_id, "Books",
                        new Fragment(), PDF_Single_Show.this);
            }

        }

        super.onDestroy();
    }



    @Override
    protected void onStop() {

        if(countDownTimer!=null) {
            countDownTimer.cancel();
            Parent.dbhelper.editGoal(Parent.educational_goals, Parent.kid_id);


            if (method.isNetworkAvailable()) {

                SET_USER_DETAILS set = new SET_USER_DETAILS();

                set.update_Educational_goals(Parent.educational_goals, Parent.kid_id, "Books",
                        new Fragment(), PDF_Single_Show.this);
            }
        }
        super.onStop();
    }
}
