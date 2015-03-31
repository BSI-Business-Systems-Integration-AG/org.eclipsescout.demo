/*******************************************************************************
 * Copyright (c) 2013 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.tutorial.jaxws.server.services.outline;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.tutorial.jaxws.shared.services.outline.CompanyTablePageData;
import org.eclipse.scout.tutorial.jaxws.shared.services.outline.IStandardOutlineService;
import org.eclipse.scout.tutorial.jaxws.shared.services.outline.WSLogTablePageData;

public class StandardOutlineService extends AbstractService implements IStandardOutlineService {

  @Override
  public CompanyTablePageData getCompanyTablePageData(CompanyTablePageData pageData) throws ProcessingException {
    SQL.selectInto("" + 
        "SELECT COMPANY_NR, " + 
        "       NAME, " + 
        "       SYMBOL " + 
        "FROM   COMPANY " + 
        "INTO   :{companyNr}, " + 
        "       :{name}, " + 
        "       :{symbol} ", 
        pageData);
    return pageData;
  }
  
  @Override
  public WSLogTablePageData getWsLogTablePageData(WSLogTablePageData pageData) throws ProcessingException {
    SQL.selectInto("" + 
        "SELECT WS_LOG_NR, " + 
        "       EVT_DATE, " + 
        "       SERVICE, " + 
        "       PORT, " + 
        "       OPERATION " + 
        "FROM   WS_LOG " + 
        "INTO   :{wsLogNr}, " + 
        "       :{date}, " + 
        "       :{service}, " + 
        "       :{port}, " + 
        "       :{operation} ", 
        pageData);
    return pageData;
  }
}
