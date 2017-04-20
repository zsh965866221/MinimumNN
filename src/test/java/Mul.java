import org.jblas.DoubleMatrix;

/**
 * Created by zsh_o on 2017/4/20.
 */
public class Mul {
    public static void main(String[] args){
        DoubleMatrix a=DoubleMatrix.randn(1,3);
        DoubleMatrix b=DoubleMatrix.randn(3,4);

        System.out.println(a.mmul(b));
    }
}
