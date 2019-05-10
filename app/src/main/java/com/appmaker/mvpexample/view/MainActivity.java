package com.appmaker.mvpexample.view;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appmaker.mvpexample.R;
import com.appmaker.mvpexample.contractor.MainActivityContract;
import com.appmaker.mvpexample.model.MainViewDataModel;
import com.appmaker.mvpexample.presenter.MainActivityPresenter;

import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    private MainActivityContract.Presenter mPresenter;

    private ProgressDialog mProgressDialog;
    private MainViewAdapter mainViewAdapter;
    private RecyclerView mRecyclerView;
    private Button btClick;
    private TextView tvMessage, tvActionBarTitle;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new MainActivityPresenter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nav_bar_button) {
            Toast.makeText(this, "Cancel action clicked", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initView() {
        tvMessage = findViewById(R.id.tv_message);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loading please wait...");
        mProgressDialog.setCancelable(false);

        mRecyclerView = findViewById(R.id.rv);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        btClick = findViewById(R.id.bt_fetch);
        btClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mPresenter.onClick(v);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            //actionBar.setTitle("ACTION_BAR");
            tvActionBarTitle = new TextView(this);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            tvActionBarTitle.setLayoutParams(layoutParams);
            tvActionBarTitle.setText("ACTION_BAR");
            tvActionBarTitle.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.AXIS_X_SHIFT);
            tvActionBarTitle.setTextSize(20);
            tvActionBarTitle.setTextColor(getResources().getColor(R.color.white));
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(tvActionBarTitle);
        }
    }

    @Override
    public void showProgressDialog() {

        try {
            if (mProgressDialog != null) {
                tvMessage.setVisibility(View.GONE);
                mProgressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hideProgressDialog() {

        try {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setViewData(List<MainViewDataModel> model) {

        try {
            mainViewAdapter = new MainViewAdapter(this, model);
            mRecyclerView.setVisibility(View.VISIBLE);
            btClick.setVisibility(View.GONE);
            mRecyclerView.setAdapter(mainViewAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
