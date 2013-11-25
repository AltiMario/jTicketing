/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.utilities;




import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 *
 * @author Danilo Del Fio
 */
public class SpringApplicationContextHelper {

  private static final Logger logWriter = Logger.getLogger(SpringApplicationContextHelper.class);

  private static SpringApplicationContextHelper springApplicationContextHelper;
  private ApplicationContext appContext;
  private static boolean contextLoaded;

  /**
   * Construttore
   */
  private SpringApplicationContextHelper() {
    logWriter.debug("SpringApplicationContextHelper(): start - inizializzazione contesto SpringFramework");
    this.appContext = new ClassPathXmlApplicationContext("classpath:META-INF/jTicketing-appcontext.xml");
    logWriter.debug("SpringApplicationContextHelper(): stop - inizializzazione contesto SpringFramework");
  }

  /**
   * Restituisce l'istanza del contesto Spring
   * @return
   */
  public static synchronized SpringApplicationContextHelper getInstance() {
    // TODO: syncronization could be avoided at this level, find a more correct solution for singleton
    if (springApplicationContextHelper == null) {
      try {
        springApplicationContextHelper = new SpringApplicationContextHelper();
        contextLoaded = true;
      } catch (Throwable e) {
        logWriter.error("Exception while getting spring context", e);
        contextLoaded = false;
        springApplicationContextHelper = null;
      }
    }
    return springApplicationContextHelper;
  }

  /**
   * Ritorna l'istanza dell'ApplicationContext dello SpringFramework
   * @return
   */
  public ApplicationContext getContext() {
    return appContext;
  }

  /**
   * Restituisce il bean specificato
   * @param beanName - nome del bean nel contesto Spring
   * @return
   */
  public Object getBean(String beanName) {
    return appContext.getBean(beanName);
  }

  /**
   * Restituisce lo stato del contesto dello SpringFramework
   * @return the contextLoaded
   */
  public static boolean isContextLoaded() {
    return contextLoaded;
  }


}
