package view;

public enum ErrorType {
    USER_NAME_ALREADY_EXIST("User name already exists!"),
    INCORRECT_PASSWORD("Incorrect password!"),
    USER_NAME_NOT_FOUND("Can't find this user name!"),
    INVALID_INPUT("Invalid input!");

    private String message;

    public String getMessage() {
        return message;
    }

    ErrorType(String message) {
        this.message = message;
    }
}
