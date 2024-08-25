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

@WebServlet(urlPatterns = "/customer", loadOnStartup = 1)
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
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try (Writer writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            List<CustomerDTO> allCustomers = customerBO.getAllCustomers();
            resp.setStatus(200);
            StandardResponse standardResponse;
            standardResponse = new StandardResponse(200, "Customer saved", allCustomers);
            jsonb.toJson(standardResponse, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) {
       try (Writer writer = resp.getWriter()) {
           Jsonb jsonb = JsonbBuilder.create();
           CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);
           String pathInfo = req.getPathInfo();
           String searchId = "";
           if(pathInfo == null || pathInfo.isEmpty()){
               searchId = "";
           }else {
               pathInfo.substring(1);
           }

           Boolean isUpdated = customerBO.updateCustomer(searchId, customerDTO);
           StandardResponse standardResponse;
           if (isUpdated) {
               logger.info("Customer updated");
               resp.setStatus(200);
               standardResponse = new StandardResponse(200, "Customer updated", customerDTO);
           } else {
               logger.info("Customer not updated");
               resp.setStatus(404);
               standardResponse = new StandardResponse(404, "Customer not updated", null);
           }
           jsonb.toJson(standardResponse, writer);
       } catch (SQLException|IOException e) {
           throw new RuntimeException(e);
       }

    }
}
