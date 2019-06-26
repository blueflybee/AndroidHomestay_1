package com.qtec.homestay.view.user.register;


import com.qtec.homestay.domain.model.mapp.rsp.RegisterResponse;
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
public interface RegisterView extends LoadDataView {

    void onGetIdCodeSuccess();

    void showRegisterSuccess(RegisterResponse response);
}
