package app.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Payment2 {
    SimpleDoubleProperty amount;
    SimpleStringProperty status;

    public Payment2(SimpleDoubleProperty amount, String status) {
        this.status = new SimpleStringProperty(status);
        this.amount = amount;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}
