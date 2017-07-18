package com.miyzh.framework.util;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;


/**
* 文件名：AppUserAction
* 版权：Copyright 2014 Digital Bay Technology Group. Co. Ltd. All Rights Reserved. 
* 描述: 文本压缩及解压工具类
* 修改人: guchangpeng
* 修改时间：2014-02-26 19:29:12
* 修改内容：新增
*/
public class ZipHelper {

    private final static int CacheSize = 1024;

    /**
     * 压缩Zip
     * 
     * @param data
     * @return
     */
    public static byte[] zipByte(byte[] data) {
        Deflater compresser = new Deflater();
        compresser.reset();
        compresser.setInput(data);
        compresser.finish();
        byte result[] = new byte[0];
        ByteArrayOutputStream o = new ByteArrayOutputStream(1);
        try {
            byte[] buf = new byte[CacheSize];
            int got = 0;
            while (!compresser.finished()) {
                got = compresser.deflate(buf);
                o.write(buf, 0, got);
            }

            result = o.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                o.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            compresser.end();
        }
        return result;
    }

    /**
     * 压缩String
     * 
     * @param data
     * @return
     */
    public static byte[] zipString(String data) {
        byte[] input = new byte[0];
        try {
            input = data.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        byte[] result = ZipHelper.zipByte(input);
        return result;
    }

    /**
     * 解压Zip
     * 
     * @param data
     * @return
     */
    public static byte[] unZipByte(byte[] data) {
        Inflater decompresser = new Inflater();
        decompresser.setInput(data);
        byte result[] = new byte[0];
        ByteArrayOutputStream o = new ByteArrayOutputStream();
        try {
            byte[] buf = new byte[CacheSize];
            int got = 0;
            while (!decompresser.finished()) {
                got = decompresser.inflate(buf);
                o.write(buf, 0, got);
            }
            result = o.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                o.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            decompresser.end();
        }
        return result;
    }

    /**
     * 解压Zip数据为String
     * 
     * @param data
     * @return
     */
    public static String unZipByteToString(byte[] data) {
        byte[] result = unZipByte(data);
        String outputString = null;
        try {
            outputString = new String(result, 0, result.length, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return outputString;
    }
    public static String readStreamToString(InputStream is) throws Exception{  
        byte[] bytes = new byte[CacheSize];  
        int leng;  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        while((leng=is.read(bytes)) != -1){  
            baos.write(bytes, 0, leng);  
        }  
        return unZipByteToString(baos.toByteArray());  
    }  
    /**
     * gzip压缩
     * @param obj obj对象
     * @param outputStream 输入流
     */
	public static void gZip(Object obj, OutputStream outputStream) {
		GZIPOutputStream gzipout = null;
		try {
			gzipout = new GZIPOutputStream(outputStream);
			ObjectOutputStream oos = new ObjectOutputStream(gzipout);
			oos.writeObject(obj);
			gzipout.finish();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (gzipout != null) {
					gzipout.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 解压gzip
	 * @param is 输入流
	 * @return object对象
	 */
	public static Object unGzip(InputStream is) {
		Object obj = null;
		try {
			GZIPInputStream gzipin = new GZIPInputStream(is);
			ObjectInputStream ois = new ObjectInputStream(gzipin);
			obj = ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return obj;
		
	}
	public static String unZip(String str) {
		StringBuffer result = new StringBuffer();

		char oldChar = 'z';
		try {
			for (int i = 0, len = str.length(); i < len; i++) {
				if (i % 2 == 0) {
					oldChar = str.charAt(i);
				} else {
					int count = Integer.parseInt(str.substring(i, i + 1));
					for (int j = 0; j < count; j++)
						result.append(oldChar);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	/**
	 * 输入流转换为字符串
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static String inputStreamToString(InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[CacheSize];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}
	@SuppressWarnings("unused")
    public static void main(String [] args) throws UnsupportedEncodingException {
        String test = "aaaa测试";
        byte[] byte1 = ZipHelper.zipString(test);
        byte[] byte2 = new String(ZipHelper.zipString(test)).getBytes("ISO-8859-1");
        byte[] byte3 = new String(ZipHelper.zipString(test), "ISO-8859-1").getBytes("ISO-8859-1");
        
        // out.println(new String(ZipHelper.zipString(test), "ISO-8859-1"));
        String str = new String(byte1, "ISO-8859-1");
        // byte[] byteTest = {120, -100, 75, 76, 76, 76, 124, -74, -75, -5, -59, -6, -87, 0, 28, -80, 5, -41};
           byte[] byteTest = {120, 63, 75, 76, 76, 76, 124, 63, 63, 63, 63, 63, 0, 28, 63, 5, 63};
        String strUnZip = unZipByteToString(byte3);
        System.out.println(strUnZip);
        // zipHelperTest();
    }
}
