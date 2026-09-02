/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 29.07.2026 15:42:09                         ---
 * ----------------------------------------------------------------
 */
package com.computationaldesign.wera.jalo;

import com.computationaldesign.wera.constants.WeraConstants;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttributeValue;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link com.computationaldesign.wera.jalo.Farbitem Farbitem}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedFarbitem extends GenericItem
{
	/** Qualifier of the <code>Farbitem.classificationattributevaluespref</code> attribute **/
	public static final String CLASSIFICATIONATTRIBUTEVALUESPREF = "classificationattributevaluespref".intern();
	/** Qualifier of the <code>Farbitem.farbcode_name</code> attribute **/
	public static final String FARBCODE_NAME = "farbcode_name".intern();
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Farbitem.classificationattributevaluespref</code> attribute.
	 * @return the classificationattributevaluespref
	 */
	public Collection<ClassificationAttributeValue> getClassificationattributevaluespref(final SessionContext ctx)
	{
		final List<ClassificationAttributeValue> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.FARBITEMCLASSIFICATIONATTRIBUTEVALUERELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Farbitem.classificationattributevaluespref</code> attribute.
	 * @return the classificationattributevaluespref
	 */
	public Collection<ClassificationAttributeValue> getClassificationattributevaluespref()
	{
		return getClassificationattributevaluespref( getSession().getSessionContext() );
	}
	
	public long getClassificationattributevaluesprefCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.FARBITEMCLASSIFICATIONATTRIBUTEVALUERELATION,
			null
		);
	}
	
	public long getClassificationattributevaluesprefCount()
	{
		return getClassificationattributevaluesprefCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Farbitem.classificationattributevaluespref</code> attribute. 
	 * @param value the classificationattributevaluespref
	 */
	public void setClassificationattributevaluespref(final SessionContext ctx, final Collection<ClassificationAttributeValue> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.FARBITEMCLASSIFICATIONATTRIBUTEVALUERELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Farbitem.classificationattributevaluespref</code> attribute. 
	 * @param value the classificationattributevaluespref
	 */
	public void setClassificationattributevaluespref(final Collection<ClassificationAttributeValue> value)
	{
		setClassificationattributevaluespref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to classificationattributevaluespref. 
	 * @param value the item to add to classificationattributevaluespref
	 */
	public void addToClassificationattributevaluespref(final SessionContext ctx, final ClassificationAttributeValue value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.FARBITEMCLASSIFICATIONATTRIBUTEVALUERELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to classificationattributevaluespref. 
	 * @param value the item to add to classificationattributevaluespref
	 */
	public void addToClassificationattributevaluespref(final ClassificationAttributeValue value)
	{
		addToClassificationattributevaluespref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from classificationattributevaluespref. 
	 * @param value the item to remove from classificationattributevaluespref
	 */
	public void removeFromClassificationattributevaluespref(final SessionContext ctx, final ClassificationAttributeValue value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.FARBITEMCLASSIFICATIONATTRIBUTEVALUERELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from classificationattributevaluespref. 
	 * @param value the item to remove from classificationattributevaluespref
	 */
	public void removeFromClassificationattributevaluespref(final ClassificationAttributeValue value)
	{
		removeFromClassificationattributevaluespref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Farbitem.farbcode_name</code> attribute.
	 * @return the farbcode_name
	 */
	public String getFarbcode_name(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedFarbitem.getFarbcode_name requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, FARBCODE_NAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Farbitem.farbcode_name</code> attribute.
	 * @return the farbcode_name
	 */
	public String getFarbcode_name()
	{
		return getFarbcode_name( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Farbitem.farbcode_name</code> attribute. 
	 * @return the localized farbcode_name
	 */
	public Map<Language,String> getAllFarbcode_name(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,FARBCODE_NAME,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Farbitem.farbcode_name</code> attribute. 
	 * @return the localized farbcode_name
	 */
	public Map<Language,String> getAllFarbcode_name()
	{
		return getAllFarbcode_name( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Farbitem.farbcode_name</code> attribute. 
	 * @param value the farbcode_name
	 */
	public void setFarbcode_name(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedFarbitem.setFarbcode_name requires a session language", 0 );
		}
		setLocalizedProperty(ctx, FARBCODE_NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Farbitem.farbcode_name</code> attribute. 
	 * @param value the farbcode_name
	 */
	public void setFarbcode_name(final String value)
	{
		setFarbcode_name( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Farbitem.farbcode_name</code> attribute. 
	 * @param value the farbcode_name
	 */
	public void setAllFarbcode_name(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,FARBCODE_NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Farbitem.farbcode_name</code> attribute. 
	 * @param value the farbcode_name
	 */
	public void setAllFarbcode_name(final Map<Language,String> value)
	{
		setAllFarbcode_name( getSession().getSessionContext(), value );
	}
	
}
