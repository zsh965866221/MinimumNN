package com.zsh_o.MinimumNN;

import com.zsh_o.MinimumNN.layer.DenseLayer;
import com.zsh_o.MinimumNN.model.TestModel;
import com.zsh_o.MinimumNN.optimizator.DefaultOptimizer;
import com.zsh_o.MinimumNN.util.DataScale;
import com.zsh_o.MinimumNN.util.LossFunction;
import com.zsh_o.MinimumNN.util.MatrixIniter;
import com.zsh_o.MinimumNN.util.active.Tanh;
import org.jblas.DoubleMatrix;

/**
 * Created by zsh_o on 2017/4/20.
 */
public class Main {
    public static void main(String[] args){
        TestModel model=new TestModel(new LossFunction.MSE());
        model.addLayer(new DenseLayer(3,4,new DefaultOptimizer(0.9),new MatrixIniter(MatrixIniter.Type.Gaussian,0,0,1),new Tanh(),0));
        model.addLayer(new DenseLayer(4,2,new DefaultOptimizer(0.9),new MatrixIniter(MatrixIniter.Type.Gaussian,0,0,1),new Tanh(),0));

        DoubleMatrix X=DoubleMatrix.randn(30,3);
        DoubleMatrix Y=DoubleMatrix.randn(30,2);
        for(int i=0;i<100;i++){
            System.out.println(model.fit(X,Y));
        }
//        System.out.println(model.predict(X));
    }
}
