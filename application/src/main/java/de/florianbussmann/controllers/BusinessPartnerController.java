package de.florianbussmann.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sap.cloud.sdk.cloudplatform.connectivity.DefaultHttpDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.datamodel.odata.client.exception.ODataServiceErrorException;
import com.sap.vdm.namespaces.scsmbusinesspartnerext.BusinessPartner;
import com.sap.vdm.services.DefaultSCSMBusinessPartnerExtService;
import com.sap.vdm.services.SCSMBusinessPartnerExtService;

@RestController
@RequestMapping( "/BusinessPartner" )
public class BusinessPartnerController
{
    private static final Logger logger = LoggerFactory.getLogger(BusinessPartnerController.class);
    
    @Value("${com.sap.vdm.host}")
    private String sapHost;

    @Value("${com.sap.vdm.client}")
    private String sapClient;
    
    @Value("${com.sap.vdm.username}")
    private String sapUsername;
    
    @Value("${com.sap.vdm.password}")
    private String sapPassword;
    
    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity<BusinessPartner> getBusinessPartner( @RequestParam( name = "id" ) final String name )
    {
    	Destination destination = DefaultHttpDestination
                .builder(String.format("https://%s/sap/opu", sapHost))
                .property("URL.queries.saml2", "disabled")
                .property("URL.queries.sap-client", sapClient)
                .basicCredentials(sapUsername, sapPassword)
                .trustAllCertificates()
                .build();
    	SCSMBusinessPartnerExtService service = new DefaultSCSMBusinessPartnerExtService().withServicePath("odata/sap/API_BUSINESS_PARTNER");
    	
    	try {
            return ResponseEntity.ok(service.getBusinessPartnerByKey(name).executeRequest(destination));
    	} catch (ODataServiceErrorException e) {
    	    logger.error(String.format("%s %s", e.getHttpCode(), e.getMessage()));

    	    if (e.getOdataError() != null) {
    	        logger.error(e.getOdataError().getODataMessage());
    	    }
    	    return null;
    	}	
    }
}
