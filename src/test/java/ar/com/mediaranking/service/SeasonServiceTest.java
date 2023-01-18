package ar.com.mediaranking.service;

import ar.com.mediaranking.exception.AlreadyExistsException;
import ar.com.mediaranking.exception.NotFoundException;
import ar.com.mediaranking.models.entity.EpisodeEntity;
import ar.com.mediaranking.models.entity.SeasonEntity;
import ar.com.mediaranking.models.entity.SeriesEntity;
import ar.com.mediaranking.models.repository.EpisodeRepository;
import ar.com.mediaranking.models.repository.ISeriesRepository;
import ar.com.mediaranking.models.repository.SeasonRepository;
import ar.com.mediaranking.models.request.EpisodeSeasonRequest;
import ar.com.mediaranking.models.request.SeasonRequest;
import ar.com.mediaranking.models.request.SeasonUpdate;
import ar.com.mediaranking.models.response.EpisodeResponse;
import ar.com.mediaranking.models.response.SeasonResponse;
import ar.com.mediaranking.service.impl.SeasonServiceImpl;
import ar.com.mediaranking.utils.DtoToEntityConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SeasonServiceTest {

    @Mock
    private SeasonRepository repository;

    @Mock
    private ISeriesRepository seriesRepository;

    @Mock
    private EpisodeRepository episodeRepository;

    @Mock
    private DtoToEntityConverter mapper;

    @InjectMocks
    private SeasonServiceImpl service;

     @Test
    void saveSeasonWithoutEpisodesSavesSeason(){
        SeriesEntity series = new SeriesEntity();

        SeasonRequest request = new SeasonRequest(1L,1,"Season","Description",null);
        SeasonEntity entity = new SeasonEntity(1L,1,"Season","Description",null,series);
        SeasonResponse response = new SeasonResponse(1L,1,"Season","Description",null);

        given(seriesRepository.findById(1L)).willReturn(Optional.of(series));
         given(repository.findByTitleAndSeasonNumberAndSeries(request.getTitle(),request.getSeasonNumber(),series)).willReturn(Optional.empty());
         given(repository.save(entity)).willReturn(entity);
        given(mapper.convertDtoToEntity(request)).willReturn(entity);
        given(mapper.convertEntityToDto(entity)).willReturn(response);

        SeasonResponse result = service.save(request);

        assertEquals(result.getId(), result.getId());

        verify(repository, Mockito.times(1)).save(entity);
        verify(seriesRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void saveSeasonWithListOfEpisodesIsSaveCorrectly(){
        SeriesEntity series = new SeriesEntity();

        List<EpisodeSeasonRequest> episodesRequest = List.of(new EpisodeSeasonRequest(1,"Episode","Description"), new EpisodeSeasonRequest(2,"Episode","Description") , new EpisodeSeasonRequest(3,"Episode","Description"));
        List<EpisodeEntity> episodesEntity = List.of(new EpisodeEntity(1L,"Episode","Description",1,null), new EpisodeEntity(2L,"Episode","Description",2,null) , new EpisodeEntity(3L,"Episode","Description",3,null));
        List<EpisodeResponse> episodesResponse = List.of(new EpisodeResponse(1L,1,"Episode","Description"), new EpisodeResponse(2L,2,"Episode","Description") , new EpisodeResponse(3L,3,"Episode","Description"));

        SeasonRequest request = new SeasonRequest(1L,1,"Season","Description",episodesRequest);
        SeasonEntity entity = new SeasonEntity(1L,1,"Season","Description",episodesEntity,series);
        SeasonResponse response = new SeasonResponse(1L,1,"Season","Description",episodesResponse);

        given(seriesRepository.findById(1L)).willReturn(Optional.of(series));
        given(repository.findByTitleAndSeasonNumberAndSeries(request.getTitle(),request.getSeasonNumber(),series)).willReturn(Optional.empty());
        given(repository.save(entity)).willReturn(entity);
        given(mapper.convertDtoToEntity(request)).willReturn(entity);
        given(mapper.convertEntityToDto(entity)).willReturn(response);

        SeasonResponse result = service.save(request);


        verify(repository, Mockito.times(1)).save(entity);
        verify(seriesRepository, Mockito.times(1)).findById(1L);
        verify(episodeRepository, Mockito.times(3)).save(any(EpisodeEntity.class));
    }

    @Test
    void saveASeasonWithASeriesThatDoNotExistThrowsException(){
        SeasonRequest request = new SeasonRequest(1L,1,"Season","Description",null);
        given(seriesRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class,()-> service.save(request));
    }

    @Test
    void savingSeasonThatAlreadyExistThrowsException(){
        SeriesEntity series = new SeriesEntity();
        SeasonRequest request = new SeasonRequest(1L,1,"Season","Description",null);
        given(seriesRepository.findById(1L)).willReturn(Optional.of(series));
        given(repository.findByTitleAndSeasonNumberAndSeries(request.getTitle(),request.getSeasonNumber(),series)).willReturn(Optional.of(new SeasonEntity()));

        assertThrows(AlreadyExistsException.class,()-> service.save(request));
    }

    @Test
    void saveListOfSeasonSavesAllSeasons(){
        SeriesEntity series = new SeriesEntity();
        List<SeasonRequest> requests = List.of(
                new SeasonRequest(1L,1,"Season","Description",null),
                new SeasonRequest(1L,2,"Season","Description",null),
                new SeasonRequest(1L,3,"Season","Description",null)
        );
        List<SeasonEntity> entities = List.of(
                new SeasonEntity(1L,1,"Season","Description",null,series),
                new SeasonEntity(2L,2,"Season","Description",null,series),
                new SeasonEntity(3L,3,"Season","Description",null,series)
        );
        List<SeasonResponse> responses = List.of(
                new SeasonResponse(1L,1,"Season","Description",null),
                new SeasonResponse(2L,2,"Season","Description",null),
                new SeasonResponse(3L,3,"Season","Description",null)
        );

        given(mapper.convertSeasonsToDto(entities)).willReturn(responses);
        given(mapper.convertDtoToEntity(requests.get(0))).willReturn(entities.get(0));
        given(mapper.convertDtoToEntity(requests.get(1))).willReturn(entities.get(1));
        given(mapper.convertDtoToEntity(requests.get(2))).willReturn(entities.get(2));

        given(seriesRepository.findById(1L)).willReturn(Optional.of(series));

        given(repository.save(entities.get(0))).willReturn(entities.get(0));
        given(repository.save(entities.get(1))).willReturn(entities.get(1));
        given(repository.save(entities.get(2))).willReturn(entities.get(2));

        List<SeasonResponse> result = service.save(requests);

        verify(repository,Mockito.times(1)).save(entities.get(0));
        verify(repository,Mockito.times(1)).save(entities.get(1));
        verify(repository,Mockito.times(1)).save(entities.get(2));
        verify(seriesRepository,Mockito.times(3)).findById(1L);
    }

    @Test
    void deleteSeasonDeletesTheData(){
         service.delete(1L);

         verify(repository,Mockito.times(1)).deleteById(1L);
    }

    @Test
    void getByIdReturnsSeason(){
        SeriesEntity series = new SeriesEntity();
        SeasonEntity entity = new SeasonEntity(1L,1,"Season","Description",null,series);
        SeasonResponse response = new SeasonResponse(1L,1,"Season","Description",null);

        given(repository.findById(1L)).willReturn(Optional.of(entity));
        given(mapper.convertEntityToDto(entity)).willReturn(response);

        SeasonResponse result = service.getById(1L);

        assertEquals(result.getId(), response.getId());
        assertEquals(result.getSeasonNumber(), response.getSeasonNumber());
        assertEquals(result.getTitle(), response.getTitle());
        assertEquals(result.getDescription(), response.getDescription());
        assertEquals(result.getEpisodes(), response.getEpisodes());

        verify(repository,Mockito.times(1)).findById(1L);
    }

    @Test
    void getAllWithoutFiltersReturnsAllSeasons(){
        SeriesEntity series = new SeriesEntity();
        List<SeasonEntity> entities = List.of(
                new SeasonEntity(1L,1,"Season","Description",null,series),
                new SeasonEntity(2L,2,"Season","Description",null,series),
                new SeasonEntity(3L,3,"Season","Description",null,series)
        );
        List<SeasonResponse> responses = List.of(
                new SeasonResponse(1L,1,"Season","Description",null),
                new SeasonResponse(2L,2,"Season","Description",null),
                new SeasonResponse(3L,3,"Season","Description",null)
        );

        given(repository.findAll(any(Specification.class))).willReturn(entities);
        given(mapper.convertSeasonsToDto(entities)).willReturn(responses);

        List<SeasonResponse> result = service.getAll(null,null,null,null);

        assertEquals(result.size(), responses.size());
        assertEquals(result.get(0).getId(), responses.get(0).getId());
        assertEquals(result.get(0).getSeasonNumber(), responses.get(0).getSeasonNumber());
        assertEquals(result.get(0).getTitle(), responses.get(0).getTitle());
        assertEquals(result.get(0).getDescription(), responses.get(0).getDescription());
        assertEquals(result.get(0).getEpisodes(), responses.get(0).getEpisodes());

        assertEquals(result.get(1).getId(), responses.get(1).getId());
        assertEquals(result.get(1).getSeasonNumber(), responses.get(1).getSeasonNumber());
        assertEquals(result.get(1).getTitle(), responses.get(1).getTitle());
        assertEquals(result.get(1).getDescription(), responses.get(1).getDescription());
        assertEquals(result.get(1).getEpisodes(), responses.get(1).getEpisodes());

        assertEquals(result.get(2).getId(), responses.get(2).getId());
        assertEquals(result.get(2).getSeasonNumber(), responses.get(2).getSeasonNumber());
        assertEquals(result.get(2).getTitle(), responses.get(2).getTitle());
        assertEquals(result.get(2).getDescription(), responses.get(2).getDescription());
        assertEquals(result.get(2).getEpisodes(), responses.get(2).getEpisodes());

        verify(repository,Mockito.times(1)).findAll(any(Specification.class));
    }

    @Test
    void getAllWithFilterReturnsSeasonsThatSatisfyIt(){
        SeriesEntity series = new SeriesEntity();
        List<SeasonEntity> entities = List.of(
                new SeasonEntity(1L,1,"Season","Description",null,series),
                new SeasonEntity(2L,2,"Season","Description",null,series),
                new SeasonEntity(3L,3,"Season","Description",null,series)
        );
        List<SeasonResponse> responses = List.of(
                new SeasonResponse(2L,2,"Season","Description",null)
        );

        List<SeasonEntity> filtered = List.of(entities.get(1));

        given(repository.findAll(any(Specification.class))).willReturn(filtered);
        given(mapper.convertSeasonsToDto(filtered)).willReturn(responses);

        List<SeasonResponse> result = service.getAll(1L,2,null,"Season");

        assertEquals(result.size(), responses.size());
        assertEquals(result.get(0).getId(), entities.get(1).getId());
        assertEquals(result.get(0).getSeasonNumber(), entities.get(1).getSeasonNumber());
        assertEquals(result.get(0).getTitle(), entities.get(1).getTitle());
        assertEquals(result.get(0).getDescription(), entities.get(1).getDescription());


        verify(repository,Mockito.times(1)).findAll(any(Specification.class));
    }

    @Test
    void updateSeasonUpdatesTheData(){
        SeriesEntity series = new SeriesEntity();
        SeriesEntity series2 = new SeriesEntity();
        SeasonEntity entity = new SeasonEntity(1L,1,"Season","Description",null,series);
        SeasonUpdate request = new SeasonUpdate(2L,2,"Season 2","Description 2");
        SeasonResponse response = new SeasonResponse(1L,2,"Season 2","Description 2",null);

        given(repository.findById(1L)).willReturn(Optional.of(entity));
        given(seriesRepository.findById(2L)).willReturn(Optional.of(series2));

        entity.setSeasonNumber(request.getSeasonNumber());
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setSeries(series2);
        given(repository.save(entity)).willReturn(entity);
        given(mapper.convertEntityToDto(entity)).willReturn(response);

        SeasonResponse result = service.update(1L,request);

        assertEquals(result.getId(), entity.getId());
        assertEquals(result.getSeasonNumber(), entity.getSeasonNumber());
        assertEquals(result.getTitle(), entity.getTitle());
        assertEquals(result.getDescription(), entity.getDescription());

        verify(repository,Mockito.times(1)).findById(1L);
        verify(repository,Mockito.times(1)).save(entity);
    }

    @Test
    void updateWithNonExistingSeasonThrowsException(){
        SeasonUpdate request = new SeasonUpdate(2L,2,"Season 2","Description 2");

        given(repository.findById(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.update(1L,request));

        verify(repository,Mockito.times(1)).findById(1L);
    }

}
