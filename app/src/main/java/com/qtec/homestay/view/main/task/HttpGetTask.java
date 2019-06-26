package com.qtec.homestay.view.main.task;

import android.os.AsyncTask;
import android.text.TextUtils;

import com.qtec.homestay.domain.mapper.JsonMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * <pre>
 *     author : shaojun
 *     e-mail : wusj@qtec.cn
 *     time   : 2018/08/14
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class HttpGetTask<T> extends AsyncTask<Object, Integer, String> {
  private final Type mType;
  private final HttpGetCallback<T> mCallback;

  public HttpGetTask(Type type, HttpGetCallback<T> callback) {
    mType = type;
    mCallback = callback;
  }

  //http://gc.ditu.aliyun.com/regeocoding?l=39.938133,116.395739&type=001
  @Override
  protected String doInBackground(Object... objects) {
    String url = createUrl(objects);
    System.out.println("url = " + url);
    if (TextUtils.isEmpty(url)) return null;
    StringBuilder res = new StringBuilder();
    try {
      HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
      conn.setDoInput(true);
      conn.setRequestMethod("GET");
      BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
      String line;
      while ((line = in.readLine()) != null) {
        res.append(line).append("\n");
      }
      in.close();
      System.out.println("res = " + res);

      return res.toString();
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }

  @Override
  protected void onPostExecute(String result) {
    super.onPostExecute(result);

    if (TextUtils.isEmpty(result)) {
      mCallback.onFail(result);
    } else {
      mCallback.onSuccess((T) new JsonMapper().fromJsonNullStringToEmpty(result, mType));
    }
  }

  private String createUrl(Object[] objects) {
    try {
      Object req = objects[0];
      String url = (String) objects[1];
      if (req == null || TextUtils.isEmpty(url)) return "";
      Class clazz = req.getClass();
      Field[] fields = clazz.getDeclaredFields();
      StringBuilder sb = new StringBuilder(url);

      for (int i = 0; i < fields.length; i++) {
        Field field = fields[i];
        field.setAccessible(true);
        String name = field.getName();
//        System.out.println("name = " + name);
        Object o = field.get(req);
//        System.out.println("objectValue = " + o);
        if (!(o instanceof String)) continue;
        String value = (String) o;
        sb.append(name).append("=").append(value);
        if (i != fields.length - 1) sb.append("&");
      }
      return sb.toString();
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }
}
