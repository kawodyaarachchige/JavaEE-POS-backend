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

@WebServlet(urlPatterns = "/customer", loadOnStartup = 1)
public class CustomerController extends HttpServlet {
    CustomerBO customerBO=(CustomerBO) BOFactory.getBOFactory().getBO(BOFactory.BOFactoryTypes.CUSTOMER);
   static Logger logger = LoggerFactory.getLogger(CustomerController.class);
    @Override
    public void init(){
        logger.info("Customer Controller initialized");


    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp){
        try(Writer writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDTO customerDTO = jsonb.fromJson(req.getReader(), CustomerDTO.class);
            Boolean isSaved = customerBO.saveCustomer(customerDTO);
            StandardResponse standardResponse;
            if(isSaved){
                logger.info("Customer saved");
                resp.setStatus(200);
              standardResponse = new StandardResponse(200, "Customer saved", customerDTO);
            }else{
                logger.info("Customer not saved");
                resp.setStatus(404);
               standardResponse = new StandardResponse(404, "Customer not saved",null);
            }
            jsonb.toJson(standardResponse, writer);
        } catch (Exception e) {
            logger.error("Error while saving customer", e);
            throw new RuntimeException(e);
        }
    }


}
