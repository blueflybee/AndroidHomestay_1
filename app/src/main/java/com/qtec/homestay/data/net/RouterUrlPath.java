package com.qtec.homestay.data.net;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 2017/06/07
 *     desc   : 网关requestUrl
 *     version: 1.0
 * </pre>
 */
public class RouterUrlPath {

  public static final String PATH_BIND_ROUTER_TO_LOCK = "devadd";
  public static final String PATH_QUERY_BIND_ROUTER_TO_LOCK = "devcheck";
  public static final String PATH_UNBIND_ROUTER_TO_LOCK = "devunbound";
  public static final String PATH_GET_ROUTER_FIRST_CONFIG = "get_systemconfigure_cfg";

  private static String sToken;

  public static String getToken() {
    return sToken;
  }

  public static void setToken(String token) {
    RouterUrlPath.sToken = token;
  }

  public static final String METHOD_GET = "get";
  public static final String METHOD_POST = "post";
  public static final String METHOD_PUT = "put";
  public static final String METHOD_DELETE = "delete";

}
