package ar.com.mediaranking.utils;

public enum Genres {
    ACTION,
    ADVENTURE,
    ANIMATION,
    BIOGRAPHY,
    COMEDY,
    CRIME,
    DOCUMENTARY,
    DRAMA,
    FAMILY,
    FANTASY,
    FILM_NOIR,
    HISTORY,
    HORROR,
    MUSIC,
    MUSICAL,
    MYSTERY,
    ROMANCE,
    SCI_FI,
    SPORT,
    THRILLER,
    WAR,
    WESTERN;


    public static final String[] ALLOWABLE_VALUES = new String[]{
            "ACTION", "ADVENTURE", "ANIMATION", "BIOGRAPHY", "COMEDY", "CRIME",
            "DOCUMENTARY", "DRAMA", "FAMILY", "FANTASY", "FILM_NOIR", "HISTORY",
            "HORROR", "MUSIC", "MUSICAL", "MYSTERY", "ROMANCE", "SCI_FI", "SPORT",
            "THRILLER", "WAR", "WESTERN"
    };

    public String getDisplayName() {
        return switch (this) {
            case SCI_FI -> "SCI-FI";
            case FILM_NOIR -> "FILM NOIR";
            default -> name();
        };
    }
}
