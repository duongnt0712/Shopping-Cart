package se2project.controller;

import se2project.GlobalData.GlobalData;
import se2project.model.MainCategory;
import se2project.model.Product;
import se2project.model.SubCategory;
import se2project.repository.MainCategoryRepository;
import se2project.repository.ProductRepository;
import se2project.repository.SubCategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
//@RequestMapping(value = "/user")
public class UserController {
    MainCategoryRepository mainCategoryRepository;
    SubCategoryRepository subCategoryRepository;
    ProductRepository productRepository;

    public UserController(MainCategoryRepository mainCategoryRepository, SubCategoryRepository subCategoryRepository, ProductRepository productRepository) {
        this.mainCategoryRepository = mainCategoryRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.productRepository = productRepository;
    }

    @GetMapping(value = {"/", "/home"})
    public String home(Model model) {
        List<MainCategory> mainCategories = mainCategoryRepository.findAll();
        model.addAttribute("ListCat", mainCategories);
        List<Product> products = new ArrayList<>();
        MainCategory mainCategory = mainCategoryRepository.findMainCategoryByName("OTHERS");
        List<SubCategory> subCategories = subCategoryRepository.findByMainCategoryEquals(mainCategory);
        for (SubCategory subCat : subCategories) {
            products.addAll(productRepository.findBySubCategoryEquals(subCat));
        }
        model.addAttribute("products", products);
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "index";
    }
    @GetMapping(value="success")
    public String successPage(){
        return "success";
    }
    @GetMapping(value="comingsoon")
    public String comingSoon(){
        return "comingsoon";
    }

    @GetMapping(value = "/shop")
    public String getCategoryAndProduct(Model model){
        List<Product> products = productRepository.findAll();
        List<MainCategory> mainCategories = mainCategoryRepository.findAll();
        List<Map<MainCategory, List<SubCategory>>> listCat = new ArrayList<Map<MainCategory, List<SubCategory>>>();
        for (MainCategory m: mainCategories ) {
            listCat.add(new HashMap<MainCategory, List<SubCategory>>(){{
                put(m,subCategoryRepository.findByMainCategoryEquals(m));
            }});
        }
        model.addAttribute("products", products);
        model.addAttribute("ListCat", listCat);
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "shop";
    }
    @GetMapping(value = "/shop/maincategory/{name}")
    public String filterByMaincategory(Model model, @PathVariable(value = "name") String name){
        List<MainCategory> mainCategoryList = mainCategoryRepository.findAll();
        List<Map<MainCategory, List<SubCategory>>> ListCat = new ArrayList<Map<MainCategory, List<SubCategory>>>();
        for (MainCategory m : mainCategoryList ) {
            ListCat.add(new HashMap<MainCategory, List<SubCategory>>(){{
                put(m,subCategoryRepository.findByMainCategoryEquals(m));
            }});
        }
        List<Product> products = new ArrayList<>();
        MainCategory mainCategory = mainCategoryRepository.findMainCategoryByName(name);
        List<SubCategory> subCategoryList = subCategoryRepository.findByMainCategoryEquals(mainCategory);
        for (SubCategory subCat: subCategoryList) {
            products.addAll(productRepository.findBySubCategoryEquals(subCat));
        }

        model.addAttribute("ListCat", ListCat);
        model.addAttribute("products", products);
        model.addAttribute("cateName", name);
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "shop";
    }
    @GetMapping(value = "/shop/subcategory/{id}")
    public String filterBySubcategory(Model model, @PathVariable(value = "id") Long id ){
        SubCategory subCategory = subCategoryRepository.findSubCategoryById(id);
        List<Product> products = productRepository.findBySubCategoryEquals(subCategory);
        List<MainCategory> mainCategoryList = mainCategoryRepository.findAll();
        List<Map<MainCategory, List<SubCategory>>> ListCat = new ArrayList<Map<MainCategory, List<SubCategory>>>();
        for (MainCategory m: mainCategoryList ) {
            ListCat.add(new HashMap<MainCategory, List<SubCategory>>(){{
                put(m,subCategoryRepository.findByMainCategoryEquals(m));
            }});
        }

        model.addAttribute("ListCat", ListCat);
        model.addAttribute("products", products);
        model.addAttribute("cateName", subCategory.getName());
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "shop";
    }

    @GetMapping(value = "/shop/viewproduct/{id}")
    public String viewProduct(Model model, @PathVariable Long id) {
        model.addAttribute("categories", mainCategoryRepository.findAll());
        model.addAttribute("product", productRepository.getById(id));
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "productDetail";
    }
}
