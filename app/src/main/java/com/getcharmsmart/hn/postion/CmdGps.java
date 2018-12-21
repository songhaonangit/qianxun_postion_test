package com.getcharmsmart.hn.postion;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by root on 18-12-21.
 */

public class CmdGps {

    /**
     * 打开千寻服务
     * @return
     */
    public static boolean openGps(){

        return canExecuteCommand("/system/bin/setprop "+"sys.qxsdk_on "+"1");


    }

    /**
     * 关闭千寻服务
     * @return
     */
    public static boolean closeGps(){

        return canExecuteCommand("/system/bin/setprop "+"sys.qxsdk_on "+"0");
    }


    /**
     * 查验千寻服务 ps | grep qx
     * @return
     */
    public static boolean checkGpsProcess(){

        return canExecuteCommand("ps | grep qx");
    }


    // executes a command on the system
    public static boolean canExecuteCommand(String command) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);

            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String info = in.readLine();
            Log.d(MainActivity.TAG,"-------info---"+info);
            if (info != null) return true;
            return false;
        } catch (Exception e) {
            //do noting
        } finally {
            if (process != null) process.destroy();
        }
        return false;
    }
}
