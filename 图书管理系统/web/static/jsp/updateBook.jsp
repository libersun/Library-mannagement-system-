<%@ page import="com.tian.pojo.Book" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改信息</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 引入 Bootstrap -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <h3 class="text-center text-error">
                修改图书信息页面
            </h3>
            <dl class="dl-horizontal">
                <dt>
                    提示信息：
                </dt>
                <dd>
                    你可以在这里随意修改书籍的信息，但是不可以修改书籍的编号。
                </dd>
            </dl>
        </div>
    </div>

    <div class="row clearfix">
        <div class="col-md-12 column">
            <form role="form" action="${pageContext.request.contextPath}/updateBook" method="post">
                <% Book book = (Book) request.getAttribute("book");%>
                <div class="form-group">
                    <label>图书编号:</label><input type="text" class="form-control"
                                               name="bookId" readonly value="<%= book.getBookId()%>"/>//只读
                </div>
                <div class="form-group">
                    <label>图书名:</label><input type="text" class="form-control"
                                              name="bookName" required value="<%= book.getBookName()%>"/>//需要赋值的框
                </div>
                <div class="form-group">
                    <label>作者:</label><input type="text" class="form-control"
                                             name="author" required value="<%= book.getAuthor()%>"/>
                </div>
                <div class="form-group">
                    <label>Pas库存sword</label><input type="text" class="form-control"
                                                    name="inventory" required value="<%= book.getInventory()%>"/>
                </div>
                <button type="submit" class="btn btn-default">提交</button>
                <a href="${pageContext.request.contextPath}/books">返回首页</a>
            </form>
        </div>
    </div>
</div>
</body>