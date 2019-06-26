package com.qtec.homestay.domain.model.mapp.rsp;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 18-7-17
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class GetUnlockRecordsResponse {

  /**
   * recordUniqueKey :
   * occurTime : 发生时间
   * userName : 管理员或者住户姓名
   * isAlarm : 是否为告警
   * messageCode : 0成功，1网络异常，2多次输入错误密码，3门锁电量不足，4防撬报警，5假锁报警，6未自动上锁报警，7胁迫开锁报警，其他待扩展
   * message : xxx
   */

  private String recordUniqueKey;
  private String occurTime;
  private String userName;
  private String isAlarm;
  private String messageCode;
  private String message;

  public String getRecordUniqueKey() {
    return recordUniqueKey;
  }

  public void setRecordUniqueKey(String recordUniqueKey) {
    this.recordUniqueKey = recordUniqueKey;
  }

  public String getOccurTime() {
    return occurTime;
  }

  public void setOccurTime(String occurTime) {
    this.occurTime = occurTime;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getIsAlarm() {
    return isAlarm;
  }

  public void setIsAlarm(String isAlarm) {
    this.isAlarm = isAlarm;
  }

  public String getMessageCode() {
    return messageCode;
  }

  public void setMessageCode(String messageCode) {
    this.messageCode = messageCode;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
