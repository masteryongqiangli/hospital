package system.core.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;

import system.core.annotation.Log;
import system.core.annotation.LogEnter;
import system.core.annotation.LogLeave;
import system.core.service.CommonServiceI;
import system.core.util.BrowserUtils;
import system.core.util.DateUtils;
import system.core.util.IpUtil;
import system.core.util.JSONHelper;
import system.core.util.ResourceUtil;
import system.web.entity.base.Sys_Base_Log;
import system.web.entity.base.Sys_Base_User;
import system.web.service.base.role.RoleServiceI;

@Aspect
@Component
public class SystemLogAspect {
	@Autowired
	private CommonServiceI commonService;
	@Autowired
	private RoleServiceI roleService;
	private static final Logger LOGGER = Logger.getLogger(SystemLogAspect.class);

	@Pointcut("execution (* system.web.controller..*.*(..))")
	public void controllerAspect() {
	}

	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) {
		try {
			String targetName = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			int operationType = 0;
			String operationName = "";
			Class<?> targetClass = Class.forName(targetName);
			Method[] methods = targetClass.getMethods();
			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					Annotation[] annotations = method.getAnnotations();
					for (Annotation annotation : annotations) {
						// 获取注解的具体类型
						Class<? extends Annotation> annotationType = annotation.annotationType();
						if (LogLeave.class == annotationType) {
							LogLeave log = method.getAnnotation(LogLeave.class);
							operationName = log.operationName();
							operationType = log.operationType();
							Sys_Base_Log sys_Base_Log = new Sys_Base_Log();
							HttpServletRequest request = ResourceUtil.getRequest();
							sys_Base_Log.setBrowser(BrowserUtils.checkBrowse(request));
							sys_Base_Log.setDate(DateUtils.getDate());
							sys_Base_Log.setLogType(operationType);
							sys_Base_Log.setDescription(operationName);
							sys_Base_Log.setMethod("" + joinPoint);
							sys_Base_Log.setRequsetIp(IpUtil.getIpAddr(request));
							
							sys_Base_Log.setUser(commonService.get(Sys_Base_User.class,ResourceUtil.getSys_User().getUserId()));
							List<Object> list=new ArrayList<Object>();
							for (Object object2 : joinPoint.getArgs()) {
								if (!object2.getClass().toString().equals("class org.apache.catalina.connector.RequestFacade") && !object2.getClass().toString().equals("org.apache.catalina.connector.ResponseFacade")) {
									list.add(object2);
								}
							}
							list.add(ResourceUtil.getRequest().getParameterMap());
							sys_Base_Log.setParms(JSONHelper.parseListToJSONArray(list).toString());
							// *========控制台输出=========*//
							System.out.println("=====controller前置通知开始=====");
							System.out.println("请求方法:" + (joinPoint.getTarget().getClass().getName() + "."
									+ joinPoint.getSignature().getName() + "()") + "." + operationType);
							System.out.println("方法描述:" + operationName);
							System.out.println("请求人:" + sys_Base_Log.getUser().getUserName());
							System.out.println("请求IP:" + sys_Base_Log.getRequsetIp());
							commonService.save(sys_Base_Log);
							System.out.println("=====controller前置通知结束=====");
						}
					}

				}
			}

		} catch (Throwable e) {
			// 记录本地异常日志
			LOGGER.error("==前置通知异常==");
			LOGGER.error("异常信息:" + e.getMessage());
		}
	}

	// 配置controller环绕通知,使用在方法aspect()上注册的切入点
	@Around("controllerAspect()")
	public Object around(JoinPoint joinPoint) {
		Object object = null;
		long start = System.currentTimeMillis();
		try {
		
			object = ((ProceedingJoinPoint) joinPoint).proceed();
	       
			long end = System.currentTimeMillis();

			String targetName = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			if (methodName.equals("list")) {
				ModelAndView mav =(ModelAndView)object;
			    mav.addObject("auth", roleService.getListAuth(ResourceUtil.getRequestPath(ResourceUtil.getRequest())));
			    object=mav;
			}
			int operationType = 0;
			String operationName = "";
			Class<?> targetClass = Class.forName(targetName);
			Method[] methods = targetClass.getMethods();
			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					Annotation[] annotations = method.getAnnotations();
					for (Annotation annotation : annotations) {
						// 获取注解的具体类型
						Class<? extends Annotation> annotationType = annotation.annotationType();
						if (Log.class == annotationType) {
							
							Log log = method.getAnnotation(Log.class);
							operationName = log.operationName();
							operationType = log.operationType();
							Sys_Base_Log sys_Base_Log = new Sys_Base_Log();
							HttpServletRequest request = ResourceUtil.getRequest();
							sys_Base_Log.setBrowser(BrowserUtils.checkBrowse(request));
							sys_Base_Log.setDate(DateUtils.getDate());
							sys_Base_Log.setLogType(operationType);
							sys_Base_Log.setDescription(operationName);
							sys_Base_Log.setMethod("" + joinPoint);
							sys_Base_Log.setRequsetIp(IpUtil.getIpAddr(request));
							sys_Base_Log.setUser(commonService.get(Sys_Base_User.class,ResourceUtil.getSys_User().getUserId()));
							List<Object> list=new ArrayList<Object>();
							for (Object object2 : joinPoint.getArgs()) {
								if (!object2.getClass().toString().equals("class org.apache.catalina.connector.RequestFacade") && !object2.getClass().toString().equals("org.apache.catalina.connector.ResponseFacade")) {
									list.add(object2);
								}
							}
							list.add(ResourceUtil.getRequest().getParameterMap());
							sys_Base_Log.setParms(JSONHelper.parseListToJSONArray(list).toString());
							sys_Base_Log.setReturnValue(JSONHelper.parseObjectToJSONObejct(object).toString());
							// *========控制台输出=========*//
							System.out.println("=====controller环绕通知开始=====");
							System.out.println("请求方法:" + (joinPoint.getTarget().getClass().getName() + "."
									+ joinPoint.getSignature().getName() + "()") + "." + operationType);
							System.out.println("方法描述:" + operationName);
							System.out.println("请求人:" + sys_Base_Log.getUser().getUserName());
							System.out.println("请求IP:" + sys_Base_Log.getRequsetIp());
							commonService.save(sys_Base_Log);
							System.out.println("=====controller环绕通知结束=====");
						}
					}

				}
			}
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms!");
			}
			return object;
		} catch (Throwable e) {
			long end = System.currentTimeMillis();
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms with exception : "
						+ e.getMessage());
				e.printStackTrace();
			}
			return object;
		}
	}

	// 后置通知 用于拦截Controller层记录用户的操作
	@After("controllerAspect()")
	public void after(JoinPoint joinPoint) {
		try {
			String targetName = joinPoint.getTarget().getClass().getName();
			String methodName = joinPoint.getSignature().getName();
			int operationType = 0;
			String operationName = "";
			Class<?> targetClass = Class.forName(targetName);
			Method[] methods = targetClass.getMethods();
			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					Annotation[] annotations = method.getAnnotations();
					for (Annotation annotation : annotations) {
						// 获取注解的具体类型
						Class<? extends Annotation> annotationType = annotation.annotationType();
						if (LogEnter.class == annotationType) {
							
							LogEnter log = method.getAnnotation(LogEnter.class);
							operationName = log.operationName();
							operationType = log.operationType();
							Sys_Base_Log sys_Base_Log = new Sys_Base_Log();
							HttpServletRequest request = ResourceUtil.getRequest();
							sys_Base_Log.setBrowser(BrowserUtils.checkBrowse(request));
							sys_Base_Log.setDate(DateUtils.getDate());
							sys_Base_Log.setLogType(operationType);
							sys_Base_Log.setDescription(operationName);
							sys_Base_Log.setMethod("" + joinPoint);
							sys_Base_Log.setRequsetIp(IpUtil.getIpAddr(request));
							sys_Base_Log.setUser(commonService.get(Sys_Base_User.class,ResourceUtil.getSys_User().getUserId()));
							List<Object> list=new ArrayList<Object>();
							for (Object object2 : joinPoint.getArgs()) {
								if (!object2.getClass().toString().equals("class org.apache.catalina.connector.RequestFacade") && !object2.getClass().toString().equals("org.apache.catalina.connector.ResponseFacade")) {
									list.add(object2);
								}
							}
							list.add(ResourceUtil.getRequest().getParameterMap());
							sys_Base_Log.setParms(JSONHelper.parseListToJSONArray(list).toString());
							// *========控制台输出=========*//
							System.out.println("=====controller后置通知开始=====");
							System.out.println("请求方法:" + (joinPoint.getTarget().getClass().getName() + "."
									+ joinPoint.getSignature().getName() + "()") + "." + operationType);
							System.out.println("方法描述:" + operationName);
							System.out.println("请求人:" + sys_Base_Log.getUser().getUserName());
							System.out.println("请求IP:" + sys_Base_Log.getRequsetIp());
							commonService.save(sys_Base_Log);
							System.out.println("=====controller后置通知结束=====");
						}
					}

				}
			}

		} catch (Throwable e) {
			// 记录本地异常日志
			LOGGER.error("==后置通知异常==");
			LOGGER.error("异常信息:" + e.getMessage());
		}

	}

}
