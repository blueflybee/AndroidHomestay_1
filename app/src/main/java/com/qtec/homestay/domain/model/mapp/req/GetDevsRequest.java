package com.qtec.homestay.domain.model.mapp.req;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 18-7-16
 *     desc   : presenter
 *     version: 1.0
 * </pre>
 */
public class GetDevsRequest {

  /**
   * userUniqueKey : 用户唯一标识符
   * deviceType : 0标识light网关，1标识门锁
   * limit : 请求第一页传-1,，请求其他页则取服务端上次返回的列表最后一个记录对应的ID
   * pageSize : 每页大小
   */

  private String userUniqueKey;
  private String deviceType;
  private String limit;
  private int pageSize;

  public String getUserUniqueKey() {
    return userUniqueKey;
  }

  public void setUserUniqueKey(String userUniqueKey) {
    this.userUniqueKey = userUniqueKey;
  }

  public String getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

  public String getLimit() {
    return limit;
  }

  public void setLimit(String limit) {
    this.limit = limit;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }
}
