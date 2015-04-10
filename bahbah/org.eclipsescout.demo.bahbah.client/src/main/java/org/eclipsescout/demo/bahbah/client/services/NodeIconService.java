package org.eclipsescout.demo.bahbah.client.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.scout.rt.platform.service.AbstractService;
import org.eclipsescout.demo.bahbah.shared.Icons;

/**
 * Service providing icons for a given node
 */
public class NodeIconService extends AbstractService implements INodeIconService {

  private final List<String> m_icons = new ArrayList<String>();
  private final Map<String, String> m_nodeIcons = new HashMap<String, String>();
  private final Object m_lockObject = new Object();

  @Override
  public void initializeService() {
    synchronized (m_lockObject) {
      m_icons.add(Icons.DotBlue);
      m_icons.add(Icons.DotOrgange);
      m_icons.add(Icons.DotGreen);
    }
  }

  @Override
  public String getIcon(String nodeId) {
    synchronized (m_lockObject) {
      if (!m_nodeIcons.containsKey(nodeId)) {
        String icon = m_icons.get(0);
        if (icon != null) {
          m_nodeIcons.put(nodeId, icon);
          m_icons.remove(0);
          return icon;
        }
        else {
          m_nodeIcons.put(nodeId, Icons.Star);
        }
      }
      return m_nodeIcons.get(nodeId);
    }

  }

}
