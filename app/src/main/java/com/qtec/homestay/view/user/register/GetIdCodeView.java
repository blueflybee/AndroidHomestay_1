package com.qtec.homestay.view.user.register;


import com.qtec.homestay.domain.model.mapp.rsp.GetIdCodeResponse;
import com.qtec.homestay.view.LoadDataView;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 2017/06/12
 *     desc   : 获取验证码view
 *     version: 1.0
 * </pre>
 */
public interface GetIdCodeView extends LoadDataView {
    void showUserPhoneEmp();

    void openRegister(GetIdCodeResponse response);

    void showGetIdCodeSuccess();

}
