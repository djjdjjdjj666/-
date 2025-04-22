package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

//这个注解能自动生成get set等等的方法
@Data
public class EmployeeDTO implements Serializable {

    private Long id;

    private String username;

    private String name;

    private String phone;

    private String sex;

    private String idNumber;

}
