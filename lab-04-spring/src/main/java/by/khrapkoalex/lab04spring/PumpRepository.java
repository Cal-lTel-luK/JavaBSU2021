package by.khrapkoalex.lab04spring;

import org.springframework.data.repository.CrudRepository;

public interface PumpRepository extends CrudRepository<Pump, Integer> {
    Iterable<Pump> findAllByOrderByPumpName();

    Iterable<Pump> findAllByPumpNameContainsOrderByPumpName(String name);
    Iterable<Pump> findAllByPumpNameContainsOrderByPriceAsc(String name);
    Iterable<Pump> findAllByPumpNameContainsOrderByPriceDesc(String name);

    Pump findPumpByPumpId(Integer pumpId);
}
