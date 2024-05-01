package se2project.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import se2project.model.MainCategory;
import se2project.model.Product;
import se2project.model.SubCategory;
import se2project.repository.MainCategoryRepository;
import se2project.repository.ProductRepository;
import se2project.repository.SubCategoryRepository;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/admin/product")
public class ProductController {
    private MainCategoryRepository mainCategoryRepository;
    private SubCategoryRepository subCategoryRepository;
    private ProductRepository productRepository;

    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/images";

    public ProductController(MainCategoryRepository mainCategoryRepository, SubCategoryRepository subCategoryRepository, ProductRepository productRepository) {
        this.mainCategoryRepository = mainCategoryRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.productRepository = productRepository;
    }

    @RequestMapping(value = "/list")
    public String viewAllProduct(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Page<Product> page =productRepository.findAllByOrderByProductIdDesc(PageRequest.of(p.orElse(0), 10));

        model.addAttribute("products", page.getContent());
        model.addAttribute("pagelist",page);
        return "productList";
    }

    //Sort
    @RequestMapping(value = "/sort/price/asc")
    public String sortProductByPriceAsc(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Page<Product> page =productRepository.findAllByOrderByPriceAsc(PageRequest.of(p.orElse(0), 10));

        model.addAttribute("products", page.getContent());
        model.addAttribute("pagelist", page);
        return "productList";
    }

    @RequestMapping(value = "/sort/price/desc")
    public String sortProductByPriceDesc(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Page<Product> page =productRepository.findAllByOrderByPriceDesc(PageRequest.of(p.orElse(0), 10));

        model.addAttribute("products", page.getContent());
        model.addAttribute("pagelist",page);
        return "productList";
    }

    @RequestMapping(value = "/sort/name/asc")
    public String sortProductByProductNameAsc(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Page<Product> page =productRepository.findAllByOrderByProductNameAsc(PageRequest.of(p.orElse(0), 10));

        model.addAttribute("products", page.getContent());
        model.addAttribute("pagelist",page);
        return "productList";
    }

    @RequestMapping(value = "/sort/name/desc")
    public String sortProductByProductNameDesc(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Page<Product> page =productRepository.findAllByOrderByProductNameDesc(PageRequest.of(p.orElse(0), 10));

        model.addAttribute("products", page.getContent());
        model.addAttribute("pagelist",page);
        return "productList";
    }

    @RequestMapping(value = "/sort/color/asc")
    public String sortProductByColorAsc(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Page<Product> page =productRepository.findAllByOrderByColorAsc(PageRequest.of(p.orElse(0), 10));

        model.addAttribute("products", page.getContent());
        model.addAttribute("pagelist",page);
        return "productList";
    }

    @RequestMapping(value = "/sort/color/desc")
    public String sortProductByColorDesc(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Page<Product> page =productRepository.findAllByOrderByColorDesc(PageRequest.of(p.orElse(0), 10));

        model.addAttribute("products", page.getContent());
        model.addAttribute("pagelist",page);
        return "productList";
    }

    //Search
    @RequestMapping(value = "/search")
    public String searchProduct(Model model, @RequestParam(value = "page") Optional<Integer> p, @RequestParam(value = "keyword", required = false)String keyword) {
        Page<Product> page =productRepository.findByProductNameContaining(keyword ,PageRequest.of(p.orElse(0), 10));

        model.addAttribute("products",page.getContent());
        model.addAttribute("pagelist",page);
        return "productList";
    }

    @RequestMapping(value = "/{id}")
    public String getProductById(@PathVariable(value = "id") Long productId, Model model){
        Product product = productRepository.getById(productId);

        model.addAttribute("product", product);
        return "productDetail";
    }
    @RequestMapping(value = "/add")
    public String addProduct(Model model){
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();
        List<MainCategory> mainCategoryList = mainCategoryRepository.findAll();

        model.addAttribute("subCategoryList", subCategoryList);
        model.addAttribute("mainCategoryList", mainCategoryList);
        model.addAttribute("product", new Product());
        return "productAdd";
    }

    @RequestMapping(value = "/update/{id}")
    public String updateProduct(@PathVariable(value = "id") Long productId,Model model){
        Product product = productRepository.getById(productId);
        List<SubCategory> subCategoryList = subCategoryRepository.findAll();

        model.addAttribute("subCategoryList",subCategoryList);
        model.addAttribute(product);
        return "productUpdate";
    }

    @RequestMapping(value = "/save")
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

    @RequestMapping(value = "/delete/{id}")
    public String deleteProduct(@PathVariable(value = "id") Long productId){
        Product product = productRepository.getById(productId);
        productRepository.delete(product);
        return "redirect:/admin/product/list";
    }

}
