

package  com.example.amber.volunteerapp;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnInfoWindowClickListener;
import com.amap.api.maps.AMap.OnMapClickListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.RouteSearch.OnRouteSearchListener;
import com.amap.api.services.route.RouteSearch.WalkRouteQuery;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;

import java.util.ArrayList;

import utils.AMapUtil;
import utils.ToastUtil;
import utils.WalkRouteOverlay;

import static com.amap.api.location.AMapLocationClientOption.AMapLocationMode.Hight_Accuracy;


public class RescuePage extends Activity implements OnMapClickListener,
        OnMarkerClickListener, OnInfoWindowClickListener, InfoWindowAdapter, OnRouteSearchListener, AMap.OnMapLoadedListener {

    private AMap aMap;
    private MapView mapView;
    private Context mContext;
    private RouteSearch mRouteSearch;
    private WalkRouteResult mWalkRouteResult;
//    private LatLonPoint mStartPoint = new LatLonPoint(39.996678,116.479271);//起点，39.996678,116.479271
//    private LatLonPoint mEndPoint = new LatLonPoint(39.997796,116.468939);//终点，39.997796,116.468939

    private final int ROUTE_TYPE_WALK = 3;

    private RelativeLayout mBottomLayout, mHeadLayout;
    private TextView mRotueTimeDes, mRouteDetailDes;
    private ProgressDialog progDialog = null;// 搜索时进度条
    private WalkRouteOverlay walkRouteOverlay;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    private double lat;
    private double lon;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    private LatLonPoint mStartPoint = null;
    private LatLonPoint mEndPoint = null;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_rescue_page);

        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);//设置其为定位完成后的回调函数
        mapView = (MapView) findViewById(R.id.route_map);
        mapView.onCreate(bundle);

//
//        mContext = this.getApplicationContext();
//        mapView = (MapView) findViewById(R.id.route_map);
//        mapView.onCreate(bundle);// 此方法必须重写
        init();
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
        mLocationOption.setInterval(200000000);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);

        //启动定位
        mLocationClient.startLocation();
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        registerListener();
        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);
        mBottomLayout = (RelativeLayout) findViewById(R.id.bottom_layout);
        mHeadLayout = (RelativeLayout) findViewById(R.id.routemap_header);
        mHeadLayout.setVisibility(View.GONE);
        mRotueTimeDes = (TextView) findViewById(R.id.firstline);
        mRouteDetailDes = (TextView) findViewById(R.id.secondline);

        setUpMap();
    }


    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    mLocationListener.onLocationChanged(amapLocation);// 显示系统小蓝点

                    Log.v("getLocationType", ""+amapLocation.getLocationType() ) ;
                    lat = amapLocation.getLatitude();
                    lon = amapLocation.getLongitude();

                    mStartPoint=new LatLonPoint(lat,lon);
                    Log.v("getAccuracy", ""+amapLocation.getAccuracy()+" 米");//获取精度信息
                    Log.v("joe", "lat :-- " + lat + " lon :--" + lon);
                    Log.v("joe", "Country : " + amapLocation.getCountry() + " province : " + amapLocation.getProvince() + " City : " + amapLocation.getCity() + " District : " + amapLocation.getDistrict());
                    //清空缓存位置
                    aMap.clear();


                    // 设置显示的焦点，即当前地图显示为当前位置
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon),13));
                    //aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
                    //aMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lon)));
                    // TODO: 2018/11/30 加个急救者的maker，透传位置的经纬度

                    ArrayList<MarkerOptions> markerOptionlst = new ArrayList <MarkerOptions>();

                    MarkerOptions rescueMarker = new MarkerOptions();
                    LatLng latLng = new LatLng(23.41571207,116.63296223);
                    mEndPoint=new LatLonPoint(23.41571207,116.63296223);
                    rescueMarker.position(latLng);
                    rescueMarker.anchor(0.5f,0.5f);
                    rescueMarker.title("呼救者位置");
//                        rescueMarker.snippet("default point");
                    rescueMarker.icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker));
                    markerOptionlst.add(rescueMarker);
//                        aMap.addMarkers(rescueMarker);
                    LatLonPoint rescuePoint  = new LatLonPoint(latLng.latitude,latLng.longitude);
//                            getAddress(point);

//


                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(lat, lon));
                    markerOptions.title("我的位置");
                    markerOptions.visible(true);
//                        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),null));
//                        markerOptions.icon(bitmapDescriptor);
                    markerOptions.draggable(true);
                    markerOptionlst.add(markerOptions);

                    LatLonPoint markerPoint = new LatLonPoint(lat,lon);
//                         = aMap.addMarker(markerOptions);
                    aMap.addMarkers(markerOptionlst,true);
//                        markers.showInfoWindow();

                    // TODO: 2018/12/1 ，mode填入计算路径的模式（驾车步行啥的）
                    //路线规划


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
     * 注册监听
     */
    private void registerListener() {
        aMap.setOnMapClickListener(RescuePage.this);
        aMap.setOnMarkerClickListener(RescuePage.this);
        aMap.setOnInfoWindowClickListener(RescuePage.this);
        aMap.setInfoWindowAdapter(RescuePage.this);
        aMap.setOnMapLoadedListener(this);

    }

    @Override
    public View getInfoContents(Marker arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public View getInfoWindow(Marker arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onInfoWindowClick(Marker arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onMarkerClick(Marker arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onMapClick(LatLng arg0) {
        // TODO Auto-generated method stub

    }


    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int routeType) {
        if (mStartPoint == null) {
//            ToastUtil.show(mContext, "定位中，稍后再试...");
            return;
        }
        if (mEndPoint == null) {
//            ToastUtil.show(mContext, "终点未设置");
        }
        showProgressDialog();
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartPoint, mEndPoint);
        if (routeType == ROUTE_TYPE_WALK) {// 步行路径规划
            WalkRouteQuery query = new WalkRouteQuery(fromAndTo);
            mRouteSearch.calculateWalkRouteAsyn(query);// 异步路径规划步行模式查询
        }
    }

    @Override
    public void onBusRouteSearched(BusRouteResult result, int errorCode) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int errorCode) {

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int errorCode) {
        dissmissProgressDialog();
        aMap.clear();// 清理地图上的所有覆盖物
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mWalkRouteResult = result;
                    final WalkPath walkPath = mWalkRouteResult.getPaths()
                            .get(0);
                    if (walkRouteOverlay != null){
                        walkRouteOverlay.removeFromMap();
                    }
                    walkRouteOverlay = new WalkRouteOverlay(
                            this, aMap, walkPath,
                            mWalkRouteResult.getStartPos(),
                            mWalkRouteResult.getTargetPos());
                    walkRouteOverlay.addToMap();
                    walkRouteOverlay.zoomToSpan();
                    mBottomLayout.setVisibility(View.VISIBLE);
                    int dis = (int) walkPath.getDistance();
                    int dur = (int) walkPath.getDuration();
                    String des = AMapUtil.getFriendlyTime(dur)+"("+AMapUtil.getFriendlyLength(dis)+")";
                    mRotueTimeDes.setText(des);
                    mRouteDetailDes.setVisibility(View.GONE);
                    mBottomLayout.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext,
                                    WalkRouteDetailActivity.class);
                            intent.putExtra("walk_path", walkPath);
                            intent.putExtra("walk_result",
                                    mWalkRouteResult);
                            startActivity(intent);
                        }
                    });
                } else if (result != null && result.getPaths() == null) {
                    ToastUtil.show(mContext, R.string.no_result);
                }
            } else {
                ToastUtil.show(mContext, R.string.no_result);
            }
        } else {
            ToastUtil.showerror(this.getApplicationContext(), errorCode);
        }
    }


    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(true);
        progDialog.setMessage("正在搜索");
        progDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onRideRouteSearched(RideRouteResult arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onMapLoaded() {
        searchRouteResult(ROUTE_TYPE_WALK);
    }
}

