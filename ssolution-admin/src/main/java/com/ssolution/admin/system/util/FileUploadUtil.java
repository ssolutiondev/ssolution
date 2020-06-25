package com.ssolution.admin.system.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.ssolution.admin.system.domain.upload.UploadFileInfo;

public class FileUploadUtil
{
	Logger l = LoggerFactory.getLogger(getClass());

	public UploadFileInfo uploadFormFile(MultipartFile formFile, String realPath) {
		
		InputStream stream = null;
		OutputStream bos = null;
		
		UUID uuid = UUID.randomUUID();
		String tempFileName = uuid.toString();
		
		try {
			
			stream = formFile.getInputStream();
			
			bos = new FileOutputStream(realPath + "/" + tempFileName);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			
			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			bos.close();
			stream.close();
			this.l.info("The file has been written to [{}]", tempFileName);
			
			if (stream != null) {
				try {
					stream.close();
					stream = null;
				}
				catch (Exception e)
				{
					this.l.error("{}", e);
				}
			}
			if (bos != null) {
				try
				{
					bos.close();
					bos = null;
				}
				catch (Exception e)
				{
					this.l.error("{}", e);
				}
			}
			UploadFileInfo f = new UploadFileInfo();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (stream != null) {
				try
				{
					stream.close();
					stream = null;
				}
				catch (Exception e)
				{
					this.l.error("{}", e);
				}
			}
			if (bos != null) {
				try
				{
					bos.close();
					bos = null;
				}
				catch (Exception e)
				{
					this.l.error("{}", e);
				}
			}
		}
		UploadFileInfo f = new UploadFileInfo();
		String ofn = formFile.getOriginalFilename();
		f.setFile_name(ofn);
		f.setFile_size(formFile.getSize() + "");
		f.setFile_type(formFile.getContentType());
		f.setFile_ext(ofn.indexOf(".") > -1 ? ofn.substring(ofn.lastIndexOf(".") + 1) : "");
		f.setFile_path_name(tempFileName);
		f.setReal_path(realPath);
		return f;
	}
}