package com.balli.kbcreturns;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<Registration> values;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtfirstLine;
        public TextView txtsecondLine;
        public TextView txtthirdLine;
        public TextView txtfourthLine;
        public TextView txtfifthLine;
        public TextView txtsixthLine;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtfirstLine = (TextView) v.findViewById(R.id.firstLine);
            txtsecondLine = (TextView) v.findViewById(R.id.secondLine);
            txtthirdLine = (TextView) v.findViewById(R.id.thirdLine);
            txtfourthLine = (TextView) v.findViewById(R.id.fourthLine);
            txtfifthLine = (TextView) v.findViewById(R.id.fifthLine);
            txtsixthLine = (TextView) v.findViewById(R.id.sixthLine);
        }
    }

    public void add(int position, Registration item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Registration> myDataset) {
        values = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String name = values.get(position).getName();
        final String uniqueid = values.get(position).getUniqueid();
        final String correct = values.get(position).getCorrect();
        final String attempted = values.get(position).getAttempted();
        final String percentage = values.get(position).getPercentage();
        final String city = values.get(position).getCity();
        final String age = values.get(position).getAge();
        final String gender = values.get(position).getGender();

//        holder.txtfirstLine.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                remove(position);
//            }
//        });

        if (name.length()>15)
            holder.txtfirstLine.setText("#"+(position+1)+"   "+name.substring(0,15)+".");
        else
            holder.txtfirstLine.setText("#"+(position+1)+"   "+name);

        if (uniqueid.length()>15)
            holder.txtsecondLine.setText("(" + uniqueid.substring(0,15)+".)");
        else
            holder.txtsecondLine.setText("(" + uniqueid+")");

        if (city.length()>15)
            holder.txtfifthLine.setText(city.substring(0,15)+".");
        else
            holder.txtfifthLine.setText(city);

        ;
        holder.txtthirdLine.setText(correct+"/"+attempted);
        holder.txtfourthLine.setText(percentage+"%");
        holder.txtsixthLine.setText(age+", "+gender);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

}
