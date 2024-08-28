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
import org.example.demo.bo.custom.OrderBO;
import org.example.demo.dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/order/*")
public class OrderController extends HttpServlet {
    JsonbConfig config = new JsonbConfig().withFormatting(true);
    Jsonb jsonb = JsonbBuilder.create(config);
    static Logger logger = LoggerFactory.getLogger(OrderController.class);
    OrderBO orderBO = (OrderBO) BOFactory.getBOFactory().getBO(BOFactory.BOFactoryTypes.ORDER);

    @Override
    public void init() throws ServletException {


    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        logger.info("Inside order get method");

        String id = req.getParameter("id");
        String all = req.getParameter("all");
        String search = req.getParameter("search");
        String nextid = req.getParameter("nextid");

        if (all != null) {
            try (var writer = resp.getWriter()) {
                writer.write(jsonb.toJson(orderBO.getAllOrders()));
            } catch (JsonException | SQLException | ClassNotFoundException e) {
                logger.error("Faild with: ",e.getMessage());
                throw new RuntimeException(e);
            }
        } else if (id != null) {
            try (var writer = resp.getWriter()) {
                writer.write(jsonb.toJson(orderBO.search(id)));
            } catch (JsonException | SQLException | ClassNotFoundException e) {
                logger.error("Faild with: ",e.getMessage());
                throw new RuntimeException(e);
            }
        } else if (search != null) {
            try (var writer = resp.getWriter()) {
                writer.write(jsonb.toJson(orderBO.searchByOrderId(search)));
            } catch (JsonException | SQLException | ClassNotFoundException e) {
                logger.error("Faild with: ",e.getMessage());
                throw new RuntimeException(e);
            }
        } else if (nextid != null) {
            try (var writer = resp.getWriter()) {
                writer.write(jsonb.toJson(orderBO.nextOrderId()));
            } catch (JsonException | SQLException | ClassNotFoundException e) {
                logger.error("Faild with: ",e.getMessage());
                throw new RuntimeException(e);
            }
        }


    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null) {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }

        try (var writer = resp.getWriter()){

            logger.info("Inside order post method");

            OrderDTO orderDTO = jsonb.fromJson(req.getReader(), OrderDTO.class);
            boolean isSave = orderBO.saveOrder(orderDTO);

            if (isSave) {
                writer.write("Order saved successfully");
                logger.info("Order saved successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                writer.write("Order not saved");
                logger.error("Order not saved");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (JsonException | SQLException | ClassNotFoundException e) {
            logger.error("Faild with: ",e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getContentType().toLowerCase().startsWith("application/json") || req.getContentType() == null) {
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }

        try (var writer = resp.getWriter()){

            logger.info("Inside order put method");

            OrderDTO orderDTO = jsonb.fromJson(req.getReader(), OrderDTO.class);
            boolean isUpdate = orderBO.updateOrder(orderDTO);

            if (isUpdate) {
                writer.write("Order updated successfully");
                logger.info("Order updated successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                writer.write("Order not updated");
                logger.error("Order not updated");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (JsonException | SQLException | ClassNotFoundException e) {
            logger.error("Faild with: ",e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try (var writer = resp.getWriter()){

            logger.info("Inside order Delete method");

            String id = req.getParameter("id");
            boolean isDelete = orderBO.deleteOrder(id);

            if (isDelete) {
                writer.write("Order deleted successfully");
                logger.info("Order deleted successfully");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                writer.write("Order not deleted");
                logger.error("Order not deleted");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (JsonException | SQLException | ClassNotFoundException e) {
            logger.error("Faild with: ",e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
