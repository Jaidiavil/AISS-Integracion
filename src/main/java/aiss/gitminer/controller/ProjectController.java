package aiss.gitminer.controller;

import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.CommentRepository;
import aiss.gitminer.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gitminer/projects")
public class ProjectController {

    @Autowired
    ProjectRepository projectRepository;

    @GetMapping
    public List<Project> findProject(){
        return projectRepository.findAll();
    }

    @GetMapping("/{id}")
    public Project findProjectById(@PathVariable String id){
        Optional<Project> result = projectRepository.findById(id);
        return result.get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Project createComment(@RequestBody @Valid Project project) {
        Project newProject = projectRepository.save(new Project(
                project.getId(),
                project.getName(),
                project.getWebUrl(),
                project.getCommits(),
                project.getIssues()));
        return newProject;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateComment(@RequestBody @Valid Comment updatedComment, @PathVariable String id) {
        Optional<Project> projectData = projectRepository.findById(id);
        Project dataProject = projectData.get();
        dataProject.setName(dataProject.getName());
        dataProject.setWebUrl(dataProject.getWebUrl());
        dataProject.setCommits(dataProject.getCommits());
        dataProject.setIssues(dataProject.getIssues());

        projectRepository.save(dataProject);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable String id) {
        if (projectRepository.existsById(id)){
            projectRepository.deleteById(id);
        }
    }
}
