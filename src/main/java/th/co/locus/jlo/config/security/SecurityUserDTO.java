package th.co.locus.jlo.config.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import th.co.locus.jlo.system.menu.bean.TokenMenuRespModelBean;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false, doNotUseGetters = true, of = "userId")
public class SecurityUserDTO extends User {

	private static final long serialVersionUID = -7155518853403064085L;
	
	private Long id;
	private String userId;
	
	@JsonIgnore
	private String password;
	
	private String roleCode;
	private String firstName;
	private String lastName;
	private String pictureUrl;
	private String buId;
	private String email;
	
	private String loginType;

	private List<GrantedAuthority> authorityInfo;
	
	@JsonProperty("menuRespList")
    private List<TokenMenuRespModelBean> tokenMenuRespList;

	public SecurityUserDTO(String userId, String password, List<GrantedAuthority> authorities) {
		super(userId, password == null ? "password" : password, authorities);
		this.userId = userId;
		this.password = password;
		this.authorityInfo = authorities;
	}
}
