package com.zsh_o.MinimumNN.optimizator;

import com.zsh_o.MinimumNN.node.Node;
import org.jblas.DoubleMatrix;

import java.util.Map;

/**
 * Created by zsh_o on 2017/4/19.
 */
public abstract class Optimizator implements IOptimize {
    Node node;

    public Optimizator(Node node) {
        this.node = node;
    }
}
