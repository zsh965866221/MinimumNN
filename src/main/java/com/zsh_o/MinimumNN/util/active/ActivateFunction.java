package com.zsh_o.MinimumNN.util.active;

import org.jblas.DoubleMatrix;

/**
 * Created by zsh_o on 2017/4/19.
 */
public interface ActivateFunction {
    double activate(double x);
    double dActivate(double dy);

    DoubleMatrix activate(DoubleMatrix x);
    DoubleMatrix dActivate(DoubleMatrix dy);
}
