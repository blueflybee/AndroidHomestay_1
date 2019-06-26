package com.qtec.homestay.domain.model.mapp.req;

/**
 * <pre>
 *     author :
 *     e-mail :
 *     time   : 2017/06/07
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class GetRoomManageRequest {

  private String userUniqueKey;
  private int pageSize;
  private String limit;

//  "userUniqueKey":"用户唯一标识符",
//      "pageSize":"每页大小",
//      "limit":"传-1代表请求第一页，请求其他页时取服务端上一次返回的列表最后一个记录对应的id"

  public String getUserUniqueKey() {
    return userUniqueKey;
  }

  public void setUserUniqueKey(String userUniqueKey) {
    this.userUniqueKey = userUniqueKey;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public String getLimit() {
    return limit;
  }

  public void setLimit(String limit) {
    this.limit = limit;
  }
}
