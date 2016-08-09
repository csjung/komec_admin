package kr.komec.admin.domain.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import kr.komec.admin.domain.enumeration.UseState;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 배너 존
 * @author csjung
 *
 */
@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BannerZone {

	@Id
	@GeneratedValue
	long id;
	
	/** 타이틀 */
	private String title;
	
	/** 주석 */
	private String remarks;
	
	/** 사용유무 */
	private UseState useState;
	
	/** 수정자 아이디 */
	private String upId;
	
	/** 수정일시 */
	private Date upDate = new Date();
	
	
	/** 사이트 ID */
	private long siteId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "siteId", insertable = false, updatable = false)
	private Site site;
	
	@Transient
	private List<Banner> nodes = new ArrayList<Banner>();
}
