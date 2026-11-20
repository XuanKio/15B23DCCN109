<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/common.jspf" %>
<%@ include file="/WEB-INF/jspf/authQL.jspf" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lí lịch chiếu - CineMan</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cineman9.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/topbar.jspf" %>
<div class="card">
    <div class="card-header">Quản lí lịch chiếu</div>
    <div class="card-body">
        <div class="btn-row" style="margin-top:0">
            <a class="btn" href="gdChonThoiGian9.jsp?moi=1">Thêm lịch chiếu</a>
            <a href="gdChinhQL9.jsp">Về trang chủ</a>
        </div>
    </div>
</div>
</body>
</html>
