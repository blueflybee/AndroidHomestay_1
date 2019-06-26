package com.qtec.homestay.view.user.mine;

import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.qtec.homestay.data.constant.PrefConstant;
import com.qtec.homestay.domain.constant.CloudUseCaseComm;
import com.qtec.homestay.internal.di.PerActivity;
import com.qtec.homestay.presenter.Presenter;
import com.qtec.homestay.domain.model.core.QtecEncryptInfo;
import com.qtec.homestay.domain.model.mapp.req.GetStsTokenRequest;
import com.qtec.homestay.domain.model.mapp.rsp.GetStsTokenResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetStsTokenResponse.*;
import com.qtec.homestay.domain.model.mapp.req.ModifyUserInfoRequest;
import com.qtec.homestay.domain.model.mapp.rsp.ModifyUserInfoResponse;
import com.qtec.homestay.interactor.AppSubscriber;

import javax.inject.Inject;
import javax.inject.Named;
import com.qtec.homestay.domain.interactor.cloud.GetStsToken;
import com.qtec.homestay.domain.interactor.cloud.ModifyUserInfo;
import com.qtec.homestay.utils.StringUtil;
import com.qtec.homestay.view.user.mine.utils.OssUtil;

import dagger.internal.Preconditions;

import static com.qtec.homestay.view.user.mine.utils.OssUtil.STS_SERVER;

/**
 * <pre>
 *     author : wusj
 *     time   : 2018/08/15
 *     desc   : MyInfoPresenter
 * </pre>
 */
@PerActivity
public class MyInfoPresenter implements Presenter {

  private final GetStsToken mGetStsToken;
  private final ModifyUserInfo mModifyUserInfo;
  private MyInfoView mView;
  private String mImageUrl;

  @Inject
  public MyInfoPresenter(@Named(CloudUseCaseComm.GET_STS_TOKEN) GetStsToken pGetStsToken,
                        @Named(CloudUseCaseComm.MODIFY_USER_INFO) ModifyUserInfo pModifyUserInfo) {
    this.mGetStsToken = Preconditions.checkNotNull(pGetStsToken, "pGetStsToken is null");
    this.mModifyUserInfo = Preconditions.checkNotNull(pModifyUserInfo, "pModifyUserInfo is null");
  }

  @Override
  public void resume() {}

  @Override
  public void pause() {}

  @Override
  public void destroy() {
    mGetStsToken.dispose();
    mModifyUserInfo.dispose();
  }

  public void setView(MyInfoView view) {
    this.mView = view;
  }

  public void getStsToken(String compressPath) {
//    GetStsTokenRequest request = new GetStsTokenRequest();
//    QtecEncryptInfo<GetStsTokenRequest> encryptInfo = new QtecEncryptInfo<>();
//    encryptInfo.setData(request);
    mView.showLoading();
    mGetStsToken.execute(new QtecEncryptInfo(), new AppSubscriber<GetStsTokenResponse<CredentialsBean, AssumedRoleUserBean>>(mView) {
      @Override
      protected void doNext(GetStsTokenResponse<CredentialsBean, AssumedRoleUserBean> response) {

        String objectKey = PrefConstant.getUserUniqueKey() + "/" + StringUtil.getUUID() + ".jpg";
        mView.showLoading();
        OssUtil.upload(mView.getContext(), response.getCredentials(), objectKey, compressPath,
            new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {


              @Override
              public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());
                Log.d("ObjectKey", request.getObjectKey());

                mImageUrl = STS_SERVER + request.getObjectKey();

                mView.uploadHeadImageSuccess(mImageUrl);
              }

              @Override
              public void onFailure(PutObjectRequest putObjectRequest, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                  // 本地异常如网络异常等
                  clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                  // 服务异常
                  Log.e("ErrorCode", serviceException.getErrorCode());
                  Log.e("RequestId", serviceException.getRequestId());
                  Log.e("HostId", serviceException.getHostId());
                  Log.e("RawMessage", serviceException.getRawMessage());
                }

                mView.uploadHeadImageFail();
              }
            },
            new OSSProgressCallback<PutObjectRequest>() {
              @Override
              public void onProgress(PutObjectRequest putObjectRequest, long currentSize, long totalSize) {
                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);

              }
            });
      }
    });
  }

  public void modifyUserInfo(String username, String headImage, String userNickName) {
    ModifyUserInfoRequest request = new ModifyUserInfoRequest();
    request.setUserPhone(username);
    request.setUserHeadImg(headImage);
    request.setUserNickName(userNickName);
    QtecEncryptInfo<ModifyUserInfoRequest> encryptInfo = new QtecEncryptInfo<>();
    encryptInfo.setData(request);
    mView.showLoading();
    mModifyUserInfo.execute(encryptInfo, new AppSubscriber<ModifyUserInfoResponse>(mView) {
      @Override
      protected void doNext(ModifyUserInfoResponse response) {
        mView.showModifySuccess(response);
      }
    });
  }

  public String getImageUrl() {
    return mImageUrl;
  }
}