package com.qtec.homestay.domain.model.mapp.req;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 2017/06/12
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class CheckAppVersionRequest {

  /**
   * appType : 0表示light网关,1表示门锁,2表示安卓,3表示ios
   * deviceModel : 非必传参数，当appType传1时，若需要传deviceModel，只能取值lock111或者lock1110
   */

  private String appType;
  private String deviceModel;

  public String getAppType() {
    return appType;
  }

  public void setAppType(String appType) {
    this.appType = appType;
  }

  public String getDeviceModel() {
    return deviceModel;
  }

  public void setDeviceModel(String deviceModel) {
    this.deviceModel = deviceModel;
  }
}
