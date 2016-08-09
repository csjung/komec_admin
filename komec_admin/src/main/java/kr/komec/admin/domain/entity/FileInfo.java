package kr.komec.admin.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 파일정보
 * @author csjung
 *
 */
@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FileInfo {

	@Id
	@GeneratedValue
	long id;
	
	/** 물리적 파일 명 */
	private String phyFileName;
	
	/** 논리적 파일 명 */
	private String logFileName;
	
	/** 파일 패스 */
	private String filePath;
	
	/** 파일 확장자 */
	private String fileType;
	
	/** 파일 사이즈 */
	private long fileSize;
}
