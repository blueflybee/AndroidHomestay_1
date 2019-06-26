package com.qtec.homestay.view.device.activity;

import com.qtec.homestay.domain.model.mapp.rsp.AddDevResponse;
import com.qtec.homestay.domain.model.mapp.rsp.UnbindDevResponse;
import com.qtec.homestay.view.LoadDataView;

/**
 * <pre>
 *     author : wusj
 *     time   : 2018/08/06
 *     desc   : UpdateDeviceView
 * </pre>
 */
public interface UpdateDeviceView extends LoadDataView {

  void onAddDevice(AddDevResponse response);

  void onUnbindDevice(UnbindDevResponse response);
}