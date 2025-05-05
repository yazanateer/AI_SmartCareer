package cloud.Service;

import cloud.Entity.CareerAdvice;

public interface CareerAdviceService {

    CareerAdvice generateAndSaveAdvice(String field, String education, String skills);
}
