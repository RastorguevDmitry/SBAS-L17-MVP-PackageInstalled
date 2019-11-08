package com.rdi.mvp.presentation.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rdi.mvp.R;
import com.rdi.mvp.data.model.InstalledPackageModel;

import java.util.List;

public class PackageInstalledAdapter extends RecyclerView.Adapter<PackageInstalledAdapter.PackageInstalledViewHolder> {
    private List<InstalledPackageModel> mInstalledPackageModelList;

    public PackageInstalledAdapter(@NonNull List<InstalledPackageModel> installedPackageModelList) {
        mInstalledPackageModelList = installedPackageModelList;
    }


    @NonNull
    @Override
    public PackageInstalledViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PackageInstalledViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.package_instolled_view_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull PackageInstalledViewHolder holder, int position) {
        holder.bindView(mInstalledPackageModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return mInstalledPackageModelList.size();
    }

    class PackageInstalledViewHolder extends RecyclerView.ViewHolder {
        private TextView mAppTextView;
        private TextView mPackageNameTextView;
        private ImageView mIconImageView;


        public PackageInstalledViewHolder(@NonNull View itemView) {
            super(itemView);
            mAppTextView = itemView.findViewById(R.id.app_name);
            mPackageNameTextView = itemView.findViewById(R.id.package_name);
            mIconImageView = itemView.findViewById(R.id.app_icon_image_view);
        }

        void bindView(InstalledPackageModel installedPackageModel) {
            mAppTextView.setText(installedPackageModel.getAppName());
            mPackageNameTextView.setText(installedPackageModel.getAppPackageName());
            mIconImageView.setImageDrawable(installedPackageModel.getAppIcon());
        }
    }

}
