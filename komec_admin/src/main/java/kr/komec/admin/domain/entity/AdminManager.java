package kr.komec.admin.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 관리자
 * @author csjung
 *
 */
@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AdminManager {

	@Id
	@GeneratedValue
	long id;
	
	/** 사용자 아이디 */
	@Column(unique=true)
	String userId;
	
	/** 패스워드 */
	String password;
	
	/** 사용자 이름 */
	String userName;
	
	
}
