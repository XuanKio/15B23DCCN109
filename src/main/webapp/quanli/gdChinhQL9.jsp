<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/common.jspf" %>
<%@ include file="/WEB-INF/jspf/authQL.jspf" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Trang chủ nhân viên quản lí - CineMan</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cineman9.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/topbar.jspf" %>
<div class="card">
    <div class="card-header">Trang chủ nhân viên quản lí</div>
    <div class="card-body">
        <p>Rạp: <b><%= h(quanLi.getCinema().getName()) %></b></p>
        <a class="btn" href="gdQLLichChieu9.jsp">Quản lí lịch chiếu</a>
    </div>
</div>
</body>
</html>
