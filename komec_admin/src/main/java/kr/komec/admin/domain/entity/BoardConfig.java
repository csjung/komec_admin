package kr.komec.admin.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import kr.komec.admin.domain.enumeration.BoardType;
import kr.komec.admin.domain.enumeration.UseState;
import kr.komec.admin.domain.enumeration.YesOrNo;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 게시판 설정정보
 * @author csjung
 *
 */
@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BoardConfig {

	@Id
	@GeneratedValue
	private long id;
	
	/** 게시판 명 */
	private String name;
	
	/** 게시판 종류  */
	private BoardType boardType;
	
	/** 페이지 별 건수 */
	private int pagePerCnt;
	
	/** NEW 아이콘 노출일수 */
	private int newIconDay;
	
	/** HOT 아이콘 노출 일수 */
	private int hotIconDay;
	
	/** 작성 여부 */
	private YesOrNo writeYesOrNo;
	
	/** 삭제 여부 */
	private YesOrNo deleteYesOrNo;
	
	/** 답글 여부 */
	private YesOrNo replayYesOrNo;
	
	
	/** 에디터 사용여부 */
	private UseState useEditState;
	
	/** 사용유무 */
	private UseState useState;
	
}
