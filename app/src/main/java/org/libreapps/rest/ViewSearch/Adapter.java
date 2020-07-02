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

import retrofit2.Callback;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements Filterable {

    private List<ProductJSON> modelClassList;
    private List<ProductJSON> modelClassListFull;
    private RecyclerViewListener listener;


    public Adapter(List<ProductJSON> modelClassList, RecyclerViewListener listener) {
        this.modelClassList = modelClassList;
        modelClassListFull = new ArrayList<>(modelClassList);
        this.listener = listener;
    }

    public void setData(List<ProductJSON> modelClassList) {
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
        String type = modelClassList.get(position).getType();
        String price = modelClassList.get(position).getPrice();
        viewholder.id.setText(id);
        viewholder.name.setText(name);
        viewholder.type.setText(type);
        viewholder.price.setText(price+"€");
    }

    @Override
    public int getItemCount() {
        return modelClassList.size();
    }

    @Override
    public Filter getFilter() {
        return modelClassFilter;
    }

    private Filter modelClassFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ProductJSON> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(modelClassListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ProductJSON product : modelClassListFull) {
                    if (product.getId().toLowerCase().contains(filterPattern) || product.getName().toLowerCase().contains(filterPattern) || product.getType().toLowerCase().contains(filterPattern) || product.getPrice().toLowerCase().contains(filterPattern)) {
                        filteredList.add(product);
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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView id, name, type, price;

        public ViewHolder(@NonNull View view) {
            super(view);

            id = itemView.findViewById(R.id.textId);
            name = itemView.findViewById(R.id.textName);
            type = itemView.findViewById(R.id.textType);
            price = itemView.findViewById(R.id.textPrice);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }
    public interface RecyclerViewListener {
        void onClick(View view, int position);
    }
}
