package uz.lib.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.lib.library.dto.QuestionDto;
import uz.lib.library.dto.QuizDto;
import uz.lib.library.dto.ResponseDto;
import uz.lib.library.dto.UserDto;
import uz.lib.library.model.Quiz;
import uz.lib.library.repository.QuizRepository;
import uz.lib.library.service.QuizService;
import uz.lib.library.service.additional.AppStatusMessages;
import uz.lib.library.service.mapper.QuizMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static uz.lib.library.service.additional.AppStatusCodes.*;
import static uz.lib.library.service.additional.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizMapper quizMapper;
    private final QuizRepository quizRepository;

    @Override
    public ResponseDto<QuizDto> addQuiz(QuizDto quizDto) {
        try {
            Optional<Quiz> quiz = quizRepository.findByTitle(quizDto.getTitle());

            if (quiz.isPresent()) {
                return ResponseDto.<QuizDto>builder()
                        .success(false)
                        .code(-1)
                        .message(AppStatusMessages.DUPLICATE_ERROR)
                        .data(quizDto)
                        .build();
            }
            quizRepository.save(quizMapper.toEntity(quizDto));
            return ResponseDto.<QuizDto>builder()
                    .success(true)
                    .code(0)
                    .message("OK")
                    .data(quizDto)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<QuizDto>builder()
                    .success(false)
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<QuizDto> updateQuiz(QuizDto quizDto) {
            if (quizDto.getId() == null) {
                return ResponseDto.<QuizDto>builder()
                        .success(false)
                        .code(-2)
                        .message("Quiz ID is null")
                        .data(quizDto)
                        .build();
            }
            
            Optional<Quiz> quizTitle = quizRepository.findByTitle(quizDto.getTitle());
            
            if (quizTitle.isPresent()) {
                return ResponseDto.<QuizDto>builder()
                        .success(false)
                        .code(-2)
                        .message("Quiz with this title already exists")
                        .build();
            }
            
            Optional<Quiz> quizId = quizRepository.findById(quizDto.getId());
            
            if (quizId.isEmpty()) {
                return ResponseDto.<QuizDto>builder()
                        .success(false)
                        .code(-2)
                        .message("Quiz with this ID not found")
                        .build();
            }
            
            Optional<Quiz> quiz = quizMapper.toEditEntity(quizDto);

            try {

                quizRepository.save(quiz.get());

                return ResponseDto.<QuizDto>builder()
                        .success(true)
                        .code(0)
                        .message(AppStatusMessages.OK)
                        .data(quizMapper.toDto(quiz.get()))
                        .build();
            } catch (Exception e) {
                return ResponseDto.<QuizDto>builder()
                        .success(false)
                        .code(DATABASE_ERROR_CODE)
                        .message(DATABASE_ERROR + ": " + e.getMessage())
                        .build();
            }
    }

    @Override
    public ResponseDto<List<QuizDto>> getQuizzes() {
        try {
            return ResponseDto.<List<QuizDto>>builder()
                    .code(OK_CODE)
                    .message(OK)
                    .data(quizRepository.findAll().stream()
                            .map(quizMapper::toDto)
                            .collect(Collectors.toList()))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<List<QuizDto>>builder()
                    .code(1)
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<QuizDto> getQuizById(Integer id) {
        try {
            Optional<Quiz> quizId = quizRepository.findById(id);

            if (quizId.isEmpty()) {
                return ResponseDto.<QuizDto>builder()
                        .success(false)
                        .code(NOT_FOUND_ERROR_CODE)
                        .message(NOT_FOUND)
                        .build();
            }
            return ResponseDto.<QuizDto>builder()
                    .success(true)
                    .message(OK)
                    .code(OK_CODE)
                    .data(quizMapper.toDto(quizId.get()))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<QuizDto>builder()
                    .success(false)
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<QuizDto> deleteQuizById(Integer quizId) {

        Optional<Quiz> quiz = quizRepository.findByIdAndIsActive(quizId, true);

        if (quiz.isEmpty()) {
            return ResponseDto.<QuizDto>builder()
                    .success(false)
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_ERROR_CODE)
                    .build();
        }
        Quiz delQuiz = quiz.get();
        delQuiz.setIsActive(false);

        try {
            quizRepository.save(delQuiz);
            return ResponseDto.<QuizDto>builder()
                    .success(true)
                    .message(OK)
                    .data(quizMapper.toDto(delQuiz))
                    .build();

        }catch (Exception e){
            return ResponseDto.<QuizDto>builder()
                    .success(false)
                    .message(e.getMessage())
                    .code(OK_CODE)
                    .build();
        }
    }
    @Override
    public ResponseDto<List<QuizDto>> getActiveQuizzes() {
        try {
            return ResponseDto.<List<QuizDto>>builder()
                    .code(OK_CODE)
                    .message(OK)
                    .data(quizRepository.findAllByIsActive(true).stream()
                            .map(quizMapper::toDto)
                            .collect(Collectors.toList()))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<List<QuizDto>>builder()
                    .code(1)
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .build();
        }
    }

}
