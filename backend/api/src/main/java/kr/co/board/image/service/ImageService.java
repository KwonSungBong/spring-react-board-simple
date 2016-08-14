package kr.co.board.image.service;

import kr.co.board.domain.ImageGroup;
import kr.co.board.dto.ImageDto;
import kr.co.board.dto.ImageGroupDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 이미지 관련 서비스
 * Created by 권 오빈 on 2016. 6. 10..
 */
public interface ImageService {

	/**
	 * 이미지 업로드 서비스
	 * 서비스에서만 사용해야 함.
	 *
	 * @param fileList 컨트롤러에서 보내주는 MultiPartFile
	 * @param imageGroupDto 기존에 업로드 되어있으면, Null체크 해서 기존에 추가로 업로드, 없는 경우 새로 만들어서 리턴
	 * @return 업로드 처리 후 ImageGroup Entity 를 넘겨 줌
	 */
	ImageGroup upload(Collection<MultipartFile> fileList, ImageGroupDto.Update imageGroupDto);

	/**
	 * 이미지 업로드 서비스
	 * 오버라이딩 용
	 * @param fileList 컨트롤러에서 보내주는 MultiPartFile
	 * @return 업로드 처리 후 ImageGroup Entity 를 넘겨 줌
	 */
	ImageGroup upload(Collection<MultipartFile> fileList);

	/**
	 * 자동으로 목록에 없는 image 를 enabled -> false 처리
	 * @param imageGroup 이미지 그룹 Entity
	 * @return enabled false 처리 완료 된 ImageGroup
	 */
	ImageGroup updateEnabledAuto(ImageGroup imageGroup);

	/**
	 * 이미지 PK 에 맞는 파일을 읽어서 가지고 온다.
	 * @param imageIdx imageIdx
	 * @return file : 해당 파일, image : Image 도메인 ( 이미지 디비 정보 )
	 */
	Map<String, Object> getImage(long imageIdx);

	/**
	 * 이미지 업로드 전 ImageGroup 가 없을 경우 호출 해서, imageGroupIdx 를 가져간다.
	 * @return new ImageGroupIdx 가 포함 된 ImageGroupDto.Response
	 */
	ImageGroupDto.Response insertBlankImageGroup();

	/**
	 * 이미지 업로드 ( Non Grouping )
	 * @param fileList 파일
	 * @return ImageDto.Response
	 */
	List<ImageDto.Response> uploadAsNonGroup(Collection<MultipartFile> fileList);
}
