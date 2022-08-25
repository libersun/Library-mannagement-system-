package com.tian.dao.student;

import com.tian.pojo.Student;

import java.sql.Connection;

/**
 * ClassName: StudentDao
 * Description: students表的dao层（之间操作数据库的代码）
 */
public interface StudentDao {
    /**
     * MethodName: getLoginStudent
     * Description: 通过stuId获得登录图书管理系统的学生信息
     */
    public Student getLoginStudent(Connection connection, String stuId) throws Exception;


}
