package com.tian.pojo;

/**
 * ClassName: Student
 * Description: Student的实体类
 */
public class Student {
    /**
     * 学号
     */
    private String stuId;
    /**
     * 学院
     */
    private String college;
    /**
     * 专业
     */
    private String profession;
    /**
     * 学生姓名
     */
    private String stuName;
    /**
     * 入学年份
     */
    private String startYear;
    /**
     * 性别
     */
    private String gender;

    /**
     * 密码
     */
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Student{" +
                "stuId='" + stuId + '\'' +
                ", college='" + college + '\'' +
                ", profession='" + profession + '\'' +
                ", stuName='" + stuName + '\'' +
                ", startYear='" + startYear + '\'' +
                ", gender='" + gender + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
