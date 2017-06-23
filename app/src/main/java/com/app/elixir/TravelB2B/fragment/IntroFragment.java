package com.app.elixir.TravelB2B.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
import com.app.elixir.TravelB2B.pojos.pojoMemberShip;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.URLS;
import com.app.elixir.TravelB2B.utils.UlTagHandler;
import com.app.elixir.TravelB2B.view.ViewRegister;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;

import static com.tokenautocomplete.TokenCompleteTextView.TAG;


public class IntroFragment extends Fragment implements View.OnClickListener {

    private static final String BACKGROUND_COLOR = "backgroundColor";
    private static final String PAGE = "page";

    private int mBackgroundColor, mPage;
    Activity thisActivity;
    ArrayList<pojoMemberShip> memberShipArrayList;

    public static IntroFragment newInstance(int backgroundColor, int page) {
        IntroFragment frag = new IntroFragment();
        Bundle b = new Bundle();
        b.putInt(BACKGROUND_COLOR, backgroundColor);
        b.putInt(PAGE, page);
        frag.setArguments(b);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        thisActivity = getActivity();

        if (!getArguments().containsKey(BACKGROUND_COLOR))
            throw new RuntimeException("Fragment must contain a \"" + BACKGROUND_COLOR + "\" argument!");
        mBackgroundColor = getArguments().getInt(BACKGROUND_COLOR);

        if (!getArguments().containsKey(PAGE))
            throw new RuntimeException("Fragment must contain a \"" + PAGE + "\" argument!");
        mPage = getArguments().getInt(PAGE);
        memberShipArrayList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Select a layout based on the current page
        int layoutResId;
        switch (mPage) {
            case 0:
                layoutResId = R.layout.intro_fragment_layout_1;
                break;
            case 1:
                layoutResId = R.layout.intro_fragment_layout_2;
                break;
            case 2:
                layoutResId = R.layout.intro_fragment_layout_3;
                break;
            case 3:
                layoutResId = R.layout.intro_fragment_layout_4;
                break;
            case 4:
                layoutResId = R.layout.intro_fragment_layout_5;
                break;
            case 5:
                layoutResId = R.layout.intro_fragment_layout_6;
                break;
            case 6:

                if (CM.isInternetAvailable(thisActivity)) {
                    callApi();
                } else {
                    CM.showToast(getString(R.string.msg_internet_unavailable_msg), thisActivity);
                }
                layoutResId = R.layout.intro_fragment_layout_7;
                break;

            default:
                layoutResId = R.layout.intro_fragment_layout_1;


        }


        final View view = getActivity().getLayoutInflater().inflate(layoutResId, container, false);
        view.setTag(mPage);
        if (mPage == 5) {


            thisActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ImageView imageView1 = (ImageView) view.findViewById(R.id.imgSignIn);
                    ImageView imageView2 = (ImageView) view.findViewById(R.id.imgLogin);
                    ImageView imageView3 = (ImageView) view.findViewById(R.id.imgRequest);
                    ImageView imageView4 = (ImageView) view.findViewById(R.id.imgChatPhone);
                    ImageView imageView5 = (ImageView) view.findViewById(R.id.imgFinalize);
                    ImageView imageView6 = (ImageView) view.findViewById(R.id.imgFinalize1);
                    Picasso.with(thisActivity).load(R.drawable.hw01).into(imageView1);
                    Picasso.with(thisActivity).load(R.drawable.hw02).into(imageView2);
                    Picasso.with(thisActivity).load(R.drawable.hw03).into(imageView3);
                    Picasso.with(thisActivity).load(R.drawable.hw04).into(imageView4);
                    Picasso.with(thisActivity).load(R.drawable.hw05).into(imageView5);
                    Picasso.with(thisActivity).load(R.drawable.hw06).into(imageView6);

                }
            });


        } else if (mPage == 6) {


            thisActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ImageView imageView1 = (ImageView) view.findViewById(R.id.imgTravel);
                    ImageView imageView2 = (ImageView) view.findViewById(R.id.imgHotlier);
                    ImageView imageView3 = (ImageView) view.findViewById(R.id.imgEp);
                    Picasso.with(thisActivity).load(R.drawable.t).into(imageView1);
                    Picasso.with(thisActivity).load(R.drawable.h).into(imageView2);
                    Picasso.with(thisActivity).load(R.drawable.e).into(imageView3);
                }
            });


            View view1 = (View) view.findViewById(R.id.layoutTa);
            View view2 = (View) view.findViewById(R.id.layoutHotl);
            View view3 = (View) view.findViewById(R.id.layoutEp);
            view1.setOnClickListener(this);
            view2.setOnClickListener(this);
            view3.setOnClickListener(this);


        } else if (mPage == 7) {


        }


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.layoutTa:

                showDiloag("0");

                break;
            case R.id.layoutHotl:
                showDiloag("1");
                break;

            case R.id.layoutEp:
                showDiloag("2");

                break;

        }

    }

    private void showDiloag(String type) {
        LayoutInflater inflater = LayoutInflater.from(thisActivity);
        View dialogLayout = inflater.inflate(R.layout.diloaglayout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(thisActivity);
        builder.setView(dialogLayout);


        final MtplTextView txtHeader = (MtplTextView) dialogLayout.findViewById(R.id.txtHeader);
        MtplTextView txtPerYear = (MtplTextView) dialogLayout.findViewById(R.id.txtPerYear);

        MtplTextView txt1 = (MtplTextView) dialogLayout.findViewById(R.id.txt1);
        MtplTextView txt2 = (MtplTextView) dialogLayout.findViewById(R.id.txt2);
        MtplTextView txt3 = (MtplTextView) dialogLayout.findViewById(R.id.txt3);
        MtplTextView txt4 = (MtplTextView) dialogLayout.findViewById(R.id.txt4);
        MtplTextView txt5 = (MtplTextView) dialogLayout.findViewById(R.id.txt5);


        if (type.equals("0")) {
            txtHeader.setText(memberShipArrayList.get(0).getMembership_name());
            txtPerYear.setText(getString(R.string.rsSymbol) + memberShipArrayList.get(0).getPrice() + " per year");
            txt1.setText(Html.fromHtml(memberShipArrayList.get(0).getDescription(), null, new UlTagHandler()));
        } else if (type.equals("1")) {

            txtHeader.setText(memberShipArrayList.get(2).getMembership_name());
            txtPerYear.setText(getString(R.string.rsSymbol) + memberShipArrayList.get(2).getPrice() + " per year");
            txt1.setText(Html.fromHtml(memberShipArrayList.get(2).getDescription(), null, new UlTagHandler()));


        } else {

            txtHeader.setText(memberShipArrayList.get(1).getMembership_name());
            txtPerYear.setText(getString(R.string.rsSymbol) + memberShipArrayList.get(1).getPrice() + " per year");
            txt1.setText(Html.fromHtml(memberShipArrayList.get(1).getDescription(), null, new UlTagHandler()));

        }


        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Get Started", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(thisActivity, ViewRegister.class);
                intent.putExtra("category", txtHeader.getText().toString());
                CM.startActivity(intent, thisActivity);


                dialog.dismiss();
                // TODO Auto-generated method stub

            }
        });
        AlertDialog customAlertDialog = builder.create();
        customAlertDialog.show();
    }


    public void callApi() {

        RequestQueue queue = Volley.newRequestQueue(thisActivity);
        JsonArrayRequest req = new JsonArrayRequest(URLS.MEMBERSHIPS,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONArray jsonElements = new JSONArray(response.toString());
                            for (int i = 0; i < jsonElements.length(); i++) {
                                pojoMemberShip memberShip = new pojoMemberShip();
                                memberShip.setDescription(jsonElements.getJSONObject(i).getString("description"));
                                memberShip.setDuration(jsonElements.getJSONObject(i).getString("duration"));
                                memberShip.setMembership_name(jsonElements.getJSONObject(i).getString("membership_name"));
                                memberShip.setPrice(jsonElements.getJSONObject(i).getString("price"));
                                memberShip.setStatus(jsonElements.getJSONObject(i).getString("status"));
                                memberShipArrayList.add(memberShip);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(thisActivity,
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(thisActivity,
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        queue.add(req);


    }

}