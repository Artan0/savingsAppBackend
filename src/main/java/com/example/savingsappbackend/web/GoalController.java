package com.example.savingsappbackend.web;

import com.example.savingsappbackend.models.Goal;
import com.example.savingsappbackend.models.dto.EditGoalDTO;
import com.example.savingsappbackend.models.dto.GoalDTO;
import com.example.savingsappbackend.models.dto.UserDto;
import com.example.savingsappbackend.service.GoalService;
import com.example.savingsappbackend.service.UserService;
import com.example.savingsappbackend.config.UserAuthenticationProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GoalController {
    private final GoalService service;
    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    public GoalController(GoalService goalService, UserService userService, UserAuthenticationProvider userAuthenticationProvider) {
        this.service = goalService;
        this.userService = userService;
        this.userAuthenticationProvider = userAuthenticationProvider;
    }

    @GetMapping("/goal/{id}")
    public ResponseEntity<Goal> getGoalById(@PathVariable Long id) {
        Goal goal = this.service.getById(id);
        if (goal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(goal);
    }

    @GetMapping("/goals/{userId}")
    public ResponseEntity<List<Goal>> getGoalsByUserId(@PathVariable Long userId,
                                                       @RequestParam(defaultValue = "1") Integer size,
                                                       @RequestParam(defaultValue = "1") Integer pageSize) {
        List<Goal> goals = this.service.getAllGoals(userId, size, pageSize);
        return ResponseEntity.ok(goals);
    }

    @PostMapping("/newGoal")
    public ResponseEntity<Goal> createNewGoal(@RequestBody GoalDTO data, @RequestHeader("Authorization") String token) {
        String userEmail = userAuthenticationProvider.getEmailFromToken(token);
        UserDto user = userService.findByEmail(userEmail);
        Goal goal = this.service.newGoal(data.getCurrentAmt(), data.getTargetAmt(), data.getTitle(),
                data.getTargetDate(), data.getDescription(), user.getId());
        return ResponseEntity.ok(goal);
    }

//    @PostMapping("/editGoal")
//    public ResponseEntity<Goal> editGoal(@RequestBody EditGoalDTO data) {
//        Goal goal = this.service.editGoal(data.getGoalId(), data.getCurrentAmt(), data.getTargetAmt(),
//                data.getTitle(), data.getTargetDate(), data.getDescription());
//        return ResponseEntity.ok(goal);
//    }

    @DeleteMapping("/delete/{goalId}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long goalId) {
        if (this.service.getById(goalId) == null) {
            return ResponseEntity.notFound().build();
        }
        this.service.deleteGoal(goalId);
        return ResponseEntity.ok().build();
    }
}
