package org.example.demo.bo;

import org.example.demo.bo.custom.impl.CustomerBOImpl;
import org.example.demo.bo.custom.impl.ItemBOImpl;

public class BOFactory {

    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getBOFactory() {
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }


    public enum BOFactoryTypes{
        CUSTOMER,
        ITEM
    }

    public SuperBO getBO(BOFactoryTypes boFactoryTypes){
        switch (boFactoryTypes){
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            default:
                return null;
        }
    }
}
