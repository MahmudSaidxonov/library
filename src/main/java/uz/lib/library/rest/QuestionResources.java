package uz.lib.library.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.lib.library.dto.QuestionDto;
import uz.lib.library.dto.QuizDto;
import uz.lib.library.dto.ResponseDto;
import uz.lib.library.model.Question;
import uz.lib.library.model.Quiz;
import uz.lib.library.repository.QuizRepository;
import uz.lib.library.service.QuestionService;
import uz.lib.library.service.QuizService;
import uz.lib.library.service.mapper.QuizMapper;

import java.util.*;

@RestController
@RequestMapping("question")
@RequiredArgsConstructor
public class QuestionResources {

    private final QuestionService service;

    @Operation(
            summary = "Create a new Question",
            description = "Need to send QuestionDto to this endpoint to add new question",
            tags = { "questions", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = Quiz.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/")
    public ResponseDto<QuestionDto> add(@RequestBody QuestionDto questionDto) {
        return service.addQuestion(questionDto);
    }

    @Operation(summary = "Update a Question", tags = { "questions", "patch" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = Quiz.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
    @PutMapping("/")
    public ResponseDto<QuestionDto> update(@RequestBody QuestionDto questionDto) {
        return service.updateQuestion(questionDto);
    }

    //get all question of any quid
    @Operation(
            summary = "Retrieve all Questions of any quid",
            description = "Get all Questions of any quid",
            tags = { "questions", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = Quiz.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "204", description = "There are no Questions", content = {
                    @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/quiz/all/{qid}")
    public ResponseDto<List<QuestionDto>> getQuestionsOfQuizAdmin(@PathVariable("qid") Integer qid) {
        return service.getQuestionsOfQuiz(qid);

    }

    @Operation(
            summary = "Retrieve all Questions of quiz",
            description = "Get all Questions",
            tags = { "questions", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = Quiz.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "204", description = "There are no Questions", content = {
                    @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/quiz/{qid}")
    public ResponseDto<List<QuestionDto>> getQuestionsOfQuiz(@PathVariable("qid") Integer qid){
        return service.getQuestions(qid);

    }

    //get single question
    @Operation(
            summary = "Retrieve a Question by Id",
            description = "Get a Question object by specifying its id.",
            tags = { "questions", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Quiz.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/{quesId}")
    public ResponseDto<QuestionDto> get(@PathVariable("quesId") Integer quesId) {
        return service.getQuestion(quesId);
    }
    //delete question
    @DeleteMapping("/{quesId}")
    public void delete(@PathVariable("quesId") Integer quesId) {
        this.service.deleteQuestionById(quesId);
    }


    @Operation(
            summary = "Get test result",
            description = "Need to send Questions list to this endpoint to get a test result",
            tags = { "questions", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Quiz.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/eval-quiz")
    public ResponseDto<Map<String, Object>> getEvalQuiz(@RequestBody List<QuestionDto> questions) {
        return service.evalQuiz(questions);
    }

}
