package com.spring.security.jwt.controllers;

import com.spring.security.jwt.data.models.User;
import com.spring.security.jwt.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

	private UserService userService;

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
