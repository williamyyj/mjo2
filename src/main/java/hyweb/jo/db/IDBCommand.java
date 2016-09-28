package hyweb.jo.db;

/**
 *
 * @author william
 * @param <M>
 */
public interface IDBCommand<M> {
    public M execute(IDB db, String cmd , Object ... params) ;
}
