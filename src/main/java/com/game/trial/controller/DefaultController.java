package com.game.trial.controller;

import com.game.trial.base.Session;
import com.game.trial.base.gameDetails.Player;
import com.game.trial.service.GameValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;


@Controller
public class DefaultController {

    @Value("${project.session.max-interval-in-seconds}")
    private int sessionDuration;
    private final Session session;
    private final GameValidatorService validatorService;


    public DefaultController(@Autowired Session session, @Autowired GameValidatorService validatorService) {
        this.session = session;
        this.validatorService = validatorService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String getIndex(HttpSession httpSession, Model model) {
        if (httpSession.isNew()) {
            return "auth";
        }
        model.addAttribute("games", validatorService.getActiveGamesWaitingStart());
        return "index";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public String session(@RequestParam String name, HttpSession httpSession) {
        httpSession.setMaxInactiveInterval(sessionDuration);
        session.setPlayer(new Player(name));
        return "index";
    }
}
