package com.xxxx.server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxx.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzh
 * @since 2022-04-14
 */
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 根据用户id 查询菜单列表
     * @return 1
     */
     List<Menu> getMenusByAdminId(Integer id);



    /**
     * 根据用户角色 查询菜单列表
     *
     *
     */
    List<Menu> getMenusWithRole();
    /**
     * 查询所有菜单
     * @return 1
     */
    List<Menu> getAllmenus();
}
