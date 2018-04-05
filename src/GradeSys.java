
import java.awt.List;
import java.awt.RenderingHints.Key;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Scanner;

import org.w3c.dom.traversal.NodeIterator;

public class GradeSys {

	ArrayList<StudentsGrade> studentList = new ArrayList<StudentsGrade>();
	public Boolean add(StudentsGrade studentsGrade) {
		this.studentList.add(studentsGrade);
		update();
		return true;
	}
	public int[] getGrade(Long ID) {
		int index = getIndexByID(ID);
		if(index>=0) {
			return studentList.get(index).getGrade();
		}
		return null;
	}
	private void update() {
		Collections.sort(studentList);
	}
	private int getIndexByID(Long ID) {
		//System.out.print("In:");
		for(int i=0;i<studentList.size();i++) {
			//System.out.println(studentList.get(i).getID()-ID);
			if(studentList.get(i).getID()-ID==0)return i;
		}
		return -1;
	}
	public boolean inList(Long ID) {
		boolean temp = getIndexByID(ID)>=0;
		return temp;
	}
	public String getName(Long ID) {
		int index = getIndexByID(ID);
		return studentList.get(index).getName();
	}
	public int getNumberOfMember() {
		return studentList.size();
	}
	public int[] getWeight(Long ID) {
		int index = getIndexByID(ID);
		return studentList.get(index).getWeight();
	}
	public void updateWeight(Long ID,int newWeight,int position) {
		int index = getIndexByID(ID);
		studentList.get(index).updateWeight(newWeight,position);
	}
	public int getTotal(Long ID) {
		int index = getIndexByID(ID);
		return studentList.get(index).getTotal();
	}
	public void showGrades(int[] grade,String[] test,Long ID,GradeSys gradeSys) {
		for(int i=0;i<grade.length;i++) {
			if(i<grade.length-2) {	
				if(grade[i]>=60)
					System.out.println(test[i]+":\t\t"+grade[i]);
				else
					System.out.println(test[i]+":\t\t"+grade[i]+"*");
			}
			else {
				if(grade[i]>=60)
					System.out.println(test[i]+":\t"+grade[i]);
				else
					System.out.println(test[i]+":\t"+grade[i]+"*");
			}
		}
		System.out.println("total grade:\t"+gradeSys.getTotal(ID));
	}
	public int caseUpdateWeight(Scanner scanner,int weight[],GradeSys gradeSys,String test[],Long ID) {
		System.out.println("舊配分");
		for(int i=0;i<weight.length;i++) {
			if(i!=weight.length-1)
				System.out.println(test[i]+"\t\t"+weight[i]);
			else
				System.out.println(test[i]+"\t"+weight[i]);
		}
		System.out.println("輸入新配分");
		for(int i=0;i<weight.length;i++) {
				System.out.print(test[i]+"\t\t");
				int inputWeight = scanner.nextInt();
				gradeSys.updateWeight(ID,inputWeight,i);
				//System.out.print("\n");
		}
		System.out.println("請確認新配分");
		for(int i=0;i<weight.length;i++) {
			if(i!=weight.length-1)
				System.out.println(test[i]+"\t\t"+weight[i]+"%");
			else
				System.out.println(test[i]+"\t"+weight[i]+"%");
		}
		System.out.println("以上正確嗎? Y (Yes) 或 N (No)");
		String inputYN = scanner.next();
		if(inputYN.equals("N")) {
			gradeSys.caseUpdateWeight(scanner,weight,gradeSys,test,ID);
		}
	
		return 0;
		
		
	}
	public int commandExecute(Long ID,Scanner scanner,GradeSys gradeSys,String input ,int[] grade, int weight[],String[] test,int flag) {
		
		switch (input) {
		case "G":
			gradeSys.showGrades(grade,test,ID,gradeSys);
			break;
		case "R":
			System.out.println(gradeSys.getName(ID)+"排名第"+(gradeSys.getIndexByID(ID)+1)+"/"+gradeSys.studentList.size());
			break;
		case "A":
			System.out.println(gradeSys.getName(ID)+"的平均為"+(grade[0]+grade[1]+grade[2]+grade[3]+grade[4])/5);
		case "W":
			gradeSys.caseUpdateWeight(scanner,weight,gradeSys,test,ID);
		case "E"	:
			flag=1;
			break;				
		default:
			break;
		}
		if(flag==1)
			return 1;
		else
			return 0;
	}

	public static void menu(Long ID,Scanner scanner,GradeSys gradeSys) {
		int flag=0;
		while(flag==0) {
			System.out.println("輸入指令 1)G 顯示成績\n        2)R 顯示排名\n        3)A 顯示平均\n        4)W 更新配分\n        5)E 離開選單");
			String input = scanner.next();
			int grade[] = gradeSys.getGrade(ID);
			int weight[] = gradeSys.getWeight(ID);
			String test[]= {"lab1","lab2","lab3","mid-term","final exam"};
			flag=gradeSys.commandExecute(ID,scanner,gradeSys,input,grade,weight,test,flag);
//			String test[]= {"lab1","lab2","lab3","mid-term","final exam"};
//			int flag=0;
//			switch (input) {
//			case "G":
//				gradeSys.showGrades(grade,test);
//				break;
//			case "R":
//				System.out.println(gradeSys.getName(ID)+"排名第"+(gradeSys.getIndexByID(ID)+1));
//				break;
//			case "A":
//				System.out.println(gradeSys.getName(ID)+"的平均為"+(grade[0]+grade[1]+grade[2]+grade[3]+grade[4])/5);
//			case "W":
//				gradeSys.caseUpdateWeight(scanner,weight,gradeSys,test,ID);
//			case "E"	:
//				flag=1;
//				break;				
//			default:
//				break;
//			}
//			if(flag==1)
//				break;
		}
	}
	public static void main(String[] args) throws IOException {
		Long ID = null;
		GradeSys gradeSys = new GradeSys();
		//System.out.print("In:");
		//Scanner scanner = new Scanner(System.in);
		//int n = scanner.nextInt();
		//scanner.nextLine();
		Scanner scanner = new Scanner(new File("src/simple.txt"));
		while(scanner.hasNext()) {
			String studentData= scanner.nextLine();
			StudentsGrade studentsGrade = new StudentsGrade(studentData.split(" "));
			gradeSys.add(studentsGrade);
		}
		scanner.close();
		
		scanner = new Scanner(System.in);
		
		while(true) {
			System.out.print("輸入ID或Q(結束使用): ");
			String input = scanner.next();
			if(input.matches("^Q"))break;
			else{
				ID = Long.parseLong(input);
				System.out.println(ID);
				if(gradeSys.inList(ID)) {
					System.out.println("Welcome "+gradeSys.getName(ID));
					menu(ID, scanner, gradeSys);
					continue;
				}
			}
			
		}
	}

}
