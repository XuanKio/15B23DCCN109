<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="controller.MovieSearchControl9,model.Movie9,java.util.List" %>
<%@ include file="/WEB-INF/jspf/common.jspf" %>
<%@ include file="/WEB-INF/jspf/authKH.jspf" %>
<%
    // Bước 4-11 kịch bản v3: nhận từ khóa, gọi searchMovies, hiển thị danh sách.
    String keyword = request.getParameter("keyword");
    List<Movie9> movies = null;
    String message = null;
    if (keyword != null) {
        try {
            movies = new MovieSearchControl9().searchMovies(keyword);
            session.setAttribute("tukhoaTimPhim", keyword.trim());
            if (movies.isEmpty()) {
                message = "Không tìm thấy phim nào có tên chứa \"" + keyword.trim() + "\".";
            }
        } catch (IllegalArgumentException e) {
            message = e.getMessage();
        }
    }
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Tìm kiếm thông tin phim - CineMan</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cineman9.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/topbar.jspf" %>
<div class="card">
    <div class="card-header">Tìm kiếm thông tin phim</div>
    <div class="card-body">
        <form method="get" action="gdTimPhim9.jsp">
            <label for="keyword">Tên phim</label>
            <input type="text" id="keyword" name="keyword" value="<%= h(keyword) %>" placeholder="Nhập tên hoặc một phần tên phim" autofocus>
            <div class="btn-row">
                <button type="submit" class="btn">Tìm kiếm</button>
                <a href="gdChinhKH9.jsp">Về trang chủ</a>
            </div>
        </form>
        <% if (message != null) { %><div class="msg note"><%= h(message) %></div><% } %>
        <% if (movies != null && !movies.isEmpty()) { %>
        <table class="data">
            <tr><th>Tên phim</th><th>Thời lượng</th><th></th></tr>
            <% for (Movie9 m : movies) { %>
            <tr>
                <td><%= h(m.getTitle()) %></td>
                <td><%= m.getDuration() %> phút</td>
                <td><a href="gdChiTietPhim9.jsp?movieId=<%= m.getId() %>">Chọn</a></td>
            </tr>
            <% } %>
        </table>
        <% } %>
    </div>
</div>
</body>
</html>
