package com.zsh_o.MinimumNN.layer;

import com.zsh_o.MinimumNN.node.Node;
import com.zsh_o.MinimumNN.node.RuningState;
import com.zsh_o.MinimumNN.optimizator.Optimizator;
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

    public DenseLayer(int inSize, int outSize, Optimizator optimizator, MatrixIniter matrixIniter,ActivateFunction activateFunction) {
        super(inSize, outSize, optimizator, matrixIniter);
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
            outNode.getParametersRecord().put("x"+CurrentTime,y);
        }

        CurrentTime++;
    }

    public void train() {
        for(int t=CurrentTime-1;t>-1;t--){
            DoubleMatrix dy=StatesRecord.get("dy"+t);



        }
    }

    public void updateParameters() {

    }
}
