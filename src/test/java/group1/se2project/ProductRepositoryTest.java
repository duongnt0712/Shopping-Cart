package group1.se2project;

import se2project.model.Product;
import se2project.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

    @DataJpaTest
    @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
    @Rollback(value = false)
    public class ProductRepositoryTest  {
        @Autowired
        private ProductRepository repos;
        @Test
        public void TestAddNewCategory(){
            Product product = new Product();
            product.setProductName("dell");
            product.setImageName("image");
            product.setPrice(1000);
            product.setDescription("laptop");
            Product saveProduct =repos.save(product);
            Assertions.assertTrue(saveProduct != null);
            Assertions.assertTrue(saveProduct.getProductId() > 0);
        }
        @Test
        public void TestListAll(){
            List<Product> products = repos.findAll();
            Assertions.assertTrue(products.size() >0);
            for (Product product:products
            ) {
                System.out.println(product);

            }
        }
        @Test
        public void TestUpdate() {
            Integer id = 9;
            Optional<Product> optionalProduct = repos.findById(Long.valueOf(id));
            Product product= optionalProduct.get();
            product.setProductName("asus");
            product.setImageName("image");
            product.setPrice(2000);
            product.setDescription("laptop");
            repos.save(product);
            Product updateProduct = repos.findById(Long.valueOf(id)).get();
            Assertions.assertTrue(updateProduct.getProductName().equals("asus"));
            Assertions.assertTrue(updateProduct.getImageName().equals("image"));
            Assertions.assertTrue(updateProduct.getPrice() == 2000);
            Assertions.assertTrue(updateProduct.getDescription().equals("laptop"));


        }
        @Test
        public void TestGet(){
            int id = 9;
            Optional<Product> optionalProduct = repos.findById(Long.valueOf(id));
            Assertions.assertTrue(optionalProduct.isPresent());
            System.out.println(optionalProduct.get());
        }
        @Test
        public void TestDelete(){
            int id = 9;
            repos.deleteById(Long.valueOf(id));
            Optional<Product> optionalProduct = repos.findById(Long.valueOf(id));
            Assertions.assertTrue(optionalProduct.isPresent() == false);
        }
    }

