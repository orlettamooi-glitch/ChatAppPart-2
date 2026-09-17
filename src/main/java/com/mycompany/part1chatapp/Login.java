/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.part1chatapp;



/**
 *
 * @author Student
 */
public class Login {
    //commit 2 - login class
    //commit 3 - validation method

    // Attributes are characteristics of a class
    private String FirstName;
    private String LastName;
    private String Username;
    private String Password;
    private String CellPhoneNumber;

    // Constructor for Login
    public Login(String FirstName, String LastName, String Username,
                 String Password, String CellPhoneNumber) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Username = Username;
        this.Password = Password;
        this.CellPhoneNumber = CellPhoneNumber;
    }

    // Check if username has an underscore and is no more than 5 characters
    public boolean checkUserName() {
        return Username.contains("_") && Username.length() <= 5;
    }

    // Check whether the password is 8+ characters long, with a capital letter,
    // a number and a special character
    public boolean checkPasswordComplexity() {
        return Password.length() >= 8
                && Password.matches(".*[A-Z].*")
                && Password.matches(".*[0-9].*")
                && Password.matches(".*[^a-zA-Z0-9].*");
    }

    // Check if cell phone number starts with +27 and has 9 digits after
    public boolean checkCellPhoneNumber() {
        return CellPhoneNumber.matches("^\\+27[0-9]{9}$");
    }

    // Format the cell phone number for display
    // Example: +27838968976 becomes +27 83 896 8976
    public String formatCellPhoneNumber() {
        if (!checkCellPhoneNumber()) {
            return "Invalid cell phone number";
        }
        String countryCode = CellPhoneNumber.substring(0, 3);   // +27
        String firstPart   = CellPhoneNumber.substring(3, 5);   // 83
        String secondPart  = CellPhoneNumber.substring(5, 8);   // 896
        String thirdPart   = CellPhoneNumber.substring(8, 12);  // 8976
        return countryCode + " " + firstPart + " " + secondPart + " " + thirdPart;
    }

    // Register the user by checking all validation rules
    public String registerUser() {
        if (!checkUserName()) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters in length.";
        }
        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted, please ensure that the password contains at least 8 characters, a capital letter, a number and a special character.";
        }
        if (!checkCellPhoneNumber()) {
            return "Cell phone number is incorrectly formatted or does not contain an international code, please correct the number and try again.";
        }
        return "Registration successful.";
    }

    // Check if the entered username and password match what was stored
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return Username.equals(enteredUsername) && Password.equals(enteredPassword);
    }

    // Return a welcome message or error message depending on login result
    public String returnLoginStatus(boolean loginSuccessful) {
        if (loginSuccessful) {
            return "Welcome " + FirstName + " " + LastName + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    // Login Details
    public void LoginDetails() {
        System.out.println("First Name: " + FirstName);
        System.out.println("Last Name: " + LastName);
        System.out.println("Username: " + Username);
        System.out.println("Cell Phone Number: " + CellPhoneNumber);
    }
}


    


