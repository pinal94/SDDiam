package com.oozeetech.sddiam.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.oozeetech.sddiam.Model.CategoryModel;
import com.oozeetech.sddiam.R;
import com.oozeetech.sddiam.Utils.SDInterface;
import com.oozeetech.sddiam.widget.DTextView;

import java.util.ArrayList;


public class CountryListFilterAdapter extends RecyclerView.Adapter<CountryListFilterAdapter.Holder> implements Filterable {
    private ValueFilter valueFilter;
    private Activity activity;
    private ArrayList<CategoryModel> arrCountryList;
    private ArrayList<CategoryModel> arrCountryFilterList;
    private SDInterface.OnCountryClick onCountryClick;

    public CountryListFilterAdapter(Activity activity, ArrayList<CategoryModel> arrCountryList, ArrayList<CategoryModel> arrCountryFilterList, SDInterface.OnCountryClick onCountryClick) {
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

        final CategoryModel categoryModel = arrCountryList.get(position);
        holder.txtName.setText(categoryModel.getName());
        holder.loutMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCountryClick.onCountryClick(categoryModel.getName());
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

                ArrayList<CategoryModel> filterList = new ArrayList<>();

                for (int i = 0; i < arrCountryList.size(); i++) {
                    if ((arrCountryList.get(i).getName().toUpperCase())
                            .contains(constraint.toString().toUpperCase())) {
                        CategoryModel data = arrCountryList.get(i);
                        filterList.add(data);
                    }
                }

                results.count = filterList.size();
                results.values = filterList;

            } else {
                results.count = arrCountryList.size();
                results.values = arrCountryList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            arrCountryFilterList = (ArrayList<CategoryModel>) results.values;


            notifyDataSetChanged();
        }
    }

}