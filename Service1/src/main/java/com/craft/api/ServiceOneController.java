package com.craft.api;

import com.craft.model.UserReport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public interface ServiceOneController {

    @RequestMapping(value = "/code", method = RequestMethod.POST)
    ResponseEntity<UserReport> submitCode(@RequestParam("questionId") String questionId, @RequestParam("userId") String userId,
                                          @RequestParam("competitionId") String compId, @RequestBody String code);
}
