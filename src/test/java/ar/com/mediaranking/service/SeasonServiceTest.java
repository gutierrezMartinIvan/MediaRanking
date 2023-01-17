package ar.com.mediaranking.service;

import ar.com.mediaranking.models.repository.EpisodeRepository;
import ar.com.mediaranking.models.repository.ISeriesRepository;
import ar.com.mediaranking.models.repository.SeasonRepository;
import ar.com.mediaranking.service.impl.SeasonServiceImpl;
import ar.com.mediaranking.utils.DtoToEntityConverter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

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


}
