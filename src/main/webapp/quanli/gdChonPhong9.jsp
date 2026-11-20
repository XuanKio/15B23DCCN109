<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="controller.ScheduleScreeningControl9,model.*,java.util.List,java.net.URLEncoder,java.nio.charset.StandardCharsets,java.time.format.DateTimeParseException" %>
<%@ include file="/WEB-INF/jspf/common.jspf" %>
<%@ include file="/WEB-INF/jspf/authQL.jspf" %>
<%
    // Bước 12-19 kịch bản v3: nhận screeningDate, timeSlotId, gọi loadAvailableRooms.
    ScheduleScreeningControl9 control = new ScheduleScreeningControl9();
    String dateParam = request.getParameter("screeningDate");
    Long slotParam = parseLong(request.getParameter("timeSlotId"));
    if (dateParam != null || slotParam != null) {
        LocalDate newDate;
        try {
            newDate = LocalDate.parse(dateParam);
        } catch (DateTimeParseException | NullPointerException e) {
            newDate = null;
        }
        if (newDate == null || slotParam == null) {
            response.sendRedirect("gdChonThoiGian9.jsp?loi=" + URLEncoder.encode("Vui lòng chọn ngày chiếu và khung giờ.", StandardCharsets.UTF_8));
            return;
        }
        // Đổi thời gian: bỏ lựa chọn phòng và phim của lượt trước.
        if (!newDate.equals(session.getAttribute("lichNgay")) || !slotParam.equals(session.getAttribute("lichKhungGio"))) {
            session.removeAttribute("lichPhong");
        }
        session.setAttribute("lichNgay", newDate);
        session.setAttribute("lichKhungGio", slotParam);
    }
    LocalDate date = (LocalDate) session.getAttribute("lichNgay");
    Long slotId = (Long) session.getAttribute("lichKhungGio");
    if (date == null || slotId == null) {
        response.sendRedirect("gdChonThoiGian9.jsp");
        return;
    }
    List<ScreeningRoom9> rooms;
    TimeSlot9 timeSlot;
    try {
        timeSlot = control.getTimeSlot(slotId);
        rooms = control.loadAvailableRooms(date, slotId, quanLi);
    } catch (IllegalArgumentException e) {
        response.sendRedirect("gdChonThoiGian9.jsp?loi=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8));
        return;
    }
    String error = request.getParameter("loi");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chọn phòng còn rảnh - CineMan</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cineman9.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/topbar.jspf" %>
<div class="card">
    <div class="card-header">Chọn phòng còn rảnh</div>
    <div class="card-body">
        <div class="context"><%= fmtDate(date) %> &nbsp;·&nbsp; <%= h(timeSlot.getLabel()) %></div>
        <% if (error != null) { %><div class="msg error"><%= h(error) %></div><% } %>
        <% if (rooms.isEmpty()) { %>
        <div class="msg note">Không còn phòng trống vào ngày và khung giờ đã chọn. Vui lòng đổi thời gian.</div>
        <% } else { %>
        <table class="data">
            <tr><th>Phòng</th><th>Sức chứa</th><th></th></tr>
            <% for (ScreeningRoom9 r : rooms) { %>
            <tr>
                <td><%= h(r.getRoomName()) %></td>
                <td><%= r.getCapacity() %> ghế</td>
                <td><a href="gdChonPhim9.jsp?roomId=<%= r.getId() %>">Chọn</a></td>
            </tr>
            <% } %>
        </table>
        <% } %>
        <div class="btn-row"><a class="btn" href="gdChonThoiGian9.jsp">Đổi thời gian</a></div>
    </div>
</div>
</body>
</html>
