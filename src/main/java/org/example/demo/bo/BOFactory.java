package org.example.demo.bo;

import org.example.demo.bo.custom.impl.CustomerBOImpl;
import org.example.demo.bo.custom.impl.ItemBOImpl;
import org.example.demo.bo.custom.impl.OrderBOImpl;
import org.example.demo.bo.custom.impl.OrderDetailsBOImpl;

public class BOFactory {

    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getBOFactory() {
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }


    public enum BOFactoryTypes{
        CUSTOMER,
        ITEM,
        ORDER,
        ORDER_DETAILS
    }

    public SuperBO getBO(BOFactoryTypes boFactoryTypes){
        switch (boFactoryTypes){
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case ORDER:
                return new OrderBOImpl();
            case ORDER_DETAILS:
                return new OrderDetailsBOImpl();
            default:
                return null;
        }
    }
}
