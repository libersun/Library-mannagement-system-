<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录页面</title>
</head>
<link rel="stylesheet" href="static/css/login.css">//引用css文件中的样式
<body class="body">
<div class="box">
    <div class="login">
        <h1>柏荪图书管理系统</h1>
        <form action="${pageContext.request.contextPath}/login" method="post">//采用post方式提交form表单，提交后跳转到下一个页面//${pageContext.request.contextPath}是获取http的绝对路径
            <span class="font errorMsg">${error}</span><br>//输入错误时弹出的字
            <span class="font">学生学号: </span><input type="text" name="stuId" class="subInput" placeholder="请输入学号">
            <br>
            <span class="font pwd">密码: </span><input type="password" name="password" class="subInput"
                                                     placeholder="请输入密码"> <br>
            <br>
            <input type="submit" value="登录" class="subBtn">
        </form>
    </div>
</div>
</body>
</html>
