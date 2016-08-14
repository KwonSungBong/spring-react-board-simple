package kr.co.board.dto;

import lombok.Data;
import org.joda.time.DateTime;

import java.util.List;
import java.util.UUID;

/**
 * 유저 Entity 관련 Dto
 * Created by 권 오빈 on 2016. 7. 31..
 */
public class UserDto {

	@Data
	public static class Create {
		private String email;
		private String password;
		private String userName;

		private List<UserRoleDto.Create> userRoleList;
	}

	@Data
	public static class Response {
		private UUID uniqueId;
		private String email;
		private String userName;
		private boolean enabled;
		private DateTime createdDate;
		private DateTime lastModifiedDate;

		private List<UserRoleDto.Response> userRoleList;
	}
}
