package ar.com.mediaranking.config;

import ar.com.mediaranking.models.entity.EpisodeEntity;
import ar.com.mediaranking.models.entity.GenreEntity;
import ar.com.mediaranking.models.entity.SeasonEntity;
import ar.com.mediaranking.models.entity.SeriesEntity;
import ar.com.mediaranking.models.repository.IGenreRepository;
import ar.com.mediaranking.models.request.EpisodeRequest;
import ar.com.mediaranking.models.request.SeasonRequest;
import ar.com.mediaranking.models.request.SeriesRequest;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Autowired
    private IGenreRepository genreRepository;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mm = new ModelMapper();
        //mm.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        /*mm.getConfiguration().setPropertyCondition(Conditions.isNotNull())
        mm.typeMap(SeriesRequest.class, SeriesEntity.class)
                .addMappings(mapper -> mapper.skip(SeriesEntity::setSeasons));*/
        mm.typeMap(SeasonRequest.class, SeasonEntity.class)
                .addMappings(mapper -> mapper.skip(SeasonEntity::setId));
        mm.typeMap(EpisodeRequest.class, EpisodeEntity.class)
                .addMappings(mapper -> mapper.skip(EpisodeEntity::setId));

        mm.addConverter(new Converter<String, GenreEntity>() {
            public GenreEntity convert(MappingContext<String, GenreEntity> context)
            {
                String s = context.getSource();
                // TODO: Falta agregar excepcion si no existe el genero
                return genreRepository.findByName(s.toUpperCase());
            }
        });

        mm.addConverter(new Converter<GenreEntity, String>() {
            public String convert(MappingContext<GenreEntity, String> context)
            {
                return StringUtils.capitalize(context.getSource().getName().toLowerCase());
            }
        });


        return mm;
    }
}