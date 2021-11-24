package by.khrapkoalex.lab04spring;

import org.springframework.data.repository.CrudRepository;

public interface ShopRepository extends CrudRepository<Shop, Integer> {
    Iterable<Shop> findAllByOrderByShopName();

    Shop findShopByShopId(Integer shop_id);
}
