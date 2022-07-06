package com.xxxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzh
 * @since 2022-04-14
 */
public interface IAdminService extends IService<Admin> {
    /**
     * 登录之后返回token
     * @param request 1
     * @param username 1
     * @param password 1
     * @param code 1
     * @return 1
     */
    RespBean login(String username, String password, String code, HttpServletRequest request);

    /**
     * 根据用户名获取用户
     * @param username 1
     * @return 1
     */
    Admin getAdminByUserName(String username);

    /**
     * 通过用户id查询菜单列表
     * @return 1
     */
    List<Role> getRoles(Integer adminId);
    /**
     * 获取所有操作员
     * @return 1
     */

    List<Admin> getAllAdmins(String keywords);

    /**
     * 更新操作员角色
     * @param adminId
     * @param rids
     * @return
     */
    RespBean updateAdminRole(Integer adminId, Integer[] rids);

    /**
     * 更信用户密码
     * @param oldPass
     * @param pass
     * @param adminId
     * @return
     */
    RespBean updateAdminPassword(String oldPass, String pass, Integer adminId);
}
