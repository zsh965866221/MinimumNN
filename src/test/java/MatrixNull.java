import org.jblas.DoubleFunction;
import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;

/**
 * Created by zsh_o on 2017/4/20.
 */
public class MatrixNull {
    public static void main(String[] args){
        DoubleMatrix a=new DoubleMatrix(1,3);
        System.out.println(a);
        a.addRowVector(new DoubleMatrix(1,3));
        System.out.println(a);

        DoubleMatrix b= DoubleMatrix.randn(1,4);
        System.out.println(b);
        DoubleMatrix b1=DoubleMatrix.randn(1,4);
        System.out.println(b1);
        DoubleMatrix b2=DoubleMatrix.concatVertically(b,b1);
        System.out.println(b2.columns);
        System.out.println(b);
        System.out.println(b1);

        b1.subi(1);
        System.out.println(b1);
    }
}
