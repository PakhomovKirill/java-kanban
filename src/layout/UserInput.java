package layout;

import java.util.Scanner;

public class UserInput {
    public static Scanner SC;
    private static PrintCommands printCommands;
    public UserInput(){
        SC = new Scanner(System.in);
        printCommands = new PrintCommands();
    }

    public <T> T setUserInputInt (int maxValue, int minValue){
        int userInput;

        try {
            userInput = SC.nextInt();
            SC.nextLine();
        } catch (Exception exception){
            SC.next();
            printCommands.printLabel("Ввести можно только цифры.");
            return null;
        }

        if(userInput < minValue){
            printCommands.printLabel("Недопустимое минимальное значение");
            return null;
        }

        if(userInput > maxValue){
            printCommands.printLabel("Недопустимое максимальное значение");
            return null;
        }

        return (T) ((Integer) userInput);
    }

    public <T> T setUserInputString (){
        String userInput;

        try {
            userInput = SC.nextLine();
        } catch (Exception exception){
            SC.next();
            printCommands.printLabel("Вы ввели недопустимое значение.");
            return null;
        }

        return (T) ((String) userInput);
    }

}
