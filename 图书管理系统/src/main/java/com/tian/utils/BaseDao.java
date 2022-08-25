package com.tian.utils;
//建立操作数据库的公共类
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * ClassName: BaseDao
 * Description: 操作数据库的基类(工具类)
 */
public class BaseDao {
    // 静态代码块,在类加载的时候执行
    static {
        init();
    }

    private static String driver;
    private static String url;
    private static String user;
    private static String password;

    /**
     * MethodName: init
     * Description: 初始化连接参数,从配置文件（database.properties）里获得
     * propoties类：用来读取Java中的配置文件
     * inputstream：输入流的基类，用来读取字节数据，将文件中的数据读取到java程序中
     */
    public static void init() {
        Properties params = new Properties();
        InputStream is = BaseDao.class.getClassLoader().getResourceAsStream("database.properties");//读取database数据文件
        try {
            params.load(is);//加载文件
        } catch (IOException e) {
            e.printStackTrace();//在命令行打印异常信息在程序中出错的位置及原因
        }
        driver = params.getProperty("driver");
        url = params.getProperty("url");
        user = params.getProperty("user");
        password = params.getProperty("password");
    }

    /**
     * MethodName: getConnection
     * Description: 获取数据库连接
     * 调用Driver接口的connect（url，info）获取数据库连接
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            //   加载数据库驱动
            Class.forName(driver);
            //   连接数据库
            connection = DriverManager.getConnection(url, user, password);//数据库地址，用户名，密码
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();//在命令行打印异常信息在程序中出错的位置及原因
        }

        return connection;
    }

    //执行相关的SQL语句
    /**
     * MethodName: execute
     * Description: 查询操作
     */
    public static ResultSet executeQuery(Connection connection, PreparedStatement preparedStatement, ResultSet rs,
                                         String sql, Object[] params) throws Exception {
        preparedStatement = connection.prepareStatement(sql);//对SQL语句进行预编译（JDBC存储过程），与值有关的全部用？代替，然后将结果赋值给prepareStatement
        for (int i = 0; i < params.length; i++) {
            // 占位符从1开始,但是数组是从0开始
            preparedStatement.setObject(i + 1, params[i]);//遍历参数列表填充参数
        }
        rs = preparedStatement.executeQuery();//查询数据
        return rs;//返回查询的数据结果集
    }

    /**
     * MethodName: execute
     * Description: 增删改操作
     */
    public static int executeUpdate(Connection connection, PreparedStatement preparedStatement,
                                    String sql, Object[] params) throws Exception {
        int updateRows = 0;
        preparedStatement = connection.prepareStatement(sql);//SQL语句预编译
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);//遍历填充值
        }
        updateRows = preparedStatement.executeUpdate();
        return updateRows;//返回updateRows，使下次追加行数据不用从头开始轮播
    }

    /**
     * MethodName: closeResource
     * Description: 关闭资源
     */
    public static boolean closeResource(Connection connection, PreparedStatement preparedStatement, ResultSet rs) {
        boolean flag = true;
        if (rs != null) {
            try {
                rs.close();
                rs = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
                // 垃圾回收, 设置为空后, JVM会自动回收
                preparedStatement = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }
        if (connection != null) {
            try {
                connection.close();
                // 垃圾回收, 设置为空后, JVM会自动回收
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }
        return flag;
    }
}
