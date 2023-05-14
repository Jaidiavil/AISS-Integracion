package aiss.gitminer.controller;

import aiss.gitminer.model.Commit;
import aiss.gitminer.model.User;
import aiss.gitminer.repository.CommitRepository;
import aiss.gitminer.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gitminer/commits")
public class CommitController {
    @Autowired
    CommitRepository commitRepository;

    @Operation(
            summary = "Retrieve a list of commits",
            description = "Get a list of commits",
            tags = {"commits", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of commits",
                    content = {@Content(schema = @Schema(implementation = Commit.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Commits not found",
                    content = {@Content(schema = @Schema())})
    })

    @GetMapping
    public List<Commit> findCommits(@RequestParam(required = false) String author_email){
        if (author_email == null) {
            return commitRepository.findAll();
        } else {
            return commitRepository.findByAuthorEmail(author_email);
        }
    }

    @Operation(
            summary = "Retrieve a commit",
            description = "Get a commit",
            tags = {"commit", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "A commit",
                    content = {@Content(schema = @Schema(implementation = Commit.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Commit not found",
                    content = {@Content(schema = @Schema())})
    })

    @GetMapping("/{id}")
    public Commit findCommitById(@PathVariable String id){
        Optional<Commit> result = commitRepository.findById(id);
        return result.get();
    }

}
