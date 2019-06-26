package com.qtec.homestay.view.lodge;


import com.qtec.homestay.domain.model.mapp.rsp.CheckInRoomResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetRoomManageResponse;
import com.qtec.homestay.view.LoadDataView;

import java.util.List;

/**
 * @author shaojun
 * @name LoginView
 * @package com.fernandocejas.android10.sample.presentation.view
 * @date 15-9-10
 */
public interface RoomCheckInView extends LoadDataView {
  void checkInRoom(CheckInRoomResponse response);
}
