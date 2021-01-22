package com.jackcode.Roomr.exceptions;

import de.codecamp.vaadin.components.messagedialog.MessageDialog;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ExceptionDialog extends MessageDialog {

    private static final Logger LOGGER = Logger.getLogger(ExceptionDialog.class.getName());

    final private String errorCode;
    final private String message;

    public ExceptionDialog(String errorCode, String userMessage) {
        this.errorCode = errorCode;
        this.message = userMessage;
        showErrorDialog();
        logError();
    }

    private void showErrorDialog() {
        setTitle("Internal Error");
        setMessage(message);
        addButton().text("Close").closeOnClick();
        open();
    }

    private void logError() {
        // Add logging code
        LOGGER.log(Level.SEVERE, errorCode);
    }
}
