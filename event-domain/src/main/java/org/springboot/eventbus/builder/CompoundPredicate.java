package org.springboot.eventbus.builder;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class CompoundPredicate extends Predicate {

    private List<Predicate> predicates;

    public CompoundPredicate(List<Predicate> predicates, PredicateOperator op) {
        super(op);
        this.predicates = predicates;
    }
}
