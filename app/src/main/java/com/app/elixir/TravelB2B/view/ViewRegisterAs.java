package com.app.elixir.TravelB2B.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
import com.app.elixir.TravelB2B.pojos.pojoMemberShip;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.URLS;
import com.app.elixir.TravelB2B.utils.UlTagHandler;
import com.app.elixir.TravelB2B.volly.VolleySingleton;

import org.json.JSONArray;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.tokenautocomplete.TokenCompleteTextView.TAG;


public class ViewRegisterAs extends AppCompatActivity implements View.OnClickListener {


    Toolbar toolbar;

    ArrayList<pojoMemberShip> memberShipArrayList;
    ImageView progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_register_as);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.register));
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.backicnwht);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CM.finishActivity(ViewRegisterAs.this);

            }
        });
        TextView titleTextView = null;
        try {
            Field f = toolbar.getClass().getDeclaredField("mTitleTextView");
            f.setAccessible(true);
            titleTextView = (TextView) f.get(toolbar);
            Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), getString(R.string.fontface_DroidSerif_Bold));
            titleTextView.setTypeface(font);
            titleTextView.setTextSize(18);
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }

        progressBar = (ImageView) findViewById(R.id.dialogProgressBar);
        progressBar.setVisibility(View.GONE);
       /* Animation a = AnimationUtils.loadAnimation(ViewRegisterAs.this, R.anim.scale);
        a.setDuration(1000);
        progressBar.startAnimation(a);
        a.setInterpolator(new Interpolator() {
            private final int frameCount = 8;

            @Override
            public float getInterpolation(float input) {
                return (float) Math.floor(input * frameCount) / frameCount;
            }
        });*/

        initView();
    }

    private void initView() {
        View view1 = (View) findViewById(R.id.layoutTa);
        View view2 = (View) findViewById(R.id.layoutHotl);
        View view3 = (View) findViewById(R.id.layoutEp);

        memberShipArrayList = new ArrayList<>();
        view1.setOnClickListener(this);
        view2.setOnClickListener(this);
        view3.setOnClickListener(this);

        if (CM.isInternetAvailable(ViewRegisterAs.this)) {
            callApi();
        } else {
            CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewRegisterAs.this);
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CM.finishActivity(ViewRegisterAs.this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.layoutTa:
                if (CM.isInternetAvailable(ViewRegisterAs.this)) {
                    showDiloag("0");
                } else {
                    CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewRegisterAs.this);
                }
                break;
            case R.id.layoutHotl:
                if (CM.isInternetAvailable(ViewRegisterAs.this)) {
                    showDiloag("1");
                } else {
                    CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewRegisterAs.this);
                }
                break;
            case R.id.layoutEp:
                if (CM.isInternetAvailable(ViewRegisterAs.this)) {
                    showDiloag("2");
                } else {
                    CM.showToast(getString(R.string.msg_internet_unavailable_msg), ViewRegisterAs.this);
                }
                break;

        }

    }

    private void showDiloag(String type) {
        LayoutInflater inflater = LayoutInflater.from(ViewRegisterAs.this);
        View dialogLayout = inflater.inflate(R.layout.diloaglayout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewRegisterAs.this);
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
                // TODO Auto-generated method stub

                Intent intent = new Intent(ViewRegisterAs.this, ViewRegister.class);
                intent.putExtra("category", txtHeader.getText().toString());
                CM.startActivity(intent, ViewRegisterAs.this);


                dialog.dismiss();

            }
        });
        AlertDialog customAlertDialog = builder.create();
        customAlertDialog.show();
    }


    public void callApi() {
        progressBar.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(ViewRegisterAs.this);
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
                            Toast.makeText(ViewRegisterAs.this,
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                        progressBar.setVisibility(View.GONE);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(ViewRegisterAs.this,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);

            }
        });

        queue.add(req);


    }


    public void show() {
        StringRequest sr = new StringRequest(Request.Method.POST, URLS.REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d(TAG, "" + error.getMessage() + "," + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("address", "xcxc");
                params.put("country_name", "india");
                params.put("city_id", "1");
                params.put("value", "1234");
                params.put("cpassword", "1234");
                params.put("password", "1234");
                params.put("pincode", "313002");
                params.put("last_name", "testLast");
                params.put("address1", "xcxc");
                params.put("email", "jain@elixinrifo.com");
                params.put("preference", "1,2");
                params.put("state_id", "2");
                params.put("locality", "udaipur");
                params.put("company_name", "test");
                params.put("city_name", "udaipur");
                params.put("role_id", "1");
                params.put("state_name", "RAJ");
                params.put("term_n_cond", "1");
                params.put("country_id", "12");
                params.put("mobile_number", "7728887982");
                params.put("first_name", "testName");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };

        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(sr);
    }


}
