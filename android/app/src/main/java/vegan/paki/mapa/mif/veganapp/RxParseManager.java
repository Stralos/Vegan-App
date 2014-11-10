package vegan.paki.mapa.mif.veganapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Voltas on 2014-07-18.
 */
public class RxParseManager {

    public enum Function {
      ;

        private String name;

        private Function(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public enum Event {


        ;

        private String name;
        private String data;

        private Event(String name) {
            this(name, null);
        }

        private Event(String name, String data) {
            this.name = name;
            this.data = data;
        }
    }

    private static RxParseManager sInstance;

    private Context mContext;

    public static RxParseManager getInstance() {
        if (sInstance == null) {
            sInstance = new RxParseManager();
        }
        return sInstance;
    }

    private RxParseManager() {
    }

    public void init(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context must be not null");
        }
        mContext = context;
    }

    public void event(Event event) {
        String name = event.name;
        String data = event.data;
        if (name == null || name.isEmpty()) {
            return;
        }
        if (data == null || data.isEmpty()) {
            ParseAnalytics.trackEventInBackground(name);
        } else {
            HashMap<String, String> dimensions = new HashMap<String, String>();
            dimensions.put(name, data);
            ParseAnalytics.trackEventInBackground(name, dimensions);
        }
    }

    public <T extends ParseObject> Observable<T> getFirst(final ParseQuery<T> query) {
        return isNetworkAvailableObservable().flatMap(new Func1<Boolean, Observable<T>>() {
            @Override
            public Observable<T> call(Boolean aBoolean) {
                if (aBoolean) {
                    return getFirstObservable(query);
                }
                return Observable.error(new ParseException(ParseException.CONNECTION_FAILED, "No network"));
            }
        });
    }

    public <T extends ParseObject> Observable<List<T>> find(final ParseQuery<T> query) {
        return isNetworkAvailableObservable().flatMap(new Func1<Boolean, Observable<List<T>>>() {
            @Override
            public Observable<List<T>> call(Boolean aBoolean) {
                if (aBoolean) {
                    return findObservable(query);
                }
                return Observable.error(new ParseException(ParseException.CONNECTION_FAILED, "No network"));
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public Observable<Boolean> isNetworkAvailableObservable() {
        return Observable.just(isNetworkAvailable());
    }

    private static <T> Observable<T> callFunctionInBackground(final Function function, final Map<String, ?> params) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(final Subscriber<? super T> subscriber) {
                ParseCloud.callFunctionInBackground(function.getName(), params, new FunctionCallback<T>() {
                    public void done(T result, ParseException e) {
                        if (e == null) {
                            Timber.d("callFunctionInBackground result " + result.toString());
                            subscriber.onNext(result);
                            subscriber.onCompleted();
                        } else {
                            Timber.d("callFunctionInBackground error " + e.toString());
                            subscriber.onError(e);
                        }
                    }
                });
            }
        });

    }

    private static <T extends ParseObject> Observable<List<T>> findObservable(final ParseQuery<T> query) {
        return Observable.create(new Observable.OnSubscribe<List<T>>() {
            @Override
            public void call(final Subscriber<? super List<T>> subscriber) {
                query.findInBackground(new FindCallback<T>() {
                    @Override
                    public void done(List<T> list, ParseException e) {
                        if (e == null) {
                            subscriber.onNext(list);
                            subscriber.onCompleted();
                        } else {
                            subscriber.onError(e);
                        }
                    }
                });
            }
        }).timeout(15, TimeUnit.SECONDS, Observable.create(new Observable.OnSubscribe<List<T>>() {
            @Override
            public void call(Subscriber<? super List<T>> subscriber) {
                query.cancel();
                subscriber.onError(new ParseException(ParseException.TIMEOUT, "query timeout"));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()));
    }

    private static <T extends ParseObject> Observable<T> getFirstObservable(final ParseQuery<T> query) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(final Subscriber<? super T> subscriber) {
                query.getFirstInBackground(new GetCallback<T>() {
                    @Override
                    public void done(T parseObject, ParseException e) {
                        if (e == null) {
                            subscriber.onNext(parseObject);
                            subscriber.onCompleted();
                        } else {
                            subscriber.onError(e);
                        }
                    }
                });
            }
        }).timeout(15, TimeUnit.SECONDS, Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                query.cancel();
                subscriber.onError(new ParseException(ParseException.TIMEOUT, "query timeout"));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()));
    }
}
