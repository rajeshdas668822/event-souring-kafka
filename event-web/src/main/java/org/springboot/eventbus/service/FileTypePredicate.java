package org.springboot.eventbus.service;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultMessage;
import org.apache.commons.lang3.StringUtils;
import org.restlet.util.Series;
import org.springframework.stereotype.Service;

/**
 * Created by rdas on 3/28/2017.
 */

public class FileTypePredicate implements Predicate {

    private String fileType;

    public FileTypePredicate( String fileType){
        this.fileType = fileType;
    }

    @Override
    public boolean matches(Exchange exchange) {
        Series series = (Series)exchange.getIn().getHeader("org.restlet.http.headers");

        if(series!=null){
            if(series.getValues("fileType")!=null){
                if(StringUtils.isNotEmpty(fileType) && fileType.equalsIgnoreCase(series.getValues("fileType")))
                    return true;
            }

        }
        return false;
    }
}
