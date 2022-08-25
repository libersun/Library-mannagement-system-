package com.tian.service.student;

import com.tian.pojo.Student;

/**
 * ClassName: StudentService
 * Description:service调用dao层
 */
public interface StudentService {
    /**
     * MethodName: login
     * Description: 根据stuId和password登录系统的方法
     */
    public Student login(String stuId, String userPassword);

}
