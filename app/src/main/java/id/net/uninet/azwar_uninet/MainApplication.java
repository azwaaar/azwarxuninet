package id.net.uninet.azwar_uninet;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.LruCache;

import androidx.multidex.MultiDexApplication;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import id.net.uninet.azwar_uninet.utils.Constants;
import id.net.uninet.azwar_uninet.utils.LruBitmapCache;
import id.net.uninet.azwar_uninet.utils.Preferences;

public class MainApplication extends MultiDexApplication {

    public static final String TAG = MainApplication.class.getSimpleName();

    private static Context context;

    private static Preferences preferences;

    private static MainApplication mInstance;

    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;


    public static final String PRIMARY_CHANNEL = "default";

    public MainApplication() {
        mInstance = this;
        preferences = new Preferences(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        context = getApplicationContext();

        preferences = new Preferences(this);

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap> cache = new LruBitmapCache(context);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });

        System.setProperty("http.keepAliveDuration", String.valueOf(30 * 60 * 1000));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerChannel();
        }


    }

    @TargetApi(Build.VERSION_CODES.O)
    private void registerChannel() {
        NotificationChannel channel = new NotificationChannel(
                PRIMARY_CHANNEL, "default_channel", NotificationManager.IMPORTANCE_MIN);
        channel.setLightColor(Color.GREEN);
        channel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
    }

    public static synchronized MainApplication getInstance() {
        MainApplication mainApplication;
        synchronized (MainApplication.class) {
            mainApplication = mInstance;
        }
        return mainApplication;
    }

    public RequestQueue getRequestQueue() {
        if (this.mRequestQueue == null) {
            this.mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return this.mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        if (TextUtils.isEmpty(tag)) {
            tag = Constants.VOLLEY_TAG;
        }
        req.setTag(tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(Constants.VOLLEY_TAG);
        req.setShouldCache(false);
        // Wait 30 seconds and don't retry more than once
        req.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        /*req.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (this.mRequestQueue != null) {
            this.mRequestQueue.cancelAll(tag);
        }
    }

    public static Context getContext() {
        return context;
    }

    public static Preferences getPreferences() {
        return preferences;
    }
}
