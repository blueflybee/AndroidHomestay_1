package com.qtec.homestay.view.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;

import com.blueflybee.titlebarlib.widget.TitleBar;
import com.qtec.homestay.R;
import com.qtec.homestay.data.constant.PrefConstant;
import com.qtec.homestay.databinding.ActivitySearchRoomBinding;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.domain.model.core.QtecEncryptInfo;
import com.qtec.homestay.domain.model.mapp.req.LeaveHotelRequest;
import com.qtec.homestay.domain.model.mapp.rsp.GetRoomManageResponse;
import com.qtec.homestay.domain.model.mapp.rsp.LeaveHotelResponse;
import com.qtec.homestay.internal.di.components.DaggerLodgeComponent;
import com.qtec.homestay.internal.di.modules.LodgeModule;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.utils.DialogUtil;
import com.qtec.homestay.utils.StringUtil;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.lodge.CheckInRoomActivity;
import com.qtec.homestay.view.lodge.GetRoomInfoListPresenter;
import com.qtec.homestay.view.lodge.LeaveHotelPresenter;
import com.qtec.homestay.view.lodge.LeaveHotelSuccessActivity;
import com.qtec.homestay.view.lodge.LeaveHotelView;
import com.qtec.homestay.view.lodge.RoomManageView;
import com.qtec.homestay.view.main.adapter.FragmentHomeAdapter;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * <pre>
 *     author : wusj
 *     e-mail :
 *     time   : 2018/08/06
 *     desc   : SearchRoomActivity
 * </pre>
 */
public class SearchRoomActivity extends BaseActivity implements RoomManageView, LeaveHotelView {

  private static final String TAG = SearchRoomActivity.class.getName();
  private FragmentHomeAdapter mAdapter;

  @Inject
  GetRoomInfoListPresenter mGetRoomInfoListPresenter;

  @Inject
  LeaveHotelPresenter mLeaveHotelPresenter;

  private ActivitySearchRoomBinding mBinding;
  private List<GetRoomManageResponse> mRooms;
  private List<GetRoomManageResponse> mMatchedRooms = new ArrayList<>();
  private String mRoomNo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_room);
    initializeInjector();
    initPresenter();
    initData();
    initView();

    mGetRoomInfoListPresenter.getRoomInfoList(1000, "-1");
  }

  private void initializeInjector() {
    DaggerLodgeComponent.builder()
        .applicationComponent(getApplicationComponent())
        .activityModule(getActivityModule())
        .lodgeModule(new LodgeModule())
        .build()
        .inject(this);
  }

  private void initPresenter() {
    mGetRoomInfoListPresenter.setView(this);
    mLeaveHotelPresenter.setView(this);
  }

  private void initData() {
//    mRooms = getData();
  }

  private void initView() {
    initTitleBar();

    mAdapter = new FragmentHomeAdapter(getContext());
    mAdapter.setOnCheckClickListener(new FragmentHomeAdapter.OnCheckClickListener() {
      @Override
      public void onCheckIn(GetRoomManageResponse item) {
        Intent intent = new Intent();
        intent.putExtra("RoomNo", item.getRoomNo());
        intent.putExtra("DeviceSerialNo", item.getDeviceSerialNo());
        mNavigator.navigateTo(getContext(), CheckInRoomActivity.class, intent);
      }

      @Override
      public void onCheckOut(GetRoomManageResponse item) {
        DialogUtil.showOkCancelDialog(getContext(), "离店", StringUtil.getCheckOutInfo(item), new DialogUtil.OnConfirmListener() {
          @Override
          public void onConfirm(View view) {
            mRoomNo = item.getRoomNo();
            mLeaveHotelPresenter.checkOut(item.getDeviceSerialNo());
          }
        });
      }
    });
    mBinding.listRoom.setAdapter(mAdapter);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mGetRoomInfoListPresenter != null) {
      mGetRoomInfoListPresenter.destroy();
    }
    mMatchedRooms.clear();
  }

  @Override
  protected void initTitleBar() {
    if (mTitleBar == null) {
      mTitleBar = (TitleBar) findViewById(R.id.title_bar);
    }
    EditText searchEditText = mTitleBar.getCenterSearchEditText();
    searchEditText.setTextColor(getResources().getColor(R.color.black_333333));
    searchEditText.setHintTextColor(getResources().getColor(R.color.gray_bdbdbd));
    searchEditText.setHint("搜索");
    searchEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
    mTitleBar.getCenterSearchRightImageView().setImageResource(R.mipmap.add_wrong);
    mTitleBar.getCenterSearchLeftImageView().setImageResource(R.mipmap.search_ic);
    mTitleBar.setListener((view, action, extra) -> {
      if (action == TitleBar.ACTION_RIGHT_TEXT) {
        finish();
      } else if (action == TitleBar.ACTION_SEARCH_DELETE) {
        searchEditText.setText("");
      }
    });
    mTitleBar.getCenterSearchEditText().addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        searchRoom(s);
      }
    });
  }

  private void searchRoom(Editable s) {
    String input = s.toString();
    if (TextUtils.isEmpty(input) || mRooms == null || mRooms.isEmpty()) {
      showNoRoomInfo();
      return;
    }
    mMatchedRooms.clear();
    for (GetRoomManageResponse room : mRooms) {
      if (!isMatch(room.getRoomNo(), input)
          && !isMatch(room.getResidentName(), input)
          && !isMatch(room.getStartTime(), input)
          && !isMatch(room.getEndTime(), input)) continue;
      mMatchedRooms.add(room);
    }
    if (mMatchedRooms.isEmpty()) {
      showNoRoomInfo();
      return;
    }
    showMatchedRooms();
  }

  private boolean isMatch(String content, String input) {
    return !TextUtils.isEmpty(content) && content.contains(input);
  }

  @Override
  public void onError(ErrorBundle bundle) {
    super.onError(bundle);
  }

  @Override
  public void showRoomInfo(List<GetRoomManageResponse> response) {
    mRooms = response;
  }

  @Override
  public void showNoRoomInfo() {
    mAdapter.clear();
    mBinding.listRoom.setVisibility(View.GONE);
    mBinding.tvNoResult.setVisibility(View.VISIBLE);
  }

  private void showMatchedRooms() {
    mBinding.listRoom.setVisibility(View.VISIBLE);
    mBinding.tvNoResult.setVisibility(View.GONE);
    mAdapter.update(mMatchedRooms);
  }

  private List<GetRoomManageResponse> getData() {
    ArrayList<GetRoomManageResponse> mData = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      GetRoomManageResponse response = new GetRoomManageResponse();
      response.setBatteryPower(i + "");
      response.setLockPass("12345" + i);
      response.setRoomNo(i + "");
      response.setResidentName("客户" + i);
      response.setStartTime("2018/07/04");
      response.setEndTime("2018/07-05");
      response.setResidentStatus(i % 2 == 0 ? "0" : "1");
      mData.add(response);
    }
    return mData;
  }

  @Override
  public void onCheckOut(LeaveHotelResponse response) {
    Intent intent = new Intent();
    intent.putExtra(Navigator.EXTRA_ROOM_NO, mRoomNo);
    mNavigator.navigateTo(getContext(), LeaveHotelSuccessActivity.class, intent);
  }
}