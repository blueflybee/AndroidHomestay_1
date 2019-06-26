package com.qtec.homestay.view.main.task;

/**
 * <pre>
 *     author : shaojun
 *     e-mail : wusj@qtec.cn
 *     time   : 2018/08/14
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public interface HttpGetCallback<T> {

  void onFail(String result);

  void onSuccess(T t);
}
