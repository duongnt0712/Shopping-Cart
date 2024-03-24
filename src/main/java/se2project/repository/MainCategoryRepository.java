package se2project.repository;


import se2project.model.MainCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MainCategoryRepository extends JpaRepository<MainCategory, Long> {
    Page<MainCategory> findByNameContaining(String name, Pageable pageable);
    Page<MainCategory> findMainCategoryByOrderByIdDesc(Pageable pageable);
    MainCategory findMainCategoryByName(String name);
}
