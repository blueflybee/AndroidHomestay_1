package com.qtec.homestay.view.user.mine;

import com.qtec.homestay.domain.model.mapp.rsp.AddFeedbackResponse;
import com.qtec.homestay.view.LoadDataView;

/**
 * <pre>
 *     author : author
 *     time   : 2018/07/30
 *     desc   : FeedbackView
 * </pre>
 */
public interface FeedbackView extends LoadDataView {

  void onAddSuccess(AddFeedbackResponse response);
}