<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="controller.ScheduleScreeningControl9,model.Movie9,java.time.format.DateTimeParseException" %>
<%@ include file="/WEB-INF/jspf/common.jspf" %>
<%@ include file="/WEB-INF/jspf/authQL.jspf" %>
<%!
    static String opt(String s) {
        return s == null || s.trim().isEmpty() ? null : s.trim();
    }
%>
<%
    // Bước 28-35 kịch bản v3: nhận thông tin phim, gọi addMovie, quay lại gdChonPhim9.jsp.
    if (session.getAttribute("lichPhong") == null) {
        response.sendRedirect("gdChonThoiGian9.jsp");
        return;
    }
    String error = null;
    String title = request.getParameter("title");
    String director = request.getParameter("director");
    String genre = request.getParameter("genre");
    String duration = request.getParameter("duration");
    String status = request.getParameter("showingStatus");
    String price = request.getParameter("baseTicketPrice");
    String description = request.getParameter("description");
    String actors = request.getParameter("actors");
    String poster = request.getParameter("poster");
    String releaseDate = request.getParameter("releaseDate");
    String language = request.getParameter("language");
    if (status == null) {
        status = Movie9.SHOWING;
    }

    if ("POST".equals(request.getMethod())) {
        Movie9 movie = new Movie9();
        movie.setTitle(title);
        movie.setDirector(director);
        movie.setGenre(genre);
        movie.setShowingStatus(status);
        movie.setDescription(opt(description));
        movie.setActors(opt(actors));
        movie.setPoster(opt(poster));
        movie.setLanguage(opt(language));
        try {
            movie.setDuration(Integer.parseInt(duration.trim()));
            String cleanPrice = price == null ? "" : price.trim().replace(".", "").replace(",", "");
            movie.setBaseTicketPrice(cleanPrice.isEmpty() ? null : new BigDecimal(cleanPrice));
            movie.setReleaseDate(opt(releaseDate) == null ? null : LocalDate.parse(releaseDate.trim()));
            Movie9 saved = new ScheduleScreeningControl9().addMovie(movie);
            response.sendRedirect("gdChonPhim9.jsp?added=" + saved.getId());
            return;
        } catch (NumberFormatException | NullPointerException e) {
            error = "Thời lượng và giá cơ sở phải là số.";
        } catch (DateTimeParseException e) {
            error = "Ngày phát hành không hợp lệ.";
        } catch (IllegalArgumentException e) {
            error = e.getMessage();
        } catch (java.sql.SQLException e) {
            error = "Lưu phim thất bại, vui lòng thử lại. (" + e.getMessage() + ")";
        }
    }
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Thêm phim mới - CineMan</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cineman9.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/topbar.jspf" %>
<div class="card">
    <div class="card-header">Thêm phim mới</div>
    <div class="card-body">
        <% if (error != null) { %><div class="msg error"><%= h(error) %></div><% } %>
        <form method="post" action="gdThemPhim9.jsp">
            <div class="grid-2">
                <div><label for="title">Tên phim</label><input type="text" id="title" name="title" value="<%= h(title) %>" required></div>
                <div><label for="director">Đạo diễn</label><input type="text" id="director" name="director" value="<%= h(director) %>" required></div>
                <div><label for="genre">Thể loại</label><input type="text" id="genre" name="genre" value="<%= h(genre) %>" required></div>
                <div><label for="duration">Thời lượng (phút)</label><input type="number" id="duration" name="duration" min="1" max="600" value="<%= h(duration) %>" required></div>
                <div>
                    <label for="showingStatus">Trạng thái</label>
                    <select id="showingStatus" name="showingStatus">
                        <% for (String s : new String[] {Movie9.SHOWING, Movie9.UPCOMING, Movie9.STOPPED}) { %>
                        <option <%= s.equals(status) ? "selected" : "" %>><%= h(s) %></option>
                        <% } %>
                    </select>
                </div>
                <div><label for="baseTicketPrice">Giá cơ sở (đồng)</label><input type="text" id="baseTicketPrice" name="baseTicketPrice" value="<%= h(price) %>" required></div>
            </div>
            <label for="description">Nội dung</label>
            <textarea id="description" name="description"><%= h(description) %></textarea>
            <details <%= opt(actors) != null || opt(poster) != null || opt(releaseDate) != null || opt(language) != null ? "open" : "" %>>
                <summary>Thông tin bổ sung</summary>
                <div class="grid-2">
                    <div><label for="actors">Diễn viên</label><input type="text" id="actors" name="actors" value="<%= h(actors) %>"></div>
                    <div><label for="language">Ngôn ngữ</label><input type="text" id="language" name="language" value="<%= h(language) %>"></div>
                    <div><label for="releaseDate">Ngày phát hành</label><input type="date" id="releaseDate" name="releaseDate" value="<%= h(releaseDate) %>"></div>
                    <div><label for="poster">Áp phích (đường dẫn ảnh)</label><input type="text" id="poster" name="poster" value="<%= h(poster) %>"></div>
                </div>
            </details>
            <div class="btn-row">
                <button type="submit" class="btn">Lưu phim</button>
                <a href="gdChonPhim9.jsp">Quay lại chọn phim</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>
