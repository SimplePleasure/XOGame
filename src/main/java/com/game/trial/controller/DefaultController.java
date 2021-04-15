package com.game.trial.controller;

import com.game.trial.base.Session;
import com.game.trial.base.gameDetails.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;


@Controller
public class DefaultController {
    @Autowired
    private Session session;

    public DefaultController(@Autowired Session session) {
        this.session = session;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String getIndex(HttpSession httpSession) {
        if (httpSession.isNew()) {
            return "auth";
        }
        return "index";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public String session(@RequestParam String name, HttpSession httpSession) {
        httpSession.setMaxInactiveInterval(60);
        session.setPlayer(new Player(name));
        return "index";
    }
}
