package com.xxxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzh
 * @since 2022-04-14
 */
public interface IMenuService extends IService<Menu> {
    /**
     * 根据用户id 查询菜单列表
     * @return 1
     */
    List<Menu> getMenusByAdminId();
/**
 * 根据用户角色 查询菜单列表
 * @return 1
 */
    List<Menu> getMenusWithRole();
    /**
     * 查询所有菜单
     * @return 1
     */
    List<Menu> getAllmenus();
}
