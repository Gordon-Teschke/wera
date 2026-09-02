package com.computationaldesign.wera.jalo;

import de.hybris.platform.catalog.constants.GeneratedCatalogConstants;
import de.hybris.platform.catalog.jalo.classification.ClassAttributeAssignment;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttributeValue;
import de.hybris.platform.catalog.jalo.classification.ClassificationClass;
import de.hybris.platform.catalog.jalo.classification.util.Feature;
import de.hybris.platform.catalog.jalo.classification.util.FeatureValue;
import de.hybris.platform.catalog.jalo.classification.util.TypedFeature;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.product.Product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class WeraClassificationHelper
{

	protected WeraManager wm = WeraManager.getInstance();
	protected HashMap<String, HashMap<String, ClassificationAttributeValue>> hClassAttributeAssignments = new HashMap();
	protected HashMap<String, ClassificationAttributeValue> hClassificationattributevaluesByValue = new HashMap();

	/**
	 * Assignmentscache freigeben
	 */
	public void ResetCache()
	{
            hClassAttributeAssignments.clear();
	}

	/**
	 * Hole die m�glichen Werte eines ClassAttributeAssignment
	 * 
	 * @param caa
	 * @return 
	 */
	public HashMap<String, ClassificationAttributeValue> getAllClassificationAttributeValuesByCaa(
			final ClassAttributeAssignment caa)
	{

		// --- Initialize
		final ClassificationClass cc = caa.getClassificationClass();
		final String sClassificationClass = cc.getCode();
		final HashMap<String, ClassificationAttributeValue> hClassificationattributevaluesResult = new HashMap();
		final String sCA_Code = caa.getClassificationAttribute().getCode();

		// --- Hole Pfad
		final String sCatalogVersion = caa.getSystemVersion().getVersion();
		String sClassificationPath = sCatalogVersion;
		final Collection<ClassificationClass> classificationclasses = cc.getSuperClasses();
		for (final Iterator it1 = classificationclasses.iterator(); it1.hasNext();)
		{
			final ClassificationClass cc_temp = (ClassificationClass) it1.next();
			sClassificationPath = sClassificationPath + "/" + cc_temp.getCode();
		}
		sClassificationPath = sClassificationPath + "/" + sClassificationClass + "/";

		// --- Pr�fe ob der Eintrag schon existiert
		if (hClassAttributeAssignments.containsKey(sClassificationPath + sCA_Code))
		{

			// --- schon da, dann nehmen wir das auch!!
			//System.out.println ( sClassificationClass + " load cav cache => sClassificationPath=" + sClassificationPath + sCA_Code );
			hClassificationattributevaluesResult.putAll(hClassAttributeAssignments.get(sClassificationPath + sCA_Code));

		}
		else
		{

			// --- Sammlen und merken ---------------
			//System.out.println ( sClassificationClass + " load all cav => sClassificationPath=" + sClassificationPath + sCA_Code );

			// --- Alle CustomValues merken
			final Collection<ClassificationAttributeValue> customvalues = (Collection<ClassificationAttributeValue>) wm
					.getAttribute(caa, "customValues");
			if (customvalues != null)
			{

				// --- Werte so merken, das wir sp�ter noch was mit anfangen k�nnen
				for (final Iterator it1 = customvalues.iterator(); it1.hasNext();)
				{
					final ClassificationAttributeValue cav = (ClassificationAttributeValue) it1.next();
					hClassificationattributevaluesResult.put(cav.getCode(), cav);
					//if ( sCA_Code.equals("AAA968f001" ) ) {
					//System.out.println ("custv - cav.getCode()=" + cav.getCode() );
					//}
				}
			}

			// --- Alle Default - Values
			final Collection<ClassificationAttributeValue> attributevalues = caa.getAttributeValues();
			if (attributevalues != null)
			{

				// --- Default Language immer "de", zur Zeit sind keine lokalisierten Values im ProfiClass
				/*
				 * if ( sCA_Code.equals("AAA968f001" ) ) { Language oAktLang =
				 * JaloSession.getCurrentSession().getSessionContext().getLanguage(); // wm.SetLanguage ( "de" );
				 * System.out.println ("AAA968f001, sCA_Code=" + sCA_Code + ", lang=" + oAktLang); System.out.println
				 * ("AAA968f001, attributevalues.size()=" + attributevalues.size() ); System.out.println
				 * ("AAA968f001, getVersion()=" + caa.getSystemVersion().getVersion() ); }
				 */
				// --- Werte so merken, das wir sp�ter noch was mit anfangen k�nnen
				for (final Iterator it1 = attributevalues.iterator(); it1.hasNext();)
				{
					final ClassificationAttributeValue cav = (ClassificationAttributeValue) it1.next();
					//if ( sCA_Code.equals("AAA968f001" ) ) {
					//System.out.println ("cav - cav.getCode()="  + cav.getCode() );
					//}
					hClassificationattributevaluesResult.put(cav.getCode(), cav);
				}
				//if ( sCA_Code.equals("AAA968f001" ) ) {
				//System.out.println ("AAA968f001, hClassificationattributevaluesResult.size()=" + hClassificationattributevaluesResult.size() );
				//}

			}

			// --- Result merken, falls wieder einer fragen sollte :)
			if (hClassificationattributevaluesResult != null)
			{
				hClassAttributeAssignments.put(sClassificationPath + sCA_Code, hClassificationattributevaluesResult);
			}
		}
                
                // --- set some globals
                this.hClassificationattributevaluesByValue.clear();
                for (final Iterator it2 = hClassificationattributevaluesResult.values().iterator(); it2.hasNext();) {
                    // --- get data
                    ClassificationAttributeValue cav = (ClassificationAttributeValue) it2.next();
                    this.hClassificationattributevaluesByValue.put( cav.getName(), cav );
                }

		return hClassificationattributevaluesResult;
	}


	/**
	 * Hole alle ClassificationClasses von Produkt / Variante oder Set
	 * 
	 * @param product
	 * @return 
	 */
	public List<ClassificationClass> getAllClassificationClassByProduct(final Product product)
	{
		final List<ClassificationClass> classificationclasses = (List<ClassificationClass>) wm.getAttribute(product,
				"classificationclasses");

		return classificationclasses;
	}


	/**
	 * Hole alle ClassAttributeAssignment von Produkt / Variante oder Set
	 * 
	 * @param product
	 * @return 
	 */
	public List<ClassAttributeAssignment> getAllClassAttributeAssignmentByProduct(final Product product)
	{

		// --- Initialize
		final List<ClassAttributeAssignment> classattributeassignmentvaluesResult = new ArrayList();

		// --- Hole alle ClassificationClasses, Loop
		final List<ClassificationClass> classificationclasses = (List<ClassificationClass>) wm.getAttribute(product,
				"classificationclasses");
		for (final Iterator it_classes = classificationclasses.iterator(); it_classes.hasNext();)
		{
			// --- Hole die Klasse
			final ClassificationClass classificationclass = (ClassificationClass) it_classes.next();

			// --- Hole alle Assignments der Klasse
			//final Collection<ClassAttributeAssignment> classattributeassignments = classificationclass
			//		.getClassificationAttributeAssignments();
			final Collection<ClassAttributeAssignment> classattributeassignments = classificationclass
					.getDeclaredClassificationAttributeAssignments();

			// --- Merken der Assignments
			classattributeassignmentvaluesResult.addAll(classattributeassignments);

		} // --- for ( iterator it_classes = classificationclasses.iterator(); it_classes.hasNext(); ) {


		return classattributeassignmentvaluesResult;
	}

	/**
	 * Hole alle Assignments einer Klasse
	 * 
	 * @param classificationclass
	 * @return 
	 */
	public Collection<ClassAttributeAssignment> getClassificationAttributeAssignments(final ClassificationClass classificationclass)
	{

		final Collection<ClassAttributeAssignment> classattributeassignments = classificationclass
				.getClassificationAttributeAssignments();

		return classattributeassignments;
	}

	/**
	 * Hole alle die m�glichen Werte ClassificationAttribute von Produkt / Variante oder Set
	 * 
	 * @param product
	 * @return 
	 */
	public HashMap<String, ClassificationAttributeValue> getAllClassificationAttributeValuesByProduct(final Product product)
	{

		// --- Initialize
		HashMap<String, ClassificationAttributeValue> classificationattributevaluesResult = new HashMap();

		// --- Hole alle ClassificationClasses, Loop
		final List<ClassificationClass> classificationclasses = (List<ClassificationClass>) wm.getAttribute(product,
				"classificationclasses");
		for (final Iterator it_classes = classificationclasses.iterator(); it_classes.hasNext();)
		{
			// --- Hole die Klasse
			final ClassificationClass classificationclass = (ClassificationClass) it_classes.next();

			// --- Hole alle Assignments der Klasse
			final Collection<ClassAttributeAssignment> classattributeassignments = classificationclass
					.getClassificationAttributeAssignments();
			for (final Iterator it_assignment = classificationclasses.iterator(); it_assignment.hasNext();)
			{
				// --- Hole das Assignment
				final ClassAttributeAssignment classattributeassignment = (ClassAttributeAssignment) it_assignment.next();

				// --- Hole die m�glichen Werte eines ClassAttributeAssignment 
				classificationattributevaluesResult = getClassificationAttributeValues(classattributeassignment);
			}

		} // --- for ( iterator it_classes = classificationclasses.iterator(); it_classes.hasNext(); ) {


		return classificationattributevaluesResult;
	}

	/**
	 * Hole die m�glichen Werte eines ClassAttributeAssignment 
	 * 
	 * @param classattributeassignment
	 * @return 
	 */
	public HashMap<String, ClassificationAttributeValue> getClassificationAttributeValues(
			final ClassAttributeAssignment classattributeassignment)
	{


		// --- Hole die m�glichen Werte eines ClassAttributeAssignment
		return getAllClassificationAttributeValuesByCaa(classattributeassignment);
	}


	/**
	 * Hole die ausgew�hlten ClassificationAttributeValue (Typ-String) eines Products / Values / oder Sets
	 * 
	 * @param product
	 * @param classattributeassignment
	 * @return 
	 */
	public HashMap<String, ClassificationAttributeValue> getPickedClassificationAttributeValuesByProduct(final Product product,
			final ClassAttributeAssignment classattributeassignment)
	{

		// --- Initalize
		final HashMap<String, ClassificationAttributeValue> hClassificationAttributeValuesResult = new HashMap();

		// --- Hole den Datentyp
		final EnumerationValue enumCAAtype = classattributeassignment.getAttributeType();

		// --- Hole je nach Type die Werte
		if (enumCAAtype.getCode().equals(GeneratedCatalogConstants.Enumerations.ClassificationAttributeTypeEnum.STRING))
		{

			// --- Hole die m�glichen Werte eines ClassAttributeAssignment 
			final HashMap<String, ClassificationAttributeValue> hClassificationAttributeValues = getClassificationAttributeValues(classattributeassignment);

			// --- Hole die ausgew�hlten features
			final TypedFeature<String> features = Feature.loadTyped(product, classattributeassignment);
			final List<FeatureValue<String>> featurevalues = features.getValues();

			// --- Hole alle CAV hierzu
			for (final Iterator it_featurevalues = featurevalues.iterator(); it_featurevalues.hasNext();)
			{
				final FeatureValue<String> featurevalue = (FeatureValue<String>) it_featurevalues.next();
				final ClassificationAttributeValue cav = hClassificationAttributeValues.get(featurevalue.getValue());
				if (cav == null)
				{
					System.out.println("cav=null, " + "featurevalue.getValue()=" + featurevalue.getValue());
				}
				hClassificationAttributeValuesResult.put(featurevalue.getValue(), cav);
			}

		}


		return hClassificationAttributeValuesResult;
	}


	/**
	 * Hole die ausgw�hlten (Typ-Boolean) eines Products / Values / oder Sets
	 * 
	 * @param product
	 * @param classattributeassignment
	 * @return 
	 */
	public Boolean getBooleanClassificationAttributeValuesByProduct(final Product product,
			final ClassAttributeAssignment classattributeassignment)
	{

		// --- Initalize
		Boolean oBoolValue = Boolean.FALSE;

		// --- Hole den Datentyp
		final EnumerationValue enumCAAtype = classattributeassignment.getAttributeType();

		// --- Hole je nach Type die Werte
		if (enumCAAtype.getCode().equals(GeneratedCatalogConstants.Enumerations.ClassificationAttributeTypeEnum.BOOLEAN))
		{

			final TypedFeature<Boolean> feature = Feature.loadTyped(product, classattributeassignment);
			if (feature.isEmpty())
			{
				oBoolValue = Boolean.FALSE;
			}
			else
			{
				final FeatureValue<Boolean> featurevalue = feature.getValue(0);
				oBoolValue = featurevalue.getValue();
			}
		}

		return oBoolValue;
	}

}
