package com.qtec.homestay.view.user.mine;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qtec.homestay.R;
import com.qtec.homestay.data.constant.PrefConstant;
import com.qtec.homestay.databinding.FragmentMineBinding;
import com.qtec.homestay.internal.di.components.MainComponent;
import com.qtec.homestay.utils.GlideUtil;
import com.qtec.homestay.utils.StringUtil;
import com.qtec.homestay.view.fragment.BaseFragment;

/**
 * <pre>
 *      author: xiehao
 *      e-mail: xieh@qtec.cn
 *      time: 2017/06/08
 *      desc:
 *      version: 1.0
 * </pre>
 */

public class MineFragment extends BaseFragment {
  private FragmentMineBinding mBinding;

  public static MineFragment newInstance() {
    Bundle args = new Bundle();
    MineFragment fragment = new MineFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getComponent(MainComponent.class).inject(this);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_mine, container, false);
    return mBinding.getRoot();
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initPresenter();
    initView();
  }

  private void initView() {
    mBinding.imgHead.setOnClickListener(v -> mNavigator.navigateTo(getContext(), MyInfoActivity.class));
    mBinding.tvNickName.setText(StringUtil.addStar(PrefConstant.getUserPhone()));
    mBinding.rlFeedBack.setOnClickListener(v -> mNavigator.navigateTo(getContext(), FeedbackActivity.class));
    mBinding.rlAboutUs.setOnClickListener(v -> mNavigator.navigateTo(getContext(), AboutUsActivity.class));
  }

  private void initPresenter() {
//    mGetDeviceCountPresenter.setView(this);
//    mGetRouterListPresenter.setView(this);
  }

  @Override
  public void onResume() {
    super.onResume();
    GlideUtil.loadCircleHeadImage(getContext(), PrefConstant.getUserHeadImg(), mBinding.imgHead, R.mipmap.set_person);
  }
}
