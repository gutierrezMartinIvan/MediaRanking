package ar.com.mediaranking.service;

import ar.com.mediaranking.exception.AlreadyExistsException;
import ar.com.mediaranking.exception.NotFoundException;
import ar.com.mediaranking.models.entity.EpisodeEntity;
import ar.com.mediaranking.models.entity.SeasonEntity;
import ar.com.mediaranking.models.repository.EpisodeRepository;
import ar.com.mediaranking.models.repository.SeasonRepository;
import ar.com.mediaranking.models.request.EpisodeRequest;
import ar.com.mediaranking.models.response.EpisodeResponse;
import ar.com.mediaranking.service.impl.EpisodeServiceImpl;
import ar.com.mediaranking.utils.DtoToEntityConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class EpisodeServiceTest {

    @Mock
    private EpisodeRepository repository;

    @Mock
    private SeasonRepository seasonRepository;
    @Mock
    private DtoToEntityConverter mapper;
    @InjectMocks
    private EpisodeServiceImpl service;

    private List<EpisodeEntity> episodes;
    private List<EpisodeRequest> requests;
    private List<EpisodeResponse> responses;


    @BeforeEach
    public void setUp() {
        episodes = List.of(
        (EpisodeEntity.builder().id(1L).title("Episode 1").description("Episode").episodeNumber(1).build()),
        (EpisodeEntity.builder().id(2L).title("Episode 2").description("Episode").episodeNumber(2).build()),
        (EpisodeEntity.builder().id(3L).title("Episode 3").description("Episode").episodeNumber(3).build())
        );

        requests = List.of(
        (EpisodeRequest.builder().title("Episode 1").description("Episode").episodeNumber(1).seasonId(1L).build()),
        (EpisodeRequest.builder().title("Episode 2").description("Episode").episodeNumber(2).seasonId(1L).build()),
        (EpisodeRequest.builder().title("Episode 3").description("Episode").episodeNumber(3).seasonId(1L).build())
        );

        responses = List.of(
        (EpisodeResponse.builder().id(1L).title("Episode 1").description("Episode").episodeNumber(1).build()),
        (EpisodeResponse.builder().id(2L).title("Episode 2").description("Episode").episodeNumber(2).build()),
        (EpisodeResponse.builder().id(3L).title("Episode 3").description("Episode").episodeNumber(3).build())
        );

        given(mapper.convertDtoToEntity(requests.get(0))).willReturn(episodes.get(0));
        given(mapper.convertDtoToEntity(requests.get(1))).willReturn(episodes.get(1));
        given(mapper.convertDtoToEntity(requests.get(2))).willReturn(episodes.get(2));

        given(mapper.convertEntityToDto(episodes.get(0))).willReturn(responses.get(0));
        given(mapper.convertEntityToDto(episodes.get(1))).willReturn(responses.get(1));
        given(mapper.convertEntityToDto(episodes.get(2))).willReturn(responses.get(2));
    }

    @Test
    public void testGetAllEpisodes() {
        given(mapper.convertEpisodesToDto(episodes)).willReturn(responses);
        given(repository.findAll(any(Specification.class))).willReturn(episodes);

        List<EpisodeResponse> result = service.getAll(null,null,null,null,null,null);

        assert result.size() == 3;
        assert result.get(0).getId() == 1L;
        verify(repository, Mockito.times(1)).findAll(any(Specification.class));
    }

    @Test
    public void testGetEpisodeById() {
        given(repository.findById(1L)).willReturn(Optional.of(episodes.get(0)));
        given(mapper.convertEntityToDto(episodes.get(0))).willReturn(responses.get(0));

        EpisodeResponse result = service.getById(1L);

        assert result.getId() == 1L;
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    void getEpisodeByIdNotFoundThrowsException() {
        given(repository.findById(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getById(1L));
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    void deleteEpisodeByIdOfExistingEntityDeletes() {
        service.delete(1L);
        verify(repository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void deleteEpisodeByIdOfNonExistingEntityThrowsException() {
        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(1L);

        assertThrows(NotFoundException.class, () -> service.delete(1L));
        verify(repository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void saveEpisodeSavesTheData() {
        SeasonEntity season = SeasonEntity.builder().id(1L).build();
        episodes.get(0).setSeason(season);
        season.setEpisodes(new ArrayList<>());

        given(repository.save(episodes.get(0))).willReturn(episodes.get(0));
        given(seasonRepository.findById(1L)).willReturn(Optional.of(season));

        EpisodeResponse result = service.save(requests.get(0));

        assert result.getId() == 1L;
        verify(repository, Mockito.times(1)).save(episodes.get(0));
    }

    @Test
    void saveEpisodeWithNoExitingSeasonThrowsNotFoundException(){
        given(repository.save(episodes.get(0))).willReturn(episodes.get(0));
        given(seasonRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.save(requests.get(0)));
    }

    @Test
    void saveEpisodeThatAlreadyExistThrowsException(){
        SeasonEntity season = SeasonEntity.builder().id(1L).title("Season 1").description("Season").seasonNumber(1).build();
        episodes.get(0).setSeason(season);
        season.setEpisodes(episodes);

        given(repository.save(episodes.get(0))).willReturn(episodes.get(0));
        given(seasonRepository.findById(1L)).willReturn(Optional.of(season));

        season.setEpisodes(episodes);

        assertThrows(AlreadyExistsException.class, () -> service.save(requests.get(0)));
    }

    @Test
    void saveListSavesTheData() {
        SeasonEntity season = SeasonEntity.builder().id(1L).build();

        season.setEpisodes(new ArrayList<>());

        given(repository.save(episodes.get(0))).willReturn(episodes.get(0));
        given(repository.save(episodes.get(1))).willReturn(episodes.get(1));
        given(repository.save(episodes.get(2))).willReturn(episodes.get(2));
        given(seasonRepository.findById(1L)).willReturn(Optional.of(season));

        List<EpisodeResponse> result = service.save(requests);

        assert result.size() == 3;
        verify(repository, Mockito.times(3)).save(any(EpisodeEntity.class));
    }

    @Test
    void saveListWithNoExitingSeasonThrowsNotFoundException(){
        given(repository.save(episodes.get(0))).willReturn(episodes.get(0));
        given(seasonRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.save(requests));
    }

    @Test
    void updateEpisodeUpdatesTheData() {
        SeasonEntity season = SeasonEntity.builder().id(1L).episodes(new ArrayList<>()).build();
        episodes.get(0).setSeason(season);

        SeasonEntity season2 = SeasonEntity.builder().id(2L).episodes(new ArrayList<>()).build();

        EpisodeEntity updated = EpisodeEntity.builder().id(1L).title(episodes.get(2).getTitle()).season(season2).description(episodes.get(2).getDescription()).episodeNumber(episodes.get(2).getEpisodeNumber()).build();
        given(repository.save(updated)).willReturn(updated);
        given(repository.findById(1L)).willReturn(Optional.of(episodes.get(0)));
        given(seasonRepository.findById(1L)).willReturn(Optional.of(season));
        given(seasonRepository.findById(2L)).willReturn(Optional.of(season2));

        EpisodeRequest request = requests.get(2);
        request.setSeasonId(2L);
        EpisodeResponse result = service.update(1L, request);

        assert result.getId() == 1L;
        verify(repository, Mockito.times(1)).save(updated);
    }

    @Test
    void updateEpisodeWithNoExitingSeasonThrowsNotFoundException(){
        given(repository.save(episodes.get(0))).willReturn(episodes.get(0));
        given(seasonRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.update(1L, requests.get(0)));
    }

}
