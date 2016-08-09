package kr.komec.admin.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import kr.komec.admin.domain.enumeration.TargetType;
import kr.komec.admin.domain.enumeration.UseState;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 배너
 * @author csjung
 *
 */
@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Banner {

	@Id
	@GeneratedValue
	long id;
	
	/** 타이틀 */
	private String title;
	
	/** 주석 */
	private String remarks;
	
	/** 사용유무 */
	private UseState useState;
	
	/** 출력순서 */
	private long sort;
	
	/** 링크 */
	private String link;
	
	/** 타겟타입 */
	private TargetType targetType;
	
	/** 게시일자(From) */
	private Date dispalyStartDay;
	
	/** 게시일자(To) */
	private Date dispalyEndDay;
	
	/** 수정자 아이디 */
	private String upId;
	
	/** 수정일시 */
	private Date upDate = new Date();
	
	/** 파일 아이디 */
	@Column(nullable = true)
	private long fileInfoId;
	
	/** BannerZone ID */
	private long bannerZoneId;
}
