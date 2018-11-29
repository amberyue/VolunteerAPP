package com.example.amber.volunteerapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;

import bean.AED;
import bean.Result4List;

public class RescuePage extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener,LocationSource,AMapLocationListener {

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescue_page);
        if (aMap==null)
        {
            aMap=mapView.getMap();
        }
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setLocationSource(this);
        aMap.setMyLocationEnabled(true);

    }

    private void initlistener(){

        btn_mapchange.setOnCheckedChangeListener(this);
    }



    public void Location() {
        // TODO Auto-generated method stub
        try {
            locationClient = new AMapLocationClient(this);
            clientOption = new AMapLocationClientOption();

            // 设置定位模式为低功耗模式
            clientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
            // 设置定位监听
            locationClient.setLocationListener(this);
            clientOption.setOnceLocation(true);//设置为单次定位
            locationClient.setLocationOption(clientOption);// 设置定位参数
            // 启动定位
            locationClient.startLocation();
//            mHandler.sendEmptyMessage(LocationUtils.MSG_LOCATION_START);
        } catch (Exception e) {
            // Toast.makeText(MainActivity.this, "定位失败", Toast.LENGTH_LONG).show();
        }
    }
    /**
     * 激活定位
     */
    @Override
    public void activate(LocationSource.OnLocationChangedListener listener) {
        mListener=listener;
        if(locationClient==null){

            locationClient=new AMapLocationClient(this);
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
