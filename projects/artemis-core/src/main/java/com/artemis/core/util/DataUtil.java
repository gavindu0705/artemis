package com.artemis.core.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class DataUtil {
	public static Pattern cellPhonePattern = Pattern.compile("^(1[23456789][0-9]{1})(-)?([0-9]{8})$");

	public static Pattern LINK = Pattern.compile("[a-zA-z]+://[a-zA-Z0-9./%?=&-]*"); // 网址
	// public static Pattern MOBILE_344 =
	// Pattern.compile("(\\d{3}-\\d{4}-\\d{4})");
	public static Pattern NUM_SERIES = Pattern.compile("(?:\\d[\\s-]*){7}");// ;连续数字
	public static Pattern URL_SUFFIX = Pattern.compile("[a-zA-Z0-9.]*\\.((?:com)|(?:COM)|(?:cn)|(?:CN)|(?:net)|(?:NET))"); // 后缀
	public static Pattern IMG = Pattern.compile("<img.*src=(.*?)[^>]*?>"); // 图片链接地址
	public static Pattern OUT_TAG = Pattern.compile("</?[a-zA-Z0-9]+:[a-zA-Z0-9]+[^>]*>"); // 外部标签
	public static final Pattern WORDS = Pattern.compile(".*[a-zA-Z].*");
	public static final Pattern NO_WORDS = Pattern.compile(".*[^a-zA-Z].*");
	public static String SPACIAL_PATTERN = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

	public static String replaceAll(String original, String with, Pattern... pattern) {
		if (StringUtils.isNotBlank(original)) {
			original = toDBCcase(original);
			original = modifyHref(original);
			for (Pattern pt : pattern) {
				original = pt.matcher(original).replaceAll(with);
			}
		}
		return original;
	}

	public static float getRound(int scale, double dSource) {
		BigDecimal deSource = new BigDecimal(dSource);
		return deSource.setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
	}

	public static double getDoubleRound(int scale, double dSource) {
		BigDecimal deSource = new BigDecimal(dSource);
		return deSource.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 截取字符串
	 * 
	 * 若原始字符串的长度小于最大截取长度，则直接返回原始字符串
	 * 
	 * @param targetString
	 *            原始字符串
	 * @param targetLength
	 *            最大长度
	 * @param tailLength
	 *            尾巴长度
	 * @param tailString
	 *            结尾字符串
	 * @return
	 */
	public static String cutString(String targetString, int targetLength, int tailLength, String tailString) {
		if (StringUtils.isBlank(targetString)) {
			return StringUtils.EMPTY;
		} else if (targetString.length() <= targetLength) {
			return targetString;
		} else if (targetLength > tailLength) {
			return targetString.substring(0, targetLength - tailLength) + tailString;
		}
		return StringUtils.EMPTY;
	}

	/**
	 * 格式化均价
	 * 
	 * 将个位和十位直接变为0
	 * 
	 * @param avgPirce
	 * @return
	 */
	public static Integer formatAvgPrice(Integer avgPirce) {
		if (null == avgPirce || 0 == avgPirce) {
			return 0;
		} else {
			return new Integer((Integer) (avgPirce / 100) * 100);
		}
	}

	/**
	 * 全角转半角
	 * 
	 * @param input
	 * @return
	 */
	public static String toDBCcase(String input) {
		if (StringUtils.isBlank(input))
			return "";
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '\u3000') {
				c[i] = ' ';
			} else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
				c[i] = (char) (c[i] - 65248);
			}
		}
		String returnString = new String(c);
		return returnString;
	}

	/**
	 * 判断文本中的链接是否是fangjia,如果不是就删除
	 * 
	 * @param htmlDes
	 * @return
	 */
	public static Pattern LINK_PATTERN = Pattern.compile("<a(.*?)>(.*?)</a>", Pattern.DOTALL);

	public static String modifyHref(String htmlDes) {
		if (StringUtils.isBlank(htmlDes)) {
			return StringUtils.EMPTY;
		}
		String s = htmlDes;
		Matcher m = LINK_PATTERN.matcher(s);
		while (m.find()) {
			if (!m.group(1).contains("fangjia.com")) {
				s = s.replace(m.group(0), m.group(2));
			}
		}
		return s;
	}

	public static String removeHref(String htmlDes) {
		if (StringUtils.isBlank(htmlDes)) {
			return StringUtils.EMPTY;
		}
		String s = htmlDes;
		Matcher m = LINK_PATTERN.matcher(s);
		while (m.find()) {
			s = s.replace(m.group(0), m.group(2));
		}
		return s;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List splitArray(List array, int splitNum) {
		List ret = new ArrayList();
		if (array == null || splitNum < 2) {
			return ret;
		}

		if (array instanceof Set || array instanceof HashSet) {
			array = new ArrayList(array);
		}

		List tmpArray = array;
		while (true) {
			if (tmpArray.size() < splitNum) {
				if (tmpArray.size() > 0) {
					ret.add(tmpArray);
				}
				break;
			}
			ret.add(tmpArray.subList(0, splitNum));
			tmpArray = tmpArray.subList(splitNum, tmpArray.size());
		}
		return ret;
	}

	/**
	 * 加密图片url
	 * 
	 * @param u
	 * @param s
	 * @param t
	 * @return
	 */
	public static String encodePicUrl(String u, String s, String t) {
		if (StringUtils.isBlank(u)) {
			return StringUtils.EMPTY;
		}
		try {
			return SecurityUtil.encode("u=" + URLEncoder.encode(u, "UTF-8") + "&s=" + s + "&t=" + t);
		} catch (UnsupportedEncodingException e) {
			return SecurityUtil.encode("u=" + u + "&s=" + s + "&t=" + t);
		}
	}

	/**
	 * 把字符串以16进制数值显示出来
	 * 
	 * @param data
	 * @return
	 */
	public static String encode(String data) {
		byte[] bytes = data.getBytes();
		StringBuilder ret = new StringBuilder();
		for (byte b : bytes) {
			int n = b & 0xFF;
			ret.append(Integer.toHexString(n));
		}
		return ret.toString();
	}

	// 字节码转换成16进制字符串
	public static String byte2hex(byte bytes[]) {
		StringBuffer retString = new StringBuffer();
		for (int i = 0; i < bytes.length; ++i) {
			retString.append(Integer.toHexString(0x0100 + (bytes[i] & 0x00FF)).substring(1).toUpperCase());
		}
		return retString.toString();
	}

	// 将16进制字符串转换成字节码
	public static byte[] hex2byte(String hex) {
		byte[] bts = new byte[hex.length() / 2];
		for (int i = 0; i < bts.length; i++) {
			bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return bts;
	}

	/**
	 * 判断是否是标准的手机号码
	 * 
	 * @param phoneNum
	 * @return
	 */
	public static boolean isCellPhoneNumber(String phoneNum) {
		if (StringUtils.isBlank(phoneNum)) {
			return false;
		}
		Matcher m = cellPhonePattern.matcher(phoneNum);
		if (m.matches()) {
			return true;
		}
		return false;
	}

	public static String hideCellPhoneNumber(String numbers) {
		if (numbers == null || "".equals(numbers)) {
			return "";
		}
		String ret = "";
		Pattern p1 = Pattern.compile("^1[3|5|8][0-9]{1}[0-9]{8}$");
		Pattern p2 = Pattern.compile("^(0[0-9]{2,3})?([2-9][0-9]{6,7})+([0-9]{1,4})?$");

		if (p1.matcher(numbers).matches()) {
			Matcher m = Pattern.compile("([0-9]{3})([0-9]{5})([0-9]*)").matcher(numbers);
			if (m.find()) {
				ret = m.group(1) + "*****" + m.group(3);
			}
		} else if (p2.matcher(numbers).matches()) {
			Matcher m = Pattern.compile("([0-9]{3})([0-9]*)([0-9]{5})").matcher(numbers);
			if (m.find()) {
				ret = m.group(1) + m.group(2) + "*****";
			}
		} else {
			int len = numbers.length();
			if (len > 3) {
				ret = numbers.substring(0, 3);
				if (len > 11) {
					len = 11;
				}
				for (int i = 0; i < len - 3; i++) {
					ret += "*";
				}
			} else {
				for (int i = 0; i < len; i++) {
					ret += "*";
				}
			}
		}

		return ret;
	}

	public static Float formatNumber(Float n) throws ParseException {
		DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.CHINESE);
		decimalFormat.setMaximumFractionDigits(2);
		decimalFormat.setMinimumFractionDigits(2);
		return decimalFormat.parse(decimalFormat.format(n)).floatValue();
	}

	/**
	 * 去掉str中的html标记
	 * 
	 * @param str
	 * @return
	 */
	public static String removeHtml(String str) {
		if (StringUtils.isBlank(str)) {
			return StringUtils.EMPTY;
		}
		String regEx = "<[^>]*>";
		return str.replaceAll(regEx, "");
	}

	/**
	 * 去除特殊字符串
	 * 
	 * <p>
	 * 特殊字符串定义:html,&*;,\[a-z]
	 * </p>
	 * 
	 * @param str
	 * @return
	 */
	public static String removeSpecialString(String str) {
		if (StringUtils.isBlank(str)) {
			return StringUtils.EMPTY;
		}
		Pattern p = Pattern.compile("<script.*>.*</script>", Pattern.DOTALL);
		str = p.matcher(str).replaceAll("");
		return str.replaceAll("<[^>]*>", "").replaceAll("&[a-z]{1,4};", "").replaceAll("[\\u000A\\u0009\\u000D\\u000C]", "");
	}

	public static Pattern nameTags = Pattern.compile("</?([^>]*)(\\s|$|>)");

	/**
	 * 去除html中不完整的标签
	 * 
	 * @param html
	 * @return
	 */
	public static String balanceTags(String html) {
		if (StringUtils.isBlank(html)) {
			return html;
		}
		Matcher matcher = nameTags.matcher(html.toLowerCase());
		List<String> tags = new ArrayList<String>();
		List<String> tagContents = new ArrayList<String>();
		List<Integer> startIndex = new ArrayList<Integer>();
		List<Integer> endIndex = new ArrayList<Integer>();
		while (matcher.find()) {
			tagContents.add(matcher.group(1));
			tags.add(matcher.group(0));
			startIndex.add(matcher.start());
			endIndex.add(matcher.end());
		}
		int tagCount = tags.size();
		if (tagCount == 0) {
			return html;
		}
		HashSet<String> ignoredTags = new HashSet<String>();
		ignoredTags.add("p");
		ignoredTags.add("img");
		ignoredTags.add("br");
		ignoredTags.add("li");
		ignoredTags.add("hr");

		int match = 0;
		boolean[] tagPaired = new boolean[tagCount];
		boolean[] tagreMove = new boolean[tagCount];
		String tagName = "";
		String tag = "";
		for (int ctag = 0; ctag < tagCount; ctag++) {
			tagName = StringUtils.substringBefore(StringUtils.trim(tagContents.get(ctag)), " ");
			tag = tags.get(ctag);
			if (tagPaired[ctag] || ignoredTags.contains(tagName)) {
				continue;
			}
			match = -1;
			if (tag.startsWith("</")) {
				for (int ptag = ctag - 1; ptag >= 0; ptag--) {
					String prevtag = tags.get(ptag);
					if (!tagPaired[ptag]) {
						if (prevtag.startsWith("<" + tagName + ">") || prevtag.startsWith("<" + tagName + " ")) {
							match = ptag;
							break;
						}
					}
				}
			} else {
				for (int ntag = ctag + 1; ntag < tagCount; ntag++) {
					if (!tagPaired[ntag] && ("</" + tagName + ">").equals(tags.get(ntag))) {
						match = ntag;
						break;
					}
				}
			}
			tagPaired[ctag] = true;
			if (match == -1) {
				tagreMove[ctag] = true; // mark for removal
			} else {
				tagPaired[match] = true; // mark paired
			}
		}
		StringBuilder sb = new StringBuilder();
		int last = 0;
		for (int i = 0; i < tagreMove.length; i++) {
			if (tagreMove[i]) {
				sb.append(html.substring(last, startIndex.get(i)));
				last = endIndex.get(i);
			}
		}
		sb.append(html.substring(last));
		html = sb.toString();
		return html;
	}

	/**
	 * 四舍五入
	 * 
	 * @param dSource
	 *            浮点数
	 * @return
	 */
	public static int getRound(double dSource) {
		BigDecimal deSource = new BigDecimal(dSource);
		return deSource.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
	}

	public static long getRoundLong(double dSource) {
		BigDecimal deSource = new BigDecimal(dSource);
		return deSource.setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
	}

	public static Float formatNumber2(Float n) throws ParseException {
		DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.CHINESE);
		decimalFormat.setMaximumFractionDigits(2);
		decimalFormat.setMinimumFractionDigits(2);
		// decimalFormat.format(number);
		return decimalFormat.parse(decimalFormat.format(n)).floatValue();
	}

	public static Double formatArea(Double n) throws ParseException {
		DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.CHINESE);
		decimalFormat.setMaximumFractionDigits(1);
		decimalFormat.setMinimumFractionDigits(1);
		return decimalFormat.parse(decimalFormat.format(n)).doubleValue();
	}

	/**
	 * 根据Value对Map进行排序
	 * 
	 * @param targetMap
	 *            key:Object value:Number
	 * @param asc
	 *            true:升序 false:降序
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map sortMapByValue(Map targetMap, final boolean asc) {

		Map resultMap = new LinkedHashMap();
		if (MapUtils.isEmpty(targetMap)) {
			return resultMap;
		}
		List<Entry> infoIds = new ArrayList<Entry>();
		for (Entry entry : (Set<Map.Entry>) targetMap.entrySet()) {
			infoIds.add(entry);
		}

		Collections.sort(infoIds, new Comparator<Map.Entry>() {
			public int compare(Map.Entry o1, Map.Entry o2) {
				if (asc) {
					return NumberUtils.compare(((Number) o1.getValue()).floatValue(), ((Number) o2.getValue()).floatValue());
				} else {
					return NumberUtils.compare(((Number) o2.getValue()).floatValue(), ((Number) o1.getValue()).floatValue());
				}
			}
		});
		for (int i = 0; i < infoIds.size(); i++) {
			resultMap.put(infoIds.get(i).getKey(), infoIds.get(i).getValue());
		}
		return resultMap;
	}

	public static String convertNumberToHanzi(String number) {
		char[] charArray = number.toCharArray();
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < charArray.length; i++) {
			String ca = String.valueOf(charArray[i]);
			if (ca.matches("\\D")) {
				ret.append(ca);
				continue;
			}

			switch (Integer.parseInt(ca)) {
			case 0:
				ret.append("零");
				break;
			case 1:
				ret.append("壹");
				break;
			case 2:
				ret.append("贰");
				break;
			case 3:
				ret.append("叁");
				break;
			case 4:
				ret.append("肆");
				break;
			case 5:
				ret.append("伍");
				break;
			case 6:
				ret.append("陆");
				break;
			case 7:
				ret.append("柒");
				break;
			case 8:
				ret.append("捌");
				break;
			case 9:
				ret.append("玖");
				break;
			default:
				ret.append(ca);
				break;
			}

		}
		// System.out.println(ret.toString());
		return ret.toString();
	}

	public static String convertNumberToCn(String number) {
		char[] charArray = number.toCharArray();
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < charArray.length; i++) {
			String ca = String.valueOf(charArray[i]);
			if (ca.matches("\\D")) {
				ret.append(ca);
				continue;
			}

			switch (Integer.parseInt(ca)) {
			case 0:
				ret.append("0");
				break;
			case 1:
				ret.append("一");
				break;
			case 2:
				ret.append("二");
				break;
			case 3:
				ret.append("三");
				break;
			case 4:
				ret.append("四");
				break;
			case 5:
				ret.append("五");
				break;
			case 6:
				ret.append("六");
				break;
			case 7:
				ret.append("七");
				break;
			case 8:
				ret.append("八");
				break;
			case 9:
				ret.append("九");
				break;
			default:
				ret.append(ca);
				break;
			}

		}
		// System.out.println(ret.toString());
		return ret.toString();
	}

	public static String convertHanziToNumber(String input) {
		char[] charArray = input.toCharArray();
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < charArray.length; i++) {
			String ca = String.valueOf(charArray[i]);

			if (ca.equals("零")) {
				ret.append("0");
			} else if (ca.equals("壹")) {
				ret.append("1");
			} else if (ca.equals("贰")) {
				ret.append("2");
			} else if (ca.equals("叁")) {
				ret.append("3");
			} else if (ca.equals("肆")) {
				ret.append("4");
			} else if (ca.equals("伍")) {
				ret.append("5");
			} else if (ca.equals("陆")) {
				ret.append("6");
			} else if (ca.equals("柒")) {
				ret.append("7");
			} else if (ca.equals("捌")) {
				ret.append("8");
			} else if (ca.equals("玖")) {
				ret.append("9");
			} else {
				ret.append(ca);
			}
		}

		return ret.toString();
	}

	public static String convertHanziToNumber2(String input) {
		char[] charArray = input.toCharArray();
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < charArray.length; i++) {
			String ca = String.valueOf(charArray[i]);

			if (ca.equals("零")) {
				ret.append("0");
			} else if (ca.equals("一")) {
				ret.append("1");
			} else if (ca.equals("二")) {
				ret.append("2");
			} else if (ca.equals("三")) {
				ret.append("3");
			} else if (ca.equals("四")) {
				ret.append("4");
			} else if (ca.equals("五")) {
				ret.append("5");
			} else if (ca.equals("六")) {
				ret.append("6");
			} else if (ca.equals("七")) {
				ret.append("7");
			} else if (ca.equals("八")) {
				ret.append("8");
			} else if (ca.equals("九")) {
				ret.append("9");
			} else {
				ret.append(ca);
			}
		}

		return ret.toString();
	}

	public static Map<String, Object> objectToMap(Object obj) throws NoSuchMethodException, IllegalAccessException,
			InvocationTargetException {
		if (obj == null) {
			return null;
		}

		Map<String, Object> ret = new HashMap<String, Object>();
		Field[] fields = obj.getClass().getDeclaredFields();
		Method[] methods = obj.getClass().getDeclaredMethods();
		Map<String, Method> methodMap = new HashMap<String, Method>();
		for (int i = 0; i < methods.length; i++) {
			methodMap.put(methods[i].getName(), methods[i]);
		}

		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			String methodName = "get" + String.valueOf(fieldName.toCharArray()[0]).toUpperCase() + fieldName.substring(1);
			if (methodMap.containsKey(methodName)) {
				Object result = methodMap.get(methodName).invoke(obj, new Object[] {});
				if (result != null) {
					ret.put(fieldName, result);
				}
			}
		}

		return ret;
	}

	public static long matchMax(long max, long min, long cur) {
		if (cur <= min) {
			return 0;
		}
		if (cur > max) {
			return max;
		}
		return cur;
	}

	public static long matchMin(long max, long min, long cur) {
		if (cur >= max) {
			return 0;
		}
		if (cur < min) {
			return min;
		}
		return cur;
	}

	/**
	 * 是否包含字母
	 * 
	 * @param str
	 * @return
	 */
	public static boolean containsWords(String str) {
		Matcher m = WORDS.matcher(str);
		if (m.find()) {
			return true;
		}

		return false;
	}

	/**
	 * 是否包含非字母
	 * 
	 * @param str
	 * @return
	 */
	public static boolean containsNonWords(String str) {
		Matcher m = NO_WORDS.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}

	/**
	 * 获取字符串中汉字部分
	 * 
	 * @param s
	 * @return
	 */
	public static List<String> getHanzi(String s) {
		List<String> ret = new ArrayList<String>();
		StringBuilder str = new StringBuilder();
		boolean flag = false;
		int length = s.length();
		for (int i = 0; i < length; i++) {
			char c = s.charAt(i);
			if (Character.isLetter(c)) {
				if (c >= 0x41 && c <= 0x7a) {
					flag = false;
				} else {
					str.append(c);
					flag = true;
				}
			}
			if ((!flag || i == length - 1) && StringUtils.isNotBlank(str.toString())) {
				ret.add(str.toString());
				str = new StringBuilder();
			}
		}
		return ret;
	}

	public static String getCnWord(String s) {
		return StringUtils.join(getHanzi(s), "");
	}

	/**
	 * 检查经纬度是否合法
	 * 
	 * @param lat
	 *            纬度为正负900000000000
	 * @param lng
	 *            经度为正负1800000000000
	 * @return
	 */
	public static boolean verifyLatLng(Long lat, Long lng) {
		if (null == lat || null == lng)
			return false;
		Double _lat = lat / 10000000000.0;
		Double _lng = lng / 10000000000.0;
		return verifyLatLng(_lat, _lng);
	}

	/**
	 * 检查经纬度是否合法
	 * 
	 * @param lat
	 *            纬度为正负90
	 * @param lng
	 *            经度为正负180
	 * @return
	 */
	public static boolean verifyLatLng(Double lat, Double lng) {
		if (null == lat || null == lng)
			return false;
		return lat >= -90 && lat <= 90 && lng >= -180 && lng <= 180;
	}

	public static Map<String, String> parse(String parameter, String splitString, String equalChar) {
		Map<String, String> ret = new HashMap<String, String>();
		String[] parameters = StringUtils.splitByWholeSeparator(parameter, splitString);
		for (String s : parameters) {
			String[] o = StringUtils.splitByWholeSeparator(s, equalChar);
			if (o.length == 2) {
				ret.put(o[0], o[1]);
			}
		}
		return ret;
	}

	public static Map<String, String> stringToMap(String str, String split, String eq) {
		Map<String, String> ret = new HashMap<String, String>();
		if (str == null) {
			return ret;
		}

		String[] pairs = str.split(split);
		for (int i = 0; i < pairs.length; i++) {
			String[] arrs = pairs[i].split(eq);
			if (arrs.length == 2) {
				ret.put(arrs[0], arrs[1]);
			} else if (arrs.length == 1) {
				ret.put(arrs[0], "");
			}
		}
		return ret;
	}

	/**
	 * 运行linux命令
	 * 
	 * @param cmd
	 * @return
	 * @throws IOException
	 */
	public static String runLinuxCommand(String cmd) {
		Process p = null;
		InputStream fis = null;
		InputStreamReader isr = null;
		StringBuffer sb = new StringBuffer();
		try {
			p = Runtime.getRuntime().exec(cmd);
			fis = p.getInputStream();
			isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (p != null) {
				p.destroy();
			}

			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e) {
					System.out.println("error in runLinuxCommand close InputStreamReader");
					e.printStackTrace();
				}
			}

			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					System.out.println("error in runLinuxCommand close InputStream");
					e.printStackTrace();
				}
			}
		}

		return sb.toString();
	}

	public static String[] SITE_SUFFIXS = { ".com.cn", ".com", ".cn", ".net", ".org" };

	public static String convertUrlToRoot(String url) {
		if (StringUtils.isBlank(url)) {
			return null;
		}

		for (String suffix : SITE_SUFFIXS) {
			if (url.indexOf(suffix) > -1) {
				String s = StringUtils.substringBefore(url, suffix);
				if (s.indexOf(".") > -1) {
					s = StringUtils.substringAfterLast(s, ".");
					if (StringUtils.isNotBlank(s)) {
						return s + suffix;
					}
				} else if (s.indexOf("://") > -1) {
					s = StringUtils.substringAfterLast(s, "://");
					if (StringUtils.isNotBlank(s)) {
						return s + suffix;
					}
				}
				return StringUtils.substringBefore(url.replace("http://", ""), "/");
			}
		}

		return StringUtils.substringBefore(url.replace("http://", ""), "/");
	}

	public static <T> List<T> combineList(List<T> list0, List<T> list1) {
		if (list0 == null) {
			list0 = new ArrayList<T>();
		}
		if (list1 == null) {
			list1 = new ArrayList<T>();
		}
		if (list0.size() == 0) {
			return list1;
		}
		if (list1.size() == 0) {
			return list0;
		}

		ArrayList<T> mainList = (ArrayList<T>) list0;
		ArrayList<T> slaveList = (ArrayList<T>) list1;
		if (list1.size() > list0.size()) {
			mainList = (ArrayList<T>) list1;
			slaveList = (ArrayList<T>) list0;
		}

		int gap = mainList.size() / slaveList.size();
		for (int i = 0; i < slaveList.size(); i++) {
			mainList.add(i * gap + gap + i, slaveList.get(i));
		}
		return mainList;
	}

	public static byte[] zip(byte[] content) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		GZIPOutputStream outputStream = new GZIPOutputStream(bos);
		outputStream.write(content);
		outputStream.finish();
		return bos.toByteArray();
	}

	/**
	 * 解压
	 * 
	 * @param content
	 * @return
	 * @throws IOException
	 */
	public static byte[] unzip(byte[] content) throws IOException {
		byte[] buff = new byte[8192];
		ByteArrayInputStream bis = new ByteArrayInputStream(content);
		GZIPInputStream inputStream = new GZIPInputStream(bis);

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while (true) {
			int len = inputStream.read(buff);
			if (len > 0) {
				bos.write(buff, 0, len);
			} else {
				break;
			}
		}
		return bos.toByteArray();
	}

	public static String mapToString(Map<String, Object> map, String split) {
		StringBuilder builder = new StringBuilder();
		for (String key : map.keySet()) {
			if (StringUtils.isNotBlank(key)) {
				builder.append(key).append(":").append(map.get(key)).append(split);
			}
		}

		String s = builder.toString();
		if (s.endsWith(",")) {
			s = s.substring(0, s.length() - 1);
		}

		return s;
	}

	public static void main(String[] args) {
		ArrayList<Integer> list0 = new ArrayList<Integer>();
		ArrayList<Integer> list1 = new ArrayList<Integer>();
		for (int i = 0; i < 100; i++) {
			list0.add(i);
		}

		for (int i = -1; i >= -80; i--) {
			list1.add(i);
		}

		System.out.println(list0.size() + "-> " + list0);
		System.out.println(list1.size() + "-> " + list1);
		List<Integer> ret = DataUtil.combineList(list0, list1);
		System.out.println(ret.size() + "-> " + ret);
	}

	public static final Pattern pattern = Pattern.compile("charset=\"?([\\w\\d-]+)\"?;?", Pattern.CASE_INSENSITIVE);

	public static String matchCharset(String input) {
		if (input == null) {
			return null;
		}

		Matcher matcher = pattern.matcher(input);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}
}
