package com.kfs.voice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "user_rel_checker")
public class UserRelChecker implements Serializable {

    private static final long serialVersionUID = -9194248853701442915L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String userId;
    private String checkerId;
}
