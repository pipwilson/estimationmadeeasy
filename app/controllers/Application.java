package controllers;

import java.util.ArrayList;

import play.cache.*;
import play.mvc.*;
import play.modules.gae.*;

import com.google.appengine.api.users.*;

public class Application extends Controller {

    public static void index() {
        if(GAE.isLoggedIn()) {
            Poker.index();
        }
        render();
    }

    public static void login() {
        GAE.login("Application.index");
    }

    public static void logout() {
        GAE.logout("Application.index");
    }
}