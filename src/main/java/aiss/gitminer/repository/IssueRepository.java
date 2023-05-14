package aiss.gitminer.repository;

import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Issue;
import aiss.gitminer.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, String> {


    Page<Issue> findIssuesByAuthorIdAndState(String authorId, String state, Pageable paging);
    Page<Issue> findIssuesByAuthorId(String authorId, Pageable paging);
    Page<Issue> findIssuesByState(String state, Pageable paging);
}
