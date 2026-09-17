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
        }

        input.close();
    }
} 

