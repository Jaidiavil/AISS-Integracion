package aiss.gitminer.controller;

import aiss.gitminer.model.Project;
import aiss.gitminer.model.User;
import aiss.gitminer.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gitminer/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Operation(
            summary = "Retrieve a list of users",
            description = "Get a list of users",
            tags = {"users", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of users",
                    content = {@Content(schema = @Schema(implementation = User.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Users not found",
                    content = {@Content(schema = @Schema())})
    })

    @GetMapping
    public List<User> findUsers(){
        return userRepository.findAll();
    }
}
