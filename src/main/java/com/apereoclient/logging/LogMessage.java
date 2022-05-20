package com.apereoclient.logging;

/**
 * Stores global constants to make code easier to read and more maintainable
 */
public class LogMessage {

    public static final String LOGIN_EVENT = "Log-in event <Username: %s>";
    public static final String GETTING_USER_DATA = "Fetching user profile data with accessToken!";
    public static final String BAD_LOGIN_REQUEST = "Received bad request. The username and/or password do not comply to the standard format!";
    public static final String BAD_DATA_FROM_APEREO_RESPONSE = "Received null data from Apereo Server.";
    public static final String BAD_RESPONSE_FROM_APEREO_SERVER = "Received an error when contacting Apareo server. Error message: %n%s";
    public static final String BAD_RESPONSE_TO_USER = "Received an error when contacting Apareo server. Error status: %n%s";
    public static final String SIGNING_JWT_ERROR = "JWT Signing error occurred!";
    public static final String DECODING_JWT_ERROR = "JWT Decoding error occurred!";
    public static final String JWT_TO_OBJECT_MAPPING_ERROR = "JWT Decoding error occurred!";
    public static final String OAUTH2_USER_MAPPING_FAILED = "Failed to map oauth2 user data to usable entity!";

}
