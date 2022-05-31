package com.aibles.springjwt.controllers;

import com.aibles.springjwt.data.models.User;
import com.aibles.springjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

  private final UserService userService;
  @Autowired
  public TestController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/all")
  public String allAccess() {
    return "Public Content.";
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public String userAccess() {
    return "User Content.";
  }

  @GetMapping("/mod")
  @PreAuthorize("hasRole('MODERATOR')")
  public String moderatorAccess() {
    return "Moderator Board.";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess() {
    return "Admin Board.";
  }

  @DeleteMapping("/admin/delete/{user_id}")
  @PreAuthorize(("hasRole('ADMIN')"))
  public ResponseEntity<Void> deleteUser(@PathVariable("user_id") int userId) {
    final User deletedUser = userService.deleteUser(userId);
    if (deletedUser == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(HttpStatus.OK);
    }
  }

  @PutMapping("/admin/update/{user_id}")
  @PreAuthorize(("hasRole('ADMIN')"))
  public ResponseEntity<User> updateUser(@PathVariable("user_id") int userId,
                                                  @RequestBody User user) {
    final User updatedUser = userService.updateUser(userId, user);
    if (updatedUser == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
  }

}
