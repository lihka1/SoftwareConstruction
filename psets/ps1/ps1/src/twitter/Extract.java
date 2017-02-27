/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        if (tweets.size() == 0){
            return new Timespan(Instant.now(), Instant.now());
        }
        Instant start = tweets.get(0).getTimestamp();
        Instant end = tweets.get(0).getTimestamp();
        
        for (int i = 0; i < tweets.size(); i++) {
            if (tweets.get(i).getTimestamp().isBefore(start)){
                start = tweets.get(i).getTimestamp();
            }
            else if (tweets.get(i).getTimestamp().isAfter(end)){
                end = tweets.get(i).getTimestamp();
            }
        }
        return new Timespan(start, end);
        //throw new RuntimeException("not implemented");
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        if (tweets.isEmpty()){
            return new HashSet<String>();
        }
        Set<String> mentionedUsers = new HashSet<>();
        for (Tweet tweet: tweets){
            String[] text = tweet.getText().split(" ");
            for (String word: text){
                if (word.length() > 0 && word.charAt(0) == '@'){
                    if (valid(word)){
                        mentionedUsers.add(word.substring(1).toLowerCase());
                    }
                }
            }
        }
        return mentionedUsers;
        //throw new RuntimeException("not implemented");
    }
    
    /**
     * Determint whether the word is valid according to tweet author.
     * 
     * @param word word to be checked whether it is valid or not
     * @return whether the word is a valid username according to tweets author
     */
    private static boolean valid(String word){
        if (word.length() < 2){
            return false;
        }
        for (int i = 1; i < word.length(); i++) {
            char c = word.charAt(i);
            if (Character.isDigit(c) || Character.isAlphabetic(c) || 
                    c == '_' || c == '-'){
                return true;
            }
        }
        return false;
    }
}
