package com.qtec.homestay.view.device.scan.activity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.blankj.utilcode.util.ToastUtils;
import com.blueflybee.titlebarlib.widget.TitleBar;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.qtec.homestay.R;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.view.activity.BaseActivity;
import com.qtec.homestay.view.device.component.ViewfinderView;
import com.qtec.homestay.view.device.data.Device;
import com.qtec.homestay.view.device.router.RouterListActivity;
import com.qtec.homestay.view.device.router.ScanRouterInfoActivity;
import com.qtec.homestay.view.device.scan.camera.CameraManager;
import com.qtec.homestay.view.device.scan.decoding.CaptureActivityHandler;
import com.qtec.homestay.view.device.scan.decoding.InactivityTimer;

import java.io.IOException;
import java.util.Vector;

/**
 * Initial the camera
 *
 * @author Ryan.Tang
 */
public class ScanActivity extends BaseActivity implements Callback {

  private CaptureActivityHandler handler;
  private ViewfinderView viewfinderView;
  private boolean hasSurface;
  private Vector<BarcodeFormat> decodeFormats;
  private String characterSet;
  private InactivityTimer inactivityTimer;
  private MediaPlayer mediaPlayer;
  private boolean playBeep;
  private static final float BEEP_VOLUME = 0.10f;
  private boolean vibrate;

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_scan);
    //ViewUtil.addTopView(getApplicationContext(), this, R.string.scan_card);
    CameraManager.init(getApplication());
    viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);

    /*initTitleBar("扫描二维码");*/

    hasSurface = false;
    inactivityTimer = new InactivityTimer(this);

    initTitleBar("添加网关");

  }

  @Override
  protected void onResume() {
    super.onResume();
    SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
    SurfaceHolder surfaceHolder = surfaceView.getHolder();
    if (hasSurface) {
      initCamera(surfaceHolder);
    } else {
      surfaceHolder.addCallback(this);
      surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }
    decodeFormats = null;
    characterSet = null;

    playBeep = true;
    AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
    if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
      playBeep = false;
    }
    initBeepSound();
    vibrate = true;

  }

  @Override
  protected void onPause() {
    super.onPause();
    if (handler != null) {
      handler.quitSynchronously();
      handler = null;
    }
    CameraManager.get().closeDriver();
  }

  @Override
  protected void onDestroy() {
    inactivityTimer.shutdown();
    super.onDestroy();
  }

  /**
   * 处理扫描结果
   *
   * @param result
   * @param barcode
   */
  public void handleDecode(Result result, Bitmap barcode) {
    inactivityTimer.onActivity();
    playBeepSoundAndVibrate();
    String resultString = result.getText();
    if (resultString.equals("")) {
      ToastUtils.showShort("扫描失败，请重试");
      return;
    }
    String codeText = resultString.replaceFirst("^0*", "");
    System.out.println("codeText = " + codeText);
    Device device = new Device();
    device.setId(codeText);
    device.setName("三点安全网关");
    device.setModel("SD001");
    device.setType(Device.TYPE_ROUTER);
    device.setTypeName("智能锁网关");
    device.setBind(false);
    Intent intent = new Intent();
    intent.putExtra(Navigator.EXTRA_DEVICE, device);
    mNavigator.navigateTo(getContext(), ScanRouterInfoActivity.class, intent);
  }

  private void initCamera(SurfaceHolder surfaceHolder) {
    try {
      CameraManager.get().openDriver(surfaceHolder);
    } catch (IOException ioe) {
      return;
    } catch (RuntimeException e) {
      return;
    }
    if (handler == null) {
      handler = new CaptureActivityHandler(this, decodeFormats,
          characterSet);
    }
  }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
  }

  @Override
  public void surfaceCreated(SurfaceHolder holder) {
    if (!hasSurface) {
      hasSurface = true;
      initCamera(holder);
    }
  }

  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {
    hasSurface = false;
  }

  public ViewfinderView getViewfinderView() {
    return viewfinderView;
  }

  public Handler getHandler() {
    return handler;
  }

  public void drawViewfinder() {
    viewfinderView.drawViewfinder();
  }

  private void initBeepSound() {
    if (playBeep && mediaPlayer == null) {
      // The volume on STREAM_SYSTEM is not adjustable, and users found it
      // too loud,
      // so we now play on the music stream.
      setVolumeControlStream(AudioManager.STREAM_MUSIC);
      mediaPlayer = new MediaPlayer();
      mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
      mediaPlayer.setOnCompletionListener(beepListener);

      AssetFileDescriptor file = getResources().openRawResourceFd(
          R.raw.beep);
      try {
        mediaPlayer.setDataSource(file.getFileDescriptor(),
            file.getStartOffset(), file.getLength());
        file.close();
        mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
        mediaPlayer.prepare();
      } catch (IOException e) {
        mediaPlayer = null;
      }
    }
  }

  private static final long VIBRATE_DURATION = 200L;

  private void playBeepSoundAndVibrate() {
    if (playBeep && mediaPlayer != null) {
      mediaPlayer.start();
    }
    if (vibrate) {
      Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
      vibrator.vibrate(VIBRATE_DURATION);
    }
  }

  /**
   * When the beep has finished playing, rewind to queue up another one.
   */
  private final OnCompletionListener beepListener = new OnCompletionListener() {
    public void onCompletion(MediaPlayer mediaPlayer) {
      mediaPlayer.seekTo(0);
    }
  };

  @Override
  protected void initTitleBar(String title) {
    super.initTitleBar(title);
    mTitleBar.getRightTextView().setText("网关列表");
    mTitleBar.setListener((view, action, extra) -> {
      if (action == TitleBar.ACTION_LEFT_TEXT) {
        finish();
      } else if (action == TitleBar.ACTION_RIGHT_TEXT) {
        mNavigator.navigateTo(getContext(), RouterListActivity.class);
      }
    });
  }
}