package com.rdi.mvp.presentation.presenter;

import androidx.annotation.NonNull;

import com.rdi.mvp.data.model.InstalledPackageModel;
import com.rdi.mvp.data.repository.PackageInstalledRepository;
import com.rdi.mvp.presentation.view.IPackageInstalledView;

import java.lang.ref.WeakReference;
import java.util.List;

public class MainPresenter {
    private WeakReference<IPackageInstalledView> mMainActivityWeakReference;
    private PackageInstalledRepository mPackageInstalledRepository;

    public MainPresenter(@NonNull IPackageInstalledView IPackageInstalledView,
                         @NonNull PackageInstalledRepository packageInstalledRepository) {
        mMainActivityWeakReference = new WeakReference<>(IPackageInstalledView);
        mPackageInstalledRepository = packageInstalledRepository;
    }


    /**
     * Метод для получения данных в синхронном режиме.
     */
    // Данный метод нужен исключительно для понимания работы Unit-тестов.
    public void loadDataSync() {
        if (mMainActivityWeakReference.get() != null) {
            mMainActivityWeakReference.get().showProgress();
        }

        List<InstalledPackageModel> data = mPackageInstalledRepository.getData(true);

        if (mMainActivityWeakReference.get() != null) {
            mMainActivityWeakReference.get().hideProgress();

            mMainActivityWeakReference.get().showData(data);
        }
    }

    /**
     * Метод для загрузки данных в ассинхронном режиме.
     */
    public void loadDataAsync() {
        if (mMainActivityWeakReference.get() != null) {
            mMainActivityWeakReference.get().showProgress();
        }

        PackageInstalledRepository.OnLoadingFinishListener onLoadingFinishListener = packageModels -> {
            if (mMainActivityWeakReference.get() != null) {
                mMainActivityWeakReference.get().hideProgress();
                mMainActivityWeakReference.get().showData(packageModels);
            }
        };

        mPackageInstalledRepository.loadDataAsync(true, onLoadingFinishListener);
    }

    /**
     * Метод для отвязки прикрепленной View.
     */
    public void detachView() {
        mMainActivityWeakReference.clear();
    }
}
