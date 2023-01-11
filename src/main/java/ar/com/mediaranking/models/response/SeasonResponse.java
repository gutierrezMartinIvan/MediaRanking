package ar.com.mediaranking.models.response;

import ar.com.mediaranking.models.request.EpisodeRequest;
import lombok.Data;

import java.util.List;

@Data
public class SeasonResponse {

    Long id;

    Integer seasonNumber;

    String title;

    String Description;

    List<EpisodeResponse> episodes;
}
