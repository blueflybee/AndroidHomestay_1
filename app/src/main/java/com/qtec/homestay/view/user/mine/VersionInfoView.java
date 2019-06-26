package com.qtec.homestay.view.user.mine;

import com.qtec.homestay.domain.model.mapp.rsp.CheckAppVersionResponse;
import com.qtec.homestay.view.LoadDataView;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 18-7-16
 *     desc   : VersionInfoView
 *     version: 1.0
 * </pre>
 */
public interface VersionInfoView extends LoadDataView {
  void showVersionInfo(CheckAppVersionResponse response);
}