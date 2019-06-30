package com.sc.app.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sc.api.model.Pd;
import com.sc.app.config.FileConfig;

@Controller
@RequestMapping("/file")
public class FileController extends BaseController {

	@Autowired
	FileConfig fileConfig;

	@RequestMapping("/download")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response) {
		Pd pd = new Pd();
		pd = this.getPd();
		String fileName = pd.getString("FILENAME");
		if (StringUtils.isNotEmpty(fileName)) {
			File file = new File(fileName);
			if (file.exists()) {
				response.setContentType("application/force-download");// 设置强制下载不打开
				response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
				byte[] buffer = new byte[1024];
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				try {
					fis = new FileInputStream(file);
					bis = new BufferedInputStream(fis);
					OutputStream os = response.getOutputStream();
					int i = bis.read(buffer);
					while (i != -1) {
						os.write(buffer, 0, i);
						i = bis.read(buffer);
					}
					os.flush();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				} finally {
					if (bis != null) {
						try {
							bis.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (fis != null) {
						try {
							fis.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	@RequestMapping("/image")
	public void imageFile(HttpServletRequest request, HttpServletResponse response) {
		Pd pd = new Pd();
		pd = this.getPd();
		String fileName = pd.getString("FILENAME");
		if (StringUtils.isNotEmpty(fileName)) {
			File file = new File(fileConfig.getDirImage() + File.separator + fileName);
			if (file.exists()) {
				response.reset();
				response.setContentType("image/png");
				response.setHeader("Content-Type", "application/octet-stream");
				response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".png");
				try {
					InputStream in = new FileInputStream(file);
					byte[] bytearray = new byte[1024];
					int len = 0;
					while ((len = in.read(bytearray)) != -1) {
						response.getOutputStream().write(bytearray);
					}
					response.getOutputStream().flush();
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
