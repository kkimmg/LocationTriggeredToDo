package kkimmg.locationtriggeredtodo;

import android.location.Location;

import java.io.Serializable;
import java.util.List;

/**
 * ロケーション
 */
public interface ILocationEntry extends Serializable, List<IAlarmEntry> {
    /**
     * 削除されていない
     */
    public static final int STATUS_NOT_DELETED = 0;
    /**
     * 削除済
     */
    public static final int STATUS_DELETED = 1;
    /**
     * IDの取得
     *
     * @return ID
     */
    public long getId();

    /**
     * 名前
     *
     * @return 名前
     */
    public String getName();

    /**
     * 範囲内にあるか？
     * あれば、対象の（通知前の）アラームをリストで返却する
     *
     * @param location 位置
     * @return アラームのリスト
     */
    public List<IAlarmEntry> getAlermEntries(Location location);

    /**
     * ID
     *
     * @param id ID
     */
    public void setId(long id);

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
     * 経度の取得
     *
     * @return 経度
     */
    public double getLongitude();

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
}
