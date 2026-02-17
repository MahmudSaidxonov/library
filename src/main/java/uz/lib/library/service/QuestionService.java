package uz.lib.library.service;

import org.springframework.http.ResponseEntity;
import uz.lib.library.dto.QuestionDto;
import uz.lib.library.dto.ResponseDto;
import uz.lib.library.model.Question;
import uz.lib.library.model.Quiz;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface QuestionService {
    public ResponseDto<QuestionDto> addQuestion(QuestionDto questionDto);

    public ResponseDto<QuestionDto> updateQuestion(QuestionDto questionDto);

    public ResponseDto<List<QuestionDto>> getQuestions(Integer id);

    public ResponseDto<QuestionDto> getQuestion(Integer questionId);

    public ResponseDto<List<QuestionDto>> getQuestionsOfQuiz(Integer quizId);

    public ResponseDto<QuestionDto> deleteQuestionById(Integer quesId);

//    public Optional<Question> get(Integer questionsId);

    public ResponseDto<Map<String, Object>> evalQuiz(List<QuestionDto> questionDto);
}
