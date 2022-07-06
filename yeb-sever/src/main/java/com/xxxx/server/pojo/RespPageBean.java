package com.xxxx.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Mr.Z
 * @title: RespPageBean
 * @projectName yeb
 * @description: TODO
 * @date 2022/5/1916:09
 */
@SuppressWarnings({"all"})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespPageBean {
    /**
     * 总条数
     *
     */
    private long total;
    /**
     * 数据
     *
     */
    private List<?> data;
}
