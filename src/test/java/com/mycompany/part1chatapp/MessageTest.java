/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.part1chatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author Student
 */
public class MessageTest {
    
 private static final String VALID_RECIPIENT   = "+27718693002";
    private static final String INVALID_RECIPIENT = "08575975889";
    private static final String MESSAGE_1 = "Hi Mike, can you join us for dinner tonight?";
    private static final String MESSAGE_2 = "Hi Keegan, did you receive the payment?";

    @BeforeEach
    public void reset() {
        Message.resetForTesting();
    }

    // 1. Message length — success
    @Test
    public void testMessageLengthSuccess() {
        Message msg = new Message(1, VALID_RECIPIENT, MESSAGE_1);
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    // 2. Message length — failure
    @Test
    public void testMessageLengthFailure() {
        String longMsg = "a".repeat(260);
        Message msg = new Message(1, VALID_RECIPIENT, longMsg);
        assertEquals("Message exceeds 250 characters by 10, please reduce the size.",
                msg.checkMessageLength());
    }

    // 3. Recipient — success
    @Test
    public void testRecipientSuccess() {
        Message msg = new Message(1, VALID_RECIPIENT, MESSAGE_1);
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    // 4. Recipient — failure
    @Test
    public void testRecipientFailure() {
        Message msg = new Message(1, INVALID_RECIPIENT, MESSAGE_2);
        assertEquals("Cell phone number is incorrectly formatted or does not contain "
                + "an international code. Please correct the number and try again.",
                msg.checkRecipientCell());
    }

    // 5. Hash exact = "00:0:HITONIGHT" (POE Test Case 1)
    @Test
    public void testMessageHashExactForMessageOne() {
        Message msg = new Message(0, VALID_RECIPIENT, MESSAGE_1, "0012345678");
        assertEquals("00:0:HITONIGHT", msg.getMessageHash());
    }

    // 6. Loop through remaining hashes (no arrays — uses if/else inside loop)
    @Test
    public void testMessageHashesInLoop() {
        for (int i = 0; i < 2; i++) {
            String messageText;
            String expectedHash;

            if (i == 0) {
                messageText = MESSAGE_1;
                expectedHash = "00:0:HITONIGHT";
            } else {
                messageText = MESSAGE_2;
                expectedHash = "00:1:HIPAYMENT";
            }

            Message msg = new Message(i, VALID_RECIPIENT, messageText, "0012345678");
            assertEquals(expectedHash, msg.getMessageHash(),
                    "Hash mismatch at index " + i);
        }
    }
}