package com.rdi.mvp.data.model;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;


public class InstalledPackageModel implements Comparable<InstalledPackageModel> {

    private String mAppName;

    private String mAppPackageName;

    private Drawable mAppIcon;

    private boolean mIsSystem;


    public InstalledPackageModel(@NonNull String appName, @NonNull String appPackageName, @NonNull Drawable appIcon, @NonNull boolean isSystem) {
        mAppName = appName;
        mAppPackageName = appPackageName;
        mAppIcon = appIcon;
        mIsSystem = isSystem;
    }

    @NonNull
    public String getAppName() {
        return mAppName;
    }


    @NonNull
    public String getAppPackageName() {
        return mAppPackageName;
    }

    @NonNull
    public Drawable getAppIcon() {
        return mAppIcon;
    }

    @NonNull
    public boolean getIsSystem() {
        return mIsSystem;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InstalledPackageModel)) {
            return false;
        }

        InstalledPackageModel that = (InstalledPackageModel) o;

        if (getAppName() != null ? !getAppName().equals(that.getAppName()) : that.getAppName() != null) {
            return false;
        }
        if (getAppPackageName() != null ? !getAppPackageName().equals(that.getAppPackageName()) : that.getAppPackageName() != null) {
            return false;
        }
        return getAppIcon() != null ? getAppIcon().equals(that.getAppIcon()) : that.getAppIcon() == null;
    }

    @Override
    public int hashCode() {
        int result = getAppName() != null ? getAppName().hashCode() : 0;
        result = 31 * result + (getAppPackageName() != null ? getAppPackageName().hashCode() : 0);
        result = 31 * result + (getAppIcon() != null ? getAppIcon().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InstalledPackageModel{");
        sb.append("mAppName='").append(mAppName).append('\'');
        sb.append(", mAppPackageName='").append(mAppPackageName).append('\'');
        sb.append(", mAppIcon=").append(mAppIcon);
        sb.append('}');
        return sb.toString();
    }


    @Override
    public int compareTo(InstalledPackageModel o) {
        return this.mAppName.compareTo(o.getAppName());
    }
}
