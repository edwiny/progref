package BasePart1;

import java.util.Scanner;

public class TwentyTwoBinaryToDecimal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a binary number: ");
        String binaryNumStr = sc.next();

        System.out.printf("Got [%s]\n", binaryNumStr);

        int sum = 0;
        int pos = 0;
        for(int i = binaryNumStr.length() - 1; i >= 0; i--) {

            int valueAtPos = (int)binaryNumStr.charAt(i) - (int)'0';
            sum += valueAtPos * Math.pow(2, pos);
            pos++;

        }

        System.out.println("Decimal is " + sum);
    }
}
