package com.qtec.homestay.view.device.lock.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ItemLockRecordListBinding;
import com.qtec.homestay.domain.model.mapp.rsp.GetUnlockRecordsResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : wusj
 *     e-mail :
 *     time   : 2018/08/13
 *     desc   : LockRecordListAdapter
 * </pre>
 */
public class LockRecordListAdapter extends BaseAdapter {

  private static final String TAG = LockRecordListAdapter.class.getName();

  private Context mContext;
  private List<GetUnlockRecordsResponse> mRecordsResponses = new ArrayList<>();

  public LockRecordListAdapter(@NonNull Context context) {
    mContext = context;
  }

  public LockRecordListAdapter(@NonNull Context context, List<GetUnlockRecordsResponse> data) {
    mContext = context;
    if (data == null || data.isEmpty()) return;
    this.mRecordsResponses = data;
  }

  @Override
  public int getCount() {
    return mRecordsResponses.size();
  }

  @Override
  public GetUnlockRecordsResponse getItem(int position) {
    return mRecordsResponses.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
    GetUnlockRecordsResponse item = getItem(position);
    if (convertView == null) {
      LayoutInflater inflater = LayoutInflater.from(mContext);
      ItemLockRecordListBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_lock_record_list, parent, false);
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

  private void onViewBind(ViewHolder holder, GetUnlockRecordsResponse item, int position) {
    ItemLockRecordListBinding binding = holder.binding;
    binding.tvName.setText(item.getUserName());
    binding.tvTime.setText(item.getOccurTime());
    binding.tvRecordContent.setText(item.getMessage());
    Resources resources = mContext.getResources();
    binding.tvRecordContent.setTextColor("0".equals(item.getIsAlarm()) ?
        resources.getColor(R.color.gray_999999) : resources.getColor(R.color.red_f44336));
  }

  public void clear() {
    mRecordsResponses.clear();
    notifyDataSetChanged();
  }

  public void add(GetUnlockRecordsResponse item) {
    mRecordsResponses.add(item);
    notifyDataSetChanged();
  }

  public void add(List<GetUnlockRecordsResponse> items) {
    if (items == null || items.isEmpty()) return;
    mRecordsResponses.addAll(items);
    notifyDataSetChanged();
  }

  public void update(List<GetUnlockRecordsResponse> items) {
    if (items == null || items.isEmpty()) {
      clear();
      return;
    }
    mRecordsResponses.clear();
    mRecordsResponses.addAll(items);
    notifyDataSetChanged();
  }

  private class ViewHolder {
    private final ItemLockRecordListBinding binding;
    ViewHolder(ItemLockRecordListBinding binding) {
      this.binding = binding;
    }

    public void bind(GetUnlockRecordsResponse item) {
      binding.executePendingBindings();
    }
  }
}