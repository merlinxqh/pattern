package com.xqh.test.ttspre;

import com.google.common.collect.Lists;
import com.xqh.utils.ExcelReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author	xiezhb
 * @date	2017年8月15日
 */
@Slf4j
public class TextPretreatment {
	private static Map<String, TimeO> cachedMap = new HashMap<String, TimeO>();

	private static final Map<String, CacheObj> mem = new HashMap<>();

	private static String replaceText="{{%d}}";

	private static String replaceRegex ="<(value|code|tel|py|pname|word|phrase|letter|mute|sub|background|audio|voice)(.*?)>(.*?)</(value|code|tel|py|pname|word|phrase|letter|mute|sub|background|audio|voice)>";

	private static String replaceRegex2 ="<(background|audio|voice)(.*?)/>";

	private static List<TtsPronunciationDto> dtoList = getAdaptList();

	/**
	 * 根据appKey进行文本预处理
	 * @param text
	 * @return
	 */
	public static String pretreatment(String text) {

		List<ReplaceDto> replaceList = new ArrayList<>();

		if (null == dtoList || dtoList.size() == 0) {
			return text;
		}

		String ret = text;
		//1.执行普通替换
		for (TtsPronunciationDto dto : dtoList) {
			String original = dto.getOriginal();
			String target = dto.getTarget();
			if (dto.getType() == 0) {
				String tmp = ret;
				ret = ret.replaceAll(original, target);
				if(!tmp.equals(ret)){
					log.info("触发匹配规则ID==>{}, 原内容==>{}, Original==>{}, target==>{}, 匹配后内容==>{}", dto.getAudio(), tmp, dto.getOriginal(), dto.getTarget(), ret);
				}
			}
		}
		//2.找出排除文本列表
		ret = replaceExcludeText(replaceList, ret, replaceRegex);
		ret = replaceExcludeText(replaceList, ret, replaceRegex2);

		//3.执行正则替换
		for (TtsPronunciationDto dto : dtoList) {
			String original = dto.getOriginal();
			String target = dto.getTarget();
			if (dto.getType() == 1) {
				Pattern pattern = Pattern.compile(original);
				Matcher matcher = pattern.matcher(ret);
				StringBuffer sb = new StringBuffer();

				String processedText="";
				while (matcher.find()) {
					String matched = matcher.group();
					processedText="<" + target + ">" + matched + "</" + target + ">";
					log.info("触发匹配规则ID==>{}, 原内容==>{}, Original==>{}, target==>{}, 匹配后内容==>{}", dto.getAudio(), matched, dto.getOriginal(), dto.getTarget(), processedText);
					matcher.appendReplacement(sb, processedText);
				}
				matcher.appendTail(sb);

				ret = sb.toString();
				//加入排除文本列表
				if(!StringUtils.isEmpty(processedText)) {
					ReplaceDto reDto = new ReplaceDto();
					reDto.setText(processedText);
					replaceList.add(reDto);
					reDto.setPlaceholder(String.format(replaceText, replaceList.size()));
					String tmp = ret;
					ret = ret.replace(processedText, reDto.getPlaceholder());
					if(!tmp.equals(ret)){
						log.info("触发匹配规则ID==>{}, 原内容==>{}, Original==>{}, target==>{}, 匹配后内容==>{}", dto.getAudio(), tmp, dto.getOriginal(), dto.getTarget(), ret);
					}
				}
			} else if (dto.getType() == 2) {
				Pattern pattern = Pattern.compile(original);
				Matcher matcher = pattern.matcher(ret);
				StringBuffer sb = new StringBuffer();

				while (matcher.find()) {
					//<sub alias="摄氏度">mmHg</sub>
					String replaceStr = "<sub alias=\"" + target + "\">" + matcher.group() + "</sub>";
					matcher.appendReplacement(sb, replaceStr);
					log.info("触发匹配规则ID==>{}, 原内容==>{}, Original==>{}, target==>{}, 匹配后内容==>{}", dto.getAudio(), sb.toString(), dto.getOriginal(), dto.getTarget(), replaceStr);
				}
				matcher.appendTail(sb);

				ret = sb.toString();
			}
		}
		//4.排除文本列表还原
		for (ReplaceDto item : replaceList) {
			ret = ret.replace(item.getPlaceholder(), item.getText());
		}

		return ret;
	}

	public static void main(String[] args) {

		pretreatment("好的，为您找到“西红柿炒鸡蛋”，该菜谱收藏量为102952，确认播报菜谱详情还是换一个");
		pretreatment("结果等于17393400");
		pretreatment("8956.4万名");
		pretreatment("9999万人次");
		pretreatment("第29军");
		pretreatment("俄罗斯联邦。。。。国土面积为1707.55万平方公里");
		pretreatment("终于见到我朝思暮想的人了");
		pretreatment("还不知道");
		pretreatment("年老体弱者宜穿大衣、呢外套加羊毛衫");
		pretreatment("西南风微风转北风");
		pretreatment("有音乐，没疲惫");
		pretreatment("圆周率");
		pretreatment("空气质量为");
		pretreatment("正方形面积等于边长乘以边长");
//		pretreatment("");
//		pretreatment("");
//		pretreatment("");
	}

	/**
	 * 替换排除过滤文本
	 * @param replaceList
	 * @param ret
	 * @param regex
	 * @return
	 */
	public static String replaceExcludeText(List<ReplaceDto> replaceList, String ret, String regex){
		Pattern replacePattern = Pattern.compile(regex);
		Matcher replaceMatcher = replacePattern.matcher(ret);
		while (replaceMatcher.find()) {
			String matched = replaceMatcher.group();
			ReplaceDto dto = new ReplaceDto();
			replaceList.add(dto);
			String placeholder = String.format(replaceText, replaceList.size());
			dto.setText(matched);
			dto.setPlaceholder(placeholder);
			ret = ret.replace(matched, placeholder);
		}
		return ret;
	}

	/**
	 * @return
	 */
	private static List<TtsPronunciationDto> getAdaptList() {
		List<TtsPronunciationDto> resList = Lists.newArrayList();
		File file = new File("D:\\pre.xlsx");
		List<String[]> list = ExcelReader.getExcelData(file, 0);
		for (String[] array:list){
			resList.add(TtsPronunciationDto.builder()
					.audio(array[0])
					.original(array[1])
					.target(array[2])
					.type(Double.valueOf(array[3]).intValue())
					.build());
		}
        return resList;
	}


	static class TimeO{
		private Map<String, String> map = null;
		private long time = System.currentTimeMillis();

		public TimeO(Map<String, String> map){
			this.map = map;
			this.time = System.currentTimeMillis();
		}

		public Map<String, String> getMap() {
			return map;
		}

		public void setMap(Map<String, String> map) {
			this.map = map;
		}

		public long getTime() {
			return time;
		}

	}

	static class CacheObj {
		private long time = System.currentTimeMillis();
		private List<TtsPronunciationDto> dataList = null;

		/**
		 * @param dataList
		 */
		public CacheObj(List<TtsPronunciationDto> dataList) {
			this.dataList = dataList;
		}
		public long getTime() {
			return time;
		}
		public void setTime(long time) {
			this.time = time;
		}
		public List getDataList() {
			return dataList;
		}
		public void setDataList(List dataList) {
			this.dataList = dataList;
		}
	}
}
