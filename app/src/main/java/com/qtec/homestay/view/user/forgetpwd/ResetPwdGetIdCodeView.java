package com.qtec.homestay.view.user.forgetpwd;


import com.qtec.homestay.domain.model.mapp.rsp.ResetPwdGetIdCodeResponse;
import com.qtec.homestay.view.LoadDataView;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 2017/06/12
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public interface ResetPwdGetIdCodeView extends LoadDataView {

  void openResetPwd(ResetPwdGetIdCodeResponse response);

  void showGetIdCodeSuccess();
}
