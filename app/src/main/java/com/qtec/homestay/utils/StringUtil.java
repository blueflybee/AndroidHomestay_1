package com.qtec.homestay.utils;

import android.text.TextUtils;

import com.qtec.homestay.domain.model.mapp.rsp.GetRoomManageResponse;

import java.util.UUID;

/**
 * <pre>
 *     author : shaojun
 *     e-mail : wusj@qtec.cn
 *     time   : 2018/07/25
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class StringUtil {
  /**
   * 对输入string的3到7位之间打*号
   *
   * @param origin
   * @return
   */
  public static String addStar(String origin) {
    if (TextUtils.isEmpty(origin)) return "";
    if (origin.length() <= 3) return origin;
    if (origin.length() > 3 && origin.length() <= 7) {
      String head = origin.substring(0, 3);
      return head + "****";
    }
    if (origin.length() > 7) {
      String head = origin.substring(0, 3);
      String tail = origin.substring(7);
      return head + "****" + tail;
    }
    return origin;
  }

  /**
   * 获取uuid
   *
   * @return
   */
  public static String getUUID() {
    UUID uuid = UUID.randomUUID();
    String uniqueKey = uuid.toString().replaceAll("-", "");
    return uniqueKey;
  }

  //  房间号：201
//  客户：张三
//  入住时间：2018/07/04-2018/07/05
//  当前密码：156345
  public static String getCheckOutInfo(GetRoomManageResponse item) {
    StringBuilder sb = new StringBuilder();
    sb.append("房间号：").append(item.getRoomNo()).append("\n")
        .append("客户：").append(item.getResidentName()).append("\n")
        .append("入住时间：").append(item.getStartTime()).append("\n")
        .append("当前密码：").append(item.getLockPass()).append("\n");
    return sb.toString();
  }

  public static String getResidentInfo(GetRoomManageResponse item) {
    StringBuilder sb = new StringBuilder();
    sb.append(item.getRoomNo()).append("\n\n")
        .append(item.getResidentName()).append("\n\n")
        .append(item.getResidentIdentifier()).append("\n\n")
        .append(item.getResidentPhone()).append("\n\n")
        .append(item.getStartTime()).append("\n\n")
        .append(item.getLockPass());
    return sb.toString();
  }

  public static void main(String args[]) {
    System.out.println("Hello World!");
    GetRoomManageResponse item = new GetRoomManageResponse();
    item.setResidentName("张三");
    item.setRoomNo("201");
    item.setStartTime("2018/07/04-2018/07/05");
    item.setLockPass("156345");
    String checkOutInfo = getCheckOutInfo(item);
    System.out.println("checkOutInfo = " + checkOutInfo);
  }
}
