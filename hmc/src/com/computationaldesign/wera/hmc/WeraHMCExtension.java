/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2010 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 * 
 *  
 */
package com.computationaldesign.wera.hmc;

import de.hybris.platform.catalog.jalo.classification.ClassificationAttribute;
import de.hybris.platform.core.PK;
import de.hybris.platform.hmc.AbstractEditorMenuChip;
import de.hybris.platform.hmc.AbstractExplorerMenuTreeNodeChip;
import de.hybris.platform.hmc.EditorTabChip;
import de.hybris.platform.hmc.HMCHelper;
import de.hybris.platform.hmc.extension.HMCExtension;
import de.hybris.platform.hmc.extension.MenuEntrySlotEntry;
import de.hybris.platform.hmc.generic.ClipChip;
import de.hybris.platform.hmc.generic.ToolbarActionChip;
import de.hybris.platform.hmc.util.action.ActionResult;
import de.hybris.platform.hmc.webchips.Chip;
import de.hybris.platform.hmc.webchips.DisplayState;
import de.hybris.platform.hmc.webchips.ExtraParamHandler;
import de.hybris.platform.hmc.webchips.Window;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloItemNotFoundException;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.jalo.type.ComposedType;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.computationaldesign.wera.jalo.WeraManager;
import com.computationaldesign.wera.jalo.WeraMedia;
import com.computationaldesign.wera.jalo.WeraProduct;
import com.computationaldesign.wera.jalo.WeraProductSet;


/**
 * Provides necessary meta information about the wera hmc extension.
 * 
 * 
 * @version ExtGen v4.1
 */
public class WeraHMCExtension extends HMCExtension implements ExtraParamHandler
{
	/** Edit the local|project.properties to change logging behavior (properties log4j.*). */
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(WeraHMCExtension.class.getName());

	/** Path to the resource bundles. */
	public final static String RESOURCE_PATH = "com.computationaldesign.wera.hmc.locales";


	/**
	 * @see HMCExtension#getTreeNodeChips(de.hybris.platform.hmc.webchips.DisplayState,
	 *      de.hybris.platform.hmc.webchips.Chip)
	 */
	@Override
	public List<AbstractExplorerMenuTreeNodeChip> getTreeNodeChips(final DisplayState displayState, final Chip parent)
	{
		return Collections.EMPTY_LIST;
	}

	/**
	 * @see HMCExtension#getMenuEntrySlotEntries(de.hybris.platform.hmc.webchips.DisplayState,
	 *      de.hybris.platform.hmc.webchips.Chip)
	 */
	@Override
	public List<MenuEntrySlotEntry> getMenuEntrySlotEntries(final DisplayState displayState, final Chip parent)
	{
		return Collections.EMPTY_LIST;
	}

	/**
	 * @see HMCExtension#getSectionChips(de.hybris.platform.hmc.webchips.DisplayState,
	 *      de.hybris.platform.hmc.generic.ClipChip)
	 */
	@Override
	public List<ClipChip> getSectionChips(final DisplayState displayState, final ClipChip parent)
	{
		return Collections.EMPTY_LIST;
	}

	@Override
	public List<EditorTabChip> getEditorTabChips(final DisplayState displayState, final AbstractEditorMenuChip parent)
	{
		return Collections.EMPTY_LIST;
	}

	/**
	 * @see HMCExtension#getToolbarActionChips(de.hybris.platform.hmc.webchips.DisplayState,
	 *      de.hybris.platform.hmc.webchips.Chip)
	 */
	@Override
	public List<ToolbarActionChip> getToolbarActionChips(final DisplayState displayState, final Chip parent)
	{
		return Collections.EMPTY_LIST;
	}

	@Override
	public ResourceBundle getLocalizeResourceBundle(final Locale locale)
	{
		return null;
	}

	@Override
	public String getResourcePath()
	{
		return RESOURCE_PATH;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.hmc.extension.HMCExtension#afterCreate(de.hybris.platform.jalo.Item,
	 * de.hybris.platform.hmc.webchips.DisplayState, java.util.Map, java.util.Map,
	 * de.hybris.platform.hmc.util.action.ActionResult)
	 */
	@Override
	public ActionResult afterCreate(final Item item, final DisplayState displayState, final Map initialValues, final Map values,
			final ActionResult actionResult)
	{
		LOG.info("afterCreate: item class = " + item.getClass());
		// YTODO Auto-generated method stub
		return super.afterCreate(item, displayState, initialValues, values, actionResult);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.hmc.extension.HMCExtension#beforeCreate(de.hybris.platform.jalo.type.ComposedType,
	 * de.hybris.platform.hmc.webchips.DisplayState, java.util.Map)
	 */
	@Override
	public ActionResult beforeCreate(final ComposedType itemType, final DisplayState displayState, final Map initialValues)
	{
		LOG.info("beforeCreate(): itemType = " + itemType.getCode());

		// YTODO Auto-generated method stub
		if (itemType.getCode().equals("WeraVarianteSet") || itemType.getCode().equals("WeraVarianteSetInSet"))
		{
			
			// --- Nacharbeit (z.B. Bilder archivieren / OutputControl pflegen)
			Item variant       = (Item)initialValues.get("weravariants");
			String strCodeBase = (String)initialValues.get("code");
			String strCodeNew  = "";
			try {
				LOG.info("beforeCreate(): setting up wrapper object for WeraVarianteSet or WeraVarianteSetInSet.");
				strCodeNew = "BASE_" + strCodeBase + "_VAR_" + (String)variant.getAttribute("code");
				LOG.info("beforeCreate(): code = "+strCodeNew);
			} catch ( Exception e ) {
				final long milli = java.lang.System.currentTimeMillis();
				strCodeNew = "WVS-" + milli;
			}

			initialValues.put("code", strCodeNew );
		}
		return super.beforeCreate(itemType, displayState, initialValues);
	}

	/**
	 * Called before an item is "saved" in the hybris Management Console. You can place own logic here and give a veto if
	 * you do not want this item to be saved. The maps currentValues and initialValues can be used to implement filtering
	 * of values, which must not be saved.
	 * 
	 * @param item
	 *           Item which attributes should be saved.
	 * @param displayState
	 *           DisplayState
	 * @param currentValues
	 *           Map containing all current attribute values. (Mapping: AttributeQualifier -> Value) Note:
	 *           AttributeQualifier should always be treated caseinsensitive. Use
	 *           <code>de.hybris.jakarta.collections.CaseInsensitiveMap</code> or other caseinsensitive implementations.
	 * @param initialValues
	 *           Map containing all initial attribute values. (Mapping: AttributeQualifier -> Value) Note:
	 *           AttributeQualifier should always be treated caseinsensitive. Use
	 *           <code>de.hybris.jakarta.collections.CaseInsensitiveMap</code> or other caseinsensitive implementations.
	 * @return ActionResult with processing informations ( action processed successful etc )
	 */
	@Override
	public ActionResult beforeSave(final Item item, final DisplayState displayState, final Map currentValues,
			final Map initialValues)
	{
		LOG.info("beforeSave(): item = " + item);
		LOG.info("beforeSave(): class = " + item.getClass().toString());
		return new ActionResult(ActionResult.OK, false);
	}

	/**
	 * Called after an item is "saved" in the hybris Management Console. You can place own business logic here after the
	 * item has been saved, e.g. updating 3rd party search indizes e.g.
	 * 
	 * @param item
	 *           Item which attributes should be saved.
	 * @param displayState
	 *           DisplayState
	 * @param values
	 *           Map containing all attribute values which have been saved. (Mapping: AttributeQualifier -> Value) Note:
	 *           AttributeQualifier should always be treated caseinsensitive. Use
	 *           <code>de.hybris.jakarta.collections.CaseInsensitiveMap</code> or other caseinsensitive implementations.
	 * @param actionResult
	 *           The <code>ActionResult</code> object, containing the status of the current action.
	 * @return ActionResult with processing informations ( action processed successful etc )
	 */
	@Override
	public ActionResult afterSave(final Item item, final DisplayState displayState, final Map values,
			final ActionResult actionResult)
	{
		LOG.info("afterSave() called with values.size() = "+values.size()+", item.getClass = "+item.getClass());

		boolean bAfterSave = false;
		// --- Typen abhängige Nachbearbeitung (MEDIA)
		if (!bAfterSave && item instanceof WeraMedia)
		{
			// --- Je nach Verknüfung eine andere Variante vorsehen
			LOG.info("afterSave(): item is WeraMedia");
			((WeraMedia) item).afterSave(values);
			bAfterSave = true;
		}

		if (!bAfterSave && item instanceof WeraProductSet)
		{
			// --- Nacharbeit (z.B. Bilder archivieren / OutputControl pflegen)
			LOG.info("afterSave(): item is WeraProductSet");
			((WeraProductSet) item).afterSave(values);
			bAfterSave = true;
		}

		// --- Überarbeiten der zugeordneten Bilder, beim Speichern eines WeraProdukts
		if (!bAfterSave && item instanceof WeraProduct)
		{
			// --- Nacharbeit (z.B. Bilder archivieren / OutputControl pflegen)
			LOG.info("afterSave(): item is WeraProduct");
			((WeraProduct) item).afterSave(values);
			bAfterSave = true;
		}

		// --- Nur reagieren wenn sich etwas ge�ndert hat.
		if (values.size() > 0)
		{
			if (item instanceof ClassificationAttribute)
			{
				// --- Bearbeite Bilder
				LOG.info("afterSave(): item is ClassificationAttribute");
				WeraManager.getInstance().checkMedia(item, values);
			}
		}

		return actionResult;
	}

	@Override
	public void handleExtraParams(final Map extraParams)
	{
		LOG.info("handleExtraParams(): extraParams: " + extraParams);
		if (extraParams.containsKey("openmyitem") && !((Collection) extraParams.get("openmyitem")).isEmpty())
		{
			// find item for the given pk
			final String pk = (String) ((Collection) extraParams.get("openmyitem")).iterator().next();
			if (!"".equals(pk))
			{
				try
				{
					final Item item = JaloSession.getCurrentSession().getItem(PK.parse(pk));
					HMCHelper.getHMCContextFor(Window.getCurrent()).openItem(item, true);
				}
				catch (final JaloItemNotFoundException e)
				{
					LOG.error("Could not open item with pk '" + pk + "'!");
					e.printStackTrace();
				}
				catch (final IllegalArgumentException e)
				{
					LOG.error("Could not open item with pk '" + pk + "'!");
					e.printStackTrace();
				}
				catch (final JaloSecurityException e)
				{
					LOG.error("Could not open item with pk '" + pk + "'!");
					e.printStackTrace();
				}
			}
		}
	}

}
