package org.springboot.eventbus.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.criteria.*;
import java.util.Map;

public class CriteriaHelper {

    private static final Logger logger = LoggerFactory.getLogger(CriteriaHelper.class);

    private static final String REGEX_INDEX_MATCH = "\\[[\\d]+\\]";

    public static final String REGEX_DOT_SEPARATOR = "\\.";

    /**
     * Find joined the path
     *
     * @return Path of compound field to the primitive type
     */
    public static Path getCompoundJoinedPath(Root<?> rootPath, String fieldName, Map<Class, JoinType> joinTypes) {
        if (fieldName == null) {
            return null;
        }
        String[] compoundField = splitByDot(fieldName);

        Join<?, ?> join;

        if (compoundField.length == 1) {
            return rootPath.get(compoundField[0]);
        } else {
            join = reuseJoin(rootPath, compoundField[0], joinTypes);
        }

        for (int i = 1; i < compoundField.length; i++) {
            if (i < (compoundField.length - 1)) {
                join = reuseJoin(join, compoundField[i], joinTypes);
            } else {
                return join.get(compoundField[i]);
            }
        }

        return null;
    }

    // trying to find already existing joins to reuse

    /**
     * Reuse the existing join
     *
     * @return Join
     */
    public static Join reuseJoin(From<?, ?> path, String fieldName, Map<Class, JoinType> joinTypes) {
        for (Join<?, ?> join : path.getJoins()) {
            if (join.getAttribute().getName().equals(fieldName)) {
                logger.debug("Reusing existing join for field " + fieldName);
                return join;
            }
        }
        JoinType joinType = null;
        try {
            Class fieldClass = path.getJavaType().getDeclaredField(fieldName).getType();
            joinType = joinTypes.get(fieldClass);
        } catch (NoSuchFieldException e) {
            logger.error("No such field [" + fieldName + "], using INNER join for it", e);
        }
        return path.join(fieldName, null == joinType ? JoinType.INNER : joinType);
    }

    /**
     * Remove indexed property from string "[0]" and split by dot
     *
     * @return String[]
     */
    public static String[] splitByDot(String fieldName) {
        String name = fieldName.replaceAll(REGEX_INDEX_MATCH, QueryBuilderConstant.EMPTY_STRING);
        return name.split(REGEX_DOT_SEPARATOR);
    }
}
