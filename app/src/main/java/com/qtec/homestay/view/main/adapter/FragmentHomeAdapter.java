package com.qtec.homestay.view.main.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ItemFragmentHomeBinding;
import com.qtec.homestay.domain.model.mapp.rsp.GetRoomManageResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : wusj
 *     e-mail :
 *     time   : 2018/08/06
 *     desc   : FragmentHomeAdapter
 * </pre>
 */
public class FragmentHomeAdapter extends BaseAdapter {

  private static final String TAG = FragmentHomeAdapter.class.getName();

  private Context mContext;
  private List<GetRoomManageResponse> mGetRoomManageResponses = new ArrayList<>();
  private OnCheckClickListener mOnCheckClickListener;

  public FragmentHomeAdapter(@NonNull Context context) {
    mContext = context;
  }

  public FragmentHomeAdapter(@NonNull Context context, List<GetRoomManageResponse> data) {
    mContext = context;
    if (data == null || data.isEmpty()) return;
    this.mGetRoomManageResponses = data;
  }

  @Override
  public int getCount() {
    return mGetRoomManageResponses.size();
  }

  @Override
  public GetRoomManageResponse getItem(int position) {
    return mGetRoomManageResponses.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
    GetRoomManageResponse item = getItem(position);
    if (convertView == null) {
      LayoutInflater inflater = LayoutInflater.from(mContext);
      ItemFragmentHomeBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_fragment_home, parent, false);
      holder = new ViewHolder(binding);
      holder.bind(item);
      convertView = binding.getRoot();
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    onViewBind(holder, item, position);
    return convertView;
  }

  private void onViewBind(ViewHolder holder, GetRoomManageResponse item, int position) {
    Resources resources = mContext.getResources();
    ItemFragmentHomeBinding binding = holder.binding;
    binding.tvRoomNo.setText("房间号：" + item.getRoomNo());
    binding.tvGuest.setText("客户：" + item.getResidentName());
    binding.tvDate.setText("入住时间：" + item.getStartTime());
    binding.tvPwd.setText("当前密码：" + item.getLockPass());
//    holder.binding.tvLockStatus.setText(item.getRoomNo());
    if (!TextUtils.isEmpty(item.getBatteryPower())) {
      binding.tvBattery.setText(item.getBatteryPower() + "%");
    }
//    "residentStatus":"入住状态,1存在住户，0不存在住户",
    if ("0".equals(item.getResidentStatus())) {
      binding.tvCheckInOut.setText("入住");
      binding.tvCheckInOut.setBackgroundResource(R.drawable.shape_rec_radius_15dp_green_bg);
      binding.tvCheckInOut.setOnClickListener(v -> {
        if (mOnCheckClickListener != null) {
          mOnCheckClickListener.onCheckIn(item);
        }
      });
    } else {
      binding.tvCheckInOut.setBackgroundResource(R.drawable.shape_rec_radius_15dp_yellow_bg);
      binding.tvCheckInOut.setText("离店");
      binding.tvCheckInOut.setOnClickListener(v -> {
        if (mOnCheckClickListener != null) {
          mOnCheckClickListener.onCheckOut(item);
        }
      });
    }

    binding.ivOnline.setVisibility("1".equals(item.getNetworkStatus()) ? View.VISIBLE : View.GONE);
  }

  public void clear() {
    mGetRoomManageResponses.clear();
    notifyDataSetChanged();
  }

  public void add(GetRoomManageResponse item) {
    mGetRoomManageResponses.add(item);
    notifyDataSetChanged();
  }

  public void update(List<GetRoomManageResponse> items) {
    if (items == null || items.isEmpty()) return;
    mGetRoomManageResponses.clear();
    mGetRoomManageResponses.addAll(items);
    notifyDataSetChanged();
  }

  private class ViewHolder {
    private final ItemFragmentHomeBinding binding;

    ViewHolder(ItemFragmentHomeBinding binding) {
      this.binding = binding;
    }

    public void bind(GetRoomManageResponse item) {
      binding.executePendingBindings();
    }
  }

  public void setOnCheckClickListener(OnCheckClickListener onCheckClickListener) {
    mOnCheckClickListener = onCheckClickListener;
  }

  public interface OnCheckClickListener{
    void onCheckIn(GetRoomManageResponse item);

    void onCheckOut(GetRoomManageResponse item);
  }
}