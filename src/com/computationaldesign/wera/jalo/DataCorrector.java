package com.computationaldesign.wera.jalo;

import de.hybris.platform.catalog.jalo.CatalogVersion;
import de.hybris.platform.catalog.jalo.Keyword;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttribute;
import de.hybris.platform.catalog.jalo.classification.ClassificationSystemVersion;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.core.Registry;
import de.hybris.platform.jalo.ConsistencyCheckException;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.c2l.LocalizableItem;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.JaloAbstractTypeException;
import de.hybris.platform.jalo.type.JaloGenericCreationException;
import de.hybris.platform.jalo.type.TypeManager;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.util.Config;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.jsp.JspWriter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import com.computationaldesign.wera.enums.XPaceTypVerpackungEnum;
import com.computationaldesign.wera.model.BildreferenzModel;
import com.computationaldesign.wera.model.OutputcontrolModel;
import com.computationaldesign.wera.model.TextbausteinModel;
import com.computationaldesign.wera.model.TippModel;
import com.computationaldesign.wera.model.WeblinkModel;
import com.computationaldesign.wera.model.WeraMediaModel;
import com.computationaldesign.wera.model.WeraProductModel;
import com.computationaldesign.wera.model.WeraProductSetModel;
import com.computationaldesign.wera.model.WeraProductSetVariantsModel;
import com.computationaldesign.wera.model.WeraProductSetinSetModel;
import com.computationaldesign.wera.model.WeraVarianteModel;
import com.computationaldesign.wera.model.WeraVarianteSetModel;
import com.computationaldesign.wera.model.WeraVarianteVariantsModel;
import de.hybris.platform.catalog.jalo.Catalog;
import de.hybris.platform.catalog.jalo.CatalogManager;
import de.hybris.platform.catalog.model.classification.ClassificationAttributeModel;
import de.hybris.platform.catalog.model.classification.ClassificationClassModel;
import de.hybris.platform.catalog.model.classification.ClassificationSystemVersionModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.core.PK;
import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.variants.model.VariantProductModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.TreeMap;
import java.util.logging.Level;


public class DataCorrector extends WeraManager
{

	private ClassificationSystemVersion m_weraCatalogVersion = null;
	private ClassificationSystemVersion m_proficlassCatalogVersion = null;
	private CatalogVersion  m_doorway = null;

	private HashSet hashSuperCategoriesGroup1 = null;
	private HashSet hashSuperCategoriesGroup2 = null;

	private final FlexibleSearchService flexibleSearchService = (FlexibleSearchService) Registry.getGlobalApplicationContext()
			.getBean("defaultFlexibleSearchService");
	private final ModelService modelService = (ModelService) Registry.getGlobalApplicationContext().getBean("modelService");
	
	private final ProductService productService = (ProductService) Registry.getGlobalApplicationContext().getBean("defaultProductService");

	public DataCorrector()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	public void importIPimCatalog(final String filename) {
            int iCountSuccess = 0;
            int iCountFails = 0;
            final Logger LOG = Logger.getLogger(DataCorrector.class.getName());
            List<List<String>> records = new ArrayList();
            try {
                BufferedReader br = new BufferedReader(new FileReader(filename));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(";");
                    //records.add(Arrays.asList(values));
                    if ( values.length >= 2 ) {
                        values[0] = StringUtils.strip( values[0], "\"");
                        values[1] = StringUtils.strip( values[1], "\"");
                        if ( this.addProductToCategory(values[0], values[1]) ) {
                            iCountSuccess++;
                        } else {
                            iCountFails++;
                        }
                    } else {
                        LOG.warn("Line found without properly separated key value pair (end of file?).");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            LOG.info("importIPimCatalog completed with "+iCountSuccess+" successful relations created and "+iCountFails+" failed relations.");
        }
        
        public boolean addProductToCategory(final String sProductCode, final String sCategory) {
            final Logger LOG = Logger.getLogger(DataCorrector.class.getName());
            Collection<CategoryModel> colNewSuperCats = new ArrayList();
            ProductModel pm = null;
            try {
                pm = productService.getProduct(sProductCode);
            } catch (UnknownIdentifierException e) {
                // nothing
            }
            if (pm != null) {
                WeraProductModel wpm = (WeraProductModel) pm;
                WeraProduct wp = (WeraProduct) this.modelService.getSource(wpm);
                final String sCategoryQuery = "SELECT {PK} FROM {Category} where {code}='"+sCategory+"' and {catalogversion}='8796093121113'";
                final SearchResult<CategoryModel> rCategories = flexibleSearchService.search(sCategoryQuery);
                final List<CategoryModel> listCategories = rCategories.getResult();
                
                if (! listCategories.isEmpty() ) {
                    Collection<CategoryModel> colSuperCats = wpm.getSupercategories();
                    colNewSuperCats.addAll(colSuperCats);
                    
                    CategoryModel newcm = (CategoryModel) listCategories.iterator().next();
                    LOG.info("newcm = "+newcm.getCode());
                    colNewSuperCats.add(newcm);
                    LOG.info("new super cats are:");
                    for (final CategoryModel cm : colNewSuperCats ) {
                        if (cm.getCode().equals(newcm.getCode())) {
                            LOG.info(" >> " + cm.getCode());
                        } else {
                            LOG.info(cm.getCode());
                        }
                    }
                    wpm.setSupercategories(colNewSuperCats);
                    modelService.save(wpm);
                    this.createCategory2ProductExt(wp, null);
                    return true;
                } else {
                    LOG.warn("Cannot find category with code >"+sCategory+"<.");
                }
            } else {
                LOG.warn("Cannot find product with code >"+sProductCode+"<.");
            }
            return false;
        }
        
        public void presetSonderartikelToRegion() {
            final Logger LOG = Logger.getLogger(DataCorrector.class.getName());
            String sProductsQuery = "SELECT {wps.PK} FROM {WeraProductSet as wps}";
            SearchResult<WeraProductSetModel> rProducts = flexibleSearchService.search(sProductsQuery);
            List<WeraProductSetModel> listProducts = rProducts.getResult();
            
            for ( final WeraProductSetModel wpsm : listProducts ) {
                wpsm.setWww_de(true);wpsm.setWww_uk(true);wpsm.setWww_us(true);wpsm.setWww_au(true);
                
            }
            this.modelService.saveAll();
            LOG.info("saved all sets");
            
            sProductsQuery = "SELECT {v.PK} FROM {WeraVariante as v}";
            SearchResult<WeraVarianteModel> rVariants = flexibleSearchService.search(sProductsQuery);
            List<WeraVarianteModel> listVariants = rVariants.getResult();
            
            for ( final WeraVarianteModel wvm : listVariants ) {
                wvm.setWww_de(true);wvm.setWww_uk(true);wvm.setWww_us(true);wvm.setWww_au(true);
            }
            this.modelService.saveAll();
            LOG.info("saved all variants");
        }
        
        public void refactorBulletPointsAndMarketingTexte() {
            SetLanguage("de");
            final Set<String> compareLanguages = new HashSet<String>(Arrays.asList("de", "en", "fr", "es", "it", "dk", "nl", "cs", "pl", "ru", "se", "jp", "fi", "ko", "cn"));
            final Set<String> allLanguages = new HashSet<String>(Arrays.asList("de", "en", "fr", "es", "it", "dk", "nl", "cs", "pl", "ru", "se", "jp", "no" ,"fi", "ko", "cn", "gr", "ro", "by", "bg", "ee", "lt", "lv", "hu" ));
            final String MARKETING_MARKER = "mrkt_";
            final Logger LOG = Logger.getLogger(DataCorrector.class.getName());
            
            // STEP 1: map distinct marketingtexte TO variants
            // final String sWebProductsQuery = "SELECT distinct {wp.PK}  FROM {CategoryProductRelation as cpr}, {Category as c}, {WeraProduct as wp} WHERE {c.catalogversion}='8800483967577'  and {cpr.source}={c.PK} and {cpr.target}={wp.PK} order by {wp.code}";
            final String sWebProductsQuery = "SELECT distinct {wp.PK} FROM {WeraProduct as wp} order by {wp.code}";
            final SearchResult<WeraProductModel> rProducts = flexibleSearchService.search(sWebProductsQuery);
            final List<WeraProductModel> listProducts = rProducts.getResult();
            Map<String, List<PK>> hMapMarketingtext2VariantPK = new TreeMap();
            int iNumProducts = 0;
            int iMaxProducts = listProducts.size();
            for ( final WeraProductModel wpm : listProducts ) {
                iNumProducts++;
                if ( iNumProducts % 10 == 0 ) {
                    LOG.info("Processing product "+iNumProducts+ " / " + iMaxProducts + " ...");
                }
                if ( wpm instanceof WeraProductSetModel ) {
                    WeraProductSetModel wpsm = (WeraProductSetModel) wpm;
                    String sMarketingtext = StringUtils.trimToNull(wpsm.getShop_description() );
                    if ( sMarketingtext != null ) {
                        List<PK> listVariantPKs =  hMapMarketingtext2VariantPK.get(sMarketingtext);
                        if ( listVariantPKs == null ) {
                            listVariantPKs = new ArrayList();
                        }
                        listVariantPKs.add(wpsm.getPk());
                        hMapMarketingtext2VariantPK.put(sMarketingtext, listVariantPKs);
                    }
                } else {
                    Collection<VariantProductModel> colVariants = wpm.getVariants();
                    for ( final VariantProductModel oVariant : colVariants ) {
                        if ( oVariant instanceof WeraVarianteModel ) {
                            WeraVarianteModel wvm = (WeraVarianteModel) oVariant;
                            String sMarketingtext = StringUtils.trimToNull(wvm.getShop_description() );
                            if ( sMarketingtext != null ) {
                                List<PK> listVariantPKs =  hMapMarketingtext2VariantPK.get(sMarketingtext);
                                if ( listVariantPKs == null ) {
                                    listVariantPKs = new ArrayList();
                                }
                                listVariantPKs.add(wvm.getPk());
                                hMapMarketingtext2VariantPK.put(sMarketingtext, listVariantPKs);
                            }
                        }
                    }
                }
            }
            /*
            for (Map.Entry<String, List<PK>> entry : hMapMarketingtext2VariantPK.entrySet()) {
                String sKey = entry.getKey();
                List<PK> listPKs = entry.getValue();
                LOG.info( StringUtils.left(sKey, 100) + " => " + listPKs.size() );
            }
            */
            // STEP 2: delete all currently created marketingtext textbausteine (for re-runs of this method)
            final String sAllMarketingTextbausteinQuery = "select distinct {PK} from {Textbaustein} where substr({code},1,"+MARKETING_MARKER.length()+")='"+MARKETING_MARKER+"'";
            final SearchResult<TextbausteinModel> rMTexte = flexibleSearchService.search(sAllMarketingTextbausteinQuery);
            final List<TextbausteinModel> listMTexte = rMTexte.getResult();
            LOG.info("Removing " + listMTexte.size() + " current marketingtext textbausteine ... ");
            for ( TextbausteinModel mtext : listMTexte ) {
                this.modelService.remove(mtext);
            }
            
            // STEP 3: create new marketingtext textbausteine AND assign them to their variants
            int iCntMarketingtexte = 0;
            for ( final String sMarketingText : hMapMarketingtext2VariantPK.keySet()) {
                iCntMarketingtexte++;
                LOG.info("Processing Marketingtext #"+iCntMarketingtexte+" ...");
                final List<PK> listVariantPKs =  hMapMarketingtext2VariantPK.get(sMarketingText);
                
                // determine "best" variant/set to copy from = a variant/set with a maximum number of filled language slots for shop_description
                WeraVarianteModel bestVariant = null;
                WeraProductSetModel bestSet = null;
                int iBestFilled = 0;
                for ( final PK variantPK : listVariantPKs ) {
                    WeraVarianteModel wvm = null;
                    WeraProductSetModel wpsm = null;
                    ItemModel im = this.modelService.get(variantPK);
                    if ( im instanceof WeraVarianteModel ) {
                        wvm = (WeraVarianteModel) im;
                        LOG.info(" ... checking variant "+wvm.getCode()+"'s language slot.");
                    } else {
                        wpsm = (WeraProductSetModel) im;
                        LOG.info(" ... checking set "+wpsm.getCode()+"'s language slot.");
                    }
                    
                    int iCntFilledSlots = 0;
                    String sDebugLangs = "";
                    for ( final String sLang : compareLanguages ) {
                        SetLanguage(sLang);
                        String sText = (wvm != null) ? wvm.getShop_description() : wpsm.getShop_description();
                        if ( ! StringUtils.trimToEmpty(sText).isEmpty() ) {
                            iCntFilledSlots++;
                            sDebugLangs+=(sLang + ", ");
                        }
                    }
                    LOG.info(" ... ... filled slots: "+sDebugLangs);
                    if ( iCntFilledSlots > iBestFilled ) {
                        if ( wvm != null ) {
                            bestVariant = wvm; bestSet = null;
                            LOG.info(" ... this makes this variant current best.");
                        } else {
                            bestSet = wpsm; bestVariant = null;
                            LOG.info(" ... this makes this set current best.");
                        }
                        iBestFilled = iCntFilledSlots;
                    }
                }
                
                if ( bestVariant != null || bestSet != null ) {
                    TextbausteinModel newTbm = this.modelService.create("Textbaustein");
                    newTbm.setCode( MARKETING_MARKER + String.format("%04d", iCntMarketingtexte) );
                    SetLanguage("de");
                    newTbm.setText(sMarketingText);
                    for ( final String sLang : allLanguages ) {
                        SetLanguage(sLang);
                        String sDesc = ( bestVariant != null ) ?  bestVariant.getShop_description() : bestSet.getShop_description();
                        if ( sDesc == null ) {
                            sDesc = "";
                        }
                        LOG.info (" ... setting TBM's "+sLang+" slot to content with length " + sDesc.length());
                        newTbm.setText( StringUtils.trimToEmpty( sDesc ) );
                    }
                    this.modelService.save(newTbm);
                    LOG.info("Created new textbaustein >"+newTbm.getCode()+"<");
                    Collection<TextbausteinModel> colNewTB = new ArrayList();
                    colNewTB.add(newTbm);
                    
                    for ( final PK variantPK : listVariantPKs ) {
                        ItemModel im = this.modelService.get(variantPK);
                        if ( im instanceof WeraVarianteModel ) {
                            WeraVarianteModel wvm2 = (WeraVarianteModel) im;
                            wvm2.setWeravariante2marketing(colNewTB);
                            this.modelService.save(wvm2);
                            LOG.info(" ... assigned to variant >"+wvm2.getCode()+"<");
                        } else {
                            WeraProductSetModel wpsm2 = (WeraProductSetModel) im;
                            wpsm2.setWeraproduct2marketing(colNewTB);
                            this.modelService.save(wpsm2);
                            LOG.info(" ... assigned to set >"+wpsm2.getCode()+"<");
                        }
                    }
                } else {
                    LOG.error("ERROR: could not determine best variant/set, all language slots empty.");
                }
            }
            
            // STEP 4: determine most used BP and MT for each wera product and assign them to the new base product field lists.
            // for each variant set precedence flag accordingly and delete lists where the base product carries the most used BP/MT.
            iNumProducts = 0;
            for ( final WeraProductModel wpm : listProducts ) {
                iNumProducts++;
                if ( iNumProducts % 10 == 0 ) {
                    LOG.info("Processing product "+iNumProducts+ " / " + iMaxProducts + " ...");
                }
                if ( wpm instanceof WeraProductSetModel ) {
                    LOG.info(" ... product "+wpm.getCode()+ " is a set, no further processing needed.");
                } else {
                    Map<String, List<PK>> hBulletpoint2VariantPKs = new HashMap();
                    Map<String, List<PK>> hMarketingtext2VariantPKs = new HashMap();
                    Collection<VariantProductModel> colVariants = wpm.getVariants();
                    for ( final VariantProductModel oVariant : colVariants ) {
                        if ( oVariant instanceof WeraVarianteModel ) {
                            WeraVarianteModel wvm = (WeraVarianteModel) oVariant;
                            Collection<TextbausteinModel> colBPs = wvm.getWeravariante2bulletpoints();
                            Collection<TextbausteinModel> colMTs = wvm.getWeravariante2marketing();
                            String sBPKey = StringUtils.join(colBPs, "|");
                            String sMTKey = StringUtils.join(colMTs, "|");
                            LOG.info("BPKey for variant "+wvm.getCode()+" = "+sBPKey);
                            LOG.info("sMTKey for variant "+wvm.getCode()+" = "+sMTKey);

                            // store, which list of BP/MT is covered by which variant per base product
                            List<PK> listVariantPKs =  hBulletpoint2VariantPKs.get(sBPKey);
                            if ( listVariantPKs == null ) {
                                listVariantPKs = new ArrayList();
                            }
                            listVariantPKs.add(wvm.getPk());
                            hBulletpoint2VariantPKs.put(sBPKey, listVariantPKs);

                            listVariantPKs =  hMarketingtext2VariantPKs.get(sMTKey);
                            if ( listVariantPKs == null ) {
                                listVariantPKs = new ArrayList();
                            }
                            listVariantPKs.add(wvm.getPk());
                            hMarketingtext2VariantPKs.put(sMTKey, listVariantPKs);    
                        }
                    }
                    // determine "best" = most used BP list among variants
                    int iMaxUsage = 0;
                    String sBestBPKey = null;
                    for ( final String sBPKey : hBulletpoint2VariantPKs.keySet()) {
                        List<PK> listVariantPKs = hBulletpoint2VariantPKs.get(sBPKey);
                        if ( listVariantPKs != null ) {
                            int iNumberOfVariants = listVariantPKs.size();
                            if ( iNumberOfVariants > iMaxUsage ) {
                                sBestBPKey = sBPKey;
                                iMaxUsage = iNumberOfVariants;
                            }
                        }
                    }
                    if ( sBestBPKey != null ) {
                        List<PK> listVariantPKs = hBulletpoint2VariantPKs.get(sBestBPKey);
                        final PK samplePK = listVariantPKs.iterator().next();
                        WeraVarianteModel bestWvm = this.modelService.get(samplePK);
                        LOG.info("Product "+wpm.getCode()+" most used BP is: "+sBestBPKey);
                        LOG.info(" ... it's used by  "+iMaxUsage+" variants, for example: "+bestWvm.getCode() );
                        // set base product's bullet point list to most used one
                        wpm.setWeraproductset2bulletpoints( bestWvm.getWeravariante2bulletpoints() );
                        this.modelService.save(wpm);
                        LOG.info(" ... assigning this BP list to product "+wpm.getCode() );

                        // for each set of BP either set variant precedence to true (not most used BP list) or false (most used BP list)
                        for ( final String sBPKey : hBulletpoint2VariantPKs.keySet()) {
                            List<PK> listPKs = hBulletpoint2VariantPKs.get(sBPKey);
                            if ( listPKs != null ) {
                                for ( final PK variantPK : listPKs ) {
                                    WeraVarianteModel currentWvm = this.modelService.get(variantPK);
                                    if ( sBPKey.equals(sBestBPKey) ) {
                                        currentWvm.setPrecedence_bulletpoints(false);
                                        // if BP list is maintained in base product then clear the variant list to avoid confusion.
                                        // currentWvm.setWeravariante2bulletpoints( new ArrayList() );
                                        LOG.info("... variant "+currentWvm.getCode()+" uses most used BP list => precedence = FALSE");
                                    } else {
                                        currentWvm.setPrecedence_bulletpoints(true);
                                        LOG.info("... variant "+currentWvm.getCode()+" does NOT use most used BP list => precedence = TRUE");
                                    }
                                    this.modelService.save(currentWvm);
                                }
                            }
                        }
                    }
                    // repeat process for MT ...
                    // determine "best" = most used MT list among variants
                    iMaxUsage = 0;
                    String sBestMTKey = null;
                    for ( final String sMTKey : hMarketingtext2VariantPKs.keySet()) {
                        List<PK> listVariantPKs = hMarketingtext2VariantPKs.get(sMTKey);
                        if ( listVariantPKs != null ) {
                            int iNumberOfVariants = listVariantPKs.size();
                            if ( iNumberOfVariants > iMaxUsage ) {
                                sBestMTKey = sMTKey;
                                iMaxUsage = iNumberOfVariants;
                            }
                        }
                    }
                    if ( sBestMTKey != null ) {
                        List<PK> listVariantPKs = hMarketingtext2VariantPKs.get(sBestMTKey);
                        final PK samplePK = listVariantPKs.iterator().next();
                        WeraVarianteModel bestWvm = this.modelService.get(samplePK);
                        LOG.info("Product "+wpm.getCode()+" most used MT is: "+sBestMTKey);
                        LOG.info(" ... it's used by  "+iMaxUsage+" variants, for example: "+bestWvm.getCode() );

                        // set base product's marketingtext list to most used one
                        wpm.setWeraproduct2marketing( bestWvm.getWeravariante2marketing() );
                        this.modelService.save(wpm);
                        LOG.info(" ... assigning this MT list to product "+wpm.getCode() );

                        // for each set of MT either set variant precedence to true (not most used MT list) or false (most used MT list)
                        for ( final String sMTKey : hMarketingtext2VariantPKs.keySet()) {
                            List<PK> listPKs = hMarketingtext2VariantPKs.get(sMTKey);
                            if ( listPKs != null ) {
                                for ( final PK variantPK : listPKs ) {
                                    WeraVarianteModel currentWvm = this.modelService.get(variantPK);
                                    if ( sMTKey.equals(sBestMTKey) ) {
                                        currentWvm.setPrecedence_marketing(false);
                                        // if MT list is maintained in base product then clear the variant list to avoid confusion.
                                        // currentWvm.setWeravariante2marketing( new ArrayList() );
                                        LOG.info("... variant "+currentWvm.getCode()+" uses most used MT list => precedence = FALSE");                                    
                                    } else {
                                        currentWvm.setPrecedence_marketing(true);
                                        LOG.info("... variant "+currentWvm.getCode()+" does NOT use most used MT list => precedence = TRUE");
                                    }
                                    this.modelService.save(currentWvm);
                                }
                            }
                        }
                    }                    
                }
            }
            
        }

        public Collection mapProductActiveToDate(final int iMode) {
            final Logger LOG = Logger.getLogger(DataCorrector.class.getName());
            Collection colVariantLists = new ArrayList();
            
            final String sInactiveVariantsQuery = "select {v.pk} from {WeraVariante! as v} where {v.aktiv}=false order by {v.code}";
            final String sInactiveSetVariantsQuery = "select {v.pk} from {WeraVarianteSet! as v} where {v.aktiv}=false order by {v.code}";
            
            final SearchResult<WeraVarianteModel> rVariants = flexibleSearchService.search(sInactiveVariantsQuery);
            final SearchResult<WeraVarianteSetModel> rSetVariants = flexibleSearchService.search(sInactiveSetVariantsQuery);
            final List<WeraVarianteModel> listVariants = rVariants.getResult();
            final List<WeraVarianteSetModel> listSetVariants = rSetVariants.getResult();
            
            if ( iMode == 1 || iMode == 2 ) {
                String sMode = ( iMode == 1 ) ? "setting" : "deleting" ;
                for ( final WeraVarianteModel wvm : listVariants ) {
                           
                    LOG.info("mapProductActiveToDate(): " + sMode + " expiration date for variant " + wvm.getCode() + " (" + wvm.getPk() + ")");
                    if ( iMode == 1 ) {
                        if ( wvm.getValid_to() == null ) {
                            wvm.setValid_to( dEarliest );
                        } else {
                            LOG.info("mapProductActiveToDate():    valid_to already defined, don't change: "+wvm.getValid_to() );
                        }
                    } else {
                        if ( wvm.getValid_to().equals(dEarliest) ) {
                            wvm.setValid_to( null);
                        } else {
                            LOG.info("mapProductActiveToDate():    valid_to already defined, don't change: "+wvm.getValid_to() );
                        }                        
                    }
                    
                    this.modelService.save(wvm);
                }
            }
            colVariantLists.add(listVariants);
            colVariantLists.add(listSetVariants);
            
            return colVariantLists;
        }
	
	public String setDefiningFeature(final String sAttributeId) {
	    final Logger LOG = Logger.getLogger(DataCorrector.class.getName());
	    String rval = "";
	    String query = "SELECT {" + ClassificationAttributeModel.PK + "} FROM {" + ClassificationAttributeModel._TYPECODE + "} WHERE {" + ClassificationAttributeModel.CODE + "} " + "='" + sAttributeId + "'";
	    final SearchResult<ClassificationAttributeModel> aResult = flexibleSearchService.search(query);
	    final List<ClassificationAttributeModel> aSearchResults = aResult.getResult();

	    if ( aSearchResults.size() > 0 ) {
		int i=0;
		for ( ClassificationAttributeModel cam : aSearchResults ) {
		    i++;
		    LOG.info("setDefiningFeature(): setting DefiningFeature flag for attribute id >"+sAttributeId+"<, occurrence #"+i);
		    cam.setIs_defining_feature(true);
		    this.modelService.save(cam);
		}
	    } else {
		LOG.info("setDefiningFeature(): no feature found with attribute id >"+sAttributeId+"<");
	    }
	    return rval;
	}
        
        public String setGreatToolsLink(final String sAttributeId, final String sLinkId) {
	    final Logger LOG = Logger.getLogger(DataCorrector.class.getName());
	    String rval = "";
	    String query = "SELECT {" + ClassificationAttributeModel.PK + "} FROM {" + ClassificationAttributeModel._TYPECODE + "} WHERE {" + ClassificationAttributeModel.CODE + "} " + "='" + sAttributeId + "'";
	    final SearchResult<ClassificationAttributeModel> aResult = flexibleSearchService.search(query);
	    final List<ClassificationAttributeModel> aSearchResults = aResult.getResult();
            query = "SELECT {" + WeblinkModel.PK + "} FROM {" + WeblinkModel._TYPECODE + "} WHERE {" + WeblinkModel.CODE + "} " + "='" + sLinkId + "'";
            final SearchResult<WeblinkModel> aResultWeblinks = flexibleSearchService.search(query);
            final List<WeblinkModel> aSearchResultsWeblinks = aResultWeblinks.getResult();
            
            if ( aSearchResultsWeblinks.size() > 0 ) {
                WeblinkModel wlm = aSearchResultsWeblinks.iterator().next();
                
                if ( aSearchResults.size() > 0 ) {
                    int i=0;
                    Collection<WeblinkModel> colWeblinks = new ArrayList();
                    for ( ClassificationAttributeModel cam : aSearchResults ) {
                        i++;
                        colWeblinks.clear();
                        colWeblinks.add(wlm);
                        cam.setWeblinks_greattools(colWeblinks);
                        this.modelService.save(cam);
                        LOG.info("setGreatToolsLink(): saved GreatTools Link for attribute id >"+sAttributeId+"< with link >"+wlm.getCode()+"<, occurrence #"+i);
                    }
                } else {
                    LOG.info("setGreatToolsLink(): no feature found with attribute id >"+sAttributeId+"<");
                }
            } else {
                LOG.info("setGreatToolsLink(): no link found with link id >"+sLinkId+"<");
            }
	    return rval;
	}        
	
	public String setAlternativeNames(final String sLang, final String sAttributeId, final String sAltName, final String sAltImageName ) {
	    final Logger LOG = Logger.getLogger(DataCorrector.class.getName());
	    String rval = "";
	    String query = "SELECT {" + ClassificationAttributeModel.PK + "} FROM {" + ClassificationAttributeModel._TYPECODE + "} WHERE {" + ClassificationAttributeModel.CODE + "} " + "='" + sAttributeId + "'";
	    final SearchResult<ClassificationAttributeModel> aResult = flexibleSearchService.search(query);
	    final List<ClassificationAttributeModel> aSearchResults = aResult.getResult();

	    if ( aSearchResults.size() > 0 ) {
		int i=0;
		for ( ClassificationAttributeModel cam : aSearchResults ) {
		    i++;
		    LOG.info("setAlternativeNames(): setting alt names for attribute id >"+sAttributeId+"<, occurrence #"+i);
		    
		    SetLanguage(sLang);
		    try {
			cam.setAlternative_name( new String(sAltName.getBytes("ISO-8859-1")) );
			cam.setAlternative_image_name( new String (sAltImageName.getBytes("ISO-8859-1")) );
		    } catch (UnsupportedEncodingException ex) {
			java.util.logging.Logger.getLogger(DataCorrector.class.getName()).log(Level.SEVERE, null, ex);
		    }
		    this.modelService.save(cam);
		}
	    } else {
		LOG.info("setAlternativeNames(): no feature found with attribute id >"+sAttributeId+"<");
	    }
	    SetLanguage("de");
	    return rval;
	}

        public String importKeywords(final String sCode, final String sKeywordsParam) {
            final Logger LOG = Logger.getLogger(DataCorrector.class.getName());
            String sKeywords = "";
            try {
                sKeywords += new String(sKeywordsParam.getBytes("ISO-8859-1")).trim();
            } catch (UnsupportedEncodingException ex) {
		java.util.logging.Logger.getLogger(DataCorrector.class.getName()).log(Level.SEVERE, null, ex);
	    }
            ProductModel p = null;
            try {
                p = this.productService.getProductForCode(sCode);
            } catch ( UnknownIdentifierException uie) {
                LOG.error("UnknownIdentifierException for code " + sCode);
            }
            if ( p == null ) {
                LOG.warn("Cannot find product for code " + sCode);
              
            } else {
                if ( p instanceof WeraProductSetinSetModel ) {
                    WeraProductSetinSetModel wpsis = (WeraProductSetinSetModel) p;
                    LOG.info("Found SiS "+ wpsis.getCode());
                    LOG.info("  ... setting keywords for SiS wrapper: " + wpsis.getCode());
                    wpsis.setShop_keywords(sKeywords);
                    this.modelService.save(wpsis);                    
                    final Collection<WeraProductSetVariantsModel> colSetWrapper = wpsis.getWeraproductsetvariants_qual();
                    final Collection<WeraVarianteVariantsModel> colVariantsWrapper = wpsis.getWeravariantevariants_qual();

                    int iCnt = 0;
                    for (final WeraProductSetVariantsModel oSiSSetWrap : colSetWrapper)
                    {
                        WeraProductSetModel wpsm = oSiSSetWrap.getWeraproductsets();
                        if (wpsm!=null) {
                            iCnt++;
                            LOG.info("  ... setting keywords for SiS component #" + iCnt + ": " + wpsm.getCode());
                            wpsm.setShop_keywords(sKeywords);
                            this.modelService.save(wpsm);
                        }
                    }

                } else {
                    if ( ! (p instanceof WeraProductSetModel) ) {
                        WeraProductModel wpm = (WeraProductModel) p;
                        LOG.info("Found product family "+ wpm.getCode());
                        Collection<VariantProductModel> colVariants = wpm.getVariants();
                        int iCnt = 0;
                        for (final VariantProductModel oVariant : colVariants)
                        {
                            iCnt++;
                            WeraVarianteModel wvm = (WeraVarianteModel) oVariant;
                            LOG.info("  ... setting keywords for variant #" + iCnt + ": " + wvm.getCode());
                            wvm.setShop_keywords(sKeywords);
                            this.modelService.save(wvm);
                        }
                    } else {
                        if ( p instanceof WeraProductSetModel ) {
                            WeraProductSetModel wpsm = (WeraProductSetModel) p;
                            LOG.info("Found set "+ wpsm.getCode());
                            LOG.info("  ... setting keywords for set: " + wpsm.getCode());
                            wpsm.setShop_keywords(sKeywords);
                            this.modelService.save(wpsm);                    
                        }
                    }
                }
            }
            return "1";
        }
        
        
        
        
        public String importMediaFeatureDescription(final String sRefLang, final String sRefFeatureDescUtf8, final String sDestLang, final String sDestFeatureDescUtf8) {
            String rval = "";
            
            try {
                String sRefFeatureDesc = new String(sRefFeatureDescUtf8.getBytes("ISO-8859-1")).trim();
                String sDestFeatureDesc = new String(sDestFeatureDescUtf8.getBytes("ISO-8859-1")).trim();
                final Logger LOG = Logger.getLogger(DataCorrector.class.getName());

                String query = "SELECT {" + WeraMediaModel.PK + "} FROM {" + WeraMediaModel._TYPECODE + "} WHERE {" + WeraMediaModel.FEATURE_DESCRIPTION + "["+sRefLang+"]} " + "='" + sRefFeatureDesc + "'";
                LOG.info("query = "+query);
                final SearchResult<WeraMediaModel> aResult = flexibleSearchService.search(query);
                final List<WeraMediaModel> aSearchResults = aResult.getResult();

                if ( aSearchResults.size() > 0 ) {
                    SetLanguage( sDestLang );
                    for ( WeraMediaModel wmm : aSearchResults ) {
                        LOG.info("Found WMM "+wmm.getCode()+". Setting feature_desc to: "+sDestFeatureDesc);
                        wmm.setFeature_description(sDestFeatureDesc);
                        this.modelService.save(wmm);
                    }
                }
            } catch (UnsupportedEncodingException ex) {
		java.util.logging.Logger.getLogger(DataCorrector.class.getName()).log(Level.SEVERE, null, ex);
	    }

            return rval;
        }

        public String importAlternativeNames(final String sRefLang, final String sRefAltNameUtf8, final String sDestLang, final String sDestAltNameUtf8) {
            String rval = "";
            
            try {
                String sRefAltName = new String(sRefAltNameUtf8.getBytes("ISO-8859-1")).trim();
                String sDestAltName = new String(sDestAltNameUtf8.getBytes("ISO-8859-1")).trim();
                final Logger LOG = Logger.getLogger(DataCorrector.class.getName());

                String query = "SELECT {" + ClassificationAttributeModel.PK + "} FROM {" + ClassificationAttributeModel._TYPECODE + "} WHERE {" + ClassificationAttributeModel.ALTERNATIVE_NAME + "["+sRefLang+"]} " + "='" + sRefAltName + "'";
                LOG.info("query = "+query);
                final SearchResult<ClassificationAttributeModel> aResult = flexibleSearchService.search(query);
                final List<ClassificationAttributeModel> aSearchResults = aResult.getResult();

                if ( aSearchResults.size() > 0 ) {
                    SetLanguage( sDestLang );
                    for ( ClassificationAttributeModel cam : aSearchResults ) {
                        LOG.info("Found CAM "+cam.getCode()+". Setting alt_name to: "+sDestAltName);
                        cam.setAlternative_name(sDestAltName);
                        this.modelService.save(cam);
                    }
                }
            } catch (UnsupportedEncodingException ex) {
		java.util.logging.Logger.getLogger(DataCorrector.class.getName()).log(Level.SEVERE, null, ex);
	    }
            SetLanguage( "de" );
            return rval;
        }

        
        /* sets attribute names to sNameForeignUTF8, if attribute id matches sCode AND the german attribute name matches sNameGermanUTF8.
	 * Note: parameters given by UTF8 encoded JSP need to be ISO decoded in this method for some reason.
	 * Also note: ISO encoding JSP does not work, because for example russian cannot be written with ISO.
	 */
	public String importFeatureName(final String sLang, final String sCode, final String sNameGermanUTF8, final String sNameForeignUTF8 ) {
	    
	    String sNameGerman, sNameForeign;
	    String rval = "";
	    try {
		sNameGerman = new String(sNameGermanUTF8.getBytes("ISO-8859-1")).trim();
		
		
		final Logger LOG = Logger.getLogger(DataCorrector.class.getName());

		String query = "SELECT {" + ClassificationAttributeModel.PK + "} FROM {" + ClassificationAttributeModel._TYPECODE + "} WHERE {" + ClassificationAttributeModel.CODE + "} " + "='" + sCode + "'";
		final SearchResult<ClassificationAttributeModel> aResult = flexibleSearchService.search(query);
		final List<ClassificationAttributeModel> aSearchResults = aResult.getResult();

		if ( aSearchResults.size() > 0 ) {
		    for ( ClassificationAttributeModel cam : aSearchResults ) {
			ClassificationSystemVersionModel csv = cam.getSystemVersion();
			String sCSV = (csv != null) ? csv.getVersion() : "<null>";
			SetLanguage("de");
			String sCurrentGermanName = cam.getName().trim();
			if ( true ||  sCurrentGermanName.equals(sNameGerman)) {
			    sNameForeign = new String(sNameForeignUTF8.getBytes("ISO-8859-1")).trim();
                            LOG.info("importFeatureName(): setting name for ("+sLang+", "+sCode+", "+sCSV+", "+sNameGerman+") to: "+sNameForeign );
			    SetLanguage(sLang);
			    cam.setName(sNameForeign);
			    this.modelService.save(cam);
			} else {
			    LOG.info("SKIP\t" + sLang + "\t" + sCode + "\t" + sCSV + "\t" + sCurrentGermanName + "\t" + sNameGerman );
			}
		    }
		}		
	    } catch (UnsupportedEncodingException ex) {
		java.util.logging.Logger.getLogger(DataCorrector.class.getName()).log(Level.SEVERE, null, ex);
	    }
            SetLanguage( "de" );
	    return rval;
	}        
        
        
        
	/* sets attribute names to sNameForeignUTF8, if attribute id matches sCode AND the german attribute name matches sNameGermanUTF8.
	 * Note: parameters given by UTF8 encoded JSP need to be ISO decoded in this method for some reason.
	 * Also note: ISO encoding JSP does not work, because for example russian cannot be written with ISO.
	 */
	/* extended private method to allow setting attribute names and alternative names as well as setting boolean isDefining.*/
	public String _importFeatureNameData(final String sLang, final String sCode, final String sNameUTF8, final String sAltNameUTF8, final String sAltFilename, final String isDefining ) {
	    String sName, sNameForeign;
	    String rval = "";
	    boolean bIsDefining = isDefining.equals("1");
	    try {
		sName = new String(sNameUTF8.getBytes("ISO-8859-1")).trim();
		final Logger LOG = Logger.getLogger(DataCorrector.class.getName());

		String query = "SELECT {" + ClassificationAttributeModel.PK + "} FROM {" + ClassificationAttributeModel._TYPECODE + "} WHERE {" + ClassificationAttributeModel.CODE + "} " + "='" + sCode + "'";
		final SearchResult<ClassificationAttributeModel> aResult = flexibleSearchService.search(query);
		final List<ClassificationAttributeModel> aSearchResults = aResult.getResult();

		if ( aSearchResults.size() > 0 ) {
		    for ( ClassificationAttributeModel cam : aSearchResults ) {
			ClassificationSystemVersionModel csv = cam.getSystemVersion();
			String sCSV = (csv != null) ? csv.getVersion() : "<null>";
			SetLanguage(sLang);
			String sCurrentName = StringUtils.trimToEmpty(cam.getName());
			if ( sCurrentName.equals(sName)) {
			    LOG.info("_importFeatureNameData(): setting name for ("+sLang+", "+sCode+", "+sCSV+", "+sCurrentName+") to: "+sAltNameUTF8 );
			    sNameForeign = new String(sAltNameUTF8.getBytes("ISO-8859-1")).trim();
			    SetLanguage(sLang);
			    cam.setAlternative_name(sNameForeign);
			    cam.setAlternative_image_name(sAltFilename);
			    cam.setIs_defining_feature(bIsDefining);
			    this.modelService.save(cam);
			} else {
			    LOG.info("SKIP\t" + sLang + "\t" + sCode + "\t" + sCSV + "\t" + sCurrentName + "\t" + sName );
			}
		    }
		}		
	    } catch (UnsupportedEncodingException ex) {
		java.util.logging.Logger.getLogger(DataCorrector.class.getName()).log(Level.SEVERE, null, ex);
	    }
	    return rval;
	}
	
	public String appendShopKeywords( final String sCode ) {
	    final Logger LOG = Logger.getLogger(DataCorrector.class.getName());
	
	    Map<String, String> langiso2text = new HashMap();
	    langiso2text.put( "de", "Take it easy Werkzeugfinder" );
	    langiso2text.put( "cn", "螺丝刀带“Take it easy”易寻色标系统" );
	    langiso2text.put( "cs", "Vyhledávač nářadí „Take it easy“" );
	    langiso2text.put( "dk", "Take it easy værktøjsfinder" );
	    langiso2text.put( "en", "“Take it easy” Tool Finder" );
	    langiso2text.put( "es", "Sistema de búsqueda de herramienta “Take it easy“" );
	    langiso2text.put( "fr", "Repéreur d’outils « Take it easy »" );
	    langiso2text.put( "it", "Trova-utensili ”take it easy\"" );
	    langiso2text.put( "nl", "Take it easy toolfinder" );
	    langiso2text.put( "no", "«Take it easy» verktøyidentifikasjon" );
	    langiso2text.put( "pl", "System wyszukiwania Take it easy" );
	    langiso2text.put( "ru", "Идентификаторы инструментов \"Take it easy\"" );
	    langiso2text.put( "se", "Verktygsväljare Take it easy" );
	    
	    if (sCode.length() == 11 ) {
		String sSixDigitCode = sCode.substring(2, 8);
		Collection<ProductModel> colMatchingProducts = findAllMatchingProducts(sSixDigitCode);
		if ( colMatchingProducts.size() > 1 ) {
		    LOG.info("appendShopKeywords(): NOTICE: "+colMatchingProducts.size() + " products matching code >"+sSixDigitCode+"<");
		}
		if ( colMatchingProducts.size() == 0 ) {
		    LOG.info("appendShopKeywords(): NOTICE: No products found matching code >"+sSixDigitCode+"<");
		}

		for ( final ProductModel oProduct : colMatchingProducts) {
		    boolean bIsVariant = (oProduct instanceof WeraVarianteModel);
		    boolean bIsSet = (oProduct instanceof WeraProductSetModel);
		    if ( ! bIsVariant && ! bIsSet ) {
			LOG.warn("appendShopKeywords(): code "+sCode+" is not a valid code.");
			continue;
		    }
		    Set<String> setLangiso = langiso2text.keySet();
		    for ( final String sLangiso : setLangiso ) {
			SetLanguage( sLangiso );
			if ( bIsVariant ) {
			    WeraVarianteModel wvm = (WeraVarianteModel) oProduct;
			    String sShopkeywords = StringUtils.trimToEmpty(wvm.getShop_keywords());
			    String sText = (String) langiso2text.get(sLangiso);
			    if ( ! sShopkeywords.contains(sText) ) {
				LOG.info("appendShopKeywords(): adding keyword >"+sText+"< to variant "+sCode+" in language "+sLangiso);
				if ( ! sShopkeywords.endsWith(";") ) {
				    sShopkeywords += ";";
				}
				sShopkeywords += sText;
				wvm.setShop_keywords(sShopkeywords);
				this.modelService.save(wvm);
			    } else {
				LOG.info("appendShopKeywords(): SKIP keyword >"+sText+"< to variant "+sCode+" in language "+sLangiso);
			    }
			} else {
			    if ( bIsSet ) {
				WeraProductSetModel wps = (WeraProductSetModel) oProduct;
				String sShopkeywords = StringUtils.trimToEmpty(wps.getShop_keywords());
				String sText = (String) langiso2text.get(sLangiso);
				if ( ! sShopkeywords.contains(sText) ) {
				    LOG.info("appendShopKeywords(): adding keyword >"+sText+"< to set "+sCode+" in language "+sLangiso);
				    if ( ! sShopkeywords.endsWith(";") ) {
					sShopkeywords += ";";
				    }
				    sShopkeywords += sText;
				    wps.setShop_keywords(sShopkeywords);
				    this.modelService.save(wps);
				} else {
				    LOG.info("appendShopKeywords(): SKIP keyword >"+sText+"< to set "+sCode+" in language "+sLangiso);
				}
			    }
			}
		    }
		}
	    } else {
		LOG.warn("appendShopKeywords(): code does not have 11 digits: "+sCode);
	    }
	    return "";
	}

	public String importWeblinks( final String sProductCode, final String sDocument ) {
	    String sRval = "";
	    final String sRefWeblinkTS = "web_lnk_torque_service";
	    final Logger LOG = Logger.getLogger(DataCorrector.class.getName());
	    final PK pkTorqueServiceWeblink = PK.parse("8823421599843");
	    //final PK pkTorqueServiceWeblink = PK.parse("8821652095075");
	    WeblinkModel newTSWeblink = (WeblinkModel) this.modelService.get(pkTorqueServiceWeblink);
	    Collection<WeblinkModel> colNewWeblinks = new ArrayList();
	    LOG.info("Found reference weblink "+newTSWeblink.getCode());
	    
	    if ( sProductCode != null && sDocument != null ) {
		ProductModel p = null;
		try {
		    p = productService.getProduct(sProductCode);
		    String query = "SELECT {" + WeblinkModel.PK + "} FROM {" + WeblinkModel._TYPECODE + "} WHERE {" + WeblinkModel.CODE + "} " + "='" + sDocument + "'";
		    final SearchResult<WeblinkModel> setCodeSearchResult = flexibleSearchService.search(query);
		    final List<WeblinkModel> weblinksSearchResults = setCodeSearchResult.getResult();
		    if ( weblinksSearchResults.size() > 0 ) {

			WeblinkModel newWeblink = weblinksSearchResults.iterator().next();
			if ( p instanceof WeraProductModel ) {
			    WeraProductModel wpm = (WeraProductModel) p;
			    LOG.info( "found product with name " + wpm.getName() );
			    Collection<WeblinkModel> colWeblinks = wpm.getWeblinks();
			    for ( final WeblinkModel wlm: colWeblinks ) {
				if ( wlm.getCode().equals( sDocument )) {
				    break;
				}
			    }
			    colNewWeblinks.clear();
			    colNewWeblinks.addAll(colWeblinks);
			    colNewWeblinks.add( newWeblink );
			    wpm.setWeblinks(colNewWeblinks);
			    this.modelService.save(wpm);
			    LOG.info("Saved product >"+wpm.getCode()+"< with "+colNewWeblinks.size()+" weblinks (formerly: "+colWeblinks.size()+".");

			} else {
			    LOG.info("Product with Code "+sProductCode+" is not a WP!");
			}
		    } else {
			LOG.info("Could not find a weblink for code "+sDocument);
		    }
		    
		} catch (UnknownIdentifierException uie) {
		    LOG.info("Cannot find product for code "+ sProductCode);
		}
	    } else {
		Collection<String> colTorqueService = new ArrayList<String>(Arrays.asList("Click-Torque A 5", "Click-Torque A 6", "Click-Torque B 1", "Click-Torque B 2", "Click-Torque C 1", "Click-Torque C 2", "Click-Torque C 3", "Click-Torque C 4", "Click-Torque C 5", "Click-Torque E 1", "Click-Torque X 1", "Click-Torque X 2", "Click-Torque X 3", "Click-Torque X 4", "Click-Torque X 5", "Click-Torque X 6", "Click-Torque A 6 Set 1", "300 Hex", "300 Hex Pistole", "300 TX", "300 TX Pistole", "300 IP", "300 IP Pistole", "7773 A", "7773 B", "7773 C", "7783 C", "7770", "7771", "7772 A", "7772 B", "7772 C", "7774/1", "7774/2", "7774/3", "7775", "7776", "7779/1", "7779/2", "7780", "7781", "7782 C", "7782 E", "7783 E", "7786", "7790/1", "7790/2", "7400", "7400 Pistole", "7400 inch", "7400 Pistole inch", "7400 ESD", "7400 ESD inch", "7440/41", "7440/41/42", "7443/61/9", "7443/12", "7400 ESD Halfmoon", "1430 ESD", "7445/46/47", "7400 vor", "7400 Pistole vor", "7400 vor inch", "7400 Pistole vor inch", "1460 Micro ESD", "7400 vor ESD", "7400 ESD vor Halfmoon"));
		Collection<Product> colProducts = this.getProducts();

		for ( final Product product : colProducts ) {
		    if ( product instanceof WeraProduct ) {
			WeraProduct wp = (WeraProduct) product;
			String sCode = wp.getCode();
			if (sCode != null && colTorqueService.contains(sCode)) {
			    LOG.info("Found product >"+sCode+"<");
			    WeraProductModel wpm = this.modelService.get(wp);
			    Collection<WeblinkModel> colWeblinks = wpm.getWeblinks();
			    boolean bCovered = false;
			    for ( final WeblinkModel wlm: colWeblinks ) {
				if ( wlm.getCode().equals( sRefWeblinkTS )) {
				    bCovered = true;
				    break;
				}
			    }
			    if ( ! bCovered ) {
				colNewWeblinks.clear();
				colNewWeblinks.addAll(colWeblinks);
				colNewWeblinks.add( newTSWeblink );
				wpm.setWeblinks(colNewWeblinks);
				this.modelService.save(wpm);
				LOG.info("Saved product >"+sCode+"< with "+colNewWeblinks.size()+" weblinks (formerly: "+colWeblinks.size()+".");
			    } else {
				LOG.info("product >"+sCode+"< already contains Weblink "+sRefWeblinkTS);
			    }
			    colTorqueService.remove(sCode);
			}
		    }
		}
		sRval = "Products NOT covered: ";
		for ( String sCode : colTorqueService ) {
		    sRval += (sCode + ", ");
		}
	    }
	    return sRval;
	}
	
        public void changeProduct2CategoryRelation( final String sProductCode, final String sCategory, final String sMode ) {
            ProductModel pm = null;
            String sProduct = null;
            try {
                sProduct = new String(sProductCode.getBytes("ISO-8859-1")).trim();
            } catch (UnsupportedEncodingException ex) {
                java.util.logging.Logger.getLogger(DataCorrector.class.getName()).log(Level.SEVERE, null, ex);
            }
            final Logger LOG = Logger.getLogger(DataCorrector.class.getName());
            try {
                pm = productService.getProduct(sProduct);
            } catch (UnknownIdentifierException e) {
                LOG.info("Could not find product with code "+sProduct);
            }
            if (pm != null) {
                WeraProductModel wpm = (WeraProductModel) pm;
                // 8801663615577 [601]
                // get category from "web_ipim"
                // 8796093121113 [601]    ==> weramaster
                // final String sCategoryQuery = "SELECT {PK} FROM {Category} where {code}='"+sCategory+"' and {catalogversion}='8801729151577'"; // web_ipim
                final String sCategoryQuery = "SELECT {PK} FROM {Category} where {code}='"+sCategory+"' and {catalogversion}='8796093121113'"; // weramaster
                final SearchResult<CategoryModel> rCategories = flexibleSearchService.search(sCategoryQuery);
                final List<CategoryModel> listCategories = rCategories.getResult();
                CategoryModel oCategory = null;
                if (! listCategories.isEmpty() ) {
                    oCategory = listCategories.iterator().next();
                }
                
                LOG.info("Processing "+sMode+" mode for "+sProduct+" in "+sCategory);
                
                if ( sMode.equals("del") ) {
                    if ( oCategory != null ) {
                        Collection<ProductModel> colProducts = oCategory.getProducts();
                        if (colProducts.contains(pm)) {
                            List<ProductModel> newColProducts = new ArrayList();
                            newColProducts.addAll(colProducts);
                            boolean bChanged = newColProducts.remove(pm);
                            if (bChanged) {
                                LOG.info("removing product changed collection.");
                            }
                            oCategory.setProducts(newColProducts);
                            LOG.info( "del: saving relation ("+pm.getCode()+","+oCategory.getCode() );
                            this.modelService.save(oCategory);
                            newColProducts.clear();
                            newColProducts = null;
                        } else {
                            LOG.info( "del: NO NEED saving relation ("+pm.getCode()+","+oCategory.getCode() );
                        }
                    } else {
                        LOG.info("del: Could not find category with code "+sCategory);
                    }
                }
                
                if ( sMode.equals("add") ) {
                    if ( oCategory != null ) {
                        Collection<ProductModel> colProducts = oCategory.getProducts();
                        if (! colProducts.contains(pm)) {
                            List<ProductModel> newColProducts = new ArrayList();
                            newColProducts.addAll(colProducts);
                            newColProducts.add(pm);
                            oCategory.setProducts(newColProducts);
                            LOG.info( "add: saving relation ("+pm.getCode()+","+oCategory.getCode() );
                            this.modelService.save(oCategory);
                            newColProducts.clear();
                            newColProducts = null;
                        } else {
                            LOG.info( "add: NO NEED saving relation ("+pm.getCode()+","+oCategory.getCode() );
                        }
                    } else {
                        LOG.info("add: Could not find category with code "+sCategory);
                    }
                }
            }
        }
        
        
        public void resetNeuFlag() {
	    final Logger LOG = Logger.getLogger(DataCorrector.class.getName());
	    String query = "SELECT {" + WeraProductModel.PK + "} FROM {" + WeraProductModel._TYPECODE + "} WHERE {" + WeraProductModel.PRODUKT_NEU + "} = true";
	    final SearchResult<WeraProductModel> setWPResults = flexibleSearchService.search(query);
            final List<WeraProductModel> wpmSearchResults = setWPResults.getResult();
            LOG.info("Found "+wpmSearchResults.size()+" WPM results with NEU set to true.");
            for (final WeraProductModel wpm : wpmSearchResults ) {
                LOG.info("Setting NEU to false for WPM "+wpm.getCode());
                wpm.setProdukt_neu(false);
                this.modelService.save(wpm);
            }
            
            // only consider REAL variants, not the wrappers
            query = "SELECT {" + WeraVarianteModel.PK + "} FROM {" + WeraVarianteModel._TYPECODE + "} WHERE {" + WeraVarianteModel.ARTIKEL_NEU + "} = true";
	    final SearchResult<WeraVarianteModel> setWVResults = flexibleSearchService.search(query);
            final List<WeraVarianteModel> wvmSearchResults = setWVResults.getResult();
            LOG.info("Found "+wvmSearchResults.size()+" WVM results with NEU set to true.");
            for (final WeraVarianteModel wvm : wvmSearchResults ) {
                if (wvm.getCode().startsWith("BASE_") || wvm.getCode().startsWith("WVS-") ) {
                    continue;
                }
                LOG.info("Setting NEU to false for WVM "+wvm.getCode());
                wvm.setArtikel_neu(false);
                this.modelService.save(wvm);
            }
        }
        
	public String copyOutputcontrolUnits( final Collection<String> colLanguages ) {
	    final Logger LOG = Logger.getLogger(DataCorrector.class.getName());
	    String query = "SELECT {" + OutputcontrolModel.PK + "} FROM {" + OutputcontrolModel._TYPECODE + "}";
	    final SearchResult<OutputcontrolModel> setOCSearchResult = flexibleSearchService.search(query);
	    final List<OutputcontrolModel> ocSearchResults = setOCSearchResult.getResult();
	    int ocSearchResultsSize = ocSearchResults.size();
	    if ( ocSearchResultsSize > 0 ) {
		LOG.info("copyOutputcontrolUnits(): found "+ocSearchResults.size()+" outputcontrols.");
		int iCnt = 0;
		for (OutputcontrolModel oc : ocSearchResults) {
		    SetLanguage("de");
		    iCnt++;
		    boolean bNeedsSave = false;
                    boolean bMapped = false;
		    String ocUnit = oc.getUnitca();
		    if ( ocUnit != null ) {
			ocUnit = ocUnit.trim();
		    }
		    if ( ocUnit != null && ! ocUnit.isEmpty() ) {
			LOG.info("copyOutputcontrolUnits(): Outputcontrol " + iCnt + "/"+ ocSearchResultsSize + " with PK "+oc.getPk()+" has german unit value: "+ocUnit);
			for ( final String sLangIso : colLanguages ) {
			    SetLanguage(sLangIso);
			    if ( ! ocUnit.equals(oc.getUnitca()) ) {
                                // special mapping for russian, belarus, bulgarian and greek
				if ( sLangIso.equals("ru") || sLangIso.equals("by") || sLangIso.equals("bg") ) {
				    if (ocUnit.equals("Nm")) {
					oc.setUnitca("Нм");
                                        bMapped = true;
				    }
				    if (ocUnit.equals("mm")) {
					oc.setUnitca("мм");
                                        bMapped = true;
				    }
				} else {
                                    if ( sLangIso.equals("gr")) {
                                        if (ocUnit.equals("Nm")) {
                                            oc.setUnitca("NM");
                                            bMapped = true;
                                        }
                                    } else {
                                        if ( sLangIso.equals("he")) {
                                            if (ocUnit.equals("Nm")) {
                                                oc.setUnitca("נ\"מ");
                                            }
                                            if (ocUnit.equals("mm")) {
                                                oc.setUnitca("מ\"מ");
                                            }
                                            if (ocUnit.equals("inch")) {
                                                oc.setUnitca("אינץ׳");
                                            }
                                            if (ocUnit.equals("Newtonmeter")) {
                                                oc.setUnitca("ניוטון\\מטר");
                                            }
                                            if (ocUnit.equals("Millimeter")) {
                                                oc.setUnitca("מילימטר");
                                            }
                                        }
                                    }
                                    
				}
                                if ( ! bMapped ) {
                                    oc.setUnitca( ocUnit );
                                }
                                bMapped = false;
				bNeedsSave = true;
				LOG.info("copyOutputcontrolUnits(): setting unit to >" + ocUnit + "< for language >"+sLangIso+"<");
			    }
			}
			
			if ( bNeedsSave ) {
			    LOG.info("copyOutputcontrolUnits(): needs save!");
			    this.modelService.save(oc);
			}
		    }
		}
	    }
	    SetLanguage("de");
	    return "";
	}
	
	public Collection<ProductModel> findAllMatchingProducts(final String sCode) {
		Collection<ProductModel> colMatchingProducts = new ArrayList<ProductModel>();
		String query = null;
		query = "SELECT {v:" + WeraVarianteModel.PK + "} FROM { " + WeraVarianteModel._TYPECODE + " AS v} WHERE {v:Code}" + "='" + sCode + "'";
		final SearchResult<WeraVarianteModel> variantCodeSearchResult = flexibleSearchService.search(query);
		final List<WeraVarianteModel> productsFromVariantCodeSearchResults = variantCodeSearchResult.getResult();
		
		query = "SELECT {" + WeraProductSetModel.PK + "} FROM {" + WeraProductSetModel._TYPECODE + "} WHERE {" + WeraProductSetModel.ARTNR + "} " + "='" + sCode + "'";
		final SearchResult<WeraProductSetModel> setCodeSearchResult = flexibleSearchService.search(query);
		final List<WeraProductSetModel> productsFromSetCodeSearchResults = setCodeSearchResult.getResult();
		
		colMatchingProducts.addAll(productsFromVariantCodeSearchResults);
		colMatchingProducts.addAll(productsFromSetCodeSearchResults);
		
		return colMatchingProducts;
	}
	
	/*
	* expects tab seperated text file
	* <code>\t<marketing?>\t<great_tools?>\t<great_tools_id>
	* boolean "true" data representent by "x"
	*/
	public String importGreatToolsData( final String sInputFile ) {
	    final String[] aAllowedGreatToolsIDs = new String[] {
		"joker", "kraftform", "lasertip", "der-schraubmeissel", "kraftform-micro", 
		"kraftform-stainless", "kraftform-kompakt-vde", "kraftform-kompakt-20", "system-impaktor", "diamant-bits", 
		"system-bitorsion", "bit-check", "rapidaptor", "werkzeuge-mit-haltefunktion", "hex-plus", 
		"winkelschluessel", "zyklop-speed", "zyklop-metal-push", "zyklop-metal-switch", "zyklop-hybrid", 
		"koloss", "zyklop-mini", "take-it-easy", "textile-boxen" 	    
	    };
	    final Set<String> setAllowedGreatToolsIDs = new HashSet<String>(Arrays.asList(aAllowedGreatToolsIDs));	    
	    
	    StringBuffer result = new StringBuffer();
	    final Logger LOG = Logger.getLogger(DataCorrector.class.getName());
	    String line = null;
	    try {
		    // Expected format: CodeNo (TAB) Land (TAB) ASIN
		    final BufferedReader reader = new BufferedReader(new FileReader(sInputFile));
		    LOG.info("importGreatToolsData(): reading data from >"+sInputFile+"<");
		    while ((line = reader.readLine()) != null) {
			    line = line.replaceAll("\r", "").replaceAll("\n", "");
			    final String[] aLineInput = line.split("\t", -1);
			    if ( aLineInput.length == 4 ) {
				String sCode = StringUtils.trimToEmpty(aLineInput[0]);
				String sRepresentMarketing = StringUtils.trimToEmpty(aLineInput[1]).toLowerCase();
				String sShowGreatTools = StringUtils.trimToEmpty(aLineInput[2]).toLowerCase();
				String sGreatToolID = StringUtils.trimToEmpty(aLineInput[3]).toLowerCase();
				
				// LOG.info("importGreatToolsData(): Processing code >"+sCode+"< from input file ...");
				if ( sCode.startsWith("05") ) {
				    boolean bGreatToolsIDMissing = sShowGreatTools.equals("x") && sGreatToolID.equals("");
				    boolean bGreatToolsIDInvalid = ! sGreatToolID.isEmpty() && ! setAllowedGreatToolsIDs.contains(sGreatToolID);
				    
				    if ( (sRepresentMarketing.equals("x") || sRepresentMarketing.equals("") ) &&
					 (sShowGreatTools.equals("x") || sShowGreatTools.equals(""))  ) {
					
					boolean bRepresentMarketing = sRepresentMarketing.equals("x");
					boolean bShowGreatTools = sShowGreatTools.equals("x");
					String sRequestCode = sCode;
					String sRequestVarNr = sCode.substring( sCode.length()-3 );
					if ( sRequestCode.endsWith("001") || sRequestCode.endsWith("002") || sRequestCode.endsWith("010") ) {
					    sRequestCode = sCode.substring(2, 8); // 05123456001
					}
					
					Collection<ProductModel> colProducts = this.findAllMatchingProducts(sRequestCode);
					int iNumberMatchingProducts = colProducts.size();
					if ( iNumberMatchingProducts > 0 ) {
					    if ( iNumberMatchingProducts > 1 ) {
						LOG.info("importGreatToolsData(): Found "+iNumberMatchingProducts+" products matching code >"+sCode+"<.");
					    }
					    int iCntMatchingProducts = 0;
					    for ( final ProductModel oProductModel : colProducts ) {
						iCntMatchingProducts++;
						if ( iNumberMatchingProducts > 1 ) {
						    LOG.info("importGreatToolsData(): ... checking match #"+iCntMatchingProducts);
						}
						boolean bFoundProduct = false;
						if ( bGreatToolsIDMissing ) {
						    LOG.warn("importGreatToolsData(): ignoring flag for Great Tools, because no ID supplied! Code: >"+sCode+"<");
						}
						if ( bGreatToolsIDInvalid ) {
						    LOG.warn("importGreatToolsData(): ignoring invalid Great Tools >"+sGreatToolID+"<! Code: >"+sCode+"<");
						}
						
						if ( oProductModel instanceof WeraProductSetModel ) {
						    WeraProductSetModel wps = (WeraProductSetModel) oProductModel;
						    if ( ! StringUtils.trimToEmpty(wps.getVariantenNr()).equals(sRequestVarNr) ) {
							LOG.warn("importGreatToolsData(): ignoring product with matching code >"+sRequestCode+"<, but differing varNR >" + wps.getVariantenNr() + "<. Expected: >"+sRequestVarNr+"<!");
							continue;
						    }
						    wps.setIs_marketing_representative(bRepresentMarketing);
						    if ( bShowGreatTools && ! bGreatToolsIDMissing && ! bGreatToolsIDInvalid ) {
							wps.setUse_for_greatttools(bShowGreatTools);
							wps.setGreat_tools_basename(sGreatToolID);
						    }
						    this.modelService.save(wps);
						    LOG.info("importGreatToolsData(): saving SET "+sCode+" ("+wps.getCode() + ")");
						    bFoundProduct = true;
						}
						
						if ( oProductModel instanceof WeraVarianteModel ) {
						    WeraVarianteModel wvm = (WeraVarianteModel) oProductModel;
						    if ( ! StringUtils.trimToEmpty(wvm.getVariantenNr()).equals(sRequestVarNr) ) {
							LOG.warn("importGreatToolsData(): ignoring product with matching code >"+sRequestCode+"<, but differing varNR >" + wvm.getVariantenNr() + "<. Expected: >"+sRequestVarNr+"<!");
							continue;
						    }
						    wvm.setIs_marketing_representative(bRepresentMarketing);
						    this.modelService.save(wvm);
						    if ( bShowGreatTools & ! bGreatToolsIDMissing && ! bGreatToolsIDInvalid ) {
							ProductModel basepm = wvm.getBaseProduct();
							if ( basepm != null ) {
							    if ( basepm instanceof WeraProductModel ) {
								WeraProductModel basewpm = (WeraProductModel) basepm;
								basewpm.setUse_for_greatttools(bShowGreatTools);
								basewpm.setGreat_tools_basename(sGreatToolID);
								this.modelService.save(basewpm);
								LOG.info("importGreatToolsData(): saving VARIANT >"+sCode+"< and BASE >"+basewpm.getCode()+"< ");
							    } else {
								LOG.warn("importGreatToolsData(): BASE of VARIANT "+sCode+ " is not WeraProduct!");
							    }
							} else {
							    LOG.warn("importGreatToolsData(): no base for VARIANT "+sCode+ "!");
							}
						    } else {
							LOG.info("importGreatToolsData(): saving VARIANT "+sCode);
						    }
						    bFoundProduct = true;
						}
						
						if ( !bFoundProduct ) {
						    LOG.warn("importGreatToolsData(): item type invalid for "+sCode+"<!");
						}
					    }
					} else {
					    LOG.warn("importGreatToolsData(): could not resolve >"+sCode+"<!");
					}
				    } else {
					LOG.warn("importGreatToolsData(): line had inconsistent data:");
					LOG.warn("importGreatToolsData(): => >"+line+"<" );
				    }
				    
				} else {
				    LOG.warn("importGreatToolsData(): code >"+sCode+"< does not start with 05.");
				}
				
			    } else {
				LOG.warn("importGreatToolsData(): data line had "+aLineInput.length+" components. Expected: 4");
			    }
		    }
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    return result.toString();
	}
	
	public String importASINFromFile( final String sInputFile ) {
		StringBuffer result = new StringBuffer();
		Set<String> setOfValidCountryCodes = new HashSet<String>(Arrays.asList("de","fr","es","uk","us"));
		final Logger LOG = Logger.getLogger(DataCorrector.class.getName());
		String line = null;
		int iNumValidCodesProcessed = 0;
		int iNumMatchingProducts = 0;
		try {
			// Expected format: CodeNo (TAB) Land (TAB) ASIN
			final BufferedReader reader = new BufferedReader(new FileReader(sInputFile));
			LOG.info("importASINFromFile(): reading data from >"+sInputFile+"<");
			while ((line = reader.readLine()) != null) {
				line = StringUtils.trimToNull(line);
				if (line == null) {
					continue;
				}
				
				final String[] aLineInput = line.split("\t");
				if ( aLineInput.length == 3 ) {
				    String sCode = aLineInput[0].trim();
				    String sLand = aLineInput[1].trim();
				    String sAsin = aLineInput[2].trim();
				    
				    if ( sCode.startsWith("05") ) {
					if ( sCode.endsWith("001") || sCode.endsWith("002") ) {
					    iNumValidCodesProcessed++;
					    
					    if ( setOfValidCountryCodes.contains(sLand) ) {
						if ( sAsin.length() == 10 ) {
						    String sSixDigitCode = sCode.substring(2, 8);
						    Collection<ProductModel> colMatchingProducts = findAllMatchingProducts(sSixDigitCode);
						    if ( colMatchingProducts.size() > 1 ) {
							LOG.info("importASINFromFile(): NOTICE: "+colMatchingProducts.size() + " products matching code >"+sSixDigitCode+"<");
						    }
						    if ( colMatchingProducts.size() == 0 ) {
							LOG.info("importASINFromFile(): NOTICE: No products found matching code >"+sSixDigitCode+"<");
							result.append("importASINFromFile(): NOTICE: No products found matching code >"+sSixDigitCode+"<<br>");
						    }
						    
						    for ( final ProductModel oProduct : colMatchingProducts) {
							boolean bIsVariant= (oProduct instanceof WeraVarianteModel);
							
							if ( bIsVariant ) {
							    WeraVarianteModel wvm = (WeraVarianteModel) oProduct;
							    if ( sLand.equals("de")) {
								wvm.setAsin_de(sAsin);
							    }
							    if ( sLand.equals("es")) {
								wvm.setAsin_es(sAsin);
							    }
							    if ( sLand.equals("fr")) {
								wvm.setAsin_fr(sAsin);
							    }
							    if ( sLand.equals("uk")) {
								wvm.setAsin_uk(sAsin);
							    }
							    if ( sLand.equals("us")) {
								wvm.setAsin_us(sAsin);
							    }
							    this.modelService.save(wvm);
							    LOG.info("importASINFromFile(): Saving variant "+wvm.getCode());
							    iNumMatchingProducts++;
							} else {
							    WeraProductSetModel wps = (WeraProductSetModel) oProduct;
							    if ( sLand.equals("de")) {
								wps.setAsin_de(sAsin);
							    }
							    if ( sLand.equals("es")) {
								wps.setAsin_es(sAsin);
							    }
							    if ( sLand.equals("fr")) {
								wps.setAsin_fr(sAsin);
							    }
							    if ( sLand.equals("uk")) {
								wps.setAsin_uk(sAsin);
							    }
							    if ( sLand.equals("us")) {
								wps.setAsin_us(sAsin);
							    }
							    this.modelService.save(wps);
							    LOG.info("importASINFromFile(): Saving set "+wps.getArtnr());
							    iNumMatchingProducts++;
							}
							
						    }
						} else {
						    LOG.info("importASINFromFile(): sAsin >"+sAsin+"< invalid, because it has "+sAsin.length()+ " digits instead of 10!");
						}
						    
						
					    } else {
						LOG.info("importASINFromFile(): Land >"+sLand+"< invalid!");
					    }
					    
					    
					} else {
					    LOG.info("importASINFromFile(): Code >"+sCode+"< invalid, does not end with 001/002!");
					}
				    } else {
					LOG.info("genASIN_ImpEx(): Code >"+sCode+"< invalid, does not start with 05!");
				    }
				}
				
			}
		} catch (Exception e) {
		    e.printStackTrace();
		}
		result.append("Processed "+iNumValidCodesProcessed+" valid code no entries.<br>");
		result.append("Found "+iNumMatchingProducts+" matching products.<br>");
		return result.toString();
	}
	
	
	public String _corrData_20170217_helper(final String sOld, final String sLanguage, final String sType, final String sCode)
	{
		final Logger LOGCORR = Logger.getLogger(DataCorrector.class.getName());

		final String[] searchAndReplacePatterns =
		{
                    /*
				"fr|<b>Utilisation :</b> vis cruciformes Phillips-Recess|<b>Utilisation :</b> pour vis Phillips-Recess",
				"fr|particulièrement indiquée pour l'emploi avec des visseuses à impact/percussion usuelles|particulièrement indiquée pour l'emploi avec des visseuses à chocs usuelles",
				"fr|revêtement diamanté rugueux permettant, grâce à une résistance frictionnelle accrue, de réduire le risque de dérapage hors des vis.|le revêtement diamant réduit considérablement le risque de ripage.",
				"fr|À marquage couleur des pointes (vert = TORX®) et taille gravée pour trouver facilement l’embout souhaité.|Banderole de couleur verte (= profil TORX®) avec marquage de la taille pour trouver facilement le bon outil.",
				"fr|À marquage couleur des pointes (rouge = Phillips) et taille gravée pour trouver facilement l’embout souhaité.|Banderole de couleur rouge (= profil Phillips) avec marquage de la taille pour trouver facilement le bon outil.",
				"fr|À marquage couleur des pointes (noir = Pozidriv) et taille gravée pour trouver facilement l’embout souhaité.|Banderole de couleur noir (= profil Pozidriv) avec marquage de la taille pour trouver facilement le bon outil.",
				"fr|À marquage couleur des pointes (jaune = fente) et taille gravée pour trouver facilement l’embout souhaité.|Banderole de couleur jaune (= profil fente) avec marquage de la taille pour trouver facilement le bon outil.",
				"fr|À marquage couleur des pointes (bleu = six pans creux) et taille gravée pour trouver facilement l’embout souhaité.|Banderole de couleur bleu (= profil six pans creux) avec marquage de la taille pour trouver facilement le bon outil.", 
                    */
                    /*"fr|″|\"" */
                    /*
                    "*|Take it Easy|Take it easy",
                    "*|Take-it-Easy|Take it easy",
                    "*|take it easy|Take it easy",
                    "*|\"Take it easy\"|Take it easy",
                    "*|„Take it easy“|Take it easy",
                    "*|« Take it easy »|Take it easy",
                    "*|«Take it easy»|Take it easy",
                    "*|«Take it easy »|Take it easy",
                    "*|\"Take it easy“|Take it easy",
                    "nl|\"Take it easy tool finder\"|Take it easy tool finder",
                    */
                    "*|« Take it easy »|Take it easy", /* non breaking spaces */
                    "*|“Take it easy\"|Take it easy",
                    "*|“Take it easy”|Take it easy",
                    "*|„Take it easy”|Take it easy",
                    "*|”Take it easy”|Take it easy",
                    "nl|\"Take it easy”-toolfinder|Take it easy toolfinder",
                    "nl|\"Take it easy”-toolfinder|Take it easy toolfinder",
                    "nl|\"Take it easy toolfinder\"|Take it easy toolfinder",
                    
          
                };

		String strNew = sOld;

		if (sOld != null)
		{
			for (int pCnt = 0; pCnt < searchAndReplacePatterns.length; pCnt++)
			{
				final String sPattern = searchAndReplacePatterns[pCnt];
				final String[] aPair = sPattern.split("\\|");
				if (aPair.length == 2 || aPair.length == 3)
				{
					final String sPatternLanguage = aPair[0];
					final String sSearch = aPair[1];
					String sReplace = "";
					if (aPair.length == 3)
					{
						sReplace = aPair[2];
					}

					// ignore case in search pattern
					if (sPatternLanguage.equals(sLanguage) || sPatternLanguage.equals("*"))
					{
						strNew = strNew.replace(sSearch, sReplace);
					}

				}
				else
				{
					LOGCORR.warn("DataCorrector._corrData_20170217_helper: Invalid S+R pattern >" + sPattern + "<");
				}
			}
			if (!sOld.equals(strNew))
			{
				LOGCORR.info("=====================");
				LOGCORR.info("OLD value (" + sType + " / " + sCode + ") = " + sOld);
				LOGCORR.info("NEW value (" + sType + " / " + sCode + ") = " + strNew);
			}

		}

		return strNew;
	}



	// --- Datenkorrektur FR Texte (17.02.2017)
	public void corrData_20170217(final String strTargetLanguage, final String strOrigLanguage)
	{
		final Logger LOGCORR = Logger.getLogger(DataCorrector.class.getName());

		// --- Output
		LOGCORR.info("DataCorrector.corrData_20170217 (Start) => Sprache: " + strTargetLanguage);

		// --- Initialize
		WeraProduct weraproduct = null;
                WeraVariante weravariante = null;
		String strData = "";
		String strNewData = "";

		Collection products = WeraProduct.getAllInstances();
		LOGCORR.info("DataCorrector.corrData_20170217 = Anzahl Produkte: " + products.size());

		int iCnt = 0;
		// --- Schleife über alle Daten (Produkt)

		SetLanguage(strTargetLanguage);
		for (final Iterator it1 = products.iterator(); it1.hasNext();)
		{
			// --- Hole Produkt
			weraproduct = (WeraProduct) it1.next();
                        
                        strData = (String) getAttribute(weraproduct, "description1");
                        
                        strNewData = _corrData_20170217_helper(strData, strTargetLanguage, "product", weraproduct.getCode());
                        if (strData != null && !strData.equals(strNewData))
                        {
                                setAttribute(weraproduct, "description1", strNewData);
                                LOGCORR.info("DC::corrData_20170217(): setting >" + strTargetLanguage + " | " + weraproduct.getCode()
                                                + "< description1 to " + strNewData);
                                iCnt++;
                        }
		}
                products.clear();
                products = null;

                final String sVariantQuery = "SELECT {k:" + WeraVarianteModel.PK + "} FROM { " + WeraVarianteModel._TYPECODE + " AS k}";
                final SearchResult<WeraVarianteModel> variantResults = flexibleSearchService.search(sVariantQuery);
                List<WeraVarianteModel> listVariants = variantResults.getResult();
                for (final WeraVarianteModel oVariant : listVariants) {
                    strData = oVariant.getShop_description();
                    strNewData = _corrData_20170217_helper(strData, strTargetLanguage, "textbaustein", oVariant.getCode() );
                    if (strData != null && !strData.equals(strNewData))
                    {
                            oVariant.setShop_description(strNewData);
                            this.modelService.save(oVariant);
                            LOGCORR.info("DC::corrData_20170217(): setting >" + strTargetLanguage + " | " + oVariant.getCode()
                                            + "< shop_description to " + strNewData);
                            iCnt++;
                    }
                }
                listVariants = null;
                
                final String sTextbausteinQuery = "SELECT {k:" + TextbausteinModel.PK + "} FROM { " + TextbausteinModel._TYPECODE + " AS k}";
                final SearchResult<TextbausteinModel> textbausteinSearchResults = flexibleSearchService.search(sTextbausteinQuery);
                List<TextbausteinModel> listTextbausteine = textbausteinSearchResults.getResult();
                for (final TextbausteinModel oTextbaustein : listTextbausteine) {
                    strData = oTextbaustein.getText();
                    strNewData = _corrData_20170217_helper(strData, strTargetLanguage, "textbaustein", oTextbaustein.getCode() );
                    if (strData != null && !strData.equals(strNewData))
                    {
                            oTextbaustein.setText(strNewData);
                            this.modelService.save(oTextbaustein);
                            LOGCORR.info("DC::corrData_20170217(): setting >" + strTargetLanguage + " | " + oTextbaustein.getCode()
                                            + "< text to " + strNewData);
                            iCnt++;
                    }
                }
                listTextbausteine = null;
                
		LOGCORR.info("DataCorrector() replaced " + iCnt + " occurrences.");

		// --- Setze Sprache zurück
		SetLanguage(strOrigLanguage);
	}

	public boolean _updateBulletPointsSet(final TextbausteinModel tbsm, final WeraProductSetModel wpsm, final int iCnt)
	{
		final Collection colLang = C2LManager.getInstance().getAllLanguages();

		for (final Iterator it0 = colLang.iterator(); it0.hasNext();)
		{
			// --- Sprache aktivieren
			final Language lang1 = (Language) it0.next();
			if (!lang1.getIsoCode().equals("de"))
			{
				SetLanguage(lang1.getIsoCode());
				String sNewText = null;
				if (iCnt == 1)
				{
					sNewText = wpsm.getShop_bullet_point1();
				}
				if (iCnt == 2)
				{
					sNewText = wpsm.getShop_bullet_point2();
				}
				if (iCnt == 3)
				{
					sNewText = wpsm.getShop_bullet_point3();
				}
				if (iCnt == 4)
				{
					sNewText = wpsm.getShop_bullet_point4();
				}
				if (iCnt == 5)
				{
					sNewText = wpsm.getShop_bullet_point5();
				}
				if (sNewText != null)
				{
					tbsm.setText(sNewText);
				}
			}
		}
		modelService.save(tbsm);
		return true;
	}

	public boolean _updateBulletPointsVariant(final TextbausteinModel tbsm, final WeraVarianteModel wvm, final int iCnt)
	{
		final Collection colLang = C2LManager.getInstance().getAllLanguages();

		for (final Iterator it0 = colLang.iterator(); it0.hasNext();)
		{
			// --- Sprache aktivieren
			final Language lang1 = (Language) it0.next();
			if (!lang1.getIsoCode().equals("de"))
			{
				SetLanguage(lang1.getIsoCode());
				String sNewText = null;
				if (iCnt == 1)
				{
					sNewText = wvm.getShop_bullet_point1();
				}
				if (iCnt == 2)
				{
					sNewText = wvm.getShop_bullet_point2();
				}
				if (iCnt == 3)
				{
					sNewText = wvm.getShop_bullet_point3();
				}
				if (iCnt == 4)
				{
					sNewText = wvm.getShop_bullet_point4();
				}
				if (iCnt == 5)
				{
					sNewText = wvm.getShop_bullet_point5();
				}
				if (sNewText != null)
				{
					tbsm.setText(sNewText);
				}
			}
		}
		modelService.save(tbsm);
		return true;
	}

	public String eliminateDuplicatesInAnwenderbildernAndTipps()
	{
		final Logger LOGCORR = Logger.getLogger(DataCorrector.class.getName());
		final ComposedType WeraProductSetType = TypeManager.getInstance().getComposedType(WeraProductSetinSet.class);
		final Collection siss = WeraProductSetType.getAllInstances();

		boolean bChangedAnwenderimages = false;
		boolean bChangedTipps = false;
		int iCnt = 0;

		LOGCORR.info("eliminateDuplicatesInAnwenderbildernAndTipps(): #SiS products = " + siss.size());
		for (final Iterator it1 = siss.iterator(); it1.hasNext();)
		{
			bChangedAnwenderimages = false;
			bChangedTipps = false;

			iCnt++;
			final WeraProductSetinSet sis = (WeraProductSetinSet) it1.next();
			final WeraProductSetinSetModel sism = modelService.get(sis);

			// handle Anwenderimages
			final Collection<BildreferenzModel> colCustomBildreferenzen = sism.getAnwenderimageref2weraproductsetinset();
			final Collection<BildreferenzModel> colInheritedBildreferenzen = sism.getAnwenderimageref2weraproduct();
			final Collection<BildreferenzModel> colNewBildreferenzen = new ArrayList();
			for (final BildreferenzModel oCustomImage : colCustomBildreferenzen)
			{
				final String sCustomImageCode = oCustomImage.getCode();
				boolean bImagesMatch = false;
				for (final BildreferenzModel oInheritedImage : colInheritedBildreferenzen)
				{
					final String sInheritedImageCode = oInheritedImage.getCode();
					if (sCustomImageCode.equals(sInheritedImageCode))
					{
						LOGCORR.info("eliminateDuplicatesInAnwenderbildernAndTipps(): Anwenderbild " + sInheritedImageCode
								+ " already in inherited list.");
						bImagesMatch = true;
						bChangedAnwenderimages = true;
						break;
					}
				}
				if (!bImagesMatch)
				{
					colNewBildreferenzen.add(oCustomImage);
					LOGCORR.info("eliminateDuplicatesInAnwenderbildernAndTipps(): adding image " + oCustomImage.getCode());
				}
			}
			if (bChangedAnwenderimages)
			{
				sism.setAnwenderimageref2weraproductsetinset(colNewBildreferenzen);
			}


			// handle Tipps
			final Collection<TippModel> colCustomTipps = sism.getSortimenttipprefsetinset();
			final Collection<TippModel> colInheritedTipps = sism.getSortimenttippref();
			final Collection<TippModel> colNewTipps = new ArrayList();
			for (final TippModel oCustomTipp : colCustomTipps)
			{
				final String sCustomTippCode = oCustomTipp.getCode();
				boolean bTippsMatch = false;
				for (final TippModel oInheritedTipp : colInheritedTipps)
				{
					final String oInheritedTippCode = oInheritedTipp.getCode();
					if (sCustomTippCode.equals(oInheritedTippCode))
					{
						LOGCORR.info("eliminateDuplicatesInAnwenderbildernAndTipps(): Tipp " + oInheritedTippCode
								+ " already in inherited list.");
						bTippsMatch = true;
						bChangedTipps = true;
						break;
					}
				}
				if (!bTippsMatch)
				{
					colNewTipps.add(oCustomTipp);
					LOGCORR.info("eliminateDuplicatesInAnwenderbildernAndTipps(): adding tipp " + oCustomTipp.getCode());
				}
			}
			if (bChangedTipps)
			{
				sism.setSortimenttipprefsetinset(colNewTipps);
			}


			if (bChangedTipps || bChangedAnwenderimages)
			{
				LOGCORR.info("eliminateDuplicatesInAnwenderbildernAndTipps(): SiS #" + iCnt
						+ " changed Anwenderimages or Tipps, saving ...");
				this.modelService.save(sism);
			}
		}
		return "Copying SiS list into complete.";
	}

	public String copyTippsAndAnwenderbilder()
	{
		final Logger LOGCORR = Logger.getLogger(DataCorrector.class.getName());
		final ComposedType WeraProductSetType = TypeManager.getInstance().getComposedType(WeraProductSetinSet.class);
		final Collection siss = WeraProductSetType.getAllInstances();

		boolean bChanged = false;
		int iCnt = 0;

		LOGCORR.info("copyTippsAndAnwenderbilder(): #SiS products = " + siss.size());
		for (final Iterator it1 = siss.iterator(); it1.hasNext();)
		{
			bChanged = false;
			iCnt++;
			final WeraProductSetinSet sis = (WeraProductSetinSet) it1.next();
			final WeraProductSetinSetModel sism = modelService.get(sis);

			final Collection<BildreferenzModel> colBildreferenzen = sism.getAnwenderimageref2weraproduct();
			final Collection<TippModel> colTipps = sism.getSortimenttippref();
			if (colBildreferenzen != null && colBildreferenzen.size() > 0)
			{
				final Collection<BildreferenzModel> newColBildreferenzen = new ArrayList();
				newColBildreferenzen.addAll(colBildreferenzen);
				sism.setAnwenderimageref2weraproductsetinset(newColBildreferenzen);
				LOGCORR.info("copyTippsAndAnwenderbilder(): SiS #" + iCnt + " - copying " + colBildreferenzen.size()
						+ " Anwenderbilder to new slot.");
				bChanged = true;
			}
			if (colTipps != null && colTipps.size() > 0)
			{
				final Collection<TippModel> newColTipps = new ArrayList();
				newColTipps.addAll(colTipps);
				sism.setSortimenttipprefsetinset(newColTipps);
				LOGCORR.info("copyTippsAndAnwenderbilder(): SiS #" + iCnt + " - copying " + colTipps.size() + " Tipps to new slot.");
				bChanged = true;
			}
			if (bChanged)
			{
				LOGCORR.info("copyTippsAndAnwenderbilder(): SiS #" + iCnt + " changed, saving ...");
				this.modelService.save(sism);
			}
		}
		return "Copying SiS list into complete.";
	}


	public String updateBulletPoints()
	{
		final Collection sets = WeraProductSet.getAllInstances();
		final Collection variants = WeraVariante.getAllInstances();

		final Set<String> setTbsmCodes = new HashSet();

		for (final Iterator it1 = sets.iterator(); it1.hasNext();)
		{

			// --- Hole Produkt
			final WeraProductSet wps = (WeraProductSet) it1.next();
			final WeraProductSetModel wpsm = modelService.get(wps);
			final Collection<TextbausteinModel> colTbsm = wpsm.getWeraproductset2bulletpoints();

			SetLanguage("de");
			final String text1 = StringUtils.trimToEmpty(wpsm.getShop_bullet_point1());
			final String text2 = StringUtils.trimToEmpty(wpsm.getShop_bullet_point2());
			final String text3 = StringUtils.trimToEmpty(wpsm.getShop_bullet_point3());
			final String text4 = StringUtils.trimToEmpty(wpsm.getShop_bullet_point4());
			final String text5 = StringUtils.trimToEmpty(wpsm.getShop_bullet_point5());
			int iCnt = 0;

			for (final TextbausteinModel tbsm : colTbsm)
			{
				iCnt++;
				boolean bUpdated = false;
				if (!setTbsmCodes.contains(tbsm.getCode()))
				{
					SetLanguage("de");
					final String listedText = StringUtils.trimToEmpty(tbsm.getText());
					if (iCnt == 1 && listedText.equals(text1))
					{
						bUpdated = _updateBulletPointsSet(tbsm, wpsm, 1);
					}
					if (iCnt == 2 && listedText.equals(text2))
					{
						bUpdated = _updateBulletPointsSet(tbsm, wpsm, 2);
					}
					if (iCnt == 3 && listedText.equals(text3))
					{
						bUpdated = _updateBulletPointsSet(tbsm, wpsm, 3);
					}
					if (iCnt == 4 && listedText.equals(text4))
					{
						bUpdated = _updateBulletPointsSet(tbsm, wpsm, 4);
					}
					if (iCnt == 5 && listedText.equals(text5))
					{
						bUpdated = _updateBulletPointsSet(tbsm, wpsm, 5);
					}
				}
				if (bUpdated)
				{
					setTbsmCodes.add(tbsm.getCode());
					System.out.println("updateBulletPoints(): Updated bullet point >" + tbsm.getCode() + "< from product set >"
							+ wpsm.getCode() + "<.");
				}
			}
		}


		for (final Iterator it2 = variants.iterator(); it2.hasNext();)
		{


			// --- Hole Produkt
			final WeraVariante wv = (WeraVariante) it2.next();
			final WeraVarianteModel wvm = modelService.get(wv);

			final Collection<TextbausteinModel> colTbsm = wvm.getWeravariante2bulletpoints();

			SetLanguage("de");
			final String text1 = StringUtils.trimToEmpty(wvm.getShop_bullet_point1());
			final String text2 = StringUtils.trimToEmpty(wvm.getShop_bullet_point2());
			final String text3 = StringUtils.trimToEmpty(wvm.getShop_bullet_point3());
			final String text4 = StringUtils.trimToEmpty(wvm.getShop_bullet_point4());
			final String text5 = StringUtils.trimToEmpty(wvm.getShop_bullet_point5());
			int iCnt = 0;

			for (final TextbausteinModel tbsm : colTbsm)
			{
				iCnt++;
				boolean bUpdated = false;
				if (!setTbsmCodes.contains(tbsm.getCode()))
				{
					SetLanguage("de");
					final String listedText = StringUtils.trimToEmpty(tbsm.getText());
					if (iCnt == 1 && listedText.equals(text1))
					{
						bUpdated = _updateBulletPointsVariant(tbsm, wvm, 1);
					}
					if (iCnt == 2 && listedText.equals(text2))
					{
						bUpdated = _updateBulletPointsVariant(tbsm, wvm, 2);
					}
					if (iCnt == 3 && listedText.equals(text3))
					{
						bUpdated = _updateBulletPointsVariant(tbsm, wvm, 3);
					}
					if (iCnt == 4 && listedText.equals(text4))
					{
						bUpdated = _updateBulletPointsVariant(tbsm, wvm, 4);
					}
					if (iCnt == 5 && listedText.equals(text5))
					{
						bUpdated = _updateBulletPointsVariant(tbsm, wvm, 5);
					}
				}
				if (bUpdated)
				{
					setTbsmCodes.add(tbsm.getCode());
					System.out.println("updateBulletPoints(): Updated bullet point >" + tbsm.getCode() + "< from variant >"
							+ wvm.getCode() + "<.");
				}
			}
		}
		return "";
	}


	// --- Übernahme englischer, franzöischer und spanischer Text nach US (Produkttitel, -beschreibungen)
	public Collection<Map<String, String>> corrData_copyTextsFromEN_FR_ES_to_US()
	{
		// --- Initialize
		WeraProduct weraproduct = null;
		WeraProductSetinSet wpsis = null;
		String sInherit = null;
		final Collection<Map<String, String>> oReturnMap = new ArrayList();

		String sReplace = "";
		Matcher m = null;

		final String sDecimalNumberRegEx = "(\\d{1,4}),(\\d{1,3})";
		final Pattern oRegExPattern = Pattern.compile(sDecimalNumberRegEx);

		// --- Hole alle aktiven Produkte, sortiert
		final Collection products = WeraProduct.getAllInstances();
		//final Collection productsets = WeraProductSet.getAllInstances();
		//final Collection productsetinsets = WeraProductSetinSet.getAllInstances();

		final Collection colAllProducts = new ArrayList();
		colAllProducts.addAll(products);
		//colAllProducts.addAll(productsets);
		//colAllProducts.addAll(productsetinsets);

		// --- Schleife über alle Daten (Produkt)
		for (final Iterator it1 = colAllProducts.iterator(); it1.hasNext();)
		{

			String sType = "product";
			sInherit = "";

			// --- Hole Produkt
			weraproduct = (WeraProduct) it1.next();

			if (weraproduct instanceof WeraProductSetinSet)
			{
				sType = "SiS";
				wpsis = (WeraProductSetinSet) weraproduct;
				final WeraProduct oSiSFather = wpsis.getWeraproducts_relation();
				if (oSiSFather != null)
				{
					sInherit = oSiSFather.getCode();
				}

			}
			else
			{
				if (weraproduct instanceof WeraProductSet)
				{
					sType = "set";
				}
			}

			// --- Setze Sprache en
			SetLanguage("en");
			final String pName = (String) weraproduct.getLocalizedProperty("name");
			final String pDesc = (String) weraproduct.getLocalizedProperty("description1");

			SetLanguage("us-en");
			weraproduct.setLocalizedProperty("name", pName);
			weraproduct.setLocalizedProperty("description1", pDesc);

			// --- Setze Sprache fr
			SetLanguage("fr");
			final String pNameFR = StringUtils.trimToEmpty((String) weraproduct.getLocalizedProperty("name"));
			final String pDescFR = StringUtils.trimToEmpty((String) weraproduct.getLocalizedProperty("description1"));

			SetLanguage("us-fr");
			m = oRegExPattern.matcher(pNameFR);
			sReplace = m.replaceAll("$1.$2");
			if (!sReplace.equals(pNameFR))
			{
				final Map<String, String> oMap = new HashMap();
				oMap.put("code", weraproduct.getCode());
				oMap.put("type", sType);
				oMap.put("lang", "FR");
				oMap.put("field", "name");
				oMap.put("inherit", sInherit);
				oMap.put("old", pNameFR);
				oMap.put("new", sReplace);
				oReturnMap.add(oMap);
			}
			m = oRegExPattern.matcher(pDescFR);
			sReplace = m.replaceAll("$1.$2");
			if (!sReplace.equals(pDescFR))
			{
				final Map<String, String> oMap = new HashMap();
				oMap.put("code", weraproduct.getCode());
				oMap.put("type", sType);
				oMap.put("lang", "FR");
				oMap.put("field", "desc");
				oMap.put("inherit", sInherit);
				oMap.put("old", pDescFR);
				oMap.put("new", sReplace);
				oReturnMap.add(oMap);
			}
			weraproduct.setLocalizedProperty("name", pNameFR);
			weraproduct.setLocalizedProperty("description1", pDescFR);

			// --- Setze Sprache es
			SetLanguage("es");
			final String pNameES = StringUtils.trimToEmpty((String) weraproduct.getLocalizedProperty("name"));
			String pDescES = StringUtils.trimToEmpty((String) weraproduct.getLocalizedProperty("description1"));

			// replace faulty ISO norm remnant in ES
			pDescES = pDescES.replace("(ISO 1173), ", "");
			weraproduct.setLocalizedProperty("description1", pDescES);

			SetLanguage("us-es");
			m = oRegExPattern.matcher(pNameES);
			sReplace = m.replaceAll("$1.$2");
			if (!sReplace.equals(pNameES))
			{
				final Map<String, String> oMap = new HashMap();
				oMap.put("code", weraproduct.getCode());
				oMap.put("type", sType);
				oMap.put("lang", "ES");
				oMap.put("field", "name");
				oMap.put("inherit", sInherit);
				oMap.put("old", pNameES);
				oMap.put("new", sReplace);
				oReturnMap.add(oMap);
			}
			m = oRegExPattern.matcher(pDescES);
			sReplace = m.replaceAll("$1.$2");
			if (!sReplace.equals(pDescES))
			{
				final Map<String, String> oMap = new HashMap();
				oMap.put("code", weraproduct.getCode());
				oMap.put("type", sType);
				oMap.put("lang", "ES");
				oMap.put("field", "desc");
				oMap.put("inherit", sInherit);
				oMap.put("old", pDescES);
				oMap.put("new", sReplace);
				oReturnMap.add(oMap);
			}
			weraproduct.setLocalizedProperty("name", pNameES);
			weraproduct.setLocalizedProperty("description1", pDescES);

			// save the changed model
			// modelService.save(weraproduct);
		}
		return oReturnMap;
	}

	// --- Datenkorrektur Texte (01.12.2015)
	public final Set<String> findMissingVariantennummern(final String sCatalogId, final String sCatalogVersionId,
			final String[] aLanguages)
	{
		final Set<String> hMsg = new HashSet();
		final Logger LOGCORR = Logger.getLogger(DataCorrector.class.getName());

		for (final String sLanguage : aLanguages)
		{
			LOGCORR.info("Setting language to: " + sLanguage);
			SetLanguage(sLanguage);

			String sVariantenNummer = null;

			final CatalogVersion catalogVersion = getCatalogVersion(sCatalogId, sCatalogVersionId);
			if (catalogVersion != null)
			{
				final ArrayList<Category> colCategories = new ArrayList();
				colCategories.addAll(catalogVersion.getRootCategories());

				while (!colCategories.isEmpty())
				{
					final Category oCategory = colCategories.remove(0);
					final Collection<Category> colSubcategories = oCategory.getSubcategories();
					if (colSubcategories.size() > 0)
					{
						colCategories.addAll(colSubcategories);
					}
					else
					{
						// now check products
						final List<Product> listOfProducts = oCategory.getProducts();
						for (final Product oProduct : listOfProducts)
						{
							if (oProduct instanceof WeraProduct)
							{
								final WeraProduct oWeraProduct = (WeraProduct) oProduct;
								if (oWeraProduct instanceof WeraProductSet)
								{
									if (oWeraProduct instanceof WeraProductSetinSet)
									{
										// SiS
										final WeraProductSetinSet wpsis = (WeraProductSetinSet) oWeraProduct;
										final Collection<WeraProductSetVariants> colSiSSets = wpsis.getWeraproductsetvariants_qual();
										for (final WeraProductSetVariants oSiSSetWrap : colSiSSets)
										{
											final WeraProductSet oWps = oSiSSetWrap.getWeraproductsets();
											sVariantenNummer = oWps.getVariantenNr();
											if (sVariantenNummer == null || sVariantenNummer.length() != 3)
											{

												hMsg.add("SiS set " + oWps.getArtnr() + " (code: " + oWps.getCode() + ", part of SiS "
														+ wpsis.getCode() + ")<br>");
												LOGCORR.info("Language " + sLanguage + ": set " + oWps.getArtnr() + " (code: "
														+ oWps.getCode() + ")");
											}

										}

									}
									else
									{
										// standard sets
										final WeraProductSet wps = (WeraProductSet) oWeraProduct;
										sVariantenNummer = wps.getVariantenNr();
										if (sVariantenNummer == null || sVariantenNummer.length() != 3)
										{
											hMsg.add("set     " + wps.getArtnr() + " (code: " + wps.getCode() + ")<br>");
											LOGCORR.info("Language " + sLanguage + ": set " + wps.getArtnr() + " (code: " + wps.getCode()
													+ ")");
										}
									}
								}
								else
								{
									// standard product
									try
									{
										final Collection<WeraVariante> colVariants = oWeraProduct.getVarianten();
										for (final WeraVariante oVariant : colVariants)
										{
											sVariantenNummer = oVariant.getVariantenNr();
											if (sVariantenNummer == null || sVariantenNummer.length() != 3)
											{
												hMsg.add("variant " + oVariant.getCode() + " (base: " + oWeraProduct.getCode() + ")<br>");
												LOGCORR.info("Language " + sLanguage + ": variant " + oVariant.getCode() + " (base: "
														+ oWeraProduct.getCode() + ")");
											}

										}
									}
									catch (final JaloInvalidParameterException e)
									{
										// YTODO Auto-generated catch block
										e.printStackTrace();
									}

								}
							}
						}

					}

				}

			}
		}
		SetLanguage("de");
		// --- Output
		LOGCORR.info("DataCorrector.findMissingVariantennummern()");
		return hMsg;
	}

	public String _corrData_20160715_helper(final String sOld, final String sLanguage, final String sType, final String sCode)
	{
		final Logger LOGCORR = Logger.getLogger(DataCorrector.class.getName());

		final String[] searchAndReplacePatterns =
		{ "ru|конец Black Point|наконечник Black Point", "ru|Конец Black Point|наконечник Black Point",
				"ru|Конец Lasertip|наконечник Lasertip", "ru|конец Lasertip|наконечник Lasertip" };

		String strNew = sOld;

		if (sOld != null)
		{
			for (int pCnt = 0; pCnt < searchAndReplacePatterns.length; pCnt++)
			{
				final String sPattern = searchAndReplacePatterns[pCnt];
				final String[] aPair = sPattern.split("\\|");
				if (aPair.length == 2 || aPair.length == 3)
				{
					final String sPatternLanguage = aPair[0];
					final String sSearch = aPair[1];
					String sReplace = "";
					if (aPair.length == 3)
					{
						sReplace = aPair[2];
					}

					// ignore case in search pattern
					if (sPatternLanguage.equals(sLanguage) || sPatternLanguage.equals("*"))
					{
						strNew = strNew.replace(sSearch, sReplace);
					}

				}
				else
				{
					LOGCORR.warn("DataCorrector._corrData_20160715_helper: Invalid S+R pattern >" + sPattern + "<");
				}
			}
			if (!sOld.equals(strNew))
			{
				LOGCORR.info("=====================");
				LOGCORR.info("OLD value (" + sType + " / " + sCode + ") = " + sOld);
				LOGCORR.info("NEW value (" + sType + " / " + sCode + ") = " + strNew);
			}

		}

		return strNew;
	}

	public String _corrData_20151201_helper(final String sOld, final String sLanguage, final String sType, final String sCode)
	{
		final Logger LOGCORR = Logger.getLogger(DataCorrector.class.getName());

		final String[] searchAndReplacePatterns =
		{ "fr| <sup>*)</sup>|", "pl| <sup>*)</sup>|", "nl| <sup>*)</sup>|", "*|<sup>*)</sup>|", "fr| *<sup>)</sup>|",
				"pl| *<sup>)</sup>|", "nl| <sup>*)</sup>|", "*|*<sup>)</sup>|", };

		String strLast = "";
		String strNew = sOld;

		if (sOld != null)
		{
			for (int pCnt = 0; pCnt < searchAndReplacePatterns.length; pCnt++)
			{
				final String sPattern = searchAndReplacePatterns[pCnt];
				final String[] aPair = sPattern.split("\\|");
				if (aPair.length == 2 || aPair.length == 3)
				{
					final String sPatternLanguage = aPair[0];
					final String sSearch = aPair[1];
					String sReplace = "";
					if (aPair.length == 3)
					{
						sReplace = aPair[2];
					}

					// --- Description1
					strLast = strNew;

					// ignore case in search pattern
					if (sPatternLanguage.equals(sLanguage) || sPatternLanguage.equals("*"))
					{
						strNew = strNew.replace(sSearch, sReplace);
						if (1 == 0 && !strLast.equals(strNew))
						{
							LOGCORR.info("=====================");
							LOGCORR.info("OLD value = " + sOld);
							LOGCORR.info("NEW value = " + strNew);
							LOGCORR.info("SR rule = ( " + sSearch + " => " + sReplace + " ) ");
						}
					}

				}
				else
				{
					LOGCORR.warn("DataCorrector._corrData_20151201_helper: Invalid S+R pattern >" + sPattern + "<");
				}
			}
			if (1 == 0 && !sOld.equals(strNew))
			{
				LOGCORR.info("=====================");
				LOGCORR.info("OLD value (" + sType + " / " + sCode + ") = " + sOld);
				LOGCORR.info("NEW value (" + sType + " / " + sCode + ") = " + strNew);
			}

		}

		return strNew;

	}

	public String _corrData_20151030_helper(final String sOld, final String sLanguage, final String sType, final String sCode)
	{
		final Logger LOGCORR = Logger.getLogger(DataCorrector.class.getName());

		final String[] searchAndReplacePatterns =
		{
				// extra adjustment DK
				"dk|ekstrahård, særligt egnet til bløde skrueemner f.eks. træ|ekstrahård",
				"dk|Ekstrahård, særligt egnet til bløde skrueemner f.eks. træ|Ekstrahård", /*
																													 * // extra adjustment EN
																													 * "en|extra-hard, ideal for less demanding screwdriving jobs e.g. in wood|extra-hard"
																													 * ,
																													 * "en|Extra-hard, ideal for less demanding screwdriving jobs e.g. in wood|Extra-hard"
																													 * ,
																													 * 
																													 * // small version extrahart ...
																													 * "de|extrahart, besonders geeignet für den weichen Schraubfall z. B. in Holz|extrahart"
																													 * ,
																													 * "en|extra-hard, ideal for less demanding screwdriving jobs e.g. in Wood|extra-hard"
																													 * ,
																													 * "es|versión extradura, especialmente adecuada para el atornillado en material blando, por ejemplo en Madera|versión extradura"
																													 * ,
																													 * "es|versión extradura, especialmente adecuada para el atornillado en material blando, por ejemplo en madera|versión extradura"
																													 * ,
																													 * "fr|extra-dure, particulièrement indiquée pour les vis devant pénétrer dans des matériaux tendres (bois par ex.)|extra-dure"
																													 * ,
																													 * "it|super duro, per materiali morbidi come legno|super duro"
																													 * ,
																													 * "nl|extra harde bits voor zachte schroefomstandigheden bij b.v. hout|extra harde bits"
																													 * ,
																													 * "dk|ekstrahård, særligt egnet til bløde skrueemner f.eks. Træ|ekstrahård"
																													 * ,
																													 * 
																													 * // capital version Extrahart
																													 * ...
																													 * "de|Extrahart, besonders geeignet für den weichen Schraubfall z. B. in Holz|Extrahart"
																													 * ,
																													 * "en|Extra-hard, ideal for less demanding screwdriving jobs e.g. in Wood|Extra-hard"
																													 * ,
																													 * "es|Versión extradura, especialmente adecuada para el atornillado en material blando, por ejemplo en Madera|Versión extradura"
																													 * ,
																													 * "es|Versión extradura, especialmente adecuada para el atornillado en material blando, por ejemplo en madera|Versión extradura"
																													 * ,
																													 * "fr|Extra-dure, particulièrement indiquée pour les vis devant pénétrer dans des matériaux tendres (bois par ex.)|Extra-dure"
																													 * ,
																													 * "it|Super duro, per materiali morbidi come legno|Super duro"
																													 * ,
																													 * "nl|Extra harde bits voor zachte schroefomstandigheden bij b.v. hout|Extra harde bits"
																													 * ,
																													 * "dk|Ekstrahård, særligt egnet til bløde skrueemner f.eks. Træ|Ekstrahård"
																													 * ,
																													 * 
																													 * // small version zähhart ...
																													 * "de|zähhart, besonders geeignet für den harten Schraubfall z. B. in Blech oder Metall|zähhart, für den universellen Einsatz"
																													 * ,
																													 * "en|tough, ideal for difficult screwdriving jobs e.g. in sheet steel or metal|tough, for universal use"
																													 * ,
																													 * "es|versión extrarresistente, especialmente adecuada para el atornillado en material duro, por ejemplo en chapas o metales|versión extrarresistente, para uso universal"
																													 * ,
																													 * "fr|extra-rigide, particulièrement indiquée pour les vis devant pénétrer dans des matériaux durs (tôle ou métal par ex.)|extra-rigide, usage universel"
																													 * ,
																													 * "it|tenace duro, per materiali duri come latta o metallo|tenace duro, per utilizzo universale"
																													 * ,
																													 * "nl|taai-harde bits voor harde schroefomstandigheden b.v. plaatstaal of metaal|taai-harde bits voor universeel gebruik"
																													 * ,
																													 * "dk|hård og slidstærk, særligt egnet til hårde skrueemner f.eks. blik eller metal|hård og slidstærk, til universel brug"
																													 * ,
																													 * 
																													 * // capital version Zähhart ...
																													 * "de|Zähhart, besonders geeignet für den harten Schraubfall z. B. in Blech oder Metall|Zähhart, für den universellen Einsatz"
																													 * ,
																													 * "en|Tough, ideal for difficult screwdriving jobs e.g. in sheet steel or metal|Tough, for universal use"
																													 * ,
																													 * "es|Versión extrarresistente, especialmente adecuada para el atornillado en material duro, por ejemplo en chapas o metales|Versión extrarresistente, para uso universal"
																													 * ,
																													 * "fr|Extra-rigide, particulièrement indiquée pour les vis devant pénétrer dans des matériaux durs (tôle ou métal par ex.)|Extra-rigide, usage universel"
																													 * ,
																													 * "it|Tenace duro, per materiali duri come latta o metallo|Tenace duro, per utilizzo universale"
																													 * ,
																													 * "nl|Taai-harde bits voor harde schroefomstandigheden b.v. plaatstaal of metaal|Taai-harde bits voor universeel gebruik"
																													 * ,
																													 * "dk|Hård og slidstærk, særligt egnet til hårde skrueemner f.eks. blik eller metal|Hård og slidstærk, til universel brug"
																													 */};

		String strLast = "";
		String strNew = sOld;

		if (sOld != null)
		{
			for (int pCnt = 0; pCnt < searchAndReplacePatterns.length; pCnt++)
			{
				final String sPattern = searchAndReplacePatterns[pCnt];
				final String[] aPair = sPattern.split("\\|");
				if (aPair.length == 2 || aPair.length == 3)
				{
					final String sPatternLanguage = aPair[0];
					final String sSearch = aPair[1];
					String sReplace = "";
					if (aPair.length == 3)
					{
						sReplace = aPair[2];
					}

					// --- Description1
					strLast = strNew;

					// ignore case in search pattern
					if (sPatternLanguage.equals(sLanguage) || sPatternLanguage.equals("*"))
					{
						strNew = strNew.replace(sSearch, sReplace);
						if (1 == 0 && !strLast.equals(strNew))
						{
							LOGCORR.info("=====================");
							LOGCORR.info("OLD value = " + sOld);
							LOGCORR.info("NEW value = " + strNew);
							LOGCORR.info("SR rule = ( " + sSearch + " => " + sReplace + " ) ");
						}
					}

				}
				else
				{
					LOGCORR.warn("_corrData_20151030_helper: Invalid S+R pattern >" + sPattern + "<");
				}
			}
			if (1 == 0 && !sOld.equals(strNew))
			{
				LOGCORR.info("=====================");
				LOGCORR.info("OLD value (" + sType + " / " + sCode + ") = " + sOld);
				LOGCORR.info("NEW value (" + sType + " / " + sCode + ") = " + strNew);
			}

		}

		return strNew;

	}

	public String _corrData_20151014_helper(final String sOld, final String sLanguage, final String sType, final String sCode)
	{
		final Logger LOGCORR = Logger.getLogger(DataCorrector.class.getName());

		final String[] searchAndReplacePatterns =
		{ "de|DIN 3126-F 6,3, ISO 1173|DIN ISO 1173-F 6,3", "cn|DIN 3126-F 6,3, ISO 1173|DIN ISO 1173-F 6,3",
				"cs|DIN 3126-F 6,3, ISO 1173|DIN ISO 1173-F 6,3", "dk|DIN 3126-F 6,3, ISO 1173|DIN ISO 1173-F 6,3",
				"en|DIN 3126-F 6,3, ISO 1173|DIN ISO 1173-F 6,3", "es|DIN 3126-F 6,3, ISO 1173|DIN ISO 1173-F 6,3",
				"fr|DIN 3126-F 6,3, ISO 1173|DIN ISO 1173-F 6,3", "it|DIN 3126-F 6,3, ISO 1173|DIN ISO 1173-F 6,3",
				"nl|DIN 3126-F 6,3, ISO 1173|DIN ISO 1173-F 6,3", "pl|DIN 3126-F 6,3, ISO 1173|DIN ISO 1173-F 6,3",
				"ru|DIN 3126-F 6,3, ISO 1173|DIN ISO 1173-F 6,3", "us-en|DIN 3126-F 6.3, ISO 1173|DIN ISO 1173-F 6.3",
				"us-es|DIN 3126-F 6.3, ISO 1173|DIN ISO 1173-F 6.3", "us-fr|DIN 3126-F 6.3, ISO 1173|DIN ISO 1173-F 6.3",
				"*|DIN 3126|DIN ISO 1173", "*|(ISO 1173) |", "*|, ISO 1173|", "*|EN ISO 6789|DIN EN ISO 6789" };

		String strLast = "";
		String strNew = sOld;

		if (sOld != null)
		{
			for (int pCnt = 0; pCnt < searchAndReplacePatterns.length; pCnt++)
			{
				final String sPattern = searchAndReplacePatterns[pCnt];
				final String[] aPair = sPattern.split("\\|");
				if (aPair.length == 2 || aPair.length == 3)
				{
					final String sPatternLanguage = aPair[0];
					final String sSearch = aPair[1];
					String sReplace = "";
					if (aPair.length == 3)
					{
						sReplace = aPair[2];
					}

					// --- Description1
					strLast = strNew;

					// ignore case in search pattern
					if (sPatternLanguage.equals(sLanguage) || sPatternLanguage.equals("*"))
					{
						strNew = strNew.replace(sSearch, sReplace);
						if (1 == 0 && !strLast.equals(strNew))
						{
							LOGCORR.info("=====================");
							LOGCORR.info("OLD value = " + sOld);
							LOGCORR.info("NEW value = " + strNew);
							LOGCORR.info("SR rule = ( " + sSearch + " => " + sReplace + " ) ");
						}
					}

				}
				else
				{
					LOGCORR.warn("DataCorrector.corrData_20150924: Invalid S+R pattern >" + sPattern + "<");
				}
			}
			if (1 == 0 && !sOld.equals(strNew))
			{
				LOGCORR.info("=====================");
				LOGCORR.info("OLD value (" + sType + " / " + sCode + ") = " + sOld);
				LOGCORR.info("NEW value (" + sType + " / " + sCode + ") = " + strNew);
			}

		}

		return strNew;

	}

	public String _corrData_20150924_helper(final String sOld, final String sLanguage, final String sType, final String sCode)
	{
		final Logger LOGCORR = Logger.getLogger(DataCorrector.class.getName());

		final String[] searchAndReplacePatterns =
		{ //"de|Fensterkarton|Karton", "ru|в упаковке �? окошком|в картонной упаковке", "fr|sous carton-fenêtre|sous carton",
		  //	"it|in confezione con finestra|in scatola di cartone", "it|in confezione|in scatola di cartone",
		  //"dk|i æske med vindue|i karton", "cs|v krabici s okénkem|v kartonu", "nl|in vensterdoos|in doos",
		  //"us-fr|sous carton-fenêtre|sous carton" 
				"cs|Hex- Plus|Hex-Plus", "nl|9-delig .|9-delig."

		};

		String strLast = "";
		String strNew = sOld;

		if (sOld != null)
		{
			for (int pCnt = 0; pCnt < searchAndReplacePatterns.length; pCnt++)
			{
				final String sPattern = searchAndReplacePatterns[pCnt];
				final String[] aPair = sPattern.split("\\|");
				if (aPair.length == 3)
				{
					final String sPatternLanguage = aPair[0];
					final String sSearch = aPair[1];
					final String sReplace = aPair[2];

					// --- Description1
					strLast = strNew;

					// ignore case in search pattern
					strNew = strNew.replaceAll(sSearch, sReplace);
					if (1 == 0 && sPatternLanguage.equals(sLanguage) && !strLast.equals(strNew))
					{
						LOGCORR.info("=====================");
						LOGCORR.info("OLD value = " + sOld);
						LOGCORR.info("NEW value = " + strNew);
						LOGCORR.info("SR rule = ( " + sSearch + " => " + sReplace + " ) ");
					}

				}
				else
				{
					LOGCORR.warn("DataCorrector.corrData_20150924: Invalid S+R pattern >" + sPattern + "<");
				}
			}
			if (!sOld.equals(strNew))
			{
				LOGCORR.info("=====================");
				LOGCORR.info("OLD value (" + sType + " / " + sCode + ") = " + sOld);
				LOGCORR.info("NEW value (" + sType + " / " + sCode + ") = " + strNew);
			}

		}

		return strNew;

	}

	public String _corrData_20150828_helper(final String sOld, final String sLanguage, final String sType, final String sCode)
	{
		final Logger LOGCORR = Logger.getLogger(DataCorrector.class.getName());

		final String[] searchAndReplacePatterns =
		{ //"de|Fensterkarton|Karton", "ru|в упаковке �? окошком|в картонной упаковке", "fr|sous carton-fenêtre|sous carton",
		  //	"it|in confezione con finestra|in scatola di cartone", "it|in confezione|in scatola di cartone",
		  //"dk|i æske med vindue|i karton", "cs|v krabici s okénkem|v kartonu", "nl|in vensterdoos|in doos",
		  //"us-fr|sous carton-fenêtre|sous carton" 
		"cs|-dílná|dílná"

		};

		String strLast = "";
		String strNew = sOld;

		if (sOld != null)
		{
			for (int pCnt = 0; pCnt < searchAndReplacePatterns.length; pCnt++)
			{
				final String sPattern = searchAndReplacePatterns[pCnt];
				final String[] aPair = sPattern.split("\\|");
				if (aPair.length == 3)
				{
					final String sPatternLanguage = aPair[0];
					final String sSearch = aPair[1];
					final String sReplace = aPair[2];

					// --- Description1
					strLast = strNew;

					// ignore case in search pattern
					strNew = strNew.replaceAll(sSearch, sReplace);
					if (1 == 0 && sPatternLanguage.equals(sLanguage) && !strLast.equals(strNew))
					{
						LOGCORR.info("=====================");
						LOGCORR.info("OLD value = " + sOld);
						LOGCORR.info("NEW value = " + strNew);
						LOGCORR.info("SR rule = ( " + sSearch + " => " + sReplace + " ) ");
					}

				}
				else
				{
					LOGCORR.warn("DataCorrector.corrData_20150421: Invalid S+R pattern >" + sPattern + "<");
				}
			}
			if (!sOld.equals(strNew))
			{
				LOGCORR.info("=====================");
				LOGCORR.info("OLD value (" + sType + " / " + sCode + ") = " + sOld);
				LOGCORR.info("NEW value (" + sType + " / " + sCode + ") = " + strNew);
			}

		}

		return strNew;

	}

	public String _corrData_20160316_helper(final String sOld, final String sType, final String sCode)
	{
		final Logger LOGCORR = Logger.getLogger(DataCorrector.class.getName());

		// this pattern matches "<b>someword: </b> ", where the space-characters can be any weird space including non-breaking (\uC2A0) spaces.
		final String sSearch = "<b>([^ \f\n\r\t\uC2A0\u00A0\u2028\u2029]+):[ \f\n\r\t\uC2A0\u00A0\u2028\u2029]*</b>[ \f\n\r\t\uC2A0\u00A0\u2028\u2029]*";
		final String sReplace = "<b>$1 :</b> ";

		String strNew = sOld;

		if (sOld != null)
		{

			// ignore case in search pattern
			strNew = strNew.replaceAll(sSearch, sReplace);

			if (!sOld.equals(strNew))
			{
				LOGCORR.info("=====================");
				LOGCORR.info("OLD value (" + sType + " / " + sCode + ") = " + sOld);
				LOGCORR.info("NEW value (" + sType + " / " + sCode + ") = " + strNew);
			}

		}

		return strNew;

	}

	// --- Datenkorrektur FR Texte (colon) (16.03.2016)
	public void corrData_20160316()
	{
		final Logger LOGCORR = Logger.getLogger(DataCorrector.class.getName());

		// --- Initialize
		WeraProduct weraproduct = null;
		String strData = "";
		String strNewData = "";

		// --- Setze Sprache
		SetLanguage("de");

		// --- Hole alle aktiven Produkte, sortiert
		final Collection products = WeraProduct.getAllInstances();
		LOGCORR.info("DataCorrector.corrData_20160316 = Anzahl Produkte: " + products.size());

		int iCnt = 0;
		// --- Schleife über alle Daten (Produkt)

		final Collection<String> colLanguages = new ArrayList();

		colLanguages.add("fr");
		colLanguages.add("us-fr");

		for (final Iterator it1 = products.iterator(); it1.hasNext();)
		{
			// --- Hole Produkt
			weraproduct = (WeraProduct) it1.next();

			for (final String sLanguage : colLanguages)
			{
				SetLanguage(sLanguage);
				strData = (String) getAttribute(weraproduct, "description1");
				strNewData = _corrData_20160316_helper(strData, "product", weraproduct.getCode());
				if (strData != null && !strData.equals(strNewData))
				{
					setAttribute(weraproduct, "description1", strNewData);
					LOGCORR.info("DC::corrData_20160316(): setting >" + sLanguage + " | " + weraproduct.getCode()
							+ "< description1 to " + strNewData);
					iCnt++;
				}
			}
		}

		LOGCORR.info("DataCorrector() replaced " + iCnt + " occurrences.");

		// --- Setze Sprache zurück
		SetLanguage("de");
	}

	// --- 14.10.2015: Copy product description1 to description2
	public void copyDesc1ToDesc2()
	{

		// --- Initialize
		WeraProduct weraproduct = null;
		final Category category = null;
		final String strData = "";

		final Collection products = WeraProduct.getAllInstances();

		// --- Schleife über alle Daten (Produkt)
		int ip = 0;
		final int maxp = products.size();
		for (final Iterator it1 = products.iterator(); it1.hasNext();)
		{
			ip++;
			// --- Hole Produkt
			weraproduct = (WeraProduct) it1.next();
			if (weraproduct instanceof WeraProductSet)
			{
				System.out.println("copyDesc1ToDesc2: WPS (" + ip + "/" + maxp + ")" + weraproduct.getCode());
			}
			else
			{
				System.out.println("copyDesc1ToDesc2: WP (" + ip + "/" + maxp + ")" + weraproduct.getCode());
			}

			final Collection colLang = C2LManager.getInstance().getAllLanguages();

			for (final Iterator it0 = colLang.iterator(); it0.hasNext();)
			{
				// --- Sprache aktivieren
				final Language lang1 = (Language) it0.next();
				SetLanguage(lang1.getIsoCode());
				System.out.println("copyDesc1ToDesc2: LANGUAGE = " + lang1.getIsoCode());

				String pDesc1 = (String) weraproduct.getLocalizedProperty("description1");
				if (pDesc1 == null)
				{
					pDesc1 = "";
				}
				weraproduct.setLocalizedProperty("description2", pDesc1);

			}
		}

	}
/*
	public String importXPace(final String code11digits, final String variant_id, final String abtrieb, String unit_width,
			String unit_depth, String griff_hoehe, final String klinge, String unit_height, String unit_weight, String euro_x,
			String euro_y, final String can_hang, final String can_stack, final String can_heap, final String filename_prefix,
			final String verpackungstyp, final String default_z, final String gap_x)
	{

		// "product_id","variant_id","abtrieb","unit_width","unit_depth","griff_hoehe","klinge","unit_height","unit_weight","euro_x","euro_y","can_hang","can_stack",
		// "can_heap","filename_prefix","verpackungstyp","default_z","gap_x"
		// dc.importXPace( "05057430001","1","","134","20","0","0","63","226","0","0","0","1","0","bc_30_bitorsion_1_display","BC30 in Display","10","0" );

		final String code = code11digits.substring(2, 8);
		SetLanguage("de");
		final Logger LOGCORR = Logger.getLogger(DataCorrector.class.getName());
		final String message = "";
		String query = "";

		// use dot as decimal point
		unit_width = StringUtils.trimToEmpty(unit_width).replace(",", ".");
		unit_depth = StringUtils.trimToEmpty(unit_depth).replace(",", ".");
		unit_weight = StringUtils.trimToEmpty(unit_weight).replace(",", ".");
		if (unit_weight.equals(""))
		{
			unit_weight = "0";
		}
		unit_height = StringUtils.trimToEmpty(unit_height).replace(",", ".");
		griff_hoehe = StringUtils.trimToEmpty(griff_hoehe).replace(",", ".");
		if (!NumberUtils.isNumber(griff_hoehe))
		{
			griff_hoehe = "0";
		}

		euro_x = StringUtils.trimToEmpty(euro_x).replace(",", ".");
		euro_y = StringUtils.trimToEmpty(euro_y).replace(",", ".");

		// LOGCORR.info("importXPace(): Processing code '" + code + "'");
		query = "SELECT {v:" + WeraVarianteModel.PK + "} FROM { " + WeraVarianteModel._TYPECODE + " AS v} WHERE {v:Code} = " + code;
		final SearchResult<WeraVarianteModel> variantCodeSearchResult = flexibleSearchService.search(query);
		final List<WeraVarianteModel> productsFromVariantCodeSearchResults = variantCodeSearchResult.getResult();

		if (productsFromVariantCodeSearchResults.isEmpty())
		{
			// LOGCORR.info("importXPace(): Trying VARIANT ... failed!");

			query = "SELECT {" + WeraProductSetModel.PK + "} FROM {" + WeraProductSetModel._TYPECODE + "} WHERE {"
					+ WeraProductSetModel.ARTNR + "} = " + code;
			final SearchResult<WeraProductSetModel> productsetCodeSearchResult = flexibleSearchService.search(query);
			final List<WeraProductSetModel> productsFromProductsetCodeSearchResults = productsetCodeSearchResult.getResult();
			if (productsFromProductsetCodeSearchResults.isEmpty())
			{
				LOGCORR.warn("importXPace(): CANNOT FIND product for code >" + code + "< ");
			}
			else
			{
				final WeraProductSetModel wps = productsFromProductsetCodeSearchResults.iterator().next();
				LOGCORR.info("importXPace(): found set  " + wps.getCode());

				wps.setXpace_variant_id(variant_id);
				wps.setXpace_gewicht_verpackung_produkt(Double.valueOf(unit_weight));
				wps.setXpace_default_z(Double.valueOf(default_z));
				wps.setXpace_gap_x(Double.valueOf(gap_x));
				wps.setXpace_klingenlaenge(Double.valueOf(klinge));

				wps.setXpace_dateiname_prefix(filename_prefix);

				if (!unit_width.equals(""))
				{
					wps.setXpace_breite_verpackung_produkt(Double.valueOf(unit_width));
				}
				if (!unit_depth.equals(""))
				{
					wps.setXpace_tiefe_verpackung_produkt(Double.valueOf(unit_depth));
				}
				if (!unit_height.equals(""))
				{
					wps.setXpace_hoehe_verpackung_produkt(Double.valueOf(unit_height));
				}
				if (!griff_hoehe.equals(""))
				{
					wps.setXpace_grifflaenge(Double.valueOf(griff_hoehe));
				}
				if (!euro_x.equals(""))
				{
					wps.setXpace_euroloch_x_koordinate(Double.valueOf(euro_x));
				}
				if (!euro_y.equals(""))
				{
					wps.setXpace_euroloch_y_koordinate(Double.valueOf(euro_y));
				}
				if (can_stack.equals("1"))
				{
					wps.setXpace_stellbar(true);
				}
				else
				{
					wps.setXpace_stellbar(false);
				}
				if (can_hang.equals("1"))
				{
					wps.setXpace_haengbar(true);
				}
				else
				{
					wps.setXpace_haengbar(false);
				}

				if (can_heap.equals("1"))
				{
					wps.setXpace_schuettbar(true);
				}
				else
				{
					wps.setXpace_schuettbar(false);
				}
				if (verpackungstyp.equals("BC12 in Display"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_BC12_IN_DISPLAY);
				}
				if (verpackungstyp.equals("BC30 in Display"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_BC30_IN_DISPLAY);
				}
				if (verpackungstyp.equals("Bit-Box"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_BIT_BOX);
				}
				if (verpackungstyp.equals("Bitkarte"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_BITKARTE);
				}
				if (verpackungstyp.equals("Bitkarte BC"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_BITKARTE_BC);
				}
				if (verpackungstyp.equals("Bitkarte lang"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_BITKARTE_LANG);
				}
				if (verpackungstyp.equals("BSK"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_BSK);
				}
				if (verpackungstyp.equals("Demo"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_DEMO);
				}
				if (verpackungstyp.equals("DIY10"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_DIY10);
				}
				if (verpackungstyp.equals("DIY100"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_DIY100);
				}
				if (verpackungstyp.equals("EFK"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_EFK);
				}
				if (verpackungstyp.equals("quergriff_400"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_QUERGRIFF_400);
				}
				if (verpackungstyp.equals("Rose"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_ROSE);
				}
				if (verpackungstyp.equals("sd_100"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_100);
				}
				if (verpackungstyp.equals("sd_100_grün_gelb"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_100_GRUEN_GELB);
				}
				if (verpackungstyp.equals("sd_100_grün_grün"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_100_GRUEN_GRUEN);
				}
				if (verpackungstyp.equals("sd_100_rot_rot"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_100_ROT_ROT);
				}
				if (verpackungstyp.equals("sd_100_rot_schwarz"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_100_ROT_SCHWARZ);
				}
				if (verpackungstyp.equals("sd_300"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_300);
				}
				if (verpackungstyp.equals("sd_300_gelb_grau"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_300_GELB_GRAU);
				}
				if (verpackungstyp.equals("sd_300_grau_schwarz "))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_300_GRAU_SCHWARZ);
				}
				if (verpackungstyp.equals("sd_300_grün_grau"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_300_GRUEN_GRAU);
				}
				if (verpackungstyp.equals("sd_300_grün_grün"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_300_GRUEN_GRUEN);
				}
				if (verpackungstyp.equals("sd_300_orange_grau"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_300_ORANGE_GRAU);
				}
				if (verpackungstyp.equals("sd_300_rot_grau"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_300_ROT_GRAU);
				}
				if (verpackungstyp.equals("sd_300_rot_rot"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_300_ROT_ROT);
				}
				if (verpackungstyp.equals("sd_900"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_900);
				}
				if (verpackungstyp.equals("sd_900_schwarz"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_900_SCHWARZ);
				}
				if (verpackungstyp.equals("sd_classic"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_CLASSIC);
				}
				if (verpackungstyp.equals("sd_classic_vde"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_CLASSIC_VDE);
				}
				if (verpackungstyp.equals("sd_comfort"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_COMFORT);
				}
				if (verpackungstyp.equals("sd_comfort_vde"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_COMFORT_VDE);
				}
				if (verpackungstyp.equals("sd_esd"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_ESD);
				}
				if (verpackungstyp.equals("sd_esd_schwarz"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_ESD_SCHWARZ);
				}
				if (verpackungstyp.equals("sd_holz"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_HOLZ);
				}
				if (verpackungstyp.equals("sd_kombi"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_KOMBI);
				}
				if (verpackungstyp.equals("sd_micro"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_MICRO);
				}
				if (verpackungstyp.equals("sd_micro_esd"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_MICRO_ESD);
				}
				if (verpackungstyp.equals("sd_spannung"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_SPANNUNG);
				}
				if (verpackungstyp.equals("sd_stainless"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_STAINLESS);
				}
				if (verpackungstyp.equals("sd_stainless_vde"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_STAINLESS_VDE);
				}
				if (verpackungstyp.equals("sd_vorstecher"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_VORSTECHER);
				}
				if (verpackungstyp.equals("stubby"))
				{
					wps.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_STUBBY);

				}

				// save the changed model
				modelService.save(wps);

			}
		}
		else
		{
			final WeraVarianteModel wvm = productsFromVariantCodeSearchResults.iterator().next();
			LOGCORR.info("importXPace(): found variant " + wvm.getCode());

			wvm.setXpace_variant_id(variant_id);
			wvm.setXpace_gewicht_verpackung_produkt(Double.valueOf(unit_weight));
			wvm.setXpace_default_z(Double.valueOf(default_z));
			wvm.setXpace_gap_x(Double.valueOf(gap_x));
			wvm.setXpace_klingenlaenge(Double.valueOf(klinge));

			wvm.setXpace_dateiname_prefix(filename_prefix);

			if (!unit_width.equals(""))
			{
				wvm.setXpace_breite_verpackung_produkt(Double.valueOf(unit_width));
			}
			if (!unit_depth.equals(""))
			{
				wvm.setXpace_tiefe_verpackung_produkt(Double.valueOf(unit_depth));
			}
			if (!unit_height.equals(""))
			{
				wvm.setXpace_hoehe_verpackung_produkt(Double.valueOf(unit_height));
			}
			if (!griff_hoehe.equals(""))
			{
				wvm.setXpace_grifflaenge(Double.valueOf(griff_hoehe));
			}
			if (!euro_x.equals(""))
			{
				wvm.setXpace_euroloch_x_koordinate(Double.valueOf(euro_x));
			}
			if (!euro_y.equals(""))
			{
				wvm.setXpace_euroloch_y_koordinate(Double.valueOf(euro_y));
			}


			if (can_stack.equals("1"))
			{
				wvm.setXpace_stellbar(true);
			}
			else
			{
				wvm.setXpace_stellbar(false);
			}
			if (can_hang.equals("1"))
			{
				wvm.setXpace_haengbar(true);
			}
			else
			{
				wvm.setXpace_haengbar(false);
			}

			if (can_heap.equals("1"))
			{
				wvm.setXpace_schuettbar(true);
			}
			else
			{
				wvm.setXpace_schuettbar(false);
			}


			if (verpackungstyp.equals("BC12 in Display"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_BC12_IN_DISPLAY);
			}
			if (verpackungstyp.equals("BC30 in Display"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_BC30_IN_DISPLAY);
			}
			if (verpackungstyp.equals("Bit-Box"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_BIT_BOX);
			}
			if (verpackungstyp.equals("Bitkarte"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_BITKARTE);
			}
			if (verpackungstyp.equals("Bitkarte BC"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_BITKARTE_BC);
			}
			if (verpackungstyp.equals("Bitkarte lang"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_BITKARTE_LANG);
			}
			if (verpackungstyp.equals("BSK"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_BSK);
			}
			if (verpackungstyp.equals("Demo"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_DEMO);
			}
			if (verpackungstyp.equals("DIY10"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_DIY10);
			}
			if (verpackungstyp.equals("DIY100"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_DIY100);
			}
			if (verpackungstyp.equals("EFK"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_EFK);
			}
			if (verpackungstyp.equals("quergriff_400"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_QUERGRIFF_400);
			}
			if (verpackungstyp.equals("Rose"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_ROSE);
			}
			if (verpackungstyp.equals("sd_100"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_100);
			}
			if (verpackungstyp.equals("sd_100_grün_gelb"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_100_GRUEN_GELB);
			}
			if (verpackungstyp.equals("sd_100_grün_grün"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_100_GRUEN_GRUEN);
			}
			if (verpackungstyp.equals("sd_100_rot_rot"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_100_ROT_ROT);
			}
			if (verpackungstyp.equals("sd_100_rot_schwarz"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_100_ROT_SCHWARZ);
			}
			if (verpackungstyp.equals("sd_300"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_300);
			}
			if (verpackungstyp.equals("sd_300_gelb_grau"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_300_GELB_GRAU);
			}
			if (verpackungstyp.equals("sd_300_grau_schwarz "))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_300_GRAU_SCHWARZ);
			}
			if (verpackungstyp.equals("sd_300_grün_grau"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_300_GRUEN_GRAU);
			}
			if (verpackungstyp.equals("sd_300_grün_grün"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_300_GRUEN_GRUEN);
			}
			if (verpackungstyp.equals("sd_300_orange_grau"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_300_ORANGE_GRAU);
			}
			if (verpackungstyp.equals("sd_300_rot_grau"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_300_ROT_GRAU);
			}
			if (verpackungstyp.equals("sd_300_rot_rot"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_300_ROT_ROT);
			}
			if (verpackungstyp.equals("sd_900"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_900);
			}
			if (verpackungstyp.equals("sd_900_schwarz"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_900_SCHWARZ);
			}
			if (verpackungstyp.equals("sd_classic"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_CLASSIC);
			}
			if (verpackungstyp.equals("sd_classic_vde"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_CLASSIC_VDE);
			}
			if (verpackungstyp.equals("sd_comfort"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_COMFORT);
			}
			if (verpackungstyp.equals("sd_comfort_vde"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_COMFORT_VDE);
			}
			if (verpackungstyp.equals("sd_esd"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_ESD);
			}
			if (verpackungstyp.equals("sd_esd_schwarz"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_ESD_SCHWARZ);
			}
			if (verpackungstyp.equals("sd_holz"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_HOLZ);
			}
			if (verpackungstyp.equals("sd_kombi"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_KOMBI);
			}
			if (verpackungstyp.equals("sd_micro"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_MICRO);
			}
			if (verpackungstyp.equals("sd_micro_esd"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_MICRO_ESD);
			}
			if (verpackungstyp.equals("sd_spannung"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_SPANNUNG);
			}
			if (verpackungstyp.equals("sd_stainless"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_STAINLESS);
			}
			if (verpackungstyp.equals("sd_stainless_vde"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_STAINLESS_VDE);
			}
			if (verpackungstyp.equals("sd_vorstecher"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_SD_VORSTECHER);
			}
			if (verpackungstyp.equals("stubby"))
			{
				wvm.setXpace_typ_verpackung(XPaceTypVerpackungEnum.XPACE_STUBBY);
			}
			// save the changed model
			modelService.save(wvm);

		}
		return message;
	}
*/
	// --- Datenkorrektur Texte (15.07.2016)
	public void corrData_20160715(final String strTargetLanguage, final String strOrigLanguage)
	{
		final Logger LOGCORR = Logger.getLogger(DataCorrector.class.getName());

		// --- Output
		LOGCORR.info("DataCorrector.corrData_20160715 (Start) => Sprache: " + strTargetLanguage);

		// --- Initialize
		WeraProduct weraproduct = null;
		String strData = "";
		String strNewData = "";

		// --- Setze Sprache
		SetLanguage("ru");

		// --- Hole alle aktiven Produkte, sortiert
		final Collection products = WeraProduct.getAllInstances();
		LOGCORR.info("DataCorrector.corrData_20160715 = Anzahl Produkte: " + products.size());

		int iCnt = 0;
		// --- Schleife über alle Daten (Produkt)

		final Collection<String> colLanguages = new ArrayList();

		colLanguages.add("ru");

		for (final Iterator it1 = products.iterator(); it1.hasNext();)
		{
			// --- Hole Produkt
			weraproduct = (WeraProduct) it1.next();
			for (final String sLanguage : colLanguages)
			{
				SetLanguage(sLanguage);
				strData = (String) getAttribute(weraproduct, "description1");
				strNewData = _corrData_20160715_helper(strData, sLanguage, "product", weraproduct.getCode());
				if (strData != null && !strData.equals(strNewData))
				{
					setAttribute(weraproduct, "description1", strNewData);
					LOGCORR.info("DC::corrData_20160715(): setting >" + sLanguage + " | " + weraproduct.getCode()
							+ "< description1 to " + strNewData);
					iCnt++;
				}
			}
		}

		LOGCORR.info("DataCorrector() replaced " + iCnt + " occurrences.");

		// --- Setze Sprache zurück
		SetLanguage(strOrigLanguage);
	}

	// --- Datenkorrektur Texte (01.12.2015)
	public void corrData_20151201(final String strTargetLanguage, final String strOrigLanguage)
	{
		final Logger LOGCORR = Logger.getLogger(DataCorrector.class.getName());

		// --- Output
		LOGCORR.info("DataCorrector.corrData_20151201 (Start) => Sprache: " + strTargetLanguage);

		// --- Initialize
		WeraProduct weraproduct = null;
		String strData = "";
		String strData2 = "";
		String strNewData = "";
		String strNewData2 = "";

		// --- Setze Sprache
		SetLanguage("de");

		// --- Hole alle aktiven Produkte, sortiert
		final Collection products = WeraProduct.getAllInstances();
		LOGCORR.info("DataCorrector.corrData_20151201 = Anzahl Produkte: " + products.size());

		int iCnt = 0;
		// --- Schleife über alle Daten (Produkt)

		final Collection<String> colLanguages = new ArrayList();

		colLanguages.add("de");
		colLanguages.add("en");
		colLanguages.add("fr");
		colLanguages.add("it");
		colLanguages.add("es");
		colLanguages.add("dk");
		colLanguages.add("nl");
		colLanguages.add("ru");
		colLanguages.add("cs");
		colLanguages.add("pl");
		colLanguages.add("cn");
		colLanguages.add("us-en");
		colLanguages.add("us-fr");
		colLanguages.add("us-es");

		for (final Iterator it1 = products.iterator(); it1.hasNext();)
		{
			// --- Hole Produkt
			weraproduct = (WeraProduct) it1.next();

			for (final String sLanguage : colLanguages)
			{
				SetLanguage(sLanguage);
				strData = (String) getAttribute(weraproduct, "description1");
				strNewData = _corrData_20151201_helper(strData, sLanguage, "product", weraproduct.getCode());
				if (strData != null && !strData.equals(strNewData))
				{
					setAttribute(weraproduct, "description1", strNewData);
					LOGCORR.info("DC::corrData_20151201(): setting >" + sLanguage + " | " + weraproduct.getCode()
							+ "< description1 to " + strNewData);
					iCnt++;
				}

				strData2 = (String) getAttribute(weraproduct, "description2");
				strNewData2 = _corrData_20151201_helper(strData2, sLanguage, "product", weraproduct.getCode());
				if (strData2 != null && !strData2.equals(strNewData2))
				{
					setAttribute(weraproduct, "description2", strNewData2);
					LOGCORR.info("DC::corrData_20151201(): setting >" + sLanguage + " | " + weraproduct.getCode()
							+ "< description2 to " + strNewData2);
					iCnt++;
				}

			}
		}

		LOGCORR.info("DataCorrector() replaced " + iCnt + " occurrences.");

		// --- Setze Sprache zurück
		SetLanguage(strOrigLanguage);
	}

	// --- Datenkorrektur Texte (30.10.2015)
	public void corrData_20151030(final String strTargetLanguage, final String strOrigLanguage)
	{
		final Logger LOGCORR = Logger.getLogger(DataCorrector.class.getName());

		// --- Output
		LOGCORR.info("DataCorrector.corrData_20151030 (Start) => Sprache: " + strTargetLanguage);

		// --- Initialize
		WeraProduct weraproduct = null;
		String strData = "";
		String strNewData = "";

		// --- Setze Sprache
		SetLanguage("de");

		// --- Hole alle aktiven Produkte, sortiert
		final Collection products = WeraProduct.getAllInstances();
		LOGCORR.info("DataCorrector.corrData_20151030 = Anzahl Produkte: " + products.size());

		int iCnt = 0;
		// --- Schleife über alle Daten (Produkt)

		final Collection<String> colLanguages = new ArrayList();

		colLanguages.add("de");
		colLanguages.add("en");
		colLanguages.add("fr");
		colLanguages.add("it");
		colLanguages.add("es");
		colLanguages.add("dk");
		colLanguages.add("nl");

		for (final Iterator it1 = products.iterator(); it1.hasNext();)
		{
			// --- Hole Produkt
			weraproduct = (WeraProduct) it1.next();

			for (final String sLanguage : colLanguages)
			{
				SetLanguage(sLanguage);
				strData = (String) getAttribute(weraproduct, "description1");
				strNewData = _corrData_20151030_helper(strData, sLanguage, "product", weraproduct.getCode());
				if (strData != null && !strData.equals(strNewData))
				{
					setAttribute(weraproduct, "description1", strNewData);
					LOGCORR.info("DC::corrData_20151030(): setting >" + sLanguage + " | " + weraproduct.getCode()
							+ "< description1 to " + strNewData);
					iCnt++;
				}
			}
		}

		LOGCORR.info("DataCorrector() replaced " + iCnt + " occurrences.");

		// --- Setze Sprache zurück
		SetLanguage(strOrigLanguage);
	}

	// --- Datenkorrektur Texte (14.10.2015)
	public void corrData_20151014(final String strTargetLanguage, final String strOrigLanguage)
	{
		final Logger LOGCORR = Logger.getLogger(DataCorrector.class.getName());

		// --- Output
		LOGCORR.info("DataCorrector.corrData_20151014 (Start) => Sprache: " + strTargetLanguage);

		// --- Initialize
		WeraProduct weraproduct = null;
		String strData = "";
		String strNewData = "";

		// --- Setze Sprache
		SetLanguage("de");

		// --- Hole alle aktiven Produkte, sortiert
		final Collection products = WeraProduct.getAllInstances();
		LOGCORR.info("DataCorrector.corrData_20151014 = Anzahl Produkte: " + products.size());

		int iCnt = 0;
		// --- Schleife über alle Daten (Produkt)

		final Collection<String> colLanguages = new ArrayList();

		colLanguages.add("de");
		colLanguages.add("cn");
		colLanguages.add("cs");
		colLanguages.add("dk");
		colLanguages.add("en");
		colLanguages.add("es");
		colLanguages.add("fr");
		colLanguages.add("it");
		colLanguages.add("nl");
		colLanguages.add("pl");
		colLanguages.add("ru");

		colLanguages.add("us-en");
		colLanguages.add("us-es");
		colLanguages.add("us-fr");

		for (final Iterator it1 = products.iterator(); it1.hasNext();)
		{
			// --- Hole Produkt
			weraproduct = (WeraProduct) it1.next();

			for (final String sLanguage : colLanguages)
			{
				SetLanguage(sLanguage);
				strData = (String) getAttribute(weraproduct, "description1");
				strNewData = _corrData_20151014_helper(strData, sLanguage, "product", weraproduct.getCode());
				if (strData != null && !strData.equals(strNewData))
				{
					setAttribute(weraproduct, "description1", strNewData);
					LOGCORR.info("DataCorrector(): setting description1 to " + strNewData);
					iCnt++;
				}
			}
		}

		LOGCORR.info("DataCorrector() replaced " + iCnt + " occurrences.");

		// --- Setze Sprache zurück
		SetLanguage(strOrigLanguage);
	}

	// --- Datenkorrektur Texte (24.09.2015)
	public void corrData_20150924(final String strTargetLanguage, final String strOrigLanguage)
	{
		final Logger LOGCORR = Logger.getLogger(DataCorrector.class.getName());

		// --- Output
		LOGCORR.info("DataCorrector.corrData_20150924 (Start) => Sprache: " + strTargetLanguage);

		// --- Initialize
		WeraProduct weraproduct = null;
		String strData = "";
		String strNewData = "";

		// --- Setze Sprache
		SetLanguage("de");

		// --- Hole alle aktiven Produkte, sortiert
		final Collection products = WeraProduct.getAllInstances();
		LOGCORR.info("DataCorrector.corrData_20150924 = Anzahl Produkte: " + products.size());

		int iCnt = 0;
		// --- Schleife über alle Daten (Produkt)

		final Collection<String> colLanguages = new ArrayList();

		//colLanguages.add("de");
		//colLanguages.add("ru");
		//colLanguages.add("fr");
		//colLanguages.add("it");
		colLanguages.add("cs");
		//colLanguages.add("dk");
		colLanguages.add("nl");
		//colLanguages.add("us-fr");

		for (final Iterator it1 = products.iterator(); it1.hasNext();)
		{

			// --- Hole Produkt
			weraproduct = (WeraProduct) it1.next();

			for (final String sLanguage : colLanguages)
			{
				SetLanguage(sLanguage);
				strData = (String) getAttribute(weraproduct, "description1");
				strNewData = _corrData_20150924_helper(strData, sLanguage, "product", weraproduct.getCode());
				if (strData != null && !strData.equals(strNewData))
				{
					// setAttribute(weraproduct, "description1", strNewData);
					LOGCORR.info("DataCorrector(): setting description1 to " + strNewData);
					iCnt++;
				}
			}

		}

		LOGCORR.info("DataCorrector() replaced " + iCnt + " occurrences.");

		// --- Setze Sprache zurück
		SetLanguage(strOrigLanguage);
	}

	// --- Datenkorrektur Texte (28.08.2015)
	public void corrData_20150828(final String strTargetLanguage, final String strOrigLanguage)
	{
		final Logger LOGCORR = Logger.getLogger(DataCorrector.class.getName());

		// --- Output
		LOGCORR.info("DataCorrector.corrData_20150421 (Start) => Sprache: " + strTargetLanguage);

		final Category category;

		final CatalogVersion catalogVersion = getCatalogVersion("weracatalog", "weramaster");

		// --- Initialize
		WeraProduct weraproduct = null;
		String strData = "";
		String strNewData = "";

		// --- Setze Sprache
		SetLanguage("de");

		// --- Hole alle aktiven Produkte, sortiert
		final Collection products = WeraProduct.getAllInstances();
		LOGCORR.info("DataCorrector.corrData_20150828 = Anzahl Produkte: " + products.size());

		final int iCnt = 0;
		// --- Schleife über alle Daten (Produkt)

		final Collection<String> colLanguages = new ArrayList();

		//colLanguages.add("de");
		//colLanguages.add("ru");
		//colLanguages.add("fr");
		//colLanguages.add("it");
		colLanguages.add("cs");
		//colLanguages.add("dk");
		//colLanguages.add("nl");
		//colLanguages.add("us-fr");

		for (final Iterator it1 = products.iterator(); it1.hasNext();)
		{

			// --- Hole Produkt
			weraproduct = (WeraProduct) it1.next();

			for (final String sLanguage : colLanguages)
			{
				SetLanguage(sLanguage);
				strData = (String) getAttribute(weraproduct, "description1");
				strNewData = _corrData_20150828_helper(strData, sLanguage, "product", weraproduct.getCode());
				if (strData != null && !strData.equals(strNewData))
				{
					setAttribute(weraproduct, "description1", strNewData);
					LOGCORR.info("DataCorrector(): setting description1 to " + strNewData);
				}
			}

		}

		LOGCORR.info("DataCorrector() replaced " + iCnt + " occurrences.");

		// --- Setze Sprache zurück
		SetLanguage(strOrigLanguage);
	}

	public String _corrData_20150421_helper(final String sOld, final String sType, final String sCode)
	{
		final Logger LOGCORR = Logger.getLogger(DataCorrector.class.getName());

		final String[] searchAndReplacePatterns =
		{ "Cacciviti a percussione|Giravite a percussione", "cacciviti a percussione|giravite a percussione",
				"Caccivite a percussione|Giravite a percussione", "caccivite a percussione|giravite a percussione",
				"Cacciaviti scalpello|Giravite scalpello", "cacciaviti scalpello|giravite scalpello",
				"Cacciavite scalpello|Giravite scalpello", "cacciavite scalpello|giravite scalpello",
				"Cacciaviti dinamometrici|Giravite dinamometrici", "cacciaviti dinamometrici|giravite dinamometrici",
				"Cacciavite dinamometrici|Giravite dinamometrici", "cacciavite dinamometrici|giravite dinamometrici",
				"Cacciavite|Giravite", "cacciavite|giravite", "Cacciaviti|Giravite", "cacciaviti|giravite", "Modello:|Versione:",
				"modello|versione", "Rack|Supporto", "rack|supporto", "con sfera|testa sferica",
				"Applicazioni:</b> per|Applicazioni:</b>", "Applicazioni:</b>per|Applicazioni:</b>",
				"Applicazioni: per|Applicazioni:",
				"<b>Applicazioni: </b>per inserti esagonali|<b>Applicazioni:</b> inserti esagonali",
				"Aplicazioni:</b>per inserti con attacco	Applicazioni:|Aplicazioni:</b> inserti con attacco",
				"<b>Applicazione:</b> per viti e dadi a testa esagonale<br|<b>Applicazione:</b> viti e dadi a testa esagonale<br",
				"<b>Aplicazioni:</b>per inserti con attacco esagonale|<b>Applicazioni:</b> inserti con attacco esagonale",
				"<b>Applicazioni:</b>la lavorazione della latta,|<b>Applicazioni:</b>per la lavorazione della latta,",
				"<b>Applicazioni:</b> avvitamenti facili|<b>Applicazioni:</b> per avvitamenti facili",
				"<b>Applicazioni:</b> il giravite a percussione 2080|<b>Da usare con:</b> il giravite a percussione 2080",
				"esagonale in un solo pezzo|passante esagonale", "in scatola da esposizione|in confezione con finestra",
				"nichelato opaco|cromo-nichel opaco", "un componente|monocomponente", "nichelata opaca|cromo-nichel opaca",
				"9 insert,|9 inserti,", ",  e|, e" };

		String strLast = "";
		String strNew = sOld;

		if (sOld != null)
		{
			for (int pCnt = 0; pCnt < searchAndReplacePatterns.length; pCnt++)
			{
				final String sPattern = searchAndReplacePatterns[pCnt];
				final String[] aPair = sPattern.split("\\|");
				if (aPair.length == 2)
				{
					final String sSearch = aPair[0];
					final String sReplace = aPair[1];

					// --- Description1
					strLast = strNew;

					// ignore case in search pattern
					strNew = strNew.replaceAll(sSearch, sReplace);
					if (0 == 1 && !strLast.equals(strNew))
					{
						LOGCORR.info("=====================");
						LOGCORR.info("OLD value = " + sOld);
						LOGCORR.info("NEW value = " + strNew);
						LOGCORR.info("SR rule = ( " + sSearch + " => " + sReplace + " ) ");
					}

				}
				else
				{
					LOGCORR.warn("DataCorrector.corrData_20150421: Invalid S+R pattern >" + sPattern + "<");
				}
			}
			if (!sOld.equals(strNew))
			{
				LOGCORR.info("=====================");
				LOGCORR.info("OLD value (" + sType + " / " + sCode + ") = " + sOld);
				LOGCORR.info("NEW value (" + sType + " / " + sCode + ") = " + strNew);
			}

		}

		return strNew;

	}

	// --- Datenkorrektur IT Texte (21.04.2015)
	public void corrData_20150421(final String strTargetLanguage, final String strOrigLanguage)
	{
		final Logger LOGCORR = Logger.getLogger(DataCorrector.class.getName());

		// --- Output
		LOGCORR.info("DataCorrector.corrData_20150421 (Start) => Sprache: " + strTargetLanguage);

		Category category;

		final CatalogVersion catalogVersion = getCatalogVersion("weracatalog", "weramaster");

		// --- Initialize
		WeraProduct weraproduct = null;
		String strData = "";
		String strNewData = "";

		// --- Setze Sprache
		SetLanguage(strTargetLanguage);

		// --- Hole alle aktiven Produkte, sortiert
		final Collection products = WeraProduct.getAllInstances();
		LOGCORR.info("DataCorrector.corrData_20150421 = Anzahl Produkte: " + products.size());

		final int iCnt = 0;
		// --- Schleife über alle Daten (Produkt)
		for (final Iterator it1 = products.iterator(); it1.hasNext();)
		{

			// --- Hole Produkt
			weraproduct = (WeraProduct) it1.next();
			strData = (String) getAttribute(weraproduct, "description1");
			strNewData = _corrData_20150421_helper(strData, "product", weraproduct.getCode());
			if (strData != null && !strData.equals(strNewData))
			{
				setAttribute(weraproduct, "description1", strNewData);
			}

			strData = (String) getAttribute(weraproduct, "name");
			strNewData = _corrData_20150421_helper(strData, "product", weraproduct.getCode());
			if (strData != null && !strData.equals(strNewData))
			{
				setAttribute(weraproduct, "name", strNewData);
			}
		}

		// --- Schleife über alle Daten (Category)
		final Collection categories = catalogVersion.getAllCategories();
		LOGCORR.info("DataCorrector.corrData_20150421 = Anzahl Kategorien: " + categories.size());
		for (final Iterator it1 = categories.iterator(); it1.hasNext();)
		{
			// --- Hole Category
			category = (Category) it1.next();

			// --- Name
			strData = (String) getAttribute(category, "name");
			strNewData = _corrData_20150421_helper(strData, "category", category.getCode());
			if (strData != null && !strData.equals(strNewData))
			{
				setAttribute(category, "name", strNewData);
			}

			// --- Untertiel
			strData = (String) getAttribute(category, "untertitel");
			strNewData = _corrData_20150421_helper(strData, "category", category.getCode());
			if (strData != null && !strData.equals(strNewData))
			{
				setAttribute(category, "untertitel", strNewData);
			}
		}

		// --- Schleife über alle Daten (Fussnoten)
		final Collection footnotes = getAllFootnotes();
		LOGCORR.info("DataCorrector.corrData_20150421 = Anzahl Fussnoten: " + footnotes.size());
		Footnote footnote = null;
		for (final Iterator it1 = footnotes.iterator(); it1.hasNext();)
		{
			// --- Hole Footnote
			footnote = (Footnote) it1.next();

			// --- Name
			strData = (String) getAttribute(footnote, "name");
			String sFootnoteCode = "?";
			try
			{
				sFootnoteCode = (String) footnote.getAttribute("code");
			}
			catch (final JaloInvalidParameterException e)
			{
				// YTODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (final JaloSecurityException e)
			{
				// YTODO Auto-generated catch block
				e.printStackTrace();
			}

			strNewData = _corrData_20150421_helper(strData, "footnote", sFootnoteCode);
			if (strData != null && !strData.equals(strNewData))
			{
				setAttribute(footnote, "name", strNewData);
			}

		}

		// --- Schleife über alle Daten (ExtImages)
		final Collection extimages = getAllExtImages();
		LOGCORR.info("DataCorrector.corrData_20150421 = Anzahl ExtImages: " + extimages.size());
		ExtImage extimage = null;
		for (final Iterator it1 = extimages.iterator(); it1.hasNext();)
		{
			// --- Hole Footnote
			extimage = (ExtImage) it1.next();

			// --- Name
			strData = (String) getAttribute(extimage, "description");
			strNewData = _corrData_20150421_helper(strData, "extimage", extimage.getCode());

			if (strData != null && !strData.equals(strNewData))
			{
				setAttribute(extimage, "description", strNewData);
			}

		}

		LOGCORR.info("DataCorrector() replaced " + iCnt + " occurrences.");

		// --- Setze Sprache zurück
		SetLanguage(strOrigLanguage);
	}

	// --- Datenkorrektur von Weralit (24.06.2014)
	public void corrData_20150217(final String strTargetLanguage, final String strOrigLanguage)
	{
		final Logger LOGCORR = Logger.getLogger(DataCorrector.class.getName());

		final String searchPattern1 = "Faible encombrement et poids réduit, pour un net gain de mobilité.";
		final String searchPattern2 = "Kraftform Micro anti-roulement et crête rotative, multicomposants";

		final String replacePattern1 = "Réduction du poids et de l’encombrement pour un net gain de mobilité.";
		final String replacePattern2 = "Kraftform Micro anti-roulement et tête rotative, multicomposants";

		// --- Output
		LOGCORR.info("DataCorrector.corrData_20150217 (Start) => Sprache: " + strTargetLanguage);

		// --- Initialize
		WeraProduct weraproduct = null;
		String strData = "";
		String strNewData = "";

		// --- Setze Sprache
		SetLanguage(strTargetLanguage);

		// --- Hole alle aktiven Produkte, sortiert
		final Collection products = WeraProduct.getAllInstances();
		//final Collection productsets = WeraProductSet.getAllInstances();
		//products.addAll(productsets);
		LOGCORR.info("DataCorrector.corrData_20150217 = Anzahl Produkte: " + products.size());

		int iCnt = 0;
		// --- Schleife über alle Daten (Produkt)
		for (final Iterator it1 = products.iterator(); it1.hasNext();)
		{

			// --- Hole Produkt
			weraproduct = (WeraProduct) it1.next();

			// --- Description1
			strData = (String) getAttribute(weraproduct, "description1");
			if (strData != null)
			{
				strNewData = strData.replaceAll(searchPattern1, replacePattern1);
				strNewData = strNewData.replaceAll(searchPattern2, replacePattern2);

				if (!strData.equals(strNewData))
				{
					LOGCORR.info("Found match in product >" + weraproduct.getCode() + "<:");
					LOGCORR.info("Old " + strTargetLanguage + " text: " + strData);
					LOGCORR.info("New " + strTargetLanguage + " text: " + strNewData);
					iCnt++;
					setAttribute(weraproduct, "description1", strNewData);
				}

			}
			else
			{
				//LOGCORR.warn("product >" + weraproduct.getCode() + "< has null description1");
			}

		}
		LOGCORR.info("DataCorrector() replaced " + iCnt + " occurrences.");

		// --- Setze Sprache zurück
		SetLanguage(strOrigLanguage);

	}

	// --- Datenkorrektur von Weralit, low level method (24.06.2014)
	public String _corrData_weralit(final String strProduct, final String strData, final String strLanguage)
	{

		// --- Gibt es überhaupt Daten?
		if (strData == null)
		{
			return strData;
		}

		// --- Initialize
		String strResult = strData;

		if (strLanguage.equals("cn"))
		{
			strResult = strResult.replaceAll("Weralit II，�?��??料手柄", "�?��??料手柄");
			strResult = strResult.replaceAll("Weralit ESD", "�?��??料手柄");
		}
		if (strLanguage.equals("en") || strLanguage.equals("us-en"))
		{
			strResult = strResult.replaceAll("Weralit II, single-component", "single-component");
			strResult = strResult.replaceAll("Weralit ESD", "single-component");

		}
		if (strLanguage.equals("fr") || strLanguage.equals("us-fr"))
		{
			strResult = strResult.replaceAll("Weralit II, monocomposant", "monocomposant");
			strResult = strResult.replaceAll("Weralit-ESD", "monocomposant");

		}
		if (strLanguage.equals("es") || strLanguage.equals("us-es"))
		{
			strResult = strResult.replaceAll("Weralit II, unicomponente", "unicomponente");
			strResult = strResult.replaceAll("Weralit-ESD", "unicomponente");

		}
		if (strLanguage.equals("it"))
		{
			strResult = strResult.replaceAll("Weralit II, un componente", "un componente");
			strResult = strResult.replaceAll("Weralit-ESD", "un componente");

		}
		if (strLanguage.equals("nl"))
		{
			strResult = strResult.replaceAll("Weralit II, 1-component", "1-component");
			strResult = strResult.replaceAll("Weralit-ESD", "1-component");

		}
		if (strLanguage.equals("dk"))
		{
			strResult = strResult.replaceAll("Weralit II, 1-komponent greb", "1-komponent");
			strResult = strResult.replaceAll("Weralit-ESD", "1-komponent");

		}
		if (strLanguage.equals("cs"))
		{
			strResult = strResult.replaceAll("Weralit II, jednokomponentní", "jednokomponentní");
			strResult = strResult.replaceAll("Weralit-ESD", "jednokomponentní");

		}
		if (strLanguage.equals("pl"))
		{
			strResult = strResult.replaceAll("Weralit II, jednokomponentowa", "jednokomponentowa");
			strResult = strResult.replaceAll("Kraftform, Weralit-ESD, zabezpieczona przed staczaniem, jednokomponentowa",
					"jednokomponentowa");

		}
		if (strLanguage.equals("ru"))
		{
			strResult = strResult.replaceAll("Weralit II, одно-компонентна�?", "1-компонентна�?");
			strResult = strResult.replaceAll("Weralit-ESD", "1-компонентна�?");

		}
		if (strLanguage.equals("de"))
		{
			strResult = strResult.replaceAll("Weralit II, 1-komponentig", "1-komponentig");
			strResult = strResult.replaceAll("Weralit-ESD", "1-komponentig");

		}

		if (!strResult.equals(strData))
		{
			System.out
					.println("DataCorrector.corrData_weralit (" + strProduct + ", " + strLanguage + ") => Sprache: " + strLanguage);
		}

		return strResult;
	}

	// --- Datenkorrektur von Weralit (24.06.2014)
	public void corrData_weralit(final String strLanguage)
	{

		// --- Output
		System.out.println("DataCorrector.corrData_weralit (Start) => Sprache: " + strLanguage);

		// --- Initialize
		WeraProduct weraproduct = null;
		final Category category = null;
		String strData = "";

		// --- Setze Sprache
		SetLanguage(strLanguage);

		// --- Hole alle aktiven Produkte, sortiert
		// m_weraCatalogVersion = getClassificationSystemVersion("weracatalog", "weramaster");
		// m_proficlassCatalogVersion = getClassificationSystemVersion("proficlass", "pc-3.0");
		final Collection products = WeraProduct.getAllInstances();
		final Collection productsets = WeraProductSet.getAllInstances();
		products.addAll(productsets);
		System.out.println("DataCorrector.corrData_weralit = Anzahl Produkte: " + products.size());

		// --- Schleife über alle Daten (Produkt)
		for (final Iterator it1 = products.iterator(); it1.hasNext();)
		{
			// --- Hole Produkt
			weraproduct = (WeraProduct) it1.next();
			if (weraproduct instanceof WeraProductSet)
			{
				// System.out.println("Bearbeite: WPS - " + weraproduct.getCode());
			}
			else
			{
				// System.out.println("Bearbeite: WP - " + weraproduct.getCode());
			}

			// --- Descp 1
			strData = (String) getAttribute(weraproduct, "description1");
			strData = _corrData_weralit(weraproduct.getCode(), strData, strLanguage);
			setAttribute(weraproduct, "description1", strData);

		}

	}

	// --- Datenkorrektur der tschechischen Übersetzungen
	public String corrDataCS_09022007(final String strData)
	{

		// --- Gibt es überhaupt Daten?
		if (strData == null)
		{
			return strData;
		}

		// --- Initialize
		String strSearchStr = "";
		String strReplStr = "";
		String strResult = "";
		strResult = strData;

		// --- Replace Dilna / Dilne / Dilny
		for (int iDilna = 1; iDilna < 25; iDilna++)
		{
			strSearchStr = iDilna + "d\u00EDln\u00E1";
			strReplStr = iDilna + "-ti d\u00EDln\u00E1";

			strResult = strResult.replaceAll(strSearchStr, strReplStr);
			if (strResult != strData)
			{
				System.out.println("++Korr-Dilna=" + iDilna + " / " + strResult);
			}

			strSearchStr = iDilna + "d\u00EDln\u00E9";
			strReplStr = iDilna + "-ti d\u00EDln\u00E9";
			strResult = strResult.replaceAll(strSearchStr, strReplStr);
			if (strResult != strData)
			{
				System.out.println("++Korr-Dilne=" + iDilna + " / " + strResult);
			}

			strSearchStr = iDilna + "d\u00EDln\u00FD";
			strReplStr = iDilna + "-ti d\u00EDln\u00FD";
			strResult = strResult.replaceAll(strSearchStr, strReplStr);
			if (strResult != strData)
			{
				System.out.println("++Korr-Dilny=" + iDilna + " / " + strResult);
			}

		} // --- for ( int iDilna=1; iDilna < 20; iDilna++ ) {

		// --- Kulatá, nerezová ocel => kruhová, nerezová ocel
		strResult = strResult.replaceAll("Kulat\u00E1, nerezov\u00E1 ocel", "kruhov\u00E1, nerezov\u00E1 ocel");

		// --- nicr matná => matný chrom
		strResult = strResult.replaceAll("nicr matn\u00E1", "matn\u00FD chrom");

		// --- Sada šroubovák?, nerezová ocel + p?ihrádka => Sada šroubovák?, nerezová ocel + držák
		strResult = strResult.replaceAll("Sada \u0161roubov\u00E1k\u016F, nerezov\u00E1 ocel + p\u0159ihr\u00E1dka",
				"Sada \u0161roubov\u00E1k\u016F, nerezov\u00E1 ocel + dr\u017E\u00E1k");

		// --- Sada šroubovák?, nerezová ocel + p?ihrádkou => Sada šroubovák?, nerezová ocel + držákem
		strResult = strResult.replaceAll("Sada \u0161roubov\u00E1k\u016F, nerezov\u00E1 ocel + p\u0159ihr\u00E1dkou",
				"Sada \u0161roubov\u00E1k\u016F, nerezov\u00E1 ocel + dr\u017E\u00E1kem");

		return strResult;
	}

	// --- Datenkorrektur der tschechischen Übersetzungen
	public void corrDataCS()
	{

		// --- Initialize
		WeraProduct weraproduct = null;
		String strData = "";

		// --- Setze Sprache
		SetLanguage("cs");

		// --- Hole alle aktiven Produkte, sortiert
		m_weraCatalogVersion = getClassificationSystemVersion(Config.getParameter("wera.mastercatalog"),
				Config.getParameter("wera.mastercatalogversion"));
		m_proficlassCatalogVersion = getClassificationSystemVersion("proficlass", "pc-3.0");
		disableCheckForActivation();
		final Collection products = WeraProduct.getAllProductsFromCatalog(m_weraCatalogVersion, true, "code");

		// --- Schleife über alle Daten
		for (final Iterator it1 = products.iterator(); it1.hasNext();)
		{
			// --- Hole Produkt
			weraproduct = (WeraProduct) it1.next();
			System.out.println("Bearbeite:" + weraproduct.getCode());

			// --- Name
			strData = (String) getAttribute(weraproduct, "name");
			strData = corrDataCS_09022007(strData);
			setAttribute(weraproduct, "name", strData);

			// --- Descp 1
			strData = (String) getAttribute(weraproduct, "description1");
			strData = corrDataCS_09022007(strData);
			setAttribute(weraproduct, "description1", strData);

			// --- Descp 2
			strData = (String) getAttribute(weraproduct, "description2");
			strData = corrDataCS_09022007(strData);
			setAttribute(weraproduct, "description2", strData);

			// --- Descp 3
			strData = (String) getAttribute(weraproduct, "description3");
			strData = corrDataCS_09022007(strData);
			setAttribute(weraproduct, "description3", strData);
		}
	}

	// --- Datenkorrektur der tschechischen Übersetzungen
	public String _corrData_trademarks(final String strData)
	{

		// --- Initialize
		String strResult = strData;
		if (strResult != null)
		{

			strResult = strResult.replaceAll("Kraftform\u00AE", "Kraftform");
			strResult = strResult.replaceAll("Bit-Check\u00AE", "Bit-Check");
			strResult = strResult.replaceAll("Bit-Checks\u00AE", "Bit-Checks");
			strResult = strResult.replaceAll("Bit-Safe\u00AE", "Bit-Safe");
			strResult = strResult.replaceAll("Bit-Safes\u00AE", "Bit-Safes");
			strResult = strResult.replaceAll("BiTorsion\u00AE", "BiTorsion");
			strResult = strResult.replaceAll("Kraftform Kompakt\u00AE", "Kraftform Kompakt");
			strResult = strResult.replaceAll("Lasertip\u00AE", "Lasertip");
			strResult = strResult.replaceAll("Rapidaptor\u00AE", "Rapidaptor");
			strResult = strResult.replaceAll("Der Schraubmeissel\u00AE", "Der Schraubmeissel");
			strResult = strResult.replaceAll("Weralit\u00AE", "Weralit");
			strResult = strResult.replaceAll("Kraftform Micro\u00AE", "Kraftform Micro");
			strResult = strResult.replaceAll("Kraftform\u00AE Micro", "Kraftform Micro");
			strResult = strResult.replaceAll("BlackLaser\u00AE", "BlackLaser");
			strResult = strResult.replaceAll("Bits mit Bi\u00df\u00AE", "Bits mit Bi\u00df");

			/*
			 * --- Mail H. Hermes (13.05.2008) kraftform kraftform kompakt BiTorsion Lasertip BlackLaser Bit-Check
			 * Bit-Checks Bit-Safe Bit-Safes Bits mit Biß Rapidaptor Schraubmeissel Weralit
			 */
		}
		else
		{
			strResult = "";
		}

		return strResult;
	}

	// --- Datenkorrektur der Warenzeichen (31.10.2007)
	public void corrData_trademarks(final String strLanguage)
	{

		// --- Output
		System.out.println("DataCorrector.corrData_trademarks (Start) => Sprache: " + strLanguage);

		// --- Initialize
		WeraProduct weraproduct = null;
		Category category = null;
		String strData = "";

		// --- Setze Sprache
		SetLanguage(strLanguage);

		// --- Hole alle aktiven Produkte, sortiert
		m_weraCatalogVersion = getClassificationSystemVersion(Config.getParameter("wera.mastercatalog"),
				Config.getParameter("wera.mastercatalogversion"));
		m_proficlassCatalogVersion = getClassificationSystemVersion("proficlass", "pc-3.0");
		final Collection products = WeraProduct.getAllInstances();
		final Collection productsets = WeraProductSet.getAllInstances();
		products.addAll(productsets);
		System.out.println("DataCorrector.corrData_trademarks = Anzahl Produkte: " + products.size());

		// --- Schleife über alle Daten (Produkt)
		for (final Iterator it1 = products.iterator(); it1.hasNext();)
		{
			// --- Hole Produkt
			weraproduct = (WeraProduct) it1.next();
			if (weraproduct instanceof WeraProductSet)
			{
				System.out.println("Bearbeite: WPS - " + weraproduct.getCode());
			}
			else
			{
				System.out.println("Bearbeite: WP - " + weraproduct.getCode());
			}

			// --- Name
			strData = (String) getAttribute(weraproduct, "name");
			strData = _corrData_trademarks(strData);
			setAttribute(weraproduct, "name", strData);

			// --- Descp 1
			strData = (String) getAttribute(weraproduct, "description1");
			strData = _corrData_trademarks(strData);
			setAttribute(weraproduct, "description1", strData);

			// --- Descp 2
			strData = (String) getAttribute(weraproduct, "description2");
			strData = _corrData_trademarks(strData);
			setAttribute(weraproduct, "description2", strData);

			// --- Descp 3
			strData = (String) getAttribute(weraproduct, "description3");
			strData = _corrData_trademarks(strData);
			setAttribute(weraproduct, "description3", strData);
		}

		// --- Schleife über alle Daten (Category)
		final Collection categories = m_weraCatalogVersion.getAllCategories();
		System.out.println("DataCorrector.corrData_trademarks = Anzahl Kategorien: " + categories.size());
		for (final Iterator it1 = categories.iterator(); it1.hasNext();)
		{
			// --- Hole Produkt
			category = (Category) it1.next();
			System.out.println("Bearbeite Category:" + category.getCode());

			// --- Name
			strData = (String) getAttribute(category, "name");
			strData = _corrData_trademarks(strData);
			setAttribute(category, "name", strData);

			// --- Untertiel
			strData = (String) getAttribute(category, "untertitel");
			strData = _corrData_trademarks(strData);
			setAttribute(category, "untertitel", strData);
		}

		// --- Schleife über alle Daten (Fussnoten)
		final Collection footnotes = getAllFootnotes();
		System.out.println("DataCorrector.corrData_trademarks = Anzahl Fussnoten: " + footnotes.size());
		Footnote footnote = null;
		for (final Iterator it1 = footnotes.iterator(); it1.hasNext();)
		{
			// --- Hole Produkt
			footnote = (Footnote) it1.next();
			System.out.println("Bearbeite Footnote:" + footnote.getLfdNr());

			// --- Name
			strData = (String) getAttribute(footnote, "name");
			strData = _corrData_trademarks(strData);
			setAttribute(footnote, "name", strData);

		}

		// --- Schleife über alle Daten (Keywords)
		final Collection keywords = getAllKeyword();
		Keyword keyword = null;
		System.out.println("DataCorrector.corrData_trademarks = Anzahl Keywords: " + keywords.size());
		for (final Iterator it1 = keywords.iterator(); it1.hasNext();)
		{
			// --- Hole Produkt
			keyword = (Keyword) it1.next();
			System.out.println("Bearbeite Keyword:" + keyword.getKeyword());

			// --- Name
			strData = (String) getAttribute(keyword, "keyword");
			strData = _corrData_trademarks(strData);
			setAttribute(keyword, "keyword", strData);

		}

	}

	// --- Datenkorrektur
	public String _corrData_081106ES(final String strData, final WeraProduct wp, final JspWriter out)
	{

		// --- Initialize
		String strResult = strData;
		if (strResult != null)
		{
			strResult = strResult.replaceAll("Blackpoint", "Black Point");
			strResult = strResult.replaceAll("Mango:</b>Kraftform", "Mango:</b>Tipo Kraftform");
			strResult = strResult.replaceAll("Mango:</b> Kraftform", "Mango:</b>Tipo Kraftform");
			strResult = strResult.replaceAll("Aplicaci\u00F3n:</b> Para puntas", "Aplicaci\u00F3n:</b>Adecuado para puntas");
			strResult = strResult.replaceAll("Aplicaci\u00F3n:</b>Para puntas", "Aplicaci\u00F3n:</b>Adecuado para puntas");
		}
		else
		{
			strResult = null;
		}
		try
		{
			if (strResult != null && !strResult.equals(strData))
			{
				out.println("<tr>");
				out.println("<td>" + ((wp != null) ? wp.getCode() : "n/a"));
				out.println("</td>");
				out.println("<td>" + strData + "</td>");
				out.println("<td>" + strResult + "</td>");
				out.println("</tr>");
				out.flush();
			}
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		return strResult;
	}

	// --- Datenkorrektur der Warenzeichen (31.10.2007)
	public void corrData_081106ES(final String strLanguage, final JspWriter out)
	{

		// --- Output
		System.out.println("DataCorrector.corrData_081106 (Start) => Sprache: " + strLanguage);

		// --- Initialize
		WeraProduct weraproduct = null;
		Category category = null;
		String strData = "";

		// --- Setze Sprache
		SetLanguage(strLanguage);

		// --- Hole alle aktiven Produkte, sortiert
		m_weraCatalogVersion = getClassificationSystemVersion(Config.getParameter("wera.mastercatalog"),
				Config.getParameter("wera.mastercatalogversion"));
		m_proficlassCatalogVersion = getClassificationSystemVersion("proficlass", "pc-3.0");
		final Collection products = WeraProduct.getAllInstances();
		final Collection productsets = WeraProductSet.getAllInstances();
		products.addAll(productsets);

		try
		{
			out.println("DataCorrector.corrData_081106ES = Anzahl Produkte: " + products.size() + "<br/>");
			out.println("<table id=\"replacement\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">");
			out.println("<tr>");
			out.println("<th width=\"100\">Product</th>");
			out.println("<th width=\"400\">Old</th>");
			out.println("<th width=\"400\">New</th>");
			out.println("</tr>");

			// --- Schleife über alle Daten (Produkt)
			for (final Iterator it1 = products.iterator(); it1.hasNext();)
			{
				// --- Hole Produkt
				weraproduct = (WeraProduct) it1.next();
				if (weraproduct instanceof WeraProductSet)
				{
					out.println("Bearbeite: WPS - " + weraproduct.getCode());
				}
				else
				{
					out.println("Bearbeite: WP - " + weraproduct.getCode());
				}

				// --- Name
				strData = (String) getAttribute(weraproduct, "name");
				strData = _corrData_081106ES(strData, weraproduct, out);
				setAttribute(weraproduct, "name", strData);

				// --- Descp 1
				strData = (String) getAttribute(weraproduct, "description1");
				strData = _corrData_081106ES(strData, weraproduct, out);
				setAttribute(weraproduct, "description1", strData);

				// --- Descp 2
				strData = (String) getAttribute(weraproduct, "description2");
				strData = _corrData_081106ES(strData, weraproduct, out);
				setAttribute(weraproduct, "description2", strData);

				// --- Descp 3
				strData = (String) getAttribute(weraproduct, "description3");
				strData = _corrData_081106ES(strData, weraproduct, out);
				setAttribute(weraproduct, "description3", strData);
			}

			// --- Schleife über alle Daten (Category)
			final Collection categories = m_weraCatalogVersion.getAllCategories();
			out.println("DataCorrector.corrData_081106 = Anzahl Kategorien: " + categories.size() + "<br/>");
			for (final Iterator it1 = categories.iterator(); it1.hasNext();)
			{
				// --- Hole Produkt
				category = (Category) it1.next();
				//System.out.println("Bearbeite Category:" + category.getCode());

				// --- Name
				strData = (String) getAttribute(category, "name");
				strData = _corrData_081106ES(strData, weraproduct, out);
				setAttribute(category, "name", strData);

				// --- Untertiel
				strData = (String) getAttribute(category, "untertitel");
				strData = _corrData_081106ES(strData, weraproduct, out);
				setAttribute(category, "untertitel", strData);
			}

			// --- Schleife über alle Daten (Fussnoten)
			final Collection footnotes = getAllFootnotes();
			out.println("DataCorrector.corrData_081106 = Anzahl Fussnoten: " + footnotes.size() + "<br/>");
			Footnote footnote = null;
			for (final Iterator it1 = footnotes.iterator(); it1.hasNext();)
			{
				// --- Hole Produkt
				footnote = (Footnote) it1.next();
				//System.out.println("Bearbeite Footnote:" + footnote.getLfdNr() );

				// --- Name
				strData = (String) getAttribute(footnote, "name");
				strData = _corrData_081106ES(strData, weraproduct, out);
				setAttribute(footnote, "name", strData);

			}

			// --- Schleife über alle Daten (Keywords)
			final Collection keywords = getAllKeyword();
			Keyword keyword = null;
			out.println("DataCorrector.corrData_trademarks = Anzahl Keywords: " + keywords.size() + "<br/>");
			for (final Iterator it1 = keywords.iterator(); it1.hasNext();)
			{
				// --- Hole Produkt
				keyword = (Keyword) it1.next();
				//System.out.println("Bearbeite Keyword:" + keyword.getKeyword() );

				// --- Name
				strData = (String) getAttribute(keyword, "keyword");
				strData = _corrData_081106ES(strData, weraproduct, out);
				setAttribute(keyword, "keyword", strData);

			}
			out.println("</table>");
		}
		catch (final IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// --- Datenkorrektur
	public String _corrData_081114EN(final String strData, final WeraProduct wp, final JspWriter out)
	{

		// --- Initialize
		String strResult = strData;
		boolean isGroup1 = false;
		boolean isGroup2 = false;

		if (strResult != null)
		{

			strResult = strResult.replaceAll("imperial", "Imperial");

			strResult = strResult.replaceAll("<b>Application:</b>\\s*For 1/4", "<b>Application:</b>Suitable for 1/4");
			strResult = strResult.replaceAll("<b>Application:</b>\\s*For 4 mm", "<b>Application:</b>Suitable for 4 mm");
			strResult = strResult.replaceAll("<b>Application:</b>\\s*For 5/16", "<b>Application:</b>Suitable for 5/16");
			// strResult = strResult.replaceAll("<b>Tip:</b>",                "<b>Design:</b>" );

			strResult = strResult.replaceAll("multi component", "multi-component");
			strResult = strResult.replaceAll("bitholder", "bit holder");
			strResult = strResult.replaceAll("quick release chuck", "quick-release chuck");
			strResult = strResult.replaceAll("Chromium vanadium", "Chrome vanadium");

			if (wp != null)
			{
				Collection colSuperCategories = null;
				try
				{
					colSuperCategories = (Collection) wp.getAttribute("supercategories");
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

				for (final Iterator it0 = colSuperCategories.iterator(); it0.hasNext();)
				{

					final Category category = (Category) it0.next();

					// --- Hole Katalogversion
					final Item catalogVersion = (Item) getAttribute(category, "catalogVersion");

					final Boolean bAktiv = null;
					try
					{
						if (catalogVersion.getAttribute("version").equals(Config.getParameter("wera.mastercatalogversion")))
						{
							if (this.hashSuperCategoriesGroup1.contains(category.getCode()))
							{
								isGroup1 = true;
							}

							if (this.hashSuperCategoriesGroup2.contains(category.getCode()))
							{
								isGroup2 = true;
							}

							if (true == isGroup1)
							{
								strResult = strResult.replaceAll("Tip", "Design");
							}
							if (true == isGroup2)
							{
								// Trick für surface, nicht entfernen
								strResult = strResult.replaceAll("<b>Blade:</b>\\s*BlackLaser surface", "<b>Blade:</b>BlackLaser");
								strResult = strResult.replaceAll("<b>Blade:</b>\\s*BlackLaser", "<b>Design:</b>BlackLaser surface");

							}

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

			}

		}
		else
		{
			strResult = null;
		}
		try
		{
			if (strResult != null && !strResult.equals(strData))
			{
				out.println("<tr>");
				out.println("<td>" + ((wp != null) ? wp.getCode() : "n/a"));
				if (true == isGroup1)
				{
					out.println("<br/>Info:<br/> in Cat-Group 1");
				}
				if (true == isGroup2)
				{
					out.println("<br/>Info:<br/> in Cat-Group 2");
				}
				out.println("</td>");
				out.println("<td>" + strData + "</td>");
				out.println("<td>" + strResult + "</td>");
				out.println("</tr>");
				out.flush();
			}
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		return strResult;
	}

	private HashSet initSuperCategoriesGroup1()
	{
		final HashSet hashSuperCategoriesGroup1 = new HashSet();
		hashSuperCategoriesGroup1.add("KRAFTKANT600");
		hashSuperCategoriesGroup1.add("KRAFTKANT600VDE");
		hashSuperCategoriesGroup1.add("KEDELSTAHL");
		hashSuperCategoriesGroup1.add("SERIE300");
		hashSuperCategoriesGroup1.add("FAHNENSTUBBIES");
		hashSuperCategoriesGroup1.add("SERIE300_ZUBEHOER");
		hashSuperCategoriesGroup1.add("SERIE100");
		hashSuperCategoriesGroup1.add("KKVDE");
		hashSuperCategoriesGroup1.add("SERIE900");
		hashSuperCategoriesGroup1.add("KCOMFORT");
		hashSuperCategoriesGroup1.add("KCOMFORTVDE");
		hashSuperCategoriesGroup1.add("KCLASSIC");
		hashSuperCategoriesGroup1.add("KCLASSICVDE");
		hashSuperCategoriesGroup1.add("HOLZ");
		hashSuperCategoriesGroup1.add("KFORMMICRO");
		hashSuperCategoriesGroup1.add("SERIEESD");
		hashSuperCategoriesGroup1.add("A400QUERFORM");
		hashSuperCategoriesGroup1.add("FESTHALT");
		hashSuperCategoriesGroup1.add("VARIO");
		hashSuperCategoriesGroup1.add("EINHANDKNARRE");
		hashSuperCategoriesGroup1.add("KSERIEVSM");
		hashSuperCategoriesGroup1.add("MERKMAL_BITORSION");
		hashSuperCategoriesGroup1.add("MERKMAL_DIAMOND");
		hashSuperCategoriesGroup1.add("PRO_00");
		hashSuperCategoriesGroup1.add("PRO_0");
		hashSuperCategoriesGroup1.add("PRO_1");
		hashSuperCategoriesGroup1.add("PRO_1_EDELSTAHL");
		hashSuperCategoriesGroup1.add("PRO_1_BITORSION");
		hashSuperCategoriesGroup1.add("PRO_1_DIAMANT");
		hashSuperCategoriesGroup1.add("PRO_1_ZAEH");
		hashSuperCategoriesGroup1.add("PRO_1_HART");
		hashSuperCategoriesGroup1.add("PRO_1_TIN");
		hashSuperCategoriesGroup1.add("PRO_1_ZUBEHOER");
		hashSuperCategoriesGroup1.add("PRO_2");
		hashSuperCategoriesGroup1.add("PRO_3");
		hashSuperCategoriesGroup1.add("PRO_4");
		hashSuperCategoriesGroup1.add("PRO_4_EDELSTAHL");
		hashSuperCategoriesGroup1.add("PRO_4_BITORSION");
		hashSuperCategoriesGroup1.add("PRO_4_DIAMANT");
		hashSuperCategoriesGroup1.add("PRO_4_ZAEH");
		hashSuperCategoriesGroup1.add("PRO_4_HART");
		hashSuperCategoriesGroup1.add("PRO_4_ZUBEHOER");
		hashSuperCategoriesGroup1.add("PRO_5");
		hashSuperCategoriesGroup1.add("PRO_6");
		hashSuperCategoriesGroup1.add("PRO_7");
		hashSuperCategoriesGroup1.add("PRO_8");
		hashSuperCategoriesGroup1.add("PRO_11");
		hashSuperCategoriesGroup1.add("PRO_12");
		hashSuperCategoriesGroup1.add("PRO_15");
		hashSuperCategoriesGroup1.add("PRO_16");
		hashSuperCategoriesGroup1.add("PRO_19");
		hashSuperCategoriesGroup1.add("PRO_23");
		hashSuperCategoriesGroup1.add("PRO_24");
		hashSuperCategoriesGroup1.add("MERKMAL_LUFTFAHRT");
		hashSuperCategoriesGroup1.add("PRO_9");
		hashSuperCategoriesGroup1.add("PRO_21");
		hashSuperCategoriesGroup1.add("PRO_22");
		hashSuperCategoriesGroup1.add("PRO_700");
		hashSuperCategoriesGroup1.add("NUTSETTER");
		hashSuperCategoriesGroup1.add("DREHM_INDIK");
		hashSuperCategoriesGroup1.add("SCHLAGDREHER");
		hashSuperCategoriesGroup1.add("IMPACTDRV090");
		hashSuperCategoriesGroup1.add("IMPACTDRV120");
		hashSuperCategoriesGroup1.add("IMPACTDRV180");
		hashSuperCategoriesGroup1.add("SCHONHAEMMER");

		return hashSuperCategoriesGroup1;
	}

	private HashSet initSuperCategoriesGroup2()
	{
		final HashSet hashSuperCategoriesGroup2 = new HashSet();
		hashSuperCategoriesGroup2.add("SCREWDRVLKEY");
		hashSuperCategoriesGroup2.add("TORXKUGEL");
		hashSuperCategoriesGroup2.add("TORX");
		hashSuperCategoriesGroup2.add("LKEYSSPECIAL");
		hashSuperCategoriesGroup2.add("WSD");

		return hashSuperCategoriesGroup2;
	}

	// --- Datenkorrektur der Warenzeichen (31.10.2007)
	public void corrData_081114EN(final String strLanguage, final JspWriter out)
	{

		// --- Output
		System.out.println("DataCorrector.corrData_081114 (Start) => Sprache: " + strLanguage);

		// "Tip wird ersetzt durch Design" in hashSuperCategoriesGroup1 Kategorien
		this.hashSuperCategoriesGroup1 = initSuperCategoriesGroup1();
		// Blade: ersetzen durch Design:
		this.hashSuperCategoriesGroup2 = initSuperCategoriesGroup2();

		// --- Initialize
		WeraProduct weraproduct = null;
		Category category = null;
		String strData = "";

		// --- Setze Sprache
		SetLanguage(strLanguage);

		// --- Hole alle aktiven Produkte, sortiert
		m_weraCatalogVersion = getClassificationSystemVersion("weracatalog", "weramaster");
		m_proficlassCatalogVersion = getClassificationSystemVersion("proficlass", "pc-3.0");
		final Collection products = WeraProduct.getAllInstances();
		final Collection productsets = WeraProductSet.getAllInstances();
		products.addAll(productsets);

		try
		{
			out.println("DataCorrector.corrData_081114EN = Anzahl Produkte: " + products.size() + "<br/>");
			out.println("<table id=\"replacement\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">");
			out.println("<tr>");
			out.println("<th width=\"100\">Product</th>");
			out.println("<th width=\"400\">Old</th>");
			out.println("<th width=\"400\">New</th>");
			out.println("</tr>");

			// --- Schleife über alle Daten (Produkt)
			for (final Iterator it1 = products.iterator(); it1.hasNext();)
			{
				// --- Hole Produkt
				weraproduct = (WeraProduct) it1.next();
				/*
				 * if ( weraproduct instanceof WeraProductSet) out.println("Bearbeite: WPS - " + weraproduct.getCode() +
				 * "<br/>"); else out.println("Bearbeite: WP - " + weraproduct.getCode() + "<br/>");
				 */

				// --- Name
				strData = (String) getAttribute(weraproduct, "name");
				strData = _corrData_081114EN(strData, weraproduct, out);
				setAttribute(weraproduct, "name", strData);

				// --- Descp 1
				strData = (String) getAttribute(weraproduct, "description1");
				strData = _corrData_081114EN(strData, weraproduct, out);
				setAttribute(weraproduct, "description1", strData);

				// --- Descp 2
				strData = (String) getAttribute(weraproduct, "description2");
				strData = _corrData_081114EN(strData, weraproduct, out);
				setAttribute(weraproduct, "description2", strData);

				// --- Descp 3
				strData = (String) getAttribute(weraproduct, "description3");
				strData = _corrData_081114EN(strData, weraproduct, out);
				setAttribute(weraproduct, "description3", strData);
			}

			// --- Schleife über alle Daten (Category)
			final Collection categories = m_weraCatalogVersion.getAllCategories();
			out.println("DataCorrector.corrData_081114 = Anzahl Kategorien: " + categories.size() + "<br/>");
			for (final Iterator it1 = categories.iterator(); it1.hasNext();)
			{
				// --- Hole Produkt
				category = (Category) it1.next();
				//System.out.println("Bearbeite Category:" + category.getCode());

				// --- Name
				strData = (String) getAttribute(category, "name");
				strData = _corrData_081114EN(strData, null, out);
				setAttribute(category, "name", strData);

				// --- Untertiel
				strData = (String) getAttribute(category, "untertitel");
				strData = _corrData_081114EN(strData, null, out);
				setAttribute(category, "untertitel", strData);
			}

			// --- Schleife über alle Daten (Fussnoten)
			final Collection footnotes = getAllFootnotes();
			out.println("DataCorrector.corrData_081114 = Anzahl Fussnoten: " + footnotes.size() + "<br/>");
			Footnote footnote = null;
			for (final Iterator it1 = footnotes.iterator(); it1.hasNext();)
			{
				// --- Hole Produkt
				footnote = (Footnote) it1.next();
				//System.out.println("Bearbeite Footnote:" + footnote.getLfdNr() );

				// --- Name
				strData = (String) getAttribute(footnote, "name");
				strData = _corrData_081114EN(strData, null, out);
				setAttribute(footnote, "name", strData);

			}

			// --- Schleife über alle Daten (Keywords)
			final Collection keywords = getAllKeyword();
			Keyword keyword = null;
			out.println("DataCorrector.corrData_081114 = Anzahl Keywords: " + keywords.size() + "<br/>");
			for (final Iterator it1 = keywords.iterator(); it1.hasNext();)
			{
				// --- Hole Produkt
				keyword = (Keyword) it1.next();
				//System.out.println("Bearbeite Keyword:" + keyword.getKeyword() );

				// --- Name
				strData = (String) getAttribute(keyword, "keyword");
				strData = _corrData_081114EN(strData, null, out);
				setAttribute(keyword, "keyword", strData);

			}
			out.println("</table>");
		}
		catch (final IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// --- Datenkorrektur
	public String _corrData_081114IT(final String strData, final WeraProduct wp, final JspWriter out)
	{

		// --- Initialize
		String strResult = strData;
		final boolean isGroup1 = false;
		final boolean isGroup2 = false;

		if (strResult != null)
		{

			strResult = strResult.replaceAll("<b>Applicazioni:</b>per viti Pozidriv", "<b>Applicazioni:</b>Adatte a viti Pozidriv");

			strResult = strResult.replaceAll("rotomda", "rotonda");
			strResult = strResult.replaceAll("tenace-duro", "tenace duro");
			strResult = strResult.replaceAll("tenace duro per", "tenace duro, per");
			strResult = strResult.replaceAll("super duro per", "super duro, per");
			strResult = strResult.replaceAll("DIN 3126 C + E 6,3 (ISO 1173)", "DIN 3126 C 6,3 + E 6,3 (ISO 1173)");

		}
		else
		{
			strResult = null;
		}
		try
		{
			if (strResult != null && !strResult.equals(strData))
			{
				out.println("<tr>");
				out.println("<td>" + ((wp != null) ? wp.getCode() : "n/a"));
				out.println("</td>");
				out.println("<td>" + strData + "</td>");
				out.println("<td>" + strResult + "</td>");
				out.println("</tr>");
				out.flush();
			}
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		return strResult;
	}

	// --- Datenkorrektur der Warenzeichen (31.10.2007)
	public void corrData_081114IT(final String strLanguage, final JspWriter out)
	{

		// --- Output
		System.out.println("DataCorrector.corrData_081114IT (Start) => Sprache: " + strLanguage);

		// --- Initialize
		WeraProduct weraproduct = null;
		Category category = null;
		String strData = "";

		// --- Setze Sprache
		SetLanguage(strLanguage);

		// --- Hole alle aktiven Produkte, sortiert
		m_weraCatalogVersion = getClassificationSystemVersion(Config.getParameter("wera.mastercatalog"),
				Config.getParameter("wera.mastercatalogversion"));
		m_proficlassCatalogVersion = getClassificationSystemVersion("proficlass", "pc-3.0");
		final Collection products = WeraProduct.getAllInstances();
		final Collection productsets = WeraProductSet.getAllInstances();
		products.addAll(productsets);

		try
		{
			out.println("DataCorrector.corrData_081114IT = Anzahl Produkte: " + products.size() + "<br/>");
			out.println("<table id=\"replacement\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">");
			out.println("<tr>");
			out.println("<th width=\"100\">Product</th>");
			out.println("<th width=\"400\">Old</th>");
			out.println("<th width=\"400\">New</th>");
			out.println("</tr>");

			// --- Schleife über alle Daten (Produkt)
			for (final Iterator it1 = products.iterator(); it1.hasNext();)
			{
				// --- Hole Produkt
				weraproduct = (WeraProduct) it1.next();
				/*
				 * if ( weraproduct instanceof WeraProductSet) out.println("Bearbeite: WPS - " + weraproduct.getCode() +
				 * "<br/>"); else out.println("Bearbeite: WP - " + weraproduct.getCode() + "<br/>");
				 */

				// --- Name
				strData = (String) getAttribute(weraproduct, "name");
				strData = _corrData_081114IT(strData, weraproduct, out);
				setAttribute(weraproduct, "name", strData);

				// --- Descp 1
				strData = (String) getAttribute(weraproduct, "description1");
				strData = _corrData_081114IT(strData, weraproduct, out);
				setAttribute(weraproduct, "description1", strData);

				// --- Descp 2
				strData = (String) getAttribute(weraproduct, "description2");
				strData = _corrData_081114IT(strData, weraproduct, out);
				setAttribute(weraproduct, "description2", strData);

				// --- Descp 3
				strData = (String) getAttribute(weraproduct, "description3");
				strData = _corrData_081114IT(strData, weraproduct, out);
				setAttribute(weraproduct, "description3", strData);
			}

			// --- Schleife über alle Daten (Category)
			final Collection categories = m_weraCatalogVersion.getAllCategories();
			out.println("DataCorrector.corrData_081114IT = Anzahl Kategorien: " + categories.size() + "<br/>");
			for (final Iterator it1 = categories.iterator(); it1.hasNext();)
			{
				// --- Hole Produkt
				category = (Category) it1.next();
				//System.out.println("Bearbeite Category:" + category.getCode());

				// --- Name
				strData = (String) getAttribute(category, "name");
				strData = _corrData_081114IT(strData, null, out);
				setAttribute(category, "name", strData);

				// --- Untertiel
				strData = (String) getAttribute(category, "untertitel");
				strData = _corrData_081114IT(strData, null, out);
				setAttribute(category, "untertitel", strData);
			}

			// --- Schleife über alle Daten (Fussnoten)
			final Collection footnotes = getAllFootnotes();
			out.println("DataCorrector.corrData_081114IT = Anzahl Fussnoten: " + footnotes.size() + "<br/>");
			Footnote footnote = null;
			for (final Iterator it1 = footnotes.iterator(); it1.hasNext();)
			{
				// --- Hole Produkt
				footnote = (Footnote) it1.next();
				//System.out.println("Bearbeite Footnote:" + footnote.getLfdNr() );

				// --- Name
				strData = (String) getAttribute(footnote, "name");
				strData = _corrData_081114IT(strData, null, out);
				setAttribute(footnote, "name", strData);

			}

			// --- Schleife über alle Daten (Keywords)
			final Collection keywords = getAllKeyword();
			Keyword keyword = null;
			out.println("DataCorrector.corrData_081114IT = Anzahl Keywords: " + keywords.size() + "<br/>");
			for (final Iterator it1 = keywords.iterator(); it1.hasNext();)
			{
				// --- Hole Produkt
				keyword = (Keyword) it1.next();
				//System.out.println("Bearbeite Keyword:" + keyword.getKeyword() );

				// --- Name
				strData = (String) getAttribute(keyword, "keyword");
				strData = _corrData_081114IT(strData, null, out);
				setAttribute(keyword, "keyword", strData);

			}
			out.println("</table>");
		}
		catch (final IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// --- Datenkorrektur Aktivflag
	public void corrDataAktiv( final String destinationLanguage )
	{

		// --- Initialize
		WeraProductSet weraproductset = null;
		Category category = null;
		Boolean bAktivDe;

		// --- Setze Sprache
		SetLanguage("de");

		final CatalogManager catalogManager = CatalogManager.getInstance();
		Catalog weraCatalog = catalogManager.getCatalog("weracatalog");
		CatalogVersion weraCV = weraCatalog.getCatalogVersion("weramaster");
		Catalog dwCatalog = catalogManager.getCatalog("web");
		CatalogVersion dwCV = dwCatalog.getCatalogVersion("web_doorway");
		
		if ( dwCV != null && weraCV != null ) {
		    System.out.println("CVs ok!");
		} else {
		    System.out.println("weraCV = "+weraCV);
		    System.out.println("dwCV   = "+dwCV);
		}
		disableCheckForActivation();
		// final Collection products = WeraProduct.getAllInstances();
		final Collection productsets = WeraProductSet.getAllInstances();

		// --- Schleife über alle Daten (Produktsätze)
		for (final Iterator it1 = productsets.iterator(); it1.hasNext();)
		{
			// --- Hole Produktsatz
			weraproductset = (WeraProductSet) it1.next();

			// --- Setze Sprache
			SetLanguage("de");
			String sVarNrDE = (String) weraproductset.getLocalizedProperty("variantenNr");
			SetLanguage( destinationLanguage );
			weraproductset.setLocalizedProperty("variantenNr", sVarNrDE);
			System.out.println("Setze Satz >"+weraproductset.getCode()+"< auf VariantenNummer "+sVarNrDE);
		}
		// --- Schleife über alle Daten (WeraVariante)
		WeraVariante weravariant = null;
		final Collection weravariants = WeraVariante.getAllInstances();
		for (final Iterator it1 = weravariants.iterator(); it1.hasNext();)
		{
			// --- Hole Produkt
			weravariant = (WeraVariante) it1.next();

			if (!weravariant.getCode().startsWith("BASE_"))
			{

				System.out.println("Bearbeite: WV - " + weravariant.getCode());

				// --- Setze Sprache
				SetLanguage("de");
				//bAktivDe = (Boolean) weravariant.getLocalizedProperty("aktiv");
				String sVarNrDE = (String) weravariant.getLocalizedProperty("variantenNr");

				// --- Setze Sprache
				/*
				 * SetLanguage("us-en"); bAktivUS = (Boolean) weravariant.getLocalizedProperty("aktiv");
				 */
				SetLanguage(destinationLanguage);
				weravariant.setLocalizedProperty("variantenNr", sVarNrDE);
                                /*
				if (bAktivDe != null && bAktivDe.booleanValue())
				{
					// --- Setze Sprache
					weravariant.setLocalizedProperty("aktiv", new Boolean(true));
				}
				else
				{
					// --- Setze Sprache
					weravariant.setLocalizedProperty("aktiv", new Boolean(false));
					System.out.println("+++ Nicht aktiv.");
				}
                                */
			}
		}

		if (1 == 1)
		{
			// --- Schleife über alle Daten (Category)
			Collection<Category> colCategories = new ArrayList();
			colCategories.addAll( weraCV.getAllCategories() );
			// colCategories.addAll( dwCV.getAllCategories() );
			
			for (final Iterator it1 = colCategories.iterator(); it1.hasNext();)
			{
				// --- Hole Produkt
				category = (Category) it1.next();
				System.out.println("Bearbeite Category:" + category.getCode());

				// --- Setze Sprache
				SetLanguage("de");
				bAktivDe = (Boolean) category.getLocalizedProperty("aktiv");

				// --- Setze Sprache
				/*
				 * SetLanguage("us-en"); bAktivUS = (Boolean) category.getLocalizedProperty("aktiv");
				 */
				SetLanguage(destinationLanguage);
				if (bAktivDe != null && bAktivDe.booleanValue())
				{
					// --- Setze Sprache

					category.setLocalizedProperty("aktiv", new Boolean(true));
				}
				else
				{
					// --- Setze Sprache
					category.setLocalizedProperty("aktiv", new Boolean(false));
				}
			}
		}

	}

	
	/**
	 * Datenkorrektur Auslaufartikel
	 * Auslaufartikel => deaktivieren
	 */
	public void corrDataAuslauf()
	{
		// --- Initialize
		final Collection colLang = C2LManager.getInstance().getAllLanguages();
		WeraProductSet weraproduct = null;
		WeraVariante weravariant = null;
		Boolean bAuslaufArtikel;

		
		// --- Setze Sprache
		SetLanguage("de");

		// --- Hole alle aktiven Produkte, sortiert
		Collection productsets = WeraProductSet.getAllInstances();
		Collection productsetinset = WeraProductSetinSet.getAllInstances();
		productsets.addAll(productsetinset);

		// --- Schleife über alle Daten (Produkt)
		for (final Iterator it1 = productsets.iterator(); it1.hasNext();)
		{
			// --- Hole Produkt
			weraproduct = (WeraProductSet) it1.next();
			if (weraproduct instanceof WeraProductSet || weraproduct instanceof WeraProductSetinSet )
			{
				System.out.println("Bearbeite: WPS - " + weraproduct.getCode());

				// --- Setze Sprache
				bAuslaufArtikel = (Boolean) getAttribute(weraproduct, "artikel_auslauf");

				// --- ist es ein auslaufartikel
				if (bAuslaufArtikel != null && bAuslaufArtikel.booleanValue())
				{
					System.out.println( "-" );
					
					// --- Setze Sprache
					setAttribute(weraproduct, "aktiv", new Boolean(false) );
					//weraproduct.setAttribute("artikel_auslauf", new Boolean(false));
				}
			}
		}
		
		
		// --- Schleife über alle Daten (WeraVariante)
		final Collection weravariants = WeraVariante.getAllInstances();
		for (final Iterator it1 = weravariants.iterator(); it1.hasNext();)
		{
			// --- Hole Produkt
			weravariant = (WeraVariante) it1.next();

			if (!weravariant.getCode().startsWith("BASE_"))
			{
				System.out.println("Bearbeite: WV - " + weravariant.getCode());

				// --- Setze Sprache
				bAuslaufArtikel = (Boolean) getAttribute(weravariant, "artikel_auslauf");

				// --- Setze Sprache
				if (bAuslaufArtikel != null && bAuslaufArtikel.booleanValue()) {
					
					for (final Iterator it0 = colLang.iterator(); it0.hasNext();)
					{
						// --- Sprache aktivieren
						final Language lang1 = (Language) it0.next();
						SetLanguage(lang1.getIsoCode());
						System.out.println( "-" + lang1.getIsoCode());
						
						// --- Setze Sprache
						weravariant.setLocalizedProperty("aktiv", new Boolean(false));

					}
					
					SetLanguage("de");
				}
			}
		}

	}

	
	// --- Datenkorrektur Aktivflag
	public void corrContentQuantity()
	{

		// --- Initialize
		WeraVariante weravariant = null;
		WeraVarianteSet weravariantset = null;
		WeraProductSet weraproductset = null;
		final String strData = "";
		Integer icontentQuantity;

		// --- Setze Sprache
		SetLanguage("de");

		// --- Hole alle aktiven Produkte, sortiert
		m_weraCatalogVersion = getClassificationSystemVersion(Config.getParameter("wera.mastercatalog"),
				Config.getParameter("wera.mastercatalogversion"));
		m_proficlassCatalogVersion = getClassificationSystemVersion("proficlass", "pc-3.0");
		disableCheckForActivation();
		final Collection products = WeraProduct.getAllInstances();
		final Collection productsets = WeraProductSet.getAllInstances();
		products.addAll(productsets);

		// --- Schleife über alle Daten (WeraVariante)
		final Collection weraproductsets = WeraProductSet.getAllInstances();
		Collection variants = null;
		System.out.println("Sätze");
		for (final Iterator it1 = weraproductsets.iterator(); it1.hasNext();)
		{
			// --- Hole Produkt
			weraproductset = (WeraProductSet) it1.next();
			//System.out.println("Bearbeite: WV - " + weravariant.getCode());

			icontentQuantity = (Integer) getAttribute(weraproductset, "contentQuantity");
			if (icontentQuantity == null)
			{
				System.out.println(weraproductset.getCode() + ";0");
			}
			else if (icontentQuantity.intValue() == 0)
			{
				System.out.println(weraproductset.getCode() + ";0");
			}

			variants = (Collection) getAttribute(weraproductset, "variants");
			for (final Iterator it2 = variants.iterator(); it2.hasNext();)
			{
				// --- Hole die Variante
				weravariantset = (WeraVarianteSet) it2.next();
				weravariant = (WeraVariante) getAttribute(weravariantset, "weravariants");
				if (weravariant != null)
				{
					icontentQuantity = (Integer) getAttribute(weravariant, "contentQuantity");
					if (icontentQuantity == null)
					{
						System.out.println(weraproductset.getCode() + "=>" + getAttribute(weravariant, "code") + ";0");
					}
					else if (icontentQuantity.intValue() == 0)
					{
						System.out.println(weraproductset.getCode() + "=>" + getAttribute(weravariant, "code") + ";0");
					}

				}
				else
				{
					System.out.println(weraproductset.getCode() + "=>null");
				}

			}
		}

		// --- Schleife über alle Daten (Sätze)
		final Collection weravariants = WeraVariante.getAllInstances();
		System.out.println("Artikel");
		for (final Iterator it1 = weravariants.iterator(); it1.hasNext();)
		{
			// --- Hole Produkt
			weravariant = (WeraVariante) it1.next();
			//System.out.println("Bearbeite: WV - " + weravariant.getCode());

			icontentQuantity = (Integer) getAttribute(weravariant, "contentQuantity");
			if (icontentQuantity == null)
			{
				System.out.println(getAttribute(weravariant, "code") + ";0");
			}
			else if (icontentQuantity.intValue() == 0)
			{
				System.out.println(getAttribute(weravariant, "code") + ";0");
			}
		}
	}

	private String _activateProperty(final LocalizableItem oItem, final String sField, final FileWriter activation_log,
			final String sReferenceLangCode, final Collection colLang)
	{
		final String rval = null;

		SetLanguage(sReferenceLangCode);

		if (sField != null)
		{

			// --- Hole Aktiv-Flag Localized
			Boolean refValue = (java.lang.Boolean) oItem.getLocalizedProperty(sField);

			if (refValue == null)
			{
				refValue = new Boolean(false);
			}

			for (final Iterator it4 = colLang.iterator(); it4.hasNext();)
			{
				// --- Sprache aktivieren
				final String lang1 = (String) it4.next();

				SetLanguage(lang1);

				try
				{
					oItem.setLocalizedProperty(sField, refValue);
					// rval += "   _activateProperty (" + oItem.getClass().toString() + ", " + sField + ") " + getAttribute(oItem, "code") + " and " + lang1 + "...\n";

				}
				catch (final Exception e)
				{
					System.out.println("_activateProperty failed to ACTIVATE item " + getAttribute(oItem, "code") + " for " + lang1);
					e.printStackTrace();
				}

			}

			SetLanguage(sReferenceLangCode);
			// foreignWeraLanguages = null;

		}
		return rval;
	}

	public String corrBackgroundCol(String stringStartCategory, final FileWriter activation_log, final String sReferenceLanguage,
			final Collection colLang)
	{
		String rval = "";

		if (stringStartCategory == null || stringStartCategory.equals(""))
		{
			stringStartCategory = "root";
		}

		// --- Auf sReferenceLanguage setzen
		SetLanguage(sReferenceLanguage);

		// --- wera catalog und catalogversion holen, oder ggf. neu anlegen
		final ClassificationSystemVersion weraCatalogVersion = getCSV(Config.getParameter("wera.mastercatalog"),
				Config.getParameter("wera.mastercatalogversion"));

		try
		{
			this.disableCheckForActivation();
			final Collection colCategories = getCategories(Config.getParameter("wera.mastercatalog"),
					Config.getParameter("wera.mastercatalogversion"), stringStartCategory);
			this.enableCheckForActivation();
			final Category category = weraCatalogVersion.getCategory(stringStartCategory);

			SetLanguage(sReferenceLanguage);

			final Collection colProducts = WeraProduct.getAllInstances();

			// -- Alle Produkte durchgehen
			int countProducts = 0;
			System.out.println("Es werden " + colProducts.size() + " bearbeitet!");
			for (final Iterator it0 = colProducts.iterator(); it0.hasNext();)
			{
				final WeraProduct wp = (WeraProduct) it0.next();
				countProducts++;
				// Update productext flag
				// _createCategory2ProductExt(wp, true);

				SetLanguage(sReferenceLanguage);

				// -- Alle Outputcontrols durchgehen
				final Collection ouputcontrols = (Collection) wp.getAttribute("outputcontrols");
				final ClassificationAttribute pca = null;
				final ClassificationAttribute classificationattribute = null;

				for (final Iterator it2 = ouputcontrols.iterator(); it2.hasNext();)
				{

					final Outputcontrol outputcontrol = (Outputcontrol) it2.next();
					if (outputcontrol != null)
					{
						rval = _activateProperty(outputcontrol, "background", activation_log, sReferenceLanguage, colLang);
						System.out.println("Produkt #" + countProducts + ", background geaendert.");
					}
					else
					{
						System.out.println("Produkt #" + countProducts + " eine OC ist null.");
					}
				}

			}

		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		return rval;
	}

	// --- Übernahme VarNr und Aktiv
	public void corrData_copyVariantActivationFromDEtoLanguage(final String strlanguage)
	{

		// --- Initialize
		WeraVariante weravariante = null;

		// --- Hole alle aktiven Produkte, sortiert
		final Collection variants = WeraVariante.getAllInstances();

		// --- Schleife über alle Daten (Produkt)
		int iv = 0;
		final int maxv = variants.size();
		for (final Iterator it1 = variants.iterator(); it1.hasNext();)
		{
			iv++;
			// --- Hole Produkt
			weravariante = (WeraVariante) it1.next();

			System.out.println("DE to " + strlanguage + "(): WV (" + iv + "/" + maxv + ")" + weravariante.getCode());

			// --- Setze Sprache de
			SetLanguage("de");
			final String sVarNrDE = (String) weravariante.getLocalizedProperty("variantenNr");

			SetLanguage(strlanguage);
			final String sVarNrJP = (String) weravariante.getLocalizedProperty("variantenNr");

			if (sVarNrDE == null)
			{
				System.out.println("DE to " + strlanguage + "(): VarNr = NULL!");
			}
			else
			{

				if (!sVarNrDE.equals(sVarNrJP))
				{
					System.out.println("DE to " + strlanguage + "(): VarNr values differ => set " + strlanguage + " to DE");
					weravariante.setLocalizedProperty("variantenNr", sVarNrDE);
				}
			}
		}

		final Collection colProducts = WeraProductSet.getAllInstances();

		// -- Alle Produkte durchgehen
		System.out.println("Es werden " + colProducts.size() + " bearbeitet!");
		for (final Iterator it0 = colProducts.iterator(); it0.hasNext();)
		{
			final WeraProduct wp = (WeraProduct) it0.next();

			try
			{
				

				if (wp instanceof WeraProductSet)
				{
                                        SetLanguage("de");
					final String sVarNrDE = (String) wp.getLocalizedProperty("variantenNr");
                                        
					SetLanguage(strlanguage);
                                        final String sVarNrJP = (String) wp.getLocalizedProperty("variantenNr");
                                        
                                        if (sVarNrDE == null)
                                        {
                                                System.out.println("PROD: DE to " + strlanguage + "(): VarNr = NULL!");
                                        }
                                        else
                                        {

                                                if (!sVarNrDE.equals(sVarNrJP))
                                                {
                                                        System.out.println("PROD: DE to " + strlanguage + "(): VarNr values differ => set " + strlanguage + " to DE");
                                                        wp.setLocalizedProperty("variantenNr", sVarNrDE);
                                                        System.out.println("ProduktSet #" + wp.getCode() + " VarNr geaendert.");

                                                }
                                        }                                        
                                        

				}
				
			}
			catch (final Exception e)
			{
				e.printStackTrace();
			}

		} // --- for (final Iterator it0 = colProducts.iterator(); it0.hasNext();) {

	}

	// --- Übernahme englischer Text nach amerikanisch (KAtegorietitel,-untertitiel,Produkttitel,-beschreibungen)
	public void corrData_copyTextsFromENtoUS()
	{

		// --- Initialize
		WeraProduct weraproduct = null;
		Category category = null;
		final String strData = "";

		// --- Hole alle aktiven Produkte, sortiert
		m_weraCatalogVersion = getClassificationSystemVersion(Config.getParameter("wera.mastercatalog"),
				Config.getParameter("wera.mastercatalogversion"));
		m_proficlassCatalogVersion = getClassificationSystemVersion("proficlass", "pc-3.0");
		final Collection products = WeraProduct.getAllInstances();
		final Collection productsets = WeraProductSet.getAllInstances();
		final Collection categories = m_weraCatalogVersion.getAllCategories();

		products.addAll(productsets);

		// --- Schleife über alle Daten (Produkt)
		int ip = 0;
		final int maxp = products.size();
		for (final Iterator it1 = products.iterator(); it1.hasNext();)
		{
			ip++;
			// --- Hole Produkt
			weraproduct = (WeraProduct) it1.next();
			if (weraproduct instanceof WeraProduct)
			{
				System.out.println("copyTextsFromENtoUS-EN(): WP (" + ip + "/" + maxp + ")" + weraproduct.getCode());
			}
			else
			{
				System.out.println("copyTextsFromENtoUS-EN(): WPS (" + ip + "/" + maxp + ")" + weraproduct.getCode());
			}

			// --- Setze Sprache en
			SetLanguage("en");
			final String pName = (String) weraproduct.getLocalizedProperty("name");
			final String pDesc = (String) weraproduct.getLocalizedProperty("description1");

			SetLanguage("us-en");
			weraproduct.setLocalizedProperty("name", pName);
			weraproduct.setLocalizedProperty("description1", pDesc);

		}

		final int ic = 0;
		final int maxc = categories.size();
		for (final Iterator it2 = categories.iterator(); it2.hasNext();)
		{
			// --- Hole Kategorie
			category = (Category) it2.next();
			System.out.println("copyTextsFromENtoUS-EN(): Cat " + category.getCode());

			// --- Setze Sprache en
			SetLanguage("en");
			final String cName = (String) category.getLocalizedProperty("name");
			final String cDesc = (String) category.getLocalizedProperty("untertitel");

			SetLanguage("us-en");
			category.setLocalizedProperty("name", cName);
			category.setLocalizedProperty("untertitel", cDesc);

		}
	}

	// --- Übernahme Textinhalte von Quell- nach Zielsprache
	public void corrData_copyAllTextContent(final String langFrom, final String langTo)
	{
		// --- Kategorie-, Produkttexte, Fußnoten, Schlagworte auf Zielsprache kopieren
		// --- Produkte: Name, Description1
		// --- Kategorie: Name, Untertitel, Description
		// --- Initialize
		final WeraManager wm = new WeraManager();
		WeraProduct weraproduct = null;
		Category category = null;
		final String strData = "";

		// --- Hole alle aktiven Produkte, sortiert
		m_weraCatalogVersion = getClassificationSystemVersion(Config.getParameter("wera.mastercatalog"),
				Config.getParameter("wera.mastercatalogversion"));
		m_proficlassCatalogVersion = getClassificationSystemVersion("proficlass", "pc-3.0");
		final Collection products = WeraProduct.getAllInstances();
		final Collection productsets = WeraProductSet.getAllInstances();
		final Collection categories = m_weraCatalogVersion.getAllCategories();

		final Language oLang = C2LManager.getInstance().getLanguageByIsoCode(langTo);

		products.addAll(productsets);

		// NUR TEST
		// products = ProductManager.getInstance().getProductsByCode("3334");
		final JaloSession js = JaloSession.getCurrentSession();
		final ComposedType KeywordType = js.getTypeManager().getComposedType(Keyword.class);

		// --- Schleife über alle Daten (Produkt)
		int ip = 0;
		final int maxp = products.size();
		for (final Iterator it1 = products.iterator(); it1.hasNext();)
		{
			ip++;
			// --- Hole Produkt
			weraproduct = (WeraProduct) it1.next();
			if (weraproduct instanceof WeraProductSet)
			{
				System.out.println("copyAllTextContent(): Set (" + ip + "/" + maxp + ")" + weraproduct.getCode());
			}
			else
			{
				System.out.println("copyAllTextContent(): Product (" + ip + "/" + maxp + ")" + weraproduct.getCode());
			}

			// --- Setze Sprache FROM
			SetLanguage(langFrom);
			final String pName = (String) weraproduct.getLocalizedProperty("name");
			final String pDesc = (String) weraproduct.getLocalizedProperty("description1");

			final Collection aKeywords = (Collection) wm.getAttribute(weraproduct, "keywords");

			final ArrayList aNewKeywordData = new ArrayList();
			final ArrayList aNewKeywords = new ArrayList();
			Keyword oNewKeyword = null;

			for (final Iterator itKeywords = aKeywords.iterator(); itKeywords.hasNext();)
			{
				final HashMap mKeyword = new HashMap();

				final Keyword thisKeyword = (Keyword) itKeywords.next();

				mKeyword.put("keyword", thisKeyword.getKeyword());
				mKeyword.put("language", oLang);
				final Collection srcProducts = (Collection) wm.getAttribute(thisKeyword, "products");

				mKeyword.put("catalogVersion", WeraManager.m_weraCatalogVersion);
				mKeyword.put("products", new ArrayList(srcProducts));
				aNewKeywordData.add(mKeyword);

			}

			// --- Setze Sprache TO
			SetLanguage(langTo);
			weraproduct.setLocalizedProperty("name", pName);
			weraproduct.setLocalizedProperty("description1", pDesc);

			Collection oldKeywords = null;
			try
			{
				oldKeywords = (Collection) weraproduct.getAttribute("keywords");
			}
			catch (final JaloInvalidParameterException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (final JaloSecurityException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for (final Iterator it3 = oldKeywords.iterator(); it3.hasNext();)
			{
				final Keyword key = (Keyword) it3.next();
				try
				{
					key.remove();
					System.out.println("copyAllTextContent(): keyword removed.");
				}
				catch (final ConsistencyCheckException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			for (final Iterator it2 = aNewKeywordData.iterator(); it2.hasNext();)
			{
				final HashMap myHash = (HashMap) it2.next();
				try
				{
					oNewKeyword = (Keyword) KeywordType.newInstance(myHash);
					aNewKeywords.add(oNewKeyword);
				}
				catch (final JaloGenericCreationException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (final JaloAbstractTypeException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			try
			{
				weraproduct.setAttribute("keywords", aNewKeywords);
				System.out.println("copyAllTextContent(): Keywords assigned");
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
			catch (final JaloBusinessException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		final int ic = 0;
		final int maxc = categories.size();
		for (final Iterator it2 = categories.iterator(); it2.hasNext();)
		{
			// --- Hole Kategorie
			category = (Category) it2.next();
			System.out.println("copyAllTextContent(): Cat " + category.getCode());

			// --- Setze Sprache FROM
			SetLanguage(langFrom);
			final String cName = (String) category.getLocalizedProperty("name");
			final String cUntertitel = (String) category.getLocalizedProperty("untertitel");
			final String cDesc = (String) category.getLocalizedProperty("description");

			// --- Setze Sprache TO
			SetLanguage(langTo);
			category.setLocalizedProperty("name", cName);
			category.setLocalizedProperty("untertitel", cUntertitel);
			category.setLocalizedProperty("description", cDesc);

		}

		// Footnotes mappen src => to
		final Collection setFootnotes = wm.getAllFootnotes();
		Footnote footnote = null;
		for (final Iterator itFootnotes = setFootnotes.iterator(); itFootnotes.hasNext();)
		{
			footnote = (Footnote) itFootnotes.next();
			SetLanguage(langFrom);
			final String footnoteNameFrom = (String) footnote.getLocalizedProperty("name");
			SetLanguage(langTo);
			footnote.setLocalizedProperty("name", footnoteNameFrom);
			System.out.println("copyAllTextContent(): Footnote: " + footnoteNameFrom);
		}

	}

	// --- Übernahme englischer Text nach amerikanisch (KAtegorietitel,-untertitiel,Produkttitel,-beschreibungen)
	public void corrData_replaceQuots()
	{

		// --- Initialize
		WeraProduct weraproduct = null;
		final Category category = null;
		final String strData = "";

		// --- Hole alle aktiven Produkte, sortiert
		m_weraCatalogVersion = getClassificationSystemVersion(Config.getParameter("wera.mastercatalog"),
				Config.getParameter("wera.mastercatalogversion"));
		m_proficlassCatalogVersion = getClassificationSystemVersion("proficlass", "pc-3.0");
		final Collection products = WeraProduct.getAllInstances();
		final Collection productsets = WeraProductSet.getAllInstances();
		products.addAll(productsets);

		final Collection colLang = C2LManager.getInstance().getAllLanguages();

		for (final Iterator it0 = colLang.iterator(); it0.hasNext();)
		{
			// --- Sprache aktivieren
			final Language lang1 = (Language) it0.next();
			SetLanguage(lang1.getIsoCode());
			System.out.println("corrData_replaceQuots: LANGUAGE = " + lang1.getIsoCode());

			// --- Schleife über alle Daten (Produkt)
			for (final Iterator it1 = products.iterator(); it1.hasNext();)
			{

				// --- Hole Produkt
				weraproduct = (WeraProduct) it1.next();
				final String pName = (String) weraproduct.getLocalizedProperty("name");

				if (pName != null && pName.contains("&quot;"))
				{
					weraproduct.setLocalizedProperty("name", pName.replace("&quot;", "\""));
					System.out.println("corrData_replaceQuots: Corrected name:" + (String) weraproduct.getLocalizedProperty("name"));
				}

			}
		}
	}
	
	
	
}
