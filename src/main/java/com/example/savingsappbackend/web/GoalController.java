package com.example.savingsappbackend.web;

import com.example.savingsappbackend.models.Goal;
import com.example.savingsappbackend.models.dto.EditGoalDTO;
import com.example.savingsappbackend.models.dto.NewGoalDTO;
import com.example.savingsappbackend.service.GoalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GoalController {
    private final GoalService service;

    public GoalController(GoalService goalService) {
        this.service = goalService;
    }

    @GetMapping("/goal/{id}")
    public ResponseEntity<Goal> getGoalById(@PathVariable Long id){
        if(id == null){
            return ResponseEntity.notFound().build();
        }else if(this.service.getById(id) == null){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(this.service.getById(id));
        }
    }

    @RequestMapping("goals/{userId}")
    public ResponseEntity<List<Goal>> getGoalById(@PathVariable Long userId,
                                                  @RequestParam Integer size,
                                                  @RequestParam Integer pageSize){

        // buni bilmeydim nasil yapam form ile mi gundereciz bilmeydim o yuzden koydum sal bule
        if(size == null){
            size = 1;
        }
        if(pageSize == null){
            pageSize = 1;
        }

        if(userId == null){
            return ResponseEntity.notFound().build();
        }else if(this.service.getById(userId) == null){
            return ResponseEntity.notFound().build();
        }else{
            List<Goal> goals = this.service.getAllGoals(userId, size, pageSize);
            return ResponseEntity.ok(goals);
        }
    }

    @PostMapping("/newGoal")
    public ResponseEntity<Goal> newGoal(@RequestBody NewGoalDTO data, @RequestHeader("Authorization") String token) {
        // burda da bilmeydim nasil alaciz userId yi o yuzden koyaym bule. duzeltireciz.
        Goal goal = this.service.newGoal(data.currentAmt, data.targetAmt, data.title, data.targetDate, data.description, data.id);
        return ResponseEntity.ok(goal);
    }

    @PostMapping("/editGoal")
    public ResponseEntity<Goal> newGoal(@RequestBody EditGoalDTO data){
        // burda da bilmeydim nasil alaciz userId yi o yuzden koyaym bule. duzeltireciz.
        Goal goal = this.service.editGoal(data.goalId, data.currentAmt, data.targetAmt, data.title, data.targetDate, data.description);
        return ResponseEntity.ok(goal);
    }

    @GetMapping("/delete/{goalId}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long goalId){
        if(goalId == null){
            return ResponseEntity.notFound().build();
        }else if(this.service.getById(goalId) == null){
            return ResponseEntity.notFound().build();
        }else{
            this.service.deleteGoal(goalId);
            return ResponseEntity.ok().build();
        }
    }
}
