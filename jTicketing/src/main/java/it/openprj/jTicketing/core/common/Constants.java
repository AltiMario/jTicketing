/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

package it.openprj.jTicketing.core.common;


/**
 * <p>
 * Manifest constants for the MailReader application.
 * </p>
 *
 * @version $Rev: 471754 $ $Date: 2011/01/31 11:51:54 $
 */

public final class Constants {

    // --- Tokens ----

    /**
     * <p>
     * The token representing a "create" task.
     * </p>
     */
    public static final String CREATE = "Create";

    /**
     * <p>
     * The application scope attribute under which our user database
     * is stored.
     * </p>
     */
    public static final String DATABASE_KEY = "database";

    /**
     * <p>
     * The token representing a "edit" task.
     * </p>
     */
    public static final String DELETE = "Delete";

    /**
     * <p>
     * The token representing a "edit" task.
     * </p>
     */
    public static final String EDIT = "Edit";

    /**
     * <p>
     * The request attributes key under the WelcomeAction stores an ArrayList
     * of error messages, if required resources are missing.
     * </p>
     */
    public static final String ERROR_KEY = "ERROR";

    /**
     * <p>
     * The token representing a "failure" result for this application.
     * </p>
     */
    public static final String FAILURE = "Failure";

    /**
     * <p>
     * The token representing a "logon" result for this application.
     * </p>
     */
    public static final String LOGON = "Logon";

    /**
     * <p>
     * The package name for this application.
     * </p>
     */
    public static final String PACKAGE = "org.apache.struts.apps.mailreader";

    /**
     * <p>
     * The token representing a "save" task.
     * </p>
     */
    public static final String SAVE = "Save";

    /**
     * <p>
     * The session scope attribute under which the Subscription object
     * currently selected by our logged-in User is stored.
     * </p>
     */
    public static final String SUBSCRIPTION_KEY = "subscription";

    /**
     * <p>
     * The token representing a "success" result for this application.
     * </p>
     */
    public static final String SUCCESS = "Success";

    /**
     * <p>
     * The session scope attribute under which the User object
     * for the currently logged in user is stored.
     * </p>
     */
    public static final String USER_KEY = "user";

    // ---- Error Messages ----

    /**
     * <p>
     * A static message in case database resource is not loaded.
     * <p>
     */
    public static final String ERROR_DATABASE_NOT_LOADED =
            "ERROR:  User database not loaded -- check servlet container logs for error messages.";

    /**
     * <p>
     * A static message in case message resource is not loaded.
     * </p>
     */
    public static final String ERROR_MESSAGES_NOT_LOADED =
            "ERROR:  Message resources not loaded -- check servlet container logs for error messages.";

    // ---- Error Tokens ----

    /**
     * <p>
     * The resource key for an error with the transactional token.
     * </p>
     */
    public static final String MSG_TRANSACTION_TOKEN = "error.transaction.token";

    // ---- Log Messages ----

    /**
     * <p>
     * The message to log when cancelling a transaction.
     * </p>
     */
    public static final String LOG_CANCEL = " Transaction cancelled: ";

    /**
     * <p>
     * The message to log when forwarding to a result.
     * </p>
     */
    public static final String LOG_RESULT = " Forwarding to result: ";

    /**
     * <p>
     * The message to log when forwarding to a 'failure' result.
     * <p>
     */
    public static final String LOG_FAILURE = LOG_RESULT + FAILURE;

    /**
     * <p>
     * The message to log when forwarding to a 'logon' result.
     * </p>
     */
    public static final String LOG_LOGON = LOG_RESULT + LOGON;

    /**
     * <p>
     * The message to log when populating a form.
     * </p>
     */
    public static final String LOG_POPULATE_FORM = " Populating form from: ";

    /**
     * <p>
     * The message to log when populating a subscription.
     * </p>
     */
    public static final String LOG_POPULATE_SUBSCRIPTION = " Populating subscription: ";

    /**
     * <p>
     * The message to log when populating a user.
     * </p>
     */
    public static final String LOG_POPULATE_USER = " Populating user: ";

    /**
     * <p>
     * The message to log when forwarding to a 'success' result.
     * </p>
     */
    public static final String LOG_PROCESSING = " Processing: ";

    /**
     * <p>
     * The message to log when forwarding to a 'success' result.
     * </p>
     */
    public static final String LOG_SUCCESS = LOG_RESULT + SUCCESS;

    /**
     * <p>
     * The message to log when setting a transactional token.
     * </p>
     */
    public static final String LOG_TOKEN = " Setting transactional control token";

    /**
     * <p>
     * The message to log when checking a transactional token.
     * </p>
     */
    public static final String LOG_TOKEN_CHECK = " Checking transactional control token";
    
    public static final String MESSAGES_ERROR = "errorMessages";
    public static final String MESSAGES_WARNING = "warningMessages";
    public static final String MESSAGES_INFO = "infoMessages";
    public static final String RAPID_INFO = "rapidMessages";

}
