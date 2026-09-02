/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 29.07.2026 15:42:09                         ---
 * ----------------------------------------------------------------
 */
package com.computationaldesign.wera.jalo;

import com.computationaldesign.wera.constants.WeraConstants;
import com.computationaldesign.wera.jalo.WeraProductSet;
import com.computationaldesign.wera.jalo.WeraProductSetinSet;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Generated class for type {@link com.computationaldesign.wera.jalo.WeraProductSetVariants WeraProductSetVariants}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedWeraProductSetVariants extends GenericItem
{
	/** Qualifier of the <code>WeraProductSetVariants.order</code> attribute **/
	public static final String ORDER = "order".intern();
	/** Qualifier of the <code>WeraProductSetVariants.view_valid_from</code> attribute **/
	public static final String VIEW_VALID_FROM = "view_valid_from".intern();
	/** Qualifier of the <code>WeraProductSetVariants.weraproductsets_qual</code> attribute **/
	public static final String WERAPRODUCTSETS_QUAL = "weraproductsets_qual".intern();
	/** Qualifier of the <code>WeraProductSetVariants.view_valid_to</code> attribute **/
	public static final String VIEW_VALID_TO = "view_valid_to".intern();
	/** Qualifier of the <code>WeraProductSetVariants.weraproductsets</code> attribute **/
	public static final String WERAPRODUCTSETS = "weraproductsets".intern();
	/** Qualifier of the <code>WeraProductSetVariants.vpe</code> attribute **/
	public static final String VPE = "vpe".intern();
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetVariants.order</code> attribute.
	 * @return the order - Order
	 */
	public Integer getOrder(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, ORDER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetVariants.order</code> attribute.
	 * @return the order - Order
	 */
	public Integer getOrder()
	{
		return getOrder( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetVariants.order</code> attribute. 
	 * @return the order - Order
	 */
	public int getOrderAsPrimitive(final SessionContext ctx)
	{
		Integer value = getOrder( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetVariants.order</code> attribute. 
	 * @return the order - Order
	 */
	public int getOrderAsPrimitive()
	{
		return getOrderAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetVariants.order</code> attribute. 
	 * @param value the order - Order
	 */
	public void setOrder(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, ORDER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetVariants.order</code> attribute. 
	 * @param value the order - Order
	 */
	public void setOrder(final Integer value)
	{
		setOrder( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetVariants.order</code> attribute. 
	 * @param value the order - Order
	 */
	public void setOrder(final SessionContext ctx, final int value)
	{
		setOrder( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetVariants.order</code> attribute. 
	 * @param value the order - Order
	 */
	public void setOrder(final int value)
	{
		setOrder( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetVariants.view_valid_from</code> attribute.
	 * @return the view_valid_from - Gültig ab vom Set
	 */
	public abstract String getView_valid_from(final SessionContext ctx);
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetVariants.view_valid_from</code> attribute.
	 * @return the view_valid_from - Gültig ab vom Set
	 */
	public String getView_valid_from()
	{
		return getView_valid_from( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetVariants.view_valid_to</code> attribute.
	 * @return the view_valid_to - Gültig bis vom Set
	 */
	public abstract String getView_valid_to(final SessionContext ctx);
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetVariants.view_valid_to</code> attribute.
	 * @return the view_valid_to - Gültig bis vom Set
	 */
	public String getView_valid_to()
	{
		return getView_valid_to( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetVariants.vpe</code> attribute.
	 * @return the vpe - vpe
	 */
	public Integer getVpe(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, VPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetVariants.vpe</code> attribute.
	 * @return the vpe - vpe
	 */
	public Integer getVpe()
	{
		return getVpe( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetVariants.vpe</code> attribute. 
	 * @return the vpe - vpe
	 */
	public int getVpeAsPrimitive(final SessionContext ctx)
	{
		Integer value = getVpe( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetVariants.vpe</code> attribute. 
	 * @return the vpe - vpe
	 */
	public int getVpeAsPrimitive()
	{
		return getVpeAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetVariants.vpe</code> attribute. 
	 * @param value the vpe - vpe
	 */
	public void setVpe(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, VPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetVariants.vpe</code> attribute. 
	 * @param value the vpe - vpe
	 */
	public void setVpe(final Integer value)
	{
		setVpe( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetVariants.vpe</code> attribute. 
	 * @param value the vpe - vpe
	 */
	public void setVpe(final SessionContext ctx, final int value)
	{
		setVpe( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetVariants.vpe</code> attribute. 
	 * @param value the vpe - vpe
	 */
	public void setVpe(final int value)
	{
		setVpe( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetVariants.weraproductsets</code> attribute.
	 * @return the weraproductsets - WERAPRODUKTSET
	 */
	public WeraProductSet getWeraproductsets(final SessionContext ctx)
	{
		return (WeraProductSet)getProperty( ctx, WERAPRODUCTSETS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetVariants.weraproductsets</code> attribute.
	 * @return the weraproductsets - WERAPRODUKTSET
	 */
	public WeraProductSet getWeraproductsets()
	{
		return getWeraproductsets( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetVariants.weraproductsets</code> attribute. 
	 * @param value the weraproductsets - WERAPRODUKTSET
	 */
	public void setWeraproductsets(final SessionContext ctx, final WeraProductSet value)
	{
		setProperty(ctx, WERAPRODUCTSETS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetVariants.weraproductsets</code> attribute. 
	 * @param value the weraproductsets - WERAPRODUKTSET
	 */
	public void setWeraproductsets(final WeraProductSet value)
	{
		setWeraproductsets( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetVariants.weraproductsets_qual</code> attribute.
	 * @return the weraproductsets_qual
	 */
	public Collection<WeraProductSetinSet> getWeraproductsets_qual(final SessionContext ctx)
	{
		final List<WeraProductSetinSet> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTSET2PRODUCTSETINSETRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetVariants.weraproductsets_qual</code> attribute.
	 * @return the weraproductsets_qual
	 */
	public Collection<WeraProductSetinSet> getWeraproductsets_qual()
	{
		return getWeraproductsets_qual( getSession().getSessionContext() );
	}
	
	public long getWeraproductsets_qualCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.PRODUCTSET2PRODUCTSETINSETRELATION,
			null
		);
	}
	
	public long getWeraproductsets_qualCount()
	{
		return getWeraproductsets_qualCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetVariants.weraproductsets_qual</code> attribute. 
	 * @param value the weraproductsets_qual
	 */
	public void setWeraproductsets_qual(final SessionContext ctx, final Collection<WeraProductSetinSet> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTSET2PRODUCTSETINSETRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetVariants.weraproductsets_qual</code> attribute. 
	 * @param value the weraproductsets_qual
	 */
	public void setWeraproductsets_qual(final Collection<WeraProductSetinSet> value)
	{
		setWeraproductsets_qual( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weraproductsets_qual. 
	 * @param value the item to add to weraproductsets_qual
	 */
	public void addToWeraproductsets_qual(final SessionContext ctx, final WeraProductSetinSet value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTSET2PRODUCTSETINSETRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weraproductsets_qual. 
	 * @param value the item to add to weraproductsets_qual
	 */
	public void addToWeraproductsets_qual(final WeraProductSetinSet value)
	{
		addToWeraproductsets_qual( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weraproductsets_qual. 
	 * @param value the item to remove from weraproductsets_qual
	 */
	public void removeFromWeraproductsets_qual(final SessionContext ctx, final WeraProductSetinSet value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTSET2PRODUCTSETINSETRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weraproductsets_qual. 
	 * @param value the item to remove from weraproductsets_qual
	 */
	public void removeFromWeraproductsets_qual(final WeraProductSetinSet value)
	{
		removeFromWeraproductsets_qual( getSession().getSessionContext(), value );
	}
	
}
