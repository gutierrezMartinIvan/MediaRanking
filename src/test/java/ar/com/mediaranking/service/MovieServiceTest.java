package ar.com.mediaranking.service;

import ar.com.mediaranking.exception.AlreadyExistsException;
import ar.com.mediaranking.exception.NotFoundException;
import ar.com.mediaranking.models.entity.GenreEntity;
import ar.com.mediaranking.models.entity.MovieEntity;
import ar.com.mediaranking.models.entity.filter.MovieFilter;
import ar.com.mediaranking.models.repository.MovieRepository;
import ar.com.mediaranking.models.repository.specification.MovieSpecification;
import ar.com.mediaranking.models.request.MovieRequest;
import ar.com.mediaranking.models.request.MovieUpdate;
import ar.com.mediaranking.models.response.MovieResponse;
import ar.com.mediaranking.service.impl.MovieServiceImpl;
import ar.com.mediaranking.utils.DtoToEntityConverter;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MovieServiceTest {

    @Mock
    private MovieRepository repository;

    @Mock
    private DtoToEntityConverter mapper;

    @InjectMocks
    private MovieServiceImpl service;


    private List<MovieRequest> movieRequests;
    private List<MovieEntity> movieEntities;
    private List<MovieResponse> movieResponses;

    @BeforeEach
    public void setUp() {
        movieRequests = List.of(
                MovieRequest.builder().title("LoTR: The Fellowship of the Ring").description("1").genres(Set.of("Action")).director("Director").year(2001).duration(10).build(),
                MovieRequest.builder().title("LoTR: The Two Towers").description("2").genres(Set.of("Action")).director("Director").year(2002).duration(10).build(),
                MovieRequest.builder().title("LoTR: The Return of the King").description("3").genres(Set.of("Action")).director("Director").year(2003).duration(10).build()
        );

        movieEntities = List.of(
                MovieEntity.builder().id(1L).title("LoTR: The Fellowship of the Ring").description("1").genres(Set.of(GenreEntity.builder().name("Action").build())).director("Director").year(2001).duration(10).build(),
                MovieEntity.builder().id(2L).title("LoTR: The Two Towers").description("2").genres(Set.of(GenreEntity.builder().name("Action").build())).director("Director").year(2002).duration(10).build(),
                MovieEntity.builder().id(3L).title("LoTR: The Return of the King").description("3").genres(Set.of(GenreEntity.builder().name("Action").build())).director("Director").year(2003).duration(10).build()
        );

        movieResponses = List.of(
                MovieResponse.builder().id(1L).title("LoTR: The Fellowship of the Ring").description("1").genres(Set.of("Action")).director("Director").year(2001).duration(10).build(),
                MovieResponse.builder().id(2L).title("LoTR: The Two Towers").description("2").genres(Set.of("Action")).director("Director").year(2002).duration(10).build(),
                MovieResponse.builder().id(3L).title("LoTR: The Return of the King").description("3").genres(Set.of("Action")).director("Director").year(2003).duration(10).build()
        );

        /* Set up mock of mapper*/
        given(mapper.convertDtoToEntity(movieRequests.get(0))).willReturn(movieEntities.get(0));
        given(mapper.convertDtoToEntity(movieRequests.get(1))).willReturn(movieEntities.get(1));
        given(mapper.convertDtoToEntity(movieRequests.get(2))).willReturn(movieEntities.get(2));

        given(mapper.convertEntityToDto(movieEntities.get(0))).willReturn(movieResponses.get(0));
        given(mapper.convertEntityToDto(movieEntities.get(1))).willReturn(movieResponses.get(1));
        given(mapper.convertEntityToDto(movieEntities.get(2))).willReturn(movieResponses.get(2));

        given(mapper.convertMoviesToDto(movieEntities)).willReturn(movieResponses);

    }

    @Test
    void  saveMovieSavesData() {
        given(repository.save(movieEntities.get(0))).willReturn(movieEntities.get(0));
        given(repository.findByTitleAndYear(movieRequests.get(0).getTitle(), movieRequests.get(0).getYear())).willReturn(Optional.empty());

        MovieResponse savedMovie = service.save(movieRequests.get(0));

        assert savedMovie.getId() > 0;
        assert savedMovie.getTitle().equals(movieResponses.get(0).getTitle());
        verify(repository, Mockito.times(1)).save(movieEntities.get(0));

    }

    @Test
    void saveMovieSavesDataWithExistingTitleAndYear() {
        given(repository.findByTitleAndYear(movieRequests.get(0).getTitle(), movieRequests.get(0).getYear())).willReturn(Optional.of(movieEntities.get(0)));

        assertThrows(AlreadyExistsException.class, () -> service.save(movieRequests.get(0)));
        verify(repository, Mockito.times(0)).save(movieEntities.get(0));
        verify(repository, Mockito.times(1)).findByTitleAndYear(movieRequests.get(0).getTitle(), movieRequests.get(0).getYear());
    }

    @Test
    void findAllMoviesWithNoSavedMoviesReturnEmptyList() {
        List<MovieResponse> movies = service.findAll();
        verify(repository, Mockito.times(1)).findAll();
        assert movies.isEmpty();
    }

    @Test
    void findAllMoviesReturnMovies() {
        given(repository.findAll()).willReturn(List.of(movieEntities.get(0), movieEntities.get(1), movieEntities.get(2)));

        List<MovieResponse> movies = service.findAll();
        verify(repository, Mockito.times(1)).findAll();
        assert !movies.isEmpty();
        assertEquals(movies, movieResponses);
    }

    @Test
    void findMovieByIdReturnsMovie() {
        given(repository.findById(1L)).willReturn(Optional.of(movieEntities.get(0)));

        MovieResponse movie = service.findById(1L);
        verify(repository, Mockito.times(1)).findById(1L);
        assertEquals(movie, movieResponses.get(0));
    }

    @Test
    void findMovieByIdThrowsException() {
        given(repository.findById(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.findById(1L));
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    void deleteMovieByIdDeletesMovie() {
        service.deleteById(1L);
        verify(repository, Mockito.times(1)).deleteById(movieEntities.get(0).getId());
    }

    @Test
    void deleteMovieByIdThrowsException() {
        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(1L);
        assertThrows(NotFoundException.class, () -> service.deleteById(1L));
        verify(repository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void updateMovieThatDoesntExistThrowsException() {
        MovieUpdate update = MovieUpdate.builder().build();

        given(repository.findById(1L)).willReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> service.update(1L, update));
        verify(repository, Mockito.times(1)).findById(1L);
    }

    @Test
    void updateMovieThatExistsUpdatesMovie() {
        MovieUpdate update = MovieUpdate.builder().title("New Title").build();
        MovieEntity movie = movieEntities.get(0);
        movie.setTitle(update.getTitle());
        MovieResponse response = movieResponses.get(0);
        response.setTitle(update.getTitle());

        given(mapper.convertEntityToDto(movie)).willReturn(response);

        given(repository.findById(1L)).willReturn(Optional.of(movieEntities.get(0)));
        given(repository.save(movie)).willReturn(movie);

        MovieResponse updatedMovie = service.update(1L, update);
        verify(repository, Mockito.times(1)).findById(1L);
        verify(repository, Mockito.times(1)).save(movie);
        assertEquals(updatedMovie.getTitle(), update.getTitle());
    }

    @Test
    void updateMovieCanUpdateAllEntity(){
        MovieUpdate update = MovieUpdate.builder().title("New Title").description("New Description").director("New Director").duration(100).year(2000).genres(Set.of("Fiction")).build();
        MovieEntity movie = MovieEntity.builder().id(1L).title(update.getTitle()).description(update.getDescription()).director(update.getDirector()).duration(update.getDuration()).year(update.getYear()).genres(Set.of(GenreEntity.builder().name("Fiction").build())).build();
        MovieResponse response = MovieResponse.builder().id(1L).title(update.getTitle()).description(update.getDescription()).director(update.getDirector()).duration(update.getDuration()).year(update.getYear()).genres(Set.of("Fiction")).build();

        given(mapper.convertEntityToDto(movie)).willReturn(response);
        given(mapper.convertSetStringToGenre(Set.of("Fiction"))).willReturn(Set.of(GenreEntity.builder().name("Fiction").build()));

        given(repository.findById(1L)).willReturn(Optional.of(movieEntities.get(0)));
        given(repository.save(movie)).willReturn(movie);

        MovieResponse updatedMovie = service.update(1L, update);
        verify(repository, Mockito.times(1)).findById(1L);
        verify(repository, Mockito.times(1)).save(movie);
        verify(mapper, Mockito.times(1)).convertSetStringToGenre(Set.of("Fiction"));
        assertEquals(updatedMovie.getTitle(), update.getTitle());
    }

    @Test
    void findByFilterWithNoFilterReturnsAllMovies() {
        given(repository.findAll(any(Specification.class))).willReturn(movieEntities);

        List<MovieResponse> movies = service.findByFilter(null, null, null, null, null, null);
        verify(repository, Mockito.times(1)).findAll(any(Specification.class));
        assert !movies.isEmpty();
        assertEquals(movies, movieResponses);
    }

    @Test
    void findByFilterWithFilterReturnsFilteredMovies() {
        given(repository.findAll(any(Specification.class))).willReturn(movieEntities);
        given(mapper.convertSetStringToGenre(Set.of("Fiction"))).willReturn(Set.of(GenreEntity.builder().name("Fiction").build()));

        List<MovieResponse> movies = service.findByFilter("Title", "Director", 2000, 10, 100, Set.of("Fiction"));

        verify(repository, Mockito.times(1)).findAll(any(Specification.class));
        assert !movies.isEmpty();
        assertEquals(movies, movieResponses);
    }

    @Test
    void saveListSavesAllMovies() {
        given(repository.findByTitleAndYear(any(String.class), any(Integer.class))).willReturn(Optional.empty());

        given(repository.save(movieEntities.get(0))).willReturn(movieEntities.get(0));
        given(repository.save(movieEntities.get(1))).willReturn(movieEntities.get(1));
        given(repository.save(movieEntities.get(2))).willReturn(movieEntities.get(2));

        List<MovieResponse> savedMovies = service.saveList(movieRequests);
        verify(repository, Mockito.times(3)).save(any(MovieEntity.class));
        assert !savedMovies.isEmpty();
        assertEquals(savedMovies, movieResponses);
    }


}
