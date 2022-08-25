<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>借书界面</title>
    <!-- 引入 Bootstrap -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    我借的书籍: <small>Subtext for header</small>
                </h1>
            </div>
            <a href="${pageContext.request.contextPath}/books" class="btn btn-primary"
               style="margin: auto;width: 200px;height: 35px;border-radius: 15px;line-height: 20px;font-size: 18px;">返回到主界面</a>
        </div>
    </div>
    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table">
                <thead>
                <tr>
                    <th>图书编号</th>
                    <th>图书名</th>
                    <th>作者</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="book" items="${requestScope.get('myBooks')}">
                    <tr class="success">
                        <td>${book.getBookId()}</td>
                        <td>${book.getBookName()}</td>
                        <td>${book.getAuthor()}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/returnBook?stuId=${studentSession.stuId}&bookName=${book.getBookName()}">归还</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
