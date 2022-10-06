package app.controller.sale;


import app.model.Sale;

public record SaleFinishedEvent(Sale sale) {
}
