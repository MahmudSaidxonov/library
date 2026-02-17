package uz.lib.library.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.lib.library.dto.RatingDto;
import uz.lib.library.dto.ResponseDto;
import uz.lib.library.model.Rating;
import uz.lib.library.model.User;
import uz.lib.library.service.RatingService;

import java.io.IOException;
import java.util.List;

@Tag(name = "Rating", description = "Rating Service APIs")
@RestController
@RequestMapping("rating")
@RequiredArgsConstructor
public class RatingResources {

    private final RatingService ratingService;

    @Operation(
            summary = "Create a new Rating",
            description = "Need to send user email and password to this endpoint to add new user-rating",
            tags = { "ratings", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = Rating.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping()
    public ResponseDto<RatingDto> addRating(@RequestParam String email, @RequestParam String password) {
        return ratingService.addRating(email, password);
    }

    @Operation(summary = "Update a Rating", tags = { "ratings", "patch" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = Rating.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
    @PatchMapping()
    public ResponseDto<RatingDto> updateRating(@RequestBody RatingDto ratingDto) {
        return ratingService.updateRating(ratingDto);
    }

    @Operation(
            summary = "Download ratings (excel)",
            description = "Download ratings",
            tags = { "ratings", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = Rating.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping()
    public ResponseEntity<ByteArrayResource> getExcel() throws IOException {
        return ratingService.getRatingsExcel();
    }

    @Operation(
            summary = "Retrieve a Rating by Id",
            description = "Get a Rating object by specifying its id.",
            tags = { "ratings", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Rating.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/by-id")
    public ResponseDto<RatingDto> getRatingById(Integer id) {
        return ratingService.getRatingById(id);
    }

    @Operation(
            summary = "Retrieve all Ratings",
            description = "Get all Ratings",
            tags = { "ratings", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = Rating.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "204", description = "There are no Ratings", content = {
                    @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/all")
    public ResponseDto<List<RatingDto>> getAllRatings() {
        return ratingService.getAllRatings();
    }
}
