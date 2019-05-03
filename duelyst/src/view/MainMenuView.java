package view;

public class MainMenuView extends View {
    public static void showMainMenu(){
        System.out.println("1. Collection");
        System.out.println("2. Shop");
        System.out.println("3. Battle");
        System.out.println("4. Exit");
        System.out.println("5. Help");
        System.out.println("6. Logout");
    }

    public static void MainMenuHelp(){
        showMainMenu();
    }
}
