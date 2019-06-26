package com.qtec.homestay.domain.model.mapp.req;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 2018/08/15
 *     desc   : $description
 * </pre>
 */
public class ModifyUserInfoRequest {
  private String userPhone;
  private String userHeadImg;
  private String userNickName;

  public String getUserPhone() {
    return userPhone;
  }

  public void setUserPhone(String userPhone) {
    this.userPhone = userPhone;
  }

  public String getUserHeadImg() {
    return userHeadImg;
  }

  public void setUserHeadImg(String userHeadImg) {
    this.userHeadImg = userHeadImg;
  }

  public String getUserNickName() {
    return userNickName;
  }

  public void setUserNickName(String userNickName) {
    this.userNickName = userNickName;
  }
}