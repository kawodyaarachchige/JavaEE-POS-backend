package org.example.demo.controller;


import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.demo.bo.BOFactory;
import org.example.demo.bo.custom.ItemBO;
import org.example.demo.dto.ItemDTO;
import org.example.demo.util.StandardResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/item/*", loadOnStartup = 1)
public class ItemController extends HttpServlet {
    ItemBO itemBO = (ItemBO) BOFactory.getBOFactory().getBO(BOFactory.BOFactoryTypes.ITEM);
    static Logger logger = LoggerFactory.getLogger(ItemController.class);
    @Override
    public void init() {
        System.out.println("Item Controller initialized");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println();
        try (Writer writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            ItemDTO itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);
            System.out.println("Item Description: " + itemDTO.getDescription());
            System.out.println("Item Price: " + itemDTO.getPrice());
            System.out.println("Item Quantity: " + itemDTO.getQuantity());
            System.out.println(itemDTO.getDescription());
            Boolean isSaved = itemBO.saveItem(itemDTO);
            System.out.println("is Item Saved : "+isSaved);
            StandardResponse standardResponse;
            if (isSaved) {
                logger.info("Item saved");
                resp.setStatus(200);
                standardResponse = new StandardResponse(200, "Item saved", itemDTO);
            } else {
                logger.info("Item not saved");
                resp.setStatus(404);
                standardResponse = new StandardResponse(404, "Item not saved", null);
            }
            jsonb.toJson(standardResponse, writer);
        } catch (Exception e) {
            logger.error("Error while saving item", e);
            throw new RuntimeException(e);
        }
    }
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try (Writer writer = resp.getWriter()) {
            String pathInfo = req.getPathInfo();
            String searchedId = (pathInfo == null || pathInfo.isEmpty()) ? null : pathInfo.substring(1);
            StandardResponse standardResponse;
            System.out.println("Searched Item Id : "+searchedId);
            if(searchedId!=null){
                ItemDTO searchedItems = itemBO.searchItem(searchedId);
                Jsonb jsonb = JsonbBuilder.create();
                if(searchedItems!=null){
                    logger.info("Item found");
                    resp.setStatus(200);
                    standardResponse = new StandardResponse(200, "Item found", searchedItems);
                }else{
                    logger.info("Item not found");
                    resp.setStatus(404);
                    standardResponse = new StandardResponse(404, "Item not found", null);
                }
                jsonb.toJson(standardResponse, writer);
            }else{
                List<ItemDTO> allItems = itemBO.getAllItems();
                Jsonb jsonb = JsonbBuilder.create();
                resp.setStatus(200);
                standardResponse = new StandardResponse(200, "All Items", allItems);
                jsonb.toJson(standardResponse, writer);
            }
        } catch (Exception e) {
            logger.error("Error while getting all items", e);
            throw new RuntimeException(e);
        }
    }
    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) {
        try(Writer writer = resp.getWriter()){
            Jsonb jsonb = JsonbBuilder.create();
            ItemDTO itemDTO = jsonb.fromJson(req.getReader(), ItemDTO.class);
            String pathInfo = req.getPathInfo();
            String searchId =(pathInfo==null||pathInfo.isEmpty())?"":pathInfo.substring(1);

            Boolean isUpdated = itemBO.updateItem(searchId, itemDTO);
            StandardResponse standardResponse;
            if (isUpdated) {
                logger.info("Item updated");
                resp.setStatus(200);
                standardResponse = new StandardResponse(200, "Item updated", itemDTO);
            } else {
                logger.info("Item not updated");
                resp.setStatus(404);
                standardResponse = new StandardResponse(404, "Item not updated", null);
            }
            jsonb.toJson(standardResponse, writer);
        } catch (SQLException | IOException e) {
            logger.error("Error while updating item", e);
            resp.setStatus(404);
            throw new RuntimeException(e);
        }
    }
    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.getPathInfo();
        String searchId =(pathInfo==null||pathInfo.isEmpty())?"":pathInfo.substring(1);
        Boolean isDeleted = null;

        try {
            isDeleted = itemBO.deleteItem(searchId);
        } catch (SQLException e) {
            logger.error("Error while deleting item", e);
            resp.setStatus(404);
            throw new RuntimeException(e);
        }
        StandardResponse standardResponse;
        if (isDeleted) {
            logger.info("Item deleted");
            resp.setStatus(200);
            standardResponse = new StandardResponse(200, "Item deleted", null);
        } else {
            logger.info("Item not deleted");
            resp.setStatus(404);
            standardResponse = new StandardResponse(404, "Item not deleted", null);
        }
        try (Writer writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            jsonb.toJson(standardResponse, writer);
        } catch (IOException e) {
            logger.error("Error while deleting item", e);
            resp.setStatus(404);
            throw new RuntimeException(e);
        }

    }

}
