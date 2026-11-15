/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.part1chatapp;

import java.io.File;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
// increased number of test from 6 to 10
public class MessageTest {

private static final String TEST_FILE = "messages.json";

    @BeforeEach
    public void setUp() {
        new File(TEST_FILE).delete();
        Message.resetForTesting();
    }

    @AfterEach
    public void tearDown() {
        new File(TEST_FILE).delete();
    }

    // ---------- Part 2 ----------

    @Test
    public void testMessageLengthSuccess() {
        Message msg = new Message(1, "Developer", "+27718693002",
                "Hi Mike, can you join us for dinner tonight?");
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    @Test
    public void testMessageLengthFailure() {
        Message msg = new Message(1, "Developer", "+27718693002", "a".repeat(260));
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.",
                msg.checkMessageLength());
    }

    @Test
    public void testMessageLengthExactly250() {
        Message msg = new Message(1, "Developer", "+27718693002", "a".repeat(250));
        assertEquals("Message ready to send.", msg.checkMessageLength());
    }

    @Test
    public void testRecipientSuccess() {
        Message msg = new Message(1, "Developer", "+27718693002", "Hi Mike");
        assertEquals("Cell phone number successfully captured.", msg.checkRecipientCell());
    }

    @Test
    public void testRecipientFailure() {
        Message msg = new Message(2, "Developer", "08575975889", "Hi Keegan");
        assertEquals("Cell phone number is incorrectly formatted or does not contain "
                + "an international code. Please correct the number and try again.",
                msg.checkRecipientCell());
    }

    @Test
    public void testExactMessageHash() {
        Message msg = new Message(0, "Developer", "+27718693002",
                "Hi Mike, can you join us for dinner tonight?", "0012345678");
        assertEquals("00:0:HITONIGHT", msg.createMessageHash());
    }

    @Test
    public void testHashLoop() {
        String[] messages = {"Hi Mike", "Wait, what?", "Hello"};
        String[] expected = {"00:1:HIMIKE", "00:2:WAITWHAT", "00:3:HELLOHELLO"};
        for (int i = 0; i < messages.length; i++) {
            Message msg = new Message(i + 1, "Developer", "+27718693002",
                    messages[i], "0099999999");
            assertEquals(expected[i], msg.createMessageHash());
        }
    }

    // ---------- Part 3 test data ----------

    private void populateTestData() {
        new Message(1, "Developer", "+27834557896",
                "Did you get the cake?", "0011111111").SentMessage("1");
        new Message(2, "Developer", "+27838884567",
                "Where are you? You are late! I have asked you to be on time.",
                "0022222222").SentMessage("3");
        new Message(3, "Developer", "+27834484567",
                "Yohoooo, I am at your gate.", "0033333333").SentMessage("2");
        new Message(4, "Developer", "0838884567",
                "It is dinner time !", "0838884567").SentMessage("1");
        new Message(5, "Developer", "+27838884567",
                "Ok, I am leaving without you.", "0055555555").SentMessage("3");
    }

    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        populateTestData();
        assertEquals("Did you get the cake?", Message.sentMessages[0].getMessageText());
        assertEquals("It is dinner time !", Message.sentMessages[1].getMessageText());
        assertEquals(2, Message.sentCount);
    }

    @Test
    public void testDisregardedArrayPopulated() {
        populateTestData();
        assertEquals(1, Message.disregardedCount);
        assertEquals("Yohoooo, I am at your gate.",
                Message.disregardedMessages[0].getMessageText());
    }

    @Test
    public void testStoredArrayPopulated() {
        populateTestData();
        assertEquals(2, Message.storedCount);
    }

    @Test
    public void testDisplayLongestMessage() {
        populateTestData();
        assertEquals("Where are you? You are late! I have asked you to be on time.",
                Message.getLongestStoredMessage());
    }

    @Test
    public void testSearchForMessageID() {
        populateTestData();
        assertEquals("Recipient: 0838884567 | Message: It is dinner time !",
                Message.searchByMessageID("0838884567"));
    }

    @Test
    public void testSearchForMessageIDNotFound() {
        populateTestData();
        assertEquals("Message ID not found.", Message.searchByMessageID("0000000000"));
    }

    @Test
    public void testSearchAllMessagesForRecipient() {
        populateTestData();
        String expected = "Message: Where are you? You are late! I have asked you to be on time.\n"
                + "Message: Ok, I am leaving without you.";
        assertEquals(expected, Message.searchByRecipient("+27838884567"));
    }

    @Test
    public void testSearchRecipientNotFound() {
        populateTestData();
        assertEquals("No messages found for this recipient.",
                Message.searchByRecipient("+27000000000"));
    }

    @Test
    public void testDeleteMessageUsingHash() {
        populateTestData();
        String hash = Message.storedMessages[0].getMessageHash();
        String result = Message.deleteByHash(hash);
        assertTrue(result.contains("\"Where are you? You are late! I have asked you to be on time.\" successfully deleted."));
        assertEquals(1, Message.storedCount);
    }

    @Test
    public void testDeleteRemovesFromParallelArrays() {
        populateTestData();
        String hash = Message.storedMessages[0].getMessageHash();
        Message.deleteByHash(hash);

        assertEquals(4, Message.hashCount);
        assertEquals(4, Message.idCount);
        for (int i = 0; i < Message.hashCount; i++) {
            assertNotEquals(hash, Message.messageHashes[i]);
        }
    }

    @Test
    public void testDeleteHashNotFound() {
        populateTestData();
        assertEquals("Message hash not found.", Message.deleteByHash("99:9:NOPE"));
    }

    @Test
    public void testReportContainsMessageID() {
        populateTestData();
        String report = Message.generateReport();
        assertTrue(report.contains("Message ID: 0022222222"));
        assertTrue(report.contains("Flag: Stored"));
        assertTrue(report.contains("Where are you?"));
    }

    @Test
    public void testParallelArraysPopulated() {
        populateTestData();
        assertEquals(5, Message.hashCount);
        assertEquals(5, Message.idCount);
    }

    // ---------- JSON round-trip ----------

    @Test
    public void testJsonRoundTripPreservesHash() {
        Message m = new Message(2, "Developer", "+27838884567",
                "Where are you? You are late! I have asked you to be on time.",
                "0022222222");
        m.SentMessage("3");
        String originalHash = m.getMessageHash();

        Message.resetForTesting();
        Message.loadStoredMessagesFromJSON();

        assertEquals(1, Message.storedCount);
        assertEquals(originalHash, Message.storedMessages[0].getMessageHash());
        assertEquals("0022222222", Message.storedMessages[0].getMessageID());
        assertEquals(originalHash, Message.messageHashes[0]);
        assertEquals("0022222222", Message.messageIDs[0]);
    }
}