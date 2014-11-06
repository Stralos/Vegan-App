package vegan.paki.mapa.mif.veganapp.util;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Mantas on 7/22/2014.
 */
public class RxImageLoader {

    private static ImageLoader imageLoader;

    public static void init(ImageLoaderConfiguration imageLoaderConfiguration) {
        ImageLoader.getInstance().init(imageLoaderConfiguration);
        imageLoader = ImageLoader.getInstance();
    }

    public static Observable<Boolean> displayImage(final String uri, final ImageView imageView) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(final Subscriber<? super Boolean> subscriber) {
                imageLoader.displayImage(uri, imageView, new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(false);
                            subscriber.onCompleted();
                        }
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(true);
                            subscriber.onCompleted();
                        }
                    }
                });
            }
        });
    }
}
