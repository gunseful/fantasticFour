package app.controller.service;

import app.controller.dao.ProductDao;
import app.model.products.Product;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    private ProductDao productDao = new ProductDao();

    @Override
    public void deleteProduct(int id) {
        Product product = productDao.findById(id);
        productDao.delete(product);
    }

    @Override
    public void addNewProduct(String name, int price) {
        productDao.add(new Product(name, price));
    }

    @Override
    public List<Product> getList() {
        return productDao.getAll();
    }
}
