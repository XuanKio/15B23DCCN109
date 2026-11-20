package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/** Phim. duration tính theo phút. */
public class Movie9 implements Serializable {
    public static final String SHOWING = "Đang chiếu";
    public static final String UPCOMING = "Sắp chiếu";
    public static final String STOPPED = "Ngừng chiếu";

    private long id;
    private String title;
    private String director;
    private String genre;
    private int duration;
    private String description;
    private String showingStatus;
    private String actors;
    private String poster;
    private LocalDate releaseDate;
    private String language;
    private BigDecimal baseTicketPrice;

    public Movie9() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShowingStatus() {
        return showingStatus;
    }

    public void setShowingStatus(String showingStatus) {
        this.showingStatus = showingStatus;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public BigDecimal getBaseTicketPrice() {
        return baseTicketPrice;
    }

    public void setBaseTicketPrice(BigDecimal baseTicketPrice) {
        this.baseTicketPrice = baseTicketPrice;
    }
}
