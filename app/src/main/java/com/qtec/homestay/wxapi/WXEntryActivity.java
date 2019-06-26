package com.qtec.homestay.wxapi;

import android.content.Intent;
import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.qtec.homestay.AndroidApplication;
import com.qtec.homestay.data.constant.PrefConstant;
import com.qtec.homestay.view.activity.BaseActivity;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import java.net.URLEncoder;

/**
 * <pre>
 *     author : shaojun
 *     e-mail : wusj@qtec.cn
 *     time   : 2018/08/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
  private IWXAPI mWXApi;
  private BaseResp resp = null;
  private String WX_APP_ID = "创建应用后得到的APP_ID";
  // 获取第一步的code后，请求以下链接获取access_token
  private String GetCodeRequest = "https://mWXApi.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
  // 获取用户个人信息
  private String GetUserInfo = "https://mWXApi.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
  private String WX_APP_SECRET = "创建应用后得到的APP_SECRET";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
//    moveTaskToBack(true);
    mWXApi = ((AndroidApplication) getApplication()).getWxApi();
    mWXApi.handleIntent(getIntent(), this);
  }

  // 微信发送请求到第三方应用时，会回调到该方法
  @Override
  public void onReq(BaseReq req) {
//    finish();
  }

  // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
  @Override
  public void onResp(BaseResp resp) {
    System.out.println("resp.errCode = " + resp.errCode);
    System.out.println("resp.errStr = " + resp.errStr);

    String code = "";
    switch (resp.errCode) {
      case BaseResp.ErrCode.ERR_OK:
        ToastUtils.showShort("发送成功");
        code = ((SendAuth.Resp) resp).code;



            /*
             * 将你前面得到的AppID、AppSecret、code，拼接成URL 获取access_token等等的信息(微信)
             */
//        String get_access_token = getCodeRequest(code);
//        AsyncHttpClient client = new AsyncHttpClient();
//        client.post(get_access_token, new JsonHttpResponseHandler() {
//          @Override
//          public void onSuccess(int statusCode, JSONObject response) {
//            // TODO Auto-generated method stub
//            super.onSuccess(statusCode, response);
//            try {
//
//
//              if (!response.equals("")) {
//                String access_token = response
//                    .getString("access_token");
//                String openid = response.getString("openid");
//                String get_user_info_url = getUserInfo(
//                    access_token, openid);
//                getUserInfo(get_user_info_url);
//              }
//
//            } catch (Exception e) {
//              // TODO Auto-generated catch block
//              e.printStackTrace();
//            }
//          }
//        });

        break;
      case BaseResp.ErrCode.ERR_USER_CANCEL:
        ToastUtils.showShort("发送取消");
        break;
      case BaseResp.ErrCode.ERR_AUTH_DENIED:
        ToastUtils.showShort("发送被拒绝");
        break;
      default:
        ToastUtils.showShort("发送返回");
        break;
    }

    PrefConstant.putWXCode(code);
    finish();
  }

//  /**
//   * 通过拼接的用户信息url获取用户信息
//   *
//   * @param user_info_url
//   */
//  private void getUserInfo(String user_info_url) {
//    AsyncHttpClient client = new AsyncHttpClient();
//    client.get(user_info_url, new JsonHttpResponseHandler() {
//      @Override
//      public void onSuccess(int statusCode, JSONObject response) {
//        // TODO Auto-generated method stub
//        super.onSuccess(statusCode, response);
//        try {
//
//          System.out.println("获取用户信息:" + response);
//
//          if (!response.equals("")) {
//            String openid = response.getString("openid");
//            String nickname = response.getString("nickname");
//            String headimgurl = response.getString("headimgurl");
//
//          }
//
//        } catch (Exception e) {
//          // TODO Auto-generated catch block
//          e.printStackTrace();
//        }
//      }
//    });
//  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    setIntent(intent);
    mWXApi.handleIntent(intent, this);
    finish();
  }

  /**
   * 获取access_token的URL（微信）
   *
   * @param code 授权时，微信回调给的
   * @return URL
   */
  private String getCodeRequest(String code) {
    String result = null;
    GetCodeRequest = GetCodeRequest.replace("APPID",
        urlEnodeUTF8(WX_APP_ID));
    GetCodeRequest = GetCodeRequest.replace("SECRET",
        urlEnodeUTF8(WX_APP_SECRET));
    GetCodeRequest = GetCodeRequest.replace("CODE", urlEnodeUTF8(code));
    result = GetCodeRequest;
    return result;
  }

  /**
   * 获取用户个人信息的URL（微信）
   *
   * @param access_token 获取access_token时给的
   * @param openid       获取access_token时给的
   * @return URL
   */
  private String getUserInfo(String access_token, String openid) {
    String result = null;
    GetUserInfo = GetUserInfo.replace("ACCESS_TOKEN",
        urlEnodeUTF8(access_token));
    GetUserInfo = GetUserInfo.replace("OPENID", urlEnodeUTF8(openid));
    result = GetUserInfo;
    return result;
  }

  private String urlEnodeUTF8(String str) {
    String result = str;
    try {
      result = URLEncoder.encode(str, "UTF-8");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }
}
