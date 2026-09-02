/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 29.07.2026 15:42:09                         ---
 * ----------------------------------------------------------------
 */
package com.computationaldesign.wera.jalo;

import com.computationaldesign.wera.constants.WeraConstants;
import com.computationaldesign.wera.jalo.WeraVariante;
import de.hybris.platform.jalo.SessionContext;

/**
 * Generated class for type {@link com.computationaldesign.wera.jalo.WeraVarianteSet WeraVarianteSet}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedWeraVarianteSet extends WeraVariante
{
	/** Qualifier of the <code>WeraVarianteSet.productdescription</code> attribute **/
	public static final String PRODUCTDESCRIPTION = "productdescription".intern();
	/** Qualifier of the <code>WeraVarianteSet.weravariants</code> attribute **/
	public static final String WERAVARIANTS = "weravariants".intern();
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraVarianteSet.productdescription</code> attribute.
	 * @return the productdescription - Produktbeschreibung
	 */
	public abstract String getProductdescription(final SessionContext ctx);
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraVarianteSet.productdescription</code> attribute.
	 * @return the productdescription - Produktbeschreibung
	 */
	public String getProductdescription()
	{
		return getProductdescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraVarianteSet.weravariants</code> attribute.
	 * @return the weravariants - WERAPRODUKT
	 */
	public WeraVariante getWeravariants(final SessionContext ctx)
	{
		return (WeraVariante)getProperty( ctx, WERAVARIANTS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraVarianteSet.weravariants</code> attribute.
	 * @return the weravariants - WERAPRODUKT
	 */
	public WeraVariante getWeravariants()
	{
		return getWeravariants( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraVarianteSet.weravariants</code> attribute. 
	 * @param value the weravariants - WERAPRODUKT
	 */
	public void setWeravariants(final SessionContext ctx, final WeraVariante value)
	{
		setProperty(ctx, WERAVARIANTS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraVarianteSet.weravariants</code> attribute. 
	 * @param value the weravariants - WERAPRODUKT
	 */
	public void setWeravariants(final WeraVariante value)
	{
		setWeravariants( getSession().getSessionContext(), value );
	}
	
}
