package com.kshrd.demobasicauth.service;

import com.kshrd.demobasicauth.exception.NotFoundExceptionClass;
import com.kshrd.demobasicauth.model.EachDescription;
import com.kshrd.demobasicauth.repository.EachDescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EachDescriptionServiceImpl implements EachDescriptionService{
    private final EachDescriptionRepository eachDescriptionRepository;

    @Override
    public Object getDescription() {
        return eachDescriptionRepository.findById(1L)
                .orElseThrow(() -> new NotFoundExceptionClass("description object is not found."));
    }

    @Override
    public Object updateDescrition(EachDescription description) {
        EachDescription existEachDesc = eachDescriptionRepository.findById(1L)
                .orElseThrow(() -> new NotFoundExceptionClass("description object is not found."));
        existEachDesc.setFindTourDesc(description.getFindTourDesc());
        existEachDesc.setPopularTourDesc(description.getPopularTourDesc());
        existEachDesc.setServiceDesc(description.getServiceDesc());
        existEachDesc.setMostVisitedDesc(description.getMostVisitedDesc());
        existEachDesc.setTourPackagesDesc(description.getTourPackagesDesc());
        existEachDesc.setFrequentlyQuestionDesc(description.getFrequentlyQuestionDesc());
        existEachDesc.setWhyUsDesc(description.getWhyUsDesc());

        return eachDescriptionRepository.save(existEachDesc);
    }
}
