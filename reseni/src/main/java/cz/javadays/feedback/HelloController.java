package cz.javadays.feedback;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Úvodní stránka – rychlá kontrola, že aplikace běží (lokálně i v Azure).
 */
@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "Ahoj JavaDays 2026! Hodnocení přednášek najdeš na /api/talks/{talkId}/ratings";
    }
}
