package com.xxxx.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxx.server.pojo.Employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzh
 * @since 2022-04-14
 */
public interface EmployeeMapper extends BaseMapper<Employee> {


    /**
     * 获取所有员工
     * @param page
     * @param employee
     * @param beginDateScope
     * @return
     */

    IPage<Employee> getEmployeeByPage(Page<Employee> page, @Param("employee") Employee employee,
                                      @Param("beginDateScope") LocalDate[] beginDateScope);

    /**
     * 添加员工
     * @param employee
     * @return
     */
    List<Employee> getEmployee(Integer id);

    /**
     * 获取所有员工的账套
     * @param currentPage
     * @param page
     * @return
     */
    IPage<Employee> getEmployeeWithSalary(Page<Employee> page);
}
