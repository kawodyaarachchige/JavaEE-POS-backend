package org.example.demo.dao;

import org.example.demo.dao.custom.impl.CustomerDAOImpl;
import org.example.demo.dao.custom.impl.ItemDAOImpl;

public class DAOFactory  {
    private static DAOFactory daoFactory;
    private DAOFactory() {

    }
    public static DAOFactory getDaoFactory(){
        return daoFactory==null ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DaoFactoryTypes{
        CUSTOMER,
        ITEM
    }

    public SuperDAO getDao(DaoFactoryTypes daoFactoryTypes){
        switch (daoFactoryTypes){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            default:
                return null;
        }
    }
}
