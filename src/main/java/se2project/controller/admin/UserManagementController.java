package se2project.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se2project.model.Role;
import se2project.model.User;
import se2project.repository.RoleRepository;
import se2project.repository.UserRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/admin/user")
public class UserManagementController {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserManagementController(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @RequestMapping(value = "/list")
    public String viewAllUsers(Model model, @RequestParam(value = "page") Optional<Integer> u) {
        Page<User> page =userRepository.findAllByOrderByIdDesc(PageRequest.of(u.orElse(0), 10));

        model.addAttribute("users", page.getContent());
        model.addAttribute("pagelist",page);
        return "userList";
    }

    @RequestMapping(value = "/search")
    public String searchUser(Model model, @RequestParam(value = "page") Optional<Integer> p, @RequestParam(value = "keyword", required = false) String keyword) {
        Page<User> page =userRepository.findByFirstNameContainingOrLastNameContainingOrEmailContaining(keyword, keyword, keyword ,PageRequest.of(p.orElse(0), 10));

        model.addAttribute("users",page.getContent());
        model.addAttribute("pagelist",page);
        return "userList";
    }

    @RequestMapping(value = "/{id}")
    public String getUserById(@PathVariable(value = "id") Integer userId, Model model){
        User user = userRepository.getById(userId);

        model.addAttribute("user", user);
        return "userDetail";
    }

    @RequestMapping(value = "/add")
    public String addUser(Model model){
        List<Role> roles = roleRepository.findAll();

        model.addAttribute("roleList", roles);
        model.addAttribute("user", new User());
        return "userAdd";
    }

    @RequestMapping(value = "/update/{id}")
    public String updateUser(@PathVariable(value = "id") Integer userId, Model model){
        User user = userRepository.getById(userId);
        model.addAttribute(user);
        return "userUpdate";
    }

    @RequestMapping(value = "/save")
    public String saveUser(@RequestParam(value = "id", required = false) Integer userId, @Valid User user, BindingResult result) {

        if (userId != null) {
            User defaultUser = userRepository.getById(userId);
            defaultUser.setFirstName(user.getFirstName());
            defaultUser.setLastName(user.getLastName());
            defaultUser.setEmail(user.getEmail());
            userRepository.save(defaultUser);
        } else {
            if( result.hasErrors()){
                return "userAdd";
            }
            String password = user.getPassword();
            user.setPassword(bCryptPasswordEncoder.encode(password));
            userRepository.save(user);
        }
        return "redirect:/admin/user/list";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") Integer userId){
        User user = userRepository.getById(userId);
        userRepository.delete(user);
        return "redirect:/admin/user/list";
    }

    //Sort
    @RequestMapping(value = "/sort/first-name/asc")
    public String sortUserByFirstNameAsc(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Page<User> page =userRepository.findAllByOrderByFirstNameAsc(PageRequest.of(p.orElse(0), 10));

        model.addAttribute("users", page.getContent());
        model.addAttribute("pagelist", page);
        return "userList";
    }

    @RequestMapping(value = "/sort/first-name/desc")
    public String sortUserByFirstNameDesc(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Page<User> page =userRepository.findAllByOrderByFirstNameDesc(PageRequest.of(p.orElse(0), 10));

        model.addAttribute("users", page.getContent());
        model.addAttribute("pagelist",page);
        return "userList";
    }

    @RequestMapping(value = "/sort/last-name/asc")
    public String sortUserByLastNameAsc(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Page<User> page =userRepository.findAllByOrderByLastNameAsc(PageRequest.of(p.orElse(0), 10));

        model.addAttribute("users", page.getContent());
        model.addAttribute("pagelist",page);
        return "userList";
    }

    @RequestMapping(value = "/sort/last-name/desc")
    public String sortUserByLastNameDesc(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Page<User> page =userRepository.findAllByOrderByLastNameDesc(PageRequest.of(p.orElse(0), 10));

        model.addAttribute("users", page.getContent());
        model.addAttribute("pagelist",page);
        return "userList";
    }

    @RequestMapping(value = "/sort/email/asc")
    public String sortUserByEmailAsc(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Page<User> page =userRepository.findAllByOrderByEmailAsc(PageRequest.of(p.orElse(0), 10));

        model.addAttribute("users", page.getContent());
        model.addAttribute("pagelist",page);
        return "userList";
    }

    @RequestMapping(value = "/sort/email/desc")
    public String sortUserByEmailDesc(Model model, @RequestParam(value = "page") Optional<Integer> p) {
        Page<User> page =userRepository.findAllByOrderByEmailDesc(PageRequest.of(p.orElse(0), 10));

        model.addAttribute("users", page.getContent());
        model.addAttribute("pagelist",page);
        return "userList";
    }
}
