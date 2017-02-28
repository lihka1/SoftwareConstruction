/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be the empty set, or A may not even exist
 * as a key in the map; this is true even if A is followed by other people in the network.
 * Twitter usernames are not case sensitive, so "ernie" is the same as "ERNie".
 * A username should appear at most once as a key in the map or in any given
 * map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

    /**
     * Guess who might follow whom, from evidence found in tweets.
     * 
     * @param tweets
     *            a list of tweets providing the evidence, not modified by this
     *            method.
     * @return a social network (as defined above) in which Ernie follows Bert
     *         if and only if there is evidence for it in the given list of
     *         tweets.
     *         One kind of evidence that Ernie follows Bert is if Ernie
     *         @-mentions Bert in a tweet. This must be implemented. Other kinds
     *         of evidence may be used at the implementor's discretion.
     *         All the Twitter usernames in the returned social network must be
     *         either authors or @-mentions in the list of tweets.
     */
    public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
        Map<String, List<Tweet>> authorTweetList = authorWiseTweetList(tweets);
        Map<String, Set<String>> authorGuessFollow = new HashMap<>();
        
        for(String author: authorTweetList.keySet()){
            Set<String> mentionedUsers = Extract.getMentionedUsers(authorTweetList.get(author));
            authorGuessFollow.put(author, mentionedUsers);
        }
        return authorGuessFollow;
        //throw new RuntimeException("not implemented");
    }
    
    private static Map<String, List<Tweet>> authorWiseTweetList(List<Tweet> tweets){
        Set<String> authorOfTweets = new HashSet<>();
        for (Tweet tweet: tweets){
            authorOfTweets.add(tweet.getAuthor().toLowerCase());
        }
        
        Map<String, List<Tweet>> authorWiseTweets = new HashMap<>(); 
        for (String author: authorOfTweets){
            authorWiseTweets.put(author, new ArrayList<Tweet>());
        }
        for (Tweet tweet: tweets){
            authorWiseTweets.get(tweet.getAuthor().toLowerCase()).add(tweet);
        }
        return authorWiseTweets;
    }

    /**
     * Find the people in a social network who have the greatest influence, in
     * the sense that they have the most followers.
     * 
     * @param followsGraph
     *            a social network (as defined above)
     * @return a list of all distinct Twitter usernames in followsGraph, in
     *         descending order of follower count.
     */
    public static List<String> influencers(Map<String, Set<String>> followsGraph) {
        Map<String, Integer> authorNoOfFollowers = new HashMap<>();
        for (String author: followsGraph.keySet()){
            authorNoOfFollowers.put(author, followsGraph.get(author).size());
        }
        Map<String, Integer> authorFollowersOrdered = sortHashMapByValues((HashMap<String, Integer>)authorNoOfFollowers); 
        List<String> answerList = new ArrayList<>();
        answerList.addAll(authorFollowersOrdered.keySet());
        return answerList;
        
        //hrow new RuntimeException("not implemented");
    }
    
    //http://stackoverflow.com/questions/8119366/sorting-hashmap-by-values
    private static LinkedHashMap<String, Integer> sortHashMapByValues(
            HashMap<String, Integer> passedMap) {
        List<String> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Integer> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap<String, Integer> sortedMap =
            new LinkedHashMap<>();

        Iterator<Integer> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Integer val = valueIt.next();
            Iterator<String> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                String key = keyIt.next();
                Integer comp1 = passedMap.get(key);
                Integer comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }
   
    

}
