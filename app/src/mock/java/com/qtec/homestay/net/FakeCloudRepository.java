/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qtec.homestay.net;

import android.content.Context;

import com.qtec.homestay.data.utils.CloudConverter;
import com.qtec.homestay.data.utils.IConverter;

import com.google.gson.reflect.TypeToken;
import com.qtec.homestay.data.net.CloudUrlPath;
import com.qtec.homestay.domain.mapper.JsonMapper;
import com.qtec.homestay.domain.model.mapp.rsp.GetRoomManageResponse;
import com.qtec.homestay.domain.model.mapp.rsp.LoginResponse;
import com.qtec.homestay.domain.params.IRequest;
import com.qtec.homestay.domain.repository.CloudRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;


/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 2017/06/08
 *     desc   : {@link FakeCloudRepository} for retrieving user data from fake remote repository.
 *     version: 1.0
 * </pre>
 */
@Singleton
public class FakeCloudRepository implements CloudRepository {

  private final JsonMapper mJsonMapper = new JsonMapper();
  private Context mContext;

  @Inject
  public FakeCloudRepository(Context context) {
    mContext = context;
  }

  @Override
  public Observable<LoginResponse> login(IRequest request) {
    return Observable.create(emitter -> {
//        NullPointerException nullPointerException1 = new NullPointerException("NullPointerException1");
//        emitter.onError(nullPointerException1);
//        throw nullPointerException1;

      LoginResponse response = new LoginResponse();
      response.setToken("asd9f8as9df8asd7gadsfua90sd8f09asd");
      response.setUserHeadImg("img");
      response.setUserUniqueKey("unikey");
      response.setUserNickName("jklolin");
      response.setUserPhone("13866666666");
      response.setId(Integer.MAX_VALUE);
      emitter.onNext(response);
      emitter.onComplete();
//        QtecResult<LoginResponse> result = new QtecResult<>();
//        result.setData(response);
//        Type type = new TypeToken<QtecResult<LoginResponse>>() {
//        }.getType();
    });
  }

  @Override
  public Observable<List<GetRoomManageResponse>> getRoomMangerInfoList(IRequest request) {
    return Observable.create(emitter -> {
//        NullPointerException nullPointerException1 = new NullPointerException("NullPointerException1");
//        emitter.onError(nullPointerException1);
//        throw nullPointerException1;
      List<GetRoomManageResponse> responses = new ArrayList<>();
      for (int i = 0; i < 5; i++) {
        GetRoomManageResponse response = new GetRoomManageResponse();
        response.setBatteryPower("batteryPower");
        response.setDeviceSerialNo("deviceSerialNo");
        response.setEndTime("end time");
        response.setId("id");
        response.setLockPass("lockPass");
        response.setStartTime("startTime");
        response.setResidentName("residentName");
        response.setResidentPhone("residentPhone");
        responses.add(response);
      }
      emitter.onNext(responses);
      emitter.onComplete();
    });
  }
}