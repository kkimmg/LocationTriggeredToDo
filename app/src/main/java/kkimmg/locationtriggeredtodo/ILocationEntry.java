package kkimmg.locationtriggeredtodo;

import android.location.Location;

import java.io.Serializable;
import java.util.List;

/**
 * ロケーション
 */
public interface ILocationEntry extends Serializable, List<INotificationEntry> {
    /**
     * 名前基準
     */
    public static final int BASE_NAME = 0;
    /**
     * 座標基準
     */
    public static final int BASE_LOCATION = 1;
    /**
     * 削除されていない
     */
    public static final int STATUS_NOT_DELETED = 0;
    /**
     * 削除済
     */
    public static final int STATUS_DELETED = 1;
    /**
     * デフォルトのロケーションではありません
     */
    public static final int IS_NOT_DEFAULT_LOCATION = 0;
    /**
     * デフォルトのロケーションです
     */
    public static final int IS_DEFAULT_LOCATION = 1;

    /**
     * 名前基準／場所基準
     *
     * @return 名前基準／場所基準
     */
    public int getBaseedOn();

    /**
     * 名前基準／場所基準
     *
     * @param base 名前基準／場所基準
     */
    public void setBasedOn(int base);

    /**
     * IDの取得
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
     * 名前
     *
     * @return 名前
     */
    public String getName();

    /**
     * 名前
     *
     * @param name 名前
     */
    public void setName(String name);

    /**
     * 範囲内にあるか？
     * あれば、対象の（通知前の）アラームをリストで返却する
     *
     * @param location 位置
     * @return アラームのリスト
     */
    public List<INotificationEntry> getNotificationEntries(Location location);

    /**
     * 一番近くのロケーションを返却する
     *
     * @param location ロケーション
     * @return ロケーション判定クラス
     */
    public ILocationEntry getNearest(Location location);

    /**
     * 緯度の取得
     *
     * @return 緯度
     */
    public double getLatitude();

    /**
     * 緯度の取得
     *
     * @param latitude 緯度
     */
    public void setLatitude(double latitude);

    /**
     * 経度の取得
     *
     * @return 経度
     */
    public double getLongitude();

    /**
     * 経度の取得
     *
     * @param longitude 経度
     */
    public void setLongitude(double longitude);

    /**
     * ToDoへの
     *
     * @return
     */
    public long getToDoId();

    /**
     * ToDoへの
     *
     * @param id ToDoId
     */
    public void setToDoId(long id);

    /**
     * 場所の状態
     *
     * @return 場所の状態
     */
    public int getStatus();

    /**
     * ステータス
     *
     * @param status ステータス
     */
    public void setStatus(int status);

    /**
     * デフォルトのロケーションですか？
     *
     * @return デフオルトかどうか
     */
    public int getDefaultLocation();

    /**
     * デフォルトのロケーションですか？
     *
     * @param isDefault デフオルトかどうか
     */
    public void setDefaultLocation(int isDefault);

    /**
     * デフォルトの通知を取得する
     * @return デフォルトの通知
     */
    public INotificationEntry getDefaultNotificationEntry ();

    /**
     * デフォルトの通知を取得する
     * @param add なければ追加する
     * @return デフォルトの通知
     */
    public INotificationEntry getDefaultNotificationEntry (boolean add);
}
