package com.xxxx.server.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author Mr.Z
 * @title: ChatMsg
 * @projectName yeb
 * @description: TODO
 * @date 2022/5/2716:16
 */
@SuppressWarnings({"all"})
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ChatMsg {

    private String from;
    private String to;
    private String content;
    private LocalDateTime data;
    private String formNickName;






}
