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
package org.eclipsescout.demo.minifigcreator.ui.swing;

import javax.swing.JComponent;

import org.eclipse.scout.commons.ITypeWithClassId;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.IBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.IGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.IStringField;
import org.eclipse.scout.rt.ui.swing.basic.ISwingScoutComposite;

public class SwingEnvironment extends com.bsiag.scout.rt.ui.swing.rayo.RayoSwingEnvironment {

  @Override
  protected void assignWidgetId(ITypeWithClassId model, ISwingScoutComposite uiField) {
    if (isWidgetIdsEnabled()) {
      JComponent swingField = uiField.getSwingField();
      if (swingField != null) {
        swingField.setName(model.classId());
      }
    }
  }
}
