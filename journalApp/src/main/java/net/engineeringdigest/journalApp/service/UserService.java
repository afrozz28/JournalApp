package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepo;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveNewUser(User userEntry){
        userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
        userEntry.setRoles(Arrays.asList("USER"));
        userRepo.save(userEntry);
    }

    public void saveAdmin(User adminEntry){
        adminEntry.setPassword(passwordEncoder.encode(adminEntry.getPassword()));
        adminEntry.setRoles(Arrays.asList("USER", "ADMIN"));
        userRepo.save(adminEntry);
    }

    public void saveUser(User user){
        userRepo.save(user);
    }

    public List<User> getAll(){
        return userRepo.findAll();
    }

    public Optional<User> findById(ObjectId id){
        return userRepo.findById(id);
    }
    public void deleteUserId(ObjectId id){
        userRepo.deleteById(id);
    }

    public void deleteByUsername(String username){
        userRepo.deleteByUserName(username);
    }

    public User findByUserName(String userName){
        return userRepo.findByUserName(userName);
    }


}
