package com.tian.dao.student;

import com.tian.pojo.Student;
import com.tian.utils.BaseDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * ClassName: StudentDaoImpl
 * Description:getLoginStudent的具体实现
 */
public class StudentDaoImpl implements StudentDao {
    @Override
    public Student getLoginStudent(Connection connection, String stuId) throws Exception {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        Student student = null;
        if (null != connection) {
            String sql = "select * from `students` where stuId=?";
            Object[] params = {stuId};
            rs = BaseDao.executeQuery(connection, preparedStatement, rs, sql, params);//查询遍历
            if (rs.next()) {//游标向下移动一行若存在数据，则获取相应的学生信息
                student = new Student();
                student.setCollege(rs.getString("college"));//获取学院
                student.setGender(rs.getString("gender"));//性别
                student.setProfession(rs.getString("profession"));//专业
                student.setStartYear(rs.getString("startYear"));//入学年份
                student.setStuId(rs.getString("stuId"));//学号
                student.setStuName(rs.getString("stuName"));//学生名字
                student.setPassword(rs.getString("password"));//登录密码
            }
            BaseDao.closeResource(null, preparedStatement, rs);//关闭资源
        }
        return student;
    }
}
