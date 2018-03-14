/**
 * 
 */
package com.sky.game.context.message.impl.ice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.springframework.util.StringUtils;

import Ice.Current;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.MessageException;
import com.sky.game.context.MessageInternalBean;
import com.sky.game.context.SpringContext;
import com.sky.game.context._MessageInternalHandlerDisp;
import com.sky.game.context.handler.ValueHolder;
import com.sky.game.context.spring.IRemoteService;
import com.sky.game.context.spring.ice.MessageInternalBeanParameterWrapper;

/**
 * @author sparrow
 *
 */
public class IceServantMessageInternalHandler extends
		_MessageInternalHandlerDisp {

	/**
	 * 
	 */
	public IceServantMessageInternalHandler() {
		// TODO Auto-generated constructor stub
	}

	private static final Map<String, InvocationTarget> targets = new HashMap<String, InvocationTarget>();

	private synchronized InvocationTarget getInvocationTarget(String namespace) {
		InvocationTarget target = null;
		if (targets.containsKey(namespace)) {
			target = targets.get(namespace);
		} else {
			target = new InvocationTarget(namespace);
			targets.put(namespace, target);
		}
		return target;
	}

	public static class InvocationTarget {
		String beanName;
		Class<?> mapperInterface;
		Method method;

		/**
		 * @param beanName
		 */
		public InvocationTarget(String namespace) {
			super();
			String[] arrays = StringUtils.tokenizeToStringArray(namespace, ".");
			if (arrays != null && arrays.length == 2) {
				this.beanName = arrays[0];
				String m = arrays[1];
				Object t = null;
				if (SpringContext.isEmpty()) {

				} else {
					t = SpringContext.getBean(beanName);
				}
				Class<?> cls[] = t.getClass().getInterfaces();
				for (Class<?> clz : cls) {
					// is a interface IRemoteService.
					if (clz.isInterface()
							&& clz.getSimpleName().equals(this.beanName)) {
						// class has super interface as IRemoteService
						mapperInterface = clz;

						Method[] mmm = mapperInterface.getMethods();
						for (Method mm : mmm) {
							if (mm.getName().equals(m)) {
								this.method = mm;
								break;
							}
						}
						break;

					}

				}
			}
		}

	}

	private Object[] getArgs(InvocationTarget target, String parameter) {
		Class<?> parametersTypes[] = target.method.getParameterTypes();
		Object[] args = new Object[0];
		if (parametersTypes != null && parametersTypes.length > 0) {
			args = new Object[parametersTypes.length];
			MessageInternalBeanParameterWrapper wrapper = GameContextGlobals
					.getJsonConvertor().convert(parameter,
							MessageInternalBeanParameterWrapper.class);
			Map<Integer, String> parameters = wrapper.getParameters();
			for (int i = 0; i < parametersTypes.length; i++) {
				String p = parameters.get(Integer.valueOf(i));
				args[i] = GameContextGlobals.getJsonConvertor().convert(p,
						parametersTypes[i]);
			}
		}
		return args;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.sky.game.context._MessageInternalHandlerOperations#invoke(com.sky
	 * .game.context.MessageInternalBean, Ice.Current)
	 */
	@Override
	public MessageInternalBean invoke(MessageInternalBean param,
			Current __current) throws MessageException {
		// TODO Auto-generated method stub

		InvocationTarget target = getInvocationTarget(param.operation);

		Object[] args = getArgs(target, param.parameter);
		Object respObject = null;

		Object object = SpringContext.getBean(target.beanName);
		try {
			respObject = target.method.invoke(object, args);

			param.parameter = GameContextGlobals.getJsonConvertor().format(
					respObject);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}

		return param;
	}

}
