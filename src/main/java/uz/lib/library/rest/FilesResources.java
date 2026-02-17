package uz.lib.library.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.lib.library.dto.ResponseDto;
import uz.lib.library.model.Quiz;
import uz.lib.library.service.FilesService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("userFiles")
@RequiredArgsConstructor
public class FilesResources {

    private final FilesService userFilesService;

    @Operation(
            summary = "Create a new File",
            description = "Need to send File to this endpoint to add new file",
            tags = { "files", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = Quiz.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping("upload")
    public ResponseDto<Integer> uploadFile(@RequestParam Integer userId, @RequestParam String type, @RequestPart("file") MultipartFile file){
        return userFilesService.fileUpload(userId, type, file);
    }

    @Operation(
            summary = "Retrieve a File by type and userId",
            description = "Get a File object by specifying its id.",
            tags = { "files", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Quiz.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("get-user-image")
    public ResponseDto<List<byte[]>> getFileById(@RequestParam Integer userId, @RequestParam String type) throws IOException {
        return userFilesService.getFileById(userId, type);
    }

}
