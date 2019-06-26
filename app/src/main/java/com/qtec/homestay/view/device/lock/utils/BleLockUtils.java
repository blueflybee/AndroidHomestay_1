package com.qtec.homestay.view.device.lock.utils;

import android.bluetooth.BluetoothAdapter;

import com.blankj.utilcode.util.ToastUtils;

/**
 * 保存和操作字典数据
 * 和spinner结合使用
 */
public class BleLockUtils {

  /**
   * 判断蓝牙是否打开
   */
  public static Boolean isBleEnable(){
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
//      ToastUtils.showShort("您还未打开蓝牙，请先打开蓝牙");
      return false;
    }
    return true;
  }

}
