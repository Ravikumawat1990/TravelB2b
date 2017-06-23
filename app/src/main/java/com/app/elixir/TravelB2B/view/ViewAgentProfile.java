package com.app.elixir.TravelB2B.view;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.MenuItem;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.adptAdvt;
import com.app.elixir.TravelB2B.adapter.adptreview;
import com.app.elixir.TravelB2B.model.PojoMyResponse;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.CustomTypefaceSpan;

import java.util.ArrayList;

public class ViewAgentProfile extends AppCompatActivity {
    adptreview mAdapter;
    ArrayList<PojoMyResponse> dataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Typeface font = Typeface.createFromAsset(getAssets(), getString(R.string.fontface_DroidSerif_Bold));
        SpannableStringBuilder SS = new SpannableStringBuilder("Agent Profile");
        SS.setSpan(new CustomTypefaceSpan("", font), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        setTitle(SS);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        RecyclerView recyclerView1 = (RecyclerView) findViewById(R.id.recycleView);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleView1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView1.setLayoutManager(layoutManager1);

        dataSet = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            PojoMyResponse pojoMyResponse = new PojoMyResponse();
            pojoMyResponse.setRequestType("1");
            dataSet.add(pojoMyResponse);

        }

        adptAdvt mAdapter1 = new adptAdvt(ViewAgentProfile.this, dataSet);
        recyclerView1.setAdapter(mAdapter1);
        mAdapter = new adptreview(ViewAgentProfile.this, dataSet);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                CM.finishActivity(ViewAgentProfile.this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CM.finishActivity(ViewAgentProfile.this);
    }
}
