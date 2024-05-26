package com.system.demo;


import com.system.demo.appl.facade.prefect.communityservice.CommunityServiceFacade;
import com.system.demo.appl.facade.prefect.communityservice.impl.CommunityServiceFacadeImpl;
import com.system.demo.appl.facade.prefect.offense.OffenseFacade;
import com.system.demo.appl.facade.prefect.offense.impl.OffenseFacadeImpl;
import com.system.demo.appl.facade.prefect.violation.ViolationFacade;
import com.system.demo.appl.facade.prefect.violation.impl.ViolationFacadeImpl;
import com.system.demo.data.prefect.communityservice.CommunityServiceDao;
import com.system.demo.data.prefect.communityservice.dao.impl.CommunityServiceDaoImpl;
import com.system.demo.data.prefect.offense.OffenseDao;
import com.system.demo.data.prefect.offense.dao.impl.OffenseDaoImpl;
import com.system.demo.data.prefect.violation.ViolationDao;
import com.system.demo.data.prefect.violation.dao.impl.ViolationDaoImpl;

public class PrefectInfoMgtApplication {
    private CommunityServiceFacade communityserviceFacade;
    private ViolationFacade violationFacade;
    private OffenseFacade offenseFacade;

    public PrefectInfoMgtApplication(){
        CommunityServiceDao communityServiceDaoImpl = new CommunityServiceDaoImpl();
        this.communityserviceFacade = new CommunityServiceFacadeImpl(communityServiceDaoImpl);

        OffenseDao offenseDaoImpl = new OffenseDaoImpl();
        this.offenseFacade = new OffenseFacadeImpl(offenseDaoImpl);

        ViolationDao violationDaoImpl = new ViolationDaoImpl();
        this.violationFacade = new ViolationFacadeImpl(violationDaoImpl);
    }

    public CommunityServiceFacade getCommunityserviceFacade() {
        return communityserviceFacade;
    }

    public OffenseFacade getOffenseFacade() {
        return offenseFacade;
    }

    public ViolationFacade getViolationFacade() {
        return violationFacade;
    }
}

