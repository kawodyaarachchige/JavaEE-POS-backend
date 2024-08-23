package org.example.demo.bo;

import org.example.demo.bo.custom.impl.CustomerBOImpl;

public class BOFactory {

    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getBOFactory() {
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }


    public enum BOFactoryTypes{
        CUSTOMER,
    }

    public SuperBO getBO(BOFactoryTypes boFactoryTypes){
        switch (boFactoryTypes){
            case CUSTOMER:
                return new CustomerBOImpl();
            default:
                return null;
        }
    }
}
