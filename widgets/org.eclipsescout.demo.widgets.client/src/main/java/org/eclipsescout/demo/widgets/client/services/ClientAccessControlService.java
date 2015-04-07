/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipsescout.demo.widgets.client.services;

import java.security.AllPermission;
import java.security.Permissions;

import org.eclipse.scout.rt.client.Client;
import org.eclipse.scout.rt.shared.services.common.security.AbstractSharedAccessControlService;
import org.eclipsescout.demo.widgets.client.ClientSession;

/**
 * Client implementation of {@link org.eclipse.scout.rt.shared.services.common.security.IAccessControlService}
 */
@Client(ClientSession.class) /* XXX mvi check requirement of session class */
public class ClientAccessControlService extends AbstractSharedAccessControlService {

  @Override
  protected Permissions execLoadPermissions() {
    Permissions permissions = new Permissions();
    permissions.add(new AllPermission());
    return permissions;
  }
}
