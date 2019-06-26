package com.qtec.homestay.domain.interactor.cloud;

import android.graphics.Rect;

import com.blueflybee.uilibrary2.data.TopologyNode;
import com.qtec.homestay.R;
import com.qtec.homestay.domain.executor.PostExecutionThread;
import com.qtec.homestay.domain.executor.ThreadExecutor;
import com.qtec.homestay.domain.interactor.UseCase;
import com.qtec.homestay.domain.model.mapp.rsp.GetDevTreeResponse;
import com.qtec.homestay.domain.model.mapp.rsp.GetDevTreeResponse.LockBean;
import com.qtec.homestay.domain.params.IRequest;
import com.qtec.homestay.domain.repository.CloudRepository;
import com.qtec.homestay.view.device.data.Device;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * <pre>
 *     author : shaojun
 *     e-mail :
 *     time   : 18-7-16
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class GetDevTree extends UseCase<TopologyNode> {

  private final CloudRepository cloudRepository;

  @Inject
  public GetDevTree(CloudRepository cloudRepository, ThreadExecutor threadExecutor,
                    PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.cloudRepository = cloudRepository;
  }

  @Override
  protected Observable<TopologyNode> buildUseCaseObservable(IRequest param) {
    return cloudRepository.getDevTree(param).map(this::getTopology);
  }

  private TopologyNode getTopology(List<GetDevTreeResponse> responses) {
    TopologyNode root = new TopologyNode();
    List<TopologyNode> nodes = new ArrayList<>();
    root.setChildNodes(nodes);
    for (int i = 0; i < responses.size(); i++) {
      GetDevTreeResponse response = responses.get(i);
      TopologyNode router = new TopologyNode();
      router.setId(response.getDeviceSerialNo());
      router.setRect(new Rect(0, 0, 158, 140));
      router.setIcon(R.mipmap.tuopu_router);
      router.setLockIcon(R.mipmap.tupu_lock);
      router.setName("网关" + (i + 1));
      router.setType(Device.TYPE_ROUTER);
      nodes.add(router);
      List<GetDevTreeResponse.LockBean> lockList = response.getLockList();
      if (lockList == null || lockList.isEmpty()) continue;
      for (int j = 0; j < lockList.size(); j++) {
        GetDevTreeResponse.LockBean lockBean = lockList.get(j);
        TopologyNode lock = new TopologyNode();
        lock.setId(lockBean.getDeviceSerialNo());
        lock.setRect(new Rect(0, 0, 280, 140));
        lock.setNodeNo(lockBean.getRoomNo());
        lock.setName("智能门锁" + lockBean.getRoomNo());
        lock.setType(Device.TYPE_LOCK);
        router.addNode(lock);
      }
    }
    return root;
  }
}

