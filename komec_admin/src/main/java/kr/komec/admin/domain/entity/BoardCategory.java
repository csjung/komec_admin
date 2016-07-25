package kr.komec.admin.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 게시판 글머리 정보
 * @author csjung
 *
 */
@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BoardCategory {

	@Id
	@GeneratedValue
	private long id;
	
	/** 게시판 글머리 명 */
	private String name;
	
	/** 게시판 설정 정보 ID */
	private long boardConfigId;
	
}
