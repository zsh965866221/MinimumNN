import org.jblas.DoubleMatrix;

/**
 * Created by zsh_o on 2017/4/19.
 */
public class Test1 {

    Test2 test2;

    public Test1(Test2 test2) {
        this.test2 = test2;
    }

    class Test2{
        Test1 test;

        public Test2(Test1 test) {
            this.test = test;
        }
    }

    public static void main(String[] args){
//        Test1 test1=new Test1(new Test2(this));

        DoubleMatrix a=DoubleMatrix.randn(2,3);
        System.out.println(a);
        DoubleMatrix b=a.addRowVector(DoubleMatrix.ones(1,3));
        System.out.println(b);
    }
}
