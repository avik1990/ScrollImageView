package com.app.imageanim;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ClickEventLisener {

    HorizontalScrollView v_scroll;
    Button btn_left, btn_right;
    int pos = 40;
    int temppos = 125;
    int initialpos = 0;
    RecyclerView rv_recycler;
    UserApapter adapter;
    List<Users> user = new ArrayList<>();
    Context mContext;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

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

        v_scroll.setOnScrollChangeListener(new View.OnScrollChangeListener() {

            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                Log.d("Positioni", "" + i);
                Log.d("Positioni1", "" + i1);
                Log.d("Positioni2", "" + i2);
                Log.d("Positioni3", "" + i3);
                initialpos = i;
                if (i == 0) {
                    temppos = 200;
                    pos = 40;
                    btn_left.setEnabled(false);
                } else {
                    btn_left.setEnabled(true);
                }
            }
        });


        rv_recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    // Scrolling up
                    Log.d("Scroll", "ScrollUp");

                    if (initialpos != 0) {
                        temppos = temppos - pos--;
                        v_scroll.scrollTo(temppos, 0);
                        Log.d("temppos", "" + temppos);
                    }
                } else {
                    temppos = temppos + pos++;
                    v_scroll.scrollTo(temppos, -temppos);
                    Log.d("temppos", "" + temppos);
                    // Scrolling down
                    Log.d("Scroll", "ScrollDown");
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


        filllist();


    }


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
        for (int i = 0; i < 1000; i++) {
            user.add(new Users(String.valueOf(i + 1), "name " + i));
        }

        inflateadapter();
    }

    private void inflateadapter() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());


        adapter = new UserApapter(this, R.layout.row_users, user, this);
        rv_recycler.setLayoutManager(mLayoutManager);
        /*rv_recycler.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewMargin decoration = new RecyclerViewMargin(15, user.size());
        rv_recycler.addItemDecoration(decoration);*/
        rv_recycler.setAdapter(adapter);
        // mLayoutManager.getChildCount();

    }


    @Override
    public void Currentposition(int position) {
        Log.d("Currentposition", "" + position);
    }

    @Override
    public void ClickEventLisener(View view, int position) {
        Toast.makeText(mContext, "" + position, Toast.LENGTH_SHORT).show();
    }
}
