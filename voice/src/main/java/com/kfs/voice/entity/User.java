package com.kfs.voice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 811610097883977248L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String username;
    private String phone;
    private String cnName;
    private String password;
    private Boolean isDelete;
}
