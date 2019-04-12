package kkimmg.locationtriggeredtodo;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;

public class SoundListAdapter extends ArrayAdapter<SoundListAdapter.SoundListItem> {
    /**
     * アダプタ
     *
     * @param context  コンテキスト
     * @param resource リソースID
     */
    public SoundListAdapter(Context context, int resource) {
        super(context, resource);
        createInnerList(context);
    }

    /**
     * リストを作成する
     *
     * @param context コンテキスト
     */
    void createInnerList(Context context) {
        String[] columns = new String[]{MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE};
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, columns, MediaStore.Audio.Media.IS_NOTIFICATION + " = ?", new String[]{"true"}, null);
        while (cursor.moveToNext()) {
            SoundListItem item = new SoundListItem(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)), cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            this.add(item);
        }
        cursor.close();
    }

    /**
     * リストアイテム
     */
    public static class SoundListItem {
        private String soundId;
        private String soundTitle;

        /**
         * コンストラクタ
         *
         * @param soundId   ID
         * @param soundText タイトル
         */
        public SoundListItem(String soundId, String soundText) {
            this.soundId = soundId;
            this.soundTitle = soundText;
        }

        /**
         * ID
         *
         * @return ID
         */
        public String getSoundId() {
            return soundId;
        }

        /**
         * ID
         *
         * @param soundId ID
         */
        public void setSoundId(String soundId) {
            this.soundId = soundId;
        }

        /**
         * タイトル
         *
         * @return タイトル
         */
        public String getSoundTitle() {
            return soundTitle;
        }

        /**
         * タイトル
         *
         * @param soundTitle タイトル
         */
        public void setSoundTitle(String soundTitle) {
            this.soundTitle = soundTitle;
        }
    }

}
