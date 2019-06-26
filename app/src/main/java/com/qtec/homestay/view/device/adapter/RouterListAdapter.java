package com.qtec.homestay.view.device.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ItemRouterListBinding;
import com.qtec.homestay.domain.model.mapp.rsp.GetDevsResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : wusj
 *     e-mail :
 *     time   : 2018/08/02
 *     desc   : RouterListAdapter
 * </pre>
 */
public class RouterListAdapter extends BaseAdapter {

  private static final String TAG = RouterListAdapter.class.getName();

  private Context mContext;
  private List<GetDevsResponse> mGetDevsResponses = new ArrayList<>();

  public RouterListAdapter(@NonNull Context context) {
    mContext = context;
  }

  public RouterListAdapter(@NonNull Context context, List<GetDevsResponse> data) {
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
      ItemRouterListBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_router_list, parent, false);
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

  private class ViewHolder {
    private final ItemRouterListBinding binding;
    ViewHolder(ItemRouterListBinding binding) {
      this.binding = binding;
    }

    public void bind(GetDevsResponse item) {
      binding.executePendingBindings();
    }
  }
}