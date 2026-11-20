<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="dao.AccountDAO9,model.*,java.util.Optional" %>
<%@ include file="/WEB-INF/jspf/common.jspf" %>
<%
    String error = null;
    String username = request.getParameter("username");
    if ("POST".equals(request.getMethod())) {
        String password = request.getParameter("password");
        if (username == null || username.isBlank() || password == null || password.isEmpty()) {
            error = "Vui lòng nhập tên đăng nhập và mật khẩu.";
        } else {
            Optional<Account9> account = new AccountDAO9().checkLogin(username.trim(), password.trim());
            if (account.isEmpty()) {
                error = "Sai tên đăng nhập hoặc mật khẩu.";
            } else {
                session.invalidate();
                HttpSession newSession = request.getSession(true);
                if (account.get() instanceof Manager9) {
                    newSession.setAttribute("quanli", account.get());
                    response.sendRedirect("quanli/gdChinhQL9.jsp");
                } else {
                    newSession.setAttribute("khachhang", account.get());
                    response.sendRedirect("khachhang/gdChinhKH9.jsp");
                }
                return;
            }
        }
    } else if ("login".equals(request.getParameter("err"))) {
        error = "Vui lòng đăng nhập để tiếp tục.";
    }
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập - CineMan</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cineman9.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/topbar.jspf" %>
<div class="card" style="max-width: 460px">
    <div class="card-header">Đăng nhập</div>
    <div class="card-body">
        <% if (error != null) { %><div class="msg error"><%= h(error) %></div><% } %>
        <form method="post" action="gdDangNhap9.jsp" autocomplete="off">
            <label for="username">Tên đăng nhập</label>
            <input type="text" id="username" name="username" value="<%= h(username) %>" autocomplete="off" autocapitalize="off" spellcheck="false" autofocus>
            <label for="password">Mật khẩu</label>
            <input type="password" id="password" name="password" autocomplete="new-password">
            <div class="btn-row"><button type="submit" class="btn">Đăng nhập</button></div>
        </form>
    </div>
</div>
</body>
</html>
