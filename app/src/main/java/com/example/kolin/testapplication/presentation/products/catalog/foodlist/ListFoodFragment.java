package com.example.kolin.testapplication.presentation.products.catalog.foodlist;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kolin.testapplication.R;
import com.example.kolin.testapplication.domain.Food;
import com.example.kolin.testapplication.domain.FoodCategory;
import com.example.kolin.testapplication.presentation.common.SimpleDividerItemDecoration;
import com.example.kolin.testapplication.presentation.common.SimpleSectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListFoodFragment extends Fragment implements ListFoodView {

    private ListFoodAdapter adapter;

    private ListFoodPresenter presenter;

    private String currentItemOfGroup;


    private OnSwipeRightListFragment listener;

    private RecyclerView recyclerView;
    private List<SimpleSectionedRecyclerViewAdapter.Section> sections = new ArrayList<>();
    private SimpleSectionedRecyclerViewAdapter sectionedAdapter;


    public ListFoodFragment() {
    }

    public interface OnSwipeRightListFragment {
        void onSwipeRight();
    }

    public static ListFoodFragment newInstance(String itemName) {
        ListFoodFragment fragment = new ListFoodFragment();
        Bundle args = new Bundle();
        args.putString("itemName", itemName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        currentItemOfGroup = bundle.getString("itemName");

        presenter = new ListFoodPresenter();
        presenter.attachView(this);

        adapter = new ListFoodAdapter(getContext());
        sectionedAdapter = new
                SimpleSectionedRecyclerViewAdapter(getContext(), adapter);

        presenter.load(currentItemOfGroup);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view_list_fragment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        return root;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSwipeRightListFragment) {
            listener = (OnSwipeRightListFragment) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnSwipeRightListFragment");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unSubscribe();
    }

    @Override
    public void showLoadedFood(HashMap<FoodCategory, List<Food>> foodCategoryListHashMap) {
        int i = 0;
        for (HashMap.Entry<FoodCategory, List<Food>> pair : foodCategoryListHashMap.entrySet()) {
            FoodCategory key = pair.getKey();
            sections.add(
                    new SimpleSectionedRecyclerViewAdapter.Section(
                            i, key.getNameFoodCategory(), key.getSrc()));
            i += pair.getValue().size();
            adapter.addAll(pair.getValue());
        }

        SimpleSectionedRecyclerViewAdapter.Section[] dummy =
                new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];

        sectionedAdapter.setSections(sections.toArray(dummy));
        recyclerView.setAdapter(sectionedAdapter);
    }

}
