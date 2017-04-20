package com.zsh_o.MinimumNN.model;

import com.zsh_o.MinimumNN.node.Node;
import com.zsh_o.MinimumNN.util.DataScale;
import com.zsh_o.MinimumNN.util.LossFunction;
import org.jblas.DoubleMatrix;

import java.util.ArrayList;

/**
 * Created by zsh_o on 2017/4/20.
 */
public abstract class Model {
    ArrayList<Node> NNStructure;
    LossFunction.ILoss lossFunction;
    DataScale dataScale;

    public Model(LossFunction.ILoss lossFunction, DataScale dataScale) {
        this.lossFunction = lossFunction;
        this.dataScale = dataScale;
        NNStructure=new ArrayList<>();
    }

    public void addLayer(Node node){
        if(NNStructure.size()!=0){
            Node preNode= NNStructure.get(NNStructure.size()-1);
            preNode.setOutNode(node);
            node.setInNode(preNode);
        }
        NNStructure.add(node);
    }

    abstract double fit(DoubleMatrix X, DoubleMatrix Y);
    abstract DoubleMatrix predict(DoubleMatrix X);
    abstract void train();
}
