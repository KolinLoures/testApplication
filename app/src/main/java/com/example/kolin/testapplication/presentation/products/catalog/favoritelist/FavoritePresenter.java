package com.example.kolin.testapplication.presentation.products.catalog.favoritelist;

import android.util.Log;

import com.example.kolin.testapplication.domain.Food;
import com.example.kolin.testapplication.domain.interactor.DefaultSubscriber;
import com.example.kolin.testapplication.domain.interactor.GetFavoriteFoodUC;
import com.example.kolin.testapplication.domain.interactor.GetObservableCalculatedFoodUC;
import com.example.kolin.testapplication.presentation.common.AbstractPresenter;
import com.example.kolin.testapplication.presentation.common.FoodMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kolin on 18.09.2016.
 */

public class FavoritePresenter extends AbstractPresenter<FavoriteView> {

    private static final String TAG = FavoritePresenter.class.getSimpleName();

    private GetFavoriteFoodUC getFavoriteFoodUC;
    private GetObservableCalculatedFoodUC getCalculatedFoodUC;

    private List<Food> loadedData = new ArrayList<>();
    private List<Food> checkedFood = new ArrayList<>();


    public FavoritePresenter() {
        getFavoriteFoodUC = new GetFavoriteFoodUC();
        getCalculatedFoodUC = new GetObservableCalculatedFoodUC();
    }

    public void loadFavorite(){
        getFavoriteFoodUC.execute(new FavoriteSubscriber());
    }

    public void loadCalculated(){
    }

    public void showData(List<Food> foodList){
        loadedData.clear();
        loadedData.addAll(foodList);
        if (!isViewAttach()){
            Log.e(TAG, "View is detach!");
        }

        getWeakReference().showFavoriteFood(FoodMapper.realmListToList(loadedData));
    }

    private final class FavoriteSubscriber extends DefaultSubscriber<List<Food>>{
        @Override
        public void onNext(List<Food> list) {
            FavoritePresenter.this.showData(list);
            getCalculatedFoodUC.execute(new FavoriteCalculatorSubscriber());
        }
    }

    private final class FavoriteCalculatorSubscriber extends DefaultSubscriber<List<Food>>{
        @Override
        public void onNext(List<Food> foodList) {
            setCalculatedFood(foodList);
        }
    }


    public void unSubscribe(){
        getFavoriteFoodUC.unsubscribe();
        getCalculatedFoodUC.unsubscribe();
    }

    public void deleteFromFavorite(int position){
        getFavoriteFoodUC.deleteFavoriteFood(loadedData.get(position));

        if (!isViewAttach()){
            Log.e(TAG, "View is detach!");
        }

        getWeakReference().showSnackBar("Удалено из избраннного!");
    }

    public void deleteCalculationFood(Food food){
        getCalculatedFoodUC.deleteCalculationFood(food);

        if (!isViewAttach()){
            Log.e(TAG, "View is detach!");
        }

        getWeakReference().showSnackBar("Удалено из калькулятора!");
    }

    public void addCalculationFood(Food food){
        getCalculatedFoodUC.addCalculationFood(food);

        if (!isViewAttach()){
            Log.e(TAG, "View is detach!");
        }

        getWeakReference().showSnackBar("Добавлено в калькулятор!");
    }

    public void setCalculatedFood(List<Food> foodList){
        checkedFood.clear();
        checkedFood.addAll(foodList);

        if (!isViewAttach()){
            Log.e(TAG, "View is detach!");
        }

        getWeakReference().addCheckedFood(checkedFood);
    }
}

