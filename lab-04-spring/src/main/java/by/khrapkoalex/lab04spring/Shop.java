package by.khrapkoalex.lab04spring;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shopId;
    @NotNull
    private String shopName;
    private Double shopRating = null;

    @ManyToMany
    @JoinTable(name = "catalog", joinColumns = @JoinColumn(name = "shop_id"), inverseJoinColumns = @JoinColumn(name="pump_id"))
    @JsonIgnoreProperties("shops")
    Set<Pump> pumps = new HashSet<>();

    void addPump(Pump pump) {
        pumps.add(pump);
    }
}
