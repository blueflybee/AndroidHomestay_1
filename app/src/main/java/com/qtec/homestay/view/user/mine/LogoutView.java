package com.qtec.homestay.view.user.mine;

import com.qtec.homestay.domain.model.mapp.rsp.LogoutResponse;
import com.qtec.homestay.view.LoadDataView;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 18-7-16
 *     desc   : LogoutView
 *     version: 1.0
 * </pre>
 */
public interface LogoutView extends LoadDataView {

  void onLogout(LogoutResponse response);
}