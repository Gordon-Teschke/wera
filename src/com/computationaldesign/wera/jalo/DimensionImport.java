package com.computationaldesign.wera.jalo;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.enums.ClassificationAttributeTypeEnum;
import de.hybris.platform.catalog.enums.ClassificationAttributeVisibilityEnum;
import de.hybris.platform.catalog.jalo.classification.ClassAttributeAssignment;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttribute;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttributeValue;
import de.hybris.platform.catalog.jalo.classification.ClassificationClass;
import de.hybris.platform.catalog.jalo.classification.util.Feature;
import de.hybris.platform.catalog.jalo.classification.util.FeatureContainer;
import de.hybris.platform.catalog.jalo.classification.util.TypedFeature;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.catalog.model.classification.ClassAttributeAssignmentModel;
import de.hybris.platform.catalog.model.classification.ClassificationAttributeModel;
import de.hybris.platform.catalog.model.classification.ClassificationAttributeValueModel;
import de.hybris.platform.catalog.model.classification.ClassificationClassModel;
import de.hybris.platform.catalog.model.classification.ClassificationSystemVersionModel;
import de.hybris.platform.category.CategoryService;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.core.PK;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.jalo.ConsistencyCheckException;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import de.hybris.platform.servicelayer.exceptions.ModelSavingException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.variants.model.VariantProductModel;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.computationaldesign.wera.model.OutputcontrolModel;
import com.computationaldesign.wera.model.WeraProductModel;
import com.computationaldesign.wera.model.WeraProductSetModel;
import com.computationaldesign.wera.model.WeraProductSetinSetModel;
import com.computationaldesign.wera.model.WeraVarianteModel;




public class DimensionImport
{

	public class DIActionLogger
	{
		public int iNumberOfNewAttributeAnnotations;
		public int iNumberOfReusableAttributeAnnotations;
		public int iNumberOfValidNewAttributeAnnotations;
		public int iNumberOfValidReusableAttributeAnnotations;
		public String sProduct;

		public Collection<String> colErrorsNewAttributeAnnotations;
		public Collection<String> colErrorsReusableAttributeAnnotations;
		public Collection<String> colOutputcontrolsSetToAssignedToVariant;

		public Collection<String> colClassedCreated;
		public Collection<String> colClassAttributeAssignmentsCreated;
		public Collection<String> colCustomValuesCreated;
		public Collection<String> colFeatureValuesCreated;

		public String sLogPath;
		public String sLogFilename;

		public Writer fw;

		public DIActionLogger()
		{
			init();
		}

		public void clear()
		{
			iNumberOfNewAttributeAnnotations = 0;
			iNumberOfReusableAttributeAnnotations = 0;
			iNumberOfValidNewAttributeAnnotations = 0;
			iNumberOfValidReusableAttributeAnnotations = 0;
			sProduct = "";
			colErrorsNewAttributeAnnotations = new ArrayList();
			colErrorsReusableAttributeAnnotations = new ArrayList();
			colOutputcontrolsSetToAssignedToVariant = new ArrayList();

			colClassedCreated = new ArrayList();
			colClassAttributeAssignmentsCreated = new ArrayList();
			colCustomValuesCreated = new ArrayList();
			colFeatureValuesCreated = new ArrayList();

			// sLogPath = "C:\\home\\hybris\\log";
			sLogPath = "/home/hybris/log";
			sLogFilename = "dimension-import.log";
		}

		public void init()
		{
			clear();
			try
			{
				fw = new FileWriter(sLogPath + "/" + sLogFilename);
			}
			catch (final IOException e)
			{
				System.err.println("Konnte Datei " + sLogPath + "/" + sLogFilename + " nicht erstellen");
			}
		}

		public void addErrorNewAttributes(final String sError)
		{
			this.colErrorsNewAttributeAnnotations.add(sError);
		}

		public void addErrorReusableAttributes(final String sError)
		{
			this.colErrorsReusableAttributeAnnotations.add(sError);
		}

		public void addOutputcontrolsSetToAssignedToVariant(final String sString)
		{
			this.colOutputcontrolsSetToAssignedToVariant.add(sString);
		}

		public void addClassesCreated(final String sString)
		{
			this.colClassedCreated.add(sString);
		}

		public void addClassAttributeAssignmentsCreated(final String sString)
		{
			this.colClassAttributeAssignmentsCreated.add(sString);
		}

		public void addCustomValuesCreated(final String sString)
		{
			this.colCustomValuesCreated.add(sString);
		}

		public void addFeatureValuesCreated(final String sString)
		{
			this.colFeatureValuesCreated.add(sString);
		}

		public void setProduct(final String sNewProduct)
		{
			sProduct = sNewProduct;
		}

		public void flush()
		{

			try
			{
				fw.append("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
				fw.append(System.getProperty("line.separator"));

				fw.append("Processing product >" + sProduct + "<");
				fw.append(System.getProperty("line.separator"));

				fw.append("   Valid new attribute annotations: (" + iNumberOfValidNewAttributeAnnotations + "/"
						+ iNumberOfNewAttributeAnnotations + ")");
				fw.append(System.getProperty("line.separator"));

				fw.append("   Valid reusable attribute annotations: (" + iNumberOfValidReusableAttributeAnnotations + "/"
						+ iNumberOfReusableAttributeAnnotations + ")");
				fw.append(System.getProperty("line.separator"));

				if (colOutputcontrolsSetToAssignedToVariant.size() > 0)
				{
					fw.append("   Outputconrols set to ASSIGNED_TO_VARIANT:");
					fw.append(System.getProperty("line.separator"));
					for (final String sString : colOutputcontrolsSetToAssignedToVariant)
					{
						fw.append("      * " + sString);
						fw.append(System.getProperty("line.separator"));
					}

				}
				else
				{
					fw.append("   Outputconrols set to ASSIGNED_TO_VARIANT: None");
					fw.append(System.getProperty("line.separator"));
				}


				if (colClassedCreated.size() > 0)
				{
					fw.append("   New classed created:");
					fw.append(System.getProperty("line.separator"));
					for (final String sString : colClassedCreated)
					{
						fw.append("      * " + sString);
						fw.append(System.getProperty("line.separator"));
					}

				}
				else
				{
					fw.append("   New classed created: None");
					fw.append(System.getProperty("line.separator"));
				}

				if (colClassAttributeAssignmentsCreated.size() > 0)
				{
					fw.append("   New class/attribute assignments created:");
					fw.append(System.getProperty("line.separator"));
					for (final String sString : colClassAttributeAssignmentsCreated)
					{
						fw.append("      * " + sString);
						fw.append(System.getProperty("line.separator"));
					}

				}
				else
				{
					fw.append("   New class/attribute assignments created: None");
					fw.append(System.getProperty("line.separator"));
				}

				if (colCustomValuesCreated.size() > 0)
				{
					fw.append("   New custom values created:");
					fw.append(System.getProperty("line.separator"));
					for (final String sString : colCustomValuesCreated)
					{
						fw.append("      * " + sString);
						fw.append(System.getProperty("line.separator"));
					}

				}
				else
				{
					fw.append("   New custom values created: None");
					fw.append(System.getProperty("line.separator"));
				}

				if (colFeatureValuesCreated.size() > 0)
				{
					fw.append("   New feature values created:");
					fw.append(System.getProperty("line.separator"));
					for (final String sString : colFeatureValuesCreated)
					{
						fw.append("      * " + sString);
						fw.append(System.getProperty("line.separator"));
					}

				}
				else
				{
					fw.append("   New feature values created: None");
					fw.append(System.getProperty("line.separator"));
				}

				if (colErrorsNewAttributeAnnotations.size() > 0)
				{
					fw.append("   Errors new attribute annotations:");
					fw.append(System.getProperty("line.separator"));

					for (final String sError : colErrorsNewAttributeAnnotations)
					{
						fw.append("      * " + sError);
						fw.append(System.getProperty("line.separator"));
					}
				}
				else
				{
					fw.append("   Errors new attribute annotations: None");
					fw.append(System.getProperty("line.separator"));
				}

				if (colErrorsReusableAttributeAnnotations.size() > 0)
				{
					fw.append("   Errors reusable attribute annotations:");
					fw.append(System.getProperty("line.separator"));

					for (final String sError : colErrorsReusableAttributeAnnotations)
					{
						fw.append("      * " + sError);
						fw.append(System.getProperty("line.separator"));
					}
				}
				else
				{
					fw.append("   Errors reusable attribute annotations: None");
					fw.append(System.getProperty("line.separator"));
				}

				fw.flush();

			}
			catch (final IOException e)
			{
				LOG.error("flush(): Cannot append text to logfile.");
				e.printStackTrace();
			}
		}

		public void close()
		{
			if (fw != null)
			{
				try
				{
					fw.close();
				}
				catch (final IOException e)
				{
					e.printStackTrace();
				}
			}

		}

	}


	private static final Logger LOG = Logger.getLogger(DimensionImport.class.getName());

	private String m_sFilepath = null;

	private static final int DI_MODE_SCAN_FOR_IMPORT_HEADER = 0;
	private static final int DI_MODE_READ_HEADER_2 = 1;
	private static final int DI_MODE_READ_HEADER_3 = 2;
	private static final int DI_MODE_READ_HEADER_4 = 3;
	private static final int DI_MODE_READ_PRODUCT_BLOCK = 4;

	private static final int DI_TYPE_IMPORT_NONE = 0;
	private static final int DI_TYPE_IMPORT_NEW_ATTRIBUTE = 1;
	private static final int DI_TYPE_IMPORT_REUSE_ATTRIBUTE = 2;


	private static final int TYPE_VARIANT = 0;
	private static final int TYPE_SET = 1;

	private static final boolean USE_DIMENSION_CLASS = true;
	private static final String DIMENSIONEN_CLASS_NAME = "DIMENSIONEN";


	final protected String MIGRATOR_DB_NAME = "wera_v4";
	final protected String MIGRATOR_DB_USER = "root";
	// final protected String MIGRATOR_DB_PASS = "wera";
	final protected String MIGRATOR_DB_PASS = "w3r4r00t";


	private final ProductService m_productService = (ProductService) Registry.getGlobalApplicationContext().getBean(
			"defaultProductService");
	private final CatalogVersionService m_catalogVersionService = (CatalogVersionService) Registry.getGlobalApplicationContext()
			.getBean("defaultCatalogVersionService");
	private final CategoryService m_categoryService = (CategoryService) Registry.getGlobalApplicationContext().getBean(
			"defaultCategoryService");
	private final ModelService m_modelService = (ModelService) Registry.getGlobalApplicationContext().getBean("modelService");
	private final FlexibleSearchService m_flexibleSearchService = (FlexibleSearchService) Registry.getGlobalApplicationContext()
			.getBean("flexibleSearchService");

	private final Map<String, String> mAttributeId2Name = new HashMap();

	private final DIActionLogger actionLogger = new DIActionLogger();

	private final String sUnknownAttributeName = "UNBENANNT";

	protected Connection con = null;

	public DimensionImport()
	{
		// TODO Auto-generated constructor stub
		mAttributeId2Name.put("_AA031f001", "Höhe");
		mAttributeId2Name.put("_AA040f001", "Breite");
		mAttributeId2Name.put("_AA081f001", "Gesamtlänge");

	}

	public String getBasecodeForAmbiguousVariant(final String sVariant)
	{
		final Map mVariant2base = new HashMap();
		String sBase = null;
		mVariant2base.put("005655", "247");
		mVariant2base.put("006105", "160 i VDE");
		mVariant2base.put("006120", "160 i VDE");
		mVariant2base.put("006152", "162 i PH VDE");
		mVariant2base.put("006154", "162 i PH VDE");
		mVariant2base.put("008710", "350 PH");
		mVariant2base.put("008720", "350 PH");
		mVariant2base.put("018274", "932 A");
		mVariant2base.put("032001", "3335");
		mVariant2base.put("032004", "3335");
		mVariant2base.put("032005", "3334");
		mVariant2base.put("032007", "3334");
		mVariant2base.put("032020", "3350 PH");
		mVariant2base.put("032021", "3350 PH");
		mVariant2base.put("032022", "3350 PH");
		mVariant2base.put("032052", "3367 TX");
		mVariant2base.put("032053", "3367 TX");
		mVariant2base.put("032054", "3367 TX");
		mVariant2base.put("032055", "3367 TX");
		mVariant2base.put("066606", "873/1 Five Lobe");
		mVariant2base.put("066607", "873/1 Five Lobe");
		mVariant2base.put("066611", "873/1 Five Lobe");
		mVariant2base.put("066612", "873/1 Five Lobe");
		mVariant2base.put("072550", "2095 S/2096 S/2170 S/2270 S");
		mVariant2base.put("072555", "2095 S/2096 S/2170 S/2270 S");
		mVariant2base.put("072905", "2095 S/2096 S/2170 S/2270 S");
		mVariant2base.put("073205", "2095 S/2096 S/2170 S/2270 S");
		mVariant2base.put("110007", "335");
		mVariant2base.put("340330", "334");

		if (mVariant2base.containsKey(sVariant))
		{
			sBase = (String) mVariant2base.get(sVariant);
		}
		return sBase;
	}


	public boolean updateLBHDB()
	{
		final boolean rval = false;

		final String sComposedTypesQuery = "select * from lbh_temp as lbh order by lbh.code";

		final HashMap<String, String> hMapOfVariantRepresentatives = new HashMap();

		final WeraManager wm = WeraManager.getInstance();

		Statement stmt;
		try
		{

			final String sClearQuery = "update lbh_temp set sixdigitcode=null, base=null, remark=null, variantrepresent=null";
			stmt = con.createStatement();
			stmt.executeUpdate(sClearQuery);

			stmt = con.createStatement();
			final ResultSet rs = stmt.executeQuery(sComposedTypesQuery);

			final CatalogVersionModel csvm = getCatalogVersion("weracatalog", "weramaster");

			final HashMap hPSM = getAllProductSets();

			while (rs.next())
			{
				final String sCode = rs.getString("lbh.code");

				String sBaseCode = "0";
				String sRemark = "";
				String sVariantrepresent = "";

				String sixdigitcode = "";
				String sLagerNr = "";

				boolean bCodeIsAmbiguous = false;

				if (sCode.length() == 11 && sCode.startsWith("05"))
				{
					sixdigitcode = sCode.substring(2, 8);
					sLagerNr = sCode.substring(8);

					LOG.info("updateLBHDB(): Processing article " + sixdigitcode + " (" + sLagerNr + ")");

					if (hMapOfVariantRepresentatives.containsKey(sixdigitcode))
					{
						LOG.info("updateLBHDB(): " + sixdigitcode + " already covered.");
						sVariantrepresent = hMapOfVariantRepresentatives.get(sixdigitcode);
						sRemark = "represented by " + sVariantrepresent;
					}
					else
					{
						if (csvm != null)
						{
							ProductModel pm = null;
							try
							{
								pm = this.m_productService.getProductForCode(csvm, sixdigitcode);

							}
							catch (final UnknownIdentifierException uie)
							{
								LOG.warn("bCheckAvailabilityOfProduct(): Cannot find variant product with code " + sixdigitcode
										+ " in weramaster.");
							}
							catch (final AmbiguousIdentifierException aie)
							{
								LOG.warn("bCheckAvailabilityOfProduct(): Ambiguous variant product code " + sixdigitcode
										+ " in weramaster!");
								bCodeIsAmbiguous = true;
								final String sBaseOfAmbig = getBasecodeForAmbiguousVariant(sixdigitcode);
								if (sBaseOfAmbig != null)
								{
									ProductModel pmbase = null;

									try
									{
										pmbase = this.m_productService.getProductForCode(csvm, sBaseOfAmbig);
									}
									catch (final Exception e)
									{
										// todo
									}
									if (pmbase != null && pmbase instanceof WeraProductModel)
									{

										pm = getVarianteForCodeAndBase(sixdigitcode, (WeraProductModel) pmbase);
										if (pm != null)
										{
											bCodeIsAmbiguous = false;
											LOG.info("bCheckAvailabilityOfProduct(): Found base for ambiguous variant through table lookup. => "
													+ sBaseOfAmbig);
										}
									}
								}
							}
							if (pm != null && pm instanceof WeraVarianteModel)
							{
								final WeraVarianteModel wvm = (WeraVarianteModel) pm;
								wm.SetLanguage("de");
								final String sVarNrDE = wvm.getVariantenNr();
								wm.SetLanguage("us-en");
								final String sVarNrUS = wvm.getVariantenNr();
								wm.SetLanguage("de");


								if (sLagerNr.equals(sVarNrDE) || sLagerNr.equals(sVarNrUS))
								{
									final WeraProductModel wp = (WeraProductModel) wvm.getBaseProduct();
									if (wp != null)
									{
										sBaseCode = wp.getCode();
										sVariantrepresent = sLagerNr;
										hMapOfVariantRepresentatives.put(sixdigitcode, sVariantrepresent);
										sRemark = "is variant";
									}
								}
								else
								{
									sRemark = "not a DE/US LagerNr";
								}
							}
							else
							{
								if (bCodeIsAmbiguous == true)
								{
									sRemark = "ambiguous variant code";
								}
								else
								{
									if (hPSM.containsKey(sixdigitcode))
									{
										final WeraProductSetModel wpsm = (WeraProductSetModel) hPSM.get(sixdigitcode);
										if (wpsm != null)
										{
											wm.SetLanguage("de");
											final String sVarNrDE = wpsm.getVariantenNr();
											wm.SetLanguage("us-en");
											final String sVarNrUS = wpsm.getVariantenNr();
											wm.SetLanguage("de");
											if (sLagerNr.equals(sVarNrDE) || sLagerNr.equals(sVarNrUS))
											{
												sBaseCode = wpsm.getCode();
												sVariantrepresent = sLagerNr;
												hMapOfVariantRepresentatives.put(sixdigitcode, sVariantrepresent);
												sRemark = "is set";
											}
											else
											{
												sRemark = "not a DE/US LagerNr";
											}
										}
									}
									else
									{
										sRemark = "not a Variant or Set";
									}
								}
							}
						}
					}

					stmt = con.createStatement();
					final String sUpdateQuery = "update lbh_temp set sixdigitcode='" + sixdigitcode + "', base='" + sBaseCode
							+ "', remark='" + sRemark + "', variantrepresent='" + sVariantrepresent + "' where code='" + sCode + "'";
					stmt.executeUpdate(sUpdateQuery);



				}
			}
		}
		catch (final SQLException e)
		{
			// YTODO Auto-generated catch block
			e.printStackTrace();
		}

		return rval;
	}

	/*
	 * Create connection to MySQL
	 */
	private boolean _init_migrator_db()
	{
		boolean rval = false;
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql:///" + MIGRATOR_DB_NAME, MIGRATOR_DB_USER, MIGRATOR_DB_PASS);

			if (!con.isClosed())
			{
				LOG.info("Successfully connected to " + "MySQL server using TCP/IP...");
				rval = true;
			}
		}
		catch (final Exception e)
		{
			System.err.println("Exception: " + e.getMessage());
		}

		/*
		 * finally { try { if (con != null) { con.close(); } } catch(SQLException e) {} }
		 */
		return rval;
	}

	public void test()
	{
		// class = AAA817c002
		final String sClass = "AAA817c002";
		// attribute = AAC081f001
		final String sAttribute = "AAC081f001";
		// get value = 3
		final String sGetValue = "3";
		// add value = 4711
		final String sAddValue = "4711";
		// test product
		final String sProductCode = "3950 PKL/9/10";
		// test variante (3334, 032005)
		//	final String sVarianteCode = "032005";
		final String sVariantePK = "8796199092225";
		// test variante class/attribute assignment
		final String sAttributeVariant = "_AA031f001";
		final String sClassVariant = "VERKAUFSHILFE_ZUSATZ";


		final CatalogVersionModel weramaster = this.getCatalogVersion("weracatalog", "weramaster");
		final ClassificationSystemVersionModel csvm = this.getClassificationSystemVersion("weraclassification", "werazusatz");
		final ClassificationClassModel ccm = this.getClassificationClass(csvm, sClass);
		final ClassAttributeAssignmentModel caam = this.getClassAttributeAssignment(ccm, sAttribute);


		if (caam != null)
		{
			LOG.info("test(): CAAM retrieved for " + sClass + "/" + sAttribute);

			LOG.info("test(): Testing getCustomValueForCAAM()... ");
			ClassificationAttributeValueModel cavm = this.getCustomValueForCAAM(caam, sGetValue);

			if (cavm != null)
			{
				LOG.info("test(): Testing ... result: " + cavm.getCode());

				LOG.info("test(): Testing addCustomValue()... ");
				this.addCustomValue(caam, sAddValue);
				LOG.info("test(): ... verification:");
				cavm = this.getCustomValueForCAAM(caam, sAddValue);
				if (cavm != null)
				{
					LOG.info("test(): ... result: " + cavm.getCode());

					final WeraProductModel wpm = (WeraProductModel) this.m_productService.getProductForCode(weramaster, sProductCode);
					if (wpm != null)
					{
						LOG.info("test(): Testing getZusatzClassForProduct(" + sProductCode + ")");
						final ClassificationClassModel zusatzccm = this.getZusatzClassForProduct(wpm);
						if (zusatzccm != null)
						{
							LOG.info("test(): ... result: " + zusatzccm.getCode());
						}
						else
						{
							LOG.info("test(): ... result: NOT FOUND");

							final ClassificationClassModel newCCM = this.createZusatzClassForProduct(wpm);
							if (newCCM != null)
							{
								LOG.info("test(): ... result: " + newCCM.getCode());

							}
							else
							{
								LOG.info("test(): ... result: NOT FOUND");
							}

						}

						LOG.info("test(): Testing createClassificiationAttributeForClass");
						this.createClassificiationAttributeForClass(TYPE_VARIANT, zusatzccm, "_AA031f001", "SJ Breite NEW");

						//final WeraVarianteModel wvm = (WeraVarianteModel) this.m_productService.getProductForCode(weramaster,
						//		sVarianteCode);

						final WeraVarianteModel wvm = (WeraVarianteModel) this.m_modelService.get(PK.parse(sVariantePK));
						if (wvm != null)
						{
							final ClassificationClassModel ccm2 = this.getClassificationClass(csvm, sClassVariant);
							final ClassAttributeAssignmentModel caam2 = this.getClassAttributeAssignment(ccm2, sAttributeVariant);
							if (ccm2 != null && caam2 != null)
							{
								LOG.info("test(): Testing setFeatureValue ");
								this.setFeatureValue(wvm, caam2, "64738");
								LOG.info("test(): Testing DONE. ");
							}

						}
					}
				}
				else
				{
					LOG.info("test(): ... result: NOT FOUND");
				}
			}
			else
			{
				LOG.info("test(): Testing ... result: NOT FOUND");
			}
		}
	}

	public void setFilename(final String sFilepath)
	{
		this.m_sFilepath = sFilepath;
	}

	public final String getFilename()
	{
		return this.m_sFilepath;
	}

	public String getAttributeNameForId(final String sAttributeId)
	{
		String sAttributeName = mAttributeId2Name.get(sAttributeId);
		if (sAttributeName == null)
		{
			sAttributeName = this.sUnknownAttributeName;
		}
		return sAttributeName;
	}

	// reverse mapping name to id
	public String getAttributeIdForName(final String sAttributeName)
	{
		String sId = null;
		for (final String sKey : mAttributeId2Name.keySet())
		{
			if (mAttributeId2Name.get(sKey).equals(sAttributeName))
			{
				sId = sKey;
				break;
			}
		}
		return sId;
	}


	private List<Map<String, Object>> getHeaderImportAnnotation(final String[] sLine)
	{
		final List<Map<String, Object>> rList = new ArrayList();

		int iColumn = 0;
		if (sLine.length > 0)
		{
			if (sLine[0].equals("#H1"))
			{
				// 1st Header Line mark
				for (int i = 0; i < sLine.length; i++)
				{
					int iRval = DI_TYPE_IMPORT_NONE;
					if (sLine[i].equals("*"))
					{
						iRval = DI_TYPE_IMPORT_NEW_ATTRIBUTE;
						// count new attribute annotations
						actionLogger.iNumberOfNewAttributeAnnotations++;
					}
					if (sLine[i].equals("**"))
					{
						iRval = DI_TYPE_IMPORT_REUSE_ATTRIBUTE;
						// count reusable attribute annotations						
						actionLogger.iNumberOfReusableAttributeAnnotations++;
					}
					if (iRval != DI_TYPE_IMPORT_NONE)
					{
						iColumn = i;
						final HashMap<String, Object> hHeaderNode = new HashMap();
						hHeaderNode.put("type", new Integer(iRval));
						hHeaderNode.put("column", new Integer(iColumn));
						rList.add(hHeaderNode);
					}
				}
			}
		}

		return rList;
	}


	public HashMap<String, WeraProductSetModel> getAllProductSets()
	{
		final WeraProductSetModel exampleProduct = new WeraProductSetModel();
		exampleProduct.setCatalogVersion(this.getCatalogVersion("weracatalog", "weramaster"));

		final List<WeraProductSetModel> foundProducts = this.m_flexibleSearchService.getModelsByExample(exampleProduct);

		final HashMap<String, WeraProductSetModel> hPSM = new HashMap();

		final int iMaxSets = foundProducts.size();

		for (final WeraProductSetModel wpsm : foundProducts)
		{
			if (!(wpsm instanceof WeraProductSetinSetModel))
			{
				final String sArtnr = wpsm.getArtnr();
				hPSM.put(sArtnr, wpsm);
			}
		}
		final int iPureSets = hPSM.size();

		LOG.info("getAllProductSets(): Returning " + iPureSets + " of " + iMaxSets + " possible sets.");

		return hPSM;
	}

	public Collection<WeraVarianteModel> getAmbiguousVariants()
	{


		final WeraVarianteModel exampleProduct = new WeraVarianteModel();
		exampleProduct.setCatalogVersion(this.getCatalogVersion("weracatalog", "weramaster"));
		final List<WeraVarianteModel> foundProducts = this.m_flexibleSearchService.getModelsByExample(exampleProduct);

		for (final WeraVarianteModel wvm : foundProducts)
		{
			if (wvm.getCode().length() == 6)
			{
				exampleProduct.setCode(wvm.getCode());
				final List<WeraVarianteModel> foundSameProducts = this.m_flexibleSearchService.getModelsByExample(exampleProduct);
				final int size = foundSameProducts.size();
				if (size > 1)
				{
					LOG.info("getAmbiguousVariants(): Variant " + wvm.getCode() + " has " + size + " occurrences.");
				}
			}
		}
		return null;
	}


	private boolean setFeatureValue(final ProductModel pm, final ClassAttributeAssignmentModel caam, final String sValue)
	{
		boolean bSuccess = false;
		FeatureContainer fc = null;

		WeraVarianteModel wvm = null;
		WeraVariante wv = null;
		WeraProductSetModel wpsm = null;
		WeraProductSet wps = null;
		ClassAttributeAssignment caa = null;
		TypedFeature<String> feat = null;

		if (caam != null)
		{
			caa = this.m_modelService.getSource(caam);
			if (pm instanceof WeraVarianteModel)
			{
				LOG.info("setFeatureValue(): product " + pm.getCode() + "is variant");
				wvm = (WeraVarianteModel) pm;
				wv = this.m_modelService.getSource(wvm);

				feat = Feature.loadTyped(wv, caa);
			}
			else
			{
				if (pm instanceof WeraProductSetModel)
				{
					LOG.info("setFeatureValue(): product " + pm.getCode() + "is set");
					wpsm = (WeraProductSetModel) pm;
					wps = this.m_modelService.getSource(wpsm);
					feat = Feature.loadTyped(wps, caa);
				}
			}
			caa = this.m_modelService.getSource(caam);

			if (feat != null)
			{
				feat.clear();
				feat.setLocalized(false);
			}

			final ClassificationAttributeValueModel cavm = this.getCustomValueForCAAM(caam, sValue);
			if (cavm != null)
			{

				feat.createValue(cavm.getCode());
				fc = feat.getParent();

				try
				{
					fc.store();
					LOG.info("setFeatureValue(): Stored feature: >" + pm.getCode() + "<, attribute >"
							+ caam.getClassificationAttribute().getCode() + "<, value >" + sValue + "<");

					bSuccess = true;
				}
				catch (final ConsistencyCheckException e)
				{
					LOG.error("setFeatureValue(): ERROR ConsistencyCheckException while saving feature container for sValue = >"
							+ sValue + "<");
				}
				catch (final NullPointerException e)
				{
					LOG.error("setFeatureValue(): ERROR NullPointerException while saving feature container for sValue >" + sValue
							+ "<");
					LOG.error("setFeatureValue(): Likely cause: feature container = null.");

				}
			}
			else
			{
				LOG.warn("setFeatureValue(): Cannot set feature value " + sValue + " for product " + pm.getCode()
						+ "; value does not exist!");
			}
		}
		return bSuccess;
	}


	// --- Get classification class for code sClassName
	public ClassificationClassModel getClassificationClass(final ClassificationSystemVersionModel csvm, final String sClassName)
	{

		// --- Initialize
		ClassificationClassModel oClass = null;
		LOG.info("getClassificationClass(): csvm = " + csvm.getVersion() + ", className = " + sClassName);
		try
		{

			// --- Hole Hole ClassificationSystemVersionModel
			oClass = (ClassificationClassModel) m_categoryService.getCategoryForCode(csvm, sClassName);
		}
		catch (final Exception e)
		{
			LOG.error("getClassificationClass(): " + sClassName + " does not exist.");
			LOG.error("getClassificationClass(): " + e.getMessage());
		}

		return oClass;
	}


	private final CatalogVersionModel getCatalogVersion(final String sCatalogId, final String sVersionId)
	{

		// --- Initialize
		CatalogVersionModel myCatalogVersionModel = null;

		try
		{
			myCatalogVersionModel = this.m_catalogVersionService.getCatalogVersion(sCatalogId.trim(), sVersionId.trim());
		}
		catch (final Exception e)
		{
			// todo
			LOG.error("getCatalogVersion(): Cannot get catalog/version for " + sCatalogId + "/" + sVersionId);
			LOG.error("getCatalogVersion(): " + e.getMessage());
		}

		return myCatalogVersionModel;
	}


	// --- Hole Hole ClassificationSystemVersionModel
	public final ClassificationSystemVersionModel getClassificationSystemVersion(final String sCatalogId, final String sVersionId)
	{

		// --- Initialize
		ClassificationSystemVersionModel myClassificationVersionModel = null;

		try
		{
			myClassificationVersionModel = (ClassificationSystemVersionModel) this.m_catalogVersionService.getCatalogVersion(
					sCatalogId.trim(), sVersionId.trim());
		}
		catch (final Exception e)
		{
			// todo
			LOG.error("getClassificationSystemVersion(): Cannot get catalog/version for " + sCatalogId + "/" + sVersionId);
			LOG.error("getClassificationSystemVersion(): " + e.getMessage());
		}

		return myClassificationVersionModel;
	}


	private ClassificationSystemVersionModel getClassificationSystemForHeader(final String sPath, final int iType)
	{
		ClassificationSystemVersionModel csvm = null;
		if (iType == DI_TYPE_IMPORT_NEW_ATTRIBUTE)
		{
			csvm = getClassificationSystemVersion("weraclassification", "werazusatz");
			LOG.info("getClassificationSystemForHeader(): CSVM = " + csvm.getVersion());
		}
		else
		{
			if (sPath != null)
			{
				final String[] aPathComponents = sPath.split("/");

				// "catalog / version / class"
				if (aPathComponents.length == 3)
				{
					csvm = getClassificationSystemVersion(aPathComponents[0], aPathComponents[1]);
				}
			}
			if (csvm == null)
			{
				LOG.warn("getClassificationSystemForHeader(): Could not retrieve CSVM for reusable attribute. sPath = " + sPath);
			}
		}
		return csvm;
	}

	private String getClassificationClassNameForHeader(final String sPath)
	{
		String sClassName = null;
		final String[] aPathComponents = sPath.split("/");

		// "catalog / version / class"
		if (aPathComponents.length == 3)
		{
			sClassName = aPathComponents[2];
		}
		return sClassName;
	}


	private ClassAttributeAssignmentModel getClassAttributeAssignment(final ClassificationClassModel oClass,
			final String sAttributeName)
	{
		ClassAttributeAssignmentModel caam = null;
		final ClassificationClass oClassJalo = m_modelService.getSource(oClass);
		final ClassificationAttribute oAttributeJalo = oClassJalo.getClassificationAttribute(sAttributeName);
		if (oAttributeJalo != null)
		{
			final ClassAttributeAssignment caa = oClassJalo.getAttributeAssignment(oAttributeJalo);
			if (caa != null)
			{
				caam = m_modelService.get(caa);
			}
		}
		return caam;
	}

	private WeraProductModel bCheckAvailabilityOfProduct(final String sVariantCode, final String sBaseProductCode)
	{
		WeraProductModel oBaseProduct = null;
		final CatalogVersionModel csvm = getCatalogVersion("weracatalog", "weramaster");
		if (csvm != null)
		{
			ProductModel pm = null;
			try
			{
				pm = this.m_productService.getProductForCode(csvm, sBaseProductCode);
			}
			catch (final UnknownIdentifierException uie)
			{
				LOG.warn("bCheckAvailabilityOfProduct(): Cannot find base product with code " + sBaseProductCode + " in weramaster.");
			}
			catch (final AmbiguousIdentifierException aie)
			{
				LOG.warn("bCheckAvailabilityOfProduct(): Ambiguous base product code " + sBaseProductCode + " in weramaster!");
			}

			if (pm != null)
			{
				if (pm instanceof WeraProductModel)
				{
					final WeraProductModel wpm = (WeraProductModel) pm;
					final Collection<VariantProductModel> colVariants = wpm.getVariants();
					for (final VariantProductModel vpm : colVariants)
					{
						if (vpm.getCode().equals(sVariantCode))
						{
							LOG.info("bCheckAvailabilityOfProduct(): WeraVariante with code " + sVariantCode
									+ " found for base product " + sBaseProductCode);
							oBaseProduct = wpm;
							break;
						}
					}
				}
			}
		}
		return oBaseProduct;
	}

	private String formatValue(final String sValue)
	{
		String sFormattedValue = sValue;
		if (sFormattedValue != null)
		{
			sFormattedValue = sFormattedValue.trim();
			if (sFormattedValue.length() == 0)
			{
				LOG.info("formatValue(): Cell value (trimmed) is empty, assuming '0'");
				sFormattedValue = new String("0");
			}
			try
			{
				Integer.parseInt(sFormattedValue);
			}
			catch (final NumberFormatException nfe)
			{
				LOG.warn("formatValue(): Cell value (trimmed) >" + sValue + "< is not a number!");
			}

		}
		return sFormattedValue;
	}

	private boolean getProductImportData(final String[] aLineInput, final Map<String, Object> mProductData, final int iMode)
	{
		final boolean bOk = false;

		final List<Map<String, Object>> listHEAD = (List) mProductData.get("HEAD");

		if (2 <= aLineInput.length)
		{
			final String sBaseProductCode = aLineInput[1].trim();
			String sVariantCode = aLineInput[2].trim();
			if (sVariantCode.startsWith("C") && sVariantCode.length() == 7)
			{
				sVariantCode = sVariantCode.substring(1);
			}

			final WeraProductModel oWpm = bCheckAvailabilityOfProduct(sVariantCode, sBaseProductCode);
			if (oWpm != null)
			{
				// get wera variant directly from base product, because product codes are no longer unique
				final WeraVarianteModel oVpm = this.getVarianteForCodeAndBase(sVariantCode, oWpm);
				Map<String, Map> mData = (Map) mProductData.get("DATA");
				if (mData == null)
				{
					mData = new HashMap();
					mProductData.put("DATA", mData);
				}

				Map<String, Object> mVariantData = mData.get(sVariantCode);
				if (mVariantData == null)
				{
					mVariantData = new HashMap();
					mVariantData.put("basecode", sBaseProductCode);
					mVariantData.put("baseproduct", oWpm);
					mVariantData.put("variantproduct", oVpm);
					mData.put(sVariantCode, mVariantData);
				}

				List<Map> lValues = (List) mVariantData.get("dimensions");
				if (lValues == null)
				{
					lValues = new ArrayList();
				}

				for (final Map<String, Object> oInfo : listHEAD)
				{
					final int iColumn = ((Integer) oInfo.get("column")).intValue();
					if (iColumn < aLineInput.length)
					{
						final Map<String, Object> mValue = new HashMap();
						mValue.put("column", new Integer(iColumn));
						mValue.put("value", formatValue(aLineInput[iColumn]));
						lValues.add(mValue);
					}
				}
				mVariantData.put("dimensions", lValues);
			}
		}
		return bOk;
	}

	private boolean getHeaderImportData(final String[] aLineInput, final Map<String, Object> mProductData, final int iMode)
	{
		boolean bNextStage = true;
		final List<Map<String, Object>> listHEAD = (List) mProductData.get("HEAD");
		switch (iMode)
		{
			case DI_MODE_SCAN_FOR_IMPORT_HEADER:
				// H1: Defines (V)ariant columns, (P)roduct columns, (*) new attributes, (**) reusable attributes (mapped P=>V)
				final List<Map<String, Object>> rList = getHeaderImportAnnotation(aLineInput);
				if (rList.size() > 0)
				{
					mProductData.put("HEAD", rList);
				}
				else
				{
					bNextStage = false;
				}
				break;
			case DI_MODE_READ_HEADER_2:
				// H2: Path catalog/catalogversion/class
				// for new attributes: empty
				for (final Map<String, Object> oInfo : listHEAD)
				{
					final int iType = ((Integer) oInfo.get("type")).intValue();
					final int iColumn = ((Integer) oInfo.get("column")).intValue();

					String sPath = null;
					if (iColumn < aLineInput.length)
					{
						sPath = aLineInput[iColumn];
						final ClassificationSystemVersionModel csvm = getClassificationSystemForHeader(sPath, iType);

						oInfo.put("csvm", csvm);
						if (csvm != null)
						{
							LOG.info("getHeaderImportData(): CSVM = " + csvm.getVersion());
							if (iType == DI_TYPE_IMPORT_REUSE_ATTRIBUTE && iColumn < aLineInput.length)
							{

								final String sClassName = getClassificationClassNameForHeader(aLineInput[iColumn]);
								oInfo.put("classname", sClassName);

								if (sClassName != null)
								{
									final ClassificationClassModel ccm = getClassificationClass(csvm, sClassName);
									oInfo.put("class", ccm);
									if (ccm == null)
									{
										actionLogger.addErrorReusableAttributes("Could not find a class for id >" + sClassName + "<");
									}
								}
								else
								{
									LOG.warn("getHeaderImportData(): Class name field empty.");
									actionLogger.addErrorReusableAttributes("Could not read class name component in column " + iColumn);
								}
							}
							if (iType == DI_TYPE_IMPORT_NEW_ATTRIBUTE)
							{
								// with new attributes check for corresponding ZUSATZ classes

							}


						}
						else
						{
							LOG.warn("getHeaderImportData(): Cannot get classification system version for path " + aLineInput[iColumn]);
						}
					}
					else
					{
						LOG.warn("getHeaderImportData(): Line has fewer cells than iColumn requires: " + iColumn);
					}


				}

				break;
			case DI_MODE_READ_HEADER_3:
				// H3: attribute id
				// for new attributes: only _AA081f001 (Gesamtlänge), _AA040f001 (Breite), _AA031f001 (Höhe) allowed
				for (final Map<String, Object> oInfo : listHEAD)
				{
					final int iType = ((Integer) oInfo.get("type")).intValue();
					final int iColumn = ((Integer) oInfo.get("column")).intValue();
					final ClassificationClassModel oClass = (ClassificationClassModel) oInfo.get("class");
					final ClassificationSystemVersionModel csvm = (ClassificationSystemVersionModel) oInfo.get("csvm");

					if (iColumn < aLineInput.length)
					{
						final String sAttributeId = aLineInput[iColumn].trim();
						LOG.info("getHeaderImportData(): Attribute id " + sAttributeId + " found in column " + iColumn);

						if (iType == DI_TYPE_IMPORT_REUSE_ATTRIBUTE)
						{
							// reuse attribute
							if (oClass != null)
							{
								final ClassAttributeAssignmentModel caam = getClassAttributeAssignment(oClass, sAttributeId);
								if (caam != null)
								{
									oInfo.put("caam", caam);
									oInfo.put("attributeid", sAttributeId);
									actionLogger.iNumberOfValidReusableAttributeAnnotations++;
								}
								else
								{
									LOG.error("getHeaderImportData(): Cannot retrieve CAAM for (" + oClass.getCode() + "/" + sAttributeId
											+ ") in system " + csvm.getVersion());
									actionLogger.addErrorReusableAttributes("Cannot get CAAM for (" + oClass.getCode() + "/"
											+ sAttributeId + ")");
								}
							}
						}
						else
						{
							// new attribute
							final String sNewAttributeName = this.getAttributeNameForId(sAttributeId);
							if (sNewAttributeName.equals(this.sUnknownAttributeName))
							{
								LOG.warn("getHeaderImportData(): New attribute Id " + sAttributeId + " not allowed!");
								actionLogger.addErrorNewAttributes("Attribute id " + sAttributeId + " not allowed for a new attribute.");
							}
							else
							{
								LOG.info("getHeaderImportData(): New attribute Id " + sAttributeId + " is valid.");
								oInfo.put("attributeid", sAttributeId);
								actionLogger.iNumberOfValidNewAttributeAnnotations++;
							}

						}
					}
					else
					{
						LOG.error("getHeaderImportData(): Line too short. Annotation index is " + iColumn + ". Line length = "
								+ aLineInput.length);
					}
				}

				break;
			case DI_MODE_READ_HEADER_4:
				// H4: attribute name
				// 
				for (final Map<String, Object> oInfo : listHEAD)
				{
					final int iColumn = ((Integer) oInfo.get("column")).intValue();

					if (iColumn < aLineInput.length)
					{
						final String sAttributeName = aLineInput[iColumn].trim();
						LOG.info("getHeaderImportData(): Attribute name " + sAttributeName + " found in column " + iColumn);
						oInfo.put("attributename", sAttributeName);
					}
					else
					{
						LOG.error("getHeaderImportData(): Line too short. Annotation index is " + iColumn + ". Line length = "
								+ aLineInput.length);
					}
				}
				break;

		}

		return bNextStage;
	}


	private WeraVarianteModel getVarianteForCodeAndBase(final String sVarianteCode, final WeraProductModel wpm)
	{
		WeraVarianteModel wvm = null;
		if (wpm != null)
		{
			final Collection<VariantProductModel> colVariants = wpm.getVariants();
			for (final VariantProductModel vpm : colVariants)
			{
				if (vpm instanceof WeraVarianteModel)
				{
					if (vpm.getCode().equals(sVarianteCode))
					{
						wvm = (WeraVarianteModel) vpm;
						break;
					}
				}
			}

		}
		return wvm;
	}


	private void showInfo(final Map<String, Object> mInfo)
	{
		for (final String sKey : mInfo.keySet())
		{
			LOG.info("showInfo(): " + sKey + " => (");
			if (sKey.equals("HEAD"))
			{
				final List list = (List) mInfo.get(sKey);
				for (final Object obj : list)
				{
					final Map oMap = (Map) obj;
					LOG.info("showInfo():     ---------------------------------------");
					final int iType = ((Integer) oMap.get("type")).intValue();
					final String sType = iType == DI_TYPE_IMPORT_NEW_ATTRIBUTE ? "(New attribute)" : "(Reuse attribute)";
					LOG.info("showInfo():     " + "type" + " => " + iType + " " + sType);

					LOG.info("showInfo():     " + "column" + " => " + oMap.get("column"));
					final ClassificationSystemVersionModel csvm = (ClassificationSystemVersionModel) oMap.get("csvm");
					final String sCSVM = csvm != null ? csvm.getCatalog().getId() + "/" + csvm.getVersion() : "";
					LOG.info("showInfo():     " + "csvm" + " => " + csvm + " (" + sCSVM + ")");

					LOG.info("showInfo():     " + "classname" + " => " + oMap.get("classname"));

					final ClassificationClassModel ccm = (ClassificationClassModel) oMap.get("class");
					final String sCCM = ccm != null ? ccm.getCode() : "";
					LOG.info("showInfo():     " + "class" + " => " + ccm + " (" + sCCM + ")");

					LOG.info("showInfo():     " + "attributeid" + " => " + oMap.get("attributeid"));

					LOG.info("showInfo():     " + "attributename" + " => " + oMap.get("attributename"));

					final ClassAttributeAssignmentModel caam = (ClassAttributeAssignmentModel) oMap.get("caam");
					String sCAAM = "";
					if (caam != null)
					{
						sCAAM = " (" + caam.getClassificationClass().getCode() + "/" + caam.getClassificationAttribute().getCode()
								+ ")";
					}
					LOG.info("showInfo():     " + "caam" + " => " + oMap.get("caam") + sCAAM);
				}
			}
			if (sKey.equals("DATA"))
			{
				LOG.info("showInfo():     ---------------------------------------");
				final Map<String, Map> oMap = (Map) mInfo.get(sKey);
				for (final String mVariantCode : oMap.keySet())
				{

					LOG.info("showInfo():     " + mVariantCode + " => (");

					final Map<String, Object> mVariantData = oMap.get(mVariantCode);
					if (mVariantData != null)
					{
						final String sBaseCode = (String) mVariantData.get("basecode");
						LOG.info("showInfo():         " + "basecode" + " => " + sBaseCode);

						final WeraProductModel oBaseProduct = (WeraProductModel) mVariantData.get("baseproduct");
						LOG.info("showInfo():         " + "baseproduct" + " => " + oBaseProduct);

						final WeraVarianteModel oVariantProduct = (WeraVarianteModel) mVariantData.get("variantproduct");
						LOG.info("showInfo():         " + "variantproduct" + " => " + oVariantProduct);


						final List<Map> lValues = (List) mVariantData.get("dimensions");

						if (lValues != null)
						{
							LOG.info("showInfo():         " + "dimensions" + " => (");
							for (final Map mEntry : lValues)
							{
								final Integer oColumn = (Integer) mEntry.get("column");
								final String sValue = (String) mEntry.get("value");
								LOG.info("showInfo():             " + "------------");
								LOG.info("showInfo():             " + "column" + " => " + oColumn);
								LOG.info("showInfo():             " + "values" + " => " + sValue);
							}
							LOG.info("showInfo():         " + ")");
						}

					}

					LOG.info("showInfo():");
					LOG.info("showInfo():     )");
				}

				LOG.info("showInfo():     ---------------------------------------");
			}


		}
		LOG.info("showInfo(): )");
	}


	private ClassificationAttributeValueModel getCustomValueForCAAM(final ClassAttributeAssignmentModel caam, final String sValue)
	{

		final String queryValue = sValue;
		ClassificationAttributeValueModel caavm = null;
		if (caam != null && queryValue != null)
		{


			Float oFloat = null;

			try
			{
				oFloat = Float.valueOf(queryValue.replace(",", "."));
			}
			catch (final NumberFormatException nfe)
			{
				LOG.error("getCustomValueForCAAM(): NumberFormatException boxing " + queryValue
						+ " into Float. Assuming value '0' for Float comparison.");
				// queryValue = "0";
				oFloat = new Float(0);
			}

			if (oFloat != null)
			{
				final List<ClassificationAttributeValueModel> listAssignedValues = caam.getCustomValues();
				for (final ClassificationAttributeValueModel caamAssigned : listAssignedValues)
				{
					String sValueAssigned = caamAssigned.getName();
					sValueAssigned = sValueAssigned.replace(",", ".");
					Float oFloatAssigned = null;
					try
					{
						oFloatAssigned = Float.valueOf(sValueAssigned);
					}
					catch (final NumberFormatException nfe)
					{
						LOG.warn("getCustomValueForCAAM(): NumberFormatException boxing assigned value " + sValueAssigned
								+ " into Float. Assuming '12345.6789 for float comparison");
						oFloatAssigned = new Float(12345.6789);
					}

					// LOG.info("getCustomValueForCAAM(): Current value " + sValueAssigned);
					if (sValueAssigned != null && oFloatAssigned != null)
					{

						if (sValueAssigned.equals(queryValue))
						{
							caavm = caamAssigned;
							// LOG.info("getCustomValueForCAAM(): Found match for value " + queryValue);
							break;
						}
						if (oFloatAssigned.floatValue() == oFloat.floatValue())
						{
							caavm = caamAssigned;
							LOG.info("getCustomValueForCAAM(): Found similiar values, assuming equality: >" + queryValue
									+ "< provided, >" + sValueAssigned + "< found.");
							break;
						}
					}
				}
			}
		}
		else
		{
			LOG.warn("getCustomValueForCAAM(): No CAAM object supplied.");
		}
		return caavm;
	}


	private void setCustomValueNames(final ClassificationAttributeValueModel cavm, final String sValue)
	{
		final ClassificationAttributeValue cav = this.m_modelService.getSource(cavm);
		final Map langMap = new HashMap();
		final Set<Language> setLanguages = C2LManager.getInstance().getAllLanguages();
		for (final Language l : setLanguages)
		{
			langMap.put(l, sValue);
		}
		cav.setAllName(langMap);
		this.m_modelService.save(cavm);
		LOG.info("setCustomValueNames(): Assigned name " + sValue + " to new custom value");
	}

	private void addCustomValue(final ClassAttributeAssignmentModel caam, final String sValue)
	{
		String newValue = sValue;
		if (newValue != null)
		{
			if (newValue.trim().length() == 0)
			{
				LOG.warn("addCustomValue(): Provided sValue = >" + sValue + "<, assuming '0'");
				newValue = "0";
			}

			ClassificationAttributeValueModel cavm = this.getCustomValueForCAAM(caam, newValue);
			if (cavm == null)
			{
				if (caam != null && caam.getClassificationAttribute() != null)
				{
					final String sAttributeCode = caam.getClassificationAttribute().getCode();

					final String sCustomValueCode = "H_" + sAttributeCode + "_" + newValue;

					final ClassificationAttributeValueModel exampleAttributeValue = new ClassificationAttributeValueModel();
					exampleAttributeValue.setCode(sCustomValueCode);
					// exampleAttributeValue.setSystemVersion(this.getClassificationSystemVersion("weraclassification", "werazusatz"));
					final List<ClassificationAttributeValueModel> foundAttributeValues = this.m_flexibleSearchService
							.getModelsByExample(exampleAttributeValue);

					boolean bAddCustomValue = false;
					if (foundAttributeValues.size() > 0)
					{
						cavm = foundAttributeValues.iterator().next();
						LOG.info("addCustomValue(): Attribute value for code " + sCustomValueCode + " already present (value:"
								+ cavm.getName() + ").");
						bAddCustomValue = true;
					}
					else
					{
						LOG.info("addCustomValue(): No existing attribute value for code " + sCustomValueCode);
						cavm = this.m_modelService.create(ClassificationAttributeValueModel.class);
						cavm.setCode(sCustomValueCode);
						cavm.setSystemVersion(this.getClassificationSystemVersion("weraclassification", "werazusatz"));
						try
						{
							// create the custom value permanently
							this.m_modelService.save(cavm);
							// set the name in all languages
							setCustomValueNames(cavm, newValue);
							// add the value to the list of custom values for the current caam
							this.m_modelService.save(cavm);
							bAddCustomValue = true;
						}
						catch (final ModelSavingException mse)
						{
							LOG.error("addCustomValue(): Error saving custom value model for value " + newValue
									+ " or adding that value to the CAAM list." + mse.getMessage());
							mse.printStackTrace();
						}
					}
					if (bAddCustomValue)
					{
						final List<ClassificationAttributeValueModel> listOfValues = caam.getCustomValues();
						final List<ClassificationAttributeValueModel> listOfNewValues = new ArrayList();
						listOfNewValues.addAll(listOfValues);
						listOfNewValues.add(cavm);
						caam.setCustomValues(listOfNewValues);

						this.m_modelService.save(caam);
						LOG.info("addCustomValue(): Saved CAAM with updated list of values (incl. value " + newValue + ").");
					}
					else
					{
						LOG.warn("addCustomValue(): CAAM not updated with list of values since value creating failed.");
					}

				}
			}
			else
			{
				LOG.info("addCustomValue(): Skipping addition of custom value " + newValue + ". Reason: Already exists.");
			}
		}
		else
		{
			LOG.error("addCustomValue(): Provided value >" + newValue + "< is null or empty!");
		}
	}

	private List<String> getInfoForZusatzClassCreation(final Collection<CategoryModel> colSuperCats)
	{

		final List aryInfo = new ArrayList();
		if (USE_DIMENSION_CLASS)
		{
			aryInfo.add(DIMENSIONEN_CLASS_NAME);
			aryInfo.add("Abmessungen (Laenge, Breite, Hoehe)");
		}
		else
		{

			for (final CategoryModel superCat : colSuperCats)
			{
				final CatalogVersionModel cvm = superCat.getCatalogVersion();
				if (cvm.getVersion().equals("weramaster"))
				{
					if (superCat.getCode().startsWith("MERKMAL_") || superCat.getCode().startsWith("PRO_"))
					{
						LOG.info("getZusatzClassForProduct(): Skipping 'MERKMAL_*' or 'PRO_*' category " + superCat.getCode() + " ...");
					}
					else
					{
						// build return pair (id_ZUSATZ, name)
						aryInfo.add(superCat.getCode() + "_ZUSATZ");
						aryInfo.add(superCat.getName());
						break;
					}
				}
			}
		}
		if (aryInfo.size() == 0)
		{
			aryInfo.add(null);
			aryInfo.add(null);
		}
		return aryInfo;
	}

	private ClassificationClassModel getZusatzClassForProduct(final WeraProductModel wpm)
	{
		ClassificationClassModel ccm = null;
		final Collection<CategoryModel> colSuperCats = wpm.getSupercategories();
		final String sZusatzClassId = getInfoForZusatzClassCreation(colSuperCats).get(0);

		if (sZusatzClassId != null)
		{
			for (final CategoryModel superCat : colSuperCats)
			{
				final CatalogVersionModel cvm = superCat.getCatalogVersion();
				if (cvm.getVersion().equals("werazusatz") && superCat.getCode().equals(sZusatzClassId))
				{
					ccm = (ClassificationClassModel) superCat;
					break;
				}
			}
		}

		return ccm;
	}

	private ClassificationAttributeModel getClassificiationAttributeForClass(final ClassificationClassModel ccm,
			final String sAttributeId)
	{
		ClassificationAttributeModel matchingCam = null;
		final List<ClassificationAttributeModel> listCAM = ccm.getClassificationAttributes();
		for (final ClassificationAttributeModel cam : listCAM)
		{
			if (cam.getCode().equals(sAttributeId))
			{
				matchingCam = cam;
				break;
			}
		}

		return matchingCam;
	}

	private ClassAttributeAssignmentModel createClassificiationAttributeForClass(final int iProductType,
			final ClassificationClassModel ccm, final String sAttributeId, final String sAttributeName)
	{
		ClassificationAttributeModel newCam = null;
		ClassAttributeAssignmentModel caam = null;
		if (ccm != null)
		{
			newCam = getClassificiationAttributeForClass(ccm, sAttributeId);
			if (newCam == null)
			{

				//search for a products with the nonunique ArticleApprovalStatus
				final ClassificationAttributeModel exampleAttribute = new ClassificationAttributeModel();
				exampleAttribute.setCode(sAttributeId);
				exampleAttribute.setSystemVersion(ccm.getCatalogVersion());

				LOG.info("createClassificiationAttributeForClass(): Checking for attribute " + sAttributeId + " in "
						+ ccm.getCatalogVersion().getVersion());
				final List<ClassificationAttributeModel> foundAttributes = this.m_flexibleSearchService
						.getModelsByExample(exampleAttribute);

				if (foundAttributes.size() > 0)
				{
					LOG.info("createClassificiationAttributeForClass(): Attribute found for code " + sAttributeId);
					newCam = foundAttributes.iterator().next();
				}
				else
				{

					// attribute does not exist, create it
					newCam = this.m_modelService.create(ClassificationAttributeModel.class);
					if (newCam != null)
					{
						newCam.setCode(sAttributeId);
						newCam.setName(sAttributeName);
						newCam.setSystemVersion(this.getClassificationSystemVersion("weraclassification", "werazusatz"));

						try
						{
							this.m_modelService.save(newCam);
							LOG.info("createClassificiationAttributeForClass(): Created new classification attribute " + sAttributeId
									+ ".");
						}
						catch (final ModelSavingException mse)
						{
							LOG.error("createClassificiationAttributeForClass(): Error saving classification attribute model for name "
									+ sAttributeId);
							LOG.error(mse.getMessage());
							mse.printStackTrace();
						}
					}
				}
				try
				{
					caam = this.m_modelService.create(ClassAttributeAssignmentModel.class);
					caam.setClassificationAttribute(newCam);
					caam.setClassificationClass(ccm);
					if (iProductType == TYPE_VARIANT)
					{
						caam.setVisibility(ClassificationAttributeVisibilityEnum.ASSIGNED_TO_VARIANT);
					}
					else
					{
						caam.setVisibility(ClassificationAttributeVisibilityEnum.VISIBLE_IN_BASE);
					}

					caam.setLocalized(new Boolean(false));
					caam.setAttributeType(ClassificationAttributeTypeEnum.STRING);
					caam.setCustomValues(new ArrayList());
					this.m_modelService.save(caam);
					LOG.info("createClassificiationAttributeForClass(): Created new class attribute assignment for (" + ccm.getCode()
							+ "/" + sAttributeName);
				}
				catch (final ModelSavingException mse)
				{
					LOG.error("createClassificiationAttributeForClass(): Error saving class attribute assignment for ("
							+ ccm.getCode() + "/" + sAttributeName);
				}


			}
			else
			{
				LOG.info("createClassificiationAttributeForClass(): classification attribute " + sAttributeId
						+ " already exists for class " + ccm.getCode());
			}
		}
		return caam;
	}


	private ClassificationClassModel createZusatzClassForProduct(final WeraProductModel wpm)
	{
		ClassificationClassModel ccm = null;
		final ClassificationClassModel ccmAlready = getZusatzClassForProduct(wpm);
		if (ccmAlready == null)
		{
			String sZusatzClassId = null;
			final Collection<CategoryModel> colSuperCats = wpm.getSupercategories();
			final List<String> colInfo = getInfoForZusatzClassCreation(colSuperCats);

			sZusatzClassId = colInfo.get(0);
			boolean bDoCreate = true;
			if (USE_DIMENSION_CLASS)
			{
				try
				{
					ccm = (ClassificationClassModel) this.m_categoryService.getCategoryForCode(
							this.getCatalogVersion("weraclassification", "werazusatz"), DIMENSIONEN_CLASS_NAME);
					bDoCreate = false;
				}
				catch (final UnknownIdentifierException uie)
				{
					LOG.info("createZusatzClassForProduct(): No class >" + DIMENSIONEN_CLASS_NAME + "< found yet.");
				}
				catch (final AmbiguousIdentifierException aie)
				{
					LOG.error("createZusatzClassForProduct(): Class >" + DIMENSIONEN_CLASS_NAME + "< is ambiguous!");
					bDoCreate = false;
				}
				catch (final IllegalArgumentException iae)
				{
					LOG.error("createZusatzClassForProduct(): Error trying to retrieve class for name >" + DIMENSIONEN_CLASS_NAME
							+ "<");
					bDoCreate = false;
				}

			}

			if (bDoCreate && sZusatzClassId != null)
			{
				ccm = this.m_modelService.create(ClassificationClassModel.class);
				if (ccm != null)
				{
					ccm.setCode(sZusatzClassId);
					ccm.setName(colInfo.get(1));

					ccm.setCatalogVersion(this.getCatalogVersion("weraclassification", "werazusatz"));
					try
					{
						this.m_modelService.save(ccm);
						actionLogger.addClassesCreated(ccm.getCode());
						LOG.info("createZusatzClassForProduct(): Created new classification class " + ccm.getCode() + ".");
					}
					catch (final ModelSavingException mse)
					{
						LOG.error("createZusatzClassForProduct(): Error saving classification class model for name " + sZusatzClassId);
						LOG.error("createZusatzClassForProduct():" + mse.getMessage());
					}
				}
			}
			if (ccm != null)
			{
				final Collection<CategoryModel> colNewSuperCats = new ArrayList();
				colNewSuperCats.addAll(wpm.getSupercategories());
				colNewSuperCats.add(ccm);
				wpm.setSupercategories(colNewSuperCats);
				try
				{
					this.m_modelService.save(wpm);
					LOG.info("createZusatzClassForProduct(): Added new classification class " + ccm.getCode() + " to product "
							+ wpm.getCode());

				}
				catch (final ModelSavingException mse)
				{
					LOG.error("createZusatzClassForProduct(): Error saving weraproduct model for code " + wpm.getCode());
				}
			}

		}
		else
		{
			LOG.warn("createZusatzClassForProduct(): WeraProduct " + wpm.getCode() + " already has a zusatz class for "
					+ ccmAlready.getCode());
		}
		return ccm;
	}

	private void setupOutputcontols(final WeraProductModel wpm, final ClassAttributeAssignmentModel caam)
	{
		// check if outputcontrol for the corresponding caam is set to variant visibility
		final Collection<OutputcontrolModel> outputcontrols = wpm.getOutputcontrols();
		final OutputcontrolModel outputcontrol = (OutputcontrolModel) WeraManager.checkContainingModel((Collection) outputcontrols,
				"code", caam.getClassificationAttribute().getCode());
		if (outputcontrol != null)
		{
			final ClassificationAttributeVisibilityEnum eVisibility = outputcontrol.getVisibility();
			//LOG.info("setupOutputcontols(): ... checking outputcontrol of CAAM ("
			//		+ caam.getClassificationClass().getCode() + "/" + caam.getClassificationAttribute().getCode() + ")");
			LOG.info("setupOutputcontols(): ... outputcontrol visibility currently: " + eVisibility);

			if (wpm instanceof WeraProductSetModel)
			{
				outputcontrol.setVisibility(ClassificationAttributeVisibilityEnum.VISIBLE_IN_BASE);
				LOG.info("setupOutputcontols(): Set product => ... setting outputcontrol visibility to VISIBLE_IN_BASE");
			}
			else
			{

				if (!(eVisibility.equals(ClassificationAttributeVisibilityEnum.ASSIGNED_TO_VARIANT) || eVisibility
						.equals(ClassificationAttributeVisibilityEnum.VISIBLE_IN_VARIANT)))
				{
					// set outputcontrol visibility to ASSIGNED_TO_VARIANT!
					outputcontrol.setVisibility(ClassificationAttributeVisibilityEnum.ASSIGNED_TO_VARIANT);
					LOG.info("setupOutputcontols(): Variant product ... setting outputcontrol visibility to ASSIGNED_TO_VARIANT");

				}
			}
			outputcontrol.setBackground(new Boolean(false));
			outputcontrol.setOrder(new Integer(99));

			// make the modification permanent
			this.m_modelService.save(outputcontrol);
			actionLogger.addOutputcontrolsSetToAssignedToVariant(caam.getClassificationAttribute().getCode());

		}
		else
		{
			LOG.warn("setupOutputcontols(): no matching outputcontrol found for code " + caam.getClassificationAttribute().getCode());
		}

	}


	private String getValueForColumn(final List<Map> lValues, final int iColumn)
	{
		String sRval = null;
		if (lValues != null)
		{
			for (final Map mEntry : lValues)
			{
				final Integer oColumn = (Integer) mEntry.get("column");
				final String sValue = (String) mEntry.get("value");
				if (oColumn != null && oColumn.intValue() == iColumn)
				{
					sRval = sValue;
					break;
				}
			}
		}

		return sRval;
	}


	private void assignFeatureValue(final ProductModel pm, final ClassAttributeAssignmentModel caam, final String sNewValue)
	{
		if (caam != null)
		{

			final ClassificationAttributeValueModel cavm = this.getCustomValueForCAAM(caam, sNewValue);
			if (cavm != null)
			{
				LOG.info("assignFeatureValue(): Custom value >" + sNewValue + "< already present.");
			}
			else
			{
				this.addCustomValue(caam, sNewValue);
				LOG.info("assignFeatureValue(): Adding new custom value >" + sNewValue + "< to caam ("
						+ caam.getClassificationClass().getCode() + "/" + caam.getClassificationAttribute().getCode() + ").");
				actionLogger.addCustomValuesCreated(sNewValue + "@ (" + caam.getClassificationClass().getCode() + "/"
						+ caam.getClassificationAttribute().getCode() + ")");
			}

			final boolean bSuccess = this.setFeatureValue(pm, caam, sNewValue);
			if (bSuccess)
			{
				if (pm instanceof WeraVarianteModel)
				{
					LOG.info("assignFeatureValue(): Successfully set feature value for variant " + pm.getCode() + " to value >"
							+ sNewValue + "<");
				}
				else
				{
					if (pm instanceof WeraProductSetModel)
					{
						LOG.info("assignFeatureValue(): Successfully set feature value for product set " + pm.getCode() + " to value >"
								+ sNewValue + "<");
					}
				}
				actionLogger.addFeatureValuesCreated(pm.getCode() + ": feat( " + caam.getClassificationClass().getCode() + "/"
						+ caam.getClassificationAttribute().getCode() + "  ) = " + sNewValue);

			}
		}
		else
		{
			LOG.error("assignFeatureValue(): No CAAM to set new value >" + sNewValue + "< for!");
		}
	}

	private WeraProductModel getWeraProductForCode(final String sCode)
	{
		WeraProductModel wpm = null;
		try
		{
			wpm = (WeraProductModel) this.m_productService.getProductForCode(sCode);
			LOG.info("getWeraProductForCode(): WeraProduct retrieved for code " + sCode);

		}
		catch (final UnknownIdentifierException uie)
		{
			LOG.error("getWeraProductForCode(): UnknownIdentifierException for Code " + sCode);
		}
		catch (final AmbiguousIdentifierException aie)
		{
			LOG.error("getWeraProductForCode(): AmbiguousIdentifierException for Code " + sCode);
		}
		catch (final IllegalArgumentException iae)
		{
			LOG.error("getWeraProductForCode(): IllegalArgumentException");
		}
		return wpm;
	}

	private boolean importProductData()
	{
		LOG.info("importProductData(): START importing data for product block ... ");

		final int iMaxProducts = 1000000;

		Statement stmt = null;
		String sNewValue = null;
		String sAllTypesQuery = null;


		ResultSet rs;
		try
		{
			int iCnt = 0;
			// loop over variants and sets

			final Set<String> setProductOutputcontrolsCovered = new HashSet();

			for (int iVS = 0; iVS < 2; iVS++)
			{

				switch (iVS)
				{
					case TYPE_VARIANT:
						sAllTypesQuery = "select lbh.* from lbh_temp as lbh where lbh.remark='is variant' order by lbh.base, lbh.code asc";
						break;
					case TYPE_SET:
						sAllTypesQuery = "select lbh.* from lbh_temp as lbh where lbh.remark='is set' order by lbh.base, lbh.code asc";
						break;
					default:
						sAllTypesQuery = "select lbh.* from lbh_temp as lbh where lbh.remark='something impossible'";
						break;
				}

				stmt = con.createStatement();
				rs = stmt.executeQuery(sAllTypesQuery);
				ClassAttributeAssignmentModel newCaam = null;


				while (rs.next() && iCnt < iMaxProducts)
				{
					iCnt++;
					final String sCode = rs.getString("lbh.code");
					final String sSixDigitCode = rs.getString("lbh.sixdigitcode");
					final String sBase = rs.getString("lbh.base");
					final String sL = rs.getString("lbh.L");
					final String sB = rs.getString("lbh.B");
					final String sH = rs.getString("lbh.H");

					/*
					 * if (!sBase.equals("1334 SK/6")) { continue; }
					 */


					final WeraProductModel wpm = getWeraProductForCode(sBase);
					WeraVarianteModel wvm = null;
					if (iVS == TYPE_VARIANT)
					{
						wvm = this.getVarianteForCodeAndBase(sSixDigitCode, wpm);
					}

					if (wpm != null && (wvm != null || iVS == TYPE_SET))
					{
						actionLogger.setProduct(sBase);

						if (iVS == TYPE_VARIANT)
						{
							LOG.info("importProductData(): Processing variant " + sSixDigitCode + ", base " + sBase);
						}
						else
						{
							LOG.info("importProductData(): Processing set with artnr " + sSixDigitCode + ", code " + sBase);
						}

						String sMappedAttributeId = null;
						String sAttributeName = null;
						String sOutputcontrolHashkey = null;

						// loop over L-B-H
						for (int iLBH = 0; iLBH < 3; iLBH++)
						{
							switch (iLBH)
							{
								case 0:
									sOutputcontrolHashkey = sBase + "|" + "L";
									sMappedAttributeId = this.getAttributeIdForName("Gesamtlänge");
									sNewValue = sL;
									break;
								case 1:
									sOutputcontrolHashkey = sBase + "|" + "B";
									sMappedAttributeId = this.getAttributeIdForName("Breite");
									sNewValue = sB;
									break;
								case 2:
									sOutputcontrolHashkey = sBase + "|" + "H";
									sMappedAttributeId = this.getAttributeIdForName("Höhe");
									sNewValue = sH;
									break;
							}
							LOG.info("importProductData(): Processing attribute " + sMappedAttributeId + " with value " + sNewValue);



							final String sAttributeId = sMappedAttributeId;

							if (sAttributeId != null)
							{

								// handle new attribute, we don't have a caam, so create classes, attributes, etc. where needed.
								// this fetches the DIMENSIONEN CLASS from werazusatz
								ClassificationClassModel ccm = this.getZusatzClassForProduct(wpm);

								if (ccm == null)
								{
									LOG.info("importProductData(): Product " + wpm.getCode() + " does not have a ZUSATZ class yet.");
									ccm = this.createZusatzClassForProduct(wpm);
									if (ccm != null)
									{
										LOG.info("importProductData(): New Zusatz class " + ccm.getCode()
												+ " created/assigned for product " + wpm.getCode());
									}
									else
									{
										LOG.error("importProductData(): New Zusatz class could not be created for product " + wpm.getCode());
										continue;
									}
								}
								else
								{
									LOG.info("importProductData(): Product " + wpm.getCode() + " already has " + ccm.getCode());
								}
								sAttributeName = this.getAttributeNameForId(sAttributeId);

								newCaam = this.getClassAttributeAssignment(ccm, sAttributeId);
								if (newCaam == null)
								{
									LOG.info("No CAAM for class " + ccm.getCode() + " and attribute " + sAttributeId);
									newCaam = this.createClassificiationAttributeForClass(iVS, ccm, sAttributeId, sAttributeName);
									if (newCaam != null)
									{
										LOG.info("importProductData(): Created new attribute and class assignment for " + sAttributeId
												+ " and class " + ccm.getCode());
										actionLogger.addClassAttributeAssignmentsCreated(ccm.getCode() + "/" + sAttributeId);
									}
									else
									{
										LOG.error("importProductData(): Could not create new CAAM or attribute for " + sAttributeId
												+ " and class " + ccm.getCode());
										continue;
									}
								}

								if (!setProductOutputcontrolsCovered.contains(sOutputcontrolHashkey))
								{
									final WeraProduct wp = this.m_modelService.getSource(wpm);
									wp.hookDimensionImport(new HashMap());
									this.m_modelService.refresh(wpm);
									setupOutputcontols(wpm, newCaam);
									setProductOutputcontrolsCovered.add(sOutputcontrolHashkey);
								}
								else
								{
									LOG.info("importProductData(): Outputcontrols already covered for >" + sOutputcontrolHashkey + "<");
								}
							}
							else
							{
								LOG.error("importProductData(): New Attribute id is null or invalid.");
							}

							// NOW start handling variant feature value data
							if (sNewValue != null)
							{
								if (newCaam != null)
								{
									if (iVS == TYPE_VARIANT)
									{
										LOG.info("importProductData(): assigning feature value to variant " + wvm.getCode() + " for caam "
												+ newCaam.toString() + " and value " + sNewValue);
										this.assignFeatureValue(wvm, newCaam, sNewValue);
									}
									else
									{
										LOG.info("importProductData(): assigning feature value to product set " + wpm.getCode()
												+ " for caam " + newCaam.toString() + " and value " + sNewValue);
										this.assignFeatureValue(wpm, newCaam, sNewValue);
									}
								}

							}
						}
					}
				}
			}
		}
		catch (final SQLException e)
		{
			// YTODO Auto-generated catch block
			e.printStackTrace();
		}


		return true;
	}

	public boolean doImport()
	{

		_init_migrator_db();

		// this.updateLBHDB();


		LOG.info("doImport(): Start");

		if (1 == 1)
		{

			// product block read, now import data
			// showInfo(mProductData);

			importProductData();
			actionLogger.flush();
			actionLogger.clear();

			//--- Eingabedatei schliessen
			actionLogger.close();


		}
		LOG.info("doImport(): End.");
		return true;
	}

}
