package code;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Soli on 27/03/2017.
 */
public class Main {
    public static void main(String [] args){
        double score = 0;
        String [] files = {"me_at_the_zoo.txt","trending_today.txt","videos_worth_spreading.txt","kittens.txt"};
        for(String file: files){
            Interpreter env = new Interpreter("Data/" + file);
            env.solve();
            System.out.println();
            print("File Score" + "(" + file +"): " + String.valueOf(env.score));
            // Adding the score of the current file to the overall score
            score += env.score;
            // Creating the solution files
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter("Solutions/sol_" + file));
                String solution = env.getSolution();
                out.write(solution);
                out.close();
            } catch (IOException e) {
                System.out.println("Exception");
            }
        }
    }

    static void print(String toPrint){
        System.out.println(toPrint);
    }
}
