package blog.ml3426.easyCal.cli;

import java.util.Scanner;

import blog.ml3426.easyCal.analysis.Add;

public final class Main {

    public static void main(String[] args) {
        System.out.println("Reading From Console: ");
        final Scanner consoleScanner = new Scanner(System.in);
        final int firstNumber = consoleScanner.nextInt();
        final int secondNumber = consoleScanner.nextInt();
        final int result = Add.add(firstNumber, secondNumber);
        System.out.println("Result is " + result);
    }
}
