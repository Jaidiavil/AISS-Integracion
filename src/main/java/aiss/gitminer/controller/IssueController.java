package aiss.gitminer.controller;

import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Commit;
import aiss.gitminer.model.Issue;
import aiss.gitminer.model.User;
import aiss.gitminer.repository.CommitRepository;
import aiss.gitminer.repository.IssueRepository;
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
    public List<Comment> findCommentsByIssue(String id){
        return issueRepository.findById(id).get().getComments();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Issue createIssue(@RequestBody @Valid Issue issue) {
        Issue newIssue = issueRepository.save(new Issue(
                issue.getId(),
                issue.getRefId(),
                issue.getTitle(),
                issue.getDescription(),
                issue.getState(),
                issue.getCreatedAt(),
                issue.getUpdatedAt(),
                issue.getClosedAt(),
                issue.getLabels(),
                issue.getAuthor(),
                issue.getAssignee(),
                issue.getUpvotes(),
                issue.getDownvotes(),
                issue.getWebUrl(),
                issue.getComments()
                ));
        return newIssue;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateIssue(@RequestBody @Valid Commit updatedIssue, @PathVariable String id) {
        Optional<Issue> issueData = issueRepository.findById(id);
        Issue dataIssue = issueData.get();
        dataIssue.setTitle(dataIssue.getTitle());
        dataIssue.setRefId(dataIssue.getRefId());
        dataIssue.setTitle(dataIssue.getTitle());
        dataIssue.setDescription(dataIssue.getDescription());
        dataIssue.setState(dataIssue.getState());
        dataIssue.setCreatedAt(dataIssue.getCreatedAt());
        dataIssue.setUpdatedAt(dataIssue.getUpdatedAt());
        dataIssue.setClosedAt(dataIssue.getClosedAt());
        dataIssue.setLabels(dataIssue.getLabels());
        dataIssue.setAuthor(dataIssue.getAuthor());
        dataIssue.setAssignee(dataIssue.getAssignee());
        dataIssue.setUpvotes(dataIssue.getUpvotes());
        dataIssue.setDownvotes(dataIssue.getDownvotes());
        dataIssue.setWebUrl(dataIssue.getWebUrl());
        dataIssue.setComments(dataIssue.getComments());

        issueRepository.save(dataIssue);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIssue(@PathVariable String id) {
        if (issueRepository.existsById(id)){
            issueRepository.deleteById(id);
        }
    }
}
