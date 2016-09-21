package kr.komec.admin.aop;

import java.lang.reflect.Field;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class DefaultDataSet {

	@Before ("execution(* org.springframework.data.jpa.repository.JpaRepository+.save(..))")
	public void dataSet(JoinPoint joinPoint) throws IllegalArgumentException, IllegalAccessException {
		System.out.println("Completed: " + joinPoint);
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String userId = "admin";
		/*
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			userId = auth.getName();
		}
		*/
		
		Object[] objs = joinPoint.getArgs();
		if (objs.length > 0) {
		    Date now = new Date(); 
			Field fieldlist[] = objs[0].getClass().getDeclaredFields();
			long idValue = 0;
            for (int i = 0; i < fieldlist.length; i++) {
                Field fld = fieldlist[i];
                String fldName = fld.getName();
                String typeName = fld.getType().getName();
                if ("id".equals(fldName) && "long".equals(typeName)) {
                	fld.setAccessible(true);
                	idValue = ((Long)fld.get(objs[0])).longValue();
                }
                if ("insertIp".equals(fldName) && idValue == 0) {
                	fld.setAccessible(true);
                	fld.set(objs[0], request.getRemoteAddr());
                }
                if ("insertId".equals(fldName) && idValue == 0) {
                	fld.setAccessible(true);
                	fld.set(objs[0], userId);
                }
                if ("insertDate".equals(fldName) && idValue == 0) {
                	fld.setAccessible(true);
                	fld.set(objs[0], now);
                }
                if ("updateIp".equals(fldName) && idValue > 0) {
                	fld.setAccessible(true);
                	fld.set(objs[0], request.getRemoteAddr());
                }
                if ("updateId".equals(fldName) && idValue > 0) {
                	fld.setAccessible(true);
                	fld.set(objs[0], userId);
                }
                if ("updateDate".equals(fldName) && idValue > 0) {
                	fld.setAccessible(true);
                	fld.set(objs[0], now);
                }
           }
		}
	}
}
