package uz.lib.library.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.lib.library.dto.ResponseDto;
import uz.lib.library.dto.TokenDto;
import uz.lib.library.dto.UserDto;
import uz.lib.library.model.User;
import uz.lib.library.service.UserService;

import java.util.List;

@Tag(name = "User", description = "User Service APIs")
@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserResources {

    private final UserService userService;

    @Operation(
            summary = "Create a new User",
            description = "Need to send UserDto to this endpoint to create new user",
            tags = { "users", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = User.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping("sign-up")
    public ResponseDto<UserDto> addUser(@RequestBody @Valid UserDto usersDto) {
        return userService.addUser(usersDto);
    }

    @Operation(summary = "Update a User", tags = { "users", "patch" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = User.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }) })
    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("edit-user")
    public ResponseDto<UserDto> updateUser(@RequestBody UserDto usersDto){
        return userService.updateUser(usersDto);
    }

    @Operation(
            summary = "Retrieve all Users",
            description = "Get all Users",
            tags = { "users", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = User.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "204", description = "There are no Users", content = {
                    @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("user-list")
    public ResponseDto<List<UserDto>> getAllUsers(){
        return userService.getAllUsers();
    }

    @Operation(summary = "Delete a User by Id", tags = { "users", "delete" })
    @ApiResponses({ @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("delete-by-id")
    public ResponseDto<UserDto> deleteUserById(@RequestParam Integer id){
        return userService.deleteUserById(id);
    }

    @Operation(
            summary = "Retrieve a User by Id",
            description = "Get a User object by specifying its id.",
            tags = { "users", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = User.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("by-id")
    public ResponseDto<UserDto> getUserById(@RequestParam Integer id){
        return userService.getUserById(id);
    }

    @Operation(
            summary = "Retrieve a User by phone number",
            description = "Get a User object by specifying its phone number.",
            tags = { "users", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = User.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("by-phone-number")
    public ResponseDto<UserDto> getUserByPhoneNumber(@RequestParam String phoneNumber){
        return userService.getUserByPhoneNumber(phoneNumber);
    }

    @Operation(
            summary = "Get User token",
            description = "Need to send TokenDto to this endpoint to get token",
            tags = { "users", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = TokenDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping("get-token")
    public ResponseDto<String> getToken(@RequestBody TokenDto getTokenDto){
        return userService.getToken(getTokenDto);
    }
}
