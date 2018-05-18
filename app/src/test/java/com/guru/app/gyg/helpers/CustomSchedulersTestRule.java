package com.guru.app.gyg.helpers;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

public class CustomSchedulersTestRule implements TestRule {

    private Scheduler mScheduler;

    public CustomSchedulersTestRule() {
        mScheduler = Schedulers.trampoline();
    }

    public CustomSchedulersTestRule(Scheduler scheduler) {
        mScheduler = scheduler;
    }

    public void updateComputationScheduler(Scheduler newScheduler) {
        RxJavaPlugins.setComputationSchedulerHandler(scheduler -> newScheduler);
    }

    public void resetSchedulers() {
        RxJavaPlugins.reset();
        RxAndroidPlugins.reset();
    }

    @Override
    public Statement apply(final Statement base, Description description) {

        return new Statement() {
            @Override
            public void evaluate() throws Throwable {

                RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> mScheduler);
                RxJavaPlugins.setIoSchedulerHandler(scheduler -> mScheduler);

                RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> mScheduler);
                RxJavaPlugins.setComputationSchedulerHandler(scheduler -> mScheduler);

                RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> mScheduler);
                RxJavaPlugins.setNewThreadSchedulerHandler(scheduler -> mScheduler);

                RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> mScheduler);
                RxJavaPlugins.setSingleSchedulerHandler(scheduler -> mScheduler);

                RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> mScheduler);

                try {
                    base.evaluate();
                } finally {
                    resetSchedulers();
                }
            }
        };
    }
}
