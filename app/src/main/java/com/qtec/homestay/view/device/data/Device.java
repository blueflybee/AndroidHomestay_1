package com.qtec.homestay.view.device.data;

import java.io.Serializable;

/**
 * <pre>
 *     author : shaojun
 *     e-mail : wusj@qtec.cn
 *     time   : 2018/08/07
 *     desc   : 抽象一个设备，如路由器、门锁或其他
 *     version: 1.0
 * </pre>
 */
public class Device implements Serializable {
  public static final String TYPE_ROUTER = "0";
  public static final String TYPE_LOCK = "1";

  private String type;
  private String typeName;
  private String id;
  private String name;
  private String model;
  private String version;
  private String mac;
  private String bleName;
  private boolean isBind;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public boolean isBind() {
    return isBind;
  }

  public void setBind(boolean bind) {
    isBind = bind;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public String getMac() {
    return mac;
  }

  public void setMac(String mac) {
    this.mac = mac;
  }

  public String getBleName() {
    return bleName;
  }

  public void setBleName(String bleName) {
    this.bleName = bleName;
  }

  @Override
  public String toString() {
    return "Device{" +
        "type='" + type + '\'' +
        ", typeName='" + typeName + '\'' +
        ", id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", model='" + model + '\'' +
        ", version='" + version + '\'' +
        ", mac='" + mac + '\'' +
        ", bleName='" + bleName + '\'' +
        ", isBind=" + isBind +
        '}';
  }
}
