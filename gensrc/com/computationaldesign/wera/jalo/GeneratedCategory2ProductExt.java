/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 29.07.2026 15:42:09                         ---
 * ----------------------------------------------------------------
 */
package com.computationaldesign.wera.jalo;

import com.computationaldesign.wera.constants.WeraConstants;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.SessionContext;

/**
 * Generated class for type {@link com.computationaldesign.wera.jalo.Category2ProductExt Category2ProductExt}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedCategory2ProductExt extends GenericItem
{
	/** Qualifier of the <code>Category2ProductExt.catalogversion_desc</code> attribute **/
	public static final String CATALOGVERSION_DESC = "catalogversion_desc".intern();
	/** Qualifier of the <code>Category2ProductExt.featureicon_exclude_list</code> attribute **/
	public static final String FEATUREICON_EXCLUDE_LIST = "featureicon_exclude_list".intern();
	/** Qualifier of the <code>Category2ProductExt.priority</code> attribute **/
	public static final String PRIORITY = "priority".intern();
	/** Qualifier of the <code>Category2ProductExt.pagenr_catalog</code> attribute **/
	public static final String PAGENR_CATALOG = "pagenr_catalog".intern();
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category2ProductExt.catalogversion_desc</code> attribute.
	 * @return the catalogversion_desc - Katalogversion
	 */
	public abstract String getCatalogversion_desc(final SessionContext ctx);
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category2ProductExt.catalogversion_desc</code> attribute.
	 * @return the catalogversion_desc - Katalogversion
	 */
	public String getCatalogversion_desc()
	{
		return getCatalogversion_desc( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category2ProductExt.featureicon_exclude_list</code> attribute.
	 * @return the featureicon_exclude_list - Feature Icons nicht anzeigen(Komma getrennte Liste)
	 */
	public String getFeatureicon_exclude_list(final SessionContext ctx)
	{
		return (String)getProperty( ctx, FEATUREICON_EXCLUDE_LIST);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category2ProductExt.featureicon_exclude_list</code> attribute.
	 * @return the featureicon_exclude_list - Feature Icons nicht anzeigen(Komma getrennte Liste)
	 */
	public String getFeatureicon_exclude_list()
	{
		return getFeatureicon_exclude_list( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category2ProductExt.featureicon_exclude_list</code> attribute. 
	 * @param value the featureicon_exclude_list - Feature Icons nicht anzeigen(Komma getrennte Liste)
	 */
	public void setFeatureicon_exclude_list(final SessionContext ctx, final String value)
	{
		setProperty(ctx, FEATUREICON_EXCLUDE_LIST,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category2ProductExt.featureicon_exclude_list</code> attribute. 
	 * @param value the featureicon_exclude_list - Feature Icons nicht anzeigen(Komma getrennte Liste)
	 */
	public void setFeatureicon_exclude_list(final String value)
	{
		setFeatureicon_exclude_list( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category2ProductExt.pagenr_catalog</code> attribute.
	 * @return the pagenr_catalog - Katalogseite
	 */
	public String getPagenr_catalog(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PAGENR_CATALOG);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category2ProductExt.pagenr_catalog</code> attribute.
	 * @return the pagenr_catalog - Katalogseite
	 */
	public String getPagenr_catalog()
	{
		return getPagenr_catalog( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category2ProductExt.pagenr_catalog</code> attribute. 
	 * @param value the pagenr_catalog - Katalogseite
	 */
	public void setPagenr_catalog(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PAGENR_CATALOG,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category2ProductExt.pagenr_catalog</code> attribute. 
	 * @param value the pagenr_catalog - Katalogseite
	 */
	public void setPagenr_catalog(final String value)
	{
		setPagenr_catalog( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category2ProductExt.priority</code> attribute.
	 * @return the priority - Reihenfolge
	 */
	public String getPriority(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PRIORITY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category2ProductExt.priority</code> attribute.
	 * @return the priority - Reihenfolge
	 */
	public String getPriority()
	{
		return getPriority( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category2ProductExt.priority</code> attribute. 
	 * @param value the priority - Reihenfolge
	 */
	public void setPriority(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PRIORITY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category2ProductExt.priority</code> attribute. 
	 * @param value the priority - Reihenfolge
	 */
	public void setPriority(final String value)
	{
		setPriority( getSession().getSessionContext(), value );
	}
	
}
