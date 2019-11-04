package android_serialport_api;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

public class ComBase extends Thread {
    protected boolean worked = false;
    protected SerialPort mSerialPort = null;
    protected InputStream mmIn = null;
    protected OutputStream mmOut = null;

    public boolean isWork() {
        return worked;
    }
    public boolean stopWork() {
        if (worked) {
            worked = false;
            try {
                this.join(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                mSerialPort.close();
            }
        }
        return true;
    }
    // //最后一个参数  0---阻塞模式     1 --非阻塞模式
    public boolean startWork(String comFile,int btl,int mode) {
        if (!worked) {
            if (!openSerialPort(comFile,btl,mode)) {
                return false;
            }
            worked = true;

            this.start();
        }
        return true;
    }
    private boolean openSerialPort(String comFile,int btl,int mode) {
        try {
            File file = new File(comFile);
            //最后一个参数  0---阻塞模式     1 --非阻塞模式
            mSerialPort = new SerialPort(file, btl, mode);
            if (mSerialPort != null) {
                mmIn = mSerialPort.getInputStream();
                mmOut = mSerialPort.getOutputStream();
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public String ArraytoHexString(byte[] linArray, int iLen) {
        return ArraytoHexString(linArray,0,iLen);
    }


    public String ArraytoHexString(byte[] linArray) {
        return ArraytoHexString(linArray,0,linArray.length);
    }


    public String ArraytoHexString(byte[] linArray, int iStart, int iLen) {
        StringBuffer stringBuffer=new StringBuffer();
        for(int i=0;i<iLen;i++)
        {
            stringBuffer.append(String.format("%02X ",0xff&linArray[i+iStart]));
        }
        return stringBuffer.toString();
    }
}
