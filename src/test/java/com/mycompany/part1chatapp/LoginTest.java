/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.part1chatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Login class.
 * I'm testing all the validation methods and the login system.
 *
 * @author Student
 */
public class LoginTest {

    // ============================================
    // 1. USERNAME TESTS
    // ============================================

    // Testing a username that should work.
    // "kyl_1" has an underscore and is exactly 5 characters.
    @Test
    public void testCheckUserNameCorrect() {
        Login user = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(user.checkUserName());
    }

    // Testing a username that shouldn't work.
    // "kyle!!!!!!!" is way too long and has no underscore.
    @Test
    public void testCheckUserNameIncorrect() {
        Login user = new Login("Kyle", "Smith", "kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976");
        assertFalse(user.checkUserName());
    }

    // ============================================
    // 2. PASSWORD TESTS
    // ============================================

    // Testing a password that should pass all the rules.
    @Test
    public void testPasswordComplexityCorrect() {
        Login user = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(user.checkPasswordComplexity());
    }

    // Testing a password that should fail.
    @Test
    public void testPasswordComplexityIncorrect() {
        Login user = new Login("Kyle", "Smith", "kyl_1", "password", "+27838968976");
        assertFalse(user.checkPasswordComplexity());
    }

    // Testing a password that is too short (only 6 characters).
    @Test
    public void testPasswordComplexity_TooShort_ShouldBeFalse() {
        Login user = new Login("Kyle", "Smith", "kyl_1", "Ab1!xy", "+27838968976");
        assertFalse(user.checkPasswordComplexity());
    }

    // Testing a password with no capital letter.
    @Test
    public void testPasswordComplexity_NoCapital_ShouldBeFalse() {
        Login user = new Login("Kyle", "Smith", "kyl_1", "password1!", "+27838968976");
        assertFalse(user.checkPasswordComplexity());
    }

    // ============================================
    // 3. CELL PHONE TESTS
    // ============================================

    // Testing a cell number that should work.
    @Test
    public void testCellPhoneCorrect() {
        Login user = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(user.checkCellPhoneNumber());
    }

    // Testing a cell number that should fail.
    @Test
    public void testCellPhoneIncorrect() {
        Login user = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "08966553");
        assertFalse(user.checkCellPhoneNumber());
    }

    // ============================================
    // 4. LOGIN TESTS
    // ============================================

    // Testing a login with the right username and password.
    @Test
    public void testLoginSuccessful() {
        Login user = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertTrue(user.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    // Testing a login with the wrong details.
    @Test
    public void testLoginFailed() {
        Login user = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertFalse(user.loginUser("wrong_user", "wrongPassword1!"));
    }

    // ============================================
    // 5. REGISTRATION MESSAGE TESTS
    // ============================================

    // Checking that a successful registration returns the right message.
    @Test
    public void testRegistrationSuccessful() {
        Login user = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        assertEquals("Registration successful.", user.registerUser());
    }

    // Checking that the username error message is exactly right.
    @Test
    public void testUsernameErrorMessage() {
        Login user = new Login("Kyle", "Smith", "kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976");
        assertEquals(
                "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters in length.",
                user.registerUser()
        );
    }

    // Checking that the password error message is exactly right.
    @Test
    public void testPasswordErrorMessage() {
        Login user = new Login("Kyle", "Smith", "kyl_1", "password", "+27838968976");
        assertEquals(
                "Password is not correctly formatted, please ensure that the password contains at least 8 characters, a capital letter, a number and a special character.",
                user.registerUser()
        );
    }

    // Checking that the cell phone error message is exactly right.
    @Test
    public void testCellPhoneErrorMessage() {
        Login user = new Login("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "08966553");
        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code, please correct the number and try again.",
                user.registerUser()
        );
    }
}