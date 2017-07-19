package com.app.elixir.TravelB2B.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.adapter.ChatListAdapter;
import com.app.elixir.TravelB2B.model.ChatMessage;
import com.app.elixir.TravelB2B.model.Status;
import com.app.elixir.TravelB2B.model.UserType;
import com.app.elixir.TravelB2B.mtplview.MtplLog;
import com.app.elixir.TravelB2B.utils.CM;
import com.app.elixir.TravelB2B.utils.CV;
import com.app.elixir.TravelB2B.volly.OnVolleyHandler;
import com.app.elixir.TravelB2B.volly.VolleyIntialization;
import com.app.elixir.TravelB2B.volly.WebService;
import com.app.elixir.TravelB2B.volly.WebServiceTag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ViewChat extends AppCompatActivity {

    private static final String TAG = "ViewChat";
    Toolbar toolbar;
    private ArrayList<ChatMessage> chatMessages;
    private ListView chatListView;
    private EditText chatEditText1;
    private ImageView enterChatView1;
    private ChatListAdapter listAdapter;
    String requestId = "";
    String chatUserId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_view_chat);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Chat");
        setSupportActionBar(toolbar);


        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.backicnwht);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CM.finishActivity(ViewChat.this);

            }
        });

        Intent intent = getIntent();
        requestId = intent.getStringExtra("refId");
        chatUserId = intent.getStringExtra("chatUserId");


        webgetChatHistory(requestId, chatUserId, CM.getSp(ViewChat.this, CV.PrefID, "").toString());

        initView();

    }

    private void initView() {

        chatMessages = new ArrayList<>();

        chatListView = (ListView) findViewById(R.id.listView1);
        chatEditText1 = (EditText) findViewById(R.id.chat_edit_text1);
        enterChatView1 = (ImageView) findViewById(R.id.enter_chat1);
        listAdapter = new ChatListAdapter(chatMessages, ViewChat.this);

        chatListView.setAdapter(listAdapter);

        chatEditText1.setOnKeyListener(keyListener);

        enterChatView1.setOnClickListener(clickListener);

    }

    private EditText.OnKeyListener keyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {

            // If the event is a key-down event on the "enter" button
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                // Perform action on key press

                EditText editText = (EditText) v;

                if (v == chatEditText1) {
                    sendMessage(editText.getText().toString(), UserType.OTHER);
                }

                chatEditText1.setText("");

                return true;
            }
            return false;

        }
    };

    private ImageView.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (v == enterChatView1) {
                sendMessage(chatEditText1.getText().toString(), UserType.OTHER);
            }

            chatEditText1.setText("");

        }
    };


    private void sendMessage(final String messageText, final UserType userType) {
        if (messageText.trim().length() == 0)
            return;

        final ChatMessage message = new ChatMessage();
        message.setMessageStatus(Status.SENT);
        message.setMessageText(messageText);
        message.setUserType(userType);
        message.setMessageTime(new Date().getTime());
        chatMessages.add(message);

        if (listAdapter != null)
            listAdapter.notifyDataSetChanged();


        webgetSendChatHistory(CM.getSp(ViewChat.this, CV.PrefID, "").toString(), chatUserId, requestId, messageText);

        // Mark message as delivered after one second

      /*  final ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);

        exec.schedule(new Runnable() {
            @Override
            public void run() {
                message.setMessageStatus(Status.DELIVERED);

                final ChatMessage message = new ChatMessage();
                message.setMessageStatus(Status.SENT);
                message.setMessageText(messageText);
                message.setUserType(UserType.SELF);
                message.setMessageTime(new Date().getTime());
                chatMessages.add(message);

                ViewChat.this.runOnUiThread(new Runnable() {
                    public void run() {
                        listAdapter.notifyDataSetChanged();
                    }
                });


            }
        }, 1, TimeUnit.SECONDS);*/

    }


    @Override
    public void onBackPressed() {
        CM.finishActivity(ViewChat.this);
        super.onBackPressed();
    }

    public void webgetSendChatHistory(String userid, String chatUserId, String reqId, String msg) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewChat.this, true, true);
            WebService.getSendChat(v, userid, chatUserId, reqId, msg, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getsendChatHistory(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewChat.this)) {
                        CM.showPopupCommonValidation(ViewChat.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void webgetChatHistory(String reqId, String chatId, String userid) {
        try {
            VolleyIntialization v = new VolleyIntialization(ViewChat.this, true, true);
            WebService.getChatHistory(v, reqId, chatId, userid, new OnVolleyHandler() {
                @Override
                public void onVollySuccess(String response) {
                    if (isFinishing()) {
                        return;
                    }
                    MtplLog.i("WebCalls", response);
                    Log.e(TAG, response);
                    getChatHistory(response);

                }

                @Override
                public void onVollyError(String error) {
                    MtplLog.i("WebCalls", error);
                    if (CM.isInternetAvailable(ViewChat.this)) {
                        CM.showPopupCommonValidation(ViewChat.this, error, false);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void getChatHistory(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewChat.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    JSONArray jsonArray = new JSONArray(jsonObject.optString("response_object").toString());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        final ChatMessage message = new ChatMessage();
                        message.setMessageStatus(Status.SENT);
                        message.setMessageText(jsonArray.getJSONObject(i).optString("message"));
                        message.setUserType(UserType.SELF);
                        if (jsonArray.getJSONObject(i).optString("read_date_time") != null && !jsonArray.getJSONObject(i).optString("read_date_time").equals("null")) {
                            String txtStartDt = CM.converDateFormate("yyyy-MM-dd'T'HH:mm:ss", "dd-MM-yyyy", jsonArray.getJSONObject(i).optString("read_date_time"));
                            DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                            Date date = format.parse(txtStartDt);
                            long millisecond = date.getTime();
                            message.setMessageTime(millisecond);

                        }
                        chatMessages.add(message);

                    }
                    listAdapter.notifyDataSetChanged();


                    break;
                case "202":
                    break;
                case "501":
                    CM.showToast(jsonObject.optString("msg"), ViewChat.this);


                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewChat.this, e.getMessage(), false);
        }
    }

    private void getsendChatHistory(String response) {
        String strResponseStatus = CM.getValueFromJson(WebServiceTag.WEB_STATUS, response);
        if (strResponseStatus.equalsIgnoreCase(WebServiceTag.WEB_STATUSFAIL)) {
            CM.showPopupCommonValidation(ViewChat.this, CM.getValueFromJson(WebServiceTag.WEB_STATUS_ERRORTEXT, response), false);
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (jsonObject.optString("response_code")) {
                case "200":
                    CM.showToast("Message Send", ViewChat.this);
                    ViewChat.this.finish();
                    CM.showToast(jsonObject.optString("response_object").toString(), ViewChat.this);
                    break;
                case "202":
                    break;
                case "501":
                    CM.showToast(jsonObject.optString("msg"), ViewChat.this);


                    break;
                default:
                    break;


            }
        } catch (Exception e) {
            CM.showPopupCommonValidation(ViewChat.this, e.getMessage(), false);
        }
    }
}
