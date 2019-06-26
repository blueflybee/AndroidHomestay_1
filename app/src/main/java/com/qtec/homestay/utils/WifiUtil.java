package com.qtec.homestay.utils;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.List;

/**
 * @author shaojun
 * @name WifiUtil
 * @package com.fernandocejas.android10.sample.presentation.view.utils
 * @date 15-11-27
 */
public class WifiUtil {

  public static List<ScanResult> getWifis(Context context) {
    WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    WifiInfo info = wifiManager.getConnectionInfo();
    if (info == null) return null;
    wifiManager.startScan();  //开始扫描AP
    List<ScanResult> scanResults = wifiManager.getScanResults();
    for (ScanResult scanResult : scanResults) {
      System.out.println("scanResult = " + scanResult);
    }
    return scanResults;
  }

}
