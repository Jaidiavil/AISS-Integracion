package aiss.gitminer.controller;

import aiss.gitminer.exception.NotFoundExcept;
import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Commit;
import aiss.gitminer.repository.CommentRepository;
import aiss.gitminer.repository.CommitRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;



import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gitminer/comments")
public class CommentController {
    @Autowired
    CommentRepository commentRepository;

    @Operation(
            summary = "Retrieve a list of comments",
            description = "Get a list of comments",
            tags = {"comments", "get"})
    @ApiResponses({
            @ApiResponse(responseCode =  "200", description = "List of comments",
                    content = {@Content(schema = @Schema(implementation = Comment.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Comments not found",
                    content = {@Content(schema = @Schema())})
    })

    @GetMapping
    public List<Comment> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String order) {

        Pageable paging;

        if(order != null) {
            if (order.startsWith("-"))
                paging = PageRequest.of(page, size, Sort.by(order.substring(1)).descending());
            else
                paging = PageRequest.of(page, size, Sort.by(order).ascending());
        }
        else
            paging = PageRequest.of(page, size);

        return commentRepository.findAll(paging).getContent();
    }

    @Operation(
            summary = "Retrieve a comments",
            description = "Get a comment",
            tags = {"comment", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "A comment",
                    content = {@Content(schema = @Schema(implementation = Comment.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Comment not found",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{id}")
    public Comment findOne(@PathVariable String id) throws NotFoundExcept{
        Optional<Comment> result = commentRepository.findById(id);
        if (!result.isPresent()) {
            throw new NotFoundExcept();
        }
        return result.get();
    }

}
