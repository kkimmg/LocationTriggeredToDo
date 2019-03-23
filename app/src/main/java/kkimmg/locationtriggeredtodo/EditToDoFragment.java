package kkimmg.locationtriggeredtodo;

import android.app.Notification;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditToDoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditToDoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditToDoFragment extends Fragment {
    /**
     * ステータス：新規
     */
    public static final int STATUS_NEW = 0;
    /**
     * ステータス：編集
     */
    public static final int STATUS_EDIT = 1;
    /**
     * ステータス：表示
     */
    public static final int STATUS_SHOW = 2;


    private static final String ARG_STATUS = "status";
    private static final String ARG_ID = "id";

    private int mStatus;
    private long mId;

    /**
     * メディア配列
     */
    private String[][] mediaArray;

    /**
     * 日付変換文字列
     *
     * @return 日付変換文字列
     */
    private static String getDateFormat() {
        return "yyyy/MM/dd";
    }

    /**
     * ToDoエントリ
     */
    private IToDoEntry mToDoEntry;

    /**
     * 時刻変換文字列
     *
     * @return 時刻変換文字列
     */
    private static String getTimeFormat() {
        return "kk:mm:ss";
    }

    private OnFragmentInteractionListener mListener;

    public EditToDoFragment() {
        // Required empty public constructor
    }

    /**
     * フラグメントのインスタンス作成
     *
     * @param status ステータス
     * @param id     ID
     * @return インスタンス.
     */
    public static EditToDoFragment newInstance(int status, long id) {
        EditToDoFragment fragment = new EditToDoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_STATUS, status);
        args.putLong(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStatus = getArguments().getInt(ARG_STATUS);
            mId = getArguments().getLong(ARG_ID);

            switch (mStatus) {
                case STATUS_NEW:
                    mToDoEntry = new DefaultTodoEntry();
                    break;
                case STATUS_EDIT:
                case STATUS_SHOW:
                    LocationDrivenDao dao = new LocationDrivenDao(getContext());
                    mToDoEntry = dao.getToDo(mId);
                    break;
            }
        }
    }

    /**
     * UIに反映する
     *
     * @param view      ビュー
     * @param toDoEntry TODO
     */
    private void readFromToDoEntry(View view, IToDoEntry toDoEntry) {
        EditText txtTitle = view.findViewById(R.id.txtEditToDoTitle);
        EditText txtDescription = view.findViewById(R.id.txtEditToDoDescription);
        CheckBox chkAllDay = view.findViewById(R.id.chkEditToDoAllDay);
        EditText txtStartDate = view.findViewById(R.id.txtEditToDoStartDate);
        EditText txtStartTime = view.findViewById(R.id.txtEditToDoStartTime);
        EditText txtEndDate = view.findViewById(R.id.txtEditToDoEndDate);
        EditText txtEndTime = view.findViewById(R.id.txtEditToDoEndTime);
        CheckBox chkClosed = view.findViewById(R.id.chkToDoClosed);
        EditText txtLocation = view.findViewById(R.id.txtToDoLocationName);
        Spinner spnAlarmTiming = view.findViewById(R.id.spnEditToDoAlarmTiming);
        EditText txtDistance = view.findViewById(R.id.txtEditToDoDistance);
        CheckBox chkVibration = view.findViewById(R.id.chkEditToDoVibration);
        CheckBox chkSound = view.findViewById(R.id.chkEditToDoSound);
        Spinner spnSound = view.findViewById(R.id.spnEditToDoSound);
        CheckBox chkLight = view.findViewById(R.id.chkEditToDoLight);

        txtTitle.setText(toDoEntry.getTitle());
        txtDescription.setText(toDoEntry.getDescription());
        if (toDoEntry.getStatus() == IToDoEntry.STATUS_CLOSED) {
            chkClosed.setChecked(true);
        } else {
            chkClosed.setChecked(false);
        }
        if (toDoEntry.getAllDay() == IToDoEntry.SCD_ALLDAY) {
            chkAllDay.setChecked(true);
        } else {
            chkAllDay.setChecked(false);
        }
        if (toDoEntry.getStartMills() > 0) {
            txtStartDate.setText(DateFormat.format(getDateFormat(), toDoEntry.getStartMills()));
            txtStartTime.setText(DateFormat.format(getTimeFormat(), toDoEntry.getStartMills()));
        } else {
            txtStartDate.setText("");
            txtStartTime.setText("");
        }
        if (toDoEntry.getEndMills() > 0) {
            txtEndDate.setText(DateFormat.format(getDateFormat(), toDoEntry.getEndMills()));
            txtEndTime.setText(DateFormat.format(getTimeFormat(), toDoEntry.getEndMills()));
        } else {
            txtEndDate.setText("");
            txtEndTime.setText("");
        }
        if (toDoEntry.size() > 0) {
            ILocationEntry location = toDoEntry.get(0);
            txtLocation.setText(location.getName());
            if (location.size() > 0) {
                INotificationEntry alarmEntry = location.get(0);
                if (alarmEntry.getNotificateTiming() == INotificationEntry.TIMING_APPROACHING) {
                    spnAlarmTiming.setSelection(1);
                } else {
                    spnAlarmTiming.setSelection(2);
                }
                txtDistance.setText(String.valueOf(alarmEntry.getDistance()));
                if (0 != (alarmEntry.getDefaults() & Notification.DEFAULT_VIBRATE)) {
                    chkVibration.setChecked(true);
                } else {
                    chkVibration.setChecked(false);
                }
                if (0 != (alarmEntry.getDefaults() & Notification.DEFAULT_SOUND)) {
                    chkSound.setChecked(true);
                    if (mediaArray != null && mediaArray.length > 0 && alarmEntry.getSound() != null) {
                        for (int i = 0; i < mediaArray[1].length; i++) {
                            if (alarmEntry.getSound().equals(mediaArray[1][i])) {
                                spnSound.setSelection(i);
                            }
                        }
                    }
                }
                if (0 != (alarmEntry.getDefaults() & Notification.DEFAULT_LIGHTS)) {
                    chkLight.setChecked(true);
                } else {
                    chkLight.setChecked(false);
                }
            } else {
                spnAlarmTiming.setSelection(0);
                chkVibration.setChecked(false);
                chkLight.setChecked(false);
            }
        } else {
            txtLocation.setText("");
            spnAlarmTiming.setSelection(0);
            chkVibration.setChecked(false);
            chkLight.setChecked(false);
        }
    }

    /**
     * 画面の値から項目の有効・無効を設定する
     *
     * @param view ビュー
     */
    private void setEnabledFromValue(View view) {
        EditText txtTitle = view.findViewById(R.id.txtEditToDoTitle);
        EditText txtDescription = view.findViewById(R.id.txtEditToDoDescription);
        CheckBox chkAllDay = view.findViewById(R.id.chkEditToDoAllDay);
        EditText txtStartDate = view.findViewById(R.id.txtEditToDoStartDate);
        EditText txtStartTime = view.findViewById(R.id.txtEditToDoStartTime);
        EditText txtEndDate = view.findViewById(R.id.txtEditToDoEndDate);
        EditText txtEndTime = view.findViewById(R.id.txtEditToDoEndTime);
        CheckBox chkClosed = view.findViewById(R.id.chkToDoClosed);
        EditText txtLocation = view.findViewById(R.id.txtToDoLocationName);
        Spinner spnAlarmTiming = view.findViewById(R.id.spnEditToDoAlarmTiming);
        EditText txtDistance = view.findViewById(R.id.txtEditToDoDistance);
        CheckBox chkVibration = view.findViewById(R.id.chkEditToDoVibration);
        CheckBox chkSound = view.findViewById(R.id.chkEditToDoSound);
        Spinner spnSound = view.findViewById(R.id.spnEditToDoSound);
        CheckBox chkLight = view.findViewById(R.id.chkEditToDoLight);
        Spinner spnEditToDoOptions = view.findViewById(R.id.spnEditToDoOptions);

        {
            txtTitle.setEnabled(!chkClosed.isChecked());
            txtDescription.setEnabled(!chkClosed.isChecked());
            chkAllDay.setEnabled(!chkClosed.isChecked());
            txtStartDate.setEnabled(!chkClosed.isChecked());
            txtStartTime.setEnabled(!chkClosed.isChecked());
            txtEndDate.setEnabled(!chkClosed.isChecked());
            txtEndTime.setEnabled(!chkClosed.isChecked());
            txtLocation.setEnabled(!chkClosed.isChecked());
            spnAlarmTiming.setEnabled(!chkClosed.isChecked());
            txtDistance.setEnabled(!chkClosed.isChecked());
            chkVibration.setEnabled(!chkClosed.isChecked());
            chkSound.setEnabled(!chkClosed.isChecked());
            spnSound.setEnabled(!chkClosed.isChecked());
            chkLight.setEnabled(!chkClosed.isChecked());
        }

        if (!chkClosed.isChecked()) {
            if (chkAllDay.isChecked()) {
                txtStartDate.setEnabled(false);
                txtStartDate.setText("");
                txtStartTime.setEnabled(false);
                txtStartTime.setText("");

                txtEndDate.setEnabled(false);
                txtEndDate.setText("");
                txtEndTime.setEnabled(false);
                txtEndTime.setText("");
            } else {
                txtStartDate.setEnabled(true);
                txtStartTime.setEnabled(true);

                txtEndDate.setEnabled(true);
                txtEndTime.setEnabled(true);
            }
        }

        {
            spnSound.setEnabled(chkSound.isChecked());
        }

    }

    private String[][] getSoundArray(Context context) {
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> uris = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.INTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA}, null, null);
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String uritxt = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            titles.add(title);
            uris.add(uritxt);
        }
        String[][] ret = new String[2][];
        ret[0] = titles.toArray(new String[titles.size()]);
        ret[1] = uris.toArray(new String[uris.size()]);
        return ret;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_to_do, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
