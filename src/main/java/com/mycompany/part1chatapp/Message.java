/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.part1chatapp;
import java.util.Random;
/**
 *
 * @author Student
 */
public final class Message {
   
    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String messageText;
    private final String messageHash;

    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    // Generates a random 10-digit message ID
    private String generateMessageID() {
        Random rand = new Random();
        long id = (long) (rand.nextDouble() * 9_000_000_000L) + 1_000_000_000L;
        return String.valueOf(id);
    }

    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        if (recipient.length() <= 10 && recipient.startsWith("+")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // Extracts the first word without using an array
    private String getFirstWord(String text) {
        text = text.trim();
        int spaceIndex = text.indexOf(" ");
        if (spaceIndex == -1) return text.replaceAll("[^a-zA-Z]", "").toUpperCase();
        return text.substring(0, spaceIndex).replaceAll("[^a-zA-Z]", "").toUpperCase();
    }

    // Extracts the last word without using an array
    private String getLastWord(String text) {
        text = text.trim();
        int spaceIndex = text.lastIndexOf(" ");
        if (spaceIndex == -1) return text.replaceAll("[^a-zA-Z]", "").toUpperCase();
        return text.substring(spaceIndex + 1).replaceAll("[^a-zA-Z]", "").toUpperCase();
    }

    // Creates the hash using string manipulation
    public String createMessageHash() {
        String firstTwo = messageID.substring(0, 2);
        String firstWord = getFirstWord(messageText);
        String lastWord = getLastWord(messageText);
        return firstTwo + ":" + messageNumber + ":" + firstWord + lastWord;
    }

    public String checkMessageLength() {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = messageText.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }

    public String sentMessage() {
        return "Message successfully sent";
    }

    public String storeMessage() {
        return "Message successfully stored";
    }

    // Display details in the required order
    public String displayMessageDetails() {
        return "Message ID: " + messageID + "\n" +
               "Message Hash: " + messageHash + "\n" +
               "Recipient: " + recipient + "\n" +
               "Message: " + messageText;
    }

    // Getters
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public int getMessageNumber() { return messageNumber; }
    
}
