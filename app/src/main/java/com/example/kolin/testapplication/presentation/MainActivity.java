package com.example.kolin.testapplication.presentation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.kolin.testapplication.R;
import com.example.kolin.testapplication.domain.VitalCharacteristic;
import com.example.kolin.testapplication.presentation.about.AboutFragment;
import com.example.kolin.testapplication.presentation.calculator.CalculatorFragment;
import com.example.kolin.testapplication.presentation.calculator.calculation.CalculationActivity;
import com.example.kolin.testapplication.presentation.common.attention.AttentionFragment;
import com.example.kolin.testapplication.presentation.index.IndexFragment;
import com.example.kolin.testapplication.presentation.index.addingdialog.AddIndexDialogFragment;
import com.example.kolin.testapplication.presentation.products.ProductsFragment;
import com.example.kolin.testapplication.presentation.products.catalog.CatalogActivity;

public class MainActivity extends AppCompatActivity implements
        ProductsFragment.OnClickCardViews,
        CalculatorFragment.OnClickCalculationFragmentListener,
        IndexFragment.OnClickIndexFragmentListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_view);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setActionBar();
        setupDrawerContent(navigationView);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(
                R.id.fragment_container, CalculatorFragment.newInstance()).commit();

        checkAttentionSharedPreferences();
        checkCountSharedPreferences();
    }

    private void checkAttentionSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.preferences_attention_key), MODE_PRIVATE);
        boolean check = sharedPreferences.getBoolean(
                getString(R.string.preferences_attention_value), false);
        if (!check) {
            AttentionFragment attentionFragment = AttentionFragment.newInstance();
            attentionFragment.show(getSupportFragmentManager(), AttentionFragment.class.getSimpleName());
        }
    }


    private void checkCountSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.preferences_count_key), MODE_PRIVATE);
        int count = sharedPreferences.getInt(getString(R.string.preferences_count_value), -1);
        if (count == -1) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(getString(R.string.preferences_count_value), 1);
            editor.apply();
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        selectDrawerItem(item);
                        return true;
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void selectDrawerItem(MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.calculator_item:
                fragment = new CalculatorFragment();
                break;
            case R.id.product_item:
                fragment = new ProductsFragment();
                break;
            case R.id.index_item:
                fragment = new IndexFragment();
                break;
            case R.id.about_system_item:
                fragment = new AboutFragment();
                break;
        }

        if (fragment != null) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
            setTitle(item.getTitle());
        }

        item.setChecked(true);
        drawerLayout.closeDrawers();
    }

    private void setActionBar() {
        setSupportActionBar(toolbar);
        setTitle(R.string.diary);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        }
    }

    @Override
    public void onClickCardViewRestaurant() {
        Intent intent = new Intent(getApplicationContext(), CatalogActivity.class);
        intent.putExtra("NameCategory", 1);
        startActivity(intent);
    }

    @Override
    public void onClickCardViewFood() {
        Intent intent = new Intent(getApplicationContext(), CatalogActivity.class);
        intent.putExtra("NameCategory", 0);
        startActivity(intent);
    }

    @Override
    public void onClickFab(View v) {
        Intent intent = new Intent(getApplicationContext(), CatalogActivity.class);
        intent.putExtra("NameCategory", 2);
        startActivity(intent);
    }

    @Override
    public void onClickReCalc(Long id) {
        Intent intent = new Intent(getApplicationContext(), CalculationActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public void onClickFabIndexFragment() {
        AddIndexDialogFragment fragment = AddIndexDialogFragment.newInstance(null);
        fragment.show(getSupportFragmentManager(), AddIndexDialogFragment.class.getSimpleName());
    }

    @Override
    public void onClickChangeIndexFragment(VitalCharacteristic vitalCharacteristic) {
        AddIndexDialogFragment fragment = AddIndexDialogFragment.newInstance(vitalCharacteristic);
        fragment.show(getSupportFragmentManager(), AddIndexDialogFragment.class.getSimpleName());
    }
}

