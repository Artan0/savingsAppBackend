package com.example.savingsappbackend.web;

import com.example.savingsappbackend.models.Goal;
import com.example.savingsappbackend.models.dto.EditGoalDTO;
import com.example.savingsappbackend.models.dto.GoalDTO;
import com.example.savingsappbackend.models.dto.UserDto;
import com.example.savingsappbackend.service.GoalService;
import com.example.savingsappbackend.service.UserService;
import com.example.savingsappbackend.config.UserAuthenticationProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Goal> getGoalById(@PathVariable Long id,
                                            @RequestHeader("Authorization") String token
                                            ) {
        String userEmail = userAuthenticationProvider.getEmailFromToken(token);
        UserDto user = userService.findByEmail(userEmail);
        Goal goal = this.service.getById(id);
        if (goal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(goal);
    }

    @GetMapping("/goals")
    public ResponseEntity<Map<String, Object>> getGoalsByUserId(
            @RequestHeader("Authorization") String token,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search
    ) {
        String userEmail = userAuthenticationProvider.getEmailFromToken(token);
        UserDto user = userService.findByEmail(userEmail);

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Goal> goalsPage = service.getAllGoals(user.getId(), search, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("totalItems", goalsPage.getTotalElements());
        response.put("totalPages", goalsPage.getTotalPages());
        response.put("currentPage", goalsPage.getNumber());
        response.put("goals", goalsPage.getContent());

        return ResponseEntity.ok(response);
    }


    @PostMapping("/newGoal")
    public ResponseEntity<Goal> createNewGoal(@RequestBody GoalDTO data, @RequestHeader("Authorization") String token) {
        String userEmail = userAuthenticationProvider.getEmailFromToken(token);
        UserDto user = userService.findByEmail(userEmail);
        Goal goal = this.service.newGoal(data.getCurrentAmt(), data.getTargetAmt(), data.getTitle(),
                data.getTargetDate(), data.getDescription(), user.getId());
        return ResponseEntity.ok(goal);
    }
    @PostMapping("/editGoal")
    public ResponseEntity<Goal> editGoal(@RequestBody EditGoalDTO data, @RequestHeader("Authorization") String token) {
        String userEmail = userAuthenticationProvider.getEmailFromToken(token);
        UserDto user = userService.findByEmail(userEmail);
        Goal goal = this.service.editGoal(data.getGoalId(), data.getCurrentAmount(), data.getTargetAmount(),
                data.getTitle(), data.getTargetDate(), data.getDescription());
        return ResponseEntity.ok(goal);
    }

    @DeleteMapping("/delete/{goalId}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long goalId, @RequestHeader("Authorization") String token) {
        String userEmail = userAuthenticationProvider.getEmailFromToken(token);
        UserDto user = userService.findByEmail(userEmail);
        if (this.service.getById(goalId) == null) {
            return ResponseEntity.notFound().build();
        }
        this.service.deleteGoal(goalId);
        return ResponseEntity.ok().build();
    }
}
