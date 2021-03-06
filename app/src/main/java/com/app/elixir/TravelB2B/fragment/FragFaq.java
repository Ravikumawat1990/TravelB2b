package com.app.elixir.TravelB2B.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.interfaceimpl.ActionBarTitleSetter;
import com.app.elixir.TravelB2B.interfaceimpl.OnFragmentInteractionListener;
import com.app.elixir.TravelB2B.utils.URLS;


/**
 * Created by NetSupport on 05-06-2017.
 */

public class FragFaq extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Activity thisActivity;
    private WebView webView;
    private ImageView progressBar;


    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnFragmentInteractionListener) context;
            ((ActionBarTitleSetter) context).setTitle(getString(R.string.blocked_users));
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.faq, container, false);
        thisActivity = getActivity();
        ((ActionBarTitleSetter) thisActivity).setTitle("FAQs");
        progressBar = (ImageView) rootView.findViewById(R.id.dialogProgressBar);
        Animation a = AnimationUtils.loadAnimation(thisActivity, R.anim.scale);
        a.setDuration(1000);
        progressBar.startAnimation(a);
        a.setInterpolator(new Interpolator() {
            private final int frameCount = 8;

            @Override
            public float getInterpolation(float input) {
                return (float) Math.floor(input * frameCount) / frameCount;
            }
        });

        setHasOptionsMenu(true);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        thisActivity.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        webView = (WebView) rootView.findViewById(R.id.webView);
        webView.setWebViewClient(new myWebClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadUrl(URLS.FAQ);
        webView.setInitialScale(200);


    }


    public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            progressBar.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.showDrawerToggle(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.myresponsedetail, menu);
        menu.findItem(R.id.filter).setVisible(false);
        menu.findItem(R.id.noti).setVisible(false);
        menu.findItem(R.id.sort).setVisible(false);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.cartMenu);
        item.setVisible(false);
    }


}
