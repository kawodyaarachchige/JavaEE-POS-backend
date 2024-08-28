package org.example.demo.controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.bo.BOFactory;
import org.example.demo.bo.custom.CustomerBO;
import org.example.demo.dto.CustomerDTO;
import org.example.demo.util.StandardResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/customer/*", loadOnStartup = 1)
public class CustomerController extends HttpServlet {
    CustomerBO customerBO = (CustomerBO) BOFactory.getBOFactory().getBO(BOFactory.BOFactoryTypes.CUSTOMER);
    static Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Override
    public void init() {
        logger.info("Customer Controller initialized");


    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try (Writer writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);
            Boolean isSaved = customerBO.saveCustomer(customerDTO);
            StandardResponse standardResponse;
            if (isSaved) {
                logger.info("Customer saved");
                resp.setStatus(200);
                standardResponse = new StandardResponse(200, "Customer saved", customerDTO);
            } else {
                logger.info("Customer not saved");
                resp.setStatus(404);
                standardResponse = new StandardResponse(404, "Customer not saved", null);
            }
            jsonb.toJson(standardResponse, writer);
        } catch (Exception e) {
            logger.error("Error while saving customer", e);
            throw new RuntimeException(e);
        }
    }
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse response) {
        try(Writer writer=response.getWriter()){
            String pathInfo = req.getPathInfo();
            String searchedId = (pathInfo == null || pathInfo.isEmpty()) ? null : pathInfo.substring(1);
            StandardResponse standardResponse;
            if (searchedId!=null){
                CustomerDTO searchedCustomers = customerBO.searchCustomer(searchedId);
                Jsonb jsonb = JsonbBuilder.create();
                if(searchedCustomers!=null){
                    System.out.println(searchedCustomers.getName());
                    response.setStatus(200);
                    standardResponse = new StandardResponse(200,"Customer found successfully",searchedCustomers);
                }else{
                    response.setStatus(404);
                    standardResponse = new StandardResponse(404,"Customer not found",null);
                }
                jsonb.toJson(standardResponse,writer);
            }else{
                List<CustomerDTO> allCustomers = customerBO.getAllCustomers();
                Jsonb jsonb = JsonbBuilder.create();
                response.setStatus(200);
                standardResponse = new StandardResponse(200,"Customers found successfully",allCustomers);
                jsonb.toJson(standardResponse,writer);
            }
        }catch (Exception e) {
            logger.error("Error while Getting Customer / Customers : ", e);
            throw new RuntimeException(e);
        }
    }
    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) {
        try (Writer writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);
            String pathInfo = req.getPathInfo();
            String searchId = (pathInfo==null||pathInfo.isEmpty())?"":pathInfo.substring(1);

            Boolean isUpdated = customerBO.updateCustomer(searchId, customerDTO);
            StandardResponse standardResponse;

            if (isUpdated) {
                resp.setStatus(200);
                standardResponse = new StandardResponse(200, "Customer updated", customerDTO);
            } else {
                resp.setStatus(404);
                standardResponse = new StandardResponse(404, "Customer not found", null);
            }

            jsonb.toJson(standardResponse, writer);
        } catch (SQLException | IOException e) {
            logger.error("Error while updating customer", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.getPathInfo();
        String searchId = (pathInfo==null||pathInfo.isEmpty())?"":pathInfo.substring(1);

        Boolean isDeleted = null;
        try {
            isDeleted = customerBO.deleteCustomer(searchId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        StandardResponse standardResponse;
        if (isDeleted) {
            logger.info("Customer deleted");
            resp.setStatus(200);
            standardResponse = new StandardResponse(200, "Customer deleted", null);
        } else {
            logger.info("Customer not found");
            resp.setStatus(404);
            standardResponse = new StandardResponse(404, "Customer not found", null);
        }
        Jsonb jsonb = JsonbBuilder.create();
        try (Writer writer = resp.getWriter()) {
            jsonb.toJson(standardResponse, writer);
        } catch (IOException e) {
            logger.error("Error while deleting customer", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
    }

}
