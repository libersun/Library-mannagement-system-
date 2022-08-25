package com.tian.service.student;

import com.tian.dao.student.StudentDao;
import com.tian.dao.student.StudentDaoImpl;
import com.tian.pojo.Student;
import com.tian.utils.BaseDao;

import java.sql.Connection;

/**
 * ClassName: StudentServiceImpl
 * Description:StudentService的实现类（根据stuid学号和password进行登录的方法）
 */
public class StudentServiceImpl implements StudentService {
    private StudentDao studentDao;

    public StudentServiceImpl() {
        this.studentDao = new StudentDaoImpl();
    }

    @Override
    public Student login(String stuId, String userPassword) {
        Connection connection = null;
        Student student = null;
        try {
            connection = BaseDao.getConnection();
            student = studentDao.getLoginStudent(connection, stuId);//获取学生学号
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);//关闭资源
        }
//        匹配密码，若不匹配，则把学生置为NULL
        if (null != student) {//学号的对应的密码要匹配
            if (!student.getPassword().equals(userPassword)) {
                student = null;
            }
        }
        return student;
    }
}
