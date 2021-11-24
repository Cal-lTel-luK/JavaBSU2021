package by.khrapkoalex.lab04spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class RestApiController {
    @Autowired
    PumpRepository pumpRepository;

    @Autowired
    ShopRepository shopRepository;

    @GetMapping("pumps/list")
    public Iterable<Pump> getPumps() {
        return pumpRepository.findAllByOrderByPumpName();
    }

    @GetMapping("shops/list")
    public Iterable<Shop> getShops() {
        return shopRepository.findAllByOrderByShopName();
    }
}
