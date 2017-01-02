package org.springboot.eventbus.helper;

import lombok.Getter;

@Getter
public class ComparisonPredicate extends Predicate {

    private String name;
    private Object value;


    public ComparisonPredicate() {
        op = PredicateOperator.EQ;
    }

    public ComparisonPredicate(String name, Object value, PredicateOperator op) {
        super(op);
        this.name = name;
        this.value = value;
    }
}
