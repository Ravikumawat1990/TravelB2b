package com.app.elixir.TravelB2B.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.interfaceimpl.ActionBarTitleSetter;
import com.app.elixir.TravelB2B.interfaceimpl.OnFragmentInteractionListener;
import com.app.elixir.TravelB2B.mtplview.MtplButton;
import com.app.elixir.TravelB2B.mtplview.MtplEditText;
import com.app.elixir.TravelB2B.mtplview.MtplTextView;
import com.app.elixir.TravelB2B.pojos.pojoContact_us;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.URLS;
import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

import org.json.JSONArray;

import java.util.ArrayList;


/**
 * Created by NetSupport on 05-06-2017.
 */

public class FragContactUs extends Fragment implements View.OnClickListener {

    private static final String TAG = "FragContactUs";
    private OnFragmentInteractionListener mListener;
    private Activity thisActivity;
    ArrayList<pojoContact_us> pojoContact_us;
    MtplTextView txtContactUs, txtEmail, txtAddress, txtOffice, txtContactUsTitle, txtAddressTitle, txtEmailTitle, txtOfficeTitle;
    Spinner spinner;
    private MtplButton btnSendMail;
    MtplEditText edtComment;

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnFragmentInteractionListener) context;
            ((ActionBarTitleSetter) context).setTitle("Contact Us");
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contactus, container, false);
        thisActivity = getActivity();
        ((ActionBarTitleSetter) thisActivity).setTitle(getString(R.string.contact_us));
        setHasOptionsMenu(true);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        txtContactUs = (MtplTextView) rootView.findViewById(R.id.txtContactUs);
        txtAddress = (MtplTextView) rootView.findViewById(R.id.txtAddress);
        txtEmail = (MtplTextView) rootView.findViewById(R.id.txtEmail);
        txtOffice = (MtplTextView) rootView.findViewById(R.id.txtOfficeHour);
        txtContactUsTitle = (MtplTextView) rootView.findViewById(R.id.txtContactUsTitle);
        txtAddressTitle = (MtplTextView) rootView.findViewById(R.id.txtAddressTitle);
        txtEmailTitle = (MtplTextView) rootView.findViewById(R.id.txtEmailTitle);
        txtOfficeTitle = (MtplTextView) rootView.findViewById(R.id.txtOfficeHourTitle);
        btnSendMail = (MtplButton) rootView.findViewById(R.id.btnsendMessage);
        edtComment = (MtplEditText) rootView.findViewById(R.id.edtComment);

        spinner = (Spinner) rootView.findViewById(R.id.spinner);


        pojoContact_us = new ArrayList<>();

        final String[] cat = getResources().getStringArray(R.array.subArray);
        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(thisActivity, R.layout.support_simple_spinner_dropdown_item, cat) {

            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(thisActivity.getAssets(), getString(R.string.fontface_roboto_regular));
                ((TextView) v).setTypeface(externalFont);
                ((TextView) v).setTextColor(ContextCompat.getColorStateList(thisActivity, R.color.mdtp_light_gray));
                return v;
            }


            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);

                Typeface externalFont = Typeface.createFromAsset(thisActivity.getAssets(), getString(R.string.fontface_roboto_regular));
                ((TextView) v).setTypeface(externalFont);
                ((TextView) v).setTextColor(Color.WHITE);
                v.setBackgroundColor(Color.parseColor("#1295a2"));

                return v;
            }
        };
        langAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(langAdapter);
        btnSendMail.setOnClickListener(this);

        callApi();
    }


    @Override
    public void onResume() {
        super.onResume();
        mListener.showDrawerToggle(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.myresponsedetail, menu);

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

    public void callApi() {


        RequestQueue queue = Volley.newRequestQueue(thisActivity);
        JsonArrayRequest req = new JsonArrayRequest(URLS.CONTACT_US,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONArray jsonElements = new JSONArray(response.toString());
                            for (int i = 0; i < jsonElements.length(); i++) {
                                pojoContact_us pojo = new pojoContact_us();
                                pojo.setValue(jsonElements.getJSONObject(i).getString("value"));
                                pojo.setField(jsonElements.getJSONObject(i).getString("field"));
                                pojo.setId(jsonElements.getJSONObject(i).getString("id"));
                                pojoContact_us.add(pojo);
                            }


                            txtContactUsTitle.setText(pojoContact_us.get(0).getField());
                            txtContactUs.setText(pojoContact_us.get(0).getValue());

                            txtEmailTitle.setText(pojoContact_us.get(1).getField());
                            txtEmail.setText(pojoContact_us.get(1).getValue());

                            txtAddressTitle.setText(pojoContact_us.get(2).getField());
                            txtAddress.setText(CM.fromHtml(pojoContact_us.get(2).getValue()));

                            txtOfficeTitle.setText(pojoContact_us.get(3).getField());
                            txtOffice.setText(CM.fromHtml(pojoContact_us.get(3).getValue()).toString());


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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnsendMessage:

                if (!edtComment.getText().toString().equals("")) {


                    BackgroundMail.newBuilder(thisActivity)
                            .withUsername(getString(R.string.emailId))
                            .withPassword(getString(R.string.password))
                            .withMailto("jnaina@elixirinfo.com")
                            .withType(BackgroundMail.TYPE_PLAIN)
                            .withSubject(spinner.getSelectedItem().toString())
                            .withBody(edtComment.getText().toString())
                            .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                                @Override
                                public void onSuccess() {

                                    CM.showToast("Success", thisActivity);
                                }
                            })
                            .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                                @Override
                                public void onFail() {
                                    CM.showToast("Failed", thisActivity);

                                }
                            })
                            .send();

                } else {

                    CM.showToast(thisActivity.getString(R.string.entcomt), thisActivity);
                }

                break;
        }
    }
}
