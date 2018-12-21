package com.getcharmsmart.hn.postion;

/**
 * Created by root on 18-12-19.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.IOException;

/**
  * Created by hackcoder on 15-1-25.
  */
public class NetWorkUtils {

        /**
         * 检查网络是否可用
         *
         * @param context
         * @return
         */
        public static boolean isNetworkAvailable(Context context) {

            ConnectivityManager manager = (ConnectivityManager) context
                    .getApplicationContext().getSystemService(
                            Context.CONNECTIVITY_SERVICE);

            if (manager == null) {
                return false;
            }

            NetworkInfo networkinfo = manager.getActiveNetworkInfo();

            if (networkinfo == null || !networkinfo.isAvailable()) {
                return false;
            }

            return true;
        }


        /**
          * 判断是否有网络连接
          * @param context
          * @return
          */
     public static boolean isNetworkConnected(Context context) {
         if (context != null) {
         ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
         return mNetworkInfo.isAvailable();
          }

         }
     return false;
    }

        /**
          * 判断WIFI网络是否可用
          * @param context
          * @return
          */
     public static boolean isWifiConnected(Context context) {
        if (context != null) {
         ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWiFiNetworkInfo != null) {
        return mWiFiNetworkInfo.isAvailable();
         }
        }
        return false;
        }
     /**
          * 判断MOBILE网络是否可用
          * @param context
          * @return
          */
    public static boolean isMobileConnected(Context context) {
         if (context != null) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mMobileNetworkInfo != null) {
         return mMobileNetworkInfo.isAvailable();
        }
        }
         return false;
        }
    /**
          * 获取当前网络连接的类型信息
          * @param context
          * @return
          */
    public static int getConnectedType(Context context) {
         if (context != null) {
         ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
         if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
        return mNetworkInfo.getType();
         }
         }
         return -1;
        }
     /**
          * 获取当前的网络状态 ：没有网络0：WIFI网络1：3G网络2：2G网络3
          *
          * @param context
          * @return
          */
     public static int getAPNType(Context context) {
        int netType = 0;
         ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
        return netType;
    }

         int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_WIFI) {
        netType = 1;// wifi
         } else if (nType == ConnectivityManager.TYPE_MOBILE) {
        int nSubType = networkInfo.getSubtype();
        TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
         if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS && !mTelephony.isNetworkRoaming()) {netType = 2;// 3G
              } else {netType = 3;// 2G
             }
        }
              return netType;
     }



    public static boolean netCanUse(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                if (!mNetworkInfo.isAvailable()) {
                    return false;
                }
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    NetworkCapabilities networkCapabilities = mConnectivityManager.getNetworkCapabilities(mConnectivityManager.getActiveNetwork());
                    return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
                } else {
                    Runtime runtime = Runtime.getRuntime();
                    try {
                        Process ipProcess = runtime.exec("ping -c 3 www.baidu.com");
                        int exitValue = ipProcess.waitFor();
                        Log.i("Avalible", "Process:" + exitValue);
                        return (exitValue == 0);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }

}
