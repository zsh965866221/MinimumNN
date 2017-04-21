import com.sun.org.apache.bcel.internal.generic.DMUL;
import com.zsh_o.MinimumNN.util.LossFunction;
import com.zsh_o.MinimumNN.util.active.Logistic;
import com.zsh_o.MinimumNN.util.active.Tanh;
import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;
import sun.rmi.runtime.Log;

/**
 * Created by zsh_o on 2017/4/21.
 */
public class SimpleBP {
    public static void main(String[] args){
        double lr=0.02;
        double mr=0.8;

        Tanh logistic=new Tanh();
        LossFunction.MSE mse=new LossFunction.MSE();

        DoubleMatrix W1 = DoubleMatrix.randn(1,10);
        DoubleMatrix b1=DoubleMatrix.randn(1,10);

        DoubleMatrix W2 = DoubleMatrix.randn(10,1);
        DoubleMatrix b2=DoubleMatrix.randn(1,1);

        DoubleMatrix X=DoubleMatrix.linspace(-1,1,100);
        DoubleMatrix Y= X.mul(2);


        DoubleMatrix lW1=DoubleMatrix.zeros(1,10);
        DoubleMatrix lW2=DoubleMatrix.zeros(10,1);
        DoubleMatrix lb1=DoubleMatrix.zeros(1,10);
        DoubleMatrix lb2=DoubleMatrix.zeros(1,1);

        for(int i=0;i<10000;i++){
            DoubleMatrix o1=X.mmul(W1).addRowVector(b1);
            DoubleMatrix y1=logistic.activate(o1);
            DoubleMatrix o2=y1.mmul(W2).addRowVector(b2);
            DoubleMatrix y2=logistic.activate(o2);

            DoubleMatrix dy2=Y.sub(y2);
            DoubleMatrix do2=dy2.mul(logistic.dActivate(y2));
            DoubleMatrix dy1=do2.mmul(W2.transpose());
            DoubleMatrix dW2=y1.transpose().mmul(do2);
            DoubleMatrix db2=DoubleMatrix.ones(1,100).mmul(do2);

            DoubleMatrix do1=dy1.mul(logistic.dActivate(y1));
            DoubleMatrix dW1=X.transpose().mmul(do1);
            DoubleMatrix db1=DoubleMatrix.ones(1,100).mmul(do1);

            W1.addi(dW1.mul(lr));
            b1.addi(db1.mul(lr));
            W2.addi(dW2.mul(lr));
            b2.addi(db2.mul(lr));

            lW1=dW1;
            lW2=dW2;
            lb1=db1;
            lb2=db2;

            System.out.println(mse.calculate(Y,y2)/2);
        }
    }

//    static DoubleMatrix dd(DoubleMatrix Y){
//        return Y.mul(Y.sub(1).mul(-1));
//    }
}
