// In this program, We use the list of multiple convergent LCAs, obtained from "List_Multiple_Convergence.java". And find out ruleset which shows multiple convergence for all CA Size(n, where n is ranging from 4 to 11) and for each n, different block sizes(1 to n-1), we use that ruleset for pattern classification.

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class Multiple_Attractor {

    public static void main(String[] args) throws IOException {

        // list of rules with multiple atrractor when CA size(n) is 11 and block size ranging from 1 to (n-1).

        int[] arr41 = { 4,6,12,14,20,22,28,30,36,38,44,46,52,54,60,62,68,70,76,78,84,86,88,92,94,100,102,108,110,116,118,120,124,126,128,130,132,133,134,135,136,138,140,141,142,143,144,146,148,150,152,154,156,158,160,162,164,165,166,167,168,170,172,173,174,175,176,178,180,182,184,186,188,190,192,194,196,197,198,199,200,202,204,205,206,207,208,210,212,214,216,217,218,220,221,222,224,226,228,229,230,231,232,234,236,237,238,239,240,242,244,246,248,249,250,252,253,254 };
        int[] arr42 = { 4,12,14,20,28,30,68,76,78,84,92,94,128,130,132,135,136,138,140,142,144,148,152,156,158,164,172,174,175,192,194,196,200,202,204,205,206,207,208,212,220,221,222,223,228,236,237,238,239,252,254 };
       int[] arr43 = { 4,12,36,68,76,78,128,132,136,140,160,192,196,200,204,205,206,207,220,221,232,236,237,238,239,252,254 };
       int[] arr44 = { 4,12,36,44,68,72,76,78,100,128,132,136,140,192,196,200,204,206,207,217,220,236,237,238,252,254 };
       int[] arr45 = { 4,12,36,68,72,76,78,100,104,128,132,136,140,160,192,196,200,204,206,207,232,236,237,238,252,254 };
       int[] arr46 = { 4,12,36,68,72,76,78,128,132,136,140,192,196,200,204,206,207,236,237,238,252,254 };
        int[] arr47 = { 4,12,36,44,68,72,76,78,100,128,132,136,140,160,164,192,196,200,204,206,207,220,232,236,237,238,252,254 };
       int[] arr48 = { 4,12,36,44,68,72,76,78,100,128,132,136,140,164,192,196,200,204,206,207,220,236,237,238,252,254 };
       int [] arr49 = {4,12,36,68,72,76,78,104,128,132,136,140,160,192,196,200,204,206,207,219,220,232,236,237,238,252,254};
       int [] arr410 = {4,12,36,44,68,72,76,78,100,128,132,136,140,192,196,200,203,204,206,207,217,219,220,221,222,223,236,237,238,252,254};

                int [] count = new int[arr41.length];

                for(int i=0; i<arr41.length; i++){
                    int cnt=0;
                    for(int j=0; j<arr42.length; j++){
                        if(arr41[i]==arr42[j]){
                            cnt++;
                            count[i] = cnt;
                        }
                    }
                    for(int j=0; j<arr43.length; j++){
                        if(arr41[i]==arr43[j]){
                            cnt++;
                            count[i] = cnt;
                        }
                    }
                    for(int j=0; j<arr44.length; j++){
                        if(arr41[i]==arr44[j]){
                            cnt++;
                            count[i] = cnt;
                        }
                    }
                    for(int j=0; j<arr45.length; j++){
                        if(arr41[i]==arr45[j]){
                            cnt++;
                            count[i] = cnt;
                        }
                    }
                    for(int j=0; j<arr46.length; j++){
                        if(arr41[i]==arr46[j]){
                            cnt++;
                            count[i] = cnt;
                        }
                    }
                    for(int j=0; j<arr47.length; j++){
                        if(arr41[i]==arr47[j]){
                            cnt++;
                            count[i] = cnt;
                        }
                    }
                    for(int j=0; j<arr48.length; j++){
                        if(arr41[i]==arr48[j]){
                            cnt++;
                            count[i] = cnt;
                        }
                    }
                    for(int j=0; j<arr49.length; j++){
                        if(arr41[i]==arr49[j]){
                            cnt++;
                            count[i] = cnt;
                        }
                    }
                    for(int j=0; j<arr410.length; j++){
                        if(arr41[i]==arr410[j]){
                            cnt++;
                            count[i] = cnt;
                        }
                    }

                }

                int [] arr = new int [255];
                int j=0;
                for(int i=0; i<count.length; i++){
                    if(count[i]==9){
                        arr[j]=arr41[i];
                        j++;
                    }
                }

        File file = new File("C:\\Users\\dharm\\Desktop\\clasify\\ruleset1.txt");
        PrintStream stream = new PrintStream(file);
        System.setOut(stream);

        for(int i=0; i<255; i++){
            System.out.print(arr[i]+",");
        }

    }
}
