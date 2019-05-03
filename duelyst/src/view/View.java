package view;

public class View {
    private static final View VIEW = new View();

    public static View getInstance() {
        return VIEW;
    }

    public void printError(ErrorType type) {
        if (type == null)
            return;
        System.out.println("Invalid Input,Try again!");
    }
}
