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
    Shop getShop(String shopName) { return shopRepository.findShopByShopName(shopName);}

    Pump getPump(Integer pumpId) {
        return pumpRepository.findPumpByPumpId(pumpId);
    }
    Pump getPump(String pumpName) {return pumpRepository.findPumpByPumpName(pumpName);}

    void connectPumpAndShop(String pumpName, String shopName) {
        Pump pump = getPump(pumpName);
        Shop shop = getShop(shopName);
        if (pump == null || shop == null) {
            return;
        }
        pump.addShop(shop);
        shop.addPump(pump);
        pumpRepository.save(pump);
        shopRepository.save(shop);
    }

    Iterable<Shop> getShops() {
        return shopRepository.findAllByOrderByShopName();
    }

    Iterable<Pump> getPumps() {
        return pumpRepository.findAllByOrderByPumpName();
    }
}
