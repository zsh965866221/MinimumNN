package com.zsh_o.MinimumNN.node;

import com.zsh_o.MinimumNN.optimizator.Optimizer;
import com.zsh_o.MinimumNN.util.MatrixIniter;
import com.zsh_o.MinimumNN.util.RandomFactory;
import org.jblas.DoubleMatrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zsh_o on 2017/4/19.
 */
public abstract class Node implements INode {
    protected Map<String,DoubleMatrix> StatesRecord;
    protected Map<String,DoubleMatrix> ParametersRecord;

    protected ArrayList<String> ParametersTrainable;

    protected Optimizer optimizer;

    protected MatrixIniter matrixIniter;


    protected int inSize,outSize;

    protected Node inNode,outNode;

    protected double droupRate=0;

    public int CurrentTime;

    protected RuningState runingState;


    /**
     * 迭代次数
     * */
    protected int iterNum;

    public abstract void updateParameters();

    public Node(int inSize, int outSize, Optimizer optimizer, MatrixIniter matrixIniter) {
        this.optimizer = optimizer;
        optimizer.setNode(this);
        this.matrixIniter=matrixIniter;
        this.inSize=inSize;
        this.outSize=outSize;
        StatesRecord=new HashMap<String, DoubleMatrix>();
        ParametersTrainable=new ArrayList<String>();
        ParametersRecord=new HashMap<String, DoubleMatrix>();
    }
    public Node(int inSize, int outSize, Optimizer optimizer, MatrixIniter matrixIniter,double droupRate) {
        this.optimizer = optimizer;
        optimizer.setNode(this);
        this.matrixIniter=matrixIniter;
        this.inSize=inSize;
        this.outSize=outSize;
        StatesRecord=new HashMap<String, DoubleMatrix>();
        ParametersTrainable=new ArrayList<String>();
        ParametersRecord=new HashMap<String, DoubleMatrix>();
        this.droupRate=droupRate;
    }

    public DoubleMatrix DroupOut(DoubleMatrix X){
        DoubleMatrix tX=new DoubleMatrix(X.rows,X.columns);
        tX.copy(X);
        for(int i=0;i<inSize;i++){
            if(RandomFactory.get().nextDouble()<droupRate){
                tX.mulColumn(i,0);
            }
        }
        return tX;
    }

    public void setX(DoubleMatrix X,int t){
        StatesRecord.put("x"+t,X);
    }
    public void setDy(DoubleMatrix dy,int t){
        StatesRecord.put("dy"+t,dy);
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

    public Optimizer getOptimizer() {
        return optimizer;
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

    public RuningState getRuningState() {
        return runingState;
    }

    public void setRuningState(RuningState runingState) {
        this.runingState = runingState;
    }

    public DoubleMatrix getY(int t){
        return StatesRecord.get("y"+t);
    }
}
