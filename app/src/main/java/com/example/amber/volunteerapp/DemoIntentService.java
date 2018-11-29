package com.example.amber.volunteerapp;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;

import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;

import bean.GlobalVariable;

import static com.example.amber.volunteerapp.Manifest.permission.volunteerapp;
import static com.igexin.sdk.GTServiceManager.context;

public class DemoIntentService extends GTIntentService {
    public DemoIntentService() {

    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        String appid = msg.getAppid();
        String taskid = msg.getTaskId();
        String messageid = msg.getMessageId();
        String pkg = msg.getPkgName();

        boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001)
        // 透传消息的处理，详看SDK demo // 透传消息的处理，详看SDK demo
        //获取后台推送通知里面的透传消息
        String payload = new String(msg.getPayload());
        Log.i("透传", "touchuanzhi   " + payload);
        if (payload == null) {
            Log.e(TAG, "receiver payload = null");
        } else {
            String data = new String(payload);
            Log.d(TAG, "receiver payload = " + data);
            try {

                sendMessage(data, 0);//这里对透传消息进行发送 通过App中的方法进行处理
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        if (payload != null) {
            Intent intent = new Intent(context, RescuePage.class);
//          intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            intent.putExtra(KeyConstans.PAYLOAD, payload);
            //***在这里需要向跳转的activity中传递参数
            startActivity(intent);
        }
    }
    private void sendMessage(String data, int what) {
        Message msg = Message.obtain();
        msg.what = what;
        msg.obj = data;
        volunteerapp.getInstance().sendMessage(data);//将消息发送给App类
    }

    @Override
    public void onReceiveClientId(Context context, String clientid) {
        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
        GlobalVariable.cid=clientid;
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage msg) {
    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage msg) {
    }
}
