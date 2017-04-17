package org.springboot.eventbus.service;

import org.apache.camel.component.restlet.RestletComponent;
import org.restlet.Component;

/**
 * Created by rdas on 3/29/2017.
 */
public class CustomRestletComponent extends RestletComponent {

    /*CustomRestletComponent(Component component, boolean disableStreamCache){
        super(component);
        super.setDisableStreamCache(disableStreamCache);
    }*/


    CustomRestletComponent( boolean disableStreamCache){
        //super(component);
        super.setDisableStreamCache(disableStreamCache);
    }


}
