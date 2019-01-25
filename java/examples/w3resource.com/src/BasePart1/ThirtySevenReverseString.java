package BasePart1;

import java.util.Scanner;

public class ThirtySevenReverseString {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a sentence: ");

        String input = sc.nextLine();

        StringBuffer resultStr = new StringBuffer();

        for(int i = input.length() - 1; i >= 0; i--) {
            resultStr.append(input.charAt(i));

        }

        System.out.println(resultStr);
    }


}
