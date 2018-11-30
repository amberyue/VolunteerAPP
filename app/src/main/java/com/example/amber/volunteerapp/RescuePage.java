
package com.example.amber.volunteerapp;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

import static com.amap.api.location.AMapLocationClientOption.AMapLocationMode.Hight_Accuracy;

    /**
     * Created by Joe.
     */

    public class RescuePage extends AppCompatActivity {

        //声明AMapLocationClient类对象
        public AMapLocationClient mLocationClient = null;
        //声明mLocationOption对象
        public AMapLocationClientOption mLocationOption = null;
        private double lat;
        private double lon;
        private MapView mapView;
        private AMap aMap;//地图控制器对象
        private UiSettings mUiSettings;

        private ImageButton end;


        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_rescue_page);
            //初始化定位
            mLocationClient = new AMapLocationClient(getApplicationContext());
            //设置定位回调监听
            mLocationClient.setLocationListener(mLocationListener);//设置其为定位完成后的回调函数
            mapView = (MapView) findViewById(R.id.id_gaode_location_map);
            mapView.onCreate(savedInstanceState);
            init();
            end=(ImageButton)this.findViewById(R.id.imageButton3);
            end.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2018/11/30 更改状态方法。。 
                }
            });

        }

        /**
         * * 初始化AMap类对象 aMap 地图控制器
         */
        private void init() {
            if (aMap == null) {
                aMap = mapView.getMap();//地图控制器对象
                mUiSettings = aMap.getUiSettings();
            }
            //设置logo位置
            mUiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_CENTER);//高德地图标志的摆放位置
            mUiSettings.setZoomControlsEnabled(true);//地图缩放控件是否可见
            mUiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_BUTTOM);//地图缩放控件的摆放位置
            //aMap  为地图控制器对象
            aMap.getUiSettings().setMyLocationButtonEnabled(true);//地图的定位标志是否可见
            aMap.setMyLocationEnabled(true);//地图定位标志是否可以点击

            setUpMap();
        }


        /**
         * 配置定位参数
         */
        private void setUpMap() {

            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();

            //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
            mLocationOption.setLocationMode(Hight_Accuracy);

            //设置是否返回地址信息（默认返回地址信息）
            mLocationOption.setNeedAddress(true);

            //设置是否只定位一次,默认为false
            mLocationOption.setOnceLocation(false);


            //设置是否允许模拟位置,默认为false，不允许模拟位置
            mLocationOption.setMockEnable(false);

            //设置定位间隔,单位毫秒,默认为2000ms
            mLocationOption.setInterval(2000);

            //给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);

            //启动定位
            mLocationClient.startLocation();
        }

        public AMapLocationListener mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {

                        Log.v("getLocationType", ""+amapLocation.getLocationType() ) ;
                        lat = amapLocation.getLatitude();
                        lon = amapLocation.getLongitude();

                        Log.v("getAccuracy", ""+amapLocation.getAccuracy()+" 米");//获取精度信息
                        Log.v("joe", "lat :-- " + lat + " lon :--" + lon);
                        Log.v("joe", "Country : " + amapLocation.getCountry() + " province : " + amapLocation.getProvince() + " City : " + amapLocation.getCity() + " District : " + amapLocation.getDistrict());
                        //清空缓存位置
                        aMap.clear();


                        // 设置显示的焦点，即当前地图显示为当前位置
                        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 18));
                        //aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
                        //aMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lon)));
                        // TODO: 2018/11/30 加个急救者的maker，透传位置的经纬度 
//                        private void addMarkersToMap（）{
//                            ArrayList<MarkerOptions> markerOptionlst = new ArrayList <MarkerOptions>（）;
//                            for（double [] coord：coords）{
//                                MarkerOptions markerOption = new MarkerOptions（）;
//                                latLng = new LatLng（coord [1]，coord [0]）;
//                                markerOption.position（的latLng）;
//                                markerOption.anchor（0.5F，0.5F）;
//                                markerOption.title（ “标记点”）;
//                                markerOption.snippet（“默认点”）;
//                                markerOption.icon（BitmapDescriptorFactory.fromResource（R.drawable.point））;
//                                markerOptionlst.add（markerOption）;
//                                LatLonPoint point = new LatLonPoint（latLng.latitude，latLng。
//                                的getAddress（点）;
//                            }
//                            aMap.addMarkers（markerOptionlst，true）

                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(new LatLng(lat, lon));
                        markerOptions.title("我的位置");
                        markerOptions.visible(true);
                        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.end_button));
                        markerOptions.icon(bitmapDescriptor);
                        markerOptions.draggable(true);
                        Marker marker = aMap.addMarker(markerOptions);
                        marker.showInfoWindow();
                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        Log.e("joe", "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                    }
                }
            }
        };


        /**
         * 重新绘制加载地图
         */
        @Override
        protected void onResume() {
            super.onResume();
            mapView.onResume();
        }

        /**
         * 暂停地图的绘制
         */
        @Override
        protected void onPause() {
            super.onPause();
            mapView.onPause();
        }


        /**
         * 保存地图当前的状态方法必须重写
         */
        @Override
        protected void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            mapView.onSaveInstanceState(outState);
        }

        /**
         * 销毁地图
         */
        @Override
        protected void onDestroy() {
            super.onDestroy();
            mapView.onDestroy();
            if (mLocationClient != null) {
                mLocationClient.stopLocation();
                mLocationClient.onDestroy();
            }
            mLocationClient = null;
        }
    }