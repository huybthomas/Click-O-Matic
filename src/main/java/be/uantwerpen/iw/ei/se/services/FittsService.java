package be.uantwerpen.iw.ei.se.services;

import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTest;
import be.uantwerpen.iw.ei.se.repositories.FittsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Thomas on 15/11/2015.
 */
@Service
public class FittsService
{
    @Autowired
    private FittsRepository fittsRepository;

    public FittsTest findById(String testID)
    {
        return this.fittsRepository.findByTestID(testID);
    }

    public Iterable<FittsTest> findAll()
    {
        return this.fittsRepository.findAll();
    }
}
