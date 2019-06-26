package com.qtec.homestay.view.user.mine;

import com.qtec.homestay.domain.model.mapp.rsp.ModifyPwdResponse;
import com.qtec.homestay.view.LoadDataView;

/**
 * <pre>
 *     author : wusj
 *     time   : 2018/08/13
 *     desc   : ModifyPwdView
 * </pre>
 */
public interface ModifyPwdView extends LoadDataView {

  void showModifySuccess(ModifyPwdResponse response);
}