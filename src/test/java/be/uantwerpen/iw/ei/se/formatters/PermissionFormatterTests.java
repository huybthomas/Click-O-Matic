package be.uantwerpen.iw.ei.se.formatters;

import be.uantwerpen.iw.ei.se.models.Permission;
import be.uantwerpen.iw.ei.se.repositories.PermissionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

/**
 * Created by Thomas on 02/11/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class PermissionFormatterTests
{
    @InjectMocks
    PermissionFormatter permissionFormatter;

    @Mock
    PermissionRepository permissionRepository;

    Permission permission;

    @Before
    public void init()
    {
        Permission permission = new Permission("logon");

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void parserTest()
    {
        when(permissionRepository.findOne(new Long(permission.getName()))).thenReturn(permission);

     //   permissionFormatter.parse();
    }

    @Test
    public void toStringTest()
    {

    }
}
