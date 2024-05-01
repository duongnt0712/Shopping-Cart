package se2project.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import se2project.GlobalData.GlobalData;
import se2project.model.Product;
import se2project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import se2project.service.PaypalService;
import se2project.util.URLUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;

@Controller
public class CartController {

    public static final String PAYPAL_SUCCESS_URL = "payment/success";
    public static final String PAYPAL_CANCEL_URL = "payment/cancel";

    private static final double VND_TO_USD_RATE = 0.000039;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private PaypalService paypalService;

    private Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping(value = "/addToCart/{id}")
    public String addToCart(@PathVariable(value = "id") Long id,Model model ) {
        GlobalData.cart.add(productRepository.getById(id));
        model.addAttribute("cart", GlobalData.cart);
        model.addAttribute("cartCount", GlobalData.cart.size());
        double amount;
        amount =GlobalData.cart.stream().mapToDouble(Product::getPrice).sum();
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        model.addAttribute("total", formatter.format(amount));
        return "cart";
    }

    @GetMapping(value = "/cart")
    public String cart(Model model) {
        double amount;
        amount =GlobalData.cart.stream().mapToDouble(Product::getPrice).sum();
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        model.addAttribute("total", formatter.format(amount));
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("cart", GlobalData.cart);
        return "cart";
    }

    @RequestMapping(value = "/cart/removeItem/{index}")
    public String deleteFromCart(@PathVariable int index ){
        GlobalData.cart.remove(index);

        return "redirect:/cart";
    }

    @RequestMapping(value = "/checkout")
    public String checkout (Model model) {
        double amount;
        amount =GlobalData.cart.stream().mapToDouble(Product::getPrice).sum();
        DecimalFormat formatter = new DecimalFormat("###,###,###");

        model.addAttribute("total", formatter.format(amount));
        model.addAttribute("cartCount", GlobalData.cart.size());
        return  "checkout";
    }

    @RequestMapping(value="/payment/create")
    public RedirectView createPayment(HttpServletRequest request, @RequestParam("amount") String amount) {
        try {
            String cancelUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_CANCEL_URL;
            String successUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_SUCCESS_URL;
            Double convertedAmount = Double.parseDouble(amount.replaceAll(",", "")) * VND_TO_USD_RATE;

            Payment payment = paypalService.createPayment(
                    convertedAmount,
                    "USD",
                    "paypal",
                    "sale",
                    "payment description",
                    cancelUrl,
                    successUrl);
            for(Links links : payment.getLinks()){
                if(links.getRel().equals("approval_url")){
                    return new RedirectView(links.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return new RedirectView("/payment/error");
    }

    @GetMapping( value = "/payment/cancel")
    public String cancelPay(){
        return "cancel";
    }

    @GetMapping( value = "/payment/error ")
    public String paymentError(){
        return "error";
    }

    @GetMapping(value = "/payment/success")
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId){
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if(payment.getState().equals("approved")){
                return "success";
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "success";
    }
}
