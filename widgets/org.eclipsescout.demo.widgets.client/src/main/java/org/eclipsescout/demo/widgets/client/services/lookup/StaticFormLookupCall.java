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
package org.eclipsescout.demo.widgets.client.services.lookup;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipsescout.demo.widgets.client.ui.forms.IPageForm;
import org.eclipsescout.demo.widgets.client.ui.forms.ImageFieldForm;
import org.eclipsescout.demo.widgets.client.ui.forms.SmartFieldForm;
import org.eclipsescout.demo.widgets.client.ui.forms.StringFieldForm;

public class StaticFormLookupCall extends LocalLookupCall<IPageForm> {
  private static final long serialVersionUID = 1L;

  private final List<ILookupRow<IPageForm>> m_lookupRows = new ArrayList<>();

  public StaticFormLookupCall() throws ProcessingException {
    m_lookupRows.add(new LookupRow<IPageForm>(createAndStartForm(StringFieldForm.class), TEXTS.get("StringField")));
    m_lookupRows.add(new LookupRow<IPageForm>(createAndStartForm(SmartFieldForm.class), TEXTS.get("SmartField")));
    m_lookupRows.add(new LookupRow<IPageForm>(createAndStartForm(ImageFieldForm.class), TEXTS.get("ImageField")));
  }

  protected IPageForm createAndStartForm(Class<? extends IPageForm> formType) throws ProcessingException {
    try {
      IPageForm form = formType.newInstance();
      form.setAutoAddRemoveOnDesktop(false);
      form.setModal(false);
      form.start();
      return form;
    }
    catch (Exception e) {
      throw new ProcessingException("Error while creating instance of " + (formType == null ? "null" : formType.getName()));
    }
  }

  public List<IPageForm> getStaticForms() {
    List<IPageForm> result = new ArrayList<>();
    for (ILookupRow<IPageForm> row : m_lookupRows) {
      result.add(row.getKey());
    }
    return result;
  }

  @Override
  protected List<ILookupRow<IPageForm>> execCreateLookupRows() throws ProcessingException {
    return m_lookupRows;
  }
}
