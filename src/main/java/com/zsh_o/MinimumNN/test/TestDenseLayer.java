package com.zsh_o.MinimumNN.test;

import com.zsh_o.MinimumNN.layer.DenseLayer;
import com.zsh_o.MinimumNN.layer.LSTMLayer;
import com.zsh_o.MinimumNN.model.TestModel;
import com.zsh_o.MinimumNN.optimizator.DefaultOptimizer;
import com.zsh_o.MinimumNN.util.DataScale;
import com.zsh_o.MinimumNN.util.LossFunction;
import com.zsh_o.MinimumNN.util.MatrixIniter;
import com.zsh_o.MinimumNN.util.active.Logistic;
import com.zsh_o.MinimumNN.util.active.Tanh;
import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;

import javax.xml.crypto.Data;
import java.io.IOException;

/**
 * Created by zsh_o on 2017/4/20.
 */
public class TestDenseLayer {
    public static void main(String[] args) throws IOException {
        double lr=0.005;
        double mr=0.8;
        TestModel model=new TestModel(new LossFunction.MSE());
        model.addLayer(new LSTMLayer(1,50,new DefaultOptimizer(lr,mr),new MatrixIniter(MatrixIniter.Type.Gaussian,0,0,0.3),0));
        model.addLayer(new DenseLayer(50,1,new DefaultOptimizer(lr,mr),new MatrixIniter(MatrixIniter.Type.Gaussian,0,0,0.3),new Tanh(),0));

//        DoubleMatrix data=DoubleMatrix.loadCSVFile("C:/Users/zsh96/Desktop/data.csv");//8x2
//        DoubleMatrix X=data.getRange(0,data.rows,0,8);
//        DoubleMatrix Y=data.getRange(0,data.rows,8,10);

        DoubleMatrix X=DoubleMatrix.linspace(-20,20,100);//1x1
        DoubleMatrix Y= MatrixFunctions.sin(X).mul(5).sub(MatrixFunctions.pow(X,2)).add(X.mul(5));

        DataScale xScale=new DataScale(-1,1);
        X=xScale.scale(X);
        DataScale yScale=new DataScale(-1,1);
        Y=yScale.scale(Y);

        for(int i=0;i<5000;i++){
            double loss=model.fit(X,Y);
            System.out.println(loss);
            if(loss<0.02)
                break;
        }
        System.out.println(Y);
        System.out.println(model.predict(X));
    }
}
