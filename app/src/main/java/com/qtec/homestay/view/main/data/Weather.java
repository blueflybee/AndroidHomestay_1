package com.qtec.homestay.view.main.data;

import java.util.List;

/**
 * <pre>
 *     author : shaojun
 *     e-mail : wusj@qtec.cn
 *     time   : 2018/08/14
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class Weather {

  /**
   * data : {"yesterday":{"date":"13鏃ユ槦鏈熶竴","high":"楂樻俯 33鈩�","fx":"涓滃寳椋�","low":"浣庢俯 25鈩�","fl":"<![CDATA[5-6绾]>","type":"闃甸洦"},"city":"鏉窞","aqi":"32","forecast":[{"date":"14鏃ユ槦鏈熶簩","high":"楂樻俯 35鈩�","fengli":"<![CDATA[3-4绾]>","low":"浣庢俯 26鈩�","fengxiang":"涓滈","type":"澶氫簯"},{"date":"15鏃ユ槦鏈熶笁","high":"楂樻俯 33鈩�","fengli":"<![CDATA[<3绾]>","low":"浣庢俯 27鈩�","fengxiang":"涓滃寳椋�","type":"闆烽樀闆�"},{"date":"16鏃ユ槦鏈熷洓","high":"楂樻俯 32鈩�","fengli":"<![CDATA[<3绾]>","low":"浣庢俯 27鈩�","fengxiang":"鍖楅","type":"闃甸洦"},{"date":"17鏃ユ槦鏈熶簲","high":"楂樻俯 32鈩�","fengli":"<![CDATA[3-4绾]>","low":"浣庢俯 27鈩�","fengxiang":"鍖楅","type":"闃甸洦"},{"date":"18鏃ユ槦鏈熷叚","high":"楂樻俯 32鈩�","fengli":"<![CDATA[3-4绾]>","low":"浣庢俯 27鈩�","fengxiang":"鍖楅","type":"闃甸洦"}],"ganmao":"鍚勯」姘旇薄鏉\u2032欢閫傚疁锛屽彂鐢熸劅鍐掓満鐜囪緝浣庛\u20ac備絾璇烽伩鍏嶉暱鏈熷浜庣┖璋冩埧闂翠腑锛屼互闃叉劅鍐掋\u20ac�","wendu":"34"}
   * status : 1000
   * desc : OK
   */

  private DataBean data;
  private int status;
  private String desc;

  public DataBean getData() {
    return data;
  }

  public void setData(DataBean data) {
    this.data = data;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  @Override
  public String toString() {
    return "Weather{" +
        "data=" + data +
        ", status=" + status +
        ", desc='" + desc + '\'' +
        '}';
  }

  public static class DataBean {
    /**
     * yesterday : {"date":"13鏃ユ槦鏈熶竴","high":"楂樻俯 33鈩�","fx":"涓滃寳椋�","low":"浣庢俯 25鈩�","fl":"<![CDATA[5-6绾]>","type":"闃甸洦"}
     * city : 鏉窞
     * aqi : 32
     * forecast : [{"date":"14鏃ユ槦鏈熶簩","high":"楂樻俯 35鈩�","fengli":"<![CDATA[3-4绾]>","low":"浣庢俯 26鈩�","fengxiang":"涓滈","type":"澶氫簯"},{"date":"15鏃ユ槦鏈熶笁","high":"楂樻俯 33鈩�","fengli":"<![CDATA[<3绾]>","low":"浣庢俯 27鈩�","fengxiang":"涓滃寳椋�","type":"闆烽樀闆�"},{"date":"16鏃ユ槦鏈熷洓","high":"楂樻俯 32鈩�","fengli":"<![CDATA[<3绾]>","low":"浣庢俯 27鈩�","fengxiang":"鍖楅","type":"闃甸洦"},{"date":"17鏃ユ槦鏈熶簲","high":"楂樻俯 32鈩�","fengli":"<![CDATA[3-4绾]>","low":"浣庢俯 27鈩�","fengxiang":"鍖楅","type":"闃甸洦"},{"date":"18鏃ユ槦鏈熷叚","high":"楂樻俯 32鈩�","fengli":"<![CDATA[3-4绾]>","low":"浣庢俯 27鈩�","fengxiang":"鍖楅","type":"闃甸洦"}]
     * ganmao : 鍚勯」姘旇薄鏉′欢閫傚疁锛屽彂鐢熸劅鍐掓満鐜囪緝浣庛€備絾璇烽伩鍏嶉暱鏈熷浜庣┖璋冩埧闂翠腑锛屼互闃叉劅鍐掋€�
     * wendu : 34
     */

    private YesterdayBean yesterday;
    private String city;
    private String aqi;
    private String ganmao;
    private String wendu;
    private List<ForecastBean> forecast;

    public YesterdayBean getYesterday() {
      return yesterday;
    }

    public void setYesterday(YesterdayBean yesterday) {
      this.yesterday = yesterday;
    }

    public String getCity() {
      return city;
    }

    public void setCity(String city) {
      this.city = city;
    }

    public String getAqi() {
      return aqi;
    }

    public void setAqi(String aqi) {
      this.aqi = aqi;
    }

    public String getGanmao() {
      return ganmao;
    }

    public void setGanmao(String ganmao) {
      this.ganmao = ganmao;
    }

    public String getWendu() {
      return wendu;
    }

    public void setWendu(String wendu) {
      this.wendu = wendu;
    }

    public List<ForecastBean> getForecast() {
      return forecast;
    }

    public void setForecast(List<ForecastBean> forecast) {
      this.forecast = forecast;
    }

    public static class YesterdayBean {
      /**
       * date : 13鏃ユ槦鏈熶竴
       * high : 楂樻俯 33鈩�
       * fx : 涓滃寳椋�
       * low : 浣庢俯 25鈩�
       * fl : <![CDATA[5-6绾]>
       * type : 闃甸洦
       */

      private String date;
      private String high;
      private String fx;
      private String low;
      private String fl;
      private String type;

      public String getDate() {
        return date;
      }

      public void setDate(String date) {
        this.date = date;
      }

      public String getHigh() {
        return high;
      }

      public void setHigh(String high) {
        this.high = high;
      }

      public String getFx() {
        return fx;
      }

      public void setFx(String fx) {
        this.fx = fx;
      }

      public String getLow() {
        return low;
      }

      public void setLow(String low) {
        this.low = low;
      }

      public String getFl() {
        return fl;
      }

      public void setFl(String fl) {
        this.fl = fl;
      }

      public String getType() {
        return type;
      }

      public void setType(String type) {
        this.type = type;
      }

      @Override
      public String toString() {
        return "YesterdayBean{" +
            "date='" + date + '\'' +
            ", high='" + high + '\'' +
            ", fx='" + fx + '\'' +
            ", low='" + low + '\'' +
            ", fl='" + fl + '\'' +
            ", type='" + type + '\'' +
            '}';
      }
    }

    public static class ForecastBean {
      /**
       * date : 14鏃ユ槦鏈熶簩
       * high : 楂樻俯 35鈩�
       * fengli : <![CDATA[3-4绾]>
       * low : 浣庢俯 26鈩�
       * fengxiang : 涓滈
       * type : 澶氫簯
       */

      private String date;
      private String high;
      private String fengli;
      private String low;
      private String fengxiang;
      private String type;

      public String getDate() {
        return date;
      }

      public void setDate(String date) {
        this.date = date;
      }

      public String getHigh() {
        return high;
      }

      public void setHigh(String high) {
        this.high = high;
      }

      public String getFengli() {
        return fengli;
      }

      public void setFengli(String fengli) {
        this.fengli = fengli;
      }

      public String getLow() {
        return low;
      }

      public void setLow(String low) {
        this.low = low;
      }

      public String getFengxiang() {
        return fengxiang;
      }

      public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
      }

      public String getType() {
        return type;
      }

      public void setType(String type) {
        this.type = type;
      }

      @Override
      public String toString() {
        return "ForecastBean{" +
            "date='" + date + '\'' +
            ", high='" + high + '\'' +
            ", fengli='" + fengli + '\'' +
            ", low='" + low + '\'' +
            ", fengxiang='" + fengxiang + '\'' +
            ", type='" + type + '\'' +
            '}';
      }
    }

    @Override
    public String toString() {
      return "DataBean{" +
          "yesterday=" + yesterday +
          ", city='" + city + '\'' +
          ", aqi='" + aqi + '\'' +
          ", ganmao='" + ganmao + '\'' +
          ", wendu='" + wendu + '\'' +
          ", forecast=" + forecast +
          '}';
    }
  }
}
