package ar.com.mediaranking.models.request;


import lombok.Data;

import java.util.List;

@Data
public class SeasonRequest {
    Integer seasonNumber;

    String title;

    String Description;

    List<EpisodeRequest> episodes;
}
