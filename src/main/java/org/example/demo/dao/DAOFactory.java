package org.example.demo.dao;

import org.example.demo.dao.custom.impl.CustomerDAOImpl;
import org.example.demo.dao.custom.impl.ItemDAOImpl;
import org.example.demo.dao.custom.impl.OrderDAOImpl;
import org.example.demo.dao.custom.impl.OrderDetailDAOImpl;

public class DAOFactory  {
    private static DAOFactory daoFactory;
    private DAOFactory() {

    }
    public static DAOFactory getDaoFactory(){
        return daoFactory==null ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DaoFactoryTypes{
        CUSTOMER,
        ITEM,
        ORDER,
        ORDER_DETAIL
    }

    public SuperDAO getDao(DaoFactoryTypes daoFactoryTypes){
        switch (daoFactoryTypes){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDER_DETAIL:
                return new OrderDetailDAOImpl();
            default:
                return null;
        }
    }
}
