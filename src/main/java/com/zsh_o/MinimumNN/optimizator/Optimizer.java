package com.zsh_o.MinimumNN.optimizator;

import com.zsh_o.MinimumNN.node.Node;
import org.jblas.DoubleMatrix;

import java.util.Map;

/**
 * Created by zsh_o on 2017/4/19.
 */
public abstract class Optimizer implements IOptimize {
    Node node;

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
