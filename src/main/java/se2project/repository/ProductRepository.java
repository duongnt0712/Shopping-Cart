package se2project.repository;


import se2project.model.Product;
import se2project.model.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //relative search
    Page<Product> findByProductNameContaining (String productName, Pageable pageable);
    Page<Product> findAllByOrderByProductIdDesc(Pageable pageable);
    Page<Product> findAllByOrderByPriceAsc(Pageable pageable);
    Page<Product> findAllByOrderByPriceDesc(Pageable pageable);
    Page<Product> findAllByOrderByProductNameAsc(Pageable pageable);
    Page<Product> findAllByOrderByProductNameDesc(Pageable pageable);
    Page<Product> findAllByOrderByColorAsc(Pageable pageable);
    Page<Product> findAllByOrderByColorDesc(Pageable pageable);
    Page<Product> findBySubCategoryEquals(SubCategory subCategory, Pageable pageable);
    List<Product> findBySubCategoryEquals(SubCategory subCategory);
}
