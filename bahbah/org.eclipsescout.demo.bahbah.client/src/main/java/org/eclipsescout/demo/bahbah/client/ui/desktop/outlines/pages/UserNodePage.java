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
package org.eclipsescout.demo.bahbah.client.ui.desktop.outlines.pages;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.tree.ITreeNode;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.service.SERVICES;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;
import org.eclipse.scout.rt.shared.ui.UserAgentUtility;
import org.eclipsescout.demo.bahbah.client.ClientSession;
import org.eclipsescout.demo.bahbah.client.services.BuddyAvatarIconProviderService;
import org.eclipsescout.demo.bahbah.client.ui.forms.ChatForm;
import org.eclipsescout.demo.bahbah.client.ui.forms.IconChooserForm;
import org.eclipsescout.demo.bahbah.shared.security.UpdateIconPermission;
import org.eclipsescout.demo.bahbah.shared.services.outline.IStandardOutlineService;

public class UserNodePage extends AbstractPageWithNodes {

  @Override
  protected boolean getConfiguredExpanded() {
    return true;
  }

  @Override
  protected String getConfiguredIconId() {
    return BuddyAvatarIconProviderService.BUDDY_ICON_PREFIX + ClientSession.get().getUserId();
  }

  @Override
  protected boolean getConfiguredTableVisible() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return ClientSession.get().getUserId();
  }

  @Override
  protected void execDecorateCell(Cell cell) {
    cell.setIconId(getConfiguredIconId());
  }

  public ChatForm getChatForm(String buddy) throws ProcessingException {
    if (StringUtility.isNullOrEmpty(buddy)) {
      return null;
    }

    for (ITreeNode node : getChildNodes()) {
      BuddyNodePage buddyNode = (BuddyNodePage) node;
      if (buddyNode.getName().equals(buddy)) {
        return buddyNode.getChatForm();
      }
    }
    return null;
  }

  @Override
  protected void execCreateChildPages(List<IPage<?>> pageList) throws ProcessingException {
    String[] buddies = SERVICES.getService(IStandardOutlineService.class).getOnlineUsers();
    for (String buddy : buddies) {
      BuddyNodePage buddyPage = new BuddyNodePage();
      buddyPage.setName(buddy);
      pageList.add(buddyPage);
    }
  }

  public void updateBuddyPages() throws ProcessingException {
    HashSet<String> newBuddy = new HashSet<String>();
    ArrayList<String> updatedList = new ArrayList<String>();
    String[] buddies = SERVICES.getService(IStandardOutlineService.class).getOnlineUsers();

    for (String buddy : buddies) {
      newBuddy.add(buddy);
    }

    // keep track of known buddies and remove buddies that are no longer here
    List<IPage<?>> childPages = getChildPages();
    for (IPage<?> page : childPages) {
      BuddyNodePage buddyPage = (BuddyNodePage) page;

      if (newBuddy.contains(buddyPage.getName())) {
        updatedList.add(buddyPage.getName());
      }
      else {
        getTree().removeChildNode(this, buddyPage);
      }
    }

    // add new buddies
    for (String buddy : newBuddy) {
      if (!updatedList.contains(buddy)) {
        BuddyNodePage buddyPage = new BuddyNodePage();
        buddyPage.setName(buddy);
        getTree().addChildNode(this, buddyPage);
      }
    }
  }

  @Order(10.0)
  public class ChangeIconMenu extends AbstractMenu {

    @Override
    protected String getConfiguredText() {
      return TEXTS.get("ChangeIcon");
    }

    @Override
    protected void execInitAction() throws ProcessingException {
      setVisible(UserAgentUtility.isDesktopDevice() && ACCESS.check(new UpdateIconPermission()));
    }

    @Override
    protected void execAction() throws ProcessingException {
      IconChooserForm form = new IconChooserForm();
      form.startNew();
    }
  }
}
