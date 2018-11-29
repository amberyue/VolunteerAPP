package com.example.amber.volunteerapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ToggleButton;

import java.io.IOException;
import java.sql.Timestamp;

import bean.GlobalVariable;
import bean.Request;
import bean.VolunteerMsg;
import utils.HttpUtils;

public class SetActivity extends AppCompatActivity {

    private ToggleButton onlineButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        onlineButton = (ToggleButton) this.findViewById(R.id.toggleButton1);

        onlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Request<VolunteerMsg> RequestBean=new Request<VolunteerMsg>();
                VolunteerMsg vm=new VolunteerMsg();
                Timestamp time1 = new Timestamp(System.currentTimeMillis());
                if (onlineButton.isChecked()) {
                    String UserID=GlobalVariable.r1.getData().getUserID();

                    vm.setUserID(UserID);
                    vm.setCid(GlobalVariable.cid);
                    RequestBean.setData(vm);
                    RequestBean.setFunction_id("007");
                    RequestBean.setTimestamp(time1);
                    RequestBean.setSession_id(GlobalVariable.r1.getData().getSession_id());

                } else {

                    vm.setSearch("0");
                    RequestBean.setData(vm);
                    RequestBean.setFunction_id("013");
                    RequestBean.setTimestamp(time1);
                    RequestBean.setSession_id(GlobalVariable.r1.getData().getSession_id());
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            HttpUtils.MypostJson("http://172.20.10.7:8080/RescueSystem/rescue",RequestBean);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }

}
