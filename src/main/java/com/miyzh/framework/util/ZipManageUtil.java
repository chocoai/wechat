package com.miyzh.framework.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;

public class ZipManageUtil {

	public static void FileToZip(File[] file1, String strZipName)
			throws IOException {

		byte[] buffer = new byte[1024];

		// 生成的ZIP文件名为Demo.zip

		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
				strZipName));

		// 需要同时下载的两个文件result.txt ，source.txt

		for (int i = 0; i < file1.length; i++) {

			FileInputStream fis = new FileInputStream(file1[i]);

			out.putNextEntry(new ZipEntry(file1[i].getName()));

			int len;

			// 读入需要下载的文件的内容，打包到zip文件

			while ((len = fis.read(buffer)) > 0) {

				out.write(buffer, 0, len);

			}

			out.closeEntry();

			fis.close();

		}

		out.close();

		System.out.println("生成Demo.zip成功");

	}

	/**
	 * 测试解压缩功能. 将d:\\download\\test.zip连同子目录解压到d:\\temp\\zipout目录下.
	 *
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static int releaseZipToFile(HttpServletRequest request,
			String outFileName) throws IOException {
		String name = request.getParameter("uuid");
		// 客户端上传图片到指定目录
		StringBuffer sourcePath = new StringBuffer();
		sourcePath.append(PropertiesUtil.getPropertyValue("image_path"));// D\:/updateImage/client/
		sourcePath.append(name + ".zip");
		String sorceZip = sourcePath.toString();
		// sourcePath D\:/updateImage/client/用户id.jpg

		File imageFile = new File(sourcePath.toString());
		if (!imageFile.getParentFile().exists()) {
			imageFile.getParentFile().mkdirs();
		}

		InputStream in = request.getInputStream();
		OutputStream out = new FileOutputStream(imageFile);
		byte[] bufff = IOUtils.toByteArray(in);

		out.write(bufff);
		out.close();
		in.close();

		ZipFile zfile = new ZipFile(sorceZip);
		System.out.println(zfile.getName());
		Enumeration zList = zfile.entries();
		ZipEntry ze = null;
		int count = 0;
		byte[] buf = new byte[1024];
		while (zList.hasMoreElements()) {
			// 从ZipFile中得到一个ZipEntry
			ze = (ZipEntry) zList.nextElement();
			if (ze.isDirectory()) {
				continue;
			}
			// 以ZipEntry为参数得到一个InputStream，并写到OutputStream中
			OutputStream os = new BufferedOutputStream(new FileOutputStream(
					getRealFileName(outFileName, ze.getName())));
			InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
			int readLen = 0;
			while ((readLen = is.read(buf, 0, 1024)) != -1) {
				os.write(buf, 0, readLen);
			}
			is.close();
			os.close();
			System.out.println("Extracted: " + ze.getName());
			count++;
		}
		zfile.close();

		return count;
	}

	public static File getRealFileName(String baseDir, String absFileName) {
		String[] dirs = absFileName.split("/");
		// System.out.println(dirs.length);
		File ret = new File(baseDir);
		// System.out.println(ret);
		if (dirs.length > 1) {
			for (int i = 0; i < dirs.length - 1; i++) {
				ret = new File(ret, dirs[i]);
			}
		}
		if (!ret.exists()) {
			ret.mkdirs();
		}
		ret = new File(ret, dirs[dirs.length - 1]);
		return ret;
	}
}
