package cloud.Service;

import cloud.CRUD.CareerAdviceCRUD;
import cloud.Entity.CareerAdvice;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class CareerAdviceImplementation implements CareerAdviceService{
    private final OpenAiChatModel chatModel;
    private final CareerAdviceCRUD careerAdviceCRUD;
    private static final Logger logger = LoggerFactory.getLogger(CareerAdviceImplementation.class);


    public CareerAdviceImplementation(OpenAiChatModel chatModel, CareerAdviceCRUD careerAdviceCRUD) {
        this.chatModel = chatModel;
        this.careerAdviceCRUD = careerAdviceCRUD;
    }

    @Override
    public CareerAdvice generateAndSaveAdvice(String field, String education, String skills){

        logger.info("[INPUT] Career Field: {}", field);
        logger.info("[INPUT] Education Level: {}", education);
        logger.info("[INPUT] Skills: {}", skills);


        String firstPrompt ="Create a professional prompt that I can use directly with an AI career advisor," +
                " focused on advising someone who wants to start a career in " + field + "with a " + education + " degree and skills in " + skills + ". Only return the crafted prompt, without any extra explanation or comments.";
        logger.debug("[Prompt Stage 1] Generated Prompt to LLM: {}", firstPrompt);

        String PromptResponse = chatModel.call(firstPrompt); //we will get the ready prompt to send a professional request
        logger.info("[LLM Prompt Response]: {}", PromptResponse);


        String PromptToSend = "Bro i want a brief answer of advice career for this request: The result should be in a simple, natural language answer, without any Markdown formatting, bullet points, or line breaks — just plain text."
                + PromptResponse;
        logger.debug("[Prompt Stage 2] Final Prompt Sent to LLM: {}", PromptToSend);


        String AdviceResponse = chatModel.call(PromptToSend); //here we got the best result from the LLM
        logger.info("[LLM Final Advice]: {}", AdviceResponse);

        CareerAdvice finalAdvice = new CareerAdvice(null,field, education, skills, AdviceResponse);

        return careerAdviceCRUD.save(finalAdvice);
    }

}
