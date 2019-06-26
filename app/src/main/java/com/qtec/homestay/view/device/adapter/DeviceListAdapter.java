package com.qtec.homestay.view.device.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ItemDeviceListBinding;
import com.qtec.homestay.domain.model.mapp.rsp.GetDevsResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetUnlockRecordsResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : wusj
 *     e-mail :
 *     time   : 2018/08/02
 *     desc   : DeviceListAdapter
 * </pre>
 */
public class DeviceListAdapter extends BaseAdapter {

  private static final String TAG = DeviceListAdapter.class.getName();

  private Context mContext;
  private List<GetDevsResponse> mGetDevsResponses = new ArrayList<>();

  public DeviceListAdapter(@NonNull Context context) {
    mContext = context;
  }

  public DeviceListAdapter(@NonNull Context context, List<GetDevsResponse> data) {
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
      ItemDeviceListBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_device_list, parent, false);
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
    holder.binding.tvName.setText(item.getDeviceName());

    Drawable dRouter = mContext.getResources().getDrawable(R.mipmap.shebei_router);
    Drawable dLock = mContext.getResources().getDrawable(R.mipmap.shebei_lock);
    Drawable dLeft = "0".equals(item.getDeviceType()) ? dRouter : dLock;
    dLeft.setBounds(0, 0, dLeft.getMinimumWidth(), dLeft.getMinimumHeight());
    Drawable dRight = mContext.getResources().getDrawable(R.mipmap.ic_navigate_next_black_48dp);
    dRight.setBounds(0, 0, dRight.getMinimumWidth(), dRight.getMinimumHeight());
    holder.binding.tvName.setCompoundDrawables(dLeft, null, dRight, null);
  }

  public void clear() {
    mGetDevsResponses.clear();
    notifyDataSetChanged();
  }

  public void add(GetDevsResponse item) {
    mGetDevsResponses.add(item);
    notifyDataSetChanged();
  }

  public void add(List<GetDevsResponse> items) {
    if (items == null || items.isEmpty()) return;
    mGetDevsResponses.addAll(items);
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

  private class ViewHolder {
    private final ItemDeviceListBinding binding;
    ViewHolder(ItemDeviceListBinding binding) {
      this.binding = binding;
    }

    public void bind(GetDevsResponse item) {
      binding.executePendingBindings();
    }
  }
}