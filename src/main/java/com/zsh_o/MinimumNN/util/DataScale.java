package com.zsh_o.MinimumNN.util;

import org.jblas.DoubleMatrix;

/**
 * Created by zsh_o on 2017/4/19.
 */
public class DataScale implements Iscale {
    double up,down;

    public DataScale(double up, double down) {
        this.up = up;
        this.down = down;
    }

    DoubleMatrix colMax,colMin;

    public DoubleMatrix scale(DoubleMatrix a) {
        colMax=a.columnMaxs();
        colMin=a.columnMins();

        double min,max;

        DoubleMatrix b=DoubleMatrix.zeros(a.rows,a.columns);
        for(int i=0;i<b.columns;i++){
            min=colMin.get(i);
            max=colMax.get(i);
            b.putColumn(i,a.getColumn(i).sub(min).div(max-min).mul(up-down).add(down));
        }

        return b;
    }

    public DoubleMatrix deScale(DoubleMatrix b) {
        DoubleMatrix a=DoubleMatrix.zeros(b.rows,b.columns);

        double min,max;
        for(int i=0;i<a.columns;i++){
            min=colMin.get(i);
            max=colMax.get(i);
            a.putColumn(i,b.getColumn(i).sub(down).div(up-down).mul(max-min).add(min));
        }

        return a;
    }
}
