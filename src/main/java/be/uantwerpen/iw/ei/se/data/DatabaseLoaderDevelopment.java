package be.uantwerpen.iw.ei.se.data;

import be.uantwerpen.iw.ei.se.fittsTest.models.*;
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
@Profile("dev")
public class DatabaseLoaderDevelopment
{
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final FittsTestRepository fittsRepository;
    private final FittsTestStageRepository fittsTestStageRepository;
    private final FittsResultRepository fittsResultRepository;
    private final FittsStageResultRepository fittsStageResultRepository;
    private final FittsTrackPathRepository fittsTrackPathRepository;
    private final FittsTrackEventRepository fittsTrackEventRepository;

    @Autowired
    public DatabaseLoaderDevelopment(PermissionRepository permissionRepository, RoleRepository roleRepository, UserRepository userRepository, FittsTestRepository fittsRepository, FittsTestStageRepository fittsTestStageRepository, FittsResultRepository fittsResultRepository, FittsStageResultRepository fittsStageResultRepository, FittsTrackPathRepository fittsTrackPathRepository, FittsTrackEventRepository fittsTrackEventRepository)
    {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.fittsRepository = fittsRepository;
        this.fittsTestStageRepository = fittsTestStageRepository;
        this.fittsResultRepository = fittsResultRepository;
        this.fittsStageResultRepository = fittsStageResultRepository;
        this.fittsTrackPathRepository = fittsTrackPathRepository;
        this.fittsTrackEventRepository = fittsTrackEventRepository;
    }

    @PostConstruct
    private void initDatabase()
    {
        //Check if tables are initialized or empty
        if(permissionRepository.findAll().iterator().hasNext())
        {
            //Tables are initialized, no need to refill database
            return;
        }

        //Initialise user database
        initUserDatabase();

        //Initialise test database
        initTestDatabase();

        //Assign tests to users
        initTestAssignment();

        //Initalise test results
        initTestResults();
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

        User u8 = new User("Beheerder", "De Administrator", "admin", "test");
        roles = new ArrayList<Role>();
        roles.add(administrator);
        u8.setRoles(roles);

        userRepository.save(u1);
        userRepository.save(u2);
        userRepository.save(u3);
        userRepository.save(u4);
        userRepository.save(u5);
        userRepository.save(u6);
        userRepository.save(u7);
        userRepository.save(u8);
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

        allFittsTests.add(new FittsTest("001", fittsTest01Stages));
        allFittsTests.add(new FittsTest("002", fittsTest02Stages));
        allFittsTests.add(new FittsTest("003", fittsTest03Stages));

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

    private void initTestAssignment()
    {
        User u1 = userRepository.findByUserName("thomas.huybrechts");
        u1.setTests(new ArrayList<FittsTest>(fittsRepository.findAll()));

        userRepository.save(u1);
    }

    private void initTestResults()
    {
        //Trackpath 01
        FittsTrackEvent event1 = new FittsTrackEvent(1449957847065L, 575, 273, false);
        FittsTrackEvent event2 = new FittsTrackEvent(1449957847127L, 581, 384, false);
        FittsTrackEvent event3 = new FittsTrackEvent(1449957847191L, 571, 489, false);
        FittsTrackEvent event4 = new FittsTrackEvent(1449957847253L, 565, 535, false);
        FittsTrackEvent event5 = new FittsTrackEvent(1449957847317L, 567, 561, false);
        FittsTrackEvent event6 = new FittsTrackEvent(1449957847381L, 569, 598, false);
        FittsTrackEvent event7 = new FittsTrackEvent(1449957847443L, 575, 636, false);
        FittsTrackEvent event8 = new FittsTrackEvent(1449957847569L, 572, 649, true);
        FittsTrackEvent event9 = new FittsTrackEvent(1449957847688L, 572, 649, false);
        Iterable trackEvents1 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7, event8, event9));
        fittsTrackEventRepository.save(trackEvents1);

        //Trackpath 02
        event1 = new FittsTrackEvent(1449957847704L, 572, 648, false);
        event2 = new FittsTrackEvent(1449957847767L, 583, 588, false);
        event3 = new FittsTrackEvent(1449957847829L, 587, 492, false);
        event4 = new FittsTrackEvent(1449957847893L, 578, 406, false);
        event5 = new FittsTrackEvent(1449957847957L, 570, 350, false);
        event6 = new FittsTrackEvent(1449957848019L, 565, 325, false);
        event7 = new FittsTrackEvent(1449957848081L, 565, 309, false);
        event8 = new FittsTrackEvent(1449957848185L, 565, 301, true);
        event9 = new FittsTrackEvent(1449957848317L, 565, 301, false);
        Iterable trackEvents2 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7, event8, event9));
        fittsTrackEventRepository.save(trackEvents2);

        //Trackpath 03
        event1 = new FittsTrackEvent(1449957848767L, 568, 289, false);
        event2 = new FittsTrackEvent(1449957848831L, 586, 409, false);
        event3 = new FittsTrackEvent(1449957848895L, 615, 626, false);
        event4 = new FittsTrackEvent(1449957848957L, 610, 715, false);
        event5 = new FittsTrackEvent(1449957849147L, 610, 717, true);
        event6 = new FittsTrackEvent(1449957849257L, 609, 713, true);
        event7 = new FittsTrackEvent(1449957849257L, 609, 713, false);
        Iterable trackEvents3 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7));
        fittsTrackEventRepository.save(trackEvents3);

        //Trackpath 04
        event1 = new FittsTrackEvent(1449957849265L, 606, 707, false);
        event2 = new FittsTrackEvent(1449957849327L, 613, 602, false);
        event3 = new FittsTrackEvent(1449957849391L, 598, 466, false);
        event4 = new FittsTrackEvent(1449957849453L, 591, 402, false);
        event5 = new FittsTrackEvent(1449957849517L, 591, 387, false);
        event6 = new FittsTrackEvent(1449957849581L, 587, 367, false);
        event7 = new FittsTrackEvent(1449957849643L, 584, 307, false);
        event8 = new FittsTrackEvent(1449957849675L, 584, 284, false);
        event9 = new FittsTrackEvent(1449957849699L, 584, 277, false);
        FittsTrackEvent event10 = new FittsTrackEvent(1449957849777L, 584, 277, true);
        FittsTrackEvent event11 = new FittsTrackEvent(1449957849904L, 584, 277, false);
        Iterable trackEvents4 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7, event8, event9, event10, event11));
        fittsTrackEventRepository.save(trackEvents4);

        //Trackpaths
        FittsTrackPath trackPath1 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents1);
        FittsTrackPath trackPath2 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents2);
        FittsTrackPath trackPath3 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents3);
        FittsTrackPath trackPath4 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents4);
        Iterable trackPaths = new ArrayList<>(Arrays.asList(trackPath1, trackPath2, trackPath3, trackPath4));
        fittsTrackPathRepository.save(trackPaths);

        //Stages
        FittsStageResult stage1 = new FittsStageResult(0, Arrays.asList(trackPath1, trackPath2));
        FittsStageResult stage2 = new FittsStageResult(1, Arrays.asList(trackPath3, trackPath4));
        Iterable stages = new ArrayList<>(Arrays.asList(stage1, stage2));
        fittsStageResultRepository.save(stages);

        //Result
        FittsResult result = new FittsResult("result-0", "003", new Date(), Arrays.asList(stage1, stage2));
        fittsResultRepository.save(result);

        User u1 = userRepository.findByUserName("thomas.huybrechts");
        u1.setResults(Arrays.asList(result));
        userRepository.save(u1);
    }
}
