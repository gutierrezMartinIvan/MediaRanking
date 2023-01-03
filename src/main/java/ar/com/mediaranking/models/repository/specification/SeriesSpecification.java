package ar.com.mediaranking.models.repository.specification;

import ar.com.mediaranking.models.entity.SeriesEntity;
import ar.com.mediaranking.models.entity.filter.SeriesFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class SeriesSpecification {

    public Specification<SeriesEntity> getByFilters(SeriesFilter filter) {
        return (((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasLength(filter.getTittle())) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("tittle")),
                                "%" + filter.getTittle().toLowerCase() + "%"));
            }

            if (!CollectionUtils.isEmpty(filter.getGenres())) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("genres")),
                                "%" + filter.getGenres() + "%"));
            }

            if (StringUtils.hasLength(filter.getAuthor())) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("author")),
                                "%" + filter.getAuthor().toLowerCase() + "%"));
            }

            if (filter.getYear() != null) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("year")),
                                "%" + filter.getYear() + "%"));
            }

            query.distinct(true);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        }));
    }
}
