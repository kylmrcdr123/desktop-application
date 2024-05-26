/**
 * The com.system.demo.appl.facade.prefect.communityservice.impl package contains implementations
 * of facades for managing community service-related operations.
 */
package com.system.demo.appl.facade.prefect.communityservice.impl;

import com.system.demo.appl.facade.prefect.communityservice.CommunityServiceFacade;
import com.system.demo.appl.model.communityservice.CommunityService;
import com.system.demo.appl.model.student.Student;
import com.system.demo.data.prefect.communityservice.CommunityServiceDao;

import java.util.List;

/**
 * The implementation of the CommunityServiceFacade interface.
 */
public class CommunityServiceFacadeImpl implements CommunityServiceFacade {

    private CommunityServiceDao communityServiceDao;

    /**
     * Constructs a new CommunityServiceFacadeImpl with the provided CommunityServiceDao.
     *
     * @param communityServiceDao The data access object for managing community service data.
     */
    public CommunityServiceFacadeImpl(CommunityServiceDao communityServiceDao) {
        this.communityServiceDao = communityServiceDao;
    }


    @Override
    public List<CommunityService> getAllCs() {
        return communityServiceDao.getAllCs();
    }


    @Override
    public CommunityService getCsById(int id) {
        return communityServiceDao.getCsById(id);
    }


    @Override
    public List<CommunityService> getAllCsByStudent(Student studentId) {
        return communityServiceDao.getAllCsByStudent(studentId);
    }

    @Override
    public boolean renderCs(CommunityService cs) throws RuntimeException {
        try {
            return communityServiceDao.renderCs(cs);
        } catch (Exception e) {
            throw new RuntimeException("Error rendering Community Service: " + e.getMessage(), e);
        }
    }

    @Override
    public CommunityService getCommunityServiceByUsername(String username) {
        return communityServiceDao.getCommunityServiceByUsername(username);
    }

    @Override
    public List<CommunityService> getCommunityServicesByStudentName(String lastName, String firstName) {
        return communityServiceDao.findByStudentName(lastName, firstName);
    }
}