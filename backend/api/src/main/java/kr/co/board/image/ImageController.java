package kr.co.board.image;

import kr.co.board.domain.Image;
import kr.co.board.dto.ImageDto;
import kr.co.board.cmm.FileSenderUtil;
import kr.co.board.image.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 이미지 업로드 / 다운로드 등 관련 Controller
 * Created by 권 오빈 on 2016. 6. 10..
 */
@Controller
@RequestMapping("/image")
@Slf4j
public class ImageController {

	@Autowired private MultipartResolver multipartResolver;

	@Resource(name = "imageService") private ImageService imageService;

	@GetMapping("/{imageIdx:\\d+}-{imageName}")
	public void downImg(
		@PathVariable("imageIdx") long imageIdx,
		@PathVariable("imageName") String imageName,
		HttpServletRequest request, HttpServletResponse response){

		fileDownload(imageIdx, imageName, request, response);
	}

	@GetMapping("/thumb/{imageIdx:\\d+}-{imageName}")
	public void downThumbImg(
		@PathVariable("imageIdx") long imageIdx,
		@PathVariable("imageName") String imageName,
		HttpServletRequest request, HttpServletResponse response){

		fileDownload(imageIdx, imageName, request, response);
	}

	@PostMapping(value = "/upload", consumes = "multipart/form-data")
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody List<ImageDto.Response> uploadImage(HttpServletRequest request){
		log.debug("파일 업로드 : {}", multipartResolver.isMultipart(request));

		if(multipartResolver.isMultipart(request)){
			Collection<MultipartFile> fileList = ((MultipartHttpServletRequest) request).getFileMap().values();
			log.debug("파일 업로드 여부 : {}", !ObjectUtils.isEmpty(fileList));
			return imageService.uploadAsNonGroup(fileList);
		}

		return null;
	}

	private void fileDownload(long imageIdx, String imageName, HttpServletRequest request, HttpServletResponse response) {
		try {

			Map<String, Object> map = imageService.getImage(imageIdx);
			File file = (File) map.get("file");
			Image image = (Image) map.get("image");

			FileSenderUtil.fromFile(file)
				.with(request)
				.with(response)
				.with(MediaType.valueOf(image.getContentType()))
				.with(imageName)
				.serveResource();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/ckeditor-upload", method = RequestMethod.POST)
	public String uploadImage(
			@RequestParam(value = "CKEditor", required = false) String cKEditor,
			@RequestParam(value = "CKEditorFuncNum", required = false) String cKEditorFuncNum,
			@RequestParam(value = "langCode", required = false) String langCode,
			@RequestParam(value = "fileIdSetting", required = false) String fileIdSetting,
			ModelMap model,
			HttpServletRequest request
	) throws Exception {
		List<ImageDto.Response> imageList = null;
		if (multipartResolver.isMultipart(request)) {
			Collection<MultipartFile> fileList = ((MultipartHttpServletRequest) request).getFileMap().values();

			imageList = imageService.uploadAsNonGroup(fileList);
		}

		model.addAttribute("cKEditor", cKEditor);
		model.addAttribute("cKEditorFuncNum", cKEditorFuncNum);
		model.addAttribute("langCode", langCode);
		model.addAttribute("fileIdSetting", fileIdSetting);
		model.addAttribute("files", imageList);

        return "ckeditorUpload";
	}
}
