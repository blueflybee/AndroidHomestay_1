package com.qtec.homestay.view.lodge;

import com.qtec.homestay.domain.model.mapp.rsp.ModifyResidentResponse;
import com.qtec.homestay.view.LoadDataView;

/**
 * <pre>
 *     author : wusj
 *     time   : 2018/09/06
 *     desc   : ModifyResidentView
 * </pre>
 */
public interface ModifyResidentView extends LoadDataView {

  void modifyResidentSuccess(ModifyResidentResponse response);
}