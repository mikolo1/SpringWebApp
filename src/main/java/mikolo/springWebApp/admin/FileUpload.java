package mikolo.springWebApp.admin;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileUpload {
	
	private MultipartFile filename;

}
