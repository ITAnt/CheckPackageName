package com.itant.checkpackagename;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Wzkj on 2016/4/15.
 * 已完成的任务列表
 */
public class AppAdapter extends BaseAdapter {

    private List<PackageInfo> packageInfos;
    private Context context;
    private PackageManager pm = null;
    private ClipboardManager clipboardManager;

    public void setPackageInfos(List<PackageInfo> packageInfos) {
        this.packageInfos = packageInfos;
    }

    public AppAdapter(Context context) {
        this.context = context;
        pm = context.getPackageManager();
        clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    @Override
    public int getCount() {
        return packageInfos == null ? 0 : packageInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return packageInfos == null ? null : packageInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final PackageInfo packageInfo = packageInfos.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            // 获取组件布局

            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_item, null);
            holder.tv_app_name = (TextView) convertView.findViewById(R.id.tv_app_name);
            holder.tv_package_name = (TextView) convertView.findViewById(R.id.tv_package_name);
            holder.ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        // 应用名称
        holder.tv_app_name.setText(pm.getApplicationLabel(packageInfo.applicationInfo).toString() + "    版本：" + packageInfo.versionName);

        // 应用包名
        holder.tv_package_name.setText(packageInfo.packageName);

        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clipboardManager.setText(packageInfo.packageName);
                Toast.makeText(context, "已复制包名到剪贴板", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }


    private static class ViewHolder {
        TextView tv_app_name;
        TextView tv_package_name;
        LinearLayout ll_item;
    }
}
