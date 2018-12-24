package kkimmg.locationtriggeredtodo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import kkimmg.locationtriggeredtodo.ToDoEntryItemFragment.OnListFragmentInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link IToDoEntry} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ToDoEntryItemRecyclerViewAdapter extends RecyclerView.Adapter<ToDoEntryItemRecyclerViewAdapter.ViewHolder> {

    private final List<IToDoEntry> mValues;
    private final OnListFragmentInteractionListener mListener;

    public ToDoEntryItemRecyclerViewAdapter(List<IToDoEntry> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_entry_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.chkToDoClosed.setChecked(mValues.get(position).getStatus() == IToDoEntry.STATUS_CLOSED);
        holder.txtToDoTitle.setText(mValues.get(position).getTitle());
        if (mValues.get(position).size() > 0) {
            holder.txtToDoLocationName.setText(mValues.get(position).get(0).getName());
        } else {
            holder.txtToDoLocationName.setText("");
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onToDoListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final CheckBox chkToDoClosed;
        public final TextView txtToDoTitle;
        public final TextView txtToDoLocationName;
        public IToDoEntry mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            chkToDoClosed = (CheckBox) view.findViewById(R.id.chkToDoClosed);
            txtToDoTitle = (TextView) view.findViewById(R.id.txtToDoTitle);
            txtToDoLocationName = (TextView) view.findViewById(R.id.txtToDoLocationName);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + txtToDoTitle.getText() + "'";
        }
    }
}
