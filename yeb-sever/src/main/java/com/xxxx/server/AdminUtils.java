package com.xxxx.server;

import com.xxxx.server.pojo.Admin;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Mr.Z
 * @title: AdminUtils
 * @projectName yeb
 * @description: TODO 操作员工具类
 * @date 2022/5/199:24
 */
@SuppressWarnings({"all"})
public class AdminUtils {
    //获取当前操作员
    public static Admin getCurrentAdmin(){
        return (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
