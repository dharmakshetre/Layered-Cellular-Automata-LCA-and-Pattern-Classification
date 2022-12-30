//This program finds the Single and Multiple Attractors for different CA Size(n, where n is ranging from 4 to 11) and for each n, different block sizes(1 to n-1)

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class List_Multiple_Convergence {

    public static int left, middle, right;
    public static int rule = 0;
    public static int width = 11;  // CA Size n
    public static int itr = (int) Math.pow(2, width);
    public static int height = itr + 1;
    public static Random randomGenerator = new Random();

    public static byte rule1(int lhs, int mid, int rhs, int rule) {          // rule f
        int idx = (lhs << 2 | mid << 1 | rhs);
        return (byte) (rule >> idx & 1);
    }

    public static byte[] rule2(byte[] temp, int X) {       // rule g
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

    private static void count(int i, byte[] temp, int X) {   // count no. of 1s in the block //
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

    public static int removeDuplicates(int array[], int n) {
        if (n == 0 || n == 1) {
            return n;
        }
        int[] temp = new int[n];
        int j = 0;
        for (int i = 0; i < n - 1; i++) {
            if (array[i] != array[i + 1]) {
                temp[j++] = array[i];
            }
        }
        temp[j++] = array[n - 1];

        for (int i = 0; i < j; i++) {
            array[i] = temp[i];
        }
        return j;
    }

    public static void main(String[] args) throws IOException {

        int X = 1;          // block size
        while(X<width){

        File file = new File("C:\\Users\\dharm\\Desktop\\clasify\\BFR2\\CA "+width+"\\TypeOfAttractor "+X+".txt");
        PrintStream stream = new PrintStream(file);
        System.setOut(stream);

        int rule = 0;
        
        ArrayList<Integer> arrli = new ArrayList<Integer>();
        ArrayList<Integer> arrli3 = new ArrayList<Integer>();

        while (rule < 256) {

            int counter = 0;
            byte[][] atta;
            atta = new byte[height][width];
            int d = 0;

            for (int i = 0; i < itr; i++) {

                byte[][] cells;
                byte[] buf;

                cells = new byte[height][width];
                buf = new byte[width];

                byte[] temp;
                temp = new byte[width];

                String f;
                f = Integer.toBinaryString(i);

                temp = f.getBytes();

                int j = cells[0].length - 1;
                for (int z = temp.length - 1; z >= 0; z--) {

                    if (temp[z] == 49) {
                        cells[0][j] = 1;
                    } else {
                        cells[0][j] = 0;
                    }
                    j--;
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

                    cells[r] = rule2(cells[r],X);

                }
                int count = 0;

                for (int b = 0; b < width; b++) {
                    if (cells[height - 3][b] == cells[height - 2][b]) {
                        count++;
                    }
                }

                if (count == width) {

                    counter++;
                }

                for (int s = 0; s < width; s++) {
                    atta[d][s] = cells[height - 2][s];
                }
                d++;

            }
            if (counter == itr) {
                
                int[] arr;
                arr = new int[itr];

                for (int i = 0; i < itr; i++) {

                    int sum = 0;
                    int y = 0;
                    for (int j = width - 1; j >= 0; j--) {

                        int x = atta[i][j];

                        double n = Math.pow(2, y);
                        y++;
                        int f = (int) n;
                        sum = sum + (f * x);

                    }

                    arr[i] = sum;

                }

                Arrays.sort(arr);// sorting array
                int length = arr.length;
                length = removeDuplicates(arr, length);

                if(length==1){
                    
                    arrli.add(rule);                   // single attractor
                }

                if(length>=2){
                    
                    arrli3.add(rule);                 // multiple attractor
                }
             
            }           

            rule++;

        }
        System.out.println("Single Attractor");
        System.out.println();
        System.out.println(arrli);
        System.out.println();
        System.out.println();
        System.out.println("Multiple Attractor");
        System.out.println();
        System.out.println(arrli3);

        File dir2 = new File("C:\\Users\\dharm\\Desktop\\clasify\\BFR2\\CA " + width +"\\single");
        dir2.mkdir();

        File file2 = new File("C:\\Users\\dharm\\Desktop\\clasify\\BFR2\\CA "+width+"\\single\\single "+X+".txt");
        PrintStream stream2 = new PrintStream(file2);
        System.setOut(stream2);
        
        for(int i=0; i<arrli.size();i++){
            System.out.print(arrli.get(i)+" ");
        }

        File dir1 = new File("C:\\Users\\dharm\\Desktop\\clasify\\BFR2\\CA " + width +"\\multiple");
        dir1.mkdir();

        File file1 = new File("C:\\Users\\dharm\\Desktop\\clasify\\BFR2\\CA "+width+"\\multiple\\multiple "+X+".txt");
        PrintStream stream1 = new PrintStream(file1);
        System.setOut(stream1);
        
        for(int i=0; i<arrli3.size();i++){
            System.out.print(arrli3.get(i)+" ");
        }

        X++;

    }


    }

}