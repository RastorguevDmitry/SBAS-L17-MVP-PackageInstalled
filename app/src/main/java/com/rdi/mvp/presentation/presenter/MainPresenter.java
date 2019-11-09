package com.rdi.mvp.presentation.presenter;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.rdi.mvp.data.model.InstalledPackageModel;
import com.rdi.mvp.data.repository.PackageInstalledRepository;
import com.rdi.mvp.presentation.view.IPackageInstalledView;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

public class MainPresenter {
    private WeakReference<IPackageInstalledView> mPackageInstalledWeakReference;
    private PackageInstalledRepository mPackageInstalledRepository;
    private List<InstalledPackageModel> mPackageModels;
    private int mSortedMode;

    private static final int SORT_AZ = 0;
    private static final int SORT_ZA = 1;


    public MainPresenter(@NonNull IPackageInstalledView IPackageInstalledView,
                         @NonNull PackageInstalledRepository packageInstalledRepository) {
        mPackageInstalledWeakReference = new WeakReference<>(IPackageInstalledView);
        mPackageInstalledRepository = packageInstalledRepository;
    }


    /**
     * Метод для получения данных в синхронном режиме.
     */
    // Данный метод нужен исключительно для понимания работы Unit-тестов.
    public void loadDataSync(boolean isSystem) {
        if (mPackageInstalledWeakReference.get() != null) {
            mPackageInstalledWeakReference.get().showProgress();
        }

        mPackageModels = mPackageInstalledRepository.getData(isSystem);

        if (mPackageInstalledWeakReference.get() != null) {
            mPackageInstalledWeakReference.get().hideProgress();

            sortInstalledPackageModel();
        }
    }

    /**
     * Метод для загрузки данных в ассинхронном режиме.
     */
    public void loadDataAsync(boolean isSystem) {
        if (mPackageInstalledWeakReference.get() != null) {
            mPackageInstalledWeakReference.get().showProgress();
        }

        PackageInstalledRepository.OnLoadingFinishListener onLoadingFinishListener =
                new PackageInstalledRepository.OnLoadingFinishListener() {
                    @Override
                    public void onFinish(List<InstalledPackageModel> packageModels) {
                        mPackageModels = packageModels;

                        if (mPackageInstalledWeakReference.get() != null) {
                            mPackageInstalledWeakReference.get().hideProgress();
                            sortInstalledPackageModel();
                        }
                    }
                };
        mPackageInstalledRepository.loadDataAsync(isSystem, onLoadingFinishListener);
    }

    /**
     * Метод для сортировки по AppName
     */
    public void sortInstalledPackageModel() {
        switch (mSortedMode) {
            case SORT_AZ:
                Collections.sort(mPackageModels);
                break;
            case SORT_ZA:
                Collections.reverse(mPackageModels);
                break;
        }
        mPackageInstalledWeakReference.get().showData(mPackageModels);
    }

    /**
     * Установка способа сортировки
     */
    public void setSortedMode(int sortedMode) {
        mSortedMode = sortedMode;
        if (mPackageModels != null) {
            sortInstalledPackageModel();
        }
    }


    /**
     * Метод для отвязки прикрепленной View.
     */
    public void detachView() {
        mPackageInstalledWeakReference.clear();
    }



}
