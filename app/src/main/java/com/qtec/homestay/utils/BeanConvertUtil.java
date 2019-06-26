package com.qtec.homestay.utils;

import com.qtec.homestay.domain.model.mapp.rsp.GetDevsResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetRoomManageResponse;
import com.qtec.homestay.view.device.data.Lock;

/**
 * @author shaojun
 * @name BeanConvertUtil
 * @package com.fernandocejas.android10.sample.presentation.view.utils
 * @date 15-11-27
 */
public class BeanConvertUtil {


  public static Lock getLock(GetRoomManageResponse item) {
    Lock lock = new Lock();
    lock.setId(item.getDeviceSerialNo());
    lock.setName(item.getDeviceName());
    lock.setBatteryPower(item.getBatteryPower());
    lock.setLockPass(item.getLockPass());
    lock.setStartTime(item.getStartTime());
    lock.setEndTime(item.getEndTime());
    lock.setNetworkStatus(item.getNetworkStatus());
    lock.setResidentIdentifier(item.getResidentIdentifier());
    lock.setResidentName(item.getResidentName());
    lock.setResidentPhone(item.getResidentPhone());
    lock.setResidentStatus(item.getResidentStatus());
    lock.setRoomNo(item.getRoomNo());
    lock.setModel("lock");
    return lock;
  }

  public static Lock getLock(GetDevsResponse item) {
    Lock lock = new Lock();
    lock.setId(item.getDeviceSerialNo());
    lock.setName(item.getDeviceName());
    lock.setBatteryPower(item.getBatteryPower());
    lock.setNetworkStatus(item.getNetworkStatus());
    lock.setRoomNo(item.getRoomNo());
    lock.setModel(item.getDeviceModel());
    lock.setMasterSerialNo(item.getMasterSerialNo());
    lock.setMac(item.getMac());
    return lock;
  }

}
