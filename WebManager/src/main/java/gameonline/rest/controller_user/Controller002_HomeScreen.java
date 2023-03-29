package gameonline.rest.controller_user;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gameonline.config.API;
import gameonline.rest.MyRespone;
import gameonline.rest.controller_user.HomeScreen.Service_HomeScreen_Download_Csharp;
import gameonline.rest.controller_user.HomeScreen.Service_HomeScreen_UpdateSetting;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/api/lobby/home")
public class Controller002_HomeScreen {
	public static String PATH = null;

	@ApiOperation("Update Setting")
	@PostMapping(value = API.HOME_Update_Setting, produces = MediaType.APPLICATION_JSON_VALUE)
	public MyRespone getUserData(@RequestHeader("token") String token, @RequestBody Service_HomeScreen_UpdateSetting service) {
		return service.process(token);
	}
	
	@GetMapping(value = "/download-csharp")
	public ResponseEntity<?> downloadCsharp(HttpServletResponse response){
		return new Service_HomeScreen_Download_Csharp().process("aaa",response);
	}
	
	
//	@GetMapping(value = "/download-csharp", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//	public @ResponseBody byte[] getFile(HttpServletResponse response) throws IOException {
//		ByteArrayOutputStream bos = new ByteArrayOutputStream();
//		ZipOutputStream zipOutputStream = new ZipOutputStream(bos);
//		
//		zipOutputStream.putNextEntry(new ZipEntry("File1.txt"));
//		zipOutputStream.write("Dương Đức Trí".getBytes());
//		zipOutputStream.putNextEntry(new ZipEntry("File2.txt"));
//		zipOutputStream.write("Diệu hằng".getBytes());
//		zipOutputStream.putNextEntry(new ZipEntry("File3.txt"));
//		zipOutputStream.write("Đức tiến".getBytes());
//		
//		zipOutputStream.closeEntry();
//		zipOutputStream.finish();
//		byte[] data = bos.toByteArray();
//		bos.close();
//		zipOutputStream.close();
//		
//		response.setHeader("Content-Disposition", "attachment; filename=" + "abc.zip");
//		return data;
//	}

	
}
