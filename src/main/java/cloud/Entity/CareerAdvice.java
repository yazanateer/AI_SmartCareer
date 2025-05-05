package cloud.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "CareerAdvice")
public class CareerAdvice {

    @Id
    private String id;
    private String field;
    private String education;
    private String skills;

    private String AdviceResponse;
}
