package cloud.Service;

import cloud.CRUD.CareerAdviceCRUD;
import cloud.Entity.CareerAdvice;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

@Service
public class CareerAdviceImplementation implements CareerAdviceService{
    private final OpenAiChatModel chatModel;
    private final CareerAdviceCRUD careerAdviceCRUD;


    public CareerAdviceImplementation(OpenAiChatModel chatModel, CareerAdviceCRUD careerAdviceCRUD) {
        this.chatModel = chatModel;
        this.careerAdviceCRUD = careerAdviceCRUD;
    }

    @Override
    public CareerAdvice generateAndSaveAdvice(String field, String education, String skills){
        String firstPrompt ="Create a professional prompt that I can use directly with an AI career advisor," +
                " focused on advising someone who wants to start a career in " + field + "with a " + education + " degree and skills in " + skills + ". Only return the crafted prompt, without any extra explanation or comments.";

        String PromptResponse = chatModel.call(firstPrompt); //we will get the ready prompt to send a professional request


        String PromptToSend = "Bro i want a brief answer of advice career for this request: The result should be in a simple, natural language answer, without any Markdown formatting, bullet points, or line breaks — just plain text."
                + PromptResponse;

        System.out.println("\nThe default prompt: " + PromptToSend);


        System.out.println("\nThe generated prompt from the LLM: " + PromptResponse);


        String AdviceResponse = chatModel.call(PromptToSend); //here we got the best result from the LLM


        System.out.println("\nThe result advice from the LLM: " + AdviceResponse);

        CareerAdvice finalAdvice = new CareerAdvice(null,field, education, skills, AdviceResponse);

        return careerAdviceCRUD.save(finalAdvice);
    }

}
