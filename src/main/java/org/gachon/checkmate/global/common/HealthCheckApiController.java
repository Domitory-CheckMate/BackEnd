package org.gachon.checkmate.global.common;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckApiController {
    @RequestMapping("/")
    public String CheckmateServer() {
        return "CheckmateServer!";
    }
}
