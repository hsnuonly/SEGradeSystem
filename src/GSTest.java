import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GSTest {

	public GradeSys gradeSys =null;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		gradeSys = new GradeSys();
		Scanner scanner = new Scanner(new File("src/gradeinput.txt"));
		while(scanner.hasNext()) {
			String studentData= scanner.nextLine();
			StudentsGrade studentsGrade = new StudentsGrade(studentData.split(" "));
			gradeSys.add(studentsGrade);
		}
		scanner.close();
	}

	@AfterEach
	void tearDown() throws Exception {
		gradeSys = null;
	}
	
	/** ----------------------------------------------------------------------------------------------------------------------
	Unit test
	testAdd()

	case 1: "104062104 吳宗欣 100 100 100 100 100"
	case 2: "104062103 李宗翰 70 70 60 100 90"
	------------------------------------------------------------------------------------------------------------------------- */

	@Test
	void testAdd() throws Exception{
		String newInput = "104062104 吳宗欣 100 100 100 100 100";
		StudentsGrade newCase = new StudentsGrade(newInput.split(" "));
		assertEquals(false, gradeSys.inList(newCase.getID()));
		gradeSys.add(newCase);
		assertEquals(true, gradeSys.inList(newCase.getID()));
	}
	@Test
	void testAdd2() throws Exception{
		String newInput = "104062103 李宗翰 70 70 60 100 90";
		StudentsGrade newCase = new StudentsGrade(newInput.split(" "));
		assertEquals(false, gradeSys.inList(newCase.getID()));
		gradeSys.add(newCase);
		assertEquals(true, gradeSys.inList(newCase.getID()));
	}
	/** ----------------------------------------------------------------------------------------------------------------------
	Unit test
	testInList()

	case 1: "104062104 "
	case 2: "104062103 "
	------------------------------------------------------------------------------------------------------------------------- */
	@Test
	void testInList() throws Exception{
		Scanner scanner = new Scanner(new File("src/gradeinput.txt"));
		while(scanner.hasNext()) {
			String studentData= scanner.nextLine();
			Long ID = Long.parseLong(studentData.split(" ")[0]);
			assertEquals(true, gradeSys.inList(ID));
		}
		scanner.close();
		assertEquals(false, gradeSys.inList(new Long(104062104)));
	}
	@Test
	void testInList2() throws Exception{
		Scanner scanner = new Scanner(new File("src/gradeinput.txt"));
		while(scanner.hasNext()) {
			String studentData= scanner.nextLine();
			Long ID = Long.parseLong(studentData.split(" ")[0]);
			assertEquals(true, gradeSys.inList(ID));
		}
		scanner.close();
		assertEquals(false, gradeSys.inList(new Long(104062103)));
	}
	/** ----------------------------------------------------------------------------------------------------------------------
	Unit test
	testGetGrade()

	case 1: "src/gradeinput.txt"
	------------------------------------------------------------------------------------------------------------------------- */
	@Test
	void testGetGrade() throws Exception{
		Scanner scanner = new Scanner(new File("src/gradeinput.txt"));
		while(scanner.hasNext()) {
			String input= scanner.nextLine();
			String studentData[] = input.split(" ");
			Long ID = Long.parseLong(studentData[0]);
			int grade[] = new int[5];
			for(int i=0;i<5;i++) {
				grade[i] = Integer.parseInt(studentData[i+2]);
			}
			assertArrayEquals(grade, gradeSys.getGrade(ID));
		}
		scanner.close();
	}
	/** ----------------------------------------------------------------------------------------------------------------------
	Unit test
	testGetName()

	case 1: "src/gradeinput.txt"
	------------------------------------------------------------------------------------------------------------------------- */
	
	@Test
	void testGetName() throws Exception{
		Scanner scanner = new Scanner(new File("src/gradeinput.txt"));
		while(scanner.hasNext()) {
			String input= scanner.nextLine();
			String studentData[] = input.split(" ");
			Long ID = Long.parseLong(studentData[0]);
			assertEquals(studentData[1], gradeSys.getName(ID));
		}
		scanner.close();
	}
	/** ----------------------------------------------------------------------------------------------------------------------
	Unit test
	testGetNumberOfMember()

	case 1: "src/gradeinput.txt"
	------------------------------------------------------------------------------------------------------------------------- */
	@Test
	void testGetNumberOfMember() throws Exception{
		Scanner scanner = new Scanner(new File("src/gradeinput.txt"));
		int i = 0;
		while(scanner.hasNextLine()) {
			scanner.nextLine();
			i++;
		}
		scanner.close();
		assertEquals(i, gradeSys.getNumberOfMember());
	}
	/** ----------------------------------------------------------------------------------------------------------------------
	Unit test
	testGetWeight()

	case 1:{10,10,10,30,40}
	------------------------------------------------------------------------------------------------------------------------- */
	@Test
	void testGetWeight() throws Exception{
		float expect[] = {10,10,10,30,40};
		assertArrayEquals(expect, gradeSys.getWeight());
	}
	/** ----------------------------------------------------------------------------------------------------------------------
	Unit test
	testUpdateWeight()

	case 1:{20,20,20,20,20}
	case 2:{10,20,30,20,20}
	------------------------------------------------------------------------------------------------------------------------- */
	@Test
	void testUpdateWeight() throws Exception{
		float expect[] = {10,10,10,30,40};
		assertArrayEquals(expect, gradeSys.getWeight());
		float newWeight[] = {20,20,20,20,20};
		gradeSys.updateWeight(newWeight);
		assertArrayEquals(newWeight, gradeSys.getWeight());
	}
	@Test
	void testUpdateWeight2() throws Exception{
		float expect[] = {10,10,10,30,40};
		assertArrayEquals(expect, gradeSys.getWeight());
		float newWeight[] = {10,20,30,20,20};
		gradeSys.updateWeight(newWeight);
		assertArrayEquals(newWeight, gradeSys.getWeight());
	}
	/** ----------------------------------------------------------------------------------------------------------------------
	Unit test
	testGetTotal()

	case 1:955002056
	case 2:962001044
	------------------------------------------------------------------------------------------------------------------------- */
	@Test
	void testGetTotal() throws Exception{
		assertEquals(true,gradeSys.getTotal(new Long(955002056))-(float)((0.1*88)+(0.1*92)+(88*0.1)+(0.3*98)+(0.4*91))<0.001);
	}
	@Test
	void testGetTotal2() throws Exception{
		assertEquals(true,gradeSys.getTotal(new Long(962001044))-(float)((0.1*87)+(0.1*86)+(98*0.1)+(0.3*88)+(0.4*87))<0.001);
	}
	/** ----------------------------------------------------------------------------------------------------------------------
	Unit test
	testGetRank()

	case 1:955002056
	case 2:962001044
	------------------------------------------------------------------------------------------------------------------------- */
	@Test
	void testGetRank() throws Exception{
		assertEquals(15, gradeSys.getRank(new Long(955002056)));
	}
	@Test
	void testGetRank2() throws Exception{
		assertEquals(37, gradeSys.getRank(new Long(962001044)));
	}
	/** ----------------------------------------------------------------------------------------------------------------------
	Unit test
	testGetAverage()
	
	case 1: "src/gradeinput.txt"
	------------------------------------------------------------------------------------------------------------------------- */
	
	@Test
	void testGetAverage() throws Exception{
		assertEquals(true, gradeSys.getAverage()-89.46191<0.00001);
	}
	
	/** ----------------------------------------------------------------------------------------------------------------------
	Unit test
	testShowGrades()
	
	case 1: lab1 81 lab2 32 lab3 50 mid-term 90 final exam 93 total 80.5
	case 2: lab1 88 lab2 92 lab3 88 mid-term 98 final exam 91 total 92.6
	------------------------------------------------------------------------------------------------------------------------- */

	@Test
	void testShowGrades() throws Exception{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(stream));
		GradeSys.showGrades(new Long(962001051), gradeSys);
		String expect = "lab1:           81\n" + 
				"lab2:           32*\n" + 
				"lab3:           50*\n" + 
				"mid-term:       90\n" + 
				"final exam:     93\n" + 
				"Total grade:   80.5\n";
		assertEquals(expect, stream.toString());
		stream.reset();
		GradeSys.showGrades(new Long(955002056), gradeSys);
		expect = "lab1:           88\n" + 
				"lab2:           92\n" + 
				"lab3:           88\n" + 
				"mid-term:       98\n" + 
				"final exam:     91\n" + 
				"Total grade:   92.6\n";
		assertEquals(expect, stream.toString());
	}
	/** ----------------------------------------------------------------------------------------------------------------------
	Unit test
	testCaseUpdateWeight()
	
	case 1: 20% 20% 20% 20% 20%
	case 2: 30% 20% 10% 20% 20%
	------------------------------------------------------------------------------------------------------------------------- */
	@Test
	void testCaseUpdateWeight() throws Exception{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(stream));
		Scanner scanner = new Scanner("20\n20\n20\n20\n20\nY\n");
		GradeSys.caseUpdateWeight(scanner, gradeSys);
		String expect = "舊配分\n" + 
				"lab1:           10%\n" + 
				"lab2:           10%\n" + 
				"lab3:           10%\n" + 
				"mid-term:       30%\n" + 
				"final exam:     40%\n" + 
				"輸入新配分\n" + 
				"lab1:          lab2:          lab3:          mid-term:      final exam:    請確認新配分\n" + 
				"lab1:           20%\n" + 
				"lab2:           20%\n" + 
				"lab3:           20%\n" + 
				"mid-term:       20%\n" + 
				"final exam:     20%\n" + 
				"以上正確嗎? Y (Yes) 或 N (No)\n";
		assertEquals(expect, stream.toString());
	}
	@Test
	void testCaseUpdateWeight2() throws Exception{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(stream));
		Scanner scanner = new Scanner("30\n20\n10\n20\n20\nY\n");
		GradeSys.caseUpdateWeight(scanner, gradeSys);
		String expect = "舊配分\n" + 
				"lab1:           10%\n" + 
				"lab2:           10%\n" + 
				"lab3:           10%\n" + 
				"mid-term:       30%\n" + 
				"final exam:     40%\n" + 
				"輸入新配分\n" + 
				"lab1:          lab2:          lab3:          mid-term:      final exam:    請確認新配分\n" + 
				"lab1:           30%\n" + 
				"lab2:           20%\n" + 
				"lab3:           10%\n" + 
				"mid-term:       20%\n" + 
				"final exam:     20%\n" + 
				"以上正確嗎? Y (Yes) 或 N (No)\n";
		assertEquals(expect, stream.toString());
	}
	/** ----------------------------------------------------------------------------------------------------------------------
	Unit test
	testMenu()
	
	case 1: 962001051
	case 2: 955002056
	------------------------------------------------------------------------------------------------------------------------- */
	@Test
	void testMenu1() throws Exception{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(stream));
		Scanner scanner = new Scanner("R\nA\nE\n");
		GradeSys.menu(new Long(962001051), scanner, gradeSys);
		String expect = "輸入指令 :\n" + 
				"1)G 顯示成績\n" + 
				"2)R 顯示排名\n" + 
				"3)A 顯示平均\n" + 
				"4)W 更新配分\n" + 
				"5)E 離開選單\n" + 
				">> 李威廷排名第63/63\n" + 
				"輸入指令 :\n" + 
				"1)G 顯示成績\n" + 
				"2)R 顯示排名\n" + 
				"3)A 顯示平均\n" + 
				"4)W 更新配分\n" + 
				"5)E 離開選單\n" + 
				">> 成績平均為: 89.46191\n" + 
				"輸入指令 :\n" + 
				"1)G 顯示成績\n" + 
				"2)R 顯示排名\n" + 
				"3)A 顯示平均\n" + 
				"4)W 更新配分\n" + 
				"5)E 離開選單\n" + 
				">> ";
		assertEquals(expect, stream.toString());
	}
	
	@Test
	void testMenu2() throws Exception{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(stream));
		Scanner scanner = new Scanner("R\nA\nE\n");
		GradeSys.menu(new Long(955002056), scanner, gradeSys);
		String expect = "輸入指令 :\n" + 
				"1)G 顯示成績\n" + 
				"2)R 顯示排名\n" + 
				"3)A 顯示平均\n" + 
				"4)W 更新配分\n" + 
				"5)E 離開選單\n" + 
				">> 許文馨排名第15/63\n" + 
				"輸入指令 :\n" + 
				"1)G 顯示成績\n" + 
				"2)R 顯示排名\n" + 
				"3)A 顯示平均\n" + 
				"4)W 更新配分\n" + 
				"5)E 離開選單\n" + 
				">> 成績平均為: 89.46191\n" + 
				"輸入指令 :\n" + 
				"1)G 顯示成績\n" + 
				"2)R 顯示排名\n" + 
				"3)A 顯示平均\n" + 
				"4)W 更新配分\n" + 
				"5)E 離開選單\n" + 
				">> ";
		assertEquals(expect, stream.toString());
	}
	/** ----------------------------------------------------------------------------------------------------------------------
	Unit test
	testMenu()
	
	case 1: 0 962001051 E Q
	case 2: k 955002056 E Q
	------------------------------------------------------------------------------------------------------------------------- */
	@Test
	void testLogin() throws Exception{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(stream));
		Scanner scanner = new Scanner("0\n962001051\nE\nQ\n");
		String expect = "輸入ID或Q(結束使用): " + 
				"查無此ID!\n" + 
				"輸入ID或Q(結束使用): " + 
				"Welcome 李威廷\n" + 
				"輸入指令 :\n" + 
				"1)G 顯示成績\n" + 
				"2)R 顯示排名\n" + 
				"3)A 顯示平均\n" + 
				"4)W 更新配分\n" + 
				"5)E 離開選單\n" + 
				">> 輸入ID或Q(結束使用): "+
				"結束程式\n";
		GradeSys.login(scanner, gradeSys);
		assertEquals(expect, stream.toString());
	}
	@Test
	void testLogin2() throws Exception{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(stream));
		Scanner scanner = new Scanner("k\n955002056\nE\nQ\n");
		String expect = "輸入ID或Q(結束使用): " + 
				"輸入格式錯誤！\n" + 
				"輸入ID或Q(結束使用): " + 
				"Welcome 許文馨\n" + 
				"輸入指令 :\n" + 
				"1)G 顯示成績\n" + 
				"2)R 顯示排名\n" + 
				"3)A 顯示平均\n" + 
				"4)W 更新配分\n" + 
				"5)E 離開選單\n" + 
				">> 輸入ID或Q(結束使用): "+
				"結束程式\n";
		GradeSys.login(scanner, gradeSys);
		assertEquals(expect, stream.toString());
	}
	/** ----------------------------------------------------------------------------------------------------------------------
	Unit test
	integrationTest()
	
	case: (0) wrong input
		  (1) add new student "104062104 吳宗欣 100 100 100 100 100"
		  (2) enter UI, login 104062104
		  (3) check grade, rank, average
		  (4) update weight
		  (5) check grade, rank, average
		  (6) exit
	------------------------------------------------------------------------------------------------------------------------- */

    	@Test
	void integrationTest() throws Exception{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(stream));
		
		String newInput = "104062104 吳宗欣 100 100 100 100 100";
		StudentsGrade newStudent = new StudentsGrade(newInput.split(" "));
		gradeSys.add(newStudent);
		
		Scanner scanner = new Scanner("104062104\n"
				+ "G\nR\nA\nW\n"
				+ "30\n20\n10\n20\n20\nY\n"
				+ "G\nR\nA\n"
				+ "E\n"
				+ "Q\n");
		String expect = "輸入ID或Q(結束使用): Welcome 吳宗欣\n" + 
				"輸入指令 :\n" + 
				"1)G 顯示成績\n" + 
				"2)R 顯示排名\n" + 
				"3)A 顯示平均\n" + 
				"4)W 更新配分\n" + 
				"5)E 離開選單\n" + 
				">> lab1:          100\n" + 
				"lab2:          100\n" + 
				"lab3:          100\n" + 
				"mid-term:      100\n" + 
				"final exam:    100\n" + 
				"Total grade:   100.0\n" + 
				"輸入指令 :\n" + 
				"1)G 顯示成績\n" + 
				"2)R 顯示排名\n" + 
				"3)A 顯示平均\n" + 
				"4)W 更新配分\n" + 
				"5)E 離開選單\n" + 
				">> 吳宗欣排名第1/64\n" + 
				"輸入指令 :\n" + 
				"1)G 顯示成績\n" + 
				"2)R 顯示排名\n" + 
				"3)A 顯示平均\n" + 
				"4)W 更新配分\n" + 
				"5)E 離開選單\n" + 
				">> 成績平均為: 89.626564\n" + 
				"輸入指令 :\n" + 
				"1)G 顯示成績\n" + 
				"2)R 顯示排名\n" + 
				"3)A 顯示平均\n" + 
				"4)W 更新配分\n" + 
				"5)E 離開選單\n" + 
				">> 舊配分\n" + 
				"lab1:           10%\n" + 
				"lab2:           10%\n" + 
				"lab3:           10%\n" + 
				"mid-term:       30%\n" + 
				"final exam:     40%\n" + 
				"輸入新配分\n" + 
				"lab1:          lab2:          lab3:          mid-term:      final exam:    請確認新配分\n" + 
				"lab1:           30%\n" + 
				"lab2:           20%\n" + 
				"lab3:           10%\n" + 
				"mid-term:       20%\n" + 
				"final exam:     20%\n" + 
				"以上正確嗎? Y (Yes) 或 N (No)\n" + 
				"輸入指令 :\n" + 
				"1)G 顯示成績\n" + 
				"2)R 顯示排名\n" + 
				"3)A 顯示平均\n" + 
				"4)W 更新配分\n" + 
				"5)E 離開選單\n" + 
				">> lab1:          100\n" + 
				"lab2:          100\n" + 
				"lab3:          100\n" + 
				"mid-term:      100\n" + 
				"final exam:    100\n" + 
				"Total grade:   100.0\n" + 
				"輸入指令 :\n" + 
				"1)G 顯示成績\n" + 
				"2)R 顯示排名\n" + 
				"3)A 顯示平均\n" + 
				"4)W 更新配分\n" + 
				"5)E 離開選單\n" + 
				">> 吳宗欣排名第1/64\n" + 
				"輸入指令 :\n" + 
				"1)G 顯示成績\n" + 
				"2)R 顯示排名\n" + 
				"3)A 顯示平均\n" + 
				"4)W 更新配分\n" + 
				"5)E 離開選單\n" + 
				">> 成績平均為: 89.56405\n" + 
				"輸入指令 :\n" + 
				"1)G 顯示成績\n" + 
				"2)R 顯示排名\n" + 
				"3)A 顯示平均\n" + 
				"4)W 更新配分\n" + 
				"5)E 離開選單\n" + 
				">> 輸入ID或Q(結束使用): 結束程式\n" + 
				"";
		GradeSys.login(scanner, gradeSys);
		assertEquals(expect, stream.toString());
	}
	

}
