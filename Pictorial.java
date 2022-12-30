// In this program, we have displayed the change in the dynamics, when rule g is applied.

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class Pictorial extends JPanel {

    public static int X = 250;         // block size
    public static int width = 500;    // CA Size n
    public static int height = 2000;  
    public static int disx = 1;
    public static int disy = 1;
    public static JFrame f = new JFrame();
    final static int[] ruleSet = {
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29,
            30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56,
            57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83,
            84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108,
            109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129,
            130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150,
            151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171,
            172, 173, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 190, 191, 192,
            193, 194, 195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213,
            214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234,
            235, 236, 237, 238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 254, 255
    };

    public static byte[][] cells;
    public static byte[][] cells1;
    public static byte[] buf;

    public static int rule = 0;
    Graphics offScrGraph;

    public static int left, middle, right;
    public static Random randomGenerator = new Random();

    public Pictorial() throws IOException {
        Dimension dim = new Dimension(width, height);
        setPreferredSize(dim);
        setBackground(Color.white);
        setFont(new Font("Arial", Font.BOLD, 28));

        File dir = new File("C:\\Users\\dharm\\Desktop\\clasify\\imagesrl\\Block_Size " + X);
        dir.mkdir();


        cells = new byte[height][width];
        buf = new byte[width];
        cells1 = new byte[height][width];

        byte[] dumarr = { 0, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 1,
                1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0,
                0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0,
                1, 1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0,
                1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1,
                1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1,
                1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0,
                0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1,
                0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1,
                0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0,
                1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0,
                1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1,
                0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0,
                1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1 };               // initial configuration //

                for(int i=0; i<500; i++){
            cells[0][i] = dumarr[i];
        }

        new Timer(1, (ActionEvent e) -> {

            if (rule < ruleSet.length - 1) {
                repaint();
                storeImage();
            } else {
                return;
            }
            rule++;
        }).start();

    }

    public void storeImage() {
        BufferedImage image = new BufferedImage(f.getWidth(), f.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        f.printAll(g2);
        try {
            ImageIO.write(image, "png",
                    new File("C:\\Users\\dharm\\Desktop\\clasify\\imagesrl\\Block_Size " + X + "\\W_" + Integer.toString(X) + "_"
                            + Integer.toString(ruleSet[rule]) + ".png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private byte rule1(int lhs, int mid, int rhs) {           // rule f //
        int idx = (lhs << 2 | mid << 1 | rhs);
        return (byte) (ruleSet[rule] >> idx & 1);
    }

    public static byte[] rule2(byte[] temp) {                // rule g //
        int itr = 0;
        byte[] b = new byte[temp.length];
        b = temp;

        for (int i = 0; i < temp.length; i = i + X) {

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

    private static void count(int i, byte[] temp) {    // count no. of 1s in the block //
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

    void drawCa(Graphics2D g, Graphics2D h) {
        g.setColor(Color.GREEN);

        for (int m = 0; m < width; m++) {
            if (cells[0][m] == 1) {
                g.fillRect(m, 0, disx, disy);
            }
        }

        for (int r = 1; r < cells.length - 1; r++) {

            // 0th cell
            int c = 0;
            byte lhs = cells[r - 1][cells[r].length - 1];
            byte mid = cells[r - 1][c];
            byte rhs = cells[r - 1][c + 1];
            cells[r][c] = rule1(lhs, mid, rhs);
            //

            // Intermediate cell
            for (c = 1; c <= cells[r - 1].length - 1; c++) {
                lhs = cells[r - 1][c - 1];
                mid = cells[r - 1][c];
                rhs = cells[r - 1][(c + 1) % cells[r].length];
                cells[r][c] = rule1(lhs, mid, rhs); // next generation
            }

            for (c = 0; c <= cells[r - 1].length - 1; c++) {
                cells1[r][c] = cells[r][c];
            }
        }

        for (int r = 1; r < cells.length - 1; r++) {

            // 0th cell
            int c = 0;
            byte lhs = cells[r - 1][cells[r].length - 1];
            byte mid = cells[r - 1][c];
            byte rhs = cells[r - 1][c + 1];
            cells[r][c] = rule1(lhs, mid, rhs);
            //

            // Intermediate cell
            for (c = 1; c <= cells[r - 1].length - 1; c++) {
                lhs = cells[r - 1][c - 1];
                mid = cells[r - 1][c];
                rhs = cells[r - 1][(c + 1) % cells[r].length];
                cells[r][c] = rule1(lhs, mid, rhs); // next generation
            }
            //

            cells[r] = rule2(cells[r]);

            for (int m = 0; m < cells[r].length; m++) {
                if (cells[r][m] == 1 && cells[r][m] == cells1[r][m]) {
                    g.fillRect(m, r, disx, disy);
                } else if (cells[r][m] == 1 && cells1[r][m] == 0) {
                    h.setColor(Color.RED);
                    h.fillRect(m, r, disx, disy);
                }

            }

        }
    }

    void drawLegend(Graphics2D g) {
        String s = String.valueOf(ruleSet[rule]);
        int sw = g.getFontMetrics().stringWidth(s);

        g.setColor(Color.white);
        g.fillRect(16, 5, 55, 30);

        g.setColor(Color.black);
        g.drawString(s, 16 + (55 - sw) / 2, 30);

    }

    @Override
    public void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        Graphics2D g = (Graphics2D) gg;
        Graphics2D h = (Graphics2D) gg;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        h.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        drawCa(g, h);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setTitle("Layered CA");
            f.setResizable(true);
            try {
                f.add(new Pictorial(), BorderLayout.CENTER);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);

        });
    }

}