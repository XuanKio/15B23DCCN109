<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="controller.ScheduleScreeningControl9,model.TimeSlot9,java.util.List" %>
<%@ include file="/WEB-INF/jspf/common.jspf" %>
<%@ include file="/WEB-INF/jspf/authQL.jspf" %>
<%
    // Bước 5-11 kịch bản v3: gọi loadTimeSlots, cho chọn ngày và khung giờ.
    if (request.getParameter("moi") != null) {
        session.removeAttribute("lichNgay");
        session.removeAttribute("lichKhungGio");
        session.removeAttribute("lichPhong");
    }
    List<TimeSlot9> timeSlots = new ScheduleScreeningControl9().loadTimeSlots();
    LocalDate chosenDate = (LocalDate) session.getAttribute("lichNgay");
    Long chosenSlot = (Long) session.getAttribute("lichKhungGio");
    String error = request.getParameter("loi");
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chọn ngày và khung giờ - CineMan</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cineman9.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/topbar.jspf" %>
<div class="card">
    <div class="card-header">Chọn ngày và khung giờ</div>
    <div class="card-body">
        <% if (error != null) { %><div class="msg error"><%= h(error) %></div><% } %>
        <form method="get" action="gdChonPhong9.jsp">
            <div class="grid-2">
                <div>
                    <label for="screeningDate">Ngày chiếu</label>
                    <input type="date" id="screeningDate" name="screeningDate" required min="<%= LocalDate.now() %>"
                           value="<%= chosenDate == null ? "" : chosenDate.toString() %>">
                </div>
                <div>
                    <label for="timeSlotId">Khung giờ</label>
                    <select id="timeSlotId" name="timeSlotId" required>
                        <option value="">-- Chọn khung giờ --</option>
                        <% for (TimeSlot9 t : timeSlots) { %>
                        <option value="<%= t.getId() %>" <%= Long.valueOf(t.getId()).equals(chosenSlot) ? "selected" : "" %>>
                            <%= h(t.getLabel()) %> (<%= h(t.getName()) %>)
                        </option>
                        <% } %>
                    </select>
                </div>
            </div>
            <div class="btn-row">
                <button type="submit" class="btn">Tìm phòng</button>
                <a href="gdQLLichChieu9.jsp">Hủy</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>
