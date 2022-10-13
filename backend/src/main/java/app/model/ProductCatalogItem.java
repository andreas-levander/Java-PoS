package app.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@AllArgsConstructor @Setter @Getter @ToString
@Jacksonized @Builder
@JacksonXmlRootElement(localName = "product")
public class ProductCatalogItem {
    private String optLockVersion;
    private Integer id;
    private String name;
    private String barCode;
    private String vat;
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<String> keyword;
}
