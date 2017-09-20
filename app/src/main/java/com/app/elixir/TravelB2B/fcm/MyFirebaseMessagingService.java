package com.app.elixir.TravelB2B.fcm;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.app.elixir.TravelB2B.R;
import com.app.elixir.TravelB2B.interfaceimpl.pushNotificationString;
import com.app.elixir.TravelB2B.view.ViewDrawer;
import com.app.elixir.TravelB2B.view.ViewNotification;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private static final String TAG1 = "logout";
    Context context;
    pushNotificationString notificationString;
    int count = 0;
    public static final String INTENT_FILTER = "INTENT_FILTER";

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        context = getApplicationContext();
        Map data = remoteMessage.getData();
        Set keys = data.keySet();
        Iterator itr = keys.iterator();
        String key;
        String value = "";
        while (itr.hasNext()) {
            key = (String) itr.next();
            value = (String) data.get(key);
            System.out.println(key + " - " + value);
        }
        String count = "";
        try {
            JSONObject jsonObject = new JSONObject(value);
            count = jsonObject.getString("countchat");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        sendNotification(remoteMessage.getNotification().getBody());
        Intent intent = new Intent(INTENT_FILTER);
        intent.putExtra("extra", count);
        sendBroadcast(intent);


    }


    private void sendNotification(String messageBody) {
        int requestID = (int) System.currentTimeMillis();
        Intent intent = new Intent(this, ViewNotification.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.noti);
        notificationBuilder.setLargeIcon(icon);
        notificationBuilder.setSmallIcon(getNotificationIcon(notificationBuilder));
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(requestID, notificationBuilder.build());
    }

    private int getNotificationIcon(NotificationCompat.Builder notificationBuilder) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int color = 0x008000;
            notificationBuilder.setColor(color);
            return R.drawable.noti;

        } else {
            return R.drawable.notic;
        }
    }


}
