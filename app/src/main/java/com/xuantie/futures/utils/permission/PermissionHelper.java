package com.xuantie.futures.utils.permission;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import com.xuantie.futures.App;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by Administrator on 2018/9/18.
 */

public class PermissionHelper {
    private static PermissionHelper mInstance;
    private IPermissionListener mPermissionListener;

    private PermissionHelper() {
    }

    public static PermissionHelper getInstance() {
        if (mInstance == null) {
            mInstance = new PermissionHelper();
        }
        return mInstance;
    }

    public void request(String[] permissions, IPermissionListener permissionListener) {
        WeakReference<Context> contextRef = new WeakReference<>(App.sContext);
        Context context = contextRef.get();
        if (context == null) return;

        mPermissionListener = permissionListener;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            //低于6.0无需动态申请权限
            mPermissionListener.onAllowed();
        } else {
            List<String> shouldRequestPermission = checkPermissions(permissions);
            if (shouldRequestPermission.size() <= 0) {
                mPermissionListener.onAllowed();
            } else {
                Intent intent = new Intent();
                intent.setClass(context, PermissionActivity.class);
                intent.putExtra(PermissionActivity.PERMISSIONS_PARAMS, shouldRequestPermission.toArray(new String[shouldRequestPermission.size()]));
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }
    }

    IPermissionListener getPermissionListener() {
        return mPermissionListener;
    }

    public void destroy() {
        mInstance = null;
    }

    private List<String> checkPermissions(String[] requestPermissions) {
        List<String> shouldRequestPermission = new ArrayList<>();
        for (String permission : requestPermissions) {
            if (!(ContextCompat.checkSelfPermission(App.sContext, permission) == PackageManager.PERMISSION_GRANTED)) {
                shouldRequestPermission.add(permission);
            }
        }
        return shouldRequestPermission;
    }
}
