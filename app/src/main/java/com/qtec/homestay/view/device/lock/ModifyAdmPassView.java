package com.qtec.homestay.view.device.lock;

import com.qtec.homestay.domain.model.mapp.rsp.ModifyAdmPassResponse;
import com.qtec.homestay.view.LoadDataView;

/**
 * <pre>
 *     author : wusj
 *     time   : 2018/08/13
 *     desc   : ModifyAdmPassView
 * </pre>
 */
public interface ModifyAdmPassView extends LoadDataView {

  void showModifySuccess(ModifyAdmPassResponse response);
}