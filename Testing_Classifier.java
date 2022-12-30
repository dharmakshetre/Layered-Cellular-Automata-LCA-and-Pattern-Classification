// In this program, we performed testing on two new datasets P1 and P2, to find LCA, which shows maximum efficiency(accuracy).

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class Testing_Classifier {

    public static int left, middle, right;
    public static int rule = 68;
    public static int X = 9; // block size
    public static int width = 16; // CA Size n
    public static int itr3 = 227;//////////////////////////// number of patterns in data 1 //////////////////////////////////////
    public static int height3 = itr3;
    public static int itr2 = 205;///////////////////////////// number of patterns in data 2 ///////////////////////////////
    public static int height2 = itr2;
    public static int itr1 = (int) Math.pow(2, width);
    public static int height1 = itr1 + 1;

    public static Random randomGenerator = new Random();

    public static byte rule1(int lhs, int mid, int rhs, int rule) {    // rule f //
        int idx = (lhs << 2 | mid << 1 | rhs);
        return (byte) (rule >> idx & 1);
    }

    public static byte[] rule2(byte[] temp) {        // rule g //
        int itr = 0;
        byte[] b = new byte[temp.length];
        b = temp;

        for (int i = 0; i < temp.length - 1; i = i + X) {

            count(i, temp);
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

    private static void count(int i, byte[] temp) {  // count no. of 1s in the block //
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

        int[] class1 = { 16, 64, 272, 288, 320 };
        int[] class2 = { 0, 32, 128, 144, 160, 256 };

        File file = new File("C:\\Users\\dharm\\Desktop\\clasify\\TESTING\\rule" + rule + ".txt");
        file.createNewFile();
        PrintStream stream = new PrintStream(file);
        System.setOut(stream);

        int counter = 0;
        byte[][] atta;
        atta = new byte[height1][width];
        int[] data1 = new int[itr3]; ///////////// attractors for data 1. dat //////////
        int[] data2 = new int[itr2];
        int d = 0;

        Scanner sc8 = new Scanner(new BufferedReader(
                new FileReader(
                        "C:\\Users\\dharm\\Downloads\\DATA\\DATA\\heart-statlog\\heart-statlog1\\cell16\\testing\\data1.dat")));

        byte[][] monkk = new byte[itr2 + itr3][width];
        while (sc8.hasNextLine()) {
            for (int i = 0; i < itr3; i++) {
                String[] line = sc8.nextLine().trim().split(" ");
                for (int j = 0; j < line.length; j++) {
                    monkk[i][j] = (byte) Integer.parseInt(line[j]);
                }
            }
        }

        Scanner sc9 = new Scanner(new BufferedReader(
                new FileReader(
                        "C:\\Users\\dharm\\Downloads\\DATA\\DATA\\heart-statlog\\heart-statlog1\\cell16\\testing\\data2.dat")));

        while (sc9.hasNextLine()) {
            for (int i = itr3; i < itr2 + itr3; i++) {
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

                // Intermediate cell
                for (c = 1; c <= cells[r - 1].length - 1; c++) {
                    lhs = cells[r - 1][c - 1];
                    mid = cells[r - 1][c];
                    rhs = cells[r - 1][(c + 1) % cells[r].length];
                    cells[r][c] = rule1(lhs, mid, rhs, rule); // next generation
                }

                cells[r] = rule2(cells[r]); // ********************************************************************************//

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

            for (int i = 0; i < itr3; i++) {
                data1[i] = arrx[i];
            }

            int op = 0;
            for (int i = itr3; i < itr2 + itr3; i++) {
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
                Crules[i] = arrxl.get(i);              // Attractors //
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
                ss.add(sum); // No. of patterns from P1 converging to an attractor

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
                System.out.println(Crules[i] + " " + ss.get(i) + " " + sum + " "); // Attractor | No. of patterns from P1 converging to attractor | No. of patterns from P2 converging to attractor // 
            }

            /////////////////////////////////////////////// Comparision /////////////////////////////////////////////////

            File file1 = new File("C:\\Users\\dharm\\Desktop\\clasify\\TESTING\\Efficiency.txt");
            file1.createNewFile();
            PrintStream stream1 = new PrintStream(file1);
            System.setOut(stream1);

            Scanner sc4 = new Scanner(new BufferedReader(
                    new FileReader(
                            "C:\\Users\\dharm\\Desktop\\clasify\\TESTING\\rule" + rule + ".txt")));
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
                            "C:\\Users\\dharm\\Desktop\\clasify\\TESTING\\rule" + rule + ".txt")));
            int[] n2 = new int[roww];
            while (sc5.hasNextLine()) {
                for (int i = 0; i < n2.length; i++) {
                    String[] line = sc5.nextLine().trim().split(" ");
                    int j = 2;
                    n2[i] = Integer.parseInt(line[j]);

                }
            }

            double success = 0;

            for (int i = 0; i < Crules.length; i++) {
                for (int j = 0; j < class1.length; j++) {
                    if (Crules[i] == class1[j]) {
                        success = success + n1[i];
                    }
                }
            }

            for (int i = 0; i < Crules.length; i++) {
                for (int j = 0; j < class2.length; j++) {
                    if (Crules[i] == class2[j]) {
                        success = success + n2[i];
                    }
                }
            }

            int cls1 = class1.length;
            int cls2 = class2.length;
            int rsl = cls1 + cls2;
            int[] result = new int[rsl];
            System.arraycopy(class1, 0, result, 0, cls1);
            System.arraycopy(class2, 0, result, cls1, cls2);

            for (int i = 0; i < Crules.length; i++) {
                int find = 0;
                for (int j = 0; j < result.length; j++) {
                    if (Crules[i] == result[j]) {
                        find = 1;
                    }
                }
                if (find == 0) {
                    if (n1[i] > n2[i]) {
                        success = success + n1[i];
                    } else if (n2[i] > n1[i]) {
                        success = success + n2[i];
                    }
                }
            }

            double eff;
            double p = itr2 + itr3;
            eff = success / p;

            //////////////////////////////////////////////// Efficiency
            //////////////////////////////////////////////// ///////////////////////////////////////////////////////////////

            System.out.println("Success " + success);
            System.out.println("Efficiency " + eff);

        }
    }

}
