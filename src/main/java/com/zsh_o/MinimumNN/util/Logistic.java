package com.zsh_o.MinimumNN.util;

import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;

/**
 * Created by zsh_o on 2017/4/19.
 */
public class Logistic implements ActivateFunction {
    public double activate(double x) {
        return 1/(1+Math.exp(-x));
    }

    public double dActivate(double dy) {
        return dy*(1-dy);
    }

    public DoubleMatrix activate(DoubleMatrix x) {
        return MatrixFunctions.pow(MatrixFunctions.exp(x.mul(-1)).add(1),-1);
    }

    public DoubleMatrix dActivate(DoubleMatrix dy) {
        return dy.mul(DoubleMatrix.ones(1,dy.length).sub(dy));
    }
}
