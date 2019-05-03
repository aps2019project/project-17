package view;

public class MainMenuView extends View {
    public static void showMainMenu(){
        System.out.println("1. Collection");
        System.out.println("2. Shop");
        System.out.println("3. Battle");
        System.out.println("Exit");
        System.out.println("Help");
    }

    public static void MainMenuHelp(){
        showMainMenu();
    }
}
