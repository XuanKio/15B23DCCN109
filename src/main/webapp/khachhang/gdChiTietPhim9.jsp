<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="controller.MovieSearchControl9,model.Movie9,java.util.Optional,java.net.URLEncoder,java.nio.charset.StandardCharsets" %>
<%@ include file="/WEB-INF/jspf/common.jspf" %>
<%@ include file="/WEB-INF/jspf/authKH.jspf" %>
<%
    // Bước 13-20 kịch bản v3: nhận movieId, gọi loadMovieDetails, hiển thị chi tiết.
    Long movieId = parseLong(request.getParameter("movieId"));
    Optional<Movie9> found = movieId == null ? Optional.empty() : new MovieSearchControl9().loadMovieDetails(movieId);
    String keyword = (String) session.getAttribute("tukhoaTimPhim");
    String backUrl = "gdTimPhim9.jsp" + (keyword == null ? "" : "?keyword=" + URLEncoder.encode(keyword, StandardCharsets.UTF_8));
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết phim - CineMan</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cineman9.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/topbar.jspf" %>
<div class="card">
    <div class="card-header">Chi tiết phim</div>
    <div class="card-body">
        <% if (found.isEmpty()) { %>
        <div class="msg error">Phim đã chọn không còn tồn tại trong hệ thống. Vui lòng tìm lại.</div>
        <% } else { Movie9 m = found.get(); %>
        <div class="title"><%= h(m.getTitle()) %></div>
        <div class="info">
            <span class="k">Đạo diễn</span><span><%= h(m.getDirector()) %></span>
            <span class="k">Thể loại</span><span><%= h(m.getGenre()) %></span>
            <span class="k">Thời lượng</span><span><%= m.getDuration() %> phút</span>
            <span class="k">Trạng thái</span><span><%= h(m.getShowingStatus()) %></span>
            <% if (m.getActors() != null) { %><span class="k">Diễn viên</span><span><%= h(m.getActors()) %></span><% } %>
            <% if (m.getReleaseDate() != null) { %><span class="k">Ngày phát hành</span><span><%= fmtDate(m.getReleaseDate()) %></span><% } %>
            <% if (m.getLanguage() != null) { %><span class="k">Ngôn ngữ</span><span><%= h(m.getLanguage()) %></span><% } %>
            <% if (m.getPoster() != null) { %><span class="k">Áp phích</span><span><%= h(m.getPoster()) %></span><% } %>
        </div>
        <div class="k" style="font-weight:bold; margin-top: 12px">Nội dung</div>
        <p><%= h(m.getDescription()) %></p>
        <% } %>
        <div class="btn-row"><a class="btn" href="<%= h(backUrl) %>">Quay lại kết quả</a></div>
    </div>
</div>
</body>
</html>
