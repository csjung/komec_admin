package kr.komec.admin.domain.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 게시판 파일정보
 * @author csjung
 *
 */
@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BoardFiles {

	@Id
	@GeneratedValue
	long id;
	
	/** 게시판 아이디 */
	private long boardDataId;
	
	/** 파일 정보 아이디 */
	private long fileInfoId;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "fileInfoId", insertable = false, updatable = false)
	private FileInfo fileInfo;
	
	/** 표시 순서 */
	private int sort;
}
