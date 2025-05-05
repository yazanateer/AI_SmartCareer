package cloud.CRUD;

import cloud.Entity.CareerAdvice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CareerAdviceCRUD extends MongoRepository<CareerAdvice, String> {
}
