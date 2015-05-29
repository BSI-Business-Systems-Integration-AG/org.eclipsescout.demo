/**
 *
 */
package org.eclipsescout.contacts.client.premium.ui.desktop.outlines;

import java.util.Set;

import org.eclipse.scout.commons.CollectionUtility;
import org.eclipse.scout.commons.annotations.FormData;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.annotations.PageData;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.extension.client.ui.action.menu.AbstractExtensibleMenu;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.shared.AbstractIcons;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.service.SERVICES;
import org.eclipsescout.contacts.client.premium.ui.desktop.outlines.EventsTablePage.Table;
import org.eclipsescout.contacts.client.premium.ui.forms.EventForm;
import org.eclipsescout.contacts.shared.premium.services.IEventManagementOutlineService;
import org.eclipsescout.contacts.shared.premium.ui.desktop.outlines.EventsTablePageData;

/**
 * @author mzi
 */
@PageData(EventsTablePageData.class)
public class EventsTablePage extends AbstractPageWithTable<Table> {

  private String m_personId;

  @Override
  protected String getConfiguredIconId() {
    return AbstractIcons.Bookmark;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Events");
  }

  @Override
  protected void execLoadData(SearchFilter filter) throws ProcessingException {
    importPageData(SERVICES.getService(IEventManagementOutlineService.class).getEventsTableData(filter, getPersonId()));
  }

  @Order(1000.0)
  public class Table extends AbstractExtensibleTable {

    /**
     * @return the TitleColumn
     */
    public TitleColumn getTitleColumn() {
      return getColumnSet().getColumnByClass(TitleColumn.class);
    }

    /**
     * @return the StartsColumn
     */
    public StartsColumn getStartsColumn() {
      return getColumnSet().getColumnByClass(StartsColumn.class);
    }

    /**
     * @return the CountryColumn
     */
    public CountryColumn getCountryColumn() {
      return getColumnSet().getColumnByClass(CountryColumn.class);
    }

    /**
     * @return the HomepageColumn
     */
    public HomepageColumn getHomepageColumn() {
      return getColumnSet().getColumnByClass(HomepageColumn.class);
    }

    @Override
    protected String getConfiguredDefaultIconId() {
      return AbstractIcons.Bookmark;
    }

    @Override
    protected void execRowAction(ITableRow row) throws ProcessingException {
      getMenu(EditMenu.class).doAction();
    }

    /**
     * @return the CityColumn
     */
    public CityColumn getCityColumn() {
      return getColumnSet().getColumnByClass(CityColumn.class);
    }

    /**
     * @return the EndsColumn
     */
    public EndsColumn getEndsColumn() {
      return getColumnSet().getColumnByClass(EndsColumn.class);
    }

    /**
     * @return the EventIdColumn
     */
    public EventIdColumn getEventIdColumn() {
      return getColumnSet().getColumnByClass(EventIdColumn.class);
    }

    @Order(1000.0)
    public class EventIdColumn extends AbstractStringColumn {

      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }

      @Override
      protected boolean getConfiguredPrimaryKey() {
        return true;
      }
    }

    @Order(2000.0)
    public class TitleColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Title");
      }
    }

    @Order(3000.0)
    public class StartsColumn extends AbstractDateTimeColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Starts");
      }
    }

    @Order(4000.0)
    public class EndsColumn extends AbstractDateTimeColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Ends");
      }
    }

    @Order(5000.0)
    public class CityColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("City");
      }
    }

    @Order(6000.0)
    public class CountryColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Country");
      }
    }

    @Order(7000.0)
    public class HomepageColumn extends AbstractStringColumn {

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Homepage");
      }
    }

    @Order(1000.0)
    public class EditMenu extends AbstractExtensibleMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Edit");
      }

      @Override
      protected void execAction() throws ProcessingException {
        EventForm form = new EventForm();
        form.setEventId(getEventIdColumn().getSelectedValue());
        form.startModify();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }

    @Order(2000.0)
    public class NewMenu extends AbstractExtensibleMenu {

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
        EventForm form = new EventForm();
        form.startNew();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }
  }

  /**
   * @return the PersonId
   */
  @FormData
  public String getPersonId() {
    return m_personId;
  }

  /**
   * @param personId
   *          the PersonId to set
   */
  @FormData
  public void setPersonId(String personId) {
    m_personId = personId;
  }
}
