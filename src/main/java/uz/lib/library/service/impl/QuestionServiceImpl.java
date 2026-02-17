package uz.lib.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.lib.library.dto.QuestionDto;
import uz.lib.library.dto.ResponseDto;
import uz.lib.library.model.Question;
import uz.lib.library.model.Quiz;
import uz.lib.library.repository.QuestionRepository;
import uz.lib.library.repository.QuizRepository;
import uz.lib.library.service.QuestionService;
import uz.lib.library.service.additional.AppStatusMessages;
import uz.lib.library.service.mapper.QuestionMapper;

import java.util.*;
import java.util.stream.Collectors;

import static uz.lib.library.service.additional.AppStatusCodes.*;
import static uz.lib.library.service.additional.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final QuestionMapper questionMapper;

    @Override
    public ResponseDto<QuestionDto> addQuestion(QuestionDto questionDto) {
        try {
            Optional<Question> question = questionRepository.getByContent(questionDto.getContent());

            if (question.isPresent()) {
                return ResponseDto.<QuestionDto>builder()
                        .success(false)
                        .code(-1)
                        .message(AppStatusMessages.DUPLICATE_ERROR)
                        .data(questionDto)
                        .build();
            }

            questionRepository.save(questionMapper.toEntity(questionDto));

            return ResponseDto.<QuestionDto>builder()
                    .success(true)
                    .code(0)
                    .message("OK")
                    .data(questionDto)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<QuestionDto>builder()
                    .success(false)
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .data(questionDto)
                    .build();
        }

    }

    @Override
    public ResponseDto<QuestionDto> updateQuestion(QuestionDto questionDto) {
        if (questionDto.getId()  == null) {
            return ResponseDto.<QuestionDto>builder()
                    .success(false)
                    .code(NOT_FOUND_ERROR_CODE)
                    .message(NOT_FOUND)
                    .data(questionDto)
                    .build();
        }

        Optional<Question> questionContent = questionRepository.getByContent(questionDto.getContent());

        if (questionContent.isPresent()) {
            return ResponseDto.<QuestionDto>builder()
                    .success(false)
                    .code(-1)
                    .message(AppStatusMessages.DUPLICATE_ERROR)
                    .data(questionDto)
                    .build();
        }

        Optional<Question> questionId = questionRepository.findById(questionDto.getId());

        if (questionId.isEmpty()) {
            return ResponseDto.<QuestionDto>builder()
                    .success(false)
                    .code(NOT_FOUND_ERROR_CODE)
                    .message(NOT_FOUND)
                    .data(questionDto)
                    .build();
        }

        Optional<Question> question = questionMapper.toEditEntity(questionDto);

        try {
            questionRepository.save(question.get());

            return ResponseDto.<QuestionDto>builder()
                    .success(true)
                    .code(OK_CODE)
                    .message(OK)
                    .data(questionMapper.toDto(question.get()))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<QuestionDto>builder()
                    .success(false)
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<List<QuestionDto>> getQuestions(Integer id) {
        try {
            Optional<Quiz> quiz = quizRepository.findById(id);

            if (quiz.isEmpty()) {
                return ResponseDto.<List<QuestionDto>>builder()
                        .success(false)
                        .code(-1)
                        .message(AppStatusMessages.NOT_FOUND)
                        .build();
            }


            List<QuestionDto> list = questionRepository.findByQuiz(id)
                    .stream().map(questionMapper::toDto).collect(Collectors.toList());
            Collections.shuffle(list);
            if (list.size() > Integer.parseInt(quiz.get().getNumberOfQuestions())) {
                list = list.subList(0, Integer.parseInt(quiz.get().getNumberOfQuestions()));
            }

            return ResponseDto.<List<QuestionDto>>builder()
                    .success(true)
                    .code(OK_CODE)
                    .message(OK)
                    .data(list)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<List<QuestionDto>>builder()
                    .success(false)
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .build();
        }

    }

    @Override
    public ResponseDto<QuestionDto> getQuestion(Integer questionId) {
        try {
            Optional<Question> question = questionRepository.findById(questionId);

            if (question.isEmpty()) {
                return ResponseDto.<QuestionDto>builder()
                        .success(false)
                        .code(-1)
                        .message(AppStatusMessages.NOT_FOUND)
                        .build();
            }
            return ResponseDto.<QuestionDto>builder()
                    .success(true)
                    .message("OK")
                    .code(0)
                    .data(questionMapper.toDto(question.get()))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<QuestionDto>builder()
                    .success(false)
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<List<QuestionDto>> getQuestionsOfQuiz(Integer id) {
        try {
            Optional<Quiz> quiz = quizRepository.findById(id);

            if (quiz.isEmpty()) {
                return ResponseDto.<List<QuestionDto>>builder()
                        .success(false)
                        .code(-1)
                        .message(AppStatusMessages.NOT_FOUND)
                        .build();
            }


            List<QuestionDto> list = questionRepository.findByQuiz(id)
                    .stream().map(questionMapper::toDto).collect(Collectors.toList());
//            Collections.shuffle(list);
//            if (list.size() > Integer.parseInt(quiz.get().getNumberOfQuestions())) {
//                list = list.subList(0, Integer.parseInt(quiz.get().getNumberOfQuestions()));
//            }

            return ResponseDto.<List<QuestionDto>>builder()
                    .success(true)
                    .code(OK_CODE)
                    .message(OK)
                    .data(list)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<List<QuestionDto>>builder()
                    .success(false)
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .build();
        }

    }

    @Override
    public ResponseDto<QuestionDto> deleteQuestionById(Integer quesId) {
        try {
            Optional<Question> question = questionRepository.findById(quesId);

            if (question.isEmpty()) {
                return ResponseDto.<QuestionDto>builder()
                        .success(false)
                        .code(NOT_FOUND_ERROR_CODE)
                        .message(NOT_FOUND)
                        .build();
            }

            Question delQuestion = new Question();
            delQuestion.setId(question.get().getId());
            questionRepository.delete(delQuestion);

            return ResponseDto.<QuestionDto>builder()
                    .success(true)
                    .code(OK_CODE)
                    .message(OK)
                    .data(questionMapper.toDto(question.get()))
                    .build();

        } catch (Exception e) {
            return ResponseDto.<QuestionDto>builder()
                    .success(true)
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .build();
        }
    }

//    @Override
//    public Optional<Question> get(Integer questionsId) {
////        return this.questionRepository.findById(questionsId);
//        return Optional.empty();
//    }

    @Override
    public ResponseDto<Map<String, Object>> evalQuiz(List<QuestionDto> questions) {
        double marksGot = 0;
        int correctAnswers = 0;
        int attempted = 0;

        try {
            for (QuestionDto q : questions) {
                Optional<Question> question = questionRepository.findById(q.getId());
                Optional<Quiz> quiz = quizRepository.findById(questions.get(0).getQuiz().getId());
                if (question.get().getAnswer().equals(q.getGivenAnswer())) {
                    correctAnswers++;
                    double marksSingle = Double.parseDouble(quiz.get().getMaxMarks()) / Integer.parseInt(quiz.get().getNumberOfQuestions());
                    marksGot += marksSingle;
                }
                if (q.getGivenAnswer() != null) {
                    attempted++;
                }
            }
            Map<String, Object> map = Map.of("marksGot", marksGot, "correctAnswers", correctAnswers, "attempted", attempted);
            return ResponseDto.<Map<String, Object>>builder()
                    .success(true)
                    .code(OK_CODE)
                    .message(OK)
                    .data(map)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<Map<String, Object>>builder()
                    .success(false)
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .build();
        }
    }
}
