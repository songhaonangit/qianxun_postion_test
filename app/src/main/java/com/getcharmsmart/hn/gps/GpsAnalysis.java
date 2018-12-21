package com.getcharmsmart.hn.gps;

/**
 * Created by root on 18-12-20.
 */


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import com.google.common.primitives.Bytes;
import android.annotation.SuppressLint;
import android.util.Log;

/**
 * 标准nmea数据解析
 * @author mmsx
 *
 */
public class GpsAnalysis {
//    List<Byte> gps_data = new ArrayList<Byte>();
//    private List<GpsSatelliteInfo> satelliteList = new ArrayList<GpsSatelliteInfo>();
//    private List<GpsSatelliteInfo> GPSList = new ArrayList<GpsSatelliteInfo>();
//    private List<GpsSatelliteInfo> BDList = new ArrayList<GpsSatelliteInfo>();
//    private List<GpsSatelliteInfo> GLONASSList = new ArrayList<GpsSatelliteInfo>();
//    private boolean bUsedGPS;
//    private boolean bUsedGlonass;
//    private boolean bUsedBDS;
//    private boolean bUsedGalileo;
//
//    public boolean m_bUpdating;
//    int GSVIndex = 0;
//    private double hrms, vrms, rms;
//    private double SigmaEast, SigmaNorth;
//    private long Time;// 当前时间 与(2009,1,1,0,0,0,0)的时间差
//    private double deltTime;// 当前时间 与上一历元的时间差
//    private Date utcDate;
//    private Date gpsDate;
//    private String strGsa = "";// 用于保存GSA里面的卫星数据
//    private int year, month, day;
//    private double pdop;
//    private double hdop;
//    private double vdop;
//    private int age;// 差分龄
//    private int m_tSatNum, m_SatNum;// m_tSatNum：可见卫星数；
//    private double dUndulation;// 大地水准面与WGS84之间的高程异常
//    private int gpsStatue;// GPS状态，0：无效定位（正在搜索卫星...）；1：单点定位；2：差分定位；3：浮动；4：固定；5：超宽巷模糊度的固定；
//    public static long m_TimeStamp = 0;
//    private double fSpeed_M_S;// 速度 ,m/s
//    private double heading;// 方位，角度格式：弧度
//    private volatile static GpsAnalysis gpsAnalysis = null;
//    //经纬度
//    private double latitude = 0;
//    private double longitude = 0;
//    private double altitude = 0;
//
//    public static GpsAnalysis getInstance() {
//        synchronized (GpsAnalysis.class) {
//            if(gpsAnalysis == null)
//                gpsAnalysis =new GpsAnalysis();
//        }
//        return gpsAnalysis;
//    }
//
//    public void processBinaryData(byte[] temp, int len) {
//        // Log.e("二进制数据", "开始");
//        if (gps_data.size() > 4096 - len) {
//            gps_data.clear();
//        }
//        for (int i = 0; i < len; i++) {
//            gps_data.add(temp[i]);
//        }
//
//        for (int i = 0; i < gps_data.size() - 1; i++) {
//            if (gps_data.get(i) == 0x0D && gps_data.get(i + 1) == 0x0A) {
//                String strs = "";
//                byte[] byteArray = Bytes.toArray(gps_data);
//                try {
//                    strs = new String(byteArray, 0, i + 2, "UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//                if (strs.contains("$")) {
//                    processNmeaData(strs);
//                }
//
//                for (int ii = i + 1; ii >= 0; ii--) {
//                    gps_data.remove(ii);
//                }
//                i = 0;
//                continue;
//            }
//        }
//
//        return;
//    }
//
//    private boolean checkNMEAData(String strData) {
//        int nCheckSum, index = -1;
//        String str, str0;
//        if (strData.indexOf("*") == -1) {
//            return false;
//        }
//        if (strData.indexOf("$") == -1)
//            return false;
//        if (strData.indexOf("$") > strData.indexOf("*"))
//            return false;
//        String stem = (strData.substring(0, strData.indexOf("*")));
//        byte[] cData = stem.getBytes();
//        nCheckSum = cData[1];
//        for (int n = 2; n < cData.length; n++)// 值校验
//        {
//            nCheckSum ^= cData[n];
//        }
//        str = String.format(("%02x"), nCheckSum);
//        index = strData.indexOf("*");
//        if (index == -1)
//            return false;
//        if (strData.length() < index + 3)
//            return false;
//        str0 = strData.substring(index + 1, index + 3);
//        if (str.equalsIgnoreCase(str0)) {
//            return true;
//        } else {
//            Log.e("CheckNmea", strData);
//            Log.e("计算校验值", str);
//            return false;
//        }
//    }
//
//    public static int stringNumbers(String str, String specter) {
//        int count = 0;
//        char c = specter.charAt(0);
//        for (int i = 0; i < str.length(); i++) {
//            char tmp = str.charAt(i);
//            if (Character.valueOf(c).equals(Character.valueOf(tmp))) {
//                count += 1;
//            }
//        }
//        return count;
//    }
//
//    @SuppressLint("DefaultLocale")
//    public void processNmeaData(String nmea) {
//        if (nmea.length() == 0)
//            return;
//        if (!checkNMEAData(nmea)) {
//            // 可能是A318的命令返回值
//
//            return;
//        }
//        // Log.e("NMEA完整语句", nmea);
//        if (!nmea.startsWith("$GPPWR,") && !nmea.startsWith("$GNGST,")
//                && !nmea.startsWith("$GPGST,") && !nmea.startsWith("$GLGSV,")
//                && !nmea.startsWith("$GNGSV,") && !nmea.startsWith("$BDGSV,")
//                && !nmea.startsWith("$GPZDA,") && !nmea.startsWith("$GPGSA,")
//                && !nmea.startsWith("$GNVTG,") && !nmea.startsWith("$GPVTG,")
//                && !nmea.startsWith("$GNGSA,") && !nmea.startsWith("$GPNTR,")
//                && !nmea.startsWith("$GNGGA,") && !nmea.startsWith("$GPGGA,")
//                && !nmea.startsWith("$GPRMC,") && !nmea.startsWith("$GPGSV,")
//                && !nmea.startsWith("$BDGSA,"))
//            return;
//        String[] sField = nmea.split(",");
//        int iFieldNum = stringNumbers(nmea, ",");
//        if (sField == null)
//            return;
//        if ((sField[0].equalsIgnoreCase("$GPGGA") || sField[0]
//                .equalsIgnoreCase("$GNGGA")) && iFieldNum >= 14) {
//            if (sField[6].trim().length() > 0) {
//                switch (Integer.valueOf(sField[6])) {
//                    case 0:// 无效定位
//                        gpsStatue = 0;
//                        break;
//                    case 1:// 单点
//                        gpsStatue = 1;
//                        break;
//                    case 2:// 差分
//                        gpsStatue = 2;
//                        break;
//                    case 3:
//                    case 4:// 固定
//                    case 8:
//                        gpsStatue = 4;
//                        break;
//                    case 5:// 浮动
//                        gpsStatue = 3;
//                        break;
//
//                }
//
//                if (sField[2].trim().length() > 3) {
//                    latitude = Double.valueOf(sField[2].substring(0, 2))
//                            + Double.valueOf(sField[2].substring(2)) / 60;
//                }
//                if (sField[3] == "S")
//                    latitude *= -1.0;
//                if (sField[4].trim().length() > 4) {
//                    longitude = Double.valueOf(sField[4].substring(0, 3))
//                            + Double.valueOf(sField[4].substring(3)) / 60;
//                }
//                if (sField[5] == "W") {
//                    longitude *= -1.0;
//                    longitude += 360;
//                }
//                if (sField[7].trim().length() > 0) {
//                    m_SatNum = Integer.valueOf(sField[7]);
//                } else {
//                    m_SatNum = 0;
//                }
//                if (sField[9].trim().length() > 0) {
//                    if (sField[11].trim().length() == 0)
//                        sField[11] = "0";
//                    altitude = Double.valueOf(sField[9]) + Double.valueOf(sField[11]);
//                    dUndulation = Double.valueOf(sField[11]);
//                }
//            }
//            if (sField[13].trim().length() > 0) {
//                age = Integer.valueOf(sField[13]);
//            } else {
//                age = 99;
//            }
//
//            int m_Sec = 1, iSecOff = 0;
//            int m_Hour = 1, m_Min = 1;
//            if (sField[1].trim().length() >= 6) {
//                m_Hour = Integer.valueOf(sField[1].substring(0, 2));
//                m_Min = Integer.valueOf(sField[1].substring(2, 4));
//                m_Sec = Integer.valueOf(sField[1].substring(4, 6));
//            }
//            if (m_Sec > 59) {
//                iSecOff = m_Sec - 59;
//                m_Sec = 59;
//            }
//            if (m_Hour < 0 || m_Hour > 23 || m_Min < 0 || m_Min > 59
//                    || iSecOff > 60) {
//                return;
//            }
//            String sDate = String.format("%04d-%02d-%02d %02d:%02d:%02d", year,
//                    month, day, m_Hour, m_Min, m_Sec);
//            gpsDate = DateUtil.GetLocalDate(DateUtil.parseDate(sDate));
//            Time = DateUtil.GetGPSTimeSpan(gpsDate) / 1000;
//            deltTime = 1;
//
//            m_TimeStamp = Time;
//        } else if ((sField[0].equalsIgnoreCase("$GPGSA")
//                || sField[0].equalsIgnoreCase("$GNGSA") || sField[0]
//                .equalsIgnoreCase("$BDGSA")) && iFieldNum >= 17) {
//
//            if (strGsa.trim() == "") {
//                if (nmea.indexOf(",,") >= 11)
//                    strGsa = nmea.substring(11, nmea.indexOf(",,"));
//            } else {
//                if (nmea.indexOf(",,") >= 11)
//                    strGsa = String.format("%s,%s", strGsa,
//                            nmea.substring(11, nmea.indexOf(",,")));
//            }
//            if (sField[15].trim().length() > 0) {
//                pdop = Double.valueOf(sField[15]);
//            }
//            if (sField[16].trim().length() > 0)
//                hdop = Double.valueOf(sField[16]);
//            if (sField[17].trim().length() > 0 && sField[17].indexOf("*") != -1) {
//                sField[17] = sField[17].substring(0, sField[17].indexOf("*"));
//                if (sField[17].trim().length() > 0)
//                    vdop = Double.valueOf(sField[17]);
//            }
//        } else if (sField[0].equalsIgnoreCase("$GPRMC")
//                || sField[0].equalsIgnoreCase("$GNRMC")
//                || sField[0].equalsIgnoreCase("$GLRMC")
//                || sField[0].equalsIgnoreCase("$BDRMC")) {
//            if (sField[7].trim().length() > 0) {
//                fSpeed_M_S = Double.valueOf(sField[7]) * 1.852 / 3600 * 1000;
//            }
//            if (sField[7].trim().length() > 0) {
//                if (Double.valueOf(sField[7]) > 0.05) {
//                    if (sField[8].trim().length() > 0)
//                        heading = Double.valueOf(sField[8]);
//                }
//            }
//            long m_Year = 1900, m_Month = 1, m_Day = 1;
//            if (sField[9].trim().length() > 5) {
//                m_Year = Integer.valueOf(sField[9].substring(4));
//                m_Month = Integer.valueOf(sField[9].substring(2, 4));
//                m_Day = Integer.valueOf(sField[9].substring(0, 2));
//            }
//            if (m_Year >= 89)
//                m_Year += 1900;
//            else
//                m_Year += 2000;
//            if (m_Year < 1990 || m_Year > 2099 || m_Month < 1 || m_Month > 12
//                    || m_Day < 1 || m_Day > 31) {
//                m_Year = m_Month = m_Day = 0;
//                return;
//            }
//
//            int m_Sec = 0, iSecOff = 0;
//            int m_Hour = 1, m_Min = 1;
//            if (sField[1].trim().length() >= 6) {
//                m_Hour = Integer.valueOf(sField[1].substring(0, 2));
//                m_Min = Integer.valueOf(sField[1].substring(2, 4));
//                m_Sec = Integer.valueOf(sField[1].substring(4, 6));
//            }
//            if (m_Sec > 59) {
//                iSecOff = m_Sec - 59;
//                m_Sec = 59;
//            }
//            if (m_Hour < 0 || m_Hour > 23 || m_Min < 0 || m_Min > 59
//                    || iSecOff > 60) {
//                return;
//            }
//            String sDate = String.format("%04d-%02d-%02d %02d:%02d:%02d",
//                    m_Year, m_Month, m_Day, m_Hour, m_Min, m_Sec);
//            // Log.e("开始时间", sDate);
//            year = (int) m_Year;
//            month = (int) m_Month;
//            day = (int) m_Day;
//
//            gpsDate = DateUtil.GetLocalDate(DateUtil.parseDate(sDate));
//            m_TimeStamp = Time;
//        } else if (sField[0].equalsIgnoreCase("$GPZDA") && iFieldNum >= 5) {
//            long m_Year = 1900, m_Month = 1, m_Day = 1;
//            if (sField[4].trim().length() > 0) {
//                m_Year = Integer.valueOf(sField[4]);
//            }
//            if (sField[3].trim().length() > 0) {
//                m_Month = Integer.valueOf(sField[3]);
//            }
//            if (sField[4].trim().length() > 0) {
//                m_Day = Integer.valueOf(sField[2]);
//            }
//
//            if (m_Year < 1990 || m_Year > 2099 || m_Month < 1 || m_Month > 12
//                    || m_Day < 1 || m_Day > 31) {
//                return;
//            }
//            int m_Sec = 1, iSecOff = 0;
//            int m_Hour = 1, m_Min = 1;
//            if (sField[1].trim().length() >= 6) {
//                m_Hour = Integer.valueOf(sField[1].substring(0, 2));
//                m_Min = Integer.valueOf(sField[1].substring(2, 4));
//                m_Sec = Integer.valueOf(sField[1].substring(4, 6));
//            }
//            if (m_Sec > 59) {
//                iSecOff = m_Sec - 59;
//                m_Sec = 59;
//            }
//            if (m_Hour < 0 || m_Hour > 23 || m_Min < 0 || m_Min > 59
//                    || iSecOff > 60) {
//                return;
//            }
//            // Log.e("开始时间", sDate);
//            year = (int) m_Year;
//            month = (int) m_Month;
//            day = (int) m_Day;
//
//            String sDate = String.format("%04d-%02d-%02d %02d:%02d:%02d",
//                    m_Year, m_Month, m_Day, m_Hour, m_Min, m_Sec);
//            gpsDate = DateUtil.GetLocalDate(DateUtil.parseDate(sDate));
//            utcDate = (DateUtil.parseDate(sDate));
//            Time = DateUtil.GetGPSTimeSpan(gpsDate) / 1000;
//            m_TimeStamp = Time;
//        } else if (sField[0].equalsIgnoreCase("$GPVTG")
//                || sField[0].equalsIgnoreCase("$GNVTG")
//                || sField[0].equalsIgnoreCase("$GLVTG")
//                || sField[0].equalsIgnoreCase("$BDVTG")) {
//            if (sField[8].trim() == ("K")) {
//                fSpeed_M_S = Double.valueOf(sField[7]) * 1000 / 3600;
//            }
//            if (sField[7].trim().length() > 0) {
//                if (Double.valueOf(sField[7]) > 0.05) {
//                    if (sField[1].trim().length() > 0)
//                        heading = Double.valueOf(sField[1]);
//                }
//            }
//        } else if (sField[0].equalsIgnoreCase("$GPGSV")
//                || sField[0].equalsIgnoreCase("$GNGSV")) {
//            if (Integer.valueOf(sField[2]) == 1) {
//                GSVIndex = 1;
//                m_tSatNum = 0;
//                GPSList.clear();
//                satelliteList.clear();
//                m_bUpdating = true;
//                if (BDList.size() > 0) {
//                    bUsedBDS = true;
//                    for (int i = 0; i < BDList.size(); i++) {
//                        GpsSatelliteInfo sGPS = BDList.get(i);
//                        satelliteList.add(sGPS);
//                        m_tSatNum++;
//                    }
//                } else {
//                    bUsedBDS = false;
//                }
//                if (GLONASSList.size() > 0) {
//                    bUsedGlonass = true;
//                    for (int i = 0; i < GLONASSList.size(); i++) {
//                        GpsSatelliteInfo sGPS = GLONASSList.get(i);
//                        satelliteList.add(sGPS);
//                        m_tSatNum++;
//                    }
//                } else {
//                    bUsedGlonass = false;
//                }
//            } else
//                GSVIndex++;
//            if (Integer.valueOf(sField[2]) == GSVIndex) {
//                int rec;
//                for (int i = 0; i < 4; i++) {
//                    rec = i * 4 + 4;
//                    if (rec < iFieldNum) {
//                        if (sField[rec + 3].indexOf("*") != -1) {
//                            sField[rec + 3] = (sField[rec + 3].substring(0,
//                                    sField[rec + 3].indexOf("*")));//
//                        }
//                        if (rec + 3 <= iFieldNum
//                                && (!sField[rec].equalsIgnoreCase(""))
//                                && (!sField[rec + 1].equalsIgnoreCase(""))
//                                && (!sField[rec + 3].equalsIgnoreCase(""))) {
//                            GpsSatelliteInfo sGPS = new GpsSatelliteInfo();
//                            sGPS.setAzimuth(Short.valueOf(sField[rec + 2]));
//                            if (sField[rec + 1].indexOf(".") != -1)
//                                sField[rec + 1] = sField[rec + 1].substring(0,
//                                        sField[rec + 1].indexOf("."));
//                            sGPS.setElevation(Integer.valueOf(sField[rec + 1]));
//                            sGPS.setPrn(Integer.valueOf(sField[rec]));
//                            if (sField[rec + 3].indexOf(".") != -1) {
//                                sField[rec + 3] = sField[rec + 3].substring(0,
//                                        sField[rec + 3].indexOf("."));
//                                sGPS.setSnrL1(Integer.valueOf(sField[rec + 3]));
//                            } else {
//                                sGPS.setSnrL1(Integer.valueOf(sField[rec + 3]));//
//                            }
//                            sGPS.setbUsed(strGsa.contains(sField[rec]));
//                            GPSList.add(sGPS);
//                            satelliteList.add(sGPS);
//                            // m_SatNum++;
//                            m_tSatNum++;
//                        }
//                        // m_tSatNum++;
//                    }
//                }
//            }
//            if (Integer.valueOf(sField[1]) == GSVIndex) {
//                m_bUpdating = false;
//            }
//        } else if (sField[0].equalsIgnoreCase("$GLGSV")) {
//            if (Integer.valueOf(sField[2]) == 1) {
//                GLONASSList.clear();
//                GSVIndex = 1;
//                m_bUpdating = true;
//            } else
//                GSVIndex++;
//
//            if (Integer.valueOf(sField[2]) == GSVIndex) {
//                int rec;
//                for (int i = 0; i < 4; i++) {
//                    rec = i * 4 + 4;
//                    if (rec + 3 <= iFieldNum) {
//                        if (sField[rec + 3].indexOf("*") != -1) {
//                            sField[rec + 3] = (sField[rec + 3].substring(0,
//                                    sField[rec + 3].indexOf("*")));//
//                        }
//                        if (sField[rec + 3].indexOf("*") != -1) {
//                            sField[rec + 3] = (sField[rec + 3].substring(0,
//                                    sField[rec + 3].indexOf("*")));//
//                        }
//                        if ((!sField[rec].equalsIgnoreCase(""))
//                                && (!sField[rec + 1].equalsIgnoreCase(""))
//                                && (!sField[rec + 3].equalsIgnoreCase(""))) {
//                            GpsSatelliteInfo sGPS = new GpsSatelliteInfo();
//                            sGPS.setAzimuth(Short.valueOf(sField[rec + 2]));
//                            if (sField[rec + 1].indexOf(".") != -1)
//                                sField[rec + 1] = sField[rec + 1].substring(0,
//                                        sField[rec + 1].indexOf("."));
//                            sGPS.setElevation(Integer.valueOf(sField[rec + 1]));
//                            sGPS.setPrn(Integer.valueOf(sField[rec]));
//                            if (sField[rec + 3].indexOf(".") != -1) {
//                                sField[rec + 3] = sField[rec + 3].substring(0,
//                                        sField[rec + 3].indexOf("."));
//                                sGPS.setSnrL1(Integer.valueOf(sField[rec + 3]));
//                            } else {
//                                sGPS.setSnrL1(Integer.valueOf(sField[rec + 3]));//
//                            }
//                            // sGPS.setSnrL1(Integer.valueOf(sField[rec +
//                            // 3]));//
//                            sGPS.setbUsed(strGsa.contains(sField[rec]));
//                            GLONASSList.add(sGPS);
//
//                            if (!bUsedGlonass) {
//                                satelliteList.add(sGPS);
//                            }
//                        }
//                    }
//                }
//            }
//            if (Integer.valueOf(sField[1]) == GSVIndex) {
//                m_bUpdating = false;
//            }
//        } else if (sField[0].equalsIgnoreCase("$BDGSV")) {
//            if (Integer.valueOf(sField[2]) == 1) {
//                BDList.clear();
//                GSVIndex = 1;
//                m_bUpdating = true;
//            } else
//                GSVIndex++;
//
//            if (Integer.valueOf(sField[2]) == GSVIndex) {
//                int rec;
//                for (int i = 0; i < 4; i++) {
//                    rec = i * 4 + 4;
//                    if (rec + 3 <= iFieldNum) {
//                        if (sField[rec + 3].indexOf("*") != -1) {
//                            sField[rec + 3] = (sField[rec + 3].substring(0,
//                                    sField[rec + 3].indexOf("*")));//
//                        }
//                        if ((!sField[rec].equalsIgnoreCase(""))
//                                && (!sField[rec + 1].equalsIgnoreCase(""))
//                                && (!sField[rec + 3].equalsIgnoreCase(""))) {
//                            GpsSatelliteInfo sGPS = new GpsSatelliteInfo();
//                            sGPS.setAzimuth(Short.valueOf(sField[rec + 2]));
//                            if (sField[rec + 1].indexOf(".") != -1)
//                                sField[rec + 1] = sField[rec + 1].substring(0,
//                                        sField[rec + 1].indexOf("."));
//                            sGPS.setElevation(Integer.valueOf(sField[rec + 1]));
//                            sGPS.setPrn((Integer.valueOf(sField[rec])));
//                            if (sField[rec + 3].indexOf(".") != -1) {
//                                sField[rec + 3] = sField[rec + 3].substring(0,
//                                        sField[rec + 3].indexOf("."));
//                                sGPS.setSnrL1(Integer.valueOf(sField[rec + 3]));
//                            } else {
//                                sGPS.setSnrL1(Integer.valueOf(sField[rec + 3]));//
//                            }
//                            sGPS.setbUsed(strGsa.contains(sField[rec]));
//                            BDList.add(sGPS);
//                            // m_SatNum++;
//                            // m_tSatNum++;
//                            if (!bUsedBDS) {
//                                satelliteList.add(sGPS);
//                            }
//                        }
//                        // m_tSatNum++;
//                    }
//                }
//            }
//            if (Integer.valueOf(sField[1]) == GSVIndex) {
//                m_bUpdating = false;
//            }
//        } else if (((sField[0].equalsIgnoreCase("$GPGST")) || (sField[0]
//                .equalsIgnoreCase("$GNGST"))) && iFieldNum >= 8) {
//
//            if (sField[7].trim().length() > 0) {
//                SigmaEast = Double.valueOf(sField[7]);
//            }
//            if (sField[6].trim().length() > 0) {
//                SigmaNorth = Double.valueOf(sField[6]);
//            }
//            if (sField[8].indexOf("*") != -1) {
//                sField[8] = (sField[8].substring(0, sField[8].indexOf("*")));//
//            }
//            if (sField[8].trim().length() > 0) {
//                vrms = Double.valueOf(sField[8]);
//            }
//            hrms = Math.sqrt(SigmaEast * SigmaEast + SigmaNorth * SigmaNorth);
//            rms = Math.sqrt(hrms * hrms + vrms * vrms);
//        }
//    }
//
//    public double getVdop() {
//        return vdop;
//    }
//
//    public double getHdop() {
//        return hdop;
//    }
//
//    public double getPdop() {
//        return pdop;
//    }
//
//    public double getHrms() {
//        return hrms;
//    }
//
//    public double getVrms() {
//        return vrms;
//    }
//
//    //纬度
//    public double getLatitude() {
//        return latitude;
//    }
//
//    //经度
//    public double getLongitude() {
//        return longitude;
//    }
//
//    //椭球高
//    public double getAltitude() {
//        return altitude;
//    }
//
//    //解状态
//    public int getStatusType() {
//        return gpsStatue;
//    }
//
//    //速度
//    public double getSpeed() {
//        return fSpeed_M_S;
//    }
//
//    //方位角 航向角
//    public double getBearing() {
//        return heading;
//    }
//
//    //可见卫星
//    public int getVisibleGnssCount() {
//        return m_tSatNum;
//    }
//
//    //可视卫星
//    public int getLockGnssCount() {
//        return m_SatNum;
//    }
//
//    //差分龄
//    public int getAge() {
//        return age;
//    }
//
//    public Date getLocalTime() {
//        return gpsDate;
//    }
//
//    public Date getUtcTime() {
//        return utcDate;
//    }
//
//    public List<GpsSatelliteInfo> getSatelliteList(){
//        return satelliteList;
//    }
}
