package gameonline.rest.controller_user.HomeScreen;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import gameonline.rest.BaseVariable;
import gameonline.rest.BinaryToken;
import gameonline.rest.MyRespone;

public class Service_HomeScreen_Download_Csharp extends BaseVariable{
	
	public ResponseEntity<?> process(String _token, HttpServletResponse response) {
//		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		
		BinaryToken binaryToken = new BinaryToken();
		if(binaryToken.decode(_token)==false) {
			System.err.println("Chưa biết cách trả về Json hoặc download file trong cùng 1 URL");
			response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			return new ResponseEntity<MyRespone>(resTokenError, HttpStatus.OK);
		}
		if(System.currentTimeMillis()>binaryToken.timeOut) {
			System.err.println("Chưa biết cách trả về Json hoặc download file trong cùng 1 URL");
			response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			return new ResponseEntity<MyRespone>(resTokenTimeout, HttpStatus.OK);
		}
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		response.setHeader("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
		response.setHeader("Content-Disposition", "attachment; filename=" + "abc.zip");
		
		byte[] data=null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ZipOutputStream zipOutputStream = new ZipOutputStream(bos);
		
		try {
			zipOutputStream.putNextEntry(new ZipEntry("File1.txt"));
			zipOutputStream.write("Dương Đức Trí".getBytes());
			zipOutputStream.putNextEntry(new ZipEntry("File2.txt"));
			zipOutputStream.write("Diệu hằng".getBytes());
			zipOutputStream.putNextEntry(new ZipEntry("File3.txt"));
			zipOutputStream.write("Đức tiến".getBytes());
			
			zipOutputStream.closeEntry();
			zipOutputStream.finish();
			data = bos.toByteArray();
			bos.close();
			zipOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<byte[]>(data, HttpStatus.OK);
	}

}
