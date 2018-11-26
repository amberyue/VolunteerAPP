package com.example.amber.volunteerapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.amber.volunteerapp.MymsgActivity;
import com.example.amber.volunteerapp.R;
import com.example.amber.volunteerapp.RecordActivity;
import com.example.amber.volunteerapp.SetActivity;

import bean.GlobalVariable;

public class MymsgFragment extends Fragment {

    private Button set;
    private ImageButton mymsg;
    private ImageButton record;
    private TextView UserId;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mymsg, container, false);

        UserId=(TextView)view.findViewById(R.id.textView);
        UserId.setText(GlobalVariable.r1.getData().getUserID());

        set=(Button)view.findViewById(R.id.set);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SetActivity.class);
                startActivity(intent);
            }
        });

        mymsg=(ImageButton)view.findViewById(R.id.imageButton);
        mymsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MymsgActivity.class);
                intent.putExtra("Mymsgflag",1);
                startActivity(intent);
            }
        });

        record=(ImageButton)view.findViewById(R.id.imageButton1);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RecordActivity.class);
                startActivity(intent);
            }
        });
        return view;
      }

    }



