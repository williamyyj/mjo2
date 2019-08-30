package hyweb.jo.ht;

import hyweb.jo.IJOInit;
import hyweb.jo.JOProcObject;
/**
 * @author william $id , $type , $label , $params id: , ref: , params : { }
 * ..... [ "id:xxxx , ref: ,
 *  @deprecated use {@link IHT} instead. 
 */
 @Deprecated
public interface IHTCell extends IJOInit<Object> {

    public String getId();

    public String getType();

    public String getScriptable();

    public String getCode();

    public String getLabel();

    public String render(JOProcObject proc);

    public String display(Object p);
    
    public String display(Object p, Object dv);

    public String getHtml();
    
    
}
