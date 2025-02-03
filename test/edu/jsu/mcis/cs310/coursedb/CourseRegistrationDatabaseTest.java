package edu.jsu.mcis.cs310.coursedb;

import edu.jsu.mcis.cs310.coursedb.dao.*;
import org.junit.*;
import static org.junit.Assert.*;
import com.github.cliftonlabs.json_simple.*;

public class CourseRegistrationDatabaseTest {

    private static final String USERNAME = "nobody@jsu.edu";
    
    private DAOFactory daoFactory;
    private RegistrationDAO registrationDao;
    private SectionDAO sectionDao;
    private int studentid;
    
    private final String s1 = "[{\"studentid\":\"1\",\"termid\":\"1\",\"crn\":\"10520\"}]";
    private final String s2 = "[{\"studentid\":\"1\",\"termid\":\"1\",\"crn\":\"10520\"},{\"studentid\":\"1\",\"termid\":\"1\",\"crn\":\"10618\"},{\"studentid\":\"1\",\"termid\":\"1\",\"crn\":\"10796\"},{\"studentid\":\"1\",\"termid\":\"1\",\"crn\":\"12074\"}]";
    private final String s3 = "[{\"studentid\":\"1\",\"termid\":\"1\",\"crn\":\"10520\"},{\"studentid\":\"1\",\"termid\":\"1\",\"crn\":\"10618\"},{\"studentid\":\"1\",\"termid\":\"1\",\"crn\":\"10796\"},{\"studentid\":\"1\",\"termid\":\"1\",\"crn\":\"12074\"},{\"studentid\":\"1\",\"termid\":\"1\",\"crn\":\"12156\"}]";
    private final String s4 = "[{\"studentid\":\"1\",\"termid\":\"1\",\"crn\":\"10520\"},{\"studentid\":\"1\",\"termid\":\"1\",\"crn\":\"10618\"},{\"studentid\":\"1\",\"termid\":\"1\",\"crn\":\"10796\"},{\"studentid\":\"1\",\"termid\":\"1\",\"crn\":\"12074\"},{\"studentid\":\"1\",\"termid\":\"1\",\"crn\":\"12156\"}]";
    private final String s5 = "[]";
    
    private final String s6 = "[{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Cynthia Gunter Jensen\",\"num\":\"201\",\"start\":\"08:45:00\",\"days\":\"MWF\",\"section\":\"001\",\"end\":\"09:45:00\",\"where\":\"Ayers Hall 357\",\"crn\":\"10559\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Scarlet Jadzia Maddox, Cynthia Gunter Jensen\",\"num\":\"201\",\"start\":\"10:00:00\",\"days\":\"MWF\",\"section\":\"002\",\"end\":\"11:00:00\",\"where\":\"Ayers Hall 359\",\"crn\":\"10560\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Cynthia Gunter Jensen\",\"num\":\"201\",\"start\":\"13:45:00\",\"days\":\"MWF\",\"section\":\"005\",\"end\":\"14:45:00\",\"where\":\"Ayers Hall 359\",\"crn\":\"10563\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Scarlet Jadzia Maddox, Cynthia Gunter Jensen\",\"num\":\"201\",\"start\":\"09:15:00\",\"days\":\"TR\",\"section\":\"006\",\"end\":\"10:45:00\",\"where\":\"Ayers Hall 363\",\"crn\":\"10564\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Cynthia Gunter Jensen\",\"num\":\"201\",\"start\":\"11:00:00\",\"days\":\"TR\",\"section\":\"007\",\"end\":\"12:30:00\",\"where\":\"Ayers Hall 359\",\"crn\":\"10565\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Cynthia Gunter Jensen\",\"num\":\"201\",\"start\":\"14:30:00\",\"days\":\"TR\",\"section\":\"010\",\"end\":\"16:00:00\",\"where\":\"Ayers Hall 363\",\"crn\":\"10568\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Thomas D White, Cynthia Gunter Jensen\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"011\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"10569\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Thomas D White, Cynthia Gunter Jensen\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"012\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"10570\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Shreyashee Ghosh, Cynthia Gunter Jensen\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"014\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"10572\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Shreyashee Ghosh, Cynthia Gunter Jensen\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"015\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"10573\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Grant K Greenwood, Cynthia Gunter Jensen\",\"num\":\"201\",\"start\":\"18:15:00\",\"days\":\"MW\",\"section\":\"016\",\"end\":\"19:45:00\",\"where\":\"Ayers Hall 359\",\"crn\":\"10574\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Cynthia Gunter Jensen\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"017\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"10575\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Scarlet Jadzia Maddox, Cynthia Gunter Jensen\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"018\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"11047\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Shreyashee Ghosh, Cynthia Gunter Jensen\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"020\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"12985\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Shreyashee Ghosh, Cynthia Gunter Jensen\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"021\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"13083\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Karen Bellman Lowe, Cynthia Gunter Jensen\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"022\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"13084\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Karen Bellman Lowe, Cynthia Gunter Jensen\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"023\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"13107\",\"subjectid\":\"CS\"}]";
    private final String s7 = "[{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Arup Kumar Ghosh\",\"num\":\"230\",\"start\":\"11:15:00\",\"days\":\"MWF\",\"section\":\"001\",\"end\":\"12:15:00\",\"where\":\"Ayers Hall 357\",\"crn\":\"10520\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Keith Bradley Foster\",\"num\":\"230\",\"start\":\"09:15:00\",\"days\":\"TR\",\"section\":\"002\",\"end\":\"10:45:00\",\"where\":\"Ayers Hall 357\",\"crn\":\"10521\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Scarlet Jadzia Maddox\",\"num\":\"230\",\"start\":\"13:45:00\",\"days\":\"MWF\",\"section\":\"003\",\"end\":\"14:45:00\",\"where\":\"Ayers Hall 355\",\"crn\":\"10522\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Scarlet Jadzia Maddox\",\"num\":\"230\",\"start\":\"12:45:00\",\"days\":\"TR\",\"section\":\"004\",\"end\":\"14:15:00\",\"where\":\"Ayers Hall 359\",\"crn\":\"10523\",\"subjectid\":\"CS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Keith Bradley Foster\",\"num\":\"230\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"005\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"10524\",\"subjectid\":\"CS\"}]";
    private final String s8 = "[{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Marcus L Shell\",\"num\":\"112\",\"start\":\"11:15:00\",\"days\":\"MWF\",\"section\":\"004\",\"end\":\"12:15:00\",\"where\":\"Ayers Hall 220\",\"crn\":\"10777\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Christopher Ray Boles\",\"num\":\"112\",\"start\":\"10:00:00\",\"days\":\"MWF\",\"section\":\"003\",\"end\":\"11:00:00\",\"where\":\"Ayers Hall 216\",\"crn\":\"10780\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"James Mitchell Jensen\",\"num\":\"112\",\"start\":\"13:45:00\",\"days\":\"MW\",\"section\":\"007\",\"end\":\"15:15:00\",\"where\":\"Ayers Hall 218\",\"crn\":\"10781\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Trenton Michael Townsend\",\"num\":\"112\",\"start\":\"16:30:00\",\"days\":\"MW\",\"section\":\"008\",\"end\":\"18:00:00\",\"where\":\"Ayers Hall 114\",\"crn\":\"10782\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Heather Black McDivitt\",\"num\":\"112\",\"start\":\"09:15:00\",\"days\":\"TR\",\"section\":\"009\",\"end\":\"10:45:00\",\"where\":\"Ayers Hall 114\",\"crn\":\"10783\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Tasha T Medellin\",\"num\":\"112\",\"start\":\"09:15:00\",\"days\":\"TR\",\"section\":\"010\",\"end\":\"10:45:00\",\"where\":\"Ayers Hall 214\",\"crn\":\"10784\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Heather Black McDivitt\",\"num\":\"112\",\"start\":\"11:00:00\",\"days\":\"TR\",\"section\":\"011\",\"end\":\"12:30:00\",\"where\":\"Ayers Hall 114\",\"crn\":\"10785\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Tasha T Medellin\",\"num\":\"112\",\"start\":\"11:00:00\",\"days\":\"TR\",\"section\":\"012\",\"end\":\"12:30:00\",\"where\":\"Ayers Hall 214\",\"crn\":\"10786\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Darius Williams\",\"num\":\"112\",\"start\":\"12:45:00\",\"days\":\"TR\",\"section\":\"013\",\"end\":\"14:15:00\",\"where\":\"Ayers Hall 216\",\"crn\":\"10787\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Jessica Warren Bentley\",\"num\":\"112\",\"start\":\"12:45:00\",\"days\":\"TR\",\"section\":\"014\",\"end\":\"14:15:00\",\"where\":\"Ayers Hall 118\",\"crn\":\"10788\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Darius Williams\",\"num\":\"112\",\"start\":\"14:30:00\",\"days\":\"TR\",\"section\":\"015\",\"end\":\"16:00:00\",\"where\":\"Ayers Hall 216\",\"crn\":\"10789\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Jessica Warren Bentley\",\"num\":\"112\",\"start\":\"14:30:00\",\"days\":\"TR\",\"section\":\"016\",\"end\":\"16:00:00\",\"where\":\"Ayers Hall 118\",\"crn\":\"10790\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Trenton Michael Townsend\",\"num\":\"112\",\"start\":\"16:30:00\",\"days\":\"TR\",\"section\":\"017\",\"end\":\"18:00:00\",\"where\":\"Ayers Hall 118\",\"crn\":\"10791\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Heather Black McDivitt\",\"num\":\"112\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"018\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"10792\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Darius Williams\",\"num\":\"112\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"019\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"10793\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Trenton Michael Townsend\",\"num\":\"112\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"020\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"10794\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Gene Heaton Burchfield\",\"num\":\"112\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"021\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"10795\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Stacy M Land\",\"num\":\"112\",\"start\":\"00:00:00\",\"days\":\"TBA\",\"section\":\"200\",\"end\":\"00:00:00\",\"where\":\"Alexandria High School\",\"crn\":\"12561\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Amy Williams Brown\",\"num\":\"112\",\"start\":\"00:00:00\",\"days\":\"TBA\",\"section\":\"202\",\"end\":\"00:00:00\",\"where\":\"Cleburne County High School\",\"crn\":\"12563\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Clint Adam Stanley\",\"num\":\"112\",\"start\":\"00:00:00\",\"days\":\"TBA\",\"section\":\"203\",\"end\":\"00:00:00\",\"where\":\"Faith Christian School\",\"crn\":\"12564\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Susan Gail Spurlin\",\"num\":\"112\",\"start\":\"00:00:00\",\"days\":\"TBA\",\"section\":\"204\",\"end\":\"00:00:00\",\"where\":\"Ohatchee High School\",\"crn\":\"12565\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Jennifer Danielle Singleton\",\"num\":\"112\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"205\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"12567\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Elaine Michelle Page\",\"num\":\"112\",\"start\":\"00:00:00\",\"days\":\"TBA\",\"section\":\"206\",\"end\":\"00:00:00\",\"where\":\"Pleasant Valley High School\",\"crn\":\"12568\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Jimmy Dean Roebuck\",\"num\":\"112\",\"start\":\"00:00:00\",\"days\":\"TBA\",\"section\":\"207\",\"end\":\"00:00:00\",\"where\":\"Weaver High School\",\"crn\":\"12569\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Kerry Anne Shaw\",\"num\":\"112\",\"start\":\"00:00:00\",\"days\":\"TBA\",\"section\":\"208\",\"end\":\"00:00:00\",\"where\":\"White Plains High School\",\"crn\":\"12570\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Deedee S Henderson\",\"num\":\"112\",\"start\":\"00:00:00\",\"days\":\"TBA\",\"section\":\"210\",\"end\":\"00:00:00\",\"where\":\"Donoho High School\",\"crn\":\"12573\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Christopher Devan Totherow\",\"num\":\"112\",\"start\":\"00:00:00\",\"days\":\"TBA\",\"section\":\"211\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"12574\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Jennifer Danielle Singleton\",\"num\":\"112\",\"start\":\"00:00:00\",\"days\":\"TBA\",\"section\":\"212\",\"end\":\"00:00:00\",\"where\":\"Oxford High School\",\"crn\":\"12575\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Suzanne Rinn Batchelor\",\"num\":\"112\",\"start\":\"00:00:00\",\"days\":\"TBA\",\"section\":\"214\",\"end\":\"00:00:00\",\"where\":\"Saks High School\",\"crn\":\"12669\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Sharon Padgett\",\"num\":\"112\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"022\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"13046\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Michelle Dawn Sanders\",\"num\":\"112\",\"start\":\"00:00:00\",\"days\":\"TBA\",\"section\":\"215\",\"end\":\"00:00:00\",\"where\":\"Southside High School\",\"crn\":\"13056\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Kevin Lockridge\",\"num\":\"112\",\"start\":\"00:00:00\",\"days\":\"TBA\",\"section\":\"216\",\"end\":\"00:00:00\",\"where\":\"Wellborn High School\",\"crn\":\"13746\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Sharon Padgett\",\"num\":\"112\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"023\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"13886\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Marcus L Shell\",\"num\":\"112\",\"start\":\"08:45:00\",\"days\":\"MWF\",\"section\":\"001\",\"end\":\"09:45:00\",\"where\":\"Ayers Hall 220\",\"crn\":\"14009\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Marcus L Shell\",\"num\":\"112\",\"start\":\"10:00:00\",\"days\":\"MWF\",\"section\":\"002\",\"end\":\"11:00:00\",\"where\":\"Ayers Hall 220\",\"crn\":\"14010\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"BL2\",\"instructor\":\"TBA\",\"num\":\"112\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"005\",\"end\":\"00:00:00\",\"where\":\"Ayers Hall 214\",\"crn\":\"14093\",\"subjectid\":\"MS\"},{\"termid\":\"1\",\"scheduletypeid\":\"BL2\",\"instructor\":\"TBA\",\"num\":\"112\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"006\",\"end\":\"00:00:00\",\"where\":\"Ayers Hall 214\",\"crn\":\"14096\",\"subjectid\":\"MS\"}]";
    private final String s9 = "[{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Cathy Glover Burrows\",\"num\":\"201\",\"start\":\"08:45:00\",\"days\":\"MWF\",\"section\":\"002\",\"end\":\"09:45:00\",\"where\":\"Stone Center 330\",\"crn\":\"12141\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Tamara Jo Levi\",\"num\":\"201\",\"start\":\"10:00:00\",\"days\":\"MWF\",\"section\":\"003\",\"end\":\"11:00:00\",\"where\":\"Stone Center 326\",\"crn\":\"12142\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Jennifer L Gross\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"010\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"12183\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Cathy Glover Burrows\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"013\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"12188\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"TBA\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"202\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"12615\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Brandon Gilliland\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"203\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"12616\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Lindsay Ford\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"TBA\",\"section\":\"204\",\"end\":\"00:00:00\",\"where\":\"Ohatchee High School\",\"crn\":\"12617\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Jerrell D Burns\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"TBA\",\"section\":\"205\",\"end\":\"00:00:00\",\"where\":\"Wadley High School\",\"crn\":\"12618\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Eboney Joelle Whitney Lewis\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"TBA\",\"section\":\"206\",\"end\":\"00:00:00\",\"where\":\"Weaver High School\",\"crn\":\"12619\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"James Robert Fleming\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"207\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"12620\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Cathy Glover Burrows\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"209\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"12622\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Barrett Rodney Ragsdale\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"210\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"12623\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"TBA\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"211\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"12624\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"George Allan Mauldin\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"TBA\",\"section\":\"212\",\"end\":\"00:00:00\",\"where\":\"Piedmont High School\",\"crn\":\"12666\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Jennifer L Gross\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"016\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"12784\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Barrett Rodney Ragsdale\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"213\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"12877\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Emily Renea Dewberry\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"TBA\",\"section\":\"214\",\"end\":\"00:00:00\",\"where\":\"Donoho High School\",\"crn\":\"12885\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Emily Renea Dewberry\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"215\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"13014\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Cathy Glover Burrows\",\"num\":\"201\",\"start\":\"10:00:00\",\"days\":\"MWF\",\"section\":\"024\",\"end\":\"11:00:00\",\"where\":\"Stone Center 330\",\"crn\":\"13458\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Paul Richard Beezley\",\"num\":\"201\",\"start\":\"13:45:00\",\"days\":\"MW\",\"section\":\"014\",\"end\":\"15:15:00\",\"where\":\"TBA\",\"crn\":\"13493\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Neale Morgan\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"TBA\",\"section\":\"216\",\"end\":\"00:00:00\",\"where\":\"Cleburne County High School\",\"crn\":\"13585\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Daniel S Harris\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"TBA\",\"section\":\"217\",\"end\":\"00:00:00\",\"where\":\"Sylacauga High School\",\"crn\":\"13672\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Cathy Glover Burrows\",\"num\":\"201\",\"start\":\"11:15:00\",\"days\":\"MWF\",\"section\":\"028\",\"end\":\"12:15:00\",\"where\":\"Stone Center 330\",\"crn\":\"13765\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Austin H Kilgore\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"TBA\",\"section\":\"218\",\"end\":\"00:00:00\",\"where\":\"Munford High School\",\"crn\":\"13768\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Richard Allan Dobbs\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"031\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"13781\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Seth Taylor O'Neal\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"219\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"13832\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"TBA\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"220\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"13873\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"TBA\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"221\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"13879\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Wesley Reid Bishop\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"001\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"14103\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Wesley Reid Bishop\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"004\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"14104\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"LEC\",\"instructor\":\"Tamara Jo Levi\",\"num\":\"201\",\"start\":\"11:15:00\",\"days\":\"MWF\",\"section\":\"005\",\"end\":\"12:15:00\",\"where\":\"Stone Center 326\",\"crn\":\"14108\",\"subjectid\":\"HY\"},{\"termid\":\"1\",\"scheduletypeid\":\"ONL\",\"instructor\":\"Gordon Harvey\",\"num\":\"201\",\"start\":\"00:00:00\",\"days\":\"\",\"section\":\"006\",\"end\":\"00:00:00\",\"where\":\"Online\",\"crn\":\"14214\",\"subjectid\":\"HY\"}]";
    private final String s10 = "[]";
    
    @Before
    public void setUp() {
        
        daoFactory = new DAOFactory("coursedb");
        
        registrationDao = daoFactory.getRegistrationDAO();
        sectionDao = daoFactory.getSectionDAO();
        
        studentid = daoFactory.getStudentDAO().find(USERNAME);
        
    }
    
    @Test
    public void testRegisterSingle() {
        
        try {
        
            JsonArray r1 = (JsonArray)Jsoner.deserialize(s1);

            // clear schedule (withdraw all classes)

            registrationDao.delete(studentid, DAOUtility.TERMID_FA24);

            // register for one course

            boolean result = registrationDao.create(studentid, DAOUtility.TERMID_FA24, 10520);

            // compare number of updated records

            assertTrue(result);

            // compare schedule

            assertEquals(r1, (JsonArray)Jsoner.deserialize(registrationDao.list(studentid, DAOUtility.TERMID_FA24)));
            
        }
        catch (Exception e) { e.printStackTrace(); }
        
    }
    
    @Test
    public void testRegisterMultiple() {
        
        try {
        
            JsonArray r2 = (JsonArray)Jsoner.deserialize(s2);

            int[] crn = {10520, 12074, 10796, 10618};

            // clear schedule (withdraw all classes)

            registrationDao.delete(studentid, DAOUtility.TERMID_FA24);

            // register for multiple courses

            for (int i = 0; i < crn.length; ++i) {

                // add next course

                boolean result = registrationDao.create(studentid, DAOUtility.TERMID_FA24, crn[i]);

                // compare number of updated records

                assertTrue(result);

            }

            // compare schedule

            JsonArray t1 = (JsonArray)Jsoner.deserialize(registrationDao.list(studentid, DAOUtility.TERMID_FA24));
            assertEquals(4, t1.size());
            assertEquals(r2, t1);
            
        }
        catch (Exception e) { e.printStackTrace(); }
        
    }
    
    @Test
    public void testDropSingle() {
        
        try {
        
            JsonArray r3 = (JsonArray)Jsoner.deserialize(s3);

            int[] crn = {10568, 10520, 12074, 10796, 10618, 12156};

            // clear schedule (withdraw all classes)

            registrationDao.delete(studentid, DAOUtility.TERMID_FA24);

            // register for multiple courses

            for (int i = 0; i < crn.length; ++i) {

                // add next course

                boolean result = registrationDao.create(studentid, DAOUtility.TERMID_FA24, crn[i]);

                // compare number of updated records

                assertTrue(result);

            }
            
            // drop individual course
            
            boolean result = registrationDao.delete(studentid, DAOUtility.TERMID_FA24, 10568);
            
            assertTrue(result);

            // compare schedule

            JsonArray t1 = (JsonArray)Jsoner.deserialize(registrationDao.list(studentid, DAOUtility.TERMID_FA24));
            assertEquals(5, t1.size());
            assertEquals(r3, t1);
            
        }
        catch (Exception e) { e.printStackTrace(); }
        
    }
    
    @Test
    public void testDropMultiple() {
        
        try {
        
            JsonArray r4 = (JsonArray)Jsoner.deserialize(s4);

            int[] crn = {10568, 10520, 12074, 10796, 10618, 12156, 12615};

            // clear schedule (withdraw all classes)

            registrationDao.delete(studentid, DAOUtility.TERMID_FA24);

            // register for multiple courses

            for (int i = 0; i < crn.length; ++i) {

                // add next course

                boolean result = registrationDao.create(studentid, DAOUtility.TERMID_FA24, crn[i]);

                // compare number of updated records

                assertTrue(result);

            }
            
            // drop multiple courses
            
            boolean result = registrationDao.delete(studentid, DAOUtility.TERMID_FA24, 10568);
            
            assertTrue(result);
            
            result = registrationDao.delete(studentid, DAOUtility.TERMID_FA24, 12615);
            
            assertTrue(result);

            // compare schedule

            JsonArray t1 = (JsonArray)Jsoner.deserialize(registrationDao.list(studentid, DAOUtility.TERMID_FA24));
            assertEquals(5, t1.size());
            assertEquals(r4, t1);
            
        }
        catch (Exception e) { e.printStackTrace(); }
        
    }
    
    @Test
    public void testWithdraw() {
        
        try {
        
            JsonArray r5 = (JsonArray)Jsoner.deserialize(s5);

            int[] crn = {10568, 10520, 12074, 10796, 10618, 12156};

            // clear schedule (withdraw all classes)

            registrationDao.delete(studentid, DAOUtility.TERMID_FA24);

            // register for multiple courses

            for (int i = 0; i < crn.length; ++i) {

                // add next course

                boolean result = registrationDao.create(studentid, DAOUtility.TERMID_FA24, crn[i]);

                // compare number of updated records

                assertTrue(result);

            }

            // clear schedule (withdraw all classes)

            boolean result = registrationDao.delete(studentid, DAOUtility.TERMID_FA24);

            // check withdrawal

            assertTrue(result);

            // compare schedule

            JsonArray t1 = (JsonArray)Jsoner.deserialize(registrationDao.list(studentid, DAOUtility.TERMID_FA24));
            assertEquals(0, t1.size());
            assertEquals(r5, t1);
            
        }
        catch (Exception e) { e.printStackTrace(); }
        
    }
    
    @Test
    public void testGetSections() {
        
        try {
        
            JsonArray r6 = (JsonArray)Jsoner.deserialize(s6);
            JsonArray r7 = (JsonArray)Jsoner.deserialize(s7);
            JsonArray r8 = (JsonArray)Jsoner.deserialize(s8);
            JsonArray r9 = (JsonArray)Jsoner.deserialize(s9);
            JsonArray r10 = (JsonArray)Jsoner.deserialize(s10);

            // get sections; compare number of registered sections and JSON data

            // CS 201

            JsonArray t1 = (JsonArray)Jsoner.deserialize(sectionDao.find(1, "CS", "201"));
            assertEquals(17, t1.size());
            assertEquals(r6, t1);

            // CS 230

            JsonArray t2 = (JsonArray)Jsoner.deserialize(sectionDao.find(1, "CS", "230"));
            assertEquals(5, t2.size());
            assertEquals(r7, t2);

            // MS 112

            JsonArray t3 = (JsonArray)Jsoner.deserialize(sectionDao.find(1, "MS", "112"));
            assertEquals(37, t3.size());
            assertEquals(r8, t3);

            // HY 201

            JsonArray t4 = (JsonArray)Jsoner.deserialize(sectionDao.find(1, "HY", "201"));
            assertEquals(32, t4.size());
            assertEquals(r9, t4);

            // PHS 212 (should be empty)

            JsonArray t5 = (JsonArray)Jsoner.deserialize(sectionDao.find(1, "PHS", "212"));
            assertEquals(0, t5.size());
            assertEquals(r10, t5);
            
        }
        catch (Exception e) { e.printStackTrace(); }
        
    }
    
}