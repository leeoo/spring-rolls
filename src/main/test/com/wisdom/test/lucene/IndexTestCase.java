package com.wisdom.test.lucene;



public class IndexTestCase {

	/*@Test
	public void testCreate(){
		RunHistory history=new RunHistory();
		history.setId(Long.decode("0"));
		history.setErrorInfo("中国共产党的优秀党员，久经考验的忠诚的共产主义战士，无产阶级革命家，我国经济建设战线的杰出领导人，中国共产党第十一届、十二届中央书记处书记，原国务委员，国务院原副总理，中国人民政治协商会议第七届全国委员会副主席谷牧同志，因病医治无效，于2009年11月6日14时55分在北京逝世，享年96岁。");
		SearchIndexCreator.createIndex(history, "id", "errorInfo");
		history=new RunHistory();
		history.setId(Long.decode("1"));
		history.setErrorInfo("11月6日上午9时许，温家宝乘坐专机从北京首都国际机场飞往埃及。温家宝指出，中非合作是全方位的，能源合作是其中的一个领域。但中国绝不是仅仅为了能源来到非洲的。中国援建坦赞铁路时，没想要来非洲开采石油。");
		SearchIndexCreator.createIndex(history, "id", "errorInfo");
		history=new RunHistory();
		history.setId(Long.decode("2"));
		history.setErrorInfo("重庆晚报11月7日报道 足坛打黑风波似乎越刮越猛，昨日，被公安机关带走的中国足协官员名字曝光，央视播出相关的讨论节目……在中国足球风雨飘摇之际，中国足协副主席、全国青少年校园足球领导办公室主任薛立昨日来到重庆，一方面显示体育总局对于发展青少年足球的决心，另一方面，薛立也代表足协，谈了对近期中国足球打黑的看法。事实上，从司法介入中国足球至今，中国足协一直没有高层敢于站出来表态，因此薛立的态度可以看作是这次自上而下的足坛扫黑运动中，中国足协对此的正式表态。");
		SearchIndexCreator.createIndex(history, "id", "errorInfo");
		history=new RunHistory();
		history.setId(Long.decode("3"));
		history.setErrorInfo("四川在线-华西都市报11月7日报道 以一曲《懂你》闻名全国的著名歌手满文军，今年5月19日曝出涉毒事件后，仅通过网站做了一次公开道歉，就销声匿迹了，甚至在妻子李俐庭审时也没有现身。昨晚，记者从成都鹏伟实业公司董事长张伟那里得到最新消息：今晚，满文军将借新楼盘“颐和京都”开业当嘉宾剪彩之际，在成都高调宣布复出。昨晚，经张伟安排，本报记者独家电话采访了满文军。");
		SearchIndexCreator.createIndex(history, "id", "errorInfo");
	}*/
	
	
	/*@Test
	public void testSearch(){
		Collection<Long> ids1=SearchIndexCreator.searchKeyword(RunHistory.class,"中国", "id", "errorInfo");
		System.out.println(ids1);
		Collection<Long> ids2=SearchIndexCreator.searchKeyword(RunHistory.class,"5 19", "id", "errorInfo");
		System.out.println(ids2);
	}*/
	
	/*@Test
	public void testDelete(){
		RunHistory history=new RunHistory();
		history.setId(Long.decode("3"));
		history.setErrorInfo("I love China");
		SearchIndexCreator.removeIndex(history, "id", "errorInfo");
	}*/
	
}
