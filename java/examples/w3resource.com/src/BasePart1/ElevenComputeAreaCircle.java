package BasePart1;

import java.util.Scanner;

public class ElevenComputeAreaCircle {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter radius: ");
        double radius = sc.nextDouble();
        double area   = Math.PI * Math.pow(radius, 2);
        double circumference   = 2 * Math.PI * radius;

        System.out.printf("Area: %30.02f\n", area);
        System.out.printf("Circumference: %30.02f\n", circumference);


    }
}
