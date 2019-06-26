package com.qtec.homestay.view.main.data;

/**
 * <pre>
 *     author : shaojun
 *     e-mail : wusj@qtec.cn
 *     time   : 2018/08/14
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class WeatherRequest {
//  http://wthrcdn.etouch.cn/weather_mini?city="杭州"
  private String city;

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }
}
