package com.qtec.homestay.view.device.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.wifi.ScanResult;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ItemChooseWifiBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : wusj
 *     e-mail :
 *     time   : 2018/08/01
 *     desc   : ChoseWifiAdapter
 * </pre>
 */
public class ChoseWifiAdapter extends BaseAdapter {

  private static final String TAG = ChoseWifiAdapter.class.getName();

  private Context mContext;
  private List<ScanResult> mScanResults = new ArrayList<>();

  public ChoseWifiAdapter(@NonNull Context context) {
    mContext = context;
  }

  public ChoseWifiAdapter(@NonNull Context context, List<ScanResult> data) {
    mContext = context;
    if (data == null || data.isEmpty()) return;
    this.mScanResults = data;
  }

  @Override
  public int getCount() {
    return mScanResults.size();
  }

  @Override
  public ScanResult getItem(int position) {
    return mScanResults.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
    ScanResult item = getItem(position);
    if (convertView == null) {
      LayoutInflater inflater = LayoutInflater.from(mContext);
      ItemChooseWifiBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_choose_wifi, parent, false);
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

  private void onViewBind(ViewHolder holder, ScanResult item, int position) {
    holder.binding.tvName.setText(item.SSID);
  }

  public void clear() {
    mScanResults.clear();
    notifyDataSetChanged();
  }

  public void add(ScanResult item) {
    mScanResults.add(item);
    notifyDataSetChanged();
  }

  public void update(List<ScanResult> items) {
    if (items == null || items.isEmpty()) {
      clear();
      return;
    }
    mScanResults.clear();
    mScanResults.addAll(items);
    notifyDataSetChanged();
  }

  private class ViewHolder {
    private final ItemChooseWifiBinding binding;
    ViewHolder(ItemChooseWifiBinding binding) {
      this.binding = binding;
    }

    public void bind(ScanResult item) {
      binding.executePendingBindings();
    }
  }
}