package com.qtec.homestay.view.device.activity;

import com.qtec.homestay.domain.model.mapp.rsp.GetDeviceDetailResponse;
import com.qtec.homestay.view.LoadDataView;

/**
 * <pre>
 *     author : wusj
 *     time   : 2018/08/09
 *     desc   : DeviceDetailView
 * </pre>
 */
public interface DeviceDetailView extends LoadDataView {

  void showDeviceDetail(GetDeviceDetailResponse response);
}