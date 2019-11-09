package com.rdi.mvp.presentation.view;

import com.rdi.mvp.data.model.InstalledPackageModel;

import java.util.List;

public interface IPackageInstalledView {
    /**
     * Показать ProgressBar.
     */
    void showProgress();

    /**
     * Скрыть ProgressBar.
     */
    void hideProgress();

    /**
     * Отобразить данные об установленных приложениях.
     *
     * @param installedPackageModels список приложений.
     */
    void showData(List<InstalledPackageModel> installedPackageModels);


}
