package info.smemo.nbase.util;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by neo on 16/6/14.
 */
public class ThreadUtil {

    public static <T> void newThreadWithMainObj(final ThreadRunnable<T> runnable) {
        Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                subscriber.onNext(runnable.inThread());
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<T>() {
                    @Override
                    public void call(T t) {
                        runnable.inMain(t);
                    }
                }).subscribe();
    }

    public static <T> void newThreadWithMainList(final ThreadRunnableList<T> runnable) {
        Observable.create(new Observable.OnSubscribe<List<T>>() {
            @Override
            public void call(Subscriber<? super List<T>> subscriber) {
                subscriber.onNext(runnable.inThread());
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<List<T>>() {
                    @Override
                    public void call(List<T> t) {
                        runnable.inMain(t);
                    }
                }).subscribe();
    }

    public static void newThreadWithMain(final ThreadRunnableVoid runnable) {
        Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                runnable.inThread();
                subscriber.onNext("next");
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        runnable.inMain();
                    }
                }).subscribe();

    }


    public interface ThreadRunnableVoid {

        void inThread();

        void inMain();
    }

    public interface ThreadRunnable<T> {

        T inThread();

        void inMain(T t);

    }

    public interface ThreadRunnableList<T> {

        List<T> inThread();

        void inMain(List<T> t);

    }

}
