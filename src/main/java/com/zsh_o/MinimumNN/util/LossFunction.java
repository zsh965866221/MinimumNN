package com.zsh_o.MinimumNN.util;

import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;

/**
 * Created by zsh_o on 2017/4/19.
 *
 * get some codes from JRNN
 */
public class LossFunction {
    public interface ILoss {
        double calculate(DoubleMatrix P, DoubleMatrix Q);
    }
    public class CrossEntropy implements ILoss {
        @Override
        public double calculate(DoubleMatrix P, DoubleMatrix Q) {
            return crossEntropy(P,Q);
        }
    }
    public class MeanBinaryCrossEntropy implements ILoss{

        @Override
        public double calculate(DoubleMatrix P, DoubleMatrix Q) {
            return getMeanBinaryCrossEntropy(P,Q);
        }
    }
    public class CategoricalCrossEntropy implements ILoss{

        @Override
        public double calculate(DoubleMatrix P, DoubleMatrix Q) {
            return getCategoricalCrossEntropy(P,Q);
        }
    }
    public class MeanCategoricalCrossEntropy implements ILoss{

        @Override
        public double calculate(DoubleMatrix P, DoubleMatrix Q) {
            return getMeanCategoricalCrossEntropy(P,Q);
        }
    }
    public class MSE implements ILoss{

        @Override
        public double calculate(DoubleMatrix P, DoubleMatrix Q) {
            return getMSE(P,Q);
        }
    }
    public class MSERecSys implements ILoss{

        @Override
        public double calculate(DoubleMatrix P, DoubleMatrix Q) {
            return getMSERecSys(P,Q);
        }
    }


    static double crossEntropy(DoubleMatrix p, DoubleMatrix q) {
        for (int i = 0; i < p.length; i++) {
            if (p.get(i) == 0) {
                p.put(i, 1e-10);
            } else if (p.get(i) == 1) {
                p.put(i, 1 - 1e-10);
            }
        }
        return -q.mul(MatrixFunctions.log(p)).sum()
                + (q.add(-1).mul(MatrixFunctions.log(p.mul(-1).add(1)))).sum();
    }

    static double getMeanBinaryCrossEntropy(DoubleMatrix P, DoubleMatrix Q) {
        double e = 0;
        if (P.rows == Q.rows) {
            for (int i = 0; i < P.rows; i++) {
                e += crossEntropy(P.getRow(i), Q.getRow(i));
            }
            e /= P.rows;
        } else {
            System.exit(-1);
        }
        return e;
    }

    private static double getCategoricalCrossEntropy(DoubleMatrix p, DoubleMatrix q) {
        for (int i = 0; i < q.length; i++) {
            if (q.get(i) == 0) {
                q.put(i, 1e-10);
            }
        }
        return -p.mul(MatrixFunctions.log(q)).sum();
    }

    static double getMeanCategoricalCrossEntropy(DoubleMatrix P, DoubleMatrix Q) {
        double e = 0;
        if (P.rows == Q.rows) {
            for (int i = 0; i < P.rows; i++) {
                e += getCategoricalCrossEntropy(P.getRow(i), Q.getRow(i));
            }
            e /= P.rows;
        } else {
            System.exit(-1);
        }
        return e;
    }

    static double getMSE(DoubleMatrix P, DoubleMatrix Q) {
        double e = 0;
        if (P.rows == Q.rows) {
            for (int i = 0; i < P.rows; i++) {
                e += P.getRow(i).distance2(Q.getRow(i));
            }
            e /= P.rows;
        } else {
            System.exit(-1);
        }
        return e;
    }

    public static double getMSERecSys(DoubleMatrix P, DoubleMatrix Q) {
        double e = 0;
        double n = 0;
        if (P.rows == Q.rows) {
            for (int i = 0; i < P.rows; i++) {
                DoubleMatrix p = P.getRow(i);
                DoubleMatrix q = Q.getRow(i);
                for (int j = 0; j < p.columns; j++) {
                    if (q.get(j) > 0) {
                        e += Math.pow(p.get(j) - q.get(j), 2);
                        n++;
                    }
                }
            }
            e = e / (n == 0 ? 1 : n);
        } else {
            System.exit(-1);
        }
        return e;
    }
}
