package com.qtec.homestay.domain.model.mapp.req;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 2018/09/05
 *     desc   : $description
 * </pre>
 */
public class CheckCodeRequest {

  /**
   * userPhone : 用户手机号码
   * smsCode : 用户输入的验证码
   */

  private String userPhone;
  private String smsCode;

  public String getUserPhone() {
    return userPhone;
  }

  public void setUserPhone(String userPhone) {
    this.userPhone = userPhone;
  }

  public String getSmsCode() {
    return smsCode;
  }

  public void setSmsCode(String smsCode) {
    this.smsCode = smsCode;
  }
}