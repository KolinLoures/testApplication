package com.example.kolin.testapplication.domain.repository;

import com.example.kolin.testapplication.domain.CalculatedFood;
import com.example.kolin.testapplication.domain.Food;
import com.example.kolin.testapplication.domain.FoodCategory;
import com.example.kolin.testapplication.domain.ItemOfGroup;
import com.example.kolin.testapplication.domain.VitalCharacteristic;
import com.example.kolin.testapplication.domain.groups.Group;

import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.subjects.ReplaySubject;


/**
 * Created by kolin on 06.09.2016.
 */

public interface Repository {

    Observable<List<ItemOfGroup>> getRestaurants(@Group.GroupAllFood String categoryName);

    Observable<List<ItemOfGroup>> getAllItemsOfGroup();

    Observable<HashMap<FoodCategory, List<Food>>> getFood(String itemGroupName);



    void addFoodToFavorite(Food food);

    Observable<List<Food>> getFavoriteFood();

    void deleteFavoriteFood(Food food);

    void addCalculationFood(Food food);

    ReplaySubject<List<Food>> getCalculationFood();

    void deleteCalculationFood(Food food);

    void onNextCalculationFood();

    void deleteAllFromCalculation();

    Observable<List<VitalCharacteristic>> getVitalCharacteristic();

    void addVitalCharacteristic(VitalCharacteristic vitalCharacteristic);

    void deleteVitalCharacteristic(VitalCharacteristic vitalCharacteristic);

    void addResultCalculatedFood(CalculatedFood calculatedFood);

    void deleteResultCalculatedFood(CalculatedFood calculatedFood);

    Observable<List<CalculatedFood>> getResultCalculatedFood();
}
