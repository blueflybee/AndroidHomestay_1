package com.qtec.homestay.domain.model.mapp.req;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 2018/07/30
 *     desc   : $description
 * </pre>
 */
public class AddFeedbackRequest {

  /**
   * userUniqueKey : 用户唯一标识
   * feedbackContent : 反馈内容
   */

  private String userUniqueKey;
  private String feedbackContent;

  public String getUserUniqueKey() {
    return userUniqueKey;
  }

  public void setUserUniqueKey(String userUniqueKey) {
    this.userUniqueKey = userUniqueKey;
  }

  public String getFeedbackContent() {
    return feedbackContent;
  }

  public void setFeedbackContent(String feedbackContent) {
    this.feedbackContent = feedbackContent;
  }
}