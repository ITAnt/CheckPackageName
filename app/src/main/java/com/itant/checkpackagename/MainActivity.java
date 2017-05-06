package com.itant.checkpackagename;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppAdapter adapter;
    private ListView lv_package;
    private List<PackageInfo> packageInfos;
    private PackageManager pm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_refresh = (Button) findViewById(R.id.btn_refresh);
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetAppListTask().execute();
            }
        });

        pm = getPackageManager();
        packageInfos = new ArrayList<>();

        lv_package = (ListView) findViewById(R.id.lv_package);
        adapter = new AppAdapter(this);
        adapter.setPackageInfos(packageInfos);
        lv_package.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new GetAppListTask().execute();
    }

    private class GetAppListTask extends AsyncTask<Void, Void, List<PackageInfo>> {

        @Override
        protected List<PackageInfo> doInBackground(Void... params) {
            return getPackageManager().getInstalledPackages(0);
        }

        @Override
        protected void onPostExecute(List<PackageInfo> results) {

            if (results != null) {
                if (packageInfos != null) {
                    packageInfos.clear();
                }

                packageInfos.addAll(results);
                Collections.sort(packageInfos, new Comparator<PackageInfo>() {

                    @Override
                    public int compare(PackageInfo lhs, PackageInfo rhs) {
                        Hanzi2Pinyin hanzi2Pinyin = new Hanzi2Pinyin();
                        // TODO Auto-generated method stub
                        return hanzi2Pinyin.getStringPinYin(pm.getApplicationLabel(lhs.applicationInfo).toString().replaceAll(" ", "")).compareToIgnoreCase(hanzi2Pinyin.getStringPinYin(pm.getApplicationLabel(rhs.applicationInfo).toString().replaceAll(" ", "")));
                    }
                });

                Toast.makeText(MainActivity.this, "列表已刷新", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }
        }
    }
}
