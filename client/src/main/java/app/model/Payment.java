package app.model;

import javafx.beans.property.SimpleStringProperty;
import org.springframework.stereotype.Component;

@Component
public class Payment {

    private SimpleStringProperty totalPrice;
    private SimpleStringProperty paymentStatus;
    private SimpleStringProperty cardNumber;
    private SimpleStringProperty cardType;
    private SimpleStringProperty paymentResult;

    private SimpleStringProperty bonusNumber;
    private SimpleStringProperty bonusResult;

    // CardReader API Request objects
    private final Request statusEndpoint = new Request("http://localhost:9002/cardreader/status");
    private final Request waitForPaymentEndpoint = new Request("http://localhost:9002/cardreader/waitForPayment");
    private final Request abortEndpoint = new Request("http://localhost:9002/cardreader/abort");
    private final Request resetEndpoint = new Request("http://localhost:9002/cardreader/reset");
    private final Request resultEndpoint = new Request("http://localhost:9002/cardreader/result");


    public Payment(){
        totalPrice = new SimpleStringProperty((String) "0");
        paymentStatus = new SimpleStringProperty((String) "Status: ");
        cardNumber = new SimpleStringProperty((String) "Card: ");
        cardType = new SimpleStringProperty((String) "Type: ");
        paymentResult = new SimpleStringProperty((String) "Result: ");
        bonusNumber = new SimpleStringProperty((String) "Bonus Card: ");
        bonusResult = new SimpleStringProperty((String) "Bonus Result");
    }

    public void send(SimpleStringProperty totalPrice) throws Exception{
        this.totalPrice = totalPrice;
        System.out.println("amount="+ totalPrice.getValue());
        waitForPaymentEndpoint.postRequest("amount=" + totalPrice.getValue());
    }

    public SimpleStringProperty status() throws Exception{
        return new SimpleStringProperty((String) statusEndpoint.getRequest());
    }

    public void abort() throws Exception{
        abortEndpoint.postRequest("");
    }

    public void reset() throws Exception{
        resetEndpoint.postRequest("");
    }

    public void result() throws Exception{
        paymentStatus.set("Status: " + statusEndpoint.getRequest());

        String resultXML = resultEndpoint.getRequest();
        cardNumber.set("Card: " + extractXmlValue(resultXML, "paymentCardNumber"));
        paymentResult.set("Result: " + extractXmlValue(resultXML, "paymentState"));
        cardType.set("Type: " + extractXmlValue(resultXML, "paymentCardType"));
        bonusNumber.set("Bonus Card: " + extractXmlValue(resultXML, "bonusCardNumber"));
        bonusResult.set("Bonus Result: " + extractXmlValue(resultXML, "bonusState"));
    }
    private String extractXmlValue(String ret, String name) {
        int i1 = ret.indexOf("<" + name + ">");
        int i2 = ret.indexOf("</" + name + ">");
        return (i1 > -1 && i2 > -1) ? ret.substring(i1 + name.length() + 2, i2) : "";
    }

    public SimpleStringProperty getPaymentStatus() {
        return paymentStatus;
    }
    public SimpleStringProperty getCardNumber() {
        return cardNumber;
    }
    public SimpleStringProperty getPaymentResult() {
        return paymentResult;
    }
    public SimpleStringProperty getCardType() {
        return cardType;
    }
    public SimpleStringProperty getBonusNumber() {
        return bonusNumber;
    }
    public SimpleStringProperty getBonusResult() {
        return bonusResult;
    }

}