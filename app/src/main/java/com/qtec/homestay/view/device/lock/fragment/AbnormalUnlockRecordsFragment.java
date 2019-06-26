package com.qtec.homestay.view.device.lock.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.qtec.homestay.R;
import com.qtec.homestay.databinding.FragmentAbnormalUnlockRecordsBinding;
import com.qtec.homestay.domain.model.mapp.rsp.GetDevsResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetUnlockRecordsResponse;
import com.qtec.homestay.internal.di.components.LockComponent;
import com.qtec.homestay.domain.exception.ErrorBundle;
import com.qtec.homestay.view.device.activity.DeviceRecordPresenter;
import com.qtec.homestay.view.device.activity.DeviceRecordView;
import com.qtec.homestay.view.device.data.Lock;
import com.qtec.homestay.view.device.lock.UnlockRecordsActivity;
import com.qtec.homestay.view.device.lock.adapter.LockRecordListAdapter;
import com.qtec.homestay.view.fragment.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * <pre>
 *     author : wusj
 *     e-mail :
 *     time   : 2018/08/13
 *     desc   : AbnormalUnlockRecordsFragment
 * </pre>
 */
public class AbnormalUnlockRecordsFragment extends BaseFragment implements DeviceRecordView {

  private static final String TAG = AbnormalUnlockRecordsFragment.class.getName();

  @Inject
  DeviceRecordPresenter mDeviceRecordsPresenter;

  private FragmentAbnormalUnlockRecordsBinding mBinding;
  private LockRecordListAdapter mAdapter;

  private String mLimit = "-1";

  public static AbnormalUnlockRecordsFragment newInstance() {
    Bundle args = new Bundle();
    AbnormalUnlockRecordsFragment fragment = new AbnormalUnlockRecordsFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getComponent(LockComponent.class).inject(this);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_abnormal_unlock_records, container, false);
    return mBinding.getRoot();
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initPresenter();
    initView();

    getRecords();
  }

  private void getRecords() {
    mDeviceRecordsPresenter.getUnlockRecords(lock().getId(), 10, mLimit, "1");
  }

  private void initPresenter() {
    mDeviceRecordsPresenter.setView(this);
  }

  private void initView() {
    mBinding.ptrLayout.setLastUpdateTimeRelateObject(this);
    mBinding.ptrLayout.setDurationToClose(200);
    mBinding.ptrLayout.disableWhenHorizontalMove(true);//解决横向滑动冲突
    mBinding.ptrLayout.setPtrHandler(new PtrDefaultHandler2() {
      @Override
      public void onRefreshBegin(PtrFrameLayout frame) {
        frame.postDelayed(() -> {
          mBinding.ptrLayout.refreshComplete();
          mLimit = "-1";
          getRecords();
        }, 1000);
      }

      @Override
      public void onLoadMoreBegin(PtrFrameLayout frame) {
        frame.postDelayed(() -> {
          mBinding.ptrLayout.refreshComplete();
          getRecords();
        }, 1000);
      }

      @Override
      public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
        return super.checkCanDoLoadMore(frame, mBinding.list, footer);
      }

      @Override
      public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return super.checkCanDoRefresh(frame, mBinding.list, header);
      }
    });
    mAdapter = new LockRecordListAdapter(getContext());
    mBinding.list.setAdapter(mAdapter);
  }

  @Override
  public void onError(ErrorBundle bundle) {
    super.onError(bundle);
    mBinding.ptrLayout.refreshComplete();
  }

  private Lock lock() {
    return getMasterActivity().lock();
  }

  private UnlockRecordsActivity getMasterActivity() {
    return (UnlockRecordsActivity) getActivity();
  }

  @Override
  public void showUnlockRecords(List<GetUnlockRecordsResponse> responses) {
    mBinding.ptrLayout.refreshComplete();
    if ("-1".equals(mLimit)) {
      mAdapter.update(responses);
    } else {
      mAdapter.add(responses);
    }
    if (responses == null || responses.isEmpty()) return;
    GetUnlockRecordsResponse lastRecord = responses.get(responses.size() - 1);
    mLimit = lastRecord.getRecordUniqueKey();
  }
}