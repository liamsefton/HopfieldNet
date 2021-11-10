import java.io.*;
import java.util.*;
public class Test {
    public static void main(String[] args){
        int cont = 1;
        while(cont == 1){
            HopfieldNet hn = new HopfieldNet();
            Reader reader = new Reader();
            Scanner scanner = new Scanner(System.in);
            //double[][] init_weights = {{1,-1,1},{1,-1,-1},{1,1,1}};
            System.out.println("Enter filename for testing and training data: ");
            String training_and_testing = scanner.nextLine();
            System.out.println("Enter 0 to train the net from training data or enter 1 to load weights from file: ");
            double[][] init_weights = new double[1][1]; //again this is cringe that i have to do this, have some faith in your programmers java
            if(scanner.nextLine().equals("0")){
                init_weights = reader.readerTraining(training_and_testing);
                System.out.println("Training... ");
                hn.train(init_weights);
                System.out.println("Enter file name to save weights to: ");
                hn.saveWeightsToFile(scanner.nextLine());
            }
            else{
                System.out.println("Enter filename for saved weights: ");
                hn.readWeightsFromFile(scanner.nextLine());
            }

            //double[][] init_weights = {{1,-1,1,-1,1,-1,1,-1,1,-1, -1,1,-1,1,-1,1,-1,1,-1,1, 1,-1,1,-1,1,-1,1,-1,1,-1, -1,1,-1,1,-1,1,-1,1,-1,1, 1,-1,1,-1,1,-1,1,-1,1,-1, -1,1,-1,1,-1,1,-1,1,-1,1, 1,-1,1,-1,1,-1,1,-1,1,-1, -1,1,-1,1,-1,1,-1,1,-1,1, 1,-1,1,-1,1,-1,1,-1,1,-1, -1,1,-1,1,-1,1,-1,1,-1,1}};

            //hn.train(init_weights);
            //hn.saveWeightsToFile("sw.txt");

            System.out.println("Testing with training data... ");
            int differences = 0;
            try{
                System.out.println("Enter filename to save results to: ");
                BufferedWriter bw = new BufferedWriter(new FileWriter(new File(scanner.nextLine())));
                for(int j = 0; j < init_weights.length; j++){
                    bw.write("Actual: \n");
                    double[] result = hn.test(init_weights[j]);
                    saveResults(init_weights[j], 10, bw);
                    for(int i = 0; i < result.length; i++){
                        if(result[i] != init_weights[j][i])
                            differences += 1;
                    }
                    bw.write("Net output: \n");
                    saveResults(result, 10, bw);
                }
                bw.write("\n");
                bw.write("Differences from stored images: " + Integer.toString(differences) + "\n");
                bw.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            System.out.println("Differences: " + differences);

            double[][] test_inputs = reader.readerTesting(training_and_testing);
            System.out.println("Testing with testing data... ");

            differences = 0;
            try{
                System.out.println("Enter filename to save results to: ");
                BufferedWriter bw = new BufferedWriter(new FileWriter(new File(scanner.nextLine())));
                for(int j = 0; j < init_weights.length; j++){
                    bw.write("Actual: \n");
                    saveResults(test_inputs[j], 10, bw);
                    double[] result = hn.test(test_inputs[j]);
                    for(int i = 0; i < result.length; i++){
                        if(result[i] != init_weights[j][i])
                            differences += 1;
                    }
                    bw.write("Net output: \n");
                    saveResults(result, 10, bw);
                }
                bw.write("\n");
                bw.write("Differences from stored images: " + Integer.toString(differences) + "\n");
                bw.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            System.out.println("Differences: " + differences);

            System.out.println("Would you like to run again? (enter 1 for yes or 0 for no)");
            cont = scanner.nextInt();
        }
    }

    public static void saveResults(double[] output, int width, BufferedWriter bw){
        try{
            for(int i = 0; i < output.length; i++){
                if(output[i] == 1)
                    bw.write("O");
                else
                    bw.write(" ");
                if((i+1) % width == 0 && i > 8)
                    bw.write("\n");
            }
            bw.write("\n");
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
