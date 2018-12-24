package kkimmg.locationtriggeredtodo;

import java.util.ArrayList;

/**
 * タスク
 */
public class DefaultTodoEntry extends ArrayList<ILocationEntry> implements IToDoEntry {
    private long id = 0, calendarId, eventId;
    private String title, description;
    private long startMills, endMills;
    private int allDay, syncType, status;

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
     * 件名の取得
     *
     * @return 件名
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * 件名
     *
     * @param title 件名
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 説明の取得
     *
     * @return 説明
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * 説明
     *
     * @param description 説明
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * タスクの開始日の取得
     *
     * @return タスクの開始日
     */
    @Override
    public long getStartMills() {
        return startMills;
    }

    /**
     * タスクの開始日
     *
     * @param startMills タスクの開始日
     */
    public void setStartMills(long startMills) {
        this.startMills = startMills;
    }

    /**
     * タスクの終了日の取得
     *
     * @return タスクの終了日
     */
    @Override
    public long getEndMills() {
        return endMills;
    }

    /**
     * タスクの終了日
     *
     * @param endMills タスクの終了日
     */
    public void setEndMills(long endMills) {
        this.endMills = endMills;
    }

    /**
     * 終日タスクかどうか
     *
     * @return 1:終日タスク<br>開始終了のあるタスク
     */
    @Override
    public int getAllDay() {
        return allDay;
    }

    /**
     * 終日タスクかどうか
     *
     * @param allDay 終日タスクかどうか
     */
    public void setAllDay(int allDay) {
        this.allDay = allDay;
    }

    /**
     * グーグルカレンダーから取り込んだタスクかどうか
     *
     * @return 1:取込タスク<br>0:単独タスク
     */
    @Override
    public int getSyncType() {
        return syncType;
    }

    /**
     * グーグルカレンダーから取り込んだタスクかどうか
     *
     * @param syncType グーグルカレンダーから取り込んだタスクかどうか
     */
    public void setSyncType(int syncType) {
        this.syncType = syncType;
    }

    /**
     * グーグルのカレンダーID
     *
     * @return カレンダーID
     */
    @Override
    public long getCalendarId() {
        return calendarId;
    }

    /**
     * グーグルのカレンダーID
     *
     * @param calendarId グーグルのカレンダーID
     */
    public void setCalendarId(long calendarId) {
        this.calendarId = calendarId;
    }

    /**
     * グーグルのイベントID
     *
     * @return イベントID
     */
    @Override
    public long getEventId() {
        return eventId;
    }

    /**
     * グーグルのイベントID
     *
     * @param eventId グーグルのイベントID
     */
    public void setEventId(long eventId) {
        this.eventId = eventId;
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
