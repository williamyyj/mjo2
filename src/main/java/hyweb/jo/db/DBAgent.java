package hyweb.jo.db;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

/**
 *
 * @author william
 */
public class DBAgent {

    public static void main(String[] args) throws MalformedObjectNameException,
      NotCompliantMBeanException, InstanceAlreadyExistsException,
      MBeanRegistrationException, IOException {
        // 下面这种方式不能再JConsole中使用
//      MBeanServer server = MBeanServerFactory.createMBeanServer();
// 首先建立一个MBeanServer,MBeanServer用来管理我们的MBean,通常是通过MBeanServer来获取我们MBean的信息，间接
// 调用MBean的方法，然后生产我们的资源的一个对象。
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        String domainName = "hyweb.jo.db";

//为MBean（下面的new Hello()）创建ObjectName实例
        ObjectName helloName = new ObjectName(domainName + ":name=DB");
// 将new Hello()这个对象注册到MBeanServer上去
        mbs.registerMBean(new DB(""), helloName);

// Distributed Layer, 提供了一个HtmlAdaptor。支持Http访问协议，并且有一个不错的HTML界面，这里的Hello就是用这个作为远端管理的界面
// 事实上HtmlAdaptor是一个简单的HttpServer，它将Http请求转换为JMX Agent的请求
        /**
         * ObjectName adapterName = new
         * ObjectName(domainName+":name=htmladapter,port=8082");
         * HtmlAdaptorServer adapter = new HtmlAdaptorServer(); adapter.start();
         * mbs.registerMBean(adapter,adapterName);
         *
         * int rmiPort = 1099; Registry registry =
         * LocateRegistry.createRegistry(rmiPort);
         *
         * JMXServiceURL url = new
         * JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:"+rmiPort+"/"+domainName);
         * JMXConnectorServer jmxConnector =
         * JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);
         * jmxConnector.start(); }
    *
         */
    }
}
