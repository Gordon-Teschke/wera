/*
 * [y] hybris jakarta
 *
 * Copyright (c) 2000-2005 hybris
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information").  You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 */
package com.computationaldesign.wera.hmc;

import de.hybris.platform.catalog.constants.GeneratedCatalogConstants;
import de.hybris.platform.catalog.jalo.CatalogManager;
import de.hybris.platform.catalog.jalo.CatalogVersion;
import de.hybris.platform.catalog.jalo.classification.ClassAttributeAssignment;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttribute;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttributeUnit;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttributeValue;
import de.hybris.platform.catalog.jalo.classification.ClassificationClass;
import de.hybris.platform.catalog.jalo.classification.ClassificationSystemVersion;
import de.hybris.platform.catalog.jalo.classification.util.Feature;
import de.hybris.platform.catalog.jalo.classification.util.FeatureValue;
import de.hybris.platform.catalog.jalo.classification.util.TypedFeature;
import de.hybris.platform.hmc.HMCHelper;
import de.hybris.platform.hmc.attribute.AbstractAttributeEditorChip;
import de.hybris.platform.hmc.attribute.BooleanEditorChip;
import de.hybris.platform.hmc.attribute.DoubleEditorChip;
import de.hybris.platform.hmc.extension.SlotManager;
import de.hybris.platform.hmc.security.NoAccessAttributeEditorChip;
import de.hybris.platform.hmc.webchips.Chip;
import de.hybris.platform.hmc.webchips.DisplayState;
import de.hybris.platform.jalo.ConsistencyCheckException;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.enumeration.EnumerationManager;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.jalo.type.CollectionType;
import de.hybris.platform.jalo.type.TypeManager;
import de.hybris.platform.variants.jalo.VariantProduct;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.computationaldesign.wera.jalo.Outputcontrol;
import com.computationaldesign.wera.jalo.WeraClassificationHelper;
import com.computationaldesign.wera.jalo.WeraProduct;
import com.computationaldesign.wera.jalo.WeraVariante;



/**
 * This class provides some helper methods to simplify access to classificationsystem related data (e.g. getting // *
 * 'classification {@link de.hybris.platform.ext.category.jalo.Category categories}' and
 * {@link de.hybris.platform.ext.catalog.jalo.classification.ClassificationAttribute ClassificationAttributes} for a
 * given product).
 * 
 * @author mr
 * @version $Revision: 1.1.2.1 $
 */
public class HMCClassificationHelper
{
	private static EnumerationValue CLASSIFICATIONATTRIBUTE_VISIBLE;

	private static EnumerationValue CLASSIFICATIONATTRIBUTE_NOT_VISIBLE;

	private static EnumerationValue CLASSIFICATIONATTRIBUTE_VISIBLE_IN_BASE;

	private static EnumerationValue CLASSIFICATIONATTRIBUTE_VISIBLE_IN_VARIANT;

	private static EnumerationValue CLASSIFICATIONATTRIBUTE_ASSIGNED_TO_VARIANT;

	/**
	 * Creates a map containing [Category -> List of ClassificationAttributeEntries] for all visible
	 * ClassificationAttributes of the given product and stores the entries in the given classificationMap. In the
	 * inheritedCategories collection all Categories which have been inherited from the baseproduct of the given product
	 * (if it has a base) are stored. The unboundFeatures collection will contain all ProductFeatures of the given
	 * product which are not contained in a classification system/category.
	 */
	public static void loadClassificationAttributes(final Product product, final Chip parentChip, final Map classificationMap,
			final Map classificationMapAssignedOnly, final Collection inheritedCategories, final Collection unboundFeatures)
	{
		final Set usedQualifiers = new HashSet(); // in this we will collect
		// all qualifiers which are
		// used by the various
		// classification classificationattributes
		// below


		//System.out.println("+++loadClassificationAttributes.product.CODE=" + product.getCode() );			
		for (final Iterator categoryIter = getAllClassificationCategories(product, inheritedCategories).iterator(); categoryIter
				.hasNext();)
		{
			final ClassificationClass classificationclass = (ClassificationClass) categoryIter.next();

			if (classificationclass.getCode().equals("PORTALATTRIBUTES"))
			{
				//Log.warn("CC.code (skip)=" + classificationclass.getCode());
				continue;
			}
			else
			{
				//log.info("CC.code (ok)=" + classificationclass.getCode());
			}

			final Map attribute2ValueMap = classificationclass.getAttributeValueMap();

			final boolean inheritedFromBase = inheritedCategories.contains(classificationclass); // the classification

			final Collection classificationattributes = new ArrayList();
			final Collection classificationattributesAssignedOnly = new ArrayList();

			for (final Iterator attributeIter = HMCClassificationHelper.getClassificationAttributes(classificationclass, product)
					.iterator(); attributeIter.hasNext();)
			{
				final ClassificationAttribute classificationattribute = (ClassificationAttribute) attributeIter.next();

				//System.out.println("+++loadClassificationAttributes.classificationattribute.CODE=" + classificationattribute.getCode() );			

				final boolean bIsVisible = HMCClassificationHelper.isVisible(classificationclass, classificationattribute,
						inheritedFromBase, product);
				final boolean bIsAssignedOnly = HMCClassificationHelper.isAssignedToVariantButInvisible(classificationclass,
						classificationattribute, inheritedFromBase, product);
				if (bIsVisible || bIsAssignedOnly)
				{

					// --- Hole mögliche Werte ----------------------------------------------------------
					final Collection colPossibleValue = new ArrayList();
					try
					{
						// --- ALT --- colPossibleValue = (Collection) classificationattribute.getAttribute("classificationAttributeValues");

						final Collection classassignments = classificationattribute.getClassAssignments();
						colPossibleValue.clear();
						for (final Iterator attrItr = classassignments.iterator(); attrItr.hasNext();)
						{

							final ClassAttributeAssignment caa = (ClassAttributeAssignment) attrItr.next();
							for (final Iterator AttrValItr = caa.getAttributeValues().iterator(); AttrValItr.hasNext();)
							{
								final ClassificationAttributeValue cav = (ClassificationAttributeValue) AttrValItr.next();
								if (!colPossibleValue.contains(cav))
								{
									colPossibleValue.add(cav);
								}
							}
						}
						// --- ALT --- colPossibleValue = (Collection) classificationattribute.getDefaultAttributeValues();

					}
					catch (final JaloInvalidParameterException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// --- Hole mögliche Werte ----------------------------------------------------------

					final ClassificationAttributeEntry classificationattributeentry = new ClassificationAttributeEntry(product,
							classificationclass, parentChip, classificationattribute, colPossibleValue);

					if (bIsVisible)
					{
						classificationattributes.add(classificationattributeentry);
					}
					if (bIsAssignedOnly)
					{
						classificationattributesAssignedOnly.add(classificationattributeentry);
					}

					final Collection uQ = classificationattributeentry.getUsedQualifiers();
					if (uQ.size() > 0)
					{
						usedQualifiers.add(classificationattribute.getCode().toLowerCase());
					}


				}
				else
				{
					usedQualifiers.add(classificationattribute.getCode().toLowerCase());
				}
			}

			// put classificationattribute collection in the category->classificationattributes map
			classificationMap.put(classificationclass, classificationattributes);
			classificationMapAssignedOnly.put(classificationclass, classificationattributesAssignedOnly);
		}

		unboundFeatures.addAll(getResumingFeatures(product, usedQualifiers));
		//System.out.println("usedqualifiers="+usedQualifiers);
		//System.out.println("unboundFeatures="+unboundFeatures);
	}

	/*
	 * -------------------- inner class ClassificationAttributeEntry ----------------
	 */

	public static class ClassificationAttributeEntry implements Serializable
	{
		private final String code;

		private final String name;

		private final EnumerationValue type;

		private final Product product;
		private Product baseproduct;
		private Outputcontrol outputcontrol;

		private ClassificationAttributeUnit unit;

		private final AbstractAttributeEditorChip valueEditor;
		private boolean bArtikelClassificationVariantListView = false;

		private final ClassAttributeAssignment classattributeassignment;
		private final ClassificationAttribute classificationAttribute;
		private final ClassificationClass classificationclass;
		private final Set usedQualifiers = new HashSet(); // in this we will collect

		public Set getUsedQualifiers()
		{
			return usedQualifiers;
		}

		public WeraClassificationHelper weraclassificationhelper = new WeraClassificationHelper();


		public void initializeProductData(final Product product, final ClassificationAttribute classificationattribute)
		{

			try
			{
				// --- Initisiere Produkt / Basisprodukt
				if (product instanceof WeraVariante)
				{
					this.baseproduct = (Product) ((WeraVariante) product).getAttribute("baseProduct");
				}
				else
				{
					this.baseproduct = product;
				}

				// --- Hole Outputcontrols
				final Collection outputcontrols = (Collection) baseproduct.getAttribute("outputcontrols");
				if (outputcontrols != null)
				{
					this.outputcontrol = (Outputcontrol) checkContaining(outputcontrols, "code", classificationattribute.getCode()
							.toString());
				}
				else
				{
					this.outputcontrol = null;
				}

			}
			catch (final JaloInvalidParameterException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (final JaloSecurityException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public ClassificationAttributeEntry(final Product product, final ClassificationClass classificationclass,
				final Chip parentChip, final ClassificationAttribute classificationAttribute, final Collection possibleValues)
		{

			// --- Initialize
			if (parentChip.getUniqueName().contains("ArtikelClassificationVariantListEntry"))
			{
				this.bArtikelClassificationVariantListView = true;
			}
			else
			{
				this.bArtikelClassificationVariantListView = false;
			}
			this.classificationclass = classificationclass;
			this.classificationAttribute = classificationAttribute;
			this.classattributeassignment = classificationclass.getAttributeAssignment(classificationAttribute);

			// --- Produktdaten / Outputcontrol initialisieren
			this.product = product;
			initializeProductData(product, classificationAttribute);

			// --- Debug
			code = classificationAttribute.getCode();
			name = classificationAttribute.getName();
			//System.out.println("++++product-code=" + product.getCode() );
			//System.out.println("++++code=" + code + ", name=" + name);

			// --- Hole Type
			type = classattributeassignment.getAttributeType();

			// --- Hole Unit
			unit = classattributeassignment.getUnit();

			Collection tmpCustomValues = null;
			try
			{
				tmpCustomValues = (Collection) classattributeassignment.getAttribute("customValues");
			}
			catch (final JaloInvalidParameterException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (final JaloSecurityException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			final Collection customValues = tmpCustomValues;


			// --- Hole	Werte
			//final Collection values = classattributeassignment.getAttributeValues();
			final Collection values = new ArrayList();

			if ((possibleValues != null && !possibleValues.isEmpty()) || (customValues != null && !customValues.isEmpty()))
			{

				if (this.bArtikelClassificationVariantListView)
				{
					// --- singleselecteditor
					// --- Create ------------
					valueEditor = new ReferenceSingleSelectEditorChip(getDisplayState(), parentChip, (CollectionType) TypeManager
							.getInstance().getType("ObjectCollection"))
					{
						@Override
						protected List createPossibleEntries()
						{
							final ArrayList ar = new ArrayList();

							if (possibleValues != null)
							{
								ar.addAll(possibleValues);
							}

							if (customValues != null)
							{

								for (final Iterator iter = customValues.iterator(); iter.hasNext();)
								{
									final ClassificationAttributeValue cav = (ClassificationAttributeValue) iter.next();
									if (!ar.contains(cav))
									{
										ar.add(cav);
									}
								}
							}

							return ar;
						}
					};
					// --- Create ------------
				}
				else
				{
					// --- multiselecteditor
					// --- Create ------------
					valueEditor = new ReferenceMultiSelectEditorChip(getDisplayState(), parentChip, (CollectionType) TypeManager
							.getInstance().getType("ObjectCollection"))
					{
						@Override
						protected List createPossibleEntries()
						{
							final ArrayList ar = new ArrayList();

							if (possibleValues != null)
							{
								ar.addAll(possibleValues);
							}

							if (customValues != null)
							{

								for (final Iterator iter = customValues.iterator(); iter.hasNext();)
								{
									final ClassificationAttributeValue cav = (ClassificationAttributeValue) iter.next();
									if (!ar.contains(cav))
									{
										ar.add(cav);
									}
								}
							}

							return ar;
						}
					};
					// --- Create ------------

				} // --- if ( bArtikelClassificationVariantListView ) {



				// --- Hole alle ausgeählten Werte
				final HashMap<String, ClassificationAttributeValue> hResult = weraclassificationhelper
						.getPickedClassificationAttributeValuesByProduct(getProduct(), classattributeassignment);
				final Collection classificationattributevalues = new ArrayList();
				if (hResult != null)
				{
					classificationattributevalues.addAll(hResult.values());
				}
				valueEditor.setInitialValue(classificationattributevalues);
				valueEditor.setWidth(220);

			}
			else if (type.getCode().equals(GeneratedCatalogConstants.Enumerations.ClassificationAttributeTypeEnum.BOOLEAN))
			{
				// --- BOOLEAN ------------------------------------------------------------------
				// --- Hole alle ausgeählten Werte
				final Boolean oBoolValue = weraclassificationhelper.getBooleanClassificationAttributeValuesByProduct(getProduct(),
						classattributeassignment);

				// --- Wert registrieren
				usedQualifiers.add(classificationAttribute);

				valueEditor = new BooleanEditorChip(getDisplayState(), parentChip, oBoolValue)
				{
					@Override
					public boolean isOptional()
					{
						return false;
					}
				};
				valueEditor.setWidth(50);
				// --- BOOLEAN ------------------------------------------------------------------

			}
			else if (type.getCode().equals(GeneratedCatalogConstants.Enumerations.ClassificationAttributeTypeEnum.NUMBER))
			{
				// --- NUMBER -------------------------------------------------------------------
				// try to find double value
				final Object value = values.isEmpty() ? null : values.iterator().next();
				Double doubleValue = null;
				if (values.isEmpty())
				{

					final TypedFeature<Double> feature = Feature.loadTyped(getProduct(), classattributeassignment);
					if (feature != null && !feature.isEmpty())
					{
						final FeatureValue<Double> featurevalue = feature.getValue(0);
						doubleValue = new Double(((Number) featurevalue.getValue()).doubleValue());
					}
				}
				else
				{
					if (value instanceof Number)
					{
						doubleValue = new Double(((Number) value).doubleValue());
					}
				}

				valueEditor = new DoubleEditorChip(getDisplayState(), parentChip, doubleValue);
				valueEditor.setWidth(70);
			}
			else
			{
				valueEditor = new NoAccessAttributeEditorChip(getDisplayState(), parentChip);
				valueEditor.setWidth(100);
			}

			// --- Wert registrieren
			usedQualifiers.add(classificationAttribute);

			valueEditor.setEditable(SlotManager.getSlotManager().isEditable(getProduct(), null));
			// --- NUMBER -------------------------------------------------------------------
		}

		public ClassificationAttributeValue getCAV(final String sCodeCAV, final List<ClassificationAttributeValue> values)
		{

			// --- Initialize
			ClassificationAttributeValue oClassifiacationAttributeValue = null;

			// ---- Schleife über alle CAVw
			for (final Iterator it1 = values.iterator(); it1.hasNext();)
			{
				final ClassificationAttributeValue oClassifiacationAttributeValueTmp = (ClassificationAttributeValue) it1.next();
				if (oClassifiacationAttributeValueTmp.getCode().equals(sCodeCAV))
				{
					oClassifiacationAttributeValue = oClassifiacationAttributeValueTmp;
					break;
				}
			}

			return oClassifiacationAttributeValue;
		}


		/**
		 * Returns the given values as a semicolon separated String.
		 * 
		 * @param values
		 *           the attribute values as a Collection of Strings
		 * @return the values of the given values as a semicolon separated String.
		 */
		public String getValuesAsString(final Collection values)
		{

			if (values == null || values.isEmpty())
			{
				return null;
			}

			final StringBuffer result = new StringBuffer();

			for (final Iterator iter = values.iterator(); iter.hasNext();)
			{

				result.append(iter.next().toString());
				//result.append((String) iter.next());

				if (iter.hasNext())
				{
					result.append(";");
				}
			}
			return result.toString();
		}

		/**
		 * Returns a collection of strings containing the parsed values from the values editor.
		 * 
		 * @return a collection of strings containing the parsed values from the values editor.
		 */
		protected Collection getValues()
		{
			final Collection result = new ArrayList();

			if (getValueEditor() instanceof ReferenceSingleSelectEditorChip
					|| getValueEditor() instanceof ReferenceMultiSelectEditorChip)
			{
				result.addAll((Collection) getValueEditor().getValue());
			}
			else
			{
				result.add(getValueEditor().getValue());
			}

			return result;
		}

		/**
		 * Saves the values of this entry (only if they have been changed). The values are set by parsing the current
		 * values String which must contain a list of values separated by semicolons (or just one value or no value at
		 * all). <br>
		 * <b>Note:</b> Leading and trailing whitespaces of both the given string and each of the separated values will be
		 * omitted!
		 */
		public void save()
		{

			if (getValueEditor().isChanged())
			{
				/*
				 * System.out.println("\n---------\n");
				 * System.out.println("+++++++++++ClassificationAttributeEntry.save()="+getValueEditor().isChanged() +
				 * ", code=" + getCode() ); System.out.println("product.getCode()="+product.getCode());
				 * System.out.println("type="+type.getCode()); System.out.println("val="+(Collection)getValues());
				 * System.out.println("classificationAttribute="+classificationAttribute.getCode());
				 */
				// save the values
				// --- BOOLEAN --------------------------------
				if (type.getCode().equals(GeneratedCatalogConstants.Enumerations.ClassificationAttributeTypeEnum.BOOLEAN))
				{
					// --- Speichern Boolean
					final Collection values = getValues();

					final Boolean value = (Boolean) values.iterator().next();

					final TypedFeature<Boolean> feature = Feature.loadTyped(getProduct(), classattributeassignment);
					feature.clearAll();
					if (value.booleanValue())
					{
						feature.createValue(value);
					}
					try
					{
						feature.getParent().store();
					}
					catch (final ConsistencyCheckException e)
					{
						e.printStackTrace();
					}
					// --- BOOLEAN --------------------------------
				}
				else if (type.getCode().equals(GeneratedCatalogConstants.Enumerations.ClassificationAttributeTypeEnum.NUMBER))
				{
					// --- NUMBER --------------------------------
					// --- Speichern Double
					final Collection values = getValues();

					try
					{
						final Object obj = values.iterator().next();
						if (obj instanceof ClassificationAttributeValue)
						{

							final String value = ((ClassificationAttributeValue) obj).getName();
							final TypedFeature<String> feature = Feature.loadTyped(getProduct(), classattributeassignment);
							feature.clearAll();
							feature.createValue(value);
							feature.getParent().store();
						}
						else
						{
							final Double value = (Double) obj;
							final TypedFeature<Double> feature = Feature.loadTyped(getProduct(), classattributeassignment);
							feature.clearAll();
							feature.createValue(value);
							feature.getParent().store();
						}

					}
					catch (final ConsistencyCheckException e)
					{
						e.printStackTrace();
					}
					// --- NUMBER --------------------------------
				}
				else if (type.getCode().equals(GeneratedCatalogConstants.Enumerations.ClassificationAttributeTypeEnum.STRING)
						|| type.getCode().equals(GeneratedCatalogConstants.Enumerations.ClassificationAttributeTypeEnum.ENUM))
				{
					// --- STRING --------------------------------
					// --- Speichern String
					final TypedFeature<String> feature = Feature.loadTyped(product, classattributeassignment);
					feature.clearAll();

					// --- Neue Werte anlegen
					final List<ClassificationAttributeValue> values = (List<ClassificationAttributeValue>) getValues();
					if (values != null && values.size() > 0)
					{
						for (final Iterator it1 = values.iterator(); it1.hasNext();)
						{
							final ClassificationAttributeValue CAV = (ClassificationAttributeValue) it1.next();
							if (CAV != null)
							{
								// --- now create it
								feature.createValue(CAV.getCode());
							}
						}
					}
					try
					{
						feature.getParent().store();
					}
					catch (final ConsistencyCheckException e)
					{
						e.printStackTrace();
					}

					// --- STRING --------------------------------
				}
				else
				{
					// --- Speichern Werteliste
					final List<ClassificationAttributeValue> values = (List<ClassificationAttributeValue>) getValues();
					classattributeassignment.setAttributeValues(values);
				}
			}
		}

		/**
		 * Returns true if the content of the editor has been changed.
		 * 
		 * @return true if the content of the editor has been changed.
		 */
		public boolean isChanged()
		{
			final boolean isChanged = getValueEditor().isChanged();
			//System.out.println("ClassificationAttributeEntry.isChanged()="+isChanged + ", code=" + getCode() );
			return isChanged;
		}

		/* ----- simple getters and setters ------------ */
		public ClassificationAttributeUnit getUnit()
		{
			return unit;
		}

		// --- Bezeichnung der Einheit
		public String getUnitName()
		{
			String strUnitName = "";

			try
			{
				// --- Hole Icon aus OutputControl
				if (this.outputcontrol != null)
				{
					strUnitName = (String) this.outputcontrol.getAttribute("unitca");
				}
				if (strUnitName == null || strUnitName.equals(""))
				{
					// --- Ansonsten direkt vom Merkmal
					this.unit = classattributeassignment.getUnit();
					strUnitName = this.unit.getSymbol();
					System.out.println("WARN - No Outputcontrol for " + classattributeassignment + " - get the CAA - defaults");
				}

			}
			catch (final JaloInvalidParameterException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (final JaloSecurityException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return strUnitName;
		}

		// --- Hintergrund hinterlegen
		public boolean getBackground()
		{
			boolean bBackground = false;

			try
			{
				// --- Hole Hintergrund aus OutputControl
				Boolean oBackground = null;
				if (this.outputcontrol != null)
				{
					oBackground = (Boolean) this.outputcontrol.getAttribute("background");
				}
				if (oBackground == null)
				{
					// --- Ansonsten direkt vom Merkmal
					oBackground = (Boolean) classattributeassignment.getAttribute("background");
					System.out.println("WARN - No Outputcontrol for " + classattributeassignment + " - get the CAA - defaults");
				}
				bBackground = oBackground.booleanValue();

			}
			catch (final JaloInvalidParameterException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (final JaloSecurityException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return bBackground;
		}

		// --- Hintergrund hinterlegen
		public boolean getShowinSet()
		{
			boolean bShowinSet = false;

			try
			{
				// --- Hole Hintergrund aus OutputControl
				Boolean oShowinSet = null;
				if (this.outputcontrol != null)
				{
					oShowinSet = (Boolean) this.outputcontrol.getAttribute("showinset");
				}
				if (oShowinSet == null)
				{
					// --- Ansonsten direkt vom Merkmal
					oShowinSet = (Boolean) classattributeassignment.getAttribute("showinset");
					System.out.println("WARN - No Outputcontrol for " + classattributeassignment + " - get the CAA - defaults");
				}
				bShowinSet = oShowinSet.booleanValue();

			}
			catch (final JaloInvalidParameterException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (final JaloSecurityException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return bShowinSet;
		}

		// --- Hintergrund hinterlegen
		public boolean getAktiv()
		{
			/* SJ 2021-04-20: sowohl OutputControl als auch ClassAttributeAssignment Aktiv Flag wurden 2011 getilgt.
			 * Daher war dieser Code danach eigentlich obsolet.
			 * Stattdessen immer true zurückliefern
			 */
			return true;
		}
		

		// --- Hole Merkmal-Icon
		public Collection getIcons()
		{

			Collection icons = null;

			try
			{
				// --- Hole Icon aus OutputControl
				if (this.outputcontrol != null)
				{
					icons = (Collection) this.outputcontrol.getAttribute("icons");
				}
				if (icons == null || icons.size() == 0)
				{
					// --- Ansonsten direkt vom Merkmal
					icons = (Collection) this.classattributeassignment.getAttribute("icons");
					System.out.println("WARN - No Outputcontrol for " + classattributeassignment + " - get the CAA - defaults");
				}

			}
			catch (final JaloInvalidParameterException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (final JaloSecurityException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return icons;
		}

		public String getCode()
		{
			return code;
		}

		public boolean isMandatory()
		{

			boolean bResult;
			if (type.getCode().equals(GeneratedCatalogConstants.Enumerations.ClassificationAttributeTypeEnum.BOOLEAN))
			{
				bResult = false;
			}
			else
			{
				bResult = classificationAttribute != null ? classificationclass.isMandatory(classificationAttribute) : false;
			}

			return bResult;
		}

		public String getName()
		{
			return name;
		}

		public EnumerationValue getType()
		{
			return type;
		}

		public String getTypeCode()
		{
			return getType() != null ? getType().getCode() : null;
		}

		public AbstractAttributeEditorChip getValueEditor()
		{
			return valueEditor;
		}

		public ClassificationAttribute getClassificationAttribute()
		{
			return classificationAttribute;
		}

		public ClassAttributeAssignment getAttributeAssignment()
		{

			return classattributeassignment;
		}



		public Product getProduct()
		{
			return product;
		}

		public EnumerationValue getVisibility()
		{

			EnumerationValue visibility = null;
			try
			{
				// --- Hole Sichtbarkeit aus OutputControl
				if (outputcontrol != null)
				{
					visibility = this.outputcontrol.getVisibility();
				}
				if (visibility == null || outputcontrol == null)
				{
					// --- Ansonsten direkt vom CA - Assignment
					visibility = classattributeassignment.getVisibility();
					System.out.println("WARN - No Outputcontrol for " + classattributeassignment + " - get the CAA - defaults");
				}

			}
			catch (final JaloInvalidParameterException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			return visibility;
		}
	}

	/* ------------ private helper methods ----------- */

	private static DisplayState getDisplayState()
	{
		return DisplayState.getCurrent();
	}

	/**
	 * Returns all categories which have a {@link CatalogVersion} which is an instance of
	 * {@link ClassificationSystemVersion}.
	 * 
	 * @return all categories which have a {@link CatalogVersion} which is an instance of
	 *         {@link ClassificationSystemVersion}
	 */
	private static Collection getAllClassificationCategories(final Product product, final Collection inheritedCategories)
	{
		if (product == null)
		{
			return Collections.EMPTY_LIST;
		}

		final Collection result = new LinkedHashSet();

		// get all supercategories of the enclosing product which are
		// classification categories
		result.addAll(HMCClassificationHelper.getClassificationCategories(product));

		if (product.isInstanceOf(HMCHelper.getType(VariantProduct.class)))
		{
			// the enclosing product is a VariantProduct -> additionally get all
			// classification categories of its base product
			for (final Iterator iter = HMCClassificationHelper.getClassificationCategories(
					((VariantProduct) product).getBaseProduct()).iterator(); iter.hasNext();)
			{
				final ClassificationClass classificationclass = (ClassificationClass) iter.next();
				if (result.add(classificationclass))
				{
					// remember this classificationclass as being inherited from base
					// product
					inheritedCategories.add(classificationclass);
				}
			}
		}

		return result;
	}

	/**
	 * Returns all super categories of the given Product which have a {@link CatalogVersion} which is an instance of
	 * {@link ClassificationSystemVersion}.
	 * 
	 * @return all super categories which have a {@link CatalogVersion} which is an instance of
	 *         {@link ClassificationSystemVersion}
	 */
	private static Collection getClassificationCategories(final Product product)
	{
		/*
		 * final Collection result = new ArrayList();
		 * 
		 * final Collection categories = getAllSuperCategories(product); ClassificationSystemVersion versionProduct =
		 * null; ClassificationSystemVersion versionWeraZusatz = null;
		 * 
		 * // --- Initialize CatalogManager cm = CatalogManager.getInstance();
		 * 
		 * ClassificationSystem weraclassification = cm.getClassificationSystem("weracatalog"); versionWeraZusatz =
		 * weraclassification.getSystemVersion("werazusatz");
		 * 
		 * try { versionProduct = (ClassificationSystemVersion) product.getAttribute("catalogVersion"); } catch
		 * (JaloInvalidParameterException e) { // TODO Auto-generated catch block e.printStackTrace(); } catch
		 * (JaloSecurityException e) { // TODO Auto-generated catch block e.printStackTrace(); } if (categories != null) {
		 * for (final Iterator iter = categories.iterator(); iter.hasNext();) { final ClassificationClass
		 * classificationclass = ((ClassificationClass) iter.next()); final CatalogVersion version =
		 * cm.getCatalogVersion(classificationclass);
		 * 
		 * if (version != null && version.isInstanceOf(HMCHelper .getType(ClassificationSystemVersion.class))) {
		 * 
		 * // --- Filtern aller doppelten Kategorien anderer Werakataloge boolean bIsWeraCatalog =
		 * version.getCatalog().getId().toString().equals("weracatalog"); boolean bIsWeraZusatz =
		 * classificationclass.getSystemVersion().equals(versionWeraZusatz); if (bIsWeraZusatz || (bIsWeraCatalog &&
		 * (versionProduct.getVersion().equals(version.getVersion()) && bIsWeraCatalog)) || bIsWeraCatalog==false )
		 * result.add(classificationclass); } } }
		 */
		Collection result = new ArrayList();
		try
		{
			result = (Collection) product.getAttribute("classificationClasses");
		}
		catch (final JaloSecurityException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	static Object checkContaining(final Collection collection, final String stringField, final String stringMatch)
	{
		Object oResult = null;
		Item item = null;
		for (final Iterator it1 = collection.iterator(); it1.hasNext();)
		{

			item = (Item) it1.next();
			try
			{
				if (item.getAttribute(stringField).equals(stringMatch))
				{
					oResult = item;
					break;
				}
			}
			catch (final JaloInvalidParameterException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (final JaloSecurityException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return oResult;
	}


	/**
	 * Returns true if the 'visibility' attribute of the given {@link ClassificationAttribute} allows it to be seen.
	 * Since this is partly based on the fact that this classification attribute may come from a base product you also
	 * have to specify if the attribute is 'inheritedFromBase'.
	 * 
	 * @param product
	 * 
	 * @return true if the 'visibility' attribute of the given ClassificationAttribute allows it to be seen.
	 */
	private static boolean isVisible(final ClassificationClass classificationclass,
			final ClassificationAttribute classificationAttribute, final boolean inheritedFromBase, Product product)
	{

		boolean bIsVariant = false;
		boolean bIsVisible = true;

		//		EnumerationValue visibility = null;
		//EnumerationValue visibility = classificationclass.getVisibility(classificationAttribute);
		final ClassAttributeAssignment classattributeassignment = classificationclass
				.getAttributeAssignment(classificationAttribute);
		EnumerationValue visibility = classattributeassignment.getVisibility();


		Collection outputcontrols = null;
		try
		{

			if (product instanceof WeraVariante)
			{
				product = (Product) ((WeraVariante) product).getAttribute("baseProduct");
				bIsVariant = true;
			}
			outputcontrols = (Collection) product.getAttribute("outputcontrols");
		}
		catch (final JaloInvalidParameterException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (final JaloSecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (outputcontrols != null)
		{
			final Outputcontrol outputcontrol = (Outputcontrol) checkContaining(outputcontrols, "code", classificationAttribute
					.getCode().toString());
			if (outputcontrol != null)
			{
				visibility = outputcontrol.getVisibility();
			}
		}

		if (visibility != null)
		{

			if (bIsVariant == false)
			{
				// --- Sichtbarkeit Produkbasis 
				bIsVisible = visibility.equals(getVisibleEnumValue()) || visibility.equals(getVisibleInBaseEnumValue());
			}
			else
			{
				// --- Sichtbarkeit Produkvariante
				bIsVisible = visibility.equals(getVisibleInVariantEnumValue());
			}

		} // --- if (visibility !== null ) {


		// all other cases 
		return bIsVisible;
	}


	private static boolean isAssignedToVariantButInvisible(final ClassificationClass classificationclass,
			final ClassificationAttribute classificationAttribute, final boolean inheritedFromBase, Product product)
	{

		if (product instanceof WeraProduct)
		{
			return false;
		}

		boolean bIsVariant = false;
		boolean bIsAssigned = false;
		Collection outputcontrols = null;
		try
		{

			if (product instanceof WeraVariante)
			{
				product = (Product) ((WeraVariante) product).getAttribute("baseProduct");
				bIsVariant = true;
			}
			outputcontrols = (Collection) product.getAttribute("outputcontrols");
		}
		catch (final JaloInvalidParameterException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (final JaloSecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (bIsVariant)
		{

			final ClassAttributeAssignment classattributeassignment = classificationclass
					.getAttributeAssignment(classificationAttribute);
			EnumerationValue visibility = classattributeassignment.getVisibility();


			if (outputcontrols != null)
			{
				final Outputcontrol outputcontrol = (Outputcontrol) checkContaining(outputcontrols, "code", classificationAttribute
						.getCode().toString());
				if (outputcontrol != null)
				{
					visibility = outputcontrol.getVisibility();
				}
				if (visibility != null)
				{
					bIsAssigned = visibility.equals(getAssignedToVariantEnumValue());
				}

			}
		}

		// all other cases 
		return bIsAssigned;
	}



	private static Collection getResumingFeatures(final Product product, final Collection usedQualifiers)
	{
		final Collection productFeatures = new ArrayList(CatalogManager.getInstance().getFeatures(product));
		if (productFeatures == null || productFeatures.size() == 0)
		{
			return Collections.EMPTY_LIST;
		}
		else
		{
			return Collections.EMPTY_LIST;
			/*
			 * for (final Iterator iter = productFeatures.iterator(); iter.hasNext();) { final ProductFeature feature =
			 * (ProductFeature) iter.next();
			 * //System.out.println("getResumingFeatures.productFeatures.code="+feature.getClassificationAttributeAssignment
			 * ().getClassificationAttribute().getCode().toLowerCase() ); if ( feature != null && usedQualifiers != null )
			 * { if
			 * (usedQualifiers.contains(feature.getClassificationAttributeAssignment().getClassificationAttribute().getCode
			 * ().toLowerCase())) { // if (usedQualifiers.contains(feature.getQualifier().toLowerCase())) {
			 * 
			 * //System.out.println("getResumingFeatures.productFeatures.remove=" ); iter.remove(); } } }
			 * 
			 * return productFeatures;
			 */
		}
	}

	/**
	 * Returns a collection of {@link de.hybris.platform.ext.catalog.jalo.classification.ClassificationAttribute
	 * ClassificationAttributes} for the given category.
	 * 
	 * @param category
	 *           the Category for which to return all ClassificationAttributes
	 * @return a collection of ClassificationAttributes for the given category.
	 */
	static class StaticComparator implements Comparator
	{
		public int compare(final Object o1, final Object o2) /* descending order */
		{

			// --- Initialize
			Integer iValue1 = new Integer(0);
			Integer iValue2 = new Integer(0);
			final int iResult = 0;

			try
			{
				// --- Hole Values
				iValue1 = (Integer) ((Item) o1).getAttribute("order");
				iValue2 = (Integer) ((Item) o2).getAttribute("order");
			}
			catch (final JaloInvalidParameterException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (final JaloSecurityException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (iValue1 == null || iValue2 == null)
			{
				return 0;
			}
			else
			{
				return iValue1.compareTo(iValue2);
			}
		}
	}

	static class OrderCtrlComparator implements Comparator
	{

		WeraProduct m_product = null;
		Collection m_outputcontrols = null;

		public void setProduct(final Product product)
		{
			// --- Hole die Ausgabesteuerung
			m_product = (WeraProduct) product;
			try
			{
				m_outputcontrols = (Collection) m_product.getAttribute("outputcontrols");
			}
			catch (final Exception e)
			{
				e.printStackTrace();
			}
		}

		public Object checkContaining(final Collection collection, final String stringField, final String stringMatch)
		{
			Object oResult = null;
			Item item = null;
			for (final Iterator it1 = collection.iterator(); it1.hasNext();)
			{

				item = (Item) it1.next();
				try
				{
					if (item.getAttribute(stringField).equals(stringMatch))
					{
						oResult = item;
						break;
					}
				}
				catch (final JaloInvalidParameterException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (final JaloSecurityException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return oResult;
		}

		public Integer getOrderByProduct(final Item item)
		{
			// --- Initialize
			String strCode = "";
			Integer oInteger = new Integer(0);
			Outputcontrol outputcontrol = null;
			try
			{
				strCode = (String) item.getAttribute("code");
				outputcontrol = (Outputcontrol) checkContaining(m_outputcontrols, "code", strCode);
				if (outputcontrol != null)
				{
					oInteger = (Integer) outputcontrol.getAttribute("order");
					if (oInteger == null)
					{
						return new Integer(0);
					}
				}
			}
			catch (final Exception e)
			{
				e.printStackTrace();
			}

			return oInteger;
		}

		public int compare(final Object o1, final Object o2) /* descending order */
		{

			// --- Initialize
			Integer iValue1 = new Integer(0);
			Integer iValue2 = new Integer(0);
			final int iResult = 0;

			// --- Hole Values
			iValue1 = getOrderByProduct((Item) o1);
			iValue2 = getOrderByProduct((Item) o2);

			if (iValue1 == null || iValue2 == null)
			{
				return 0;
			}
			else
			{
				return iValue1.compareTo(iValue2);
			}
		}
	}

	private static Collection getClassificationAttributes(final ClassificationClass classificationclass, Product product)
	{

		/*
		 * !!!!!!!! ACHTUNG List colCA = (List)CatalogManager.getInstance().getClassificationAttributes(category);
		 */
		final List colCA = classificationclass.getClassificationAttributes();

		if (product instanceof WeraVariante)
		{
			try
			{
				product = (Product) ((WeraVariante) product).getAttribute("baseProduct");
			}
			catch (final JaloInvalidParameterException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (final JaloSecurityException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// --- Initialize sortierung
		final OrderCtrlComparator orderctrl_comparator = new OrderCtrlComparator();
		orderctrl_comparator.setProduct(product);

		// --- Sortiere, nach "order"
		final List colResult = new ArrayList();
		colResult.addAll(colCA);
		Collections.sort(colResult, orderctrl_comparator);
		return colResult;
	}

	/**
	 * Returns all supercategories of the given product.
	 * 
	 * @param product
	 *           the Product for which to return the supercategories
	 * @return all supercategories of the product
	 */
	private static Collection getAllSuperCategories(final Product product)
	{
		final List allCategories = new ArrayList();

		Collection classificationclasses = new ArrayList();
		try
		{
			classificationclasses = (Collection) product.getAttribute("classificationClasses");
		}
		catch (final JaloSecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (final Iterator iter = classificationclasses.iterator(); iter.hasNext();)
		{
			//final ClassificationClass classificationclass = (ClassificationClass) iter.next();

			final ClassificationClass classificationclass = (ClassificationClass) iter.next();
			allCategories.add(classificationclass);

			for (final Iterator superCatIter = getAllSupercategories(classificationclass).iterator(); superCatIter.hasNext();)
			{
				final ClassificationClass superCat = (ClassificationClass) superCatIter.next();
				if (!allCategories.contains(superCat))
				{
					allCategories.add(superCat);
				}
			}
		}

		Collections.reverse(allCategories);
		return allCategories;
	}

	/**
	 * Copied from {@link Category#getAllSupercategories(SessionContext)} to provide predictable iteration order...
	 */
	private static Collection getAllSupercategories(final ClassificationClass classificationclass)
	{
		final Collection ret = new LinkedHashSet();
		Collection currentLevel = classificationclass.getSupercategories();
		while (!currentLevel.isEmpty())
		{
			final Collection nextLevel = new LinkedHashSet();
			for (final Iterator it = currentLevel.iterator(); it.hasNext();)
			{
				final ClassificationClass superCat = (ClassificationClass) it.next();
				final Collection superSuperCategories = superCat.getSupercategories();
				// add super category to result
				ret.add(superCat);
				// remember super super categories for next level
				nextLevel.addAll(superSuperCategories);
			}
			// avoid cycles by removing all which are already found
			nextLevel.removeAll(ret);
			currentLevel = nextLevel;
		}
		return ret;
	}

	private static EnumerationValue getVisibleEnumValue()
	{
		if (CLASSIFICATIONATTRIBUTE_VISIBLE == null)
		{
			CLASSIFICATIONATTRIBUTE_VISIBLE = EnumerationManager.getInstance().getEnumerationValue(
					"ClassificationAttributeVisibilityEnum", "visible");
		}

		return CLASSIFICATIONATTRIBUTE_VISIBLE;
	}

	private static EnumerationValue getNotVisibleEnumValue()
	{
		if (CLASSIFICATIONATTRIBUTE_NOT_VISIBLE == null)
		{
			CLASSIFICATIONATTRIBUTE_NOT_VISIBLE = EnumerationManager.getInstance().getEnumerationValue(
					"ClassificationAttributeVisibilityEnum", "not_visible");
		}

		return CLASSIFICATIONATTRIBUTE_NOT_VISIBLE;
	}

	private static EnumerationValue getVisibleInBaseEnumValue()
	{
		if (CLASSIFICATIONATTRIBUTE_VISIBLE_IN_BASE == null)
		{
			CLASSIFICATIONATTRIBUTE_VISIBLE_IN_BASE = EnumerationManager.getInstance().getEnumerationValue(
					"ClassificationAttributeVisibilityEnum", "visible_in_base");
		}

		return CLASSIFICATIONATTRIBUTE_VISIBLE_IN_BASE;
	}

	private static EnumerationValue getVisibleInVariantEnumValue()
	{
		if (CLASSIFICATIONATTRIBUTE_VISIBLE_IN_VARIANT == null)
		{
			CLASSIFICATIONATTRIBUTE_VISIBLE_IN_VARIANT = EnumerationManager.getInstance().getEnumerationValue(
					"ClassificationAttributeVisibilityEnum", "visible_in_variant");
		}

		return CLASSIFICATIONATTRIBUTE_VISIBLE_IN_VARIANT;
	}

	private static EnumerationValue getAssignedToVariantEnumValue()
	{
		if (CLASSIFICATIONATTRIBUTE_ASSIGNED_TO_VARIANT == null)
		{
			CLASSIFICATIONATTRIBUTE_ASSIGNED_TO_VARIANT = EnumerationManager.getInstance().getEnumerationValue(
					"ClassificationAttributeVisibilityEnum", "assigned_to_variant");
		}

		return CLASSIFICATIONATTRIBUTE_ASSIGNED_TO_VARIANT;
	}


}

/*
 * $Log: hmcClassificationHelper.java,v $ Revision 1.1.2.1 2005/10/09 20:23:57 rutten CATALOG-241: added first version
 * of classification variant list editor
 */