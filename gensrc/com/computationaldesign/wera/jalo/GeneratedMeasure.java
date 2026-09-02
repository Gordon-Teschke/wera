/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 29.07.2026 15:42:09                         ---
 * ----------------------------------------------------------------
 */
package com.computationaldesign.wera.jalo;

import com.computationaldesign.wera.constants.WeraConstants;
import com.computationaldesign.wera.jalo.WeraVariante;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Generated class for type {@link com.computationaldesign.wera.jalo.Measure Measure}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedMeasure extends GenericItem
{
	/** Qualifier of the <code>Measure.variant2measure</code> attribute **/
	public static final String VARIANT2MEASURE = "variant2measure".intern();
	/** Qualifier of the <code>Measure.measure_unit</code> attribute **/
	public static final String MEASURE_UNIT = "measure_unit".intern();
	/** Qualifier of the <code>Measure.measure_amount</code> attribute **/
	public static final String MEASURE_AMOUNT = "measure_amount".intern();
	/** Qualifier of the <code>Measure.measure_stichmass</code> attribute **/
	public static final String MEASURE_STICHMASS = "measure_stichmass".intern();
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Measure.measure_amount</code> attribute.
	 * @return the measure_amount
	 */
	public String getMeasure_amount(final SessionContext ctx)
	{
		return (String)getProperty( ctx, MEASURE_AMOUNT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Measure.measure_amount</code> attribute.
	 * @return the measure_amount
	 */
	public String getMeasure_amount()
	{
		return getMeasure_amount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Measure.measure_amount</code> attribute. 
	 * @param value the measure_amount
	 */
	public void setMeasure_amount(final SessionContext ctx, final String value)
	{
		setProperty(ctx, MEASURE_AMOUNT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Measure.measure_amount</code> attribute. 
	 * @param value the measure_amount
	 */
	public void setMeasure_amount(final String value)
	{
		setMeasure_amount( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Measure.measure_stichmass</code> attribute.
	 * @return the measure_stichmass
	 */
	public String getMeasure_stichmass(final SessionContext ctx)
	{
		return (String)getProperty( ctx, MEASURE_STICHMASS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Measure.measure_stichmass</code> attribute.
	 * @return the measure_stichmass
	 */
	public String getMeasure_stichmass()
	{
		return getMeasure_stichmass( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Measure.measure_stichmass</code> attribute. 
	 * @param value the measure_stichmass
	 */
	public void setMeasure_stichmass(final SessionContext ctx, final String value)
	{
		setProperty(ctx, MEASURE_STICHMASS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Measure.measure_stichmass</code> attribute. 
	 * @param value the measure_stichmass
	 */
	public void setMeasure_stichmass(final String value)
	{
		setMeasure_stichmass( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Measure.measure_unit</code> attribute.
	 * @return the measure_unit
	 */
	public EnumerationValue getMeasure_unit(final SessionContext ctx)
	{
		return (EnumerationValue)getProperty( ctx, MEASURE_UNIT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Measure.measure_unit</code> attribute.
	 * @return the measure_unit
	 */
	public EnumerationValue getMeasure_unit()
	{
		return getMeasure_unit( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Measure.measure_unit</code> attribute. 
	 * @param value the measure_unit
	 */
	public void setMeasure_unit(final SessionContext ctx, final EnumerationValue value)
	{
		setProperty(ctx, MEASURE_UNIT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Measure.measure_unit</code> attribute. 
	 * @param value the measure_unit
	 */
	public void setMeasure_unit(final EnumerationValue value)
	{
		setMeasure_unit( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Measure.variant2measure</code> attribute.
	 * @return the variant2measure
	 */
	public Collection<WeraVariante> getVariant2measure(final SessionContext ctx)
	{
		final List<WeraVariante> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.VARIANTEMEASURERELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Measure.variant2measure</code> attribute.
	 * @return the variant2measure
	 */
	public Collection<WeraVariante> getVariant2measure()
	{
		return getVariant2measure( getSession().getSessionContext() );
	}
	
	public long getVariant2measureCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.VARIANTEMEASURERELATION,
			null
		);
	}
	
	public long getVariant2measureCount()
	{
		return getVariant2measureCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Measure.variant2measure</code> attribute. 
	 * @param value the variant2measure
	 */
	public void setVariant2measure(final SessionContext ctx, final Collection<WeraVariante> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.VARIANTEMEASURERELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Measure.variant2measure</code> attribute. 
	 * @param value the variant2measure
	 */
	public void setVariant2measure(final Collection<WeraVariante> value)
	{
		setVariant2measure( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to variant2measure. 
	 * @param value the item to add to variant2measure
	 */
	public void addToVariant2measure(final SessionContext ctx, final WeraVariante value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.VARIANTEMEASURERELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to variant2measure. 
	 * @param value the item to add to variant2measure
	 */
	public void addToVariant2measure(final WeraVariante value)
	{
		addToVariant2measure( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from variant2measure. 
	 * @param value the item to remove from variant2measure
	 */
	public void removeFromVariant2measure(final SessionContext ctx, final WeraVariante value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.VARIANTEMEASURERELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from variant2measure. 
	 * @param value the item to remove from variant2measure
	 */
	public void removeFromVariant2measure(final WeraVariante value)
	{
		removeFromVariant2measure( getSession().getSessionContext(), value );
	}
	
}
