package com.zsh_o.MinimumNN.layer;

import com.zsh_o.MinimumNN.node.Node;
import com.zsh_o.MinimumNN.optimizator.Optimizer;
import com.zsh_o.MinimumNN.util.MatrixIniter;
import com.zsh_o.MinimumNN.util.active.Logistic;
import com.zsh_o.MinimumNN.util.active.Tanh;
import org.jblas.DoubleMatrix;

import static com.zsh_o.MinimumNN.node.RuningState.Training;

/**
 * Created by zsh_o on 2017/4/20.
 *
 * get some codes from JRNN
 */
public class LSTMLayer extends Node {
    Logistic logistic=new Logistic();
    Tanh tanh=new Tanh();

    DoubleMatrix Wxi,Whi,Wci,bi;
    DoubleMatrix Wxf,Whf,Wcf,bf;
    DoubleMatrix Wxc,Whc,bc;
    DoubleMatrix Wxo,Who,Wco,bo;


    public LSTMLayer(int inSize, int outSize, Optimizer optimizer, MatrixIniter matrixIniter,double droupRate) {
        super(inSize, outSize, optimizer, matrixIniter,droupRate);
        this.Wxi=matrixIniter.generate(inSize,outSize);
        this.Whi=matrixIniter.generate(outSize,outSize);
        this.Wci=matrixIniter.generate(outSize,outSize);
        this.bi=new DoubleMatrix(1,outSize);

        this.Wxf=matrixIniter.generate(inSize,outSize);
        this.Whf=matrixIniter.generate(outSize,outSize);
        this.Wcf=matrixIniter.generate(outSize,outSize);
        this.bf=new DoubleMatrix(1,outSize);

        this.Wxc=matrixIniter.generate(inSize,outSize);
        this.Whc=matrixIniter.generate(outSize,outSize);
        this.bc=new DoubleMatrix(1,outSize);

        this.Wxo=matrixIniter.generate(inSize,outSize);
        this.Who=matrixIniter.generate(outSize,outSize);
        this.Wco=matrixIniter.generate(outSize,outSize);
        this.bo=new DoubleMatrix(1,outSize);

        ParametersRecord.put("Wxi",Wxi);
        ParametersTrainable.add("Wxi");
        ParametersRecord.put("Whi",Whi);
        ParametersTrainable.add("Whi");
        ParametersRecord.put("Wci",Wci);
        ParametersTrainable.add("Wci");
        ParametersRecord.put("bi",bi);
        ParametersTrainable.add("bi");

        ParametersRecord.put("Wxf",Wxf);
        ParametersTrainable.add("Wxf");
        ParametersRecord.put("Whf",Whf);
        ParametersTrainable.add("Whf");
        ParametersRecord.put("Wcf",Wcf);
        ParametersTrainable.add("Wcf");
        ParametersRecord.put("bf",bf);
        ParametersTrainable.add("bf");

        ParametersRecord.put("Wxc",Wxc);
        ParametersTrainable.add("Wxc");
        ParametersRecord.put("Whc",Whc);
        ParametersTrainable.add("Whc");
        ParametersRecord.put("bc",bc);
        ParametersTrainable.add("bc");

        ParametersRecord.put("Wxo",Wxo);
        ParametersTrainable.add("Wxo");
        ParametersRecord.put("Who",Who);
        ParametersTrainable.add("Who");
        ParametersRecord.put("Wco",Wco);
        ParametersTrainable.add("Wco");
        ParametersRecord.put("bo",bo);
        ParametersTrainable.add("bo");
    }

    @Override
    public void activate() {
        DoubleMatrix x=StatesRecord.get("x"+CurrentTime);
        if(runingState==Training){
            x=DroupOut(x);
        }

        DoubleMatrix preH=null,preC=null;
        if(CurrentTime==0){
            preH=new DoubleMatrix(1,outSize);
            preC= preH.dup();
        }else{
            preH=StatesRecord.get("h"+(CurrentTime-1));
            preC=StatesRecord.get("c"+(CurrentTime-1));
        }

        DoubleMatrix i = logistic.activate(x.mmul(Wxi).add(preH.mmul(Whi)).add(preC.mmul(Wci)).add(bi));
        DoubleMatrix f = logistic.activate(x.mmul(Wxf).add(preH.mmul(Whf)).add(preC.mmul(Wcf)).add(bf));
        DoubleMatrix gc = tanh.activate(x.mmul(Wxc).add(preH.mmul(Whc)).add(bc));
        DoubleMatrix c = f.mul(preC).add(i.mul(gc));
        DoubleMatrix o = logistic.activate(x.mmul(Wxo).add(preH.mmul(Who)).add(c.mmul(Wco)).add(bo));
        DoubleMatrix gh = tanh.activate(c);
        DoubleMatrix h = o.mul(gh);

        StatesRecord.put("i"+CurrentTime,i);
        StatesRecord.put("f"+CurrentTime,f);
        StatesRecord.put("gc"+CurrentTime,gc);
        StatesRecord.put("c"+CurrentTime,c);
        StatesRecord.put("o"+CurrentTime,o);
        StatesRecord.put("gh"+CurrentTime,gh);
        StatesRecord.put("h"+CurrentTime,h);
        StatesRecord.put("y"+CurrentTime,h);

        if(outNode!=null){
            outNode.getStatesRecord().put("x"+CurrentTime,h);
        }

        if(runingState==Training){
            CurrentTime++;
        }
    }

    @Override
    public void train() {
        for(int t=CurrentTime-1;t>-1;t--){
            DoubleMatrix dy=StatesRecord.get("dy"+t);

            DoubleMatrix h=StatesRecord.get("h"+t);
            DoubleMatrix dh=dy;
            if(t!=CurrentTime-1){
                DoubleMatrix lateDgc=StatesRecord.get("dgc"+(t+1));
                DoubleMatrix lateDf = StatesRecord.get("df" + (t + 1));
                DoubleMatrix lateDo = StatesRecord.get("do" + (t + 1));
                DoubleMatrix lateDi = StatesRecord.get("di" + (t + 1));
                dh = dh .add(Whc.mmul(lateDgc.transpose()).transpose())
                        .add(Whi.mmul(lateDi.transpose()).transpose())
                        .add(Who.mmul(lateDo.transpose()).transpose())
                        .add(Whf.mmul(lateDf.transpose()).transpose());
            }

            StatesRecord.put("dh"+t,dh);

            DoubleMatrix gh = StatesRecord.get("gh" + t);
            DoubleMatrix o = StatesRecord.get("o" + t);
            DoubleMatrix deltaO = dh.mul(gh).mul(logistic.dActivate(o));
            StatesRecord.put("do" + t, deltaO);

            DoubleMatrix deltaC = null;
            if (t == CurrentTime-1) {
                deltaC = dh.mul(o).mul(tanh.dActivate(gh))
                        .add(Wco.mmul(deltaO.transpose()).transpose());
            } else {
                DoubleMatrix lateDc = StatesRecord.get("dc" + (t + 1));
                DoubleMatrix lateDf = StatesRecord.get("df" + (t + 1));
                DoubleMatrix lateF = StatesRecord.get("f" + (t + 1));
                DoubleMatrix lateDi = StatesRecord.get("di" + (t + 1));
                deltaC = dh.mul(o).mul(tanh.dActivate(gh))
                        .add(Wco.mmul(deltaO.transpose()).transpose())
                        .add(lateF.mul(lateDc))
                        .add(Wcf.mmul(lateDf.transpose()).transpose())
                        .add(Wci.mmul(lateDi.transpose()).transpose());
            }
            StatesRecord.put("dc" + t, deltaC);

            DoubleMatrix gc = StatesRecord.get("gc" + t);
            DoubleMatrix i = StatesRecord.get("i" + t);
            DoubleMatrix deltaGc = deltaC.mul(i).mul(tanh.dActivate(gc));
            StatesRecord.put("dgc" + t, deltaGc);

            DoubleMatrix preC = null;
            if (t > 0) {
                preC = StatesRecord.get("c" + (t - 1));
            } else {
                preC = DoubleMatrix.zeros(1, h.length);
            }

            DoubleMatrix f = StatesRecord.get("f" + t);
            DoubleMatrix deltaF = deltaC.mul(preC).mul(logistic.dActivate(f));
            StatesRecord.put("df" + t, deltaF);

            DoubleMatrix deltaI = deltaC.mul(gc).mul(logistic.dActivate(i));
            StatesRecord.put("di" + t, deltaI);

            DoubleMatrix dx=deltaF.add(deltaI).add(deltaGc).add(deltaO);

            StatesRecord.put("dx"+t,dx);
            if(inNode!=null){
                inNode.getStatesRecord().put("dy"+t,dx);
            }
        }

        updateParameters();
    }

    @Override
    public void updateParameters() {
        iterNum++;

        DoubleMatrix gWxi = new DoubleMatrix(Wxi.rows, Wxi.columns);
        DoubleMatrix gWhi = new DoubleMatrix(Whi.rows, Whi.columns);
        DoubleMatrix gWci = new DoubleMatrix(Wci.rows, Wci.columns);
        DoubleMatrix gbi = new DoubleMatrix(bi.rows, bi.columns);

        DoubleMatrix gWxf = new DoubleMatrix(Wxf.rows, Wxf.columns);
        DoubleMatrix gWhf = new DoubleMatrix(Whf.rows, Whf.columns);
        DoubleMatrix gWcf = new DoubleMatrix(Wcf.rows, Wcf.columns);
        DoubleMatrix gbf = new DoubleMatrix(bf.rows, bf.columns);

        DoubleMatrix gWxc = new DoubleMatrix(Wxc.rows, Wxc.columns);
        DoubleMatrix gWhc = new DoubleMatrix(Whc.rows, Whc.columns);
        DoubleMatrix gbc = new DoubleMatrix(bc.rows, bc.columns);

        DoubleMatrix gWxo = new DoubleMatrix(Wxo.rows, Wxo.columns);
        DoubleMatrix gWho = new DoubleMatrix(Who.rows, Who.columns);
        DoubleMatrix gWco = new DoubleMatrix(Wco.rows, Wco.columns);
        DoubleMatrix gbo = new DoubleMatrix(bo.rows, bo.columns);

        for(int t=0;t<CurrentTime;t++){
            DoubleMatrix x = StatesRecord.get("x" + t).transpose();
            gWxi = gWxi.add(x.mmul(StatesRecord.get("di" + t)));
            gWxf = gWxf.add(x.mmul(StatesRecord.get("df" + t)));
            gWxc = gWxc.add(x.mmul(StatesRecord.get("dgc" + t)));
            gWxo = gWxo.add(x.mmul(StatesRecord.get("do" + t)));

            if (t > 0) {
                DoubleMatrix preH = StatesRecord.get("h" + (t - 1)).transpose();
                DoubleMatrix preC = StatesRecord.get("c" + (t - 1)).transpose();
                gWhi = gWhi.add(preH.mmul(StatesRecord.get("di" + t)));
                gWhf = gWhf.add(preH.mmul(StatesRecord.get("df" + t)));
                gWhc = gWhc.add(preH.mmul(StatesRecord.get("dgc" + t)));
                gWho = gWho.add(preH.mmul(StatesRecord.get("do" + t)));
                gWci = gWci.add(preC.mmul(StatesRecord.get("di" + t)));
                gWcf = gWcf.add(preC.mmul(StatesRecord.get("df" + t)));
            }
            gWco = gWco.add(StatesRecord.get("c" + t).transpose().mmul(StatesRecord.get("do" + t)));

            gbi = gbi.add(StatesRecord.get("di" + t));
            gbf = gbf.add(StatesRecord.get("df" + t));
            gbc = gbc.add(StatesRecord.get("dgc" + t));
            gbo = gbo.add(StatesRecord.get("do" + t));
        }

        gWxi=gWxi.div(CurrentTime);
        gWhi=gWhi.div(CurrentTime);
        gWci=gWci.div(CurrentTime);
        gbi=gbi.div(CurrentTime);
        ParametersRecord.put("dWxi",gWxi);
        ParametersRecord.put("dWhi",gWhi);
        ParametersRecord.put("dWci",gWci);
        ParametersRecord.put("dbi",gbi);

        gWxf=gWxf.div(CurrentTime);
        gWhf=gWhf.div(CurrentTime);
        gWcf=gWcf.div(CurrentTime);
        gbf=gbf.div(CurrentTime);
        ParametersRecord.put("dWxf",gWxf);
        ParametersRecord.put("dWhf",gWhf);
        ParametersRecord.put("dWcf",gWcf);
        ParametersRecord.put("dbf",gbf);

        gWxo=gWxo.div(CurrentTime);
        gWho=gWho.div(CurrentTime);
        gWco=gWco.div(CurrentTime);
        gbo=gbo.div(CurrentTime);
        ParametersRecord.put("dWxo",gWxo);
        ParametersRecord.put("dWho",gWho);
        ParametersRecord.put("dWco",gWco);
        ParametersRecord.put("dbo",gbo);

        gWxc=gWxc.div(CurrentTime);
        gWhc=gWhc.div(CurrentTime);
        gbc=gbc.div(CurrentTime);
        ParametersRecord.put("dWxc",gWxc);
        ParametersRecord.put("dWhc",gWhc);
        ParametersRecord.put("dbc",gbc);

        optimizer.update();
    }
}
