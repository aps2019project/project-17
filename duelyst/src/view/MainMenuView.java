package view;

public class MainMenuView extends View {
    public static void showMainMenu(){
        System.out.println("1. Collection");
        System.out.println("2. CustomCard");
        System.out.println("3. Shop");
        System.out.println("4. Battle");
        System.out.println("5. Exit");
        System.out.println("6. Help");
        System.out.println("7. Logout");

    }

    public static void MainMenuHelp(){
        showMainMenu();
    }
}
