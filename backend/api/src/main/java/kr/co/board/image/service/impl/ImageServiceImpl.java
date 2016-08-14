package kr.co.board.image.service.impl;

import kr.co.board.domain.Image;
import kr.co.board.domain.ImageGroup;
import kr.co.board.dto.ImageDto;
import kr.co.board.dto.ImageGroupDto;
import kr.co.board.image.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 이미지 관련 서비스 구현체
 * Created by 권 오빈 on 2016. 6. 10..
 */
@Service("imageService")
@Slf4j
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private ImageGroupRepository imageGroupRepository;

	@Value("${board.upload.path}") private String uploadPath;

	@Transactional
	@Modifying
	@Override
	public ImageGroup upload(Collection<MultipartFile> fileList, ImageGroupDto.Update imageGroupDtoUpdate) {
		ImageGroup imageGroup;
		List<Image> imageList;

		if(ObjectUtils.isEmpty(imageGroupDtoUpdate) || imageGroupDtoUpdate.getImageGroupIdx() == 0){
			imageGroup = new ImageGroup();
			imageList = new ArrayList<>();
		} else {
			imageGroup = imageGroupRepository.getOne(imageGroupDtoUpdate.getImageGroupIdx());
			imageList = imageGroup.getImageList();

			List<ImageDto.Update> imageDtoUpdate = imageGroupDtoUpdate.getImageList();

			imageList.forEach(image -> {
				if(ObjectUtils.isEmpty(imageDtoUpdate) || imageDtoUpdate.size() == 0){
					image.setEnabled(false);
				} else {
					boolean update = true;
					for(ImageDto.Update imageDto : imageDtoUpdate){
						if(image.getImageIdx() == imageDto.getImageIdx()){
							update = false;
						}
					}
					if(update) image.setEnabled(false);
				}
			});
		}

		uploadFile(fileList, imageList);

		imageList.stream().forEach(image -> image.setImageGroup(imageGroup));

		imageGroup.setImageList(imageList);

		if(log.isDebugEnabled()){
			imageGroup.getImageList().forEach(image -> {
				log.debug("이미지 : {}", image.getImageName());
			});
		}

		return imageGroupRepository.save(imageGroup);
	}

	@Override
	public ImageGroup upload(Collection<MultipartFile> fileList) {
		return upload(fileList, null);
	}

	@Transactional
	@Modifying
	@Override
	public ImageGroup updateEnabledAuto(ImageGroup imageGroup) {
		if(ObjectUtils.isEmpty(imageGroup)){
			return imageGroup;
		}

		ImageGroup preImageGroup = imageGroupRepository.getOne(imageGroup.getImageGroupIdx());
		List<Image> preImageList = preImageGroup.getImageList();

		List<Image> imageList = imageGroup.getImageList();

		if(preImageGroup.getImageList().size() == imageList.size()){
			return preImageGroup;
		}

		preImageList.forEach(image -> {
			if(ObjectUtils.isEmpty(imageList) || imageList.size() == 0){
				image.setEnabled(false);
			} else {
				boolean update = true;
				for(Image imageUpdate : imageList){
					if(image.getImageIdx() == imageUpdate.getImageIdx()){
						update = false;
					}
				}
				if(update) image.setEnabled(false);
			}
		});

		preImageGroup.setImageList(preImageList);

		return imageGroupRepository.save(preImageGroup);
	}

	@Override
	public Map<String, Object> getImage(long imageIdx) {
		Image image = imageRepository.findOne(imageIdx);

		if(ObjectUtils.isEmpty(image)){
			return null;
		}

		Map<String, Object> map = new HashMap<>();
		map.put("image", image);

		String storedImageName = image.getStoredImageName();

		String fileFullPath = uploadPath
			+ File.separator
			+ storedImageName.substring(0, 4)
			+ File.separator
			+ storedImageName.substring(5, 7)
			+ File.separator
			+ storedImageName.substring(8, 10)
			+ File.separator
			+ storedImageName;

		map.put("file", new File(fileFullPath));

		return map;
	}

	@Override
	public ImageGroupDto.Response insertBlankImageGroup() {
		ImageGroup imageGroup = new ImageGroup();
		return modelMapper.map(imageGroupRepository.save(imageGroup), ImageGroupDto.Response.class);
	}

	@Override
	public List<ImageDto.Response> uploadAsNonGroup(Collection<MultipartFile> fileList) {
		List<Image> imageList = new ArrayList<>();
		uploadFile(fileList, imageList);

		return imageRepository.save(imageList).stream().map(image -> modelMapper.map(image, ImageDto.Response.class)).collect(Collectors.toList());
	}

	private void uploadFile(Collection<MultipartFile> fileList, List<Image> imageList){
		fileList.forEach(file -> {
			DateTime now = DateTime.now(DateTimeZone.UTC);
			String storedImageName = now + UUID.randomUUID().toString();
			storedImageName = storedImageName.replace(":", "").replace(".", "");

			log.info("글자 자리수 : {}", storedImageName.length());
			log.info("신규 로그인 파일 저장 명 : {}", storedImageName);
			log.info("오리지널 파일 명 : {}", file.getOriginalFilename());
			log.info("파일 사이즈 : {}", file.getSize());

			DecimalFormat df = new DecimalFormat("00");
			String fileUploadPath =
					uploadPath
							+ File.separator
							+ now.getYear()
							+ File.separator
							+ df.format(now.getMonthOfYear())
							+ File.separator
							+ df.format(now.getDayOfMonth())
							+ File.separator;

			log.info("업로드 경로 : {}", fileUploadPath);

			File dir = new File(fileUploadPath);
			if(!dir.exists() && !dir.mkdirs()){
				log.error("폴더 생성 에러");
			}

			fileUploadPath = fileUploadPath.concat(storedImageName);

			try {
				FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(fileUploadPath));
			} catch (IOException e) {
				log.error("업로드 에러 : {}", e.getCause());
			}

			Image image = new Image();
			image.setStoredImageName(storedImageName);
			image.setContentType(file.getContentType());
			image.setImageSize(file.getSize());
			image.setImageName(file.getOriginalFilename());

			imageList.add(image);
		});
	}
}
