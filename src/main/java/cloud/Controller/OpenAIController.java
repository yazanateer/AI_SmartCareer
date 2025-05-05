package cloud.Controller;

import cloud.Entity.CareerAdvice;
import cloud.Service.CareerAdviceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/openai")
public class OpenAIController {
    private final CareerAdviceService careerAdviceService;

    public OpenAIController(CareerAdviceService careerAdviceService){
        this.careerAdviceService = careerAdviceService;
    }

    @GetMapping
    public ResponseEntity<CareerAdvice> getAdvice(
                @RequestParam String field,
                @RequestParam String education,
                @RequestParam String skills) {
       CareerAdvice Advice = careerAdviceService.generateAndSaveAdvice(field, education, skills);
       return ResponseEntity.ok(Advice);
    }

}
