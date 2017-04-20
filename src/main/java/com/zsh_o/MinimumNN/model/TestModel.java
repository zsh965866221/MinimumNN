package com.zsh_o.MinimumNN.model;

import com.zsh_o.MinimumNN.node.Node;
import com.zsh_o.MinimumNN.node.RuningState;
import com.zsh_o.MinimumNN.util.DataScale;
import com.zsh_o.MinimumNN.util.LossFunction;
import org.jblas.DoubleMatrix;

/**
 * Created by zsh_o on 2017/4/20.
 */
public class TestModel extends Model {
    public TestModel(LossFunction.ILoss lossFunction) {
        super(lossFunction);
    }

    @Override
    public double fit(DoubleMatrix X, DoubleMatrix Y) {
        Node firstNode=NNStructure.get(0);
        Node lastNode=NNStructure.get(NNStructure.size()-1);

        Node currentNode;

        for(int i=0;i<X.rows;i++){
            firstNode.setX(X.getRow(i),i);
            currentNode=firstNode;
            while(currentNode!=null){
                currentNode.setRuningState(RuningState.Training);
                currentNode.activate();

                currentNode=currentNode.getOutNode();
            }

            lastNode.setDy(lastNode.getY(i).sub(Y.getRow(i)),i);
        }
        DoubleMatrix pY=lastNode.getY(0);
        for(int i=1;i<X.rows;i++){
            pY=DoubleMatrix.concatVertically(pY,lastNode.getY(i));
        }

        currentNode=lastNode;
        while(currentNode!=null){
            currentNode.train();
            currentNode=currentNode.getInNode();
        }
        clearTime();
        return lossFunction.calculate(pY,Y);
    }

    @Override
    public DoubleMatrix predict(DoubleMatrix X) {
        return forward(X,RuningState.Predicting);
    }

    DoubleMatrix forward(DoubleMatrix X,RuningState state){
        Node firstNode=NNStructure.get(0);
        Node lastNode=NNStructure.get(NNStructure.size()-1);

        Node currentNode;

        for(int i=0;i<X.rows;i++){
            firstNode.setX(X.getRow(i),i);
            currentNode=firstNode;
            while(currentNode.getOutNode()!=null){
                currentNode.setRuningState(state);
                currentNode.activate();
            }
        }
        DoubleMatrix pY=lastNode.getY(0);
        for(int i=1;i<X.rows;i++){
            pY=DoubleMatrix.concatVertically(pY,lastNode.getY(i));
        }
        return pY;
    }

}
