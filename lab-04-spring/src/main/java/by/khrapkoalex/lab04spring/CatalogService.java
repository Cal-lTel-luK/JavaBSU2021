package by.khrapkoalex.lab04spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalogService {
    @Autowired
    ShopRepository shopRepository;

    @Autowired
    PumpRepository pumpRepository;

    void addPump(Pump pump) {
        if (pump.getPumpRating() == null) {
            pump.setPumpRating(10.1);
        }
        pumpRepository.save(pump);
    }

    void addShop(Shop shop) {
        if (shop.getShopRating() == null) {
            shop.setShopRating(9.9);
        }
        shopRepository.save(shop);
    }

    Shop getShop(Integer shopId) {
        return shopRepository.findShopByShopId(shopId);
    }

    Pump getPump(Integer pumpId) {
        return pumpRepository.findPumpByPumpId(pumpId);
    }


}
