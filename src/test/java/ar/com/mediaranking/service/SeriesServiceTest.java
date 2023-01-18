package ar.com.mediaranking.service;


import ar.com.mediaranking.exception.AlreadyExistsException;
import ar.com.mediaranking.exception.NotFoundException;
import ar.com.mediaranking.models.entity.GenreEntity;
import ar.com.mediaranking.models.entity.SeasonEntity;
import ar.com.mediaranking.models.entity.SeriesEntity;
import ar.com.mediaranking.models.repository.ISeriesRepository;
import ar.com.mediaranking.models.request.SeasonRequest;
import ar.com.mediaranking.models.request.SeasonSeriesRequest;
import ar.com.mediaranking.models.request.SeriesRequest;
import ar.com.mediaranking.models.request.SeriesUpdate;
import ar.com.mediaranking.models.response.SeasonResponse;
import ar.com.mediaranking.models.response.SeriesResponse;
import ar.com.mediaranking.service.impl.SeriesServiceImpl;
import ar.com.mediaranking.utils.DtoToEntityConverter;
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


import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SeriesServiceTest {

    @Mock
    private ISeriesRepository repository;

    @Mock
    private SeasonService seasonService;

    @Mock
    private DtoToEntityConverter mapper;

    @InjectMocks
    private SeriesServiceImpl service;

    @Test
    void getAllReturnAllSeries() {
        List<SeriesEntity> series = List.of(
                new SeriesEntity(1L,"Series 1","Desc 1","Author 1",2022, Set.of(GenreEntity.builder().name("Action").build()),null,null),
                new SeriesEntity(2L,"Series 2","Desc 2","Author 2",2023, Set.of(GenreEntity.builder().name("Sci-Fi").build()),null,null)
        );
        List<SeriesResponse> seriesResponse = List.of(
                new SeriesResponse(1L,"Series 1","Desc 1","Author 1",2022, List.of("Action"),List.of(new SeasonResponse())),
                new SeriesResponse(2L,"Series 2","Desc 2","Author 2",2023, List.of("Sci-Fi"),List.of(new SeasonResponse()))
        );
        given(repository.findAll()).willReturn(series);
        given(mapper.convertSeriesToDto(series)).willReturn(seriesResponse);

        List<SeriesResponse> result = service.getAll();

        assert result.size() == 2;
        assert result.equals(seriesResponse);
        verify(repository, Mockito.times(1)).findAll();
    }

    @Test
    void getByIdReturnsSeries(){
        SeriesEntity series = new SeriesEntity(1L,"Series 1","Desc 1","Author 1",2022, Set.of(GenreEntity.builder().name("Action").build()),null,null);
        SeriesResponse seriesResponse = new SeriesResponse(1L,"Series 1","Desc 1","Author 1",2022, List.of("Action"),List.of(new SeasonResponse()));
        given(repository.findById(1L)).willReturn(java.util.Optional.of(series));
        given(mapper.convertEntityToDto(series)).willReturn(seriesResponse);

        SeriesResponse result = service.getSerieById(1L);

        assert result.equals(seriesResponse);
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    void getByIdOfNotExistantSeriesThrowsException(){
        given(repository.findById(1L)).willReturn(java.util.Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getSerieById(1L));
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    void getByFilterReturnsSeries(){
        List<SeriesEntity> series = List.of(
                new SeriesEntity(1L,"Series 1","Desc 1","Author",2022, Set.of(GenreEntity.builder().name("Action").build()),null,null),
                new SeriesEntity(2L,"Series 2","Desc 2","Author",2023, Set.of(GenreEntity.builder().name("Sci-Fi").build()),null,null)
        );
        List<SeriesResponse> seriesResponse = List.of(
                new SeriesResponse(1L,"Series 1","Desc 1","Author 1",2022, List.of("Action"),List.of(new SeasonResponse())),
                new SeriesResponse(2L,"Series 2","Desc 2","Author 2",2023, List.of("Sci-Fi"),List.of(new SeasonResponse()))
        );
        given(repository.findAll(any(Specification.class))).willReturn(series);
        given(mapper.convertSeriesToDto(series)).willReturn(seriesResponse);

        List<SeriesResponse> result = service.getByFilters("Series","Author",null,null);

        assert result.size() == 2;
        assert result.equals(seriesResponse);
        verify(repository, Mockito.times(1)).findAll(any(Specification.class));
    }

    @Test
    void deleteByIdDeletesSeries(){
        SeriesEntity series = new SeriesEntity(1L,"Series 1","Desc 1","Author 1",2022, Set.of(GenreEntity.builder().name("Action").build()),null,null);
        given(repository.findById(1L)).willReturn(java.util.Optional.of(series));

        service.deleteSerieById(1L);

        verify(repository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdOfNotExistantSeriesThrowsException(){
        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(1L);
        assertThrows(NotFoundException.class, () -> service.deleteSerieById(1L));
        verify(repository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void updateSerieUpdatesSerie(){
        SeriesEntity series = new SeriesEntity(1L,"Series 1","Desc 1","Author 1",2022, Set.of(GenreEntity.builder().name("Action").build()),null,null);
        SeriesUpdate update = new SeriesUpdate("Series Update","Desc Update","Author Update",2023, Set.of("Sci-Fi"));
        SeriesResponse response = new SeriesResponse(1L,"Series Update","Desc Update","Author Update",2023, List.of("Sci-Fi"),List.of(new SeasonResponse()));

        given(repository.findById(1L)).willReturn(java.util.Optional.of(series));

        series.setGenres(Set.of(GenreEntity.builder().name("Sci-Fi").build()));
        series.setTitle(update.getTitle());
        series.setDescription(update.getDescription());
        series.setAuthor(update.getAuthor());
        series.setYear(update.getYear());
        given(repository.save(series)).willReturn(series);


        service.update(1L,update);

        verify(repository, Mockito.times(1)).save(series);
        assert series.getTitle().equals(update.getTitle());
        assert series.getDescription().equals(update.getDescription());
        assert series.getAuthor().equals(update.getAuthor());
    }

    @Test
    void updateSerieOfNotExistantSeriesThrowsException(){
        SeriesUpdate update = new SeriesUpdate("Series Update","Desc Update","Author Update",2023, Set.of("Sci-Fi"));
        given(repository.findById(1L)).willReturn(java.util.Optional.empty());

        assertThrows(NotFoundException.class, () -> service.update(1L,update));
        verify(repository, Mockito.times(0)).save(any(SeriesEntity.class));
    }

    @Test
    void saveSeriesWithNoSeasonSavesSeries(){
        SeriesEntity series = new SeriesEntity(1L,"Series 1","Desc 1","Author 1",2022, Set.of(GenreEntity.builder().name("Action").build()),null,null);
        SeriesRequest request = new SeriesRequest("Series 1","Desc 1","Author 1",2022, Set.of("Action"),null);
        SeriesResponse response = new SeriesResponse(1L,"Series 1","Desc 1","Author 1",2022, List.of("Action"),List.of(new SeasonResponse()));

        given(mapper.convertDtoToEntity(request)).willReturn(series);
        given(repository.save(series)).willReturn(series);
        given(mapper.convertEntityToDto(series)).willReturn(response);
        given(repository.findByTitleAndYear(request.getTitle(),request.getYear())).willReturn(java.util.Optional.empty());

        SeriesResponse result = service.save(request);

        assert result.equals(response);
        verify(repository, Mockito.times(1)).save(series);
        verify(repository, Mockito.times(1)).findByTitleAndYear(request.getTitle(),request.getYear());
    }

    @Test
    void saveSeriesThatAlreadyExistThrowsException(){

        SeriesRequest request = new SeriesRequest("Series 1","Desc 1","Author 1",2022, Set.of("Action"),null);

        given(repository.findByTitleAndYear(request.getTitle(),request.getYear())).willReturn(java.util.Optional.of(new SeriesEntity()));


        assertThrows(AlreadyExistsException.class, () -> service.save(request));
        verify(repository, Mockito.times(1)).findByTitleAndYear(request.getTitle(),request.getYear());
    }

    @Test
    void saveSeriesWithSeasonsSavesSeries(){
        List<SeasonSeriesRequest> seasonRequest = List.of(new SeasonSeriesRequest(),new SeasonSeriesRequest());
        List<SeasonEntity> season = List.of(new SeasonEntity(),new SeasonEntity());
        SeriesRequest request = new SeriesRequest("Series 1","Desc 1","Author 1",2022, Set.of("Action"),seasonRequest);
        SeriesEntity series = new SeriesEntity(1L,"Series 1","Desc 1","Author 1",2022, Set.of(GenreEntity.builder().name("Action").build()),season,null);
        SeriesResponse response = new SeriesResponse(1L,"Series 1","Desc 1","Author 1",2022, List.of("Action"),List.of(new SeasonResponse(),new SeasonResponse()));

        given(mapper.convertDtoToEntity(request)).willReturn(series);
        given(repository.save(series)).willReturn(series);
        given(mapper.convertEntityToDto(series)).willReturn(response);
        given(repository.findByTitleAndYear(request.getTitle(),request.getYear())).willReturn(java.util.Optional.empty());

        SeriesResponse result = service.save(request);

        assert result.equals(response);
        verify(repository, Mockito.times(1)).save(series);
        verify(repository, Mockito.times(1)).findByTitleAndYear(request.getTitle(),request.getYear());
        verify(seasonService, Mockito.times(1)).save(season.get(0),series);
        verify(seasonService, Mockito.times(1)).save(season.get(1),series);
    }
}
