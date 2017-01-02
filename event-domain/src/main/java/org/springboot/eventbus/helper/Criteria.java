package org.springboot.eventbus.helper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import javax.persistence.criteria.JoinType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Criteria {

    private final List<Predicate> filters;



    @JsonIgnore
    private final Map<Class, JoinType> joinTypes;

    private Integer offset;

    private Integer size;

    @JsonIgnore
    private Map<String, String> fieldNameMapping;

    public Criteria() {
        this.filters = new ArrayList<>();
        this.joinTypes = new HashMap<>();
    }

    public static Criteria of() {
        return new Criteria();
    }

    public Criteria and(Predicate... predicates) {
        filters.add(Predicate.and(predicates));
        return this;
    }

    public Criteria or(Predicate... predicates) {
        filters.add(Predicate.or(predicates));
        return this;
    }

    public Criteria eq(String name, Object value) {
        filters.add(Predicate.eq(name, value));
        return this;
    }

    public Criteria ne(String name, Object value) {
        filters.add(Predicate.ne(name, value));
        return this;
    }

    public Criteria ge(String name, Object value) {
        filters.add(Predicate.ge(name, value));
        return this;
    }

    public Criteria gt(String name, Object value) {
        filters.add(Predicate.gt(name, value));
        return this;
    }

    public Criteria le(String name, Object value) {
        filters.add(Predicate.le(name, value));
        return this;
    }

    public Criteria lt(String name, Object value) {
        filters.add(Predicate.lt(name, value));
        return this;
    }

    public Criteria like(String name, String pattern) {
        filters.add(Predicate.like(name, pattern));
        return this;
    }



    public Criteria join(Class clazz, JoinType type) {
        joinTypes.put(clazz, type);
        return this;
    }

    public void addFilters(List<Predicate> filters) {
        if (filters != null) {
            this.getFilters().addAll(filters);
        }
    }

    /**
     * Set offset value - from which record
     *
     * @param offset
     * @return Criteria
     */
    public Criteria offset(Integer offset) {
        this.offset = offset;
        return this;
    }

    /**
     * Set number of records to fetch
     *
     * @param size
     * @return Criteria
     */
    public Criteria size(Integer size) {
        this.size = size;
        return this;
    }

    public Criteria fieldNameMapping(Map<String, String> fieldNameMapping) {
        this.fieldNameMapping = fieldNameMapping;
        return this;
    }

    public String mapFieldName(String fieldName) {
        if (fieldNameMapping != null) {
            String mapping = fieldNameMapping.get(fieldName);
            return mapping != null ? mapping : fieldName;
        } else {
            return fieldName;
        }
    }
}
