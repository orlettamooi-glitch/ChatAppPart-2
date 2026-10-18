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
public class MessageTest {
    
    // Testing if the message is ready to send (Success)
    @Test
    public void testMessageLengthSuccess() {
        Message msg = new Message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    // Testing if the message is too long (Failure)
    @Test
    public void testMessageLengthFailure() {
        String longMsg = "";
        for (int i = 0; i < 260; i++) {
            longMsg = longMsg + "a";
        }
        Message msg = new Message(1, "+27718693002", longMsg);
        assertTrue(msg.checkMessageLength().contains("exceeds 250 characters"));
    }

    // Testing if the cell phone number is captured (Success)
    @Test
    public void testRecipientSuccess() {
        Message msg = new Message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    // Testing if the cell phone number is invalid (Failure)
    @Test
    public void testRecipientFailure() {
        Message msg = new Message(1, "08575975889", "Hi Keegan, did you receive the payment?");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", msg.checkRecipientCell());
    }

    // Testing that the ID is created and valid
    @Test
    public void testMessageIDCreated() {
        Message msg = new Message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        assertTrue(msg.checkMessageID());
    }

    // Testing the Hash format
    @Test
    public void testMessageHashFormat() {
        Message msg = new Message(0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        String hash = msg.getMessageHash();
        assertTrue(hash.contains(":"));
        assertTrue(hash.equals(hash.toUpperCase()));
    }
}
