package group1.se2project;


import se2project.model.MainCategory;
import se2project.repository.MainCategoryRepository;
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
public class MainCategoryRepositoryTest {
    @Autowired
    private MainCategoryRepository repos;
    @Test
    public void TestAddNewCategory(){
        MainCategory mainCategory = new MainCategory();
        mainCategory.setName("laptop");
        MainCategory saveMainCategory =repos.save(mainCategory);
        Assertions.assertTrue(saveMainCategory != null);
        Assertions.assertTrue(saveMainCategory.getId() > 0);
    }
    @Test
    public void TestListAll(){
        List<MainCategory> mainCategories = repos.findAll();
        Assertions.assertTrue(mainCategories.size() >0);
        for (MainCategory maincat:mainCategories
             ) {
            System.out.println(maincat);

        }
    }
    @Test
    public void TestUpdate() {
        Integer id = 1;
        Optional<MainCategory> optionalMainCategory = repos.findById(Long.valueOf(id));
        MainCategory mainCategory = optionalMainCategory.get();
        mainCategory.setName("mouse");
        repos.save(mainCategory);
        MainCategory updateMaincategory = repos.findById(Long.valueOf(id)).get();
        Assertions.assertTrue(updateMaincategory.getName().equals("mouse"));
    }
    @Test
    public void TestGet(){
        int id = 1;
        Optional<MainCategory> optionalMainCategory = repos.findById(Long.valueOf(id));
        Assertions.assertTrue(optionalMainCategory.isPresent());
        System.out.println(optionalMainCategory.get());
    }
    @Test
    public void TestDelete(){
        int id = 1;
        repos.deleteById(Long.valueOf(id));
        Optional<MainCategory> optionalMainCategory = repos.findById(Long.valueOf(id));
        Assertions.assertTrue(optionalMainCategory.isPresent() == false);
    }
}
