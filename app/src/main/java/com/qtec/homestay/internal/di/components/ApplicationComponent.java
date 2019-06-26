package com.qtec.homestay.internal.di.components;

import android.content.Context;

import com.qtec.homestay.domain.executor.PostExecutionThread;
import com.qtec.homestay.domain.executor.ThreadExecutor;
import com.qtec.homestay.domain.repository.CloudRepository;
import com.qtec.homestay.internal.di.modules.ApplicationModule;
import com.qtec.homestay.navigation.Navigator;
import com.qtec.homestay.view.activity.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

  void inject(BaseActivity baseActivity);

  //Exposed to sub-graphs.
  Context context();
  Navigator navigator();
  ThreadExecutor threadExecutor();
  PostExecutionThread postExecutionThread();

  CloudRepository cloudRepository();

}
