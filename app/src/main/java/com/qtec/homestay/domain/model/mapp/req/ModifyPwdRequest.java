package com.qtec.homestay.domain.model.mapp.req;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 2018/08/13
 *     desc   : $description
 * </pre>
 */
public class ModifyPwdRequest {

  /**
   * userPhone : 用户手机号码
   * oldPassword : 旧密码
   * newPassword : 新密码
   */

  private String userPhone;
  private String oldPassword;
  private String newPassword;

  public String getUserPhone() {
    return userPhone;
  }

  public void setUserPhone(String userPhone) {
    this.userPhone = userPhone;
  }

  public String getOldPassword() {
    return oldPassword;
  }

  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }
}