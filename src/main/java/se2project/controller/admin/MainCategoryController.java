package se2project.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se2project.model.MainCategory;
import se2project.model.SubCategory;
import se2project.repository.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/admin/maincategory")
public class MainCategoryController {

    private MainCategoryRepository mainCategoryRepository;
    private SubCategoryRepository subCategoryRepository;

    public MainCategoryController(MainCategoryRepository mainCategoryRepository, SubCategoryRepository subCategoryRepository) {
        this.mainCategoryRepository = mainCategoryRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    @GetMapping(value = "/list")
    public String getMainCategory(Model model, @RequestParam(value = "page") Optional<Integer> p){
        Page<MainCategory> page = mainCategoryRepository.findMainCategoryByOrderByIdDesc(PageRequest.of(p.orElse(0), 10));

        model.addAttribute("mainCategoryList", page.getContent());
        model.addAttribute("pagelist",page);
        return "maincategoryList";
    }

    @RequestMapping(value = "/detail/{id}")
    public String getMainCategoryById(@PathVariable(value = "id") Long id, Model model, @RequestParam(value = "page")Optional<Integer> p){
        MainCategory mainCategory = mainCategoryRepository.getById(id);
        Page<SubCategory> page = subCategoryRepository.findByMainCategoryEquals(mainCategory, PageRequest.of(p.orElse(0), 10));
        List<MainCategory> mainCategoryList = mainCategoryRepository.findAll();

        model.addAttribute("subCatList", page.getContent());
        model.addAttribute("mainCategory", mainCategory);
        model.addAttribute("mainCategoryList", mainCategoryList);
        model.addAttribute("pagelist",page);
        return "maincategoryDetail";
    }

    @RequestMapping(value = "/add")
    public String addMainCategory (Model model) {
        model.addAttribute("maincategory", new MainCategory());
        return "maincategoryAdd";
    }
    @RequestMapping(value = "/save")
    public String saveUpdate(@RequestParam(value = "id", required = false) Long id,  MainCategory mainCategory) {
        mainCategory.setId(id);
        mainCategoryRepository.save(mainCategory);
        return "redirect:/admin/maincategory/list";
    }
    @RequestMapping(value = "/update/{id}")
    public String updateMainCategory(@PathVariable (value = "id") Long id, Model model)  {
        MainCategory mainCategory = mainCategoryRepository.getById(id);
        List<MainCategory> mainCategoryList = mainCategoryRepository.findAll();

        model.addAttribute("maincategoryList", mainCategoryList);
        model.addAttribute(mainCategory);
        return "maincategoryUpdate";
    }

    @RequestMapping(value ="/delete/{id}")
    public String deleteMainCategory(@PathVariable(value = "id") Long id){
        MainCategory mainCategory = mainCategoryRepository.getById(id);
        mainCategoryRepository.delete(mainCategory);
        return "redirect:/admin/maincategory/list";
    }
    @GetMapping (value = "/search")
    public String searchMaincategory(@RequestParam(value = "name") String name, Model model, @RequestParam(value = "page")Optional<Integer> p){
        Page<MainCategory> page = mainCategoryRepository.findByNameContaining(name, PageRequest.of(p.orElse(0), 10));
        model.addAttribute("mainCategoryList", page.getContent());
        model.addAttribute("pagelist",page);
        return "maincategoryList";
    }
}
