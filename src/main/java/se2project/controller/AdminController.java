package se2project.controller;

import se2project.model.*;
import se2project.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private UserRepository userRepository;

    private RoleRepository roleRepository;

    public AdminController(MainCategoryRepository mainCategoryRepository, SubCategoryRepository subCategoryRepository,
                           ProductRepository productRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.mainCategoryRepository = mainCategoryRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping(value = "")
    public String adminHome(Model model) {
        return "adminHome";
    }

    // MAIN CATEGORY
    @GetMapping(value = "/maincategory/list")
    public String getMainCategory(Model model, @RequestParam(value = "page")Optional<Integer> p){
        Page<MainCategory> page = mainCategoryRepository.findMainCategoryByOrderByIdDesc(PageRequest.of(p.orElse(0), 10));

        model.addAttribute("mainCategoryList", page.getContent());
        model.addAttribute("pagelist",page);
        return "maincategoryList";
    }

    @RequestMapping(value = "/maincategory/detail/{id}")
    public String getMainCategoryById(@PathVariable(value = "id") Long id, Model model,  @RequestParam(value = "page")Optional<Integer> p){
        MainCategory mainCategory = mainCategoryRepository.getById(id);
        Page<SubCategory> page = subCategoryRepository.findByMainCategoryEquals(mainCategory, PageRequest.of(p.orElse(0), 10));
        List<MainCategory> mainCategoryList = mainCategoryRepository.findAll();

        model.addAttribute("subCatList", page.getContent());
        model.addAttribute("mainCategory", mainCategory);
        model.addAttribute("mainCategoryList", mainCategoryList);
        model.addAttribute("pagelist",page);
        return "maincategoryDetail";
    }

    @RequestMapping(value = "/maincategory/add")
    public String addMainCategory (Model model) {
        model.addAttribute("maincategory", new MainCategory());
        return "maincategoryAdd";
    }
    @RequestMapping(value = "/maincategory/save")
    public String saveUpdate(@RequestParam(value = "id", required = false) Long id,  MainCategory mainCategory) {
        mainCategory.setId(id);
        mainCategoryRepository.save(mainCategory);
        return "redirect:/admin/maincategory/list";
    }
    @RequestMapping(value = "/maincategory/update/{id}")
    public String updateMainCategory(@PathVariable (value = "id") Long id, Model model)  {
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
    public String searchMaincategory(@RequestParam(value = "name") String name, Model model, @RequestParam(value = "page")Optional<Integer> p){
        Page<MainCategory> page = mainCategoryRepository.findByNameContaining(name, PageRequest.of(p.orElse(0), 10));
        model.addAttribute("mainCategoryList", page.getContent());
        model.addAttribute("pagelist",page);
        return "maincategoryList";
    }

    //............................SUB CATEGORY......................................//
    @GetMapping(value = "/subcategory/list")
    public String getSubCategoryList(Model model, @RequestParam(value = "page") Optional<Integer> p){
        Page<SubCategory> page = subCategoryRepository.findSubCategoryByOrderByIdDesc(PageRequest.of(p.orElse(0), 10));

        model.addAttribute("subCategoryList", page.getContent());
        model.addAttribute("pagelist", page);
        return "subcategoryList";
    }
    @GetMapping(value = "/subcategory/detail/{id}")
    public String getSubCategoryDetail(@PathVariable(value = "id") Long id, Model model, @RequestParam(value = "page")Optional<Integer> p){
        SubCategory subCategory = subCategoryRepository.getById(id);
        Page<Product> page = productRepository.findBySubCategoryEquals(subCategory, PageRequest.of(p.orElse(0), 20));

        model.addAttribute("products", page.getContent());
        model.addAttribute("pagelist",page);
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
        List<MainCategory> mainCategoryList = mainCategoryRepository.findAll();

        model.addAttribute("subCategory",subCategory);
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
    public String searchSubCategory(@RequestParam(value = "name") String name, Model model, @RequestParam(value = "page") Optional<Integer> p){
        Page<SubCategory> page = subCategoryRepository.findByNameContaining(name, PageRequest.of(p.orElse(0), 10));

        model.addAttribute("subCategoryList", page.getContent());
        model.addAttribute("pagelist",page);
        return "subcategoryList";
    }

    //........................................... PRODUCT..............................................//
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/images";

    @RequestMapping(value = "/product/list")
    public String viewAllProduct(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Page<Product> page =productRepository.findAllByOrderByProductIdDesc(PageRequest.of(p.orElse(0), 10));

        model.addAttribute("products", page.getContent());
        model.addAttribute("pagelist",page);
        return "productList";
    }

    //Sort
    @RequestMapping(value = "/product/sort/price/asc")
    public String sortProductByPriceAsc(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Page<Product> page =productRepository.findAllByOrderByPriceAsc(PageRequest.of(p.orElse(0), 10));

        model.addAttribute("products", page.getContent());
        model.addAttribute("pagelist", page);
        return "productList";
    }

    @RequestMapping(value = "/product/sort/price/desc")
    public String sortProductByPriceDesc(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Page<Product> page =productRepository.findAllByOrderByPriceDesc(PageRequest.of(p.orElse(0), 10));

        model.addAttribute("products", page.getContent());
        model.addAttribute("pagelist",page);
        return "productList";
    }

    @RequestMapping(value = "/product/sort/name/asc")
    public String sortProductByProductNameAsc(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Page<Product> page =productRepository.findAllByOrderByProductNameAsc(PageRequest.of(p.orElse(0), 10));

        model.addAttribute("products", page.getContent());
        model.addAttribute("pagelist",page);
        return "productList";
    }

    @RequestMapping(value = "/product/sort/name/desc")
    public String sortProductByProductNameDesc(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Page<Product> page =productRepository.findAllByOrderByProductNameDesc(PageRequest.of(p.orElse(0), 10));

        model.addAttribute("products", page.getContent());
        model.addAttribute("pagelist",page);
        return "productList";
    }

    @RequestMapping(value = "/product/sort/color/asc")
    public String sortProductByColorAsc(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Page<Product> page =productRepository.findAllByOrderByColorAsc(PageRequest.of(p.orElse(0), 10));

        model.addAttribute("products", page.getContent());
        model.addAttribute("pagelist",page);
        return "productList";
    }

    @RequestMapping(value = "/product/sort/color/desc")
    public String sortProductByColorDesc(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Page<Product> page =productRepository.findAllByOrderByColorDesc(PageRequest.of(p.orElse(0), 10));

        model.addAttribute("products", page.getContent());
        model.addAttribute("pagelist",page);
        return "productList";
    }

    //Search
    @RequestMapping(value = "/product/search")
    public String searchProduct(Model model, @RequestParam(value = "page") Optional<Integer> p, @RequestParam(value = "keyword", required = false)String keyword) {
        Page<Product> page =productRepository.findByProductNameContaining(keyword ,PageRequest.of(p.orElse(0), 10));

        model.addAttribute("products",page.getContent());
        model.addAttribute("pagelist",page);
        return "productList";
    }

    @RequestMapping(value = "/product/{id}")
    public String getProductById(@PathVariable(value = "id") Long productId, Model model){
        Product product = productRepository.getById(productId);

        model.addAttribute("product", product);
        return "productDetail";
    }
    @RequestMapping(value = "/product/add")
    public String addProduct(Model model){
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        List<MainCategory> mainCategoryList = mainCategoryRepository.findAll();

        model.addAttribute("subCategoryList", subCategoryList);
        model.addAttribute("mainCategoryList", mainCategoryList);
        model.addAttribute("product", new Product());
        return "productAdd";
    }

    @RequestMapping(value = "/product/update/{id}")
    public String updateProduct(@PathVariable(value = "id") Long productId,Model model){
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
        String urlDir = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\images";
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
    public String deleteProduct(@PathVariable(value = "id") Long productId){
        Product product = productRepository.getById(productId);
        productRepository.delete(product);
        return "redirect:/admin/product/list";
    }

    //........................................... USER ..............................................//
    @RequestMapping(value = "/user/list")
    public String viewAllUsers(Model model, @RequestParam(value = "page") Optional<Integer> u) {
        Page<User> page =userRepository.findAllByOrderByIdDesc(PageRequest.of(u.orElse(0), 10));

        model.addAttribute("users", page.getContent());
        model.addAttribute("pagelist",page);
        return "userList";
    }

    @RequestMapping(value = "/user/add")
    public String addUser(Model model){
        List<Role> roles = roleRepository.findAll();

        model.addAttribute("roleList", roles);
        model.addAttribute("user", new User());
        return "userAdd";
    }

    @RequestMapping(value = "/user/update/{id}")
    public String updateUser(@PathVariable(value = "id") Integer userId, Model model){
        User user = userRepository.getById(userId);
        model.addAttribute(user);
        return "userUpdate";
    }

    @RequestMapping(value = "/user/save")
    public String saveUser(@RequestParam(value = "id", required = false)Integer userId, @Valid User user, BindingResult result) {
        if( result.hasErrors()){
            return "userAdd";
        }
        if (userId != null) {
            User defaultUser = userRepository.getById(userId);
            defaultUser.setFirstName(user.getFirstName());
            defaultUser.setLastName(user.getLastName());
            defaultUser.setEmail(user.getEmail());
            userRepository.save(defaultUser);
        } else {
            userRepository.save(user);
        }
        return "redirect:/admin/user/list";
    }

    @RequestMapping(value = "/user/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") Integer userId){
        User user = userRepository.getById(userId);
        userRepository.delete(user);
        return "redirect:/admin/user/list";
    }

    //Sort
    @RequestMapping(value = "/user/sort/first-name/asc")
    public String sortUserByFirstNameAsc(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Page<User> page =userRepository.findAllByOrderByFirstNameAsc(PageRequest.of(p.orElse(0), 10));

        model.addAttribute("users", page.getContent());
        model.addAttribute("pagelist", page);
        return "userList";
    }

    @RequestMapping(value = "/user/sort/first-name/desc")
    public String sortUserByFirstNameDesc(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Page<User> page =userRepository.findAllByOrderByFirstNameDesc(PageRequest.of(p.orElse(0), 10));

        model.addAttribute("users", page.getContent());
        model.addAttribute("pagelist",page);
        return "userList";
    }

    @RequestMapping(value = "/user/sort/last-name/asc")
    public String sortUserByLastNameAsc(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Page<User> page =userRepository.findAllByOrderByLastNameAsc(PageRequest.of(p.orElse(0), 10));

        model.addAttribute("users", page.getContent());
        model.addAttribute("pagelist",page);
        return "userList";
    }

    @RequestMapping(value = "/user/sort/last-name/desc")
    public String sortUserByLastNameDesc(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Page<User> page =userRepository.findAllByOrderByLastNameDesc(PageRequest.of(p.orElse(0), 10));

        model.addAttribute("users", page.getContent());
        model.addAttribute("pagelist",page);
        return "userList";
    }

    @RequestMapping(value = "/user/sort/email/asc")
    public String sortUserByEmailAsc(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Page<User> page =userRepository.findAllByOrderByEmailAsc(PageRequest.of(p.orElse(0), 10));

        model.addAttribute("users", page.getContent());
        model.addAttribute("pagelist",page);
        return "userList";
    }

    @RequestMapping(value = "/user/sort/email/desc")
    public String sortUserByEmailDesc(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Page<User> page =userRepository.findAllByOrderByEmailDesc(PageRequest.of(p.orElse(0), 10));

        model.addAttribute("users", page.getContent());
        model.addAttribute("pagelist",page);
        return "userList";
    }
}
