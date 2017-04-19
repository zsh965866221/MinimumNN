/**
 * Created by zsh_o on 2017/4/19.
 */
public class InnerClassAndFinal {
    interface IC{
        double Do(double x);
    }

    double Do(int k,IC ic){
        return ic.Do(k);
    }

    public static void main(String[] args){
//        int K=11;

        Integer K=11;
        InnerClassAndFinal Main=new InnerClassAndFinal();
        System.out.println(Main.Do(10,(k) -> k*k));

        System.out.println(Main.Do(10, new IC() {
            @Override
            public double Do(double x) {
//                K=20;
                return x*x;
            }
        }));
        System.out.println(K);

    }
}
