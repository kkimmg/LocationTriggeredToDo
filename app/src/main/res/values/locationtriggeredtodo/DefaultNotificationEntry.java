package kkimmg.locationtriggeredtodo;

import android.graphics.Color;

public class DefaultNotificationEntry implements INotificationEntry {
    public static final long[] DEFAULT_PATTERN = {0, 1000, 500, 1000, 500, 1000, 500};

    private long id = 0;
    private long locationId;
    private int notificateTiming;
    private int defaults;
    private String soundText;
    private int lightArgb = Color.WHITE, lightOnMs = 100, lightOffMs = 100;
    private long[] pattern = DEFAULT_PATTERN;
    private int status;
    private double distance;
    private int defaultNotification;
    private int light;
    private int sound;
    private int vibration;

    @Override
    public int getDefaultNotification() {
        return defaultNotification;
    }

    @Override
    public void setDefaultNotification(int defaultNotification) {
        this.defaultNotification = defaultNotification;
    }

    /**
     * ID
     *
     * @return ID
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     * ID
     *
     * @param id ID
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * 場所のID
     *
     * @return 場所のID
     */
    @Override
    public long getLocationId() {
        return locationId;
    }

    /**
     * 紐づく場所のID
     *
     * @param locationId 紐づく場所のID
     */
    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    /**
     * 通知するタイミング
     *
     * @return TIMING_APPROACHING/TIMING_LEAVING
     */
    @Override
    public int getNotificateTiming() {
        return notificateTiming;
    }

    /**
     * 通知するタイミング
     *
     * @param notificateTiming 通知するタイミング
     */
    public void setNotificateTiming(int notificateTiming) {
        this.notificateTiming = notificateTiming;
    }

    /**
     * 通知のデフォルトプロパティ
     *
     * @return 通知のデフォルトプロパティ
     */
    @Override
    public int getDefaults() {
        return defaults;
    }

    /**
     * 通知のデフォルトプロパティ
     *
     * @param defaults 通知のデフォルトプロパティ
     */
    public void setDefaults(int defaults) {
        this.defaults = defaults;
    }

    /**
     * 再生する音声のUri
     *
     * @return 再生する音声のUri
     */
    @Override
    public String getSoundId() {
        return soundText;
    }

    /**
     * 再生する音声のUri
     *
     * @param soundId 再生する音声のUri
     */
    public void setSoundId(String soundId) {
        this.soundText = soundId;
    }

    /**
     * ライトの色
     *
     * @return ライトの色
     */
    @Override
    public int getLightArgb() {
        return lightArgb;
    }

    /**
     * ライトの色
     *
     * @param lightArgb ライトの色
     */
    public void setLightArgb(int lightArgb) {
        this.lightArgb = lightArgb;
    }

    /**
     * ライトの点灯時間
     *
     * @return ライトの点灯時間
     */
    @Override
    public int getLightOnMs() {
        return lightOnMs;
    }

    /**
     * ライトの点灯時間
     *
     * @param lightOnMs ライトの点灯時間
     */
    public void setLightOnMs(int lightOnMs) {
        this.lightOnMs = lightOnMs;
    }

    /**
     * ライトの消灯時間
     *
     * @return ライトの消灯時間
     */
    @Override
    public int getLightOffMs() {
        return lightOffMs;
    }

    /**
     * ライトの消灯時間
     *
     * @param lightOffMs ライトの消灯時間
     */
    public void setLightOffMs(int lightOffMs) {
        this.lightOffMs = lightOffMs;
    }

    /**
     * 振動パターン
     *
     * @return 振動パターン
     */
    @Override
    public long[] getPattern() {
        return pattern;
    }

    public void setPattern(long[] pattern) {
        this.pattern = pattern;
    }

    /**
     * 状態
     *
     * @return 状態
     */
    @Override
    public int getStatus() {
        return status;
    }

    /**
     * 状態
     *
     * @param status 状態
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 範囲内化どうか判定するための距離
     *
     * @return 距離(m)
     */
    @Override
    public double getDistance() {
        return distance;
    }

    /**
     * 範囲内化どうか判定するための距離
     *
     * @param distance 距離(m)
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public int getSound() {
        return sound;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }

    public int getVibration() {
        return vibration;
    }

    public void setVibration(int vibration) {
        this.vibration = vibration;
    }
}
