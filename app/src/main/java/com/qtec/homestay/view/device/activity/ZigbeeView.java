package com.qtec.homestay.view.device.activity;

import com.qtec.homestay.view.LoadDataView;

/**
 * <pre>
 *     author : wusj
 *     time   : 2018/08/08
 *     desc   : ZigbeeView
 * </pre>
 */
public interface ZigbeeView extends LoadDataView {

  void showBindFail(String msg);

  void showBindSuccess();

  void showUnbindFail(String msg);

  void showUnbindSuccess();

  void showQuerySuccess();
}