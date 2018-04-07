

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Scanner;

public class GradeSys {
	// static method皆為UI用途
	
	// 使用HashTable以學生ID映射其成績物件
	Hashtable<Long, StudentsGrade> studentTable = new Hashtable<Long,StudentsGrade>();
	// 使用float array儲存權重，以百分比呈現
	private float weight[] = {10,10,10,30,40};
	// 分數欄位
	public String scoreName[]= {"lab1","lab2","lab3","mid-term","final exam"};
	
	/* method add----------------------------------------------------------------------------------                                                                                                    
	*在這裡call HashTable.put()將新增的StudentsGrade object放入table
	* @param StudentsGrade object
	* @return 一Boolean，是否成功
	*Pseudo code:
	*1.call HashTable.put()將StudentsGrade object放入table
	*2.return 是否成功
	* Time estimate : average case:O(1), worst case:O(n)
	----------------------------------------------------------------------------------------------------------*/
	public Boolean add(StudentsGrade studentsGrade) {
		this.studentTable.put(studentsGrade.getID(), studentsGrade);
		return true;
	}
	
	/* method getGrade----------------------------------------------------------------------------------                                                                                                    
	*在這裡call StudentsGrade.getGrade()取得每個成績
	* @param ID 該生ID
	* @return 成績陣列，對於不存在列表中的特殊狀況，則返回null
	*Pseudo code:
	*1.call inList查詢ID是否在studentTable中
	*2.call StudentsGrade.getGrade()取得成績，並回傳
	* Time estimate : O(1)
	----------------------------------------------------------------------------------------------------------*/
	public int[] getGrade(Long ID) {
		if(inList(ID))return this.studentTable.get(ID).getGrade();
		return null;
	}
	
	/* method inList----------------------------------------------------------------------------------                                                                                                    
	*查詢ID是否在列表中
	* @param ID 該生ID
	* @return boolean:此ID是否在列表中
	*Pseudo code:
	*1.call HashTable.containsKey(ID)確認ID是否在其中
	*2.回傳結果
	* Time estimate : O(1)
	----------------------------------------------------------------------------------------------------------*/
	public boolean inList(Long ID) {
		return this.studentTable.containsKey(ID);
	}
	
	/* method getName----------------------------------------------------------------------------------                                                                                                    
	* 在這裡call StudentsGrade.getName()取得該ID之學生名字
	* @param ID 該生ID
	* @return 名字，對於不存在列表中的特殊狀況，則返回空字串
	*Pseudo code:
	*1.call inList查詢ID是否在studentTable中
	*2.call StudentsGrade.getName()取得名字並回傳
	* Time estimate : O(1)
	----------------------------------------------------------------------------------------------------------*/
	public String getName(Long ID) {
		if(inList(ID))return studentTable.get(ID).getName();
		else return "";
	}
	
	/* method getNumberOfMember----------------------------------------------------------------------------------                                                                                                    
	* 將學生人數回傳，方便其他class使用
	* @return 學生人數
	*Pseudo code:
	*1.return studentTable.size();
	* 
	* Time estimate : O(1)
	----------------------------------------------------------------------------------------------------------*/
	public int getNumberOfMember() {
		return studentTable.size();
	}
	
	/* method getWeight----------------------------------------------------------------------------------                                                                                                    
	* 將weight陣列回傳（weight陣列是為private）
	* @return weight陣列
	*Pseudo code:
	*1.回傳weight陣列
	* 
	* Time estimate : O(1)
	----------------------------------------------------------------------------------------------------------*/
	public float[] getWeight() {
		return this.weight;
	}
	
	/* method  updateWeight ----------------------------------------------------------------------------------                                                                                                    
	* 將舊的weight以新weight更新
	*@param newWeight 	新的weight
	*Pseudo code:
	*1.將舊weight陣列以newWeight陣列覆蓋
	* 
	* Time estimate : O(1)
	----------------------------------------------------------------------------------------------------------*/
	public void updateWeight(float[] newWeight) {
		this.weight = newWeight;
	}
	
	/* method  getTotal ----------------------------------------------------------------------------------                                                                                                    
	* 由於成績存在getTotal中，在這裡呼叫StudentsGrade中的getTotal()取得該ID之total
	*@param ID 該生ID
	*@return Total，若發生異常情況，如該ID不存在列表中，return -1以告知系統有地方出錯
	*Pseudo code:
	*1.call inList查詢ID是否在studentTable中
	*2.call StudentsGrade中.getTotal(weight)取得total後回傳
	* 
	* Time estimate : average case:O(1), worst case:O(n)
	----------------------------------------------------------------------------------------------------------*/
	public float getTotal(Long ID) {
		if(inList(ID))return studentTable.get(ID).getTotal(weight);
		return -1;
	}


	/* method  getRank ----------------------------------------------------------------------------------                                                                                                    
	* 取得該ID之排名
	*@param ID 該生ID
	*@return 排名，若發生異常情況，如該ID不存在列表中，return -1以告知系統有地方出錯
	*Pseudo code:
	*1.建立相當於學生數量大小的新陣列，並將學生從studentTable中抽出為新陣列
	*2.用Arrays.sort排列此陣列，以學生之total作為Comparator
	*3.查詢ID於已排序列表的index，並且加上1作為該ID之排名並回傳排名
	* 
	* Time estimate : O(nlogn)
	----------------------------------------------------------------------------------------------------------*/
	public int getRank(Long ID) {
		StudentsGrade studentArray[] = new StudentsGrade[studentTable.size()];
		studentTable.values().toArray(studentArray);
		Arrays.sort(studentArray,new Comparator<StudentsGrade>() {
			@Override
			public int compare(StudentsGrade a,StudentsGrade b) {
				return Float.floatToIntBits(b.getTotal(weight)-a.getTotal(weight));
			}
		});
		for(int i=0;i<studentArray.length;i++) {
			if(studentArray[i].getID().equals(ID))return i+1;
		}
		return -1;
	}
	
	/* method getAverage ----------------------------------------------------------------------------------                                                                                                    
	*取得班級平均
	*@return 算出之平均
	*Pseudo code:
	*1.利用for迴圈，call StudentsGrade.getTotal來取得每位學生之total，並將每人total相加
	*2.將total除以studentTable.size()，也就是人數，來取得平均。
	* 
	* Time estimate : O(n)
	----------------------------------------------------------------------------------------------------------*/
	public float getAverage() {
		float total = 0;
		for(StudentsGrade sGrade:studentTable.values()) {
			total+=sGrade.getTotal(weight);
		}
		return total/studentTable.size();
	}
	
	/* method  showGrades ----------------------------------------------------------------------------------                                                                                                    
	* 顯示成績
	*@param ID 該生ID
	*@param gradeSys 重複利用main中new出的gradeSys
	*Pseudo code:
	*1.call gradeSys.getGrade(ID)取得ID之成績
	*2.利用for迴圈各個印出
	*3.call gradeSys.getTotal(ID)取得total並印出
	* 
	* Time estimate : O(1)
	----------------------------------------------------------------------------------------------------------*/
	public static void showGrades(Long ID,GradeSys gradeSys) {
		int[] grade = gradeSys.getGrade(ID);
		for(int i=0;i<grade.length;i++) {
			System.out.printf("%-15s%3d%s\n", gradeSys.scoreName[i]+":",grade[i],grade[i]<60?"*":""); // 低於60時顯示*號
		}
		System.out.printf("%-15s%3.1f\n", "Total grade:",gradeSys.getTotal(ID));
	}
	
	/* method  caseUpdateWeight ----------------------------------------------------------------------------------                                                                                                    
	* 處理更新權重方面的功能，包含印出
	*@param scanner 重複利用main中的scanner
	*@param gradeSys 重複利用main中new出的gradeSys
	*Pseudo code:
	*1.先利用gradeSys.getWeight()取得當前權重，並印出
	*2.利用for迴圈以及scanner.next()讓使用者輸入新的權重
	*3.印出newWeight請使用者確認
	*4.如果正確，call updateWeight()將newWeight更新至weight，如果錯誤，call GradeSys.caseUpdateWeight()
	*
	* Time estimate :O(n)
	----------------------------------------------------------------------------------------------------------*/
	public static void caseUpdateWeight(Scanner scanner,GradeSys gradeSys) {
		float newWeight[] = new float[5];
		float weight[] = gradeSys.getWeight();
		System.out.println("舊配分");
		for(int i=0;i<weight.length;i++) {
			System.out.printf("%-15s%3.0f%%\n", gradeSys.scoreName[i]+":",gradeSys.getWeight()[i]);
		}
		System.out.println("輸入新配分");
		for(int i=0;i<weight.length;i++) {
			System.out.printf("%-15s", gradeSys.scoreName[i]+":");
			String input = scanner.next();
			if(input.matches("[0-9]+"))newWeight[i]=Float.parseFloat(input);
			else i--;
		}
		System.out.println("請確認新配分");
		for(int i=0;i<weight.length;i++) {
			System.out.printf("%-15s%3.0f%%\n", gradeSys.scoreName[i]+":",newWeight[i]);
		}
		System.out.println("以上正確嗎? Y (Yes) 或 N (No)");
		if(scanner.next().equals("Y")) gradeSys.updateWeight(newWeight);
		else GradeSys.caseUpdateWeight(scanner,gradeSys);
	}
	
	/* method  menu ----------------------------------------------------------------------------------                                                                                                    
	* 選單畫面
	*@param ID 該生ID
	*@param scanner 重複利用main中的scanner
	*@param gradeSys 重複利用main中new出的gradeSys
	*Pseudo code:
	*1.根據scanner.next進行switch
	*2.根據不同case執行不同指令
	*3.直到case E 才會return，跳出while loop
	* 
	* Time estimate :O(nlogn)
	----------------------------------------------------------------------------------------------------------*/
	public static void menu(Long ID,Scanner scanner,GradeSys gradeSys) {
		while(true) {
			System.out.print("輸入指令 :\n1)G 顯示成績\n2)R 顯示排名\n3)A 顯示平均\n4)W 更新配分\n5)E 離開選單\n>> ");
			switch (scanner.next()) {
				// 顯示成績
				case "G": GradeSys.showGrades(ID,gradeSys);break;
				// 顯示排名
				case "R": System.out.println(gradeSys.getName(ID)+"排名第"+(gradeSys.getRank(ID))+"/"+gradeSys.studentTable.size());break;
				// 顯示平均
				case "A": System.out.println("成績平均為: "+gradeSys.getAverage());break;
				// 更新配分
				case "W": GradeSys.caseUpdateWeight(scanner,gradeSys);break;
				// 離開選單
				case "E": return;
				// 錯誤指令
				default: System.out.println("無此指令！");break;
			}
		}
	}
	
	
	/* method  main  ----------------------------------------------------------------------------------                                                                                                    
	* 程式進入點
	*Pseudo code:
	*1.先new GradeSys()以利之後使用
	*2.new scanner
	*3.利用while(scanner.hasnext())來將整份gradeinput.txt讀入並一行一行存入StudentsGrade object
	*  並用gradeSys.add()將StudentsGrade存入studentTable
	*4.while()迴圈結束後進入login()
	*
	* Time estimate : average case:O(n), worst case:O(n^2)
	----------------------------------------------------------------------------------------------------------*/

	public static void main(String[] args) throws IOException {
		GradeSys gradeSys = new GradeSys();
		Scanner scanner = new Scanner(new File("src/gradeinput.txt"));
		while(scanner.hasNext()) {
			String studentData= scanner.nextLine();
			StudentsGrade studentsGrade = new StudentsGrade(studentData.split(" "));
			gradeSys.add(studentsGrade);
		}
		scanner.close();
		scanner = new Scanner(System.in);
		login(scanner,gradeSys);
	}
	
	/* method  login ----------------------------------------------------------------------------------                                                                                                    
	* 登入畫面
	*@param scanner 重複利用main中的scanner
	*@param gradeSys 重複利用main中new出的gradeSys
	*Pseudo code:
	*1.While(true)
	*2.利用scanner讀入學號或指令
	*3.依照輸入的類別執行指令，若為學號且存在在studentTable中menu，若為Ｑ則結束程式，其他異常情況則顯示錯誤訊息
	* 
	* Time estimate : average case:O(n), worst case:O(n^2)
	----------------------------------------------------------------------------------------------------------*/

	public static void login(Scanner scanner,GradeSys gradeSys) {
		Long ID = null;
		while(true) {
			System.out.print("輸入ID或Q(結束使用): ");
			String input = scanner.next();
			if(input.equals("Q"))break;
			else if(input.matches("[0-9]+")){
				ID = Long.parseLong(input);
				if(gradeSys.inList(ID)) {
					System.out.println("Welcome "+gradeSys.getName(ID));
					menu(ID, scanner, gradeSys);
				}
				else System.out.println("查無此ID!");
			}
			else System.out.println("輸入格式錯誤！");
		}
		System.out.println("結束程式");
	}
	
}
