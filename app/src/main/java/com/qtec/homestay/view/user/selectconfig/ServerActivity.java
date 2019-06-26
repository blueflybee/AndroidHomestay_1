package com.qtec.homestay.view.user.selectconfig;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;

import com.blueflybee.titlebarlib.widget.TitleBar;
import com.qtec.homestay.R;
import com.qtec.homestay.data.net.CloudRestApi;
import com.qtec.homestay.data.net.CloudRestApiImpl;
import com.qtec.homestay.data.net.ConnectionCreator;
import com.qtec.homestay.data.net.IPostConnection;


public class ServerActivity extends ListActivity {
  private TitleBar mTitleBar;
  private static int sPosition = -1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_server);

    initTitleBar("可选服务器列表");

    String[] servers = {
        "开发环境：" + CloudRestApi.URL_DEVELOP,
        "测试环境：" + CloudRestApi.URL_TEST,
//        "生产环境：" + CloudRestApi.URL_GATEWAY_3_CARETEC,
    };

    setListAdapter(new ArrayAdapter<>(this, R.layout.item_server_list, servers));

    getListView().setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
    getListView().setItemChecked(sPosition, true);


    getListView().setOnItemClickListener((parent, view, position, id) -> {
      sPosition = position;
      String server = servers[position].split("：")[1];
      IPostConnection cloudConnection =
          ConnectionCreator.create(ConnectionCreator.CLOUD_CONNECTION, server);
      CloudRestApiImpl.setApiPostConnection(cloudConnection);

 /*     LoginConfig.OS_IP = urlList.get(position).getIp();//截取云端Ip 安全存储外网访问用
      System.out.println("LoginConfig.OS_IP = " + LoginConfig.OS_IP);*/

    });

  }
  protected void initTitleBar(String title) {
    if (mTitleBar == null) {
      mTitleBar = (TitleBar) findViewById(R.id.title_bar);
    }
    mTitleBar.getCenterTextView().setText(title);
    mTitleBar.setListener((view, action, extra) -> {
      if (action == TitleBar.ACTION_LEFT_TEXT) {
        finish();
      }
    });
  }

}
