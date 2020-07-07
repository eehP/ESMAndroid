package org.libreapps.rest.ViewSearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.libreapps.rest.R;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements Filterable {

    private List<BillJSON> modelClassList;
    private List<BillJSON> modelClassListFull;
    private RecyclerViewClickInterface recyclerViewClickInterface;


    public Adapter(List<BillJSON> modelClassList, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.modelClassList = modelClassList;
        modelClassListFull = new ArrayList<>(modelClassList);
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    public void setData(List<BillJSON> modelClassList) {
        this.modelClassList = modelClassList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewholder, int position) {

        String id = modelClassList.get(position).getId();
        String name = modelClassList.get(position).getName();
        String date = modelClassList.get(position).getDate();
        String type = modelClassList.get(position).getType();
        double price = Double.parseDouble(modelClassList.get(position).getPrice());
        viewholder.id.setText(id);
        viewholder.name.setText(name);
        viewholder.date.setText(date);
        viewholder.type.setText(type);
        viewholder.price.setText(price+" €");
    }

    @Override
    public int getItemCount() {
        return modelClassList.size();
    }

    public List<BillJSON> get_actualList(){
        return this.modelClassList;
    }

    @Override
    public Filter getFilter() {
        return modelClassFilter;
    }

    private Filter modelClassFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<BillJSON> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(modelClassListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (BillJSON bill : modelClassListFull) {
                    if (bill.getId().toLowerCase().contains(filterPattern) || bill.getName().toLowerCase().contains(filterPattern) || bill.getType().toLowerCase().contains(filterPattern) || bill.getDate().toLowerCase().contains(filterPattern) || bill.getPrice().toLowerCase().contains(filterPattern)) {
                        filteredList.add(bill);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            modelClassList.clear();
            modelClassList.addAll((List) results.values);

            notifyDataSetChanged();
        }

    };

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView id, name, date, type, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.textId);
            name = itemView.findViewById(R.id.textName);
            date = itemView.findViewById(R.id.textDate);
            type = itemView.findViewById(R.id.textType);
            price = itemView.findViewById(R.id.textPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
