package by.khrapkoalex.lab04spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    PumpRepository pumpRepository;

    @GetMapping("/")
    String startPage() {
        return "index";
    }

    @GetMapping("/pump/list")
    public String getPumpsList(Model model, String sortingMode, String findName) {
        if (findName == null) {
            findName = "";
        }
        if ("desc".equals(sortingMode)) {
            model.addAttribute("pumpRepository",
                    pumpRepository.findAllByPumpNameContainsOrderByPriceDesc(findName));
        } else if ("asc".equals(sortingMode)) {
            model.addAttribute("pumpRepository",
                    pumpRepository.findAllByPumpNameContainsOrderByPriceAsc(findName));
        } else {
            model.addAttribute("pumpRepository",
                    pumpRepository.findAllByPumpNameContainsOrderByPumpName(findName));
        }
        return "pump_list";
    }

    @GetMapping("/shop/list")
    public String getShopsList(Model model) {
        model.addAttribute("shopRepository", shopRepository.findAllByOrderByShopName());
        return "shop_list";
    }

    @GetMapping("/pump")
    public String getPump(Model model, Integer pumpId) {
        Pump pump = pumpRepository.findPumpByPumpId(pumpId);
        model.addAttribute("pump", pump);
        return "pump";
    }

    @GetMapping("/shop")
    public String getShop(Model model, Integer shopId) {
        Shop shop = shopRepository.findShopByShopId(shopId);
        model.addAttribute("shop", shop);
        return "shop";
    }
}
