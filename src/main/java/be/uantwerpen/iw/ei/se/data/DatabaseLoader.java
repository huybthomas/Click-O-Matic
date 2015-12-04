package be.uantwerpen.iw.ei.se.data;

import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTest;
import be.uantwerpen.iw.ei.se.fittsTest.models.FittsTestStage;
import be.uantwerpen.iw.ei.se.repositories.*;
import be.uantwerpen.iw.ei.se.models.Permission;
import be.uantwerpen.iw.ei.se.models.Role;
import be.uantwerpen.iw.ei.se.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by Thomas on 22/10/2015.
 */
@Service
@Profile("default")
public class DatabaseLoader
{
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final FittsTestRepository fittsRepository;
    private final FittsTestStageRepository fittsTestStageRepository;

    @Autowired
    public DatabaseLoader(PermissionRepository permissionRepository, RoleRepository roleRepository, UserRepository userRepository, FittsTestRepository fittsRepository, FittsTestStageRepository fittsTestStageRepository)
    {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.fittsRepository = fittsRepository;
        this.fittsTestStageRepository = fittsTestStageRepository;
    }

    @PostConstruct
    private void initDatabase()
    {
        //Initialise user database
        initUserDatabase();

        //Initialise test database
        initTestDatabase();
    }

    private void initUserDatabase()
    {
        List<Role> roles;
        List<Permission> allPermissions = new ArrayList<Permission>();


        // usermanagement algemene permission van maken
        allPermissions.add(new Permission("logon"));
        allPermissions.add(new Permission("secret-message"));
        allPermissions.add(new Permission("user-management"));
        allPermissions.add(new Permission("test-management"));
        //allPermissions.add(new Permission("createUsers"));
        //allPermissions.add(new Permission("editUsers"));
        //allPermissions.add(new Permission("viewUsers"));
        //allPermissions.add(new Permission("createFittsTest"));

        Iterator<Permission> permissionIterator = allPermissions.iterator();
        while(permissionIterator.hasNext())
        {
            permissionRepository.save(permissionIterator.next());
        }

        Role administrator = new Role("Administrator");
        Role researcher = new Role("Researcher");
        Role tester = new Role("Tester");

        administrator.setPermissions(allPermissions);

        List<Permission> permissions = new ArrayList<Permission>();
        permissions.add(allPermissions.get(0));     // logon
        tester.setPermissions(permissions);

        permissions = new ArrayList<Permission>();
        permissions.add(allPermissions.get(0));     // logon
        permissions.add(allPermissions.get(3));     // test-management
        researcher.setPermissions(permissions);

        roleRepository.save(administrator);
        roleRepository.save(researcher);
        roleRepository.save(tester);

        User u1 = new User("Thomas", "Huybrechts", "thomas.huybrechts", "test");
        roles = new ArrayList<Role>();
        roles.add(administrator);
        u1.setRoles(roles);

        User u2 = new User("Dries", "Blontrock", "dries.blontrock", "test");
        roles = new ArrayList<Role>();
        roles.add(administrator);
        u2.setRoles(roles);

        User u3 = new User("Quinten", "Van Hasselt", "quinten.vanhasselt", "test");
        roles = new ArrayList<Role>();
        roles.add(administrator);
        u3.setRoles(roles);

        User u4 = new User("Timothy", "Verstraete", "timothy.verstraete", "test");
        roles = new ArrayList<Role>();
        roles.add(administrator);
        u4.setRoles(roles);

        User u5 = new User("Nick", "Pauwels", "nick.pauwels", "test");
        roles = new ArrayList<Role>();
        roles.add(administrator);
        u5.setRoles(roles);

        User u6 = new User("Albert", "Einstein", "researcher", "test");
        roles = new ArrayList<Role>();
        roles.add(researcher);
        u6.setRoles(roles);

        User u7 = new User("Tester", "De Test", "tester", "test");
        roles = new ArrayList<Role>();
        roles.add(tester);
        u7.setRoles(roles);

        userRepository.save(u1);
        userRepository.save(u2);
        userRepository.save(u3);
        userRepository.save(u4);
        userRepository.save(u5);
        userRepository.save(u6);
        userRepository.save(u7);
    }

    private void initTestDatabase()
    {
        List<FittsTestStage> fittsTest01Stages = new ArrayList<FittsTestStage>();
        List<FittsTestStage> fittsTest02Stages = new ArrayList<FittsTestStage>();
        List<FittsTestStage> fittsTest03Stages = new ArrayList<FittsTestStage>();
        List<FittsTestStage> allFittsTestStages = new ArrayList<FittsTestStage>();
        List<FittsTest> allFittsTests = new ArrayList<FittsTest>();

        allFittsTestStages.add(new FittsTestStage("001", 9, 25, 100));
        allFittsTestStages.add(new FittsTestStage("002", 11, 15, 150));
        allFittsTestStages.add(new FittsTestStage("003", 5, 50, 110));
        allFittsTestStages.add(new FittsTestStage("004", 2, 100, 200));

        fittsTest01Stages.add(allFittsTestStages.get(0));
        fittsTest01Stages.add(allFittsTestStages.get(1));
        fittsTest01Stages.add(allFittsTestStages.get(2));

        fittsTest02Stages.add(allFittsTestStages.get(3));
        fittsTest02Stages.add(allFittsTestStages.get(1));
        fittsTest02Stages.add(allFittsTestStages.get(0));

        fittsTest03Stages.add(allFittsTestStages.get(3));
        fittsTest03Stages.add(allFittsTestStages.get(3));

        allFittsTests.add(new FittsTest("001", fittsTest01Stages, "TestComment"));
        allFittsTests.add(new FittsTest("002", fittsTest02Stages, "TestComment"));
        allFittsTests.add(new FittsTest("003", fittsTest03Stages, "TestComment"));

        Iterator<FittsTestStage> fittsTestStageIterator = allFittsTestStages.iterator();
        while(fittsTestStageIterator.hasNext())
        {
            fittsTestStageRepository.save(fittsTestStageIterator.next());
        }

        Iterator<FittsTest> fittsTestIterator = allFittsTests.iterator();
        while(fittsTestIterator.hasNext())
        {
            fittsRepository.save(fittsTestIterator.next());
        }
    }
}
