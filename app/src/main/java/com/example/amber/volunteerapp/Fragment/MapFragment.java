package com.example.amber.volunteerapp.Fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;



import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.amber.volunteerapp.LoginActivity;
import com.example.amber.volunteerapp.R;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import bean.AED;
import bean.GlobalVariable;
import bean.Location;
import bean.Request;
import bean.Result4List;
import utils.HttpUtils;
import utils.JsonUtils;
import utils.LocationUtils;


public class MapFragment extends Fragment implements CompoundButton.OnCheckedChangeListener,LocationSource,AMapLocationListener {
    private MapView mapView;
    private AMap aMap;
    private ToggleButton btn_mapchange;
    private MyLocationStyle myLocationStyle;
    private OnLocationChangedListener mListener;


    private AMapLocationClient locationClient;
    private AMapLocationClientOption clientOption;
    private Result4List<AED> r2 =new Result4List<AED>();

    private Double longtitude;
    private Double latitude;
    public class nThread extends Thread{
        @Override
        public void run() {
            Looper.prepare();
            new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what){
                        case 1:

                            break;
                    }
                }
            };
            Looper.loop();
        }

    }

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    public MapFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        //声明AMapLocationClientOption对象
//
//        //初始化AMapLocationClientOption对象
//        clientOption = new AMapLocationClientOption();
//
//        //获取一次定位结果：
//        //该方法默认为false。
//        clientOption.setOnceLocation(true);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        //mLocationOption.setOnceLocationLatest(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        initview(savedInstanceState,view);

//        final Handler mHandler =new Handler(){
//
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                switch (msg.what) {
//                    case 1:
//
//                        Intent intent=new Intent(getActivity(),LoginActivity.class);
//                        System.out.println(intent);
//                        startActivity(intent);
//                        break;
//                    case 0:
//                        System.out.println(r2);
//                        List<AED> AEDs= r2.getData();
//                        for(AED a: AEDs) {
//
//                            MarkerOptions markerOption = new MarkerOptions();
//                            MarkerOptions position = markerOption.position(new LatLng(a.getLongitude().doubleValue(), a.getLatitude().doubleValue()));
//                            markerOption.title(a.getAddress()).snippet(a.getAddress());
//
//                            //      markerOption.draggable(true);//设置Marker可拖动
//                            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
//                                    .decodeResource(getResources(),R.drawable.aed_marker)));
//                            // 将Marker设置为贴地显示，可以双指下拉地图查看效果
//                            //      markerOption.setFlat(true);//设置marker平贴地图效果
//                            break;
//                        }
//                }
//            }
//        };
        Thread t=new nThread(){
            @Override
            public void run() {
                String url="http://172.20.10.7:8080/RescueSystem/rescue";
//192.168.48.141    10.0.2.2   192.168.191.1   10.34.24.13   172.20.10.7
//                {"function_id":"006",
//                        "timestamp": "2018-06-25 17:00:00",
//                        "digest":"ASFLKSJFJSDLFJS",
//                        "session_id": "C09BBC55F863450AEDC2582373A6C472",
//                        "data":   { "latitude":"0.00000000","longitude":"0.00000001"}}

                String session_id=GlobalVariable.r1.getData().getSession_id();
                Location l=new Location();
                l.setLatitude(new BigDecimal(latitude));
                l.setLongitude(new BigDecimal(longtitude));
                        Request r=(Request<Location>)JsonUtils.toBean("006",session_id,l);
                String result= null;

                try {
                    result = HttpUtils.MypostJson(url,r);
                    r2=Result4List.fromJson(result, AED.class);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                // TODO: 2018/7/31 session过期 重新登录
//                if (r2.getRetcode()==1) {
//
//                }

                Message msg = Message.obtain();

                msg.what = r2.getRetcode();   //从这里把你想传递的数据放进去就行了
                System.out.println(msg.what);
                mHandler.sendMessage(msg);
            }
        };
        t.start();



        return view;
    }
    final Handler mHandler =new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:

                    Intent intent=new Intent(getActivity(),LoginActivity.class);
                    System.out.println(intent);
                    startActivity(intent);
                    break;
                case 0:
                    System.out.println(r2);
                    List<AED> AEDs= r2.getData();
                    for(AED a: AEDs) {

                        MarkerOptions markerOption = new MarkerOptions();
                        MarkerOptions position = markerOption.position(new LatLng(a.getLongitude().doubleValue(), a.getLatitude().doubleValue()));
                        markerOption.title(a.getAddress()).snippet(a.getAddress());

                        //      markerOption.draggable(true);//设置Marker可拖动
                        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                                .decodeResource(getResources(),R.drawable.aed_marker)));
                        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
                        //      markerOption.setFlat(true);//设置marker平贴地图效果
                        break;
                    }
            }
        }
    };
    private void FindAEDs(){


    }
    private void initview(Bundle savedInstanceState,View view){
        mapView= (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        if (aMap==null)
        {
            aMap=mapView.getMap();
        }
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setLocationSource(this);
        aMap.setMyLocationEnabled(true);

        btn_mapchange=(ToggleButton) view.findViewById(R.id.btn_mapchange);
    }

    private void initlistener(){

        btn_mapchange.setOnCheckedChangeListener(this);
    }



    public void Location() {
        // TODO Auto-generated method stub
        try {
            locationClient = new AMapLocationClient(getActivity());
            clientOption = new AMapLocationClientOption();

            // 设置定位模式为低功耗模式
            clientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
            // 设置定位监听
            locationClient.setLocationListener(this);
            clientOption.setOnceLocation(true);//设置为单次定位
            locationClient.setLocationOption(clientOption);// 设置定位参数
            // 启动定位
            locationClient.startLocation();
            mHandler.sendEmptyMessage(LocationUtils.MSG_LOCATION_START);
        } catch (Exception e) {
           // Toast.makeText(MainActivity.this, "定位失败", Toast.LENGTH_LONG).show();
        }
    }
    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener=listener;
        if(locationClient==null){

            locationClient=new AMapLocationClient(getActivity());
            clientOption=new AMapLocationClientOption();
            locationClient.setLocationListener(this);
            clientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//高精度定位
            clientOption.setOnceLocationLatest(true);//设置单次精确定位
            locationClient.setLocationOption(clientOption);
            locationClient.startLocation();
        }
    }
    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener=null;
        if(locationClient!=null){
            locationClient.stopLocation();
            locationClient.onDestroy();
        }
        locationClient=null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null&&aMapLocation != null) {
            if (aMapLocation != null
                    &&aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点

                latitude=aMapLocation.getLatitude();//获取纬度
                longtitude=aMapLocation.getLongitude();//获取经度

            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode()+ ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
        }
        else {
            aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        }
    }

    /**
     * 必须重写以下方法
     */
    @Override
    public void onResume(){
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if(locationClient!=null){
            locationClient.onDestroy();
        }
    }

}
