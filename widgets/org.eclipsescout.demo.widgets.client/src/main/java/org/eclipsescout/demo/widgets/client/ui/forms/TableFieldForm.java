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
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.eclipse.scout.commons.CollectionUtility;
import org.eclipse.scout.commons.DateUtility;
import org.eclipse.scout.commons.IRunnable;
import org.eclipse.scout.commons.NumberUtility;
import org.eclipse.scout.commons.StringUtility;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.job.ClientJobs;
import org.eclipse.scout.rt.client.job.ModelJobs;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ColumnSet;
import org.eclipse.scout.rt.client.ui.basic.table.ITable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.TableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBooleanColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractIconColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.IColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractLinkButton;
import org.eclipse.scout.rt.client.ui.form.fields.checkbox.AbstractCheckBox;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.ICodeType;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipsescout.demo.widgets.client.services.lookup.IconIdLookupCall;
import org.eclipsescout.demo.widgets.client.ui.forms.TableFieldForm.MainBox.CloseButton;
import org.eclipsescout.demo.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox;
import org.eclipsescout.demo.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.DefaultIconIdField;
import org.eclipsescout.demo.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.DeletedRowsField;
import org.eclipsescout.demo.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.InsertedRowsField;
import org.eclipsescout.demo.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox;
import org.eclipsescout.demo.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.AutoResizeColumnsField;
import org.eclipsescout.demo.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.IsCheckableField;
import org.eclipsescout.demo.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.IsEditableField;
import org.eclipsescout.demo.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.MultiSelectField;
import org.eclipsescout.demo.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.TableHeaderVisibleField;
import org.eclipsescout.demo.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.TableStatusVisibleField;
import org.eclipsescout.demo.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.PropertiesGroupBox.WrapTextField;
import org.eclipsescout.demo.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.SelectedRowsField;
import org.eclipsescout.demo.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.TableField;
import org.eclipsescout.demo.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.TableField.Table.LocationColumn;
import org.eclipsescout.demo.widgets.client.ui.forms.TableFieldForm.MainBox.ConfigurationBox.UpdatedRowsField;
import org.eclipsescout.demo.widgets.client.ui.forms.TableFieldForm.MainBox.ExamplesBox;
import org.eclipsescout.demo.widgets.client.ui.forms.TableFieldForm.MainBox.ExamplesBox.DefaultField;
import org.eclipsescout.demo.widgets.client.ui.template.formfield.AbstractFileTableField;
import org.eclipsescout.demo.widgets.shared.services.code.IndustryICBCodeType;

public class TableFieldForm extends AbstractForm implements IPageForm {

  public TableFieldForm() throws ProcessingException {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("TableField");
  }

  @Override
  public void startPageForm() throws ProcessingException {
    startInternal(new PageFormHandler());
  }

  /**
   * @return the AutoResizeColumnsField
   */
  public AutoResizeColumnsField getAutoResizeColumnsField() {
    return getFieldByClass(AutoResizeColumnsField.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  /**
   * @return the DefaultField
   */
  public DefaultField getDefaultField() {
    return getFieldByClass(DefaultField.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  public ConfigurationBox getConfigurationBox() {
    return getFieldByClass(ConfigurationBox.class);
  }

  /**
   * @return the TableHeaderVisibleField
   */
  public TableHeaderVisibleField getTableHeaderVisibleField() {
    return getFieldByClass(TableHeaderVisibleField.class);
  }

  /**
   * @return the InsertedRowsField
   */
  public InsertedRowsField getInsertedRowsField() {
    return getFieldByClass(InsertedRowsField.class);
  }

  /**
   * @return the IsEditableField
   */
  public IsEditableField getIsEditableField() {
    return getFieldByClass(IsEditableField.class);
  }

  public IsCheckableField getIsCheckableField() {
    return getFieldByClass(IsCheckableField.class);
  }

  public WrapTextField getWrapText() {
    return getFieldByClass(WrapTextField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  /**
   * @return the MultiSelectField
   */
  public MultiSelectField getMultiSelectField() {
    return getFieldByClass(MultiSelectField.class);
  }

  public PropertiesGroupBox getPropertiesGroupBox() {
    return getFieldByClass(PropertiesGroupBox.class);
  }

  /**
   * @return the SelectedRowsField
   */
  public SelectedRowsField getSelectedRowsField() {
    return getFieldByClass(SelectedRowsField.class);
  }

  /**
   * @return the TableField
   */
  public TableField getTableField() {
    return getFieldByClass(TableField.class);
  }

  /**
   * @return the TableStatusVisibleField
   */
  public TableStatusVisibleField getTableStatusVisibleField() {
    return getFieldByClass(TableStatusVisibleField.class);
  }

  public UpdatedRowsField getUpdatedRowsField() {
    return getFieldByClass(UpdatedRowsField.class);
  }

  /**
   * @return the DefaultIconIdField
   */
  public DefaultIconIdField getDefaultIconIdField() {
    return getFieldByClass(DefaultIconIdField.class);
  }

  public DeletedRowsField getDeletedRowsField() {
    return getFieldByClass(DeletedRowsField.class);
  }

  @Order(10.0)
  public class MainBox extends AbstractGroupBox {

    @Order(10.0)
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10.0)
      public class DefaultField extends AbstractFileTableField {

        @Override
        protected int getConfiguredGridH() {
          return 4;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("Default");
        }

        @Override
        protected String getConfiguredTooltipText() {
          return TEXTS.get("TableContextMenus");
        }
      }
    }

    @Order(20.0)
    public class ConfigurationBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Configure");
      }

      @Order(10.0)
      public class TableField extends AbstractTableField<TableField.Table> {

        private static final String EDITABLE_CELL_BACKGROUND_COLOR = "EFEFFF";

        private long m_maxId = 0;

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected int getConfiguredGridW() {
          return 2;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("TableField");
        }

        private long getNextId() {
          return ++m_maxId;
        }

        public void addExampleRows() throws ProcessingException {
          Table table = getTable();
          ITableRow r;

          //First Row:
          r = table.addRow(getTable().createRow());
          table.getIdColumn().setValue(r, getNextId());
          table.getNameColumn().setValue(r, "Eclipsecon USA");
          table.getLocationColumn().setValue(r, "San Francisco, USA");
          table.getDateColumn().setValue(r, DateUtility.parse("18.03.2014", "dd.MM.yyyy"));
          table.getIndustryColumn().setValue(r, IndustryICBCodeType.ICB9000.ICB9500.ICB9530.ICB9537.ID);
          table.getParticipantsColumn().setValue(r, 680L);
          table.getWebPageColumn().setValue(r, "http://www.eclipsecon.org");
          table.getPhoneColumn().setValue(r, "+41 (0)79 123 45 67");
          table.getTrendColumn().setValue(r, "font:\uF176");

          //Second Row:
          r = table.addRow(getTable().createRow());
          table.getIdColumn().setValue(r, getNextId());
          table.getNameColumn().setValue(r, "Javaland");
          table.getLocationColumn().setValue(r, "Bruehl, Germany");
          table.getDateColumn().setValue(r, DateUtility.parse("25.03.2014", "dd.MM.yyyy"));
          table.getIndustryColumn().setValue(r, IndustryICBCodeType.ICB9000.ICB9500.ICB9530.ICB9537.ID);
          table.getParticipantsColumn().setValue(r, 810L);
          table.getWebPageColumn().setValue(r, "http://www.javaland.eu");
          table.getAttendedColumn().setValue(r, true);
          table.getTrendColumn().setValue(r, "font:\uF175");
        }

        @Override
        protected void execUpdateTableStatus() {
          super.execUpdateTableStatus();

          ITable table = getTable();

          // TODO why is updatetablestatus called during construction of the table?
          if (getRootGroupBox() != null) {
            getSelectedRowsField().setValue(rowsToKeyString(table.getSelectedRows()));
            getInsertedRowsField().setValue(rowsToKeyString(table.getInsertedRows()));
            getUpdatedRowsField().setValue(rowsToKeyString(table.getUpdatedRows()));
            getDeletedRowsField().setValue(rowsToKeyString(table.getDeletedRows()));
          }
        }

        private String rowsToKeyString(List<ITableRow> list) {
          if (list == null || list.size() == 0) {
            return "";
          }

          StringBuffer buf = new StringBuffer(Long.toString((Long) list.get(0).getCellValue(0)));
          for (int i = 1; i < list.size(); i++) {
            buf.append(";" + (Long) list.get(i).getCellValue(0));
          }

          return buf.toString();
        }

        @Order(10.0)
        public class Table extends AbstractTable {

          // TODO: [BUG] Table bug: organize columns throws null pointer exception

          public NameColumn getNameColumn() {
            return getColumnSet().getColumnByClass(NameColumn.class);
          }

          public LocationColumn getLocationColumn() {
            return getColumnSet().getColumnByClass(LocationColumn.class);
          }

          public IndustryColumn getIndustryColumn() {
            return getColumnSet().getColumnByClass(IndustryColumn.class);
          }

          public ParticipantsColumn getParticipantsColumn() {
            return getColumnSet().getColumnByClass(ParticipantsColumn.class);
          }

          public WebPageColumn getWebPageColumn() {
            return getColumnSet().getColumnByClass(WebPageColumn.class);
          }

          public PhoneColumn getPhoneColumn() {
            return getColumnSet().getColumnByClass(PhoneColumn.class);
          }

          public TrendColumn getTrendColumn() {
            return getColumnSet().getColumnByClass(TrendColumn.class);
          }

          public DateColumn getDateColumn() {
            return getColumnSet().getColumnByClass(DateColumn.class);
          }

          public IdColumn getIdColumn() {
            return getColumnSet().getColumnByClass(IdColumn.class);
          }

          public AttendedColumn getAttendedColumn() {
            return getColumnSet().getColumnByClass(AttendedColumn.class);
          }

          @Override
          protected void execDecorateRow(ITableRow row) throws ProcessingException {
            Long participants = getParticipantsColumn().getValue(row);
            if (participants != null && participants > 800) {
              row.setIconId("font:\uE001");
            }
            else {
              row.setIconId(null);
            }
          }

          @Order(10.0)
          public class IdColumn extends AbstractLongColumn {

            @Override
            protected boolean getConfiguredDisplayable() {
              return false;
            }

            @Override
            protected boolean getConfiguredPrimaryKey() {
              return true;
            }

            @Override
            protected boolean getConfiguredVisible() {
              return false;
            }
          }

          @Order(20.0)
          public class NameColumn extends AbstractStringColumn {

            @Override
            protected boolean getConfiguredEditable() {
              return true;
            }

            @Override
            protected boolean getConfiguredMandatory() {
              return true;
            }

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Name");
            }

            @Override
            protected int getConfiguredWidth() {
              return 120;
            }

            @Override
            protected void execDecorateCell(Cell cell, ITableRow row) throws ProcessingException {
              super.execDecorateCell(cell, row);
            }

            @Override
            protected String execValidateValue(ITableRow row, String rawValue) throws ProcessingException {
              Cell cell = row.getCellForUpdate(getNameColumn());

              if (StringUtility.isNullOrEmpty(rawValue)) {
                cell.setErrorStatus(TEXTS.get("NoEmptyName"));
              }
              else {
                cell.clearErrorStatus();
              }

              return rawValue;
            }
          }

          @Order(30.0)
          public class LocationColumn extends AbstractStringColumn {

            @Override
            protected boolean getConfiguredEditable() {
              return true;
            }

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Location");
            }

            @Override
            protected int getConfiguredWidth() {
              return 150;
            }

          }

          @Order(40.0)
          public class DateColumn extends AbstractDateColumn {

            @Override
            protected boolean getConfiguredEditable() {
              return true;
            }

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Date");
            }

            @Override
            protected int getConfiguredWidth() {
              return 110;
            }

          }

          @Order(50.0)
          public class IndustryColumn extends AbstractSmartColumn<Long> {

            @Override
            protected boolean getConfiguredEditable() {
              return true;
            }

            @Override
            protected boolean getConfiguredBrowseHierarchy() {
              return true;
            }

            @Override
            protected Class<? extends ICodeType<?, Long>> getConfiguredCodeType() {
              return IndustryICBCodeType.class;
            }

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Industry");
            }

            @Override
            protected int getConfiguredWidth() {
              return 80;
            }

          }

          @Order(70.0)
          public class ParticipantsColumn extends AbstractLongColumn {

            @Override
            protected boolean getConfiguredEditable() {
              return true;
            }

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Participants");
            }

            @Override
            protected int getConfiguredWidth() {
              return 100;
            }

            @Override
            protected Long execValidateValue(ITableRow row, Long rawValue) throws ProcessingException {
              Cell cell = row.getCellForUpdate(this);

              if (NumberUtility.nvl(rawValue, 1) >= 0) {
                cell.clearErrorStatus();
              }
              else {
                cell.setErrorStatus(TEXTS.get("NoNegNumber"));
              }

              return rawValue;
            }
          }

          @Order(80.0)
          public class WebPageColumn extends AbstractStringColumn {

            @Override
            protected boolean getConfiguredEditable() {
              return true;
            }

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("WebPage");
            }

            @Override
            protected boolean getConfiguredVisible() {
              return false;
            }

            @Override
            protected int getConfiguredWidth() {
              return 120;
            }

          }

          @Order(90.0)
          public class AttendedColumn extends AbstractBooleanColumn {

            @Override
            protected boolean getConfiguredEditable() {
              return true;
            }

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Attended");
            }

            @Override
            protected boolean getConfiguredVisible() {
              return true;
            }

            @Override
            protected int getConfiguredWidth() {
              return 120;
            }

          }

          @Order(100.0)
          public class PhoneColumn extends AbstractStringColumn {
            @Override
            protected boolean getConfiguredEditable() {
              return true;
            }

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Phone");
            }

            @Override
            protected void execDecorateCell(Cell cell, ITableRow row) throws ProcessingException {
              cell.setIconId("font:\uE011");//Icons.Phone
            }
          }

          @Order(100.0)
          public class TrendColumn extends AbstractIconColumn {

            @Override
            protected String getConfiguredHeaderText() {
              return TEXTS.get("Trend");
            }

          }

          private void newRow() throws ProcessingException {
            ColumnSet cols = getColumnSet();
            ITableRow row = new TableRow(cols);

            row.getCellForUpdate(getIdColumn()).setValue(++m_maxId);
            row.getCellForUpdate(getNameColumn()).setValue("New Row");

            addRow(row, true);
          }

          @Order(10.0)
          public class NewMenu extends AbstractMenu {

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
            }

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("New");
            }

            @Override
            protected void execAction() throws ProcessingException {
              newRow();
            }
          }

          @Order(15.0)
          public class MoreMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
              return TEXTS.get("More");
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
            }

            @Order(10.0)
            public class NewDelayedMenu extends AbstractMenu {

              @Override
              protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.<IMenuType> hashSet(TableMenuType.EmptySpace);
              }

              @Override
              protected String getConfiguredText() {
                return TEXTS.get("NewDelayed");
              }

              @Override
              protected void execAction() throws ProcessingException {
                ClientJobs.schedule(new IRunnable() {
                  @Override
                  public void run() throws Exception {
                    ModelJobs.schedule(new IRunnable() {
                      @Override
                      public void run() throws Exception {
                        newRow();
                      }
                    });
                  }
                }, 2, TimeUnit.SECONDS);

              }
            }

            @Order(15.0)
            public class ScrollToSelection extends AbstractMenu {
              @Override
              protected String getConfiguredText() {
                return TEXTS.get("ScrollToSelection");
              }

              @Override
              protected void execAction() throws ProcessingException {
                scrollToSelection();
              }
            }
          }

          @Order(20.0)
          public class DeleteMenu extends AbstractMenu {

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.<IMenuType> hashSet(TableMenuType.MultiSelection, TableMenuType.SingleSelection);
            }

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("Delete");
            }

            @Override
            protected void execAction() throws ProcessingException {
              List<ITableRow> rows = getSelectedRows();
              deleteRows(rows);
            }
          }

          @Order(25.0)
          public class DeleteDelayedMenu extends AbstractMenu {

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.<IMenuType> hashSet(TableMenuType.MultiSelection, TableMenuType.SingleSelection);
            }

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("DeleteDelayed");
            }

            @Override
            protected void execAction() throws ProcessingException {
              ClientJobs.schedule(new IRunnable() {
                @Override
                public void run() throws Exception {
                  ModelJobs.schedule(new IRunnable() {
                    @Override
                    public void run() throws Exception {
                      List<ITableRow> rows = getSelectedRows();
                      deleteRows(rows);
                    }
                  });
                }
              }, 2, TimeUnit.SECONDS);
            }
          }

          @Order(30.0)
          public class ToggleRowEnabledMenu extends AbstractMenu {

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.<IMenuType> hashSet(TableMenuType.SingleSelection);
            }

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("ToggleRowEnabled");
            }

            @Override
            protected boolean getConfiguredInheritAccessibility() {
              return false;
            }

            @Override
            protected void execAction() throws ProcessingException {
              getSelectedRow().setEnabled(!getSelectedRow().isEnabled());
            }
          }

          @Order(40.0)
          public class HighlightRow extends AbstractMenu {

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
              return CollectionUtility.<IMenuType> hashSet(TableMenuType.SingleSelection, TableMenuType.MultiSelection);
            }

            @Override
            protected String getConfiguredText() {
              return TEXTS.get("Highlight");
            }

            @Override
            protected boolean getConfiguredInheritAccessibility() {
              return false;
            }

            @Override
            protected void execAction() throws ProcessingException {
              for (ITableRow row : getSelectedRows()) {
                if (row.getCssClass() == null) {
                  row.setCssClass("highlight");
                }
                else {
                  row.setCssClass(null);
                }
              }
            }
          }
        }
      }

      @Order(20.0)
      public class SelectedRowsField extends AbstractStringField {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("SelectedRows");
        }
      }

      @Order(30.0)
      public class InsertedRowsField extends AbstractStringField {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("InsertedRows");
        }
      }

      @Order(40.0)
      public class UpdatedRowsField extends AbstractStringField {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("UpdatedRows");
        }
      }

      @Order(50.0)
      public class DeletedRowsField extends AbstractStringField {

        @Override
        protected boolean getConfiguredEnabled() {
          return false;
        }

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("DeletedRows");
        }
      }

      @Order(60.0)
      public class DefaultIconIdField extends AbstractSmartField<String> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("DefaultIconId");
        }

        @Override
        protected String getConfiguredLabelFont() {
          return "ITALIC";
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return (Class<? extends ILookupCall<String>>) IconIdLookupCall.class;
        }

        @Override
        protected void execChangedValue() throws ProcessingException {
          getTableField().getTable().setDefaultIconId(getValue());

          // setting the default icon id for existing rows doesn't work.
          // that's why we are setting the icon on all table rows individually as well
          for (ITableRow row : getTableField().getTable().getRows()) {
            row.setIconId(getValue());
          }
        }
      }

      @Order(70.0)
      public class PropertiesGroupBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredGridW() {
          return 1;
        }

        @Override
        protected int getConfiguredGridH() {
          return 5;
        }

        @Override
        protected int getConfiguredGridColumnCount() {
          return 2;
        }

        @Override
        protected boolean getConfiguredBorderVisible() {
          return false;
        }

        @Order(80.0)
        public class AutoResizeColumnsField extends AbstractCheckBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("AutoResizeColumns");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          @Override
          protected void execChangedValue() throws ProcessingException {
            getTableField().getTable().setAutoResizeColumns(getValue());
          }

          @Override
          protected void execInitField() throws ProcessingException {
            setValue(getTableField().getTable().isAutoResizeColumns());
          }

        }

        @Order(90.0)
        public class IsVisibleField extends AbstractCheckBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("IsVisible");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected void execChangedValue() throws ProcessingException {
            getLocationColumn().setVisible(getValue());
          }

          @Override
          protected void execInitField() throws ProcessingException {
            setValue(getLocationColumn().isVisible());
          }

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          private IColumn<String> getLocationColumn() {
            ITable table = getTableField().getTable();
            return table.getColumnSet().getColumnByClass(LocationColumn.class);
          }
        }

        @Order(95.0)
        public class IsEnabledField extends AbstractCheckBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("IsEnabled");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          @Override
          protected void execChangedValue() throws ProcessingException {
            getTableField().setEnabled(getValue());
          }

          @Override
          protected void execInitField() throws ProcessingException {
            setValue(getTableField().isEnabled());
          }
        }

        @Order(100.0)
        public class IsEditableField extends AbstractCheckBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("IsEditable");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          @Override
          protected void execChangedValue() throws ProcessingException {
            for (IColumn<?> column : getTableField().getTable().getColumns()) {
              column.setEditable(getValue());
            }
          }

          @Override
          protected void execInitField() throws ProcessingException {
            setValue(true);
          }
        }

        @Order(110.0)
        public class MultiSelectField extends AbstractCheckBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("MultiSelect");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          @Override
          protected void execChangedValue() throws ProcessingException {
            getTableField().getTable().setMultiSelect(getValue());
          }

          @Override
          protected void execInitField() throws ProcessingException {
            setValue(getTableField().getTable().isMultiSelect());
            setValue(getTableField().getTable().isHeaderVisible());
          }
        }

        @Order(120.0)
        public class IsCheckableField extends AbstractCheckBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("IsCheckable");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          @Override
          protected void execChangedValue() throws ProcessingException {
            getTableField().getTable().setCheckable(getValue());
          }

          @Override
          protected void execInitField() throws ProcessingException {
            setValue(getTableField().getTable().isCheckable());
          }

        }

        @Order(122.0)
        public class IsRowIconVisibleField extends AbstractCheckBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("RowIconVisible");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          @Override
          protected void execChangedValue() throws ProcessingException {
            getTableField().getTable().setRowIconVisible(getValue());
          }

          @Override
          protected void execInitField() throws ProcessingException {
            setValue(getTableField().getTable().isRowIconVisible());
          }

        }

        @Order(125.0)
        public class WrapTextField extends AbstractCheckBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("WrapText");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          @Override
          protected void execChangedValue() throws ProcessingException {
            getTableField().getTable().getNameColumn().setTextWrap(getValue());
          }

          @Override
          protected void execInitField() throws ProcessingException {
            setValue(getTableField().getTable().getNameColumn().isTextWrap());
          }

        }

        @Order(130.0)
        public class TableStatusVisibleField extends AbstractBooleanField {

          @Override
          protected boolean getConfiguredEnabled() {
            return false;
          }

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("TableStatusVisible");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          @Override
          protected String getConfiguredTooltipText() {
            return TEXTS.get("ThisPropertyCannotBeChangedAtRuntime");
          }

          @Override
          protected void execInitField() throws ProcessingException {
            setValue(true);
            getTableField().setTableStatusVisible(true);
          }
        }

        @Order(140.0)
        public class TableHeaderVisibleField extends AbstractCheckBox {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("TableHeaderVisible");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          @Override
          protected void execChangedValue() throws ProcessingException {
            getTableField().getTable().setHeaderVisible(getValue());
          }

          @Override
          protected void execInitField() throws ProcessingException {
            setValue(getTableField().getTable().isHeaderVisible());
          }
        }

        @Order(150.0)
        public class ToggleHorizontalAlignmentField extends AbstractLinkButton {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("ToggleHorizontalAlignment");
          }

          @Override
          protected boolean getConfiguredProcessButton() {
            return false;
          }

          @Override
          protected String getConfiguredFont() {
            return "ITALIC";
          }

          @Override
          protected void execClickAction() throws ProcessingException {
            for (IColumn column : getTableField().getTable().getColumns()) {
              int newAlignment = column.getHorizontalAlignment() + 1;
              if (newAlignment > 1) {
                newAlignment = -1;
              }
              column.setHorizontalAlignment(newAlignment);
            }
          }

        }
      }
    }

    @Order(40.0)
    public class SampleContentButton extends AbstractButton {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("SampleContent");
      }

      @Override
      protected void execClickAction() throws ProcessingException {
        getTableField().addExampleRows();
      }
    }

    @Order(50.0)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }
}
