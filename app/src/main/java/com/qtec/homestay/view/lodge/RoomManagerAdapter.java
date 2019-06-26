package com.qtec.homestay.view.lodge;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qtec.homestay.R;
import com.qtec.homestay.domain.model.mapp.rsp.GetRoomManageResponse;
import com.qtec.homestay.view.adapter.BaseViewHolder;
import com.qtec.homestay.view.adapter.CommAdapter;

import java.util.List;

/**
 * <pre>
 *      author: xiehao
 *      e-mail: xieh@qtec.cn
 *      time: 2017/06/08
 *      desc:
 *      version: 1.0
 * </pre>
 */

public class RoomManagerAdapter extends CommAdapter<GetRoomManageResponse> {
  OnCheckInRoomClickListener mOnCheckInClickListener;

  public RoomManagerAdapter(Context context, List<GetRoomManageResponse> data, int layoutId) {
    super(context, data, layoutId);
  }

  @Override
  public void convert(BaseViewHolder baseViewHolder, GetRoomManageResponse response,int position) {
    TextView battery = baseViewHolder.getView(R.id.tv_battery);
    battery.setText("门锁电量: "+response.getBatteryPower());

    TextView name = baseViewHolder.getView(R.id.tv_residentName);
    name.setText("客户: "+response.getResidentName());

    TextView time = baseViewHolder.getView(R.id.tv_time);
    time.setText("入住时间: "+response.getStartTime()+" - "+response.getEndTime());

    TextView deviceNo = baseViewHolder.getView(R.id.tv_device_no);
    deviceNo.setText(response.getDeviceSerialNo());

    TextView pwd = baseViewHolder.getView(R.id.tv_pwd);
    pwd.setText("当前密码: "+response.getLockPass());

    TextView roomNo = baseViewHolder.getView(R.id.tv_roomNo);
    roomNo.setText("房间号: "+response.getRoomNo());

    Button roomStatus = baseViewHolder.getView(R.id.btn_room_status);
    roomStatus.setText(response.getResidentStatus());
    roomStatus.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(mOnCheckInClickListener != null){
          mOnCheckInClickListener.onCheckInRoomClick(response);
        }
      }
    });


  }

  public void setOnCheckInRoomClickListener(OnCheckInRoomClickListener mOnCheckInClickListener) {
    this.mOnCheckInClickListener = mOnCheckInClickListener;
  }

  public interface OnCheckInRoomClickListener {
    void onCheckInRoomClick(GetRoomManageResponse data);
  }

}
