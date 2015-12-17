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
        List<FittsTestStage> fittsTest04Stages = new ArrayList<FittsTestStage>();
        List<FittsTestStage> allFittsTestStages = new ArrayList<FittsTestStage>();
        List<FittsTest> allFittsTests = new ArrayList<FittsTest>();

        allFittsTestStages.add(new FittsTestStage("001", 9, 25, 100));
        allFittsTestStages.add(new FittsTestStage("002", 11, 15, 150));
        allFittsTestStages.add(new FittsTestStage("003", 5, 50, 110));
        allFittsTestStages.add(new FittsTestStage("004", 2, 100, 200));
        allFittsTestStages.add(new FittsTestStage("stage1", 5, 20, 100));
        allFittsTestStages.add(new FittsTestStage("stage2", 5, 20, 100));

        fittsTest01Stages.add(allFittsTestStages.get(0));
        fittsTest01Stages.add(allFittsTestStages.get(1));
        fittsTest01Stages.add(allFittsTestStages.get(2));

        fittsTest02Stages.add(allFittsTestStages.get(3));
        fittsTest02Stages.add(allFittsTestStages.get(1));
        fittsTest02Stages.add(allFittsTestStages.get(0));

        fittsTest03Stages.add(allFittsTestStages.get(3));
        fittsTest03Stages.add(allFittsTestStages.get(3));

        fittsTest04Stages.add(allFittsTestStages.get(4));
        fittsTest04Stages.add(allFittsTestStages.get(4));

        allFittsTests.add(new FittsTest("001", fittsTest01Stages));
        allFittsTests.add(new FittsTest("002", fittsTest02Stages));
        allFittsTests.add(new FittsTest("003", fittsTest03Stages));
        allFittsTests.add(new FittsTest("test01", fittsTest04Stages));

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
        FittsTrackEvent event1 = new FittsTrackEvent(1450050149077L, 4,	-95, false);
        FittsTrackEvent event2 = new FittsTrackEvent(1450050149122L, 24, -24, false);
        FittsTrackEvent event3 = new FittsTrackEvent(1450050149133L, 35, 3, false);
        FittsTrackEvent event4 = new FittsTrackEvent(1450050149149L, 43, 25, false);
        FittsTrackEvent event5 = new FittsTrackEvent(1450050149173L, 56, 47, false);
        FittsTrackEvent event6 = new FittsTrackEvent(1450050149213L, 65, 62, false);
        FittsTrackEvent event7 = new FittsTrackEvent(1450050149381L, 63, 63, false);
        FittsTrackEvent event8 = new FittsTrackEvent(1450050149470L, 59, 73, true);
        FittsTrackEvent event9 = new FittsTrackEvent(1450050149541L, 59, 73, false);
        Iterable trackEvents1 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7, event8, event9));
        fittsTrackEventRepository.save(trackEvents1);

        //Trackpath 02
        event1 = new FittsTrackEvent(1450050149566L, 58, 70, false);
        event2 = new FittsTrackEvent(1450050149605L, 13, 38, false);
        event3 = new FittsTrackEvent(1450050149629L, -34, 16, false);
        event4 = new FittsTrackEvent(1450050149654L, -61, 0, false);
        event5 = new FittsTrackEvent(1450050149685L, -77, -10, false);
        event6 = new FittsTrackEvent(1450050149709L, -80, -15, false);
        event7 = new FittsTrackEvent(1450050149797L, -83, -17, false);
        event8 = new FittsTrackEvent(1450050149927L, -85, -19, true);
        event9 = new FittsTrackEvent(1450050149989L, -85, -19, false);
        Iterable trackEvents2 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7, event8, event9));
        fittsTrackEventRepository.save(trackEvents2);

        //Trackpath 03
        event1 = new FittsTrackEvent(1450050150029L, -84, -20, false);
        event2 = new FittsTrackEvent(1450050150069L, -49, -22, false);
        event3 = new FittsTrackEvent(1450050150094L, -15, -22, false);
        event4 = new FittsTrackEvent(1450050150149L, 64, -22, false);
        event5 = new FittsTrackEvent(1450050150181L, 73, -22, false);
        event6 = new FittsTrackEvent(1450050150653L, 80, -23, true);
        event7 = new FittsTrackEvent(1450050150718L, 80, -23, false);
        Iterable trackEvents3 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7));
        fittsTrackEventRepository.save(trackEvents3);

        //Trackpath 04
        event1 = new FittsTrackEvent(1450050150821L, 79, -22, false);
        event2 = new FittsTrackEvent(1450050150853L, 42, -1, false);
        event3 = new FittsTrackEvent(1450050150861L, 30, 7, false);
        event4 = new FittsTrackEvent(1450050150893L, -15, 48, false);
        event5 = new FittsTrackEvent(1450050150927L, -38, 73, false);
        event6 = new FittsTrackEvent(1450050151028L, -45, 85, false);
        event7 = new FittsTrackEvent(1450050151085L, -45, 89, false);
        event8 = new FittsTrackEvent(1450050151133L, -48, 90, false);
        event9 = new FittsTrackEvent(1450050151184L, -54, 535, false);
        FittsTrackEvent event10 = new FittsTrackEvent(1450050151189L, -54, 91, true);
        FittsTrackEvent event11 = new FittsTrackEvent(1450050151253L, -54, 91, false);
        Iterable trackEvents4 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7, event8, event9, event10, event11));
        fittsTrackEventRepository.save(trackEvents4);

        //Trackpath 05
        event1 = new FittsTrackEvent(1450050151275L, -53, 90, false);
        event2 = new FittsTrackEvent(1450050151301L, -48, 65, false);
        event3 = new FittsTrackEvent(1450050151355L, -27, -11, false);
        event4 = new FittsTrackEvent(1450050151384L, -18, -61, false);
        event5 = new FittsTrackEvent(1450050151396L, -15, -69, false);
        event6 = new FittsTrackEvent(1450050151425L, -13, -74, false);
        event7 = new FittsTrackEvent(1450050151557L, -10, -84, false);
        event8 = new FittsTrackEvent(1450050151670L, 1, -107, true);
        event9 = new FittsTrackEvent(1450050151756L, 1, -107, false);
        Iterable trackEvents5 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7, event8, event9));
        fittsTrackEventRepository.save(trackEvents5);

        //Trackpath 06
        event1 = new FittsTrackEvent(1450050149077L, 4,	-95, false);
        event2 = new FittsTrackEvent(1450050149122L, 24, -24, false);
        event3 = new FittsTrackEvent(1450050149133L, 35, 3, false);
        event4 = new FittsTrackEvent(1450050149149L, 43, 25, false);
        event5 = new FittsTrackEvent(1450050149173L, 56, 47, false);
        event6 = new FittsTrackEvent(1450050149213L, 65, 62, false);
        event7 = new FittsTrackEvent(1450050149381L, 63, 63, false);
        event8 = new FittsTrackEvent(1450050149470L, 59, 73, true);
        event9 = new FittsTrackEvent(1450050149541L, 59, 73, false);
        Iterable trackEvents6 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7, event8, event9));
        fittsTrackEventRepository.save(trackEvents6);

        //Trackpath 07
        event1 = new FittsTrackEvent(1450050149566L, 58, 70, false);
        event2 = new FittsTrackEvent(1450050149605L, 13, 38, false);
        event3 = new FittsTrackEvent(1450050149629L, -34, 16, false);
        event4 = new FittsTrackEvent(1450050149654L, -61, 0, false);
        event5 = new FittsTrackEvent(1450050149685L, -77, -10, false);
        event6 = new FittsTrackEvent(1450050149709L, -80, -15, false);
        event7 = new FittsTrackEvent(1450050149797L, -83, -17, false);
        event8 = new FittsTrackEvent(1450050149927L, -85, -19, true);
        event9 = new FittsTrackEvent(1450050149989L, -85, -19, false);
        Iterable trackEvents7 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7, event8, event9));
        fittsTrackEventRepository.save(trackEvents7);

        //Trackpath 08
        event1 = new FittsTrackEvent(1450050150029L, -84, -20, false);
        event2 = new FittsTrackEvent(1450050150069L, -49, -22, false);
        event3 = new FittsTrackEvent(1450050150094L, -15, -22, false);
        event4 = new FittsTrackEvent(1450050150149L, 64, -22, false);
        event5 = new FittsTrackEvent(1450050150181L, 73, -22, false);
        event6 = new FittsTrackEvent(1450050150653L, 80, -23, true);
        event7 = new FittsTrackEvent(1450050150718L, 80, -23, false);
        Iterable trackEvents8 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7));
        fittsTrackEventRepository.save(trackEvents8);

        //Trackpath 09
        event1 = new FittsTrackEvent(1450050150821L, 79, -22, false);
        event2 = new FittsTrackEvent(1450050150853L, 42, -1, false);
        event3 = new FittsTrackEvent(1450050150861L, 30, 7, false);
        event4 = new FittsTrackEvent(1450050150893L, -15, 48, false);
        event5 = new FittsTrackEvent(1450050150927L, -38, 73, false);
        event6 = new FittsTrackEvent(1450050151028L, -45, 85, false);
        event7 = new FittsTrackEvent(1450050151085L, -45, 89, false);
        event8 = new FittsTrackEvent(1450050151133L, -48, 90, false);
        event9 = new FittsTrackEvent(1450050151184L, -54, 535, false);
        event10 = new FittsTrackEvent(1450050151189L, -54, 91, true);
        event11 = new FittsTrackEvent(1450050151253L, -54, 91, false);
        Iterable trackEvents9 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7, event8, event9, event10, event11));
        fittsTrackEventRepository.save(trackEvents9);

        //Trackpath 10
        event1 = new FittsTrackEvent(1450050151275L, -53, 90, false);
        event2 = new FittsTrackEvent(1450050151301L, -48, 65, false);
        event3 = new FittsTrackEvent(1450050151355L, -27, -11, false);
        event4 = new FittsTrackEvent(1450050151384L, -18, -61, false);
        event5 = new FittsTrackEvent(1450050151396L, -15, -69, false);
        event6 = new FittsTrackEvent(1450050151425L, -13, -74, false);
        event7 = new FittsTrackEvent(1450050151557L, -10, -84, false);
        event8 = new FittsTrackEvent(1450050151670L, 1, -107, true);
        event9 = new FittsTrackEvent(1450050151756L, 1, -107, false);
        Iterable trackEvents10 = new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7, event8, event9));
        fittsTrackEventRepository.save(trackEvents10);

        //Trackpaths
        FittsTrackPath trackPath1 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents1);
        FittsTrackPath trackPath2 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents2);
        FittsTrackPath trackPath3 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents3);
        FittsTrackPath trackPath4 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents4);
        FittsTrackPath trackPath5 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents5);
        FittsTrackPath trackPath6 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents6);
        FittsTrackPath trackPath7 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents7);
        FittsTrackPath trackPath8 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents8);
        FittsTrackPath trackPath9 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents9);
        FittsTrackPath trackPath10 = new FittsTrackPath((List<FittsTrackEvent>)trackEvents10);
        Iterable trackPaths = new ArrayList<>(Arrays.asList(trackPath1, trackPath2, trackPath3, trackPath4, trackPath5, trackPath6, trackPath7, trackPath8, trackPath9, trackPath10));
        fittsTrackPathRepository.save(trackPaths);

        //Stages
        FittsStageResult stage1 = new FittsStageResult(0, Arrays.asList(trackPath1, trackPath2, trackPath3, trackPath4, trackPath5));
        FittsStageResult stage2 = new FittsStageResult(1, Arrays.asList(trackPath6, trackPath7, trackPath8, trackPath9, trackPath10));
        Iterable stages = new ArrayList<>(Arrays.asList(stage1, stage2));
        fittsStageResultRepository.save(stages);

        //Result
        FittsResult result = new FittsResult("result-0", "test01", new Date(), Arrays.asList(stage1, stage2));
        fittsResultRepository.save(result);

        User u1 = userRepository.findByUserName("thomas.huybrechts");
        u1.setResults(Arrays.asList(result));
        userRepository.save(u1);
    }
}
