package com.qtec.homestay.view.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.reflect.TypeToken;
import com.qtec.homestay.R;
import com.qtec.homestay.data.constant.PrefConstant;
import com.qtec.homestay.databinding.FragmentHomeBinding;
import com.qtec.homestay.domain.model.mapp.rsp.GetRoomManageResponse;
import com.qtec.homestay.domain.model.mapp.rsp.LeaveHotelResponse;
import com.qtec.homestay.internal.di.components.MainComponent;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.utils.BeanConvertUtil;
import com.qtec.homestay.utils.DialogUtil;
import com.qtec.homestay.utils.StringUtil;
import com.qtec.homestay.view.component.popup.CommonPopupWindow;
import com.qtec.homestay.view.device.activity.SelectDevTypeActivity;
import com.qtec.homestay.view.device.data.Lock;
import com.qtec.homestay.view.device.lock.LockActivity;
import com.qtec.homestay.view.fragment.BaseFragment;
import com.qtec.homestay.view.lodge.CheckInRoomActivity;
import com.qtec.homestay.view.lodge.GetRoomInfoListPresenter;
import com.qtec.homestay.view.lodge.LeaveHotelPresenter;
import com.qtec.homestay.view.lodge.LeaveHotelSuccessActivity;
import com.qtec.homestay.view.lodge.LeaveHotelView;
import com.qtec.homestay.view.lodge.ResidentDetailActivity;
import com.qtec.homestay.view.lodge.RoomManageView;
import com.qtec.homestay.view.main.adapter.FragmentHomeAdapter;
import com.qtec.homestay.view.main.data.Address;
import com.qtec.homestay.view.main.data.AddressRequest;
import com.qtec.homestay.view.main.data.Weather;
import com.qtec.homestay.view.main.data.WeatherRequest;
import com.qtec.homestay.view.main.task.HttpGetCallback;
import com.qtec.homestay.view.main.task.HttpGetTask;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

/**
 * <pre>
 *      author: wusj
 *      e-mail: wusj@qtec.cn
 *      time: 2017/06/08
 *      desc:
 *      version: 1.0
 * </pre>
 */
public class HomeFragment extends BaseFragment implements RoomManageView, LeaveHotelView {
  public static final String ADDRESS_NAME = "address_name";
  public static final String TEMPERATURE = "temperature";
  public static final String WEATHER_TEXT = "weather_text";
  private CommonPopupWindow popupWindow;
  private FragmentHomeBinding mBinding;
  private FragmentHomeAdapter mAdapter;
  private String roomNo;

  @Inject
  GetRoomInfoListPresenter mGetRoomInfoListPresenter;

  @Inject
  LeaveHotelPresenter mLeaveHotelPresenter;
  private SPUtils mSPUtils;

  public static HomeFragment newInstance() {
    Bundle args = new Bundle();
    HomeFragment fragment = new HomeFragment();
    fragment.setArguments(args);

    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getComponent(MainComponent.class).inject(this);
    mSPUtils = SPUtils.getInstance(PrefConstant.SP_NAME);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
    return mBinding.getRoot();
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initPresenter();
    initLocation();
    initView();
  }

  @SuppressLint("MissingPermission")
  private void initLocation() {
    LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 15000, 100, new LocationListener() {
      @Override
      public void onLocationChanged(Location location) {
        queryAddress(location);
      }

      @Override
      public void onStatusChanged(String provider, int status, Bundle extras) {
        System.out.println("HomeFragment.onStatusChanged");
      }

      @Override
      public void onProviderEnabled(String provider) {
        System.out.println("HomeFragment.onProviderEnabled");
      }

      @Override
      public void onProviderDisabled(String provider) {
        System.out.println("HomeFragment.onProviderDisabled");
      }
    });
  }

  private void queryAddress(Location location) {
//    System.out.println("location = [" + location + "]");
    AddressRequest request = new AddressRequest();
    request.setL(location.getLatitude() + "," + location.getLongitude());
    request.setType("100");
    Type type = new TypeToken<Address>() {
    }.getType();
    new HttpGetTask<>(type, new HttpGetCallback<Address>() {
      @Override
      public void onFail(String result) {
      }

      @Override
      public void onSuccess(Address address) {
        List<Address.AddrListBean> addrList = address.getAddrList();
        if (addrList != null && !addrList.isEmpty()) {
          String admName = addrList.get(0).getAdmName();
          mSPUtils.put(ADDRESS_NAME, admName);
          queryWeather(admName);
          mBinding.tvAddress.setText(admName.replace(",", " "));
        }
      }
    }).execute(request, "http://gc.ditu.aliyun.com/regeocoding?");
  }

  private void queryWeather(String admName) {
    if (TextUtils.isEmpty(admName)) return;
    String[] addresses = admName.split(",");
    if (addresses.length == 0) return;
    WeatherRequest request = new WeatherRequest();
    request.setCity(addresses[addresses.length - 1]);
    Type type = new TypeToken<Weather>() {
    }.getType();
    new HttpGetTask<>(type, new HttpGetCallback<Weather>() {
      @Override
      public void onFail(String result) {
      }

      @Override
      public void onSuccess(Weather weather) {
//        System.out.println("weather = " + weather);
        String wendu = weather.getData().getWendu();
        mSPUtils.put(TEMPERATURE, wendu);
        mBinding.tvTemperature.setText(wendu);
        mBinding.tvUnit.setText("℃");
        List<Weather.DataBean.ForecastBean> forecast = weather.getData().getForecast();
        if (forecast == null || forecast.isEmpty()) return;
        Weather.DataBean.ForecastBean forecastBean = forecast.get(0);
        String weatherText = forecastBean.getType() + "  " + forecastBean.getLow() + "~" + forecastBean.getHigh();
        mSPUtils.put(WEATHER_TEXT, weatherText);
        mBinding.tvWeather.setText(weatherText);

      }
    }).execute(request, "http://wthrcdn.etouch.cn/weather_mini?");
  }

  private void initView() {
    mBinding.tvTitle.setText(PrefConstant.getUserNickName() + "的民宿");
    String addressName = mSPUtils.getString(ADDRESS_NAME);
    if (!TextUtils.isEmpty(addressName)) {
      mBinding.tvAddress.setText(addressName.replace(",", " "));
    }
    String wendu = mSPUtils.getString(TEMPERATURE);
    if (!TextUtils.isEmpty(wendu)) {
      mBinding.tvTemperature.setText(wendu);
      mBinding.tvUnit.setText("℃");
    }

    String weatherText = mSPUtils.getString(WEATHER_TEXT);
    if (!TextUtils.isEmpty(weatherText)) {
      mBinding.tvWeather.setText(weatherText);
    }
    mBinding.ivAdd.setOnClickListener(v -> {
      showDownPop(mBinding.ivAdd, -360, 20);
    });

    mBinding.ivSearch.setOnClickListener(v -> {
      mNavigator.navigateTo(getContext(), SearchRoomActivity.class);
    });

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
        DialogUtil.showOkCancelDialog(getContext(), "离店", StringUtil.getCheckOutInfo(item), view -> {
          roomNo = item.getRoomNo();
          mLeaveHotelPresenter.checkOut(item.getDeviceSerialNo());
        });

      }
    });
    mBinding.listGuest.setAdapter(mAdapter);
    mBinding.scrollGuest.smoothScrollTo(0, 20);

    mBinding.listGuest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GetRoomManageResponse item = mAdapter.getItem(position);
        Lock lock = BeanConvertUtil.getLock(item);
        if ("0".equals(item.getResidentStatus())) {
          Intent intent = new Intent();
          intent.putExtra(Navigator.EXTRA_DEVICE, lock);
          mNavigator.navigateTo(getContext(), LockActivity.class, intent);
          return;
        }
        if  ("1".equals(item.getResidentStatus())){
          Intent intent = new Intent();
          intent.putExtra(Navigator.EXTRA_RESIDENT, item);
          mNavigator.navigateTo(getContext(), ResidentDetailActivity.class, intent);
          return;
        }
      }
    });

    mBinding.tvAddDev.setOnClickListener(v -> {
      mNavigator.navigateTo(getContext(), SelectDevTypeActivity.class);
    });
  }

  private void initPresenter() {
    mGetRoomInfoListPresenter.setView(this);
    mLeaveHotelPresenter.setView(this);
  }

  //向下弹出
  private void showDownPop(View view, int xOff, int yOff) {
    if (popupWindow != null && popupWindow.isShowing()) return;
    popupWindow = new CommonPopupWindow.Builder(getActivity())
        .setView(R.layout.pop_home_add)
        .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        .setAnimationStyle(R.style.AnimDown)
        .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
          @Override
          public void getChildView(View view, int layoutResId) {
            view.findViewById(R.id.tv_add_dev).setOnClickListener(v -> {
              mNavigator.navigateTo(getContext(), SelectDevTypeActivity.class);
              popupWindow.dismiss();
            });

            view.findViewById(R.id.tv_check_in).setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("RoomNo", "");
                intent.putExtra("DeviceSerialNo", "");
                mNavigator.navigateTo(getContext(), CheckInRoomActivity.class, intent);
                popupWindow.dismiss();
              }
            });


          }
        })
        .setOutsideTouchable(true)
        .create();
    popupWindow.showAsDropDown(view, xOff, yOff);
    //得到button的左上角坐标
//    int[] positions = new int[2];
//    view.getLocationOnScreen(positions);
//    System.out.println("positions[0] = " + positions[0]);
//    System.out.println("positions[1] = " + positions[1]);
//    int height = view.getHeight();
//    System.out.println("height = " + height);
//    popupWindow.showAsDropDown(view, -400, 20);
  }

  @Override
  public void showRoomInfo(List<GetRoomManageResponse> response) {
    mBinding.listGuest.setVisibility(View.VISIBLE);
    mBinding.tvAddDev.setVisibility(View.GONE);
    mAdapter.update(response);
  }

  @Override
  public void showNoRoomInfo() {
    mBinding.listGuest.setVisibility(View.GONE);
    mBinding.tvAddDev.setVisibility(View.VISIBLE);
    mAdapter.clear();
  }

  @Override
  public void onResume() {
    super.onResume();
    mGetRoomInfoListPresenter.getRoomInfoList(1000, "-1");
  }

  @Override
  public void onCheckOut(LeaveHotelResponse response) {
    Intent intent = new Intent();
    intent.putExtra(Navigator.EXTRA_ROOM_NO, roomNo);
    mNavigator.navigateTo(getContext(), LeaveHotelSuccessActivity.class, intent);
  }
}
