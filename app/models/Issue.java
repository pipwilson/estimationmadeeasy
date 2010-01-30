package models;

import java.util.*;
import play.modules.gae.*;

public class Issue {

    public String id;
    public String name;

    public ArrayList<UserVote> votes;

    public Issue(String id) {
        this.id = id;
    }

    /**
     * Return the vote by a particular user
     */
    public String myVote() {

        String response = "";

        // loop over the votes and get the one where vote.name = name
        if (votes != null) {
            for (UserVote vote : votes) {
                if (vote.name.equalsIgnoreCase(GAE.getUser().getNickname())) {
                    response = "You voted "+vote.score;
                }
            }
        }

        return response;
    }


    /**
     * Get the most frequently occurring vote.
     * In case of a tie, highest vote wins, I think :)
     * Really we are just getting the mode of the votes
     */
    public int getConsensusVote() {
        int mostFrequentScore = -1;
        int maxNumberOfVotes = 0;

        if (votes == null) {
            return -1;
        }

        // loop over the array of values
        for (UserVote vote1 : votes) {

            // how many of this number have there been?
            int numberOfVotes = 0;

            // check this number against the others in the array
            for (UserVote vote2 : votes) {
                // if they match then increment count
                // don't worry about comparing with self because all numbers will get that
                if (vote1.score == vote2.score) {
                    numberOfVotes++;
                } else {
                    // todo
                    // compare the values for similarity so we can flag up massive differences in vote scores
                }
            }

            // if this is the most of any number then update
            // maxCount and mostFrequentScore
            if (numberOfVotes > maxNumberOfVotes) {
                maxNumberOfVotes = numberOfVotes;
                mostFrequentScore = vote1.score;
            }
        }

        // return the number there has been the most of!
        return mostFrequentScore;
    }

}