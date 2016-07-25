package kr.komec.admin.domain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import kr.komec.admin.domain.enumeration.UseState;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사이트
 * @author csjung
 *
 */
@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Site {

	@Id
	@GeneratedValue
	private long id;
	
	/** 사이트명 */
	private String name;
	
	/** 사이트 주소  */
	private String siteUrl;
	
	/** 사용유무 */
	private UseState useState;
	
	@Transient
	private List<Menu> menus = new ArrayList<Menu>();
	
}
