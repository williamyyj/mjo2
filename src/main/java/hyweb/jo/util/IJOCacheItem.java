package hyweb.jo.util;

/**
 * @author william
 *  @param <E> 
 */
public interface IJOCacheItem<E> {
    long lastModified();
    public E load() throws Exception ; 
    public void unload();
}
