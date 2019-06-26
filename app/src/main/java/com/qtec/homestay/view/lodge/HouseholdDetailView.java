package com.qtec.homestay.view.lodge;


import com.qtec.homestay.domain.model.mapp.rsp.CheckInRoomResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetHoldDetailResponse;
import com.qtec.homestay.view.LoadDataView;

/**
 * @author shaojun
 * @name LoginView
 * @package com.fernandocejas.android10.sample.presentation.view
 * @date 15-9-10
 */
public interface HouseholdDetailView extends LoadDataView {
  void holdDetail(GetHoldDetailResponse response);
}
