<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>柏荪图书管理系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 引入 Bootstrap -->//Bootstrap前端框架
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
//以下class样式都放在了css文件中
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1 style="text-align: center;color: orange;">
                    柏荪图书管理系统
                </h1>
                <a href="${pageContext.request.contextPath}/jumpToBorrow?stuId=${studentSession.stuId}"
                   class="btn btn-primary"
                   style="margin: auto;width: 200px;height: 35px;border-radius: 15px;line-height: 20px;font-size: 18px;">我借的书籍</a>

                <input type="text" readonly value="当前用户: ${studentSession.stuId}" disabled="disabled"
                       style="margin-left: 500px;border-radius: 10px">
                <a href="${pageContext.request.contextPath}/jumpToLogin"
                   class="btn btn-primary"
                   style="margin: auto;width: 200px;height: 35px;border-radius: 15px;line-height: 20px;font-size: 18px;">注销登录</a>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-4 column">//bootstrap样式，表示将覆盖手机或平板1/3的屏幕
            //btn btn-primary:bootstrap的按钮样式
            //${pageContext.request.contextPath}是JSP取得绝对路径的方法
            <a class="btn btn-primary"
               href="${pageContext.request.contextPath}/add"
               style="background-color:#ccc;width: 150px; color: black;font-weight: bold;font-size: 16px"
            >新增</a>
        </div>
        <div class="col-md-4 column">
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/books"
               style="background-color:#ccc;width: 150px; color: black;font-weight: bold;font-size: 16px">显示所有书籍</a>
        </div>
        <div class="col-md-4 column">
            <form method="post" action="${pageContext.request.contextPath}/getBooksByName">
                <label>
                    <input type="text" name="bookName" class="form-control" placeholder="请输入书籍名称" required>
                </label>
                <input type="submit" value="查询" class="btn btn-primary"
                       style="background-color:#ccc;width: 50px; color: black;font-weight: bold;font-size: 16px">
            </form>
        </div>
    </div>

    <div class="row clearfix">//清除浮动，不受其他样式控制（bootstrap中）
        <div class="col-md-12 column">
            <table class="table table-hover table-striped">//bootstrap中的任一行启用鼠标悬停状态（表格样式，背景为灰色）
                <thead>
                <tr>
                    <th>图书编号</th>
                    <th>图书名</th>
                    <th>作者</th>
                    <th>库存</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="book" items="${requestScope.get('books')}">
                    <tr>
                        <td>${book.getBookId()}</td>
                        <td>${book.getBookName()}</td>
                        <td>${book.getAuthor()}</td>
                        <td>${book.getInventory()}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/update?bookId=${book.getBookId()}">更改</a>
                            |
                            <a href="${pageContext.request.contextPath}/delBook?bookId=${book.getBookId()}">删除</a>
                            |
                            <a href="${pageContext.request.contextPath}/borrowBook?bookName=${book.getBookName()}&
bookId=${book.getBookId()}&author=${book.getAuthor()}&stuId=${studentSession.stuId}" onclick="x()">借书</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>