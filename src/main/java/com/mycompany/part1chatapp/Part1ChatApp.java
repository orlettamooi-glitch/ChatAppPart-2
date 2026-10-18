/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.part1chatapp;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Student
 */
public class Part1ChatApp {
    
     // Keep track of total messages sent 
    private static int totalMessagesSent = 0;

    public static void main(String[] args) {
  
       Scanner input = new Scanner (System.in);
       
    System.out.println("=========================");
    System.out.println(" ChatApp Registeration ");
    System.out.println("=========================");

    // Details 
    
System.out.println("Enter your First Name : ");
String FirstName = input.nextLine();


 System.out.println("Enter your Last Name : ");
String LastName = input.nextLine();

System.out.println("Enter your Username : ");
String Username = input.nextLine();

System.out.println("Enter your Password : ");
String Password = input.nextLine();

System.out.print("Enter your South African cell phone number: ");
        String CellPhoneNumber = input.nextLine();

        // Create the Login object
        Login user = new Login(FirstName, LastName, Username, Password, CellPhoneNumber);

        System.out.println("\n========== REGISTRATION ==========");

        // Show registration result
        String registrationMessage = user.registerUser();
        System.out.println(registrationMessage);

        // Only continue to login if registration was successful
        if (registrationMessage.equals("Registration successful.")) {

            // Show user details
            System.out.println("\n========== USER DETAILS ==========");
            user.LoginDetails();

            System.out.println("\n============ LOGIN ============");

            System.out.print("Enter your username: ");
            String enteredUsername = input.nextLine();

            System.out.print("Enter your password: ");
            String enteredPassword = input.nextLine();

            // Check login
            boolean loginSuccessful = user.loginUser(enteredUsername, enteredPassword);
            System.out.println(user.returnLoginStatus(loginSuccessful));
            
            //Part 2- Messaging 
            if (loginSuccessful) {
                runMessagingApp(input);
            
        }

        input.close();
    }
} 

// Method to handle the messaging app
    private static void runMessagingApp(Scanner input) {
        System.out.println("\n=================================");
        System.out.println("     Welcome to QuickChat.       ");
        System.out.println("=================================");

        System.out.println("How many messages do you wish to enter? ");
        int numMessages = Integer.parseInt(input.nextLine());

        boolean running = true;

        // While loop to keep the app running until quit
        while (running) {
            System.out.println("\n===== QUICKCHAT MENU =====");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("Choose an option: ");
            
            int choice = Integer.parseInt(input.nextLine());

            switch (choice) {
                case 1 -> sendMessages(input, numMessages);
                case 2 -> System.out.println("Coming Soon.");
                case 3 -> {
                    System.out.println("Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }
    }

    // Method to send messages using a for loop
    private static void sendMessages(Scanner input, int numMessages) {
        
        // For loop to allow the user to enter the set number of messages
        for (int i = 1; i <= numMessages; i++) {
            System.out.println("\n--- Message " + i + " ---");

            System.out.println("Enter recipient cell number: ");
            String recipient = input.nextLine();

            System.out.println("Enter message: ");
            String messageText = input.nextLine();

            Message msg = new Message(i, recipient, messageText);

            // Validate message length
            String lengthCheck = msg.checkMessageLength();
            System.out.println(lengthCheck);
            
            if (!lengthCheck.equals("Message ready to send.")) {
                i--; // Decrease i so the loop repeats this message
                continue;
            }

            // Validate recipient
            String recipientCheck = msg.checkRecipientCell();
            System.out.println(recipientCheck);

            if (!recipientCheck.equals("Cell phone number successfully captured.")) {
                i--; // Decrease i so the loop repeats this message
                continue;
            }

            // Action menu
            System.out.println("\nChoose an action:");
            System.out.println("1) Send Message");
            System.out.println("2) Disregard Message");
            System.out.println("3) Store Message to send later");
            System.out.println("Option: ");

            int action = Integer.parseInt(input.nextLine());

            switch (action) {
                case 1 -> {
                    System.out.println("Message successfully sent");
                    System.out.println("\n" + msg.displayMessageDetails());
                    totalMessagesSent++;
                }
                case 2 -> {
                    System.out.println("Press 0 to delete the message");
                    input.nextLine(); // Wait for user to press enter
                }
                case 3 -> {
                    System.out.println("Message successfully stored");
                    storeMessageToJSON(msg);
                }
                default -> System.out.println("Invalid option.");
            }
        }

        System.out.println("\n=================================");
        System.out.println("Total messages sent: " + totalMessagesSent);
        System.out.println("=================================");
    }

    // Method to store message as JSON
    private static void storeMessageToJSON(Message msg) {
        String json = "{\n" +
                "  \"messageID\": \"" + msg.getMessageID() + "\",\n" +
                "  \"messageHash\": \"" + msg.getMessageHash() + "\",\n" +
                "  \"recipient\": \"" + msg.getRecipient() + "\",\n" +
                "  \"message\": \"" + msg.getMessageText() + "\"\n" +
                "}\n";

        try {
            try (FileWriter writer = new FileWriter("messages.json", true)) {
                writer.write(json);
            }
            System.out.println("Message saved to messages.json");
        } catch (IOException e) {
            System.out.println("Error saving message: " + e.getMessage());
        }
    }
}

