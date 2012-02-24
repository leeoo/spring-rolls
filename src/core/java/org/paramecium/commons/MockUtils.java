package org.paramecium.commons;

import java.util.Date;
import java.util.Random;


/**
 * 功 能 描 述:<br>
 * 模拟数据生成工具
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2012-2-21上午10:09:20
 * <br>项 目 信 息:paramecium:org.paramecium.commons.MockUtils.java
 */
public abstract class MockUtils {

	private static final char[] firstNames = new char[]{'张','王','李','赵','刘','周','吴','曹','蔡','董',
												'夏','徐','陈','潘','解','郭','郑','魏','林','姚',
												'范','钱','肖','冯','蒋','沈','韩','杨','孟','鲁',
												'彭','苏','葛','程','丁','费','薛','廉','叶','温',
												'秦','许','何','吕','严','陶','姜','金','佟','宋'};
	private static final char[] lastNames = new char[]{'雅','力','丽','梅','钢','连','明','雷','晟','智',
												'达','阳','玉','畅','迪','家','新','月','荣','静',
												'平','欢','羽','凡','权','东','生','华','辉','勇',
												'英','准','硕','文','曼','雪','劲','笑','天','岳',
												'海','国','青','旭','路','行','川','如','铃','方'};
	private static final String[] provinceNames = new String[]{"北京市","上海市","重庆市","天津市","河北省",
												"山西省","山东省","河南省","辽宁省","江苏省","浙江省","江西省",
												"福建省","黑龙江省","吉林省","湖北省","湖南省","四川省","广东省",
												"海南省","甘肃省","青海省","陕西省","贵州省","安徽省","内蒙古自治区",
												"新疆维吾尔自治区","西藏自治区","广西壮族自治区","宁夏回族自治区"};
	private static final String[] cityNames = new String[]{"心安市","常德市","江州市","平川县","新择日市","把迪市","新也县",
												"蒋家堡县","罗汉市","大及市","川田市","江河县","大川县","新华市",
												"训潞市","野百合市","玫瑰の市","风华市","天间市","炼火市","平极市",
												"美丽欣城县","全兴市","光和市","青竹县","鱼米县","天地市","开封府市",
												"太极市","盛事县","太平市","光武市","上游县","兴民市","全祥市","下深市"};
	private static final String[] areaNames = new String[]{"大陆区","新加区","家栋区","小王庄村","和田区","江南新区","福地村",
												"何家心村","长雨区","中山一区","中山二区","祥和新区","吉利村","海潞区",
												"凝集区","北光区","铁中一区","新河路区","天梦区","张庄村","四平区","同方区",
												"达信村","集美区","花心二区","彩虹区","晚霞区","太平庄村","大同区","高新区",
												"河中村","新梅区","低水村","新阳光区","龙月区","永福村","广记村","上河区"};
	private static final String[] roadNames = new String[]{"常平路","核心街","北中路","明天北路","陆家街","比诺街","上合南街",
												"海天中路","上品二街","天雅新路","和睦路","瑞祥路","金太阳街","四合木路",
												"东西一路","天野路","新城大街","大北二路","兴盛三路","知己大街","文艺路","爱民路",
												"南十六路","北十一街","五好大街","江民路","百叶街","爱之心路","铁路二街","雍和路",
												"和睦街","爱心路","科技六路","富工大街","沧海路","爱都国际大街","温馨一街","上文路"};
	private static final String[] emails = new String[]{"@126.com","@qq.com","@163.com","@sina.com","@yahoo.com.cn","@21cn.com","@139.com","@sohu.com","@hotmail.com","@gmail.com"};
	
	private static final char[] chs = new char[]{'甲','乙','丙','丁','戊','己','庚','辛','壬','癸',
												'子','丑','寅','卯','辰','巳','午','未','申','酉',
												'戌','亥','壹','贰','叁','肆','伍','陆','柒','捌',
												'九','零','的','地','得','，','。','\n','！','？',
												'兑','天','人','乾','坤','坎','离','震','艮','巽'};
	
	/**
	 * 获得随机的姓名
	 * @return
	 */
	public static String getFullName(){
		int firstCount = firstNames.length;
		int lastCount = lastNames.length;
		Random random = new Random();
		int randomFirstCount = random.nextInt(firstCount);
		int randomLastCount = random.nextInt(lastCount);
		int i = random.nextInt(2);
		if(i == 0){
			return String.valueOf(new char[]{firstNames[randomFirstCount],lastNames[randomLastCount]});
		}else{
			int randomLastCount2 = random.nextInt(lastCount);
			return String.valueOf(new char[]{firstNames[randomFirstCount],lastNames[randomLastCount],lastNames[randomLastCount2]});
		}
	}
	
	/**
	 * 获得随机的地址
	 * @return
	 */
	public static String getAddress(){
		int provinceCount = provinceNames.length;
		int cityCount = cityNames.length;
		int areaCount = areaNames.length;
		int roadCount = roadNames.length;
		Random random = new Random();
		int randomProvinceCount = random.nextInt(provinceCount);
		int randomCityCount = random.nextInt(cityCount);
		int randomAreaCount = random.nextInt(areaCount);
		int randomRoadCount = random.nextInt(roadCount);
		StringBuffer sb = new StringBuffer();
		sb.append(provinceNames[randomProvinceCount]).append(cityNames[randomCityCount]).append(areaNames[randomAreaCount]).append(roadNames[randomRoadCount]);
		sb.append(random.nextInt(999)).append('-').append(random.nextInt(99)).append('号');
		return sb.toString();
	}

	/**
	 * 获得随机的邮政编码
	 * @return
	 */
	public static String getPostal(){
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for(int j = 0 ; j<6 ; j++){
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}
	
	/**
	 * 获得随机的手机号
	 * @return
	 */
	public static String getMobile(){
		StringBuffer sb = new StringBuffer();
		sb.append(1);
		Random random = new Random();
		int i = random.nextInt(10);
		if(i <= 6){//概率，13开头的电话需要多一些
			sb.append(3);
		}else if(i >6 && i <=8){
			sb.append(5);
		}else{
			sb.append(8);
		}
		for(int j = 0 ; j<9 ; j++){
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}
	
	/**
	 * 获得随机的电话号码
	 * @return
	 */
	public static String getTel(){
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		int i = random.nextInt(10);
		if(i < 7){//概率，无的分电话需要多一些
			for(int j = 0 ; j<3 ; j++){
				sb.append(random.nextInt(10));
			}
			sb.append('-');
			for(int j = 0 ; j<8 ; j++){
				sb.append(random.nextInt(10));
			}
		}else{
			for(int j = 0 ; j<3 ; j++){
				sb.append(random.nextInt(10));
			}
			sb.append('-');
			for(int j = 0 ; j<8 ; j++){
				sb.append(random.nextInt(10));
			}
			sb.append('-');
			sb.append(random.nextInt(999));
		}
		return sb.toString();
	}
	
	/**
	 * 获得身份证号码
	 * @return
	 */
	public static String getIDCode(){
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for(int j = 0 ; j<18 ; j++){
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}
	
	/**
	 * 获得随机的Email地址
	 * @return
	 */
	public static String getMail(){
		int emailCount = emails.length;
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		int i = random.nextInt(15);//邮箱长度位数
		String s = EncodeUtils.randomUUID().replaceAll("-", "");
		if(i<5){
			i = 5;
		}
		s = s.substring(0, i);
		int randomEmailCount = random.nextInt(emailCount);
		sb.append(s).append(emails[randomEmailCount]);
		return sb.toString();
	}
	
	/**
	 * 生成随机的汉字信息
	 * @param minLength最小长度
	 * @param maxLength最大长度
	 * @return
	 */
	public static String getChineseText(int minLength,int maxLength){
		int chsCount = chs.length;
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		int textLength = random.nextInt(maxLength);
		if(textLength < minLength){
			textLength = minLength;
		}
		for(int j = 0 ; j<textLength ; j++){
			int randomChCount = random.nextInt(chsCount);
			sb.append(chs[randomChCount]);
		}
		return sb.toString();
	}
	
	public static Date getDate(boolean isTodayBefore){
		long current = EncodeUtils.millisTime();
		Random random = new Random();
		Date date = new Date(current);
		if(isTodayBefore){
			current = current - random.nextInt(Integer.MAX_VALUE)*1000L;
		}else{
			current = current - (random.nextLong()/3000000L);
		}
		date = new Date(current);
		return date;
	}
	
	public static void main(String[] args) {
		for(int i =0 ;i<100;i++){
			System.out.println(getFullName()+" | "+getAddress()+" | "+getPostal()+" | "+DateUtils.parse(DateUtils.DATE_TIME_FORMAT, getDate(true))+" | "+getMobile()+" | "+getTel()+" | "+getIDCode()+" | "+getMail());
			System.out.println(getChineseText(2000,10000));
			System.out.println("=================================================");
		}
	}
	
}
