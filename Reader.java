import java.io.*;
import java.util.*;

public class Reader{
    public double[][] readerTraining(String filename){
        double[][] imgArray = new double[0][0];
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
            String line = br.readLine();
            while (line.trim().length() == 0){  //read empty lines until there is a non empty line, which means its at the dimension data
                line = br.readLine();
            }
            String[] dimensions = line.split("\\s+");
            int dimImg = Integer.parseInt(dimensions[0]); //length of each image
            String[] numberImages = br.readLine().split("\\s+");
            int numImg = Integer.parseInt(numberImages[0]);  //number of images

            imgArray = new double[numImg][dimImg];  //this was reversed before and had dimImg rows and numImg cols
            int count = 1;
            int dimension = 0;
            br.readLine(); //read last empty line
            String input = br.readLine();
            int width = input.length();
            int l = input.length();
            for (int j = 0; j < width; j++){
                dimension += 1; //counter for the full image length, j is counter for current line of image
                if(input.charAt(j) == 'O'){  //nonwhitspace is 1
                    imgArray[count-1][dimension-1] = 1;
                }
                else{ //whitespace is -1
                    imgArray[count-1][dimension-1] = -1;
                }
            }

            for (int i = 0; i < dimImg/width - 1; i++){  //10 rows of 10 in this case, needs to be generalized
                input = br.readLine();
                l = input.length();
                for (int j = 0; j < width; j++){
                    dimension += 1; //counter for the full image length, j is counter for current line of image
                    if(input.charAt(j) == 'O'){  //nonwhitspace is 1
                        imgArray[count-1][dimension-1] = 1;
                    }
                    else{ //whitespace is -1
                        imgArray[count-1][dimension-1] = -1;
                    }
                }
            }
            while (count < numImg){
                br.readLine();  //reads the empty line preceeding each image
                count += 1;
                System.out.println(count);
                    dimension = 0;
                    for (int i = 0; i < dimImg/width; i++){  //10 rows of 10 in this case, needs to be generalized
                        input = br.readLine();
                        l = input.length();
                        System.out.println(l);
                        for (int j = 0; j < width; j++){
                            dimension += 1; //counter for the full image length, j is counter for current line of image
                            if(input.charAt(j) == 'O'){  //nonwhitspace is 1
                                imgArray[count-1][dimension-1] = 1;
                            }
                            else{ //whitespace is -1
                                imgArray[count-1][dimension-1] = -1;
                            }
                        }
                    }
            }
            br.close();
        }
        catch(Exception e){
            System.out.println("User error, this is all your fault.");
            e.printStackTrace();
            System.out.println(e);
        }
    return imgArray;
    }

    public double[][] readerTesting(String filename){
        double[][] imgArray = new double[0][0];
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
            String line = br.readLine();
            while (line.trim().length() == 0){
                line = br.readLine();
            }

            int dimImg = Integer.parseInt(line.split("\\s+")[0]);

            String[] numberImages = br.readLine().split("\\s+");
            int numImg = Integer.parseInt(numberImages[0]);

            int count = 1;
            br.readLine();
            int width = br.readLine().length();
            for (int i = 0; i < dimImg/width - 1; i++){
                br.readLine();
            }
            while (count < numImg){
                br.readLine();
                count += 1;
                    for (int i = 0; i < 10; i++){
                        br.readLine();
                        }
                }


            //up until this point, ignored all the data we read
            //now begin reading and outputting data into double array
            line = "";
            while (line.trim().length() == 0){
                line = br.readLine();
            }
            dimImg = Integer.parseInt(line.split("\\s+")[0]);
            numberImages = br.readLine().split("\\s+");
            numImg = Integer.parseInt(numberImages[0]);
            imgArray = new double[numImg][dimImg];

            count = 0;
            int dimension = 0;
            while (count < numImg){
                br.readLine();
                count += 1;
                    dimension = 0;
                    for (int i = 0; i < dimImg/width; i++){
                        String input = br.readLine();
                        int l = input.length();
                        if (l > 9){ //if line has more than 10 characters
                            input = input.substring(0,10);
                        }
                        for (int j = 0; j < width; j++){
                            dimension += 1; //counter for the full image length, j is counter for current line of image
                            if(input.charAt(j) == 'O'){  //nonwhitspace is 1
                                imgArray[count-1][dimension-1] = 1;
                            }
                            else{ //whitespace is -1
                                imgArray[count-1][dimension-1] = -1;
                            }
                        }
                    }
            }
            br.close();
        }
        catch(Exception e){
            System.out.println("User error, this is all your fault.");
            e.printStackTrace();
            System.out.println(e);
        }
        return imgArray;
    }
}