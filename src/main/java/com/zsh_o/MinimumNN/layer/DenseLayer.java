package com.zsh_o.MinimumNN.layer;

import com.zsh_o.MinimumNN.node.Node;
import com.zsh_o.MinimumNN.optimizator.Optimizer;
import com.zsh_o.MinimumNN.util.active.ActivateFunction;
import com.zsh_o.MinimumNN.util.MatrixIniter;
import org.jblas.DoubleMatrix;

import static com.zsh_o.MinimumNN.node.RuningState.Training;

/**
 * Created by zsh_o on 2017/4/19.
 */
public class DenseLayer extends Node {

    DoubleMatrix W,b;

    ActivateFunction activateFunction;

    public DenseLayer(int inSize, int outSize, Optimizer optimizer, MatrixIniter matrixIniter, ActivateFunction activateFunction) {
        super(inSize, outSize, optimizer, matrixIniter);
        this.W=matrixIniter.generate(inSize,outSize);
        this.b=new DoubleMatrix(1,outSize);
        ParametersRecord.put("W",W);
        ParametersRecord.put("b",b);
        ParametersTrainable.add("W");
        ParametersTrainable.add("b");
        this.activateFunction=activateFunction;
    }

    public void activate() {
        DoubleMatrix x=StatesRecord.get("x"+CurrentTime);
        if(runingState==Training){
            x=DroupOut(x);
        }
        DoubleMatrix y=activateFunction.activate(x.mul(W).add(b));
        StatesRecord.put("y"+CurrentTime,y);

        if(outNode!=null){
            outNode.getStatesRecord().put("x"+CurrentTime,y);
        }

        if(runingState==Training){
            CurrentTime++;
        }
    }

    public void train() {
        for(int t=CurrentTime-1;t>-1;t--){
            DoubleMatrix dy=StatesRecord.get("dy"+t);
            DoubleMatrix dx=activateFunction.dActivate(dy).mul(W.transpose());
            StatesRecord.put("dx"+t,dx);

            if(inNode!=null){
                inNode.getStatesRecord().put("dy"+t,dx);
            }
        }

        updateParameters();
    }

    public void updateParameters() {
        iterNum++;
        DoubleMatrix gW=new DoubleMatrix(W.rows,W.columns);
        DoubleMatrix gb=new DoubleMatrix(b.rows,b.columns);

        for(int t=0;t<CurrentTime;t++){
            DoubleMatrix dy=StatesRecord.get("dy"+t);
            DoubleMatrix x=StatesRecord.get("x"+t).transpose();
            DoubleMatrix dfy=activateFunction.dActivate(dy);
            gW= gW.add(x.mul(dfy));
            gb=gb.add(dfy);
        }
        gW=gW.div(CurrentTime);
        gb=gb.div(CurrentTime);
        ParametersRecord.put("dw",gW);
        ParametersRecord.put("db",gb);

        optimizer.update();
    }
}
