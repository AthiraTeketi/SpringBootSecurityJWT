package Dto;

import lombok.Data;

@Data
public class RefreshTokenRequest {
	
	private String token;

	public RefreshTokenRequest( ) {
		//no Args Constructor;

	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
