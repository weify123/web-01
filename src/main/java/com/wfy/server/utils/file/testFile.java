package com.wfy.server.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 当我们读写文本文件的时候，采用Reader是非常方便的，比如FileReader，InputStreamReader和BufferedReader。其中最重要的类是InputStreamReader， 
 * 它是字节转换为字符的桥梁。你可以在构造器重指定编码的方式，如果不指定的话将采用底层操作系统的默认编码方式，例如GBK等。使用FileReader读取文件
 * FileReader fr = new FileReader("ming.txt");    
	  
	int ch = 0;    
	  
	while((ch = fr.read())!=-1 )   
	  
	  {     
	  
	  	System.out.print((char)ch);     
	  }
其中read()方法返回的是读取得下个字符。当然你也可以使用read(char[] ch,int off,int length)这和处理二进制文件的时候类似。
事实上在FileReader中的方法都是从InputStreamReader中继承过来的。read()方法是比较好费时间的，如果为了提高效率我们可以使用BufferedReader对Reader进行包装，
这样可以提高读取得速度，我们可以一行一行的读取文本，使用readLine()方法。

BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("ming.txt")));
String data = null;
while((data = br.readLine())!=null)
{
System.out.println(data); 
}

了解了FileReader操作使用FileWriter写文件就简单了，这里不赘述。

 * @author wfy  2017年3月2日 上午10:38:15
 * @since JDK 1.8	
 */
public class testFile {

	/** 
     * @param args 
     */  
    public static void main(String[] args) {  
        // TODO Auto-generated method stub  
        // file(内存)----输入流---->【程序】----输出流---->file(内存)  
        File file = new File("d:/temp", "addfile.txt");  
        try {  
            file.createNewFile(); // 创建文件  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
  
        // 向文件写入内容(输出流)  
        String str = "亲爱的小南瓜！";  
        byte bt[] = new byte[1024];  
        bt = str.getBytes();  
        try {  
            FileOutputStream in = new FileOutputStream(file);  
            try {  
                in.write(bt, 0, bt.length);  
                in.close();  
                // boolean success=true;  
                // System.out.println("写入文件成功");  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        try {  
            // 读取文件内容 (输入流)  
            FileInputStream out = new FileInputStream(file);  
            InputStreamReader isr = new InputStreamReader(out);  
            int ch = 0;  
            while ((ch = isr.read()) != -1) {  
                System.out.print((char) ch);  
            }  
        } catch (Exception e) {  
            // TODO: handle exception  
        }  
    }  
}
