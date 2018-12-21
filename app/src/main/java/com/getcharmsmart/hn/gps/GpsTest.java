package com.getcharmsmart.hn.gps;

/**
 * Created by root on 18-12-20.
 */

import java.util.Iterator;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.GpsStatus.NmeaListener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class GpsTest extends Activity implements OnClickListener {
    LocationManager mLocationManager;
    Location mlocation;
    TextView mTextView;
    TextView mTextView2;
    TextView mTextView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    /*    setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.textView1);
        mTextView2 = (TextView) findViewById(R.id.textView2);
        mTextView3 = (TextView) findViewById(R.id.textView3);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        getLocation();*/
    }

    public void getLocation() {
        if (mLocationManager == null)
            mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
            mLocationManager.addNmeaListener(mNmeaListener);
            mLocationManager.addGpsStatusListener(mStatusChanged);
        }
    }

    //	卫星状态变化
    Listener mStatusChanged = new Listener() {
        @Override
        public void onGpsStatusChanged(int arg0) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            GpsStatus statues = mLocationManager.getGpsStatus(null);
            Iterator<GpsSatellite> iterator = statues.getSatellites().iterator();
            GpsSatellite temSatellite = null;
            //这里列表长度就是有多少颗卫星
            while (iterator.hasNext()) {
                temSatellite = iterator.next();
                if (temSatellite.getSnr() <= 1E-2) {
                    continue;
                }
                //Prn 就是卫星号
                //Snr 信噪比
                // Azimuth 方位角
                //Elevation 高度角
                String strResult = "getSnr:" + temSatellite.getSnr() + "\r\n"
                        + "getElevation:" + temSatellite.getElevation() + "\r\n"
                        + "getPrn:" + temSatellite.getPrn() + "\r\n"
                        + "getAzimuth:" + temSatellite.getAzimuth() + "\r\n"
                        + "usedInFix:" + temSatellite.usedInFix() + "\r\n";
                Log.i("Show", strResult);
                if (mTextView3 != null) {
                    mTextView3.setText(strResult);
                }
            }
        }
    };

    //打开GPS设置
    public void OpenGpsSettingEvent() {
        Intent callGPSSettingIntent = new Intent(
                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(callGPSSettingIntent);
    }

    LocationListener mLocationListener = new LocationListener() {
        @TargetApi(17)
        @Override
        public void onLocationChanged(Location mlocal) {
            if(mlocal == null) return;
            String strResult = "getAccuracy:" + mlocal.getAccuracy() + "\r\n"
                    + "getAltitude:" + mlocal.getAltitude() + "\r\n"
                    + "getBearing:" + mlocal.getBearing() + "\r\n"
                    + "getElapsedRealtimeNanos:" + String.valueOf(mlocal.getElapsedRealtimeNanos()) + "\r\n"
                    + "getLatitude:" + mlocal.getLatitude() + "\r\n"
                    + "getLongitude:" + mlocal.getLongitude() + "\r\n"
                    + "getProvider:" + mlocal.getProvider()+ "\r\n"
                    + "getSpeed:" + mlocal.getSpeed() + "\r\n"
                    + "getTime:" + mlocal.getTime() + "\r\n";
            Log.i("Show", strResult);
            if (mTextView != null) {
                mTextView.setText(strResult);
            }
        }

        @Override
        public void onProviderDisabled(String arg0) {
        }

        @Override
        public void onProviderEnabled(String arg0) {
        }

        @Override
        public void onStatusChanged(String provider, int event, Bundle extras) {

        }
    };


    //$GPGGA,072024.0,2307.582054,N,11321.928800,E,1,07,0.7,45.2,M,-4.0,M,,*76
    //原始数据监听
    NmeaListener mNmeaListener = new NmeaListener() {
        @Override
        public void onNmeaReceived(long arg0, String arg1) {
        /*    GpsAnalysis.getInstance().processNmeaData(arg1);
            if(arg1.contains("GPGGA")){
                Log.i("Show",arg1);
                String[] result = arg1.split(",");
                if(result != null && result.length >= 11){
                    mTextView2.setText(GetnSolutionState(Integer.parseInt(result[6])));
                }
            }
*/
        }
    };

    private String GetnSolutionState(int nType) {
        String strSolutionState = "";
        switch (nType) {
            case 0:
                strSolutionState = "无效解";
                break;
            case 1:
                strSolutionState = "单点解";
                break;
            case 2:
                strSolutionState = "差分解";
                break;
            case 4:
                strSolutionState = "固定解";
                break;
            case 5:
                strSolutionState = "浮点解";
                break;
            default:
                strSolutionState = "" + nType;
                break;
        }
        return strSolutionState;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mLocationManager.removeNmeaListener(mNmeaListener);
            mLocationManager.removeUpdates(mLocationListener);
            mLocationManager.removeGpsStatusListener(mStatusChanged);
        }
    }

    @Override
    protected void onResume() {
        getLocation();
        super.onResume();
    }


    @Override
    public void onClick(View arg0) {
      /*  switch(arg0.getId()){
            case R.id.button1:
                OpenGpsSettingEvent() ;
                break;
            case R.id.button2:
                startActivity(new Intent(this, SatellitesViewActivity.class));
                break;
        }*/
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
      /*  if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle(getString(R.string.tip_text));
            builder.setMessage(getString(R.string.exit_demo));
            builder.setPositiveButton(getResources().getString(R.string.btn_sure), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    System.exit(0);
                }
            });
            builder.setNegativeButton(getResources().getString(R.string.btn_cancer), null);
            builder.show();
        }
        */
        return super.onKeyDown(keyCode, event);
    }
}
