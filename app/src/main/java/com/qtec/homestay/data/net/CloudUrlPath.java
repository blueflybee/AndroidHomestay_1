package com.qtec.homestay.data.net;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 2017/06/07
 *     desc   : 云端requestUrl
 *     version: 1.0
 * </pre>
 */
public class CloudUrlPath {
	public static final String PATH_MODIFY_RESIDENT = "/resident/modify";
	public static final String PATH_CHECK_CODE = "/user/checkcode";
	public static final String PATH_WX_LOGIN = "/login/wechat";
	public static final String PATH_GET_STS_TOKEN = "/external/sts/gettoken";
	public static final String PATH_MODIFY_USER_INFO = "/user/save";
	public static final String PATH_MODIFY_PWD = "/user/modifypassword";
	public static final String PATH_GET_ROUTER_DETAIL = "/device/detail";
	public static final String PATH_TRANSMIT = "/transmit";
	public static final String PATH_ADD_FEEDBACK = "/feedback/add";


  public static final String PATH_UNBIND_DEV = "/device/unbind";
  public static final String PATH_GET_DEV_TREE = "/device/tree";
  public static final String PATH_MODIFY_ADM_PASS = "/device/modifypass";
  public static final String PATH_MODIFY_ROOM_NO = "/device/modifyroomno";
  public static final String PATH_GET_UNLOCK_RECORDS = "/device/recordlist";

  private static String sToken;

  public static String getToken() {
    return sToken;
  }

  public static void setToken(String token) {
    CloudUrlPath.sToken = token;
  }

  public static final String METHOD_GET = "get";
  public static final String METHOD_POST = "post";

  public static final String PATH_LOGIN = "/login/login";
  public static final String PATH_GET_ID_CODE = "/external/sms/register";
  public static final String PATH_REGISTER = "/user/register";
  public static final String PATH_RESET_PWD_GET_ID_CODE = "/external/sms/modifypassword";
  public static final String PATH_RESET_PWD_FORGET = "/user/forgetpassword";
  public static final String PATH_CHECK_APP_VERSION = "/version/queryversion";
  public static final String PATH_LOGOUT = "/login/logout";
  public static final String PATH_ADD_DEV = "/device/adddevice";
  public static final String PATH_GET_DEVS = "/device/list";


  public static final String PATH_ROOM_INFO_LIST = "/resident/list";
  public static final String PATH_SELECT_ROOM_LIST = "/device/getrooms";
  public static final String PATH_ROOM_CHECK_IN = "/resident/in";
  public static final String PATH_LEAVL_HOTEL = "/resident/out";
  public static final String PATH_HOUSE_HOLDER_DETAIL = "/resident/detail";

}
