package app.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
@AllArgsConstructor
@Jacksonized @Builder @Getter
@JacksonXmlRootElement(localName = "result")
public class CardTransactionResult {
    private String bonusCardNumber;
    private String bonusState;
    private String paymentCardNumber;
    private String paymentState;
    private String paymentCardType;
}
