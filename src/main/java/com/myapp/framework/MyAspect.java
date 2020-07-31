package com.myapp.framework;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class MyAspect {

	//@Before("execution(* com.myapp.MyController+.**(..)) && @annotation(org.springframework.web.bind.annotation.GetMapping))")
	@Before("execution(* com.myapp.framework.MyControllerMVC+.**(..))")
	public void beforeRequest(JoinPoint joinPoint) {
		System.out.println("Before Calling the method " + joinPoint.getSignature().getName());
		MyControllerMVC controller = (MyControllerMVC) joinPoint.getTarget();

		
		try {
			Method[] methods = controller.getModel().getClass().getMethods();
			for (Method m : methods) {
				m.setAccessible(true);
				if (m.getName().startsWith("set")) {
					String attributeName = m.getName().substring(3, m.getName().length()).toLowerCase();
					if (controller.request.getParameter(attributeName) != null) {
						m.invoke(controller.getModel(), controller.request.getParameter(attributeName));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Per vedere tutte le annotazioni del metodo
		/*
		 * MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		 * Method method = signature.getMethod();
		 * 
		 * Annotation[] an = method.getDeclaredAnnotations(); for(Annotation a : an) {
		 * System.out.println("annotation: " + a.toString()); }
		 */
	}
}