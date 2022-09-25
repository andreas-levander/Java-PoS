package app.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import org.springframework.stereotype.Component;

@Component
public class Payment {

    private SimpleDoubleProperty change;
    private SimpleDoubleProperty cashTotal;
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

    private final Request openCashBoxEndpoint = new Request("http://localhost:9001/cashbox/open");

    public Payment(){
        cashTotal = new SimpleDoubleProperty((Double) 0.0);
        change = new SimpleDoubleProperty((Double) 0.0);
        paymentStatus = new SimpleStringProperty((String) "Status: ");
        cardNumber = new SimpleStringProperty((String) "Card: ");
        cardType = new SimpleStringProperty((String) "Type: ");
        paymentResult = new SimpleStringProperty((String) "Result: ");
        bonusNumber = new SimpleStringProperty((String) "Bonus Card: ");
        bonusResult = new SimpleStringProperty((String) "Bonus Result");
    }

    public void send(Double totalPrice) throws Exception{
        System.out.println("amount="+ totalPrice);
        waitForPaymentEndpoint.postRequest("amount=" + totalPrice);
    }

    public SimpleStringProperty status() throws Exception{
        return new SimpleStringProperty((String) statusEndpoint.getRequest());
    }

    public void abort() throws Exception{
        abortEndpoint.postRequest("");
        cashTotal.set(0.0);
    }

    public void reset() throws Exception{
        resetEndpoint.postRequest("");
        change.set(0.0);
        cashTotal.set(0.0);
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

    public void open() throws Exception{
        openCashBoxEndpoint.postRequest("");
    }
    private String extractXmlValue(String ret, String name) {
        int i1 = ret.indexOf("<" + name + ">");
        int i2 = ret.indexOf("</" + name + ">");
        return (i1 > -1 && i2 > -1) ? ret.substring(i1 + name.length() + 2, i2) : "";
    }

    public Boolean calculateChange(String amountReceivedString){
        try{
            Double amountReceived = Double.parseDouble(amountReceivedString);
            if((amountReceived - cashTotal.get()) < 0 ){
                return false;
            } else {
                change.set(amountReceived - cashTotal.get());
                return true;
            }
        } catch (Exception e){
            return false;
        }

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
    public SimpleDoubleProperty getChange(){return change;}

    public SimpleDoubleProperty getCashTotal(){return cashTotal;}
    public void setCashTotal(Double amount){
        cashTotal.set(amount);
    }

}