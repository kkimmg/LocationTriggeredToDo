package kkimmg.locationtriggeredtodo;

import java.io.Serializable;
import java.util.List;

/**
 * TODOアイテム
 */
public interface IToDoEntry extends Serializable, List<ILocationEntry> {
    /**
     * オープンされたタスク
     */
    public static final int STATUS_OPEN = 0;
    /**
     * クローズされたタスク
     */
    public static final int STATUS_CLOSED = 1;
    /**
     * 削除されたタスク
     */
    public static final int STATUS_DELETED = 2;
    /**
     * 終日タスクではない
     */
    public static final int SCD_NOT_ALLDAY = 0;
    /**
     * 終日タスク
     */
    public static final int SCD_ALLDAY = 1;
    /**
     * 単独のタスク
     */
    public static final int SYNC_NONE = 0;
    /**
     * グーグルと同期するタスク
     */
    public static final int SYMC_GOOGLE = 1;

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
     * 件名の取得
     *
     * @return 件名
     */
    public String getTitle();

    /**
     * 説明の取得
     *
     * @return 説明
     */
    public String getDescription();

    /**
     * タスクの開始日の取得
     *
     * @return タスクの開始日
     */
    public long getStartMills();

    /**
     * タスクの終了日の取得
     *
     * @return タスクの終了日
     */
    public long getEndMills();

    /**
     * 終日タスクかどうか
     *
     * @return 1:終日タスク<br>開始終了のあるタスク
     */
    public int getAllDay();

    /**
     * グーグルカレンダーから取り込んだタスクかどうか
     *
     * @return 1:取込タスク<br>0:単独タスク
     */
    public int getSyncType();

    /**
     * グーグルのカレンダーID
     *
     * @return カレンダーID
     */
    public long getCalendarId();

    /**
     * グーグルのイベントID
     *
     * @return イベントID
     */
    public long getEventId();

    /**
     * タスクの状態
     *
     * @return 0:オープン<br>1:クローズ
     */
    public int getStatus();

    /**
     * ステータス
     *
     * @param status ステータス
     */
    public void setStatus(int status);

    /**
     * 件名の取得
     *
     * @param title 件名
     */
    public String setTitle(String title);

    /**
     * 説明の取得
     *
     * @param description 説明
     */
    public String setDescription(String description);

    /**
     * タスクの開始日の取得
     *
     * @param startMills タスクの開始日
     */
    public long setStartMills(long startMills);

    /**
     * タスクの終了日の取得
     *
     * @param endMills タスクの終了日
     */
    public long setEndMills(long endMills);

    /**
     * 終日タスクかどうか
     *
     * @param allDay 1:終日タスク<br>開始終了のあるタスク
     */
    public int setAllDay(int allDay);

    /**
     * グーグルカレンダーから取り込んだタスクかどうか
     *
     * @param syncType 1:取込タスク<br>0:単独タスク
     */
    public int setSyncType(int syncType);

    /**
     * グーグルのカレンダーID
     *
     * @param calenderId カレンダーID
     */
    public long setCalendarId(long calenderId);

    /**
     * グーグルのイベントID
     *
     * @param eventId イベントID
     */
    public long setEventId(long eventId);
}
