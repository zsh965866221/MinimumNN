package com.zsh_o.MinimumNN;

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
public class Main {
    public static void main(String[] args) throws IOException {
        TestModel model=new TestModel(new LossFunction.MSE());
        model.addLayer(new LSTMLayer(8,20,new DefaultOptimizer(0.2),new MatrixIniter(MatrixIniter.Type.Gaussian,0,0,0.5),0));
        model.addLayer(new DenseLayer(20,2,new DefaultOptimizer(0.02),new MatrixIniter(MatrixIniter.Type.Gaussian,0,0,0.5),new Tanh(),0));


        DoubleMatrix data=DoubleMatrix.loadCSVFile("C:/Users/zsh96/Desktop/data.csv");
        DoubleMatrix X=data.getRange(0,data.rows,0,8);
        DoubleMatrix Y=data.getRange(0,data.rows,8,10);

        DataScale xScale=new DataScale(-1,1);
        X=xScale.scale(X);
        DataScale yScale=new DataScale(-1,1);
        Y=yScale.scale(Y);

        for(int i=0;i<5000;i++){
            double loss=model.fit(X,Y);
            System.out.println(loss);
        }
//        System.out.println(model.predict(X));
    }
}
