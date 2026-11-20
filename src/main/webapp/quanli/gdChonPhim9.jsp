<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="controller.ScheduleScreeningControl9,model.*,java.util.List,java.net.URLEncoder,java.nio.charset.StandardCharsets" %>
<%@ include file="/WEB-INF/jspf/common.jspf" %>
<%@ include file="/WEB-INF/jspf/authQL.jspf" %>
<%
    // Bước 21-26 và 36-39 kịch bản v3: nhận roomId, gọi loadShowingMovies; khi chọn phim gọi validateMovie.
    ScheduleScreeningControl9 control = new ScheduleScreeningControl9();
    LocalDate date = (LocalDate) session.getAttribute("lichNgay");
    Long slotId = (Long) session.getAttribute("lichKhungGio");
    if (date == null || slotId == null) {
        response.sendRedirect("gdChonThoiGian9.jsp");
        return;
    }
    Long roomParam = parseLong(request.getParameter("roomId"));
    if (roomParam != null) {
        boolean available = false;
        for (ScreeningRoom9 r : control.loadAvailableRooms(date, slotId, quanLi)) {
            available = available || r.getId() == roomParam;
        }
        if (!available) {
            response.sendRedirect("gdChonPhong9.jsp?loi=" + URLEncoder.encode("Phòng đã chọn không còn trống, vui lòng chọn phòng khác.", StandardCharsets.UTF_8));
            return;
        }
        session.setAttribute("lichPhong", roomParam);
    }
    Long roomId = (Long) session.getAttribute("lichPhong");
    if (roomId == null) {
        response.sendRedirect("gdChonPhong9.jsp");
        return;
    }
    TimeSlot9 timeSlot = control.getTimeSlot(slotId);
    ScreeningRoom9 room = control.getRoom(roomId);
    String error = null;

    Long movieId = parseLong(request.getParameter("movieId"));
    if (movieId != null) {
        Movie9 movie = null;
        try {
            movie = control.getMovie(movieId);
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        }
        if (movie != null) {
            if (control.validateMovie(movie, timeSlot)) {
                response.sendRedirect("gdXacNhanLich9.jsp?movieId=" + movieId);
                return;
            }
            error = "Phim \"" + movie.getTitle() + "\" (" + movie.getDuration() + " phút) không vừa khung giờ "
                    + timeSlot.getLabel() + " hoặc chưa có giá cơ sở. Vui lòng chọn phim khác hoặc đổi giờ.";
        }
    }
    List<Movie9> movies = control.loadShowingMovies();
    Long addedId = parseLong(request.getParameter("added"));
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chọn phim đang chiếu - CineMan</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cineman9.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/topbar.jspf" %>
<div class="card">
    <div class="card-header">Chọn phim đang chiếu</div>
    <div class="card-body">
        <div class="context"><%= fmtDate(date) %> &nbsp;·&nbsp; <%= h(timeSlot.getLabel()) %> &nbsp;·&nbsp; <%= h(room.getRoomName()) %></div>
        <% if (error != null) { %><div class="msg error"><%= h(error) %></div><% } %>
        <% if (addedId != null) { %><div class="msg note">Đã thêm phim mới. Danh sách phim đã được cập nhật.</div><% } %>
        <% if (movies.isEmpty()) { %>
        <div class="msg note">Chưa có phim đang chiếu. Hãy thêm phim mới.</div>
        <% } else { %>
        <table class="data">
            <tr><th>Tên phim</th><th>Thời lượng</th><th></th></tr>
            <% for (Movie9 m : movies) { %>
            <tr <%= Long.valueOf(m.getId()).equals(addedId) ? "style=\"background:#eef6ea\"" : "" %>>
                <td><%= h(m.getTitle()) %></td>
                <td><%= m.getDuration() %> phút</td>
                <td><a href="gdChonPhim9.jsp?movieId=<%= m.getId() %>">Chọn</a></td>
            </tr>
            <% } %>
        </table>
        <% } %>
        <div class="btn-row">
            <a class="btn" href="gdThemPhim9.jsp">Thêm phim mới</a>
            <a href="gdChonPhong9.jsp">Đổi phòng</a>
            <a href="gdChonThoiGian9.jsp">Đổi thời gian</a>
        </div>
    </div>
</div>
</body>
</html>
