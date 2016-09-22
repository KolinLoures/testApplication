package com.example.kolin.testapplication.presentation.products.catalog.foodlist;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kolin.testapplication.R;
import com.example.kolin.testapplication.domain.Food;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kolin on 12.09.2016.
 */

public class ListFoodAdapter extends RecyclerView.Adapter<ListFoodAdapter.ListFoodViewHolder> {

    private List<Food> listFood;
    private OnClickFavoriteBtn listener;
    private Resources resources;

    public interface OnClickFavoriteBtn {
        void onClickFavoriteBtn(int position);
    }


    public ListFoodAdapter() {
        listFood = new ArrayList<>();
    }

    @Override
    public ListFoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        resources = parent.getResources();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_fragment, parent, false);
        return new ListFoodViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ListFoodViewHolder holder, int position) {
        Food food = listFood.get(position);
        holder.textViewNameProduct.setText(food.getName());
        holder.textViewB.setText(resources.getString(R.string.b)
                + String.valueOf(food.getB()));
        holder.textViewJ.setText(resources.getString(R.string.j)
                + String.valueOf(food.getJ()));
        holder.textViewY.setText(resources.getString(R.string.y)
                + String.valueOf(food.getY()));
    }

    @Override
    public int getItemCount() {
        return listFood.size();
    }

    public void addAll(List<Food> listFood) {
        this.listFood.clear();
        this.listFood.addAll(listFood);
        notifyDataSetChanged();
    }

    public void clearAll() {
        this.listFood.clear();
        notifyDataSetChanged();
    }

    class ListFoodViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewNameProduct;
        private TextView textViewB;
        private TextView textViewJ;
        private TextView textViewY;
        private ImageButton imageViewFavorite;

        public ListFoodViewHolder(View itemView) {
            super(itemView);

            textViewNameProduct = (TextView) itemView.findViewById(R.id.list_food_name_product);
            textViewB = (TextView) itemView.findViewById(R.id.list_food_b);
            textViewJ = (TextView) itemView.findViewById(R.id.list_food_j);
            textViewY = (TextView) itemView.findViewById(R.id.list_food_y);
            imageViewFavorite = (ImageButton) itemView.findViewById(R.id.list_food_add_to_favorite);


            imageViewFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onClickFavoriteBtn(getLayoutPosition());
                    }
                }
            });
        }
    }

    public void setListener(OnClickFavoriteBtn listener) {
        this.listener = listener;
    }

}
