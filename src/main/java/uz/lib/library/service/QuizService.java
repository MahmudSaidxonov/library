package uz.lib.library.service;

import uz.lib.library.dto.QuestionDto;
import uz.lib.library.dto.QuizDto;
import uz.lib.library.dto.ResponseDto;
import uz.lib.library.model.Quiz;

import java.util.List;
import java.util.Set;

public interface QuizService {
    public ResponseDto<QuizDto> addQuiz(QuizDto quizDto);
    public ResponseDto<QuizDto> updateQuiz(QuizDto quizDto);
    public ResponseDto<List<QuizDto>> getQuizzes();
    public ResponseDto<QuizDto> getQuizById(Integer quizId);
    public ResponseDto<QuizDto> deleteQuizById(Integer quizId);
    public ResponseDto<List<QuizDto>> getActiveQuizzes();

}
