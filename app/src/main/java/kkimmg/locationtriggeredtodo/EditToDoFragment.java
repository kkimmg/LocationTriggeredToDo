package kkimmg.locationtriggeredtodo;

import android.app.Notification;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;


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
     * ToDoエントリ
     */
    private IToDoEntry mToDoEntry;

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
        CheckBox chkClosed = view.findViewById(R.id.chkToDoClosed);
        EditText txtLocation = view.findViewById(R.id.txtToDoLocationName);
        Spinner spnAlarmTiming = view.findViewById(R.id.spnEditToDoAlarmTiming);
        EditText txtDistance = view.findViewById(R.id.txtEditToDoDistance);
        CheckBox chkVibration = view.findViewById(R.id.chkEditToDoVibration);
        Spinner spnVibration = view.findViewById(R.id.spnEditToDoVibration);
        CheckBox chkSound = view.findViewById(R.id.chkEditToDoSound);
        Spinner spnSound = view.findViewById(R.id.spnEditToDoSound);
        CheckBox chkLight = view.findViewById(R.id.chkEditToDoLight);

        txtTitle.setText(toDoEntry.getTitle());
        txtDescription.setText(toDoEntry.getDescription());
        if (toDoEntry.getStatus() == IToDoEntry.STATUS_CLOSED) {
            chkClosed.setChecked(true);
        }
        if (toDoEntry.size() > 0) {
            ILocationEntry location = toDoEntry.get(0);
            txtLocation.setText(location.getName());
            if (location.size() > 0) {
                IAlarmEntry alarmEntry = location.get(0);
                if (alarmEntry.getAlarmTiming() == IAlarmEntry.TIMING_APPROACHING) {
                    spnAlarmTiming.setSelection(0);
                } else {
                    spnAlarmTiming.setSelection(1);
                }
                txtDistance.setText(String.valueOf(alarmEntry.getDistance()));
                if (0 != (alarmEntry.getDefaults() & Notification.DEFAULT_VIBRATE)) {
                    chkVibration.setChecked(true);
                } else {
                    chkVibration.setChecked(false);
                }
            }
        }
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
