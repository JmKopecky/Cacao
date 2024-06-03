package dev.prognitio.cacao;
import java.util.Scanner;
public class course {

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);

        System.out.println("How many classes do you have? ");
        Integer classes = scanner.nextLine();

        System.out.println("You have "+classes);
    }
}
