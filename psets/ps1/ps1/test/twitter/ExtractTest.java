/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     * Testing Strategy
     * 
     * Partition the input as follows
     * 
     * tweets.length() :0, 1, 2
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "mit", "@mit rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet4 = new Tweet(4, "mit1", "@mit @akhil rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet5 = new Tweet(2, "mit2", "@ @mit @akhil @Akhil akhil2.new@gmail.com rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet6 = new Tweet(2, "mit2", "", d2);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // covers tweets.size() == 0
    @Test
    public void testGetTimeSpan0Tweets(){
        Timespan timespan = Extract.getTimespan(Arrays.asList());
        assertEquals(timespan.getStart(), timespan.getEnd());
    }
    
    // covers tweets.size() == 1
    @Test
    public void testGetTimeSpan1Tweets(){
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
        assertEquals(d1, timespan.getStart());
        assertEquals(d1, timespan.getEnd());
    }
    
    // covers tweets.size() > 0 or two tweets
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    // covers no tweets
    @Test
    public void testGetMentionedUsers0Tweets() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList());

        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    
    // covers one mention, one tweet
    @Test
    public void testGetMentionedUsersOneMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3));
        
        assertTrue("expected online one element in set", mentionedUsers.contains("mit"));
    }
    
    // covers > 1 mentions, list of tweets
    @Test
    public void testGetMentionedUsersManyMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3, tweet4));
        
        assertTrue("expected set", mentionedUsers.containsAll(Arrays.asList("mit", "akhil")));
    }
    
    // covers upper-case and lowercase differences and also invalid type like @gmail.com
    @Test
    public void testGetMentionedUsersDuplicatesMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet5));
        
        assertEquals(2 ,mentionedUsers.size());
        assertTrue(mentionedUsers.contains("mit"));
        assertTrue(mentionedUsers.contains("akhil"));
    }
    
    // covers when the text is empty string
    @Test
    public void testGetMentionedUsersTextEmptyMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet6));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
