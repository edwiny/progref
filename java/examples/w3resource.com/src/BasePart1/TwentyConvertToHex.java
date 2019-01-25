package BasePart1;

public class TwentyConvertToHex {
    public static String decimalToHex(int decNumber) {
        char map[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        int  base = 16;

        StringBuffer result = new StringBuffer("");


        while(decNumber > 0) {
            int quotient  = decNumber / base;
            int remainder = decNumber % base;
            result.insert(0, map[remainder]);
            decNumber = quotient;
        }

        return result.toString();

    }

    public static String decimalToBinary(int decNumber) {
        int  base = 2;

        StringBuffer result = new StringBuffer("");


        while(decNumber > 0) {
            int quotient  = decNumber / base;
            int remainder = decNumber % base;
            result.insert(0, remainder);
            decNumber = quotient;
        }

        return result.toString();

    }

    public static void main(String[] args) {
        int input = 1444;

        System.out.printf("%d base 10 is %s in hex (printf says %x)\n", input, decimalToHex(input), input);
        System.out.printf("%d base  2 is %s in binary (Integer.toBinaryString says %s)\n",
                input,
                decimalToBinary(input),
                Integer.toBinaryString(input));



    }
}
