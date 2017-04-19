package com.zsh_o.MinimumNN.util;

import org.jblas.DoubleMatrix;

/**
 * Created by zsh_o on 2017/4/19.
 */
public interface Iscale {
    DoubleMatrix scale(DoubleMatrix a);
    DoubleMatrix deScale(DoubleMatrix b);
}
