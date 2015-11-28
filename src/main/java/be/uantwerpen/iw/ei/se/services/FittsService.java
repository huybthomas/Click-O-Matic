package be.uantwerpen.iw.ei.se.services;

import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTest;
import be.uantwerpen.iw.ei.se.repositories.FittsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public boolean add(final FittsTest fittstest)
    {
        if(fittstest.getNumberOfDots()<2)
        {
            fittstest.setNumberOfDots(2);
        }
        if(fittstest.getDotSize()>70)
        {
            fittstest.setDotSize(70);
        }
        if(fittstest.getDotDistance()>250)
        {
            fittstest.setDotDistance(250);
        }


        int size=0;
        int amount_zeros =0;
        for( FittsTest u : findAll())
        {
            size+=1;
        }
        if(size>=10)
        {
            String givenID = fittstest.getTestID();
            size+=1;
            fittstest.setTestID(givenID +"0"+Integer.toString(size));
        }
        else if(size>100)
        {
            String givenID = fittstest.getTestID();
            size+=1;
            fittstest.setTestID(givenID +Integer.toString(size));
        }
        else
        {
            String givenID = fittstest.getTestID();
            size += 1;
            fittstest.setTestID(givenID +"00"+ Integer.toString(size));
        }


        this.fittsRepository.save(fittstest);
        return true;
    }

    public void save(FittsTest fittstest)
    {
        for(FittsTest u : findAll())
        {
            if(u.getId() == fittstest.getId())
            {
                u.setNumberOfDots(fittstest.getNumberOfDots());
                u.setDotSize(fittstest.getDotSize());
                u.setDotDistance(fittstest.getDotDistance());
                u.setCompleted(fittstest.getCompleted());

                fittsRepository.save(u);
            }
        }
    }
}
