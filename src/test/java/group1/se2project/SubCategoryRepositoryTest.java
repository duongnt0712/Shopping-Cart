package group1.se2project;


import se2project.model.SubCategory;
import se2project.repository.SubCategoryRepository;
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
public class SubCategoryRepositoryTest {
    @Autowired
    private SubCategoryRepository repos;
    @Test
    public void TestAddNewCategory(){
        SubCategory subCategory = new SubCategory();
        subCategory.setName("laptop");
        SubCategory saveSubCategory =repos.save(subCategory);
        Assertions.assertTrue(saveSubCategory != null);
        Assertions.assertTrue(saveSubCategory.getId() > 0);
    }
    @Test
    public void TestListAll(){
        List<SubCategory> subCategories = repos.findAll();
        Assertions.assertTrue(subCategories.size() >0);
        for (SubCategory subcat:subCategories
        ) {
            System.out.println(subcat);

        }
    }
    @Test
    public void TestUpdate() {
        Integer id = 2;
        Optional<SubCategory> optionalSubCategory = repos.findById(Long.valueOf(id));
        SubCategory subCategory = optionalSubCategory.get();
        subCategory.setName("m");
        repos.save(subCategory);
        SubCategory updateSubcategory = repos.findById(Long.valueOf(id)).get();
        Assertions.assertTrue(updateSubcategory.getName().equals("m"));
    }
    @Test
    public void TestGet(){
        int id = 2;
        Optional<SubCategory> optionalSubCategory = repos.findById(Long.valueOf(id));
        Assertions.assertTrue(optionalSubCategory.isPresent());
        System.out.println(optionalSubCategory.get());
    }
    @Test
    public void TestDelete(){
        Integer id = 3;
        repos.deleteById(Long.valueOf(id));
        Optional<SubCategory> optionalSubCategory = repos.findById(Long.valueOf(id));
        Assertions.assertTrue(optionalSubCategory.isPresent() == false);
    }
}
