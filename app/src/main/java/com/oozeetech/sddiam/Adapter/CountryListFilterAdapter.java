package com.oozeetech.sddiam.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.oozeetech.sddiam.Model.ResponseModel;
import com.oozeetech.sddiam.R;
import com.oozeetech.sddiam.Utils.SDInterface;
import com.oozeetech.sddiam.Widget.DTextView;

import java.util.ArrayList;


public class CountryListFilterAdapter extends RecyclerView.Adapter<CountryListFilterAdapter.Holder> implements Filterable {
    private ValueFilter valueFilter;
    private Activity activity;
    private ArrayList<ResponseModel> arrCountryList;
    private ArrayList<ResponseModel> arrCountryFilterList;
    private SDInterface.OnCountryClick onCountryClick;

    public CountryListFilterAdapter(Activity activity, ArrayList<ResponseModel> arrCountryList, ArrayList<ResponseModel> arrCountryFilterList, SDInterface.OnCountryClick onCountryClick) {
        this.activity = activity;
        this.arrCountryList = arrCountryList;
        this.arrCountryFilterList = arrCountryFilterList;
        this.onCountryClick = onCountryClick;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.row_country_list, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {

        final ResponseModel responseModel = arrCountryList.get(position);
        holder.txtName.setText(responseModel.getText());
        holder.loutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCountryClick.onCountryClick(responseModel.getText(),responseModel.getValue());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrCountryList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {

            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    class Holder extends RecyclerView.ViewHolder {
        private DTextView txtName;
        private LinearLayout loutMain;

        Holder(View view) {
            super(view);
            txtName = view.findViewById(R.id.txtName);
            loutMain = view.findViewById(R.id.loutMain);
        }

    }

    private class ValueFilter extends Filter {
        //Invoked in a worker thread to filter the data according to the constraint.
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {

                ArrayList<ResponseModel> filterList = new ArrayList<>();

                for (int i = 0; i < arrCountryFilterList.size(); i++) {
                    if ((arrCountryFilterList.get(i).getText().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        ResponseModel data = arrCountryFilterList.get(i);
                        filterList.add(data);
                    }
                }

                results.count = filterList.size();
                results.values = filterList;

            } else {
                results.count = arrCountryFilterList.size();
                results.values = arrCountryFilterList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            arrCountryList = (ArrayList<ResponseModel>) results.values;


            notifyDataSetChanged();
        }
    }

}