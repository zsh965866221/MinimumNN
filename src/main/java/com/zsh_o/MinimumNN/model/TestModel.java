package com.zsh_o.MinimumNN.model;

import com.zsh_o.MinimumNN.node.Node;
import com.zsh_o.MinimumNN.util.DataScale;
import com.zsh_o.MinimumNN.util.LossFunction;
import org.jblas.DoubleMatrix;

/**
 * Created by zsh_o on 2017/4/20.
 */
public class TestModel extends Model {
    public TestModel(LossFunction.ILoss lossFunction, DataScale dataScale) {
        super(lossFunction, dataScale);
    }

    @Override
    double fit(DoubleMatrix X, DoubleMatrix Y) {
        Node firstNode=NNStructure.get(0);
        Node lastNode=NNStructure.get(NNStructure.size()-1);

        for(int i=0;i<X.rows;i++){
            firstNode.setX(X.getRow(i));
            lastNode.setDy(Y.getRow(i));
        }

        Node currentNode=firstNode;
        while(currentNode.getOutNode()!=null){
            currentNode.activate();
        }
    }

    @Override
    DoubleMatrix predict(DoubleMatrix X) {
        return null;
    }

    @Override
    void train() {

    }
}
