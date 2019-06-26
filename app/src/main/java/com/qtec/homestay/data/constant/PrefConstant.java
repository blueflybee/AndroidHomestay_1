package com.qtec.homestay.data.constant;

import android.support.annotation.NonNull;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.SPUtils;

/**
 * Preferences 常量
 *
 * @author shaojun
 * @name PrefConstant
 * @package com.fernandocejas.android10.sample.presentation.comm
 * @date 15-10-10
 */
public class PrefConstant {

  public static final String SP_NAME = "sp_router";
  public static String MSG_DEVICE_ID = "msg_device_id";

  public static final String SP_USER_HEAD_IMG = "user_head_img";
  public static final String SP_USER_NICK_NAME = "user_nick_name";
  public static final String SP_APP_VERSION_CODE = "app_version_code";

  private static final String SP_APP_TOKEN = "app_token";
  private static final String SP_CLOUD_URL = "cloud_url";

  public static final String SP_TOKEN = "app_token";
  private static final String SP_USER_ID = "sp_user_id";
  public static final String SP_USER_UNIQUE_KEY = "sp_user_unique_key";
  public static final String SP_USER_PHONE = "sp_user_phone";
  public static final String SP_USER_PWD = "sp_user_pwd";
  public static final String SP_WX_CODE = "sp_wx_code";
  public static final String SP_WX_OPEN_ID = "sp_wx_open_id";


  public static void setUserId(int userId) {
    SPUtils.getInstance(SP_NAME).put(SP_USER_ID, userId);
  }

  /**
   * 获取门锁用户id
   */
  public static int getUserId() {
    int userId = SPUtils.getInstance(SP_NAME).getInt(SP_USER_ID);
    return userId == -1 ? 0 : userId;
  }

  public static String getUserIdInHexString() {
    String userId = Integer.toHexString(PrefConstant.getUserId());
    StringBuilder sb = new StringBuilder();
    sb.append(userId);
    if (userId.length() < 8) {
      int supplyZeroLength = 8 - userId.length();
      for (int i = 0; i < supplyZeroLength; i++) {
        sb.insert(0, "0");
      }
    }
    return sb.toString();
  }

  public static String getUserIdInHexString(int lockUserId) {
    String userId = Integer.toHexString(lockUserId);
    StringBuilder sb = new StringBuilder();
    sb.append(userId);
    if (userId.length() < 8) {
      int supplyZeroLength = 8 - userId.length();
      for (int i = 0; i < supplyZeroLength; i++) {
        sb.insert(0, "0");
      }
    }
    return sb.toString();
  }

  /**
   * 根据设备类型获取网关或门锁的用户id
   */
  public static String getUserUniqueKey() {
    return SPUtils.getInstance(SP_NAME).getString(SP_USER_UNIQUE_KEY);
  }

  public static String getUserHeadImg() {
    return SPUtils.getInstance(SP_NAME).getString(SP_USER_HEAD_IMG);
  }

  public static void putUserHeadImg(String imageUrl) {
    SPUtils.getInstance(SP_NAME).put(SP_USER_HEAD_IMG, imageUrl);
  }

  public static String getUserNickName() {
    return SPUtils.getInstance(SP_NAME).getString(SP_USER_NICK_NAME);
  }

  public static void putUserNickName(@NonNull String nickName) {
    SPUtils.getInstance(SP_NAME).put(SP_USER_NICK_NAME, nickName);
  }

  public static String getAppToken() {
    return SPUtils.getInstance(SP_NAME).getString(SP_APP_TOKEN);
  }

  public static void putAppToken(@NonNull String token) {
    SPUtils.getInstance(SP_NAME).put(SP_APP_TOKEN, token);
  }

  public static String getCloudUrl() {
    return SPUtils.getInstance(SP_NAME).getString(SP_CLOUD_URL);
  }

  public static void putCloudUrl(@NonNull String url) {
    SPUtils.getInstance(SP_NAME).put(SP_CLOUD_URL, url);
  }

  public static String getUserPwd() {
    return SPUtils.getInstance(SP_NAME).getString(SP_USER_PWD);
  }

  public static void putUserPwd(String pwd) {
    SPUtils.getInstance(SP_NAME).put(PrefConstant.SP_USER_PWD, EncryptUtils.encryptMD5ToString(pwd));
  }


  public static String getUserPhone() {
    return SPUtils.getInstance(SP_NAME).getString(SP_USER_PHONE);
  }

  public static String getMsgDeviceID() {
    return SPUtils.getInstance(SP_NAME).getString(MSG_DEVICE_ID);
  }

  public static void putWXCode(String wxCode) {
    SPUtils.getInstance(SP_NAME).put(SP_WX_CODE, wxCode);
  }

  public static String getWXCode() {
    return SPUtils.getInstance(SP_NAME).getString(SP_WX_CODE);
  }
  public static void putWXOpenId(String openId) {
    SPUtils.getInstance(SP_NAME).put(SP_WX_OPEN_ID, openId);
  }

  public static String getWXOpenId() {
    return SPUtils.getInstance(SP_NAME).getString(SP_WX_OPEN_ID);
  }

}
