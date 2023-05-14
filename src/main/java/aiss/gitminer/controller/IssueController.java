package aiss.gitminer.controller;

import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Commit;
import aiss.gitminer.model.Issue;
import aiss.gitminer.model.User;
import aiss.gitminer.repository.CommitRepository;
import aiss.gitminer.repository.IssueRepository;
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
@RequestMapping("/gitminer/issues")
public class IssueController {

    @Autowired
    IssueRepository issueRepository;

    @Operation(
            summary = "Retrieve a list of issues",
            description = "Get a list of issues",
            tags = {"issues", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of issues",
                    content = {@Content(schema = @Schema(implementation = Issue.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Issues not found",
                    content = {@Content(schema = @Schema())})
    })

    @GetMapping
    public List<Issue> findIssue(@RequestParam(required = false) String authorId, @RequestParam(required = false) String state){
        if (authorId != null && state != null) {
            return issueRepository.findIssuesByAuthorIdAndState(authorId, state);
        } else if (authorId != null) {
            return issueRepository.findIssuesByAuthorId(authorId);
        } else if (state != null) {
            return issueRepository.findIssuesByState(state);
        } else {
            return issueRepository.findAll();
        }
    }

    @GetMapping("/{id}")
    public Issue findIssueById(@PathVariable String id){
        Optional<Issue> result = issueRepository.findById(id);
        return result.get();
    }

    @GetMapping("/{id}/comments")
    public List<Comment> findCommentsByIssue(@PathVariable String id){
        return issueRepository.findById(id).get().getComments();
    }

}
