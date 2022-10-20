package app.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
@Getter
@AllArgsConstructor
@Jacksonized @Builder
@JacksonXmlRootElement(localName = "customer")
public class Customer {
    private Integer customerNo;
    private String firstName;
    private String lastName;
    private String address;
    private BonusCard bonusCard;
    private String sex;
}
