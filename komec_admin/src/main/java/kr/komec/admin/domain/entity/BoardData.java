package kr.komec.admin.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import kr.komec.admin.domain.enumeration.UseState;
import kr.komec.admin.domain.enumeration.YesOrNo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BoardData {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	/** 타이틀 */
	private String title;
	
	/** 내용 */
	private String contents;
	
	/** 공지 여부 */
	private YesOrNo noticeOn;
	
	/** 사용유무 */
	private UseState useState;
	
	/** 신규 입력 아이디 */
	private String insertId;
	/** 신규 입력 사용자명 */
	private String insertName;
	/** 신규 입력 아이피 */
	private String insertIp;
	/** 패스워드 */
	private String password;
	
	/** 수정 입력 아이디 */
	private String updateId;
	/** 수정 입력 사용자명 */
	private String updateName;
	/** 수정 입력 아이피 */
	private String updateIp;
	
	/** 조회수 */
	private int cnt;
		
	/** 게시판 정보 아이디 */
	private long boardConfigId;
	
	/** 게시판 정보 아이디 */
	@Column(nullable = true)
	private long boardCategoryId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "boardCategoryId", insertable = false, updatable = false)
	private BoardCategory boardCategory;
	
}
