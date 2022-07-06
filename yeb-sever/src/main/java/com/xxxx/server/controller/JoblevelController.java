package com.xxxx.server.controller;


import com.xxxx.server.pojo.Joblevel;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IJoblevelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zzh
 * @since 2022-04-14
 */
@RestController
@RequestMapping("/system/basic/joblevel")
public class JoblevelController {
    @Autowired
    private IJoblevelService joble;

    @ApiOperation(value = "获取所有职称")
    @GetMapping("/")
    public List<Joblevel> getAllJoblevels(){
        return joble.list();
    }
    @ApiOperation(value = "添加职称")
    @PostMapping("/")
    public RespBean addJoblevel(@RequestBody Joblevel joblevel){
        joblevel.setCreateDate(LocalDateTime.now());
        if (joble.save(joblevel)){
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败!");

    }

    @ApiOperation(value = "更新职称")
    @PutMapping("/")
    public  RespBean updateJolevel(@RequestBody Joblevel joblevel){
        if (joble.updateById(joblevel)){
            return RespBean.success("更新成功");
        }
        return RespBean.error("添加失败");
    }
    @ApiOperation(value="删除职称")
    @DeleteMapping("/{id}")
    public RespBean deleteJobLevel(@PathVariable Integer id){
        if (joble.removeById(id)){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
    @ApiOperation(value="批量删除职称")
    @DeleteMapping("/")
    public RespBean deleteJobLevel(Integer[] ids){
        if (joble.removeByIds(Arrays.asList(ids))){
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败");
    }


}
