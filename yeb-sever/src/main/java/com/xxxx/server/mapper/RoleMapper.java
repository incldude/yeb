package com.xxxx.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxx.server.pojo.Role;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzh
 * @since 2022-04-14
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 通过用户id查询菜单列表
     * @return 1
     */
    List<Role> getRoles(Integer adminId);
}
