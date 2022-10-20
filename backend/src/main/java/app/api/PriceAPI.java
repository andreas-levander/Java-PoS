package app.api;
import app.model.Item;
import app.model.tables.Price;
import app.service.PriceService;
import org.javamoney.moneta.Money;
import org.springframework.web.bind.annotation.*;

/** Class responsible for the price endpoints */
@RestController
@RequestMapping(path = "api/v1/price")
public class PriceAPI {
    private final PriceService priceService;

    public PriceAPI(PriceService priceService) {
        this.priceService = priceService;
    }


    @PostMapping(path = "/save")
    public void savePrice(@RequestBody Item item) {
        var price = new Price(item.getId(), item.getPrice());
        priceService.save(price);
    }


}
