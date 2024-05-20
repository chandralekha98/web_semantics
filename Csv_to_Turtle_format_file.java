import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Phase2 {
    public static void main(String[] args) {
        String csvFile = "C:\\Users\\chlek\Downloads\\student.csv"; 
        String turtleFile = "C:\\\\Users\\\\chlek\\\\OneDrive\\\\Desktop\\\\output.ttl"; 

        // Create an empty Jena model
        Model model = ModelFactory.createDefaultModel();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile));
             FileWriter fw = new FileWriter(turtleFile)) {

            String line;
            int lineNum = 0;

            while ((line = br.readLine()) != null) {
                if (lineNum == 0) {
                    lineNum++;
                    continue; // Skip header line
                }

                String[] values = line.split(",");

                if (values.length != 9) {
                    System.out.println("Invalid CSV format at line: " + lineNum);
                    continue;
                }

                Resource subject = model.createResource("http://education.edu/" + values[0].replace(" ", "_"));

                for (int i = 0; i < values.length; i++) {
                    String predicateName = getColumnHeader(i);
                    String objectValue = values[i];
                    Property predicate = model.createProperty("http://education.edu/property/" + predicateName);
                    Literal object = model.createLiteral(objectValue);
                    Statement statement = model.createStatement(subject, predicate, object);
                    model.add(statement);
                }

                lineNum++;
            }

            // Write the model to the Turtle file
            model.write(fw, "TURTLE");
            model.write(System.out,"TURTLE");
            System.out.println("Conversion completed successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getColumnHeader(int index) {
        switch (index) {
            case 0:
                return "student";
            case 1:
                return "gender";            
            case 2:
                return "PlaceofBirth";
            case 3:
                return "organization";
            case 4:
                return "Grade";
            case 5:
                return "SectionID";
            case 6:
                return "course";
            case 7:
                return "Semester";
            case 8:
            	return "Faculty";        
            default:
                return "";
        }
    }
}
