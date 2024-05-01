package se2project.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se2project.model.MainCategory;
import se2project.model.Product;
import se2project.model.SubCategory;
import se2project.repository.MainCategoryRepository;
import se2project.repository.ProductRepository;
import se2project.repository.SubCategoryRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/admin/subcategory")
public class SubCategoryController {
    private MainCategoryRepository mainCategoryRepository;
    private SubCategoryRepository subCategoryRepository;
    private ProductRepository productRepository;

    public SubCategoryController(MainCategoryRepository mainCategoryRepository, SubCategoryRepository subCategoryRepository, ProductRepository productRepository) {
        this.mainCategoryRepository = mainCategoryRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.productRepository = productRepository;
    }

    @GetMapping(value = "/list")
    public String getSubCategoryList(Model model, @RequestParam(value = "page") Optional<Integer> p){
        Page<SubCategory> page = subCategoryRepository.findSubCategoryByOrderByIdDesc(PageRequest.of(p.orElse(0), 10));

        model.addAttribute("subCategoryList", page.getContent());
        model.addAttribute("pagelist", page);
        return "subcategoryList";
    }
    @GetMapping(value = "/detail/{id}")
    public String getSubCategoryDetail(@PathVariable(value = "id") Long id, Model model, @RequestParam(value = "page")Optional<Integer> p){
        SubCategory subCategory = subCategoryRepository.getById(id);
        Page<Product> page = productRepository.findBySubCategoryEquals(subCategory, PageRequest.of(p.orElse(0), 20));

        model.addAttribute("products", page.getContent());
        model.addAttribute("pagelist",page);
        model.addAttribute("subCategory",subCategory);
        return "subcategoryDetail";
    }
    @GetMapping(value = "/add")
    public String addSubCategory(Model model){
        SubCategory subCategory = new SubCategory();
        List<MainCategory> mainCategoryList = mainCategoryRepository.findAll();

        model.addAttribute("mainCategoryList",mainCategoryList);
        model.addAttribute(subCategory);
        return "subcategoryAdd";
    }
    @PostMapping(value = "/saveadd")
    public String SaveAddSubCategory(SubCategory subCategory){
        subCategoryRepository.save(subCategory);
        return "redirect:/admin/subcategory/list";
    }
    @GetMapping(value = "/update/{id}")
    public String updateSubCategory(@PathVariable(value = "id") Long id, Model model){
        SubCategory subCategory = subCategoryRepository.getById(id);
        List<MainCategory> mainCategoryList = mainCategoryRepository.findAll();

        model.addAttribute("subCategory",subCategory);
        model.addAttribute("mainCategoryList",mainCategoryList);
        return "subcategoryUpdate";
    }
    @PostMapping(value = "/saveupdate")
    public String saveUpdateSubCategory(SubCategory subCategory){
        subCategoryRepository.save(subCategory);
        return "redirect:/admin/subcategory/list";
    }
    @RequestMapping(value = "/delete/{id}")
    public String deleteSubCategory(@PathVariable(value = "id") Long id){
        SubCategory subCategory = subCategoryRepository.getById(id);
        subCategoryRepository.delete(subCategory);
        return "redirect:/admin/subcategory/list";
    }
    @GetMapping (value = "/search")
    public String searchSubCategory(@RequestParam(value = "name") String name, Model model, @RequestParam(value = "page") Optional<Integer> p){
        Page<SubCategory> page = subCategoryRepository.findByNameContaining(name, PageRequest.of(p.orElse(0), 10));

        model.addAttribute("subCategoryList", page.getContent());
        model.addAttribute("pagelist",page);
        return "subcategoryList";
    }
}
