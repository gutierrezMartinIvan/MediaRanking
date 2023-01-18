package ar.com.mediaranking.service;

import ar.com.mediaranking.exception.AlreadyExistsException;
import ar.com.mediaranking.exception.NotFoundException;
import ar.com.mediaranking.models.entity.SeasonEntity;
import ar.com.mediaranking.models.entity.SeriesEntity;
import ar.com.mediaranking.models.repository.EpisodeRepository;
import ar.com.mediaranking.models.repository.ISeriesRepository;
import ar.com.mediaranking.models.repository.SeasonRepository;
import ar.com.mediaranking.models.request.SeasonRequest;
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
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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

}
