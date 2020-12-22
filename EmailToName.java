/*
 * Space Cadets Challenge 1
 * @author James Harcourt
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailToName {
    
    /* 
     * Purpose: Given an email ID such as "dem", print the name of the person that ID corresponds to.
     *          This will be done by scraping HTML from the Uni of Southampton ECS public searchable directory
     *          of members and visitors - checking for properties in the HTML tags.
     */
    public static void main(String[] args) {

        try {
            // Create InputStreamReader and BufferedReader to get input.
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            String email_id;
            
            // Check for parameters passed from command line- otherwise use default test.
            if (args.length == 0) {
                email_id = "dem";
            } else {
                email_id = args[0];
            }

            // Append email ID to web address and open a URL connection.
            String web_address = "https://www.ecs.soton.ac.uk/people/" + email_id;
            URL url_obj = new URL(web_address);
            URLConnection urlConn = url_obj.openConnection();

            // New BufferedReader to get input from HTML file.
            BufferedReader br2 = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            boolean found = false;
            String result;
            
            String line;
            while (!found && (line = br2.readLine()) != null) {
                // Begin searching for '<property="name">' string in HTML of web page.
                Pattern p = Pattern.compile(".*property=\"name\">([\\w|\\s]+)<.*");
                Matcher m = p.matcher(line);

                // If a group is found that matches the regex expression- this must be a name.
                if (m.find()) {
                    System.out.println("Name: " + m.group(1));
                    found = true;
                }
            }

        } catch(IOException ioe) {
            System.out.println("IOException Caught.");
        }

    }
}
