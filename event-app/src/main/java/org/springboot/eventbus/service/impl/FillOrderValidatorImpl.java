package org.springboot.eventbus.service.impl;

import org.activiti.engine.delegate.DelegateExecution;
import org.springboot.eventbus.dao.OrderDao;
import org.springboot.eventbus.entity.Order;
import org.springboot.eventbus.services.FillOrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by rdas on 10/17/2016.
 */

@Service("fillOrderValidator")
public class FillOrderValidatorImpl implements FillOrderValidator {


    @Autowired
    OrderDao orderDao;


    @Override
    public void validateFillOrder(DelegateExecution execution) {

       System.out.println("Inside Check order Fill  Script") ;

        Order order = (Order) execution.getVariable("order");
        boolean isFill = false;
        String workingOrderAction = "";
        String pendingCancelAction = (String)execution.getVariable("actionType");
        if(pendingCancelAction != null && pendingCancelAction == "Cancel"){
            execution.setVariable("previousAction", "Working Order" );
            workingOrderAction = "Cancel";
            order.setStatus("Pending Cancel");
            execution.setVariable("workingOrderAction",workingOrderAction);
            execution.setVariable("order",order);
        }else{
            double convertedFilledAmount = (Double)execution.getVariable("filledAmount");

            if (convertedFilledAmount == order.getAmount()){
                execution.setVariable("workingOrderAction","Fill");
                workingOrderAction = "Fill";
                order.setStatus("Fill");

            }else{
                execution.setVariable("workingOrderAction","Partial Fill");
                workingOrderAction = "Partial Fill";
                order.setStatus("Partial Fill");
            }
        }
        execution.setVariable("order",order);
        orderDao.updateOrder(order);
        System.out.println("Going out of Fill Script:" + workingOrderAction);

    }
}
