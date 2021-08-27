package com.craft.dao;

import com.craft.domain.BusinessProfileModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessProfileRepository extends MongoRepository<BusinessProfileModel, String> {

    public BusinessProfileModel findBusinessProfileModelByIdAndStatus(String id, String status);

}
