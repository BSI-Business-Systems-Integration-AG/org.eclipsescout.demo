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
package org.eclipsescout.demo.bahbah.server.services.notification;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.platform.service.SERVICES;
import org.eclipse.scout.rt.server.services.common.clustersync.IClusterNotificationListener;
import org.eclipse.scout.rt.server.services.common.clustersync.IClusterNotificationMessage;
import org.eclipsescout.demo.bahbah.shared.services.process.IUserProcessService;

public class RegisterUserNotificationListener implements IClusterNotificationListener {
  private static final IScoutLogger LOG = ScoutLogManager.getLogger(RegisterUserNotificationListener.class);

  @Override
  public void onNotification(IClusterNotificationMessage notification) {
    if (isInteresting(notification)) {
      RegisterUserNotification registerUserNotification = (RegisterUserNotification) notification.getNotification();
      try {
        SERVICES.getService(IUserProcessService.class).registerUserInternal(registerUserNotification.getUserName());
      }
      catch (ProcessingException e) {
        LOG.error("Unable to register user internal", e);
      }
    }
  }

  public boolean isInteresting(IClusterNotificationMessage notification) {
    return (notification.getNotification() instanceof RegisterUserNotification);
  }

}
