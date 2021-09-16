package ru.ibs.dpr.crud.spring.services;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.ibs.dpr.crud.spring.dto.ResponseDto;
import ru.ibs.dpr.crud.spring.mapper.Mapper;
import ru.ibs.dpr.crud.spring.params.*;

import java.util.List;
import java.util.stream.Collectors;

import static ru.ibs.dpr.crud.spring.services.AbstractFindService.PageBase.ZERO_BASED;

@Accessors(chain = true)
public abstract class AbstractFindService<DTO, ENTITY>
        implements FindService<DTO> {

    public enum PageBase {ZERO_BASED, ONE_BASED};

    @Getter(AccessLevel.PROTECTED)
    private final JpaSpecificationExecutor<ENTITY> repository;
    @Getter(AccessLevel.PROTECTED)
    private final Mapper<DTO, ENTITY> mapper;

    @Setter private PageBase pageBase = ZERO_BASED;

    public AbstractFindService(JpaSpecificationExecutor<ENTITY> repository, Mapper<DTO, ENTITY> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public <PARAMS extends Params> ResponseDto<DTO> find(PARAMS params) {
        Specification<ENTITY> specification = buildSpecification(params);

        if (params != null && params.getPage() != null && params.getLimit() != null) {
            Page<ENTITY> entitiesPage = getRepository().findAll(specification, buildPageable(params));
            return new ResponseDto<>(
                    entitiesPage.stream()
                            .map(getMapper()::entityToDto)
                            .collect(Collectors.toList()),
                    entitiesPage.getTotalElements());
        }

        List<ENTITY> foundEntities = getRepository().findAll(specification, buildSort(params));

        return new ResponseDto<>(
                foundEntities.stream()
                        .map(getMapper()::entityToDto)
                        .collect(Collectors.toList()),
                (long) foundEntities.size());
    }

    protected Specification<ENTITY> buildSpecification(Params params) {
        if (params == null || CollectionUtils.isEmpty(params.getFilters())) {
            return null;
        }

        List<FilterSpecification<ENTITY>> filterSpecifications = params.getFilters().stream()
                                                                            .map(this::createFilterSpecification)
                                                                            .collect(Collectors.toList());
        Specification<ENTITY> result = null;
        for (FilterSpecification<ENTITY> filterSpec : filterSpecifications) {
            result = result == null ? Specification.where(filterSpec)
                                    : Specification.where(result).and(filterSpec);
        }

        return result;
    }

    private FilterSpecification<ENTITY> createFilterSpecification(FilterItem filterItem) {
        return new FilterSpecification<>(new FilterCriteria(filterItem.getProperty(),
                                                            Operator.valueOf(filterItem.getOperator().toUpperCase()),
                                                            filterItem.getValue(),
                                                            filterItem.getValue2()));
    }

    private Pageable buildPageable(Params params) {
        int pageNum = ZERO_BASED.equals(pageBase) ? params.getPage() : params.getPage() - 1;
        return PageRequest.of(pageNum, params.getLimit(), buildSort(params));
    }

    private Sort buildSort(Params params) {
        if (params == null || CollectionUtils.isEmpty(params.getSorts())) {
            return Sort.unsorted();
        }
        List<Sort.Order> orders = params.getSorts().stream()
                .map(sort -> new Sort.Order(Sort.Direction.fromString(sort.getType()), sort.getField()))
                .collect(Collectors.toList());
        return Sort.by(orders);
    }
}
