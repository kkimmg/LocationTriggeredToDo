package kkimmg.locationtriggeredtodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO
 */
public class LocationDrivenDao extends SQLiteOpenHelper {
    /**
     * ToDoテーブル
     */
    public static final String TABLE_TODO = "TODO";
    /**
     * 場所テーブル
     */
    public static final String TABLE_LOCATION = "LOCATION";
    /**
     * 通知テーブル
     */
    public static final String TABLE_NOTIFICATION = "NOTIFICATION";
    /**
     * 列名：ID
     */
    public static final String COLUMN_ID = "_id";
    /**
     * 列名：
     */
    public static final String COLUMN_TITLE = "title";
    /**
     * 列名：
     */
    public static final String COLUMN_DESCRIPTION = "description";
    /**
     * 列名：
     */
    public static final String COLUMN_START_MILLS = "start_mills";
    /**
     * 列名：
     */
    public static final String COLUMN_END_MILLS = "end_mills";
    /**
     * 列名：
     */
    public static final String COLUMN_ALL_DAY = "all_day";
    /**
     * 列名：
     */
    public static final String COLUMN_SYNC_TYPE = "sync_type";
    /**
     * 列名：
     */
    public static final String COLUMN_CALENDAR_ID = "calendar_id";
    /**
     * 列名：
     */
    public static final String COLUMN_EVENT_ID = "event_id";
    /**
     * 列名：
     */
    public static final String COLUMN_STATUS = "status";
    /**
     * 列名：
     */
    public static final String COLUMN_TODO_ID = "todo_id";
    /**
     * 列名：
     */
    public static final String COLUMN_NAME = "name";
    /**
     * 列名：
     */
    public static final String COLUMN_LATITUDE = "latitude";
    /**
     * 列名：
     */
    public static final String COLUMN_LONGITUDE = "longitude";
    /**
     * 列名：
     */
    public static final String COLUMN_BASEDON = "basedon";
    /**
     * 列名：
     */
    public static final String COLUMN_DEFAULT_LOCATION = "defaultlocation";
    /**
     * 列名：
     */
    public static final String COLUMN_LOCATION_ID = "location_id";
    /**
     * 列名：
     */
    public static final String COLUMN_NOTIFICATE_TIMING = "alarm_timing";
    /**
     * 列名：
     */
    public static final String COLUMN_DEFAULTS = "defaults";
    /**
     * 列名：
     */
    public static final String COLUMN_SOUND = "sound";
    /**
     * 列名：
     */
    public static final String COLUMN_LIGHT_ARGB = "light_argb";
    /**
     * 列名：
     */
    public static final String COLUMN_LIGHT_ONMS = "light_onms";
    /**
     * 列名：
     */
    public static final String COLUMN_LIGHT_OFFMS = "light_offms";
    /**
     * 列名：
     */
    public static final String COLUMN_NOTIFICATION_STATUS = "status";
    /**
     * 列名：
     */
    public static final String COLUMN_DISTANCE = "distance";
    /**
     * 列名：パターン
     */
    public static final String COLUMN_PATTERN = "pattern";
    /**
     * 列名：パターン
     */
    public static final String COLUMN_DEFAULT_NOTIFICATION = "defaultnotification";
    /**
     * データベースファイル名
     */
    private static final String DATABASE_NAME = "locationdriven.db";
    /**
     * データベースバージョン（レイアウト変更時この数値を変更）</br>
     * 履歴</br>
     * 1:初期バージョン</br>
     */
    private static final int DB_VERSION = 1;
    /**
     * ToDo情報を保持するテーブル
     */
    private static final String SQL_CREATE_TABLE_TODO =
            "CREATE TABLE " + TABLE_TODO + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_TITLE + " TEXT," +
                    COLUMN_DESCRIPTION + " TEXT," +
                    COLUMN_START_MILLS + " INTEGER," +
                    COLUMN_END_MILLS + "endmills INTEGER," +
                    COLUMN_ALL_DAY + " INTEGER," +
                    COLUMN_SYNC_TYPE + " INTEGER," +
                    COLUMN_CALENDAR_ID + " INTEGER," +
                    COLUMN_EVENT_ID + " INTEGER," +
                    COLUMN_STATUS + " INTEGER" +
                    ");";
    /**
     * ロケーション情報を保持するテーブル
     */
    private static final String SQL_CREATE_TABLE_LOCATION =
            "CREATE TABLE " + TABLE_LOCATION + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_TODO_ID + " INTEGER," +
                    COLUMN_BASEDON + " INTEGER," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_LATITUDE + " REAL," +
                    COLUMN_LONGITUDE + " REAL" +
                    ");";
    /**
     * アラーム情報を保持するテーブル
     */
    private static final String SQL_CREATE_TABLE_NOTIFICATION =
            "CREATE TABLE " + TABLE_NOTIFICATION + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_LOCATION_ID + " INTEGER," +
                    COLUMN_NOTIFICATE_TIMING + " INTEGER," +
                    COLUMN_DEFAULTS + " INTEGER," +
                    COLUMN_PATTERN + "TEXT," +
                    COLUMN_SOUND + " TEXT," +
                    COLUMN_LIGHT_ARGB + " INTEGER," +
                    COLUMN_LIGHT_ONMS + " INTEGER," +
                    COLUMN_LIGHT_OFFMS + " INTEGER," +
                    COLUMN_NOTIFICATION_STATUS + " INTEGER," +
                    COLUMN_DISTANCE + " REAL," +
                    COLUMN_DEFAULT_NOTIFICATION + "INTEGER" +
                    ");";
    /**
     * データベースインスタンス
     */
    private SQLiteDatabase db;

    /**
     * コンストラクタ
     *
     * @param context コンテキスト
     */
    public LocationDrivenDao(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
        db = getWritableDatabase();
        onCreate(db);
    }

    /**
     * 文字列からパターン配列へ返還する
     *
     * @param patternStr 文字列
     * @return パターン配列
     */
    public static long[] getPatterns(String patternStr) {
        List<Long> work = new ArrayList<>();
        for (String str : patternStr.split(",")) {
            long val = Long.parseLong(str);
            work.add(val);
        }
        long[] ret = null;
        if (work.size() > 0) {
            ret = new long[work.size()];
            for (int i = 0; i < ret.length; i++) {
                ret[i] = work.get(i).longValue();
            }
        }
        return ret;
    }

    /**
     * ToDoを保存
     *
     * @param toDoEntry ToDo
     */
    public void saveToDoEntry(IToDoEntry toDoEntry) {
        if (toDoEntry.getId() <= 0) {
            if (toDoEntry.getStatus() != IToDoEntry.STATUS_DELETED) {
                insertToDoEntry(toDoEntry);
            }
        } else {
            if (toDoEntry.getStatus() != IToDoEntry.STATUS_DELETED) {
                updateToDoEntry(toDoEntry);
            } else {
                deleteToDoEntry(toDoEntry);
            }
        }
    }

    /**
     * ToDoを保存
     *
     * @param toDoEntry ToDo
     */
    public void insertToDoEntry(IToDoEntry toDoEntry) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, toDoEntry.getTitle());
        cv.put(COLUMN_DESCRIPTION, toDoEntry.getDescription());
        cv.put(COLUMN_CALENDAR_ID, toDoEntry.getCalendarId());
        cv.put(COLUMN_EVENT_ID, toDoEntry.getEventId());
        cv.put(COLUMN_STATUS, toDoEntry.getStatus());
        cv.put(COLUMN_SYNC_TYPE, toDoEntry.getSyncType());
        cv.put(COLUMN_START_MILLS, toDoEntry.getStartMills());
        cv.put(COLUMN_END_MILLS, toDoEntry.getEndMills());
        cv.put(COLUMN_ALL_DAY, toDoEntry.getAllDay());
        long id = db.insert(TABLE_TODO, null, cv);
        toDoEntry.setId(id);

        for (ILocationEntry locationEntry : toDoEntry) {
            locationEntry.setToDoId(id);
            saveLocationEntry(locationEntry);
        }
    }

    /**
     * ToDoを保存
     *
     * @param toDoEntry ToDo
     */
    public void updateToDoEntry(IToDoEntry toDoEntry) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, toDoEntry.getId());
        cv.put(COLUMN_TITLE, toDoEntry.getTitle());
        cv.put(COLUMN_DESCRIPTION, toDoEntry.getDescription());
        cv.put(COLUMN_CALENDAR_ID, toDoEntry.getCalendarId());
        cv.put(COLUMN_EVENT_ID, toDoEntry.getEventId());
        cv.put(COLUMN_STATUS, toDoEntry.getStatus());
        cv.put(COLUMN_SYNC_TYPE, toDoEntry.getSyncType());
        cv.put(COLUMN_START_MILLS, toDoEntry.getStartMills());
        cv.put(COLUMN_END_MILLS, toDoEntry.getEndMills());
        cv.put(COLUMN_ALL_DAY, toDoEntry.getAllDay());
        db.update(TABLE_TODO, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(toDoEntry.getId())});

        for (ILocationEntry locationEntry : toDoEntry) {
            locationEntry.setToDoId(toDoEntry.getId());
            saveLocationEntry(locationEntry);
        }
    }

    /**
     * ToDoを削除
     *
     * @param toDoEntry ToDo
     */
    public void deleteToDoEntry(IToDoEntry toDoEntry) {
        db.delete(TABLE_TODO, COLUMN_ID + " = ?", new String[]{String.valueOf(toDoEntry.getId())});

        for (ILocationEntry locationEntry : toDoEntry) {
            locationEntry.setToDoId(toDoEntry.getId());
            deleteLocationEntry(locationEntry);
        }
    }

    /**
     * 場所を保存
     *
     * @param locationEntry アラーム
     */
    public void saveLocationEntry(ILocationEntry locationEntry) {
        if (locationEntry.getId() <= 0) {
            if (locationEntry.getStatus() != ILocationEntry.STATUS_DELETED) {
                insertLocationEntry(locationEntry);
            }
        } else {
            if (locationEntry.getStatus() != ILocationEntry.STATUS_DELETED) {
                updateLocationEntry(locationEntry);
            } else {
                deleteLocationEntry(locationEntry);
            }
        }
    }

    /**
     * 場所を保存
     *
     * @param locationEntry 場所
     */
    public void insertLocationEntry(ILocationEntry locationEntry) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TODO_ID, locationEntry.getToDoId());
        cv.put(COLUMN_NAME, locationEntry.getName());
        cv.put(COLUMN_LATITUDE, locationEntry.getLatitude());
        cv.put(COLUMN_LONGITUDE, locationEntry.getLongitude());
        cv.put(COLUMN_BASEDON, locationEntry.getBaseedOn());
        cv.put(COLUMN_DEFAULT_LOCATION, locationEntry.getDefaultLocation());
        long id = db.insert(TABLE_LOCATION, null, cv);
        locationEntry.setId(id);

        for (INotificationEntry alermEntry : locationEntry) {
            alermEntry.setLocationId(id);
            saveNotificationEntry(alermEntry);
        }
    }

    /**
     * 場所を保存
     *
     * @param locationEntry 場所
     */
    public void updateLocationEntry(ILocationEntry locationEntry) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, locationEntry.getId());
        cv.put(COLUMN_TODO_ID, locationEntry.getToDoId());
        cv.put(COLUMN_NAME, locationEntry.getName());
        cv.put(COLUMN_LATITUDE, locationEntry.getLatitude());
        cv.put(COLUMN_LONGITUDE, locationEntry.getLongitude());
        cv.put(COLUMN_BASEDON, locationEntry.getBaseedOn());
        cv.put(COLUMN_DEFAULT_LOCATION, locationEntry.getDefaultLocation());
        db.update(TABLE_LOCATION, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(locationEntry.getId())});

        for (INotificationEntry alermEntry : locationEntry) {
            alermEntry.setLocationId(locationEntry.getId());
            saveNotificationEntry(alermEntry);
        }
    }

    /**
     * 場所を削除
     *
     * @param locationEntry 場所
     */
    public void deleteLocationEntry(ILocationEntry locationEntry) {
        db.delete(TABLE_LOCATION, COLUMN_ID + " = ?", new String[]{String.valueOf(locationEntry.getId())});

        for (INotificationEntry alermEntry : locationEntry) {
            alermEntry.setLocationId(locationEntry.getId());
            deleteNotificationEntry(alermEntry);
        }
    }

    /**
     * アラームを保存
     *
     * @param alermEntry アラーム
     */
    public void saveNotificationEntry(INotificationEntry alermEntry) {
        if (alermEntry.getId() <= 0) {
            if (alermEntry.getStatus() != INotificationEntry.STATUS_DELETED) {
                insertNotificationEntry(alermEntry);
            }
        } else {
            if (alermEntry.getStatus() != INotificationEntry.STATUS_DELETED) {
                updateNotificationEntry(alermEntry);
            } else {
                deleteNotificationEntry(alermEntry);
            }
        }
    }

    /**
     * アラームを登録
     *
     * @param alermEntry アラーム
     */
    public void insertNotificationEntry(INotificationEntry alermEntry) {
        ContentValues cvAlerm = new ContentValues();
        cvAlerm.put(COLUMN_LOCATION_ID, alermEntry.getLocationId());
        cvAlerm.put(COLUMN_NOTIFICATE_TIMING, alermEntry.getNotificateTiming());
        cvAlerm.put(COLUMN_DEFAULTS, alermEntry.getDefaults());
        cvAlerm.put(COLUMN_DISTANCE, alermEntry.getDistance());
        cvAlerm.put(COLUMN_NOTIFICATION_STATUS, alermEntry.getStatus());
        cvAlerm.put(COLUMN_SOUND, (alermEntry.getSound()));
        cvAlerm.put(COLUMN_LIGHT_ARGB, alermEntry.getLightArgb());
        cvAlerm.put(COLUMN_LIGHT_ONMS, alermEntry.getLightOnMs());
        cvAlerm.put(COLUMN_LIGHT_OFFMS, alermEntry.getLightOffMs());
        cvAlerm.put(COLUMN_DEFAULT_LOCATION, alermEntry.getDefaultNotification());

        StringBuffer patternBuf = new StringBuffer();
        String cm = "";
        for (long pattern : alermEntry.getPattern()) {
            patternBuf.append(cm);
            patternBuf.append(String.valueOf(pattern));
            cm = ",";
        }
        cvAlerm.put(COLUMN_PATTERN, patternBuf.toString());

        long id = db.insert(TABLE_NOTIFICATION, null, cvAlerm);
        alermEntry.setId(id);
    }

    /**
     * アラームを更新
     *
     * @param alermEntry アラーム
     */
    public void updateNotificationEntry(INotificationEntry alermEntry) {
        ContentValues cvAlerm = new ContentValues();
        cvAlerm.put(COLUMN_ID, alermEntry.getId());
        cvAlerm.put(COLUMN_LOCATION_ID, alermEntry.getLocationId());
        cvAlerm.put(COLUMN_NOTIFICATE_TIMING, alermEntry.getNotificateTiming());
        cvAlerm.put(COLUMN_DEFAULTS, alermEntry.getDefaults());
        cvAlerm.put(COLUMN_DISTANCE, alermEntry.getDistance());
        cvAlerm.put(COLUMN_NOTIFICATION_STATUS, alermEntry.getStatus());
        cvAlerm.put(COLUMN_SOUND, (alermEntry.getSound()));
        cvAlerm.put(COLUMN_LIGHT_ARGB, alermEntry.getLightArgb());
        cvAlerm.put(COLUMN_LIGHT_ONMS, alermEntry.getLightOnMs());
        cvAlerm.put(COLUMN_LIGHT_OFFMS, alermEntry.getLightOffMs());
        cvAlerm.put(COLUMN_DEFAULT_LOCATION, alermEntry.getDefaultNotification());

        StringBuffer patternBuf = new StringBuffer();
        String cm = "";
        for (long pattern : alermEntry.getPattern()) {
            patternBuf.append(cm);
            patternBuf.append(String.valueOf(pattern));
            cm = ",";
        }
        cvAlerm.put(COLUMN_PATTERN, patternBuf.toString());

        db.update(TABLE_NOTIFICATION, cvAlerm, COLUMN_ID + " = ?", new String[]{String.valueOf(alermEntry.getId())});

    }

    /**
     * アラームを削除
     *
     * @param alermEntry アラーム
     */
    public void deleteNotificationEntry(INotificationEntry alermEntry) {
        db.delete(TABLE_NOTIFICATION, COLUMN_ID + " = ?", new String[]{String.valueOf(alermEntry.getId())});
    }

    /**
     * ToDoの取得
     *
     * @param selection 検索条件
     * @param args      検索条件の値
     * @param orderBy   並び替え条件
     * @return ToDo
     */
    public List<IToDoEntry> getToDos(String selection, String[] args, String orderBy) {
        List<IToDoEntry> ret = new ArrayList<>();
        Cursor cursor = db.query(TABLE_TODO, null, selection, args, null, null, orderBy, null);
        while (cursor.moveToNext()) {
            DefaultTodoEntry work = new DefaultTodoEntry();
            work.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
            work.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
            work.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
            work.setCalendarId(cursor.getLong(cursor.getColumnIndex(COLUMN_CALENDAR_ID)));
            work.setEventId(cursor.getLong(cursor.getColumnIndex(COLUMN_EVENT_ID)));
            work.setStartMills(cursor.getLong(cursor.getColumnIndex(COLUMN_START_MILLS)));
            work.setEndMills(cursor.getLong(cursor.getColumnIndex(COLUMN_END_MILLS)));
            work.setAllDay(cursor.getInt(cursor.getColumnIndex(COLUMN_ALL_DAY)));
            work.setSyncType(cursor.getInt(cursor.getColumnIndex(COLUMN_SYNC_TYPE)));
            work.setStatus(cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS)));
            work.addAll(getLocations(work.getId()));
            ret.add(work);
        }
        cursor.close();
        return ret;
    }

    /**
     * ToDoの取得
     *
     * @param id ID
     * @return ToDo
     */
    public IToDoEntry getToDo(long id) {
        DefaultTodoEntry ret = new DefaultTodoEntry();

        Cursor cursor = db.query(TABLE_TODO, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, COLUMN_ID, null);
        if (cursor.moveToNext()) {
            ret.setId(id);
            ret.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
            ret.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
            ret.setCalendarId(cursor.getLong(cursor.getColumnIndex(COLUMN_CALENDAR_ID)));
            ret.setEventId(cursor.getLong(cursor.getColumnIndex(COLUMN_EVENT_ID)));
            ret.setStartMills(cursor.getLong(cursor.getColumnIndex(COLUMN_START_MILLS)));
            ret.setEndMills(cursor.getLong(cursor.getColumnIndex(COLUMN_END_MILLS)));
            ret.setAllDay(cursor.getInt(cursor.getColumnIndex(COLUMN_ALL_DAY)));
            ret.setSyncType(cursor.getInt(cursor.getColumnIndex(COLUMN_SYNC_TYPE)));
            ret.setStatus(cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS)));
            ret.addAll(getLocations(id));
        }
        cursor.close();
        return ret;
    }

    /**
     * 場所カーソルの取得
     * @param selection 検索条件
     * @param values 検索条件の値
     * @return カーソル
     */
    public Cursor getLocationCursor (String selection, String[] values) {
        Cursor cursor = db.query(TABLE_LOCATION, null, selection, values, null, null, COLUMN_ID, null);
        return cursor;
    }

    /**
     * 場所
     *
     * @param todoid ToDoID
     * @return 場所
     */
    public List<ILocationEntry> getLocations(long todoid) {
        List<ILocationEntry> ret = new ArrayList<>();
        Cursor cursor = db.query(TABLE_LOCATION, null, COLUMN_TODO_ID + " = ?", new String[]{String.valueOf(todoid)}, null, null, COLUMN_ID, null);
        while (cursor.moveToNext()) {
            DefaultLocationEntry work = new DefaultLocationEntry();
            work.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
            work.setToDoId(todoid);
            work.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            work.setLatitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUDE)));
            work.setLongitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE)));
            work.setBasedOn(cursor.getInt(cursor.getColumnIndex(COLUMN_BASEDON)));
            work.setDefaultLocation(cursor.getInt(cursor.getColumnIndex(COLUMN_DEFAULT_LOCATION)));
            work.addAll(getNotificationEntries(work.getId()));
            ret.add(work);
        }
        cursor.close();
        return ret;
    }

    /**
     * 場所
     *
     * @param id ID
     * @return 場所
     */
    public ILocationEntry getLocation(long id) {
        DefaultLocationEntry ret = new DefaultLocationEntry();

        Cursor cursor = db.query(TABLE_LOCATION, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, COLUMN_ID, null);
        if (cursor.moveToNext()) {
            ret.setId(id);
            ret.setToDoId(cursor.getLong(cursor.getColumnIndex(COLUMN_TODO_ID)));
            ret.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            ret.setLatitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUDE)));
            ret.setLongitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE)));
            ret.setBasedOn(cursor.getInt(cursor.getColumnIndex(COLUMN_BASEDON)));
            ret.setDefaultLocation(cursor.getInt(cursor.getColumnIndex(COLUMN_DEFAULT_LOCATION)));
            ret.addAll(getNotificationEntries(id));
        }
        cursor.close();
        return ret;
    }

    /**
     * アラームの取得
     *
     * @param locationid 場所ID
     * @return アラーム
     */
    public List<INotificationEntry> getNotificationEntries(long locationid) {
        List<INotificationEntry> ret = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NOTIFICATION, null, COLUMN_LOCATION_ID + " = ?", new String[]{String.valueOf(locationid)}, null, null, COLUMN_ID, null);
        while (cursor.moveToNext()) {
            DefaultNotificationEntry work = new DefaultNotificationEntry();
            work.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
            work.setLocationId(locationid);
            work.setAlarmTiming(cursor.getInt(cursor.getColumnIndex(COLUMN_NOTIFICATION_STATUS)));
            work.setDistance(cursor.getDouble(cursor.getColumnIndex(COLUMN_DISTANCE)));
            work.setStatus(cursor.getInt(cursor.getColumnIndex(COLUMN_NOTIFICATION_STATUS)));
            work.setDefaults(cursor.getInt(cursor.getColumnIndex(COLUMN_DEFAULTS)));
            String uristr = cursor.getString(cursor.getColumnIndex(COLUMN_SOUND));
            if (uristr != null && uristr.trim().length() > 0) {
                work.setSound(uristr);
            }
            work.setLightArgb(cursor.getInt(cursor.getColumnIndex(COLUMN_LIGHT_ARGB)));
            work.setLightOnMs(cursor.getInt(cursor.getColumnIndex(COLUMN_LIGHT_ONMS)));
            work.setLightOffMs(cursor.getInt(cursor.getColumnIndex(COLUMN_LIGHT_OFFMS)));
            work.setDefaultNotification(cursor.getInt(cursor.getColumnIndex(COLUMN_DEFAULT_NOTIFICATION)));

            work.setPattern(getPatterns(cursor.getString(cursor.getColumnIndex(COLUMN_PATTERN))));
            ret.add(work);
        }
        cursor.close();
        return ret;
    }

    /**
     * アラームの取得
     *
     * @param id ID
     * @return アラーム
     */
    public INotificationEntry getNotificationEntry(long id) {
        DefaultNotificationEntry ret = new DefaultNotificationEntry();

        Cursor cursor = db.query(TABLE_NOTIFICATION, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, COLUMN_ID, null);
        if (cursor.moveToNext()) {
            ret.setId(id);
            ret.setLocationId(cursor.getLong(cursor.getColumnIndex(COLUMN_LOCATION_ID)));
            ret.setAlarmTiming(cursor.getInt(cursor.getColumnIndex(COLUMN_NOTIFICATE_TIMING)));
            ret.setDistance(cursor.getDouble(cursor.getColumnIndex(COLUMN_DISTANCE)));
            ret.setStatus(cursor.getInt(cursor.getColumnIndex(COLUMN_NOTIFICATION_STATUS)));
            ret.setDefaults(cursor.getInt(cursor.getColumnIndex(COLUMN_DEFAULTS)));
            String uristr = cursor.getString(cursor.getColumnIndex(COLUMN_SOUND));
            if (uristr != null && uristr.trim().length() > 0) {
                ret.setSound(uristr);
            }
            ret.setLightArgb(cursor.getInt(cursor.getColumnIndex(COLUMN_LIGHT_ARGB)));
            ret.setLightOnMs(cursor.getInt(cursor.getColumnIndex(COLUMN_LIGHT_ONMS)));
            ret.setLightOffMs(cursor.getInt(cursor.getColumnIndex(COLUMN_LIGHT_OFFMS)));
            ret.setPattern(getPatterns(cursor.getString(cursor.getColumnIndex(COLUMN_PATTERN))));
            ret.setDefaultNotification(cursor.getInt(cursor.getColumnIndex(COLUMN_DEFAULT_NOTIFICATION)));
        }
        cursor.close();
        return ret;
    }

    /**
     * クローズ処理
     */
    public void close() {
        try {
            if (db != null && db.isOpen()) {
                db.close();
                db = null;
            }
        } catch (Exception e) {
            Log.e("DAO", e.getMessage(), e);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        close();
        super.finalize();

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(SQL_CREATE_TABLE_TODO);
            sqLiteDatabase.execSQL(SQL_CREATE_TABLE_LOCATION);
            sqLiteDatabase.execSQL(SQL_CREATE_TABLE_NOTIFICATION);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // とりあえず何もしない
    }
}
