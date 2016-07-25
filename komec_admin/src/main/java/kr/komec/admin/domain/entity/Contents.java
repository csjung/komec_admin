package kr.komec.admin.domain.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 콘텐츠 정보
 * @author csjung
 *
 */
@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Contents {

	@Id
	@GeneratedValue
	private long id;
	
	/** 콘텐츠 내용 */
	private String content;
	
	/** 수정자 아이디 */
	private String upId;
	
	/** 수정일시 */
	private Date upDate = new Date();

}
