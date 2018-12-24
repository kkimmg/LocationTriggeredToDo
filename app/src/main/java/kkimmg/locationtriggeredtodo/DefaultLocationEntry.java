package kkimmg.locationtriggeredtodo;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * 場所
 */
public class DefaultLocationEntry extends ArrayList<IAlarmEntry> implements ILocationEntry {
    /**
     * ToDoId
     */
    private long toDoId;
    /**
     * ID
     */
    private long id = 0;
    /**
     * 名前
     */
    private String name;
    /**
     * 高度,緯度,経度,判定する距離
     */
    private double latitude, longitude;
    /**
     * 状態
     */
    private int status;

    /**
     * 範囲内にあるか？
     * あれば、対象の（通知前の）アラームをリストで返却する
     *
     * @param location 位置
     * @return アラームのリスト
     */
    @Override
    public List<IAlarmEntry> getAlermEntries(Location location) {
        List<IAlarmEntry> ret = new ArrayList<>();
        for (IAlarmEntry cld : this) {
            if (cld.getStatus() == IAlarmEntry.STATUS_NOT_YET) {
                if (isNearBy(location, cld.getDistance())) {
                    ret.add(cld);
                }
            }
        }
        return ret;
    }

    /**
     * ToDoId
     */
    @Override
    public long getToDoId() {
        return toDoId;
    }

    /**
     * ToDoId
     */
    public void setToDoId(long toDoId) {
        this.toDoId = toDoId;
    }

    /**
     * IDの取得
     *
     * @return ID
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     * IDのセット
     *
     * @param id ID
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * 名前
     *
     * @return 名前
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * 名前のセット
     *
     * @param name 名前
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 範囲内にあるか？
     *
     * @param location 位置
     * @param distance 範囲内かどうか判定する距離
     * @return 範囲内にあればtrue, なければfalse
     */
    private boolean isNearBy(Location location, double distance) {
        boolean ret = false;
        float[] work = new float[2];
        Location.distanceBetween(getLatitude(), getLongitude(), location.getLatitude(), location.getLongitude(), work);
        if ((double) work[0] < distance) {
            ret = true;
        }
        return ret;
    }

    /**
     * 一番近くのロケーションを返却する
     *
     * @param location ロケーション
     * @return ロケーション判定クラス
     */
    @Override
    public ILocationEntry getNearest(Location location) {
        return this;
    }

    /**
     * 緯度の取得
     *
     * @return 緯度
     */
    @Override
    public double getLatitude() {
        return latitude;
    }

    /**
     * 緯度のセット
     *
     * @param latitude 緯度
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * 経度の取得
     *
     * @return 経度
     */
    @Override
    public double getLongitude() {
        return longitude;
    }

    /**
     * 経度のセット
     *
     * @param longitude 経度
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * タスクの状態
     *
     * @return 0:オープン<br>1:クローズ
     */
    @Override
    public int getStatus() {
        return status;
    }

    /**
     * タスクの状態
     *
     * @param status タスクの状態
     */
    public void setStatus(int status) {
        this.status = status;
    }
}
