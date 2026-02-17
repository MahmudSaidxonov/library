package uz.lib.library.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.lib.library.dto.QuizDto;
import uz.lib.library.dto.ResponseDto;
import uz.lib.library.model.Quiz;
import uz.lib.library.model.Rating;
import uz.lib.library.service.QuestionService;
import uz.lib.library.service.QuizService;
import uz.lib.library.service.mapper.QuizMapper;

import java.util.List;

@RestController
@RequestMapping("quiz")
@RequiredArgsConstructor
public class QuizResources {

    private final QuizService quizService;

    @Operation(
            summary = "Create a new Quiz",
            description = "Need to send QuizDto to this endpoint to add new quiz",
            tags = { "quizzes", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = Quiz.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/")
    public ResponseDto<QuizDto> add(@RequestBody QuizDto quizDto) {
        return quizService.addQuiz(quizDto);
    }

    @Operation(summary = "Update a Quiz", tags = { "quizzes", "patch" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = Quiz.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
    @PutMapping("/")
    public ResponseDto<QuizDto> update(@RequestBody QuizDto quizDto) {
        return quizService.updateQuiz(quizDto);
    }

    @Operation(
            summary = "Retrieve all Quizzes (admin)",
            description = "Get all Quizzes",
            tags = { "quizzes", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = Quiz.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "204", description = "There are no Quizzes", content = {
                    @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    //get all quizzes (admin)
    @GetMapping("/")
    public ResponseDto<List<QuizDto>> quizzes() {
        return quizService.getQuizzes();
    }

    @Operation(
            summary = "Retrieve a Quiz by Id",
            description = "Get a Quiz object by specifying its id.",
            tags = { "quizzes", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Quiz.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/{qid}")
    public ResponseDto<QuizDto> quiz(@PathVariable("qid") Integer qid) {
        return quizService.getQuizById(qid);
    }

    @Operation(summary = "Delete a Quiz by Id", tags = { "quizzes", "delete" })
    @ApiResponses({ @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @DeleteMapping("/{qid}")
    public ResponseDto<QuizDto> delete(@PathVariable("qid") Integer qid) {
        return quizService.deleteQuizById(qid);
    }

    @Operation(
            summary = "Retrieve active Quizzes",
            description = "Get active Quizzes",
            tags = { "quizzes", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = Quiz.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "204", description = "There are no active Quizzes", content = {
                    @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    // get active quizzes
    @GetMapping("/active")
    public ResponseDto<List<QuizDto>> getActiveQuizzes(){
        return quizService.getActiveQuizzes();
    }

}
