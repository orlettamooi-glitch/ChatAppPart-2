/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.part1chatapp;
import java.util.Scanner;

/**
 *
 * @author Student
 */
public class Part1ChatApp {
    
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

 /** Menu loop — runs until the user selects Quit. */
    private static void runMessagingApp(Scanner input) {
        System.out.println("\n=================================");
        System.out.println("     Welcome to QuickChat.       ");
        System.out.println("=================================");

        int numMessages = readPositiveInt(input, "How many messages do you wish to enter? ");

        boolean running = true;
        while (running) {
            System.out.println("\n===== QUICKCHAT MENU =====");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");

            int choice = readInt(input);
            switch (choice) {
                case 1 -> sendMessages(input, numMessages);
                case 2 -> System.out.println("Coming Soon.");
                case 3 -> {
                    System.out.println("Total messages sent: " + Message.returnTotalMessages());
                    System.out.println("Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }
    }

    /** Collects exactly numMessages valid messages. */
    private static void sendMessages(Scanner input, int numMessages) {
        for (int i = 1; i <= numMessages; i++) {
            System.out.println("\n--- Message " + i + " of " + numMessages + " ---");

            Message msg;

            // Retry this message until BOTH checks pass
            while (true) {
                System.out.print("Enter recipient cell number: ");
                String recipient = input.nextLine();

                System.out.print("Enter message: ");
                String messageText = input.nextLine();

                msg = new Message(i, recipient, messageText);

                String lengthCheck = msg.checkMessageLength();
                System.out.println(lengthCheck);
                if (!lengthCheck.equals("Message ready to send.")) continue;

                String recipientCheck = msg.checkRecipientCell();
                System.out.println(recipientCheck);
                if (!recipientCheck.equals("Cell phone number successfully captured.")) continue;

                break;
            }

            System.out.println("\nChoose an action:");
            System.out.println("1) Send Message");
            System.out.println("2) Disregard Message");
            System.out.println("3) Store Message to send later");
            System.out.print("Option: ");

            int action = readInt(input);
            switch (action) {
                case 1 -> {
                    System.out.println(msg.SentMessage(1));
                    System.out.println("\n" + msg.displayMessageDetails());
                }
                case 2 -> {
                    System.out.println(msg.SentMessage(2));
                    System.out.print("Enter 0 to confirm deletion: ");
                    String confirm = input.nextLine();
                    System.out.println("0".equals(confirm)
                            ? "Message disregarded."
                            : "Message kept.");
                }
                case 3 -> System.out.println(msg.SentMessage(3));
                default -> System.out.println("Invalid option. Message not sent or stored.");
            }
        }

        System.out.println("\n=================================");
        System.out.println("Total messages sent: " + Message.returnTotalMessages());
        System.out.println("=================================");
    }

    // Input helpers 

    private static int readPositiveInt(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            int v = readInt(input);
            if (v > 0) return v;
            System.out.println("Please enter a positive whole number.");
        }
    }

    private static int readInt(Scanner input) {
        try {
            return Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}