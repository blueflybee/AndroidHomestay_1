package com.qtec.homestay.view.main.data;

import java.util.List;

/**
 * <pre>
 *     author : shaojun
 *     e-mail : wusj@qtec.cn
 *     time   : 2018/08/14
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class Address {

  private List<Double> queryLocation;
  private List<AddrListBean> addrList;

  public List<Double> getQueryLocation() {
    return queryLocation;
  }

  public void setQueryLocation(List<Double> queryLocation) {
    this.queryLocation = queryLocation;
  }

  public List<AddrListBean> getAddrList() {
    return addrList;
  }

  public void setAddrList(List<AddrListBean> addrList) {
    this.addrList = addrList;
  }

  public static class AddrListBean {
    /**
     * type : street
     * status : 1
     * name : 宁东路
     * admCode : 330109
     * admName : 浙江省,杭州市,萧山区
     * addr :
     * nearestPoint : [120.27253,30.1957]
     * distance : 20.667
     */

    private String type;
    private int status;
    private String name;
    private String admCode;
    private String admName;
    private String addr;
    private double distance;
    private List<Double> nearestPoint;

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public int getStatus() {
      return status;
    }

    public void setStatus(int status) {
      this.status = status;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getAdmCode() {
      return admCode;
    }

    public void setAdmCode(String admCode) {
      this.admCode = admCode;
    }

    public String getAdmName() {
      return admName;
    }

    public void setAdmName(String admName) {
      this.admName = admName;
    }

    public String getAddr() {
      return addr;
    }

    public void setAddr(String addr) {
      this.addr = addr;
    }

    public double getDistance() {
      return distance;
    }

    public void setDistance(double distance) {
      this.distance = distance;
    }

    public List<Double> getNearestPoint() {
      return nearestPoint;
    }

    public void setNearestPoint(List<Double> nearestPoint) {
      this.nearestPoint = nearestPoint;
    }

    @Override
    public String toString() {
      return "AddrListBean{" +
          "type='" + type + '\'' +
          ", status=" + status +
          ", name='" + name + '\'' +
          ", admCode='" + admCode + '\'' +
          ", admName='" + admName + '\'' +
          ", addr='" + addr + '\'' +
          ", distance=" + distance +
          ", nearestPoint=" + nearestPoint +
          '}';
    }
  }

  @Override
  public String toString() {
    return "Address{" +
        "queryLocation=" + queryLocation +
        ", addrList=" + addrList +
        '}';
  }

//  public static Address fromJson(String json) {
//    json = "{\"queryLocation\":[30.195179,120.272258],\"addrList\":[{\"type\":\"street\",\"status\":1,\"name\":\"宁东路\",\"admCode\":\"330109\",\"admName\":\"浙江省,杭州市,萧山区\",\"addr\":\"\",\"nearestPoint\":[120.27253,30.19570],\"distance\":20.667}]}";
////    if (TextUtils.isEmpty(json)) return null;
//    Type type = new TypeToken<Address>() {
//    }.getType();
//    return (Address) new JsonMapper().fromJsonNullStringToEmpty(json, type);
//  }

}
