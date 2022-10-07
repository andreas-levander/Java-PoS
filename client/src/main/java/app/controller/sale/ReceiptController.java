package app.controller.sale;

import app.controller.CustomerController;
import app.model.Sale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Component
public class ReceiptController {
    private final ApplicationContext ctx;
    @Value("./receipts/")
    private String receiptLocation;
    private Sale sale;

    public ReceiptController(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @EventListener
    public void handler(SaleFinishedEvent event) {

        System.out.println("sale finished from receipt listener");
        sale = event.sale();
        ctx.getBean(CustomerController.class).toggleReceiptButton();

    }

    public void printReceipt() {
        var receipt = createReceipt(sale);
        saveImage(sale.getId().toString(), receipt);
    }

    private BufferedImage createReceipt(Sale sale) {
        var cart = sale.getCart();

        var img = new BufferedImage(600,600,BufferedImage.TYPE_INT_RGB);
        var g = img.createGraphics();

        int y = 20;
        int x = 10;

        g.drawString("Receipt for sale: " + sale.getId().toString(), x, y);
        y += 20;
        // draw items in cart
        for (var item : cart.getCart()) {
            g.drawString(item.toString(),x,y);
            y += 20;
        }

        g.drawString("------------------", x, y);
        y += 20;
        var total = cart.getTotalPrice().toString();
        g.drawString("Total: " + total, x, y);

        return img;
    }

    private void saveImage(String name, BufferedImage img) {
        try {
            File f = new File(receiptLocation + name + ".jpeg");
            if (!ImageIO.write(img, "JPEG", f)) {
                System.err.println("No plugin to write " + img + " in JPEG format to " + f);
            }
        }
        catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}
