
package hyweb.jo.util;

import hyweb.jo.os.MonitorInfoBean;
import hyweb.jo.os.win.MonitorServiceImpl;

/**
 *
 * @author william
 */
public class WinInfo {
        public static void main(String args[])  
    {  
        MonitorServiceImpl MSImpl = new MonitorServiceImpl();  
        try {  
            Thread.sleep(MonitorServiceImpl.CPUTIME);  
            MonitorInfoBean bean = MSImpl.getMonitorInfoBean();  
            System.out.println("OSName: "+bean.getOsName());  
            System.out.println("CPU Ratio: "+bean.getCpuRatio());  
            System.out.println("Available Mem in JVM: "+String.valueOf(bean.getTotalMemory()/1000.0)+" MB");  
            System.out.println("Free Mem in JVM: "+bean.getFreeMemory()/1000.0+" MB");  
            System.out.println("Max Available Mem for JVM: "+bean.getMaxMemory()/1000.0+" MB");  
            System.out.println("============================");  
            System.out.println("Total Pyysical Mem Size: "+bean.getTotalMemorySize()/1000.0+" MB");  
            System.out.println("Total Physical Free Mem: "+bean.getFreePhysicalMemorySize()/1000.0+" MB");  
            System.out.println("Total Physical Used Memory: "+bean.getUsedMemory()/1000.0+" MB");  
            System.out.println("============================");  
            System.out.println("Total Thread in Java: "+bean.getTotalThread());  
        } catch (Exception e) {           
            e.printStackTrace();  
        }  
          
    }  
}
