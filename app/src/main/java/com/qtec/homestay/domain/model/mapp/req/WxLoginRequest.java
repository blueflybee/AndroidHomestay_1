package com.qtec.homestay.domain.model.mapp.req;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 2018/08/24
 *     desc   : $description
 * </pre>
 */
public class WxLoginRequest {

  /**
   * code : 客户端从微信获取的code
   * deviceSerialNumber : 非必传、手机设备序列号，用于阿里云推送
   * deviceToken : 非必传、ios做apns推送用到的，安卓做mqtt推送用到的参数
   * phoneModel : 非必传、手机型号；安卓端推送时，手机型号是华为时走mqtt推送走不通，需要走阿里云推送，所以需要根据手机型号选择推送方式
   * platform : 平台：ios/android
   * wechatAccount : 非必传、微信账号
   * userPhone : 非必传、用户手机号
   * openId : 非必传、微信平台返回的openId由服务器带回，第二次调用微信登录接口必填
   */

  private String code;
  private String deviceSerialNumber;
  private String deviceToken;
  private String phoneModel;
  private String platform;
  private String wechatAccount;
  private String userPhone;
  private String userPassword;
  private String openId;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDeviceSerialNumber() {
    return deviceSerialNumber;
  }

  public void setDeviceSerialNumber(String deviceSerialNumber) {
    this.deviceSerialNumber = deviceSerialNumber;
  }

  public String getDeviceToken() {
    return deviceToken;
  }

  public void setDeviceToken(String deviceToken) {
    this.deviceToken = deviceToken;
  }

  public String getPhoneModel() {
    return phoneModel;
  }

  public void setPhoneModel(String phoneModel) {
    this.phoneModel = phoneModel;
  }

  public String getPlatform() {
    return platform;
  }

  public void setPlatform(String platform) {
    this.platform = platform;
  }

  public String getWechatAccount() {
    return wechatAccount;
  }

  public void setWechatAccount(String wechatAccount) {
    this.wechatAccount = wechatAccount;
  }

  public String getUserPhone() {
    return userPhone;
  }

  public void setUserPhone(String userPhone) {
    this.userPhone = userPhone;
  }

  public String getUserPassword() {
    return userPassword;
  }

  public void setUserPassword(String userPassword) {
    this.userPassword = userPassword;
  }

  public String getOpenId() {
    return openId;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }
}