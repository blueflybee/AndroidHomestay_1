package com.qtec.homestay.view.device.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ItemLockListBinding;
import com.qtec.homestay.domain.model.mapp.rsp.GetDevsResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : wusj
 *     e-mail :
 *     time   : 2018/08/02
 *     desc   : LockListAdapter
 * </pre>
 */
public class LockListAdapter extends BaseAdapter {

  private static final String TAG = LockListAdapter.class.getName();

  private Context mContext;
  private List<GetDevsResponse> mGetDevsResponses = new ArrayList<>();
  private OnNetworkClickListener mOnNetworkClickListener;

  public LockListAdapter(@NonNull Context context) {
    mContext = context;
  }

  public LockListAdapter(@NonNull Context context, List<GetDevsResponse> data) {
    mContext = context;
    if (data == null || data.isEmpty()) return;
    this.mGetDevsResponses = data;
  }

  @Override
  public int getCount() {
    return mGetDevsResponses.size();
  }

  @Override
  public GetDevsResponse getItem(int position) {
    return mGetDevsResponses.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
    GetDevsResponse item = getItem(position);
    if (convertView == null) {
      LayoutInflater inflater = LayoutInflater.from(mContext);
      ItemLockListBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_lock_list, parent, false);
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

  private void onViewBind(ViewHolder holder, GetDevsResponse item, int position) {
    //networkStatus : 门锁组网状态：0未组网，1已组网
    holder.binding.tvName.setText(item.getDeviceName());
    String networkStatus = item.getNetworkStatus();
    Resources resources = mContext.getResources();
    holder.binding.btnNetwork.setTextColor("0".equals(networkStatus) ? resources.getColor(R.color.green_00beaf) : resources.getColor(R.color.black_333333));
    holder.binding.btnNetwork.setText("0".equals(networkStatus) ? "zigbee组网" : "已组网");
    holder.binding.btnNetwork.setEnabled("0".equals(networkStatus));
    holder.binding.btnNetwork.setOnClickListener(v -> {
      if (mOnNetworkClickListener != null) {
        mOnNetworkClickListener.onClick(item);
      }
    });
  }

  public void clear() {
    mGetDevsResponses.clear();
    notifyDataSetChanged();
  }

  public void add(GetDevsResponse item) {
    mGetDevsResponses.add(item);
    notifyDataSetChanged();
  }

  public void update(List<GetDevsResponse> items) {
    if (items == null || items.isEmpty()) {
      clear();
      return;
    }
    mGetDevsResponses.clear();
    mGetDevsResponses.addAll(items);
    notifyDataSetChanged();
  }

  public interface OnNetworkClickListener{
    void onClick(GetDevsResponse lock);
  }

  public void setOnNetworkClickListener(OnNetworkClickListener onNetworkClickListener) {
    mOnNetworkClickListener = onNetworkClickListener;
  }

  private class ViewHolder {
    private final ItemLockListBinding binding;
    ViewHolder(ItemLockListBinding binding) {
      this.binding = binding;
    }

    public void bind(GetDevsResponse item) {
      binding.executePendingBindings();
    }
  }
}