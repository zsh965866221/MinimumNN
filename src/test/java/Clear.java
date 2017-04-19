import java.util.HashMap;

/**
 * Created by zsh_o on 2017/4/19.
 */
public class Clear {
    public static void main(String[] args){
        HashMap<String,Double> hm=new HashMap<String, Double>();
        Double td=10.0;
        hm.put("1",td);
        System.out.println(hm.get("1"));

        hm.clear();

        System.out.println(td);
    }
}
