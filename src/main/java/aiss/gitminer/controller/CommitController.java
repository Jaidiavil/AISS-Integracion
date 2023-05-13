package aiss.gitminer.controller;

import aiss.gitminer.model.Commit;
import aiss.gitminer.model.User;
import aiss.gitminer.repository.CommitRepository;
import aiss.gitminer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public List<Commit> findCommit(){
        return commitRepository.findAll();
    }

    @GetMapping("/{id}")
    public Commit findCommitById(@PathVariable String id){
        Optional<Commit> result = commitRepository.findById(id);
        return result.get();
    }

    @GetMapping("/{id}/{email}")
    public List<Commit> findCommitByEmail(String email){
        return commitRepository.findByEmail(email);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Commit createCommit(@RequestBody @Valid Commit commit) {
        Commit newCommit = commitRepository.save(new Commit(
                commit.getId(),
                commit.getTitle(),
                commit.getMessage(),
                commit.getAuthorName(),
                commit.getAuthorEmail(),
                commit.getAuthoredDate(),
                commit.getCommitterName(),
                commit.getCommitterEmail(),
                commit.getCommittedDate(),
                commit.getWebUrl()));
        return newCommit;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@RequestBody @Valid Commit updatedCommit, @PathVariable String id) {
        Optional<Commit> commitData = commitRepository.findById(id);
        Commit dataCommit = commitData.get();
        dataCommit.setTitle(updatedCommit.getTitle());
        dataCommit.setMessage(updatedCommit.getMessage());
        dataCommit.setAuthorName(updatedCommit.getAuthorName());
        dataCommit.setAuthorEmail(updatedCommit.getAuthorEmail());
        dataCommit.setAuthoredDate(updatedCommit.getAuthoredDate());
        dataCommit.setCommitterName(updatedCommit.getCommitterName());
        dataCommit.setCommitterEmail(updatedCommit.getCommitterEmail());
        dataCommit.setCommittedDate(updatedCommit.getCommittedDate());
        dataCommit.setWebUrl(updatedCommit.getWebUrl());
        commitRepository.save(dataCommit);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommit(@PathVariable String id) {
        if (commitRepository.existsById(id)){
            commitRepository.deleteById(id);
        }
    }
}
