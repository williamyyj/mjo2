package hyweb.jo;

/**
 * @author William
 * @param <RET> 回傳值
 * @param <P> 參數
 */

public interface IJOFunction<RET,P> {
    public RET exec(P p) throws Exception ; 
}
