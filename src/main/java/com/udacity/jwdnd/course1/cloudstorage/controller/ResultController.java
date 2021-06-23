package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Implements a controller for the result endpoint.
 */
@Controller
@RequestMapping("/result")
public class ResultController {

    /**
     * Gets the result page
     * @return string for result.html page.
     */
    @GetMapping()
    public String getResultPage() {
        return "result";
    }
}
