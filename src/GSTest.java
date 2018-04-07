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
		Scanner scanner = new Scanner(new File("src/simple.txt"));
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
	
	@Test
	void testAdd() throws Exception{
		String newInput = "104062104 吳宗欣 100 100 100 100 100";
		StudentsGrade newCase = new StudentsGrade(newInput.split(" "));
		assertEquals(false, gradeSys.inList(newCase.getID()));
		gradeSys.add(newCase);
		assertEquals(true, gradeSys.inList(newCase.getID()));
	}
	
	@Test
	void testInList() throws Exception{
		Scanner scanner = new Scanner(new File("src/simple.txt"));
		while(scanner.hasNext()) {
			String studentData= scanner.nextLine();
			Long ID = Long.parseLong(studentData.split(" ")[0]);
			assertEquals(true, gradeSys.inList(ID));
		}
		scanner.close();
		assertEquals(false, gradeSys.inList(new Long(104062104)));
	}
	
	@Test
	void testGetGrade() throws Exception{
		Scanner scanner = new Scanner(new File("src/simple.txt"));
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
	
	
	@Test
	void testGetName() throws Exception{
		Scanner scanner = new Scanner(new File("src/simple.txt"));
		while(scanner.hasNext()) {
			String input= scanner.nextLine();
			String studentData[] = input.split(" ");
			Long ID = Long.parseLong(studentData[0]);
			assertEquals(studentData[1], gradeSys.getName(ID));
		}
		scanner.close();
	}
	
	@Test
	void testGetNumberOfMember() throws Exception{
		Scanner scanner = new Scanner(new File("src/simple.txt"));
		int i = 0;
		while(scanner.hasNextLine()) {
			scanner.nextLine();
			i++;
		}
		scanner.close();
		assertEquals(i, gradeSys.getNumberOfMember());
	}

	@Test
	void testGetWeight() throws Exception{
		float expect[] = {10,10,10,30,40};
		assertArrayEquals(expect, gradeSys.getWeight());
	}
	
	@Test
	void testUpdateWeight() throws Exception{
		float expect[] = {10,10,10,30,40};
		assertArrayEquals(expect, gradeSys.getWeight());
		float newWeight[] = {20,20,20,20,20};
		gradeSys.updateWeight(newWeight);
		assertArrayEquals(newWeight, gradeSys.getWeight());
	}
	
	@Test
	void testGetTotal() throws Exception{
		Scanner scanner = new Scanner(new File("src/simple.txt"));
		while(scanner.hasNext()) {
			String input= scanner.nextLine();
			String studentData[] = input.split(" ");
			Long ID = Long.parseLong(studentData[0]);
			int grade[] = new int[5];
			for(int i=0;i<5;i++) {
				grade[i] = Integer.parseInt(studentData[i+2]);
			}
			float weight[] = gradeSys.getWeight();
			float total = 0;
			for(int i=0;i<weight.length;i++) {
				total+=weight[i]*(float)grade[i]/100;
			}
			if(Math.abs(total-gradeSys.getTotal(ID))>0.01)fail("");
		}
		scanner.close();
	}
	
	@Test
	void testGetRank() throws Exception{
		Long studentArray[] = new Long[gradeSys.getNumberOfMember()];
		Scanner scanner = new Scanner(new File("src/simple.txt"));
		while(scanner.hasNext()) {
			String input= scanner.nextLine();
			String studentData[] = input.split(" ");
			Long ID = Long.parseLong(studentData[0]);
			studentArray[gradeSys.getRank(ID)-1] = ID;
		}
		scanner.close();
		for(int i=0;i<studentArray.length-1;i++) {
			assertEquals(true, gradeSys.getTotal(studentArray[i])>=gradeSys.getTotal(studentArray[i+1]));
		}
	}
	
	@Test
	void testGetAverage() throws Exception{
		Scanner scanner = new Scanner(new File("src/simple.txt"));
		float total = 0;
		float numOfMembers = 0;
		while(scanner.hasNext()) {
			String input= scanner.nextLine();
			String studentData[] = input.split(" ");
			Long ID = Long.parseLong(studentData[0]);
			int grade[] = new int[5];
			for(int i=0;i<5;i++) {
				grade[i] = Integer.parseInt(studentData[i+2]);
			}
			float weight[] = {10,10,10,30,40};
			for(int i=0;i<weight.length;i++) {
				total+=weight[i]*(float)grade[i]/100;
			}
			numOfMembers++;
		}
		scanner.close();
		if(Math.abs(total/numOfMembers-gradeSys.getAverage())>0.01)fail("");
	}
	

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
	
	@Test
	void testCaseUpdateWeight() throws Exception{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(stream));
		Scanner scanner = new Scanner("20\n20\n20\n20\n20\nY\n");
		GradeSys.caseUpdateWeight(scanner, gradeSys);
		String expect = "舊配分\r\n" + 
				"lab1:           10%\n" + 
				"lab2:           10%\n" + 
				"lab3:           10%\n" + 
				"mid-term:       30%\n" + 
				"final exam:     40%\n" + 
				"輸入新配分\r\n" + 
				"lab1:          lab2:          lab3:          mid-term:      final exam:    請確認新配分\r\n" + 
				"lab1:           20%\n" + 
				"lab2:           20%\n" + 
				"lab3:           20%\n" + 
				"mid-term:       20%\n" + 
				"final exam:     20%\n" + 
				"以上正確嗎? Y (Yes) 或 N (No)\r\n";
		assertEquals(expect, stream.toString());
	}
	
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
				">> 李威廷排名第63/63\r\n" + 
				"輸入指令 :\n" + 
				"1)G 顯示成績\n" + 
				"2)R 顯示排名\n" + 
				"3)A 顯示平均\n" + 
				"4)W 更新配分\n" + 
				"5)E 離開選單\n" + 
				">> 成績平均為: 89.46191\r\n" + 
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
				">> 許文馨排名第15/63\r\n" + 
				"輸入指令 :\n" + 
				"1)G 顯示成績\n" + 
				"2)R 顯示排名\n" + 
				"3)A 顯示平均\n" + 
				"4)W 更新配分\n" + 
				"5)E 離開選單\n" + 
				">> 成績平均為: 89.46191\r\n" + 
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
	void testLogin() throws Exception{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(stream));
		Scanner scanner = new Scanner("0\n962001051\nE\nQ\n");
		String expect = "輸入ID或Q(結束使用): " + 
				"查無此ID!\r\n" + 
				"輸入ID或Q(結束使用): " + 
				"Welcome 李威廷\r\n" + 
				"輸入指令 :\n" + 
				"1)G 顯示成績\n" + 
				"2)R 顯示排名\n" + 
				"3)A 顯示平均\n" + 
				"4)W 更新配分\n" + 
				"5)E 離開選單\n" + 
				">> 輸入ID或Q(結束使用): ";
		GradeSys.login(scanner, gradeSys);
		assertEquals(expect, stream.toString());
	}
}
