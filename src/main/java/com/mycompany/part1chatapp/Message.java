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
   
   // Attributes
    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String messageText;
    private final String messageHash;

    // Constructor for Message
    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        
        // Generate the ID first, because the hash needs it
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    // Generate a random 10-digit message ID
    private String generateMessageID() {
        Random rand = new Random();
        // Generates a number between 1000000000 and 9999999999
        long id = (long) (rand.nextDouble() * 9000000000L) + 1000000000L;
        return String.valueOf(id);
    }

    // Check that the ID is not more than 10 characters
    public boolean checkMessageID() {
        if (messageID.length() <= 10) {
            return true;
        } else {
            return false;
        }
    }

    // Check that the recipient number is no more than 10 characters and starts with +
    public String checkRecipientCell() {
        // Must start with + and be a valid length (Part 1 used +27 + 9 digits = 12 characters)
        if (recipient.startsWith("+") && recipient.length() <= 13) { 
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // Check if the message is 250 characters or less
    public String checkMessageLength() {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        } else {
            int excess = messageText.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
    }

    // Create the Message Hash
    public String createMessageHash() {
        // Get the first two digits of the ID using substring
        String firstTwo = messageID.substring(0, 2);
        
        // Find the first and last words without using arrays
        String cleanMessage = messageText.trim();
        String firstWord = "";
        String lastWord = "";
        
        // Get the first word
        int firstSpace = cleanMessage.indexOf(" ");
        if (firstSpace != -1) {
            firstWord = cleanMessage.substring(0, firstSpace).toUpperCase();
            // Get the last word
            int lastSpace = cleanMessage.lastIndexOf(" ");
            lastWord = cleanMessage.substring(lastSpace + 1).toUpperCase();
        } else {
            // If the message is just one word
            firstWord = cleanMessage.toUpperCase();
            lastWord = cleanMessage.toUpperCase();
        }

        // Remove punctuation from the words
        firstWord = firstWord.replace("?", "").replace(",", "").replace(".", "").replace("!", "");
        lastWord = lastWord.replace("?", "").replace(",", "").replace(".", "").replace("!", "");

        // Format: 00:0:HITONIGHT
        return firstTwo + ":" + messageNumber + ":" + firstWord + lastWord;
    }

    // Return message details in the correct order
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
}
