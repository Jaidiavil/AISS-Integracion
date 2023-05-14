package aiss.gitminer.controller;

import aiss.gitminer.exception.NotFoundExcept;
import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.CommentRepository;
import aiss.gitminer.repository.ProjectRepository;
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
@RequestMapping("/gitminer")
public class ProjectController {

    @Autowired
    ProjectRepository projectRepository;

    @Operation(
            summary = "Retrieve a list of projects",
            description = "Get a list of projects",
            tags = {"projects", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of projects",
                    content = {@Content(schema = @Schema(implementation = Project.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Projects not found",
                    content = {@Content(schema = @Schema())})
    })

    @GetMapping
    public List<Project> findProject(){
        return projectRepository.findAll();
    }

    @Operation(
            summary = "Retrieve a project",
            description = "Get a project",
            tags = {"project", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "A project",
                    content = {@Content(schema = @Schema(implementation = Project.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Project not found",
                    content = {@Content(schema = @Schema())})
    })

    @GetMapping("/{id}")
    public Project findProjectById(@PathVariable String id) throws NotFoundExcept {
        Optional<Project> result = projectRepository.findById(id);
        if (!result.isPresent()) {
            throw new NotFoundExcept();
        }
        return result.get();
    }

    @Operation(
            summary = "Create a project",
            description = "Create a project",
            tags = {"create", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "A created project",
                    content = {@Content(schema = @Schema(implementation = Project.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Project not created",
                    content = {@Content(schema = @Schema())})
    })

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

    @Operation(
            summary = "Update a project",
            description = "Update a project",
            tags = {"update", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "A updated project",
                    content = {@Content(schema = @Schema(implementation = Project.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Update not created",
                    content = {@Content(schema = @Schema())})
    })

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

    @Operation(
            summary = "Delete a project",
            description = "Delete a project",
            tags = {"delete", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "A deleted project",
                    content = {@Content(schema = @Schema(implementation = Project.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Deletion not created",
                    content = {@Content(schema = @Schema())})
    })

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable String id) {
        if (projectRepository.existsById(id)){
            projectRepository.deleteById(id);
        }
    }
}
