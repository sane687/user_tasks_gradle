package com.alex.demo.service;

import com.alex.demo.model.EnumStatus;
import com.alex.demo.model.Role;
import com.alex.demo.model.Task;
import com.alex.demo.model.User;
import com.alex.demo.repository.RoleRepository;
import com.alex.demo.repository.TaskRepository;
import com.alex.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserService{

    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TaskRepository taskRepository;
    @Autowired
    public UserService(BCryptPasswordEncoder encoder, UserRepository userRepository, RoleRepository roleRepository, TaskRepository taskRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.taskRepository = taskRepository;
    }

    public List<User> findAll(){
        List<User> userList = userRepository.findAll();
        userList.removeIf(user -> user.getRoles().contains(roleRepository.findByRoleName("ADMIN")));
        return userList;
    }
    public List<User> findAllUsers(){
        List<User> userList = userRepository.findAll();
        userList.removeIf(user ->
                user.getRoles().contains(roleRepository.findByRoleName("ADMIN"))
                || user.getRoles().contains(roleRepository.findByRoleName("MODERATOR")));
        return userList;
    }

    public boolean save(User user){
        userRepository.save(user);
        return true;
    }

    public boolean saveUser(User user){
        User DBUser = userRepository.findByUsername(user.getUsername());
        if(DBUser != null){
            return false;
        }
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleRepository.findByRoleName("USER"));
        user.setRoles(roleSet);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setLocked(false);
        userRepository.save(user);
        return true;
    }

    public boolean creteTask(Task task, User user){
        task.setStatus(EnumStatus.IN_PROGRESS);
        task.setUser(user);
        taskRepository.save(task);
        return true;
    }

    public User findUserById(Long id){
        return userRepository.findUserById(id);
    }
    public void deleteById(Long id){
        userRepository.deleteById(id);
    }


    public String banUser(Long userId) {
        User user = userRepository.findUserById(userId);
        if (user.isLocked()){
            return "no changes";
        }
        user.setLocked(true);
        userRepository.save(user);
        return "banned";
    }

    public String unbanUser(Long userId) {
        User user = userRepository.findUserById(userId);
        if (!user.isLocked()){
            return "no changes";
        }
        user.setLocked(false);
        userRepository.save(user);
        return "unbanned";
    }


}
