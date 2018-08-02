package com.bonnidee.rxjava2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ThreadActivity extends AppCompatActivity {

    private String TAG = "ThreadActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);


    }

    public void button1(View view) {

        // 生产者
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {  // 订阅的事件
                Log.d(TAG, "Observablethread==" + Thread.currentThread().getName());
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);

            }
        });
        //消费者
        Consumer<Integer> consumer = new Consumer<Integer>() {

            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "accept==" + integer);
                Log.d(TAG, "Consumer==" + Thread.currentThread().getName());
            }
        };
        // 订阅事件
        observable.subscribeOn(Schedulers.newThread()) //订阅线程
                .observeOn(AndroidSchedulers.mainThread()) //观察者线程
                .subscribe(consumer);

    }
    // 链接式线程切换rxjava
    public void button2(View view) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "Observablethread==" + Thread.currentThread().getName());
                emitter.onNext(1);
                emitter.onNext(2);
            }

            // 调度者代表的线程
            /*Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作
            Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作
            Schedulers.newThread() 代表一个常规的新线程
            AndroidSchedulers.mainThread() 代表Android的主线程*/

        }).subscribeOn(Schedulers.newThread()) //Schedulers 调度者 用来切换线程的
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "accept==" + integer);
                        Log.d(TAG, "Consumer==" + Thread.currentThread().getName());
                    }
                });

    }
}
