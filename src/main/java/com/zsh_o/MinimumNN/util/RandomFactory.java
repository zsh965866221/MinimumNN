package com.zsh_o.MinimumNN.util;

import java.util.Random;

/**
 * Created by zsh_o on 2017/4/19.
 */
public class RandomFactory {
    static Random random;
    public static Random get(){
        if(random==null){
            random=new Random();
        }
        return random;
    }
}
