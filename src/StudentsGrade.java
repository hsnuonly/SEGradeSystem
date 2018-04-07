
public class StudentsGrade{
	//學生ID
	private Long ID;
	//學生名字
	private String name;
	//學生之成績陣列
	private int grade[] = new int[5];
	
	/* method StudentsGrade----------------------------------------------------------------------------------                                                                                                    
	*建構子，在這裡將處理好的資料存入
	* @param inputSource，是為處理好的資料
	*Pseudo code:
	*1.利用for迴圈將資料一一存入
	*
	* Time estimate : O(1)
	----------------------------------------------------------------------------------------------------------*/
	public StudentsGrade(String[] inputSource) {
		this.ID=Long.parseLong(inputSource[0]);
		this.name=inputSource[1];
		for(int i=2;i<7;i++)grade[i-2]=Integer.parseInt(inputSource[i]);
	}
	/* method getTotal----------------------------------------------------------------------------------                                                                                                    
	*計算total
	* @param weight[]，gradeSys中的權重
	* ＠return total 
	*Pseudo code:
	*1.利用for迴圈將grade乘上對應的weight，再除100，再將全部結果相加
	*
	* Time estimate : O(1)
	----------------------------------------------------------------------------------------------------------*/
	public float getTotal(float weight[]) {
		float total = 0;
		for(int j =0;j<this.grade.length;j++) {
			total+=this.grade[j]*((float)weight[j]/100);
		}
		return total;
	}
	public Long getID() {
		return ID;
	}
	public int[] getGrade() {
		return this.grade;
	}
	public String getName() {
		return name;
	}

}
