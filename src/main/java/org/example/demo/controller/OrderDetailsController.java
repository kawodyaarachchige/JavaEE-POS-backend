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

@WebServlet(urlPatterns = "/orderDetails/*")
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
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Inside order detail GET method");

        String id = req.getParameter("id");
        try (var writer = resp.getWriter()) {
            if (id != null) {
                var orderDetails = orderDetailsBO.searchByOrderId(id);
                if (orderDetails != null) {
                    resp.setContentType("application/json");
                    resp.setStatus(HttpServletResponse.SC_OK);
                    writer.write(jsonb.toJson(orderDetails));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    StandardResponse standardResponse = new StandardResponse(404, "Order not found", null);
                    writer.write(jsonb.toJson(standardResponse));
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                StandardResponse standardResponse = new StandardResponse(400, "Order ID is required", null);
                writer.write(jsonb.toJson(standardResponse));
            }
        } catch (JsonException | SQLException | ClassNotFoundException e) {
            logger.error("Failed with: ", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            StandardResponse standardResponse = new StandardResponse(500, "An error occurred", null);
            try (var writer = resp.getWriter()) {
                writer.write(jsonb.toJson(standardResponse));
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null) {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
            return;
        }

        try (var writer = resp.getWriter()) {
            logger.info("Inside order detail POST method");

            OrderDetailDTO orderDetailDTO = jsonb.fromJson(req.getReader(), OrderDetailDTO.class);
            boolean isSave = orderDetailsBO.save(orderDetailDTO);
            boolean isUpdate = itemBO.updateQty(orderDetailDTO.getItem_id(), String.valueOf(orderDetailDTO.getQty()));

            StandardResponse standardResponse;
            if (isSave && isUpdate) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                standardResponse = new StandardResponse(201, "Order Details saved successfully", null);
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                standardResponse = new StandardResponse(400, "Order Details not saved", null);
            }
            resp.setContentType("application/json");
            writer.write(jsonb.toJson(standardResponse));
        } catch (JsonException | SQLException | ClassNotFoundException e) {
            logger.error("Failed with: ", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            StandardResponse standardResponse = new StandardResponse(500, "An error occurred", null);
            try (var writer = resp.getWriter()) {
                writer.write(jsonb.toJson(standardResponse));
            }
        }
    }

}
