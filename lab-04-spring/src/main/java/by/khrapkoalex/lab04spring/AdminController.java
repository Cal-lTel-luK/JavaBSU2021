package by.khrapkoalex.lab04spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    CatalogService catalogService;

    @GetMapping("/shop/add")
    public String addShop() {
        return "add_shop";
    }

    @PostMapping(value = "/shop/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addShop(Shop shop) {
        catalogService.addShop(shop);
        return "redirect:/shop/list";
    }

    @GetMapping("/pump/add")
    public String addPump() {
        return "add_pump";
    }

    @PostMapping(value = "/pump/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addPump(Pump pump) {
        catalogService.addPump(pump);
        return "redirect:/pump/list";
    }

    @GetMapping("/connect")
    public String connectPumpAndShop() {
        return "connect";
    }

    @PostMapping(value = "/connect", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String connectPumpAndShop(String pumpName, String shopName) {

        catalogService.connectPumpAndShop(pumpName, shopName);
        return "redirect:/";
    }
}
