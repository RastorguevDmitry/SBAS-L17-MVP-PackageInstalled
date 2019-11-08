package com.rdi.mvp.presentation.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rdi.mvp.R;
import com.rdi.mvp.data.model.InstalledPackageModel;
import com.rdi.mvp.data.repository.PackageInstalledRepository;
import com.rdi.mvp.presentation.presenter.MainPresenter;

import java.util.List;

public class PackageInstalledView extends AppCompatActivity implements IPackageInstalledView {
    private static final String TAG = "PackageInstalledView";

    private RecyclerView mRecyclerView;
    private View  mProgressFrameLayout;

    private MainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        providePresenter();
    }

    @Override
    protected void onStart() {
        super.onStart();

        mMainPresenter.loadDataAsync();
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
    }

    private void initViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(layoutManager);

        mProgressFrameLayout = findViewById(R.id.progress_frame_layout);
    }

    @Override
    public void showProgress() {
        mProgressFrameLayout.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        mProgressFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void showData(List<InstalledPackageModel> installedPackageModels) {
        Log.i(TAG, "showData: ");
        PackageInstalledAdapter adapter = new PackageInstalledAdapter(installedPackageModels);

        mRecyclerView.setAdapter(adapter);
    }
}
