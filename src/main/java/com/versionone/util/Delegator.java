package com.versionone.util;

import java.lang.reflect.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Delegator class from the article
 * http://www.onjava.com/pub/a/onjava/2003/05/21/delegates.html?page=1
 * <p/>
 * Class representing a delegate template -
 * also a factory returning the real delegate object
 *
 * @author Steve Lewis smlewis@lordjoe.com
 */
@SuppressWarnings("unchecked")
public class Delegator {
    public static final Method[] EMPTY_METHOD_ARRAY = {};
    public static final Object[] EMPTY_OBJECT_ARRAY = {};
    public static final Delegator[] EMPTY_ARRAY = {};

    // convenience implementation
    public static final Delegator RUNNABLE_DELEGATE = new Delegator(Runnable.class);

    /**
     * Convenience method to make a runnable delegate
     *
     * @param item       non-null target object
     * @param methodName non-null name of a method of type void ()
     * @return non-null Runnable proxy
     */
    public static Runnable buildRunnable(Object item, String methodName) {
        return ((Runnable) RUNNABLE_DELEGATE.build(item, methodName));
    }

    /**
     * Convenience method to make a runnable delegate
     *
     * @param item       non-null target class
     * @param methodName non-null name of a method of type void ()
     * @return non-null Runnable proxy
     */
    public static Runnable buildRunnable(Class item, String methodName) {
        return ((Runnable) RUNNABLE_DELEGATE.build(item, methodName));
    }

    private final Class m_Interface;  // may be null
    private final Class m_Return;
    private final Class[] m_Arguments;

    /**
     * @param params   non-null array of arguments
     * @param retClass possibly null return class null says do not care
     */
    public Delegator(Class[] params, Class retClass) {
        m_Interface = null;
        m_Return = retClass;
        m_Arguments = params;
    }

    /**
     * @param TheInterface an non-null interface with EXACTLY one method
     */
    public Delegator(Class TheInterface) {
        m_Interface = TheInterface;
        Method met = findMethod(TheInterface);
        m_Return = met.getReturnType();
        m_Arguments = met.getParameterTypes();
    }

    /**
     * accessor for return class
     */
    public Class getReturn() {
        return m_Return;
    }


    /**
     * accessor for argument classes
     */
    public Class[] getArguments() {
        return m_Arguments;
    }

    public Class getInterface() {
        return m_Interface;
    }

    /**
     * @param target     non-null class with a bindable static method
     * @param MethodName name of the static method
     * @return non-null IDelegate if getInterface() is non-null it will be a dynamic proxy implementing that interface
     */
    public IDelegate build(Class target, String MethodName) {
        Class myInterface = getInterface();
        DelegateProxy theDelegate = new DelegateProxy(null, target, MethodName, this);

        if (myInterface != null) {
            Class[] interfaces = {myInterface, IDelegate.class};
            IDelegate ret = (IDelegate) java.lang.reflect.Proxy.newProxyInstance(
                    target.getClassLoader(),
                    interfaces, theDelegate);
            return ret;
        }

        return theDelegate;
    }

    /**
     * @param target     non-null target with a bindable method
     * @param MethodName name of the  method
     * @return non-null IDelegate if getInterface() is non-null it will be a dynamic proxy implementing that interface
     */
    public IDelegate build(Object target, String MethodName) {
        Class myInterface = getInterface();
        DelegateProxy theDelegate = new DelegateProxy(target, target.getClass(), MethodName, this);

        if (myInterface != null) {      // build a dynamic proxy
            Class[] interfaces = {myInterface, IDelegate.class};
            IDelegate ret = (IDelegate) java.lang.reflect.Proxy.newProxyInstance(
                    target.getClass().getClassLoader(),
                    interfaces, theDelegate);
            return (ret);

        }

        if (!(theDelegate instanceof IDelegate)) {
            throw new ClassCastException();
        }

        return theDelegate;
    }

    protected class DelegateProxy implements IDelegate, InvocationHandler {
        private final Method m_Method;
        private final Object m_Target;
        @SuppressWarnings("unused")
        private final Delegator m_Template;

        /**
         * constructor supplying a Class passing in types not template
         *
         * @param target     possibly null target if null the method must be static
         * @param target     non-null class implementing a suitable  method
         * @param target     non-null object implementing a suitable static method
         * @param methodName nun-null name of a public static method in target
         * @param template   non-null template with the required arguemts and return
         */
        protected DelegateProxy(Object target, Class targetClass, String methodName, Delegator template) {
            m_Template = template;
            m_Target = target;
            m_Method = findSuitableMethod(targetClass, methodName, template);
        }

        /**
         * convenience call to handle case of no arguments
         *
         * @return whatever is returned
         */
        public Object invoke() throws IllegalArgumentException, DelegateInvokeException {
            return invoke(EMPTY_OBJECT_ARRAY);
        }

        /**
         * convenience call to handle case of one argument
         *
         * @param arg some argument
         * @return whatever is returned
         */
        public Object invoke(Object arg) throws IllegalArgumentException, DelegateInvokeException {
            Object[] args = {arg};
            return invoke(args);
        }

        /**
         * convenience call to handle case of two argument
         *
         * @param arg1 some argument
         * @param arg2 some argument
         * @return whatever is returned
         */
        public Object invoke(Object arg1, Object arg2) throws IllegalArgumentException, DelegateInvokeException {
            Object[] args = {arg1, arg2};
            return invoke(args);
        }

        /**
         * method required by InvocationHandler so we can build dynamic Proxys
         *
         * @param proxy  object for which we are a proxy   (ignored)
         * @param method method to call (ignored)
         * @param args   arguments to pass
         * @return whatever is returned primitive types are wrapped
         */
        public Object invoke(Object proxy, Method method, Object[] args) {
            return invoke(args);
        }

        /**
         * basic call to method
         *
         * @param args method arguments
         * @return whatever is returned
         */
        public Object invoke(Object[] args) throws IllegalArgumentException, DelegateInvokeException {
            // validateArgs(args);
            try {
                Object ret = getMethod().invoke(getTarget(), args);
                return ret;
            } catch (IllegalAccessException ex) {
                throw new IllegalStateException("Bad Delegate State: " + ex.getMessage()); // should not happen
            } catch (InvocationTargetException ex) {
                throw new Delegator.DelegateInvokeException(ex.getCause());
            }
        }


        /**
         * if uncommented in invoke this code will throw an IllegalArgument call
         * if arguments are of the wrong type
         */
        protected void validateArgs(Object[] args) throws IllegalArgumentException {
            Class[] MyArgs = getArguments();

            if (args.length != MyArgs.length) {
                throw new IllegalArgumentException("Delegate required " + MyArgs.length + "arguments");
            }

            for (int i = 0; i < args.length; i++) {
                if (!MyArgs[i].isInstance(args[i])) {
                    throw new IllegalArgumentException("Argument " + i + " must be of class " + MyArgs[i].getName());
                }
            }
        }

        /**
         * accessor for the method
         */
        public Method getMethod() {
            return m_Method;
        }

        /**
         * accessor for the target
         */
        public Object getTarget() {
            return m_Target;
        }

    }// end class DelegateProxy

    /**
     * All problems become this type of exception
     * As a runtime we do not impose burdens on the calling code
     */
    public static class DelegateInvokeException extends RuntimeException {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        public DelegateInvokeException(Throwable cause) {
            super(cause);
        }
    }

    // ===================================================================
    // static utility methods in this section identify the
    // method in verious targets
    // ===================================================================

    /**
     * utility method to test suitability
     */
    protected static boolean isSuitableMethod(Method testMethod, Class[] args, Class retClass) {
        Class[] methodArgs = testMethod.getParameterTypes();

        for (int i = 0; i < methodArgs.length; i++) {
            Class arg = methodArgs[i];

            if (!arg.isAssignableFrom(args[i])) {
                return false;
            }
        }
        // This is the only
        isValidReturn(testMethod, retClass);
        return (true);
    }

    /**
     * utility method to get candidate methods to search
     */
    protected static Method[] getCandidateMethods(Class targetClass, String MethodName, int nargs) {
        Method[] possibilities = targetClass.getMethods();
        List holder = new ArrayList();

        for (int i = 0; i < possibilities.length; i++) {
            Method possibility = possibilities[i];

            if (possibility.getName().equals(MethodName) &&
                    possibility.getParameterTypes().length == nargs &&
                    Modifier.isPublic(possibility.getModifiers())) {
                holder.add(possibility);
            }
        }

        return (Method[]) holder.toArray(EMPTY_METHOD_ARRAY);
    }

    /**
     * utility method to test return
     */
    protected static boolean isValidReturn(Method test, Class retClass) {
        if (retClass == null) {
            return true; // we do not care
        }

        if (test.getReturnType() == retClass) {
            return true;
        }

        if (retClass.isAssignableFrom(test.getReturnType())) {
            return true;
        }

        return (false);
    }

    /**
     * Utility method to locate a proper Method object
     */
    protected static Method findSuitableMethod(Class targetClass, String MethodName, Delegator templ) {
        Class[] args = templ.getArguments();
        Class retClass = templ.getReturn();
        // perfect match
        try {
            Method ret = targetClass.getMethod(MethodName, args);

            if (!isValidReturn(ret, retClass)) {
                throw new IllegalArgumentException("Requested method returns wrong type");
            }

            if (!Modifier.isPublic(ret.getModifiers())) {
                throw new IllegalArgumentException("Requested method is not public");
            }

            return ret;
        } catch (Exception ex) {
        } // on to try2

        Method[] possibilities = getCandidateMethods(targetClass, MethodName, args.length);

        for (int i = 0; i < possibilities.length; i++) {
            Method possibility = possibilities[i];

            if (isSuitableMethod(possibility, args, retClass)) {
                return possibility;
            }
        }

        throw new IllegalArgumentException("No suitable method found");
    }

    /**
     * utility code to find the one suitable method in the passed in interface.
     */
    protected static Method findMethod(Class TheInterface) {
        if (!TheInterface.isInterface()) {
            throw new IllegalArgumentException("DelegateTemplate must be constructed with an interface");
        }

        Method[] methods = TheInterface.getMethods();
        Method ret = null;

        for (int i = 0; i < methods.length; i++) {
            Method test = methods[i];

            if (Modifier.isAbstract(test.getModifiers())) {
                if (ret != null) {
                    throw new IllegalArgumentException("DelegateTemplate must be constructed with an interface implementing only one method!");
                }

                ret = test;
            }
        }

        if (ret == null) {
            throw new IllegalArgumentException("DelegateTemplate must be constructed with an interface implementing exactly method!");
        }

        return ret;
    }
}
