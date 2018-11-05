package com.xuantie.futures.utils.permission;

import java.util.List;

/**
 * Created by Administrator on 2018/9/18.
 */

public interface IPermissionListener {
    /**
     * 申请成功
     */
    void onAllowed();

    /**
     * 申请失败
     *
     * @param permissions 申请失败的权限
     */
    void onRefused(List<String> permissions);
}
