<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="controller.ScheduleScreeningControl9,model.*" %>
<%@ include file="/WEB-INF/jspf/common.jspf" %>
<%@ include file="/WEB-INF/jspf/authQL.jspf" %>
<%
    // Bước 39-48 kịch bản v3: hiển thị thông tin lịch, gọi confirmScreening khi nhấn Xác nhận.
    ScheduleScreeningControl9 control = new ScheduleScreeningControl9();
    LocalDate date = (LocalDate) session.getAttribute("lichNgay");
    Long slotId = (Long) session.getAttribute("lichKhungGio");
    Long roomId = (Long) session.getAttribute("lichPhong");
    Long movieId = parseLong(request.getParameter("movieId"));
    if (date == null || slotId == null || roomId == null || movieId == null) {
        response.sendRedirect("gdChonThoiGian9.jsp");
        return;
    }
    TimeSlot9 timeSlot = control.getTimeSlot(slotId);
    ScreeningRoom9 room = control.getRoom(roomId);
    Movie9 movie;
    try {
        movie = control.getMovie(movieId);
    } catch (IllegalArgumentException e) {
        response.sendRedirect("gdChonPhim9.jsp");
        return;
    }
    Screening9 saved = null;
    String error = null;
    if ("POST".equals(request.getMethod())) {
        try {
            saved = control.confirmScreening(date, slotId, roomId, movieId, quanLi);
            session.removeAttribute("lichNgay");
            session.removeAttribute("lichKhungGio");
            session.removeAttribute("lichPhong");
        } catch (IllegalArgumentException | IllegalStateException e) {
            error = e.getMessage();
        } catch (java.sql.SQLException e) {
            error = "Ghi lịch chiếu thất bại, chưa có dữ liệu nào được lưu. Vui lòng thử lại.";
        }
    }
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title><%= saved == null ? "Xác nhận lịch chiếu" : "Kết quả lập lịch" %> - CineMan</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cineman9.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/topbar.jspf" %>
<% if (saved != null) { %>
<div class="card">
    <div class="card-header">Kết quả lập lịch</div>
    <div class="card-body">
        <div class="success">Thêm lịch chiếu thành công</div>
        <p><%= fmtDate(saved.getScreeningDate()) %> &nbsp;·&nbsp; <%= h(timeSlot.getLabel()) %> &nbsp;·&nbsp; <%= h(room.getRoomName()) %></p>
        <p>Phim <b><%= h(movie.getTitle()) %></b>, giá vé cơ sở <%= fmtMoney(saved.getTicketPrice()) %> đồng, <%= room.getUsableSeats() %> ghế dùng được.</p>
        <div class="btn-row"><a class="btn" href="gdQLLichChieu9.jsp">Về quản lí lịch</a></div>
    </div>
</div>
<% } else { %>
<div class="card">
    <div class="card-header">Xác nhận lịch chiếu</div>
    <div class="card-body">
        <% if (error != null) { %>
        <div class="msg error"><%= h(error) %></div>
        <% } %>
        <div class="info">
            <span class="k">Ngày chiếu</span><span><%= fmtDate(date) %></span>
            <span class="k">Khung giờ</span><span><%= h(timeSlot.getLabel()) %></span>
            <span class="k">Phòng</span><span><%= h(room.getRoomName()) %></span>
            <span class="k">Phim</span><span><%= h(movie.getTitle()) %> (<%= movie.getDuration() %> phút)</span>
        </div>
        <form method="post" action="gdXacNhanLich9.jsp?movieId=<%= movie.getId() %>">
            <div class="btn-row">
                <button type="submit" class="btn">Xác nhận</button>
                <a href="gdChonPhim9.jsp">Chọn phim khác</a>
                <a href="gdChonPhong9.jsp">Chọn lại phòng</a>
            </div>
        </form>
    </div>
</div>
<% } %>
</body>
</html>
