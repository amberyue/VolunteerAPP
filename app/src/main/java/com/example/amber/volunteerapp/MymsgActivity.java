package com.example.amber.volunteerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import bean.GlobalVariable;
import bean.Request;
import bean.User;
import bean.UserMsg;
import utils.HttpUtils;
import utils.JsonUtils;

public class MymsgActivity extends AppCompatActivity implements
        Thread.UncaughtExceptionHandler{

    private Button submit;
    private EditText userName;
    private EditText phoneNumber;
    private EditText password;
    private EditText email;
    private EditText address;

    String UserName;
    String PhoneNumber;
    String Password;
    String Email;
    String Address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymsg);

        UserMsg data = GlobalVariable.r1.getData();
        String sessionId = data.getSession_id();
        final String UserID=data.getUserID();

        Thread.setDefaultUncaughtExceptionHandler(this);

        UserName = data.getUsername();
        PhoneNumber = data.getTel();
        Password = data.getPwd();
        Email = data.getEmail();
        Address = data.getAddress();

        userName = (EditText) this.findViewById(R.id.userName);
        phoneNumber = (EditText) this.findViewById(R.id.phoneNumber);
        password = (EditText) this.findViewById(R.id.password);
        email = (EditText) this.findViewById(R.id.email);
        address = (EditText) this.findViewById(R.id.address);

        submit = (Button) this.findViewById(R.id.button3);

        userName.setText(UserName);
        phoneNumber.setText(PhoneNumber);
        password.setText(Password);
        email.setText(Email);
        address.setText(Address);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserName = userName.getText().toString();
                PhoneNumber = phoneNumber.getText().toString();
                Password = password.getText().toString();
                Email = email.getText().toString();
                Address = address.getText().toString();

                User u = new User();
                u.setAddress(Address);
                u.setEmail(Email);
                u.setPwd(Password);
                u.setTel(PhoneNumber);
                u.setUserName(UserName);
                u.setUserID(UserID);

                String sessionId = GlobalVariable.r1.getData().getSession_id();
                Log.i("sessionId",sessionId);
                submitUser(sessionId, u);

                Intent intent = new Intent(MymsgActivity.this, MainActivity.class);
                intent.putExtra("position",2);
                startActivity(intent);

//                Intent intent=new Intent();
//                intent.putExtra("position",1);
                //首先在Activity跳转之前，在Intent中传入一个flag，用来标记跳转到哪一个Fragment。
                // 然后根据flag来判断显示哪个Fragment

//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                // import android.support.v4.app.FragmentTransaction;
//                transaction.replace(
//                transaction.replace(1,new MymsgFragment());
//                transaction.commit();
                //
                // TODO: 2018/11/21 可能跳转有问题跳回fregment ，获得sessionid（存到缓存。。。）

            }
        });

    }
        public void submitUser(final String sessionId, final User u){

//            final Handler mHandler =new Handler(){
//
//                @Override
//                public void handleMessage(Message msg) {
//                    super.handleMessage(msg);
//                    switch (msg.what) {
//                        case 0:
//
//                            Intent intent = new Intent(MymsgActivity.this, MainActivity.class);
//                            intent.putExtra("flag",2);
//                            startActivity(intent);
//                            break;
//                        case 2:
//
//                            break;
//                    }
//                }
//            };

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Request r=(Request<User>)JsonUtils.toBean("010",sessionId,u);
                        String url="http://172.20.10.7:8080/RescueSystem/rescue";
                        //192.168.48.141  192.168.191.1  10.34.24.13   172.20.10.7(iphone手机热点）

//                            Log.i("BBB","Request:"+r);
                            String result=HttpUtils.MypostJson(url,r);

                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
//        new Thread(new Runnable(){
//
//            @Override
//            public void run() {
//                try {
//                    mAuthTask = new UserLoginTask(idNo, password);
//                    String url="http://172.20.10.7:8080/RescueSystem/rescue";
//
////192.168.48.141  192.168.191.1  10.34.24.13   172.20.10.7(iphone手机热点）
//                    requestOne= (RequestOne) mAuthTask.toBean();
//                    Log.i("AAA","request"+requestOne);
//                    String result=mAuthTask.postJson(url,requestOne);
//
//                    JSONObject jsonObject= null;
//                    String retcode="";
//                    try {
//                        jsonObject = new JSONObject(result);
//                        retcode=(String)jsonObject.get("retcode");
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    Gson gson = new GsonBuilder()
//                            .setDateFormat("yyyy-MM-dd HH:mm:ss")
//                            .create();
//                    GlobalVariable.r1= Result.fromJson(result, UserMsg.class);
//
//                    int num=Integer.parseInt(retcode);
//                    Message msg = Message.obtain();
//
//                    msg.what = num;   //从这里把你想传递的数据放进去就行了
//                    System.out.println(msg.what);
//                    mHandler.sendMessage(msg);
//
//                    //     System.out.println(result);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    @Override
    public void uncaughtException(Thread t, Throwable e) {
    //在此处理异常， arg1即为捕获到的异常
        Log.i("CCC", "uncaughtException   " + e);

    }
    }



