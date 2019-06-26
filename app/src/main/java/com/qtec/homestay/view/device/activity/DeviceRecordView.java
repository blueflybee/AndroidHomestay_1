package com.qtec.homestay.view.device.activity;

import com.qtec.homestay.domain.model.mapp.rsp.GetUnlockRecordsResponse;
import com.qtec.homestay.view.LoadDataView;

import java.util.List;

/**
 * <pre>
 *     author : wusj
 *     time   : 2018/08/13
 *     desc   : DeviceRecordView
 * </pre>
 */
public interface DeviceRecordView extends LoadDataView {

  void showUnlockRecords(List<GetUnlockRecordsResponse> responses);
}