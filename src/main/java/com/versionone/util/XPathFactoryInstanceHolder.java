package com.versionone.util;

import javax.xml.xpath.XPathFactory;

/**
 * Holding a XmlFactory instance using ThreadLocal.
 * <p>
 * This approach to fix issues that VersionOne always call {@link XPathFactory#newInstance()} whenever they use it.
 * 
 * @author tuanle
 *
 */
public class XPathFactoryInstanceHolder {
  private static final ThreadLocal<XPathFactory> xpathFactoryInstanceHolder = new ThreadLocal<XPathFactory>();

  /**
   * Get or create new instance of XPathFactory.
   * 
   * @return
   */
  public static XPathFactory get() {
    XPathFactory factory = xpathFactoryInstanceHolder.get();
    if (null == factory) {
      factory = XPathFactory.newInstance();
      xpathFactoryInstanceHolder.set(factory);
    }
    return factory;
  }

}
