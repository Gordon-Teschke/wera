package com.computationaldesign.wera.jalo;

import de.hybris.platform.catalog.jalo.classification.ClassAttributeAssignment;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;


//Wrapperklassen f�r formatierte Ausgabe der Bildpfade
//BluestoreExportFormatter formatiert Bildpfade komplett mit TIF bzw. GIF Pfaden.
//BluestoreExportFormatter formatiert Bildpfade komplett mit TIF bzw. GIF Pfaden.
//Der Exporter muss �ber setExportFormatter ( "BluestoreExportFormatter" )
//vom Bluestore aus gesetzt werden.
class BluestoreExportFormatter extends ExportFormatter
{
	/** Used logger instance. */
	private static final Log LOG = LogFactory.getLog(BluestoreExportFormatter.class);

	// use cmyk as of 2011-11-10 to avoid bad colors
	private final String COLORSPACE_TIF = "cmyk";
	private final String COLORSPACE_JPG = "cmyk";

	@Override
	public String formatIconPath(final String s)
	{
		if (s != null)
		{
			//LOG.info("formatIconPath() called for " + s);
			if (s.startsWith("merkmale/"))
			{
				return s.replace("merkmale/", "merkmale/jpg/").replace(".eps", ".jpg");
			}
			if (s.startsWith("pictures/sb_"))
			{
				return s.replace("pictures/sb_", "icons/jpg/sb_").replace(".eps", ".jpg");
			}
			if (s.startsWith("icons/"))
			{
				return s.replace("icons/", "icons/tif/").replace(".eps", ".tif");
			}
			if (s.startsWith("features/"))
			{
				return s.replace("features/", "features/tif/").replace(".eps", ".tif");
			}
			return s;
		}
		// return "merkmale/jpg/code_schwarz.jpg";
		return "";
	}

	@Override
	public String formatPicturePath(final String s)
	{
		//LOG.info("formatPicturePath() called for " + s);
		// pictures/935_sph.eps => pictures/tif/935_sph_rgb_300dpi.tif
		if (s != null)
		{
			//return s.replace("pictures/", "pictures/tif/").replace(".eps", "_" + COLORSPACE_TIF + "_300dpi.tif");
			return s.replace("pictures/", "pictures/jpg/").replace(".eps", "_" + COLORSPACE_JPG + "_300dpi.jpg");
		}
		return "";
	}

	@Override
	public String formatSetContent(final String s)
	{
		/*
		 * <cNoBreak:1> ;<cNoBreak:> <cNoBreak:> <ASCII-WIN>\r\n<Version:4><ParaStyle:links ohne
		 * Einzug><CharStyle:wera\\_tabelle> <CharStyle:>
		 */
		//LOG.info("formatSetContent() called for " + s);
		if (s != null)
		{
			return s.replaceAll("\r\n", "").replace("<cNoBreak:1>", "").replace(";<cNoBreak:>", ";").replace("<cNoBreak:>", "")
					.replace("<ASCII-WIN>", "").replace("<Version:4><ParaStyle:links ohne Einzug><CharStyle:wera\\_tabelle>", "")
					.replace("<CharStyle:>", "").replaceAll("<cPosition:Superscript>", "").replaceAll("<cPosition:>", "");
		}
		return "";
	}

	@Override
	public String formatDescription(final String s)
	{
		//LOG.info("formatDescription() called for " + s);
		if (s != null)
		{
			return s.replaceAll("##[^#]+##", "");
		}
		return "";
	}

	@Override
	public String formatFileDatum(final String strLang, final String strDatum)
	{
		return strLang + strDatum;
	}

	@Override
	public String formatXmlFilePrefix(final String s)
	{
		//LOG.info("formatXmlFilePrefix() called for " + s);
		if (s != null)
		{
			return s;
		}
		return "";
	}

	@Override
	public String formatIndesignSetSupS()
	{
		return formatSetSupS();
	}

	@Override
	public String formatIndesignSetSupE()
	{
		return formatSetSupE();
	}

	@Override
	public String formatSetSupS()
	{
		return "##sup##";
	}

	@Override
	public String formatSetSupE()
	{
		return "##e_sup##";
	}

	@Override
	public String formatSetSubS()
	{
		return "##down##";
	}

	@Override
	public String formatSetSubE()
	{
		return "##e_down##";
	}

	@Override
	public String formatCode(final String strLagerNr, final String strCode, final String strVarNr)
	{
		//LOG.info("formatCode() called.");
		return strLagerNr + "#b#" + strCode + "#e_b#" + strVarNr;
	}

	@Override
	public String formatCodeUS(final String strLagerNr, final String strCode, final String strVarNr)
	{
		//LOG.info("formatCodeUS() called.");
		return strLagerNr + "#b#" + strCode + "#e_b#" + strVarNr;
		/*
		 * String strOutput = "<ASCII-WIN>\r\n"; strOutput +=
		 * "<Version:4><FeatureSet:InDesign-Roman><ColorTable:=<Black:COLOR:CMYK:Process:0.000000,0.000000,0.000000,1.000000><Black \\(CMYK\\):COLOR:CMYK:Process:0.000000,0.000000,0.000000,1.000000>>\r\n"
		 * ; strOutput +=
		 * "<DefineCharStyle:wera\\_tabelle=<Nextstyle:wera\\_tabelle><cColor:Black \\(CMYK\\)><cTypeface:LT 47 Light Condensed><cSize:7.000000><cFont:Helvetica Neue LT>>\r\n"
		 * ; strOutput +=
		 * "<DefineParaStyle:links ohne Einzug=<Nextstyle:links ohne Einzug><cColor:Black \\(CMYK\\)><cTypeface:LT 47 Light Condensed><cSize:7.000000><cFont:Helvetica Neue LT><pRuleAboveColor:Black \\(CMYK\\)><pRuleAboveStroke:0.000000><pRuleBelowColor:Black \\(CMYK\\)><pRuleBelowStroke:0.000000><pRuleAboveGapColor:Black \\(CMYK\\)><pRuleBelowGapColor:Black \\(CMYK\\)>>\r\n"
		 * ; strOutput +=
		 * "<DefineParaStyle:rechts Einzug 2.1mm=<Nextstyle:rechts Einzug 2.1mm><cColor:Black \\(CMYK\\)><cTypeface:LT 47 Light Condensed><cSize:7.000000><pHyphenationLadderLimit:0><pRightIndent:5.952756><pMinCharBeforeHyphen:3><pHyphenateCapitals:0><pShortestWordHyphenated:6><pHyphenationZone:0.000000><cFont:Helvetica Neue LT><pDesiredWordSpace:1.100000><pMaxWordSpace:2.500000><pMinWordSpace:0.850000><pMaxLetterspace:4.000000><pRuleAboveColor:Black \\(CMYK\\)><pRuleAboveStroke:0.000000><pRuleBelowColor:wera\\_icons\\_bg><pRuleBelowStroke:0.198425><pRuleBelowOffset:2.834646><pRuleBelowStrokeType:CannedDash3x2><pRuleAboveGapColor:Black \\(CMYK\\)><pRuleBelowGapColor:white \\(CMYK\\)><pTextAlignment:Right>>\r\n"
		 * ; strOutput +=
		 * "<DefineParaStyle:center\\_wera=<Nextstyle:center\\_wera><cColor:Black \\(CMYK\\)><cTypeface:LT 47 Light Condensed><cSize:7.000000><cFont:Helvetica Neue LT><pRuleAboveColor:Black \\(CMYK\\)><pRuleAboveStroke:0.000000><pRuleBelowColor:Black \\(CMYK\\)><pRuleBelowStroke:5.669291><pRuleBelowOffset:0.850394><pRuleBelowStrokeType:Dashed><pRuleAboveGapColor:Black \\(CMYK\\)><pRuleBelowGapColor:Black \\(CMYK\\)><pTextAlignment:Center>>\r\n"
		 * ; strOutput += "<ParaStyle:links ohne Einzug><cSize:6.000000>" + strLagerNr + "<cSize:><cTypeface:Bold>" +
		 * strCode + "<cTypeface:><cSize:6.000000>" + strVarNr + "<cSize:>";
		 * 
		 * return strOutput;
		 */
	}

	//public String formatCode ( String strLagerNr, String strCode, String strVarNr ) {
	//	return strLagerNr+"<b>"+strCode+"</b>"+strVarNr;
	//}

	//public String formatCodeUS ( String strLagerNr, String strCode, String strVarNr ) {

	//	return strLagerNr+"<b>"+strCode+"</b>"+strVarNr;
	//}
}


public class WeraBluestoreExport extends WeraProduktExport
{

	/** Used logger instance. */
	private static final Log LOG = LogFactory.getLog(WeraBluestoreExport.class);

	public WeraBluestoreExport()
	{
		super();
		// TODO Auto-generated constructor stub
		setExportFormatter();
                
                // --- Auslaufartikel als Fussnoten export
                setM_bExportDiscontinuedItemAsFootnote ( true );
	}

	@Override
	public void setExportFormatter()
	{
		// TODO Auto-generated method stub
		this.m_oExportFormatter = new BluestoreExportFormatter();
	}

	@Override
	protected Element _initImage(final WeraMedia weramedia, final String strDirectory, String cProductNr,
			final boolean bProductBild, String strTagName)
	{
		//LOG.info("_initImage() called.");
		output("s._initImage=", 2);

		// --- <File Id="0" MimeType="image/gif" Media="INTERNET" FileName="\images\pictos\aussen_sechskant_durchmesser.gif" Thumbnail="" ColorDepth="" Height="" Size="" Software="" Author="" Sequence="10" MediaName="INTERNET" Keywords="">
		final Element oFile = new Element("File");
		m_iOffset++;
		oFile.setAttribute("PrintFileName", "I" + new Integer(m_iOffset).toString() + ".eps");
		if (weramedia != null)
		{

			//oFile.setAttribute("PrintMimeType", "image/eps" );
			if (bProductBild)
			{
				// --- Produktbild
				cProductNr = WeraProduct.s_normalizeFilenameForImageLookup(cProductNr);
				oFile.setAttribute("PrintFileName", getExportFormatter().formatPicturePath(strDirectory + "/" + cProductNr + ".eps"));
			}
			else
			{
				String strRealName = weramedia.getRealFileName();
				if (strRealName != null)
				{
					strRealName = strRealName.replace("\\", "/");
					final Pattern p = Pattern.compile("/");
					final String[] aRealName = p.split(strRealName);
					if (aRealName.length > 1)
					{
						strRealName = aRealName[aRealName.length - 1];
					}
					strRealName = strRealName.replaceAll(".jpg", ".eps");
					strRealName = strRealName.replaceAll(".gif", ".eps");
					oFile.setAttribute("PrintFileName", getExportFormatter().formatIconPath(strDirectory + "/" + strRealName));
				}
			}
		}
		else
		{
			// --- Bereinige ProduktNr f�r Bildsuche
			if (!cProductNr.equals(""))
			{

				cProductNr = WeraProduct.s_normalizeFilenameForImageLookup(cProductNr);
				oFile.setAttribute("PrintFileName", getExportFormatter().formatPicturePath(strDirectory + "/" + cProductNr + ".eps"));

			}
			else
			{
				m_iOffset++;
				oFile.setAttribute("PrintFileName", "I" + new Integer(m_iOffset).toString() + ".eps");
			}
		}

		output("e._initImage=", 2);
		//LOG.info("_initImage() finished.");
		return oFile;
	}

	@Override
	protected Element _initMerkmalIcon(final ClassAttributeAssignment classattributeassignment, final Outputcontrol outputcontrol,
			final WeraMedia weramedia, final String strHashCode, final String strDirectory, final String strOrder)
	{

		//LOG.info("_initMerkmalIcon() called.");
		// --- Initialize
		ClassificationAttribute classificationAttribute = null;
		if (classattributeassignment != null)
		{
			classificationAttribute = classattributeassignment.getClassificationAttribute();
		}

		// --- Debug
		if (strHashCode != null)
		{
			output("s._initMerkmalIcon=" + strHashCode, 2);
		}
		else
		{
			output("s._initMerkmalIcon=", 2);
		}


		// --- Initialize
		final Element oMotive = new Element("Motive");
		WeraMedia icon = null;
		String strIconCode = strHashCode;
		String strIconRealName = "";
		String strCaCode = "";
		Collection icons = new ArrayList();
		if (classificationAttribute != null)
		{
			strCaCode = classificationAttribute.getCode();
		}

		// --- �bernehme �bergegebenes Object
		if (weramedia != null)
		{
			icons.add(weramedia);
		}
		else
		{
			// --- Hole Icon aus Ausgabesteuerung
			if (outputcontrol != null)
			{
				icons = (Collection) getAttribute(outputcontrol, "icons");
			}
			if (icons == null || icons.size() == 0)
			{
				// --- Fallback aus CA
				if (classificationAttribute != null)
				{
					icons = (Collection) getAttribute(classattributeassignment, "icons");
				}
			}

		}

		// --- Wurde ein Icon gefunden
		if (icons != null && icons.size() > 0)
		{

			// --- Initialisiere Daten
			icon = (WeraMedia) icons.iterator().next();
			strIconCode = icon.getCode() + "_" + strOrder;
			strIconRealName = icon.getRealFileName();
		}
		if (strIconRealName != null)
		{
			strIconRealName = strIconRealName.replaceAll(".jpg", ".eps");
			strIconRealName = strIconRealName.replaceAll(".gif", ".eps");
		}
		else
		{
			strIconRealName = "";
		}

		// --- Neues Element anlegen
		// --- <Motive Id="0" Type="ATTRIBUTE_IMAGE" LinkType="ATTRIBUTE_IMAGE" RefCode="ATTR_aussen_sechskant_durchmesser" Thumbnail="" Sequence="0" TypeName="ATTRIBUTE_IMAGE" LinkTypeName="ATTRIBUTE_IMAGE" Name="Leertext" Keywords="">
		output("s._initMerkmalIcon.strIconCode=" + strIconCode, 2);
		output("s._initMerkmalIcon.strOrder=" + strOrder, 2);
		output("s._initMerkmalIcon.strIconRealName=" + strIconRealName, 2);
		output("s._initMerkmalIcon.strCaCode=" + strCaCode, 2);
		oMotive.setAttribute("Id", strIconCode);
		oMotive.setAttribute("Type", "ATTRIBUTE_IMAGE");
		oMotive.setAttribute("LinkType", "ATTRIBUTE_IMAGE");
		oMotive.setAttribute("RefCode", strIconCode);
		//oMotive.setAttribute("Thumbnail",    "" );
		oMotive.setAttribute("Sequence", strOrder);
		oMotive.setAttribute("TypeName", strIconRealName);
		oMotive.setAttribute("LinkTypeName", strCaCode);
		oMotive.setAttribute("Name", strIconRealName);
		//oMotive.setAttribute("Keywords",     "" );

		// --- Attribute-Header-List (ICON)
		if (strHashCode != null)
		{
			final HashMap hashCA = (HashMap) m_hashAttributeHeaderList.get(strHashCode);
			if (hashCA != null)
			{
				hashCA.put("FileName", "");
				hashCA.put("PrintFileName", "");
				if (icon != null)
				{
					hashCA.put("FileName", icon.getFileName());

					hashCA.put("PrintFileName", getExportFormatter().formatIconPath(strDirectory + "/" + strIconRealName));
					// System.out.println("Calling getExportFormatter() getting " + getExportFormatter().formatMerkmalePath ( strDirectory + "/" + strIconRealName) );
					m_hashAttributeHeaderList.put(strHashCode, hashCA);
				}
			}
		}


		// --- <File-List>
		final Element oFileList = new Element("File-List");

		// --- <File Id="0" MimeType="image/gif" Media="INTERNET" FileName="\images\pictos\aussen_sechskant_durchmesser.gif" Thumbnail="" ColorDepth="" Height="" Size="" Software="" Author="" Sequence="10" MediaName="INTERNET" Keywords="">
		final Element oFile = _initImage(icon, strDirectory, "", false, null);
		strIconRealName = oFile.getAttributeValue("PrintFileName");
		output("s._initMerkmalIcon.oFile=" + oFile, 2);
		output("s._initMerkmalIcon.strIconRealName=" + strIconRealName, 2);
		if (strIconRealName == null)
		{
			strIconRealName = "";
		}
		output("s._initMerkmalIcon.strIconRealName=" + strIconRealName, 2);
		output("s._initMerkmalIcon.oFile=" + oFile, 2);
		oMotive.setAttribute("TypeName", strIconRealName);
		oMotive.setAttribute("Name", strIconRealName);
		oFileList.addContent(oFile);

		// --- Setze zusammen
		oMotive.addContent(oFileList);

		// --- Debug
		output("e._initMerkmalIcon=", 2);

		//LOG.info("_initMerkmalIcon() finished.");
		return oMotive;
	}
      
        /**
         * Version f�r Weraliveausgabe, keine �nderung der Datenstruktur
         * @param iCntIcon
         * @param iOrder
         * @param oIcon1
         * @param oTexList
         * @return Element
         */
        @Override
        protected Element _assign2TextList ( int iCntIcon, int iOrder, Element oIconLinks, Element textXML, Element oTexList ) {
            
            // --- 1. - 2. icon
            textXML.addContent(oIconLinks);

            return oTexList;
        }
}
