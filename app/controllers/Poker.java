package controllers;

import java.util.*;

import play.*;
import play.cache.*;
import play.mvc.*;
import play.data.validation.*;
import play.modules.gae.*;

import models.*;
import jira.*;

public class Poker extends Application {

    @Before
    static void checkConnected() {
        if (GAE.getUser() == null) {
            Application.login();
        } else {
            Cache.set("nickname", GAE.getUser().getNickname());
            ArrayList users = (ArrayList) Cache.get("users");
            if (users == null) {
                users = new ArrayList();
            }
            String nickname = GAE.getUser().getNickname();
            if (!users.contains(nickname)) {
                users.add(nickname);
            }

            Cache.set("users", users);
        }
    }

    @Before
    static void addDefaults() {
        renderArgs.put("nickname", Cache.get("nickname", String.class));
        renderArgs.put("gameName", Cache.get("gameName", String.class));
        renderArgs.put("users", Cache.get("users", ArrayList.class));
        renderArgs.put("issues", Cache.get("issues", ArrayList.class));

        // loop over issues, adding new renderArgs for each issue
        ArrayList<Issue> issues = Cache.get("issues", ArrayList.class);

        renderArgs.put("issues", issues);
    }

    public static void index() {
        render();
    }

    public static void createGame(@Required String gameName) {
        if (validation.hasErrors()) {
            flash.error("You have to actually provide a name for the game!");
            index();
        }

        Cache.set("gameName", gameName);
        index();
    }

    public static void addIssues(@Required String issues) {
        if (validation.hasErrors()) {
            flash.error("You have to actually provide some issues!");
            index();
        }

        ArrayList<Issue> issueList = (ArrayList<Issue>) Cache.get("issues");
        if (issueList == null) {
            issueList = new ArrayList<Issue>();
        }

        ArrayList<String> issueIds = new ArrayList<String>();
        for (Issue issue : issueList) {
            issueIds.add(issue.id);
        }

        for (String issueId : Arrays.asList(issues.split("\r\n"))) {
            if (!issueIds.contains(issueId)) {
                Issue i = new Issue(issueId);
                issueList.add(i);
            }
        }

        XmlRpcJiraClient client = new XmlRpcJiraClient();
        //client.doStuff();

        Cache.set("issues", issueList);
        index();
    }

    public static void vote(String issueId, int score) {
        storeVote(GAE.getUser().getNickname(), issueId, score);
        index();
    }

    private static void storeVote(String nickname, String issueId, int score) {

        ArrayList<Issue> issues = Cache.get("issues", ArrayList.class);

        for (Issue issue : issues) {
            if (issue.id.equalsIgnoreCase(issueId)) {
                UserVote vote = new UserVote(nickname, score);
                if (issue.votes == null) {
                    issue.votes = new ArrayList<UserVote>();
                }
                issue.votes.add(vote);
            }
        }

        Cache.set("issues", issues);
    }
}
