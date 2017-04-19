package com.zsh_o.MinimumNN.util.active;

import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;

/**
 * Created by zsh_o on 2017/4/19.
 */
public class Tanh implements ActivateFunction {
    public double activate(double x) {
        double e = Math.exp(2 * x);
        return (e - 1) / (e + 1);
    }

    public double dActivate(double dy) {
        return 1-Math.pow(dy,2);
    }

    public DoubleMatrix activate(DoubleMatrix x) {
        return MatrixFunctions.tanh(x);
    }

    public DoubleMatrix dActivate(DoubleMatrix dy) {
        return DoubleMatrix.ones(1,dy.length).sub(MatrixFunctions.pow(dy,2));
    }
}
