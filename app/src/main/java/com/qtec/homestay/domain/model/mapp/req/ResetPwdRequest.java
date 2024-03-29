package com.qtec.homestay.domain.model.mapp.req;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 2017/06/13
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class ResetPwdRequest {

    private String userPhone;
    private String smsCode;
    private String userPassword;

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
