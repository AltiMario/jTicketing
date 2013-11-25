/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.core.forms.security;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class AddForm extends ActionForm
{
  private String name = null;
  private String ssNum = null;

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setSsNum(String ssNum) {
    this.ssNum = ssNum;
  }

  public String getSsNum() {
    return ssNum;
  }

  // Validate form data.
  public ActionErrors validate(ActionMapping mapping,
    HttpServletRequest request)
  {
    ActionErrors errors = new ActionErrors();

    boolean ssNumEntered = true;

    // Determine if name has been entered.
    if (name == null || name.length() == 0) {
			errors.add("name", new ActionMessage("error.add.name.missing"));
    }

    // Determine if social security number has been entered.
    if (ssNum == null || ssNum.length() == 0) {
			errors.add("ssNum", new ActionMessage("error.add.ssNum.missing"));
			ssNumEntered = false;
    }

    /* Validate format of social security number if
       it has been entered. */
    if (ssNumEntered && !isValidSsNum(ssNum.trim())) {
      errors.add("ssNum",
        new ActionMessage("error.add.ssNum.invalid"));
    }

    return errors;
  }

  // Validate format of social security number.
  private static boolean isValidSsNum(String ssNum) {
    if (ssNum.length() < 11) {
      return false;
    }

    for (int i = 0; i < 11; i++) {
      if (i == 3 || i == 6) {
        if (ssNum.charAt(i) != '-') {
          return false;
        }
      } else if ("0123456789".indexOf(ssNum.charAt(i)) == -1) {
        return false;
      }
    }

    return true;
  }
}
