package kkimmg.locationtriggeredtodo;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * 場所
 */
public class DefaultLocationEntry extends ArrayList<INotificationEntry> implements ILocationEntry {
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
    private int base;
    private int isDefault;

    /**
     * 範囲内にあるか？
     * あれば、対象の（通知前の）アラームをリストで返却する
     *
     * @param location 位置
     * @return アラームのリスト
     */
    @Override
    public List<INotificationEntry> getNotificationEntries(Location location) {
        List<INotificationEntry> ret = new ArrayList<>();
        for (INotificationEntry cld : this) {
            if (cld.getStatus() == INotificationEntry.STATUS_NOT_YET) {
                if (isNearBy(location, cld.getDistance())) {
                    ret.add(cld);
                }
            }
        }
        return ret;
    }

    @Override
    public int getBaseedOn() {
        return base;
    }

    @Override
    public void setBasedOn(int base) {
        this.base = base;
    }

    @Override
    public int getDefaultLocation() {
        return isDefault;
    }

    @Override
    public void setDefaultLocation(int isDefault) {
        this.isDefault = isDefault;
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

    @Override
    public INotificationEntry getDefaultNotificationEntry() {
        INotificationEntry ret = null;
        for (INotificationEntry notificationEntry: this) {
            if (notificationEntry.getDefaultNotification() == INotificationEntry.IS_DEFAULT_NOTIFICATION) {
                if (ret != null) {
                    ret.setDefaultNotification(INotificationEntry.IS_NOT_DEFAULT_NOTIFICATION);
                }
                ret = notificationEntry;
            }
        }
        return ret;
    }

    @Override
    public INotificationEntry getDefaultNotificationEntry(boolean addFlg) {
        INotificationEntry ret = getDefaultNotificationEntry();
        if (ret == null && addFlg) {
            ret = createLocationEntry();
            ret.setDefaultNotification(INotificationEntry.IS_DEFAULT_NOTIFICATION);
            add(ret);
        }
        return ret;
    }

    /**
     * 通知を作成する
     * @return 通知
     */
    protected INotificationEntry createLocationEntry () {
        return new DefaultNotificationEntry();
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
