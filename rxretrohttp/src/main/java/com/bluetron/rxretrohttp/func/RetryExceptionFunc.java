package com.bluetron.rxretrohttp.func;

import com.bluetron.rxretrohttp.exception.ApiException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

/**
 * @author tongxb
 * @date 2018/3/22
 * RX重试机制
 */
public class RetryExceptionFunc implements Function<Observable<? extends Throwable>,
        Observable<?>> {
    private int count = 0;//当前重试次数
    private long delay = 500;//重试延迟
    private long increaseDelay = 3000;//叠加延时

    public RetryExceptionFunc(int count, long delay, long increaseDelay) {
        this.count = count;
        this.delay = delay;
        this.increaseDelay = increaseDelay;
    }

    @Override
    public Observable<?> apply(@NonNull Observable<? extends Throwable> observable) {
        return observable.zipWith(Observable.range(1, count + 1)
                , (BiFunction<Throwable, Integer, Wrapper>) Wrapper::new)
                .flatMap((Function<Wrapper, Observable<?>>) wrapper -> {
                    int errCode = 0;
                    if (wrapper.throwable instanceof ApiException) {
                        ApiException exception = (ApiException) wrapper.throwable;
                        errCode = exception.getCode();
                    }
                    if ((wrapper.throwable instanceof ConnectException
                            || wrapper.throwable instanceof SocketTimeoutException
                            || errCode == ApiException.NETWORK_ERROR
                            || errCode == ApiException.TIMEOUT_ERROR
                            || wrapper.throwable instanceof SocketTimeoutException
                            || wrapper.throwable instanceof TimeoutException)
                            && wrapper.index < count + 1) {
                        return Observable.timer(delay + (wrapper.index - 1) * increaseDelay,
                                TimeUnit.MILLISECONDS);

                    }
                    return Observable.error(wrapper.throwable);
                });
    }

    private class Wrapper {
        private int index;
        private Throwable throwable;

        Wrapper(Throwable throwable, int index) {
            this.index = index;
            this.throwable = throwable;
        }
    }

}
