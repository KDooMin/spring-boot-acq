package com.acq.collection.acqcollectionbook.homepage;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainController {
    @GetMapping({"/","/main"})
    public ModelAndView homePage() {
        return new ModelAndView("main/main");
    }
}
