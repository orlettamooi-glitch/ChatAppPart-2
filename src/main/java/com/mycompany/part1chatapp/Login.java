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
 
private String FirstName; 
private String LastName; 
private String Username; 
private String Password; 
private String CellPhoneNumber; 

// Constructor for Login 

public Login(String FirstName, String LastName, String Username,String Password,
              String CellPhoneNumber) {
    
    this. FirstName = FirstName;
    this. LastName = LastName;
    this. Username = Username;
    this. Password = Password;
    this. CellPhoneNumber = CellPhoneNumber;
}

//

public boolean checkUsername() {
  return Username.contains("_") && Username.length() <= 5; 
 
}

// check wheter the password has more than 8 characters long, a number, capital letter
// speacial character 

public boolean checkPasswordIntricacy() {
    return Password.length() >= 8
            && Password.matches(".* [A-Z].*" )
            && Password.matches(".* [0-9].*" )
            && Password.matches(".* [^a-zA-Z0-9].*" );
}

// Register the user by cheking all verified 

public String registerUser() {
    if (!checkUsername()) {
   return "Username is not written correctly, make sure that username contains a "
           + "underscore and that it has no more than 5 characters" ;
    }
    
    if (!checkPasswordIntricacy ()) {
    return "Password is not written correctly, make sure that the Password contains "
            + "at least 8 characters, a capital letter, a number and speacial character.";
    }
    if (!checkCellPhoneNumber()) {
        return "Cell phone number is incorrectly formatted or does not contaian anjnternationalcode, please correct the number and try again.";
    }
     return "Registration successful.";
    }
    
// check if the entered Username and password match what was stored 
public boolean loginUser(String enteredUsername, String enteredPassword) {
    return Username.equals(enteredUsername) && Password.equals(enteredPassword);
}

// Return a welcome message or error message depending on Login result
public String returnLoginStatus(boolean loginSuccessful) {
 if (loginSuccessful) {
 return "Welcome " + FirstName + " " + LastName + " , it is great to see you again.";
 
 }else {
     return "Username or Password incorrcet, please try again."; 
 }
}

// Login Details 
public void LoginDetails(){
    System.out.print("First Name: " + FirstName);
    System.out.print("Last Name: " + LastName);
    System.out.print("Username : " + Username);
    System.out.print("Cell Phone Number: " + CellPhoneNumber);
}
}


    


