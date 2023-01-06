package ar.com.mediaranking.config;

import ar.com.mediaranking.models.entity.GenreEntity;
import ar.com.mediaranking.models.repository.IGenreRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeriesServiceConfig {

    @Autowired
    private IGenreRepository genreRepository;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mm = new ModelMapper();



        mm.addConverter(new Converter<String, GenreEntity>() {
            public GenreEntity convert(MappingContext<String, GenreEntity> context)
            {
                String s = context.getSource();
                GenreEntity genre = genreRepository.findByName(s);
                // TODO: Falta agregar excepcion si no existe el genero
                return genre;
            }
        });

        mm.addConverter(new Converter<GenreEntity, String>() {
            public String convert(MappingContext<GenreEntity, String> context)
            {
                return context.getSource().getName();
            }
        });

        return mm;
    }
}
