package by.khrapkoalex.lab04spring;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class Pump {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pumpId;

    @NotNull
    private String pumpName;
    @NotNull
    private Double price;
    private Double pumpRating = null;

    @ManyToMany
    @JoinTable(name = "catalog", joinColumns = @JoinColumn(name = "pump_id"), inverseJoinColumns = @JoinColumn(name="shop_id"))
    @JsonIgnoreProperties("pumps")
    Set<Shop> shops = new HashSet<>();

    void addShop(Shop shop) {
        shops.add(shop);
    }
}
