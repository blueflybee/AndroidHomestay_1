/**
 *
 */
package com.qtec.homestay.utils;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.ScanResult;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blueflybee.titlebarlib.widget.TitleBar;
import com.qtec.homestay.R;
import com.qtec.homestay.databinding.DialogAppVersionUpdateBinding;
import com.qtec.homestay.databinding.DialogChooseRouterBinding;
import com.qtec.homestay.databinding.DialogOkBinding;
import com.qtec.homestay.databinding.DialogOkCancelBinding;
import com.qtec.homestay.databinding.DialogOneBtnBinding;
import com.qtec.homestay.databinding.DialogOpenBleBinding;
import com.qtec.homestay.databinding.DialogZigbeeNetworkBinding;
import com.qtec.homestay.domain.model.mapp.rsp.GetDevsResponse;
import com.qtec.homestay.view.component.CustomProgressDialog;
import com.qtec.homestay.view.device.adapter.ChoseWifiAdapter;
import com.qtec.homestay.view.device.adapter.SelectRouterListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Dialog 工具类
 */
public class DialogUtil {

  private static Dialog mProgressDialog = null;

  /**
   * 显示进度对话框（可取消）
   */
  public static void showProgress(Context context) {
    try {
      hideProgress();
      mProgressDialog = CustomProgressDialog.createDialog(context);
      mProgressDialog.setCancelable(true);
      mProgressDialog.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void hideProgress() {
    try {
      if (mProgressDialog != null) mProgressDialog.dismiss();
      mProgressDialog = null;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void showChooseWifiDialog(Context context, OnWifiChooseListener listener) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    View view = LayoutInflater.from(context).inflate(R.layout.dialog_choose_wifi, null);
    builder.setView(view);
    AlertDialog dialog = builder.create();
    ListView listView = view.findViewById(R.id.list_wifi);
    ChoseWifiAdapter adapter = new ChoseWifiAdapter(context, WifiUtil.getWifis(context));
    listView.setAdapter(adapter);
    listView.setOnItemClickListener((parent, view12, position, id) -> {
      if (listener != null) {
        listener.onChoose(adapter.getItem(position));
      }
      dialog.dismiss();
    });

    TitleBar titleBar = view.findViewById(R.id.title_bar);
    titleBar.getLeftTextView().setVisibility(View.GONE);
    titleBar.getCenterTextView().setText("选择WiFi");
    titleBar.getRightTextView().setText("刷新");
    titleBar.getRightTextView().setTextColor(context.getResources().getColor(R.color.green_00beaf));
    titleBar.setListener((view1, action, s) -> {
      if (action == TitleBar.ACTION_RIGHT_TEXT) {
        adapter.update(WifiUtil.getWifis(context));
      }
    });

    builder.setCancelable(true);
    dialog.show();
    int screenHeight = ScreenUtils.getScreenHeight();
    dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, screenHeight * 3 / 4);
  }

  public interface OnWifiChooseListener {
    void onChoose(ScanResult wifi);
  }

  public static void showOpenBleDialog(Context context, OnConfirmListener listener) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    LayoutInflater inflater = LayoutInflater.from(context);
    DialogOpenBleBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_open_ble, null, false);
    builder.setView(binding.getRoot());
    AlertDialog dialog = builder.create();
    binding.tvCancel.setOnClickListener(v -> {
      dialog.dismiss();
    });
    binding.tvOpen.setOnClickListener(v -> {
      if (listener != null) {
        listener.onConfirm(v);
      }
      dialog.dismiss();
    });
    builder.setCancelable(false);
    dialog.setCanceledOnTouchOutside(false);
    dialog.show();
  }

  public interface OnRouterSelectListener {
    void onRouterSelect(GetDevsResponse router);
  }

  public static void showChooseRouterDialog(Context context, List<GetDevsResponse> responses, OnRouterSelectListener listener) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    LayoutInflater inflater = LayoutInflater.from(context);
    DialogChooseRouterBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_choose_router, null, false);
    builder.setView(binding.getRoot());
    AlertDialog dialog = builder.create();
    binding.tvCancel.setOnClickListener(v -> {
      dialog.dismiss();
    });
    binding.tvOk.setOnClickListener(v -> {
      GetDevsResponse router = (GetDevsResponse) v.getTag();
      if (router == null) {
        ToastUtils.showShort("请先选择一台网关");
        return;
      }
      if (listener != null) {
        listener.onRouterSelect(router);
      }
      dialog.dismiss();
    });
    SelectRouterListAdapter adapter = new SelectRouterListAdapter(context, responses);
    binding.list.setAdapter(adapter);
    binding.list.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
    binding.list.setOnItemClickListener((parent, view, position, id) -> {
      binding.tvOk.setTag(adapter.getItem(position));
    });
    builder.setCancelable(false);
    dialog.setCanceledOnTouchOutside(false);
    dialog.show();
    int screenHeight = ScreenUtils.getScreenHeight();
    dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, screenHeight * 3 / 4);
  }

  private static List<GetDevsResponse> getRouters() {
    ArrayList<GetDevsResponse> devsResponses = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      GetDevsResponse dev = new GetDevsResponse();
      dev.setDeviceName("安全网关00" + i);
      devsResponses.add(dev);
    }
    return devsResponses;
  }

  public static AlertDialog showGetLockInfoDialog(Context context, OnConfirmListener listener) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    LayoutInflater inflater = LayoutInflater.from(context);
    DialogOneBtnBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_one_btn, null, false);
    builder.setView(binding.getRoot());
    AlertDialog dialog = builder.create();
    binding.tvConfirm.setOnClickListener(v -> {
      if (listener != null) {
        listener.onConfirm(v);
      }
      dialog.dismiss();
    });
    builder.setCancelable(false);
    dialog.setCanceledOnTouchOutside(true);
    dialog.show();
    return dialog;
  }

  public static void showBindLockDialog(Context context, OnConfirmListener listener) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    LayoutInflater inflater = LayoutInflater.from(context);
    DialogOneBtnBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_one_btn, null, false);
    builder.setView(binding.getRoot());
    AlertDialog dialog = builder.create();

    binding.tvTitle.setText("门锁未绑定");
    binding.tvContent.setText("检查设备和网络后再尝试连接");
    binding.tvConfirm.setText("再次尝试");
    binding.tvConfirm.setOnClickListener(v -> {
      if (listener != null) {
        listener.onConfirm(v);
      }
      dialog.dismiss();
    });
    builder.setCancelable(false);
    dialog.setCanceledOnTouchOutside(true);
    dialog.show();
  }

  public static void showUpdateDialog(Context context, boolean forceUpdate, String content, OnConfirmListener listener) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    LayoutInflater inflater = LayoutInflater.from(context);
    DialogAppVersionUpdateBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_app_version_update, null, false);
    builder.setView(binding.getRoot());
    AlertDialog dialog = builder.create();
    binding.ivClose.setOnClickListener(v -> {
      dialog.dismiss();
    });
    binding.ivClose.setVisibility(forceUpdate ? View.INVISIBLE : View.VISIBLE);
    binding.btnUpdate.setOnClickListener(v -> {
      if (listener != null) {
        listener.onConfirm(v);
      }
      dialog.dismiss();
    });
    binding.tvVersionInfo.setText(content);
    dialog.setCancelable(!forceUpdate);
    dialog.setCanceledOnTouchOutside(!forceUpdate);
    dialog.show();
//    int screenHeight = ScreenUtils.getScreenHeight();
//    dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, screenHeight * 3 / 4);
    dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
  }

  public static void showOkCancelDialog(Context context, String title, String content, OnConfirmListener listener) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    LayoutInflater inflater = LayoutInflater.from(context);
    DialogOkCancelBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_ok_cancel, null, false);
    builder.setView(binding.getRoot());
    AlertDialog dialog = builder.create();
    binding.tvTitle.setText(title);
    binding.tvContent.setText(content);
    binding.tvCancel.setOnClickListener(v -> {
      dialog.dismiss();
    });
    binding.tvOk.setOnClickListener(v -> {
      if (listener != null) {
        listener.onConfirm(v);
      }
      dialog.dismiss();
    });
    builder.setCancelable(false);
    dialog.setCanceledOnTouchOutside(false);
    dialog.show();
  }

  public static void showOkDialog(Context context, String title, String content, OnConfirmListener listener) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    LayoutInflater inflater = LayoutInflater.from(context);
    DialogOkBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_ok, null, false);
    builder.setView(binding.getRoot());
    AlertDialog dialog = builder.create();
    binding.tvTitle.setText(title);
    binding.tvContent.setText(content);
    binding.tvOk.setOnClickListener(v -> {
      if (listener != null) {
        listener.onConfirm(v);
      }
      dialog.dismiss();
    });
    dialog.setCancelable(false);
    dialog.setCanceledOnTouchOutside(false);
    dialog.show();
  }

  public static void showZigbeeNetDialog(Context context, String title, String content, long time, QueryBindFinishedCallback callback) {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    LayoutInflater inflater = LayoutInflater.from(context);
    DialogZigbeeNetworkBinding binding = DataBindingUtil.inflate(inflater, R.layout.dialog_zigbee_network, null, false);
    builder.setView(binding.getRoot());
    AlertDialog dialog = builder.create();
    binding.tvTitle.setText(title);
    binding.tvContent.setText(content);
    dialog.setCancelable(false);
    dialog.setCanceledOnTouchOutside(false);
    dialog.show();
    new CountDownTimer(time, 60) {
      @Override
      public void onTick(long millisUntilFinished) {
        int progress = (int) (((time - millisUntilFinished) / 1000.0) * 100.0 / (time / 1000.0));
        binding.progress.setProgress(progress);
      }

      @Override
      public void onFinish() {
        binding.progress.setProgress(100);
        dialog.dismiss();
        callback.onQueryFinished();
      }
    }.start();
  }

  public interface QueryBindFinishedCallback {
    void onQueryFinished();
  }

  public interface OnCancelListener {
    void onCancel(View view);
  }

  public interface OnConfirmListener {
    void onConfirm(View view);
  }

}
