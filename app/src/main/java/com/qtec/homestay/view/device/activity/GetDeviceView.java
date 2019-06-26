package com.qtec.homestay.view.device.activity;

import com.blueflybee.uilibrary2.data.TopologyNode;
import com.qtec.homestay.domain.model.mapp.rsp.GetDevsResponse;
import com.qtec.homestay.view.LoadDataView;

import java.util.List;

/**
 * <pre>
 *     author : wusj
 *     time   : 2018/08/02
 *     desc   : GetDeviceView
 * </pre>
 */
public interface GetDeviceView extends LoadDataView {

  void showDevices(List<GetDevsResponse> responses, String devType);

  void showTopology(TopologyNode node);
}