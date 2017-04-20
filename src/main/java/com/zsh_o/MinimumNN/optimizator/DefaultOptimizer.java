package com.zsh_o.MinimumNN.optimizator;

import org.jblas.DoubleMatrix;

/**
 * Created by zsh_o on 2017/4/19.
 */
public class DefaultOptimizer extends Optimizer {
    double learningRate;

    public DefaultOptimizer(double learningRate) {
        this.learningRate=learningRate;
    }

    public void update() {
        for(String p : node.getParametersTrainable()){
            DoubleMatrix mp=node.getParametersRecord().get(p);
            mp.subi(node.getParametersRecord().get("d"+p).mul(learningRate));
        }
    }
}
