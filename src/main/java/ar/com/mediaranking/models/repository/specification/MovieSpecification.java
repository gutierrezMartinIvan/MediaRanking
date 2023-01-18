package ar.com.mediaranking.models.repository.specification;

import ar.com.mediaranking.models.entity.GenreEntity;
import ar.com.mediaranking.models.entity.MovieEntity;
import ar.com.mediaranking.models.entity.SeriesEntity;
import ar.com.mediaranking.models.entity.filter.MovieFilter;
import ar.com.mediaranking.models.entity.filter.SeriesFilter;
import ar.com.mediaranking.utils.DtoToEntityConverter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MovieSpecification {
    public static Specification<MovieEntity> getByFilters(MovieFilter filter) {
        return (((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasLength(filter.getTitle())) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("title")),
                                "%" + filter.getTitle().toLowerCase() + "%"));
            }

            if (StringUtils.hasLength(filter.getDirector())) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("author")),
                                "%" + filter.getDirector().toLowerCase() + "%"));
            }

            if (filter.getYear() != null) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get("year"), filter.getYear())
                );
            }

            if (filter.getGenres() != null && !filter.getGenres().isEmpty()) {
                Join<SeriesEntity, GenreEntity> join = root.join("genres");
                CriteriaBuilder.In<String> in = criteriaBuilder.in(join.get("name"));
                for(String genre : filter.getGenres()) {
                    in.value(genre.toUpperCase());
                }
                predicates.add(in);
            }

            if(filter.getMinDuration() != null){
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(
                                root.get("duration"), filter.getMinDuration())
                );
            }

            if(filter.getMaxDuration() != null) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get("duration"), filter.getMaxDuration())
                );
            }

            query.distinct(true);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        }));
    }
}
