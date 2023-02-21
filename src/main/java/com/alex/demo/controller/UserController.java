package com.alex.demo.controller;

import com.alex.demo.model.User;
import com.alex.demo.service.TaskService;
import com.alex.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes("user")
public class UserController {

    private final UserService userService;
    private final TaskService taskService;
    @Autowired
    public UserController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/login")
    public String index(@RequestParam(required = false) String error, Model model){
        if (error!=null && error.equals("true")){
            model.addAttribute("errorMessage", "Wrong password or username");
        }
        return "login";
    }


    @GetMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id")  // позволяет доступ пользователю только к его странице (id авторизованного совпадает с запрашиваемым)
    public String userPage(@PathVariable Long id, Model model){
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("inProgressAndWaitingForApproveTasks", taskService.findAllInProgressAndWaitingForApproveTasks(id));
        model.addAttribute("completedTasks", taskService.findAllCompletedTasks(id));
        model.addAttribute("expiredTasks", taskService.findAllExpiredTasks(id));
        return "user/user-page";
    }

    @GetMapping("/{userId}/task/{taskId}")
    @PreAuthorize("#userId == authentication.principal.id")
    public String showTask(@PathVariable Long taskId,
                           @PathVariable Long userId,
                           Model model){
        model.addAttribute("task", taskService.findTaskById(taskId));
        return "user/task-details";
    }

    @PostMapping("task/{taskId}")
    public String setTaskCompleted(@PathVariable Long taskId, @ModelAttribute("user") User user){
        taskService.setTaskCompleted(taskId);
        return "redirect:/" + user.getId();
    }

    @GetMapping("/user-create")
    public String createUserForm(Model model){
        model.addAttribute("user", new User());
        return "user-create";

    }
    @PostMapping("/user-create")
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult result, Model model){
        if (result.hasErrors()){
            return "user-create";
        }
        if(!userService.saveUser(user)){
            model.addAttribute("usernameError", "Username already taken");
            return "user-create";
        }
        return "successPage";
    }
}
