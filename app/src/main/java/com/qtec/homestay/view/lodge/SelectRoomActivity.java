package com.qtec.homestay.view.lodge;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.blueflybee.titlebarlib.widget.TitleBar;
import com.qtec.homestay.R;
import com.qtec.homestay.databinding.ActivitySelectRoomBinding;
import com.qtec.homestay.domain.model.mapp.rsp.SelectRoomResponse;
import com.qtec.homestay.internal.di.components.DaggerLodgeComponent;
import com.qtec.homestay.internal.di.modules.LodgeModule;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.view.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * <pre>
 *      author: shaojun
 *      e-mail: wusj@qtec.cn
 *      time: 2017/06/22
 *      desc:
 *      version: 1.0
 * </pre>
 */
public class SelectRoomActivity extends BaseActivity implements AdapterView.OnItemClickListener, SelectRoomView {
  private ActivitySelectRoomBinding mBinding;
  private SelectRoomListAdapter adapter;
  private List<SelectRoomResponse> roomsList;
  private int checkedItemPosition = 0;

  @Inject
  SelectRoomListPresenter mPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_select_room);

    initializeInjector();

    initPresenter();

    initView();

    mPresenter.selectRoomList();
  }

  private void initPresenter() {
    mPresenter.setView(this);
  }

  private void initializeInjector() {
    DaggerLodgeComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .lodgeModule(new LodgeModule())
        .build()
        .inject(this);
  }

  private void initView() {
    roomsList = new ArrayList<>();

    mBinding.titleBar.setListener(new TitleBar.OnTitleBarListener() {
      @Override
      public void onClicked(View view, int action, String s) {
        if (action == TitleBar.ACTION_LEFT_TEXT) {
          //

        } else if (action == TitleBar.ACTION_RIGHT_TEXT) {
          Intent intent = new Intent();
          intent.putExtra(Navigator.EXTRA_SELECT_ROOM, (SelectRoomResponse) adapter.getItem(checkedItemPosition));
          setResult(2, intent);
          finish();
        }
      }
    });
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    checkedItemPosition = mBinding.lvRoom.getCheckedItemPosition();

    ((ImageView) view.findViewById(R.id.img_chooseLogo)).setBackground(getResources().getDrawable(R.mipmap.router_pick));
    adapter.initOtherItem(checkedItemPosition);
  }

  @Override
  public void getRoomList(List<SelectRoomResponse> response) {

    roomsList.clear();
    roomsList.addAll(response);

    adapter = new SelectRoomListAdapter(this, roomsList, R.layout.item_select_room, checkedItemPosition);
    mBinding.lvRoom.setAdapter(adapter);
    mBinding.lvRoom.setOnItemClickListener(this);
  }

  @Override
  public void showNoRoom() {
    ToastUtils.showShort("暂时没有数据");
  }

}