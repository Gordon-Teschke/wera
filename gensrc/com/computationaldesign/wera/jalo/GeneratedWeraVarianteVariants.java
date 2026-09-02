/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 29.07.2026 15:42:09                         ---
 * ----------------------------------------------------------------
 */
package com.computationaldesign.wera.jalo;

import com.computationaldesign.wera.constants.WeraConstants;
import com.computationaldesign.wera.jalo.WeraProductSetinSet;
import com.computationaldesign.wera.jalo.WeraVariante;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Generated class for type {@link com.computationaldesign.wera.jalo.WeraVarianteVariants WeraVarianteVariants}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedWeraVarianteVariants extends GenericItem
{
	/** Qualifier of the <code>WeraVarianteVariants.weravariantsets_qual</code> attribute **/
	public static final String WERAVARIANTSETS_QUAL = "weravariantsets_qual".intern();
	/** Qualifier of the <code>WeraVarianteVariants.weravariants</code> attribute **/
	public static final String WERAVARIANTS = "weravariants".intern();
	/** Qualifier of the <code>WeraVarianteVariants.vpe</code> attribute **/
	public static final String VPE = "vpe".intern();
	/** Qualifier of the <code>WeraVarianteVariants.order</code> attribute **/
	public static final String ORDER = "order".intern();
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraVarianteVariants.order</code> attribute.
	 * @return the order - Order
	 */
	public Integer getOrder(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, ORDER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraVarianteVariants.order</code> attribute.
	 * @return the order - Order
	 */
	public Integer getOrder()
	{
		return getOrder( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraVarianteVariants.order</code> attribute. 
	 * @return the order - Order
	 */
	public int getOrderAsPrimitive(final SessionContext ctx)
	{
		Integer value = getOrder( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraVarianteVariants.order</code> attribute. 
	 * @return the order - Order
	 */
	public int getOrderAsPrimitive()
	{
		return getOrderAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraVarianteVariants.order</code> attribute. 
	 * @param value the order - Order
	 */
	public void setOrder(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, ORDER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraVarianteVariants.order</code> attribute. 
	 * @param value the order - Order
	 */
	public void setOrder(final Integer value)
	{
		setOrder( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraVarianteVariants.order</code> attribute. 
	 * @param value the order - Order
	 */
	public void setOrder(final SessionContext ctx, final int value)
	{
		setOrder( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraVarianteVariants.order</code> attribute. 
	 * @param value the order - Order
	 */
	public void setOrder(final int value)
	{
		setOrder( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraVarianteVariants.vpe</code> attribute.
	 * @return the vpe - vpe
	 */
	public Integer getVpe(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, VPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraVarianteVariants.vpe</code> attribute.
	 * @return the vpe - vpe
	 */
	public Integer getVpe()
	{
		return getVpe( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraVarianteVariants.vpe</code> attribute. 
	 * @return the vpe - vpe
	 */
	public int getVpeAsPrimitive(final SessionContext ctx)
	{
		Integer value = getVpe( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraVarianteVariants.vpe</code> attribute. 
	 * @return the vpe - vpe
	 */
	public int getVpeAsPrimitive()
	{
		return getVpeAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraVarianteVariants.vpe</code> attribute. 
	 * @param value the vpe - vpe
	 */
	public void setVpe(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, VPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraVarianteVariants.vpe</code> attribute. 
	 * @param value the vpe - vpe
	 */
	public void setVpe(final Integer value)
	{
		setVpe( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraVarianteVariants.vpe</code> attribute. 
	 * @param value the vpe - vpe
	 */
	public void setVpe(final SessionContext ctx, final int value)
	{
		setVpe( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraVarianteVariants.vpe</code> attribute. 
	 * @param value the vpe - vpe
	 */
	public void setVpe(final int value)
	{
		setVpe( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraVarianteVariants.weravariants</code> attribute.
	 * @return the weravariants - WERAVARIANTE
	 */
	public WeraVariante getWeravariants(final SessionContext ctx)
	{
		return (WeraVariante)getProperty( ctx, WERAVARIANTS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraVarianteVariants.weravariants</code> attribute.
	 * @return the weravariants - WERAVARIANTE
	 */
	public WeraVariante getWeravariants()
	{
		return getWeravariants( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraVarianteVariants.weravariants</code> attribute. 
	 * @param value the weravariants - WERAVARIANTE
	 */
	public void setWeravariants(final SessionContext ctx, final WeraVariante value)
	{
		setProperty(ctx, WERAVARIANTS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraVarianteVariants.weravariants</code> attribute. 
	 * @param value the weravariants - WERAVARIANTE
	 */
	public void setWeravariants(final WeraVariante value)
	{
		setWeravariants( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraVarianteVariants.weravariantsets_qual</code> attribute.
	 * @return the weravariantsets_qual
	 */
	public Collection<WeraProductSetinSet> getWeravariantsets_qual(final SessionContext ctx)
	{
		final List<WeraProductSetinSet> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTSET2VARIANTESETINSETRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraVarianteVariants.weravariantsets_qual</code> attribute.
	 * @return the weravariantsets_qual
	 */
	public Collection<WeraProductSetinSet> getWeravariantsets_qual()
	{
		return getWeravariantsets_qual( getSession().getSessionContext() );
	}
	
	public long getWeravariantsets_qualCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.PRODUCTSET2VARIANTESETINSETRELATION,
			null
		);
	}
	
	public long getWeravariantsets_qualCount()
	{
		return getWeravariantsets_qualCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraVarianteVariants.weravariantsets_qual</code> attribute. 
	 * @param value the weravariantsets_qual
	 */
	public void setWeravariantsets_qual(final SessionContext ctx, final Collection<WeraProductSetinSet> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTSET2VARIANTESETINSETRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraVarianteVariants.weravariantsets_qual</code> attribute. 
	 * @param value the weravariantsets_qual
	 */
	public void setWeravariantsets_qual(final Collection<WeraProductSetinSet> value)
	{
		setWeravariantsets_qual( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weravariantsets_qual. 
	 * @param value the item to add to weravariantsets_qual
	 */
	public void addToWeravariantsets_qual(final SessionContext ctx, final WeraProductSetinSet value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTSET2VARIANTESETINSETRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weravariantsets_qual. 
	 * @param value the item to add to weravariantsets_qual
	 */
	public void addToWeravariantsets_qual(final WeraProductSetinSet value)
	{
		addToWeravariantsets_qual( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weravariantsets_qual. 
	 * @param value the item to remove from weravariantsets_qual
	 */
	public void removeFromWeravariantsets_qual(final SessionContext ctx, final WeraProductSetinSet value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTSET2VARIANTESETINSETRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weravariantsets_qual. 
	 * @param value the item to remove from weravariantsets_qual
	 */
	public void removeFromWeravariantsets_qual(final WeraProductSetinSet value)
	{
		removeFromWeravariantsets_qual( getSession().getSessionContext(), value );
	}
	
}
