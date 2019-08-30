package app.controller.dao;

import app.model.products.Product;
import app.model.products.ProductOrder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

public class ProductOrderDao extends AbstractDao<ProductOrder> {

    public ProductDao productDao = new ProductDao();


    @Override
    public boolean add(ProductOrder productOrder) throws SQLException {
        String sql = "INSERT INTO PRODUCTS_ORDERS (PRODUCT_ID, ORDER_ID, COUNT) Values (?, ?, ?)";
        PreparedStatement ps = getPreparedStatement(sql);
        ps.setInt(1, productOrder.getProductId());
        ps.setInt(2, productOrder.getOrderId());
        ps.setInt(3, productOrder.getCount());
        ps.executeUpdate();
        return true;
    }

    @Override
    public List<ProductOrder> getAll() {
        return getResultList("SELECT * FROM PRODUCTS_ORDERS");
    }

    @Override
    public void update(ProductOrder productOrder) throws SQLException {
        String sql = "UPDATE PRODUCTS_ORDERS SET COUNT = ? WHERE PRODUCT_ID = ? AND ORDER_ID = ?";
        PreparedStatement ps = getPreparedStatement(sql);
        ps.setInt(1, productOrder.getCount());
        ps.setInt(2, productOrder.getProductId());
        ps.setInt(3, productOrder.getOrderId());
        ps.executeUpdate();
    }

    @Override
    public void delete(ProductOrder productOrder) throws SQLException {
        String sql = "DELETE FROM PRODUCTS_ORDERS WHERE PRODUCT_ID = ? AND ORDER_ID = ?";
        PreparedStatement ps = getPreparedStatement(sql);
        ps.setInt(1,productOrder.getProductId());
        ps.setInt(2,productOrder.getOrderId());
        ps.executeUpdate();
    }

    @Override
    protected List<ProductOrder> parseResultSet(ResultSet resultSet) {
        try {
            List<ProductOrder> list = new ArrayList<>();
            while (resultSet.next()) {
                ProductOrder productOrder = new ProductOrder();
                productOrder.setProductId(resultSet.getInt("PRODUCT_ID"));
                productOrder.setOrderId(resultSet.getInt("ORDER_ID"));
                productOrder.setCount(resultSet.getInt("COUNT"));
                list.add(productOrder);
            }
            return list;
        } catch (SQLException e) {
            logger.error(e);
            return emptyList();
        }
    }

    public List<Product> findProductsByOrderId(int orderId){
        List<Product> products = new ArrayList<>();
        List<ProductOrder> list = findBy("ORDER_ID="+orderId);
        for(ProductOrder productOrder : list){
            Product product = productDao.getProduct(productOrder.getProductId());
            product.setCount(productOrder.getCount());
            products.add(product);
        }
        return products;
    }




    @Override
    protected String tableName() {
        return "PRODUCTS_ORDERS";
    }
}
