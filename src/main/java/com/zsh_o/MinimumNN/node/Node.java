package com.zsh_o.MinimumNN.node;

import com.zsh_o.MinimumNN.optimizator.Optimizator;
import com.zsh_o.MinimumNN.util.MatrixIniter;
import org.jblas.DoubleMatrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zsh_o on 2017/4/19.
 */
public abstract class Node implements INode {
    Map<String,DoubleMatrix> StatesRecord;
    Map<String,DoubleMatrix> ParametersRecord;

    ArrayList<String> ParametersTrainable;

    Optimizator optimizator;

    MatrixIniter matrixIniter;


    int inSize,outSize;

    Node inNode,outNode;

    /**
     * 迭代次数
     * */
    int iterNum;

    public abstract void updateParameters();

    public Node(int inSize,int outSize,Optimizator optimizator,MatrixIniter matrixIniter) {
        this.optimizator = optimizator;
        this.matrixIniter=matrixIniter;
        this.inSize=inSize;
        this.outSize=outSize;
        StatesRecord=new HashMap<String, DoubleMatrix>();
        ParametersTrainable=new ArrayList<String>();
        ParametersRecord=new HashMap<String, DoubleMatrix>();
    }

    public Map<String, DoubleMatrix> getStatesRecord() {
        return StatesRecord;
    }

    public Map<String, DoubleMatrix> getParametersRecord() {
        return ParametersRecord;
    }

    public ArrayList<String> getParametersTrainable() {
        return ParametersTrainable;
    }

    public Optimizator getOptimizator() {
        return optimizator;
    }

    public int getIterNum() {
        return iterNum;
    }

    public Node getInNode() {
        return inNode;
    }

    public void setInNode(Node inNode) {
        this.inNode = inNode;
    }

    public Node getOutNode() {
        return outNode;
    }

    public void setOutNode(Node outNode) {
        this.outNode = outNode;
    }

    public void clearStates(){
        StatesRecord.clear();
    }
}
