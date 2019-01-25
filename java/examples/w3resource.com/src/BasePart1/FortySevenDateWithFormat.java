package BasePart1;

/* Task: print the current date time in given format.


 */

import java.text.SimpleDateFormat;
import java.util.Date;

public class FortySevenDateWithFormat {

    public static void main(String[] args) {
        SimpleDateFormat dt = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");

        Date d = new Date();

        System.out.println("[method1] " + dt.format(d));




    }
}
