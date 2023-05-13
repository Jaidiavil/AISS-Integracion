package aiss.gitminer.controller;

import aiss.gitminer.model.User;
import aiss.gitminer.repository.UserRepository;
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

    @GetMapping
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createProject(@RequestBody @Valid User user) {
        User newUser = userRepository.save(new User(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getAvatarUrl(),
                user.getWebUrl()));
        return newUser;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@RequestBody @Valid User updatedUser, @PathVariable String id) {
        Optional<User> userData = userRepository.findById(id);
        User dataUser = userData.get();
        dataUser.setUsername(updatedUser.getUsername());
        dataUser.setName(updatedUser.getName());
        dataUser.setAvatarUrl(updatedUser.getAvatarUrl());
        dataUser.setWebUrl(updatedUser.getWebUrl());
        userRepository.save(dataUser);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id) {
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
        }
    }







}
