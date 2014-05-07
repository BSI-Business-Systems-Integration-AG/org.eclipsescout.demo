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
package org.eclipsescout.demo.widgets.client.ui.forms;

import java.util.List;

import org.eclipse.scout.commons.CollectionUtility;
import org.eclipse.scout.commons.CompareUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractOkButton;
import org.eclipse.scout.rt.client.ui.form.fields.colorpickerfield.AbstractColorField;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateField;
import org.eclipse.scout.rt.client.ui.form.fields.filechooserfield.AbstractFileChooserField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipsescout.demo.widgets.client.services.lookup.CompanyTypeLookupCall;
import org.eclipsescout.demo.widgets.client.ui.forms.ContextMenuForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.shared.Icons;
import org.eclipsescout.demo.widgets.shared.services.code.CountryCodeType;
import org.eclipsescout.demo.widgets.shared.services.code.CountryCodeType.FranceCode;
import org.eclipsescout.demo.widgets.shared.services.code.CountryCodeType.USACode;

/**
 *
 */
public class ContextMenuForm extends AbstractForm implements IPageForm {

  /**
   * @throws ProcessingException
   */
  public ContextMenuForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("ContextMenu");
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class LegacySupportGroupBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return "Legacy support";
      }

      @Override
      protected String getConfiguredBorderDecoration() {
        return BORDER_DECORATION_LINE;
      }

      @Order(10.0)
      public class CountrySmartField extends AbstractSmartField<Long> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Country");
        }

        @Override
        protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
          return CountryCodeType.class;
        }

        // context menus
        @Order(10.0)
        public class Edit01Menu extends AbstractMenu {

          @Override
          protected String getConfiguredText() {
            return "Edit (not empty)";
          }

          @Override
          protected boolean getConfiguredSingleSelectionAction() {
            return super.getConfiguredSingleSelectionAction();
          }

          @Override
          protected void execOwnerValueChanged(Object newMasterValue) throws ProcessingException {
            super.execOwnerValueChanged(newMasterValue);
          }

        }
//
//        @Order(10.0)
//        public class EditMenuGroup extends AbstractMenu {
//          @Override
//          protected String getConfiguredText() {
//            return TEXTS.get("Edit");
//          }
//
//          @Override
//          protected String getConfiguredIconId() {
//            return Icons.StarYellow;
//          }
//
//          @Override
//          protected void execAction() throws ProcessingException {
//            MessageBox.showOkMessage("Menu action", "Menu: '" + getLabel() + "'", "");
//          }
//
//        }

      }

      @Order(20.0)
      public class TableField extends AbstractTableField<TableField.Table> {
        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected int getConfiguredGridH() {
          return 3;
        }

        @Override
        protected void execInitField() throws ProcessingException {
          super.execInitField();
          getTable().addRowByArray(new Object[]{"Baluu", "Boralimon"});
          getTable().addRowByArray(new Object[]{"Baluu1", "Boralimon"});
          getTable().addRowByArray(new Object[]{"Baluu2", "Boralimon"});
          getTable().addRowByArray(new Object[]{"Baluu3", "Boralimon"});
        }

        @Order(10)
        public class Table extends AbstractTable {
          @Override
          protected String getConfiguredDefaultIconId() {
            return Icons.StarRed;
          }

          @Override
          protected void execRowsSelected(List<? extends ITableRow> rows) throws ProcessingException {
//            super.execRowsSelected(rows);
          }

          public NameColumn getNameColumn() {
            return getColumnSet().getColumnByClass(NameColumn.class);
          }

          @Order(10)
          public class NameColumn extends AbstractStringColumn {
            @Override
            protected String getConfiguredHeaderText() {
              return "Name (editable)";
            }

            @Override
            protected int getConfiguredWidth() {
              return 250;
            }
          }

          @Order(20)
          public class PrenameColumn extends AbstractStringColumn {
            @Override
            protected String getConfiguredHeaderText() {
              return "Name (!editable)";
            }

            @Override
            protected int getConfiguredWidth() {
              return 250;
            }
          }

          @Order(100)
          public class SingleSelectionMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
              return "Single selection";
            }

            @Override
            protected boolean getConfiguredSingleSelectionAction() {
              return true;
            }

            @Override
            protected void execAction() throws ProcessingException {
              System.out.println("Menu: '" + getClass().getSimpleName() + "'");
            }
          }

          @Order(110)
          public class MultiSelectionMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
              return getClass().getSimpleName();
            }

            @Override
            protected boolean getConfiguredMultiSelectionAction() {
              return true;
            }

            @Override
            protected boolean getConfiguredSingleSelectionAction() {
              return false;
            }

            @Override
            protected void execAction() throws ProcessingException {
              System.out.println("Menu: '" + getClass().getSimpleName() + "'");
            }
          }

          @Order(120)
          public class EmptySpaceMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
              return getClass().getSimpleName();
            }

            @Override
            protected boolean getConfiguredEmptySpaceAction() {
              return true;
            }

            @Override
            protected void execAction() throws ProcessingException {
              System.out.println("Menu: '" + getClass().getSimpleName() + "'");
            }
          }
        }
      }

    }

    @Order(20.0)
    public class ContextMenuGroupBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return "Context menu";
      }

      @Override
      protected String getConfiguredBorderDecoration() {
        return BORDER_DECORATION_LINE;
      }

      @Order(10.0)
      public class CountrySmartField extends AbstractSmartField<Long> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Country");
        }

        @Override
        protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
          return CountryCodeType.class;
        }

        // context menus
        @Order(10.0)
        public class EditMenuGroup extends AbstractMenu {
          @Override
          protected String getConfiguredText() {
            return TEXTS.get("Edit");
          }

          @Override
          protected boolean getConfiguredInheritAccessibility() {
            return true;
          }

          @Override
          protected String getConfiguredIconId() {
            return Icons.StarYellow;
          }

          @Override
          protected void execAction() throws ProcessingException {
            MessageBox.showOkMessage("Menu action", "Menu: '" + getLabel() + "'", "");
          }

          @Order(10.0)
          public class Edit01Menu extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return "Edit (not empty and not France)";
            }

            @Override
            protected void execOwnerValueChanged(Object newOwnerValue) throws ProcessingException {
              setVisible(newOwnerValue != null && !CompareUtility.equals(newOwnerValue, FranceCode.ID));
            }
          }

          @Order(20.0)
          public class Edit02Menu extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return "Edit (empty)";
            }

            @Override
            protected void execOwnerValueChanged(Object newOwnerValue) throws ProcessingException {
              setVisible(newOwnerValue == null);
            }
          }

          @Order(30.0)
          public class Edit03Menu extends AbstractMenu {

            @Override
            protected String getConfiguredText() {
              return "Edit (USA)";
            }

            @Override
            protected void execOwnerValueChanged(Object newOwnerValue) throws ProcessingException {
              setVisible(CompareUtility.equals(newOwnerValue, USACode.ID));
            }
          }
        }

      }

      @Order(50.0)
      public class CompanySmartField extends AbstractSmartField<Long> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Company");
        }

        @Override
        protected Class<? extends ILookupCall<Long>> getConfiguredLookupCall() {
          return CompanyTypeLookupCall.class;
        }
      }

      @Order(60)
      public class StringField extends AbstractStringField {
        @Override
        protected String getConfiguredLabel() {
          return "String field";
        }

        @Override
        protected int getConfiguredHorizontalAlignment() {
          return 1;
        }

        @Order(10)
        public class ContextMenuItem extends AbstractMenu {
          @Override
          protected String getConfiguredText() {
            return "Context menu (only empty)";
          }

          @Override
          protected void execOwnerValueChanged(Object newOwnerValue) throws ProcessingException {
            setVisible(newOwnerValue == null);
          }
        }
      }

      @Order(65)
      public class PasswordField extends AbstractStringField {
        @Override
        protected String getConfiguredLabel() {
          return "Password field";
        }

        @Override
        protected boolean getConfiguredInputMasked() {
          return true;
        }

        @Override
        protected int getConfiguredHorizontalAlignment() {
          return -1;
        }

        @Order(10)
        public class ContextMenuItem extends AbstractMenu {
          @Override
          protected String getConfiguredText() {
            return "Context menu (only empty)";
          }

          @Override
          protected void execOwnerValueChanged(Object newOwnerValue) throws ProcessingException {
            setVisible(newOwnerValue == null);
          }
        }
      }

      @Order(70)
      public class DateField extends AbstractDateField {
        @Override
        protected String getConfiguredLabel() {
          return "Date field";
        }

        @Override
        protected boolean getConfiguredHasTime() {
          return true;
        }

        @Order(10)
        public class ContextMenuItem extends AbstractMenu {
          @Override
          protected String getConfiguredText() {
            return "Context menu (not empty)";
          }

          @Override
          protected void execOwnerValueChanged(Object newOwnerValue) throws ProcessingException {
            setVisible(newOwnerValue != null);
          }
        }
      }

      @Order(80)
      public class IntegerField extends AbstractIntegerField {
        @Override
        protected String getConfiguredLabel() {
          return "Integer field";
        }

        @Override
        protected int getConfiguredHorizontalAlignment() {
          return -1;
        }

        @Order(10)
        public class ContextMenuItem extends AbstractMenu {
          @Override
          protected String getConfiguredText() {
            return "Context menu (not empty)";
          }

          @Override
          protected void execOwnerValueChanged(Object newOwnerValue) throws ProcessingException {
            setVisible(newOwnerValue != null);
          }
        }
      }

      @Order(90.0)
      public class FileChooserField extends AbstractFileChooserField {

        @Override
        protected List<String> getConfiguredFileExtensions() {
          return CollectionUtility.arrayList("png", "bmp", "jpg", "jpeg", "gif");
        }

        @Override
        protected String getConfiguredLabel() {
          return "File chooser";
        }

        @Override
        protected boolean getConfiguredTypeLoad() {
          return true;
        }

        @Order(10)
        public class ContextMenuItem extends AbstractMenu {
          @Override
          protected String getConfiguredText() {
            return "Context menu (not abc)";
          }

          @Override
          protected void execOwnerValueChanged(Object newOwnerValue) throws ProcessingException {
            setVisible(!CompareUtility.equals("abc", newOwnerValue));
          }
        }
      }

      @Order(100)
      public class Button extends AbstractButton {
        @Override
        protected String getConfiguredLabel() {
          return "Button";
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected void execClickAction() throws ProcessingException {
          System.out.println("button selected");
        }

        @Order(10)
        public class ContextMenuItem extends AbstractMenu {
          @Override
          protected String getConfiguredText() {
            return "Context menu";
          }

        }
      }

      @Order(110)
      public class LinkButton extends AbstractButton {
        @Override
        protected String getConfiguredLabel() {
          return "Link";
        }

        @Override
        protected int getConfiguredDisplayStyle() {
          return DISPLAY_STYLE_LINK;
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Order(10)
        public class ContextMenuItem extends AbstractMenu {
          @Override
          protected String getConfiguredText() {
            return "Context menu";
          }

        }
      }

      @Order(120)
      public class RadioButton extends AbstractButton {
        @Override
        protected String getConfiguredLabel() {
          return "Radio";
        }

        @Override
        protected int getConfiguredDisplayStyle() {
          return DISPLAY_STYLE_RADIO;
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Order(10)
        public class ContextMenuItem extends AbstractMenu {
          @Override
          protected String getConfiguredText() {
            return "Context menu";
          }

        }
      }

      @Order(130)
      public class ToggleButton extends AbstractButton {
        @Override
        protected String getConfiguredLabel() {
          return "Toggle";
        }

        @Override
        protected int getConfiguredDisplayStyle() {
          return DISPLAY_STYLE_TOGGLE;
        }

        @Override
        protected boolean getConfiguredProcessButton() {
          return false;
        }

        @Override
        protected void execClickAction() throws ProcessingException {
          getFieldByClass(CountrySmartField.class).setEnabled(isSelected());
        }

        @Order(10)
        public class ContextMenuItem extends AbstractMenu {
          @Override
          protected String getConfiguredText() {
            return "Context menu";
          }

        }
      }

      @Order(140)
      public class ColorField extends AbstractColorField {
        @Override
        protected String getConfiguredLabel() {
          return "Color field";
        }

        @Order(10)
        public class ContextMenuItem extends AbstractMenu {
          @Override
          protected String getConfiguredText() {
            return "Context menu";
          }

          @Override
          protected void execOwnerValueChanged(Object newOwnerValue) throws ProcessingException {
            setVisible(newOwnerValue != null);

          }
        }
      }

      @Order(150)
      public class ColorField2 extends AbstractColorField {
        @Override
        protected String getConfiguredLabel() {
          return "Color field (no icon)";
        }

        @Override
        protected String getConfiguredIconId() {
          return null;
        }

        @Order(10)
        public class ContextMenuItem extends AbstractMenu {
          @Override
          protected String getConfiguredText() {
            return "Context menu";
          }

          @Override
          protected void execOwnerValueChanged(Object newOwnerValue) throws ProcessingException {

          }
        }
      }
    }

    @Order(90.0)
    public class OkButton extends AbstractOkButton {
    }

    @Order(100.0)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }

}