package se2project.repository;


import se2project.model.MainCategory;
import se2project.model.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    Page<SubCategory> findByMainCategoryEquals(MainCategory mainCategory, Pageable pageable);
    Page<SubCategory> findByNameContaining(String name, Pageable pageable);
    List<SubCategory> findByMainCategoryEquals(MainCategory mainCategory);
    SubCategory findSubCategoryByName(String name);
    SubCategory findSubCategoryById(Long id);
}
