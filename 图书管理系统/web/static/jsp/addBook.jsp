<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>新增书籍</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="../css/add.css" rel="stylesheet">
</head>

<body>
<div class="container">
    <h1>
        新增书籍页面
    </h1>
    <div class="fom">
        <form action="${pageContext.request.contextPath}/addBook" method="post">
            //required placeholder:为表单加上默认的文字提示和必填属性
            <span> 图书编号：</span><input type="text" name="bookId" required placeholder="请输入要添加的图书编号"><br><br><br>
            <span style="margin-left: 20px;"> 图书名：</span><input type="text" name="bookName" required
                                                                placeholder="请输入要添加的图书名"><br><br><br>
            <span style="margin-left: 42px;"> 作者：</span><input type="text" name="author" required
                                                               placeholder="请输入要添加的图书的作者"><br><br><br>
            <span style="margin-left: 42px;"> 库存：</span><input type="text" name="inventory" required
                                                               placeholder="请输入要添加的图书的库存数量"><br><br><br>

            <button type="submit">添加</button>
            <a href="${pageContext.request.contextPath}/books">返回首页</a>
        </form>
    </div>
</div>

</body>
