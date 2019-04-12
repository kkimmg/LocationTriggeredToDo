package kkimmg.locationtriggeredtodo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ToDoEntryItemFragment extends Fragment {
    public static final String TAG = "TODOENTRYITEMFLAGMENT";

    private static final String ARG_COLUMN_COUNT = "column-count";
    /**
     * デフォルトの日付書式
     */
    private static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd";
    /**
     * 24時間表記
     */
    private static final String TIME_24 = "24";
    /**
     * 24時間表記書式
     */
    private static final String TIME_24_FORMAT = "H:mm";
    /**
     * 12時間表記書式
     */
    private static final String TIME_12_FORMAT = "h:mm a";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    /**
     * TODOエントリ
     */
    private IToDoEntry toDoEntry;
    /**
     * 日付時刻書式
     */
    private SimpleDateFormat dateTimeFormat;
    /**
     * 日付書式
     */
    private SimpleDateFormat dateFormat;
    /**
     * 時刻書式
     */
    private SimpleDateFormat timeFormat;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ToDoEntryItemFragment() {
    }

    @SuppressWarnings("unused")
    public static ToDoEntryItemFragment newInstance(int columnCount) {
        ToDoEntryItemFragment fragment = new ToDoEntryItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    /**
     * タスクの値を各コンポーネントに展開する
     *
     * @param act       アクティビティ
     * @param toDoEntry タスク
     */
    private void setToComponents(Activity act, IToDoEntry toDoEntry) {
        final Button btnEditToDoCancel = act.findViewById(R.id.btnEditToDoCancel);
        final TextView tvEditTodoTitle = act.findViewById(R.id.tvEditTodoTitle);
        final EditText txtEditToDoTitle = act.findViewById(R.id.txtEditToDoTitle);
        final TextView tvEditToDoDescription = act.findViewById(R.id.tvEditToDoDescription);
        final EditText txtEditToDoDescription = act.findViewById(R.id.txtEditToDoDescription);
        final CheckBox chkEditToDoAllDay = act.findViewById(R.id.chkEditToDoAllDay);
        final TextView tvEditToDoStart = act.findViewById(R.id.tvEditToDoStart);
        final EditText txtEditToDoStartDate = act.findViewById(R.id.txtEditToDoStartDate);
        final EditText txtEditToDoStartTime = act.findViewById(R.id.txtEditToDoStartTime);
        final Button btnEditToDoStart = act.findViewById(R.id.btnEditToDoStart);
        final TextView tvEditToDoEnd = act.findViewById(R.id.tvEditToDoEnd);
        final EditText txtEditToDoEndDate = act.findViewById(R.id.txtEditToDoEndDate);
        final EditText txtEditToDoEndTime = act.findViewById(R.id.txtEditToDoEndTime);
        final Button btnEditToDoEnd = act.findViewById(R.id.btnEditToDoEnd);
        final CheckBox chkEditClosed = act.findViewById(R.id.chkEditClosed);
        final TextView tvEditToDoLocation = act.findViewById(R.id.tvEditToDoLocation);
        final EditText txtEditToDoLocation = act.findViewById(R.id.txtEditToDoLocation);
        final Button btnEditToDoLocationMore = act.findViewById(R.id.btnEditToDoLocationMore);
        final TextView tvEditToDoAlarm = act.findViewById(R.id.tvEditToDoAlarm);
        final Spinner spnEditToDoAlarmTiming = act.findViewById(R.id.spnEditToDoAlarmTiming);
        final EditText txtEditToDoDistance = act.findViewById(R.id.txtEditToDoDistance);
        final TextView tvEditToDoMeters = act.findViewById(R.id.tvEditToDoMeters);
        final CheckBox chkEditToDoVibration = act.findViewById(R.id.chkEditToDoVibration);
        final CheckBox chkEditToDoSound = act.findViewById(R.id.chkEditToDoSound);
        final Spinner spnEditToDoSound = act.findViewById(R.id.spnEditToDoSound);
        final CheckBox chkEditToDoLight = act.findViewById(R.id.chkEditToDoLight);
        final Spinner spnEditToDoOptions = act.findViewById(R.id.spnEditToDoOptions);
        final Button btnEditToDoOK = act.findViewById(R.id.btnEditToDoOK);

        txtEditToDoTitle.setText(toDoEntry.getTitle());
        txtEditToDoDescription.setText(toDoEntry.getDescription());
        chkEditToDoAllDay.setChecked(toDoEntry.getAllDay() == IToDoEntry.SCD_ALLDAY);
        if (toDoEntry.getStartMills() > 0) {
            txtEditToDoStartDate.setText(getLong2DateString(act, toDoEntry.getStartMills()));
            txtEditToDoStartTime.setText(getLong2TimeString(act, toDoEntry.getStartMills()));
        }
        if (toDoEntry.getEndMills() > 0) {
            txtEditToDoEndDate.setText(getLong2DateString(act, toDoEntry.getEndMills()));
            txtEditToDoEndTime.setText(getLong2TimeString(act, toDoEntry.getEndMills()));
        }
        chkEditClosed.setChecked(toDoEntry.getStartMills() == IToDoEntry.STATUS_CLOSED);

        ILocationEntry locationEntry = toDoEntry.getDefaultLocationEntry(true);
        txtEditToDoLocation.setText(locationEntry.getName());
        INotificationEntry alarmEntry = locationEntry.getDefaultNotificationEntry(true);
        txtEditToDoDistance.setText(String.valueOf(alarmEntry.getDistance()));
        chkEditToDoVibration.setChecked(alarmEntry.getVibration() == INotificationEntry.CHECK_ENABLED);
        chkEditToDoSound.setChecked(alarmEntry.getSound() == INotificationEntry.CHECK_ENABLED);
        chkEditToDoLight.setChecked(alarmEntry.getLight() == INotificationEntry.CHECK_ENABLED);


    }

    /**
     * イベントとかの対応
     *
     * @param act       アクティビティ
     * @param toDoEntry タスク
     */
    private void setUoComponents(final Activity act, final IToDoEntry toDoEntry) {
        final Button btnEditToDoCancel = act.findViewById(R.id.btnEditToDoCancel);
        final TextView tvEditTodoTitle = act.findViewById(R.id.tvEditTodoTitle);
        final EditText txtEditToDoTitle = act.findViewById(R.id.txtEditToDoTitle);
        final TextView tvEditToDoDescription = act.findViewById(R.id.tvEditToDoDescription);
        final EditText txtEditToDoDescription = act.findViewById(R.id.txtEditToDoDescription);
        final CheckBox chkEditToDoAllDay = act.findViewById(R.id.chkEditToDoAllDay);
        final TextView tvEditToDoStart = act.findViewById(R.id.tvEditToDoStart);
        final EditText txtEditToDoStartDate = act.findViewById(R.id.txtEditToDoStartDate);
        final EditText txtEditToDoStartTime = act.findViewById(R.id.txtEditToDoStartTime);
        final Button btnEditToDoStart = act.findViewById(R.id.btnEditToDoStart);
        final TextView tvEditToDoEnd = act.findViewById(R.id.tvEditToDoEnd);
        final EditText txtEditToDoEndDate = act.findViewById(R.id.txtEditToDoEndDate);
        final EditText txtEditToDoEndTime = act.findViewById(R.id.txtEditToDoEndTime);
        final Button btnEditToDoEnd = act.findViewById(R.id.btnEditToDoEnd);
        final CheckBox chkEditClosed = act.findViewById(R.id.chkEditClosed);
        final TextView tvEditToDoLocation = act.findViewById(R.id.tvEditToDoLocation);
        final EditText txtEditToDoLocation = act.findViewById(R.id.txtEditToDoLocation);
        final Button btnEditToDoLocationMore = act.findViewById(R.id.btnEditToDoLocationMore);
        final TextView tvEditToDoAlarm = act.findViewById(R.id.tvEditToDoAlarm);
        final Spinner spnEditToDoAlarmTiming = act.findViewById(R.id.spnEditToDoAlarmTiming);
        final EditText txtEditToDoDistance = act.findViewById(R.id.txtEditToDoDistance);
        final TextView tvEditToDoMeters = act.findViewById(R.id.tvEditToDoMeters);
        final CheckBox chkEditToDoVibration = act.findViewById(R.id.chkEditToDoVibration);
        final CheckBox chkEditToDoSound = act.findViewById(R.id.chkEditToDoSound);
        final Spinner spnEditToDoSound = act.findViewById(R.id.spnEditToDoSound);
        final CheckBox chkEditToDoLight = act.findViewById(R.id.chkEditToDoLight);
        final Spinner spnEditToDoOptions = act.findViewById(R.id.spnEditToDoOptions);
        final Button btnEditToDoOK = act.findViewById(R.id.btnEditToDoOK);
        btnEditToDoOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDoEntry.setTitle(txtEditToDoTitle.getText().toString());
                toDoEntry.setDescription(txtEditToDoDescription.getText().toString());
                toDoEntry.setAllDay((chkEditToDoAllDay.isSelected() ? IToDoEntry.SCD_ALLDAY : IToDoEntry.SCD_NOT_ALLDAY));
                if (chkEditToDoAllDay.isSelected()) {
                    toDoEntry.setStartMills(0);
                    toDoEntry.setEndMills(0);
                } else {
                    {
                        // 開始
                        String txtDate = txtEditToDoStartDate.getText().toString();
                        String txtTime = txtEditToDoStartTime.getText().toString();
                        toDoEntry.setStartMills(getString2TimeMills(getContext(), txtDate, txtTime));
                    }
                    {
                        // 終了
                        String txtDate = txtEditToDoEndDate.getText().toString();
                        String txtTime = txtEditToDoEndTime.getText().toString();
                        toDoEntry.setEndMills(getString2TimeMills(getContext(), txtDate, txtTime));
                    }
                }
                toDoEntry.setStatus(chkEditClosed.isSelected() ? IToDoEntry.STATUS_CLOSED : IToDoEntry.STATUS_OPEN);
                ILocationEntry locationEntry = toDoEntry.getDefaultLocationEntry(true);
                locationEntry.setName(txtEditToDoLocation.getText().toString());
                INotificationEntry notificationEntry = locationEntry.getDefaultNotificationEntry(true);
                try {
                    notificationEntry.setDistance(Double.valueOf(txtEditToDoDistance.getText().toString()));
                } catch (NumberFormatException e) {
                    notificationEntry.setDistance(Double.valueOf(1000.0D));
                }
                notificationEntry.setVibration((chkEditToDoVibration.isSelected() ? INotificationEntry.CHECK_ENABLED : INotificationEntry.CHECK_DISABLED));
                notificationEntry.setLight((chkEditToDoLight.isSelected() ? INotificationEntry.CHECK_ENABLED : INotificationEntry.CHECK_DISABLED));
                notificationEntry.setSound((chkEditToDoSound.isSelected() ? INotificationEntry.CHECK_ENABLED : INotificationEntry.CHECK_DISABLED));
            }
        });
    }

    /**
     * 書式のセットアップ
     */
    private void setUpFormat(Context context) {
        // 日付フォーマット
        String dateFormatStr = Settings.System.getString(context.getContentResolver(), Settings.System.DATE_FORMAT);
        if (dateFormatStr == null) {
            dateFormatStr = DEFAULT_DATE_FORMAT;
        }
        // 時刻フォーマット
        String time_12_24 = Settings.System.getString(context.getContentResolver(), Settings.System.TIME_12_24);
        String timeFormatStr = (TIME_24.equals(time_12_24) ? TIME_24_FORMAT : TIME_12_FORMAT);
        dateTimeFormat = new SimpleDateFormat(dateFormatStr + " " + timeFormatStr);
        dateFormat = new SimpleDateFormat(dateFormatStr);
        timeFormat = new SimpleDateFormat(timeFormatStr);
    }

    /**
     * 日付時刻書式
     */
    public SimpleDateFormat getDateTimeFormat(Context context) {
        if (dateTimeFormat == null) setUpFormat(context);
        return dateTimeFormat;
    }

    /**
     * 日付書式
     */
    public SimpleDateFormat getDateFormat(Context context) {
        if (dateFormat == null) setUpFormat(context);
        return dateFormat;
    }

    /**
     * 時刻書式
     */
    public SimpleDateFormat getTimeFormat(Context context) {
        if (timeFormat == null) setUpFormat(context);
        return timeFormat;
    }

    /**
     * 文字列から時間へ変換する
     *
     * @param context コンテキスト
     * @param date    日付文字列
     * @param time    時刻文字列
     * @return 時間
     */
    private long getString2TimeMills(Context context, String date, String time) {
        long ret = 0;
        try {
            if (date.trim().length() > 0) {
                if (time.trim().length() > 0) {
                    Date work = getDateTimeFormat(context).parse(date + " " + time);
                    ret = work.getTime();
                } else {
                    Date work = getDateFormat(context).parse(date);
                    ret = work.getTime();
                }
            } else if (time.trim().length() > 0) {
                Date work = getTimeFormat(context).parse(time);
                ret = work.getTime();
            } else {
                ret = 0;
            }
        } catch (ParseException pe) {
            ret = 0;
        }

        return ret;
    }

    /**
     * ロング値から日付文字列に変換する
     *
     * @param context   コンテキスト
     * @param timeMills ロング値
     * @return 日付文字列
     */
    private String getLong2DateString(Context context, long timeMills) {
        String ret = "";
        if (timeMills > 0) {
            Date date = new Date();
            date.setTime(timeMills);
            ret = getDateFormat(context).format(date);
        }
        return ret;
    }

    /**
     * ロング値から時刻文字列に変換する
     *
     * @param context   コンテキスト
     * @param timeMills ロング値
     * @return 日付文字列
     */
    private String getLong2TimeString(Context context, long timeMills) {
        String ret = "";
        if (timeMills > 0) {
            Date date = new Date();
            date.setTime(timeMills);
            ret = getTimeFormat(context).format(date);
        }
        return ret;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.todo_entry_fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            LocationDrivenDao dao = new LocationDrivenDao(getContext());
            List<IToDoEntry> todos = dao.getToDos("", new String[]{}, "");
            recyclerView.setAdapter(new ToDoEntryItemRecyclerViewAdapter(todos, mListener));
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public static interface ToDoEntoryItemFragmentOKCancelListerner {
        public boolean onOkPressed(IToDoEntry toDoEntry);

        public boolean onCancelPressed(IToDoEntry toDoEntry);

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onToDoListFragmentInteraction(IToDoEntry item);
    }
}
