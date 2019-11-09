package com.rdi.mvp.presentation.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rdi.mvp.R;
import com.rdi.mvp.data.model.InstalledPackageModel;
import com.rdi.mvp.data.repository.PackageInstalledRepository;
import com.rdi.mvp.presentation.presenter.MainPresenter;

import java.util.Comparator;
import java.util.List;

public class PackageInstalledView extends AppCompatActivity implements IPackageInstalledView {
    private static final String TAG = "PackageInstalledView";

    private RecyclerView mRecyclerView;
    private View mProgressFrameLayout;
    private Button btnLoadData;
    private CheckBox mCheckBoxShowSystemPackage;
    private Spinner mSpinnerSortByPackageName;

    private MainPresenter mMainPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setListeners();
        providePresenter();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        mMainPresenter.detachView();
    }

    private void providePresenter() {
        PackageInstalledRepository packageInstalledRepository =
                new PackageInstalledRepository(this);

        mMainPresenter = new MainPresenter(this, packageInstalledRepository);
        mMainPresenter.setSortedMode(mSpinnerSortByPackageName.getSelectedItemPosition());
    }

    private void initViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(layoutManager);

        mCheckBoxShowSystemPackage = findViewById(R.id.checkbox_show_system_package);

        mSpinnerSortByPackageName = findViewById(R.id.spinner_sort);
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(this, R.array.сhose_sort, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerSortByPackageName.setAdapter(adapter);

        btnLoadData = findViewById(R.id.btn_load_data);

        mProgressFrameLayout = findViewById(R.id.progress_frame_layout);
    }

    private void setListeners() {
        mSpinnerSortByPackageName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                mMainPresenter.setSortedMode(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        btnLoadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPresenter.loadDataAsync(mCheckBoxShowSystemPackage.isChecked());
            }
        });
    }


    @Override
    public void showProgress() {
        mRecyclerView.setVisibility(View.GONE);
        mProgressFrameLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void showData(List<InstalledPackageModel> installedPackageModels) {
        Log.i(TAG, "showData: ");
        mRecyclerView.setVisibility(View.VISIBLE);
        PackageInstalledAdapter adapter = new PackageInstalledAdapter(installedPackageModels);
        mRecyclerView.setAdapter(adapter);
    }
}
