<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/common.jspf" %>
<%
    // Trang của nhân viên bán hàng: chức năng bán vé, cấp thẻ nằm ngoài hai mô-đun được cài đặt.
    if (!(session.getAttribute("banhang") instanceof model.SalesEmployee9)) {
        response.sendRedirect(request.getContextPath() + "/gdDangNhap9.jsp?err=login");
        return;
    }
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Trang chủ nhân viên bán hàng - CineMan</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cineman9.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/topbar.jspf" %>
<div class="card">
    <div class="card-header">Trang chủ nhân viên bán hàng</div>
    <div class="card-body">
        <p>Chưa có chức năng nào.</p>
    </div>
</div>
</body>
</html>
