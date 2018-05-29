package info.codestart.daniel.Utils;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import info.codestart.daniel.R;
import info.codestart.daniel.UpdateRecordActivity;
import info.codestart.daniel.model.Person;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
    private List<Person> mPeopleList;
    private Context mContext;
    private RecyclerView mRecyclerV;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    String theTime = df.format(Calendar.getInstance().getTime());


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView personNameTxtV;
        public TextView personAgeTxtV;
        public TextView personOccupationTxtV;
        public Button personImageImgV;
        public TextView currentTime;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            personNameTxtV = (TextView) v.findViewById(R.id.name);
            personAgeTxtV = (TextView) v.findViewById(R.id.age);
            personOccupationTxtV = (TextView) v.findViewById(R.id.occupation);
            personImageImgV = (Button) v.findViewById(R.id.buttonDelete);
            currentTime = (TextView) v.findViewById(R.id.currentTime);
        }
    }

    public void add(int position, Person person) {
        mPeopleList.add(position, person);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mPeopleList.remove(position);
        notifyItemRemoved(position);
    }



    // Provide a suitable constructor (depends on the kind of dataset)
    public PersonAdapter(List<Person> myDataset, Context context, RecyclerView recyclerView) {
        mPeopleList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PersonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.single_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final Person person = mPeopleList.get(position);
        holder.personNameTxtV.setText("Amount: " + person.getName());
        holder.personAgeTxtV.setText("Tip Percent: " + person.getAge());
        holder.personOccupationTxtV.setText("Tip Amount : " + person.getOccupation());
        holder.personImageImgV.setText("Delete");
        holder.currentTime.setText(theTime);

        //listen to single view layout click
        holder.personImageImgV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        PersonDBHelper dbHelper = new PersonDBHelper(mContext);
                        dbHelper.deletePersonRecord(person.getId(), mContext);

                        mPeopleList.remove(position);
                        mRecyclerV.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mPeopleList.size());
                        notifyDataSetChanged();
            }
        });
    }

    private void goToUpdateActivity(long personId){
        Intent goToUpdate = new Intent(mContext, UpdateRecordActivity.class);
        goToUpdate.putExtra("USER_ID", personId);
        mContext.startActivity(goToUpdate);
    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mPeopleList.size();
    }



}