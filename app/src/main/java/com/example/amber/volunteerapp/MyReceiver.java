package com.example.amber.volunteerapp;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import cn.jpush.android.api.JPushInterface;

public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "MyReceiver";

    private NotificationManager nm;
	@Override
	public void onReceive(Context context, Intent intent) {
        Log.i("context", context.toString());
        Log.i("intent", intent.getAction().toString());
//		Bundle bundle = intent.getExtras();
//		String title = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
//		Log.e("id",title);
//		SharedPreferences id = context.getSharedPreferences("id", MODE_PRIVATE);
//		id.edit().putString("id", title).commit();

//        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
//            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
//            Intent i = new Intent(context, RescuePage.class);
//            i.putExtras(bundle);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(i);
//        }
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        Bundle bundle = intent.getExtras();
        Log.i(TAG, "onReceive - " + intent.getAction() + ", extras: ");
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            Log.i(TAG, "JPush用户注册成功");
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.i(TAG, "接受到推送下来的自定义消息");
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {

            Log.i(TAG, "接受到推送下来的通知");
            receivingNotification(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.i(TAG, "用户点击打开了通知");
            Intent i = new Intent(context, com.example.amber.volunteerapp.RescuePage.class);
//            i.putExtras(bundle);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            openNotification(context, bundle);
        } else {
        }


    }

    private void receivingNotification(Context context, Bundle bundle){
//String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
//        Logger.d(TAG, " title : " + title);
//        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
//        Logger.d(TAG, "message : " + message);
//        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//        Logger.d(TAG, "extras : " + extras);
        }

        private void openNotification(Context context, Bundle bundle) {
//            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//            String myValue = "";
//            try {
//            JSONObject extrasJson = new JSONObject(extras);
//
//                myValue = extrasJson.optString("myKey");
//            } catch (Exception e) {
//            Log.i(TAG, "Unexpected: extras is not a valid json", e);
//            return;
        }
//        if (TYPE_THIS.equals(myValue)) {
//            Intent mIntent = new Intent(context, ThisActivity.class);
//            mIntent.putExtras(bundle);
//            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(mIntent);
//        } else if (TYPE_ANOTHER.equals(myValue)) {
//            Intent mIntent = new Intent(context, AnotherActivity.class);
//            mIntent.putExtras(bundle);
//            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(mIntent);
//        }

    }


