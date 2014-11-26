package vegan.paki.mapa.mif.veganapp;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.Parse;
import com.parse.ParseObject;

import timber.log.Timber;
import vegan.paki.mapa.mif.veganapp.core.model.dto.CategoryDTO;
import vegan.paki.mapa.mif.veganapp.core.model.dto.PostDTO;
import vegan.paki.mapa.mif.veganapp.util.RxImageLoader;

/**
 * VeganApplication application
 */
public class VeganApplication extends Application {

    /**
     * Create main application
     */
    public VeganApplication() {

    }

    /**
     * Create main application
     *
     * @param context
     */
    public VeganApplication(final Context context) {
        this();
        attachBaseContext(context);

    }

    @Override
    public void onCreate() {
        super.onCreate();

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                //.showImageForEmptyUri(R.drawable.cu_def) // resource or drawable
                //.showImageOnFail(R.drawable.no_internet) // resource or drawable
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .build();

        ImageLoaderConfiguration.Builder imageLoaderConfig = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(20)
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(150)
                .defaultDisplayImageOptions(options);
/*        if(BuildConfig.DEBUG)
            imageLoaderConfig.writeDebugLogs();*/


        RxImageLoader.init(imageLoaderConfig.build());
        RxParseManager.getInstance().init(this);

        ParseObject.registerSubclass(PostDTO.class);
        ParseObject.registerSubclass(CategoryDTO.class);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "juQPiQJ3E9A4gVg69BjT7X0xfDwLeaTtyByOIhcf", "75lGmleK7sguI67H8XqimcwmACvkHN6hP91d9fwj");
        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    /** A tree which logs important information for crash reporting. */
    private static class CrashReportingTree extends Timber.HollowTree {
        @Override
        public void i(String message, Object... args) {
            // TODO e.g., Crashlytics.log(String.format(message, args));
        }

        @Override
        public void i(Throwable t, String message, Object... args) {
            i(message, args); // Just add to the log.
        }

        @Override
        public void e(String message, Object... args) {
            i("ERROR: " + message, args); // Just add to the log.
        }

        @Override
        public void e(Throwable t, String message, Object... args) {
            e(message, args);

            // TODO e.g., Crashlytics.logException(t);
        }
    }
}
