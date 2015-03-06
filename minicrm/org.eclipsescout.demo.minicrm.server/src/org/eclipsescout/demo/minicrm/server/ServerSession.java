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
package org.eclipsescout.demo.minicrm.server;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.server.AbstractServerSession;
import org.eclipse.scout.rt.server.IServerSession;
import org.eclipse.scout.rt.shared.ISession;

public class ServerSession extends AbstractServerSession {
  private static final long serialVersionUID = 47025786503269671L;
  private static IScoutLogger LOG = ScoutLogManager.getLogger(ServerSession.class);

  public ServerSession() {
    super(true);
  }

  /**
   * @return session in current ThreadContext
   */
  public static IServerSession get() {
    ISession session = ISession.CURRENT.get();
    return (IServerSession) (session instanceof IServerSession ? session : null);
  }

  @Override
  protected void execLoadSession() throws ProcessingException {
    LOG.info("created a new session for " + getUserId());
  }
}
