package com.sathwik.project;

import javax.swing.JOptionPane;

public class PopupMessageExample {
    public static void main(String[] args) {
        // Show a simple message dialog
        JOptionPane.showMessageDialog(null, "This is a pop-up message.");

        // Show an information message dialog
        JOptionPane.showMessageDialog(null, "This is an information message.", "Information", JOptionPane.INFORMATION_MESSAGE);

        // Show a warning message dialog
        JOptionPane.showMessageDialog(null, "This is a warning message.", "Warning", JOptionPane.WARNING_MESSAGE);

        // Show an error message dialog
        JOptionPane.showMessageDialog(null, "This is an error message.", "Error", JOptionPane.ERROR_MESSAGE);

        // Show a question message dialog with Yes/No buttons
        int result = JOptionPane.showConfirmDialog(null, "Do you want to proceed?", "Question", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            System.out.println("User clicked Yes.");
        } else {
            System.out.println("User clicked No.");
        }

        // Show an input dialog to get user input
        String userInput = JOptionPane.showInputDialog(null, "Enter your name:");
        if (userInput != null && !userInput.isEmpty()) {
            System.out.println("User entered: " + userInput);
        } else {
            System.out.println("User cancelled or entered empty input.");
        }
    }
}

