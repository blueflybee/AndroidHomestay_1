package com.qtec.homestay.view.user.mine;

import com.qtec.homestay.domain.model.mapp.rsp.ModifyUserInfoResponse;
import com.qtec.homestay.view.LoadDataView;

/**
 * <pre>
 *     author : wusj
 *     time   : 2018/08/15
 *     desc   : MyInfoView
 * </pre>
 */
public interface MyInfoView extends LoadDataView {

  void uploadHeadImageSuccess(String imageUrl);

  void uploadHeadImageFail();

  void showModifySuccess(ModifyUserInfoResponse response);
}