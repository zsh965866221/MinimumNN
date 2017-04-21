import org.jblas.DoubleMatrix;

import java.io.IOException;

/**
 * Created by zsh_o on 2017/4/21.
 */
public class CSVtoMatrix {
    public static void main(String[] args) throws IOException {
        DoubleMatrix data=DoubleMatrix.loadCSVFile("C:/Users/zsh96/Desktop/data.csv");
        DoubleMatrix X=data.getRange(0,data.rows,0,8);
        DoubleMatrix Y=data.getRange(0,data.rows,8,10);
        System.out.println();
    }
}
