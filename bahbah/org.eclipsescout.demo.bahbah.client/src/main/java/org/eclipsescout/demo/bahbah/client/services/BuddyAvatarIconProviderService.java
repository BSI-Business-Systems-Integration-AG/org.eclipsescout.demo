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

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.client.services.common.icon.IIconProviderService;
import org.eclipse.scout.rt.client.services.common.icon.IconLocator;
import org.eclipse.scout.rt.client.services.common.icon.IconSpec;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipsescout.demo.bahbah.client.ClientSession;
import org.eclipsescout.demo.bahbah.shared.services.process.IIconProcessService;

@Order(-2000)
public class BuddyAvatarIconProviderService implements IIconProviderService {
  private static final IScoutLogger logger = ScoutLogManager.getLogger(BuddyAvatarIconProviderService.class);

  /**
   * the default buddy icon used when the user has not uploaded an icon yet. icon must be located in client plugin
   * under resources/icons
   */
  public static final String BUDDY_DEFAULT_ICON = "default_buddy_icon";
  public static final String BUDDY_ICON_PREFIX = "@@BUDDY_ICON@@_";
  public static final String OPT_BUDDY_ICON_SUFFIX = "_open";

  public BuddyAvatarIconProviderService() {
  }

  @Override
  public IconSpec getIconSpec(String iconName) {
    if (iconName.startsWith(BUDDY_ICON_PREFIX)) {
      return getBuddyAvatarIconSpec(iconName);
    }
    return null;
  }

  protected IconSpec getBuddyAvatarIconSpec(String iconName) {
    // it is a buddy icon
    if (iconName.endsWith(OPT_BUDDY_ICON_SUFFIX)) {
      // special case for tables: they may add a suffix for open tree nodes -> remove as we only have one icon for expanded & not expanded folders
      iconName = iconName.substring(0, iconName.length() - OPT_BUDDY_ICON_SUFFIX.length());
    }

    IconSpec spec = loadBuddyAvatarIconSpec(iconName.substring(BUDDY_ICON_PREFIX.length()));
    if (spec.getContent() != null) {
      // return the icon from the database
      return spec;
    }
    else {
      // but the user has no icon uploaded yet
      return IconLocator.instance().getIconSpec(BUDDY_DEFAULT_ICON);
    }
  }

  protected IconSpec loadBuddyAvatarIconSpec(String m_iconName) {
    try {
      if (ClientSession.get() != null) {
        byte[] data = BEANS.get(IIconProcessService.class).loadIcon(m_iconName);
        return new IconSpec(m_iconName, data);
      }
    }
    catch (ProcessingException e) {
      logger.error("unable to get buddy icon '" + m_iconName + "' from the database", e);
    }
    return null;
  }

}
