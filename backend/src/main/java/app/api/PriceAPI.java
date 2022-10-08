package app.api;
import app.model.Item;
import app.model.tables.Price;
import app.service.PriceService;
import org.javamoney.moneta.Money;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "api/v1/price")
public class PriceAPI {
    private final PriceService priceService;

    public PriceAPI(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping(path = "/{id}", produces = {"application/json"})
    public Money getPriceById(@PathVariable(name = "id") String id) {

        var price = priceService.get(Integer.parseInt(id));
        System.out.println(price.getPrice().toString());
        System.out.println(price.getPrice().getNumber());
        System.out.println(price.getPrice().getNumber().doubleValueExact());

        return price.getPrice();
    }

    @PostMapping(path = "/save/")
    public void savePrice(@RequestBody Item item) {
        var price = new Price();
        price.setProductId(Integer.parseInt(item.getId()));
        price.setPrice(item.getPrice());
        priceService.save(price);
    }


}
