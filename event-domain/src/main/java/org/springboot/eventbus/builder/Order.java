package org.springboot.eventbus.builder;



import org.springboot.eventbus.builder.helper.CriteriaHelper;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import java.util.HashMap;
import java.util.Map;


import static org.springboot.eventbus.builder.helper.QueryBuilderConstant.ASCENDING_PROPERTY;
import static org.springboot.eventbus.builder.helper.QueryBuilderConstant.KEY_PROPERTY;

public class Order {

    private final String name;
    private final boolean asc;

    public Order(String name, boolean asc) {
        this.name = name;
        this.asc = asc;
    }

    public Order(Map<String, Object> map) {
        name = (String) map.get(KEY_PROPERTY);
        if (map.containsKey(ASCENDING_PROPERTY)) {
            asc = (Boolean) map.get(ASCENDING_PROPERTY);
        } else {
            asc = true; // default to ASC
        }
    }

    public Map<String, Object> toSimpleMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(KEY_PROPERTY, name);
        map.put(ASCENDING_PROPERTY, asc);
        return map;
    }

    public javax.persistence.criteria.Order build(CriteriaBuilder b, CriteriaQuery<?> cq, Criteria c) {
        Path e = CriteriaHelper.getCompoundJoinedPath(cq.getRoots().iterator().next(), name, c.getJoinTypes()); // assume only one root
        return asc ? b.asc(e) : b.desc(e);
    }
}
