package org.example;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class Utils {

    public static void limitJTextField(JTextField textField, int maxValue){
        AbstractDocument document = (AbstractDocument) textField.getDocument();
        document.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (isNumeric(string) && (fb.getDocument().getLength() + string.length()) <= 2) {
                    String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;
                    if (isValid(newText)) {
                        super.insertString(fb, offset, string, attr);
                    }
                }
            }

            // Allow only numeric characters and check text length
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (isNumeric(text) && (fb.getDocument().getLength() + text.length() - length) <= 2) {
                    String newText = fb.getDocument().getText(0, offset) + text + fb.getDocument().getText(offset + length, fb.getDocument().getLength() - offset - length);
                    if (isValid(newText)) {
                        super.replace(fb, offset, length, text, attrs);
                    }
                }
            }

            //checks whether the String contains only numbers
            private boolean isNumeric(String text) {
                return text != null && text.matches("\\d");
            }

            // Checks whether the String passes the wanting limitation
            private boolean isValid(String text) {
                if (text.isEmpty()) {
                    return true;
                }
                int numberInput = Integer.parseInt(text);
                return numberInput >= 0 && numberInput <= maxValue;
            }
        });
    }
    public static void limitJTextField(JTextField textField, int maxValue, int start){
        AbstractDocument document = (AbstractDocument) textField.getDocument();
        document.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (isNumeric(string) && (fb.getDocument().getLength() + string.length()) <= 2) {
                    String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;
                    if (isValid(newText)) {
                        super.insertString(fb, offset, string, attr);
                    }
                }
            }

            // Allow only numeric characters and check text length
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (isNumeric(text) && (fb.getDocument().getLength() + text.length() - length) <= 2) {
                    String newText = fb.getDocument().getText(0, offset) + text + fb.getDocument().getText(offset + length, fb.getDocument().getLength() - offset - length);
                    if (isValid(newText)) {
                        super.replace(fb, offset, length, text, attrs);
                    }
                }
            }

            //checks whether the String contains only numbers
            private boolean isNumeric(String text) {
                return text != null && text.matches("\\d");
            }

            // Checks whether the String passes the wanting limitation
            private boolean isValid(String text) {
                if (text.isEmpty()) {
                    return true;
                }
                int numberInput = Integer.parseInt(text);
                return numberInput >= start && numberInput <= maxValue;
            }
        });
}
}