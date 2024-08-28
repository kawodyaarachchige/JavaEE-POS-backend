package org.example.demo.controller;


import jakarta.json.JsonException;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.bo.BOFactory;
import org.example.demo.bo.custom.ItemBO;
import org.example.demo.bo.custom.OrderDetailsBO;
import org.example.demo.dto.OrderDetailDTO;
import org.example.demo.util.StandardResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/orderDetails")
public class OrderDetailsController extends HttpServlet {
    JsonbConfig config = new JsonbConfig().withFormatting(true);
    Jsonb jsonb = JsonbBuilder.create(config);
    static Logger logger = LoggerFactory.getLogger(OrderDetailsController.class);
    OrderDetailsBO orderDetailsBO = (OrderDetailsBO) BOFactory.getBOFactory().getBO(BOFactory.BOFactoryTypes.ORDER_DETAILS);
    ItemBO itemBO = (ItemBO) BOFactory.getBOFactory().getBO(BOFactory.BOFactoryTypes.ITEM);

    @Override
    public void init() throws ServletException {


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        logger.info("Inside order detail get method");

        String id = req.getParameter("id");

        if (id != null) {
            try (var writer = resp.getWriter()) {
                writer.write(jsonb.toJson(orderDetailsBO.searchByOrderId(id)));
            } catch (JsonException | SQLException | ClassNotFoundException e) {
                logger.error("Faild with: ",e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null) {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }

        try (var writer = resp.getWriter()){

            logger.info("Inside order detail post method");

            OrderDetailDTO orderDetailDTO = jsonb.fromJson(req.getReader(), OrderDetailDTO.class);
            boolean isSave = orderDetailsBO.save(orderDetailDTO);
            boolean isUpdate = itemBO.updateQty(orderDetailDTO.getItem_id(), String.valueOf(orderDetailDTO.getQty()));

            StandardResponse standardResponse;
            if (isSave && isUpdate) {
                writer.write("Order Details saved successfully");
                logger.info("Order Details saved successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
                standardResponse = new StandardResponse(200,"Order Details saved successfully", null);
            } else {
                writer.write("Order Details not saved");
                logger.error("Order Details not saved");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                standardResponse = new StandardResponse(400,"Order Details not saved", null);
            }
            jsonb.toJson(standardResponse, writer);
        } catch (JsonException | SQLException | ClassNotFoundException e) {
            logger.error("Faild with: ",e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
