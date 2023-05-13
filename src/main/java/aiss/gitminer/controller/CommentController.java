package aiss.gitminer.controller;

import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Commit;
import aiss.gitminer.repository.CommentRepository;
import aiss.gitminer.repository.CommitRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public List<Comment> findComment(){
        return commentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Comment findCommentById(@PathVariable String id){
        Optional<Comment> result = commentRepository.findById(id);
        return result.get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createComment(@RequestBody @Valid Comment comment) {
        Comment newComment = commentRepository.save(new Comment(
                comment.getId(),
                comment.getBody(),
                comment.getAuthor(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()));
        return newComment;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateComment(@RequestBody @Valid Comment updatedComment, @PathVariable String id) {
        Optional<Comment> commentData = commentRepository.findById(id);
        Comment dataComment = commentData.get();
        dataComment.setBody(dataComment.getBody());
        dataComment.setAuthor(dataComment.getAuthor());
        dataComment.setCreatedAt(dataComment.getCreatedAt());
        dataComment.setUpdatedAt(dataComment.getUpdatedAt());

        commentRepository.save(dataComment);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable String id) {
        if (commentRepository.existsById(id)){
            commentRepository.deleteById(id);
        }
    }

}
