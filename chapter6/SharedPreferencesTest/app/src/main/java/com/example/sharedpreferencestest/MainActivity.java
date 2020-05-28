package com.example.sharedpreferencestest;

import android.Manifest;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.otaserver.android.dao.DeviceInfo;
import com.otaserver.android.util.DeviceInfoGsonUtil;
import com.otaserver.android.util.DeviceInfoTextUtil;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    static final String sharedPrefsName = "MyDeviceInfo";


    DeviceInfoGsonUtil devInfoUtil = new DeviceInfoGsonUtil();
//    DeviceInfoTextUtil devInfoUtil = new DeviceInfoTextUtil();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //无权限记录设备id
        updateDeviceInfoNoNeedPermission();

        // 调用带权限检查的 取设备信息方法。
        MainActivityPermissionsDispatcher.updateDeviceinfoWithPermissionCheck(this);

        Button saveData = (Button) findViewById(R.id.save_data);
        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //在按钮中执行才进行权限提示,这里是一个内部类调用。
                MainActivityPermissionsDispatcher.updateDeviceinfoWithPermissionCheck(MainActivity.this);
            }
        });

        Button printData = (Button) findViewById(R.id.restore_data);
        printData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences(sharedPrefsName, MODE_PRIVATE);
                DeviceInfo devInfo = devInfoUtil.load(pref);
                Log.d(TAG, "devInfo=" + devInfo);
            }
        });
    }


    /**
     * 获取不需要运行时权限获取DeviceInfo的例子。
     */
    void updateDeviceInfoNoNeedPermission() {
        DeviceInfo deviceInfo = devInfoUtil.getDeviceInfoNoNeedPermission(this.getContentResolver());
        SharedPreferences pref = getSharedPreferences(sharedPrefsName, MODE_PRIVATE);
        devInfoUtil.save(deviceInfo, pref);
        Log.d(TAG, "do updateDeviceInfoNoNeedPermission()");
    }

    /**
     * 需要运行时权限获取DeviceInfo的例子
     */
    @NeedsPermission(Manifest.permission.READ_PHONE_STATE)
//    @SuppressLint("MissingPermission")
    void updateDeviceinfo() {
        DeviceInfo deviceInfo = devInfoUtil.getDeviceInfoPermission(this.getApplicationContext());
        SharedPreferences pref = getSharedPreferences(sharedPrefsName, MODE_PRIVATE);
        devInfoUtil.save(deviceInfo, pref);
        Log.d(TAG, "do updateDeviceInfoPermission()");
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


}
