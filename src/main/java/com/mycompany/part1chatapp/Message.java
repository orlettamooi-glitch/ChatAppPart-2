/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.part1chatapp;

import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Student
 */
public final class Message {
   
   // Instance fields 
    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String messageText;
    private final String messageHash;

    // Static tracking (no arrays)
    private static int totalMessages = 0;
    private static final StringBuilder allSentMessages = new StringBuilder();

    // Constructors 

    /** A basic constructor: generates a random 10-digit ID.
     * @param messageNumber
     * @param recipient
     * @param messageText */
    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    /** Test-only constructor with a fixed ID (used to test the exact hash).
     * @param messageNumber
     * @param recipient
     * @param messageText
     * @param fixedID */
    public Message(int messageNumber, String recipient, String messageText, String fixedID) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = fixedID;
        this.messageHash = createMessageHash();
    }

    // ID generation (uses substring) 

    private String generateMessageID() {
        Random rand = new Random();
        long seed = (long) (rand.nextDouble() * 9000000000L) + 1000000000L;
        String raw = String.valueOf(seed);
        String padded = raw + "0000000000";
        return padded.substring(0, 10);   // exact 10-digit ID via substring
    }

    // Validation

    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        if (recipient != null && recipient.matches("^\\+27\\d{9}$")) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain "
                + "an international code. Please correct the number and try again.";
    }

    public String checkMessageLength() {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        }
        int excess = messageText.length() - 250;
        return "Message exceeds 250 characters by " + excess + ", please reduce the size.";
    }

    // Hash (uses substring + string manipulation)

    public String createMessageHash() {
        String firstTwo = messageID.substring(0, 2);

        String cleanMessage = messageText.trim();
        String firstWord;
        String lastWord;

        int firstSpace = cleanMessage.indexOf(" ");
        if (firstSpace != -1) {
            firstWord = cleanMessage.substring(0, firstSpace);
            int lastSpace = cleanMessage.lastIndexOf(" ");
            lastWord = cleanMessage.substring(lastSpace + 1);
        } else {
            firstWord = cleanMessage;
            lastWord = cleanMessage;
        }

        firstWord = firstWord.replaceAll("[?,.!']", "").toUpperCase();
        lastWord  = lastWord.replaceAll("[?,.!']", "").toUpperCase();

        return firstTwo + ":" + messageNumber + ":" + firstWord + lastWord;
    }

    // ---------- Actions ----------

    /** 1 = Send, 2 = Disregard, 3 = Store (JSON).
     * @param choice
     * @return  */
    public String SentMessage(int choice) {
        switch (choice) {
            case 1 -> {
                totalMessages++;
                allSentMessages.append(displayMessageDetails()).append("\n\n");
                return "Message successfully sent.";
            }
            case 2 -> {
                return "Press 0 to delete the message.";
            }
            case 3 -> {
                storeMessage();
                return "Message successfully stored.";
            }
            default -> {
                return "Invalid option.";
            }
        }
    }

    // Reporting (has no arrays) 

    public static String printMessages() {
        if (allSentMessages.length() == 0) {
            return "No messages sent yet.";
        }
        return allSentMessages.toString();
    }

    public static int returnTotalMessages() {
        return totalMessages;
    }

    /** Used by @BeforeEach in tests so counters don't leak between runs. */
    public static void resetForTesting() {
        totalMessages = 0;
        allSentMessages.setLength(0);
    }

    // JSON persistence 

    /**
     * Stores this message as a JSON line in messages.json.
     *Attribution:
     * JSON file writing technique adapted from:
     * Oracle. (2024). File I/O (Featuring NIO.2). Java SE Documentation.
     * Available at: https://docs.oracle.com/javase/tutorial/essential/io/file.html
     * (Accessed: 2026).
     */
    public void storeMessage() {
        String json = "{\n"
                + "  \"messageID\": \"" + messageID + "\",\n"
                + "  \"messageHash\": \"" + messageHash + "\",\n"
                + "  \"recipient\": \"" + recipient + "\",\n"
                + "  \"message\": \"" + messageText + "\"\n"
                + "}\n";

        try (FileWriter writer = new FileWriter("messages.json", true)) {
            writer.write(json);
        } catch (IOException e) {
            System.out.println("Error saving message: " + e.getMessage());
        }
    }

    // Display 

    public String displayMessageDetails() {
        return "Message ID: " + messageID + "\n"
                + "Message Hash: " + messageHash + "\n"
                + "Recipient: " + recipient + "\n"
                + "Message: " + messageText;
    }

    // Getters

    public String getMessageID()     { return messageID; }
    public String getMessageHash()   { return messageHash; }
    public String getRecipient()     { return recipient; }
    public String getMessageText()   { return messageText; }
    public int    getMessageNumber() { return messageNumber; }
}