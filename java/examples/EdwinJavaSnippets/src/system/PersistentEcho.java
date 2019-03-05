package system;
import java.util.Map;
import java.util.Properties;
import  java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/* Question:

Write an application, PersistentEcho, with the following features:

* If PersistentEcho is run with command line arguments, it prints out those arguments. It also saves the string printed out to a property, and saves the property to a file called PersistentEcho.txt
* If PersistentEcho is run with no command line arguments, it looks for an environment variable called PERSISTENTECHO. If that variable exists, PersistentEcho prints out its value, and also saves the value in the same way it does for command line arguments.
* If PersistentEcho is run with no command line arguments, and the PERSISTENTECHO environment variable is not defined, it retrieves the property value from PersistentEcho.txt and prints that out.

 */
public class PersistentEcho {

    public static final String PROPKEY = "PERSISTENTECHO";

    public static void main(String[] args) {
        String toEcho = null;
        String propFilename = "PersistentEcho.txt";
        String locationFrom = null;

        Properties props = new Properties();

        Map<String, String> env = System.getenv();

        try {

            if(args.length > 0) {
                for (String arg: args) {
                    toEcho += arg + " ";
                }
                toEcho = toEcho.trim();
                locationFrom = "cmdline";
            } else if(env.containsKey(PROPKEY)) {
                toEcho = env.get(PROPKEY);
                locationFrom = "environment";
            }

            if(toEcho != null) {
                //save to props file
                FileWriter writer = new FileWriter(propFilename);
                props.setProperty(PROPKEY, toEcho);
                props.store(writer, "saved from " + locationFrom);
            }

            if (toEcho == null) {
                //get from properties file
                FileReader reader = new FileReader(propFilename);
                props.load(reader);
                reader.close();
                toEcho = props.getProperty(PROPKEY);
            }

            if(toEcho != null) {
                System.out.println(toEcho);
            }
        } catch (Exception e) {
            System.out.println("Error; " + e.getMessage());

        }
    }

}
