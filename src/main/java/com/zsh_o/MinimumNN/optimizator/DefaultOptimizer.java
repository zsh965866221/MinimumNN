package com.zsh_o.MinimumNN.optimizator;

import com.zsh_o.MinimumNN.util.RandomFactory;
import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;

/**
 * Created by zsh_o on 2017/4/19.
 */
public class DefaultOptimizer extends Optimizer {
    double learningRate;
    double momentum=0.8;

    public DefaultOptimizer(double learningRate,double momentum) {
        this.learningRate=learningRate;
        this.momentum=momentum;
    }

    public void update() {
        for(String p : node.getParametersTrainable()){
            DoubleMatrix mp=node.getParametersRecord().get(p);

            DoubleMatrix lastP=node.getParametersRecord().get("last"+p);
            if(lastP==null)
                lastP=mp.dup();

            DoubleMatrix ddp=mp.sub(lastP);

            DoubleMatrix dp=node.getParametersRecord().get("d"+p);
            for(int i=0;i<dp.rows;i++){
                for(int j=0;j<dp.columns;j++){
                    if(dp.get(i,j)<1e-6){
                        dp.put(i,j, RandomFactory.get().nextGaussian()/1e5);
                    }
                    if(ddp.get(i,j)<1e-6){
                        ddp.put(i,j, RandomFactory.get().nextGaussian()/1e5);
                    }
                }
            }

            mp.subi(dp.mul(learningRate).add(ddp.mul(momentum)));
        }
    }
}
