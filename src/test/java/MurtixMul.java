import org.jblas.DoubleMatrix;

/**
 * Created by zsh_o on 2017/4/19.
 */
public class MurtixMul {
    public static void main(String[] args){
        DoubleMatrix m=DoubleMatrix.rand(2,2);
        System.out.println(m);
        DoubleMatrix m1 = new DoubleMatrix(m.rows,m.columns);
        m1.copy(m);
//        m.copy(DoubleMatrix.scalar(m1));

        System.out.println(m1);

        DoubleMatrix k=m.mulColumn(0,0);
        System.out.println(m);

    }
}
