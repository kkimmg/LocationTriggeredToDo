package kkimmg.locationtriggeredtodo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /** TODOエントリ */
    private IToDoEntry toDoEntry;

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
     * イベントとかの対応
     */
    private void setToComponents(Activity act, IToDoEntry toDoEntry) {
        Button btnEditToDoCancel = act.findViewById(R.id.btnEditToDoCancel);
        TextView tvEditTodoTitle = act.findViewById(R.id.tvEditTodoTitle);
        EditText txtEditToDoTitle = act.findViewById(R.id.txtEditToDoTitle);
        TextView tvEditToDoDescription = act.findViewById(R.id.tvEditToDoDescription);
        EditText txtEditToDoDescription = act.findViewById(R.id.txtEditToDoDescription);
        CheckBox chkEditToDoAllDay = act.findViewById(R.id.chkEditToDoAllDay);
        TextView tvEditToDoStart = act.findViewById(R.id.tvEditToDoStart);
        EditText txtEditToDoStartDate = act.findViewById(R.id.txtEditToDoStartDate);
        EditText txtEditToDoStartTime = act.findViewById(R.id.txtEditToDoStartTime);
        Button btnEditToDoStart = act.findViewById(R.id.btnEditToDoStart);
        TextView tvEditToDoEnd = act.findViewById(R.id.tvEditToDoEnd);
        EditText txtEditToDoEndDate = act.findViewById(R.id.txtEditToDoEndDate);
        EditText txtEditToDoEndTime = act.findViewById(R.id.txtEditToDoEndTime);
        Button btnEditToDoEnd = act.findViewById(R.id.btnEditToDoEnd);
        CheckBox chkEditClosed = act.findViewById(R.id.chkEditClosed);
        TextView tvEditToDoLocation = act.findViewById(R.id.tvEditToDoLocation);
        EditText txtEditToDoLocation = act.findViewById(R.id.txtEditToDoLocation);
        Button btnEditToDoLocationMore = act.findViewById(R.id.btnEditToDoLocationMore);
        TextView tvEditToDoAlarm = act.findViewById(R.id.tvEditToDoAlarm);
        Spinner spnEditToDoAlarmTiming = act.findViewById(R.id.spnEditToDoAlarmTiming);
        EditText txtEditToDoDistance = act.findViewById(R.id.txtEditToDoDistance);
        TextView tvEditToDoMeters = act.findViewById(R.id.tvEditToDoMeters);
        CheckBox chkEditToDoVibration = act.findViewById(R.id.chkEditToDoVibration);
        CheckBox chkEditToDoSound = act.findViewById(R.id.chkEditToDoSound);
        Spinner spnEditToDoSound = act.findViewById(R.id.spnEditToDoSound);
        CheckBox chkEditToDoLight = act.findViewById(R.id.chkEditToDoLight);
        Spinner spnEditToDoOptions = act.findViewById(R.id.spnEditToDoOptions);
        Button btnEditToDoOK = act.findViewById(R.id.btnEditToDoOK);

        txtEditToDoTitle.setText(toDoEntry.getTitle());
        txtEditToDoDescription.setText(toDoEntry.getDescription());
        chkEditToDoAllDay.setChecked(toDoEntry.getAllDay() == IToDoEntry.SCD_ALLDAY);
        if (toDoEntry.getStartMills() > 0) {
            txtEditToDoStartDate.setText(DateUtils.formatDateTime(act, toDoEntry.getStartMills(), DateUtils.FORMAT_SHOW_DATE));
            txtEditToDoStartTime.setText(DateUtils.formatDateTime(act, toDoEntry.getStartMills(), DateUtils.FORMAT_SHOW_TIME));
        }
        if (toDoEntry.getEndMills() > 0) {
            txtEditToDoEndDate.setText(DateUtils.formatDateTime(act, toDoEntry.getEndMills(), DateUtils.FORMAT_SHOW_DATE));
            txtEditToDoEndTime.setText(DateUtils.formatDateTime(act, toDoEntry.getEndMills(), DateUtils.FORMAT_SHOW_TIME));
        }
        chkEditClosed.setChecked(toDoEntry.getStartMills() == IToDoEntry.STATUS_CLOSED);

        for (ILocationEntry locationEntry : toDoEntry) {
            if (locationEntry.getDefaultLocation() == ILocationEntry.IS_DEFAULT_LOCATION) {
                txtEditToDoLocation.setText(locationEntry.getName());
                for (INotificationEntry alarmEntry : locationEntry) {
                    if (alarmEntry.getDefaultNotification() == INotificationEntry.IS_DEFAULT_NOTIFICATION) {

                    }
                }
            }
        }

    }

    /**
     * イベントとかの対応
     */
    private void setUoComponents(Activity act) {
        Button btnEditToDoCancel = act.findViewById(R.id.btnEditToDoCancel);
        TextView tvEditTodoTitle = act.findViewById(R.id.tvEditTodoTitle);
        EditText txtEditToDoTitle = act.findViewById(R.id.txtEditToDoTitle);
        TextView tvEditToDoDescription = act.findViewById(R.id.tvEditToDoDescription);
        EditText txtEditToDoDescription = act.findViewById(R.id.txtEditToDoDescription);
        CheckBox chkEditToDoAllDay = act.findViewById(R.id.chkEditToDoAllDay);
        TextView tvEditToDoStart = act.findViewById(R.id.tvEditToDoStart);
        EditText txtEditToDoStartDate = act.findViewById(R.id.txtEditToDoStartDate);
        EditText txtEditToDoStartTime = act.findViewById(R.id.txtEditToDoStartTime);
        Button btnEditToDoStart = act.findViewById(R.id.btnEditToDoStart);
        TextView tvEditToDoEnd = act.findViewById(R.id.tvEditToDoEnd);
        EditText txtEditToDoEndDate = act.findViewById(R.id.txtEditToDoEndDate);
        EditText txtEditToDoEndTime = act.findViewById(R.id.txtEditToDoEndTime);
        Button btnEditToDoEnd = act.findViewById(R.id.btnEditToDoEnd);
        CheckBox chkEditClosed = act.findViewById(R.id.chkEditClosed);
        TextView tvEditToDoLocation = act.findViewById(R.id.tvEditToDoLocation);
        EditText txtEditToDoLocation = act.findViewById(R.id.txtEditToDoLocation);
        Button btnEditToDoLocationMore = act.findViewById(R.id.btnEditToDoLocationMore);
        TextView tvEditToDoAlarm = act.findViewById(R.id.tvEditToDoAlarm);
        Spinner spnEditToDoAlarmTiming = act.findViewById(R.id.spnEditToDoAlarmTiming);
        EditText txtEditToDoDistance = act.findViewById(R.id.txtEditToDoDistance);
        TextView tvEditToDoMeters = act.findViewById(R.id.tvEditToDoMeters);
        CheckBox chkEditToDoVibration = act.findViewById(R.id.chkEditToDoVibration);
        CheckBox chkEditToDoSound = act.findViewById(R.id.chkEditToDoSound);
        Spinner spnEditToDoSound = act.findViewById(R.id.spnEditToDoSound);
        CheckBox chkEditToDoLight = act.findViewById(R.id.chkEditToDoLight);
        Spinner spnEditToDoOptions = act.findViewById(R.id.spnEditToDoOptions);
        Button btnEditToDoOK = act.findViewById(R.id.btnEditToDoOK);


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
