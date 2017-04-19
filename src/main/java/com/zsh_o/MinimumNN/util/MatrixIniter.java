package com.zsh_o.MinimumNN.util;

import org.jblas.DoubleMatrix;

import java.util.Random;

/**
 * Created by zsh_o on 2017/4/19.
 */
public class MatrixIniter {
    public enum Type{
        Uniform,
        Gaussian
    }

    Type type;
    double scale=0.01;
    double miu=0;
    double sigma=0.01;

    public MatrixIniter(Type type, double scale, double miu, double sigma) {
        this.type = type;
        this.scale = scale;
        this.miu = miu;
        this.sigma = sigma;
    }

    public DoubleMatrix generate(int rows,int cols){
        if(type==Type.Uniform){
            return uniform(rows,cols);
        }else if(type==Type.Gaussian){
            return gaussian(rows,cols);
        }else
            return null;
    }

    DoubleMatrix uniform(int rows,int cols){
        return DoubleMatrix.rand(rows, cols).mul(2 * scale).sub(scale);
    }

    DoubleMatrix gaussian(int rows, int cols) {
        DoubleMatrix m = new DoubleMatrix(rows, cols);
        for (int i = 0; i < m.length; i++) {
            m.put(i, RandomFactory.get().nextGaussian() * sigma + miu);
        }
        return m;
    }

    public Type getType() {
        return type;
    }

    public double getScale() {
        return scale;
    }

    public double getMiu() {
        return miu;
    }

    public double getSigma() {
        return sigma;
    }
}
