package com.qtec.homestay.view.lodge;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qtec.homestay.R;
import com.qtec.homestay.domain.model.mapp.rsp.SelectRoomResponse;
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

public class SelectRoomListAdapter extends CommAdapter<SelectRoomResponse> {
  private int mIndexSelected = 0;
  private Context mContext;

  public SelectRoomListAdapter(Context context, List<SelectRoomResponse> data, int layoutId, int checkItemPosition) {
    super(context, data, layoutId);
    this.mContext = context;
    this.mIndexSelected = checkItemPosition;
  }

  public void initOtherItem(int index) {
    mIndexSelected = index;
    notifyDataSetChanged();
  }

  @Override
  public void convert(BaseViewHolder baseViewHolder, SelectRoomResponse response,int position) {
    TextView roomNo = (TextView) baseViewHolder.getView(R.id.tv_roomNo);
    ImageView imgChoose = (ImageView) baseViewHolder.getView(R.id.img_chooseLogo);

    roomNo.setText(response.getRoomNo());

    if (position != mIndexSelected) {
      ((ImageView) baseViewHolder.getView(R.id.img_chooseLogo)).setBackground(mContext.getResources().getDrawable(R.mipmap.router_no));
    } else {
      ((ImageView) baseViewHolder.getView(R.id.img_chooseLogo)).setBackground(mContext.getResources().getDrawable(R.mipmap.router_pick));
    }
  }
}
