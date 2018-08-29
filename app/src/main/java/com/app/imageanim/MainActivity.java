package com.app.imageanim;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ClickEventLisener {

    HorizontalScrollView v_scroll;
    Button btn_left, btn_right;
    int pos = 0;
    int temppos = 0;
    int initialpos = 0;
    RecyclerView rv_recycler;
    UserApapter adapter;
    List<Users> user = new ArrayList<>();
    Context mContext;
    boolean isFirsttime = true;
    ImageView iv_image;
    String imageurl = "https://www.isometrix.com/wp-content/uploads/2017/03/featured-images-about.jpg";
    int screenwidth = 0, imagewidth = 0;
    int scroolby_val = 0;

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        iv_image = findViewById(R.id.iv_image);
        v_scroll = findViewById(R.id.v_scroll);
        btn_left = findViewById(R.id.btn_left);
        btn_right = findViewById(R.id.btn_right);
        rv_recycler = findViewById(R.id.rv_recycler);
        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);

        v_scroll.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        filllist();
        getScreenSize();
        setupimage();
        getleftscroll();
        // when scrolled to the leftmost position the variables are initialized
        v_scroll.setOnScrollChangeListener(new View.OnScrollChangeListener() {

            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                initialpos = i;
                if (i == 0) {
                    temppos = 0;
                } else {
                }
            }
        });

        ///not able to understand how to handle the values
        //handling the scroll
        rv_recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                getleftscroll();
                if (dy > 0) {
                    temppos = temppos + scroolby_val;
                    v_scroll.scrollTo(temppos, 0);
                    Log.d("Scroll", "ScrollUp");
                    Log.d("temppos", "" + temppos);
                } else {
                    if (initialpos != 0) {
                        temppos = temppos - scroolby_val;
                        v_scroll.scrollTo(temppos, 0);
                        Log.d("temppos1", "" + temppos);
                        Log.d("Scroll", "ScrollDown");
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    // Do something
                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    // Do something
                } else {
                    // Do something
                }
            }
        });


    }

    private void getScreenSize() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        Log.d("ScreenWidth", "" + width);
        screenwidth = width;
    }

    private void setupimage() {
        Glide.with(mContext)
                .asBitmap()
                .load(imageurl)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                        int w = bitmap.getWidth();
                        int h = bitmap.getHeight();
                        Log.d("ImageWitdh", "" + w);
                        iv_image.setImageBitmap(bitmap);
                        imagewidth = w;
                    }
                });


    }

    private void getleftscroll() {
        imagewidth = 2000;
        if (imagewidth > screenwidth) {
            int leftoutscreen = imagewidth - screenwidth;
            scroolby_val = leftoutscreen / user.size();
            if (scroolby_val == 0) {
                scroolby_val = 1;
            }
            // temppos = 0;
            Log.d("scroolby_val", "" + scroolby_val);
        }

    }


    ///button is just for demo purpose///
    @Override
    public void onClick(View v) {
        if (v == btn_left) {
            if (initialpos != 0) {
                temppos = temppos - pos--;
                v_scroll.scrollTo(temppos, 0);
                Log.d("temppos", "" + temppos);
            }
        } else if (v == btn_right) {
            temppos = temppos + pos++;
            v_scroll.scrollTo(temppos, -temppos);
            Log.d("temppos", "" + temppos);
        }
    }


    private void filllist() {
        user.clear();
        for (int i = 0; i <= 300; i++) {
            user.add(new Users(String.valueOf(i + 1), "name " + i));
        }
        inflateadapter();
    }

    private void inflateadapter() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = new UserApapter(this, R.layout.row_users, user, this);
        rv_recycler.setLayoutManager(mLayoutManager);
        rv_recycler.setAdapter(adapter);
    }


    @Override
    public void Currentposition(int position) {
        Log.d("Currentposition", "" + position);
        if (position == user.size() - 1) {
            if (scroolby_val == 1) {
                Log.d("scroolby_val", "" + scroolby_val);
                Log.d("Inside", "Inside");
                // v_scroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                // temppos = 1;
            }
        } else if (position == 0) {
            temppos = 0;
        }
        pos = position;
    }

    @Override
    public void ClickEventLisener(View view, int position) {
        Toast.makeText(mContext, "" + position, Toast.LENGTH_SHORT).show();
    }
}
