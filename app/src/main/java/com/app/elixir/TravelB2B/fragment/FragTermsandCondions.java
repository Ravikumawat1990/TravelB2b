package com.app.elixir.TravelB2B.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.interfaceimpl.ActionBarTitleSetter;
import com.app.elixir.TravelB2B.interfaceimpl.OnFragmentInteractionListener;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.URLS;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by NetSupport on 05-06-2017.
 */

public class FragTermsandCondions extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Activity thisActivity;
    private MtplTextView webView;
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
        View rootView = inflater.inflate(R.layout.fragtermescondition, container, false);
        thisActivity = getActivity();
        ((ActionBarTitleSetter) thisActivity).setTitle("Terms & Conditions");
/*
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
*/

        setHasOptionsMenu(true);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        thisActivity.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        webView = (MtplTextView) rootView.findViewById(R.id.webView);
        webView.setMovementMethod(new ScrollingMovementMethod());
        if (CM.isInternetAvailable(thisActivity)) {
            sendRequest();
        } else {
            CM.showToast(getString(R.string.msg_internet_unavailable_msg), thisActivity);
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
        menu.findItem(R.id.noti).setVisible(false);
        menu.findItem(R.id.filter).setVisible(false);

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

    private void sendRequest() {

        StringRequest stringRequest = new StringRequest(URLS.TANDC,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {

                        Log.i("", "onResponse: " + response);


                        thisActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response.toString());

                                    webView.setText(CM.fromHtml(jsonObject.getString("description").toString()));
                                    webView.setMovementMethod(ScrollingMovementMethod.getInstance());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(thisActivity, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(thisActivity);
        requestQueue.add(stringRequest);
    }


    public void showPopup(Context context) {
        new AlertDialog.Builder(context)
                .setTitle(getString(R.string.app_name))
                .setMessage("Are you sure you want to unblock this user?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setIcon(R.drawable.logo3).show();
    }
}
