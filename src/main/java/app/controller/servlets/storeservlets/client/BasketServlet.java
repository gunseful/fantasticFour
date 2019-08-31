package app.controller.servlets.storeservlets.client;

import app.controller.service.OrderServiceImpl;
import app.model.user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BasketServlet extends HttpServlet {
    public static Logger logger = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        //getting this session user to work with
        User user = (User) req.getSession().getAttribute("user");
        OrderServiceImpl orderService = new OrderServiceImpl();
        //если юзер не админ кидает на на вьюшку корзина
        try {
            req.setAttribute("basket", orderService.getBasketProducts(user));
            if (!user.isAdministrator()) {
                logger.info("User=" + user.getNickname() + "requests his basket list");
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/client/basket.jsp");
                requestDispatcher.forward(req, resp);
            } else {
                resp.sendRedirect("/listAdmin");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        //getting this session user to work with
        User user = (User) req.getSession().getAttribute("user");
        OrderServiceImpl orderService = new OrderServiceImpl();

        try {
            if (req.getParameter("plus") != null) {
                int i = Integer.parseInt(req.getParameter("plus"));
                orderService.updateBasket(true, user, i);
            } else {
                if (req.getParameter("minus") != null) {
                    int i = Integer.parseInt(req.getParameter("minus"));
                    orderService.updateBasket(false, user, i);
                } else {
                    if (req.getParameter("getOrder") != null) {
                        orderService.makeOrder(user);
                        logger.info("User=" + user.getNickname() + " makes order");
                        resp.sendRedirect("/orders");
                        return;
                    } else {
                        if (req.getParameterValues("productForDelete") != null) {
                            String[] productsID = req.getParameterValues("productForDelete");
                            for (String productID : productsID) {
                                logger.info("User=" + user.getNickname() + " delete product from his basket");
                                orderService.deleteProductFromBasket(user, Integer.parseInt(productID.trim()));
                            }
                        } else {
                            logger.info("User=" + user.getNickname() + " chose nothing");
                            req.setAttribute("nullData", "");
                        }
                    }
                }
            }
        } catch (
                Exception e) {
            logger.error(e);
        }
        doGet(req, resp);
    }
}
