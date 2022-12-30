// In this program, we performed training on two datasets P1 and P2, to create a Two-Class Pattern Classifier.

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class Training_Classifier {

    public static int left, middle, right;
    public static int rule = 0;
    public static int width = 16; // CA Size
    public static int itr2 = 75;/////////////////////////// number of patterns in data 1 ////////////////////////////////////
    public static int height2 = itr2;
    public static int itr3 = 60;/////////////////////////// number of patterns in data 2 ////////////////////////////////////
    public static int height3 = itr3;
    public static int itr1 = (int) Math.pow(2, width);
    public static int height1 = itr1 + 1;

    public static Random randomGenerator = new Random();

    public static byte rule1(int lhs, int mid, int rhs, int rule) {     // rule f //
        int idx = (lhs << 2 | mid << 1 | rhs);
        return (byte) (rule >> idx & 1);
    }

    public static byte[] rule2(byte[] temp, int X) {        // rule g //
        int itr = 0;
        byte[] b = new byte[temp.length];
        b = temp;

        for (int i = 0; i < temp.length - 1; i = i + X) {
            count(i, temp, X);
            itr = 0;
            itr = middle - ((left + middle + right) / 3);

            if (itr >= 0) {
                int cnt = 0;

                for (int index = 0; index < X; index++) { // left to right cell updation in a block
                    if (i + index <= width - 1) {
                        if (temp[i + index] == 1 && itr > 0) {
                            b[i + index] = 0;
                            itr--;
                        }
                    } else if (temp[cnt] == 1 && itr > 0) {
                        b[cnt] = 0;
                        cnt++;
                        itr--;
                    }
                }

            }

            else if (itr < 0) {
                int cnt = 0;
                itr = Math.abs(itr); 

                for (int index = 0; index < X; index++) { // left to right cell updation in a block
                    if (i + index <= width - 1) {
                        if (temp[i + index] == 0 && itr > 0) {
                            b[i + index] = 1;
                            itr--;
                        }
                    } else if (temp[cnt] == 0 && itr > 0) {
                        b[cnt] = 1;
                        cnt++;
                        itr--;
                    }
                }

            }
        }
        return b;

    }

    private static void count(int i, byte[] temp, int X) {     // count no. of 1s in the block //
        left = 0;
        middle = 0;
        right = 0;
        int sl, sm, sr;
        if (i == 0) {
            sl = temp.length - X;
            sm = i;
            sr = i + X;

        } else if (i == temp.length - X) {
            sl = i - X;
            sm = i;
            sr = 0;
        } else {
            sl = i - X;
            sm = i;
            sr = i + X;
        }

        int cnt = 0;
        cnt = sl + X;
        int z = 0;

        for (int j = sl; j < sl + X; j++) {
            if (j >= width && cnt > 0) {
                if (temp[z] == 1) {
                    left++;
                }
                z++;
                cnt--;
            } else if (j < width) {
                if (temp[j] == 1) {
                    left++;
                }
                cnt--;
            }

        }

        cnt = 0;
        cnt = sm + X;
        z = 0;
        for (int j = sm; j < sm + X; j++) {
            if (j >= width && cnt > 0) {
                if (temp[z] == 1) {
                    middle++;
                }
                z++;
                cnt--;
            } else if (j < width) {
                if (temp[j] == 1) {
                    middle++;
                }
                cnt--;
            }

        }

        cnt = 0;
        cnt = sr + X;
        z = 0;
        for (int j = sr; j < sr + X; j++) {
            if (j >= width && cnt > 0) {
                if (temp[z] == 1) {
                    right++;
                }
                z++;
                cnt--;
            } else if (j < width) {
                if (temp[j] == 1) {
                    right++;
                }
                cnt--;
            }

        }
    }

    public static void main(String[] args) throws IOException {

        Scanner sc2 = new Scanner(new FileReader("C:\\Users\\dharm\\Desktop\\ruleset1.txt")).useDelimiter(","); //list of rules that shows convergences for all cells sizes and block sizes, obtained from "Multiple_Attractor.java".
                                                                                                                                  // 4,12,68,76,128,132,136,140,192,196,200,206,207,236,237,238,252,254.

        ArrayList<Integer> ruleset = new ArrayList<Integer>();
        String l;
        while (sc2.hasNext()) {
            l = sc2.next();
            int g = Integer.parseInt(l);
            ruleset.add(g);
        }

        double[][] MaxEf = new double[ruleset.size()][5];
        int rl = 0;

        while (rl < ruleset.size()) {

            int rule = ruleset.get(rl);

            ArrayList<Double> effi = new ArrayList<Double>();

            int X = 1; // X denotes Block Size

            while (X < width) {

                File dir = new File("C:\\Users\\dharm\\Desktop\\clasify\\TRAINING1\\RULE " + rule);
                dir.mkdir();

                File file = new File("C:\\Users\\dharm\\Desktop\\clasify\\TRAINING1\\RULE " + rule + "\\rule" + rule
                        + "block" + X + "table.txt"); ///////////// Table to store the count of attractors
                                                      ///////////// ///////////////
                file.createNewFile();
                PrintStream stream = new PrintStream(file);
                System.setOut(stream);

                int counter = 0;
                byte[][] atta;
                atta = new byte[itr2 + itr3][width]; ////// array to store the combined data1 and data2///////

                int[] data1 = new int[itr2]; ///////////// attractors for data 1. dat//////////
                int[] data2 = new int[itr3]; //////////// attractors for data 2. dat///////////
                int d = 0;

                Scanner sc8 = new Scanner(new BufferedReader(
                        new FileReader(
                                "C:\\Users\\dharm\\Downloads\\DATA\\DATA\\heart-statlog\\heart-statlog1\\cell16\\training\\data1.dat"))); 
                byte[][] monkk = new byte[itr2 + itr3][width];
                while (sc8.hasNextLine()) {
                    for (int i = 0; i < itr2; i++) {
                        String[] line = sc8.nextLine().trim().split(" ");
                        for (int j = 0; j < line.length; j++) {
                            monkk[i][j] = (byte) Integer.parseInt(line[j]);
                        }
                    }
                }

                Scanner sc9 = new Scanner(new BufferedReader(
                        new FileReader(
                                "C:\\Users\\dharm\\Downloads\\DATA\\DATA\\heart-statlog\\heart-statlog1\\cell16\\training\\data2.dat"))); 

                while (sc9.hasNextLine()) {
                    for (int i = itr2; i < itr2 + itr3; i++) {
                        String[] line = sc9.nextLine().trim().split(" ");
                        for (int j = 0; j < line.length; j++) {
                            monkk[i][j] = (byte) Integer.parseInt(line[j]);
                        }
                    }
                }

                for (int i = 0; i < itr2 + itr3; i++) {

                    byte[][] cells;
                    byte[] buf;

                    cells = new byte[height1][width];
                    buf = new byte[width];

                    // 2^width configurations
                    byte[] temp;

                    temp = new byte[width];

                    for (int j = 0; j < width; j++) {

                        cells[0][j] = monkk[i][j];
                    }

                    for (int r = 1; r < cells.length - 1; r++) {

                        // 0th cell
                        int c = 0;
                        byte lhs = cells[r - 1][cells[r].length - 1];
                        byte mid = cells[r - 1][c];
                        byte rhs = cells[r - 1][c + 1];

                        cells[r][c] = rule1(lhs, mid, rhs, rule);
                        //

                        // Intermediate cell
                        for (c = 1; c <= cells[r - 1].length - 1; c++) {
                            lhs = cells[r - 1][c - 1];
                            mid = cells[r - 1][c];
                            rhs = cells[r - 1][(c + 1) % cells[r].length];
                            cells[r][c] = rule1(lhs, mid, rhs, rule); // next generation
                        }
                        //

                        cells[r] = rule2(cells[r], X);

                    }
                    int count = 0;

                    for (int b = 0; b < width; b++) {
                        if (cells[height1 - 3][b] == cells[height1 - 2][b]) {
                            count++;
                        }
                    }

                    if (count == width) {

                        counter++;
                    }

                    for (int s = 0; s < width; s++) {
                        atta[d][s] = cells[height1 - 2][s];

                    }
                    d++;

                }
                if (counter == itr2 + itr3) {

                    int[] arrx;
                    arrx = new int[itr2 + itr3];

                    for (int i = 0; i < itr2 + itr3; i++) {

                        int sum = 0;
                        int y = 0;
                        for (int j = width - 1; j >= 0; j--) {

                            int x = atta[i][j];

                            double n = Math.pow(2, y);
                            y++;
                            int f = (int) n;
                            sum = sum + (f * x);

                        }

                        arrx[i] = sum;

                    }

                    for (int i = 0; i < itr2; i++) {
                        data1[i] = arrx[i];
                    }

                    int op = 0;
                    for (int i = itr2; i < itr2 + itr3; i++) {
                        data2[op] = arrx[i];
                        op++;
                    }

                    Arrays.sort(arrx);
                    ArrayList<Integer> arrxl = new ArrayList<Integer>();
                    int length = arrx.length;
                    int[] tempo = new int[length];
                    int q = 0;
                    for (int i = 0; i < length - 1; i++) {
                        if (arrx[i] != arrx[i + 1]) {
                            tempo[q++] = arrx[i];
                        }
                    }
                    tempo[q++] = arrx[length - 1];

                    for (int i = 0; i < q; i++) {
                        int z = tempo[i];
                        arrxl.add(z);
                    }

                    int[] Crules = new int[arrxl.size()];
                    for (int i = 0; i < arrxl.size(); i++) {
                        Crules[i] = arrxl.get(i);             // Attractors //
                    }

                    ///////////////////////////////////////////////////// DATA SET 1
                    ///////////////////////////////////////////////////// //////////////////////////////////////////////////////////////

                    ArrayList<Integer> ss = new ArrayList<Integer>();

                    for (int i = 0; i < Crules.length; i++) {
                        int sum = 0;
                        for (int j = 0; j < data1.length; j++) {
                            if (Crules[i] == data1[j]) {
                                sum = sum + 1;
                            }
                        }
                        ss.add(sum);          // No. of patterns from P1 converging to an attractor
                    }

                    ///////////////////////////////////////////////////// DATA SET 2
                    ///////////////////////////////////////////////////// ///////////////////////////////////////////////////////////////

                    for (int i = 0; i < Crules.length; i++) {
                        int sum = 0;
                        for (int j = 0; j < data2.length; j++) {
                            if (Crules[i] == data2[j]) {
                                sum = sum + 1;
                            }
                        }
                        System.out.println(Crules[i] + " " + ss.get(i) + " " + sum + " ");  // Attractor | No. of patterns from P1 converging to attractor | No. of patterns from P2 converging to attractor // 
                    }

                    //////////////////////////////////////////////// Efficiency
                    //////////////////////////////////////////////// ///////////////////////////////////////////////////////////////

                    File file1 = new File(
                            "C:\\Users\\dharm\\Desktop\\clasify\\TRAINING1\\RULE " + rule + "\\Class-Block-" + X
                                    + ".txt");
                    file1.createNewFile();
                    PrintStream stream1 = new PrintStream(file1);
                    System.setOut(stream1);

                    Scanner sc4 = new Scanner(new BufferedReader(
                            new FileReader(
                                    "C:\\Users\\dharm\\Desktop\\clasify\\TRAINING1\\RULE " + rule + "\\rule" + rule
                                            + "block" + X + "table.txt")));
                    int roww = Crules.length;
                    int[] n1 = new int[roww];
                    while (sc4.hasNextLine()) {
                        for (int i = 0; i < n1.length; i++) {
                            String[] line = sc4.nextLine().trim().split(" ");
                            int j = 1;
                            n1[i] = Integer.parseInt(line[j]);

                        }
                    }

                    Scanner sc5 = new Scanner(new BufferedReader(
                            new FileReader(
                                    "C:\\Users\\dharm\\Desktop\\clasify\\TRAINING1\\RULE " + rule + "\\rule" + rule
                                            + "block" + X + "table.txt")));
                    int[] n2 = new int[roww];
                    while (sc5.hasNextLine()) {
                        for (int i = 0; i < n2.length; i++) {
                            String[] line = sc5.nextLine().trim().split(" ");
                            int j = 2;
                            n2[i] = Integer.parseInt(line[j]);

                        }
                    }

                    double success = 0;

                    ArrayList<Integer> p1 = new ArrayList<Integer>();
                    ArrayList<Integer> p2 = new ArrayList<Integer>();
                    for (int i = 0; i < Crules.length; i++) {
                        if (n1[i] > n2[i]) {
                            p1.add(Crules[i]);
                            success = success + n1[i];
                        } else if (n2[i] > n1[i]) {
                            p2.add(Crules[i]);
                            success = success + n2[i];
                        } else if (n1[i] == n2[i]) {
                            p1.add(Crules[i]);
                            success = success + n1[i];
                        }
                    }
                    double eff;
                    double p = itr2 + itr3;
                    eff = success / p;
                    eff = eff * 100;

                    effi.add(eff);

                    int zz = 0;
                    zz = p1.size() + p2.size();
                    System.out.println("Class 1 - " + p1);
                    System.out.println("Class 2 - " + p2);
                    System.out.println("Success " + success);
                    System.out.println("Efficiency " + eff);
                    System.out.println("No. Of Attractors " + zz);

                }

                X++;
            }

            /////////////////////////////////////////////// MAXIMUM
            /////////////////////////////////////////////// EFFICIENCY///////////////////////////////////////////////////////////

            File file2 = new File(
                    "C:\\Users\\dharm\\Desktop\\clasify\\TRAINING1\\RULE " + rule + "\\EFFICIENCY.txt");
            file2.createNewFile();
            PrintStream stream2 = new PrintStream(file2);
            System.setOut(stream2);

            double max = Collections.max(effi);
            System.out.println("Maximum efficiency : " + max);

            double maximum = effi.get(0);
            int large = 0;
            for (int i = 1; i < effi.size(); i++) {

                if (maximum < effi.get(i)) {
                    maximum = effi.get(i);
                    large = i;
                }
            }
            large = large + 1;
            System.out.print("Rule " + rule + " Block " + large);
            System.out.println();

            double min = Collections.min(effi);
            System.out.println("Minimum efficiency : " + min);

            Double minimum = effi.get(0);
            int small = 0;
            for (int i = 1; i < effi.size(); i++) {
                if (minimum > effi.get(i)) {
                    minimum = effi.get(i);
                    small = i;
                }
            }
            small = small + 1;
            System.out.print("Rule " + rule + " Block " + small);

            int j = 0;
            MaxEf[rl][j] = rule;
            j++;
            MaxEf[rl][j] = large;
            j++;
            MaxEf[rl][j] = max;
            j++;
            MaxEf[rl][j] = small;
            j++;
            MaxEf[rl][j] = min;

            rl++;
        }

        File file3 = new File(
                "C:\\Users\\dharm\\Desktop\\clasify\\TRAINING1\\EFFICIENCY Comparision Table.txt");
        file3.createNewFile();
        PrintStream stream3 = new PrintStream(file3);
        System.setOut(stream3);

        for (int i = 0; i < ruleset.size(); i++) {
            int j = 0;
            System.out.print((int) MaxEf[i][j] + " ");
            j++;
            System.out.print((int) MaxEf[i][j] + " ");
            j++;
            System.out.print(MaxEf[i][j] + " ");
            j++;
            System.out.print((int) MaxEf[i][j] + " ");
            j++;
            System.out.print(MaxEf[i][j] + " ");
            System.out.println();
        }

    }
}