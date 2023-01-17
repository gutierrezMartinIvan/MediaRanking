package ar.com.mediaranking.models.repository.specification;

import ar.com.mediaranking.models.entity.GenreEntity;
import ar.com.mediaranking.models.entity.MovieEntity;
import ar.com.mediaranking.models.entity.SeasonEntity;
import ar.com.mediaranking.models.entity.SeriesEntity;
import ar.com.mediaranking.models.entity.filter.MovieFilter;
import ar.com.mediaranking.models.entity.filter.SeasonFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SeasonSpecification {
    public static Specification<SeasonEntity> getByFilters(SeasonFilter filter) {
        return (((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasLength(filter.getTitle())) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("title")),
                                "%" + filter.getTitle().toLowerCase() + "%"));
            }
            if (filter.getYear() != null) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get("year"), filter.getYear())
                );
            }
            if (filter.getSeasonNumber() != null) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get("seasonNumber"), filter.getSeasonNumber())
                );
            }
            if (filter.getSeriesId() != null) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get("series").get("id"), filter.getSeriesId())
                );
            }

            query.distinct(true);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        }));
    }
}
