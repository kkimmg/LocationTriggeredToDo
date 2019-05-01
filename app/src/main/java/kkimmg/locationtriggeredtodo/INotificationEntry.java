package kkimmg.locationtriggeredtodo;

import java.io.Serializable;

/**
 * 通知情報
 */
public interface INotificationEntry extends Serializable {
    /**
     * デフォルトの振動パターン
     */
    public static final long[] DEFAULT_PATTERN = {100, 100, 100, 100};
    /**
     * 有効
     */
    public static final int CHECK_ENABLED = 1;
    /**
     * 無効
     */
    public static final int CHECK_DISABLED = 0;
    /**
     * デフォルトの通知ではありません
     */
    public static final int IS_NOT_DEFAULT_NOTIFICATION = 0;
    /**
     * デフォルトの通知です
     */
    public static final int IS_DEFAULT_NOTIFICATION = 1;
    /**
     * まだ通知されていない
     */
    public static final int STATUS_NOT_YET = 0;
    /**
     * 通知中
     */
    public static final int STATUS_NOTICING = 1;
    /**
     * スヌーズ
     */
    public static final int STATUS_SNOOZE = 2;
    /**
     * 通知済
     */
    public static final int STATUS_NOTICED = 3;
    /**
     * 削除済
     */
    public static final int STATUS_DELETED = 4;
    /**
     * 範囲内に入ったとき通知する
     */
    public static final int TIMING_APPROACHING = 2;
    /**
     * 範囲外に出たとき通知する
     */
    public static final int TIMING_LEAVING = 1;

    /**
     * デフォルトの通知ですか？
     *
     * @return はい・いいえ
     */
    public int getDefaultNotification();

    /**
     * デフォルトの通知ですか？
     *
     * @param defaultNotification はい・いいえ
     */
    public void setDefaultNotification(int defaultNotification);

    /**
     * ID
     *
     * @return ID
     */
    public long getId();

    /**
     * ID
     *
     * @param id ID
     */
    public void setId(long id);

    /**
     * 場所のID
     *
     * @return 場所のID
     */
    public long getLocationId();

    /**
     * 場所のID
     *
     * @param locationId 場所のID
     */
    public void setLocationId(long locationId);

    /**
     * 通知するタイミング
     *
     * @return TIMING_APPROACHING/TIMING_LEAVING
     */
    public int getNotificateTiming();

    /**
     * 通知するタイミング
     *
     * @param notificationTiming TIMING_APPROACHING/TIMING_LEAVING
     */
    public void setNotificateTiming(int notificationTiming);

    /**
     * 通知のデフォルトプロパティ
     *
     * @return 通知のデフォルトプロパティ
     */
    public int getDefaults();

    /**
     * 通知のデフォルトプロパティ
     *
     * @param defaults 通知のデフォルトプロパティ
     */
    public void setDefaults(int defaults);

    /**
     * 再生する音声のUri
     *
     * @return 再生する音声のID
     */
    public String getSoundId();

    /**
     * 再生する音声のUri
     *
     * @param soundId 再生する音声のID
     */
    public void setSoundId(String soundId);

    /**
     * ライトの色
     *
     * @return ライトの色
     */
    public int getLightArgb();

    /**
     * ライトの色
     *
     * @param lightArgb ライトの色
     */
    public void setLightArgb(int lightArgb);

    /**
     * ライトの点灯時間
     *
     * @return ライトの点灯時間
     */
    public int getLightOnMs();

    /**
     * ライトの点灯時間
     *
     * @param lightOnMs ライトの点灯時間
     */
    public void setLightOnMs(int lightOnMs);

    /**
     * ライトの消灯時間
     *
     * @return ライトの消灯時間
     */
    public int getLightOffMs();

    /**
     * ライトの消灯時間
     *
     * @param lightOffMs ライトの消灯時間
     */
    public void setLightOffMs(int lightOffMs);

    /**
     * 振動パターン
     *
     * @return 振動パターン
     */
    public long[] getPattern();

    /**
     * 振動パターン
     *
     * @param pattern 振動パターン
     */
    public void setPattern(long[] pattern);

    /**
     * 状態
     *
     * @return 状態
     */
    public int getStatus();

    /**
     * ステータス
     *
     * @param status ステータス
     */
    public void setStatus(int status);

    /**
     * 範囲内化どうか判定するための距離
     *
     * @return 距離(m)
     */
    public double getDistance();

    /**
     * 範囲内化どうか判定するための距離
     *
     * @param distance 距離(m)
     */
    public void setDistance(double distance);

    /**
     * ライトを使う
     *
     * @return 使う／使わない
     */
    public int getLight();

    /**
     * ライトを使う
     *
     * @param light 使う／使わない
     */
    public void setLight(int light);

    /**
     * サウンドを使う
     *
     * @return 使う／使わない
     */
    public int getSound();

    /**
     * サウンドを使う
     *
     * @param sound 使う／使わない
     */
    public void setSound(int sound);

    /**
     * 振動を使う
     *
     * @return 使う／使わない
     */
    public int getVibration();

    /**
     * 振動を使う
     *
     * @param vibration 使う／使わない
     */
    public void setVibration(int vibration);
}
