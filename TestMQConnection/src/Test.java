import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Test {

	
	public static void tt(String[] args) throws IOException
	{
//		String fileName = "C:/Users/Kbankuser/Desktop/Fraud_Transac/vdolog/vdolog2016-09-21_121435000696.msg";
//		byte[] out = readBinaryFromFile(fileName);
//		String str = new String(out,"ibm838");
//		System.out.println(out);
//		System.out.println(str);
//		System.out.println(str.length());
	}
	
	public static byte[] readBinaryFromFile(String filename)
		    throws IOException
	  {
	    File file = new File(filename);
	    byte[] bytes = new byte[(int)file.length()];
	    DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(filename)));
	    dataInputStream.readFully(bytes);
	    dataInputStream.close();
	    return bytes;
	  }
	  
	  public static void writeTextToFile(String text, String filename)
	  {
	    try
	    {
	      File file = new File(filename);
	      if (!file.exists()) {
	        file.createNewFile();
	      }
	      FileWriter fw = new FileWriter(file.getAbsoluteFile());
	      BufferedWriter bw = new BufferedWriter(fw);
	      bw.write(text);
	      bw.close();
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	  }
	  
	  public static void writeByteToFile(byte[] data, String filename)
	  {
	    try
	    {
	      FileOutputStream fos = new FileOutputStream(filename);
	      fos.write(data);
	      fos.close();
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	  }
}
