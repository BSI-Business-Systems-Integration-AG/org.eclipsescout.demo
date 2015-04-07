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
package org.eclipsescout.demo.bahbah.client.services;

import java.net.URL;

import org.eclipse.scout.rt.client.Client;
import org.eclipse.scout.rt.client.services.common.icon.AbstractIconProviderService;
import org.eclipsescout.demo.bahbah.client.ClientSession;

@Client(ClientSession.class) /* XXX mvi check requirement of session class */
public class BuddyIconProviderService extends AbstractIconProviderService {

  @Override
  protected URL findResource(String relativePath) {
    return org.eclipsescout.demo.bahbah.client.ResourceBase.class.getResource("icons/" + relativePath);
  }
}
