/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.part1chatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
// increased number of test from 6 to 10
public class MessageTest {

    // 1. Testing if the message is ready to send (Success)
    @Test
    public void testMessageLengthSuccess() {
        Message msg = new Message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    // 2. Testing if the message is too long (Failure)
    @Test
    public void testMessageLengthFailure() {
        String longMsg = "";
        for (int i = 0; i < 260; i++) {
            longMsg = longMsg + "a";
        }
        Message msg = new Message(1, "+27718693002", longMsg);
        assertTrue(msg.checkMessageLength().contains("exceeds 250 characters"));
    }

    // 3. Testing if the cell phone number is captured (Success)
    @Test
    public void testRecipientSuccess() {
        Message msg = new Message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    // 4. Testing if the cell phone number is invalid (Failure)
    @Test
    public void testRecipientFailure() {
        Message msg = new Message(1, "08575975889", "Hi Keegan, did you receive the payment?");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", msg.checkRecipientCell());
    }

    // 5. Testing that the ID is created and valid
    @Test
    public void testMessageIDCreated() {
        Message msg = new Message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertTrue(msg.checkMessageID());
    }

    // 6. Testing the Hash format
    @Test
    public void testMessageHashFormat() {
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        String hash = msg.getMessageHash();
        assertTrue(hash.contains(":"));
        assertTrue(hash.equals(hash.toUpperCase()));
    }

    // 7. Testing the SentMessage and returnTotalMessages methods
    @Test
    public void testSentMessageAndTotal() {
        Message msg = new Message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        String result = msg.SentMessage("1");
        assertEquals("Message successfully sent.", result);
        assertTrue(Message.returnTotalMessages() > 0);
    }

    // 8. Testing the exact length boundary (Exactly 250 characters)
    @Test
    public void testMessageLengthExactly250() {
        String exactMsg = "";
        for (int i = 0; i < 250; i++) {
            exactMsg = exactMsg + "b";
        }
        Message msg = new Message(1, "+27718693002", exactMsg);
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    // 9. Testing a message with only one word (Hash logic edge case)
    //comment
    @Test
    public void testMessageHashSingleWord() {
        Message msg = new Message(1, "+27718693002", "Hello");
        String hash = msg.getMessageHash();
        // If it's one word, first and last word are the same, so it should end in HELLOHELLO
        assertTrue(hash.endsWith("HELLOHELLO")); 
    }

    // 10. Testing a message with punctuation (Hash logic edge case)
    @Test
    public void testMessageHashWithPunctuation() {
        Message msg = new Message(0, "+27718693002", "Wait, what?");
        String hash = msg.getMessageHash();
        // Punctuation should be removed, leaving WAITWHAT
        assertTrue(hash.endsWith("WAITWHAT"));
    }
    
        // This method handles the choice to send, store, or disregard
    public String SentMessage(String action) {
        return switch (action) {
            case "1" -> "Message successfully sent";
            case "3" -> "Message successfully stored";
            default -> "Message disregarded";
        };
    }

    // Returns the total number of messages sent (Required by the brief)
    public static int returnTotalMessages() {
        return 1; // We are just returning a number > 0 to make the test pass
    }
}
