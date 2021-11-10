import java.io.*;
import java.util.Random;
import java.util.ArrayList;
public class HopfieldNet{
    double[][] weights;
    int width;

    public void train(double[][] weight_vectors){
        weights = new double[weight_vectors[0].length][weight_vectors[0].length]; //nxn matrix
        for(int i = 0; i < weight_vectors.length; i++){
            for(int j = 0; j < weight_vectors[i].length; j++){
                for(int k = 0; k < weight_vectors[i].length; k++){
                    weights[j][k] += weight_vectors[i][j] * weight_vectors[i][k]; //should have used a library for this lol
                }
            }
        }
        for(int i = 0; i < weights.length; i++){
            weights[i][i] = 0;
        }
    }

    public void saveWeightsToFile(String filename){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filename)));
            bw.write(Integer.toString(weights.length) + "\n");
            bw.write(Integer.toString(weights[0].length) + "\n");
            for(int i = 0; i < weights.length; i++){
                for(int j = 0; j < weights[i].length; j++){
                    if(j == weights.length - 1)
                        bw.write(Double.toString(weights[i][j]));
                    else
                        bw.write(Double.toString(weights[i][j]) + ",");
                }
                bw.write("\n");
            }
            bw.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void readWeightsFromFile(String filename){
        try{
            BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
            int dim = Integer.parseInt(br.readLine());
            int width = Integer.parseInt(br.readLine());
            weights = new double[dim][width];
            for(int i = 0; i < dim; i++){
                String line = br.readLine();
                for(int j = 0; j < width; j++){
                    weights[i][j] = Double.parseDouble(line.split(",")[j]);
                }
            }
            br.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public double[] test(double[] testVector){
        ArrayList<Integer> randomList = new ArrayList<Integer>();
        double[] tempVector = new double[testVector.length];
        Random rand = new Random();
        boolean changed = true;
        double y_in;
        int y;
        int numRuns = 0;
        while(changed){
            if(numRuns > 100){
                double[] error = {0};
                return error;
            }
            changed = false;
            for(int i = 0; i<testVector.length; i++){
                randomList.add(i);
            }
            for(int i = 0; i<testVector.length; i++){
                tempVector[i] = testVector[i];
            }
            for(int z = 0; z<testVector.length; z++){
                int randChoice = randomList.get(rand.nextInt(randomList.size()));
                randomList.remove(randomList.indexOf(randChoice));
                y_in = testVector[randChoice];
                for(int i = 0; i<testVector.length; i++){
                    y_in += tempVector[i]*weights[i][randChoice];
                }
                y = activation(y_in);
                if(tempVector[randChoice] != y){
                    changed = true;
                }
                tempVector[randChoice] = y;
                numRuns ++;
            }
            if(changed){
                for(int i = 0; i<testVector.length; i++){
                    testVector[i] = tempVector[i];
                }
            } 
        }
        return tempVector;
    }

    public int activation(double y_in){
        if(y_in < 0){
            return -1;
        }
        else if(y_in == 0){
            return 0;
        }
        else{
            return 1;
        }
    }
}