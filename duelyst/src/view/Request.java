package view;

import java.util.Scanner;

public class Request {
    protected Scanner scanner = new Scanner(System.in);
    protected String command;
    protected ErrorType error = null;

    public void getNewCommand() {
        command = scanner.nextLine();
    }

    public ErrorType getError() {
        return error;
    }

    public void setError(ErrorType error) {
        this.error = error;
    }
}
