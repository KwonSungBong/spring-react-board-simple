package kr.co.board.dto;

import lombok.Data;

/**
 * 유저 권한 관련 Dto
 * Created by 권 오빈 on 2016. 7. 31..
 */
public class UserRoleDto {

	@Data
	public static class Create {
		private String role;
	}

	@Data
	public static class Response {
		private String role;
	}
}
