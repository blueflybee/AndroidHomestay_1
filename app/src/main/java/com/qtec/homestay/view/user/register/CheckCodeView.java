package com.qtec.homestay.view.user.register;

import com.qtec.homestay.domain.model.mapp.rsp.CheckCodeResponse;
import com.qtec.homestay.view.LoadDataView;

/**
 * <pre>
 *     author : wusj
 *     time   : 2018/09/05
 *     desc   : CheckCodeView
 * </pre>
 */
public interface CheckCodeView extends LoadDataView {

  void onCheckCodeSuccess(CheckCodeResponse response);
}