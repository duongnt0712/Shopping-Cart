package se2project.controller;

import se2project.model.MainCategory;
import se2project.model.Product;
import se2project.model.SubCategory;
import se2project.repository.MainCategoryRepository;
import se2project.repository.ProductRepository;
import se2project.repository.SubCategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private MainCategoryRepository mainCategoryRepository;
    private SubCategoryRepository subCategoryRepository;
    private ProductRepository productRepository;

    public AdminController(MainCategoryRepository mainCategoryRepository, SubCategoryRepository subCategoryRepository, ProductRepository productRepository) {
        this.mainCategoryRepository = mainCategoryRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.productRepository = productRepository;
    }

    // MAIN CATEGORY
    @GetMapping(value = "")
    public String adminHome(Model model) {
        return "adminHome";
    }
    @GetMapping(value = "/maincategory/list")
    public String getMainCategory(Model model, @RequestParam(value = "page")Optional<Integer> p){
        Pageable pageable =  PageRequest.of(p.orElse(0), 10);
        Page<MainCategory> page = mainCategoryRepository.findAll(pageable);
        model.addAttribute("mainCategoryList", page.getContent());
        model.addAttribute("pagelist",page);
        return "maincategoryList";
    }
    @RequestMapping(value = "/maincategory/detail/{id}")
    public String getMainCategoryById(@PathVariable(value = "id") Long id,
                                      Model model,  @RequestParam(value = "page")Optional<Integer> p){
        MainCategory mainCategory = mainCategoryRepository.getById(id);
        Pageable pageable =  PageRequest.of(p.orElse(0), 10);
        Page<SubCategory> page = subCategoryRepository.findByMainCategoryEquals(mainCategory, pageable);
        model.addAttribute("subCatList", page.getContent());
        List<MainCategory> mainCategoryList = mainCategoryRepository.findAll();
        model.addAttribute("mainCategory", mainCategory);
        model.addAttribute("mainCategoryList", mainCategoryList);
        model.addAttribute("pagelist",page);
        return "maincategoryDetail";
    }
    @RequestMapping(value = "/maincategory/add")
    public String addMainCategory (Model model) {
        MainCategory mainCategory = new MainCategory();
        model.addAttribute("maincategory", mainCategory);
        return "maincategoryAdd";
    }
    @RequestMapping(value = "/maincategory/save")
    public String saveUpdate(
            @RequestParam(value = "id", required = false) Long id,  MainCategory mainCategory)
    {
        mainCategory.setId(id);
        mainCategoryRepository.save(mainCategory);
        return "redirect:/admin/maincategory/list";
    }
    @RequestMapping(value = "/maincategory/update/{id}")
    public String updateMainCategory(
            @PathVariable (value = "id") Long id, Model model)  {
        MainCategory mainCategory = mainCategoryRepository.getById(id);
        List<MainCategory> mainCategoryList = mainCategoryRepository.findAll();
        model.addAttribute("maincategoryList", mainCategoryList);
        model.addAttribute(mainCategory);
        return "maincategoryUpdate";
    }

    @RequestMapping(value ="/maincategory/delete/{id}")
    public String deleteMainCategory(@PathVariable(value = "id") Long id){
        MainCategory mainCategory = mainCategoryRepository.getById(id);
        mainCategoryRepository.delete(mainCategory);
        return "redirect:/admin/maincategory/list";
    }
    @GetMapping (value = "/maincategory/search")
    public String searchMaincategory(@RequestParam(value = "name") String name,Model model,
                                     @RequestParam(value = "page")Optional<Integer> p){
        Pageable pageable = PageRequest.of(p.orElse(0), 10);
        Page<MainCategory> page = mainCategoryRepository.findByNameContaining(name, pageable);
        model.addAttribute("mainCategoryList", page.getContent());
        model.addAttribute("pagelist",page);
        return "maincategoryList";
    }


    //............................SUB CATEGORY......................................//
    @GetMapping(value = "/subcategory/list")
    public String getSubCategoryList(Model model, @RequestParam(value = "page") Optional<Integer> p){
        Pageable pageable =  PageRequest.of(p.orElse(0), 10);
        Page<SubCategory> page = subCategoryRepository.findAll(pageable);
        model.addAttribute("subCategoryList", page.getContent());
        model.addAttribute("pagelist",page);
        return "subcategoryList";
    }
    @GetMapping(value = "/subcategory/detail/{id}")
    public String getSubCategoryDetail(@PathVariable(value = "id") Long id,
                                       Model model,
                                       @RequestParam(value = "page")Optional<Integer> p){
        SubCategory subCategory = subCategoryRepository.getById(id);
        Pageable pageable =  PageRequest.of(p.orElse(0), 20);
        Page<Product> page = productRepository.findBySubCategoryEquals(subCategory, pageable);
        model.addAttribute("products", page.getContent());
        model.addAttribute("pagelist",page);
        // get subcatgory id
        model.addAttribute("subCategory",subCategory);
        return "subcategoryDetail";
    }
    @GetMapping(value = "/subcategory/add")
    public String addSubCategory(Model model){
        SubCategory subCategory = new SubCategory();
        List<MainCategory> mainCategoryList = mainCategoryRepository.findAll();
        model.addAttribute("mainCategoryList",mainCategoryList);
        model.addAttribute(subCategory);
        return "subcategoryAdd";
    }
    @PostMapping(value = "/subcategory/saveadd")
    public String SaveAddSubCategory(SubCategory subCategory){
        subCategoryRepository.save(subCategory);
        return "redirect:/admin/subcategory/list";
    }
    @GetMapping(value = "/subcategory/update/{id}")
    public String updateSubCategory(@PathVariable(value = "id") Long id, Model model){
        SubCategory subCategory = subCategoryRepository.getById(id);
        model.addAttribute("subCategory",subCategory);
        List<MainCategory> mainCategoryList = mainCategoryRepository.findAll();
        model.addAttribute("mainCategoryList",mainCategoryList);
        return "subcategoryUpdate";
    }
    @PostMapping(value = "/subcategory/saveupdate")
    public String saveUpdateSubCategory(SubCategory subCategory){
        subCategoryRepository.save(subCategory);
        return "redirect:/admin/subcategory/list";
    }
    @RequestMapping(value = "/subcategory/delete/{id}")
    public String deleteSubCategory(@PathVariable(value = "id") Long id){
        SubCategory subCategory = subCategoryRepository.getById(id);
        subCategoryRepository.delete(subCategory);
        return "redirect:/admin/subcategory/list";
    }
    @GetMapping (value = "/subcategory/search")
    public String searchSubCategory(@RequestParam(value = "name") String name,
                                    Model model,
                                    @RequestParam(value = "page") Optional<Integer> p){
        Pageable pageable = PageRequest.of(p.orElse(0), 10);
        Page<SubCategory> page = subCategoryRepository.findByNameContaining(name, pageable);
        model.addAttribute("subCategoryList", page.getContent());
        model.addAttribute("pagelist",page);
        return "subcategoryList";
    }
    //........................................... PRODUCT..............................................//
//    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/images";

    @RequestMapping(value = "/product/list")
    public String viewAllProduct(Model model,
                                 @RequestParam(value = "page") Optional<Integer> p) {
        Pageable pageable =  PageRequest.of(p.orElse(0), 10);
        Page<Product> page =productRepository.findAll(pageable);
        model.addAttribute("products", page.getContent());
        model.addAttribute("pagelist",page);
        //return view
        return "productList";
    }

    //Sort
    @RequestMapping(value = "/product/sort/price/asc")
    public String sortProductByPriceAsc(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Pageable pageable =  PageRequest.of(p.orElse(0), 10);
        Page<Product> page =productRepository.findAllByOrderByPriceAsc(pageable);
        model.addAttribute("products", page.getContent());
        model.addAttribute("pagelist",page);
        return "productList";
    }

    @RequestMapping(value = "/product/sort/price/desc")
    public String sortProductByPriceDesc(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Pageable pageable =  PageRequest.of(p.orElse(0), 10);
        Page<Product> page =productRepository.findAllByOrderByPriceDesc(pageable);
        model.addAttribute("products", page.getContent());
        model.addAttribute("pagelist",page);
        return "productList";
    }

    @RequestMapping(value = "/product/sort/name/asc")
    public String sortProductByProductNameAsc(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Pageable pageable =  PageRequest.of(p.orElse(0), 10);
        Page<Product> page =productRepository.findAllByOrderByProductNameAsc(pageable);
        model.addAttribute("products", page.getContent());
        model.addAttribute("pagelist",page);
        return "productList";
    }

    @RequestMapping(value = "/product/sort/name/desc")
    public String sortProductByProductNameDesc(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Pageable pageable =  PageRequest.of(p.orElse(0), 10);
        Page<Product> page =productRepository.findAllByOrderByProductNameDesc(pageable);
        model.addAttribute("products", page.getContent());
        model.addAttribute("pagelist",page);
        return "productList";
    }

    @RequestMapping(value = "/product/sort/color/asc")
    public String sortProductByColorAsc(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Pageable pageable =  PageRequest.of(p.orElse(0), 10);
        Page<Product> page =productRepository.findAllByOrderByColorAsc(pageable);
        model.addAttribute("products", page.getContent());
        model.addAttribute("pagelist",page);
        return "productList";
    }

    @RequestMapping(value = "/product/sort/color/desc")
    public String sortProductByColorDesc(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Pageable pageable =  PageRequest.of(p.orElse(0), 10);
        Page<Product> page =productRepository.findAllByOrderByColorDesc(pageable);
        model.addAttribute("products", page.getContent());
        model.addAttribute("pagelist",page);
        return "productList";
    }

    //Search
    @RequestMapping(value = "/product/search")
    public String searchProduct(
            Model model,
            @RequestParam(value = "page") Optional<Integer> p,
            @RequestParam(value = "keyword", required = false)String keyword) {
//        List<Product> products = productRepository.findByProductNameContaining(keyword);
        Pageable pageable =  PageRequest.of(p.orElse(0), 10);
        Page<Product> page =productRepository.findByProductNameContaining(keyword ,pageable);
        model.addAttribute("products",page.getContent());
        model.addAttribute("pagelist",page);
        return "productList";
    }


    @RequestMapping(value = "/product/{id}")
    public String getProductById(
            @PathVariable(value = "id") Long productId,Model model){
        Product product = productRepository.getById(productId);
        model.addAttribute("product", product);
        return "productDetail";
    }
    @RequestMapping(value = "/product/add")
    public String addProduct(Model model){
        Product product = new Product();
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        List<MainCategory> mainCategoryList = mainCategoryRepository.findAll();
        model.addAttribute("subCategoryList", subCategoryList);
        model.addAttribute("mainCategoryList", mainCategoryList);
        model.addAttribute("product", product);
        return "productAdd";
    }

    @RequestMapping(value = "/product/update/{id}")
    public String updateProduct(
            @PathVariable(value = "id") Long productId,Model model){
        Product product = productRepository.getById(productId);
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        model.addAttribute("subCategoryList",subCategoryList);
        model.addAttribute(product);
        return "productUpdate";
    }

    @RequestMapping(value = "/product/save")
    public String saveProduct(
            @RequestParam(value = "productId", required = false)Long productId,
            @RequestParam("productImage") MultipartFile file,
            @RequestParam("imgName") String  imgName,
            @Valid Product product, BindingResult result) throws IOException {
        if( result.hasErrors()){
            return "productAdd";
        }
        String imageUUID;
        String urlDir = "E:\\TienProjectSE2\\Se2prj_group3\\se2project-main\\src\\main\\resources\\static\\images";
        if(!file.isEmpty()){
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(urlDir, imageUUID);
            Files.createFile(fileNameAndPath);
            Files.write(fileNameAndPath, file.getBytes());
        } else {
            imageUUID = imgName;
        }

        product.setImageName(imageUUID);
        product.setProductId(productId);
        productRepository.save(product);

        return "redirect:/admin/product/list";
    }

    @RequestMapping(value = "/product/delete/{id}")
    public String deleteProduct(
            @PathVariable(value = "id") Long productId){
        Product product = productRepository.getById(productId);
        productRepository.delete(product);
        return "redirect:/admin/product/list";
    }
}
