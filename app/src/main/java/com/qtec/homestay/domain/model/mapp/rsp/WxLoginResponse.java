package com.qtec.homestay.domain.model.mapp.rsp;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 2018/08/24
 *     desc   : $description
 * </pre>
 */
public class WxLoginResponse {

  /**
   * id : 用户id
   * token : 与app交互token
   * userHeadImg : 用户头像
   * userNickName : 用户昵称
   * userPhone : 用户手机号
   * userUniqueKey : 用户唯一标识
   * deviceSerialNumber : 设备序列号
   * deviceToken : 设备token，ios走apns推送以及安卓走mqtt推送时用到的参数
   * platform : 登录平台 ANDROID/IOS
   * phoneModel : 手机型号，安卓华为机型无法使用mqtt，所以标识型号为huawei的手机走阿里云推送
   * userSex : 用户性别。0是男，1是女
   * openId : 微信平台返回的openId
   */

  private int id;
  private String token;
  private String userHeadImg;
  private String userNickName;
  private String userPhone;
  private String userUniqueKey;
  private String deviceSerialNumber;
  private String deviceToken;
  private String platform;
  private String phoneModel;
  private String userSex;
  private String isFirstLogin;
  private String openId;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
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

  public String getUserPhone() {
    return userPhone;
  }

  public void setUserPhone(String userPhone) {
    this.userPhone = userPhone;
  }

  public String getUserUniqueKey() {
    return userUniqueKey;
  }

  public void setUserUniqueKey(String userUniqueKey) {
    this.userUniqueKey = userUniqueKey;
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

  public String getPlatform() {
    return platform;
  }

  public void setPlatform(String platform) {
    this.platform = platform;
  }

  public String getPhoneModel() {
    return phoneModel;
  }

  public void setPhoneModel(String phoneModel) {
    this.phoneModel = phoneModel;
  }

  public String getUserSex() {
    return userSex;
  }

  public void setUserSex(String userSex) {
    this.userSex = userSex;
  }

  public String getIsFirstLogin() {
    return isFirstLogin;
  }

  public void setIsFirstLogin(String isFirstLogin) {
    this.isFirstLogin = isFirstLogin;
  }

  public String getOpenId() {
    return openId;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }
}