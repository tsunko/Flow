package academy.hekiyou.flow;

import academy.hekiyou.flow.interp.Interpreter;
import academy.hekiyou.flow.interp.Interpreters;

import java.util.ArrayList;
import java.util.List;

/**
 * An extremely simple String-array based Flow implementation
 */
public class StringFlow implements Flow {

    private String[] underlying;
    private int pos;

    public StringFlow(String[] underlying){
        this.underlying = underlying;
    }

    @Override
    public <T> T next(Class<T> type, T def){
        if(!hasRemaining()) throw new FlowEmptyException();
        Interpreter<T> interp = Interpreters.of(type);
        try {
            return interp.apply(underlying[pos++]);
        } catch (FlowException ex){
            if(def == null){
                throw ex;
            } else {
                return def;
            }
        }
    }

    @Override
    public <T> List<T> remaining(Class<T> type){
        Interpreter<T> interp = Interpreters.of(type);
        List<T> ret = new ArrayList<>();
        while(hasRemaining()){
            ret.add(interp.apply(underlying[pos])); // apply implicitly throws FlowException
            pos++;
        }
        return ret;
    }

    @Override
    public int index(){
        return pos;
    }

    @Override
    public boolean hasRemaining(){
        return pos < underlying.length;
    }

}
