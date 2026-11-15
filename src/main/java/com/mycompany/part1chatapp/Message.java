/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.part1chatapp;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Student
 */
public final class Message {
   
    private final String messageID;
    private final int messageNumber;
    private final String sender;
    private final String recipient;
    private final String messageText;
    private final String messageHash;
    private String flag;

    // PART 3 ARRAYS
    public static Message[] sentMessages = new Message[100];
    public static int sentCount = 0;

    public static Message[] disregardedMessages = new Message[100];
    public static int disregardedCount = 0;

    public static Message[] storedMessages = new Message[100];
    public static int storedCount = 0;

    public static String[] messageHashes = new String[100];
    public static int hashCount = 0;

    public static String[] messageIDs = new String[100];
    public static int idCount = 0;

    private static int totalMessages = 0;

    // ---------- Constructors ----------

    public Message(int messageNumber, String sender, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.sender = sender;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    /** Test constructor: fixed ID, hash recomputed. */
    public Message(int messageNumber, String sender, String recipient,
                   String messageText, String fixedID) {
        this.messageNumber = messageNumber;
        this.sender = sender;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = fixedID;
        this.messageHash = createMessageHash();
    }

    /**
     * JSON-load constructor: keeps BOTH the stored ID and the stored hash.
     * Fixes the "hash recomputed on reload" bug.
     */
    public Message(int messageNumber, String sender, String recipient,
                   String messageText, String fixedID, String fixedHash) {
        this.messageNumber = messageNumber;
        this.sender = sender;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = fixedID;
        this.messageHash = fixedHash;   // keep exactly what was saved
    }

    Message(int i, String recipient, String messageText) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private String generateMessageID() {
        Random rand = new Random();
        long seed = (long) (rand.nextDouble() * 9000000000L) + 1000000000L;
        String padded = String.valueOf(seed) + "0000000000";
        return padded.substring(0, 10);
    }

    // ---------- Validation ----------

    public boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
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
        return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
    }

    // ---------- Hash ----------

    public String createMessageHash() {
        String firstTwo = messageID.substring(0, 2);
        String clean = messageText.trim();
        String firstWord, lastWord;

        int firstSpace = clean.indexOf(' ');
        if (firstSpace != -1) {
            firstWord = clean.substring(0, firstSpace);
            lastWord  = clean.substring(clean.lastIndexOf(' ') + 1);
        } else {
            firstWord = lastWord = clean;
        }
        firstWord = firstWord.replaceAll("[?,.!']", "").toUpperCase();
        lastWord  = lastWord.replaceAll("[?,.!'\\\"]", "").toUpperCase();

        return firstTwo + ":" + messageNumber + ":" + firstWord + lastWord;
    }

    // ---------- Actions ----------

    public String SentMessage(String choice) {
        switch (choice) {
            case "1":
                this.flag = "Sent";
                totalMessages++;
                sentMessages[sentCount++] = this;
                addHashID(this);
                return "Message successfully sent.";
            case "2":
                this.flag = "Disregarded";
                disregardedMessages[disregardedCount++] = this;
                addHashID(this);
                return "Press 0 to delete the message.";
            case "3":
                this.flag = "Stored";
                storedMessages[storedCount++] = this;
                addHashID(this);
                saveStoredMessagesToJSON();
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }

    /**
     * Confirms the disregard. If the user enters "0", the message is removed
     * from the disregarded array (it was tentatively added so the user could
     * see the "Press 0..." prompt; the real removal happens here).
     */
    public String confirmDisregard(String input) {
        if (!"0".equals(input)) {
            // user chose to keep: remove from disregarded array
            for (int i = 0; i < disregardedCount; i++) {
                if (disregardedMessages[i] == this) {
                    for (int j = i; j < disregardedCount - 1; j++) {
                        disregardedMessages[j] = disregardedMessages[j + 1];
                    }
                    disregardedMessages[--disregardedCount] = null;
                    break;
                }
            }
            return "Message kept.";
        }
        return "Message disregarded.";
    }

    /** Adds the message hash + ID exactly once. */
    private static void addHashID(Message m) {
        if (m.messageHash != null && !containsHash(m.messageHash)) {
            messageHashes[hashCount++] = m.messageHash;
        }
        if (m.messageID != null && !containsID(m.messageID)) {
            messageIDs[idCount++] = m.messageID;
        }
    }

    private static boolean containsHash(String hash) {
        for (int i = 0; i < hashCount; i++) {
            if (messageHashes[i].equals(hash)) return true;
        }
        return false;
    }

    private static boolean containsID(String id) {
        for (int i = 0; i < idCount; i++) {
            if (messageIDs[i].equals(id)) return true;
        }
        return false;
    }

    public static int returnTotalMessages() {
        return totalMessages;
    }

    public static void resetForTesting() {
        totalMessages = 0;
        sentCount = 0;
        disregardedCount = 0;
        storedCount = 0;
        hashCount = 0;
        idCount = 0;
        java.util.Arrays.fill(sentMessages, null);
        java.util.Arrays.fill(disregardedMessages, null);
        java.util.Arrays.fill(storedMessages, null);
        java.util.Arrays.fill(messageHashes, null);
        java.util.Arrays.fill(messageIDs, null);
    }

    // ---------- JSON persistence ----------

    public void storeMessage() {
        saveStoredMessagesToJSON();
    }

    public static void saveStoredMessagesToJSON() {
        JSONArray array = new JSONArray();
        for (int i = 0; i < storedCount; i++) {
            Message m = storedMessages[i];
            JSONObject obj = new JSONObject();
            obj.put("messageID", m.messageID);
            obj.put("messageHash", m.messageHash);
            obj.put("sender", m.sender);
            obj.put("recipient", m.recipient);
            obj.put("message", m.messageText);
            obj.put("flag", m.flag);
            array.put(obj);
        }
        try (FileWriter writer = new FileWriter("messages.json")) {
            writer.write(array.toString(2));
        } catch (IOException e) {
            System.out.println("Error saving messages: " + e.getMessage());
        }
    }

    /**
     * Loads stored messages from JSON. Preserves the stored hash, clears
     * stored/hash/ID arrays first to avoid duplicates, and uses the new
     * 6-arg constructor so the hash is not recomputed.
     */
    public static void loadStoredMessagesFromJSON() {
        storedCount = 0;
        hashCount = 0;
        idCount = 0;
        java.util.Arrays.fill(storedMessages, null);
        java.util.Arrays.fill(messageHashes, null);
        java.util.Arrays.fill(messageIDs, null);

        try {
            String content = new String(Files.readAllBytes(Paths.get("messages.json")));
            if (content.trim().isEmpty()) return;
            JSONArray array = new JSONArray(content);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Message m = new Message(
                        i + 1,
                        obj.getString("sender"),
                        obj.getString("recipient"),
                        obj.getString("message"),
                        obj.getString("messageID"),
                        obj.getString("messageHash"));   // keep stored hash
                m.flag = obj.getString("flag");
                storedMessages[storedCount++] = m;
                addHashID(m);
            }
        } catch (Exception e) {
            // no file or invalid JSON — silently continue
        }
    }

    // ---------- Part 3 search / display ----------

    public static String getLongestStoredMessage() {
        if (storedCount == 0) return "No stored messages.";
        Message longest = storedMessages[0];
        for (int i = 1; i < storedCount; i++) {
            if (storedMessages[i].messageText.length() > longest.messageText.length()) {
                longest = storedMessages[i];
            }
        }
        return longest.messageText;
    }

    public static String searchByMessageID(String id) {
        for (int i = 0; i < sentCount; i++) {
            if (sentMessages[i].messageID.equals(id)) {
                return "Recipient: " + sentMessages[i].recipient
                        + " | Message: " + sentMessages[i].messageText;
            }
        }
        for (int i = 0; i < storedCount; i++) {
            if (storedMessages[i].messageID.equals(id)) {
                return "Recipient: " + storedMessages[i].recipient
                        + " | Message: " + storedMessages[i].messageText;
            }
        }
        return "Message ID not found.";
    }

    public static String searchByRecipient(String recipient) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < storedCount; i++) {
            if (storedMessages[i].recipient.equals(recipient)) {
                result.append("Message: ").append(storedMessages[i].messageText).append("\n");
            }
        }
        for (int i = 0; i < sentCount; i++) {
            if (sentMessages[i].recipient.equals(recipient)) {
                result.append("Message: ").append(sentMessages[i].messageText).append("\n");
            }
        }
        return result.length() == 0
                ? "No messages found for this recipient."
                : result.toString().trim();
    }

    /**
     * Deletes a stored message by hash. Removes it from storedMessages[],
     * messageHashes[] and messageIDs[], then rewrites messages.json so the
     * change is persistent.
     */
    public static String deleteByHash(String hash) {
        for (int i = 0; i < storedCount; i++) {
            if (storedMessages[i].messageHash.equals(hash)) {
                String deletedText = storedMessages[i].messageText;
                String deletedID   = storedMessages[i].messageID;

                // shift stored array
                for (int j = i; j < storedCount - 1; j++) {
                    storedMessages[j] = storedMessages[j + 1];
                }
                storedMessages[--storedCount] = null;

                // remove hash
                for (int h = 0; h < hashCount; h++) {
                    if (messageHashes[h].equals(hash)) {
                        for (int k = h; k < hashCount - 1; k++) {
                            messageHashes[k] = messageHashes[k + 1];
                        }
                        messageHashes[--hashCount] = null;
                        break;
                    }
                }
                // remove ID
                for (int d = 0; d < idCount; d++) {
                    if (messageIDs[d].equals(deletedID)) {
                        for (int k = d; k < idCount - 1; k++) {
                            messageIDs[k] = messageIDs[k + 1];
                        }
                        messageIDs[--idCount] = null;
                        break;
                    }
                }

                saveStoredMessagesToJSON();   // persist
                return "Message: \"" + deletedText + "\" successfully deleted.";
            }
        }
        return "Message hash not found.";
    }

    public static String generateReport() {
        if (storedCount == 0) return "No stored messages.";
        StringBuilder report = new StringBuilder();
        for (int i = 0; i < storedCount; i++) {
            report.append("Message ID: ").append(storedMessages[i].messageID).append("\n")
                  .append("Hash: ").append(storedMessages[i].messageHash).append("\n")
                  .append("Recipient: ").append(storedMessages[i].recipient).append("\n")
                  .append("Message: ").append(storedMessages[i].messageText).append("\n")
                  .append("Flag: ").append(storedMessages[i].flag).append("\n")
                  .append("-------------------------\n");
        }
        return report.toString().trim();
    }

    public static void displayStoredMenu(Scanner input) {
        boolean running = true;
        while (running) {
            System.out.println("\n--- STORED MESSAGES MENU ---");
            System.out.println("a) Display sender and recipient of all stored messages");
            System.out.println("b) Display the longest stored message");
            System.out.println("c) Search for a message ID");
            System.out.println("d) Search for all messages for a recipient");
            System.out.println("e) Delete a message using the message hash");
            System.out.println("f) Display full message report");
            System.out.println("0) Back to Main Menu");
            System.out.print("Choice: ");
            String choice = input.nextLine().toLowerCase();

            switch (choice) {
                case "a" -> {
                    if (storedCount == 0) System.out.println("No stored messages.");
                    for (int i = 0; i < storedCount; i++) {
                        System.out.println("Sender: " + storedMessages[i].sender
                                + " | Recipient: " + storedMessages[i].recipient);
                    }
                }
                case "b" -> System.out.println("Longest Message: " + getLongestStoredMessage());
                case "c" -> {
                    System.out.print("Enter Message ID: ");
                    System.out.println(searchByMessageID(input.nextLine()));
                }
                case "d" -> {
                    System.out.print("Enter Recipient: ");
                    System.out.println(searchByRecipient(input.nextLine()));
                }
                case "e" -> {
                    System.out.print("Enter Message Hash: ");
                    System.out.println(deleteByHash(input.nextLine()));
                }
                case "f" -> System.out.println(generateReport());
                case "0" -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }

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
    public String getSender()        { return sender; }
    public int    getMessageNumber() { return messageNumber; }
    public String getFlag()          { return flag; }
}