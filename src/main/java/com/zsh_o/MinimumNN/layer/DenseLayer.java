package com.zsh_o.MinimumNN.layer;

import com.zsh_o.MinimumNN.node.Node;
import com.zsh_o.MinimumNN.optimizator.Optimizator;
import com.zsh_o.MinimumNN.util.MatrixIniter;
import org.jblas.DoubleMatrix;

/**
 * Created by zsh_o on 2017/4/19.
 */
public class DenseLayer extends Node {

    DoubleMatrix W,b;

    public DenseLayer(int inSize, int outSize, Optimizator optimizator, MatrixIniter matrixIniter) {
        super(inSize, outSize, optimizator, matrixIniter);
        this.W=matrixIniter.generate(inSize,outSize);
        this.b=new DoubleMatrix(1,outSize);
    }

    public void activate() {

    }

    public void train() {

    }

    public void updateParameters() {

    }
}
