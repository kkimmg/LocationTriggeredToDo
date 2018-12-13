package kkimmg.locationtriggeredtodo;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class LocationCheckService extends Service implements LocationListener {
    /**
     * 設定キー：ロケーション　パワー消費
     */
    public static final String LOCATION_POWER_KEY = "pref_key_location_power";
    /**
     * 設定キー：ロケーション　精度
     */
    public static final String LOCATION_ACCURACY_KEY = "pref_key_location_accuracy";
    /**
     * 設定キー：ロケーション　最少距離
     */
    public static final String MIN_DISTANCE_KEY = "pref_key_min_distance";
    /**
     * 設定キー：ロケーション　最少時間
     */
    public static final String MIN_TIME_KEY = "pref_key_min_time";
    /**
     * ロケーションマネージャ
     */
    private LocationManager locationManager;

    public LocationCheckService() {
    }

    /**
     * 設定から正数値を取得する
     *
     * @param key          設定キー
     * @param defaultValue 初期値
     * @return 設定値または初期値
     */
    private int getPrefInt(String key, int defaultValue) {
        int ret = defaultValue;
        try {
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
            ret = pref.getInt(key, defaultValue);
        } catch (Exception e) {
            Log.e("PREFERENCE", e.getMessage(), e);
            try {
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
                String strSensorDelay = pref.getString(key, String.valueOf(defaultValue));
                ret = Integer.valueOf(strSensorDelay);
            } catch (NumberFormatException ex) {
                Log.e("PREFERENCE", ex.getMessage(), ex);
                ret = defaultValue;
            }
        }
        return ret;
    }

    /**
     * 設定から正数値を取得する
     *
     * @param key          設定キー
     * @param defaultValue 初期値
     * @return 設定値または初期値
     */
    private long getPrefLong(String key, long defaultValue) {
        long ret = defaultValue;
        try {
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
            ret = pref.getLong(key, defaultValue);
        } catch (Exception e) {
            Log.e("PREFERENCE", e.getMessage(), e);
            try {
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
                String strSensorDelay = pref.getString(key, String.valueOf(defaultValue));
                ret = Long.valueOf(strSensorDelay);
            } catch (NumberFormatException ex) {
                Log.e("PREFERENCE", ex.getMessage(), ex);
                ret = defaultValue;
            }
        }
        return ret;
    }

    /**
     * 設定から浮動小数点数を取得する
     *
     * @param key          設定キー
     * @param defaultValue 初期値
     * @return 設定値または初期値
     */
    private float getPrefFloat(String key, float defaultValue) {
        float ret = defaultValue;
        try {
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
            ret = pref.getFloat(key, defaultValue);
        } catch (Exception e) {
            Log.e("PREFERENCE", e.getMessage(), e);
            try {
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
                String strSensorDelay = pref.getString(key, String.valueOf(defaultValue));
                ret = Float.valueOf(strSensorDelay);
            } catch (NumberFormatException ex) {
                Log.e("PREFERENCE", ex.getMessage(), ex);
                ret = defaultValue;
            }
        }
        return ret;
    }

    /**
     * 初期化
     */
    private void initialize() {
        // 位置情報はパーミションチェック後
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // ロケーションマネージャ
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            // プロバイダ
            Criteria criteria = new Criteria();
            criteria.setAccuracy(getPrefInt(LOCATION_ACCURACY_KEY, Criteria.ACCURACY_HIGH));
            criteria.setPowerRequirement(getPrefInt(LOCATION_POWER_KEY, Criteria.NO_REQUIREMENT));
            String provider = locationManager.getBestProvider(criteria, true);
            long minTime = getPrefLong(MIN_TIME_KEY, 1000L * 1L);
            float minDistance = getPrefFloat(MIN_DISTANCE_KEY, 1.0F);
            // ロケーション
            locationManager.requestLocationUpdates(provider, minTime, minDistance, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
