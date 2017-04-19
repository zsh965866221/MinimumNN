package com.zsh_o.MinimumNN.optimizator;

import com.zsh_o.MinimumNN.node.Node;
import org.jblas.DoubleMatrix;

/**
 * Created by zsh_o on 2017/4/19.
 */
public class DefaultOptimizator extends Optimizator {
    double learningRate;

    public DefaultOptimizator(Node node,int learningRate) {
        super(node);
        this.learningRate=learningRate;
    }

    public void update(String p) {
        DoubleMatrix mp=node.getParametersRecord().get(p);
        node.getParametersRecord().put(p,mp.sub(node.getParametersRecord().get("d"+p).mul(learningRate)));
    }
}
